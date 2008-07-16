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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Solution to make native libraries available for an applet. Some of the code
 * in this class has been copied from the Java Sun forums:
 * 
 * - http://forum.java.sun.com/thread.jspa?threadID=627890&start=15
 * - http://forum.java.sun.com/thread.jspa?threadID=658650&messageID=3875555
 *               
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class JavaLibraryPath {
	
	/**
	 * Extract the tray.dll or libtray.so (depending on the Operating System)
	 * into a temporary location and add this very location to the
	 * <i>java.library.path</i> path.
	 * 
	 * @throws Exception In case of an error.
	 */
	public static void injectTrayNativLib() throws Exception {
		String osName = System.getProperty("os.name");
		String osAch = System.getProperty("os.arch");
		File path;
		
		if (osName.contains("Solaris") || osName.contains("SunOS")) {
			if (osAch.contains("x86")) {
				path = extract("solaris/x86", "libtray.so");
			} else {
				path = extract("solaris/sparc", "libtray.so");
			}
		} else if (osName.contains("Linux")) {
			path = extract("linux", "libtray.so");
		} else {
			path = extract("win", "tray.dll");
		}

		add(path);
	}
	
	private static File extract(String packageName, String resourceName) throws Exception {
		InputStream fin = JavaLibraryPath.class.getResourceAsStream("/" + packageName + "/" + resourceName);
			
		String tmpDirName = System.getProperty("java.io.tmpdir");
			
		if (null == tmpDirName) {
			tmpDirName = System.getProperty("user.home");
		}
			
		File tmpFile = new File(tmpDirName + resourceName);
		
		if (!tmpFile.exists()) {
			tmpFile.createNewFile();
			// tmpFile.deleteOnExit();
	    
			OutputStream fout = new FileOutputStream(tmpFile);
	        
			byte[] buf = new byte[1024];
			int len;
			while ((len = fin.read(buf)) > 0) {
				fout.write(buf, 0, len);
			}

			fin.close();
			fout.close();
		}

		return new File(tmpDirName);
	}

	private static void add(File path) throws Exception {
		String newLibraryPath = System.getProperty("java.library.path");
		if (newLibraryPath == null || newLibraryPath.length() < 1) {
			newLibraryPath = path.getCanonicalPath();
		} else {
			newLibraryPath += File.pathSeparator +
			path.getCanonicalPath();
		}
	 
		Field f = System.class.getDeclaredField("props");
		f.setAccessible(true);
		Properties props = (Properties) f.get(null);

		props.put("java.library.path", newLibraryPath);
	 
		Field usr_pathsField = ClassLoader.class.getDeclaredField("usr_paths");
		usr_pathsField.setAccessible(true);
		String[] usr_paths = (String[]) usr_pathsField.get(null);
		String[] newUsr_paths = new String[usr_paths == null ? 1 : 
			usr_paths.length + 1];
		
		if (usr_paths != null) {
			System.arraycopy(usr_paths, 0, newUsr_paths, 0, usr_paths.length);
		}

		newUsr_paths[newUsr_paths.length - 1] = path.getAbsolutePath();
		usr_pathsField.set(null, newUsr_paths);
	}

}
