package com.google.gwt.gwtai.applet.client;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Target;

/**
 * The <code>Param</code> Annotation specifies the value that is filled into the applet's PARAM
 * attribute. With the PARAM attribute any number of parameters (name/value pair) can be passed to
 * the applet.
 * 
 * This Annotation is optional.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 * 
 * @see com.google.gwt.gwtai.applet.client.Height
 */
@Target(TYPE)
public @interface Params {
	String[] names();
	String[] values();
}
