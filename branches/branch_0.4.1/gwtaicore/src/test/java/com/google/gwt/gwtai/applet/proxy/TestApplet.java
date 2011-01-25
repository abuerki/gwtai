/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.proxy;

import java.applet.Applet;

/**
 *
 * @author krog
 */
public class TestApplet extends Applet {
        boolean invoked;

        public String handleByte2String(byte[] data) {
            invoked = true;
            return new String(data);
        }

        public boolean handleBoolean2boolean(Boolean bool) {
            invoked= true;
            return bool;
        }

        public Long handleLong(Long value) {
            invoked= true;
            return value;
        }

        public Double handleDouble(Double value) {
            invoked= true;
            return value;
        }

        public boolean isInvoked() {
            return invoked;
        }
}
