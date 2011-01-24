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

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JOptionPane;

import com.google.gwt.gwtai.trayicon.client.TrayIconApplet;

/**
 * Accesses the system tray using JRE classes present in Java >= 1.6.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class TrayAccess16 extends JApplet implements TrayIconApplet {
	private static final long serialVersionUID = -8907821050278144296L;

	private SystemTray _tray;
	private PopupMenu _menu;
	private TrayIcon _trayIcon;

	public TrayAccess16() {
		_tray = SystemTray.getSystemTray();

		ImageIcon i = new ImageIcon(TrayIconAppletImpl.class.getResource("icon.gif"));

		_menu = new PopupMenu("GwtAI Menu");
		_trayIcon = new TrayIcon(i.getImage(), "GwtAI TrayIcon Demo", _menu);

		_trayIcon.setImageAutoSize(true);
		_trayIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								null,
								"This feature is brought to you by GwtAI.\n"
										+ " - http://code.google.com/gwtai",
								"GwtAI TrayIcon Demo",
								JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	public void showTrayIcon() {
		try {
			_tray.add(_trayIcon);
		} catch (AWTException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void hideTrayIcon() {
		_tray.remove(_trayIcon);
	}

	public void addSeparator() {
		_menu.addSeparator();
	}

	public void addTextItem(String caption) {
		_menu.add(new MenuItem(caption));
	}

	public void addCheckBoxItem(String caption) {
		_menu.add(new CheckboxMenuItem(caption));
	}

}