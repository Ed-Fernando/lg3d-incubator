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

import edu.cmu.sun.animation.AnimationAction;
import edu.cmu.sun.animation.AnimationPlan;
import edu.cmu.sun.animation.TransitionManager;
import edu.cmu.sun.model.ColumnModel;
import edu.cmu.sun.model.ItemModel;
import edu.cmu.sun.model.Model;
import edu.cmu.sun.model.ModelListener;
import edu.cmu.sun.model.SceneModel;
import edu.cmu.sun.model.WindowModel;

/**
 * Coordinates the drawing and updating of the glue (or spreads) that visually
 * conect windows (or panels) together.
 * 
 * Objects of this class belong to a column and are responsible for coordinating the
 * drawing of glue to the previous column.  
 * 
 * @author Braden Kowitz, Jake Pierson, Jessica Smith
 */
public class ColumnGlueView extends Component3D implements AnimationAction, ModelListener
{

	private static final float WIDTH = SceneModel.SPACING + ItemView.RIGHT_INSET + 1*SceneModel.P2M;
	
	ColumnView columnView;
	ColumnModel.Stacked prevColumnStackedModel;
	List<GlueView> glueViews;
	List<WindowModel> windowModelsForThisGlue;
	
	public ColumnGlueView(ColumnView columnView)
	{
		this.columnView = columnView;
		glueViews = new ArrayList<GlueView>();
		windowModelsForThisGlue =  new LinkedList<WindowModel>();
		listenToAdjacentColumnModels();
	}
	
	private void listenToAdjacentColumnModels() {
		columnView.getModel().addModelListener(this);
		ColumnModel prevColumn = columnView.getModel().getPrevColumn();
		if (prevColumn == null)
		{
			System.err.println("Err: ColumnGlueView could not find next adjacent column model");
			return;
		}
		prevColumn.addModelListener(this);
		prevColumnStackedModel = prevColumn.getStackedModel();
		prevColumnStackedModel.addModelListener(this);
	}

	public void update()
	{
		//System.out.println("updating glue views");
		removeGlueViews();
		if (isGlueVisible()) scheduleGlueCreation();
	}

	private boolean isGlueVisible() {
		if (prevColumnStackedModel == null) return true;
		else return (!prevColumnStackedModel.isStacked());

	}

	private void scheduleGlueCreation() {
		AnimationPlan plan = TransitionManager.getConsolidationPlan();
		plan.addAnimationAction(this, 0, 0, this);
	}

	private void removeGlueViews() {
		stopListeningToWindowModels();
		AnimationPlan plan = TransitionManager.getTransitionPlan();
		for (GlueView view : glueViews)
		{
			plan.removeComponent(view, 0, this);
		}
		glueViews.clear();
	}

	public void doAnimationAction(Component3D target, int duration) {
		if (target != this) return;
		createGlueViews();
	}

	private void createGlueViews() {
		for (WindowView windowView : columnView.getWindows())
		{
			ColumnView prevColumnView = columnView.getPrevColumn();
			if (prevColumnView == null)
			{
				System.err.println("can't create glue, no previous column found");
				return;
			}
			ItemModel itemModel = windowView.getModel().getParentItemModel();
			ItemView itemView = prevColumnView.getItemViewMatchingModel(itemModel);
			
			if (itemView == null)
			{
				System.err.println("can't create glue, no parent ItemView found");
			}
			else
			{
				createGlueView(windowView, itemView, itemModel);
			}
		}
	}

	private void createGlueView(WindowView windowView, ItemView itemView, ItemModel itemModel) {
		
		listenToWindowModel(itemModel.getParentListModel().getWindowModel());
		
		Vector3f vec = new Vector3f();
		float winY = windowView.getTranslationTo(columnView, vec).y + WindowBarView.getHeight();
		float winH = windowView.getModel().getHeight() - WindowBarView.getHeight();
		float itemY = itemView.getTranslationTo(columnView, vec).y;
		itemY += ItemView.INSET;
		float itemH = ItemView.HEIGHT;
		itemH -= ItemView.INSET * 2;
		//float width = calculateWidth(itemY);
		//float width
		
		/*
		System.out.println("CREATING GLUE VIEW");
		System.out.println("\t winY  : " + winY);
		System.out.println("\t winH  : " + winH);
		System.out.println("\t itemY : " + itemY);
		System.out.println("\t itemH : " + itemH);
		*/
		
		GlueView glueView = new GlueView(itemModel);
		glueView.setLeft(itemY, itemH);
		glueView.setRight(winY, winH);
		glueView.setWidth(WIDTH);
		glueView.update();
		
		this.addChild(glueView);
		glueViews.add(glueView);
	}

	private void listenToWindowModel(WindowModel windowModel) {
		if (windowModel == null) return;
		windowModel.addModelListener(this);
		windowModelsForThisGlue.add(windowModel);
	}

	private void stopListeningToWindowModels()
	{
		for (WindowModel winModel : windowModelsForThisGlue)
			winModel.removeModelListener(this);
		windowModelsForThisGlue.clear();
	}
	
	/*
	private float calculateWidth(float itemY) {
		float height = SceneModel.getHeight();
		float scale = Math.abs(itemY / height);
		return MIN_WIDTH + scale * (MAX_WIDTH - MIN_WIDTH);
	}
	*/
	public void modelChanged(Model m) {
		update();
	}


	
}
