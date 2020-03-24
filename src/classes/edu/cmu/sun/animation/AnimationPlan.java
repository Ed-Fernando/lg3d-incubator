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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Component3D;

/**
 * Schedules animation on Component3D objects.
 * When the user interacts with a system, the response should be a smooth animation.
 * However, not all animations can start right away.  Sometimes we need to start
 * an animation later on in the scene, and sometiems we need to run many animations
 * at the same time so that components appear to be moving in relation to one another.
 * This class allows the user to schdule animations to happen at specified times.
 * AnimationPlan then calls methods on Component3D at the time requested to produce
 * the entire animation.
 * 
 * @see edu.cmu.sun.animation.TransitionManager
 * 
 * @author Braden Kowitz
 */
public class AnimationPlan {

	/**
	 * A list of animations that will be executed when the plan is run.
	 */
	List<ScheduledAnimation> animations;
	
	/**
	 * A list of cleanup actions for this plan.
	 * After the plan is executed, cleanup actions are taken.
	 */
	List<CleanupAction> cleanupActions;
	
	/**
	 * Create a new animation plan which is initially empty.
	 */
	public AnimationPlan()
	{
		animations = new ArrayList<ScheduledAnimation>();
		cleanupActions = new ArrayList<CleanupAction>();
	}
	
	/**
	 * Add a ScheduledAnimation object to the plan.
	 * The plan will execute the animations with the same time in the order
	 * they are added.  That is, processing is done FIFO.
	 * @param sa
	 */
	public void add(ScheduledAnimation sa)
	{
		animations.add(sa);
	}
	
	/**
	 * Runs each ScheduledAnimation and all CleanupActions.
	 * Runs through the list of SchdueledAnimations, calling doAnimation()
	 * on each one. All animations with start times of zero are animated
	 * first.  Then, the list of ScheduledAnimations is sorted by start time and
	 * called in order.  Finally, the function waits till the end of the animtion
	 * to return.  This is a <strong>blocking function call</strong>.
	 * 
	 * Also, this fucntion leaves the AnimationPlan object in a unknown state.
	 * <strong>This function should only be called once for each AnimationPlan.</strong>
	 */
	public void execute()
	{

		// calculate the time of the end of the animation
		long endTime = 0;
		for (final ScheduledAnimation animation : animations)
		{
			long end = animation.getEndTime();
			if (end > endTime) endTime = end;
		}
		
		// perform all animations with star-time = zero 
		doZeroTimedAnimations();
		
		// sort by time (prserves ordering)
		Collections.sort(animations);
		
		// perform the animation
		long startTime = System.currentTimeMillis();
		for (final ScheduledAnimation animation : animations)
		{
			waitTill(animation.getStartTime(), startTime);
			animation.doAnimation();
		}
		
		// do any remaining cleanup actions
		doCleanup();
		
		// wait till the end of the animation to return.
		waitTill(endTime, startTime);
	}
	
	/**
	 * Causes this thread to sleep until time provided matched
	 * the elapsed time since the provided start time.
	 * 
	 * @param time Time to wait till, in msec
	 * @param startTime the currentTimeMillis() on the clock at the
	 * 	start of the animation.
	 */
	private void waitTill(long time, long startTime)
	{
		long elapsedTime = System.currentTimeMillis() - startTime;
		long delay = time - elapsedTime;
		if (delay > 0)
		{
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Performs all animations with a startTime of zero.
	 * Removes these animations from the animations list.
	 */
	private void doZeroTimedAnimations()
	{
		Iterator<ScheduledAnimation> it = animations.iterator();
		while (it.hasNext())
		{
			ScheduledAnimation anim = it.next();
			if (anim.getStartTime() == 0)
			{
				it.remove();
				anim.doAnimation();
			}
		}
	}
	
	/**
	 * Performs all of the cleanup actions, leaving them in the list.
	 *
	 *	@see CleanupAction
	 */
	public void doCleanup() {
		for (final CleanupAction item : cleanupActions)
		{
			item.hide();
		}
		for (final CleanupAction item : cleanupActions)
		{
			item.cleanup();
		}
	}

	/**
	 * Convenience method to change (animate) the translation of a component.
	 * @param target Component to be affected by this animation
	 * @param start Start time of the animation, in milliseconds from the start.
	 * @param duration Duration of the animation, in milliseconds
	 * @param vec Translation vector to be applied to the component
	 */
	public void changeTranslation(Component3D target, int start, int duration, Vector3f vec)
	{
		animations.add(new TranslationAnimation(target, start, duration, vec));
	}
	
	/**
	 * Convenience method to change (animate) the scale of a component.
	 * @param target Component to be affected by this animation
	 * @param start Start time of the animation, in milliseconds from the start.
	 * @param duration Duration of the animation, in milliseconds
	 * @param vec Scale vector to be applied to the component
	 */
	public void changeScale(Component3D target, int start, int duration, Vector3f vec)
	{
		animations.add(new ScaleAnimation(target, start, duration, vec));
	}
	
	/**
	 * Convenience method to change (animate) the scale of a component.
	 * @param target Component to be affected by this animation
	 * @param start Start time of the animation, in milliseconds from the start.
	 * @param duration Duration of the animation, in milliseconds
	 * @param scale A uniform scaling factor.
	 */
	public void changeScale(Component3D target, int start, int duration, float scale)
	{
		changeScale(target, start, duration, new Vector3f(scale,scale,scale));
	}
	
	
	/**
	 * Convenience method to set (no animation) the translation of a component.
	 * @param target Component to be affected by this animation
	 * @param start Start time of the animation, in milliseconds from the start.
	 * @param vec Translation vector to be applied to the component
	 */
	public void setTranslation(Component3D target, int start, Vector3f vec)
	{
		animations.add(new TranslationAnimation(target, start, vec));
	}
	
	/**
	 * Convenience method to set (no animation) the scale of a component.
	 * @param target Component to be affected by this animation
	 * @param start Start time of the animation, in milliseconds from the start.
	 * @param vec Scale vector to be applied to the component
	 */
	public void setScale(Component3D target, int start, Vector3f vec)
	{
		animations.add(new ScaleAnimation(target, start, vec));
	}
	
	/**
	 * Convenience method to set (no animation) the visibility of a component.
	 * @param target Component to be affected by this animation
	 * @param start Start time of the animation, in milliseconds from the start.
	 * @param visibility True for visible, False for invisible.
	 */
	public void setVisible(Component3D target, int start, boolean visibility)
	{
		animations.add(new VisibilityAnimation(target, start, visibility));
	}
	
	/**
	 * Convenience method to change (animate) the rotation of a component.
	 * @param target Component to be affected by this animation
	 * @param start Start time of the animation, in milliseconds from the start.
	 * @param duration Duration of the animation, in milliseconds
	 * @param axis the axis of rotation (Yaw, Pitch, Roll) in Radians from X-axis
	 * @param angle the angle of rotation in radians.
	 */
	public void changeRotation(Component3D target, int start, int duration, Vector3f axis, double angle)
	{
		animations.add(new RotationAnimation(target, start, duration, axis, (float) angle));
	}
	
	/**
	 * Adds a cleanup action to ths animaion.
	 * After the animation is done executing, all of the cleanup actions are called.
	 * 
	 * TODO can ths be made private?
	 * 
	 * @param itemToCleanup
	 * @param parentOfItem
	 */
	public void addCleanup(Component3D itemToCleanup, Component3D parentOfItem)
	{
		CleanupAction clean = new CleanupAction(itemToCleanup, parentOfItem);
		cleanupActions.add(clean);
	}

	/**
	 * Adds a component to the scene at the specified time.
	 * 
	 * First, it makes the component invisible.  Then it adds the component
	 * to the scene graph.  Finally, at the specified time in the animation,
	 * the component is made visible again.  This way all component added
	 * as the result of a user action will appear on the scereen at
	 * the same time.
	 * 
	 * @param target Component to be added to the scene graph
	 * @param msec Starrt time, in msec, that the component should become visible
	 * @param parent Component to which the target is added.
	 */
	public void addComponent(Component3D target, int msec, Component3D parent)
	{
		addComponent(target, msec, parent, true);
	}

	/**
	 * Adds a component to the scene as invisible, later the visiblity is set.
	 * 
	 * First, this makes the target component invisible and adds it to the scenegraph.
	 * Then, at the specifeid time the visibility is set as specified.
	 * (the component may remain invisible)
	 * 
	 * @param target Component to be added to the scene graph
	 * @param msec Starrt time, in msec, that the component should become visible
	 * @param parent Component to which the target is added.
	 * @param visible
	 */
	public void addComponent(Component3D target, int msec, Component3D parent, boolean visible) {
		target.setVisible(false);
		parent.addChild(target);
		if (visible) setVisible(target,msec, true);
	}
	
	/**
	 * Removes a component from the scene graph at the specified time.
	 * First, the component is made invisible at the specified time.  Then, after
	 * the animation is complete, the component is removed from the scene graph.
	 * @param target Component to be removed from the scene graph
	 * @param msec Starrt time, in msec, that the component should become invisible
	 * @param parent Component to which the target is removed.
	 */
	public void removeComponent(Component3D target, int msec, Component3D parent)
	{
		setVisible(target,msec, false);
		addCleanup(target, parent);
	}

	/**
	 * Allows for custom animations to be added to the plan.
	 * Any object that provides an AnimationAction interface can be called
	 * to perform an animation at the specifeid time.
	 * 
	 * @param target Component to be animated
	 * @param start Star time of the animation, in msec
	 * @param duration Duration of the animation, in msec
	 * @param action AnimationAction to be performed on the target.
	 */
	public void addAnimationAction(Component3D target, int start, int duration, AnimationAction action) {
		animations.add(new ScheduledAnimationAction(target, start, duration, action));
	}


	
}
