/*
 * ContactStackLayout.java
 *
 * Created on June 22, 2005, 1:51 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.ui.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import static org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId.*;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.Contact;
import org.jdesktop.lg3d.apps.orgchart.ui.common.ContactPanel;


/**
 *
 * @author cc144453
 */
public class ContactStackLayout implements LayoutManager3D {
    
    private static final float X_OFFSET = -0.1f;
    private static final float SPACING = ContactPanel.PANEL_DEPTH * 0.25f;
    private static final float DEPTH = -0.005f;
    private static final float DEPTH_OPEN = 0.01f;
    private static final float BOUNDARY_TOP = 0.003f; // starting point to skip the top
    private static final float BOUNDARY_BOTTOM = 0.02f; // starting point to skip the task bar
    private static final float BOUNDARY_SIDE = 0.01f;
    private static final float ROTATION_CLOSED = (float)(75.0f / 180.0 * Math.PI); // 75 degrees
    private static final Vector3f ROTATION_AXIS = new Vector3f(0.0f,  1.0f, 0.0f);
    private static final float MAX_SCALE = 1.5f; // max scale when rotating to front
    
    private float maxScale;
    private float maxWidth;
    private float maxHeight;
    private float left;
    private float depth;
    private float scale;
    private Chart3D orgChart;
    private Container3D cont;
    private ContactPanel compOpen = null;
    private ContactPanel compSelected = null;
    private float compOpenOriginalScale;
    private Vector3f compOpenOriginalTranslation = new Vector3f();
    private float compOpenDepth = 0.0f;
    private Component3D compToSkip = null;
    private ArrayList<Component3D> compList = new ArrayList<Component3D>();
    // FIXME -- it is inefficient to have one more HashMap,
    // but works for now...
    private HashMap<Component3D, MouseClickedEventAdapter> mouseClickedMap = null;
    private HashMap<Component3D, MouseEnteredEventAdapter> mouseEnteredMap = null;
    private Vector3f tmpV3f = new Vector3f();
    
    public ContactStackLayout(Chart3D orgChart,
            float depth,
            float left,
            float maxScale, float maxWidth, float maxHeight) {
        this.orgChart = orgChart;
        this.depth = depth;
        this.left = left;
        this.maxScale = (maxScale <= 0.0f) ? 1.0f : maxScale;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        mouseClickedMap = new HashMap<Component3D, MouseClickedEventAdapter>();
        mouseEnteredMap = new HashMap<Component3D, MouseEnteredEventAdapter>();
    }
    
    public void setContainer(Container3D cont) {
        this.cont = cont;
    }
    
    public void layoutContainer() {
        // calculate any scaling to ensure every object is in the space
        // skip if no components
        int numComp = compList.size();
        if (numComp > 0) {
            // container dimensions
            float contWidth = maxWidth;
            float contHeight = maxHeight;
            
            // size it based on first component
            // total width = 1 expanded panel + (n-1) rotated panels + borders
            Component3D firstComp = compList.get(0);
            firstComp.getPreferredSize(tmpV3f);
            float compWidth = tmpV3f.x;
            float compHeight = tmpV3f.y;
            float compClosedWidth = (float)Math.cos(ROTATION_CLOSED) * compWidth;
            float totalCompWidth = (compWidth + SPACING +
                    (numComp - 1) * (compClosedWidth + SPACING));
            float compScale = (contWidth - 2 * BOUNDARY_SIDE) / totalCompWidth;
            if (compScale >= maxScale) {
                scale = compScale = maxScale; // make sure it's not too large
            }
            if (totalCompWidth > contWidth) {
                totalCompWidth = contWidth;
            }
            // compute x position based on left or right side of screen to place panel
            float x = (left == 0.0f)
            ? (X_OFFSET + totalCompWidth * -0.5f + BOUNDARY_SIDE + compClosedWidth)
            : left;
            left = x;
            float y = 0.0f;
            float z = depth + DEPTH;
            compOpenDepth = compWidth * 0.6f;
            
            // compute y position for each panel
            float xAdvanceOpen = (compWidth /*+ SPACING*/) * compScale;
            float xAdvanceClosed = (compClosedWidth + SPACING) * compScale;
            
            // draw the selected component first
            if (compSelected != null) {
                compSelected.changeTranslation(x, y, z + compOpenDepth, Chart3D.LAYOUT_DURATION);
                compSelected.changeRotationAngle(0.0f, Chart3D.LAYOUT_DURATION);
                x += xAdvanceOpen;
            }
            for (Component3D comp : compList) {
                if (comp != compToSkip && comp != compSelected) {
                    comp.changeScale(compScale, Chart3D.LAYOUT_DURATION);
                    comp.changeRotationAxis(ROTATION_AXIS, Chart3D.LAYOUT_DURATION);
                    if (comp == compOpen) {
                        comp.changeTranslation(x, y, z + compOpenDepth, Chart3D.LAYOUT_DURATION);
                        comp.changeRotationAngle(0.0f, Chart3D.LAYOUT_DURATION);
                        x += xAdvanceOpen;
                    } else {
                        comp.changeTranslation(x, y, z, Chart3D.LAYOUT_DURATION);
                        comp.changeRotationAngle(ROTATION_CLOSED, Chart3D.LAYOUT_DURATION);
                        x += xAdvanceClosed;
                    }
                }
            }
        }
    }
    
    protected void setComponentToSkipLayout(Component3D compToSkip) {
        this.compToSkip = compToSkip;
    }
    
    protected Component3D getComponentToSkipLayout() {
        return compToSkip;
    }
    
    protected Container3D getTargetContainer() {
        return cont;
    }
    
    protected ArrayList<Component3D> getManagedComponents() {
        return compList;
    }
    
    public void addLayoutComponent(Component3D comp, Object constraints) {
        synchronized (compList) {
            if (constraints != null && constraints instanceof Integer) {
                compList.add((Integer)constraints, comp);
            } else {
                compList.add(comp);
            }
            
            ContactPanel contactUI = (ContactPanel)comp;
            
            // move to foreground on mouse enter
            MouseEnteredEventAdapter mouseEnteredAdapter =
                    new MouseEnteredEventAdapter(new MouseEnterActionBoolean(contactUI));
            mouseEnteredMap.put(comp, mouseEnteredAdapter);
            comp.addListener(mouseEnteredAdapter);
            
            // click action listener
            MouseClickedEventAdapter mouseClickedAdapter =
                    new MouseClickedEventAdapter(
                    BUTTON1, new MouseClickedAction(contactUI));
            mouseClickedMap.put(comp, mouseClickedAdapter);
            comp.addListener(mouseClickedAdapter);
        }
    }
    
    public void removeLayoutComponent(Component3D comp) {
        synchronized (compList) {
            // remove the mouse enter listener and the component
            MouseClickedEventAdapter mouseClickedAdapter = mouseClickedMap.get(comp);
            if (mouseClickedAdapter != null) {
                comp.removeListener(mouseClickedAdapter);
            }
            MouseEnteredEventAdapter mouseEnteredAdapter = mouseEnteredMap.get(comp);
            if (mouseEnteredAdapter != null) {
                comp.removeListener(mouseEnteredAdapter);
            }
            compList.remove(comp);
        }
    }
    
    public boolean rearrangeLayoutComponent(Component3D comp, Object constraints) {
        assert(compList.contains(comp));
        synchronized (compList) {
            if (constraints != null && constraints instanceof Integer) {
                int idx = (Integer)constraints;
                if (compList.indexOf(comp) == idx) {
                    return false;
                }
                compList.remove(comp);
                compList.add(idx, comp);
            } else {
                if (compList.indexOf(comp) == compList.size() -1) {
                    return false;
                }
                compList.remove(comp);
                compList.add(comp);
            }
            return true;
        }
    }
    
    public void setLeft(float x) {
        left = x;
        cont.revalidate();
    }
    
    public float getLeft() {
        return left;
    }
    
    public void setDepth(float depth) {
        synchronized (compList) {
            this.depth = depth;
            for (Component3D comp : compList) {
                comp.changeTranslation(0.0f, 0.0f, depth, Chart3D.LAYOUT_DURATION);
            }
        }
    }
    
    public float getDepth() {
        return depth;
    }
    
    public void setScale(float scale) {
        synchronized (compList) {
            this.depth = depth;
            for (Component3D comp : compList) {
                comp.changeScale(scale);
            }
        }
    }
    
    public float getScale() {
        return scale;
    }
    
    public Map getSelected() {
        return (compSelected != null) ? compSelected.getContact() : null;
    }
    
    public void select(Map contact) {
        synchronized (this) {
            for (Component3D comp : compList) {
                ContactPanel ui = (ContactPanel)comp;
                if (ui.getContact().equals(contact)) {
                    compSelected = ui;
                    cont.revalidate(); // redraw the other items
                }
            }
        }
    }
    
    private class MouseClickedAction implements ActionNoArg {
        private ContactPanel target;
        
        public MouseClickedAction(ContactPanel target) {
            this.target = target;
        }
        
        public void performAction(LgEventSource source) {
            // if nothing locked, then open and close cards
            synchronized (cont.getLayout()) {
                orgChart.removeLevels(cont);
                if (compSelected == target) {
                    compOpen = compSelected = null;
                } else {
                    orgChart.addLevel(target.getContact());
                    compSelected = target;
                }
            }
            cont.revalidate(); // redraw the other items
        }
    }
    
    private class MouseEnterActionBoolean implements ActionBoolean {
        private ContactPanel target;
        
        public MouseEnterActionBoolean(ContactPanel target) {
            this.target = target;
        }
        
        public void performAction(LgEventSource source, boolean state) {
            // if nothing locked, then open and close cards
            synchronized (this) {
                if (state) {
                    if (target != compSelected) {
                        compOpen = target;
                    }
                } else  {
                    compOpen = null;
                }
            }
            cont.revalidate(); // redraw the other items
        }
    }
}
