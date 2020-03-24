/*
 * Effector.java
 */

package org.jdesktop.lg3d.apps.physics;

import java.util.Vector;

/**
 * An abstract super class for things applying forces to physical objects.
 * Can be used for gravity, wind, air drag, etc. 
 */

abstract public class Effector {
    
    
    protected Vector<PhysicsObject> objects;
    
    /**
     * Constructor that initiates the objects vector.
     */
    public Effector() {
        objects=new Vector();
    }
    /**
     * Applies the effects of the Effector to its PhysicsObjects.
     * 
     */
    
    abstract public void apply();
    
    /**
     * Adds an object to the list of objects affected by this effector.
     * @param object the object to add to the list.
     */
    public void addObject(PhysicsObject object) {
        objects.addElement(object);
    }
            
    
}
