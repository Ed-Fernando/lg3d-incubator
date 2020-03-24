package org.jdesktop.lg3d.apps.physics;
import javax.vecmath.Vector3f;
import javax.vecmath.Quat4f;
/**
 * An integrator that uses the Leap Frog method to calculate future states of objects.
 */

public class LeapFrogIntegrator implements Integrator {
    
    /**
     * Update the given object's state using the supplied time step.
     * 
     * @param object the PhysicsObject to update.
     * @param dh the length of the time step to use.
     *
     */
    
    public void updateObject(PhysicsObject object, float dh) {
        Vector3f velocity=object.getVelocity();
        Vector3f velocityChange=object.getForce();
        velocityChange.scale(object.getInvMass()*dh);
        velocity.add(velocityChange);
        object.setVelocity(velocity);
        
        
        Vector3f position=object.getPosition();
        velocity.scale(dh);
        position.add(velocity);
        object.setPosition(position);
        
        
    }
    
    /**
     * Update the given body's state using the supplied time step.
     * 
     * @param body the PhysicsBody to update.
     * @param dh the length of the time step to use.
     *
     */
    
    public void updateBody(PhysicsBody body, float dh) {
        Vector3f velocity=body.getVelocity();
        Vector3f velocityChange=body.getForce();
        velocityChange.scale(body.getInvMass()*dh);
        velocity.add(velocityChange);
        body.setVelocity(velocity);
        
        
        Vector3f position=body.getPosition();
        velocity.scale(dh);
        position.add(velocity);
        body.setPosition(position);
        
        Quat4f attitudeChange=body.getAngularVelocity();
        Quat4f attitude=body.getAttitude();
        attitudeChange.mul(attitude);
        attitudeChange.scale(0.5f*dh);
        attitude.add(attitudeChange);
        attitude.normalize();
    }
    
    /**
     * Update the given object's velocity using the supplied time step.
     * 
     * @param object the PhysicsObject to update.
     * @param dh the length of the time step to use.
     *
     */
    
    public void updateVelocity(PhysicsObject object, float dh) {
        Vector3f velocity=object.getVelocity();
        Vector3f velocityChange=object.getForce();
        velocityChange.scale(object.getInvMass()*dh);
        velocity.add(velocityChange);
        object.setVelocity(velocity);
        
    }

    
    /**
     * Update the given body's position using the supplied time step.
     * 
     * @param body the PhysicsBody to update.
     * @param dh the length of the time step to use.
     *
     */
    
    public void updatePosition(PhysicsBody body, float dh) {
        Vector3f velocity=body.getVelocity();
        Vector3f position=body.getPosition();
        velocity.scale(dh);
        position.add(velocity);
        body.setPosition(position);
        
        Quat4f attitudeChange=body.getAngularVelocity();
        Quat4f attitude=body.getAttitude();
        attitudeChange.mul(attitude);
        attitudeChange.scale(0.5f*dh);
        attitude.add(attitudeChange);
        attitude.normalize();
        body.setAttitude(attitude);
        body.resetForce();
    }
    
     /**
     * Update the given object's position using the supplied time step.
     * 
     * @param object the PhysicsObject to update.
     * @param dh the length of the time step to use.
     *
     */
    
     public void updatePosition(PhysicsObject object, float dh) {
        Vector3f velocity=object.getVelocity();
        Vector3f position=object.getPosition();
        velocity.scale(dh);
        position.add(velocity);
        object.setPosition(position);
        object.resetForce();
     }
}  
