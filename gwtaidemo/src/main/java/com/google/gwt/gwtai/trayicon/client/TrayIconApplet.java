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

package com.google.gwt.gwtai.trayicon.client;

import com.google.gwt.gwtai.applet.client.Applet;
import com.google.gwt.gwtai.applet.client.Height;
import com.google.gwt.gwtai.applet.client.ImplementingClass;
import com.google.gwt.gwtai.applet.client.Width;

/**
 * The applet methods that shall be available from GWT are defined in this
 * interface. There are also a couple of annotations used to configure how the
 * applet is embedded within the page (the applet's dimension and other stuff).
 * 
 * Note; don't call the add methods to soon. Make sure the applet is
 * initialized first.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
@ImplementingClass(com.google.gwt.gwtai.trayicon.impl.TrayIconAppletImpl.class)
@Height("70")
@Width("200")
public interface TrayIconApplet extends Applet {

    public void showTrayIcon();
    
    public void hideTrayIcon();

    public void addSeparator();
    
    public void addTextItem(String caption);
    
    public void addCheckBoxItem(String caption);
    
}