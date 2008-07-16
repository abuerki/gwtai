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

import netscape.javascript.JSObject;

/**
 * Applet-side utility class.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */ 
public class AppletUtil {
	
	/**
	 * At the moment only <code>String</code>s are supported.
	 * 
	 * @param applet
	 * @param callbackValue
	 */
	public static void callback(Applet applet, String callbackValue) {
		String appletName = getAppletName(applet);
		
		String jsCmd = "callbackApplet(\"" + appletName + "\", \"" + callbackValue + "\");";

		eval(applet, jsCmd);
	}
	
	public static Object eval(Applet applet, String jsCmd) {
		JSObject jso = JSObject.getWindow(applet);
		
		if (jso != null ) {
			return jso.eval(jsCmd);
		}

		return null;
	}

	public static Object call(Applet applet, String jsCmd, Object[] params) {
		JSObject jso = JSObject.getWindow(applet);
		
		if (jso != null ) {
			return jso.call(jsCmd, params);
		}

		return null;
	}
	
	private static String getAppletName(Applet applet) {
		String tmp = applet.getClass().getName();
		
		if (tmp.lastIndexOf(".") > -1) {
			return tmp.substring(tmp.lastIndexOf(".") + 1);
		} else {
			return tmp;
		}
	}

}
