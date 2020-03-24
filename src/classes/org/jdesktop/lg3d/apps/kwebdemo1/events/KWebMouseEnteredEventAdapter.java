/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/events/KWebMouseEnteredEventAdapter.java,v 1.3 2006-03-01 19:45:46 hideya Exp $
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

import org.jdesktop.lg3d.utils.action.ActionBooleanInt;
import org.jdesktop.lg3d.utils.eventadapter.EventAdapter;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventSource;
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
public class KWebMouseEnteredEventAdapter implements EventAdapter {
    private static final Class[] targetEventClasses = {
            MouseEnteredEvent3D.class
        };

    private ActionBooleanInt actionBooleanInt;

    /**
     * This constuctor passes <code>ActionBooleanInt</code> rather than
     * <code>ActionBoolean</code> in the LG version because I need to
     * passed through the associated KNode id the was picked.
     * 
     * @param action Action class that does smoething with this event.
     */
    public KWebMouseEnteredEventAdapter(ActionBooleanInt action) {
        if (action == null) {
            throw new IllegalArgumentException("action object cannot be null");
        }
        this.actionBooleanInt = action;
    }

    /**
     * Process an incoming event. Invoked when a registered event happens.
     * This does some extra stuff specific to the KWeb by returning the
     * KWeb node id that was picked.
     * 
     * @param event an event of a registered type and from a registerd source.
     */
    public void processEvent(LgEvent event) {
        assert(event instanceof MouseEnteredEvent3D);
        assert(actionBooleanInt != null);
        
        MouseEnteredEvent3D me3d = (MouseEnteredEvent3D)event;
        NodeData nodeData = NodeData.getInstance();
        Integer knode = nodeData.nodePickTable.get(me3d.getIntersectedNode(0).hashCode());
        if (knode == null) knode = new Integer(-1);
        LgEventSource source = me3d.getSource();
        boolean entered = me3d.isEntered();
        actionBooleanInt.performAction(source, entered, knode.intValue());
    }

    /**
     * Called by Component3D when attaching this listener to the component
     * in order to obtain the list of LgEvent classes which this listens to.
     * Copied from <code>MouseEnteredEventAdapter</code>.
     * 
     * @return  the list of LgEvent classes which this listener listens to.
     */
    public Class[] getTargetEventClasses() {
        return targetEventClasses;
    }

}
