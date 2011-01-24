/*
 * Copyright 2010 Adrian Buerki
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
 * The type of the actual <code>Applet</code> implementation. Either this
 * Annotation or the <code>AppletClassName</code> one have to be set, without
 * the actual name of the concrete <code>Applet</code> class the
 * <code>AppletProxyGenerator</code> can not perform its task. Note that the
 * <code>ImplementingClass</code> Annotation should be preferred, because it
 * checks for the existence of the given class and provides some more safety.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 * 
 * @see com.google.gwt.gwtai.applet.client.AppletClassName
 */
@Target(TYPE)
public @interface ImplementingClass {

	Class<? extends Applet> value();

}