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
 * An interface for any object which can perform an animation action.
 * This interface is used in ScheduledAnimationAction to allow a class
 * to be called back when it is time to perform an animation.
 * @author Braden Kowitz
 */
public interface AnimationAction {

	/**
	 * The implementing class shoud perform an animation on the
	 * provided target, for a specified duration.
	 * This function should perform animations immediately on
	 * the target object.
	 * @param target The target Component3D to be animatied
	 * @param duration The duration of the animation, in msec.
	 */
	public void doAnimationAction(Component3D target, int duration);
	
}
