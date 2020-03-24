package org.jdesktop.lg3d.apps.presentoire.transition;

import org.jdesktop.lg3d.wg.Component3D;

/**
 * A simple Transition : a scale...
 * @author Pierre Ducroquet
 *
 */
public class ScaleTransition extends Transition {
	
	/**
	 * Rescale the Component3D "comp" from scale "from" to scale "to" during about "duration" milliseconds. 
	 * @param comp the component3D to rotate
	 * @param from the start scale
	 * @param to the destination scale
	 * @param duration the duration of the rotation
	 */
	public void rescale (Component3D comp, float from, float to, long duration) {
    	float scale = from;
    	float diff = (to - from) / duration;
    	int factor = (diff < 0) ? -1 : 1;
    	while (factor*scale < to) {
    		comp.changeScale(scale);
    		scale = scale + diff;
    		try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	comp.changeScale(to);
	}
	
	/**
	 * Run the transition...
	 */
	public void runTransition(Component3D mainComponent,
			Component3D slideContainer, Component3D nextSlide) {
		slideContainer.changeScale(1.0f);
		rescale(slideContainer, 1.0f, 0.0f, 100);
		slideContainer.removeAllChildren();
		slideContainer.addChild(nextSlide);
		rescale(slideContainer, 0.0f, 1.0f, 100);
		slideContainer.changeScale(1.0f);
	}
}