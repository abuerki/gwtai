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

package com.google.gwt.gwtai.applet.util;

import java.applet.Applet;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gwt.gwtai.applet.client.Base64Util;

import netscape.javascript.JSObject;

/**
 * Applet-side utility class.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class AppletUtil {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * <code>Float</code>s will be converted and sent as <code>String</code>s,
	 * once arrived in the JavaScript world GwtAI parses the number back into an
	 * appropriate GWT object.
	 * 
	 * @param applet -
	 *            The <code>Applet</code> doing the callback.
	 * @param callbackValue -
	 *            The value to send.
	 */
	public static void callback(Applet applet, Float callbackValue) {
		String transferString;

		if (null == callbackValue) {
			transferString = "";
		} else {
			transferString = callbackValue.toString();
		}

		callback(applet, transferString, Float.class.getName());
	}

	/**
	 * <code>Integer</code>s will be converted and sent as
	 * <code>String</code>s, once arrived in the JavaScript world GwtAI
	 * parses the number back into an appropriate GWT object.
	 * 
	 * @param applet -
	 *            The <code>Applet</code> doing the callback.
	 * @param callbackValue -
	 *            The value to send.
	 */
	public static void callback(Applet applet, Integer callbackValue) {
		String transferString;

		if (null == callbackValue) {
			transferString = "";
		} else {
			transferString = callbackValue.toString();
		}

		callback(applet, transferString, Integer.class.getName());
	}

	/**
	 * <code>Dates</code>s will be converted and sent as <code>String</code>s,
	 * once arrived in the JavaScript world GwtAI parses the date back into an
	 * appropriate GWT object.
	 * 
	 * @param applet -
	 *            The <code>Applet</code> doing the callback.
	 * @param callbackValue -
	 *            The value to send.
	 */
	public static void callback(Applet applet, Date callbackValue) {
		String transferString;

		if (null == callbackValue) {
			transferString = "";
		} else {
			transferString = DATE_FORMAT.format(callbackValue);
		}

		callback(applet, transferString, Date.class.getName());
	}

	/**
	 * The an easy one, everything is transfered as <code>String</code>
	 * anyway.
	 * 
	 * @param applet -
	 *            The <code>Applet</code> doing the callback.
	 * @param callbackValue -
	 *            The value to send.
	 */
	public static void callback(Applet applet, String callbackValue) {
		String encodedValue = Base64Util.encodeString(callbackValue);
		callback(applet, encodedValue, String.class.getName());
	}

	/**
	 * Does the actual calling.
	 * 
	 * @param applet -
	 *            The <code>Applet</code> doing the callback.
	 * @param callbackValue -
	 *            The value to send.
	 * @param callbackType -
	 *            The type of the value, so we can parse it back later on.
	 */
	private static void callback(Applet applet, String callbackValue,
			String callbackType) {
		String appletName = applet.getParameter("applet_name");

		String jsCmd = "callbackApplet(\"" + appletName + "\", \""
				+ callbackValue + "\", \"" + callbackType + "\");";

		eval(applet, jsCmd);
	}

	public static Object eval(Applet applet, String jsCmd) {
		JSObject jso = JSObject.getWindow(applet);

		if (jso != null) {
			return jso.eval(jsCmd);
		}

		return null;
	}

	public static Object call(Applet applet, String jsCmd, Object[] params) {
		JSObject jso = JSObject.getWindow(applet);

		if (jso != null) {
			return jso.call(jsCmd, params);
		}

		return null;
	}

}
