package com.google.gwt.gwtai.applet.client;

import com.google.gwt.user.server.Base64Utils;
import java.io.UnsupportedEncodingException;
import org.apache.catalina.util.Base64;

import junit.framework.TestCase;

/**
 * Test class for <code>com.google.gwt.gwtai.applet.client.Base64Util</code>.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class Base64UtilTest extends TestCase {
	public static String TEST_INPUT = "abcdefg \u00d8\u00c8 \u00f8\u00c5";
	public static String TEST_OUTPUT ="YWJjZGVmZyDDmMOIIMO4w4U=";
        public static String TEST_LONG_STRING = "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp "+
                "asdlqdløpe3l2lø3p223lø l23l4 234øp l234ølr æræ lf24æl dfæ34l 2øædlÆL23 Ædl 23rl23ælr23ÆLrqwL   asdlqdløpe3l2lø3p223lø l23l4 234øp ";

        public void testEncode() throws UnsupportedEncodingException {
                
		String gwtaiEncodedString = Base64Util.encodeString(TEST_INPUT);
		String catalinaEncodedString = Base64Utils.toBase64(TEST_INPUT.getBytes("utf-8"));
		System.out.print(gwtaiEncodedString);
		assertEquals(catalinaEncodedString, gwtaiEncodedString);		
	}
	
	public void testDecode() throws UnsupportedEncodingException {
		String gwtaiDecodedString = Base64Util.decodeString(TEST_OUTPUT);
		String catalinaDecodedString = new String(Base64Utils.fromBase64(TEST_OUTPUT),"utf-8");
		
		assertEquals(catalinaDecodedString, gwtaiDecodedString);
	}
	
        public void testLongString() throws UnsupportedEncodingException {
		String encodedString = Base64Util.encodeString(TEST_LONG_STRING);
		String decodedString = new String(Base64Utils.fromBase64(encodedString), "utf-8");

		assertEquals(TEST_LONG_STRING, decodedString);
	}

        public void testLongString2() throws UnsupportedEncodingException {
		String encodedString = Base64Utils.toBase64(TEST_LONG_STRING.getBytes("utf-8"));
		String decodedString = Base64Util.decodeString(encodedString);

		assertEquals(TEST_LONG_STRING, decodedString);
	}

        public void testLongString3() throws UnsupportedEncodingException {
		String encodedString = Base64Util.encodeString(TEST_LONG_STRING);
		String decodedString = Base64Util.decodeString(encodedString);

		assertEquals(TEST_LONG_STRING, decodedString);
	}

}
