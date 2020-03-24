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

import edu.cmu.sun.view.WindowView;

/**
 * Model for the state of a column of windows.
 * Contains a list of WindowModels for the contents of this column.
 * Also does some basic layout for the components within the column.
 * 
 * 
 * @author Braden Kowitz, Jake Pierson, Jessica Smith
 */
public class ColumnModel extends Model{

	public static final float SPACING = 20 * SceneModel.P2M;
	public static final float UNSTACKED_WIDTH = WindowView.WIDTH;
	public static final float STACKED_WIDTH = WindowView.WIDTH * 0.2f;
	
	
	// keeps a list of windows in the order they appear in the list
	ArrayList<WindowModel> windows;
	WindowModel activeWindowModel = null;
	SceneModel sceneModel = null;
	Stacked stackedModel;
	
	public static class Stacked extends Model
	{
		boolean stacked = false;

		public boolean isStacked() {
			return stacked;
		}

		public void setStacked(boolean folded) {
			if (this.stacked != folded)
			{
				this.stacked = folded;
				needsUpdate();
			}
		}
		
	}
	

	
	public ColumnModel()
	{
		windows = new ArrayList<WindowModel>();
		stackedModel = new Stacked();
	}

	public void addWindow(WindowModel win) {
		windows.add(win);
		win.setColumnModel(this);
		activeWindowModel = win;
		if (sceneModel != null) sceneModel.makeUnstacked(this);
		needsUpdate();
	}
	
	public void removeWindow(WindowModel win) {
		windows.remove(win);
		if (activeWindowModel == win)
		{
			if (windows.isEmpty()) activeWindowModel = null;
			else activeWindowModel = windows.get(0);
		}
		needsUpdate();
		if (isEmpty() && sceneModel != null)
		{
			sceneModel.updateStacking();
		}
	}



	public boolean contains(WindowModel model) {
		return windows.contains(model);
	}

	/**
	 * returns a list of the windows in this model.
	 * Note: this returns a COPY of the underlying structure.
	 * @return
	 */
	public List<WindowModel> getWindowModels() {
		return new LinkedList<WindowModel>(windows);
	}

	public WindowModel getActiveWindowModel() {
		return activeWindowModel;
	}

	public void setActiveWindowModel(WindowModel activeWindowModel) {
		if (this.activeWindowModel != activeWindowModel)
		{
			this.activeWindowModel = activeWindowModel;
			needsUpdate();
		}
	}
	
	protected void needsUpdate()
	{
		resizeWindowModels();
		super.needsUpdate();
	}
	
	private void resizeWindowModels()
	{
		if (windows.isEmpty() || activeWindowModel == null) return;
		
		// we need to space the windows apart
		float totalSpacing = SPACING * (windows.size() -1);

		
		// figure out how much space we have:
		float activeViewMinHeight = activeWindowModel.getMinHeight();
		float activeViewPrefHeight = activeWindowModel.getPerferedHeight();
		float otherViewsMinHeight = 0;
		float otherViewsPrefHeight = 0;
		for (WindowModel winModel : windows)
		{
			if (winModel != activeWindowModel)
			{
				otherViewsMinHeight += winModel.getMinHeight();
				otherViewsPrefHeight += winModel.getPerferedHeight();
			}
		}
		
		float minLayoutHeight = activeViewMinHeight + otherViewsMinHeight + totalSpacing;
		float prefLayoutHeight = activeViewPrefHeight + otherViewsPrefHeight + totalSpacing;
		float sceneHeight = SceneModel.getHeight();
		
		if (minLayoutHeight > sceneHeight)
		{
			System.err.println("ERR: All windows can't be positioned in a column.");
			for (WindowModel winModel : windows)
			{
				float height = winModel.getMinHeight();
				winModel.setMaxHeight(height);
			}
			return;
		}
		
		if (prefLayoutHeight < sceneHeight)
		{
			// all views can be positioned at prefered height.
			for (WindowModel winModel : windows)
			{
				float height = winModel.getPerferedHeight();
				winModel.setMaxHeight(height);
			}
			return;
		}
		
		// how much space is left in the column for remaining windows
		float spaceLeft = sceneHeight - totalSpacing;
		
		// size the active view:
		activeWindowModel.setMaxHeight(spaceLeft - otherViewsMinHeight);
		spaceLeft -= activeWindowModel.getHeight();
		
		// split the slack between the rest of the views:
		float slack = spaceLeft - otherViewsMinHeight;
		float slackForEach = slack / (float) (windows.size() - 1);
		if (slack < 0) System.err.println("ERR: negative slack in column layout.");
		
		for (WindowModel winModel : windows)
		{
			if (winModel != activeWindowModel)
			{
				float maxHeight = winModel.getMinHeight() + slackForEach;
				winModel.setMaxHeight(maxHeight);
			}
		}


	}


	public SceneModel getSceneModel() {
		return sceneModel;
	}

	public void setSceneModel(SceneModel sceneModel) {
		this.sceneModel = sceneModel;
	}

	public float getWidth() {
		if (windows.isEmpty()) return 0f;
		if (stackedModel.isStacked()) return STACKED_WIDTH;
		else return UNSTACKED_WIDTH;
	}


	public ColumnModel getNextColumn() {
		if (sceneModel == null)
		{
			System.err.println("cannot get next column because scene model can not be found.");
			return null;
		}
		
		return sceneModel.getNextColumn(this);
	}

	public ColumnModel getPrevColumn() {
		if (sceneModel == null)
		{
			System.err.println("cannot get prev column because scene model can not be found.");
			return null;
		}
		
		return sceneModel.getPrevColumn(this);
	}
	
	public void sortWindows() {
		Collections.sort(windows);
		needsUpdate();;
	}

	public int getPosition(WindowModel model) {
		return windows.indexOf(model);
	}

	public Stacked getStackedModel() {
		return stackedModel;
	}



	public boolean isEmpty()
	{
		return windows.isEmpty();
	}

	public int getSize() {
		return windows.size();
	}

	
}
