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

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Component3D;

/**
 * Adjusts the scale of a Component3D.
 * 
 * @author Braden Kowitz
 */
public class ScaleAnimation extends ScheduledAnimation {

	Vector3f scale;
	boolean change;
	
	/**
	 * Creates a new action to call changeScale();
	 * @param target Component to be called as a targt
	 * @param startTime Time this animation should start, in msec
	 * @param duration The duration of this animation, in msec
	 * @param scale Scaling vector to be applied as the animation.
	 */
	public ScaleAnimation(Component3D target, int startTime, int duration, Vector3f scale) {
		super(target, startTime, duration);
		this.scale = scale;
		change = true;
	}
	
	/**
	 * Creates a new action to call setScale();
	 * @param target Component to be called as a targt
	 * @param startTime Time this animation should start, in msec
	 * @param scale Scaling vector to be applied as the animation.
	 */
	public ScaleAnimation(Component3D target, int startTime, Vector3f scale) {
		super(target, startTime, 0);
		this.scale = scale;
		change = false;
	}
	
	public void doAnimation() {
		if (change) target.changeScale(scale, duration);
		else target.setScale(scale.x, scale.y, scale.z);
	}

}
