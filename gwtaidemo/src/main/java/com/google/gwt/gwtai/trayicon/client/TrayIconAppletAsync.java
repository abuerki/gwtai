/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.trayicon.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author michaelzachariassenkrog
 */
public interface TrayIconAppletAsync {

    void showTrayIcon(AsyncCallback<Void> callback);

    void hideTrayIcon(AsyncCallback<Void> callback);

    void addSeparator(AsyncCallback<Void> callback);

    void addTextItem(String caption,AsyncCallback<Void> callback);

    void addCheckBoxItem(String caption,AsyncCallback<Void> callback);
}
