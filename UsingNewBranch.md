# Introduction #

The new branch introduces some new stuff:
  * Maven build process
  * GWT-RPC communication with applet allowing full object graph.

But because of the new build process you might want some insight into using it. Read on! :)

# Prerequisites #

Your development environment needs to support maven and preferably have some GWT support to make your life easier.

  * Netbeans: Supports maven but you wanna [install GWT4NB](http://netbeans.org/kb/docs/web/quickstart-webapps-gwt.html).
  * Eclipse: You need to [install m2clipse](http://m2eclipse.sonatype.org/installing-m2eclipse.html) for maven support and the [Google Eclipse Plugin](http://code.google.com/intl/da/eclipse/docs/getting_started.html) for som GWT support.

# Opening the project #
When you have [checked out the code](http://code.google.com/p/gwtai/source/checkout) you open the outer/parent project 'gwtai'. The subfolders, gwtaicore, gwtaiclient and gwtaidemo are all subproject to the parent project which you can open as modules inside you IDE.


# TO BE CONTINUED #