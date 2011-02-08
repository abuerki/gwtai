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
public interface CounterAppletAsync {

    public void increment(AsyncCallback<Void> callback);

    public void decrement(AsyncCallback<Void> callback);

    public void getCurrentValue(AsyncCallback<String> callback);

    public void setValue(Integer value, AsyncCallback<Void> callback);
}
