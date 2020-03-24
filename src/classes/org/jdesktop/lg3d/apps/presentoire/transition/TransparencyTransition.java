package org.jdesktop.lg3d.apps.presentoire.transition;

import org.jdesktop.lg3d.wg.Component3D;

/**
 * A simple Transition : use translucency to hide one slide and show the new one...
 * @author Pierre Ducroquet
 *
 */
public class TransparencyTransition extends Transition {
	
	/**
	 * Rescale the opacity of the Component3D "comp" from opacity "from" to opacity "to" during about "duration" milliseconds. 
	 * @param comp the component3D to rotate
	 * @param from the start opacity
	 * @param to the destination opacity
	 * @param duration the duration of the rotation
	 */
	public void transparency (Component3D comp, float from, float to, long duration) {
    	float opac = from;
    	float diff = (to - from) / duration;
    	int factor = (diff < 0) ? -1 : 1;
    	while (factor*opac < to) {
    		comp.changeTransparency(opac);
    		opac = opac + diff;
    		try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	comp.changeTransparency(to);
	}
	
	/**
	 * Run the transition...
	 */
	public void runTransition(Component3D mainComponent,
			Component3D slideContainer, Component3D nextSlide) {
		transparency(slideContainer, 0.0f, 1.0f, 100);
		slideContainer.removeAllChildren();
		slideContainer.addChild(nextSlide);
		transparency(slideContainer, 1.0f, 0.0f, 100);
	}

}
