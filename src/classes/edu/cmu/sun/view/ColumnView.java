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

import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Cursor3D;

import edu.cmu.sun.animation.AnimationPlan;
import edu.cmu.sun.animation.TransitionManager;
import edu.cmu.sun.controller.HoverEventAdapter;
import edu.cmu.sun.model.ColumnModel;
import edu.cmu.sun.model.ItemModel;
import edu.cmu.sun.model.Model;
import edu.cmu.sun.model.ModelListener;
import edu.cmu.sun.model.SceneModel;
import edu.cmu.sun.model.WindowModel;


/**
 * Coordinates the drawing of a ColumnModel, and contains several WindowViews.
 * 
 * The column view represents each column in the sceen.  Whenever the underlying
 * column model changes, this view synchronizes with the model to update the surrounding
 * views.
 * 
 * @author Braden Kowitz, Jessica Smith
 */
public class ColumnView extends Component3D implements ModelListener, HoverEventAdapter.Listener{

	ColumnModel model;
	List<WindowView> windows;
	SceneView sceneView;
	ColumnGlueView columnGlueView;
	Component3D contents;
	
	
	private static final int WINDOW_POSITION_MSEC = 700;
	private static final int HOVER_TIME_MSEC = 250;
	private static final float STACKING_ANGLE = .85f;
	private static final int STACKING_DURATION_MSEC = 400;
	private Vector3f ROTATION_AXIS = new Vector3f(0f, (float)(0.5f * Math.PI) , 0f);
	
	private static boolean TOP_ALIGN = true;
	
	public ColumnView(ColumnModel model, SceneView sceneView)
	{
		this.model = model;
		model.addModelListener(this);
		model.getStackedModel().addModelListener(this);
		
		this.sceneView = sceneView;
		
		contents = new Component3D();
		this.addChild(contents);
		
		this.setAnimation(new NaturalMotionAnimation(250));
		contents.setAnimation(new NaturalMotionAnimation(250));
		
		windows = new LinkedList<WindowView>();
		this.setCursor(Cursor3D.SMALL_CURSOR);
		columnGlueView = new ColumnGlueView(this);
		this.addChild(columnGlueView);
		
		// hook up an event adapter to receive hover events.
		contents.addListener(
				new HoverEventAdapter(this, HOVER_TIME_MSEC));
		
		//update();
	}
	
	public void update()
	{
		//System.out.println("updating columnView");
		
		// make sure we have all the window views we need, and in order:
		syncViewsToModel();
	
		// position the views.
		positionViews();

	}


	private void syncViewsToModel() {
		
		AnimationPlan plan = TransitionManager.getTransitionPlan();
		
		List<WindowView> nextWindows = new LinkedList<WindowView>();
		
		// create a new list of windows:
		for (WindowModel winModel : model.getWindowModels())
		{
			WindowView winView = getViewForModel(winModel);
			if (winView == null)
			{
				// create a new window
				winView = new WindowView(winModel);
				// add it to the secene
				plan.addComponent(winView, 0, contents, false);
			}
			nextWindows.add(winView);
		}
		
		// remove any windows w/o models:
		for (WindowView winView : windows)
		{
			if (!nextWindows.contains(winView))
			{
				// remove the view from the scene.
				//plan.changeScale(winView, 0, WINDOW_CLOSE_TIME, 0.5f);
				//winView.foldBack();
				plan.removeComponent(winView, 0, contents);
			}
		}
		
		windows = nextWindows;
	}
	

	private void positionViews()
	{
		AnimationPlan transPlan = TransitionManager.getTransitionPlan();
		AnimationPlan prepPlan = TransitionManager.getPrepPlan();
		
		float totalHeight = ColumnModel.SPACING * (windows.size() - 1);
		for (WindowView view : windows) totalHeight += view.getModel().getHeight();
		
		
		float position;;
		if (TOP_ALIGN) position = SceneModel.getHeight() / 2f;
		else position = totalHeight / 2f;
		
		for (WindowView view : windows)
		{
			Vector3f posVec = new Vector3f(0f, position, 0f);
			// folded views are new
			// we need to position them in the prep stage, and fold them forward.
			//boolean delay = ! (model.getSize() == 1); // if this is the only window
			boolean delay = true;
			if (view.isFolded())
			{
				prepPlan.changeTranslation(view, 0, 0, posVec);
				view.foldForward(delay);
			}
			else // other views just animate to their new place.
			{
				transPlan.changeTranslation(view, 0, WINDOW_POSITION_MSEC, posVec);
			}
			position -= view.getModel().getHeight() + ColumnModel.SPACING;
		}
	}
	/**
	 * returns the windowView with the corresponding WindowModel.
	 * However, if no view can be found, it returns null;
	 * @param winModel
	 * @return
	 */
	private WindowView getViewForModel(WindowModel winModel) {
		for (WindowView view : windows)
		{
			if (view.getModel() == winModel) return view;
		}
		return null;
	}

	public void modelChanged(Model m) {
		if (m == model) update();
		else if (m == model.getStackedModel()) updateStacked();
	}

	private void updateStacked() {
		boolean stacked = model.getStackedModel().isStacked();
		float stackingAngle = 0f;
		if (stacked) stackingAngle = STACKING_ANGLE;
		
		AnimationPlan plan = TransitionManager.getTransitionPlan();
		plan.changeRotation(contents,0,STACKING_DURATION_MSEC, ROTATION_AXIS, stackingAngle);
	}

	public ColumnModel getModel() {
		return model;
	}

	public List<WindowView> getWindows() {
		return new ArrayList<WindowView>(windows);
	}

	public ColumnView getPrevColumn() {
		if (sceneView == null) return null;
		return sceneView.getPrevColumn(this);
	}

	
	public SceneView getSceneView() {
		return sceneView;
	}


	public ItemView getItemViewMatchingModel(ItemModel itemModel) {
		ItemView itemView = null;
		for (WindowView window : windows)
		{
			itemView = window.getItemViewMatchingModel(itemModel);
			if (itemView != null) return itemView;
		}
		return itemView;
	}

	public void handleHoverEvent(int msec) {
		// TODO: this is a little controller, should we move it elsewhere?
		if (model.getStackedModel().isStacked())
		{
			sceneView.getModel().makeUnstacked(model);
		}
		else
		{
			sceneView.getModel().makeRecent(model);
		}
		
		TransitionManager.updateViews();
	}





}
