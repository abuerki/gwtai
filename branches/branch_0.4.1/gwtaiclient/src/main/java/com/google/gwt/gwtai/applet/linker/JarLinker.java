package com.google.gwt.gwtai.applet.linker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.AbstractLinker;
import com.google.gwt.core.ext.linker.ArtifactSet;
import com.google.gwt.core.ext.linker.ConfigurationProperty;
import com.google.gwt.core.ext.linker.LinkerOrder;
import com.google.gwt.dev.resource.impl.DefaultFilters;
import com.google.gwt.dev.resource.impl.PathPrefix;
import com.google.gwt.dev.resource.impl.PathPrefixSet;
import com.google.gwt.dev.resource.impl.ResourceOracleImpl;
import com.google.gwt.gwtai.applet.client.Applet;
import com.google.gwt.gwtai.applet.proxy.AppletProxy;
import com.google.gwt.gwtai.applet.proxy.AppletProxyStub;
import com.google.gwt.gwtai.applet.util.AppletUtil;
import com.google.gwt.gwtai.applet.util.JavaLibraryPath;
import com.google.gwt.gwtai.applet.util.LibraryElement;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.impl.AbstractSerializationStreamReader;
import com.google.gwt.user.server.Base64Utils;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyProvider;
import com.google.gwt.user.server.rpc.UnexpectedException;
import java.util.Arrays;

/**
 * A GWT linker to create and sign a JAR file which contains all the Applet classes.
 * The advantage of using a linker over Ant is that the linker runs within the
 * compiler context, meaning the same properties and environment variables are available.
 * <br><br>
 * Use an add-linker tag to trigger this linker:<br>
 * <i>&lt;add-linker name="jarlinker" /&gt;</i>
 * <br><br>
 * The following configuration properties can be used to configure the linker's behavior:
 * <ul>
 * <li>jarlinker.name</li>
 * <li>jarlinker.include</li>
 * <li>jarlinker.jarsigner</li>
 * <li>jarlinker.keystore</li>
 * <li>jarlinker.alias</li>
 * <li>jarlinker.storepass</li>
 * </ul>
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
@LinkerOrder(LinkerOrder.Order.POST)
public class JarLinker extends AbstractLinker {
    private static final String[] DEFAULTINCLUDES = {
                        "com/google/gwt/user/server::"+
                                "include=**/rpc/**/*.class;"+
                                "include=**/Base64Utils*.class;"+
                                "exclude=**Servlet.class",
                        "com/google/gwt/user/client::"+
                                "include=**/AsyncCaalback.class;"+
                                "include=**/IsSerializable.class;"+
                                "include=**/RemoteService.class;"+
                                "include=**/rpc/impl/Abstract*.class;"+
                                "include=**/rpc/**/*Exception.class;"+
                                "include=**/core/java/math/*.class;"+
                                "include=**/core/java/lang/*.class;"+
                                "include=**/rpc/Serialization*.class",
                        "com/google/gwt/gwtai/applet::"+
                                "include=**/proxy/AppletProxy*.class;"+
                                "include=**/Applet.class;"+
                                "include=**/AppletUtil.class;"+
                                "include=**/LibraryElement.class;"+
                                "include=**/JavaLibraryPath.class;"+
                                "include=**/Base64Util.class;"+
                                "include=**/StringUtil.class",
                        "com/google/gwt/core/client::"+
                                "include=**/GWT*.class"
                    };

    /*
     * import com.google.gwt.gwtai.applet.client.Applet;
import com.google.gwt.gwtai.applet.proxy.AppletProxy;
import com.google.gwt.gwtai.applet.proxy.AppletProxyStub;
import com.google.gwt.gwtai.applet.util.AppletUtil;
import com.google.gwt.gwtai.applet.util.JavaLibraryPath;
import com.google.gwt.gwtai.applet.util.LibraryElement;
     */

    	/**
	 * Returns a human-readable String describing the Linker.
	 */
	@Override
	public String getDescription() {
		return "jarlinker";
	}
	
	/**
	 * Invoked by the GWT compiler if our linker is properly configured in the module configuration
	 * xml using the <code>add-linker</code> tag.
	 */
	@Override
	public ArtifactSet link(TreeLogger logger, LinkerContext context, ArtifactSet artifacts)
			throws UnableToCompleteException {
		String jarName = null;
		String includeResources[] = null;
		String jarsignerPath = null;
		String keystore = null;
		String alias = null;
		String storepass = null;

                

		
		for (ConfigurationProperty currentProperty: context.getConfigurationProperties()) {
			String propName = currentProperty.getName();
			
			//TODO Should we handle multiple values?
			List<String> propValues = currentProperty.getValues();
			if(propValues.size()==0)
				continue;
			String firstPropValue = propValues.get(0);
			
			if (propName != null) {
				propName = propName.trim();
			}
			
			if (firstPropValue != null) {
				firstPropValue = firstPropValue.trim();
			}
			
			if (propName.equalsIgnoreCase("jarlinker.name")) {
				jarName = firstPropValue;
			} else if (propName.equalsIgnoreCase("jarlinker.include")) {
				if (firstPropValue != null && firstPropValue.length() > 0) {
					if (includeResources != null) {
						logger.log(Type.ERROR, "Resources already specified. Please don't use jarlinker.resource and jarlinker.include at the same time.");
						
						throw new UnableToCompleteException();
					}

					includeResources = firstPropValue.split(",");
				}
			} else if (propName.equalsIgnoreCase("jarlinker.resource")) {
				if (includeResources != null) {
					logger.log(Type.ERROR, "Resources already specified. Please don't use jarlinker.resource and jarlinker.include at the same time.");
					
					throw new UnableToCompleteException();
				}
				List<String> resourceList = currentProperty.getValues();
                                resourceList.addAll(Arrays.asList(DEFAULTINCLUDES));
				includeResources = resolveResources(logger,resourceList);
			} else if (propName.equalsIgnoreCase("jarlinker.jarsigner")) {
				jarsignerPath = firstPropValue;
			} else if (propName.equalsIgnoreCase("jarlinker.keystore")) {
				keystore = firstPropValue;
			} else if (propName.equalsIgnoreCase("jarlinker.storepass")) {
				storepass = firstPropValue;
			} else if (propName.equalsIgnoreCase("jarlinker.alias")) {
				alias = firstPropValue;
			}
		}
		
		if (includeResources == null || includeResources.length == 0) {
			logger.log(Type.ERROR, "No resources to include, use <set-configuration-property name=\"jarlinker.resource\" value=\"...\"/>.");
			
			throw new UnableToCompleteException();
		}
		
		if (keystore == null) {
			logger.log(Type.INFO, "No keystore set, JAR will not be signed.");
		}
		
		if (alias == null) {
			logger.log(Type.INFO, "No alias set, JAR will not be signed.");
		}
		
		if (storepass == null) {
			logger.log(Type.INFO, "No storepass set, JAR will not be signed.");
		}
		
		String fileSeparator = System.getProperty("file.separator");
		
		if (jarsignerPath == null || jarsignerPath.length() <= 0) {
			String javaHomeDir = System.getProperty("java.home");

			String cmd = javaHomeDir + fileSeparator + "bin" + fileSeparator + "jarsigner";
			
			try {
				logger.log(Type.INFO, "Running jarsigner: "+cmd);
				Runtime.getRuntime().exec(cmd);
			
				jarsignerPath = cmd;
			} catch (Exception e1) {
				// Probably in jre dir, change one up
				cmd = javaHomeDir + fileSeparator +".." + fileSeparator + "bin" + fileSeparator + "jarsigner";
				
				try {
					logger.log(Type.INFO, "Running jarsigner: "+cmd);
					Runtime.getRuntime().exec(cmd);
					
					jarsignerPath = cmd;
				} catch (Exception e2) {
					logger.log(Type.ERROR, "Unable to locate jarsigner");
					
					throw new UnableToCompleteException();
				}
			}
		} else {
			try {
				Runtime.getRuntime().exec(jarsignerPath);
			} catch (Exception e12) {
				logger.log(Type.ERROR, "Unable to locate jarsigner");
				
				throw new UnableToCompleteException();
			}
		}
		
		if (jarName == null || jarName.length() <= 0) {
			String moduleName = context.getModuleName();
			jarName = moduleName.substring(moduleName.lastIndexOf(".") + 1) +".jar";
		}
		
		// TODO
		// Linux seems to have problems with the '..' notation, so let's assemble an absolute path
		
		byte[] jarContent = createJar(logger, includeResources);
		
		if (keystore != null && alias != null && storepass != null) {
			jarContent = signJar(logger, jarsignerPath, keystore, alias, storepass, jarContent);
		}
		
		ArtifactSet appletLinkerArtifacts = new ArtifactSet(artifacts);
		appletLinkerArtifacts.add(emitBytes(logger, jarContent, jarName));
		
		logger.log(Type.INFO, "Added " + jarName + " (" + jarContent.length +" bytes) to output");

		return appletLinkerArtifacts;
	}
	
	/**
	 * Private helper-method to create and sign our JAR file.
	 */
	private byte[] createJar(TreeLogger logger, String includeResources[]) throws UnableToCompleteException {
		if(includeResources.length == 0){
			logger.log(Type.INFO, "No resources to add. JAR-file will not be created.");
			return new byte[0];
			
		}

                logger.log(Type.INFO, "Adding " + includeResources.length + " resources to JAR file");
		
		ByteArrayOutputStream byteArrayOutStream = null;
		JarOutputStream jarOutStream = null;

		try {
			byteArrayOutStream = new ByteArrayOutputStream();
			jarOutStream = new JarOutputStream(byteArrayOutStream);
			
			for (String includeResource: includeResources) {
                            logger.log(Type.DEBUG, "Adding "+includeResource);

				addEntryToJar(jarOutStream, includeResource.trim());
			}
		
			jarOutStream.close();
			
			return byteArrayOutStream.toByteArray();
		} catch (Exception e) {
			logger.log(Type.ERROR, "Failed to create JAR file.", e);
			
			if (jarOutStream != null) {
				try {
					jarOutStream.close();
				} catch (IOException ioe) {
					// Ignore
				}
			}
			
			throw new UnableToCompleteException();
		} finally {
			if (byteArrayOutStream != null) {
				try {
					byteArrayOutStream.close();
				} catch (IOException ioe) {
					// Ignore
				}
			}
		}
	}
	
	/**
	 * Private helper-method to read a resource from the drive and add it to a jar file.
	 */
	private void addEntryToJar(JarOutputStream jo, String resourceName) throws IOException {
		InputStream fileContent = null;
		
		try {
			fileContent = JarLinker.class.getClassLoader().getResourceAsStream(resourceName);
		
			JarEntry je = new JarEntry(resourceName);
			jo.putNextEntry(je);

			byte[] buffer = new byte[1024];
			int nbrRead;
		     
			while ((nbrRead = fileContent.read(buffer)) != -1) {
				jo.write(buffer, 0, nbrRead);
			}
			
			jo.closeEntry();
		} finally {
			if (fileContent != null) {
				try {
					fileContent.close();
				} catch (IOException ioe) {
					// Ignore
				}
			}
		}
	}
	
        /**
         * Merges an array of resources to merge with the default set of resources.
         * @return The merged array.
         */
        /*private String[] mergeResourcesWithDefault(String[] resources) {
            List<String> merged = new ArrayList<String>();
            merged.addAll(Arrays.asList(resources));

            for(Class clazz : DEFAULTRESOURCES) {
                merged.add(clazz.getName().replace('.', '/')+".class");
            }
            return merged.toArray(new String[0]);

        }*/

	/**
	 * Resolves the resources specified by values with the following syntax:
	 * <root packagename>[::[include=<pattern>][;exclude=<pattern>]]
	 * 
	 * If no values are specified, 'applet, is used as default relative packagename.
	 * This pattern is the same syntax as used by ant for inclusion and exclusion.
	 * 
	 * @param values The values to use for retrieval.
	 * @return The list of full paths to the classes to include.
	 */
	private String[] resolveResources(TreeLogger logger, List<String> values){
		DefaultFilters defaultFilters = new DefaultFilters();
		ResourceOracleImpl resourceOracle = new ResourceOracleImpl(logger);
		PathPrefixSet pathPrefixSet = new PathPrefixSet();
		
		for(String value:values){
			String[] valuedata = value.split("::");
			String[] includes={};
			String[] excludes={};
			
			if(valuedata.length>1){
				List<String> incList = new ArrayList<String>();
				List<String> exList = new ArrayList<String>();
				String[] inclusiondata = valuedata[1].split(";");
				
				for(String inclusionElement:inclusiondata){
					String[] elementData = inclusionElement.split("=");
					if(elementData.length==2){
						if("include".equals(elementData[0])){
							incList.add(elementData[1]);
						}
						if("exclude".equals(elementData[0])){
							exList.add(elementData[1]);
						}
					}
				}
				includes=incList.toArray(new String[0]);
				excludes=exList.toArray(new String[0]);
			}
			
			PathPrefix pathPrefix = new PathPrefix(valuedata[0], 
					defaultFilters.customResourceFilter(includes, excludes, new String[0], false, false));
			pathPrefixSet.add(pathPrefix);
			
		}
		
		resourceOracle.setPathPrefixes(pathPrefixSet);
		
		ResourceOracleImpl.refresh(logger, resourceOracle);
		
		return resourceOracle.getPathNames().toArray(new String[0]);
	}
	
	/**
	 * Private helper-method to sign the jar. The Applet code must be sign, because it reaches
	 * out of the sandbox into the system by accessing the tray.
	 */
	private byte[] signJar(TreeLogger logger, String jarsignerPath, String keystore, String alias,
			String storepass, byte[] jarContent) throws UnableToCompleteException {
		logger.log(Type.INFO, "Signing JAR using alias '" + alias + "'");
		// Check the keystore
		File ksFile = new File(keystore);
			
		if (!ksFile.exists()) {
			// Look in the working directory
			String workplaceDir = System.getProperty("user.dir");
			String fileSeparator = System.getProperty("file.separator");
				
			ksFile = new File(workplaceDir + fileSeparator + keystore);
				
			if (!ksFile.exists()) {
				logger.log(Type.ERROR, "Keystore does not exist");
			}
		}
		
		File tmpFile = null;
		FileOutputStream tmpFileOutStream = null;
		ByteArrayOutputStream signedContentOutStream = null;
		FileInputStream signedTmpFileInStream = null;
		
		try {	
			tmpFile = File.createTempFile(System.currentTimeMillis() +"", ".jar");
			tmpFileOutStream = new FileOutputStream(tmpFile);
			
			tmpFileOutStream.write(jarContent);
			tmpFileOutStream.close();

			String[] cmd = {
					jarsignerPath,
					"-keystore",
					ksFile.getAbsolutePath(),
					"-storepass",
					storepass,
					tmpFile.getAbsolutePath(),
					alias
			};
			
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			
			signedContentOutStream = new ByteArrayOutputStream();
			signedTmpFileInStream = new FileInputStream(tmpFile);
			
			byte[] buffer = new byte[1024];
			int nbrRead;
		     
			while ((nbrRead = signedTmpFileInStream.read(buffer)) != -1) {
				signedContentOutStream.write(buffer, 0, nbrRead);
			}
			
			signedContentOutStream.close();

			return signedContentOutStream.toByteArray();
		} catch(Exception e) {
			logger.log(Type.ERROR, "Failed to sign JAR file.", e);
			
			if (tmpFileOutStream != null) {
				try {
					tmpFileOutStream.close();
				} catch (IOException ioe) {
					// Ignore
				}
			}
			
			if (signedContentOutStream != null) {
				try {
					signedContentOutStream.close();
				} catch (IOException ioe) {
					// Ignore
				}
			}
			
			throw new UnableToCompleteException();
		} finally {
			if (tmpFile != null) {
				try {
					tmpFile.delete();
				} catch (Exception e) {
					// Ignore
				}
			}
			
			if (signedTmpFileInStream != null) {
				try {
					signedTmpFileInStream.close();
				} catch (IOException ioe) {
					// Ignore
				}
			}
		}
	}

}
