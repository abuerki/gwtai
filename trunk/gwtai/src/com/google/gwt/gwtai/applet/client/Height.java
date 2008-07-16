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
 * The <code>Height</code> Annotation specifies the value that is filled into the applet's HEIGHT
 * attribute. The WIDTH and HEIGHT attributes define the dimensions of the applet. The value can be
 * given in pixels or as a percentage of the parent element's height.
 * 
 * This Annotation is optional. If no value is given, the <code>AppletProxyGenerator</code> will
 * use 350 as a default value.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 * 
 * @see com.google.gwt.gwtai.applet.client.Width
 */
@Target(TYPE)
public @interface Height {

	int value();

}