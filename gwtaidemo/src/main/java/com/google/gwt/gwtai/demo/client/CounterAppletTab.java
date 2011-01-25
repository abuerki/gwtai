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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.gwtai.applet.client.AppletJSUtil;

/**
 * The content of the <code>CounterApplet</code> demo tab.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class CounterAppletTab extends Composite {

	public CounterAppletTab() {
		VerticalPanel panelMain = new VerticalPanel();
		panelMain.setWidth("100%");
		panelMain.setSpacing(4);
		
		Button buttonInc = new Button("Increment");
		Button buttonSet = new Button("Set to 10");
		Button buttonDec = new Button("Decrement");
		Button buttonGet = new Button("Get current count");
		
		final CounterApplet counterApplet = (CounterApplet) GWT.create(CounterApplet.class);
		
		buttonInc.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				counterApplet.increment();
			}
		});

		buttonDec.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				counterApplet.decrement();
			}
		});

                buttonSet.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				counterApplet.setValue(10);
			}
		});

		buttonGet.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Object value = counterApplet.getCurrentValue();
				
				DialogBox dialogBox = createDialogBox(value);
				dialogBox.center();
				dialogBox.show();
			}
		});
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setSpacing(4);
		buttonPanel.add(buttonInc);
		buttonPanel.add(buttonDec);
		buttonPanel.add(buttonSet);
		buttonPanel.add(buttonGet);
		
		Widget widgetApplet = AppletJSUtil.createAppletWidget(counterApplet);
		Label labelTitle = new Label("To call a method on an applet object from within your GWT code - a piece of cake!");
		DisclosurePanel panelCode = new DisclosurePanel("View code");
		panelCode.setWidth("100%");
		panelCode.setAnimationEnabled(true);
		panelCode.setContent(createCodeHTML());
		
		panelMain.add(labelTitle);
		panelMain.add(widgetApplet);
		panelMain.add(buttonPanel);
		panelMain.add(panelCode);
		
		panelMain.setCellHorizontalAlignment(labelTitle, VerticalPanel.ALIGN_CENTER);
		panelMain.setCellHorizontalAlignment(widgetApplet, VerticalPanel.ALIGN_CENTER);
		panelMain.setCellHorizontalAlignment(buttonPanel, VerticalPanel.ALIGN_CENTER);

		initWidget(panelMain);
	}
	
	private DialogBox createDialogBox(Object currentValue) {
	    final DialogBox dialogBox = new DialogBox();
	    dialogBox.setText("Current...");

	    VerticalPanel panelContent = new VerticalPanel();
	    panelContent.setWidth("100%");
	    panelContent.setSpacing(4);
	    dialogBox.setWidget(panelContent);

	    HTML labelCurrentValue = new HTML("The current count is : " +currentValue);
	    panelContent.add(labelCurrentValue);
	    panelContent.setCellHorizontalAlignment(labelCurrentValue, VerticalPanel.ALIGN_CENTER);

	    Button buttonCloseDlg = new Button("Close");
	    buttonCloseDlg.addClickHandler(new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		dialogBox.hide();
	    	}
	    });
	    
	    panelContent.add(buttonCloseDlg);
	    panelContent.setCellHorizontalAlignment(buttonCloseDlg, VerticalPanel.ALIGN_RIGHT);

	    return dialogBox;
	}

	/**
	 * Helper-Method to construct an HTML element containing some example code snippets.
	 */
	private HTML createCodeHTML() {
		String html =
			"<b>CounterAppletTab.java</b>" +
			"<pre>...\n" +
			"VerticalPanel panelMain = new VerticalPanel();\n" +
			"Button buttonInc = new Button(\"Increment\");\n" +
			"final CounterApplet counterApplet = (CounterApplet) GWT.create(CounterApplet.class);\n\n" +
			"buttonInc.addClickListener(new ClickListener() {\n" +
			"	public void onClick(Widget sender) {\n" +
			"		counterApplet.increment();\n" +
			"	}\n" +
			"});\n\n" +
			"Widget widgetApplet = AppletJSUtil.createAppletWidget(counterApplet);\n" +
			"panelMain.add(widgetApplet);\n\n" +
			"initWidget(panelMain);\n" +
			"...</pre>";
		
		return new HTML(html);
	}

}
