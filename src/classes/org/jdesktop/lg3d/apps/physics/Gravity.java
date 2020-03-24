package org.jdesktop.lg3d.apps.physics;

import javax.vecmath.Vector3f;
import java.util.Vector;

/**
 * A class used to model gravity in a simulation.
 * Will apply a force along the negative y-axis that is proportional to the objects mass to each object it affects.
 **/

public class Gravity extends Effector {
    private float g;

    /**
     * Creates a new gravity effector.
     * @param g the gravitational constant to use.
     **/
    
    public Gravity(float g) {
        super();
        this.g=g;
    }
    
    /**
     * Creates a new gravity effector with earth like gravitational constant.
     *
     **/
    
    public Gravity() {
        this(9.81f);
    }
    
    /**
     * Applies the gravitational force to all affected objects.
     */
    
    public void apply() {
        for(int i=0;i<objects.size();i++) {
            objects.get(i).addForce(new Vector3f(0.0f,-g*objects.get(i).getMass(),0.0f));
        }
    }
}
