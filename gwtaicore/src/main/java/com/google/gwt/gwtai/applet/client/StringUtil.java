/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.client;

/**
 *
 * @author krog
 */
public class StringUtil {

    /**
	 * Helper-method to emulate the <code>new String(byte[])</code> constructor
	 * missing from the JRE emulation library.
	 */
	public static String convertByteArrayToString(byte[] bytes) {
		StringBuilder builder = new StringBuilder();

		boolean twoByte = false;

		for (byte b: bytes) {

			if (twoByte) {
				twoByte = false;

				builder.append((char)(b + 320));
			} else if (b != -61) {
				builder.append((char) b);
			} else {
				twoByte = true;
			}
		}

		return builder.toString();
	}

	/**
	 * Helper-method to emulate the <code>getByte()</code> method
	 * missing from the JRE emulation library.
	 */
	public static byte[] convertStringToByteArray(String input) {
		char[] chars = input.toCharArray();
		byte[] bytes1 = new byte[chars.length * 2];

		int i = 0;

		for (char c: chars) {
			if (c < 128) {
				bytes1[i] = (byte) c;
				i++;
			} else {
				bytes1[i] = (byte) -61;
				i++;

				bytes1[i] = (byte) ((int) c - 320);
				i++;
			}
		}

		byte[] bytes2 = new byte[i];

		for (int j = 0; j < i; j++) {
			bytes2[j] = bytes1[j];
		}

		return bytes2;
	}
}
