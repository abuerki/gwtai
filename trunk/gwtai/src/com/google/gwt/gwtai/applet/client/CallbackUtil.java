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

/**
 * Injects code to bridge between GWT and applets.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class CallbackUtil {
	public static HashMap<String, AppletCallback> _appletCallbacks = new HashMap<String, AppletCallback>();
	
	public static void registerCallback(String appletName, AppletCallback appletCallback) {
		_appletCallbacks.put(appletName, appletCallback);
	}

	public static void callbackApplet(String appletName, Object callbackValue) {
		AppletCallback appletCallback = _appletCallbacks.get(appletName);
		
		if (null != appletCallback) {
			appletCallback.callback(callbackValue);
		}
	}

	public static native void defineBridgeMethod() /*-{
	  	$wnd.callbackApplet = function(appletName, callbackValue) {
	    	@com.google.gwt.gwtai.applet.client.CallbackUtil::callbackApplet(Ljava/lang/String;Ljava/lang/Object;)(appletName, callbackValue);
		}
	}-*/;

}
