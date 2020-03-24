package org.jdesktop.lg3d.apps.bgmanager;

import java.util.prefs.Preferences;
import org.jdesktop.lg3d.apps.bgmanager.configReaders.BgConfigFileReader;
import org.jdesktop.lg3d.apps.bgmanager.configReaders.BgFileReader;
import org.jdesktop.lg3d.scenemanager.utils.background.Background;
import org.jdesktop.lg3d.utils.prefs.LgPreferencesHelper;

public class BgManager {
    static  String CONFIG_FILE_PATH = "resources/Backgrounds/BgConfig.xml";
    private  Preferences prefs ;
    static  String initialbg;
    
    public static void main (String[] args) {
	BgManager bg = new BgManager (true);
    }
    public BgManager (boolean isAppRunning){
	
	if(!isAppRunning){
	    prefs = LgPreferencesHelper.userNodeForPackage (getClass ());
	    initialbg = prefs.get ("initialBackgroundConfigFileURL" , "resources/Backgrounds/Stanford/LgBgConf.lgbg");
	} else if (isAppRunning){
	    BgConfigFileReader configReader = new BgConfigFileReader (CONFIG_FILE_PATH);
	    BgFileReader bgReader = new BgFileReader (configReader.getBgFiles (),configReader.getNumBg ());
	    BgFrame frame = new BgFrame (bgReader.getComponentBgList ());
	}
    }
    static Background getInitialBg () {
	BgManager bg = new BgManager (false);
	BgLgComponent initialBg = null;
        BgFileReader bgReader = new BgFileReader (initialbg);
	initialBg = bgReader.getInitialBgComp ();
	
	return initialBg.getBackground ();
    }
}
