/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.client;

import java.io.UnsupportedEncodingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author krog
 */
public class StringUtilTest {

    public StringUtilTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of convertByteArrayToString method, of class StringUtil.
     */
    @Test
    public void testConvertByteArrayToString() throws UnsupportedEncodingException {
        System.out.println("convertByteArrayToString");
        String string = "Howdy æblegrød med fløde på!";
        byte[] bytes = string.getBytes("utf-8");

        String result = StringUtil.convertByteArrayToString(bytes);
        assertEquals(string, result);

    }

    /**
     * Test of convertStringToByteArray method, of class StringUtil.
     */
    @Test
    public void testConvertStringToByteArray() throws UnsupportedEncodingException {
        System.out.println("convertStringToByteArray");
        String string = "Howdy æblegrød med fløde på!";
        byte[] expResult = string.getBytes("utf-8");
        byte[] result = StringUtil.convertStringToByteArray(string);
        assertArrayEquals(expResult, result);
    }

    	
}