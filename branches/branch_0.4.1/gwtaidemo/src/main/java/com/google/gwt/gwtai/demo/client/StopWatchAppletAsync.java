/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.demo.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author michaelzachariassenkrog
 */
public interface StopWatchAppletAsync {

    public void stopWatch(AsyncCallback<Void> callback);

    public void startWatch(AsyncCallback<Void> callback);
}
