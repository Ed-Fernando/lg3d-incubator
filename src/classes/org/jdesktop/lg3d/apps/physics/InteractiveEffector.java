package org.jdesktop.lg3d.apps.physics;

import javax.vecmath.Vector3f;
/**
 * Effector to add an interactive force to objects.
 * It contains a force vector that can be set that is added to the affected objects each time frame.
 * The force vector can be set to decay over time.
 *
 * @param multiple a multiple that is applied to the force vector each time it is set.
 * @param decay a multiple that is applied to the force vector each time has been applied to the obejcts.
 */

public class InteractiveEffector extends Effector {
    private Vector3f currentForce;
    private float decay;
    private float multiple;
            
    public InteractiveEffector(float multiple, float decay) {
        currentForce=new Vector3f();
        this.multiple=multiple;
        this.decay=decay;
    }
    
    public void apply() {
        for(int i=0;i<objects.size();i++) 
            objects.get(i).addForce(currentForce);
        currentForce.scale(decay);
        
    }
    
    public void setCurrentForce(Vector3f force) {
        currentForce.set(force);
        currentForce.scale(multiple);
        
    }
    
    
}
