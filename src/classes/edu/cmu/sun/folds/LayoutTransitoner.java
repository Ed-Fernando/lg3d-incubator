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

import java.util.LinkedList;

import edu.cmu.sun.model.ListModel;

/**
 * When the Folded Scrolling window changes, we need to make a smooth transition
 * between scenes. In order to do this, we run the LayoutTransitioner.
 * 
 * The transitioner takes two layouts, a current layout, and a next layout.
 * The class creates a third layout as a return that represents the planed
 * transition between the current layout and the next layout.  The layout 
 * that is returned has components with their nextRange field set.  This means
 * that a call to updateNextRange will change the returned layout to the ranges
 * needed to represent the next layout.
 * 
 * @see LayoutComponent
 * 
 * @author Braden Kowitz
 */
public class LayoutTransitoner {

	/**
	 * the current, active layout
	 */
	Layout activeLayout;
	
	/**
	 * the next layout, the one we are transitioning to.
	 */
	Layout nextLayout;
	
	/**
	 * the layout that this object creates to transition between
	 * activeLayout and nextLayout.
	 */
	Layout plan;
	
	/**
	 * As we iterate, we build the plan slowly. This holds the index
	 * of the ListModdel that we have built up-to in activeComps.  This variable
	 * is useful for inserting components that have to disapear,
	 * or appear in the transition.  This helps us make sure the components
	 * animate in the correct way as they disapear or appear.
	 */
	int activePos;
	
	/**
	 * As we iterate, we build the plan slowly. This holds the index
	 * of the ListModel that we have built up-to in nextComps.  This variable
	 * is useful for inserting components that have to disapear,
	 * or appear in the transition.  This helps us make sure the components
	 * animate in the correct way as they disapear or appear.
	 */
	int nextPos;
	
	/**
	 * We transition from the activeLayout to nextLayout.
	 * This field holds a currently built part of the part.
	 * 
	 * It represents the components that are goign to be the FROM components
	 * in the plan.  Then, once all of the components are matched and
	 * added, we create a plan from activeComps and nextComps.
	 */
	LinkedList<LayoutComponent> activeComps;
	
	
	/**
	 * We transition from the activeLayout to nextLayout.
	 * This field holds a currently built part of the part.
	 * 
	 * It represents the components that are goign to be the TO components
	 * in the plan.  Then, once all of the components are matched and
	 * added, we create a plan from activeComps and nextComps.
	 * (these ranges will be set as nextRanges in the LayoutComponents of the plan)
	 */
	LinkedList<LayoutComponent> nextComps;
	
	/**
	 * Creates an animation plan that visually transitions between
	 * two layouts. Note that the Layout it returns is set to visually represent
	 * the fromActiveLayout passed in.  However, the layout that is returned may have been
	 * segmented or consolidated to be differnet on a logic leve.  To perform the
	 * transition, the caller must replace the current layout with the returned layout,
	 * then call updateToNextRanges() on the layout, then do a smooth animation update.
	 */
	public Layout prepareAnimation(Layout fromActiveLayout, Layout toNextLayout) {
		
		this.activeLayout = new Layout(fromActiveLayout);
		this.nextLayout = new Layout(toNextLayout);
		

		consolidateLayouts();
		segmentLayouts();
		matchLayouts();
		makeAnimationPlan();
		return plan;
	}


	/**
	 * Between the two laytous, match the components
	 * that need to animate between each other.  Matched layouts 
	 * will be in both lists, they will not disappear or appear during
	 * the visual scene transition. 
	 */
	private void matchLayouts() {
		LayoutMatcher matcher = new LayoutMatcher();
		matcher.match(activeLayout, nextLayout);
	}


	/**
	 * Since we must insert and remove visual elements, we must make sure
	 * that they disaper or appear at the right spot in the list, and then
	 * animate correctly.  We segment in both directions to make sure that there are no 2-1 or 
	 * 1-2 mappings between the layouts.  This makes sure we have space
	 * to instert growing components.
	 * 
	 * @see Layout
	 */
	private void segmentLayouts() {
		this.activeLayout.segment(nextLayout);
		this.nextLayout.segment(this.activeLayout);
	}

	/**
	 * Before we begin the transition calculation, we want to
	 * make sure there are no empty components (size zero)
	 * hanging in there, or adjacent LIST components, that could introduce
	 * matching problems. 
	 */
	private void consolidateLayouts() {
		activeLayout.consolidate();
		nextLayout.consolidate();
	}


	/**
	 * makes an animation plan between activeLayout and nextLayout
	 * and stores it in the plan member variable
	 */
	private void makeAnimationPlan() {
		plan = new Layout();
		activePos = 0;
		nextPos = 0;
		
		// set up a listing of the components:
		// Note, these are COPIES of the list, because we are going to edit them.
		activeComps = activeLayout.getComponentList();
		nextComps = nextLayout.getComponentList();
		
		// insert any unmatched top folds first:
		addUnmatchedFoldsOfType(LayoutComponent.Type.TOP_FOLD);
		
		int activeSize;
		int nextSize;
		while(activeComps.size() > 0 || nextComps.size() > 0)
		{
			activeSize = activeComps.size();
			nextSize = nextComps.size();
				
			addNextComponentsToPlan();
			
			// if we have made no progress.
			if (activeComps.size() == activeSize && nextComps.size() == nextSize)
			{
				break;
			}
		}
		
		addUnmatchedFoldsOfType(LayoutComponent.Type.BOTTOM_FOLD);

		if (activeComps.size() > 0 || nextComps.size() > 0)
		{
			System.err.println("Err: Animation Manager Failed");
		}
	}



	
	private void addUnmatchedFoldsOfType(LayoutComponent.Type type)
	{
		LayoutComponent lcActive = getFirst(activeComps);
		LayoutComponent lcNext = getFirst(nextComps);
		
		if (   lcActive != null
			&& lcActive.getType() == type
			&& ! lcActive.isMatchedWithComponent())
		{
			addActiveComp(lcActive);
		}
		
		if (   lcNext != null
				&& lcNext.getType() == type
				&& ! lcNext.isMatchedWithComponent())
			{
				addNextComp(lcNext);
			}
	}



	private void addNextComponentsToPlan() {
		LayoutComponent lcActive = getFirst(activeComps);
		LayoutComponent lcNext = getFirst(nextComps);

		// if both are null, there is nothing we can do
		if (lcActive == null && lcNext == null) return;
		
		// if the two are matched we add them together
		if ( addIfMatched(lcActive, lcNext) ) return;


		// otherwise, we add each one if it is not matched
		// or a bottom fold
		
		if (   lcActive != null
			&& !lcActive.isMatchedWithComponent()
			&& lcActive.getType() != LayoutComponent.Type.BOTTOM_FOLD)
		{
			addActiveComp(lcActive);
		}
		
		if (   lcNext != null
			&& !lcNext.isMatchedWithComponent()
			&& lcNext.getType() != LayoutComponent.Type.BOTTOM_FOLD)
		{
			addNextComp(lcNext);
		}

	}



	private void addNextComp(LayoutComponent lcNext) {
		addPlanComponent(
				lcNext.getType(),
				lcNext.getModel(),
				new IndexRange(activePos, 0),
				lcNext.getRange());
		
		nextComps.remove(lcNext);
	}



	private void addActiveComp(LayoutComponent lcActive) {
		addPlanComponent(
				lcActive.getType(),
				lcActive.getModel(),
				lcActive.getRange(),
				new IndexRange(nextPos, 0));
		
		activeComps.remove(lcActive);
	}



	/**
	 * returns true if the components have been added
	 */
	private boolean addIfMatched(LayoutComponent lcActive, LayoutComponent lcNext) {
	
		if (lcActive == null || lcNext == null
			|| ! lcActive.isMatchedWith(lcNext)) return false;
		
		addPlanComponent(
				lcActive.getType(),
				lcActive.getModel(),
				lcActive.getRange(),
				lcNext.getRange());
		

		// remove the components from the lists
		activeComps.remove(lcActive);
		nextComps.remove(lcNext);
		
		return true;
	}

	
	private void addPlanComponent(LayoutComponent.Type type, ListModel model, IndexRange range, IndexRange nextRange)
	{
		// add component and update position:
		LayoutComponent newComp = new LayoutComponent(model, range);
		newComp.setType(type);
		newComp.setNextRange(nextRange);
		activePos += range.size();
		nextPos += nextRange.size();
		plan.addComponent(newComp);
	}
	

	private LayoutComponent getFirst(LinkedList<LayoutComponent> compList) {
		if (compList.isEmpty()) return null;
		else return compList.getFirst();
	}
	
}
