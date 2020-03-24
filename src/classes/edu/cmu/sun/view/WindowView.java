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

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Component3D;

import edu.cmu.sun.animation.AnimationPlan;
import edu.cmu.sun.animation.TransitionManager;
import edu.cmu.sun.controller.WindowController;
import edu.cmu.sun.folds.Layout;
import edu.cmu.sun.folds.LayoutTransitoner;
import edu.cmu.sun.interpolators.SimpleMotionAnimation;
import edu.cmu.sun.model.ItemModel;
import edu.cmu.sun.model.Model;
import edu.cmu.sun.model.ModelListener;
import edu.cmu.sun.model.SceneModel;
import edu.cmu.sun.model.WindowModel;

public class WindowView extends Component3D implements ModelListener{

	public static final int WINDOW_TRANSISION_MSEC = 700;
	public static final int FOLD_START_MSEC = 700;
	public static final int FOLD_DURATION_MSEC = 100;
	private Vector3f ROTATION_AXIS = new Vector3f(0f, (float)(0.5f * Math.PI) , 0f);
	
	public static final float Z_LAYERING = 2f * SceneModel.P2M;
	
	public static final float WIDTH = 266f * SceneModel.P2M;
	public static final float DEPTH = 15f * SceneModel.P2M;
	
	WindowModel model;
	WindowController controller;
	LayoutTransitoner animationManager;
	Layout activeLayout;
	boolean foldedBack = false;
	
	// sub-components:
	WindowBarView windowBarView;
	WindowContentView windowContentView;
	
	public WindowView(WindowModel model)
	{
		
		this.model = model;
		model.addModelListener(this);
		
		foldBack();
		
		controller = new WindowController(model);
		animationManager = new LayoutTransitoner();
		
		windowBarView = new WindowBarView(model, controller);
		this.addChild(windowBarView);

		
		windowContentView = new WindowContentView();
		windowContentView.setTranslation(0f, -WindowBarView.getHeight(), 0f);
		this.addChild(windowContentView);

		
		this.setAnimation(new SimpleMotionAnimation(250));

		update();
	}
	

	public void foldBack() {
		if (foldedBack) return;
		foldedBack = true;
	
		//this.setVisible(false);
		//this.setRotationAxis(rotationAxis.x, rotationAxis.y, rotationAxis.z);
		//this.setRotationAngle((float)(Math.PI * 0.5));\
		AnimationPlan plan = TransitionManager.getPrepPlan();
		plan.setVisible(this, 0, false);
		plan.changeRotation(this, FOLD_START_MSEC, FOLD_DURATION_MSEC, ROTATION_AXIS, Math.PI * 0.5);
		
	}

	public void foldForward(boolean delay) {
		if (!foldedBack) return;
		foldedBack = false;
		
		// fold soooner if requested
		int foldStart = FOLD_START_MSEC;
		if (!delay) foldStart = 0;
				
		AnimationPlan plan = TransitionManager.getTransitionPlan();
		plan.setVisible(this, foldStart, true);
		plan.changeRotation(this, foldStart, FOLD_DURATION_MSEC, ROTATION_AXIS, 0f);
	}

	public boolean isFolded()
	{
		return foldedBack;
	}
	
	private void update()
	{
		
		Layout nextLayout = model.getLayout();
		AnimationPlan prepPlan = TransitionManager.getPrepPlan();
		if (activeLayout == null)
		{
			activeLayout = nextLayout;
			windowContentView.syncToLayout(activeLayout, prepPlan);
		}
		else
		{
			Layout transitionLayout = animationManager.prepareAnimation(activeLayout, nextLayout);
			
			//System.out.println("ACTIVE " + activeLayout);
			//System.out.println("NEXT " + nextLayout);
			//System.out.println("TRANSITION " + transitionLayout);
			
			windowContentView.syncToLayout(transitionLayout, prepPlan);
			
			transitionLayout.updateToNextRanges();
			AnimationPlan transitionPlan = TransitionManager.getTransitionPlan();
			windowContentView.update(transitionPlan , WINDOW_TRANSISION_MSEC);
			
			activeLayout = nextLayout;
			
			// we need to re-consolidate so that the CrinkleViewController can understand
			// if the crinkle is above or below the primary scroll section.
			// TODO: do we need to do post-consolidation now that the underlying model is NOT segmented?
			AnimationPlan consolidationPlan = TransitionManager.getConsolidationPlan();
			windowContentView.syncToLayout(activeLayout, consolidationPlan);
		
		}
	}


	public void modelChanged(Model m) {
		if (m != model) return;
		update();
	}


	public WindowModel getModel() {
		return model;
	}

	public ItemView getItemViewMatchingModel(ItemModel itemModel) {
		return windowContentView.getItemViewMatchingModel(itemModel);
	}







	
}
