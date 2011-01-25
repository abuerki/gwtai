/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.proxy;

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
public class PayloadTranslatorTest {

    public PayloadTranslatorTest() {
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
     * Test of encodePayload method, of class PayloadTranslator.
     */
    @Test
    public void testEncodePayload() {
        System.out.println("encodePayload");
        Object data = null;
        PayloadTranslator instance = new PayloadTranslator();
        String expResult = "";
        String result = instance.encodePayload(data);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of decodeRequest method, of class PayloadTranslator.
     */
    @Test
    public void testDecodeRequest() throws Exception {
        System.out.println("decodeRequest");
        String request = "myMethod|V|IA:1,2,3|SA:SGVsbG8=,V29ybGQh|S:SGVsbG8gV29ybGQh|B:92|BA:SGVsbG8gV29ybGQh";
        PayloadTranslator instance = new PayloadTranslator();
        ProxyRequest result = instance.decodeRequest(request);
        assertEquals("myMethod", result.getMethod());
        assertEquals(Void.class, result.getReturnType());
        
    }

}