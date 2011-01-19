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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.google.gwt.gwtai.applet.util.AppletUtil;
import com.google.gwt.gwtai.demo.client.StopWatchApplet;

/**
 * A demo applet to show how callback works.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class StopWatchAppletImpl extends JApplet implements StopWatchApplet {
	private static final long serialVersionUID = -6910666758506270768L;

	private StopWatchLabel _swLabel;

	public void init() {
		JPanel panelMain = new JPanel(new BorderLayout());

		JButton buttonLap = new JButton("Lap");
		buttonLap.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				startWatch();
				AppletUtil.callback(StopWatchAppletImpl.this, _swLabel
						.getText());
			}

		});

		_swLabel = new StopWatchLabel();

		panelMain.add(_swLabel, BorderLayout.CENTER);
		panelMain.add(buttonLap, BorderLayout.SOUTH);

		panelMain
				.setBorder(BorderFactory.createTitledBorder("StopWatchApplet"));
		panelMain.setBackground(Color.WHITE);

		getContentPane().add(panelMain);
	}

	public void stopWatch() {
		_swLabel.stopWatch();
	}

	public void startWatch() {
		_swLabel.startWatch();
	}

	class StopWatchLabel extends JLabel implements ActionListener {
		private static final long serialVersionUID = 564090526934223258L;

		private long _startTime;

		private boolean _running;

		private Timer _timer;

		public StopWatchLabel() {
			super("Click start...", JLabel.CENTER);

			setFont(new Font("SansSerif", Font.BOLD, 24));
		}

		public void actionPerformed(ActionEvent evt) {
			setText(getCurrentTime());
		}

		public void startWatch() {
			if (!_running) {
				_running = true;

				_startTime = System.currentTimeMillis();
				setText("0.0");

				if (_timer == null) {
					_timer = new Timer(100, this);
					_timer.start();
				} else {
					_timer.restart();
				}
			}
		}

		public String getCurrentTime() {
			return (System.currentTimeMillis() - _startTime) / 1000.0 + "";
		}

		public void stopWatch() {
			if (_running) {
				_timer.stop();

				setText(getCurrentTime());

				_running = false;
			}
		}
	}

}
