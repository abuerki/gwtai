/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.proxy;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import java.applet.Applet;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michaelzachariassenkrog
 */
public class AppletProxy extends Applet {

    public static final String CLASSNAMEPARAM = "classname";

    private Applet proxyFor;
    private String className = null;
    private AppletProxySerializationPolicyProvider policyProvider = new AppletProxySerializationPolicyProvider();
    private AppletProxySerializationPolicy policy = new AppletProxySerializationPolicy();

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

    public String handleRequest(final String data) throws Throwable {
        System.out.println("handling method call:"+data);

        return AccessController.doPrivileged(new PrivilegedAction<String>() {

            public String run() {
                RPCRequest rpcRequest = null;
                try{
                    rpcRequest = RPC.decodeRequest(data, null, policyProvider);
                    Object result = rpcRequest.getMethod().invoke(proxyFor, rpcRequest.getParameters());
                    String returnVar = RPC.encodeResponseForSuccess(rpcRequest.getMethod(), result, policy);

                    System.out.println("returnValue:"+returnVar);
                    return returnVar;
                } catch (Throwable t) {
                    t.printStackTrace();
                    try {
                        return RPC.encodeResponseForFailure(rpcRequest.getMethod(), t);
                    } catch (SerializationException ex) {
                        Logger.getLogger(AppletProxy.class.getName()).log(Level.SEVERE, null, ex);
                        return null;
                    }
                }
            }
        });

        
    }

}
