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
 * MyHorizontalReorderableLayout.java
 *
 * Created on 19 de junio de 2006, 18:10
 *
 * $Revision: 1.3 $
 * $Date: 2006-08-17 23:22:19 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop.tabbed;

import java.util.ArrayList;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionFloat3;
import org.jdesktop.lg3d.utils.c3danimation.Component3DAnimationFactory;
import org.jdesktop.lg3d.utils.eventaction.Component3DMover;
import org.jdesktop.lg3d.utils.eventadapter.Component3DManualMoveEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseDragDistanceAdapter;
import org.jdesktop.lg3d.utils.layoutmanager.HorizontalLayout;
import org.jdesktop.lg3d.utils.layoutmanager.HorizontalLayout.AlignmentType;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 *
 * @author opsi
 */
public class MyHorizontalReorderableLayout extends MyHorizontalLayout {
    private static final long defaultAnimDuration = 175;
    
    private Component3DManualMoveEventAdapter manualMoveEventAdapter;
    private MouseDragDistanceAdapter dragEventAdapter;
    private Component3DMover componentMover;
    private Vector3f compOrigLoc = new Vector3f();
    
    public MyHorizontalReorderableLayout(AlignmentType policy, float spacing) {
        super(policy, spacing);
        initialize();
    }
    
    public MyHorizontalReorderableLayout(AlignmentType policy, float spacing, Component3DAnimationFactory c3daFactory) {
        super(policy, spacing, c3daFactory);
        initialize();
    }
    
    private void initialize() {
        componentMover = new Component3DMover();
        
        manualMoveEventAdapter = new Component3DManualMoveEventAdapter(
            new ActionBoolean() {
                public void performAction(LgEventSource source, boolean started) {
                    assert(source instanceof Component3D);
                    Component3D comp = (Component3D)source;
                    if (started) {
                        setComponentToSkipLayout(comp);
                        comp.setCursor(Cursor3D.SMALL_MOVE_CURSOR);
                        comp.getTranslation(compOrigLoc);
                    } else {
                        setComponentToSkipLayout(null);
                        comp.setCursor(Cursor3D.SMALL_CURSOR);
                        getTargetContainer().revalidate();
                    }
                }
            });
            
        dragEventAdapter = new MouseDragDistanceAdapter(
            new ActionFloat3() {
                public void performAction(LgEventSource source, 
                    float x, float y, float z) 
                {
                    x += compOrigLoc.x;
                    assert(source instanceof Component3D);
                    Component3D comp = (Component3D)source;
                    ArrayList<Component3D> compList = getManagedComponents();
                    Vector3f tmpV3f = new Vector3f();
                    synchronized (compList) {
                        int idx = compList.indexOf(comp);
                        int size = compList.size();
                        int newIdx = idx;
                        for (int i = idx - 1; i >= 0; i--) {
                            Component3D c = compList.get(i);
                            c.getFinalTranslation(tmpV3f);
                            if (tmpV3f.x < x) {
                                break;
                            }
                            newIdx = i;
                        }
                        if (newIdx == idx) {
                            for (int i = idx + 1; i < size; i++) {
                                Component3D c = compList.get(i);
                                c.getFinalTranslation(tmpV3f);
                                if (tmpV3f.x > x) {
                                    break;
                                }
                                newIdx = i;
                            }
                        }
                        if (newIdx != idx) {
                            getTargetContainer().rearrangeChildLayout(comp, newIdx);
                        }
                    }
                }
            });
    }
    
    /**
     * Add a component to the layout. Adding a component causes the spacing
     * of all the components to be recalculated.
     * @param comp The component to add
     * @param constraints An optional <code>Integer</code> specifying the postion to add the component
     * @see org.jdesktop.lg3d.wg.LayoutManager3D#addLayoutComponent(org.jdesktop.lg3d.wg.Component3D, java.lang.Object)
     */
    public void addLayoutComponent(Component3D comp, Object constraints) {
        super.addLayoutComponent(comp, constraints);
        
        comp.addListener(componentMover);
        comp.addListener(manualMoveEventAdapter);
        comp.addListener(dragEventAdapter);
    }
    
    /**
     * Remove a component from the layout. The spacing of the remaining components
     * is recalculated after removal.
     * @param comp The component to remove
     * @see org.jdesktop.lg3d.wg.LayoutManager3D#removeLayoutComponent(org.jdesktop.lg3d.wg.Component3D)
     */
    public void removeLayoutComponent(Component3D comp) {
        comp.removeListener(componentMover);
        comp.removeListener(manualMoveEventAdapter);
        comp.removeListener(dragEventAdapter);
        
        super.removeLayoutComponent(comp);
    }
}