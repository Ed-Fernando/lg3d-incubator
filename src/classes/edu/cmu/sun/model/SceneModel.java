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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A state model of the available screen space and column order.
 * 
 * The Scene model contains a list of ColumnModels. It is responsible
 * for layout out columns so that they fit on the screen. So, it stacks
 * columns as necessary to make room.
 * 
 * It also handles updates to the total amount of screen space left.  There are
 * some bugs in this, but it does an okay job for the demo.
 * 
 * @author Braden Kowitz, Jake Pierson, Jessica Smith
 *
 */
public class SceneModel extends Model{

	List<ColumnModel> columns;
	
	// sorted list of recently used columns
	// recently used columns are in the back.
	LinkedList<ColumnModel> recentlyUsedColumns;
	
	public static final float P2M = .0002f;
	
	public static final float BORDER = 30 * SceneModel.P2M;
	public static final float BOTTOM_BORDER = 150 * SceneModel.P2M;
	
	static float sceneHeight = 800 * SceneModel.P2M; // defaults, not used in practice
	static float sceneWidth = 1200 * SceneModel.P2M;
	
	public static final float SPACING = 30 * SceneModel.P2M;
	
	public SceneModel()
	{
		columns = new ArrayList<ColumnModel>();
		recentlyUsedColumns = new LinkedList<ColumnModel>();
	}
	
	public void addColumn(ColumnModel col)
	{
		col.setSceneModel(this);
		columns.add(col);
		recentlyUsedColumns.add(col);
		updateStacking();
		needsUpdate();
	}
	
	public void removeColumn(ColumnModel col)
	{
		if (col.getSceneModel() == this) col.setSceneModel(null);
		columns.remove(col);
		recentlyUsedColumns.remove(col);
		updateStacking();
		needsUpdate();
	}
	
	public static float getHeight() {
		return sceneHeight;
	}
	public static float getWidth() {
		return sceneWidth;
	}

	public List<ColumnModel> getColumnModels() {
		return new ArrayList<ColumnModel>(columns);
	}

	public ColumnModel getNextColumn(ColumnModel afterThisColumn) {
		boolean found = false;
		for (ColumnModel column : columns)
		{
			if (found) return column;
			if (column == afterThisColumn) found = true;
		}
		
		// not found
		ColumnModel newColumn = new ColumnModel();
		addColumn(newColumn);
		return newColumn;
	}

	public ColumnModel getPrevColumn(ColumnModel beforeThisColumn) {
		ColumnModel prevColumn = null;
		for (ColumnModel column : columns)
		{
			if (column == beforeThisColumn) return prevColumn;
			prevColumn = column;
		}
		return null;
	}

	public void makeUnstacked(ColumnModel col) {
		makeRecent(col);
		// unstack this model (Facing forward)
		if (col.getStackedModel().isStacked())
		{
			col.getStackedModel().setStacked(false);
		}
		updateStacking();
	}
	
	public void makeRecent(ColumnModel col)
	{
		if (!recentlyUsedColumns.contains(col))
		{
			System.err.println("SceneModel can't make column active, it does not exist in model.");
			return;
		}
		
		// update it's position to make it the most recently active
		recentlyUsedColumns.remove(col); // move the item to the back of the list
		recentlyUsedColumns.add(col);
		
		//int index = columns.indexOf(col);
		//System.out.println("making colum recent: " + index);
		//System.out.print("New Recent List: ");
		//for (ColumnModel col1 : recentlyUsedColumns)
		//	System.out.print(columns.indexOf(col1)+ " ");
		//System.out.println();
	}

	public void updateStacking() {
		//System.out.println("updateing scene");
		while (getSceneWidth() > sceneWidth)
		{
			boolean success = stackOldColumn();
			if (!success)
			{
				System.err.println("not all columns will fit in the scene.");
				break;
			}
		}
		float spaceNeededToUnfold = ColumnModel.UNSTACKED_WIDTH - ColumnModel.STACKED_WIDTH;
		while (getSceneWidth() < sceneWidth - spaceNeededToUnfold)
		{
			boolean success = unstackRecentColumn();
			if (!success) break;
		}
		needsUpdate();
	}

	private boolean unstackRecentColumn() {
		
		LinkedList<ColumnModel> reversed = new LinkedList<ColumnModel>(recentlyUsedColumns);
		Collections.reverse(reversed);
		// now, recent items are in the front of the list.
		for (ColumnModel col : reversed)
		{
			//int index = columns.indexOf(col);
			if (col.getStackedModel().isStacked())
			{
				//System.out.println("stacking column " + index);
				col.getStackedModel().setStacked(false);
				return true;
			}
			//else System.out.println("\talready unstacked column " + index);
		}
		return false;
	}

	/**
	 * tries to stack the least recently used column.
	 * If there are no more columns left to stack, it returns false.
	 * Otherwise, true.
	 * @return
	 */
	private boolean stackOldColumn() {
		// older items are in the front of the list:
		for (ColumnModel col : recentlyUsedColumns)
		{
			//int index = columns.indexOf(col);
			if (!col.getStackedModel().isStacked())
			{
				//System.out.println("stacking column " + index);
				col.getStackedModel().setStacked(true);
				return true;
			}
			//else System.out.println("\talready stacked column " + index);
		}
		return false;
	}

	private float getSceneWidth() {
		float width = - SPACING;
		for (ColumnModel col : columns)
		{
			if (!col.isEmpty())
				width += col.getWidth() + SPACING;
		}
		return width;
	}

	
	float lastSizeWidth = 0f;
	float lastSizeHeight = 0f;

	public void setSize(float height, float width) {
		if (height != lastSizeWidth || width != lastSizeHeight)
		{
			lastSizeWidth = height;
			lastSizeHeight = width;
			System.out.println("screen h: " + height + " w: " + width);
			System.out.println("scene h: " + sceneHeight + " w: " + sceneWidth);
			
			sceneHeight = height - BORDER - BOTTOM_BORDER;
			sceneWidth = width - BORDER * 2;
			needsUpdate();
			for (ColumnModel col : columns) col.needsUpdate();
		}

	}
	
}
