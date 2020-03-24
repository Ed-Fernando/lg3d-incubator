/*
 * TilleContainer.java
 *
 */

package org.jdesktop.lg3d.apps.bgmanager;

import java.util.ArrayList;
import java.util.prefs.Preferences;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.apps.bgmanager.layouts.TilleLayout;
import org.jdesktop.lg3d.scenemanager.utils.appcontainer.NaturalMotionF3DAnimationFactory;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 *
 * @author root
 */
public class TilleContainer extends Container3D implements BgSorting {
    private TilleLayout tilledLayout;
    private Preferences prefs;
    private Container3D compContainer;
    private BgTypes bgtype;
    private ArrayList <BgLgComponent>bgCompList;
    private BgLgComponent tmpBg;
    /** Creates a new instance of TilleContainer */
    
    public TilleContainer(ArrayList bgCompList , Preferences prefs) {
        this.bgCompList = bgCompList;
        this.prefs = prefs;
        compContainer = new Container3D();
        tilledLayout = new TilleLayout(0.1f,0.13f,8,8,new Vector3f(0.03f,0.02f,0.0f),
                new NaturalMotionF3DAnimationFactory(150));
        compContainer.setLayout(tilledLayout);
        this.addChild(compContainer);
    }
    
    public void showBg(BgTypes type) {
        bgtype = type;
        if(compContainer.numChildren() != 0) {
            compContainer.removeAllChildren();
        } else if (this == null)  {
            compContainer.setLayout(tilledLayout);
        }
        
        for (int i=0;i<bgCompList.size();i++) {
            tmpBg = bgCompList.get(i);
            tmpBg.init();
            if(tmpBg.getBgTpe() == bgtype || bgtype == BgTypes.ALLBG) {
                
                tmpBg.addListener(
                        new MouseClickedEventAdapter(
                        new ActionNoArg() {
                    public void performAction(LgEventSource source) {
                   //     rearaangeBg((BgLgComponent)source);
                    }
                }));
                tmpBg.addListener(
                        new MouseClickedEventAdapter(true,
                        new ActionNoArg() {
                    public void performAction(LgEventSource source) {
                        BgLgComponent initialBg =(BgLgComponent)source;
                        initialBg.setBackground();
                        prefs.put("initialBackgroundConfigFileURL",initialBg.getConfigURL());
                    }
                }));
                tmpBg.setMouseEventPropagatable(true);
                compContainer.addChild(tmpBg);
                compContainer.revalidate();
                
            }
        }
    }
    
    public Vector3f getViewMenuTranslation() {
        return new Vector3f(0.05f,0.0f,0.0f);
    }
    
    public Vector3f getSortingMenuTranslation() {
        return new Vector3f(0.03f,0.0f,0.0f);
    }
    
    public Vector3f getCloseButtonTranslation() {
        return new Vector3f(0.0f,0.0f,0.0f);
    }
    
    public void changeEnableContainer(boolean enable) { //here remove listeners
        
    }
     
    public void setBgCompList(ArrayList<BgLgComponent> BgList) {
        this.bgCompList=BgList;
}
}