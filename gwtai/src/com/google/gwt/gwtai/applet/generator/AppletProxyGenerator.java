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

package com.google.gwt.gwtai.applet.generator;

import java.io.PrintWriter;
import java.util.HashMap;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.gwtai.applet.client.Align;
import com.google.gwt.gwtai.applet.client.Archive;
import com.google.gwt.gwtai.applet.client.Codebase;
import com.google.gwt.gwtai.applet.client.Height;
import com.google.gwt.gwtai.applet.client.ImplementingClass;
import com.google.gwt.gwtai.applet.client.Params;
import com.google.gwt.gwtai.applet.client.Width;

public class AppletProxyGenerator extends Generator {
	private HashMap<String, String> _createdClassNames;
	
	public AppletProxyGenerator() {
		_createdClassNames = new HashMap<String, String>();
	}

	public String generate(TreeLogger logger, GeneratorContext context, String typeName)
			throws UnableToCompleteException {
		
		String createdClassName = _createdClassNames.get(typeName);
		
		if (null == createdClassName) {
			JClassType classType;
			
			try {			
				classType = context.getTypeOracle().getType(typeName);
			} catch (Exception e) {
				logger.log(Type.ERROR, e.getMessage(), e);
				
				throw new UnableToCompleteException();
			}
			
			String packageName = classType.getPackage().getName ();
			String simpleName = classType.getSimpleSourceName() + "Impl";
			ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(packageName, simpleName);
			
			composer.addImport("java.util.HashMap");
			composer.addImport("com.google.gwt.gwtai.applet.client.AppletCallback");
			composer.addImport("com.google.gwt.dom.client.Element");
			composer.addImport("com.google.gwt.user.client.DOM");
			
			composer.addImplementedInterface(typeName);
			composer.setSuperclass("com.google.gwt.gwtai.applet.client.AppletAccomplice");
			
			PrintWriter printWriter = context.tryCreate (logger, packageName, simpleName);
			SourceWriter sw = composer.createSourceWriter(context, 	printWriter);
				
			JMethod[] methods = classType.getOverridableMethods();
				
			for (JMethod method : methods) {
				sw.println();
				sw.indent();
				sw.print(method.getReadableDeclaration(false, false, false, false, true));
				sw.println(" {");
				sw.indent();
				sw.print("Element elem = DOM.getElementById(");
				sw.print("getId()");
				sw.println(");");
				sw.print("Object obj = ");
				sw.print("call(elem, \"");
				sw.print(method.getName());
				sw.println("\");");
				
				JType type = method.getReturnType();
				
				if (!type.getSimpleSourceName().equals("void")) {
					if (null != type.isPrimitive()) {
						sw.print("return ");
						sw.print(type.isPrimitive().getQualifiedBoxedSourceName());
						sw.println(".valueOf(obj.toString());"); 
					} else {
						sw.print("return (");
						sw.print(type.getSimpleSourceName());
						sw.print(") ");
						sw.println("obj;");
					}
				}

				sw.outdent();
				sw.println("}");
				sw.outdent();
			}

			sw.println();
			sw.indent();
			sw.println("public native Object call(Element elem, String methodName) /*-{");
			sw.indent();
			sw.println("var theFunc = elem[methodName];");
			sw.println("return theFunc();");
			sw.outdent();
			sw.println("}-*/;");
			sw.outdent();
				
			ImplementingClass implementingClass = classType.getAnnotation(ImplementingClass.class);
				
			if (null == implementingClass) {
				logger.log(Type.ERROR, "Implementing class annotation is missing.");
					
				throw new UnableToCompleteException();
			}

			sw.println();
			sw.indent();
			sw.println("public String getCode() {");
			sw.indent();
			sw.print("return \"");
			sw.print(implementingClass.value().getName() + ".class");
			sw.println("\";");
			sw.outdent();
			sw.println("}");
			sw.outdent();
			
			Width widthAttribute = classType.getAnnotation(Width.class);
			
			sw.println();
			sw.indent();
			sw.println("public int getWidth() {");
			sw.indent();
			sw.print("return ");
			
			if (null == widthAttribute) {
				// Default to 350
				sw.print("350");
			} else {
				sw.print(widthAttribute.value() + "");
			}
			
			sw.println(";");
			sw.outdent();
			sw.println("}");
			sw.outdent();
			
			Height heigthAttribute = classType.getAnnotation(Height.class);
			
			sw.println();
			sw.indent();
			sw.println("public int getHeight() {");
			sw.indent();
			sw.print("return ");
			
			if (null == heigthAttribute) {
				// Default to 350
				sw.print("350");
			} else {
				sw.print(heigthAttribute.value() + "");
			}
			
			sw.println(";");
			sw.outdent();
			sw.println();
			sw.println("}");
			sw.outdent();
			
			Align alignAttribute = classType.getAnnotation(Align.class);
			
			if (null != alignAttribute) {
				sw.println();
				sw.indent();
				sw.println("public AppletCallback getAlign() {");
				sw.indent();
				sw.print("return \"");
				sw.print(alignAttribute.value().name());
				sw.println("\";");
				sw.outdent();
				sw.println();
				sw.println("}");
				sw.outdent();
			}
			
			Archive archiveAttribute = classType.getAnnotation(Archive.class);
			
			if (null != archiveAttribute) {
				sw.println();
				sw.indent();
				sw.println("public String getArchive() {");
				sw.indent();
				sw.print("return \"");
				sw.print(archiveAttribute.value());
				sw.println("\";");
				sw.outdent();
				sw.println();
				sw.println("}");
				sw.outdent();
			}
			
			Codebase codeBaseAttribute = classType.getAnnotation(Codebase.class);
			
			if (null != codeBaseAttribute) {
				sw.println();
				sw.indent();
				sw.println("public String getCodebase() {");
				sw.indent();
				sw.print("return \"");
				sw.print(codeBaseAttribute.value());
				sw.println("\";");
				sw.outdent();
				sw.println();
				sw.println("}");
				sw.outdent();
			}
			
			Params paramAttribute = classType.getAnnotation(Params.class);
			
			if (null != paramAttribute) {
				sw.println();
				sw.indent();
				sw.println("public HashMap<String, String> getParameters() {");
				sw.indent();
				sw.println("HashMap<String, String> props = new HashMap<String, String>();");
				
				String[] names = paramAttribute.names();
				String[] values = paramAttribute.values();
				
				for (int i = 0; i < names.length; i++) {
					sw.print("props.put(\"");
					sw.print(names[i]);
					sw.print("\", \"");
					sw.print(values[i]);
					sw.println("\");");
				}

				sw.print("return props;");
				sw.outdent();
				sw.println();
				sw.println("}");
				sw.outdent();
			}
			
			sw.commit(logger);
			
			createdClassName = composer.getCreatedClassName();
			
			_createdClassNames.put(typeName, createdClassName);
		}

		return createdClassName;
	}

}
