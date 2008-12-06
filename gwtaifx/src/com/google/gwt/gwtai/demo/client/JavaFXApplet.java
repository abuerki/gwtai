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

import com.google.gwt.gwtai.applet.client.Applet;
import com.google.gwt.gwtai.applet.client.Archive;
import com.google.gwt.gwtai.applet.client.Height;
import com.google.gwt.gwtai.applet.client.ImplementingClass;
import com.google.gwt.gwtai.applet.client.Params;
import com.google.gwt.gwtai.applet.client.Width;

/**
 * This project demonstrates how to integrate a JavaFX Applet into a
 * GWT application using the GwtAi project (http://code.google.com/p/gwtai/).
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
@ImplementingClass(com.google.gwt.gwtai.demo.impl.JavaFXAppletStarter.class)
@Height("230px")
@Width("320px")
@Archive("GwtAI-Client.jar, JavaFXDemo.jar, javafxrt.jar, javafxgui.jar, javafx-swing.jar, Scenario.jar, eula.jar")
@Params(names={"MainJavaFXScript", "draggable"}, values={"com.google.gwt.gwtai.demo.impl.JavaFXAppletImpl", "true"})
public interface JavaFXApplet extends Applet {

}
