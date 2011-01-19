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

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gwtai.applet.client.AppletJSUtil;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The content of the <code>StopWatchApplet</code> demo tab.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class StopWatchAppletTab extends Composite {

	public StopWatchAppletTab() {
		VerticalPanel panelMain = new VerticalPanel();
		panelMain.setWidth("100%");
		panelMain.setSpacing(4);

		VerticalPanel panelLaps = new VerticalPanel();
		panelLaps.setWidth("100%");
		panelLaps.setSpacing(4);

		Button buttonStart = new Button("Start");
		Button buttonStop = new Button("Stop");

		final StopWatchApplet stopWatchApplet = (StopWatchApplet) GWT
				.create(StopWatchApplet.class);

		buttonStart.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				stopWatchApplet.startWatch();
			}
		});

		buttonStop.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				stopWatchApplet.stopWatch();
			}
		});

		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setSpacing(4);
		buttonPanel.add(buttonStart);
		buttonPanel.add(buttonStop);

		Widget widgetApplet = AppletJSUtil.createAppletWidget(stopWatchApplet);
		AppletJSUtil.registerAppletCallback(stopWatchApplet,
				new StopWatchCallback(panelLaps));

		Label labelTitle = new Label(
				"Register a callback object to notify GWT of changes from within the Applet code.");
		DisclosurePanel panelCode = new DisclosurePanel("View code");
		panelCode.setWidth("100%");
		panelCode.setAnimationEnabled(true);
		panelCode.setContent(createCodeHTML());

		panelMain.add(labelTitle);
		panelMain.add(widgetApplet);
		panelMain.add(buttonPanel);
		panelMain.add(panelLaps);
		panelMain.add(panelCode);

		panelMain.setCellHorizontalAlignment(labelTitle,
				VerticalPanel.ALIGN_CENTER);
		panelMain.setCellHorizontalAlignment(widgetApplet,
				VerticalPanel.ALIGN_CENTER);
		panelMain.setCellHorizontalAlignment(panelLaps,
				VerticalPanel.ALIGN_CENTER);
		panelMain.setCellHorizontalAlignment(buttonPanel,
				VerticalPanel.ALIGN_CENTER);

		initWidget(panelMain);
	}

	/**
	 * Helper-Method to construct an HTML element containing some example code
	 * snippets.
	 */
	private HTML createCodeHTML() {
		String html = "<b>StopWatchAppletTab.java</b>"
				+ "<pre>...\n"
				+ "StopWatchApplet stopWatchApplet = (StopWatchApplet) GWT.create(StopWatchApplet.class);\n"
				+ "Widget widgetApplet = AppletJSUtil.createAppletWidget(stopWatchApplet);\n"
				+ "AppletJSUtil.registerAppletCallback(stopWatchApplet, new StopWatchCallback(panelLaps));\n"
				+ "...</pre>\n"
				+ "<b>StopWatchCallback.java</b>"
				+ "<pre>...\n"
				+ "public class StopWatchCallback implements AppletCallback&lt;String&gt; {\n"
				+ "  private VerticalPanel _panelLaps;\n"
				+ "  private int _lap;\n\n"
				+ "  public StopWatchCallback(VerticalPanel panelLaps) {\n"
				+ "    _panelLaps = panelLaps;\n"
				+ "    _lap = 1;\n"
				+ "  }\n\n"
				+ "  public void callback(String msg) {\n"
				+ "    _panelLaps.add(new HTML(\"&lt;b&gt;Lap \" + _lap + \"&lt;/b&gt; : \" + msg + \" seconds\"));\n"
				+ "    _lap++;\n"
				+ "  }\n"
				+ "}</pre>"
				+ "<b>StopWatchAppletImpl.java</b>"
				+ "<pre>...\n"
				+ "public class StopWatchAppletImpl extends JApplet implements StopWatchApplet {\n"
				+ "  ...\n"
				+ "  public void init() {\n"
				+ "    ...\n"
				+ "    JButton buttonLap = new JButton(\"Lap\");\n"
				+ "    buttonLap.addActionListener(new ActionListener() {\n\n"
				+ "      public void actionPerformed(ActionEvent e) {\n"
				+ "        startWatch();\n"
				+ "        AppletUtil.callback(StopWatchAppletImpl.this, _swLabel.getText());\n"
				+ "      }\n" + "    });\n" + "    ...\n" + "  }\n" + "}</pre>";

		return new HTML(html);
	}

}
