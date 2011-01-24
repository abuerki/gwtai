package com.google.gwt.gwtai.applet.client;

import com.google.gwt.gwtai.applet.client.Base64Util;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWTTest class for <code>com.google.gwt.gwtai.applet.client.Base64Util</code>.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class Base64UtilGWTTest extends GWTTestCase {
	public static String TEST_INPUT = "abcdefg äöü éàè";
	public static String TEST_OUTPUT ="YWJjZGVmZyDDpMO2w7wgw6nDoMOo";

	@Override
	public String getModuleName() {
		return "com.google.gwt.gwtai.applet.AppletIntegration";
	}
	
	public void testEncode() {
		String gwtaiEncodedString = Base64Util.encodeString(TEST_INPUT);
		
		assertEquals(TEST_OUTPUT, gwtaiEncodedString);
	}
	
	public void testDecode() {
		String gwtaiDecodedString = Base64Util.decodeString(TEST_OUTPUT);
		
		assertEquals(TEST_INPUT, gwtaiDecodedString);
	}

}
