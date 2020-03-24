
package org.jdesktop.lg3d.apps.physics;

/**
 * An interface with the methods necessary for a class used to resolve any collisions between Collideables.
 *
 * @author Tobias Evert
 */
public interface CollisionResolver {
  
    /**
     * Resolves the given Contact.
     * 
     * @param contact the contact to resolve.
     */
    public void resolve(Contact contact);
    
    /**
     * Resolves the given Contact with the given constant of restitution e.
     * Used with a gradually increasing e going from -1 to 0 this method will put a contact to rest.
     *
     * @param contact the contact to resolve.
     * @param e the constant of restitution to use for the contact.
     */
    
    
    public void putToRest(Contact contact, float e);
    
    
    /**
     * Separates the objects in a contact so that they no longer overlap.
     * @param amount a float value from 0 to 1, how much of the overlap to prevent.
     *
     */
    
    public void separate(Contact contact, float amount);
}
