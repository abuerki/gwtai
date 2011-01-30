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
package com.google.gwt.gwtai.applet.generator;

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.gwtai.applet.client.Align;
import com.google.gwt.gwtai.applet.client.AppletClassName;
import com.google.gwt.gwtai.applet.client.Archive;
import com.google.gwt.gwtai.applet.client.Codebase;
import com.google.gwt.gwtai.applet.client.Height;
import com.google.gwt.gwtai.applet.client.ImplementingClass;
import com.google.gwt.gwtai.applet.client.JavaArguments;
import com.google.gwt.gwtai.applet.client.JavaVersion;
import com.google.gwt.gwtai.applet.client.LoadingImage;
import com.google.gwt.gwtai.applet.client.Params;
import com.google.gwt.gwtai.applet.client.SeparateJVM;
import com.google.gwt.gwtai.applet.client.Width;
import com.google.gwt.gwtai.applet.client.ProxyRequest;

/**
 * GWT Generator that creates the proxy classes to access the applet. The proxy
 * classes consist mostly of native JavaScript code embedded with GWT's JNSI
 * functionality. 
 * 
 * @author Adrian Buerki <a.buerki@gmail.com>
 */
public class AppletProxyGenerator extends Generator {
    // private static HashMap<String, String> _createdClassNames = new HashMap<String, String>();;

    public String generate(TreeLogger logger, GeneratorContext context,
            String typeName) throws UnableToCompleteException {


        // String createdClassName = _createdClassNames.get(typeName);

        // if (null == createdClassName) {
        JClassType classType;

        try {
            classType = context.getTypeOracle().getType(typeName);
        } catch (Exception e) {
            logger.log(Type.ERROR, e.getMessage(), e);

            throw new UnableToCompleteException();
        }

        String packageName = classType.getPackage().getName();
        String simpleName = classType.getSimpleSourceName() + "Impl";

        PrintWriter printWriter = context.tryCreate(logger, packageName,
                simpleName);

        if (printWriter == null) {
            // Has already been created
            return packageName + "." + simpleName;
        }

        ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(
                packageName, simpleName);

        composer.addImport("java.util.HashMap");
        composer.addImport("com.google.gwt.gwtai.applet.client.AppletCallback");
        composer.addImport("com.google.gwt.gwtai.applet.client.ProxyRequest");
        composer.addImport("com.google.gwt.gwtai.applet.client.GwtProxyTranslator");
        composer.addImport("java.util.ArrayList");
        composer.addImport("java.util.List");
        composer.addImport("com.google.gwt.dom.client.Element");
        composer.addImport("com.google.gwt.user.client.DOM");
        composer.addImport("com.google.gwt.gwtai.applet.proxy.AppletProxy");

        composer.addImplementedInterface(typeName);
        composer.setSuperclass("com.google.gwt.gwtai.applet.client.AppletAccomplice");

        SourceWriter sw = composer.createSourceWriter(context, printWriter);

        sw.println("GwtProxyTranslator translator = new GwtProxyTranslator();");
        sw.println();

        sw.println();
        sw.indent();
        sw.println("private native String requestApplet(String data) ");
        sw.println(" /*-{");
        sw.indent();
        sw.println("var id = this.@" + packageName + "." + simpleName + "::getName()();");
        sw.println("var gwtElem = @com.google.gwt.user.client.DOM::getElementById(Ljava/lang/String;)(id);");
        sw.println("return gwtElem['handleRequest'](data);");
        sw.outdent();
        sw.println("}-*/;");
        sw.outdent();
        sw.outdent();

        JMethod[] methods = classType.getOverridableMethods();

        for (JMethod method : methods) {
            String methodDeclaration = method.getReadableDeclaration(false,
                    false, false, false, true);
            
            sw.println();
            sw.indent();
            sw.print(methodDeclaration);
            sw.println("{");
            sw.indent();

            sw.println("List params = new ArrayList();");

            JParameter[] methodParams = method.getParameters();
            for (JParameter param : methodParams) {
                sw.println("params.add("+param.getName()+");");
            }

            JType type = method.getReturnType();

            sw.println();
            sw.println("String encodedRequest=null;");
            sw.println("try{");
            sw.println("ProxyRequest proxyRequest = new ProxyRequest(\""+method.getName()+"\", "+type.getQualifiedSourceName()+".class , params);");
            sw.println("encodedRequest = translator.encodeRequest(proxyRequest);");
            sw.println("} catch(Exception ex){com.google.gwt.user.client.Window.alert(ex.getMessage());}");

            sw.println();

            if (!type.getSimpleSourceName().equals("void")) {
                sw.println("return ("+type.getSimpleSourceName()+")translator.decodeResponse(requestApplet(encodedRequest));");
            } else {
                sw.println("requestApplet(encodedRequest);");
            }

            sw.outdent();
            sw.println("}");
            sw.outdent();
        }

        sw.println();
        sw.indent();
        sw.print("protected native boolean isAppletActive()");
        sw.println(" /*-{");
        sw.indent();
        sw.print("var id = '");
        sw.println(simpleName + "';");
        sw.println("var gwtElem = @com.google.gwt.user.client.DOM::getElementById(Ljava/lang/String;)(id);");
        sw.println("return gwtElem['isActive']();");
        sw.outdent();
        sw.println("}-*/;");
        sw.outdent();

        ImplementingClass implementingClass = classType.getAnnotation(ImplementingClass.class);

        String appletName;

        if (null == implementingClass) {
            AppletClassName appletClassName = classType.getAnnotation(AppletClassName.class);

            if (null == appletClassName) {
                logger.log(Type.ERROR, "No Applet class name given, use the ImplementingClass or AppletClassName annotation.");

                throw new UnableToCompleteException();
            } else {
                appletName = appletClassName.value();
            }
        } else {
            appletName = implementingClass.value().getName();
        }

        sw.println();
        sw.indent();
        sw.println("public String getCode() {");
        sw.indent();
        sw.print("return \"");
        sw.print(appletName + ".class");
        sw.println("\";");
        sw.outdent();
        sw.println("}");
        sw.outdent();

        Width widthAttribute = classType.getAnnotation(Width.class);

        sw.println();
        sw.indent();
        sw.println("public String getWidth() {");
        sw.indent();
        sw.print("return ");

        if (null == widthAttribute) {
            // Default to 350
            sw.print("\"350\"");
        } else {
            sw.print("\"" + widthAttribute.value() + "\"");
        }

        sw.println(";");
        sw.outdent();
        sw.println("}");
        sw.outdent();

        Height heigthAttribute = classType.getAnnotation(Height.class);

        sw.println();
        sw.indent();
        sw.println("public String getHeight() {");
        sw.indent();
        sw.print("return ");

        if (null == heigthAttribute) {
            // Default to 350
            sw.print("\"350\"");
        } else {
            sw.print("\"" + heigthAttribute.value() + "\"");
        }

        sw.println(";");
        sw.outdent();
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
            sw.println("}");
            sw.outdent();
        }

        JavaVersion javaVersionAttribute = classType.getAnnotation(JavaVersion.class);

        if (null != javaVersionAttribute) {
            sw.println();
            sw.indent();
            sw.println("public String getJavaVersion() {");
            sw.indent();
            sw.print("return \"");
            sw.print(javaVersionAttribute.value());
            sw.println("\";");
            sw.outdent();
            sw.println("}");
            sw.outdent();
        }

        JavaArguments javaArgumentsAttribute = classType.getAnnotation(JavaArguments.class);

        if (null != javaArgumentsAttribute) {
            sw.println();
            sw.indent();
            sw.println("public String getJavaArguments() {");
            sw.indent();
            sw.print("return \"");
            sw.print(javaArgumentsAttribute.value());
            sw.println("\";");
            sw.outdent();
            sw.println("}");
            sw.outdent();
        }

        SeparateJVM separateJVM = classType.getAnnotation(SeparateJVM.class);

        if (null != separateJVM) {
            sw.println();
            sw.indent();
            sw.println("public Boolean hasSeparateJVM() {");
            sw.indent();
            sw.print("return \"");
            sw.print(javaArgumentsAttribute.value());
            sw.println("\";");
            sw.outdent();
            sw.println("}");
            sw.outdent();
        }

        LoadingImage loadingImage = classType.getAnnotation(LoadingImage.class);

        if (null != loadingImage) {
            sw.println();
            sw.indent();
            sw.println("public String getLoadingImage() {");
            sw.indent();
            sw.print("return \"");
            sw.print(loadingImage.value());
            sw.println("\";");
            sw.outdent();
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

        String createdClassName = composer.getCreatedClassName();

        // _createdClassNames.put(typeName, createdClassName);
        // }

        return createdClassName;
    }
}
