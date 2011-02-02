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

    private final String TEST_LONG_DECODED = "print|v|S:dGV4dC9zdmc=|S:PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz48IURPQ1RZUEUgc3ZnIFBVQkxJQyAnLS8vVzNDLy9EVEQgU1ZHIDEuMC8vRU4nICdodHRwOi8vd3d3LnczLm9yZy9UUi8yMDAxL1JFQy1TVkctMjAwMTA5MDQvRFREL3N2ZzEwLmR0ZCc$PHN2ZyBoZWlnaHQ9Ijg0MSIgc3R5bGU9ImNvbG9yLWludGVycG9sYXRpb246YXV0bzsgY29sb3ItcmVuZGVyaW5nOmF1dG87IGZpbGw6YmxhY2s7IGZpbGwtb3BhY2l0eToxOyBmb250LWZhbWlseTomYXBvcztEaWFsb2cmYXBvczs7IGZvbnQtc2l6ZToxMjsgZm9udC1zdHlsZTpub3JtYWw7IGZvbnQtd2VpZ2h0Om5vcm1hbDsgaW1hZ2UtcmVuZGVyaW5nOmF1dG87IHNoYXBlLXJlbmRlcmluZzphdXRvOyBzdHJva2U6YmxhY2s7IHN0cm9rZS1kYXNoYXJyYXk6bm9uZTsgc3Ryb2tlLWRhc2hvZmZzZXQ6MDsgc3Ryb2tlLWxpbmVjYXA6c3F1YXJlOyBzdHJva2UtbGluZWpvaW46bWl0ZXI7IHN0cm9rZS1taXRlcmxpbWl0OjEwOyBzdHJva2Utb3BhY2l0eToxOyBzdHJva2Utd2lkdGg6MTsgdGV4dC1yZW5kZXJpbmc6YXV0bzsiIHdpZHRoPSI1OTUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiPiAgPCEtLUdlbmVyYXRlZCBieSB0aGUgQmF0aWsgR3JhcGhpY3MyRCBTVkcgR2VuZXJhdG9yLS0$ICA8ZGVmcyBpZD0iZ2VuZXJpY0RlZnMiIC8$ICA8Zz4gICAgPGRlZnMgaWQ9ImRlZnMxIj4gICAgICA8Y2xpcFBhdGggY2xpcFBhdGhVbml0cz0idXNlclNwYWNlT25Vc2UiIGlkPSJjbGlwUGF0aDEiPiAgICAgICAgPHBhdGggZD0iTTAgMCBMNTIzIDAgTDUyMyA3NjkgTDAgNzY5IEwwIDAgWiIgLz4gICAgICA8L2NsaXBQYXRoPiAgICA8L2RlZnM$ICAgIDxnIHN0eWxlPSJmaWxsOnJnYigxMTEsMTExLDExMSk7IGZvbnQtZmFtaWx5OnNhbnMtc2VyaWY7IGZvbnQtc2l6ZToyNDsgc2hhcGUtcmVuZGVyaW5nOm9wdGltaXplU3BlZWQ7IHN0cm9rZTpyZ2IoMTExLDExMSwxMTEpOyB0ZXh0LXJlbmRlcmluZzpnZW9tZXRyaWNQcmVjaXNpb247IiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgzNiwzNikiPiAgICAgIDx0ZXh0IHN0eWxlPSJjbGlwLXBhdGg6dXJsKCNjbGlwUGF0aDEpOyBzdHJva2U6bm9uZTsiIHg9IjM4OSIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgeT0iNDciPkFwYXEgU2hvcDwvdGV4dD4gICAgICA8dGV4dCBzdHlsZT0iY2xpcC1wYXRoOnVybCgjY2xpcFBhdGgxKTsgZmlsbDpibGFjazsgZm9udC1zaXplOjIwOyBzdHJva2U6bm9uZTsiIHg9IjkiIHhtbDpzcGFjZT0icHJlc2VydmUiIHk9IjE0OCI$RmFrdHVyYTwvdGV4dD4gICAgICA8dGV4dCBzdHlsZT0iY2xpcC1wYXRoOnVybCgjY2xpcFBhdGgxKTsgZmlsbDpibGFjazsgZm9udC1zaXplOjEwOyBzdHJva2U6bm9uZTsiIHg9IjQ0MCIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgeT0iMTQwIj5GYWt0dXJhbnIuOiA3Mjc8L3RleHQ$ICAgICAgPHRleHQgc3R5bGU9ImNsaXAtcGF0aDp1cmwoI2NsaXBQYXRoMSk7IGZpbGw6YmxhY2s7IGZvbnQtc2l6ZToxMDsgc3Ryb2tlOm5vbmU7IiB4PSI0NDkiIHhtbDpzcGFjZT0icHJlc2VydmUiIHk9IjE1NSI$T3JkcmVuci46IDcyNzwvdGV4dD4gICAgICA8dGV4dCBzdHlsZT0iY2xpcC1wYXRoOnVybCgjY2xpcFBhdGgxKTsgZmlsbDpibGFjazsgZm9udC1zaXplOjEwOyBzdHJva2U6bm9uZTsiIHg9IjQwMyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgeT0iMTcwIj5GYWt0dXJhZGF0bzogMjAvMTIvMTA8L3RleHQ$ICAgICAgPHRleHQgc3R5bGU9ImNsaXAtcGF0aDp1cmwoI2NsaXBQYXRoMSk7IGZpbGw6YmxhY2s7IGZvbnQtc2l6ZToxMDsgc3Ryb2tlOm5vbmU7IiB4PSI0MDEiIHhtbDpzcGFjZT0icHJlc2VydmUiIHk9IjE4NSI$Rm9yZmFsZHNkYXRvOiAyOC8xMi8xMDwvdGV4dD4gICAgPC9nPiAgICA8ZyBzdHlsZT0iZmlsbDpyZ2IoNzksNzksNzkpOyBmb250LWZhbWlseTpzYW5zLXNlcmlmOyBmb250LXNpemU6MTA7IGZvbnQtd2VpZ2h0OmJvbGQ7IHNoYXBlLXJlbmRlcmluZzpvcHRpbWl6ZVNwZWVkOyBzdHJva2U6cmdiKDc5LDc5LDc5KTsgdGV4dC1yZW5kZXJpbmc6Z2VvbWV0cmljUHJlY2lzaW9uOyIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMzYsMzYpIj4gICAgICA8dGV4dCBzdHlsZT0iY2xpcC1wYXRoOnVybCgjY2xpcFBhdGgxKTsgc3Ryb2tlOm5vbmU7IiB4PSI5IiB4bWw6c3BhY2U9InByZXNlcnZlIiB5PSIyMzMiPlRFS1NUPC90ZXh0PiAgICAgIDx0ZXh0IHN0eWxlPSJjbGlwLXBhdGg6dXJsKCNjbGlwUGF0aDEpOyBzdHJva2U6bm9uZTsiIHg9IjE1MCIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgeT0iMjMzIj5BTlRBTDwvdGV4dD4gICAgICA8dGV4dCBzdHlsZT0iY2xpcC1wYXRoOnVybCgjY2xpcFBhdGgxKTsgc3Ryb2tlOm5vbmU7IiB4PSIxOTIiIHhtbDpzcGFjZT0icHJlc2VydmUiIHk9IjIzMyI$RU5IRUQ8L3RleHQ$ICAgICAgPHRleHQgc3R5bGU9ImNsaXAtcGF0aDp1cmwoI2NsaXBQYXRoMSk7IHN0cm9rZTpub25lOyIgeD0iMzcxIiB4bWw6c3BhY2U9InByZXNlcnZlIiB5PSIyMzMiPlBSSVM8L3RleHQ$ICAgICAgPHRleHQgc3R5bGU9ImNsaXAtcGF0aDp1cmwoI2NsaXBQYXRoMSk7IHN0cm9rZTpub25lOyIgeD0iNDc2IiB4bWw6c3BhY2U9InByZXNlcnZlIiB5PSIyMzMiPkJFTMOYQjwvdGV4dD4gICAgICA8dGV4dCBzdHlsZT0iY2xpcC1wYXRoOnVybCgjY2xpcFBhdGgxKTsgZmlsbDpibGFjazsgZm9udC13ZWlnaHQ6bm9ybWFsOyBzdHJva2U6bm9uZTsiIHg9IjkiIHhtbDpzcGFjZT0icHJlc2VydmUiIHk9IjI1NSI$dGVzdDwvdGV4dD4gICAgICA8dGV4dCBzdHlsZT0iY2xpcC1wYXRoOnVybCgjY2xpcFBhdGgxKTsgZmlsbDpibGFjazsgZm9udC13ZWlnaHQ6bm9ybWFsOyBzdHJva2U6bm9uZTsiIHg9IjE3MiIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgeT0iMjU1Ij4yLjA8L3RleHQ$ICAgICAgPHRleHQgc3R5bGU9ImNsaXAtcGF0aDp1cmwoI2NsaXBQYXRoMSk7IGZpbGw6YmxhY2s7IGZvbnQtd2VpZ2h0Om5vcm1hbDsgc3Ryb2tlOm5vbmU7IiB4PSIxOTIiIHhtbDpzcGFjZT0icHJlc2VydmUiIHk9IjI1NSI$c3RrLjwvdGV4dD4gICAgICA8dGV4dCBzdHlsZT0iY2xpcC1wYXRoOnVybCgjY2xpcFBhdGgxKTsgZmlsbDpibGFjazsgZm9udC13ZWlnaHQ6bm9ybWFsOyBzdHJva2U6bm9uZTsiIHg9IjM0OSIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgeT0iMjU1Ij5rciAxMjMsMDA8L3RleHQ$ICAgICAgPHRleHQgc3R5bGU9ImNsaXAtcGF0aDp1cmwoI2NsaXBQYXRoMSk7IGZpbGw6YmxhY2s7IGZvbnQtd2VpZ2h0Om5vcm1hbDsgc3Ryb2tlOm5vbmU7IiB4PSI0NjUiIHhtbDpzcGFjZT0icHJlc2VydmUiIHk9IjI1NSI$a3IgMjQ2LDAwPC90ZXh0PiAgICAgIDx0ZXh0IHN0eWxlPSJjbGlwLXBhdGg6dXJsKCNjbGlwUGF0aDEpOyBmaWxsOmJsYWNrOyBmb250LXdlaWdodDpub3JtYWw7IHN0cm9rZTpub25lOyIgeD0iMjg0IiB4bWw6c3BhY2U9InByZXNlcnZlIiB5PSIyNzciPk5ldHRvOjwvdGV4dD4gICAgICA8dGV4dCBzdHlsZT0iY2xpcC1wYXRoOnVybCgjY2xpcFBhdGgxKTsgZmlsbDpibGFjazsgZm9udC13ZWlnaHQ6bm9ybWFsOyBzdHJva2U6bm9uZTsiIHg9IjQ2NSIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSIgeT0iMjc3Ij5rciAyNDYsMDA8L3RleHQ$ICAgICAgPHRleHQgc3R5bGU9ImNsaXAtcGF0aDp1cmwoI2NsaXBQYXRoMSk7IGZpbGw6YmxhY2s7IGZvbnQtd2VpZ2h0Om5vcm1hbDsgc3Ryb2tlOm5vbmU7IiB4PSIyODQiIHhtbDpzcGFjZT0icHJlc2VydmUiIHk9IjI5OSI$TW9tczo8L3RleHQ$ICAgICAgPHRleHQgc3R5bGU9ImNsaXAtcGF0aDp1cmwoI2NsaXBQYXRoMSk7IGZpbGw6YmxhY2s7IGZvbnQtd2VpZ2h0Om5vcm1hbDsgc3Ryb2tlOm5vbmU7IiB4PSI0NzEiIHhtbDpzcGFjZT0icHJlc2VydmUiIHk9IjI5OSI$a3IgNjEsNTA8L3RleHQ$ICAgICAgPHRleHQgc3R5bGU9ImNsaXAtcGF0aDp1cmwoI2NsaXBQYXRoMSk7IGZpbGw6YmxhY2s7IGZvbnQtd2VpZ2h0Om5vcm1hbDsgc3Ryb2tlOm5vbmU7IiB4PSIyODQiIHhtbDpzcGFjZT0icHJlc2VydmUiIHk9IjMyMSI$SSBhbHQ6PC90ZXh0PiAgICAgIDx0ZXh0IHN0eWxlPSJjbGlwLXBhdGg6dXJsKCNjbGlwUGF0aDEpOyBmaWxsOmJsYWNrOyBmb250LXdlaWdodDpub3JtYWw7IHN0cm9rZTpub25lOyIgeD0iNDY1IiB4bWw6c3BhY2U9InByZXNlcnZlIiB5PSIzMjEiPmtyIDMwNyw1MDwvdGV4dD4gICAgPC9nPiAgPC9nPjwvc3ZnPg==";
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

    @Test
    public void testNullArgument() {
        String data = null;
        List params = new ArrayList();
        params.add(data);
        GwtProxyTranslator instance = new GwtProxyTranslator();
        String expResult = null;
        ProxyRequest proxyRequest = new ProxyRequest("Test", String.class, params);

        String encoded = instance.encodeRequest(proxyRequest);
        proxyRequest = instance.decodeRequest(encoded);
        assertEquals(expResult, proxyRequest.getParameters().get(0));

        
    }

    @Test
    public void testNullArrayArgument() {
        String[] data = {null, null};
        List params = new ArrayList();
        params.add(data);

        GwtProxyTranslator instance = new GwtProxyTranslator();
        ProxyRequest proxyRequest = new ProxyRequest("Test", String.class, params);

        String encoded = instance.encodeRequest(proxyRequest);
        proxyRequest = instance.decodeRequest(encoded);

        assertEquals(null, ((String[])proxyRequest.getParameters().get(0))[1]);
    }


    @Test
    public void testStringArgument() {
        String data = "Test123";
        List params = new ArrayList();
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
        List params = new ArrayList();
        params.add(data);

        GwtProxyTranslator instance = new GwtProxyTranslator();
        ProxyRequest proxyRequest = new ProxyRequest("Test", String.class, params);

        String encoded = instance.encodeRequest(proxyRequest);
        proxyRequest = instance.decodeRequest(encoded);

        assertEquals("Doe", ((String[])proxyRequest.getParameters().get(0))[1]);
    }

    /**@Test
    public void testLongDecode() {
        GwtProxyTranslator instance = new GwtProxyTranslator();
        instance.decodeRequest(TEST_LONG_DECODED);
    }**/


    
}