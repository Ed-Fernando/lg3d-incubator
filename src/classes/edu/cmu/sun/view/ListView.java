 /*
  * 3D File Manager - Project Looking Glass 
  * Copyright Sun Microsystems, 2005
  * 
  * Project Course in Human-Computer Interaction
  * Carnegie Mellon University
  * 
  * Aditya Chand, Braden Kowitz, Jake Pierson, Jessica Smith
  */

package edu.cmu.sun.view;




import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Component3D;

import edu.cmu.sun.animation.AnimationPlan;
import edu.cmu.sun.components.BoxComponent;
import edu.cmu.sun.folds.LayoutComponent;
import edu.cmu.sun.interpolators.SimpleMotionAnimation;
import edu.cmu.sun.model.ItemModel;

/**
 * The view of a list within a window 
 * in the file manager
 * 
 */
public class ListView extends AbstractListView {

	BoxComponent slab;
	
	/**
	 * component that holds the item views.
	 * this component moves to scroll the items.
	 */
	Component3D listItemContainer;
	
	/**
	 * a set of itemViews so that we can access them later.
	 */
	List<ItemView> itemViews;

	int currentPosition;
	private int currentSize;
	
	final static float WIDTH = WindowView.WIDTH;
	final static float DEPTH = WindowView.DEPTH;
	
	public ListView(LayoutComponent layout) {
		super(layout);
		itemViews = new ArrayList<ItemView>();
		initialize();
	}

	/**
	 * creates the SLAB component, the container component for ListItemViews
	 * and the ListItemsViews for this list view.
	 */
	private void initialize()
	{
		currentPosition = layout.getRange().position();
		currentSize = layout.getRange().size();
		
		slab = new BoxComponent(WIDTH, 1f, DEPTH, getAppearance(false));
		slab.setAnimation(new SimpleMotionAnimation(250));
		//slab.setScale(1f, getHeight(), 1f);
		this.addChild(slab);
		
		
		listItemContainer = new Component3D();
		listItemContainer.setAnimation(new SimpleMotionAnimation(250));
		this.addChild(listItemContainer);
		
		itemViews = new ArrayList<ItemView>();
		
		addListItemViews();
		
		updateNow();
	}
	
	/**
	 * adds all of the ListItemViews to this component.
	 * initially, they are all invisible and not yet positioned
	 */
	private void addListItemViews()
	{
		
		//TextComponent.timeTracker.reset();
		
		List<ItemModel> items = layout.getModel().getItems();		
		if (items.size() == 0) return;
		
		
		// add list item views from the model
		// initially, none of these are positioned or visible
		int index = 0;
		ItemView iv;
		for (final ItemModel item : items)
		{
			iv = new ItemView(item);
			
			iv.setAnimation(new SimpleMotionAnimation(1000));
			iv.setVisible(getItemVisibility(index));
			iv.setTranslation(0f, getItemYPosition(index), 0f);
			listItemContainer.addChild(iv);
			itemViews.add(iv);
			index++;
		}
		
		//TextComponent.timeTracker.report();
	}
	
	/**
	 * returns the Y position for the listItemContainer
	 * @return
	 */
	private float getItemYPosition(int index)
	{
		//float pos = index - layout.getRange().position();
		//return (- pos * Prop.LIST_ITEM_HEIGHT);
		return (- index * ItemModel.HEIGHT );
	}
	
	private float getListContainerYPosition()
	{
		float pos = layout.getRange().position();
		return (pos * ItemModel.HEIGHT);
	}
	
	/**
	 * returns the visibility for an ItemView at the specified index in the model
	 * @return
	 */
	private boolean getItemVisibility(int index)
	{
		return (layout.getRange().encompassesIndex(index));
	}
	

	
	public List<ItemModel> getActiveListItemModels()
	{
		List<ItemModel> selected = new ArrayList<ItemModel>();
		for (final ItemModel item : layout.getIncludedItemModels())
		{
			if (item.isPinned()) selected.add(item);
		}
		return selected;
	}
	
	@Override
	public void update(AnimationPlan plan, int msec)
	{
		//System.out.println(range);
		int lastPosition = currentPosition;
		int lastSize = currentSize;
		currentPosition = layout.getRange().position();
		currentSize   = layout.getRange().size();
		
		for (int i=0; i< itemViews.size(); i++)
		{
			ItemView itemView = itemViews.get(i);
			// oldY and newY are positive as down.
			float oldY = (i - lastPosition) * ItemModel.HEIGHT;
			float newY = (i - currentPosition) * ItemModel.HEIGHT;
			float oldH = LayoutComponent.getListComponentHeight(lastSize);
			float newH = LayoutComponent.getListComponentHeight(currentSize);
			
			// find the time to set the visibility:
			// this looks really complicated, but is just a solution
			// to a linear equation of when the list item goes off of the
			// list boundaries during the animation.
			int visibiltiyChangeTime = 0;
			if ((newY >= 0 && oldY < 0) || (newY < 0  && oldY >= 0))
			{
				visibiltiyChangeTime = (int) (msec * (Math.abs(oldY) / (Math.abs(oldY) + Math.abs(newY))));
			}
			else
			{	
				double startDiff = Math.abs(oldY - oldH + ItemModel.HEIGHT);
				double endDiff = Math.abs(newY - newH + ItemModel.HEIGHT);
				double coeff = startDiff / (startDiff + endDiff);
				visibiltiyChangeTime = (int) (coeff * (double) msec);
			}
			
		
			// sanity check to make sure animation takes place on time.
			if (visibiltiyChangeTime > msec) visibiltiyChangeTime = msec;
			if (visibiltiyChangeTime < 0) visibiltiyChangeTime = 0;
			
			//System.out.println(visibiltiyChangeTime);
			//	set the visibility of the list item components:
			plan.setVisible(itemView, visibiltiyChangeTime, getItemVisibility(i));
			
			// animate the list item components to place
			//plan.changeTranslation(itemView, 0, msec, new Vector3f(0f, - newY, 0f));
		}

		plan.changeTranslation(listItemContainer, 0, msec, new Vector3f(0, getListContainerYPosition(), 0));
		
		// scale the slab to the the correct height
		plan.changeScale(slab, 0, msec, getSlabScaling());
	}
	
	private void updateNow()
	{
		AnimationPlan plan = new AnimationPlan();
		updateNow(plan);
		plan.execute();
	}
	
	/**
	 * updates using set methods instead of change (To avoid animation)
	 * used in ListView recycling.
	 * @param plan
	 */
	private void updateNow(AnimationPlan plan)
	{
		currentPosition = layout.getRange().position();
		currentSize   = layout.getRange().size();
		
		//System.out.println("currentSize = " + currentSize);
		
		for (int i=0; i< itemViews.size(); i++)
		{
			ItemView itemView = itemViews.get(i);
			
			//	set the visibility of the list item components:
			plan.setVisible(itemView, 0, getItemVisibility(i));
			
			// move the list item components to place
			//plan.setTranslation(itemView, 0, new Vector3f(0f, getItemYPosition(i), 0f));
		}
		
		plan.setTranslation(listItemContainer, 0, new Vector3f(0, getListContainerYPosition(), 0));
		
		// scale the slab to the the correct height
		plan.changeScale(slab, 0, 0, getSlabScaling());
	}

	
	private Vector3f getSlabScaling()
	{
		float height = getHeight();
		if (height < 0.0001f) height = 0.001f;
		//System.out.println("slab height = " + height);
		return new Vector3f(1f, height, 1f);
	}

	@Override
	public float getHeight() {
		return LayoutComponent.getListComponentHeight(layout.getRange().size());
	}

	public void syncToLayout(LayoutComponent newLayout, AnimationPlan plan) {
		// first, ensure the models are compatable:
		if (layout.getModel() != newLayout.getModel())
		{
			System.err.println("ERR: cannot update ListView to a different layout model");
			return;
		}
		
		layout = newLayout;
		updateNow(plan);
	}

	public ItemView getItemViewMatchingModel(ItemModel itemModel) {
		ItemView itemView;
		for (int i=0; i< itemViews.size(); i++)
		{
			if (getItemVisibility(i))
			{
				itemView = itemViews.get(i);
				if (itemView.getModel() == itemModel) return itemView;
			}
		}
		return null;
	}


	

}
