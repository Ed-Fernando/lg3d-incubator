package org.jdesktop.lg3d.apps.physics;
import javax.vecmath.Vector3f;
    /**
     * A class used to model fixed points in a physics simulation.
     * Can be used to attach moving objects to fixed coordinates with damped springs.
     */
    
public class FixedPoint extends PhysicsObject {
    
    /**
     * Constructs a new fixed point.
     * @param point the coordinates for the fixed point.
     */
    public FixedPoint(Vector3f point) {
        this.velocity=new Vector3f();
        this.force=new Vector3f();
        this.position=new Vector3f(point);
        this.invMass=0.0f;
        
    } 
    /**
     * Dummy function to prevent the fixed point to be moved around by the simulation.
     */
    
    public void update() {
        //a fixed point is fixed and not updated, ever.
    }

   
}
