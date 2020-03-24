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
import edu.cmu.sun.model.ItemModel;
import edu.cmu.sun.model.WindowModel;

/**
 * A controller object for items in lists.
 * Every time a list item is clicked on, an instance of this
 * component is called to handle the event.  Right now,
 * we only look at left and right clicks.
 * 
 * @author Braden Kowitz, Jessica Smith
 */
public class ItemController {
	ItemModel model;
	
	public ItemController(ItemModel model)
	{
		this.model = model;
	}

	/**
	 * A left click causes the list item to be selected,
	 * and all of the sibling items to be deslected.
	 * 
	 * If the item is already selected, the controller just
	 * deselects the items.
	 */
	public void leftClick() {
		if (model.isSelected()) model.setSelected(false);
		else
		{
			model.deselectSiblings();
			model.setSelected(true);
		}
		// do an update right away to see highlight
		TransitionManager.updateViews();

		// then handle the windows opening.
		updateChildWindows();
	}

	/**
	 * A right click causes the list itme to be selected.
	 * All siblings retain their selection state.
	 *  
	 * If the item is already selected, the controller just
	 * deselects the items.
	 */
	public void rightClick() {
		if (model.isSelected()) model.setSelected(false);
		else model.setSelected(true);
		// do an update right away to see highlight
		TransitionManager.updateViews();

		// then handle the windows opening.
		updateChildWindows();
	}
	
	/**
	 * When an item is selected/deselected, we must create/remove
	 * windows from the next column to the right.  This call just 
	 * passes the responsibility for this update to teh Model.
	 */
	private void updateChildWindows()
	{
		WindowModel winModel = model.getParentListModel().getWindowModel();
		if (winModel == null)
		{
			System.err.println("can't update children windows because window model cannot be found.");
			return;
		}
		winModel.updateChildrenWindows();
		TransitionManager.updateViews();
	}

	
}
