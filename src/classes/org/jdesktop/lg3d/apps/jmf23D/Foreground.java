package org.jdesktop.lg3d.apps.jmf23D;

import java.awt.Color;

import javax.vecmath.*;

import org.jdesktop.lg3d.sg.*;
import org.jdesktop.lg3d.utils.action.*;
import org.jdesktop.lg3d.utils.c3danimation.*;
import org.jdesktop.lg3d.utils.eventadapter.*;
import org.jdesktop.lg3d.utils.shape.*;
import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.wg.event.*;

/**
* Allows a video to be played in fullscreen.
**/
public class Foreground extends Frame3D {

    protected Box imagePlane;
    protected Component3D comp;
    protected float aspectRatio;
    
    public Foreground(){
	    this(16f/9f);
    }
    
    public Foreground(float aspectRatio) {
	this.aspectRatio = aspectRatio;
	setTranslation(0.0f, 0.0f, 0.0f);
        comp = new Component3D();
	this.setMouseEventPropagatable(false);
        comp.setMouseEventPropagatable(false);
        comp.changeTranslation(0.0f, 0.0f, 0.0f);
        comp.setAnimation(new NaturalMotionAnimation(500));

        Group foregroundGroup = AlgeaUtilities.createRenderingNode(aspectRatio);
        AlgeaUtilities.setCapabilitiesRecursive(foregroundGroup);
        imagePlane = (Box)foregroundGroup.getChild(1);
        comp.addChild(foregroundGroup);
        addChild(comp);
        addListeners();
        setCursor(Cursor3D.NULL_CURSOR);
        setThumbnail(new Thumbnail());
        changeEnabled(true);
    }

    public void setAppearance(Appearance app) {
        AlgeaUtilities.setAppearanceRecursive(imagePlane, app);
    }

    void addListeners() {
	    
	MouseDraggedEventAdapter md = new MouseDraggedEventAdapter(new org.jdesktop.lg3d.utils.action.ActionFloat2() {
            public void performAction(LgEventSource source, float x, float y) {
            }
        });
	addListener(md);
	comp.addListener(md);
        comp.addListener(new KeyPressedEventAdapter(new org.jdesktop.lg3d.utils.action.ActionBooleanInt() {
            public void performAction(LgEventSource lg, boolean state, 
                                      int code) {

                if (code == java.awt.event.KeyEvent.VK_ESCAPE)
                    changeVisible(false);
            }
        }));
    }

    public void changeVisible(boolean visible) {

        if (visible) {
            comp.changeTransparency(0.0f);
            comp.changeTranslation(0.0f, 0.0f, 0.0f);
        } else {
            changeTransparency(1.0f);
            comp.changeTranslation(0.0f, 0.0f, -50.0f);
        }

        super.changeVisible(visible);
    }
}
