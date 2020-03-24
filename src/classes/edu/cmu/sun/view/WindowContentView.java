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
import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Component3D;

import edu.cmu.sun.animation.AnimationPlan;
import edu.cmu.sun.folds.Layout;
import edu.cmu.sun.folds.LayoutComponent;
import edu.cmu.sun.model.ItemModel;

/**
 * creating a ListView is expensive.  So, we try to recycle them here
 * between layouts.
 * 
 * @author Administrator
 *
 */
public class WindowContentView extends Component3D{

	List<AbstractListView> abstractListViews;
	
	// recycled views are not visible in the scene graph.
	LinkedList<ListView> recycledListViews;
	
	
	Component3D contents;
	
	public WindowContentView()
	{
		abstractListViews = new ArrayList<AbstractListView>();
		recycledListViews = new LinkedList<ListView>();
		contents = new Component3D();
		this.addChild(contents);
	}

	
	/**
	 * make this windowContents match the given layout.
	 * does this right away
	 * @param activeLayout
	 */
	public void syncToLayout(Layout activeLayout, AnimationPlan plan) {
		//TimeTracker tt = new TimeTracker("syncToLayout");
		
		// clean old components
		cleanComponents(plan);
		
		List<LayoutComponent> layoutComps = activeLayout.getComponentList();
		
		// add all the new components:
		float pos = 0f;
		for (LayoutComponent layoutComp : layoutComps)
		{
			Vector3f position = new Vector3f(0f, pos, 0f);
			
			// get a view for this layout
			AbstractListView aListView = getViewForLayout(layoutComp, plan, position);
			
			// update position marker
			pos -= aListView.getHeight();
		}
		
		
		// ensure component visibility:
		for (AbstractListView aView : abstractListViews)
			plan.setVisible(aView,0,true);
		for (AbstractListView aView : recycledListViews)
			plan.setVisible(aView,0,false);
		

	
		//tt.stop();
	}
	
	private AbstractListView getViewForLayout(LayoutComponent layoutComp, AnimationPlan plan, Vector3f position) {
		
		
		AbstractListView aListView;
		boolean recycle = (layoutComp.getType() == LayoutComponent.Type.LIST
				&& !recycledListViews.isEmpty());
		
		//recycle = false;
		//System.err.println("recycling off!");
		
		// first, try to get the view from the recycling bin:
		if (recycle)
		{
			//System.out.println("RECYCLING " + layoutComp);
			ListView recycledView = recycledListViews.removeFirst();

			recycledView.syncToLayout(layoutComp, plan);

			aListView = recycledView;
		}
		else
		{
			// otherwise, create a new view
			aListView = makeNewViewForLayout(layoutComp);
			//aListView.setVisible(false);
		}
		
		abstractListViews.add(aListView);
		plan.setTranslation(aListView, 0, position);
		
		// add the list view to the scene.
		//if (recycle) plan.setVisible(aListView, 0, true);
		//else plan.addComponent(aListView, 0, contents);
		if (!recycle) plan.addComponent(aListView, 0, contents);
		
		return aListView;
	}


	/**
	 * we remove all components in abstractListViews
	 * we place ListViews in the recycle bin.
	 * @param plan
	 */
	private void cleanComponents(AnimationPlan plan) {
		for (AbstractListView aView : abstractListViews)
		{
			if (ListView.class.isAssignableFrom(aView.getClass()))
			{
				// recycle this view:
				//plan.setVisible(aView, 0, false);
				recycledListViews.add((ListView) aView);
			}
			else
			{
				// just get rid of this view.
				plan.removeComponent(aView, 0, contents);
			}
		}
		abstractListViews.clear();
	}



	private AbstractListView makeNewViewForLayout(LayoutComponent layoutComp) {
		
		LayoutComponent.Type type = layoutComp.getType();
		AbstractListView view = null;
		
		if (type == LayoutComponent.Type.LIST)
		{
			view = new ListView(layoutComp);
		}
		else if (type == LayoutComponent.Type.FOLD)
		{
			view = new MiddleFoldView(layoutComp);
		}
		else if (type == LayoutComponent.Type.TOP_FOLD)
		{
			view = new TopFoldView(layoutComp);
		}
		else if (type == LayoutComponent.Type.BOTTOM_FOLD)
		{
			view = new BottomFoldView(layoutComp);
		}
		else
		{
			System.err.println("ERROR: Layout type not set, can't convert to view");
		}
		
		return view;
	}

	public void update(AnimationPlan plan, int msec)
	{
		float pos = 0f;
		for (AbstractListView view : abstractListViews)
		{
			plan.changeTranslation(view,0,msec, new Vector3f(0f, pos, 0f));
			view.update(plan, msec);
			pos -= view.getHeight();
		}
	}


	public ItemView getItemViewMatchingModel(ItemModel itemModel) {
		ItemView itemView = null;
		for (AbstractListView aView : abstractListViews)
		{
			if (ListView.class.isAssignableFrom(aView.getClass()))
			{
				itemView = ((ListView)aView).getItemViewMatchingModel(itemModel);
				if (itemView != null) return itemView;
			}
		}
		return null;
	}
	
	
	
}
