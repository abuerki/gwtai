/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.client;

import com.google.gwt.http.client.Request;

/**
 *
 * @author michaelzachariassenkrog
 */
public class AppletRequest extends Request {

    @Override
    public void cancel() {
        
    }

    @Override
    public boolean isPending() {
        return false;
    }


}
