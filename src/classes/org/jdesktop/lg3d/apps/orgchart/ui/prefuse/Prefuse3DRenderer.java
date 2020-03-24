/*
 * Prefuse3DRenderer.java
 *
 * Created on August 22, 2005, 1:57 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.ui.prefuse;

import edu.berkeley.guir.prefuse.EdgeItem;
import edu.berkeley.guir.prefuse.VisualItem;
import edu.berkeley.guir.prefuse.graph.Node;
import edu.berkeley.guir.prefuse.render.Renderer;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.ContactChannel;
import org.jdesktop.lg3d.wg.Component3D;


/**
 *
 * @author cc144453
 */
class Prefuse3DRenderer implements Renderer {
    private static final String ATTR_COMP3D = "__c3d";
    static final String ATTR_X = "__x";
    static final String ATTR_Y = "__y";
    static final String ATTR_Z = "__z";
    
    private static final float EDGE_RADIUS = 0.001f;
    
    private Prefuse3D orgChart;
    private Component3D chartComp;
    
    Prefuse3DRenderer(Prefuse3D orgChart, Component3D chartComp) {
        this.orgChart = orgChart;
        this.chartComp = chartComp;
    }
    
    public Rectangle2D getBoundsRef(VisualItem item) {
        // dummy
        return null;
    }
    public boolean locatePoint(Point2D p, VisualItem item) {
        // dummy
        return false;
    }
    
    public void	render(Graphics2D g, VisualItem item) {
        // ignore G
        Object comp3DObj = item.getVizAttribute(ATTR_COMP3D);
        Prefuse3DItem comp3D = null;
        if (item instanceof EdgeItem) {
            EdgeItem edgeItem = (EdgeItem)item;
            Prefuse3DItem firstNode = getOrgChartItem(edgeItem.getFirstNode());
            Prefuse3DItem secondNode = getOrgChartItem(edgeItem.getSecondNode());
            if (firstNode != null && secondNode != null) {
                //new Cylinder(float radius, float height, Appearance ap);
            }
        } else {
            Map attributes = item.getAttributes();
            if (comp3DObj == null && attributes instanceof ContactChannel) {
                // if nothing, draw it
                comp3D = new Prefuse3DItem(orgChart, item);
                chartComp.addChild(comp3D);
                item.setVizAttribute(ATTR_COMP3D, comp3D);
            } else if (comp3DObj instanceof Prefuse3DItem) {
                comp3D = (Prefuse3DItem)comp3DObj;
            }
        }
        if (comp3D != null) {
            comp3D.setVisible(item.isVisible());
            // get and set visual attributes
            Float fx = (Float)item.getVizAttribute(ATTR_X);
            Float fy = (Float)item.getVizAttribute(ATTR_Y);
            Float fz = (Float)item.getVizAttribute(ATTR_Z);
            if (fx != null && fy != null && fz != null) {
                comp3D.changeTranslation(fx.floatValue(),
                        fy.floatValue(),
                        fz.floatValue());
            }
        }
    }
    
    static final Prefuse3DItem getOrgChartItem(Node node) {
        Prefuse3DItem item = null;
        if (node instanceof VisualItem) {
            return (Prefuse3DItem)((VisualItem)node).getVizAttribute(ATTR_COMP3D);
        } else {
            return null;
        }
    }
}
