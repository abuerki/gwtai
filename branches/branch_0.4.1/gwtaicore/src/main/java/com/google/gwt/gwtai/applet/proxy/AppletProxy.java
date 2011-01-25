/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.proxy;

import java.applet.Applet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michaelzachariassenkrog
 */
public class AppletProxy extends Applet {

    private Applet proxyFor;

    
    @Override
    public void init() {
        try {
            String classname = getParameter("classname");
            proxyFor = (Applet) Class.forName(classname).newInstance();
            proxyFor.init();
            add(proxyFor);
        } catch (Exception ex) {
            Logger.getLogger(AppletProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start() {
        proxyFor.start();
    }

    @Override
    public void stop() {
        proxyFor.stop();
    }

    public String handleMethodCall(String data) {
        return null;
    }

}
