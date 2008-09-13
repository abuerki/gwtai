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

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Target;

/**
 * The <code>Param</code> Annotation specifies the value that is filled into
 * the applet's PARAM attribute. With the PARAM attribute any number of
 * parameters (name/value pair) can be passed to the applet.
 * 
 * This Annotation is optional. The values are of type <code>String</code>.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 * 
 * @see com.google.gwt.gwtai.applet.client.Height
 */
@Target(TYPE)
public @interface Params {
	
	String[] names();

	String[] values();
	
}
