/*
 * BgSorting.java
 *
 * Created on July 5, 2006, 3:40 PM
 *
 */

package org.jdesktop.lg3d.apps.bgmanager;

import java.util.ArrayList;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.wg.Container3D;

/**
 *
 * @author Radek Kierner
 */
public interface BgSorting {
    public void showBg(BgTypes type);
    public Vector3f getViewMenuTranslation();
    public Vector3f getSortingMenuTranslation();
    public Vector3f getCloseButtonTranslation();
    public void setBgCompList( ArrayList <BgLgComponent> BgList);
    public void changeEnableContainer(boolean enable);
}
