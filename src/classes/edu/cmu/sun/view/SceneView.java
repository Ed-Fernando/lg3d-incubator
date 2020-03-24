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
import edu.cmu.sun.animation.TransitionManager;
import edu.cmu.sun.model.ColumnModel;
import edu.cmu.sun.model.Model;
import edu.cmu.sun.model.ModelListener;
import edu.cmu.sun.model.SceneModel;

public class SceneView extends Component3D implements ModelListener{

	SceneModel model;
	List<ColumnView> columns;
	
	private static final int COLUMN_POSITION_MSEC = 400;
	
	public SceneView(SceneModel model)
	{
		this.model = model;
		model.addModelListener(this);
		columns = new ArrayList<ColumnView>();
		
		
	}

	private void update() {
		syncViewsToModel();
		positionColumns();
	}
	
	
	private void syncViewsToModel()
	{
		AnimationPlan plan = TransitionManager.getTransitionPlan();
		List<ColumnView> nextColumns = new LinkedList<ColumnView>();
		
		// create a new list of columns:
		for (ColumnModel colModel : model.getColumnModels())
		{
			ColumnView colView = getViewForModel(colModel);
			if (colView == null)
			{
				// create a new column
				colView = new ColumnView(colModel, this);
				// add it to the secene
				plan.addComponent(colView, 0, this);
			}
			nextColumns.add(colView);
		}
		
		// remove any windows w/o models:
		for (ColumnView colView : columns)
		{
			if (!nextColumns.contains(colView))
			{
				// remove the view from the scene.
				plan.removeComponent(colView, 0, this);
			}
		}
		
		columns = nextColumns;	
		
		
	}

	/**
	 * returns the columnView with the corresponding ColumnModel.
	 * However, if no view can be found, it returns null;
	 * @param colModel
	 * @return
	 */
	private ColumnView getViewForModel(ColumnModel colModel) {
		for (ColumnView view : columns)
		{
			if (view.getModel() == colModel) return view;
		}
		return null;
	}
	
	
	private void positionColumns()
	{
		
		AnimationPlan plan = TransitionManager.getTransitionPlan();
		float position = - SceneModel.getWidth() / 2f;
		
		for (ColumnView column : columns)
		{
			plan.changeTranslation(column, 0, COLUMN_POSITION_MSEC,
					new Vector3f(position, 0f, 0f));
			position += column.getModel().getWidth() + SceneModel.SPACING;
		}
	}
	
	
	public void modelChanged(Model m) {
		if (m != model) return;
		update();
	}

	public ColumnView getPrevColumn(ColumnView toThisColumn) {
		ColumnView prevColumn = null;
		for (ColumnView column : columns)
		{
			if (column == toThisColumn) return prevColumn;
			prevColumn = column;
		}
		return null;
	}

	public SceneModel getModel() {
		return model;
	}
	
	/*
	public ColumnView getNextColumn(ColumnView view)
	{
		int index = columns.indexOf(view);
		if (index == -1) return null;
		if (index+1 < columns.size()) return columns.get(index+1);
	
		// oterwise, create next column and return it.
		model.getNextColumn(view.getModel());
		fasdfaw
		return null;
	}
	*/


	
	
}
