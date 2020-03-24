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
 * This action removes component from a scenegraph.
 * 
 * Removing components can be tricky because it can take a nontrivial amount of time,
 * and you may want to remove many components at the same time.  In order to acomplish
 * this effect, we perform the component removal in two steps.  First, the component
 * is hidden, then on the second pass, the component is removed from the scene graph.
 * Because setting the visible state of a component is fast, all components appear
 * to disapear at the same time.
 * 
 * @author Braden Kowitz
 */
public class CleanupAction
{
	/**
	 * The child to be removed from the scene graph
	 */
	Component3D child;
	
	/**
	 * The parent, from which the child be removed.
	 */
	Component3D parent;
	
	/**
	 * Create a new cleanup action.
	 * @param itemToCleanup Component to be removed from the scenegraph.
	 * @param parentOfItem Parent component which will have a child removed.
	 */
	public CleanupAction(Component3D itemToCleanup, Component3D parentOfItem)
	{
		this.child = itemToCleanup;
		this.parent = parentOfItem;
	}

	/**
	 * Makes the component to be removed invisible.
	 */
	public void hide()
	{
		child.setVisible(false);
	}
	
	/**
	 * Removes the specified component from the scene graph.
	 */
	public void cleanup()
	{
		parent.removeChild(child);
	}

}
