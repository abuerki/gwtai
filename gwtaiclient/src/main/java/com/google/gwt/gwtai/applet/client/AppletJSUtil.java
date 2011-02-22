/*
 * Copyright 2010 Adrian Buerki
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.gwt.gwtai.applet.client;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * GWT-side utility class.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class AppletJSUtil {

        private static final String PROXYCLASS = "com.google.gwt.gwtai.applet.proxy.AppletProxy";
        private static final String PROXYCLASSPARAM = "classname";

	static {
		CallbackUtil.defineBridgeMethod();
	}

	/**
	 * Constructs an <code>HTML</code> element which contains an applet tag. The HTML specification
	 * states that the <code>applet</code> tag is deprecated. But the browser support for the <code>object</code>
	 * and <code>embed</code> tag is currently inconsistent. So using the <code>applet</code> tag is the only 
	 * consistent way to deploy a Java Applet across browsers on all platforms.
	 *
	 * @param applet The <code>Applet</code> to take the information from.
	 * @return An <code>HTML</code> element which contains an applet tag.
	 */
	public static Widget createAppletWidget(AppletDefTarget defTarget) {
		String htmlCode = createAppletHTML(defTarget);

		if (htmlCode != null) {
			return new HTML(htmlCode);
		}

		return null;
	}

	/**
	 * Constructs an applet tag. The HTML specification states that the <code>applet</code> tag is deprecated.
	 * But the browser support for the <code>object</code> and <code>embed</code> tag is currently
	 * inconsistent. So using the <code>applet</code> tag is the only consistent way to deploy a Java Applet
	 * across browsers on all platforms.
	 *
         * In order to support more rich datatype flow between GWT and the Applet this tag loads a proxy applet which
         * in turn will load the real applet. The real applet class will be specified via a parameter in the applet tag.
         *
	 * @param applet The <code>Applet</code> to take the information from.
	 * @return The applet tag.
	 */
	public static String createAppletHTML(AppletDefTarget defTarget) {
		
			

			String htmlCode = "<applet mayscript='true'"
					+ " code='"
                                        + PROXYCLASS
					+ "' width='"
					+ defTarget.getWidth()
					+ "' height='"
					+ defTarget.getHeight()
					+ "' name='"
					+ defTarget.getName()
					+ "' id='"
					+ defTarget.getName()
					+ "' alt='Java Runtime Environment is not working on your system'";

			if (null != defTarget.getCodebase()) {
				htmlCode += "codebase='" + defTarget.getCodebase() + "'";
			}

			if (null != defTarget.getArchive()) {
				htmlCode += "archive='" + defTarget.getArchive() + "'";
			}

			if (null != defTarget.getAlign()) {
				htmlCode += "align='" + defTarget.getAlign() + "'";
			}

			htmlCode += ">";

			HashMap<String, String> parameters = defTarget.getParameters();

                        //Add parameter for proxy
                        String classname = defTarget.getCode();
                        if(classname.endsWith(".class"))
                            classname = classname.substring(0,classname.length()-6);
                        htmlCode+=createParamTag(PROXYCLASSPARAM, classname);

			if (parameters != null && !parameters.isEmpty()) {
				for (String name : parameters.keySet()) {
					htmlCode += createParamTag(name, parameters.get(name));
				}
			}
			
			htmlCode += createParamTag("java_version", defTarget.getJavaVersion());
			htmlCode += createParamTag("java_arguments", defTarget.getJavaArguments());
			htmlCode += createParamTag("separate_jvm", defTarget.hasSeparateJVM());
			htmlCode += createParamTag("image", defTarget.getLoadingImage());
			htmlCode += createParamTag("applet_name", defTarget.getName());
			htmlCode += createParamTag("codebase_lookup", defTarget.getCodebaseLookup());

			htmlCode += "</applet>";

			return htmlCode;
		
	}

	public static Widget createAppletWidget(AppletDefTarget defTarget,
			String forceJavaVersion) {
		
			String codebase = GWT.getModuleBaseURL();

			String htmlCode = "<p style='text-align: center;'><script src='http://java.com/js/deployJava.js'></script>"
					+ "<script>deployJava.runApplet({codebase:'"
					+ codebase
					+ "', ";

			if (null != defTarget.getArchive()) {
				htmlCode += "archive:'" + defTarget.getArchive() + "', ";
			}

			htmlCode += "code:'" + PROXYCLASS + "', width:'"
					+ defTarget.getWidth() + "', Height:'" + defTarget.getHeight()
					+ "'}, {"+PROXYCLASSPARAM+":"+defTarget.getCode()+"}, '" + forceJavaVersion + "');"
					+ "</script></p>";

			return new HTML(htmlCode);
		
	}

	/**
	 * Registers an instance of your <code>AppletCallback</code>
	 * implementation to listen for callbacks coming from the given
	 * <code>Applet</code>. The actual <code>Applet</code> implementation
	 * can check whether an <code>AppletCallback</code> handler is installed,
	 * if so it can notify the given <code>AppletCallback</code> instance
	 * about updates or events.
	 * 
	 * Example:
	 * 
	 * First, register a listening <code>AppletCallback</code> object:
	 * <code>AppletJSUtil.registerAppletCallback(stopWatchApplet,
	 * new StopWatchCallback(panelLaps));</code>
	 * 
	 * Then callback to GWT from your <code>Applet</code> implementation
	 * (Java):
	 * <code>AppletUtil.callback(StopWatchAppletImpl.this, _swLabel.getText());</code>
	 * 
	 * @param applet -
	 *            The <code>Applet</code> instance to listen to.
	 * @param appletCallback -
	 *            The <code>AppletCallback</code> instance to notify once a
	 *            callback is coming.
	 */
	public static void registerAppletCallback(AppletDefTarget defTarget,
			AppletCallback<? extends Object> appletCallback) {
			
			CallbackUtil.registerCallback(defTarget.getName(), appletCallback);
		
	}

	/**
	 * Calls the method with the given name on the <code>Applet</code>
	 * instance.
	 * 
	 * @param applet -
	 *            The <code>Applet</code> instance to call the method on.
	 * @param methodName -
	 *            The name of the method to call.
	 */
	public static void call(AppletDefTarget target, String methodName) {
                String id = target.getId();
                Element elem = DOM.getElementById(id);

                call(elem, methodName);

	}

	/**
	 * Calls the method with the given name on the <code>Applet</code>
	 * instance.
	 * 
	 * @param applet -
	 *            The <code>Applet</code> instance to call the method on.
	 * @param methodName -
	 *            The name of the method to call.
	 * @param args -
	 *            The method arguments.
	 */
	public static void call(AppletDefTarget target, String methodName, Object[] args) {
            String id = target.getId();
            Element elem = DOM.getElementById(id);

            call(elem, methodName, args);

	}

	/**
	 * Helper-method to create a param tag.
	 */
	private static String createParamTag(String name, Object value) {
		if (value != null) {
			return "<param name='" + name + "' value='" + value + "'>";	
		}

		return "";
	}
	
	/**
	 * Helper-method to do the actual calling.
	 */
	private static native Object call(Element elem, String methodName) /*-{
		var theFunc = elem[methodName];
		return theFunc();
	}-*/;

	/**
	 * Helper-method to do the actual calling.
	 */
	private static native Object call(Element elem, String methodName,
			Object args) /*-{
		var theFunc = elem[methodName];
		return theFunc(args);
	}-*/;

}
