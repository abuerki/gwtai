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

package com.google.gwt.gwtai.applet.util;

import java.io.Serializable;

/**
 * A <code>LibraryElement</code> represents one path entry. Depending on its
 * operating system and architecture the entry is added or not.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class LibraryElement implements Serializable {
	private static final long serialVersionUID = 6235962207992562388L;

	public enum OS {
		Windows, SunOS, Linux, Mac
	};

	public enum ARCH {
		x86, amd64, x86_64, ppc, i386, sparc, sparcv9
	};

	private String _packageName;

	private String _libraryName;

	private OS _osName;

	private ARCH _osAch;

	/**
	 * Empty default constructor to honor the Java Bean specification.
	 */
	public LibraryElement() {
		super();

		_packageName = "";
		_libraryName = "";
		_osName = null;
		_osAch = null;
	}

	public LibraryElement(String packageName, String libraryName, OS osName,
			ARCH osAch) {
		_packageName = packageName;
		_libraryName = libraryName;
		_osName = osName;
		_osAch = osAch;
	}

	public LibraryElement(String packageName, String libraryName, OS osName) {
		_packageName = packageName;
		_libraryName = libraryName;
		_osName = osName;
		_osAch = null;
	}

	public String getPackageName() {
		return _packageName;
	}

	public void setPackageName(String packageName) {
		_packageName = packageName;
	}

	public String getLibraryName() {
		return _libraryName;
	}

	public void setLibraryName(String libraryName) {
		_libraryName = libraryName;
	}

	public OS getOsName() {
		return _osName;
	}

	public void setOsName(OS osName) {
		_osName = osName;
	}

	public ARCH getOsArch() {
		return _osAch;
	}

	public void setOsArch(ARCH osAch) {
		_osAch = osAch;
	}

	public boolean matchesOsNameAndArch(String osName, String osAch) {
		if (_osName == null || osName.contains(_osName.name())) {
			if (_osAch == null || osAch.contains(_osAch.name())) {
				return true;
			}
		}

		return false;
	}

}
