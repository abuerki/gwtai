/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.proxy;

import com.google.gwt.gwtai.applet.client.Base64Util;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author krog
 */
public class PayloadTranslator implements PayloadEncoder, PayloadDecoder {

    public String encodePayload(Object data) {
        return encodeData(data);
    }

    public ProxyRequest decodeRequest(String request) throws ParseException {
        String[] requestElements = request.split("\\|");

        if(requestElements.length < 2) {
            throw new ParseException("Request must contain at least 2 elements(methodname and return type.)", 0);
        }

        String methodName = requestElements[0];
        Class returnType = typeFromString(requestElements[1]);
        List params = new ArrayList();

        //The rest of the elements are parameters
        for(int i=2;i<requestElements.length;i++) {
            String[] paramElements = requestElements[i].split(":");
            if(requestElements.length < 2) {
                throw new ParseException("Parameter must contain at least 2 elements(type and data.)", 0);
            }

            Class paramType = typeFromString(paramElements[0]);
            Object data = decodeData(paramType, paramElements[1]);
            params.add(data);
        }

        return new ProxyRequest(methodName, returnType, params);
    }

    private Class typeFromString(String type) throws ParseException {
        Class definedType = null;
        boolean array = type.length() == 2 && type.endsWith("A");
        type = type.substring(0,1);

        if("B".equals(type))
            definedType = Byte.class;
        if("I".equals(type))
            definedType = Integer.class;
        if("L".equals(type))
            definedType = Long.class;
        if("F".equals(type))
            definedType = Float.class;
        if("D".equals(type))
            definedType = Double.class;
        if("Z".equals(type))
            definedType = Boolean.class;
        if("S".equals(type))
            definedType = String.class;
        if("V".equals(type))
            definedType = Void.class;

        if(definedType == null) {
            throw new ParseException("Could not parse datatype[type="+type+"]", 0);
        }

        if (array) {
            Object obj = Array.newInstance(definedType, 0);
            definedType = obj.getClass();
        }

        return definedType;
    }

    private Object decodeData(Class type, String data) {
        if(type.isArray()) {
            if(type.getComponentType() == Byte.class) {
                return Base64Util.decode(data);
            }
            return decodeData(type, data.split(","));
        }

        if(type == Byte.class) {
            return Byte.parseByte(data);
        }
        
        if(type == String.class){
            return Base64Util.decodeString(data);
        }
        
        if(type == Integer.class){
            return Integer.parseInt(data);
        }
        if(type == Long.class){
            return Long.parseLong(data);
        }
        
        if(type == Float.class){
            return Float.parseFloat(data);
        }
        
        if(type == Double.class){
            return Double.parseDouble(data);
        }
        
        if(type == Boolean.class){
            return data.equals("1");
        }
        return null;

    }

    private Object decodeData(Class type, String[] data) {
        Class arrayType = type.getComponentType();
        Object[] array = (Object[])Array.newInstance(arrayType, data.length);

        for(int i=0;i<data.length;i++) {
            array[i] = decodeData(arrayType, data[i]);
        }
        
        return array;
    }

    private String encodeData(Object data) {
        Class type = data.getClass();
        if(type.isArray()) {
            if(type.getComponentType() == Byte.class) {
                return new String(Base64Util.encode((byte[])data));
            }
            return encodeData((Object[])data);
        }

        if(type == Byte.class) {
            return ((Byte)data).toString();
        }

        if(type == String.class){
            return Base64Util.encodeString((String)data);
        }

        if(type == Integer.class){
            return ((Integer)data).toString();
        }
        if(type == Long.class){
            return ((Long)data).toString();
        }

        if(type == Float.class){
            return ((Float)data).toString();
        }

        if(type == Double.class){
            return ((Double)data).toString();
        }

        if(type == Boolean.class){
            return ((Boolean)data)?"1":"0";
        }
        return null;

    }

    private String encodeData(Object[] data) {
        String encoded="";
        for(int i=0;i<data.length;i++) {
            if(i==0)
                encoded+=",";
            encoded+=","+encodeData(data[i]);
        }

        return encoded;
    }



}
