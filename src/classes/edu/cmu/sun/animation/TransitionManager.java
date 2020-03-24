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

import edu.cmu.sun.model.Model;

/**
 * Manages scene transitions after each user action.
 * 
 * After each user action, the TransitionManager is used as a central touch-point
 * to transition the scene to an updated state.  First, the Model components are
 * told to update their listeners. This causes the Views to add their update commands
 * to the AnimationPlans in the TransitionManager.  Finally, the TransitionManager
 * runs each of the three plans: preperation, transition, and consolidation.
 * 
 * @author Braden Kowitz
 */
public class TransitionManager
{
	private static AnimationPlan prepPlan;
	private static AnimationPlan transitionPlan;
	private static AnimationPlan consolidationPlan;
	
	/**
	 * An AnimationPlan meant to prepare the Scene for animation.
	 * 
	 * No movement should appear to happen during the prep plan.  This
	 * plan is often used to re-arrange the scene graph to ready it for
	 * a transition animation.
	 */
	public synchronized static AnimationPlan getPrepPlan()
	{
		if (prepPlan == null) prepPlan = new AnimationPlan();
		return prepPlan;
	}
	
	/**
	 * An AnimationPlan meant to visually transition the scene from one state to the next.
	 * 
	 * This is the bulk of what the user sees when an animation takes place.
	 * Most acctions are added to this plan.
	 */
	public synchronized static AnimationPlan getTransitionPlan()
	{
		if (transitionPlan == null) transitionPlan = new AnimationPlan();
		return transitionPlan;
	}
	
	/**
	 * An AnimationPlan meant to consolidate the scene after the transition.
	 * 
	 * Many times, an animation will require the scene graph to be structured in
	 * a specific way.  However, interaction may require the graph to be structured
	 * differently.  Because of this, the consolidation plan is used to handle any
	 * last-minute cleanup after the animation has completed.
	 */
	public synchronized static AnimationPlan getConsolidationPlan()
	{
		if (consolidationPlan == null) consolidationPlan = new AnimationPlan();
		return consolidationPlan;
	}
	
	/**
	 * Notifys all ModelListeners, then runs though the Prep, Transition and Consolidation plans.
	 *
	 * Since Scenegraph updates are expensive, we only update the scene graph right before
	 * we are ready to do a transition.  So, each Model is asked to send a modelChanded() event.
	 * Then, the various AnimationPlans are run in oder.
	 */
	public synchronized static void updateViews()
	{
		Model.notifyAllListeners();
		
		runPrepPlan();

		runTransitionPlan();

		runConsolidationPlan();
	}
	
	/**
	 * Runs the AnimationPlan to prepare the scene for the transition.
	 */
	private static void runPrepPlan()
	{
		if (prepPlan != null) prepPlan.execute();
		prepPlan = null;
	}
	
	/**
	 * Runs the AnimationPlan that actuially performs the visual transition.
	 */
	private static void runTransitionPlan()
	{
		if (transitionPlan != null)
			{
				transitionPlan.execute();
			}
		transitionPlan = null;
	}
	
	/**
	 * Runs the AnimationPlan that cleans up after the transition.
	 * Often times, this involves re-building components to be interactive again.
	 */
	private static void runConsolidationPlan()
	{
		if (consolidationPlan != null) consolidationPlan.execute();
		consolidationPlan = null;
	}
}
