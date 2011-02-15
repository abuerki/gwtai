/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.proxy;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.SerializationPolicy;

/**
 *
 * @author michaelzachariassenkrog
 */
public class AppletProxySerializationPolicy extends SerializationPolicy {

    @Override
    public boolean shouldDeserializeFields(Class<?> clazz) {
        if(clazz == Object.class)
            return false;
        return true;
    }

    @Override
    public boolean shouldSerializeFields(Class<?> clazz) {
        if(clazz == Object.class)
            return false;
        return true;
    }

    @Override
    public void validateDeserialize(Class<?> clazz) throws SerializationException {
    }

    @Override
    public void validateSerialize(Class<?> clazz) throws SerializationException {
    }

}
