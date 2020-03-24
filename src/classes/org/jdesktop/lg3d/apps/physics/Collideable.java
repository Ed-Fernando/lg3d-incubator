package org.jdesktop.lg3d.apps.physics;
import javax.vecmath.Vector3f;
import javax.vecmath.Matrix3f;
import java.util.Vector;
import org.jdesktop.lg3d.sg.GeometryArray;
/**
 *
 * An interface for physical objects capable of collision
 */

public interface Collideable {
    /**
     * checks this object for collisions with a rigid body sphere. 
     * @param sphere the sphere to check for collisions with.
     * @param contacts a Vector in which any contacts found will be stored.
     */
    public void collideSphere(PhysicsSphere sphere, Vector<Contact> contacts);
    
    
    /**
     * checks this object for collisions with a rigid body box.
     * @param box the box to check for collisions with.
     * @param contacts a Vector in which any contacts found will be stored.
     */
  
    public void collideBox(PhysicsBox box, Vector<Contact> contacts);
  
    
    /**
     * Checks the object for collision against another Collideable.
     * This method will typically just initiate the correct the correct colllision detection method by double dispatch.
     *
     * @param c the Collideable to check collision with.
     * @param contacts a vector in which any contacts found will be stored.
     */
    
    public void collide(Collideable c, Vector<Contact> contacts);
  
    
    /**
     * checks this object for collisions with a static plane.
     * @param plane the plane to check for collisions with.
     * @param contacts a vector in which any contacts found will be stored.
     */
    public void collidePlane(PhysicsPlane plane, Vector<Contact> contacts);
  
    
   /**
     * checks this object for collisions with a convex polyhedra.
     * @param polyhedra the polyhedra to check for collisions with.
     * @param contacts a Vector in which any contacts found will be stored.
     */
      
    public void collideConvexPolyhedra(ConvexPolyhedra polyhedra, Vector<Contact> contacts);
    
    
    /**
     * Computes the sum of linear and angular velocities at the given point. 
     * The point must be inside the object for the result to be meaningful.
     * @param point the point at which to sompute the compound velocity. 
     * @return the velocity as a vector.
     */
    
    public Vector3f getCollisionVelocity(Vector3f point);
    
    
    /**
     * A method to get an objects inverse mass.
     * @return the inverse of the mass as a float.
     */
     public float getInvMass();
    
    
    /**
     * Applies a Newton Coloumb impulse to the object.
     * @param impulse the impulse expressed as a vector.
     * @param point the point (in world coordinates) of the body at which the impulse is applied.  
     *
     */
    public void applyImpulse(Vector3f impulse, Vector3f point);
    
    
 
    
    /**
     * Gets the coordinates of the body's center of mass.
     *
     * @return the center of the body's mass as a vector.
     */
    public Vector3f getPosition();
    
    /**
     * Gets a copy of the inverse of the body's inertia tensor oriented according to the objects rotation.
     * @return a copy of the tensor as a matrix.
     */ 
    public Matrix3f getRotatedTensor();
    
    /**
     * Moves the object instantly by the amount and in the direction specified by the vector.
     * 
     * @param change the change in position expressed as a vector.
     * 
     */
    public void changePosition(Vector3f change);
    
    /**
     * Returns the objects restitution constant.
     * 
     * @return the objects restitution constant.
     */
    
    public float getRestitution();
    
    /**
     * Returns the objects friction constant.
     * 
     * @return the objects friction constant.
     */
    
    public float getFriction();

}
