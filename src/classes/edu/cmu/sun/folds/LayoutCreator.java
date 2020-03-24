/*
* 3D File Manager - Project Looking Glass 
* Copyright Sun Microsystems, 2005
* 
* Project Course in Human-Computer Interaction
* Carnegie Mellon University
* 
* Aditya Chand, Braden Kowitz, Jake Pierson, Jessica Smith
*/

package edu.cmu.sun.folds;


import java.util.List;

import edu.cmu.sun.model.ItemModel;
import edu.cmu.sun.model.ListModel;
import edu.cmu.sun.model.WindowModel;

/**
 * Creates new Layouts from a set of constraints.
 * 
 * When a folded scroll window needs to be shown, it must decide
 * where to place the folds.  This class takes a height constraint,
 * and creates a layout that shows as many items as possible.  The layout
 * represents where the folds and lists will be positioned.
 * 
 * This class helps with Fulid Negotiation between other lists.
 * For more information on this concept, see:<br>
 * A Negotiation Architecture for Fluid Documents<br>
 * Bay-Wei Chang, Jock D. Mackinlay, Polle T. Zellweger, Takeo Igarashi<br>
 * Xeroc PARC<br>
 * 
 * @see Layout, LayoutComponent
 * 
 * @author Braden Kowitz
 */
public class LayoutCreator
{
	/**
	 * the maximum number of items that any Layout may have.
	 */
	public static final int MAX_ITEMS_IN_LIST = 100;
	
	/**
	 * The minimum number of items that any layout may have.
	 */
	public static final int MIN_ITEMS_IN_LIST = 3;
	
	/**
	 * The window model that this object will do layout for.
	 * This is important becasue the Window Model stores the "Index of interest"
	 */
	WindowModel winModel;
	
	/**
	 * The underlying List model (From the WindowModel) that this object
	 * will create Layouts for.
	 */
	ListModel listModel;
	
	/**
	 * The maximum height of the final Layout that this object will create.
	 */
	float maxHeight;
	
	/**
	 * Create a new LayoutCreator for the WindowModel
	 * @param winModel the underlying WindowModel (and thus ListModel) for all Layouts
	 */
	public LayoutCreator(WindowModel winModel)
	{
		this.winModel = winModel;
		this.listModel = winModel.getListModel();
		maxHeight = getPerferedHeight();
	}
	
	/**
	 * The Minimum possible height that the window's ListModel could be displayed in.
	 * @return height in meters
	 */
	public float getMinHeight()
	{
		int items = listModel.getItems().size();
		if (items > MIN_ITEMS_IN_LIST) items = MIN_ITEMS_IN_LIST;
		return calculateLayout(items).getHeight();
	}
	
	/**
	 * The prefered height of the window's ListModel.
	 * A perfered layout is one where all list items are visible on the screen.
	 * @return height in meters
	 */
	public float getPerferedHeight()
	{
		// the prefered height would be just
		// one list with all of the items:
		int numItems = listModel.getItems().size();
		LayoutComponent comp = new LayoutComponent(listModel, new IndexRange(0,numItems));
		comp.setType(LayoutComponent.Type.LIST);
		return comp.getHeight();
	}
	
	/**
	 * Set the maximum height allowed in generated layouts.
	 * @param maxHeight maximum height in meters
	 */
	public void setMaxHeight(float maxHeight)
	{
		this.maxHeight = maxHeight;
	}
	
	
	/**
	 * Create a new layout for the window's ListModel that  has a height
	 * no greater than the maxHeigt set for this LayoutCreator.
	 * NOTE: This function breaks down when a layout cannot be made
	 * when the MaxHeight is set to less than the MinHeight returned by
	 * this function.
	 * @return A new layout for the window's ListModel.
	 */
	public Layout generateLayout()
	{
		int maxItems = listModel.getItems().size();
		if (maxItems > MAX_ITEMS_IN_LIST) maxItems = MAX_ITEMS_IN_LIST;
		
		Layout layout = calculateLayout(0, maxItems);
		layout.consolidate(); // make sure there are no adjacent lists.
		return layout;
	}

	/**
	 * This function will try to find the layout that has the most number of items
	 * in it's primary list, without exceeding the maxSize restriction.
	 * It does this by doing a binary search on the number of items.  It keeps looking until
	 * it finds a layout with the max possible that is under the maxHeight restriction.
	 */
	private Layout calculateLayout(int minPrimaryItems, int maxPrimaryItems) {
		
		// A BINARY SEARCH for a layout that has the max height
		
		// base case:
		if (maxPrimaryItems == minPrimaryItems)
		{
			return calculateLayout(minPrimaryItems);
		}
		else if (maxPrimaryItems-1 == minPrimaryItems)
		{
			Layout maxLayout = calculateLayout(maxPrimaryItems);
			if (maxLayout.getHeight() <= maxHeight) return maxLayout;
			else return calculateLayout(minPrimaryItems);
		}
		
		// recursive case: test midpoint
		int middle = (maxPrimaryItems - minPrimaryItems)/2 + minPrimaryItems;
		Layout midLayout = calculateLayout(middle);
		float midLayoutHeight = midLayout.getHeight();
		
		if (midLayoutHeight <= maxHeight  + 0.0001) // a little leway (rounding errors)
		{
			// the midLayout is small enough,
			return calculateLayout(middle, maxPrimaryItems);
		}
		else
		{
			// the midLayout is too large
			return calculateLayout(minPrimaryItems, middle-1);
		}
	}

	
	/**
	 * Returns a valid layout for the model if the primary view is
	 * showing the item with the primary index and has the provided
	 * number of items in it.  The layout this method provides may
	 * be greater than the maximum height allowable for the layout
	 * manager.  To create the largest size possible, call generateLayout()
	 */
	private Layout calculateLayout(int numPrimaryItems) {
		
		// the primary range is the section of the list that is always visible
		IndexRange primaryRange = primaryRange(numPrimaryItems);
		
		// here are the items from the ListModel
		List<ItemModel> items = listModel.getItems();
		
		// create an empty layout:
		Layout layout = new Layout();
		
		// handle empty folders:
		if (items.size() == 0)
		{
			System.err.println("doing layout for empty folder");
			return layout;
		}
		
		// create an initial working range for the first item
		ItemModel firstItem = items.get(0);
		LayoutComponent workingComponent = new LayoutComponent(listModel, new IndexRange(0,1));
		workingComponent.setType(LayoutComponent.Type.FOLD);
		
		// the first item can't be a fold if it must be visible
		if (firstItem.isForcedVisible()) workingComponent.setType(LayoutComponent.Type.LIST);
		// and it can't be a fold if it overlaps with the primaryRange
		if (primaryRange.encompassesIndex(0)) workingComponent.setType(LayoutComponent.Type.LIST);
		
		// now, for every other item (Besides the first):
		for (int p=1; p<items.size(); p++)
		{
			// p is the item index, and these are its attributes
			boolean forcedVisible = items.get(p).isForcedVisible();
			boolean inPrimaryRange = primaryRange.encompassesIndex(p);
			
			// we decide the type of this item:
			LayoutComponent.Type thisItemType;
			if (forcedVisible || inPrimaryRange)
				thisItemType = LayoutComponent.Type.LIST;
			else
				thisItemType = LayoutComponent.Type.FOLD;
			
			// then, we decide if we should merge this item with the prev:
			boolean merge = true;
			if (workingComponent.getType() == LayoutComponent.Type.FOLD
					&& workingComponent.getRange().size() > 1
					&& thisItemType == LayoutComponent.Type.LIST)
				merge = false;
			if (workingComponent.getType() == LayoutComponent.Type.LIST
					&& thisItemType == LayoutComponent.Type.FOLD)
				merge = false;
			
			// a special case if we're on the last item:
			if (p == items.size() - 1)
			{
				// we're not going to create a fold with a single item:
				if (thisItemType == LayoutComponent.Type.FOLD && merge == false)
				{
					thisItemType = LayoutComponent.Type.LIST;
					merge = true;
				}
			}
			
			// process this item:
			if (merge)
			{
				workingComponent.getRange().setSize(workingComponent.getRange().size()+1);
			}
			else
			{
				layout.addComponent(workingComponent);
				workingComponent = new LayoutComponent(listModel, new IndexRange(p,1));
			}
			
			
			workingComponent.setType(thisItemType);
		}
		
		// add the last working component to the layout
		layout.addComponent(workingComponent);
		
		// add top and bottom folds:
		LayoutComponent firstComponent = layout.getFirstComponent();
		if (firstComponent.getType() == LayoutComponent.Type.FOLD)
			firstComponent.setType(LayoutComponent.Type.TOP_FOLD);
		
		LayoutComponent lastComponent = layout.getLastComponent();
		if (lastComponent.getType() == LayoutComponent.Type.FOLD)
			lastComponent.setType(LayoutComponent.Type.BOTTOM_FOLD);
		
		// NOTE: there can still be adjacent lists in this layout.
		// for efficency, we do not consolidate the layout yet.
		
		return layout;
	}

	/**
	 * Returns an index range to match the window's model.
	 * The index range will be as large as numItems (unless the model is smaller).
	 * It will be aligned to TOP so, if possible, the top of the
	 * range will be the primaryIndex.
	 */
	private IndexRange primaryRange(int numItems) {
		int modelSize = listModel.getItems().size();
	
		// top align
		int index = winModel.getIndexOfInterest();
		int size = numItems;
		if (size > modelSize) size = modelSize;
		int dif = modelSize - (index + size);
		if (dif < 0) index += dif;
		
		return new IndexRange(index, size);
	}


	
}
