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

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * A tag interface that facilitates compile-time creation of an handle objects
 * for Java Applet classes. Such a handle can be used to call methods in the
 * Applet from within a GWT application.
 * 
 * Using <code>GWT.create(<i>class</i>)</code> to 'instantiate' an interface
 * that extends <code>Applet</code> returns an instance of an automatically
 * generated subclass that implements proxy methods to the actual implementation
 * in the Java Applet class. All public methods declared in the interface
 * extending <code>Applet</code> will be wrapped and made available in the
 * proxy class.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public interface Applet extends RemoteService {

}
