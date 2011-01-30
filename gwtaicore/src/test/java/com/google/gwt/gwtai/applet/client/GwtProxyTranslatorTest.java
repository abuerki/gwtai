/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.client;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michaelzachariassenkrog
 */
public class GwtProxyTranslatorTest {

    public GwtProxyTranslatorTest() {
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

    @Test
    public void testStringReturn() {
        String data = "Test123";
        GwtProxyTranslator instance = new GwtProxyTranslator();
        String expResult = data;
        String encoded = instance.encodeResponse(data);
        String result = (String)instance.decodeResponse(encoded);
        assertEquals(expResult, result);

    }

    @Test
    public void testStringArrayReturn() {
        String[] data = {"Test123","John Doe"};
        GwtProxyTranslator instance = new GwtProxyTranslator();
        String[] expResult = data;
        String encoded = instance.encodeResponse(data);
        String[] result = (String[])instance.decodeResponse(encoded);
        assertArrayEquals(expResult, result);
    }

    /*@Test
    public void testNullArgument() {
        String data = null;
        List<String> params = new ArrayList<String>();
        params.add(data);
        GwtProxyTranslator instance = new GwtProxyTranslator();
        String expResult = null;
        ProxyRequest proxyRequest = new ProxyRequest("Test", String.class, params);

        String encoded = instance.encodeRequest(proxyRequest);
        proxyRequest = instance.decodeRequest(encoded);
        assertEquals(expResult, proxyRequest.getParameters().get(0));

    }*/

    @Test
    public void testStringArgument() {
        String data = "Test123";
        List<String> params = new ArrayList<String>();
        params.add(data);
        GwtProxyTranslator instance = new GwtProxyTranslator();
        String expResult = data;
        ProxyRequest proxyRequest = new ProxyRequest("Test", String.class, params);

        String encoded = instance.encodeRequest(proxyRequest);
        proxyRequest = instance.decodeRequest(encoded);
        assertEquals(expResult, proxyRequest.getParameters().get(0));

    }

    @Test
    public void testStringArrayArgument() {
        String[] data = {"John", "Doe"};
        List<String[]> params = new ArrayList<String[]>();
        params.add(data);
        
        GwtProxyTranslator instance = new GwtProxyTranslator();
        ProxyRequest proxyRequest = new ProxyRequest("Test", String.class, params);

        String encoded = instance.encodeRequest(proxyRequest);
        proxyRequest = instance.decodeRequest(encoded);

        assertEquals("Doe", ((String[])proxyRequest.getParameters().get(0))[1]);
    }

    
}