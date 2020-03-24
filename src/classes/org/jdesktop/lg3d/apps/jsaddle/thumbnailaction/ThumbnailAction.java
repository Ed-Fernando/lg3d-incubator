/*
 * ThumbnailAction.java
 *
 * Created on 2007/02/10, 22:33
 *
 */

package org.jdesktop.lg3d.apps.jsaddle.thumbnailaction;

import java.util.Vector;
import org.jdesktop.lg3d.apps.jsaddle.*;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public abstract class ThumbnailAction {

    protected Vector<Component3D> panelList = new Vector<Component3D>();
    
    protected Component3D component;
    
    /** Creates a new instance of ThumbnailAction */
    public ThumbnailAction(Component3D c, Vector<Component3D> p) {
        component = c;
        panelList = p;
    }
    
    public abstract void initLocation();

    public abstract boolean setLocation(int value, int move);
}
