package org.jdesktop.lg3d.apps.bgmanager;

import java.util.ArrayList;
import javax.vecmath.Vector3f;

/**
 * @author Radek Kierner
 */
public class MouseTransContainer implements BgSorting{
    
    /** Creates a new instance of MauseTransContainer */
    public MouseTransContainer() {
    }

    public void showBg(BgTypes type) {
    }

    public Vector3f getViewMenuTranslation() {
        return new Vector3f(0.0f,0.0f,0.0f);
    }

    public Vector3f getSortingMenuTranslation() {
        return new Vector3f(0.0f,0.0f,0.0f);
    }

    public Vector3f getCloseButtonTranslation() {
        return new Vector3f(0.0f,0.0f,0.0f);
    }

    public void setBgCompList(ArrayList<BgLgComponent> BgList) {
    }

    public void changeEnableContainer(boolean enable) {
    }
    
}
