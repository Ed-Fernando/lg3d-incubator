/*
 * 3D File Manager - Project Looking Glass 
 * Copyright Sun Microsystems, 2005
 * 
 * Project Course in Human-Computer Interaction
 * Carnegie Mellon University
 * 
 * Aditya Chand, Braden Kowitz, Jake Pierson, Jessica Smith
 */

package edu.cmu.sun.animation;

import org.jdesktop.lg3d.wg.Component3D;


/**
 * Adjusts the visibility of a target Component3D.
 *
 * @author Braden Kowitz
 */
public class VisibilityAnimation extends ScheduledAnimation {

	boolean visible;
	
	/**
	 * Creates a new action to call setVisibility();
	 * @param target Component to be called as a targt
	 * @param startTime Time this animation should start, in msec
	 * @param visible True if the component is to be visible, faslt for invisible.
	 */
	public VisibilityAnimation(Component3D target, int startTime, boolean visible) {
		super(target, startTime, 0);
		this.visible = visible;
	}
	
	public void doAnimation() {
		target.setVisible(visible);
	}

}
