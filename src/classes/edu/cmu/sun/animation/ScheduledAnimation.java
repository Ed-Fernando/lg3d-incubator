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
 * An abstract class representing an animation action.
 * The animation can be scheduled to take place at a specific time,
 * and last for a specified duration. 
 * 
 * Additionally, this class is compariable against it's start Time.
 * 
 * @author Braden Kowitz
 */
public abstract class ScheduledAnimation implements Comparable<ScheduledAnimation>{

	Component3D target;
	int startTime;
	int duration;
	
	/**
	 * Create a new ScheduledAnimation.  This should be added to an AnimationPlan.
	 * @param target Component to be animated.
	 * @param startTime Start time, in msec for this animation.
	 * @param duration The duration, in msec, for this animation.
	 */
	public ScheduledAnimation(Component3D target, int startTime, int duration)
	{
		this.target = target;
		this.startTime = startTime;
		this.duration = duration;
	}
	
	/**
	 * The time, in msec from the start, this animation is to be performed
	 * @return Time in msec
	 */
	public int getStartTime()
	{
		return startTime;
	}
	
	/**
	 * Performs the animation required for this ScheduledAnimation.
	 * Returns as soon as possible (nonblocking)
	 */
	public abstract void doAnimation();

	/**
	 * Used to sort ScheduledAnimation objects by their start time.
	 * @param sa
	 * @return 0 if same-time, -1 if earlier, 1 if later.
	 */
	public int compareTo(ScheduledAnimation sa) {
		if (startTime == sa.startTime) return 0;	
		else if (startTime < sa.startTime) return -1;
		else return 1;
	}
	
	/**
	 * The time  the animation will be done.
	 * @return in msec.
	 */
	public long getEndTime() {
		return startTime + duration;
	}
}
