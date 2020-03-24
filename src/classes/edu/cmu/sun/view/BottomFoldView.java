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
 * Assembles, draws, and updates the bottom fold component of a folded-scrolling window.
 * 
 * @author Braden Kowitz, Jessica Smith
 */
public class BottomFoldView extends AbstractFoldView{

	protected Component3D topCrinkle;
	protected Component3D slab;
	
	public BottomFoldView(LayoutComponent layout) {
		super(layout);
		
		initialize();
	}

	private void initialize()
	{
		float totalHeight = getHeight();
		float crinkleHeight = CRINKLE_PINCH_Y;
		float slabHeight = totalHeight - crinkleHeight;
		
		
		topCrinkle = makeTopCrinkleShape();
		topCrinkle.setAnimation(new SimpleMotionAnimation(250));
		
		slab = makeSlabShape();
		slab.setAnimation(new SimpleMotionAnimation(250));
		
		
		if (totalHeight == 0)
		{
			topCrinkle.setScale(1f, 0f, 0f);
			slab.setScale(1f, 0f, 0f);
			slab.setTranslation(0f, 0f, 0f);
		}
		else
		{
			topCrinkle.setScale(1f, 1f, 1f);
			slab.setScale(1f, slabHeight, 1f);
			slab.setTranslation(0f, -crinkleHeight, 0f);
		}
		
		
		
		this.addChild(topCrinkle);
		this.addChild(slab);
	}


	public void update(AnimationPlan plan, int msec)
	{
		// todo, should call w/ plan
		super.update(plan, msec);
		
		float totalHeight = getHeight();
		float crinkleHeight = CRINKLE_PINCH_Y;
		float slabHeight = totalHeight - crinkleHeight;
		
		if (totalHeight == 0)
		{
			plan.changeScale(topCrinkle, 0, msec, new Vector3f(1f, 0f, 0f));
			plan.changeTranslation(slab, 0, msec, new Vector3f(0f, 0f, 0f));
			plan.changeScale(slab, 0, msec, new Vector3f(1f, 0f, 0f));
		}
		else
		{
			plan.changeScale(topCrinkle, 0, msec, new Vector3f(1f, 1f, 1f));
			plan.changeTranslation(slab, 0, msec, new Vector3f(0f, -crinkleHeight, 0f));
			plan.changeScale(slab, 0, msec, new Vector3f(1f, slabHeight, 1f));
		}	
	}


}
