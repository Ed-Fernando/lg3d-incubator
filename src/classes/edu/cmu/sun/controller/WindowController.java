/*
 * 3D File Manager - Project Looking Glass 
 * Copyright Sun Microsystems, 2005
 * 
 * Project Course in Human-Computer Interaction
 * Carnegie Mellon University
 * 
 * Aditya Chand, Braden Kowitz, Jake Pierson, Jessica Smith
 */



package edu.cmu.sun.controller;

import edu.cmu.sun.animation.TransitionManager;
import edu.cmu.sun.model.ColumnModel;
import edu.cmu.sun.model.WindowModel;

/**
 * Receives all actions from the WindoBarView and translates the
 * system's Models as needed.  This object is created by the WindowView
 * to handle events.
 * 
 * @author Braden Kowitz, Jessica Smith
 */
public class WindowController {

	/**
	 * The model obejct representing this window.
	 */
	WindowModel model;
	
	/**
	 * Create a new controller to affect changes in the provided model.
	 * @param model
	 */
	public WindowController(WindowModel model)
	{
		this.model = model;
	}

	/**
	 * Close this window.  Called when the red button is pressed.
	 */
	public void close() {
		model.close();
		TransitionManager.updateViews();
	}

	/**
	 * Unpin this window.  Called when the pin button is toggled off.
	 */
	public void unpin() {
		model.setPinned(false);
		TransitionManager.updateViews();
	}

	/**
	 * Pin this window. Called when the pin button is toggled on.
	 */
	public void pin() {
		model.setPinned(true);
		TransitionManager.updateViews();
	}

	/**
	 * Flip the window arround to the search mode.
	 * This method is not yet implemented.
	 */
	public void search() {
	}

	/**
	 * Make this window the active window in the column.
	 * This method is called when the window bar is clicked
	 * (other than the widgets).  The active window in a column
	 * takes up the primary amount of space.
	 */
	public void makeActive() {
		ColumnModel columnModel = model.getColumnModel();
		if (columnModel == null)
		{
			System.err.println("can't make window active, no column model found.");
			return;
		}
		model.makeActive();
		TransitionManager.updateViews();
	}
	
}
