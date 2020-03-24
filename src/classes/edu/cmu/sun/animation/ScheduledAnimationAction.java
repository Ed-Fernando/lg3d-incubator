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
 * Allows for a custom animation to be added to an AnimationPlan.
 * 
 * This is a ScheduledAnimation which delegates it's animation responsibility
 * to another object.
 * 
 * @author Braden Kowitz
 */
public class ScheduledAnimationAction extends ScheduledAnimation {

	AnimationAction action;
	
	/**
	 * Create an ScheduledAnimation that will call an AnimationAction
	 * when the animation needs to be performed.
	 * 
	 * @param target Component to be animated.
	 * @param startTime Start time, in msec for this animation.
	 * @param duration The duration, in msec, for this animation.
	 * @param action The object to perform the animation on the target.
	 */
	public ScheduledAnimationAction(Component3D target, int startTime, int duration, AnimationAction action) {
		super(target, startTime, duration);
		this.action = action;
	}

	/**
	 * Calls the AnimationAction delegate to perform the animation.
	 */
	@Override
	public void doAnimation() {
		action.doAnimationAction(target, duration);
	}

}
