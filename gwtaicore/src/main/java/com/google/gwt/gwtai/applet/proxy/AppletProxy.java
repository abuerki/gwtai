/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.proxy;

import com.google.gwt.gwtai.applet.client.GwtProxyTranslator;
import com.google.gwt.gwtai.applet.client.ProxyRequest;
import java.applet.Applet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michaelzachariassenkrog
 */
public class AppletProxy extends Applet {

    public static final String CLASSNAMEPARAM = "classname";

    private Applet proxyFor;
    private RequestInvoker invoker;
    private GwtProxyTranslator translator = new GwtProxyTranslator();
    private String className = null;

    @Override
    public void init() {
        try {
            try{
                if(getParameter(CLASSNAMEPARAM)!=null)
                    className = getParameter(CLASSNAMEPARAM);
            } catch(Exception ex) { }

            System.out.println("init:"+className);

            proxyFor = (Applet) Class.forName(className).newInstance();
            proxyFor.setStub(new AppletProxyStub(this));
            invoker = new RequestInvoker(proxyFor);
            proxyFor.init();
            add(proxyFor);

            System.out.println( "inited.");

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

    public String handleRequest(String data) throws Throwable {
        System.out.println("handling method call:"+data);
        try{
            ProxyRequest request = translator.decodeRequest(data);
            Object result = invoker.invoke(request);

            String returnVar = translator.encodeResponse(result);
            System.out.println("returnValue:"+returnVar);
            return returnVar;
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }

}
