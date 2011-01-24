package com.google.gwt.gwtai.applet.client;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Target;

/**
 * The <code>LoadingImage</code> Annotation allows you to replace the default animation with a custom
 * graphic. Optimally the image should be the same size as the area of the applet widget. If the dimensions
 * don't match, the image will be placed in the upper left corner of the applet area. The image can be a GIF
 * a or JPEG, and it has to be placed in the resources directory of the applet (codebase directory). Don't
 * put the file inside of a JAR file, since the image needs to be displayed before the JAR's are loaded.
 *
 * This Annotation is optional. If no value is given, the respective parameter is simply omitted and the
 * default Java logo is displayed.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
@Target(TYPE)
public @interface LoadingImage {
	
	String value();

}