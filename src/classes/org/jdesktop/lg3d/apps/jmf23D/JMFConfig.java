package org.jdesktop.lg3d.apps.jmf23D;
import java.io.*;
import java.util.*;
 
/**
* @deprecated JMFConfig.xml file is jar'd with other classes and can't be updated.
* An xml-serialized class that specifies a default media URI to playback.
**/
public class JMFConfig implements Serializable {

   String mediaLocation = "http://images.apple.com/movies/rogue_pictures/unleashed/unleashed-broken_m320.mov";
 
   public String getMediaLocation(){
	   return mediaLocation;
   }
   
   public void setMediaLocation(String mediaLocation){
	   this.mediaLocation = mediaLocation;
   }
   }
