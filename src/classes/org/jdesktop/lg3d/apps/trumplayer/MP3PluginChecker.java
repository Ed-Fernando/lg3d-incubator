/*
 * MP3PluginChecker.java
 *
 * Created on 2006/08/07, 14:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.lg3d.apps.trumplayer;

/**
 *
 * @author yasuhiro
 */
public class MP3PluginChecker {
    
    private static MP3PluginChecker checker;
    /** Creates a new instance of MP3PluginChecker */
    private MP3PluginChecker() {
    }
    
    public static MP3PluginChecker getChecker(){
        if(checker == null)
            checker = new MP3PluginChecker();
        return checker;
    }
    public boolean exist(){
        return mp3pluginCheck();
    }
        
    private boolean mp3pluginCheck(){
        boolean result = false;
        try {
            ClassLoader loader = this.getClass().getClassLoader();
            loader.loadClass("com.sun.media.codec.audio.mp3.JS_MP3FileReader");
            // System.err.println("MP3Plugin Found!!");
            result = true;
        } catch (Exception e){
            // e.printStackTrace();
            // System.err.println("No MP3Plugin Found!!");
            result = false;
        }
        return result;
    }
    
}
