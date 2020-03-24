/*
 * SmallToolKit.java
 *
 * Created on 2007/03/11, 20:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.lg3d.apps.smalltoolkit.sample;

import org.jdesktop.lg3d.utils.smalltoolkit.buttons.BoxButtonComponent3D;
import org.jdesktop.lg3d.utils.smalltoolkit.buttons.ConeButtonComponent3D;
import org.jdesktop.lg3d.utils.smalltoolkit.buttons.CylinderButtonComponent3D;
import org.jdesktop.lg3d.utils.smalltoolkit.buttons.ExitButtonComponent3D;
import org.jdesktop.lg3d.utils.smalltoolkit.buttons.MinimizeButtonComponent3D;

import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Component3D;

import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.ColorCube;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;

import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.utils.action.ActionBoolean;

/**
 *
 * @author Yasuhiro Fujitsuki (thaniwa)
 */
public class SmallToolKit {

    Component3D component;
    
    /** Creates a new instance of SimpleToolKit */
    public SmallToolKit() {
        Frame3D frame = new Frame3D();
        
        ColorCube cube = new ColorCube(0.02f);
        component = new Component3D();
        component.setAnimation(new NaturalMotionAnimation(1000));
        component.addChild(cube);
        frame.addChild(component);
        
        ClassLoader loader = this.getClass().getClassLoader();
                
        try{
            // Cylinder Button
            CylinderButtonComponent3D cylinder = new CylinderButtonComponent3D(loader.getResource("org/jdesktop/lg3d/apps/smalltoolkit/sample/resources/button_a.png"));
            cylinder.setTranslation(0.015f,-0.05f,0.0f);
            cylinder.setPressedAction(new ActionBoolean(){
                public void performAction(LgEventSource source, boolean state){
                    component.setRotationAxis(1.0f,0.0f,0.0f);
                    if(state)
                        component.changeRotationAngle((float)Math.PI*2);
                    else
                        component.changeRotationAngle(0.0f);                        
                }
            });
            frame.addChild(cylinder);

            // Box Button
            BoxButtonComponent3D box = new BoxButtonComponent3D(loader.getResource("org/jdesktop/lg3d/apps/smalltoolkit/sample/resources/button_b.png"));
            box.setTranslation(0.030f,-0.05f,0.0f);
            box.setPressedAction(new ActionBoolean(){
                public void performAction(LgEventSource source, boolean state){
                    component.setRotationAxis(0.0f,1.0f,0.0f);
                    if(state)
                        component.changeRotationAngle((float)Math.PI*2);
                    else
                        component.changeRotationAngle(0.0f);                        
                }
            });
            frame.addChild(box);
        } catch(Exception e){
            e.printStackTrace();
        }

        // Up Button
        ConeButtonComponent3D coneUp = new ConeButtonComponent3D(0.005f, 0.01f, new SimpleAppearance(1.0f,0.6f,1.0f,0.95f), ConeButtonComponent3D.Direction.UP);
        coneUp.setTranslation(-0.015f,-0.040f,0.0f);
        coneUp.setPressedAction(new ActionBoolean(){
            public void performAction(LgEventSource source, boolean state){
                if(state)
                    component.changeTranslation(0.0f,0.1f,0.0f);
                else
                    component.changeTranslation(0.0f,0.0f,0.0f);
            }
        });
        frame.addChild(coneUp);

        // Down Button
        ConeButtonComponent3D coneDown = new ConeButtonComponent3D(0.005f, 0.01f, new SimpleAppearance(0.6f,1.0f,1.0f,0.95f),ConeButtonComponent3D.Direction.DOWN);
        coneDown.setTranslation(-0.015f,-0.060f,0.0f);
        coneDown.setPressedAction(new ActionBoolean(){
            public void performAction(LgEventSource source, boolean state){
                if(state)
                    component.changeTranslation(0.0f,-0.1f,0.0f);
                else
                    component.changeTranslation(0.0f,0.0f,0.0f);
            }
        });
        frame.addChild(coneDown);

        // Right Button
        ConeButtonComponent3D coneRight = new ConeButtonComponent3D(0.005f, 0.01f, new SimpleAppearance(0.6f,0.6f,1.0f,0.95f),ConeButtonComponent3D.Direction.RIGHT);
        coneRight.setTranslation(-0.005f,-0.050f,0.0f);
        coneRight.setPressedAction(new ActionBoolean(){
            public void performAction(LgEventSource source, boolean state){
                if(state)
                    component.changeTranslation(0.1f,0.0f,0.0f);
                else
                    component.changeTranslation(0.0f,0.0f,0.0f);
            }
        });
        frame.addChild(coneRight);

        // Left Button
        ConeButtonComponent3D coneLeft = new ConeButtonComponent3D(0.005f, 0.01f, new SimpleAppearance(1.0f,1.0f,0.6f,0.95f),ConeButtonComponent3D.Direction.LEFT);
        coneLeft.setTranslation(-0.025f,-0.050f,0.0f);
        coneLeft.setPressedAction(new ActionBoolean(){
            public void performAction(LgEventSource source, boolean state){
                if(state)
                    component.changeTranslation(-0.1f,0.0f,0.0f);
                else
                    component.changeTranslation(0.0f,0.0f,0.0f);
            }
        });
        frame.addChild(coneLeft);

        // exit button
        Component3D exit = new ExitButtonComponent3D(frame);
        exit.setTranslation(0.041f,0.03f,0.0f);
        frame.addChild(exit);

        // exit button
        Component3D minimize = new MinimizeButtonComponent3D(frame);
        minimize.setTranslation(0.030f,0.03f,0.0f);
        frame.addChild(minimize);
        
        frame.setEnabled(true);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new SmallToolKit();
    }
    
}
