/*
 * Contact.java
 * Class used to represent contact points between rigid body pairs.
 */

package org.jdesktop.lg3d.apps.physics;
import javax.vecmath.*;
import java.util.PriorityQueue;
import java.util.Vector;
/**
 * Class used for storing information about a contact point between two physical objects.
 * 
 * @author Tobias Evert
 */
public class Contact{
    
    private Vector3f impactPoint;
    private Vector3f normal;
    private Collideable left;
    private Collideable right;
    private boolean separating;
    private float restitution;
    private float friction;
    private float depth;
    
    /** Creates a new instance of Contact 
     * 
     * @param left the first Collideable in the collision.
     * @param right the second Collideable in the collision.
     * @param normal the collision normal as a vector poining away from the first Collideable.
     * @param impactPoint the point of impact in world coordinates.
     * @param depth the penetration depth of the contact.
     * 
     */
    public Contact(Collideable left, Collideable right, 
            Vector3f normal, Vector3f impactPoint,  float  depth) {
        this.left=left;
        this.right=right;
        
        this.impactPoint=new Vector3f(impactPoint);
        this.normal=new Vector3f(normal);
        this.restitution=(left.getRestitution()+right.getRestitution())/2.0f;
        this.friction=(left.getFriction()+right.getFriction())/2.0f;
        this.normal.normalize();
        this.depth=depth;
    }
    
    /**
     * Checks if the Collideables are allready moving apart and flags the contact accordingly.
     */
    
    public void flagSeparating() {
        Vector3f relativeVelocity=new Vector3f();
        relativeVelocity.sub(left.getCollisionVelocity(impactPoint),right.getCollisionVelocity(impactPoint));
        separating=(relativeVelocity.dot(normal) > 0);
    }
    
    /**
     * Returns the value of the separating flag.
     * @return true if the contact is flagged as separating.
     */
    
    public boolean isSeparating() {
        return separating;
    }
    
    
    /** 
     * Returns the depth of penetration between the two objects.
     * @return the penetration depth in meters as a positive float.
     */
    
    public float getDepth() {
        return depth;
    }
    
    
    /**
     * Returns the first of the two Collideables involved in the contact.
     * @return a reference to the first Collideables involved in the contact.
     */ 
    
    public Collideable getLeft() {
        return left;
    }
    
    /**
     * Returns the second of the two Collideables involved in this contact.
     * @return a reference to the first Collideables involved in this contact.
     */ 
    
    public Collideable getRight() {
        return right;
    }
    
    /**
     * Retruns the point of impact in this contact.
     * @return a copy of this contacts point of impact.
     */
    
    public Vector3f getImpactPoint() {
        return new Vector3f(impactPoint);
    }
    
    /**
     * Returns the collision normal for this contact.
     * @return a copy of the collision normal for this contact.
     *
     */
    
    public Vector3f getNormal(){
        return new Vector3f(normal);
    }
    
    /** 
     * Returns the coefficient of restitution for this contact.
     * @return the coefficient of restitution as a float.
     *
     */
    
    public float getRestitution() {
        return restitution;
    }
    
    
    
    /** 
     * Returns the coefficient of friction for this contact.
     * @return the coefficient of restitution as a float.
     *
     */
    
    public float getFriction() {
        return friction;
    }
        
    
    /**
     * Updates the separating flag of this contact. 
     * If the contact is separating, it will remain so, if not it's separating flag will be recalculated.
     **/
    
    public void updateFlag() {
        if(!separating)
            this.flagSeparating();
    }
    
    /**
     * Checks this contact to another object for equality.
     * @param o the object to check equality with.
     * @return false if o is of another class than Contact, otherwise the result of equals((Contact)o).
     */
    
    public boolean equals(Object o) {
        if(o.getClass()==this.getClass())
            return equals((Contact)o);
        else 
            return false; 
                
    }
    
    /**
     * Checks this contact to another contact for equality.
     * @param c the contact to chack equality with.
     * @return true if both contacts involve the same collideables (regardless of order), false otherwise.
     */
    
    public boolean equals(Contact c) {
        
        return ((c.getLeft()==left && c.getRight()==right) || (c.getLeft()==right && c.getRight()==left) );
    }
    
    /**
     * Returns the hashCode for this contact.
     *
     * @return the sum of the hash codes of the two collideables involved in the contact.
     */ 
    
    public int hashCode() {
        return left.hashCode() + (int)right.hashCode();
 
    }
    
    /**
     * Updates the contact depth to the depth of the deepest penetration between the two collideables.
     * @return true if there is still an overalp, false otherwise.
     */
    
    public boolean update() {
        Vector<Contact> contacts=new Vector<Contact>();
        left.collide(right, contacts);
        if(!contacts.isEmpty()) {
            this.depth=contacts.get(0).getDepth();
            this.impactPoint.set(contacts.get(0).getImpactPoint());
            this.normal.set(contacts.get(0).getNormal());
            return true;
        }
        return false;
    }
}