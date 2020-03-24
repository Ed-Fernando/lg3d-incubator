/*
 * LG3D Incubator Project - Zoetrope
 *
 * $RCSfile$
 *
 * Copyright (c) 2004, Zoetrope Project Team, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision$
 * $Date$
 * Author: yuichi sakuraba
 */

package org.jdesktop.lg3d.apps.zoetrope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.zoetrope.event.LayoutEvent;
import org.jdesktop.lg3d.apps.zoetrope.event.LayoutListener;
import org.jdesktop.lg3d.apps.zoetrope.event.SelectionEvent;
import org.jdesktop.lg3d.apps.zoetrope.event.SelectionListener;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.Texture2D;
import org.jdesktop.lg3d.utils.action.ActionInt;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseWheelEventAdapter;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

public class ThumbnailWheel extends Container3D {

    private static final float THUMBNAIL_SIZE = 64.0f * Zoetrope.UNIT_TRANS_FACTOR;
    private static final float FRONTROW_SIZE = 80.0f * Zoetrope.UNIT_TRANS_FACTOR;
    private static final float DECO_SIZE = 5.0f * Zoetrope.UNIT_TRANS_FACTOR;
    private static final float DEPTH = 10.0f * Zoetrope.UNIT_TRANS_FACTOR;
    private static final float EDGE_SIZE = 0.0f;

    private float minorAxis;
    private float majorAxis;

    private static int[] bandOffset = { 0, 1, 2, 3};

    private Component3D back;
    private Component3D base;
    private Container3D wheel;
    private boolean focused = false;
    private int current = 1, imagecount = 1;
    
    private Component3D selectedComponent;
    private List<SelectionListener> listeners;

    public ThumbnailWheel(float majorAxis) {
	this.majorAxis = majorAxis;
	minorAxis = majorAxis / 5.0f;

        listeners = Collections.synchronizedList(new ArrayList<SelectionListener>());

        back = new Component3D();
        Appearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.0f, 
                                                     SimpleAppearance.DISABLE_CULLING);
        Shape3D backPanel = new FuzzyEdgePanel(THUMBNAIL_SIZE, majorAxis * 2.0f, 0.0f, appearance);
        back.addChild(backPanel);
        back.changeTranslation(0.0f, 0.0f, -minorAxis);
	back.setCursor(Cursor3D.DEFAULT_CURSOR);
        super.addChild(back, null);

        base = new Component3D();
        appearance = new SimpleAppearance(1.0f, 0.6f, 0.6f, 1.0f, 
                                                     SimpleAppearance.DISABLE_CULLING);
        Shape3D panel = new GlassyPanel(THUMBNAIL_SIZE * 1.5f + DECO_SIZE * 2.0f,
                                        THUMBNAIL_SIZE * 1.5f + DECO_SIZE * 2.0f,
                                        DEPTH, DECO_SIZE, appearance);
        base.addChild(panel);
        base.changeTranslation(0.0f, 0.0f, minorAxis);
	base.setCursor(Cursor3D.DEFAULT_CURSOR);
        super.addChild(base, null);

        wheel = new Container3D();
	WheelLayout layout = new WheelLayout(Direction.VERTICAL, majorAxis, minorAxis);
	layout.addLayoutListener(new LayoutListener() {
		public void layoutDone(LayoutEvent event) {
		    fireSelectionEvent(event.getLayoutedComponent(0));
		}
	    });
        wheel.setLayout(layout);

        back.addListener(
            new MouseWheelEventAdapter(
                new ActionInt() {
                    public void performAction(LgEventSource source, int value) {
			if (source != null) {
			    rearrange(value);
			}
                    }
                }));

        base.addListener(
            new MouseWheelEventAdapter(
                new ActionInt() {
                    public void performAction(LgEventSource source, int value) {
        		if (source != null) {
			    rearrange(value);
			}
                    }
                }));

        super.addChild(wheel, null);
        
        setPreferredSize(new Vector3f(THUMBNAIL_SIZE + DECO_SIZE * 2, 
				      majorAxis * 2, minorAxis * 2));
    }

    private void rearrange(Component3D selectedComp) {
        wheel.rearrangeChildLayout(selectedComp, null);
    }

    private void rearrange(int angle) {
        wheel.rearrangeChildLayout((Component3D)wheel.getChild(0), Integer.valueOf(angle));
    }

    public void addChild(Texture2D texture, float width, float height) {
        Component3D comp = new Component3D();
        comp.setAnimation(new NaturalMotionAnimation(2000));
	comp.setCursor(Cursor3D.DEFAULT_CURSOR);
        
        SimpleAppearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                                                     SimpleAppearance.ENABLE_TEXTURE 
                                                     | SimpleAppearance.DISABLE_CULLING);
        appearance.setTexture(texture);

        float w = width;
        float h = height;

        if (width > height) {
            if (width > THUMBNAIL_SIZE) {
                w = THUMBNAIL_SIZE;
                h = height * THUMBNAIL_SIZE / width;
            }
        } else {
            if (height > THUMBNAIL_SIZE) {
                h = THUMBNAIL_SIZE;
                w = width * THUMBNAIL_SIZE / height;
            }
        }

        FuzzyEdgePanel panel = new FuzzyEdgePanel(w, h, 0.0f, appearance);

        comp.addChild(panel);
        comp.setPreferredSize(new Vector3f(w, h, 0));
        
        wheel.addChild(comp);
        
        imagecount++;
        
        comp.addListener(
            new MouseClickedEventAdapter(
                new ActionNoArg() {
                    public void performAction(LgEventSource source) {
			if (source != null) {
			    rearrange((Component3D)source);
			}
                    }
                }));

        comp.addListener(
            new MouseWheelEventAdapter(
                new ActionInt() {
                    public void performAction(LgEventSource source, int value) {
			if (source != null) {
			    rearrange(value);
			}
                    }
                }));
    }
    
    public int getImageCount () {
        return imagecount;
    }
    
    public int getSelectedIndex () {
        return current;
    }
    
    public Texture getSelectedTexture() {
        Shape3D shape = (Shape3D)selectedComponent.getChild(0);
        Appearance app = shape.getAppearance();
        return app.getTexture();
    }

    public void addSelectionListener(SelectionListener listener) {
        listeners.add(listener);
    }

    public void removeSelectionListener(SelectionListener listener) {
        listeners.remove(listener);
    }

    private void fireSelectionEvent(Component3D comp) {
	if (selectedComponent == null || selectedComponent != comp) {
	   selectedComponent = comp;
	   current = wheel.indexOfChild(comp) + 1;

	    SelectionEvent event = new SelectionEvent(selectedComponent);
	    for(SelectionListener listener: listeners) {
		listener.itemSelected(event);
	    }
	}
    }
}
