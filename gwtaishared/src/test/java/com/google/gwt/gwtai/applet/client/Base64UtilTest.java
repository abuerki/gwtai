package com.google.gwt.gwtai.applet.client;

import org.apache.catalina.util.Base64;

import junit.framework.TestCase;

/**
 * Test class for <code>com.google.gwt.gwtai.applet.client.Base64Util</code>.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class Base64UtilTest extends TestCase {
	public static String TEST_INPUT = "abcdefg äöü éàè";
	public static String TEST_OUTPUT ="YWJjZGVmZyDDpMO2w7wgw6nDoMOo";
	
	public void testEncode() {
		String gwtaiEncodedString = Base64Util.encodeString(TEST_INPUT);
		String catalinaEncodedString = new String(Base64.encode(TEST_INPUT.getBytes()));
		
		assertEquals(catalinaEncodedString, gwtaiEncodedString);		
	}
	
	public void testDecode() {
		String gwtaiDecodedString = Base64Util.decodeString(TEST_OUTPUT);
		String catalinaDecodedString = new String(Base64.decode(TEST_OUTPUT.getBytes()));
		
		assertEquals(catalinaDecodedString, gwtaiDecodedString);
	}
	
	public void testByteArrayToString() {
		byte[] bytes = Base64Util.convertStringToByteArray(TEST_INPUT);
		String output = Base64Util.convertByteArrayToString(bytes);
		
		assertEquals(new String(bytes), output);
	}
	
	public void testStringToByteArray() {
		byte[] bytes = Base64Util.convertStringToByteArray(TEST_INPUT);
		
		assertEquals(TEST_INPUT.getBytes().length, bytes.length);
		
		for (int i = 0; i < bytes.length; i++) {
			assertEquals(TEST_INPUT.getBytes()[i], bytes[i]);
		}
	}

}
