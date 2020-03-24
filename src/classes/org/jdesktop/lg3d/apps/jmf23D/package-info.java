/**
* Provides an API to playback multimedia, mainly video, under
* the Project Looking Glass 3D environment.
* The provided extensions to JMF are: SVGParser, HTMLParser org.jdesktop.lg3d.apps.jmf23D.media.protocol.alg3d.DataSource/DataSink
* Those classes should work seemlessly with normal JMF apps. However they need first to be registered as follows:<p>
* <code>
* Algea3D.registerAll();
* </code>
* <p>
* Otherwise using, the jmf.properties file provided should work.<br>
* //TODO: update jmf.properties<br>
* This package also specifies a new protocol called alg3d, which is based on JXTA and allows for P2P audio/video conferences. 
* A <a href="http://java.sun.com/products/java-media/jmf/2.1.1/apidocs/javax/media/MediaLocator.html">MediaLocator</a>
* for this protocol uses the following syntax:<br>
* <code> alg3d://myGroupName </code>
* where: -myGroupName is the name of the group to join. When multiple users connect to a group, they will start a videoconference.
* <br>
* <b>NOTE: This package requires JMF to be installed in order to work properly. In addition Fobs available at fobs.sourceforge.net will be needed
* to playback most modern media files.
* These can be downloaded freely at :
* http://java.sun.com/products/java-media/jmf/2.1.1/download.html<br>
* http://fobs.sourceforge.net</b>
*@see <a href="http://java.sun.com/products/java-media/jmf/2.1.1/apidocs/overview-summary.html">javax.media</a>
*@see <a href="http://javadesktop.org/lg3d/javadoc/0-7-0-latest/api/overview-summary.html">org.jdesktop.lg3d</a>
**/
package org.jdesktop.lg3d.apps.jmf23D;

