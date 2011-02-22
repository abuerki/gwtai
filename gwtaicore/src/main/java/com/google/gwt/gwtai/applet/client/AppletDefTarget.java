/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.client;

import java.util.HashMap;

/**
 *
 * @author michaelzachariassenkrog
 */
public interface AppletDefTarget {

    /**
	 * The identifier of the applet DOM object, that allows it to be used from
	 * JavaScript.
	 *
	 * @return The ID.
	 */
	String getId() ;

	/**
	 * The codebase specifies the directory that contains the applet's code.
	 * This attribute is option, if not specified the module root URL is used.
	 *
	 * @return The codebase URL.
	 */
	String getCodebase();

        /**
	 * The codebase lokoup specifies wether the applet classloader should look
         * for classes in the codebase if not found in the loaded jar.
	 * This attribute is optional, if not specified the value is true.
	 *
	 * @return Wether codebase lookup shouldbe used by the applet.
	 */
	boolean getCodebaseLookup();


	/**
	 * The fully qualified name of the class representing this applet. The
	 * implementation used here must be a subclass of
	 * <code>java.applet.Applet</code> and must implement your
	 * applet-interface which extends from the
	 * <code>com.google.gwtai.applet.client.Applet</code> interface.
	 *
	 * @return The fully qualified class name.
	 */
	String getCode();

	/**
	 * The width defines the x-axis portion of the dimensions.
	 *
	 * @return A value in pixels or as a percentage.
	 */
	String getWidth();

	/**
	 * The height defines the y-axis portion of the dimensions.
	 *
	 * @return A value in pixels or as a percentage.
	 */
	String getHeight();

	String getArchive();

	/**
	 * Leveraging the new version selection mechanism.
	 *
	 * @return A pattern to select the Java version.
	 */
	String getJavaVersion();

	/**
	 * Command-line arguments to be used when executing this applet instance.
	 *
	 * @return Java Command-line Argument.
	 */
	String getJavaArguments();

	/**
	 * A boolean parameter specifying that a particular applet should run in its own JVM instance.
	 *
	 * @return Java Command-line Argument.
	 */
	 Boolean hasSeparateJVM();

	/**
	 * A custom loading graphic show during applet startup.
	 *
	 * @return The name of the image to load.
	 */
	String getLoadingImage();

	HashMap<String, String> getParameters();

	/**
	 * The alignment of the applet, may be left, right, top, texttop, middle,
	 * absmiddle, baseline, bottom or absbottom. If not specified this attribute
	 * is simply omitted.
	 *
	 * @return - The alignment attribute.
	 */
	String getAlign();

	String getName();

	/**
	 * Detect if an <code>Applet</code> is ready. An <code>Applet</code> is
	 * active just before its start method is called.
	 *
	 * @return <code>true</code> if the <code>Applet</code> is active,
	 *         <code>false</code> otherwise.
	 */
	boolean isActive() ;

}
