/*
 * PressedAction.java
 *
 * Created on 2006/06/15, 11:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.lg3d.apps.trumplayer;

import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import javax.vecmath.Vector3f;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class PressedAction implements ActionBoolean {
    protected Component3D component;
    //boolean pressed = false;
    float z = 0.025f;

    public Component3D getComponent3D(){
        return component;
    }
    
    public PressedAction(Component3D c){
        component = c;
    }
    
    public PressedAction(Component3D c,float z){
        component = c;
        this.z = z;
    }
        
    public void performAction(LgEventSource source, boolean b){
        Vector3f loc = new Vector3f();
        component.getFinalTranslation(loc);
        if(b){
            component.changeTranslation(loc.x,loc.y,loc.z-z, 200);
        } else {
            component.changeTranslation(loc.x,loc.y,loc.z+z, 200);
        }
    }
        
}
