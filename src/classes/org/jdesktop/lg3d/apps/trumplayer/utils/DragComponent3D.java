/*
 * DragComponent3D.java
 * Original : org/jdesktop/lg3d/apps/trumplayer/base/AlbumBase.java
 * DragComponent3D is for create a Drag And Drop Component.
 *
 * Created on 2005/10/23, 22:53
 *
 */

package org.jdesktop.lg3d.apps.trumplayer.utils;

import javax.vecmath.Vector3f;
import javax.vecmath.Color3f;

import org.jdesktop.lg3d.apps.trumplayer.base.Manager;
import org.jdesktop.lg3d.apps.trumplayer.base.MouseReleasedEventAdapter;

import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;

import org.jdesktop.lg3d.utils.action.ActionFloat2;
import org.jdesktop.lg3d.utils.action.ActionBooleanFloat3;
import org.jdesktop.lg3d.utils.eventadapter.MouseDraggedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.EventAdapter;


/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class DragComponent3D extends Container3D {

    // current position
    protected Vector3f translation;

    /**
     * a flag wheter Album Component can execute Drag and Drop Action
     */
    protected boolean dragAndDrop = false;
    private boolean dragged = false;
    
    /**
     * start mouse position (when drag action started)
     *
     */
    protected float mouseX,mouseY;

    /**
     * Creates a new instance of AlbumBase 
     */
    public DragComponent3D(){
        init();
    }

    public DragComponent3D(Manager manager){
        this.manager = manager;
        init();
    }

    protected Manager manager = null;

    public void setManager(Manager manager){
        this.manager = manager;
    }
    
    public Manager getManager(){
        return manager;
    }

    /**
     * initialize AlbumBase Component
     * add Drag and Drop event automatically
     *
     */
    public void init(){


        // move item 
        MouseDraggedEventAdapter draggedAdapter =
                new MouseDraggedEventAdapter(ButtonId.BUTTON1, new DraggedAction(this));
        addListener(draggedAdapter);

        new MousePressedAction(this);
        MousePressedEventAdapter pressedAdapter =
                new MousePressedEventAdapter(ButtonId.BUTTON1, new MousePressedAction(this));
        addListener(pressedAdapter);

        // setMouseEventPropagatable(true);
        setMouseEventPropagatable(false);
        
        // default translation
        setDefaultTranslation(new Vector3f(0.0f,0.0f,0.0f));
        
    }

    /** 
     * set default position(translation) of Album Component 
     */
    public void setDefaultTranslation(Vector3f translation){
        this.translation = translation;
    }

    /** 
     * set default position(translation) of Album Component 
     */
    public void setDefaultTranslation(float x,float y, float z){
        setDefaultTranslation(new Vector3f(x,y,z));
    }

    /** 
     * get default position(translation) of Album Component 
     */
    public Vector3f getDefaultTranslation(){
        return translation;
    }
    
    /** 
     * move album component to default position(translation) 
     */
    public void changeDefaultTranslation(){
        this.changeTranslation(translation);
    }

    public void setDragAndDrop(boolean dragAndDrop){
        this.dragAndDrop = dragAndDrop;
    }
    
    public boolean enableDragAndDrop(){
        return dragAndDrop;
    }

    public void doDraggedAction(Component3D component,float x,float y){
        // move action
        Vector3f loc = new Vector3f();
        Vector3f bLoc = getManager().getFrameLocation();
        Vector3f sLoc = getManager().getShelfLocation();
        component.getFinalTranslation(loc);

        float scale = 1.0f / manager.getFrame3D().getFinalScale();
        component.setTranslation( (x-bLoc.x-sLoc.x-mouseX) * scale , (y-bLoc.y-sLoc.y-mouseY) * scale ,loc.z);
    }

    private class MousePressedAction implements ActionBooleanFloat3 {
        Component3D component;
        
        public MousePressedAction(Component3D c){
            component = c;
        }
        
        public void performAction(LgEventSource source, boolean b, float f1, float f2, float f3) {
            if(b == true){
                Vector3f loc = new Vector3f();
                component.getFinalTranslation(loc);
                mouseX = f1;
                mouseY = f2;
            }
        }
        
    }

    private class DraggedAction implements ActionFloat2 {
        Component3D component;
        
        /** Creates a new instance of RotateAction */
        public DraggedAction(Component3D c) {
            component = c;
        }
        
        public void performAction(LgEventSource source, float f1, float f2) {
            if( enableDragAndDrop() ){
                // mouseX = f1;
                // mouseY = f2;
                dragged = true;
                doDraggedAction(component,f1,f2);
            }
        }
    }


}
