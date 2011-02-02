/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google.gwt.gwtai.applet.proxy;

import java.applet.Applet;
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
public class AppletProxyTest {


    public AppletProxyTest() {
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
     * Test of handleMethodCall method, of class AppletProxy.
     */
    @Test
    public void testHandleMethodCallByte2String() throws Throwable {
        System.out.println("handleMethodCall");

        String expResult = "SGVsbG8gV29ybGQh";
        String data = "handleByte2String|S|BA:"+expResult;

        AppletProxy instance = new AppletProxy();
        instance.setClassName(MockApplet.class.getName());
        instance.init();

        
        String result = instance.handleRequest(data);
        assertEquals("S:"+expResult, result);
        assertTrue(((MockApplet)instance.getProxyFor()).isInvoked());
    }

        /**
     * Test of handleMethodCall method, of class AppletProxy.
     */
    @Test
    public void testHandleMethodCallBoolean2boolean() throws Throwable {

        String data = "handleBoolean2boolean|Z|Z:1";

        AppletProxy instance = new AppletProxy();
        instance.setClassName(MockApplet.class.getName());
        instance.init();

        String result = instance.handleRequest(data);
        assertEquals("Z:1", result);
        assertTrue(((MockApplet)instance.getProxyFor()).isInvoked());
    }

       /**
     * Test of handleMethodCall method, of class AppletProxy.
     */
    @Test
    public void testHandleMethodCallLong() throws Throwable {

        String data = "handleLong|L|L:1000000000";

        AppletProxy instance = new AppletProxy();
        instance.setClassName(MockApplet.class.getName());
        instance.init();

        String result = instance.handleRequest(data);
        assertEquals("L:1000000000", result);
        assertTrue(((MockApplet)instance.getProxyFor()).isInvoked());
    }


       /**
     * Test of handleMethodCall method, of class AppletProxy.
     */
    @Test
    public void testHandleMethodCalldouble() throws Throwable {

        String data = "handleDouble|D|D:10000.000001";

        AppletProxy instance = new AppletProxy();
        instance.setClassName(MockApplet.class.getName());
        instance.init();

        String result = instance.handleRequest(data);
        assertEquals("D:10000.000001", result);
        assertTrue(((MockApplet)instance.getProxyFor()).isInvoked());
    }
}