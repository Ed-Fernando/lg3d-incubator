package org.jdesktop.lg3d.apps.physics;

import javax.vecmath.*;
import javax.media.j3d.*;
import java.util.Vector;
import org.jdesktop.lg3d.wg.Component3D;
/**
 * The basic abstract class for all phyiscally simulated objects. 
 * This is the base class all classes that represents objects that the physics enginge can move around should inherit from.
 *
 * @author Tobias Evert
 */
public abstract class PhysicsObject {
    protected Vector3f position;
    protected Vector3f velocity;
    protected Vector3f force;
    protected float mass;
    protected float invMass;
    protected Integrator integrator;
    protected Component3D graphicalObject;
    protected float restitution;
    protected float friction;
    /**
     * Constructor that initiates the vectors for position, velocity and force.
     */
    
    public PhysicsObject() {
        position=new Vector3f();
        velocity=new Vector3f();
        force=new Vector3f();
    }
   
    /**
     * Returns a copy of the objects center of mass coordinates.
     * 
     * @return the coordinates of the objects center of mass as a vector
     */
    
    public Vector3f getPosition() {
        return new Vector3f(position);
    }
    
   /**
    * Returns a copy of the objects velocity.
    * 
    * @return the objects velocity of mass as a vector
    */
    
    public Vector3f getVelocity() {
        return new Vector3f(velocity);
    }
    
      
    void update(float dh) {
        integrator.updateObject(this,dh);
    }
    
    /**
     * Resets the force working on the object to the zero vector.
     */
    
    public void resetForce() {
        force.set(0.0f,0.0f,0.0f);
    }
    
    
    /**
     * Moves the object according to the supplied vector. 
     */
        
    public void changePosition(Vector3f change) {
        position.add(change);
    }

    
    /**
     * Returns the mass of the object in kilograms.
     * 
     * @return a float with the mass of the object in kilograms.
     */ 
    
     public float getMass() {
        return mass;
    }
   
     /**
     * Returns 1/the mass of the object in kilograms.
     * 
     * @return a float with  1/the mass of the object in kilograms.
     */ 
    
     public float getInvMass() {
        return this.invMass;
    }
     
     
    /**
     * Sets the mass of the object to the supplied float.
     *
     * @param mass the mass of the object in kilograms. 
     */
     
    public void setMass(float mass) {
        this.mass=mass;
    }
    /**
     * Sets the Integrator the object uses for time integration.
     * 
     * @param integrator the Integrator to use from now on.
     */
    public void setIntegrator(Integrator integrator) {
        this.integrator=integrator;
    }
    /**
     * Returns the force currently applied to the object.
     *
     * @return the force expressed as a Vector.
    */
    public Vector3f getForce() {
        return new Vector3f(force);
    } 
    /**
     * Sets the objects velocity to the velocity specified in the given vector.
     *
     * @param velocity the new velocity of the object.
     */
    
    public void setVelocity(Vector3f velocity){
        this.velocity.set(velocity);
    } 
    
     /**
     * Sets the objects position to the point specified in the given vector.
     *
     * @param position the new position of the object.
     */
    public void setPosition(Vector3f position) {
        this.position.set(position);
    }
    /**
     * Adds a force vector to the current force at work on this object.
     * @param force the force vector to add.
     */
    public void addForce(Vector3f force) {
        this.force.add(force);
    }
    
    /**
     *  Uses the objects integrator to update its velocity.
     *
     *  @param dh the timestep to use.
     */ 
    public void updateVelocity(float dh) {
        integrator.updateVelocity(this, dh);
    }
  
    /**
     *  Uses the objects integrator to update its position.
     *
     *  @param dh the timestep to use.
     */ 
    
    public void updatePosition(float dh) {
        integrator.updatePosition(this, dh);
    }
   
    /**
     * updates the object graphical object
     */ 
    public void updateGraphics() {
         if((graphicalObject)!=null) 
            graphicalObject.setTranslation(position.x,position.y,position.z);
    }
    
    /**
     * Returns the objects restitution constant.
     * 
     * @return the objects restitution constant.
     */
    
    public float getRestitution() {
        return restitution;
    }
    
    /**
     * Returns the objects friction constant.
     * 
     * @return the objects friction constant.
     */
    
    public float getFriction() {
        return friction;
    }
}