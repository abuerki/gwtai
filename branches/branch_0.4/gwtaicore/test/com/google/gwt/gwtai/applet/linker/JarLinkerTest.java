package com.google.gwt.gwtai.applet.linker;

import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.catalina.util.Base64;

import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.Artifact;
import com.google.gwt.core.ext.linker.ArtifactSet;
import com.google.gwt.core.ext.linker.ConfigurationProperty;
import com.google.gwt.core.ext.linker.SelectionProperty;
import com.google.gwt.core.ext.linker.impl.StandardConfigurationProperty;
import com.google.gwt.gwtai.applet.linker.JarLinker;

import junit.framework.TestCase;

/**
 * Test class for <code>com.google.gwt.gwtai.applet.client.Base64Util</code>.
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class JarLinkerTest extends TestCase {
	
	private class TestContext implements LinkerContext{

		@Override
		public SortedSet<ConfigurationProperty> getConfigurationProperties() {
			TreeSet<ConfigurationProperty> set = new TreeSet<ConfigurationProperty>();
			
			com.google.gwt.dev.cfg.ConfigurationProperty cp = new com.google.gwt.dev.cfg.ConfigurationProperty("jarlinker.include");
			cp.setValue("applet");
			//set.add(new StandardConfigurationProperty(cp));
			
			cp = new com.google.gwt.dev.cfg.ConfigurationProperty("jarlinker.resource");
			// <location>[::[include=**/applet/**.class][;exclude:**/applet.test.class]]"
			cp.setValue("com/google/gwt/gwtai::include=**/*.class;exclude=**/applet/client/**");
			set.add(new StandardConfigurationProperty(cp));
			return set;
		}

		@Override
		public String getModuleFunctionName() {
			return "ModuleFunction";
		}

		@Override
		public long getModuleLastModified() {
			return 0;
		}

		@Override
		public String getModuleName() {
			return "TestModule";
		}

		@Override
		public SortedSet<SelectionProperty> getProperties() {
			return new TreeSet<SelectionProperty>();
		}

		@Override
		public boolean isOutputCompact() {
			return false;
		}

		@Override
		public String optimizeJavaScript(TreeLogger logger, String jsProgram)
				throws UnableToCompleteException {
			return jsProgram;
		}
		
	}
	
	private class TestLogger extends TreeLogger{

		@Override
		public TreeLogger branch(Type type, String msg, Throwable caught,
				HelpInfo helpInfo) {
			//TODO implement
			return this;
		}

		@Override
		public boolean isLoggable(Type type) {
			return true;
		}

		@Override
		public void log(Type type, String msg, Throwable caught,
				HelpInfo helpInfo) {
			if(type==Type.INFO || type==Type.ERROR || type==Type.WARN)
				System.out.println(type.name()+": "+msg);
		}
		
	}
	 
	public void testLink() {
		JarLinker jarLinker = new JarLinker();
		
		TestLogger logger = new TestLogger();
		LinkerContext context = new TestContext();
		ArtifactSet artifacts = new ArtifactSet();
		
		try {
			ArtifactSet retArtifacts = jarLinker.link(logger, context, artifacts);
			//TODO Make this test make sense :)
		} catch (UnableToCompleteException e) {
			fail(e.getMessage());
		}
	}

}
;