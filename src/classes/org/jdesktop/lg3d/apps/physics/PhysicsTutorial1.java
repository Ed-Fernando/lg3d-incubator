package org.jdesktop.lg3d.apps.physics;

import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import javax.vecmath.Vector3f;
import javax.vecmath.Quat4f;


public class PhysicsTutorial1 extends Frame3D {
    public static void main(String[] args) {
        Frame3D f = new PhysicsTutorial1();

        f.changeEnabled(true);
        f.changeVisible(true);
    }

    PhysicsThread physThread;

    public PhysicsTutorial1() {
        /* First we create some graphics for the application, this is no different from how you do it otherwise. */
          
        SimpleAppearance brickColor = new SimpleAppearance(0.6f, 0.2f, 0.2f);
        Component3D boardComp=new Component3D();
        Component3D middleComp=new Component3D();
        Component3D smallComp=new Component3D();
        Component3D bigComp=new Component3D();
        Component3D ballComp=new Component3D();
        
        Box middleBox=new Box(0.02f,0.02f,0.03f,brickColor);
        Box boardBox=new Box(0.10f,0.005f,0.035f,brickColor);
        Box bigBox=new Box(0.025f,0.025f,0.025f,brickColor);
        Box smallBox=new Box(0.012f,0.012f,0.012f,brickColor);
        
        
        boardComp.addChild(boardBox);
        middleComp.addChild(middleBox);
        bigComp.addChild(bigBox);
        smallComp.addChild(smallBox);
        
        
        addChild(boardComp);
        addChild(middleComp);
        addChild(smallComp);
        addChild(bigComp);
        /* Now the graphics (if you can call some redish boxes graphics) are all ready to go */ 
        
        /* Create a thread to handle the simulation */
        physThread=new PhysicsThread(15, this);
        	/* The 15 is how many times any contacts will be resolved during each frame. The higher the number the slower and more stable the simulation */
        
        /* Let's create an integrator for the objects. */
        LeapFrogIntegrator leapFrog=new LeapFrogIntegrator();
        
        /* We want some gravity, but we want a weak one for nicer movement. */
        Gravity gravity=new Gravity(.4f);
        
        
        
        /* Let's create the corresponding physical boxes to the graphical ones we created earlier */
        PhysicsBox board=new PhysicsBox(0.10f,0.005f,0.035f,.2f, new Vector3f(0.00f,0.0425f,-0.06f), new Quat4f(0,1,0,0 ),  boardComp, leapFrog, 0.02f, 0.5f);
        PhysicsBox middle=new PhysicsBox(0.02f,0.02f,0.03f,0.3f, new Vector3f(0.0f,0.00f,-0.06f), new Quat4f(0,1,0,0),  middleComp, leapFrog, 0.02f, 0.8f);
        PhysicsBox small=new PhysicsBox(0.012f,0.012f,0.012f,.05f, new Vector3f(-0.05f, 0.110f,-0.06f), new Quat4f(0,1,0,0),  smallComp, leapFrog, 0.2f, 0.5f);
        PhysicsBox big=new PhysicsBox(0.025f,0.025f,0.025f,1.2f, new Vector3f(0.08f,0.157f,-0.06f), new Quat4f(0,1,0,0),  bigComp, leapFrog, 0.2f, 0.5f);
        
        gravity.addObject(board);
        gravity.addObject(middle);
        gravity.addObject(small);
        gravity.addObject(big);
        
        PhysicsPlane plane=new PhysicsPlane(new Vector3f(0,1,0),new Vector3f(0,-0.04f,0), 0.02f,0.5f);
       
        physThread.addCollideable(plane,plane);
        physThread.addCollideable(big,big);
        physThread.addCollideable(middle,middle);
        physThread.addCollideable(board,board);
        physThread.addCollideable(small,small);   
        
        physThread.addEffector(gravity);
      
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
