package org.jdesktop.lg3d.apps.physics;

import javax.vecmath.Vector3f;
import javax.vecmath.Matrix3f;

/**
 * A collision resolver that resolves collision by use of Newton-Coulomb impulses.
 */

public class ImpulseResolver implements CollisionResolver {
    
    /**
     * Resolves the given contact using a Newton-Coulomb impulse.
     * Calculates the impulse necessary to prevent overlap between the two objects and applies it.
     *
     * @param contact the contact to resolve.
     */
    public void resolve(Contact contact) {
        Vector3f impulse=new Vector3f();
                Vector3f combinedVelocity=new Vector3f();
                Vector3f finalVelocity=new Vector3f();
                Vector3f velocityChange=new Vector3f();
                Vector3f leftsPoint=new Vector3f();
                Vector3f rightsPoint=new Vector3f();
                Vector3f normal=contact.getNormal();
                Vector3f impactPoint=contact.getImpactPoint();
                
                //calculate the vectors pointing from the centers of the bodies to the impact point
                leftsPoint.sub(impactPoint,contact.getLeft().getPosition());
                rightsPoint.sub(impactPoint,contact.getRight().getPosition());
                
                //start the computation of the collisionMatrix
                float x,y,z;
                x=leftsPoint.x;
                y=leftsPoint.y;
                z=leftsPoint.z;
                
                Matrix3f leftCrossMatrix=new Matrix3f (0.0f,  -z,    y,
                                                        z,    0.0f, -x,    
                                                       -y,     x,   0.0f );
                x=rightsPoint.x;
                y=rightsPoint.y;
                z=rightsPoint.z;
               
                 Matrix3f rightCrossMatrix=new Matrix3f (0.0f,  -z,    y,
                                                        z,    0.0f, -x,    
                                                       -y,     x,   0.0f );
                 
               
                Matrix3f leftTensor=contact.getLeft().getRotatedTensor();
                leftTensor.mul(leftCrossMatrix);
                leftCrossMatrix.mul(leftTensor);
                
                Matrix3f rightTensor=contact.getRight().getRotatedTensor();
                rightTensor.mul(rightCrossMatrix);
                rightCrossMatrix.mul(rightTensor);
                
                float combinedInvMasses;
                combinedInvMasses=contact.getLeft().getInvMass() + contact.getRight().getInvMass();

                Matrix3f massMatrix=new Matrix3f(combinedInvMasses, 0.0f,               0.0f,
                                                 0.0f,              combinedInvMasses,  0.0f,
                                                 0.0f,              0.0f,               combinedInvMasses);
               
                Matrix3f collisionMatrix=new Matrix3f();
                collisionMatrix.sub(massMatrix,rightCrossMatrix);
                collisionMatrix.sub(leftCrossMatrix);
                
                Matrix3f invCollisionMatrix=new Matrix3f();
                invCollisionMatrix.invert(collisionMatrix);
                
                
                //Assume a sticking impulse
                combinedVelocity.sub(contact.getLeft().getCollisionVelocity(impactPoint), contact.getRight().getCollisionVelocity(impactPoint));
                finalVelocity.scale(normal.dot(combinedVelocity)*-contact.getRestitution(),normal);
                velocityChange.sub(finalVelocity,combinedVelocity);
                
                invCollisionMatrix.transform(velocityChange, impulse);
                //Now we need to compute the tangential and normal parts of the impulse to see if sticking applies
                //If the tangential impulse is smaller than or eqaul to the normal one, sticking applies
               
                Vector3f normalImpulse=new Vector3f();
                normalImpulse.scale(normal.dot(impulse),normal);
                Vector3f tangentImpulse=new Vector3f();
                tangentImpulse.sub(impulse,normalImpulse);
                float nL=normalImpulse.length();
                float tL=tangentImpulse.length();
                float dif=nL-tL;
                Vector3f normalVelocity=new Vector3f();
                normalVelocity.scale(normal.dot(combinedVelocity), normal);
                Vector3f tangent=new Vector3f();
                tangent.sub(combinedVelocity, normalVelocity);
                
                if(dif<0.0f && tangent.lengthSquared() > 0.000001f) { //sticking does not apply
                    tangent.normalize();
                    tangent.scale(contact.getFriction());
                    Vector3f tmpVector=new Vector3f(); //needed to store part of computation
                    tmpVector.sub(normal,tangent);
                    collisionMatrix.transform(tmpVector);
                    float normalAmplitude; //the length of the normal component of the impulse;
                    normalAmplitude=(-(1+contact.getRestitution())*normal.dot(combinedVelocity))/(normal.dot(tmpVector));
                    tangent.scale(normalAmplitude);
                    impulse.scale(normalAmplitude,normal);
                    impulse.sub(tangent);
                }
                contact.getLeft().applyImpulse(impulse, impactPoint);
                impulse.scale(-1.0f);
                contact.getRight().applyImpulse(impulse,impactPoint);
        
    }
    
        /**
         * Resolves the contact using a specified value for the constant of restitution.
         * When used iterativly with a gradually increasing constant of restitution going 
         * from -1 to zero it will put the contact to rest.
         *
         * @param contact the contact to resolve.
         * @param restitution the desired constant of restitution.
         */
    
        public void putToRest(Contact contact, float restitution) {
                Vector3f impulse=new Vector3f();
                Vector3f combinedVelocity=new Vector3f();
                Vector3f finalVelocity=new Vector3f();
                Vector3f velocityChange=new Vector3f();
                Vector3f leftsPoint=new Vector3f();
                Vector3f rightsPoint=new Vector3f();
                Vector3f normal=contact.getNormal();
                Vector3f impactPoint=contact.getImpactPoint();
                
                //calculate the vectors pointing from the centers of the bodies to the impact point
                leftsPoint.sub(impactPoint,contact.getLeft().getPosition());
                rightsPoint.sub(impactPoint,contact.getRight().getPosition());
                
                //start the computation of the collisionMatrix
                float x,y,z;
                x=leftsPoint.x;
                y=leftsPoint.y;
                z=leftsPoint.z;
                
                Matrix3f leftCrossMatrix=new Matrix3f (0.0f,  -z,    y,
                                                        z,    0.0f, -x,    
                                                       -y,     x,   0.0f );
                x=rightsPoint.x;
                y=rightsPoint.y;
                z=rightsPoint.z;
               
                 Matrix3f rightCrossMatrix=new Matrix3f (0.0f,  -z,    y,
                                                        z,    0.0f, -x,    
                                                       -y,     x,   0.0f );
                 
               
                Matrix3f leftTensor=contact.getLeft().getRotatedTensor();
                leftTensor.mul(leftCrossMatrix);
                leftCrossMatrix.mul(leftTensor);
                
                Matrix3f rightTensor=contact.getRight().getRotatedTensor();
                rightTensor.mul(rightCrossMatrix);
                rightCrossMatrix.mul(rightTensor);
                
                float combinedInvMasses;
                combinedInvMasses=contact.getLeft().getInvMass() + contact.getRight().getInvMass();
                Matrix3f massMatrix=new Matrix3f(combinedInvMasses, 0.0f,               0.0f,
                                                 0.0f,              combinedInvMasses,  0.0f,
                                                 0.0f,              0.0f,               combinedInvMasses);
              
               
                Matrix3f collisionMatrix=new Matrix3f();
                collisionMatrix.sub(massMatrix,rightCrossMatrix);
                collisionMatrix.sub(leftCrossMatrix);
                
                Matrix3f invCollisionMatrix=new Matrix3f();
                invCollisionMatrix.invert(collisionMatrix);
                
                
                //Assume a sticking impulse
                combinedVelocity.sub(contact.getLeft().getCollisionVelocity(impactPoint), contact.getRight().getCollisionVelocity(impactPoint));
                finalVelocity.scale(normal.dot(combinedVelocity)*-restitution,normal);
                velocityChange.sub(finalVelocity,combinedVelocity);
                
                invCollisionMatrix.transform(velocityChange, impulse);
                //Now we need to compute the tangential and normal parts of the impulse to see if sticking applies
                //If the tangential impulse is smaller than or eqaul to the normal one, sticking applies
               
                Vector3f normalImpulse=new Vector3f();
                normalImpulse.scale(normal.dot(impulse),normal);
                Vector3f tangentImpulse=new Vector3f();
                tangentImpulse.sub(impulse,normalImpulse);
                float nL=normalImpulse.length();
                float tL=tangentImpulse.length();
                float dif=nL-tL;
                Vector3f normalVelocity=new Vector3f();
                normalVelocity.scale(normal.dot(combinedVelocity), normal);
                Vector3f tangent=new Vector3f();
                tangent.sub(combinedVelocity, normalVelocity);
                
                if(dif<0.0f && tangent.lengthSquared() > 0.000001f) { //sticking does not apply
                    tangent.normalize();
                    tangent.scale(contact.getFriction());
                    Vector3f tmpVector=new Vector3f(); //needed to store part of computation
                    tmpVector.sub(normal,tangent);
                    collisionMatrix.transform(tmpVector);
                    float normalAmplitude; //the length of the normal component of the impulse;
                    normalAmplitude=(-(1+restitution)*normal.dot(combinedVelocity))/(normal.dot(tmpVector));
                    tangent.scale(normalAmplitude);
                    impulse.scale(normalAmplitude,normal);
                    impulse.sub(tangent);
                }
                contact.getLeft().applyImpulse(impulse, impactPoint);
                impulse.scale(-1.0f);
                contact.getRight().applyImpulse(impulse,impactPoint);
        
    }
    /**
     * Moves the two objects involved in the contact apart to prevent overlap.
     * Will move change the objects positions directly. The amount of change
     * is determined by the objects inverse masses (the heavier the object the less it will move)
     * and the given amount value. 
     *
     * The given amount will determine how much of the overlap to remove, 0 means non of it will be removed and
     * 1 means all of it will be removed. Values over 1 are not recomended but will work.
     * 
     * @param amount the amount of the overlap to remove.
     */
        
    public void separate(Contact contact, float amount) {
        float invMasses=contact.getLeft().getInvMass()+contact.getRight().getInvMass();
        Vector3f leftMove=new Vector3f();
        Vector3f rightMove=new Vector3f();
        Vector3f normal=contact.getNormal();
        rightMove.scale(-1.0f*amount*contact.getDepth()*(contact.getRight().getInvMass()/invMasses),normal);
        leftMove.scale(amount*contact.getDepth()*(contact.getLeft().getInvMass()/invMasses),normal);
        contact.getLeft().changePosition(leftMove);
        contact.getRight().changePosition(rightMove);
 
        
    }
}
