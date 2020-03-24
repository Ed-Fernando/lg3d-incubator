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

import edu.cmu.sun.folds.Layout;
import edu.cmu.sun.folds.LayoutCreator;
import edu.cmu.sun.view.WindowBarView;


/**
 * A state model of the windows (, or panels,) seen in the interface.
 * The WindowModel is based on a ListModel.  The window is then tasked
 * to displaying the contents of the list.  This class handles a lot of
 * the high level behavior needed after a user action.  Adjacent columns need
 * to be modified, etc.
 * 
 * @author Braden Kowitz, Jessica Smith
 */
public class WindowModel extends Model implements Comparable<WindowModel>{

	ListModel listModel;
	ColumnModel columnModel = null;
	LayoutCreator layoutManager;
	Layout layout;
	ItemModel parentItemModel;
	boolean closeable = true;
	
	boolean pinned = false;
	int indexOfInterest = 0;
	
	public WindowModel(ItemModel item) {
		this(item.getFile());
		parentItemModel = item;
	}

	public WindowModel(FileNodeModel file) {
		this(new ListModel(file));
	}
	
	public WindowModel(ListModel listModel)
	{
		this.listModel = listModel;

		listModel.setWindowModel(this);
		
		layoutManager = new LayoutCreator(this);
		layout = layoutManager.generateLayout();
	}
	


	public String getTitle()
	{
		return listModel.getFile().getName();
	}
	

	
	public boolean isPinned() {
		return pinned;
	}



	public void setPinned(boolean pinned) {
		if (this.pinned != pinned)
		{
			this.pinned = pinned;
			updateParentPinnedState(pinned);
			needsUpdate();
		}
	}

	/**
	 * set's this parentItem's state,
	 * then, called recusrively on parent window to call the same down the line.
	 * @param isPinned
	 */
	private void updateParentPinnedState(boolean isPinned) {
		if (parentItemModel == null) return;
		if (isPinned) parentItemModel.incrementPinCount();
		else parentItemModel.decrementPinCount();
		WindowModel win = getParentWindow();
		if (win != null) win.updateParentPinnedState(isPinned);
		
		// make sure the parent item is still selected!
		//if (!pinned && parentItemModel != null)
		//	parentItemModel.setSelected(true);
		if (parentItemModel != null)
			parentItemModel.setSelected(!isPinned);

	}

	public ListModel getListModel() {
		return listModel;
	}

	public int getIndexOfInterest() {
		return indexOfInterest;
	}

	public void setIndexOfInterest(int primaryIndex) {
		if (this.indexOfInterest != primaryIndex)
		{
			this.indexOfInterest = primaryIndex;
			layout = layoutManager.generateLayout();
			needsUpdate();
		}
	}

	public ColumnModel getColumnModel() {
		return columnModel;
	}

	public void setColumnModel(ColumnModel columnModel) {
		this.columnModel = columnModel;
	}

	// Methods for fluid negoitation.
	
	public float getMinHeight()
	{
		return layoutManager.getMinHeight() + WindowBarView.getHeight();
	}
	
	public float getPerferedHeight()
	{
		return layoutManager.getPerferedHeight() + WindowBarView.getHeight();
	}
	
	public void setMaxHeight(float maxHeight)
	{
		layoutManager.setMaxHeight(maxHeight - WindowBarView.getHeight());
		layout = layoutManager.generateLayout();
		needsUpdate();
	}
	
	public float getHeight() {
		return layout.getHeight() + WindowBarView.getHeight();
	}

	public Layout getLayout() {
		return layout;
	}

	public void makeActive() {
		if (columnModel != null) columnModel.setActiveWindowModel(this);
	}

	public void updateChildrenWindows() {
		
		if (columnModel == null)
		{
			System.err.println("can't update children windows because column model could not be found.");
			return;
		}
		

		ColumnModel nextColumn = columnModel.getNextColumn();
		
		// go through each item in this window
		for (ItemModel item : listModel.getItems())
		{
			if((item.isSelected() || item.isPinned()) && item.getFile().isFolder())
			{
				// make sure a matching window exists
				boolean found = false;
				for (WindowModel window : nextColumn.getWindowModels())
				{
					if (window.getParentItemModel() == item) {found = true; break;}
				}
				if (!found) // if not found, add a new window
				{
					WindowModel newWin = new WindowModel(item);
					nextColumn.addWindow(newWin);
				}
			}
			else
			{
				// remove any matching windows, should they exist
				for (WindowModel window : nextColumn.getWindowModels())
				{
					if (window.getParentItemModel() == item) window.close();
				}
			}
		}
		
		nextColumn.sortWindows();
		// TODO: finish this method by ordering children.
	}

	public void close() {
		// remove all child windows.
		ColumnModel nextColumn = columnModel.getNextColumn();
		if (nextColumn != null)
		{
			for (WindowModel window : nextColumn.getWindowModels())
			{
				if (listModel.containsItem(window.getParentItemModel()))
				{
					window.close();	
				}
			}
		}
		
		// unpin
		setPinned(false);
		
		// deselct parent
		if (parentItemModel != null)
		{
			parentItemModel.setSelected(false);
		}
		
		// remove this window
		if (columnModel != null) columnModel.removeWindow(this);
	}


	public ItemModel getParentItemModel() {
		return parentItemModel;
	}

	public void setParentItemModel(ItemModel parentItemModel) {
		this.parentItemModel = parentItemModel;
	}

	public int compareTo(WindowModel other) {
		int myWinPos = getParentWindowPosition();
		int myItemPos = getParentItemPosition();
		int otherWinPos = other.getParentWindowPosition();
		int otherItemPos = other.getParentItemPosition();
		
		// sort first my window order
		if (myWinPos == -1 || otherWinPos == -1) return 0;
		if (myWinPos != otherWinPos)
		{
			if (myWinPos < otherWinPos) return -1;
			else return 1;
		}
		
		// then by item order
		if (myItemPos == -1 || otherItemPos == -1) return 0;
		if (myItemPos < otherItemPos) return -1;
		else if (myItemPos > otherItemPos) return 1;
		else return 0;
	}

	private int getParentItemPosition() {
		if (parentItemModel == null) return -1;
		return parentItemModel.getPosition();
	}

	private int getParentWindowPosition() {
		
		WindowModel parent = getParentWindow();
		if (parent == null) return -1;
		return parent.getPosition();
	}

	
	private int getPosition() {
		if (columnModel == null) return -1;
		return columnModel.getPosition(this);
	}

	private WindowModel getParentWindow()
	{
		if (parentItemModel == null) return null;
		return parentItemModel.getParentListModel().getWindowModel();
	}

	public boolean isCloseable() {
		return closeable;
	}

	public void setCloseable(boolean closeable) {
		this.closeable = closeable;
	}


}
