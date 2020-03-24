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
import org.jdesktop.lg3d.apps.zoetrope.event.LayoutEventcaster;
import org.jdesktop.lg3d.apps.zoetrope.event.LayoutListener;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;

public class WheelLayout implements LayoutManager3D, LayoutEventcaster {
    private float EXPAND_RATIO = 1.5f;

    private Container3D container;

    private Direction direction;
    private float majorAxis;
    private float minorAxis;

    private List<Component3D> components;
    private List<LayoutListener> listeners;
    
    public WheelLayout(Direction direction, float majorAxis, float minorAxis) {
        listeners = Collections.synchronizedList(new ArrayList<LayoutListener>());

        this.direction = direction;
        this.majorAxis = majorAxis;
        this.minorAxis = minorAxis;

	components = new ArrayList<Component3D>();
    }

    public void setContainer(Container3D container) {
	this.container = container;
    }
        
    public void layoutContainer() {
	doLayout();
    }

    public void addLayoutComponent(Component3D comp, Object constraints) {
        comp.setRotationAxis(0.0f, 1.0f, 0.0f);
	components.add(comp);
    }
    
    public void removeLayoutComponent(Component3D comp) {
	comp.changeScale(1.0f);
	components.remove(comp);
    }

    public boolean rearrangeLayoutComponent(Component3D comp, Object newConstraints) {
	if (newConstraints != null) {
	    if (newConstraints instanceof Integer) {
		if ((Integer)newConstraints > 0) {
		    goForward();
		} else if ((Integer)newConstraints < 0) {
		    goBackward();
		}
	    } else {
		return false;
	    }
	} else if (comp != null) {
	    int index = components.indexOf(comp);
	    int number = components.size();

	    if (index >= number / 2) {
                for (int c = number - index; c > 0; c--) {
                    goBackward();
                }
	    } else if (index < number / 2 && index > 0) {
                for (int c = index; c > 0; c--) {
                    goForward();
                }
	    } else {
		return false;
	    }
	} else {
	    return false;
	}

	return true;
    }

    private void goForward() {
	Component3D node = components.get(0);
	components.remove(node);
	components.add(node);
    }

    private void goBackward() {
	int number = components.size();
	Component3D node = components.get(number - 1);
	components.remove(node);
	components.add(0, node);
    }

    public void addLayoutListener(LayoutListener listener) {
	listeners.add(listener);
    }

    public void removeLayoutListener(LayoutListener listener) {
	listeners.remove(listener);
    }

    private void doLayout() {
        int number = components.size();
        Component3D comp0 = components.get(0);

	Vector3f v = new Vector3f();
	v = comp0.getPreferredSize(v);

	double length = (double)v.y;
	if (length < (double)v.x) {
	    length = (double)v.x;
	}

        int coefficient = calculateCoefficient(number, length * 1.25f);

        float x = 0.0f;
        float y = 0.0f;
        
        for (int i = 0; i < number; i++) {
            Component3D comp = components.get(i);

            double r = Math.PI * 2.0 / number * i;
            double rr = 1;
            double a = 1.0 / Math.PI;

            for (int j = 0; j < coefficient; j++) {
                rr *= (r - Math.PI);
                a *= Math.PI;
            }

            r = rr / a + Math.PI;

	    float sin = (float)Math.sin(r);
	    float cos = (float)Math.cos(r);

	    float scale;
	    if (i == 0) {
		scale = EXPAND_RATIO;
	    } else {
		scale = (cos + 2.0f) / 3.0f;
	    }

	    comp.changeScale(scale);

	    float location = majorAxis * sin;
            if (direction == Direction.VERTICAL) {
                y = location;
            } else {
                x = location;
            }

            float z = minorAxis * cos;
            comp.changeTranslation(x, y, z);
        }

	fireLayoutEvent();
    }
    
    private int calculateCoefficient(int number, double length) {
        double radSeg = 2.0 * Math.PI / number;
        double x = 1;
        double a = 1;
        double pi2 = Math.PI * Math.PI;
        int coefficient;

        if (number < 3) {
            coefficient = 1;
        } else {
            for (coefficient = 1;; coefficient += 2) {
                if (coefficient == 1) {
                    x = (radSeg - Math.PI);
                } else {
                    x *= (radSeg - Math.PI);
                    x *= (radSeg - Math.PI);
                    a *= pi2;
                }
                
                if (length < majorAxis * Math.sin(x / a + Math.PI)) {
                    break;
                }
            }
        }

        return coefficient;
    }

    private void fireLayoutEvent() {
        LayoutEvent event = new LayoutEvent(this, container, components);

        for(LayoutListener listener: listeners) {
            listener.layoutDone(event);
        }
    }
}
