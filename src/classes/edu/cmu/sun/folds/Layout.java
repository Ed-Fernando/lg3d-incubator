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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;




/**
 * A Layout is a model representation of the contents of a Window.  That is,
 * It represents the folded scrolling portion of the window.  Simply put,
 * it contains a set of LayoutComponents, in order, that make up the layout.
 * This class also provides some methods for helping with the animations process.
 * 
 * For example, the Segment and Consolidate functions are called during the
 * animation process to ensure that the layout is formed correctly for the next step.
 * 
 * @See WindowModel
 */
public class Layout {

	/**
	 * The underlying components that make up the Layout.  This is 
	 * sorted in order from the top-most component to the bottom-most.
	 */
	List<LayoutComponent> components;

	/**
	 * Create a new Layout, initially empty.
	 */
	public Layout()
	{
		components = new ArrayList<LayoutComponent>();
	}
	
	/**
	 * Create a layout that is a copy of another layout.
	 * All components will be copied (not referenced )from the provided Layout.
	 * @param otherLayout the layout to copy from.
	 */
	public Layout(Layout otherLayout)
	{
		this();
		for (LayoutComponent comp : otherLayout.components)
		{
			LayoutComponent newComp = new LayoutComponent(comp);
			newComp.setParentLayout(this);
			components.add(newComp);
		}
	}
	
	/**
	 * @return The sum of the height of all LayoutComponents in this Layout
	 */
	public float getHeight() {
		float height = 0;
		for (LayoutComponent comp : components)
		{
			height += comp.getHeight();
		}
		return height;
	}

	/**
	 * Returns the ListCompoennts that make up this Layout.
	 * The function returns not the underlying list that this object uses,
	 * but a copy of the list for protection.
	 */
	public LinkedList<LayoutComponent> getComponentList()
	{
		return new LinkedList<LayoutComponent>(components);
	}
	
	/**
	 * Returns the first component of this list.
	 * Throws a an OutOfBounds exception if list is empty.
	 */
	public LayoutComponent getFirstComponent()
	{
		return components.get(0);
	}
	
	/**
	 * Returns the last component of this list.
	 * Throws a an OutOfBounds exception if list is empty.
	 */
	public LayoutComponent getLastComponent()
	{
		return components.get(components.size()-1);
	}
	
	/**
	 * Adds a component to the end of this layout, and sets
	 * the parent of the provided component to this Layout.
	 * @param newComp
	 */
	public void addComponent(LayoutComponent newComp) {
		components.add(newComp);
		newComp.setParentLayout(this);
	}
	
	/**
	 * @return A list of all components, in order, that have a type of LIST
	 */
	public List<LayoutComponent> getComponentsTypedAsList() {
		List<LayoutComponent> lists = new LinkedList<LayoutComponent>();
		for (LayoutComponent comp : components)
		{
			if (comp.getType() == LayoutComponent.Type.LIST)
				lists.add(comp);
		}
		return lists;
	}
	
	/**
	 * Cleans up the Layout.
	 * It removes any components with a current range size of zero.
	 * Also, it finds consecutive components typed as LIST and
	 * merges them into a single component.
	 */
	public void consolidate()
	{
		if (components.size() < 2) return;
		
		// remove components of size = 0
		Iterator<LayoutComponent> it = components.iterator();
		while (it.hasNext())
		{
			LayoutComponent lc = it.next();
			if (lc.getRange().size() == 0) it.remove();
		}
		
		// find 2 list views that are ajacent
		for (int i=0; i<components.size()-1; i++)
		{
			LayoutComponent comp1 = components.get(i);
			LayoutComponent comp2 = components.get(i+1);
			if (   comp1.getType() == LayoutComponent.Type.LIST
				&& comp2.getType() == LayoutComponent.Type.LIST)
			{
				// merge the views:
				comp1.getRange().merge(comp2.getRange());
				components.remove(comp2);

				// since the data structure changed
				// we just recurse and try to consolidate again.
				consolidate();
				return;
			}
		}
	}
	
	/**
	 * Segment the layout across another layout.
	 * 
	 * During the animation process, it is important to avoid 2-1 and 1-2 mappings
	 * between the current layout and the next layout.  We detect these mapping by 
	 * seeing which components overlap.  If two LayoutComponents represnet the same
	 * elements, then we count them as connected.  If one component is connected to
	 * two components in another Layout, then that component needs to be segmented
	 * to produce two 1-1 matchings rather than a single 1-2 mapping.
	 * 
	 *  This function segments this Layout acorss another.  the call should be
	 *  done bi-directionally.  So, both layouts are segmented acorss each other.
	 *  This function modifies THIS object, by segmenting where necessary.
	 * 
	 * @see LayoutTransitioner
	 * @param acrossLayout layout to segment across. 
	 */
	public void segment(Layout acrossLayout)
	{
		// repeat this until we made a pass w/o adding a segment
		while (segmentAcorss(acrossLayout));
	}
	
	/**
	 * Implementation of the Segment Algorithm.  Returns True immediately if
	 * the function performs a segment.  Since a segment modifies the layout,
	 * we simpley start again from the top if a segment action oaccours.
	 * (probably not the most efecient thing.
	 * @param acrossLayout layout to segment across.
	 * @return True if segment occoured
	 */
	private boolean segmentAcorss(Layout acrossLayout)
	{
		// for each consecuitive LIST compnents in acrossLayout
		Iterator<LayoutComponent> acrossIt = acrossLayout.getComponentsTypedAsList().iterator();
		if (!acrossIt.hasNext()) return false;
		LayoutComponent lastAcross;
		LayoutComponent thisAcross = acrossIt.next();
		while(acrossIt.hasNext())
		{
			lastAcross = thisAcross;
			thisAcross = acrossIt.next();
			
			IndexRange acrossRange1 = lastAcross.getRange();
			IndexRange acrossRange2 = thisAcross.getRange();
			
			// for each LIST component in this layout
			for (LayoutComponent localComp : getComponentsTypedAsList())
			{
				// if an overlap exists between both acrosses:
				IndexRange localRange = localComp.getRange();
				
				IndexRange.Overlap.Type t1 = localRange.getOverlap(acrossRange1).getType();
				IndexRange.Overlap.Type t2 = localRange.getOverlap(acrossRange2).getType();
			
				if (   t1 != IndexRange.Overlap.Type.DISJOINT
					&& t2 != IndexRange.Overlap.Type.DISJOINT)
				{
					// we have to do a Segment:
					int rangeIndex = (acrossRange1.getEnd() + acrossRange2.getStart()) / 2;
					segmentComponent(localComp, rangeIndex);
					// return true;
				}
			}
			
		}
		
		return false;
	}
	
	/**
	 * Segments a particular LIST component that belongs to this Layout.
	 * 
	 * This breaks the compoennt into two parts at the specified index.
	 * @param localComp component in this Layout to segment
	 * @param rangeIndex inded at which to segment the component.
	 */
	private void segmentComponent(LayoutComponent localComp, int rangeIndex)
	{
		// remove old component
		int compIndex = components.indexOf(localComp);
		components.remove(compIndex);
		
		// calculate ranges for new component
		int pos = localComp.getRange().position();
		int size = localComp.getRange().size();;
		
		IndexRange r1 = new IndexRange(pos, rangeIndex - pos);
		IndexRange r2 = new IndexRange(pos + (rangeIndex - pos), size - (rangeIndex - pos));
		
		// create the new components
		LayoutComponent lc1 = 
			new LayoutComponent(localComp.getModel(), r1, localComp.getType());
		LayoutComponent lc2 = 
			new LayoutComponent(localComp.getModel(), r2, localComp.getType());
		
		// add them:
		components.add(compIndex,lc2);
		components.add(compIndex,lc1);
	}
	
	/**
	 * Updates all of the child LayoutComponents to their next ranges.
	 * @see LayoutComponent
	 */
	public void updateToNextRanges()
	{
		for (LayoutComponent comp : components)
			comp.updateToNextRange();
	}
	
	/**
	 * Clears all of the matches from the children LayoutComponents
	 * @see LayoutComponent
	 */
	public void clearMatches()
	{
		for (LayoutComponent comp : components)
			comp.setMatchedWithComponent(null);
	}
	
	/**
	 * Simply for debugging.
	 */
	public String toString()
	{
		String str = "Layout\n";
		for (LayoutComponent comp : components)
			str += comp.toString();
		return str;
	}

	/**
	 * Gets the previous component in the Layout
	 * @param component returns a component previous to this one.
	 * @return the previous component in the Layout, or null if none found.
	 */
	public LayoutComponent getPreviousSibling(LayoutComponent component) {
		LayoutComponent prev = null;
		
		for (LayoutComponent comp : components)
		{
			if (comp == component) return prev;
			prev = comp;
		}
		
		return null;
	}

	/**
	 * Gets the next component in the Layout
	 * @param component returns a component after to this one.
	 * @return the next component in the Layout, or null if none found.
	 */
	public LayoutComponent getNextSibling(LayoutComponent component) {
		boolean found = false;
		
		for (LayoutComponent comp : components)
		{
			if (found) return comp;
			if (comp == component) found = true;
		}
		
		return null;
	}


}
