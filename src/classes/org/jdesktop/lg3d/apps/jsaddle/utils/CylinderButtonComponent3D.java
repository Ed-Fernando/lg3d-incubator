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
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Cylinder;
import org.jdesktop.lg3d.utils.shape.Disc;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;

import java.net.URL;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class CylinderButtonComponent3D extends Component3D {

    private Component3D button;
    private ActionBoolean pressedActionBoolean = null;
    
    private float z_depth = 0.015f;

    /** Creates a new instance of ButtonComponent3D */
    public CylinderButtonComponent3D() {
        initialize(null,0.5f,1.0f,1.0f,1.0f);
    }
    
    public CylinderButtonComponent3D(float red, float green, float blue, float alpha){
        initialize(null,red,green,blue,alpha);
    }
    
    public CylinderButtonComponent3D(URL url){
        initialize(url,0.5f,1.0f,1.0f,1.0f);
    }

    public CylinderButtonComponent3D(URL url, float red, float green, float blue, float alpha ){
        initialize(url,red,green,blue,alpha);
    }
    
    public CylinderButtonComponent3D(Component3D c){
        addChild(c);
        setButtonEvent();
    }

    public void setDepth(float z){
        z_depth = z;
    }
    
    public void setPressedAction(ActionBoolean action){
        pressedActionBoolean = action;
    }
    
    private void initialize(URL url, float red, float green, float blue, float alpha){

        Component3D cylinderComponent = new Component3D();
        Cylinder cylinder = new Cylinder(0.005f,0.01f,new SimpleAppearance( red, green, blue, alpha));
        cylinderComponent.addChild(cylinder);
        cylinderComponent.setRotationAxis(1.0f,0.0f,0.0f);
        cylinderComponent.setRotationAngle((float)Math.PI/2.0f);

        Disc disc = null;
        if(url == null)
            disc = new Disc(0.01f,100,new SimpleAppearance( red, green, blue, alpha ) );
        else{
            try{
                disc = new Disc(0.01f,100,new SimpleAppearance( url ) );
            } catch (java.io.IOException ioe){
                ioe.printStackTrace();
            }
        }

        Component3D discComponent = new Component3D();
        discComponent.addChild(disc);
        discComponent.setTranslation(0.0f,0.0f, 0.0055f);

        addChild(discComponent);        
        addChild(cylinderComponent);

        // button event
        setButtonEvent();
    }

    // button event
    private void setButtonEvent(){
        setAnimation(new NaturalMotionAnimation(1000));
        addListener(new MousePressedEventAdapter(MouseEvent3D.ButtonId.BUTTON1,
            new PressedAction( this ) ));
        addListener(new MouseEnteredEventAdapter(new EnteredAction(this)));
    }

    // pressed event
    public class PressedAction implements ActionBoolean {
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

    public class EnteredAction implements ActionBoolean {
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
