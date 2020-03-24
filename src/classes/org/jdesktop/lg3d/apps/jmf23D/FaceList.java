package org.jdesktop.lg3d.apps.jmf23D;

import javax.vecmath.*;

import org.jdesktop.lg3d.sg.*;
import org.jdesktop.lg3d.utils.c3danimation.*;
import org.jdesktop.lg3d.utils.eventadapter.*;
import org.jdesktop.lg3d.utils.shape.*;
import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.wg.event.*;


/**
* Container that arranges video in 3D.
**/
public class FaceList extends Container3D {

    PolyhedronLayout pl;
    Foreground foreground;

    public FaceList() {
        pl = new PolyhedronLayout();
        foreground = new Foreground();
        setLayout(pl);
        setAnimation(new NaturalMotionAnimation(500));
    }

    public void addAppearance(Appearance app) {
        addAppearance(app, 16f / 9f);
    }

    public void addAppearance(Appearance app, float aspectRatio) {

        Component3D top = new Component3D();
        top.setAnimation(new NaturalMotionAnimation(500));

        Box box = new Box(0.045f / aspectRatio, 0.045f, 0.0f, 
                          Box.GENERATE_TEXTURE_COORDS, app);
        top.addChild(box);
        addChild(top);
        top.setMouseEventPropagatable(true);
        top.addListener(new MouseClickedEventAdapter(Boolean.TRUE, 
                                                     new org.jdesktop.lg3d.utils.action.ActionFloat2() {
            public void performAction(LgEventSource source, float x, float y) {
		    Renderer3DControl r3d = Renderer3DControl.controls.get(indexOfChild((Component3D)source));
		    r3d.foreground.setAppearance(r3d.renderer3D.getAppearance());
		    r3d.foreground.changeVisible(true);
            }
        }));
	top.addListener(new MouseClickedEventAdapter(MouseEvent3D.ButtonId.BUTTON3, 
                                                     new org.jdesktop.lg3d.utils.action.ActionFloat2() {
            public void performAction(LgEventSource source, float x, float y) {
		    Renderer3DControl r3d = Renderer3DControl.controls.get(indexOfChild((Component3D)source));
		    r3d.close();
            }
        }));
    }

    /**
    * Reorders the videos so that the previous one comes in front.
    **/
    public void previous(int n) {
        pl.layoutNow = false;

        int numComponents = numChildren();

        while (--n >= 0) {

            Node nextChild = getChild(0);

            for (int i = 0; i < numComponents; i++) {

                int nextIndex = (i + 1) % numComponents;
                Node temp = getChild(nextIndex);
                removeChild(nextChild);
                insertChild(nextChild, nextIndex);
                nextChild = temp;
            }
        }

        pl.layoutNow = true;
        pl.layoutContainer();
    }

    /**
    * Reorders the videos so that the next one comes in front.
    **/
    public void next(int n) {
        pl.layoutNow = false;

        int numComponents = numChildren();

        while (--n >= 0) {
		
	    Node child0 = getChild(0);
	    removeChild(0);
	    addChild(child0);
        }

        pl.layoutNow = true;
        pl.layoutContainer();
    }

    /**
    * If rolled, will arrange each video to form a n-gon, where n is the number of video playing at the moment.
    **/
    public void setRolled(boolean rolled) {
        pl.rolled = rolled;
        pl.layoutContainer();
    }

    /**
    * Returns true if the container is rolled, i.e. all frames are rolled into an polygon.
    **/
    public boolean getRolled() {

        return pl.rolled;
    }
}

class PolyhedronLayout implements LayoutManager3D {

    float insets = 0.005f;
    float length = 0.08f;
    boolean rolled = true;
    boolean layoutNow = true;
    Vector3f rotationAxis = new Vector3f(0.0f, 1.0f, 0.0f);
    Container3D root;

    public final int faces() {

        return root.numChildren();
    }

    public void addLayoutComponent(Component3D comp, Object constraints) {

        if (layoutNow)
            layoutContainer();
    }

    public void layoutContainer() {

        float angle = angle(faces());
        Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);
        float spacing = length + insets;
        float scale = 1.0f;

        if (!rolled) {
            position = new Vector3f(-spacing * (faces() / 2.0f), 0.0f, 0.0f);
            spacing = length;
            scale = 1f / faces();
        }

        root.changeScale(scale);
	
        for (int i=0; i<faces(); i++) {
	    int j = (i);
	    while(j<0)
		    j++;
            Component3D comp = (Component3D)root.getChild(j);
            comp.changeTranslation(position, 500);
            comp.changeRotationAxis(rotationAxis, 500);
            comp.changeRotationAngle(i * angle, 500);

            Vector3f trans = new Vector3f(
                                     (float)(spacing * Math.cos(i * angle)), 
                                     0.0f, 
                                     (float)(-spacing * Math.sin(i * angle)));
            Vector3f trans2 = new Vector3f(
                                      (float)(spacing * Math.cos((i + 1) * angle)), 
                                      0.0f, 
                                      (float)(-spacing * Math.sin((i + 1) * angle)));
            position.add(trans);
            position.add(trans2);
        }
    }

    public boolean rearrangeLayoutComponent(Component3D comp, 
                                            Object newConstraints) {

        return false;
    }

    public void removeLayoutComponent(Component3D comp) {

        if (layoutNow)
            layoutContainer();
    }

    public void setContainer(Container3D cont) {
        root = cont;
        layoutContainer();
    }

    public float angle(int faces) {

        float angle = (float)(2.0 * Math.PI / faces);

        if (!rolled)
            angle = 0;

	if(faces==2&rolled)
		return (float)(3.0*Math.PI/4.0);
        return angle;
    }
}
