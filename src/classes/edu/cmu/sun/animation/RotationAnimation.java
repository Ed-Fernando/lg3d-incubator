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
 * Adjusts the rotation of a target Component3D.
 * 
 * Rotation is accomplished first by setting the rotation axis, then
 * by rotating a specified number of degrees.
 * 
 * @author Braden Kowitz
 */
public class RotationAnimation extends ScheduledAnimation {

	Vector3f axis;
	float angle;
	boolean change;
	
	/**
	 * Creates a new action to call changeRotation();
	 * @param target Component to be called as a targt
	 * @param startTime Time this animation should start, in msec
	 * @param duration The duration of this animation, in msec
	 * @param axis The axis of rotation (yaw, pitch, roll) in radians from X-axis.
	 * @param angle The angle of rotation, in radians
	 */
	public RotationAnimation(Component3D target, int startTime, int duration, Vector3f axis, float angle) {
		super(target, startTime, duration);
		this.axis = axis;
		this.angle = angle;
		change = true;
	}
	
	/**
	 * Creates a new action to call setRotation();
	 * @param target Component to be called as a targt
	 * @param startTime Time this animation should start, in msec
	 * @param axis The axis of rotation (yaw, pitch, roll) in radians from X-axis.
	 * @param angle The angle of rotation, in radians
	 */
	public RotationAnimation(Component3D target, int startTime, Vector3f axis, float angle) {
		super(target, startTime, 0);
		this.axis = axis;
		this.angle = angle;
		change = false;
	}
	

	public void doAnimation() {
		target.setRotationAxis(axis.x, axis.y, axis.z);
		if (change) target.changeRotationAngle(angle, duration);
		else target.setRotationAngle(angle);
	}

}
