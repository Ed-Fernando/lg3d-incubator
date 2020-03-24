/*
 * PhysicsParticle.java
 */

package org.jdesktop.lg3d.apps.physics;
import javax.vecmath.Vector3f;
import javax.vecmath.Matrix3f;
import java.util.Vector;

/**
 * Used to represent a paritcle in a particle system.
 *
 */
public class PhysicsParticle extends PhysicsObject implements Collideable {
    
    /** Creates a new instance of PhysicsParticle with the given starting position and velocity.
     * @param position the particles starting point.
     * @param initialVelocity the particles initial velocity.
     */
    
    public PhysicsParticle(Vector3f position, Vector3f initialVelocity) {
        
    }
    
    /**
     * checks this object for collisions with a rigid body sphere. 
     * @param sphere the sphere to check for collisions with.
     * @param contacts a Vector in which any contacts found will be stored.
     */
    public void collideSphere(PhysicsSphere sphere, Vector<Contact> contacts) {
        
    }
    
    
    /**
     * checks this object for collisions with a rigid body box.
     * @param box the box to check for collisions with.
     * @param contacts a Vector in which any contacts found will be stored.
     */
  
    public void collideBox(PhysicsBox box, Vector<Contact> contacts) {
        
    }
  
    
    /**
     * Checks the object for collision against another Collideable.
     * This method will typically just initiate the correct the correct colllision detection method by double dispatch.
     *
     * @param c the Collideable to check collision with.
     * @param contacts
     */
    
    public void collide(Collideable c, Vector<Contact> contacts) {
        
    }
  
    
    /**
     * checks this object for collisions with a static plane.
     * @param plane the plane to check for collisions with.
     * @param contacts a Vector in which any contacts found will be stored.
     */
    public void collidePlane(PhysicsPlane plane, Vector<Contact> contacts) {
        
    }
  
    
   /**
     * checks this object for collisions with a convex polyhedra.
     * @param polyhedra the polyhedra to check for collisions with.
     * @param contacts a Vector in which any contacts found will be stored.
     */
      
    public void collideConvexPolyhedra(ConvexPolyhedra polyhedra, Vector<Contact> contacts) {
        
    }
    
    
    /**
     * Computes the sum of linear and angular velocities at the given point. 
     * The point must be inside the object for the result to be meaningful.
     * @param point the point at which to sompute the compound velocity. 
     * @return the velocity as a vector.
     */
    
    public Vector3f getCollisionVelocity(Vector3f point) {
        return new Vector3f(velocity);
    }
    
    
    
    
    /**
     * Applies a Newton Coloumb impulse to the object.
     * @param impulse the impulse expressed as a vector.
     * @param point the point (in world coordinates) of the body at which the impulse is applied.  
     *
     */
    public void applyImpulse(Vector3f impulse, Vector3f point) {
        
        
    }
    
    
    
    /**
     * Gets a copy of the inverse of the body's inertia tensor oriented according to the objects rotation.
     * @return a copy of the tensor as a matrix.
     */ 
    public Matrix3f getRotatedTensor() {
        return new Matrix3f();
    }
    
    
    
    
}
