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

/**
 * This abstract class provides functionality that is only meant to be used within the GwtAI
 * library. The information obtainable from a <code>AppletAccomplice</code> object are mainly
 * used to build up the wrapper widget.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */ 
public abstract class AppletAccomplice {

	/**
	 * The identifier of the applet DOM object, that allows it to be used from JavaScript. 
	 * 
	 * @return The ID.
	 */
	public String getId() {
		return getName();
	}

	/**
	 * The codebase specifies the directory that contains the applet's code. This attribute is
	 * option, if not specified the module root URL is used. 
	 * 
	 * @return The codebase URL.
	 */
	public String getCodebase() {
		return null;
	}
	
	/**
	 * The fully qualified name of the class representing this applet. The implementation used
	 * here must be a subclass of <code>java.applet.Applet</code> and must implement your
	 * applet-interface which extends from the <code>com.google.gwtai.applet.client.Applet</code>
	 * interface. 
	 * 
	 * @return The fully qualified class name.
	 */
	public abstract String getCode();
	
	public abstract int getWidth();
	
	public abstract int getHeight();
	
	public String getArchive() {
		return null;
	}
	
	public HashMap<String, String> getParameters() {
		return null;
	}
	
	/**
	 * The alignment of the applet, may be left, right, top, texttop, middle, absmiddle, baseline,
	 * bottom or absbottom. If not specified this attribute is simply omitted.
	 * 
	 * @return - The alignment attribute.
	 */
	public String getAlign() {
		return null;
	};
	
	public String getName() {
		String tmp = getClass().getName();
		
		if (tmp.lastIndexOf(".") > -1) {
			return tmp.substring(tmp.lastIndexOf(".") + 1);
		} else {
			return tmp;
		}
	}

}
