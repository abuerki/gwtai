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
import java.util.Random;

/**
 * Solution to make native libraries available for an applet. Some of the code
 * in this class has been copied from the Java Sun forums:
 * <ul>
 * <li>http://forum.java.sun.com/thread.jspa?threadID=627890&start=15</li>
 * <li>http://forum.java.sun.com/thread.jspa?threadID=658650&messageID=3875555</li>
 * </ul>
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class JavaLibraryPath {
	private static final Random RANDOM = new Random();

	/**
	 * Extract the given files into a temporary location and add this very
	 * location to the <i>java.library.path</i> path.
	 * 
	 * @throws Exception
	 *             In case of an error.
	 */
	public static void injectNativLib(LibraryElement... pathElements)
			throws Exception {
		String osName = System.getProperty("os.name");
		String osAch = System.getProperty("os.arch");
		File path = null;

		for (LibraryElement pathElement : pathElements) {
			if (pathElement.matchesOsNameAndArch(osName, osAch)) {
				if (path == null) {
					path = extract(pathElement.getPackageName(), pathElement
							.getLibraryName());
				} else {
					path = extract(path, pathElement.getPackageName(),
							pathElement.getLibraryName());
				}
			}
		}

		if (path != null) {
			add(path);
		}
	}

	/**
	 * Extract the given files into a temporary location and add this very
	 * location to the <i>java.library.path</i> path.
	 * 
	 * @throws Exception
	 *             In case of an error.
	 */
	public static void injectNativLib(String folderName, String... fileNames)
			throws Exception {
		File path = extract(folderName, fileNames);

		add(path);
	}

	private static File extract(File tmpDirFile, String packageName,
			String... resourceNames) throws Exception {
		InputStream fin;
		File tmpFile;

		for (String resourceName : resourceNames) {
			fin = JavaLibraryPath.class.getResourceAsStream("/" + packageName
					+ "/" + resourceName);
			tmpFile = new File(tmpDirFile.getPath() + File.separator
					+ resourceName);

			if (!tmpFile.exists()) {
				tmpFile.createNewFile();
			}

			OutputStream fout = new FileOutputStream(tmpFile);

			byte[] buf = new byte[1024];
			int len;

			while ((len = fin.read(buf)) > 0) {
				fout.write(buf, 0, len);
			}

			fin.close();
			fout.flush();
			fout.close();
		}

		return tmpDirFile;
	}

	private static File extract(String packageName, String... resourceNames)
			throws Exception {
		String tmpDirName = System.getProperty("java.io.tmpdir");

		if (null == tmpDirName) {
			tmpDirName = System.getProperty("user.home");
		}

		tmpDirName += File.separator + "ld" + RANDOM.nextInt(100);

		File tmpDirFile = new File(tmpDirName);

		if (!tmpDirFile.exists()) {
			tmpDirFile.mkdir();
		}

		return extract(tmpDirFile, packageName, resourceNames);
	}

	private static void add(File path) throws Exception {
		String newLibraryPath = System.getProperty("java.library.path");
		if (newLibraryPath == null || newLibraryPath.length() < 1) {
			newLibraryPath = path.getCanonicalPath();
		} else {
			newLibraryPath += File.pathSeparator + path.getCanonicalPath();
		}

		Field f = System.class.getDeclaredField("props");
		f.setAccessible(true);
		Properties props = (Properties) f.get(null);

		props.put("java.library.path", newLibraryPath);

		Field usr_pathsField = ClassLoader.class.getDeclaredField("usr_paths");
		usr_pathsField.setAccessible(true);
		String[] usr_paths = (String[]) usr_pathsField.get(null);
		String[] newUsr_paths = new String[usr_paths == null ? 1
				: usr_paths.length + 1];

		if (usr_paths != null) {
			System.arraycopy(usr_paths, 0, newUsr_paths, 0, usr_paths.length);
		}

		newUsr_paths[newUsr_paths.length - 1] = path.getAbsolutePath();
		usr_pathsField.set(null, newUsr_paths);
	}

}
