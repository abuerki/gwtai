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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import org.jdesktop.jdic.tray.SystemTray;
import org.jdesktop.jdic.tray.TrayIcon;

import com.google.gwt.gwtai.applet.util.JavaLibraryPath;
import com.google.gwt.gwtai.trayicon.client.TrayIconApplet;


/**
 * A demo applet that uses the system tray. This demo does work on Windows, Linux and Solaris (No
 * Mac yet - sorry).
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class TrayIconAppletImpl extends JApplet implements TrayIconApplet {

	private static final long serialVersionUID = -8907821050278144296L;

	private SystemTray _tray;
	private JPopupMenu _menu;
	private TrayIcon _trayIcon;
	private JCheckBox _cbTrayIcon;

	public TrayIconAppletImpl() {
		try {
			JavaLibraryPath.injectTrayNativLib();
		} catch (Exception e) {
			throw new RuntimeException("Can not add tray native library to the java.library.path",
					e);
		}
	}

	public void showTrayIcon() {
		_tray.addTrayIcon(_trayIcon);
		
		if (!_cbTrayIcon.isSelected()) {
			_cbTrayIcon.setSelected(true);
		}
	}

	public void hideTrayIcon() {
		_tray.removeTrayIcon(_trayIcon);
		
		if (_cbTrayIcon.isSelected()) {
			_cbTrayIcon.setSelected(false);
		}
	}
	
    public void addSeparator() {
    	_menu.addSeparator();
    }
    
    public void addTextItem(String caption) {
    	_menu.add(new JMenuItem(caption));
    }
    
    public void addRadioButtonItem(String caption) {
    	_menu.add(new JRadioButtonMenuItem(caption));
    }
    
    public void addCheckBoxItem(String caption) {
    	_menu.add(new JCheckBoxMenuItem(caption));
    }

	public void stop() {
		hideTrayIcon();
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

		_tray = SystemTray.getDefaultSystemTray();

		ImageIcon i = new ImageIcon(TrayIconAppletImpl.class.getResource("icon.gif"));

		_menu = new JPopupMenu("GwtAI Menu");
		_trayIcon = new TrayIcon(i, "GwtAI TrayIcon Demo", _menu);

		_trayIcon.setIconAutoSize(true);
		_trayIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "This feature is brought to you by GwtAI.\n"
						+ " - http://code.google.com/gwtai\n\n"
						+ "With the friendly assistance of the JDIC project.\n"
						+ " - http://jdic.dev.java.net/", "GwtAI TrayIcon Demo",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		panelMain.setBorder(BorderFactory.createTitledBorder("TrayIconApplet"));
		panelMain.setBackground(Color.WHITE);
		
		getContentPane().add(panelMain);
	}

}