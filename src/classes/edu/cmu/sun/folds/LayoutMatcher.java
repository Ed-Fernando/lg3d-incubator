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

/**
 * When the Folded Scrolling window changes, we need to make a smooth transition
 * between scenes. In order to do this, we run the LayoutTransitioner. One of
 * the steps in the transition is to match component from the current layout to
 * the next layout. These matched components will animate between one another.
 * 
 * The LayoutMatcher matches together components based on a number of limits:
 * First, the LayoutComponents must be of the same type. Edge folds (TOP_FOLD or
 * BOTTOM_FOLD) are matched before any other component.
 * 
 * Next, the manager matches components in the order of their overlap. For
 * example, it Matches all components with an EXACT_OVERLAP first. Then, after
 * all matching has been done for EXACT_OVERLAP, the matcher moves on to
 * TOTAL_OVERLAP, then PARTIAL_OVERLAP.
 * 
 * Once components have been matched, they can be built together into a layout
 * that acts as an animation plan.
 * 
 * @see LayoutTransitioner, LayoutComponent, IndexRange
 * @author Braden Kowitz
 */
public class LayoutMatcher {

	enum Mode {
		EDGE, OVERLAP
	};

	/**
	 * The mode of operation for the matchLayouts() call. Edge mode will only
	 * match edge components  (TOP_FOLD or BOTTOM_FOLD).  OVERLAP MODE will
	 * match components based on the overlapMode field.
	 */
	Mode mode = Mode.EDGE;

	/**
	 * The types of overlap that will be accepted as a match when 
	 * matchLayouts() is called.
	 */
	IndexRange.Overlap.Type overlapMode = IndexRange.Overlap.Type.EXACT_MATCH;

	/**
	 * first layotu to match against.
	 */
	Layout layout1;

	/**
	 * second layout to match against.
	 */
	Layout layout2;

	/**
	 * Mathces components between these two layouts.
	 */
	public void match(Layout firstLayout, Layout secondLayout) {
		this.layout1 = firstLayout;
		this.layout2 = secondLayout;

		// clear any matches that are presently set.
		firstLayout.clearMatches();
		secondLayout.clearMatches();

		// 1) match edge folds
		mode = Mode.EDGE;
		matchLayouts();

		// 2) match exact overlaps
		mode = Mode.OVERLAP;
		overlapMode = IndexRange.Overlap.Type.EXACT_MATCH;
		matchLayouts();

		// 3) match total overlaps
		overlapMode = IndexRange.Overlap.Type.TOTAL_OVERLAP;
		matchLayouts();

		// 4) match partial overlaps
		overlapMode = IndexRange.Overlap.Type.PARTIAL_OVERLAP;
		matchLayouts();

		// 5) match disjoing layouts (to a specific limit)
		// NOTE: it is possible to do matches on disjoint layouts.
		// however, if you match too far, the scrolling animation will
		// get out of hand. For this implementation, we simply
		// do not consider transitions between disjoint layouts.

	}

	/**
	 * Go through each pair of components (cross product)
	 * and call tryToMatch();
	 */
	private void matchLayouts() {
		for (LayoutComponent lc1 : layout1.getComponentList()) {
			for (LayoutComponent lc2 : layout2.getComponentList()) {
				tryToMatch(lc1, lc2);
			}
		}
	}

	/**
	 * Tries to match two components together using the doesMatch() 
	 * method as a test to see if the components should match together.
	 */
	private void tryToMatch(LayoutComponent lc1, LayoutComponent lc2) {
		if (lc1.isMatchedWithComponent())
			return;
		if (lc2.isMatchedWithComponent())
			return;
		if (doesMatch(lc1, lc2))
			lc1.setMatchedWithComponent(lc2);
	}

	/**
	 * Returns True if the two components are matched
	 * based on the specs in the mode field and the overlapMode field.
	 */
	private boolean doesMatch(LayoutComponent lc1, LayoutComponent lc2) {
		// check to see if the types match:
		if (lc1.getType() != lc2.getType())
			return false;

		if (mode == Mode.EDGE) {
			if (lc1.getType() == LayoutComponent.Type.TOP_FOLD
					|| lc1.getType() == LayoutComponent.Type.BOTTOM_FOLD)
				return true;
		} else if (mode == Mode.OVERLAP) {
			IndexRange.Overlap overlap = lc1.getRange().getOverlap(
					lc2.getRange());
			if (overlap == null)
				return false;
			if (overlap.getType() == overlapMode)
				return true;
		}

		return false;
	}

}
