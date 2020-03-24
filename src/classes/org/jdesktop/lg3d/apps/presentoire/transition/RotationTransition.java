package org.jdesktop.lg3d.apps.presentoire.transition;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Component3D;

/**
 * A simple Transition : a rotation...
 * @author Pierre Ducroquet
 *
 */
public class RotationTransition extends Transition {

	/**
	 * Rotate the Component3D "comp" from angle "from" to angle "to" during about "duration" milliseconds. 
	 * @param comp the component3D to rotate
	 * @param from the start angle
	 * @param to the destination angle
	 * @param duration the duration of the rotation
	 */
	public void rotate (Component3D comp, float from, float to, long duration) {
		System.out.println("Rotating...");
    	float angle = from;
    	float diff = (to - from) / duration;
    	while (angle < to) {
    		comp.changeRotationAngle(angle);
    		angle = angle + diff;
    		try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	comp.changeRotationAngle(to);
	}
	
	/**
	 * Run the transition...
	 */
	public void runTransition(Component3D mainComponent,
			Component3D slideContainer, Component3D nextSlide) {
		System.out.println("Executing...");
		slideContainer.changeRotationAxis(new Vector3f(0.0f, 1.0f, 0.0f));
		slideContainer.changeRotationAngle(0.0f);
		rotate(slideContainer, 0.0f, 3.14f, 100);
		slideContainer.removeAllChildren();
		slideContainer.addChild(nextSlide);
		rotate(slideContainer, 3.14f, 6.28f, 100);
		slideContainer.changeRotationAngle(0.0f);
	}

}
