/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.client;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author krog
 */
public class GwtProxyTranslator {

    public String encodeResponse(Object data) {
        return encodeParameter(data);
    }

    public Object decodeResponse(String response) {
        try {
            return decodeParameter(response);
        } catch (ParseException ex) {
            throw new RuntimeException("Unable to parse reponse.",ex);
        }
    }

    public String encodeRequest(ProxyRequest request) {
        String encoded = request.getMethod()+"|"+typeFromClass(request.getReturnType());
        for(Object o : request.getParameters())
            encoded+="|"+encodeParameter(o);
        return encoded;
    }

    public ProxyRequest decodeRequest(String request) {
        String[] requestElements = request.split("\\|");

        try{
            if(requestElements.length < 2) {
                throw new ParseException("Request must contain at least 2 elements(methodname and return type.)", 0);
            }

            String methodName = requestElements[0];
            Class returnType = typeFromString(requestElements[1]);
            List params = new ArrayList();

            //The rest of the elements are parameters
            for(int i=2;i<requestElements.length;i++) {
                Object data = decodeParameter(requestElements[i]);
                params.add(data);
            }

            return new ProxyRequest(methodName, returnType, params);
        } catch(ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Class typeFromString(String type) throws ParseException {
        Class definedType = null;
        boolean array = type.length() == 2 && type.endsWith("A");
        type = type.substring(0,1);

        if("B".equals(type))
            definedType = array ? new Byte[0].getClass():Byte.class;
        if("I".equals(type))
            definedType = array ? new Integer[0].getClass():Integer.class;
        if("L".equals(type))
            definedType = array ? new Long[0].getClass():Long.class;
        if("F".equals(type))
            definedType = array ? new Float[0].getClass():Float.class;
        if("D".equals(type))
            definedType = array ? new Double[0].getClass():Double.class;
        if("Z".equals(type))
            definedType = array ? new Boolean[0].getClass():Boolean.class;
        if("S".equals(type))
            definedType = array ? new String[0].getClass():String.class;
        if("V".equals(type))
            definedType = Void.class;
        if("v".equals(type))
            definedType = void.class;

        if(definedType == null) {
            throw new ParseException("Could not parse datatype[type="+type+"]", 0);
        }

        return definedType;
    }

    private String typeFromClass(Class type){
        String definedType = null;
        boolean array = type.isArray();
        type = array ? type.getComponentType() : type;

        if(Byte.class == type)
            definedType = "B";
        if(Integer.class == type)
            definedType = "I";
        if(Long.class == type)
            definedType = "L";
        if(Float.class==type)
            definedType = "F";
        if(Double.class == type)
            definedType = "D";
        if(Boolean.class == type)
            definedType = "Z";
        if(String.class==type)
            definedType = "S";
        if(Void.class==type)
            definedType = "V";
        if(void.class==type)
            definedType = "v";

        if(definedType == null) {
            throw new IllegalArgumentException("Class is not supported [type="+type+"]");
        }

        if (array) {
            definedType+="A";
        }

        return definedType;
    }

    private Object decodeParameter(String data) throws ParseException {
        String[] paramElements = data.split(":");
        if(paramElements.length < 2) {
            throw new ParseException("Parameter must contain at least 2 elements(type and data.)", 0);
        }

        Class paramType = typeFromString(paramElements[0]);
        return decodeData(paramType, paramElements[1]);
    }

    private String encodeParameter(Object data) {
        return typeFromClass(data.getClass())+":"+encodeData(data);
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
        Object[] array = createArray(arrayType, data.length);

        for(int i=0;i<data.length;i++) {
            array[i] = decodeData(arrayType, data[i]);
        }
        
        return array;
    }

    private Object[] createArray(Class type, int length) {
        if(type==Byte.class)
            return new Byte[length];
        if(type==String.class)
            return new String[length];
        if(type==Integer.class)
            return new Integer[length];
        if(type==Long.class)
            return new Long[length];
        if(type==Float.class)
            return new Float[length];
        if(type==Double.class)
            return new Double[length];
        if(type==Boolean.class)
            return new Boolean[length];
        return null;
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
