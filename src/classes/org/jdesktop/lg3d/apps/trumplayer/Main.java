/*
 * Main.java
 *
 * Created on 2005/10/23, 22:44
 *
 */

package org.jdesktop.lg3d.apps.trumplayer;

import java.io.File;

import org.jdesktop.lg3d.apps.trumplayer.utils.mp3pluginWarningFrame;
import org.jdesktop.lg3d.apps.trumplayer.utils.SetupSwingFrame;
import org.jdesktop.lg3d.apps.trumplayer.utils.TrumplayerDefaults;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        /* ******
         * trumplayer move from JMF to JLayer,
         * so this function does not needs.
        MP3PluginChecker checker = MP3PluginChecker.getChecker();
        if( checker.exist() == false )
            openMP3PluginWarningWindow();
        else startup();
         ***** */
        startup();
    }

    public static void startup(){
        // if trumplayer cannot found trumplayer.xml,
        // run auto search dialog.
        if( checkConfig() )
            runTrumplayer();
        else runConfigurator();
    }

    // run trumplayer
    public static void runTrumplayer(){
        new Frame();
    }

    // configurator : make trumplayer.xml and playlist
    public static void runConfigurator(){
        // need to add configurator module
        // configurator JFrame have to run runTrumplayer() after configuration. 
        javax.swing.JFrame f = new SetupSwingFrame();
        f.setLocation(300,300);
        f.setVisible(true);
        f.setEnabled(true);
    }

    // check whether trumplayer.xml exists or not
    public static boolean checkConfig(){
        // check whether trumplayer.xml exists or not
        File file = new File(TrumplayerDefaults.TRUMPLAYER_CONFIG_PATH);

        return file.exists();
    }
    
    public static void openMP3PluginWarningWindow(){
        
        javax.swing.JFrame f = new mp3pluginWarningFrame();
        f.setLocation(300,300);
        f.setVisible(true);
        f.setEnabled(true);
        
    }
    

    
}
