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

package com.google.gwt.gwtai.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.gwtai.applet.client.AppletCallback;
import com.google.gwt.gwtai.applet.client.AppletJSUtil;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point represents the demo's main class, its <code>onModuleLoad()</code>
 * it the first thing that is called once the page is loaded.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class GwtAI implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setWidth("600px");

		tabPanel.getDeckPanel().setAnimationEnabled(true);

		tabPanel.add(new CounterAppletTab(), "Counter");
		tabPanel.add(new StopWatchAppletTab(), "Stop Watch");
		tabPanel.add(new TrayIconAppletTab(), "Tray Icon");
		tabPanel.add(createCallbackApplet(), "CallbackApplet");

		tabPanel.selectTab(0);

		RootPanel.get().add(tabPanel);
	}
	
	private Widget createCallbackApplet() {
		CallbackApplet  applet = (CallbackApplet) GWT.create(CallbackApplet.class);
		Widget widgetAppletOne = AppletJSUtil.createAppletWidget(applet);
		RootPanel.get().add(widgetAppletOne);
		
		AppletJSUtil.registerAppletCallback(applet, new AppletCallback<String>() {
			public void callback(String callbackValue) {
				Window.alert("Received: "+callbackValue);
			}
		});
		
		return widgetAppletOne;
	}

}
