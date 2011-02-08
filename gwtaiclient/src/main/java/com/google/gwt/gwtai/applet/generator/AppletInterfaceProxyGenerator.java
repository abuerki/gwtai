/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.generator;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.rpc.ProxyCreator;
import com.google.gwt.user.rebind.rpc.ServiceInterfaceProxyGenerator;

/**
 *
 * @author michaelzachariassenkrog
 */
public class AppletInterfaceProxyGenerator extends ServiceInterfaceProxyGenerator {

    @Override
    protected ProxyCreator createProxyCreator(JClassType remoteService) {
        return new AppletProxyCreator(remoteService);
    }


}
