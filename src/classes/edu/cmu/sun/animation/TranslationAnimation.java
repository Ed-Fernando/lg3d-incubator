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
 * Adjusts the translation of a target Component3D.
 *
 * @author Braden Kowitz
 */
public class TranslationAnimation extends ScheduledAnimation {

	Vector3f translation;
	boolean change;
	
	/**
	 * Creates a new action to call changeTranslation();
	 * @param target Component to be called as a targt
	 * @param startTime Time this animation should start, in msec
	 * @param duration The duration of this animation, in msec
	 * @param translation Translation vector to be applied during the animation.
	 */
	public TranslationAnimation(Component3D target, int startTime, int duration, Vector3f translation) {
		super(target, startTime, duration);
		this.translation = translation;
		change = true;
	}
	
	/**
	 * Creates a new action to call setTranslation();
	 * @param target Component to be called as a targt
	 * @param startTime Time this animation should start, in msec
	 * @param translation Translation vector to be applied during the animation.
	 */
	public TranslationAnimation(Component3D target, int startTime, Vector3f translation) {
		super(target, startTime, 0);
		this.translation = translation;
		change = false;
	}
	
	public void doAnimation() {
		if (change) target.changeTranslation(translation, duration);
		else target.setTranslation(translation.x, translation.y, translation.z);
	}

}
