## Summary ##

People shout for fast loading, responsive, accessible and searchable websites/web-applications. On the other hand the very same people expect a desktop-like experience with these applications. The [Google Web Toolkit (GWT) ](http://code.google.com/webtoolkit/) has gone a long way in fulfilling these requirements, but there are some things that can simply not be done in plain HTML/JavaScript. There are two way to make these things happen in a browser, either [Adobe Flash](http://www.adobe.com/products/flash/) or the [Java Applet](http://java.sun.com/applets/) technology. [GWT](http://code.google.com/webtoolkit/) development is done in Java, so [Java Applets](http://java.sun.com/applets/) seem like the natural choice.

## Explanation ##

I have been listening to some talks about [Adobe Flex](http://www.adobe.com/de/products/flex/) at the [Jazoon Java Conference](http://jazoon.com/) 2008. Those guys made a big fuss about the ability to have a web-application (Flex) running in the browser and kind of a standalone version of the application (AIR). As far as I know it is even possible  to interacting between those two applications.

At this moment I had the idea to do something similar in [GWT](http://code.google.com/webtoolkit/). Integrating and interacting with a full-blown standalone application from within a website is something that can be done in Java since a decade.

Integration of a [Java Applet](http://java.sun.com/applets/) into a website is not that much of a problem, at least not technically. Of course things like the place where the jar
files are located and all the settings that are necessary to load an Applet in the browser
have to be correct. That is not trivial but pretty much the same as in a classical website. Alas GwtAI can not help much with these things. What GwtAI can do for you is save you from writing tedious JavaScript code to finally communicate from your GWT application with the Applet. Interaction with the website the [Applet](http://java.sun.com/applets/) is running within is a bit more complicated.

The goal of GwtAI is to provide an easy to use cross-browser solution by automatically creating wrapper widgets for [Java Applets](http://java.sun.com/applets/) and mechanism to communicate between Java and JavaScript.

## Future ##

  * [Java SE6 Update 10](http://java.sun.com/javase/downloads/ea.jsp) brings a [new browser plugin](http://java.sun.com/javase/downloads/ea/6u10/plugin2/index.jsp) that allows Applets to run in a separate Thread and improves Java/JavaScript communication. This opens a new world of possibilities.
  * Running a GWT web-application as a standalone desktop application would be a great feature. Using the `JWebPane` from the upcoming [Java 7](https://jdk7.dev.java.net/), implementing some GWTtoSwing compiler or perhaps integrateing a _Universal UI API_ like the [UFace](http://code.google.com/p/uface/) project.