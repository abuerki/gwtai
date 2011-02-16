/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.gwt.gwtai.applet.generator;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.ConfigurationProperty;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.rebind.rpc.SerializableTypeOracle;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.gwtai.applet.client.Align;
import com.google.gwt.gwtai.applet.client.AppletClassName;
import com.google.gwt.gwtai.applet.client.Codebase;
import com.google.gwt.gwtai.applet.client.Height;
import com.google.gwt.gwtai.applet.client.ImplementingClass;
import com.google.gwt.gwtai.applet.client.JavaArguments;
import com.google.gwt.gwtai.applet.client.JavaVersion;
import com.google.gwt.gwtai.applet.client.LoadingImage;
import com.google.gwt.gwtai.applet.client.Params;
import com.google.gwt.gwtai.applet.client.RemoteAppletProxy;
import com.google.gwt.gwtai.applet.client.SeparateJVM;
import com.google.gwt.gwtai.applet.client.Width;
import com.google.gwt.rpc.server.RPC;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.rebind.rpc.ProxyCreator;
import com.google.gwt.user.rebind.rpc.SerializableTypeOracleBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author michaelzachariassenkrog
 */
public class AppletProxyCreator extends ProxyCreator {

    private String jarname;

    public AppletProxyCreator(JClassType remoteService) {
        super(remoteService);
    }

    @Override
    public String create(TreeLogger logger, GeneratorContext context) throws UnableToCompleteException {
        jarname = getJarName(context);
        if (jarname == null) {
            logger.log(TreeLogger.Type.ERROR, "jarlinker.name missing from config.");
            throw new UnableToCompleteException();
        }

        return super.create(logger, context);


    }

    /**
     * Generate the proxy constructor and delegate to the superclass constructor
     * using the default address for the
     * {@link com.google.gwt.user.client.rpc.RemoteService RemoteService}.
     */
    @Override
    protected void generateProxyContructor(SourceWriter srcWriter) {

        srcWriter.println("public " + getProxySimpleName() + "() {");
        srcWriter.indent();
        srcWriter.println("super(");
        srcWriter.indent();
        srcWriter.println("SERIALIZATION_POLICY, ");
        srcWriter.println("SERIALIZER);");
        srcWriter.outdent();
        srcWriter.outdent();
        srcWriter.println("}");
    }

    @Override
    protected void generateProxyMethods(SourceWriter w, SerializableTypeOracle serializableTypeOracle, Map<JMethod, JMethod> syncMethToAsyncMethMap) {
        super.generateProxyMethods(w, serializableTypeOracle, syncMethToAsyncMethMap);

        String packageName = serviceIntf.getPackage().getName();
        String simpleName = serviceIntf.getSimpleSourceName() + "_Proxy";

        w.println();
        w.indent();
        w.println("protected native String requestApplet(String data) ");
        w.println(" /*-{");
        w.indent();
        w.println("var id = this.@" + packageName + "." + simpleName + "::getName()();");
        w.println("var gwtElem = @com.google.gwt.user.client.DOM::getElementById(Ljava/lang/String;)(id);");
        w.println("return gwtElem['handleRequest'](data);");
        w.outdent();
        w.println("}-*/;");
        w.outdent();

        w.println();
        w.indent();
        w.print("protected native boolean isAppletActive()");
        w.println(" /*-{");
        w.indent();
        w.println("var id = this.@" + packageName + "." + simpleName + "::getName()();");
        w.println("var gwtElem = @com.google.gwt.user.client.DOM::getElementById(Ljava/lang/String;)(id);");
        w.println("return gwtElem['isActive']();");
        w.outdent();
        w.println("}-*/;");
        w.outdent();

        ImplementingClass implementingClass = serviceIntf.getAnnotation(ImplementingClass.class);

        String appletName;

        if (null == implementingClass) {
            AppletClassName appletClassName = serviceIntf.getAnnotation(AppletClassName.class);

            if (null == appletClassName) {
                //logger.log(Type.ERROR, "No Applet class name given, use the ImplementingClass or AppletClassName annotation.");

                throw new RuntimeException("No Applet class name given, use the ImplementingClass or AppletClassName annotation.");
            } else {
                appletName = appletClassName.value();
            }
        } else {
            appletName = implementingClass.value().getName();
        }

        w.println();
        w.indent();
        w.println("public String getCode() {");
        w.indent();
        w.print("return \"");
        w.print(appletName + ".class");
        w.println("\";");
        w.outdent();
        w.println("}");
        w.outdent();

        Width widthAttribute = serviceIntf.getAnnotation(Width.class);

        w.println();
        w.indent();
        w.println("public String getWidth() {");
        w.indent();
        w.print("return ");

        if (null == widthAttribute) {
            // Default to 350
            w.print("\"350\"");
        } else {
            w.print("\"" + widthAttribute.value() + "\"");
        }

        w.println(";");
        w.outdent();
        w.println("}");
        w.outdent();

        Height heigthAttribute = serviceIntf.getAnnotation(Height.class);

        w.println();
        w.indent();
        w.println("public String getHeight() {");
        w.indent();
        w.print("return ");

        if (null == heigthAttribute) {
            // Default to 350
            w.print("\"350\"");
        } else {
            w.print("\"" + heigthAttribute.value() + "\"");
        }

        w.println(";");
        w.outdent();
        w.println("}");
        w.outdent();

        Align alignAttribute = serviceIntf.getAnnotation(Align.class);

        w.println();
        w.indent();
        w.println("public String getAlign() {");
        w.indent();
        if (null != alignAttribute) {
            w.print("return \"");
            w.print(alignAttribute.value().name());
            w.println("\";");
        } else {
            w.println("return null;");
        }
        w.outdent();
        w.println();
        w.println("}");
        w.outdent();

        w.println();
        w.indent();
        w.println("public String getArchive() {");
        w.indent();
        w.print("return GWT.getModuleBaseURL() + \"");
        w.print(jarname);
        w.println("\";");
        w.outdent();
        w.println("}");
        w.outdent();

        JavaVersion javaVersionAttribute = serviceIntf.getAnnotation(JavaVersion.class);

        w.println();
        w.indent();
        w.println("public String getJavaVersion() {");
        w.indent();
        if (null != javaVersionAttribute) {
            w.print("return \"");
            w.print(javaVersionAttribute.value());
            w.println("\";");
        } else {
            w.println("return null;");

        }
        w.outdent();
        w.println("}");
        w.outdent();

        JavaArguments javaArgumentsAttribute = serviceIntf.getAnnotation(JavaArguments.class);

        w.println();
        w.indent();
        w.println("public String getJavaArguments() {");
        w.indent();
        if (null != javaArgumentsAttribute) {
            w.print("return \"");
            w.print(javaArgumentsAttribute.value());
            w.println("\";");
        } else {
            w.println("return null;");
        }
        w.outdent();
        w.println("}");
        w.outdent();


        SeparateJVM separateJVM = serviceIntf.getAnnotation(SeparateJVM.class);

        w.println();
        w.indent();
        w.println("public Boolean hasSeparateJVM() {");
        w.indent();
        if (null != separateJVM) {
            w.print("return \"");
            w.print(javaArgumentsAttribute.value());
            w.println("\";");
        } else {
            w.println("return null;");
        }
        w.outdent();
        w.println("}");
        w.outdent();


        LoadingImage loadingImage = serviceIntf.getAnnotation(LoadingImage.class);

        w.println();
        w.indent();
        w.println("public String getLoadingImage() {");
        w.indent();
        if (null != loadingImage) {
            w.print("return \"");
            w.print(loadingImage.value());
            w.println("\";");
        } else {
            w.println("return null;");

        }
        w.outdent();
        w.println("}");
        w.outdent();


        Codebase codeBaseAttribute = serviceIntf.getAnnotation(Codebase.class);

        w.println();
        w.indent();
        w.println("public String getCodebase() {");
        w.indent();
        if (null != codeBaseAttribute) {
            w.print("return \"");
            w.print(codeBaseAttribute.value());
            w.println("\";");
        } else {
            w.println("return null;");
        }
        w.outdent();
        w.println();
        w.println("}");
        w.outdent();


        Params paramAttribute = serviceIntf.getAnnotation(Params.class);

        w.println();
        w.indent();
        w.println("public java.util.HashMap<String, String> getParameters() {");
        w.indent();
        w.println("java.util.HashMap<String, String> props = new java.util.HashMap<String, String>();");

        if (null != paramAttribute) {

            String[] names = paramAttribute.names();
            String[] values = paramAttribute.values();

            for (int i = 0; i < names.length; i++) {
                w.print("props.put(\"");
                w.print(names[i]);
                w.print("\", \"");
                w.print(values[i]);
                w.println("\");");
            }
        }

        w.print("return props;");
        w.outdent();
        w.println();
        w.println("}");
        w.outdent();

    }

    @Override
    protected Class<? extends RemoteServiceProxy> getProxySupertype() {
        return RemoteAppletProxy.class;
    }

    private String getJarName(GeneratorContext context) {
        try {
            ConfigurationProperty prop = context.getPropertyOracle().getConfigurationProperty("jarlinker.name");
            if (prop.getValues().isEmpty()) {
                return null;
            }
            return prop.getValues().get(0);
        } catch (BadPropertyValueException ex) {
            return null;
        }

    }
}
