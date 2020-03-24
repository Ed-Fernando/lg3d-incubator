/***************************************************************************
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
 *                                                                         *
 *   Copyright (C) 2006                                                    * 
 *                  Juan Gonzalez (juan@aga-system.com)                    *
 *                & Sun Microsystems                                       *
 *                                                                         * 
 ***************************************************************************
 * InfinityMatrixLayout.java
 *
 * Created on 7 de julio de 2006, 13:07
 *
 * $Revision: 1.4 $
 * $Date: 2006-08-17 23:22:19 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop.tabbed;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Logger;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.utils.action.ActionBooleanFloat3;
import org.jdesktop.lg3d.utils.action.ActionFloat2;
import org.jdesktop.lg3d.utils.action.ActionFloat3;
import org.jdesktop.lg3d.utils.action.ActionInt;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseDragDistanceAdapter;
import org.jdesktop.lg3d.utils.shape.Cone;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;
import org.jdesktop.lg3d.wg.event.Component3DManualMoveEvent;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 *
 * @author opsi
 */
public class InfinityMatrixLayout implements LayoutManager3D{
    private Logger logger;
    private Container3D top;
    private TreeSet<Component3D> orderedElements;
    private Vector<Vector<Component3D>> rowVector;
    
    private float width;
    private float height;
    
    private float rowSpacing;
    private float colSpacing;
    private int nrows;
    private int ncols;
    private int firstShownRow;
    private int rowsFilled;
    private int colsFilled;
    
    private Component3D scrollUpComp;
    private Component3D scrollDownComp;
    private Component3D scrollingBG;
    
    /** Creates a new instance of InfinityMatrixLayout */
    public InfinityMatrixLayout(float width,float height,int rows2show,int cols2show,Comparator<Component3D> orderComparator) {
        logger = Logger.getLogger("lg.utils");
        setSize(width,height);
        setMaxShowed(rows2show,cols2show);
        setOrderComparator(orderComparator);
        initScrollCones();
    }
    private void initScrollCones() {
        scrollUpComp = new Component3D();
        Cone cone = new Cone(colSpacing/3,rowSpacing);
        scrollUpComp.addChild(cone);
        scrollUpComp.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            public void performAction(LgEventSource source) {
                scrollUp();
                layoutContainer();
            }
        }));
        
        scrollDownComp = new Component3D();
        cone = new Cone(colSpacing/3,rowSpacing);
        scrollDownComp.addChild(cone);
        scrollDownComp.setRotationAxis(0,0,1);
        scrollDownComp.setRotationAngle((float)Math.toRadians(180));
        scrollDownComp.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            public void performAction(LgEventSource source) {
                scrollDown();
                layoutContainer();
            }
        }));
        if(scrollingBG!=null) {
            top.removeChild(scrollingBG);
        }
        scrollingBG = new Component3D();
        scrollingBG.setCapability(Component3D.ALLOW_DETACH);
        scrollingBG.addChild(scrollDownComp);
        scrollingBG.addChild(scrollUpComp);
    }
    private void scrollUp() {
        if(firstShownRow > 0)
            firstShownRow--;
    }
    private void scrollDown() {
        if(firstShownRow+nrows < rowsFilled)
            firstShownRow++;
    }
    
    public void setSize(float width,float height) {
        if(width<=0)
            throw new IllegalArgumentException("width must be bigger than 0 (was " + width + ")");
        if(height<=0)
            throw new IllegalArgumentException("height must be bigger than 0 (was " + height + ")");
        this.width = width;
        this.height = height;
        setUpSpacing();
    }
    public void setMaxShowed(int rows2show,int cols2show) {
        if(rows2show<=0)
            throw new IllegalArgumentException("rows2show must be bigger than 0 (was " + rows2show + ")");
        if(cols2show<=0)
            throw new IllegalArgumentException("cols2show must be bigger than 0 (was " + cols2show + ")");
        this.nrows = rows2show;
        this.ncols = cols2show;
        rowVector = new Vector<Vector<Component3D>>(nrows);
        rowVector.add(new Vector<Component3D>(ncols));
        rowsFilled = 0;
        colsFilled = 0;
        firstShownRow = 0;
        setUpSpacing();
    }
    private void setUpSpacing() {
        if(width>0 && height>0 && nrows>0 && ncols>0) {
            colSpacing = width/ncols;
            rowSpacing = height/nrows;
        }
    }
    public void setOrderComparator(Comparator<Component3D> orderComparator) {
        if(orderComparator==null)
            orderedElements = new TreeSet<Component3D>();
        if(orderedElements==null)
            orderedElements = new TreeSet<Component3D>(orderComparator);
        else if(orderedElements.size() > 0) {
            TreeSet<Component3D> tmp = orderedElements;
            orderedElements = new TreeSet<Component3D>(orderComparator);
            orderedElements.addAll(tmp);
            tmp=null;
        }
    }
    /**
     * Removes the specified component from the layout.
     *
     * @param comp the component to be removed
     */
    public void removeLayoutComponent(Component3D comp) {
        colsFilled--;
        if(colsFilled<=0) {
            colsFilled = ncols-1;
            rowsFilled--;
        }
    }
    
    /**
     * Lays out the associated container.
     */
    public void layoutContainer() {
        Iterator<Component3D> iter = orderedElements.iterator();
        int row = 0, col = 0, screenRow = 0,screenCol = 0;
        Component3D current = null;
        while(iter.hasNext()) {
            current = iter.next();
            if(row<firstShownRow) {
                current.changeVisible(false);
                current.setTranslation(col*colSpacing,-screenRow*rowSpacing,0);
            } else if(row>firstShownRow+nrows-1) {
                current.changeVisible(false);
                current.setTranslation(col*colSpacing,-screenRow*rowSpacing,0);
            } else {
                current.changeVisible(true);
                current.changeTranslation(screenCol*colSpacing,-screenRow*rowSpacing,0);
                screenCol++;
                if(screenCol == ncols) {
                    screenRow++;
                    screenCol=0;
                }
            }
            col++;
            if(col == ncols) {
                row++;
                col=0;
            }
        }
        if(row >= nrows) {
            scrollDownComp.setVisible(true);
            scrollUpComp.setVisible(true);
            scrollUpComp.setTranslation(ncols*colSpacing,0,0);
            scrollDownComp.setTranslation(ncols*colSpacing,-nrows*rowSpacing,0);
        } else {
            scrollDownComp.setVisible(false);
            scrollUpComp.setVisible(false);
        }
//        if(orderedElements.size() > 30)
//        {
//            System.out.println("INICIO");
//            for(Component3D comp : orderedElements)
//            {
//                System.out.println(((DesktopIcon)comp).entry.getName(Locale.getDefault()));
//            }
//            System.out.println("FIN");
//        }
    }
    
    /**
     * Set the target container.  Invoked with a valid target Container3D
     * object when this layout is set to the container, and with null when
     * the layout is removed from the container.
     *
     * @param parent the container to be laid out
     */
    public void setContainer(Container3D cont) {
        top = cont;
        if(top==null) {
            top.removeChild(scrollingBG);
        }else {
            top.addChild(scrollingBG);
        }
    }
    
    /**
     * Rearrange the specified component in the layout to a new location,
     * using the specified newConstraints object.
     *
     * @param comp the component to be rearranged
     * @param newConstraints where/how the component is added to the layout.
     * @return true if rearrrangement is taken place and layoutContainer()
     * needs to be invoked by the associated container.
     */
    public boolean rearrangeLayoutComponent(Component3D comp, Object newConstraints) {
        
        return false;
    }
    
    /**
     * Adds the specified component to the layout, using the specified
     * constraint object.
     *
     * @param comp the component to be added
     * @param constraints where/how the component is added to the layout.
     */
    public void addLayoutComponent(Component3D comp, Object constraints) {
        orderedElements.add(comp);
        colsFilled++;
        if(colsFilled == ncols) {
            rowsFilled++;
            colsFilled = 0;
        }
        comp.setScale(0);
    }
    
}
