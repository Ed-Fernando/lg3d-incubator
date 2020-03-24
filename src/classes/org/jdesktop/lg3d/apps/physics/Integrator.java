/*
 * Integrator.java
 *
 */

package org.jdesktop.lg3d.apps.physics;

/**
 * An interface for time integrators used with the physics package. 
 * @author Administrator
 */
public interface Integrator {
    /**
     * Update the given object's state using the supplied time step.
     * 
     * @param object the PhysicsObject to update.
     * @param dh the length of the time step to use.
     *
     */
     
    public void updateObject(PhysicsObject object, float dh);
    
    /**
     * Update the given body's state using the supplied time step.
     * 
     * @param body the PhysicsBody to update.
     * @param dh the length of the time step to use.
     *
     */
 
    public void updateBody(PhysicsBody body, float dh);
    
    /**
     * Update the given object's velocity using the supplied time step.
     * 
     * @param object the PhysicsObject to update.
     * @param dh the length of the time step to use.
     *
     */
    public void updateVelocity(PhysicsObject object, float dh);
    
    /**
     * Update the given body's position using the supplied time step.
     * 
     * @param body the PhysicsBody to update.
     * @param dh the length of the time step to use.
     *
     */
    public void updatePosition(PhysicsBody body, float dh);
    
    /**
     * Update the given object's position using the supplied time step.
     * 
     * @param object the PhysicsObject to update.
     * @param dh the length of the time step to use.
     *
     */
    public void updatePosition(PhysicsObject object, float dh);
    
}
