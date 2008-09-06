/*
 * Copyright 2008 Adrian Buerki
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

	static {
		CallbackUtil.defineBridgeMethod();
	}
	
	public static Widget createAppletWidget(Applet applet) {
		if (applet instanceof AppletAccomplice) {
			AppletAccomplice aapplet = (AppletAccomplice) applet;
			
			String htmlCode = "<applet mayscript='true'" + " code='" + aapplet.getCode() +
					"' width='" + aapplet.getWidth() + "' height='" + aapplet.getHeight() +
					"' name='" + aapplet.getName() + "' id='" + aapplet.getName() +
					"' alt='Java Runtime Environment is not working on your system'";

			if (null != aapplet.getCodebase()) {
				htmlCode += "codebase='" + aapplet.getCodebase() +"'";
			}
			
			if (null != aapplet.getArchive()) {
				htmlCode += "archive='" + aapplet.getArchive() +"'";
			}
			
			if (null != aapplet.getAlign()) {
				htmlCode += "align='" + aapplet.getAlign() +"'";
			}

			htmlCode += ">";

			HashMap<String, String> parameters = aapplet.getParameters();
			
			if (parameters != null && !parameters.isEmpty()) {
				for (String name: parameters.keySet()) {
					htmlCode += "<param name='";
					htmlCode += name;
					htmlCode += "' value='";
					htmlCode += parameters.get(name);
					htmlCode += "'>";
				}
			}
				
			htmlCode +=	"</applet>";
			
			return new HTML(htmlCode);
		}
		
		return null;
	}
	
	public static Widget createAppletWidget(Applet applet, String forceJavaVersion) {
		if (applet instanceof AppletAccomplice) {
			AppletAccomplice aapplet = (AppletAccomplice) applet;
			
			String codebase = GWT.getModuleBaseURL();
			
			String htmlCode = "<p style='text-align: center;'><script src='http://java.com/js/deployJava.js'></script>" +
					"<script>deployJava.runApplet({codebase:'" + codebase + "', ";
			
			if (null != aapplet.getArchive()) {
				htmlCode += "archive:'" + aapplet.getArchive() +"', ";
			}
			
			htmlCode += "code:'" +aapplet.getCode() +"', width:'" + aapplet.getWidth()
					+"', Height:'" + aapplet.getHeight() +"'}, null, '" + forceJavaVersion +"');" +
					"</script></p>";

			return new HTML(htmlCode);
		}
		
		return null;
	}
	
	/**
	 * Registers an instance of your <code>AppletCallback</code> implementation to listen for callbacks coming
	 * from the given <code>Applet</code>. The actual <code>Applet</code> implementation can check whether an
	 * <code>AppletCallback</code> handler is installed, if so it can notify the given <code>AppletCallback</code>
	 * instance about updates or events.
	 * 
	 * Example:
	 * 
	 * First, register a listening <code>AppletCallback</code> object:
	 * <code>AppletJSUtil.registerAppletCallback(stopWatchApplet, new StopWatchCallback(panelLaps));</code>
	 *
	 * Then callback to GWT from your <code>Applet</code> implementation (Java):
	 * <code>AppletUtil.callback(StopWatchAppletImpl.this, _swLabel.getText());</code>
	 * 
	 * @param applet - The <code>Applet</code> instance to listen to.
	 * @param appletCallback - The <code>AppletCallback</code> instance to notify once a callback is coming.
	 */
	@SuppressWarnings("unchecked")
	public static void registerAppletCallback(Applet applet, AppletCallback appletCallback) {
		if (applet instanceof AppletAccomplice) {
			AppletAccomplice aapplet = (AppletAccomplice) applet;
			
			CallbackUtil.registerCallback(aapplet.getName(), appletCallback);
		}
	}

	/**
	 * Calls the method with the given name on the <code>Applet</code> instance.
	 *  
	 * @param applet - The <code>Applet</code> instance to call the method on.
	 * @param methodName - The name of the method to call.
	 */
	public static void call(Applet applet, String methodName) {
		if (applet instanceof AppletAccomplice) {
			String id = ((AppletAccomplice) applet).getId();
			Element elem = DOM.getElementById(id);
			
			call(elem, methodName);
		}
	}
	
	/**
	 * Calls the method with the given name on the <code>Applet</code> instance.
	 *  
	 * @param applet - The <code>Applet</code> instance to call the method on.
	 * @param methodName - The name of the method to call.
	 * @param args - The method arguments.
	 */
	public static void call(Applet applet, String methodName, Object[] args) {
		if (applet instanceof AppletAccomplice) {
			String id = ((AppletAccomplice) applet).getId();
			Element elem = DOM.getElementById(id);
			
			call(elem, methodName, args);
		}
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
	private static native Object call(Element elem, String methodName, Object args) /*-{
		var theFunc = elem[methodName];
		return theFunc(args);
	}-*/;

}
