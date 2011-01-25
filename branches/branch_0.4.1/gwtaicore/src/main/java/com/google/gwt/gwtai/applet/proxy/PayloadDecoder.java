/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.proxy;

import java.text.ParseException;

/**
 *
 * @author krog
 */
public interface PayloadDecoder {

    ProxyRequest decodeRequest(String request) throws ParseException;
}
