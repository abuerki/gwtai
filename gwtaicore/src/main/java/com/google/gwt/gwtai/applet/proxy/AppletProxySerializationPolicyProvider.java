/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.proxy;

import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyProvider;

/**
 *
 * @author michaelzachariassenkrog
 */
public class AppletProxySerializationPolicyProvider implements SerializationPolicyProvider {

    private final AppletProxySerializationPolicy policy = new AppletProxySerializationPolicy();

    public SerializationPolicy getSerializationPolicy(String moduleBaseURL, String serializationPolicyStrongName) {
        return policy;
    }

}
