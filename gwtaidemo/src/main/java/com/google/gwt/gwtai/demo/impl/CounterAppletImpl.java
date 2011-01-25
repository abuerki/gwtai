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

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.gwt.gwtai.demo.client.CounterApplet;

/**
 * A demo applet to show how calling methods from GWT works.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class CounterAppletImpl extends JApplet implements CounterApplet {
	private static final long serialVersionUID = 9090323562863086730L;

	private JTextField _tfCounter;

	/**
	 * Performs the initialization.
	 */
	public void init() {
		JPanel panelMain = new JPanel();

		_tfCounter = new JTextField(20);
		_tfCounter.setHorizontalAlignment(JTextField.CENTER);
		_tfCounter.setText("0");
		_tfCounter.setEditable(false);

		panelMain.add(new JLabel("Current count : "));
		panelMain.add(_tfCounter);

		panelMain.setBorder(BorderFactory.createTitledBorder("CounterApplet"));
		panelMain.setBackground(Color.WHITE);

		getContentPane().add(panelMain);
	}

	public void increment() {
		int currentCount = Integer.parseInt(_tfCounter.getText());
		currentCount++;

		_tfCounter.setText(currentCount + "");
	}

	public void decrement() {
		int currentCount = Integer.parseInt(_tfCounter.getText());
		currentCount--;

		_tfCounter.setText(currentCount + "");
	}

	public Object getCurrentValue() {
		return _tfCounter.getText();
	}

        public void setValue(Integer value) {
            _tfCounter.setText(value + "");
        }

}