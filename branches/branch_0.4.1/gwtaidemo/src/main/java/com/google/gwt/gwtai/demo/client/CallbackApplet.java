package com.google.gwt.gwtai.demo.client;

import com.google.gwt.gwtai.applet.client.Applet;
import com.google.gwt.gwtai.applet.client.Height;
import com.google.gwt.gwtai.applet.client.ImplementingClass;
import com.google.gwt.gwtai.applet.client.Width;

@ImplementingClass(com.google.gwt.gwtai.demo.impl.CallbackAppletImpl.class)
@Height("100")
@Width("350")
public interface CallbackApplet extends Applet {

}
