package org.jdesktop.lg3d.apps.physics;
import javax.vecmath.Vector3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Point3f;
import javax.vecmath.Matrix3f;
import java.util.Vector;
import java.util.PriorityQueue;
import org.jdesktop.lg3d.sg.GeometryArray;
/**
 * An unbounded plane in the physics simulation, defined by a point and a normal.
 */

public class PhysicsPlane extends PhysicsBody implements Collideable {
    private Vector3f normal;
    
    /**
     * Creates a new static plane with the given normal, position and constants.
     * 
     * @param normal the planes normal pointing outwards from the plane.
     * @param point any point in the plane.
     * @param restitution the plane's restitution constant.
     * @param friction the plane's friction constant.
     */
    
    public PhysicsPlane(Vector3f normal, Vector3f point, float restitution, float friction) {
        super();
        position.set(point);
        this.normal=new Vector3f(normal);
        this.normal.normalize();
        invMass=0.0f;
        attitude.set(0.0f,1.0f,0.0f,0.0f);
        this.friction=friction;
        this.restitution=restitution;
    }

    /**
     *  This method does nothing since the plane is static.
     */
    public void applyImpulse(Vector3f impulse, Vector3f point) {
        //the plane is static
    }
    
 
    
    /**
     * Returns a zero vector since the plane never moves.
     * @return the zero vector
     */
    public Vector3f getVelocity() {
        return new Vector3f();
    }
 
    
    /** Check the plane for intersection against another Collideable.
     * Used in the double dispatch of the collision detection.
     * @param c the Collideable to check for intersection with.
     * @param contacts the Vector in which to store any encountered contacts.
     **/
 
    public void collide(Collideable c, Vector<Contact> contacts) {
        c.collidePlane(this,contacts);
    }
    
    /** 
     * Checks the plane for overlap against a rigid body box. 
     * Any encountered contacts are stored in the supplied Vector. 
     *
     * @param box the PhysicsBox to check for intersection with.
     * @param contacts the Vector in which to store any encountered contacts.
     */
    public void collideBox(PhysicsBox box, Vector<Contact> contacts) {
        Vector3f[] corners=box.getCorners();
        float dot=normal.dot(position);
        PriorityQueue<Contact> pq=new PriorityQueue(8,new ContactComparator());
        
        for(int i=0;i<8;i++) {
            if(corners[i].dot(normal)-dot<0.0f) {
                Contact c=new Contact(box, this,normal,corners[i],-(corners[i].dot(normal)-dot));
              
                pq.add(c);
            }
        }
           
        if (!pq.isEmpty()){
            Contact[] found;
            found=pq.toArray(new Contact[1]);
            java.util.Arrays.sort(found, new ContactComparator());
            contacts.addAll(java.util.Arrays.asList(found));
        }
        
    }
     /**
     *  Checks this plane for collision against a general convex polyhedra. 
     *  NOT IMPLEMENTED YET!
     *
     * @param polyhedra the convex polyhedra to check for overlap with.
     * @param contacts the vector in which the contacts found will be stored.
     *
     */
     
     public void collideConvexPolyhedra(ConvexPolyhedra polyhedra, Vector<Contact> contacts) {
     
     }
    /** 
     * Checks the plane for overlap against a rigid body sphere. 
     * Any encountered contacts are stored in the supplied Vector. 
     *
     * @param sphere the PhysicsSphere to check for intersection with.
     * @param contacts the Vector in which to store any encountered contacts.
     */
    
    public void collideSphere(PhysicsSphere sphere, Vector<Contact> contacts) {
        Vector3f distance=new Vector3f();
        distance.sub(sphere.getPosition(),position);
        
        float normalDistance=distance.dot(normal);
        if (normalDistance<=sphere.getRadius()) {
            float depth=sphere.getRadius()-normalDistance;
            Vector3f impactPoint=new Vector3f();
            impactPoint.scale(-(sphere.getRadius()-(depth/2.0f)), normal);
            impactPoint.add(sphere.getPosition());
            contacts.add(new Contact(sphere,this,normal,impactPoint,depth));
        }
        
    }
    
    /**
     * Returns a copy of the normal of the plane.
     * 
     * @return a copy of the planes normal.
     */ 
    
    public Vector3f getNormal() {
        return new Vector3f(normal);
    }
    
    /**
     * Since planes are static they don't collide with each other.
     * This method exists only to implement all methods in the Collideable interface.
     */
    
    public void collidePlane(PhysicsPlane plane, Vector<Contact> contacts) {
        //DOES NOTHING
    }
    
    /**
     * Returns a zero vector since the plane never moves.
     * @return the zero vector
     */ 
    
    public Vector3f getCollisionVelocity( Vector3f point) {
        return new Vector3f();
    }
    
    
    /**
     * Returns a zero matrix as the plane has infinit inertia.
     * @return the zero vector
     */
    
    public Matrix3f getRotatedTensor() {
        return new Matrix3f();
        
    }
    
    /**
     * To make sure the plane does not move because of numerical errors, this method does nothing.
     */
    
    public void changePosition(Vector3f change) {
        //DOES NOTHING
    }
    
    /**
     * Does nothing to prevent the static plane from moving.
     * 
     * @param dh the timestep in with which to do nothing.
     */
    
    public void updateVelocity(float dh) {
    
    }
    
    /**
     * Does nothing to prevent the static plane from moving.
     * 
     * @param dh the timestep in with which to do nothing.
     */
    
    public void update(float dh) {
    
    }
    /**
     * Does nothing to prevent the static plane from moving.
     * 
     * @param dh the timestep in with which to do nothing.
     */
    
    public void updatePosition(float dh) {
    
    }
    
}
