/*
 * PhysicsSphere.java
 *
 * Created on den 14 juni 2006, 17:50
 *
 */

package org.jdesktop.lg3d.apps.physics;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Point3f;
import javax.vecmath.Matrix3f;
import javax.vecmath.GVector;
import javax.vecmath.GMatrix;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.sg.GeometryArray;
import java.util.Vector;
import java.lang.String;

        
/**
 * Used to represent a rigid sphere in the physics engine.
 *
 * @author Tobias Evert
 *
 */
public class PhysicsSphere extends PhysicsBody implements Collideable{
    private float radius;
    private float rollFriction;
    

    
 /**
  * Creates a new rigid body sphere for a physics simulation.
  * The created sphere will have the given properties and be linked to the given Component3D.
  * 
  * @param radius the radius of the sphere.
  * @param center the center of the sphere in world coordinates.
  * @param mass the sphere's mass in kilograms. 
  * @param graphics the Component3D that the sphere updates when it's state is updated.
  * @param integrator the integrator to use for time integration.
  */
    
    public PhysicsSphere(float radius, Vector3f center, float mass, Component3D graphics, Integrator integrator, float restitution, float friction, float rollFriction) {
        super();
        
        this.radius=radius;
        position.set(center);
        this.mass=mass;
        invMass=1.0f/mass;
        inertiaInverse.setRow(0, 1.0f/(2.0f/5.0f*mass*radius*radius), 0 , 0);
        inertiaInverse.setRow(1,                             0,  1.0f/(2.0f/5.0f*mass*radius*radius), 0);
        inertiaInverse.setRow(2,                             0,  0,  1.0f/(2.0f/5.0f*mass*radius*radius));
        graphicalObject=graphics;
  
        graphicalObject.setTranslation(position.x, position.y, position.z);
      
        this.restitution=restitution;
        this.friction=friction;
        this.rollFriction=rollFriction;
    
        this.integrator=integrator;
      
        setAttitude(new Quat4f(1.0f,0.0f,0.0f,0.0f));
       
        
         
    }
    /**
     * Checks collision between the sphere and a collideable of unknown type.
     * Enables the use of double dispatch for collision testing different collideables against each other. Just calls c.collideSphere(this, contacts).
     *
     * @param c the object implementing Collideable to test for overlap against.
     * @param contacts a Vector of Contact objects to store any occuring contacts in.
     */
    
    public void collide(Collideable c,Vector<Contact> contacts) {
        c.collideSphere(this, contacts);
    }
    
/**
 * Tests a sphere for overlap against another sphere. 
 * Will not test for separation at the contact, just determine if there is a contact and if so the contacts point in world coordinates and the contact normal. 
 * Adds any contact found in the supplied Vector.
 *
 * @param sphere the sphere to test for intersection against
 * @param contacts a Vector in which to store the contact that is found.
 *
 */  
    public void collideSphere(PhysicsSphere sphere, Vector<Contact> contacts) {
        //Check spheres for overlap
        Vector3f distance=new Vector3f();
        distance.sub(position,  sphere.getPosition());
        float addedRadius = sphere.getRadius() + this.getRadius();
        if (distance.lengthSquared() <= addedRadius * addedRadius) {
           float lengthOfDistance=distance.length();
           distance.normalize();
                  
           //From now on distance will be used as the collision normal
            
            Vector3f collisionPoint = new Vector3f(distance);
                
                collisionPoint.scale(radius-(radius+sphere.getRadius()-lengthOfDistance)/2.0f);

                collisionPoint.add(position);
                Contact contact=new Contact(this, sphere, distance, collisionPoint, lengthOfDistance-addedRadius);
                contacts.add(contact);
          
          }
    } 
    
  /**
   * Returns the radius of the sphere as a float.
   *
   * @return the radius of the sphere as a float.
   **/
    
    public float getRadius() {
        return radius;
    }
    
 
    /**
     * Checks the sphere for collision agains a convex polyhedra. 
     * 
     * @param polyhedra the polyhedra to check against.
     * @param contacts a vector to store any found contact points in.
     *
     **/
    
    public void collideConvexPolyhedra(ConvexPolyhedra polyhedra, Vector<Contact> contacts) {
        
    }
    
    
 
    
    /**
     * Checks the sphere for collision against a rigid box. Any contacts will be stored in the supplied Vector.
     *
     * @param box the box to check collisions with.
     * @param contacts the Vector in which the found contacts will be stored.
     */ 
    public void collideBox(PhysicsBox box, Vector<Contact> contacts)       {
        
        //Rotate the sphere and the box so the box becomes axis aligned.
        //In the box's case this is easy, just don't rotate it with it's attitude. 
        //The sphere will be rotated with the attitudes inverse.
        Quat4f boxAttitude=box.getAttitude();
        Vector3f sphereCenter=getPosition();
        Vector3f boxPosition=box.getPosition();
        if( boxAttitude.w>0.0000001f){
            Matrix3f transformation=new Matrix3f();
            transformation.set(boxAttitude);
            transformation.invert();
            
            transformation.transform(sphereCenter);
        }
        float[] distance=new float[3];
        distance[0]=sphereCenter.x-boxPosition.x;
        distance[1]=sphereCenter.y-boxPosition.y;
        distance[2]=sphereCenter.z-boxPosition.z;
        
        boolean[] overlapAtAxis=new boolean[3];
        overlapAtAxis[0]=false;
        overlapAtAxis[1]=false;
        overlapAtAxis[2]=false;
        
        
        Vector3f[] axis=new Vector3f[3];
        axis[0]=new Vector3f(1.0f,0.0f,0.0f);
        axis[1]=new Vector3f(0.0f,1.0f,0.0f);
        axis[2]=new Vector3f(0.0f,0.0f,1.0f);
        
        float distanceSquareSum=0.0f;
        float bodySquareSum=0.0f;
        float[] depths=new float[3];
        
        float least=900.0f;
        int leastId=-1;
        float[] halfEdges=box.getHalfEdges();
        
        //Now check for overlap along each axis
        for(int i=0;i<3;i++) {
            float abs;
            abs=java.lang.StrictMath.abs(distance[i])-halfEdges[i]-radius;
            
            if(abs<=0.0f) {
                overlapAtAxis[i]=true;
                //depthsSum+=(abs*abs);
                //foundOverlap=true;   
                depths[i]=-abs;
                if (-abs<least) {
                    least=-abs;
                    leastId=i;
                }
            }
            
        }
        
        if(overlapAtAxis[0] && overlapAtAxis[1] && overlapAtAxis[2]) {
            Vector3f collisionNormal=new Vector3f();
            for(int i=0;i<3;i++) {
                int id;
                id=(leastId+i)%3;
                if (depths[id]-least < 0.00001f) {
                    bodySquareSum+=halfEdges[id]*halfEdges[id];
                    distanceSquareSum+=distance[id]*distance[id];        
                    if(distance[id]>0)
                        collisionNormal.sub(axis[id]);
                    else
                        collisionNormal.add(axis[id]);
                }    
            }
            
            //Do the bodies realy touch?
            if(java.lang.StrictMath.sqrt(distanceSquareSum)-java.lang.StrictMath.sqrt(bodySquareSum)<=radius ) {
                //Calculate depth and normal
                if(boxAttitude.w>0.00001) {
                    collisionNormal.normalize();
                    Quat4f normalQuat=new Quat4f();
                    Quat4f conjugate=new Quat4f();
                    normalQuat.set(collisionNormal.x,collisionNormal.y,collisionNormal.z, 0.0f);
                    boxAttitude.mul(normalQuat,normalQuat);
                    conjugate.conjugate(boxAttitude);
                    normalQuat.mul(conjugate);
                
                    collisionNormal.set(normalQuat.x,normalQuat.y, normalQuat.z);
                }
                Vector3f collisionPoint=new Vector3f();
                collisionPoint.scale(radius-least/2.0f, collisionNormal);
                collisionPoint.add(sphereCenter);
                contacts.add(new Contact(box,this,collisionNormal,collisionPoint,least));
            }
        }
            
        
    }
   
     /**
     * Checks the sphere for collision against a limitless plane. Any contacts will be stored in the supplied Vector.
     *
     * @param plane the plane to check collisions with.
     * @param contacts the Vector in which the found contacts will be stored.
     */ 
   
    public void collidePlane(PhysicsPlane plane, Vector<Contact> contacts) {
        Vector3f distance=new Vector3f();
        distance.sub(position,plane.getPosition());
        Vector3f collisionNormal=plane.getNormal();
        float normalDistance=distance.dot(collisionNormal);
        if (normalDistance<=radius) {
            float depth=radius-normalDistance;
            Vector3f impactPoint=new Vector3f();
            impactPoint.scale(-(radius-(depth/2.0f)), collisionNormal);
            impactPoint.add(position);
            contacts.add(new Contact(this,plane,collisionNormal,impactPoint,depth));
        }
        
    }
    
    /**
     * Returns the velocity of the sphere at a given point in space.
     * This combines angular and linear velocities to a combined velocity dependent on where it is meassured.
     * 
     * @param point the point at which the velocity will be meassured.
     * @return a vector representing the velocity.
     */
    //public Vector3f getCollisionVelocity(Vector3f point) {
    //    return velocity;
    //}
    
    /**
     * Returns the oriented tensor of the sphere.
     * Since a sphere is completely symetrical this will always return the spheres unoriented tensor.
     */
    
    public Matrix3f getRotatedTensor() {
        return new Matrix3f(inertiaInverse);
    
    } 
    
    /**
     * Applies a Newtonian impulse at a given point to this sphere. 
     * The impulse (instant velocity change) is applied at the given point as if it was inside the sphere.
     *
     * @param impulse a vector giving the size and direction of the impulse.
     * @param point the point in world coordinates at which the impulse is applied.
     *
     */
    
    public void applyImpulse(Vector3f impulse, Vector3f point) {
        super.applyImpulse(impulse,point);
        angularVelocity.scale(1.0f-rollFriction);
    }
    
   
}
