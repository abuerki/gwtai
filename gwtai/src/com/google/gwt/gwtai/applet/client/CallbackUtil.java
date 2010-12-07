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

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Injects code to bridge between GWT and applets.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class CallbackUtil {
	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");

	public static HashMap<String, AppletCallback<?>> _appletCallbacks = new HashMap<String, AppletCallback<?>>();

	public static void registerCallback(String appletName,
			AppletCallback<? extends Object> appletCallback) {
		_appletCallbacks.put(appletName, appletCallback);
	}

	public static void callbackApplet(String appletName, String callbackValue, String callbackType) {
		@SuppressWarnings("unchecked")
		AppletCallback<Object> appletCallback = (AppletCallback<Object>) _appletCallbacks.get(appletName);

		Object parsedCallbackValue;

		if (callbackType.equals(Integer.class.getName())) {
			parsedCallbackValue = Integer.valueOf(callbackValue);
		} else if (callbackType.equals(Float.class.getName())) {
			parsedCallbackValue = Float.valueOf(callbackValue);
		} else if (callbackType.equals(Date.class.getName())) {
			parsedCallbackValue = DATE_FORMAT.parse(callbackValue);
		} else {
			parsedCallbackValue = Base64Util.decodeString(callbackValue);
		}

		if (null != appletCallback) {
			appletCallback.callback(parsedCallbackValue);
		}
	}

	public static native void defineBridgeMethod() /*-{
		$wnd.callbackApplet = function(appletName, callbackValue, callbackType) {
			@com.google.gwt.gwtai.applet.client.CallbackUtil::callbackApplet(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(appletName, callbackValue, callbackType);
		}
	}-*/;

}