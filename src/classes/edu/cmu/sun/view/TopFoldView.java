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
 * Assembles, draws, and updates the top fold component of a folded-scrolling window.
 * 
 * @author Braden Kowitz, Jessica Smith
 */
public class TopFoldView extends AbstractFoldView{

	protected Component3D bottomCrinkle;
	protected Component3D slab;
	
	public TopFoldView(LayoutComponent layout) {
		super(layout);
		
		initialize();
	}

	private void initialize()
	{
		float totalHeight = getHeight();
		float crinkleHeight = CRINKLE_PINCH_Y;
		float slabHeight = totalHeight - crinkleHeight;
		if (slabHeight < 0) slabHeight = 0;
		
		slab = makeSlabShape();
		slab.setAnimation(new SimpleMotionAnimation(250));
		slab.setScale(1f, slabHeight, 1f);
		this.addChild(slab);
		
		bottomCrinkle = makeBottomCrinkleShape();
		bottomCrinkle.setAnimation(new SimpleMotionAnimation(250));
		bottomCrinkle.setTranslation(0f, -slabHeight, 0f);
		this.addChild(bottomCrinkle);
	}
	
	
	public void update(AnimationPlan plan, int msec)
	{
		super.update(plan, msec);
		
		float totalHeight = getHeight();
		float crinkleHeight = CRINKLE_PINCH_Y;
		float slabHeight = totalHeight - crinkleHeight;
		if (slabHeight < 0) slabHeight = 0;
		
		if (totalHeight == 0)
		{
			plan.changeScale(slab, 0, msec, new Vector3f(1f, 0.00001f, 0f));
			plan.changeTranslation(bottomCrinkle, 0, msec, new Vector3f(0f, 0f, 0f));
			plan.changeScale(bottomCrinkle, 0, msec, new Vector3f(1f, 0f, 0f));
		}
		else
		{
			plan.changeScale(slab, 0, msec, new Vector3f(1f, slabHeight, 1f));
			plan.changeScale(bottomCrinkle, 0, msec, new Vector3f(1f, 1f, 1f));
			plan.changeTranslation(bottomCrinkle, 0, msec, new Vector3f(0f, -slabHeight, 0f));
		}
    	
	}
}
