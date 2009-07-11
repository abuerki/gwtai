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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import org.jdesktop.jdic.tray.SystemTray;
import org.jdesktop.jdic.tray.TrayIcon;

import com.google.gwt.gwtai.applet.util.JavaLibraryPath;
import com.google.gwt.gwtai.applet.util.LibraryElement;
import com.google.gwt.gwtai.applet.util.LibraryElement.ARCH;
import com.google.gwt.gwtai.applet.util.LibraryElement.OS;
import com.google.gwt.gwtai.trayicon.client.TrayIconApplet;

/**
 * Accesses the system tray using JDIC for machines running a JRE < 1.6.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class TrayAccessJDIC extends JApplet implements TrayIconApplet {
	private static final long serialVersionUID = -8907821050278144296L;

	private SystemTray _tray;
	private JPopupMenu _menu;
	private TrayIcon _trayIcon;

	public TrayAccessJDIC() {
		try {
			injectTrayNativLib();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(
					"Can not add tray native library to the java.library.path", e);
		}
		
		_tray = SystemTray.getDefaultSystemTray();

		ImageIcon i = new ImageIcon(TrayIconAppletImpl.class.getResource("icon.gif"));

		_menu = new JPopupMenu("GwtAI Menu");
		_trayIcon = new TrayIcon(i, "GwtAI TrayIcon Demo", _menu);

		_trayIcon.setIconAutoSize(true);
		_trayIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								null,
								"This feature is brought to you by GwtAI.\n"
										+ " - http://code.google.com/gwtai\n\n"
										+ "With the friendly assistance of the JDIC project.\n"
										+ " - http://jdic.dev.java.net/",
								"GwtAI TrayIcon Demo",
								JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	public void showTrayIcon() {
		_tray.addTrayIcon(_trayIcon);
	}

	public void hideTrayIcon() {
		_tray.removeTrayIcon(_trayIcon);
	}

	public void addSeparator() {
		_menu.addSeparator();
	}

	public void addTextItem(String caption) {
		_menu.add(new JMenuItem(caption));
	}

	public void addCheckBoxItem(String caption) {
		_menu.add(new JCheckBoxMenuItem(caption));
	}

	/**
	 * Extract the tray.dll or libtray.so (depending on the Operating System) 
	 * into a temporary location and add this very location to the
	 * <i>java.library.path</i> path.
	 * 
	 * @throws Exception
	 *             In case of an error.
	 */
	public void injectTrayNativLib() throws Exception {
		/**
		 * Windows
		 */
		LibraryElement winLib1 = new LibraryElement("win", "jdic.dll",
				OS.Windows);
		LibraryElement winLib2 = new LibraryElement("win", "tray.dll",
				OS.Windows);
		LibraryElement winStub = new LibraryElement("win", "jdic_stub.jar",
				OS.Windows);
		JavaLibraryPath.injectNativLib(winLib1, winLib2, winStub);	

		/**
		 * Linux
		 */
		LibraryElement linuxLibi3861 = new LibraryElement("linux/x86", "libjdic.so",
				OS.Linux, ARCH.i386);
		LibraryElement linuxLibi3862 = new LibraryElement("linux/x86", "libtray.so",
				OS.Linux, ARCH.i386);
		LibraryElement linuxLibx861 = new LibraryElement("linux/x86", "libjdic.so",
				OS.Linux, ARCH.x86);
		LibraryElement linuxLibx862 = new LibraryElement("linux/x86", "libtray.so",
				OS.Linux, ARCH.x86);
		LibraryElement linuxLibamd641 = new LibraryElement("linux/amd64", "libjdic.so",
				OS.Linux, ARCH.amd64);
		LibraryElement linuxLibamd642 = new LibraryElement("linux/amd64", "libtray.so",
				OS.Linux, ARCH.amd64);
		LibraryElement linuxStub = new LibraryElement("linux", "jdic_stub.jar",
				OS.Linux);
		JavaLibraryPath.injectNativLib(linuxLibi3861, linuxLibi3862, linuxLibx861,
				linuxLibx862, linuxLibamd641, linuxLibamd642, linuxStub);
		
		/**
		 * Mac
		 */
		LibraryElement libMac1 = new LibraryElement("mac", "libtray.jnilib",
				OS.Mac);
		LibraryElement libMac2 = new LibraryElement("mac", "libjdic.jnilib",
				OS.Mac);
		JavaLibraryPath.injectNativLib(libMac1, libMac2);
		
		/**
		 * Sun Solaris
		 */
		LibraryElement libSunX861 = new LibraryElement("solaris/x86", "libtray.so",
				OS.SunOS, ARCH.x86);
		LibraryElement libSunX862 = new LibraryElement("solaris/x86", "libjdic.so",
				OS.SunOS, ARCH.x86);
		LibraryElement libSunSparc1 = new LibraryElement("solaris/sparc", "libtray.so",
				OS.SunOS, ARCH.sparc);
		LibraryElement libSunSparc2 = new LibraryElement("solaris/sparc", "libjdic.so",
				OS.SunOS, ARCH.sparc);
		LibraryElement sunStub = new LibraryElement("solaris", "jdic_stub.jar",
				OS.SunOS);
		JavaLibraryPath.injectNativLib(libSunX861, libSunX862,
				libSunSparc1, libSunSparc2, sunStub);
	}

}