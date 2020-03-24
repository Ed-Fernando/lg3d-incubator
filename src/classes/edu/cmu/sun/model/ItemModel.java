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

 /**
  * An state model of an item in a list that represents a file or directory.
  * Contains the basic state of each list item of a file that is displayed on 
  * the screen. Also, has some high level calls that help to handle model changes
  * after user actions.
  * 
  * @author Braden Kowitz, Jake Pierson, Jessica Smith
  *
  */
public class ItemModel extends Model{

	/**
	 * Height of a line of text with an icon
	 */
	public static final float CONTENT_HEIGHT = 22f * SceneModel.P2M;
	
	/**
	 * Spacing to be set between each text/icon line
	 */
	public static final float SPACING = 4f * SceneModel.P2M;
	
	/**
	 * Standard window height for 1 line of text;
	 */
	public static final float HEIGHT = CONTENT_HEIGHT + (2 * SPACING);
	
	
	
	
	FileNodeModel file;
	ListModel parentList;
	
	boolean selected = false;
	int pinnedCount = 0;
	
	public ItemModel(FileNodeModel file, ListModel parentList)
	{
		this.file = file;
		this.parentList = parentList;
	}

	/**
	 * returns the file that this item is based on.
	 * @return
	 */
	public FileNodeModel getFile()
	{
		return file;
	}
	
	
	// ----- getters and setters
	
	public boolean isPinned() {
		return (pinnedCount > 0);
	}

	public void incrementPinCount() {
		boolean oldPinned = isPinned();
		pinnedCount++;
		if (oldPinned != isPinned()) needsUpdate();
	}

	public void decrementPinCount() {
		boolean oldPinned = isPinned();
		pinnedCount--;
		if (pinnedCount < 0) pinnedCount = 0;
		if (oldPinned != isPinned()) needsUpdate();
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		if (this.selected != selected)
		{
			this.selected = selected;
			needsUpdate();
		}
	}
	
	public boolean isForcedVisible()
	{
		return (selected || isPinned());
	}

	
	public void deselectSiblings() {
		for (ItemModel m : parentList.getItems())
		{
			if (m != this) m.setSelected(false);
		}
	}

	public ListModel getParentListModel()
	{
		return parentList;
	}

	public int getPosition() {
		return parentList.getItems().indexOf(this);
	}


}
