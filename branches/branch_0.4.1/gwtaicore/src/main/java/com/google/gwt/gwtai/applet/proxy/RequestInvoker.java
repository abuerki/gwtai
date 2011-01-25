/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.proxy;

import com.google.gwt.gwtai.applet.client.ProxyRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author krog
 */
public class RequestInvoker {

    private final Object invokeObject;

    public RequestInvoker(Object invokeObject) {
        this.invokeObject = invokeObject;
    }

    public Object invoke(ProxyRequest request) throws NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        List<Class> paramTypes = new ArrayList<Class>();
        for(Object object : request.getParameters()) {
            paramTypes.add(object.getClass());
        }

        Method method = invokeObject.getClass().getMethod(request.getMethod(), paramTypes.toArray(new Class[0]));
        return method.invoke(invokeObject, request.getParameters().toArray());
    }
}
