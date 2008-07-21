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
 * The <code>Align</code> Annotation specifies the value that is filled into the applet's ALIGN
 * attribute. The ALIGN attributes define the alignment of the applet. The possible values of this
 * attribute are:
 * - left
 * - right
 * - top
 * - texttop
 * - middle
 * - absmiddle
 * - baseline
 * - bottom
 * - absbottom
 * 
 * This Annotation is optional.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
@Target(TYPE)
public @interface Align {
	enum Alignment {left, right, top, texttop, middle, absmiddle, baseline, bottom, absbottom}; 
	
	Alignment value();

}