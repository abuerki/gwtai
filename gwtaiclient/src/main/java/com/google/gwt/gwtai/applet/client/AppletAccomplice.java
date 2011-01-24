/*
 * Copyright 2008 Adrian Buerki
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.gwt.gwtai.applet.client;

import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.user.client.Window;

/**
 * This abstract class provides functionality that is only meant to be used
 * within the GwtAI library. The information obtainable from a
 * <code>AppletAccomplice</code> object are mainly used to build up the
 * wrapper widget.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public abstract class AppletAccomplice {
	private static int _nextGobalId = 0;
    private int _myId;

    public AppletAccomplice() {
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

	/**
	 * The codebase specifies the directory that contains the applet's code.
	 * This attribute is option, if not specified the module root URL is used.
	 * 
	 * @return The codebase URL.
	 */
	public String getCodebase() {
		return null;
	}

	/**
	 * The fully qualified name of the class representing this applet. The
	 * implementation used here must be a subclass of
	 * <code>java.applet.Applet</code> and must implement your
	 * applet-interface which extends from the
	 * <code>com.google.gwtai.applet.client.Applet</code> interface.
	 * 
	 * @return The fully qualified class name.
	 */
	public abstract String getCode();

	/**
	 * The width defines the x-axis portion of the dimensions.
	 * 
	 * @return A value in pixels or as a percentage.
	 */
	public abstract String getWidth();

	/**
	 * The height defines the y-axis portion of the dimensions.
	 * 
	 * @return A value in pixels or as a percentage.
	 */
	public abstract String getHeight();

	public String getArchive() {
		return null;
	}
	
	/**
	 * Leveraging the new version selection mechanism.
	 * 
	 * @return A pattern to select the Java version.
	 */
	public String getJavaVersion() {
		return null;
	}
	
	/**
	 * Command-line arguments to be used when executing this applet instance.
	 *  
	 * @return Java Command-line Argument.
	 */
	public String getJavaArguments() {
		return null;
	}
	
	/**
	 * A boolean parameter specifying that a particular applet should run in its own JVM instance.
	 *  
	 * @return Java Command-line Argument.
	 */
	public Boolean hasSeparateJVM() {
		return null;
	}
	
	/**
	 * A custom loading graphic show during applet startup. 
	 *  
	 * @return The name of the image to load.
	 */
	public String getLoadingImage() {
		return null;
	}
	
	public HashMap<String, String> getParameters() {
		return null;
	}

	/**
	 * The alignment of the applet, may be left, right, top, texttop, middle,
	 * absmiddle, baseline, bottom or absbottom. If not specified this attribute
	 * is simply omitted.
	 * 
	 * @return - The alignment attribute.
	 */
	public String getAlign() {
		return null;
	};

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

}
