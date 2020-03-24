package org.jdesktop.lg3d.apps.physics;
import javax.vecmath.*;
import org.jdesktop.lg3d.wg.Component3D;
/**
 * An abstract class inherited by all classes representing rigid bodies.
 **/

public abstract class PhysicsBody extends PhysicsObject {
    protected Matrix3f inertiaInverse;
    protected Quat4f attitude;
    protected Vector3f angularVelocity;
   
    /**
     * Constructor that initiates the storage for the intertia tensor, attitude and angular velocity.
     */
    public PhysicsBody() {
        super();
        inertiaInverse=new Matrix3f();
        attitude=new Quat4f();
        angularVelocity=new Vector3f();
    }
            
    
    /** 
     * Returns the a copy of the inverse of the inertia tensor.
     *
     * @return a copy of the body's inverse inertia tensor.
     */
    
    public Matrix3f getTensor() {
        return new Matrix3f(inertiaInverse);
    }
    
   /**
     * Sets the inverse inertia tensor to the supplied matrix.
     *
     * @param tensor the inverted inertia tensor matrix of the body.
     **/
    
    public void setTensor(Matrix3f tensor) {
        inertiaInverse=new Matrix3f(tensor);
    }
    
    
    

    /**
     * Returns a the body's angular velocity as a quaternion.
     
     * @return a quaternion with the x,y, and z components set to the body's angular velocity around that axis and the w component set to zero.
     */
    public Quat4f getAngularVelocity() {
        Quat4f quaternion=new Quat4f();
        quaternion.set(angularVelocity.x,angularVelocity.y,angularVelocity.z,0.0f);
        
        return quaternion;
    }
  /**
   * Returns a copy of the body's attitude as a quaternion.
   * @return a quaternion representing the body's orientation in space.
   */
    
   public Quat4f getAttitude() {
        return new Quat4f(attitude);
    }
   
   /**
    * Updates the graphical object simulated by this body with its position and attitude.
    */
   public void updateGraphics() {
        if((graphicalObject)!=null) {
            graphicalObject.setTranslation(position.x,position.y,position.z);
        
            AxisAngle4f newAngle=new AxisAngle4f();
            newAngle.set(attitude);
            graphicalObject.setRotationAxis(newAngle.x,newAngle.y,newAngle.z);
            graphicalObject.setRotationAngle(newAngle.angle);
        }
        
   } 
   /**
    * Sets the body's attitude to the one in the supplied quaternion.
    * 
    * @param attitude the new attitude.
    */
   public void setAttitude(Quat4f attitude) {
       this.attitude.set(attitude);
       this.attitude.normalize();
   }
   
   /**
    * Updates the body's velocity using its integrator and the supplied timestep.
    *
    * @param dh the timestep to use when integrating.
    */ 
   public void updateVelocity(float dh) {
        integrator.updateVelocity(this, dh);
    }
    /**
    * Updates the body's position using its integrator and the supplied timestep.
    *
    * @param dh the timestep to use when integrating.
    */ 
    public void updatePosition(float dh) {
        integrator.updatePosition(this, dh);
    }
    /**
     * Applies a Newtonian impulse at a given point to this body. 
     * The impulse (instant velocity change) is applied at the given point as if it was inside the body.
     *
     * @param impulse a vector giving the size and direction of the impulse.
     * @param point the point in world coordinates at which the impulse is applied.
     *
     */
    
    public void applyImpulse(Vector3f impulse, Vector3f point) {
      
        Vector3f velocityChange=new Vector3f(impulse);
        velocityChange.scale(invMass);
        velocity.add(velocityChange);
              
        Matrix3f rotatedInertiaInverse=getRotatedTensor();
        Vector3f cross=new Vector3f();
        Vector3f localPoint=new Vector3f();
        localPoint.sub(point, position);
        cross.cross(localPoint,impulse);
        rotatedInertiaInverse.transform(cross);
        angularVelocity.add(cross);
   
    }
    
   /**
    * Returns a copy of the body's inverse inertia tensor in alignment with the body current attitude.
    *
    * @return the tensor as a matrix.
    */
   
   public Matrix3f getRotatedTensor() {
       Matrix3f rotationMatrix=new Matrix3f();
       rotationMatrix.set(attitude);
       Matrix3f rotatedTensor=new Matrix3f();
       Matrix3f tmp=new Matrix3f();
       rotatedTensor.mul(rotationMatrix, inertiaInverse);
       rotationMatrix.transpose();
       rotatedTensor.mul(rotationMatrix);
       return rotatedTensor;
  
   }
   
    /**
    * Returns the compuond velocity of the body at a given point.
    * Calculates a total velocity from the body's angular and linear velocities at the given point.
    * 
    * @param point the point at which to meassure the compound velocity.
    */
     
   public Vector3f getCollisionVelocity(Vector3f point) {
       Vector3f cross=new Vector3f();
       cross.sub(point, position);
       Vector3f totalVelocity=new Vector3f();
       totalVelocity.cross(angularVelocity, cross); 
       totalVelocity.add(velocity);
       return totalVelocity;
   } 
   
}