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
import edu.cmu.sun.folds.IndexRange;
import edu.cmu.sun.folds.LayoutComponent;

/**
 * Handles all click events for folded scrolling.
 * 
 * Each fold in the window listens to its own click events and 
 * passes the information to this conrtorller, which is responsible
 * for updating the model to scroll the list correctly.
 * 
 * The controller makes a distinction between three types of foldes
 * to sroll correctly.  Clicks directly above and below the main portion
 * of the list act differently than click on other folds that are seperated
 * from the main list.
 * 
 * @author Braden Kowitz
 */
public class FoldViewController {

	/**
	 * The current layout component for the list that this 
	 * controller is using.  The layout contains the Model which
	 * we need to access.
	 */
	private LayoutComponent layout;
	
	/**
	 * Internal variable to store the range of the main portion of the list. 
	 */
	private IndexRange primaryRange;
	
	/**
	 * DIRECTLY_ABOVE is the fold that is directly above the main list portion.
	 * DIRECTLY_BELOW is the fold that is directly below the main list portion.
	 * SEPERATE are any other folds in the list.
	 */
	private enum Position {SEPERATE, DIRECTLY_ABOVE, DIRECTLY_BELOW};
	
	/**
	 * The detected position for the fold of interest for a click.
	 */
	private Position position = Position.SEPERATE;
	
	
	/**
	 * Create a new fold controller to handle mouse clikc events for folds.
	 * @param layout The layout that represnets the list under control.
	 * 	The LayoutComponent has a pointer to greater Layout context.
	 */
	public FoldViewController(LayoutComponent layout)
	{
		this.layout = layout;
	}
	
	/**
	 * Move the list to the top of the current fold.
	 */
	private void gotoTopOfFold()
	{
		int index = layout.getRange().getStart();
		layout.getModel().getWindowModel().setIndexOfInterest(index);
	}
	
	/**
	 * Move the list to the bottom of the current view.
	 */
	private void gotoBottomOfFold()
	{
		int index = layout.getRange().getEnd();
		layout.getModel().getWindowModel().setIndexOfInterest(index);
	}

	
	/**
	 * Scroll the main portion of the list down by one page.
	 * The bottom item in the list will now be the top item.
	 */
	private void scrollDown() {
		if (primaryRange == null)
		{
			System.err.println("ERR: Can't scroll w/o a valid range");
			return;
		}

		int newPrimaryIndex = primaryRange.getEnd();
		layout.getModel().getWindowModel().setIndexOfInterest(newPrimaryIndex);
	}

	/**
	 * Scroll the main portion of the list up by one page.
	 * The top item in the list will now be the bottom item
	 */
	private void scrollUp() {
		if (primaryRange == null)
		{
			System.err.println("ERR: Can't scroll w/o a valid range");
			return;
		}
		
		int newPrimaryIndex = primaryRange.getStart() - primaryRange.size() + 1;
		if (newPrimaryIndex < 0) newPrimaryIndex = 0;
		layout.getModel().getWindowModel().setIndexOfInterest(newPrimaryIndex);
	}
	
	/**
	 * Discovers the type of fold (described above) that this component is.
	 * It stores the type of fold in the position field.
	 */
	private void discoverPositionType()
	{
		position = Position.SEPERATE;
		primaryRange = null;
		int primaryIndex = layout.getModel().getWindowModel().getIndexOfInterest();
		
		LayoutComponent prevSibling = layout.getPreviousSibling();
		if (prevSibling != null && prevSibling.getRange().encompassesIndex(primaryIndex))
		{
			position = Position.DIRECTLY_BELOW;
			primaryRange = prevSibling.getRange();
		}
		
		LayoutComponent nextSibling = layout.getNextSibling();
		if (nextSibling != null && nextSibling.getRange().encompassesIndex(primaryIndex))
		{
			position = Position.DIRECTLY_ABOVE;
			primaryRange = nextSibling.getRange();
		}
	}
	
	/**
	 * Handles click events on the center portion of the fold.
	 */
	public void clickOnFold() {
		discoverPositionType();
		if (position == Position.DIRECTLY_ABOVE) scrollUp();
		else if (position == Position.DIRECTLY_BELOW) scrollDown();
		else gotoTopOfFold();
		activateWindowAndUpdate();
	}

	/**
	 * Handles click events on the right portion of the fold.
	 * This side corresponds to the end of list in most cases.
	 */
	public void clickOnEnd() {
		discoverPositionType();
		if (position == Position.DIRECTLY_ABOVE) scrollUp();
		else if (position == Position.DIRECTLY_BELOW) gotoBottomOfFold();
		else gotoBottomOfFold();
		activateWindowAndUpdate();
	}

	/**
	 * Handles click events on the left portion of the fold.
	 * This side corresponds to the begining of list in most cases.
	 */
	public void clickOnStart() {
		discoverPositionType();
		if (position == Position.DIRECTLY_ABOVE) gotoTopOfFold();
		else if (position == Position.DIRECTLY_BELOW) scrollDown();
		else gotoTopOfFold();
		activateWindowAndUpdate();
	}

	/**
	 * Makes this window the active window in the column, and updates
	 * the TransitionManager.  By making the window the active window,
	 * we make more room in the column to show the items under the fold.
	 */
	private void activateWindowAndUpdate() {
		layout.getModel().getWindowModel().makeActive();
		TransitionManager.updateViews();
	}
	

}
