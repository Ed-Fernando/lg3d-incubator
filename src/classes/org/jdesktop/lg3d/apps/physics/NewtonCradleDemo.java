/*
 * NewtonCradleDemo.java
 */

package org.jdesktop.lg3d.apps.physics;

/**
 * A demo of damped springs and force propagation.
 * 
 */
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import javax.vecmath.Vector3f;
import javax.vecmath.Quat4f;
import java.util.HashSet;



public class NewtonCradleDemo extends Frame3D {
    public static void main(String[] args) {
        Frame3D f = new NewtonCradleDemo();

        f.changeEnabled(true);
        f.changeVisible(true);
    }

    PhysicsThread physThread;

    public NewtonCradleDemo() {
		// Icky flesh tone color
        SimpleAppearance ballColor = new SimpleAppearance(0.6f, 0.6f, 0.9f);
		// Creepy blood color
        SimpleAppearance boxColor = new SimpleAppearance(0.8f, 0.2f, 0.0f);

        
        LeapFrogIntegrator leapFrog=new LeapFrogIntegrator();
        Gravity gravity=new Gravity(5.81f);
        
        PhysicsSphere[] cradleSpheres=new PhysicsSphere[7];
        FixedPoint[] cradleAnchors=new FixedPoint[14];
        Spring[] cradleSprings=new Spring[14];
        Sphere[] cradleGraphics=new Sphere[7];
        Component3D[] cradleComponents=new Component3D[7];
        for(int i=0;i<7;i++) {
            cradleGraphics[i]=new Sphere(0.01f, ballColor);
            cradleComponents[i]=new Component3D();
            cradleComponents[i].addChild(cradleGraphics[i]);
            cradleSpheres[i]=new PhysicsSphere(0.01f, new Vector3f((i-3)*0.02f,0.0f,0.0f),1.1f,cradleComponents[i] ,leapFrog,0.95f,0.0f,0.0f);
            addChild(cradleComponents[i]);
            cradleAnchors[i]=new FixedPoint(new Vector3f((i-3)*0.02f,0.4f,0.003f));
            cradleAnchors[i+7]=new FixedPoint(new Vector3f((i-3)*0.02f,0.4f,-0.003f));
            
            cradleSprings[i]=new Spring(cradleAnchors[i],cradleSpheres[i], 0.403f, 205.9f,206.2f);
            cradleSprings[i+7]=new Spring(cradleAnchors[i+7],cradleSpheres[i], 0.403f, 205.9f,206.2f);
            
            gravity.addObject(cradleSpheres[i]);
            
        }
        setPreferredSize(new Vector3f(0.08f, 0.06f, 0.04f));
        cradleSpheres[0].setVelocity(new Vector3f(-0.15f,0.0f,0.0f));    

	physThread = new PhysicsThread(7,this);
 
	physThread.addEffector(gravity);
	for(int i=0;i<7;i++) {
	    physThread.addCollideable(cradleSpheres[i],cradleSpheres[i]);
	    physThread.addEffector(cradleSprings[i]);
	}
    }

    /**
     * Override to start physics thread when frame3d is enabled
     */
    public void changeEnabled(boolean enabled, int duration) {
	super.changeEnabled(enabled, duration);

	if (enabled)
	    physThread.start();
    }
}
