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

package com.google.gwt.gwtai.trayicon.impl;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.gwt.gwtai.trayicon.client.TrayIconApplet;

/**
 * A demo applet that uses the system tray. This demo does work on Windows,
 * Linux and Solaris (No Mac yet - sorry).
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class TrayIconAppletImpl extends JApplet implements TrayIconApplet {
	private static final long serialVersionUID = -8907821050278144296L;

	private TrayIconApplet _trayAccess;
	private JCheckBox _cbTrayIcon;

	public TrayIconAppletImpl() {
		String accessClassName;
		
		try {
			Class<?> systemTrayClass = Class.forName("java.awt.SystemTray");
			
			try {
				Method isSupportedMethod = systemTrayClass.getDeclaredMethod("isSupported", new Class<?>[0]);
				Boolean supported = (Boolean) isSupportedMethod.invoke(null, new Object[0]);
				
				if (supported) {
					accessClassName = "com.google.gwt.gwtai.trayicon.impl.TrayAccess16";
				} else {
					accessClassName = "com.google.gwt.gwtai.trayicon.impl.TrayAccessJDIC";
				}
			} catch (Exception uop) {
				// The system tray is available, but no yet supported on this platform, use JDIC
				accessClassName = "com.google.gwt.gwtai.trayicon.impl.TrayAccessJDIC";
			}
		} catch (ClassNotFoundException cnfe) {
			// No system tray available from JRE, use JDIC 
			accessClassName = "com.google.gwt.gwtai.trayicon.impl.TrayAccessJDIC";
		}
		
		try {
			_trayAccess = (TrayIconApplet) Class.forName(accessClassName).newInstance();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void showTrayIcon() {
		_trayAccess.showTrayIcon();

		if (!_cbTrayIcon.isSelected()) {
			_cbTrayIcon.setSelected(true);
		}
	}

	public void hideTrayIcon() {
		_trayAccess.hideTrayIcon();

		if (_cbTrayIcon.isSelected()) {
			_cbTrayIcon.setSelected(false);
		}
	}

	public void addSeparator() {
		_trayAccess.addSeparator();
	}

	public void addTextItem(String caption) {
		_trayAccess.addTextItem(caption);
	}

	public void addCheckBoxItem(String caption) {
		_trayAccess.addCheckBoxItem(caption);
	}

	public void stop() {
		try {
			hideTrayIcon();
		} catch (Exception e) {
			// Too bad, nothing we can do about
		}
	}

	/**
	 * Performs the initialization.
	 */
	public void init() {
		JPanel panelMain = new JPanel();

		_cbTrayIcon = new JCheckBox("Show tray icon");
		_cbTrayIcon.setBackground(Color.WHITE);

		_cbTrayIcon.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (_cbTrayIcon.isSelected()) {
					showTrayIcon();
				} else {
					hideTrayIcon();
				}
			}

		});

		panelMain.add(_cbTrayIcon);

		panelMain.setBorder(BorderFactory.createTitledBorder("TrayIconApplet"));
		panelMain.setBackground(Color.WHITE);

		getContentPane().add(panelMain);
	}

}