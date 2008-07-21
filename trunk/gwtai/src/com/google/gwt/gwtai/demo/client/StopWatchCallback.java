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

import com.google.gwt.gwtai.applet.client.AppletCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * An example implementation of the  <code>AppletCallback</code> interface.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class StopWatchCallback implements AppletCallback<String> {
	private VerticalPanel _panelLaps;
	private int _lap;
	
	public StopWatchCallback(VerticalPanel panelLaps) {
		_panelLaps = panelLaps;
		_lap = 1;
	}

	public void callback(String msg) {
		_panelLaps.add(new HTML("<b>Lap " + _lap + "</b> : " + msg + " seconds"));
		_lap++;
	}

}
