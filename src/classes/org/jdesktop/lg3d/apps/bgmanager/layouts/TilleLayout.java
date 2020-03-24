/*
 * TilleLayout.java
 *
 * Created on June 23, 2006, 11:48 AM
 * Project Looking Glass
 *
 * @author Radek Kierner
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 */

package org.jdesktop.lg3d.apps.bgmanager.layouts;

import java.util.ArrayList;
import java.util.HashMap;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.c3danimation.Component3DAnimationFactory;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Component3DAnimation;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;


public class TilleLayout implements LayoutManager3D {
    
    private ArrayList<Component3D>bGList;
    private float heigh, width, columnWidth,rowHeigh;
    private int rows, columns;
    private HashMap<Component3D, Component3DAnimation> c3dAnimMap = null;
    private Component3DAnimationFactory c3daFactory = null;
    private Container3D bGcont;
    private Vector3f compSize;
    private Vector3f prevSize;
    private float scaleFactorX,scaleFactorY;
    /** Creates a new instance of TilleLayout */
    
    public TilleLayout (float heigh, float width, int rows, int columns, Vector3f compSize,
            Component3DAnimationFactory c3daFactory) {
	this.heigh = heigh;
	this.width = width;
	this.rows = rows;
	this.columns = columns;
	this.compSize = compSize;
	bGList = new ArrayList<Component3D>();
	this.c3daFactory = c3daFactory;
        c3dAnimMap = new HashMap<Component3D, Component3DAnimation>();
	
    }
    
    public void setContainer (Container3D container3D) {
	this.bGcont = container3D;
    }
    
    public void layoutContainer () {
	int numBg = bGList.size ();
	int tpc = 0;
	for(int i = 0;i<numBg;i++) {
	    int idx = i % numBg;
	    Component3D comp = bGList.get (idx);
	    columnWidth = (width/columns) * ((idx % columns)-1);
	    
	    if ((idx % columns) == columns -1 ){
	    
		rowHeigh = (heigh/rows) * tpc;
		tpc++;
	    }
	    
	    comp.changeTranslation (columnWidth - (width/((columns)/2)),rowHeigh- (heigh/((rows/2))),0.0f,700);
	    comp.changeScale (0.5f,700);
	}
    }
    
    public void addLayoutComponent (Component3D component3D, Object object) {
	if (object == null) {
	    bGList.add (0, component3D);
	} else if (object != null && object instanceof Integer){
	    bGList.add ((Integer)object,component3D);
	}
	
    }
    
    public void removeLayoutComponent (Component3D component3D) {
	bGList.remove (component3D);
	
    }
    
    public boolean rearrangeLayoutComponent (Component3D component3D, Object object) {
	return true;
    }
    
}
