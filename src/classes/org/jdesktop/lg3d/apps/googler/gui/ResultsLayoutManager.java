/***************************************************************************
 *   Copyright (C) 2005 by Juan Gonzalez                                   *
 *   juan@aga-system.com                                                   *
 *                                                                         *
 *   This program has been developed under Google's "Summer of Code 2005". *
 *   Special thanks goes to all people from Google and Sun Microsystems    *
 *   who made this cool experience a kind of success.                      *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************
 * Created on August 3, 2005, 8:45 PM
 */

package org.jdesktop.lg3d.apps.googler.gui;
import java.util.Hashtable;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;

/**
 *
 * @author opsi
 */
public class ResultsLayoutManager implements LayoutManager3D {
    private Container3D top;
    protected MyExtendableLine lineShape;
    private Component3D lineComp;
    private int maxPerRing;
    private float initialRadius,ringHop,ringVertHop;
    private Hashtable<Component3D,Point3f> compPositions;
    
    /**
     * Creates a new instance of ResultsLayoutManager
     */
    public ResultsLayoutManager(float initialRadius,float ringHop,float ringVertHop, int maxPerRing, Color3f lineColor,float lineTrans) {
        this.maxPerRing = maxPerRing;
        this.initialRadius = initialRadius;
        this.ringHop = ringHop;
        this.ringVertHop = ringVertHop;
        lineShape = new MyExtendableLine();                
        lineShape.setLineStyle(lineColor.x, lineColor.y,lineColor.z, lineTrans, 2f);
        lineComp = new Component3D();
        lineComp.addChild(lineShape);
        lineComp.setPickable(false);
        compPositions = new Hashtable();
    }    
    public boolean rearrangeLayoutComponent(Component3D component3D, Object obj) {        
        return true;
    }
    
    public void addLayoutComponent(Component3D component3D, Object obj) {
        float angle = 360/maxPerRing;
        float vertHop = ringVertHop/(top.numChildren()-1);
        Point3f point;
        
        lineShape.removeAllGeometries();
        compPositions = new Hashtable();        
        lineShape.addVertex(new Point3f(0,0,0));
        for(int i = 1; i < top.numChildren();i++) {
            float currRadius = initialRadius + (i/maxPerRing)*ringHop + (ringHop/maxPerRing)*(i%maxPerRing);
            Component3D curr = (Component3D)top.getChild(i);
            curr.changeTranslation(
                    -(float)(currRadius*Math.cos(Math.toRadians(angle*i))),
                    (float)(currRadius*Math.sin(Math.toRadians(angle*i))),
                    -vertHop*i);            
            point = new Point3f(-(float)(currRadius*Math.cos(Math.toRadians(angle*i))),
                    (float)(currRadius*Math.sin(Math.toRadians(angle*i))),
                    -vertHop*i);
            lineShape.addVertex(point);
            compPositions.put(curr, point);
        }
    }
    
    public void setContainer(Container3D container3D) {
        top = container3D;
        top.addChild(lineComp);
    }
    
    public void removeLayoutComponent(Component3D component3D) {
        if(compPositions.containsKey(component3D))
            compPositions.remove(component3D);
        component3D.changeTranslation(0,0,0);
        component3D.changeScale(0);
    }
    
    public void layoutContainer() {

    }
}
