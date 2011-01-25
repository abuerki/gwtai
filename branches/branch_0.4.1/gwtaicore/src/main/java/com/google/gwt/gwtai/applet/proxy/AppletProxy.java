/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.proxy;

import java.applet.Applet;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michaelzachariassenkrog
 */
public class AppletProxy extends Applet {

    private Applet proxyFor;
    private RequestInvoker invoker;
    private PayloadDecoder decoder = new PayloadTranslator();
    private PayloadEncoder encoder = new PayloadTranslator();
    private String className = null;

    @Override
    public void init() {
        try {
            try{
                if(getParameter("classname")!=null)
                    className = getParameter("classname");
            } catch(Exception ex) { }

            proxyFor = (Applet) Class.forName(className).newInstance();
            invoker = new RequestInvoker(proxyFor);
            proxyFor.init();
            add(proxyFor);
        } catch (Exception ex) {
            Logger.getLogger(AppletProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Applet getProxyFor() {
        return proxyFor;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public void start() {
        proxyFor.start();
    }

    @Override
    public void stop() {
        proxyFor.stop();
    }

    public String handleMethodCall(String data) throws Exception {
        ProxyRequest request = decoder.decodeRequest(data);
        Object result = invoker.invoke(request);
        return encoder.encodePayload(result);
    }

}
