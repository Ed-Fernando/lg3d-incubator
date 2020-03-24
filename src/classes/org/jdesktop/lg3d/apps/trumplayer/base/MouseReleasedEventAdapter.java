/*
 * MouseReleasedEventAdapter.java
 *
 * Created on 2005/11/06, 13:44
 *
 */

package org.jdesktop.lg3d.apps.trumplayer.base;

import org.jdesktop.lg3d.utils.eventadapter.EventAdapter;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.LgEvent;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class MouseReleasedEventAdapter implements EventAdapter {
    
    private ActionBoolean action;
    
    /** Creates a new instance of MouseReleasedEventAdapter */
    public MouseReleasedEventAdapter(ActionBoolean action) {
        this.action = action;
    }
    
    public Class[] getTargetEventClasses(){
        Class[] targetEventClasses = { MouseButtonEvent3D.class };
        return targetEventClasses;
    }
    
    public void processEvent(LgEvent event){
        if(event instanceof MouseButtonEvent3D){
            MouseButtonEvent3D mouseEvent = (MouseButtonEvent3D)event;
            if( mouseEvent.getButton() == MouseButtonEvent3D.ButtonId.BUTTON1 )
                action.performAction(event.getSource(), mouseEvent.isReleased());
            else
                action.performAction(event.getSource(), false);
        }
    }
    
}
