/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.proxy;

import java.util.List;
import org.apache.naming.java.javaURLContextFactory;

/**
 *
 * @author krog
 */
public class ProxyRequest {

    private final String method;
    private final Class returnType;
    private final List parameters;

    public ProxyRequest(String method, Class returnType, List parameters) {
        this.method = method;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public String getMethod() {
        return method;
    }

    public Class getReturnType() {
        return returnType;
    }

    public List getParameters() {
        return parameters;
    }

}
