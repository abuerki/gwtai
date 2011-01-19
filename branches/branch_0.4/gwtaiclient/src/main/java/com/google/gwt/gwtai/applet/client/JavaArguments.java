package com.google.gwt.gwtai.applet.client;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Target;

/**
 * The <code>JavaArguments</code> Annotation specifies JVM command-line arguments to be used when
 * executing this applet instance. This makes it possible to increase the heap size and specify
 * command-line arguments on a per-applet basis  For example, to specify larger-than-default maximum
 * heap size use <code>@JavaArguments("-Xmx128m")</code>. Nearly all JVM command-line arguments are
 * supported. The <code>-Xbootclasspath</code> argument and all argument used to specify a path
 * (<code>-classpath</code> or <code>-jar</code>) are forbidden. More information can be found in the
 * <a href='http://java.sun.com/javase/6/docs/technotes/guides/jweb/applet/applet_deployment.html#JAVA_ARGUMENTS'>
 * Java documentation</a>.
 * 
 * Note: Passing JVM arguments through an <code>applet</code> tag is a new feature only available
 * through the new Java Plug-In (>= 1.6.0_10) with support platforms.
 *
 * This Annotation is optional. If no value is given, the respective parameter is simply omitted.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
@Target(TYPE)
public @interface JavaArguments {

	String value();
	
}
