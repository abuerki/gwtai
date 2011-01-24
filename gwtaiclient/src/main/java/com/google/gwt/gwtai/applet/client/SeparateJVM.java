package com.google.gwt.gwtai.applet.client;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Target;

/**
 * The <code>SeparateJVM</code> Annotation specifies that a particular applet should run in its own
 * JVM instance. Rather than executing all applets in one JVM instance a new JVM instance is created
 * for this applet. 
 * 
 * Note: This feature is only available on systems which have the new Java Plug-In (>= 1.6.0_10)
 * installed.
 *
 * This Annotation is optional. If no value is given, the respective parameter is simply omitted. The
 * default behavior of the plug-in is to run all applets in the same JVM instance.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
@Target(TYPE)
public @interface SeparateJVM {

	boolean value();
	
}
