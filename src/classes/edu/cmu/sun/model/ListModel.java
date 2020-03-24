/*
 * 3D File Manager - Project Looking Glass 
 * Copyright Sun Microsystems, 2005
 * 
 * Project Course in Human-Computer Interaction
 * Carnegie Mellon University
 * 
 * Aditya Chand, Braden Kowitz, Jake Pierson, Jessica Smith
 */

package edu.cmu.sun.model;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.sun.folds.IndexRange;

/**
 * A state model of a list of items that each represent a file in the
 * filesystem.
 * 
 * A ListModel is made up of a list of ItemModel components. This class doesn't
 * do much except allow for some calls that make the design simpler for the
 * ItemModel objects.
 * 
 * 
 * @author Braden Kowitz, Jake Pierson, Jessica Smith
 * 
 */
public class ListModel {

	public enum Order {
		FILENAME, SIZE, DATE
	};

	FileNodeModel file;

	List<ItemModel> items;

	WindowModel windowModel;

	public ListModel(FileNodeModel file) {
		this.file = file;
		generateItems();
		setItemOrder(Order.FILENAME);
		windowModel = null;
	}

	/**
	 * returns the file that this list is based on.
	 * 
	 * @return
	 */
	public FileNodeModel getFile() {
		return file;
	}

	/**
	 * generates ItemModels for this list.
	 */
	private void generateItems() {
		items = new ArrayList<ItemModel>();
		List<FileNodeModel> children = file.getChildren();
		if (children == null)
			return;
		for (FileNodeModel child : children) {
			items.add(new ItemModel(child, this));
		}
	}

	public void setItemOrder(Order itemOrder) {
		// TODO: order the items in the list model.
	}

	public List<ItemModel> getItems() {
		return items;
	}

	/**
	 * Builds a list from the internal Items
	 * 
	 * @throws IndexOutOfBoundsExcetion
	 *             if the range does not fit in the item list.
	 * @param range
	 * @return
	 */
	public List<ItemModel> getItemsWithin(IndexRange range) {
		if (range.size() == 0)
			return new ArrayList<ItemModel>();
		int start = range.getStart();
		int end = range.getEnd();

		// TODO: come back and check this out.
		// this protects from an IndexOutOfBoundsException when the list has one
		// element and we try to return a range from start -> end+1. I know it's
		// ugly,
		// but it's the only thing that didn't mess up the folds
		try {
			return items.subList(start, end + 1);
		} catch (IndexOutOfBoundsException e) {
			return items.subList(start, end);
		}
		// return items.subList(range.getStart(), range.getEnd());
	}

	public void setWindowModel(WindowModel model) {
		windowModel = model;
	}

	/**
	 * might return null if not set yet.
	 * 
	 * @return
	 */
	public WindowModel getWindowModel() {
		return windowModel;
	}

	public boolean containsItem(ItemModel parentItemModel) {
		return items.contains(parentItemModel);
	}

}
