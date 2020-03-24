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
import edu.cmu.sun.folds.LayoutComponent;
import edu.cmu.sun.interpolators.SimpleMotionAnimation;


/**
 * Assembles, draws, and updates the middle fold component of a folded-scrolling window.
 * 
 * @author Braden Kowitz, Jessica Smith
 */
public class MiddleFoldView extends AbstractFoldView {

	protected Component3D topCrinkle;
	protected Component3D bottomCrinkle;
	protected Component3D slab;
	
	public MiddleFoldView(LayoutComponent layout) {
		super(layout);
		initialize();
	}
	
	/**
	 * add components to the tree
	 *
	 */
	protected void initialize()
	{
		float crinkleHeight = CRINKLE_PINCH_Y;
		float totalHeight = getHeight();
		float middleHeight = totalHeight - crinkleHeight * 2;
		if (middleHeight < 0) middleHeight = 0;
		
		topCrinkle = makeTopCrinkleShape();
		topCrinkle.setAnimation(new SimpleMotionAnimation(250));
		this.addChild(topCrinkle);
		
		slab = makeSlabShape();
		slab.setAnimation(new SimpleMotionAnimation(250));
		slab.setScale(1f, middleHeight, 1f);
		slab.setTranslation(0f, -crinkleHeight, 0f);
		this.addChild(slab);
		
		bottomCrinkle = makeBottomCrinkleShape();
		bottomCrinkle.setAnimation(new SimpleMotionAnimation(250));
		bottomCrinkle.setTranslation(0f, crinkleHeight - totalHeight, 0f);
		this.addChild(bottomCrinkle);
	}
	
	
	public void update(AnimationPlan plan, int msec)
	{
		super.update(plan, msec);
		
		float totalHeight = getHeight();
		float crinkleHeight = CRINKLE_PINCH_Y;
		float middleHeight = totalHeight - crinkleHeight * 2;
		if (middleHeight < 0) middleHeight = 0;
		
		if (totalHeight == 0)
		{
			plan.changeScale(topCrinkle, 0, msec, new Vector3f(1f, 0f, 0f));
			plan.changeTranslation(slab, 0, msec, new Vector3f(0f, 0f, 0f));
			plan.changeScale(slab, 0, msec, new Vector3f(1f, 0f, 0f));
			plan.changeTranslation(bottomCrinkle, 0, msec, new Vector3f(0f, 0f, 0f));
			plan.changeScale(bottomCrinkle, 0, msec, new Vector3f(1f, 0f, 0f));
		}
		else
		{
			plan.changeScale(topCrinkle, 0, msec, new Vector3f(1f,1f,1f));
			plan.changeTranslation(slab, 0, msec, new Vector3f(0f, -crinkleHeight, 0f));
			plan.changeScale(slab, 0, msec, new Vector3f(1f, middleHeight, 1f));
			plan.changeScale(bottomCrinkle, 0, msec, new Vector3f(1f,1f,1f));
			plan.changeTranslation(bottomCrinkle, 0, msec, new Vector3f(0f, crinkleHeight - totalHeight, 0f));
		}
	}

	
}
