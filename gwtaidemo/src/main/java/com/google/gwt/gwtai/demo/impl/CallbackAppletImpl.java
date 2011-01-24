package com.google.gwt.gwtai.demo.impl;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JTextArea;

import com.google.gwt.gwtai.applet.util.AppletUtil;
import com.google.gwt.gwtai.demo.client.CallbackApplet;

public class CallbackAppletImpl extends JApplet implements CallbackApplet {
	private static final long serialVersionUID = 395928305058840092L;
	
	@Override
	public void init() {
		JButton button = new JButton("Send msg");
	    final JTextArea msgTextArea = new JTextArea("Callback test");
	    
	    button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String msg = msgTextArea.getText();
				
				AppletUtil.callback(CallbackAppletImpl.this, msg); 
			}
	    	
	    });
	    
	    setLayout(new BorderLayout());
	    getContentPane().add(msgTextArea, BorderLayout.CENTER);
	    getContentPane().add(button, BorderLayout.SOUTH);
	}

}
