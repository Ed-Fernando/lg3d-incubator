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
import edu.cmu.sun.model.SceneModel;

/**
 * A portion of a folded scroll interface.  This Component does not do
 * drawing, but instead simply represents the state of the component, and 
 * points to underlying models that the component will draw from and modify.
 * 
 * A Component will typically represent only a portion of a ListModel.
 * The exact portion of the list is determined by the range field.
 * 
 * LayoutComponents can be either, TOP_FOLD, FOLD (middle), BOTTOM_FOLD.
 * Additionally, folds can be of "UNSPECIFIED" type, but this shouldn't ever
 * happen for long.
 * 
 * @author kowitz
 */
public class LayoutComponent {

	public enum Type {TOP_FOLD, FOLD, BOTTOM_FOLD, LIST, UNSPECIFIED};
	
	/**
	 * The layout object that contains this component.
	 */
	Layout parentLayout = null;
	
	/**
	 * The range of the ListModel that this component is to represent.
	 */
	IndexRange range;
	
	/**
	 * This field is used to animate transitions.  Layouts components
	 * understand what their next range should be after a transition.
	 * In this way, they store the state of an animation plan.
	 */
	IndexRange nextRange;
	
	/**
	 * The model that underlies this component.
	 * The component will display Items from this model
	 * that match the range field.
	 */
	ListModel model;
	
	/**
	 * The type of LayoutComponent:
	 * TOP_FOLD, FOLD, BOTTOM_FOLD, LIST, UNSPECIFIED
	 */
	Type type;
	
	/**
	 * During the animation steps, components need to be matched
	 * with other components they will be transitioning into.
	 * This field holds a pointer to another LayoutComponent
	 * that is matched to transition with this component.
	 */
	LayoutComponent matchedWithComponent;
	
	/**
	 * Create a new LayoutComponent that is backed by a model, and represents a range.
	 * Initially this creates a component of type UNSPECIFIED;
	 * @param model The ListModel that this component is based upon.
	 * @param range The range of the list model that this component represents.
	 */
	public LayoutComponent(ListModel model, IndexRange range)
	{
		this(model, range, Type.UNSPECIFIED);
	}
	
	
	/**
	 * Create a new LayoutComponent that is backed by a model, and represents a range.
	 * @param model The ListModel that this component is based upon.
	 * @param range The range of the list model that this component represents.
	 * @param type The initial Type for the component. (kind of component)
	 */
	public LayoutComponent(ListModel model, IndexRange range, Type type)
	{
		this.model = model;
		this.range = range;
		this.nextRange = null;
		this.type = type;
		this.matchedWithComponent = null;
	}
	
	/**
	 * Create a copy of a Layout Component.
	 * The new layout will have a pointer to the same underlying model.
	 * It will also share a pointer to the matched if it is set
	 * All other objects are copied.
	 * @param comp
	 */
	public LayoutComponent(LayoutComponent comp) {
		model = comp.model;
		range = new IndexRange(comp.range);
		if (comp.nextRange == null) nextRange = null;
		else nextRange = new IndexRange(comp.nextRange);
		type = comp.type;
		matchedWithComponent = null;
	}


	/**
	 * The range that this component represnts in the model.
	 */
	public IndexRange getRange() {
		return range;
	}
	
	/**
	 * Returns the range that this compoennt will represent in the model
	 * after a call to updateToNextRange().
	 */
	public IndexRange getNextRange() {
		return nextRange;
	}
	
	/**
	 * Seths the range that this component represents in the underlying model.
	 */
	public void setRange(IndexRange range) {
		this.range = range;
	}
	
	/**
	 * @return the type of this component. (Whether a type of fold, or a list)
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Change the type of this component (For instance, to a top fold)
	 * @param type
	 */
	public void setType(Type type) {
		this.type = type;
	}
	
	/**
	 * Gets the heigth of this component (in meters) as it would be 
	 * displayed in the scenegraph.  Because so much of the layout model
	 * depends on size, we deal in these meter coordinates here.
	 */
	public float getHeight()
	{
		if (type == Type.UNSPECIFIED)
		{
			System.err.println("ERR: getHeight() called for unepscified LayoutComponent");
			return 0f;
		}
		if (type == Type.LIST) return getListComponentHeight(range.size());
		else return getFoldComponentHeight(range.size());
	}

	/**
	 * @return The underlying model that this component represents.
	 */
	public ListModel getModel() {
		return model;
	}
	
	/**
	 * Since this component only represents a portion of the items in a model,
	 * sometimes we will want to have acess to just the list Items that this component
	 * is responsible for.  This function returns just the items that the layout
	 * component is meant to represent.
	 * @return
	 */
	public List<ItemModel> getIncludedItemModels()
	{
		return model.getItemsWithin(range);
	}

	/**
	 * Changes the current range for this component to the value stored
	 * in nextRange.  If nextRange has not been set, this does nothing.
	 * Components should always have a non-null range. We enforce that here.
	 */
	public void updateToNextRange() {
		if (nextRange != null)
		{
			range = nextRange;
			nextRange = null;
		}
	}

	/**
	 * Sets the nextRange field.  When updateToNextRange() is called, the
	 * current range is set to this field.
	 * @param nextRange
	 */
	public void setNextRange(IndexRange nextRange) {
		this.nextRange = nextRange;
	}

	/**
	 * The matchedWithComponent field is used during the
	 * animation process.
	 * 
	 * @see LayoutMatcher
	 */
	public LayoutComponent getMatchedWithComponent() {
		return matchedWithComponent;
	}

	/**
	 * The matchedWithComponent field is used during the
	 * animation process.
	 * 
	 * @see LayoutMatcher
	 */
	public void setMatchedWithComponent(LayoutComponent match) {
		this.matchedWithComponent = match;
		if (match != null) match.matchedWithComponent = this;
	}
	
	/**
	 * @return True if this component is matched with another for transitioning.
	 * 
	 * @see LayoutMatcher
	 */
	public boolean isMatchedWithComponent()
	{
		return (matchedWithComponent != null);
	}
	
	/**
	 * Checks to see if this component currently matched with another
	 * @param match Component to test against matched field
	 * @return True if this component is currently matched to the provided component.
	 * 
	 * @see LayoutMatcher
	 */
	public boolean isMatchedWith(LayoutComponent match)
	{
		return (matchedWithComponent == match && match.matchedWithComponent == this);
	}
	
	/**
	 * Simply used for debugging.
	 */
	public String toString()
	{	
		String str = "\t" + type.toString() + "\n";
		str += "\t\t" + "range: " + range + "\n";
		if (nextRange != null)
			str += "\t\t" + "nextRange: " + nextRange + "\n"; 
		str += "\t\t" + "matched: ";
		if (isMatchedWithComponent()) str += "yes\n"; else str += "no\n";
		return str;
	}


	/**
	 * Returns the Layout that contains this component.
	 */
	public Layout getParentLayout() {
		return parentLayout;
	}


	/**
	 * Sets the layout that contains this component.  When a 
	 * component is added to a Layout, the layout calls this 
	 * method to link the two together.
	 * @param parentLayout
	 */
	public void setParentLayout(Layout parentLayout) {
		this.parentLayout = parentLayout;
	}


	/** 
	 * @return the previous sibling component in this layout,
	 * 	or null if none if previous component cannot be found.
	 */
	public LayoutComponent getPreviousSibling() {
		if (parentLayout == null) return null;
		return parentLayout.getPreviousSibling(this);
	}
	
	/** 
	 * @return the next sibling component in this layout,
	 * 	or null if none if next component cannot be found.
	 */
	public LayoutComponent getNextSibling() {
		if (parentLayout == null) return null;
		return parentLayout.getNextSibling(this);
	}


	/**
	 * This method returns the height of a fold component.
	 * (A LayoutComponent of type TOP_FOLD, FOLD, or BOTTOM_FOLD)
	 * 
	 * The architecture is a bit strange at this point because 
	 * components in the Model, Folds, and View packages all
	 * need to know how tall the each type of component will be.
	 * 
	 * @param items number of items the component needs to represent
	 * @return height of the component in meters
	 */
	public static float getFoldComponentHeight(int items)
	{
		float baseHeight = ItemModel.HEIGHT;
		int startGrowing = 10;
		if (items == 0) return 0f;
		if (items < startGrowing) return baseHeight;
		return baseHeight + (float) (Math.log(items-startGrowing+1) / Math.log(2) * SceneModel.P2M) * 2f;
	}
	
	/**
	 * This method returns the height of a list component.
	 * (A LayoutComponent of type LIST)
	 * 
	 * The architecture is a bit strange at this point because 
	 * components in the Model, Folds, and View packages all
	 * need to know how tall the each type of component will be.
	 * 
	 * @param items number of items the component needs to represent
	 * @return height of the component in meters
	 */
	public static float getListComponentHeight(int items)
	{
		return ItemModel.HEIGHT * items;
	}

}
