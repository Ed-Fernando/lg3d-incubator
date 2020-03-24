/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/events/KWebMouseClickedEventAdapter.java,v 1.3 2006-03-01 19:45:46 hideya Exp $
 * $Date: 2006-03-01 19:45:46 $
 *
 * Joint Copyright (c) 2005 by
 *   James A. Zaun, Consultant,
 *   The Burke Institute,
 *   Sun Microsystems, Inc.
 * ALL RIGHTS RESERVED.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 */
package org.jdesktop.lg3d.apps.kwebdemo1.events;

import javax.vecmath.Point3f;

import org.jdesktop.lg3d.utils.action.ActionBooleanInt;
import org.jdesktop.lg3d.utils.action.ActionFloat2;
import org.jdesktop.lg3d.utils.action.ActionFloat3;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.EventAdapter;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEnteredEvent3D;
import org.jdesktop.lg3d.apps.kwebdemo1.singletons.NodeData;

/**
 * This Adapter shouldn't be here but it was the only way I could figure out
 * how to get at pickable shapes inside a component. The LG supplied
 * <code>MouseEnteredEventAdapter</code> hides this from users (and
 * probably for good reason).  However, the LG code was frozen at this point
 * and I needed a way to pick nodes within the KWeb. 
 * 
 * <p><em>Implementation:</em> This class replaces the equivalent LG class and
 * gives me access to <code>MouseEnteredEvent3D</code> internals which the LG
 * version hides. From <code>MouseEnteredEvent3D</code> I can get the Java3D
 * Node that was picked. This is a Java3D object not an LG object which
 * violates LG rules against exposing Java3D objects. This situation must be
 * changed in a future LG release. From the Java3D shape's <code>hashCode</code>
 * I can get the KWeb Node id associated with the Java3D Node via a lookup table
 * <code>nodeData.nodePickTable</code>.  This hash table is public because this
 * whole thing is a horrible hack anyway.  The more general way to do this is
 * to return the LG shape that wraps the associated Java3D shape, but I don't
 * know how to do that. Anyway, this code could break if the LG internals are
 * changed.  Not good.</p>
 * 
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.3 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator 0.7.0
 */
public class KWebMouseClickedEventAdapter implements EventAdapter {
    private static final Class[] targetEventClasses = {
        MouseButtonEvent3D.class
    };
    
    private ActionBooleanInt actionBInt = null;
    private ActionFloat2 actionFloat2 = null;
    private ActionFloat3 actionFloat3 = null;
    private ButtonId button;
    private Boolean doubleClick; // handle single click by default
    private Point3f tmpV3f = new Point3f();
    
    /**
     * Create a KWebMouseClickedEventAdapter.
     * Listens to BUTTON1 events.
     * 
     * @param action       the action to propagate the event information to.
     * @throws IllegalArgumentException 
     *         if the action is null.
     */
    public KWebMouseClickedEventAdapter(ActionBooleanInt action) {
        this(ButtonId.BUTTON1, action);
    }
    
    /**
     * Create a KWebMouseClickedEventAdapter.
     * 
     * @param action       the action to propagate the event information to.
     * @param button       the button to listen to.
     * @throws IllegalArgumentException 
     *         if the action is null or
     *         if the button value is out of range.
     */
    public KWebMouseClickedEventAdapter(ButtonId button, ActionBooleanInt action) {
        this(button, false, action);
    }
    
    /**
     * Create a KWebMouseClickedEventAdapter.
     * Listens to BUTTON1 events.
     * 
     * @param action       the action to propagate the event information to.
     * @param dblClick     If set to true handle double click event instead of single click.
     * @throws IllegalArgumentException 
     *         if the action is null.
     */
    public KWebMouseClickedEventAdapter(Boolean dblClick, ActionBooleanInt action) {
        this(ButtonId.BUTTON1, dblClick, action);
    }
    
    /**
     * Create a KWebMouseClickedEventAdapter.
     * 
     * @param action       the action to propagate the event information to.
     * @param button       the button to listen to.
     * @param dblClick     If set to true handle double click event instead of single click.
     * @throws IllegalArgumentException 
     *         if the action is null or
     *         if the button value is out of range.
     */
    public KWebMouseClickedEventAdapter(ButtonId button, Boolean dblClick, ActionBooleanInt action) {
        if (button == null) {
            throw new IllegalArgumentException("button object cannot be null");
        }
        if (action == null) {
            throw new IllegalArgumentException("action object cannot be null");
        }
        this.button = button;
        this.doubleClick = dblClick;
        this.actionBInt = action;
    }
    
    /**
     * Create a KWebMouseClickedEventAdapter.
     * Listens to BUTTON1 events.
     * 
     * @param action       the action to propagate the event information to.
     * @throws IllegalArgumentException 
     *         if the action is null.
     */
    public KWebMouseClickedEventAdapter(ActionFloat2 action) {
        this(ButtonId.BUTTON1, action);
    }
    
    /**
     * Create a KWebMouseClickedEventAdapter.
     * 
     * @param action       the action to propagate the event information to.
     * @param button       the button to listen to.
     * @throws IllegalArgumentException 
     *         if the action is null or.
     */
    public KWebMouseClickedEventAdapter(ButtonId button, ActionFloat2 action) {
        this(button, false, action);
    }
    
    /**
     * Create a KWebMouseClickedEventAdapter.
     * Listens to BUTTON1 events.
     * 
     * @param action       the action to propagate the event information to.
     * @param dblClick     If set to true handle double click event instead of single click.
     * @throws IllegalArgumentException 
     *         if the action is null.
     */
    public KWebMouseClickedEventAdapter(Boolean dblClick, ActionFloat2 action) {
        this(ButtonId.BUTTON1, dblClick, action);
    }
    
    /**
     * Create a KWebMouseClickedEventAdapter.
     * 
     * @param action       the action to propagate the event information to.
     * @param button       the button to listen to.
     * @param dblClick     If set to true handle double click event instead of single click.
     * @throws IllegalArgumentException 
     *         if the action is null.
     */
    public KWebMouseClickedEventAdapter(ButtonId button, Boolean dblClick, ActionFloat2 action) {
        if (button == null) {
            throw new IllegalArgumentException("button object cannot be null");
        }
        if (action == null) {
            throw new IllegalArgumentException("action object cannot be null");
        }
        this.button = button;
        this.doubleClick = dblClick;
        this.actionFloat2 = action;
    }
    
    /**
     * Create a KWebMouseClickedEventAdapter.
     * Listens to BUTTON1 events.
     * 
     * @param action       the action to propagate the event information to.
     * @throws IllegalArgumentException 
     *         if the action is null.
     */
    public KWebMouseClickedEventAdapter(ActionFloat3 action) {
        this(action, ButtonId.BUTTON1);
    }
    
    /**
     * Create a KWebMouseClickedEventAdapter.
     * 
     * @param action       the action to propagate the event information to.
     * @param button       the button to listen to.
     * @throws IllegalArgumentException 
     *         if the action is null or.
     */
    public KWebMouseClickedEventAdapter(ActionFloat3 action, ButtonId button) {
        this(button, false, action);
    }
    
    /**
     * Create a KWebMouseClickedEventAdapter.
     * Listens to BUTTON1 events.
     * 
     * @param action       the action to propagate the event information to.
     * @param dblClick     If set to true handle double click event instead of single click.
     * @throws IllegalArgumentException 
     *         if the action is null.
     */
    public KWebMouseClickedEventAdapter(Boolean dblClick, ActionFloat3 action) {
        this(ButtonId.BUTTON1, dblClick, action);
    }
    
    /**
     * Create a KWebMouseClickedEventAdapter.
     * 
     * @param action       the action to propagate the event information to.
     * @param button       the button to listen to.
     * @param dblClick     If set to true handle double click event instead of single click.
     * @throws IllegalArgumentException 
     *         if the action is null.
     */
    public KWebMouseClickedEventAdapter(ButtonId button, Boolean dblClick, ActionFloat3 action) {
        if (button == null) {
            throw new IllegalArgumentException("button object cannot be null");
        }
        if (action == null) {
            throw new IllegalArgumentException("action object cannot be null");
        }
        this.button = button;
        this.doubleClick = dblClick;
        this.actionFloat3 = action;
    }
    
    /**
     * Process an incoming event. Invoked when a registered event happends.
     * 
     * @param event  an event of a registered type and from a registerd source.
     */
    public void processEvent(LgEvent event) {
        assert(event instanceof MouseButtonEvent3D);
        MouseButtonEvent3D me3d = (MouseButtonEvent3D)event;
        NodeData nodeData = NodeData.getInstance();
        Integer knode = nodeData.nodePickTable.get(me3d.getIntersectedNode(0).hashCode());
        if (knode == null) knode = new Integer(-1);
        
        if (!me3d.isClicked() || me3d.getButton() != button
            || (doubleClick && me3d.getClickCount() != 2)) 
        {
            return;
        }
        
        if (actionBInt != null) {
            actionBInt.performAction(event.getSource(), doubleClick, knode.intValue());
        } else if (actionFloat2 != null) {
            assert(actionFloat3 == null);
            actionFloat2.performAction(event.getSource(),
                me3d.getImagePlateX(), me3d.getImagePlateY());
        } else if (actionFloat3 != null) {
            me3d.getLocalIntersection(tmpV3f);
            actionFloat3.performAction(me3d.getSource(), 
                tmpV3f.x, tmpV3f.y, tmpV3f.z);
        }
    }
    
    /**
     * Called by Component3D when attaching this listener to the component
     * in order to obtain the list of LgEvent classes which this listens to.
     * 
     * @return  the list of LgEvent classes which this listener listens to.
     */
    public Class[] getTargetEventClasses() {
        return targetEventClasses;
    }
}
