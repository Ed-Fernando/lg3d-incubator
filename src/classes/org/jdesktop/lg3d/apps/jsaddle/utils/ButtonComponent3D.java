/*
 * ButtonComponent3D.java
 *
 * Created on 2007/03/02, 20:23
 *
 */

package org.jdesktop.lg3d.apps.jsaddle.utils;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;

import java.net.URL;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class ButtonComponent3D extends Component3D {

    private Component3D button;
    private ActionBoolean pressedActionBoolean = null;

    private float z_depth = 0.025f;

    /** Creates a new instance of ButtonComponent3D */
    protected ButtonComponent3D(){
        setButtonEvent();
    }

    public ButtonComponent3D(Component3D c){
        addChild(c);
        setButtonEvent();
    }

    public void setDepth(float z){
        z_depth = z;
    }
    
    public void setPressedAction(ActionBoolean action){
        pressedActionBoolean = action;
    }

    // button event
    protected void setButtonEvent(){
        setAnimation(new NaturalMotionAnimation(1000));
        addListener(new MousePressedEventAdapter(MouseEvent3D.ButtonId.BUTTON1,
            new PressedAction( this ) ));
        addListener(new MouseEnteredEventAdapter(new EnteredAction(this)));
    }

    // pressed event
    protected class PressedAction implements ActionBoolean {
        protected Component3D component;
        //boolean pressed = false;
        protected int num = 0;

        public PressedAction(Component3D component){
            this.component = component;
        }

        public void performAction(LgEventSource source, boolean b){
            Vector3f loc = new Vector3f();
            component.getFinalTranslation(loc);
            if(b)
                component.changeTranslation(loc.x,loc.y,loc.z-z_depth, 200);
            else
                component.changeTranslation(loc.x,loc.y,loc.z+z_depth, 200);

            // call original action
            if(pressedActionBoolean != null)
                pressedActionBoolean.performAction(source,b);
        }
    }

    protected class EnteredAction implements ActionBoolean {
        protected Component3D component;
        protected float z = 0.0f;

        public Component3D getComponent3D(){
            return component;
        }
    
        public EnteredAction(Component3D c){
            component = c;
            Vector3f loc = new Vector3f();
            component.getFinalTranslation(loc);
            z = loc.z;
        }

        public void performAction(LgEventSource source, boolean b){
            Vector3f loc = new Vector3f();
            component.getFinalTranslation(loc);

            if(b)
                z = loc.z;
            if(!b && loc.z != z){
                component.changeTranslation(loc.x,loc.y, z, 200);
                // call original action
                if(pressedActionBoolean != null)
                    pressedActionBoolean.performAction(source,b);
            }

        }
    }

}
