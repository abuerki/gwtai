package com.google.gwt.gwtai.applet.client;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.user.client.rpc.impl.RpcStatsContext;
import com.google.gwt.user.client.rpc.impl.Serializer;

/**
 *
 * @author michaelzachariassenkrog
 */
public abstract class RemoteAppletProxy extends RemoteServiceProxy implements AppletDefTarget {

    private static int _nextGobalId = 0;
    private int _myId;

    public RemoteAppletProxy(
            String serializationPolicyName,
            Serializer serializer) {
        super("", "", serializationPolicyName, serializer);

        _nextGobalId++;
        _myId = _nextGobalId;
    }

    /**
     * The identifier of the applet DOM object, that allows it to be used from
     * JavaScript.
     *
     * @return The ID.
     */
    public String getId() {
        return getName();
    }

    public String getName() {
        String tmp = getClass().getName();

        if (tmp.lastIndexOf(".") > -1) {
            return tmp.substring(tmp.lastIndexOf(".") + 1) + _myId;
        } else {
            return tmp + _myId;
        }
    }

    /**
     * Detect if an <code>Applet</code> is ready. An <code>Applet</code> is
     * active just before its start method is called.
     *
     * @return <code>true</code> if the <code>Applet</code> is active,
     *         <code>false</code> otherwise.
     */
    public boolean isActive() {
        try {
            return isAppletActive();
        } catch (JavaScriptException jse) {
            Window.alert(jse.getDescription());
        }

        return false;
    }

    protected abstract boolean isAppletActive();

    @Override
    protected <T> Request doInvoke(ResponseReader responseReader, String methodName, RpcStatsContext statsContext, String requestData, AsyncCallback<T> callback) {
        T result = null;
        Throwable caught = null;

        try {
            String encoded = requestApplet(requestData);

            if (isReturnValue(encoded)) {
                result = (T) responseReader.read(createStreamReader(encoded));
            } else if (isThrownException(encoded)) {
                caught = (Throwable) createStreamReader(encoded).readObject();
            } else {
                caught = new InvocationException(encoded);
            }
        } catch (SerializationException ex) {
            caught = new IncompatibleRemoteServiceException(
                    "The response could not be deserialized", ex);
        } catch (Throwable e) {
            caught = e;
        } finally {
            if (statsContext.isStatsAvailable()) {
                statsContext.stats(statsContext.bytesStat(methodName,
                        requestData.length(), "requestSent"));
            }
        }


        if (caught == null) {
            callback.onSuccess(result);
        } else {
            callback.onFailure(caught);
        }
        return new AppletRequest();
    }

    /**
     * Return <code>true</code> if the encoded response contains a value returned
     * by the method invocation.
     *
     * @param encodedResponse
     * @return <code>true</code> if the encoded response contains a value returned
     *         by the method invocation
     */
    static boolean isReturnValue(String encodedResponse) {
        return encodedResponse.startsWith("//OK");
    }

    /**
     * Return <code>true</code> if the encoded response contains a checked
     * exception that was thrown by the method invocation.
     *
     * @param encodedResponse
     * @return <code>true</code> if the encoded response contains a checked
     *         exception that was thrown by the method invocation
     */
    static boolean isThrownException(String encodedResponse) {
        return encodedResponse.startsWith("//EX");
    }

    protected abstract String requestApplet(String data);
}
