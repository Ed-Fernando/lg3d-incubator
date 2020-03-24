package org.jdesktop.lg3d.apps.physics;

import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import javax.vecmath.Vector3f;
import javax.vecmath.Quat4f;

import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;

public class PhysicsTutorial2 extends Frame3D {
/*
 * We create a action class to add some interactivity to a simuation. 
 * This is not my strong side, if you want to know more about actions and listeners check a tutorial about that.
 * This class will be used when we catch clicks on a sphere and push a physical sphere around.
 */
     private class clickAction implements ActionNoArg {
        private InteractiveEffector effector;
        private PhysicsSphere sphere;
        public clickAction(InteractiveEffector effector) {
            this.effector=effector;
            
        } 
        
        public void performAction(LgEventSource source) {
           //This vector is the push we are adding to the sphere.
           Vector3f force=new Vector3f(0.5f,0.0f, -0.3f);
           effector.setCurrentForce(force);

        }
    }     
    public static void main(String[] args) {
        Frame3D f = new PhysicsTutorial2();

        f.changeEnabled(true);
        f.changeVisible(true);
    }
  
    PhysicsThread physThread;

    public PhysicsTutorial2() {
        /* Create graphics */
        SimpleAppearance blueColor = new SimpleAppearance(0.2f, 0.1f, 0.8f);
        Component3D sphereComp=new Component3D();
        Sphere sphere=new Sphere(0.02f, blueColor);
        
         /* Create an interactive effector. This is a simple effector to add an interactive force to objects. */
        InteractiveEffector interactive=new InteractiveEffector(6.0f, 0.0f);
        
        /*  Hook up a listener to the graphical spheres component.  */
        /*  This will make the force in the interactive effector change when the sphere is clicked */
        sphereComp.addListener(new MouseClickedEventAdapter(new clickAction(interactive)));
        sphereComp.setMouseEventPropagatable(true);
        
        
        sphereComp.addChild(sphere);
        addChild(sphereComp);
        
         /* Create physics thread */
        
         physThread=new PhysicsThread(5, this);
         
         /* Let's create an integrator for the objects. */
         LeapFrogIntegrator leapFrog=new LeapFrogIntegrator();
        
         /* We want some gravity, but we want a weak one for nicer movement. */
         Gravity gravity=new Gravity(4.9f);
         /* Create a physical sphere with the given radius, position, weight, orientation, restitution, friction and rolling friction */
         PhysicsSphere physicalSphere=new PhysicsSphere(0.2f,  new Vector3f(0.03f,0.0f,0.05f),0.2f, sphereComp, leapFrog,  0.5f, 0.5f, 0.001f);
         /* Create a fix point to anchor the spring holding the sphere */
         FixedPoint anchor=new FixedPoint(new Vector3f(0, 0.2f, 0f));
         /* Hook up a spring between the spring and the point. A word of warning on damped springs, they are very sensetive so you might have to calibrate your constants. */
         Spring spring = new Spring(physicalSphere, anchor, 0.20f, 55.4f, 23.2f);
         
         /* Add the sphere to the gravity and interactive effectors */
         interactive.addObject(physicalSphere);
         gravity.addObject(physicalSphere);
         
         
         /* Add stuff to the thread */
         physThread.addEffector(spring);
         physThread.addEffector(interactive);
         physThread.addEffector(gravity);
         
         physThread.addCollideable(physicalSphere,physicalSphere);
         
         
         /* set up frame */
         setPreferredSize(new Vector3f(0.08f, 0.06f, 0.04f));
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
