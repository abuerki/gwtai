/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.proxy;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.net.URL;

/**
 *
 * @author michaelzachariassenkrog
 */
public class AppletProxyStub implements AppletStub {

    private final Applet applet;

    public AppletProxyStub(Applet applet) {
        this.applet = applet;
    }


    public boolean isActive() {
        return applet.isActive();
    }

    public URL getDocumentBase() {
        return applet.getDocumentBase();
    }

    public URL getCodeBase() {
        return applet.getCodeBase();
    }

    public String getParameter(String string) {
        return applet.getParameter(string);
    }

    public AppletContext getAppletContext() {
        return applet.getAppletContext();
    }

    public void appletResize(int i, int i1) {
        applet.resize(i, i1);
    }

}
