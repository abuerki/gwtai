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
import com.google.gwt.gwtai.applet.client.AppletJSUtil;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This project demonstrates how to integrate a JavaFX Applet into a
 * GWT application using the GwtAi project (http://code.google.com/p/gwtai/).
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class JavaFXDemo implements EntryPoint {

	public void onModuleLoad() {
		DecoratedTabPanel tabPanel = new DecoratedTabPanel();
		tabPanel.setWidth("800px");

		tabPanel.getDeckPanel().setAnimationEnabled(true);

		VerticalPanel panelMain = new VerticalPanel();
		panelMain.setWidth("100%");
		panelMain.setSpacing(4);
		
		JavaFXApplet javaFxApplet = (JavaFXApplet) GWT.create(JavaFXApplet.class);
		
		Widget widgetApplet = AppletJSUtil.createAppletWidget(javaFxApplet);

		panelMain.add(new HTML("This demo is featuring a "
				+ "<a href=\"http://www.sun.com/software/javafx/\">JavaFX</a> applet "
				+ "integrated into a <a href=\"http://code.google.com/webtoolkit/\">Goolge Web"
				+ " Toolkit</a> application using the "
				+ "<a href=\"http://code.google.com/p/gwtai/\">gwtai project</a>."));
		panelMain.add(widgetApplet);
		panelMain.setCellHorizontalAlignment(widgetApplet, VerticalPanel.ALIGN_CENTER);
		
		DisclosurePanel panelCode = new DisclosurePanel("View code");
		panelCode.setWidth("100%");
		panelCode.setAnimationEnabled(true);
		panelCode.setContent(createCodeHTML());
		
		panelMain.add(panelCode);
		
		tabPanel.add(panelMain, "JavaFXDemo");

		tabPanel.selectTab(0);

		RootPanel.get().add(tabPanel);
	}
	
	/**
	 * Helper-Method to construct an HTML element containing some example code snippets.
	 */
	private HTML createCodeHTML() {
		String html =
			"<b>CounterAppletTab.java</b>" +
			"<pre>...\n" +
			"VerticalPanel panelMain = new VerticalPanel();\n\n" +
			"JavaFXApplet javaFxApplet = (JavaFXApplet) GWT.create(JavaFXApplet.class);\n\n" +
			"Widget widgetApplet = AppletJSUtil.createAppletWidget(javaFxApplet);\n" +
			"panelMain.add(widgetApplet);\n" +
			"...</pre>" +
			"<hr>" +
			"<b>JavaFXApplet.java</b>" +
			"<pre>\n" +
			"@ImplementingClass(com.google.gwt.gwtai.demo.impl.JavaFXAppletStarter.class)\n" +
			"@Height(\"230px\")\n" +
			"@Width(\"320px\")\n" +
			"@Archive(\"GwtAI-Core.jar, JavaFXDemo.jar, javafxc.jar, javafxgui.jar, javafx-swing.jar, Scenario.jar\")\n" +
			"@Params(names={\"applicationclass\", \"draggable\"}, values={\"com.google.gwt.gwtai.demo.impl.JavaFXAppletImpl\", \"true\"})\n" +
			"public interface JavaFXApplet extends Applet { };\n" +
			"</pre>" +
			"<hr>" +
			"<b>JavaFXAppletStarter.java</b>" +
			"<pre>\n" +
			"public class JavaFXAppletStarter extends Applet implements JavaFXApplet {\n\n" +
			"  public void init() {\n" +
			"    // Omitted code to check the Java version\n" +
			"    ...\n" +
			"    super.init();\n" +
			"  }\n" +
			"}" +
			"</pre>" +
			"<hr>" +
			"<b>JavaFXAppletImpl.fx</b>" +
			"<pre>\n" +
			"var o1:Number;\n\n" +
			"var t = Timeline {\n" +
		    "  repeatCount: Timeline.INDEFINITE\n" +
		    "  autoReverse: true\n" +
		    "  keyFrames: [\n" +
		    "    KeyFrame{\n" +
		    "      time  : 0s\n" +
		    "      values: o1 => 0.3},\n" +
		    "    KeyFrame{\n" +
		    "      time  : 3.5s\n" +
		    "      values: o1 => 1.0 tween Interpolator.EASEBOTH}\n" +
		    "    ]\n" +
		    "}\n\n" +
		    "t.start();\n\n" +
		    "Application {\n" +
		    "  stage: Stage {\n" +
		    "  fill: Color.BLACK\n" +
		    "  content:[\n" +
            "    Group {\n" +
		    "      transform: Transform.translate(0,5);\n" +
            "      content:[\n" +
            "        Rectangle {\n" +
            "          x: 20.0\n" +
            "          y: 20.0\n" +
            "          fill: Color.WHITESMOKE\n" +
            "          width: 280\n" +
            "          height: 80\n" +
            "          arcHeight: 15\n" +
            "          arcWidth: 15\n" +
            "          stroke: Color.ORANGE strokeWidth: 3\n" +
            "        },\n" +
            "        Text {\n" +
            "          verticalAlignment: VerticalAlignment.BOTTOM\n" +
            "          content: 'JavaFX rocks!'\n" +
            "          font: Font {name: 'Verdana', style: FontStyle.BOLD, size: 30}\n" +
            "          fill: Color.ORANGE\n" +
            "          x: 45.0\n" +
            "          y: 110.0\n" +
            "        }\n" +
            "      ]\n" +
		    "      opacity: bind o1\n" +
            "      effect:\n" +
            "        Reflection {\n" +
            "          fraction: 0.50\n" +
            "          topOpacity: 0.8\n" +
            "        }\n" +
            "      },\n" +
            "      Text {\n" +
            "        verticalAlignment: VerticalAlignment.BOTTOM\n" +
            "        content: 'GWT and JavaFX => http://code.google.com/p/gwtai/'\n" +
            "        font: Font {name: 'Verdana', style: FontStyle.ITALIC, size: 10}\n" +
            "        fill: Color.WHITESMOKE\n" +
            "        x: 20.0\n" +
            "        y: 210.0\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "};" +
		    "</pre>";
			
		return new HTML(html);
	}

}
