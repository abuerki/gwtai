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

package com.google.gwt.gwtai.demo.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;

import com.sun.javafx.runtime.adapter.Applet;

import com.google.gwt.gwtai.demo.client.JavaFXApplet;

/**
 * A wrapper to start a JavaFX applet. This class makes sure that a Java 1.6 is
 * present in order to have things like dragging applet out of the browser work properly.
 * 
 * The idea for this is coming from the <code>CompatibilityApplet</code> class presented
 * in the 'Java SE 6 Update N Early Access' documentation:
 * https://jdk6.dev.java.net/plugin2/jnlp/CompatibilityApplet.java
 *
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class JavaFXAppletStarter extends Applet implements JavaFXApplet {
	private static final long serialVersionUID = -4814134732593547664L;

	/**
     * Overriding the <code>init()</code> method to make sure the Java version is 1.6 or above.
     */
	@Override
    public void init() {
    	String javaVersion = System.getProperty("java.version");
    	float version;
    	
    	try {
    		version = Float.parseFloat(javaVersion.substring(0, 3));
    	} catch (NumberFormatException nfe) {
    		version = 0;
    	}
    	
    	if (version < 1.6) {
    		JButton button = new JButton("Click here to get the new Java Plug-In");
    		button.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					try {
						getAppletContext().showDocument(new URL("http://jdk6.dev.java.net/6uNea.html"), "_blank");
					} catch (Exception ex) {
					}
				}
    			
    		});
    		
    		add(button);
    	} else {
    		super.init();
    	}
    }

}
