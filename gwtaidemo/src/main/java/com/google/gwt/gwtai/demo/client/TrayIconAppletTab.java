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
import com.google.gwt.gwtai.applet.client.AppletDefTarget;
import com.google.gwt.gwtai.applet.client.AppletJSUtil;
import com.google.gwt.gwtai.trayicon.client.TrayIconApplet;
import com.google.gwt.gwtai.trayicon.client.TrayIconAppletAsync;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The content of the <code>TrayIconApplet</code> demo tab.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class TrayIconAppletTab extends Composite {
	private TrayIconAppletAsync _trayIconApplet;

         private abstract class ErrorHandlingCallback<T> implements AsyncCallback<T> {

        public void onFailure(Throwable caught) {
            Window.alert(caught.getMessage());
        }

    }

    private class DevNullCallback extends ErrorHandlingCallback<Void> {

        public void onSuccess(Void result) { }
    }

	public TrayIconAppletTab() {
		VerticalPanel panelMain = new VerticalPanel();
		panelMain.setWidth("100%");
		panelMain.setSpacing(4);

		_trayIconApplet = GWT.create(TrayIconApplet.class);
                AppletDefTarget defTarget = (AppletDefTarget)_trayIconApplet;
		Widget widgetApplet = AppletJSUtil.createAppletWidget(defTarget);

		Label labelTitle = new Label(
				"Hook into the desktop tray from a GWT application. This is a 'Proof of Concept', the feature is not finished yet.");
		DisclosurePanel panelCode = new DisclosurePanel("View code");
		panelCode.setWidth("100%");
		panelCode.setAnimationEnabled(true);
		panelCode.setContent(createCodeHTML());

		HorizontalPanel panelItems = new HorizontalPanel();
		panelItems.setSpacing(4);

		final TextBox boxCaption = new TextBox();

		final ListBox boxItemType = new ListBox();
		boxItemType.addItem("Text");
		boxItemType.addItem("CheckBox");
		boxItemType.setSelectedIndex(0);

		Button buttonAdd = new Button("Add menu item");

		buttonAdd.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String caption = boxCaption.getText();

				if (null == caption || caption.length() < 1) {
					Window.alert("Caption can not be empty");
				} else {
					String itemType = boxItemType.getItemText(boxItemType
							.getSelectedIndex());

					if (itemType.equals("CheckBox")) {
						_trayIconApplet.addCheckBoxItem(caption, new DevNullCallback());
					} else {
						_trayIconApplet.addTextItem(caption, new DevNullCallback());
					}
				}
			}
		});

		Button buttonSeparator = new Button("Add separator");
		buttonSeparator.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				_trayIconApplet.addSeparator(new DevNullCallback());
			}
		});

		panelItems.add(boxCaption);
		panelItems.add(boxItemType);
		panelItems.add(buttonAdd);
		panelItems.add(buttonSeparator);

		panelMain.add(labelTitle);
		panelMain.add(widgetApplet);
		panelMain.add(panelItems);
		panelMain.add(panelCode);

		panelMain.setCellHorizontalAlignment(labelTitle,
				VerticalPanel.ALIGN_CENTER);
		panelMain.setCellHorizontalAlignment(widgetApplet,
				VerticalPanel.ALIGN_CENTER);

		initWidget(panelMain);
	}

	/**
	 * Helper-Method to construct an HTML element containing some example code
	 * snippets.
	 */
	private HTML createCodeHTML() {
		String html = "<b>TrayIconAppletTab.java</b>"
				+ "<pre>...\n"
				+ "TrayIconApplet trayIconApplet = (TrayIconApplet) GWT.create(TrayIconApplet.class);\n"
				+ "Widget widgetApplet = AppletJSUtil.createAppletWidget(trayIconApplet);\n"
				+ "...\n" + "panelMain.add(widgetApplet);\n"
				+ "initWidget(panelMain);\n" + "...</pre>";

		return new HTML(html);
	}

}
