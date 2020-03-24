package org.jdesktop.lg3d.apps.bgmanager;

import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.scenemanager.utils.SceneControl;
import org.jdesktop.lg3d.scenemanager.utils.plugin.SceneManagerPlugin;
import org.jdesktop.lg3d.scenemanager.utils.background.Background;
import org.jdesktop.lg3d.scenemanager.utils.taskbar.TaskbarItemConfig;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.action.AppLaunchAction;
import org.jdesktop.lg3d.utils.component.SwichablePseudo3DIcon;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Tapp;
import org.jdesktop.lg3d.wg.event.LgEventConnector;


public class BgManagerIcon implements SceneManagerPlugin {
    private SceneControl sceneControl;
    private Component3D root;
    
    public BgManagerIcon() {
    }
    
    public void initialize(SceneControl sceneControl) {
        Background initBg = BgManager.getInitialBg();
        sceneControl.setBackground(initBg);
        
//        System.out.println("Classloader "+getClass().getClassLoader());
//        System.out.println(getClass().getClassLoader().getResource("resources/images/icon/bgicon.png")+"\n"+
//                    getClass().getClassLoader().getResource("resources/images/icon/bgicon2.png"));
        
        final SwichablePseudo3DIcon icon = new SwichablePseudo3DIcon(
                    getClass().getClassLoader().getResource("resources/images/icon/bgicon.png"),
                    getClass().getClassLoader().getResource("resources/images/icon/bgicon2.png"));
        
        icon.addListener(
            new MouseClickedEventAdapter(
                new AppLaunchAction("java org.jdesktop.lg3d.apps.bgmanager.BgManager", getClass().getClassLoader())));
        
        // Post the taskbar item event
        LgEventConnector.getLgEventConnector().postEvent(
            new TaskbarItemConfig() {
                @Override
                public Tapp createItem() {
                    Tapp tapp = new Tapp();
                    tapp.addChild(icon);
                    tapp.setPreferredSize(icon.getPreferredSize(new Vector3f()));
                    return tapp;
                }
                @Override
                public int getItemIndex() {
                    return -2;
                }
            }, null);
    }
    
    public void destroy() {
        // TODO -- remove the taskbar item
    }
    
    public boolean isRemovable() {
        return true;        
    }
    
    public Component3D getPluginRoot() {
        return null;
    }
}
