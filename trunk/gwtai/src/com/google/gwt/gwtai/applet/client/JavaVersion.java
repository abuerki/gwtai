package com.google.gwt.gwtai.applet.client;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Target;

/**
 * The <code>JavaVersion</code> Annotation specifies a which JRE version is required to launch
 * this Applet.  This makes it possible to select a particular version of the JRE for an individual
 * applet. The default behavior is to run on top of the latest available JRE on the system.
 * 
 * Note: The <code>java_version</code> parameter requires the new Java Plug-In (>= 1.6.0_10) to work,
 * older version will ignore it. If the new Java Plug-In is not present, then browser-specific
 * mechanisms are needed, see <a href='http://jdk6.dev.java.net/plugin2/version-selection/#JAVA_VERSION'>here</a>.
 * 
 * The <code>JavaVersion</code> Annotation can be used to select:
 * 
 * <ul>
 * <li>A particular JRE version, e.g. "1.5.0_11"</li>
 * <li>A particular JRE family, e.g. "1.5*"</li>
 * <li>A particular JRE family or later, e.g. "1.5+"</li>
 * </ul>  
 * 
 * This Annotation is optional. If no value is given, the respective parameter is simply omitted.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
@Target(TYPE)
public @interface JavaVersion {

	String value();
	
}
