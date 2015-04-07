## Download ##

Download the [GwtAI-Client.jar](http://code.google.com/p/gwtai/source/browse/trunk/gwtai/www/com.google.gwt.gwtai.demo.GwtAI/GwtAI-Client.jar) and [GwtAI-Core.jar](http://code.google.com/p/gwtai/source/browse/trunk/gwtai/www/com.google.gwt.gwtai.demo.GwtAI/GwtAI-Core.jar) files and place them into your project's classpath.

## Tell the GWT compiler that your project depends on GwtAI ##

Even though the GwtAI classes are in the classpath and your IDE can access them, the GWT compiler does not yet know about the dependency. The GWT compiler needs access to the source code of everything that shall be translated to JavaScript, so you have to add the GwtAI module to your GWT application file. This is done by adding the line:

```
<inherits name='com.google.gwt.gwtai.applet.AppletIntegration' />
```

## Create applet interface ##

GwtAI uses the [deferred command mechanism](http://code.google.com/docreader/#p=google-web-toolkit-doc-1-5&s=google-web-toolkit-doc-1-5&t=DevGuideDeferredBinding) to get a handle to the Applet object. Thus working with GwtAI is in some way similar to [GWT's RPC](http://code.google.com/docreader/#p=google-web-toolkit-doc-1-5&s=google-web-toolkit-doc-1-5&t=DevGuideRemoteProcedureCalls) handling. First of all you have to create an interface which defines the method on the Applet class that shall be available to the GWT code.

```
import com.google.gwt.gwtai.applet.client.*;

@ImplementingClass(com.google.gwt.gwtai.demo.impl.CounterAppletImpl.class)
@Height("60")
@Width("350")
@Archive("GwtAI-Client.jar,GwtAI-Demo.jar")
public interface CounterApplet extends Applet {
                
        public void increment();
        
        public void decrement();
        
        public Object getCurrentValue();

}
```

Note: The interface has to be within your client-side code (in the `client` package). The interface will be compiled to JavaScript, so keep [GWT’s rules](http://code.google.com/docreader/#p=google-web-toolkit-doc-1-5&s=google-web-toolkit-doc-1-5&t=DevGuideJavaCompatibility) for _runtime library support_ in mind.

## Implement applet ##

The next step is to implement the actual Applet class. The Applet class can not reside in the client-side package; you have to put the applet class into a package of its own.

```
import java.awt.Color;

import javax.swing.*;

import com.google.gwt.gwtai.demo.client.CounterApplet;

public class CounterAppletImpl extends JApplet implements CounterApplet {
        private JTextField _tfCounter;

        public void init() {
                JPanel panelMain = new JPanel();
                
                _tfCounter = new JTextField(20);
                _tfCounter.setHorizontalAlignment(JTextField.CENTER);
                _tfCounter.setText("0");
                _tfCounter.setEditable(false);
        
                panelMain.add(new JLabel("Current count : "));
                panelMain.add(_tfCounter);
                
                panelMain.setBorder(BorderFactory.createTitledBorder("CounterApplet"));
                panelMain.setBackground(Color.WHITE);
                
                getContentPane().add(panelMain);
        }
        
        public void increment() {
                int currentCount = Integer.parseInt(_tfCounter.getText());
                currentCount++;
                
                _tfCounter.setText(currentCount + "");
        }
        
        public void decrement() {
                int currentCount = Integer.parseInt(_tfCounter.getText());
                currentCount--;
                
                _tfCounter.setText(currentCount + "");
        }
        
        public Object getCurrentValue() {
                return _tfCounter.getText();
        }

}
```

## Use generated applet proxy in your GWT code ##

Now obtain a handle to the Applet using `GWT.create(...)`. The last step in integration an Applet is to construct a widget object with the help of the `AppletJSUtil` class and to add this very object to an UI container.


```
VerticalPanel panelMain = new VerticalPanel();

Button buttonInc = new Button("Increment");

final CounterApplet counterApplet = (CounterApplet) GWT.create(CounterApplet.class);
                
buttonInc.addClickListener(new ClickListener() {
  public void onClick(Widget sender) {
    counterApplet.increment();
  }
});

Widget widgetApplet = AppletJSUtil.createAppletWidget(counterApplet);

panelMain.add(widgetApplet);
panelMain.add(buttonInc);
```

## Deploy the application ##

The applet is started by the browser, thus runs in a JVM on the client machine. In order to load and start the applet the browser needs to have access to the applet classes and resources. There are two annotations in GwtAI to control from where and which resources are loaded by the browser.

### @Archive ###

The `Archive` annotation tells the browser what resources to load. It takes a comma-separated list of archived files (usually Jar files).

### @Codebase ###

The `Codebase` specifies the location from where the applet code and resources are loaded. The `Codebase` annotation is optional, if not specified the module root URL is used.

Given the above Applet interface using the following an `Archive` annotation (Note that there is no Codebase annotation specified):
```
@Archive("GwtAI-Client.jar,GwtAI-Demo.jar")
```

The structure of the war file has to be like this:
  * gwt
    * standard
    * ..
  * WEB-INF
    * web.xml
  * GwtAI.html
  * GwtAI.css
  * GwtAI-Client.jar
  * GwtAI-Demo.jar
  * ...