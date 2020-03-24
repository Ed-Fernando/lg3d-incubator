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

import javax.vecmath.Color3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Material;
import org.jdesktop.lg3d.sg.PolygonAttributes;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.wg.Component3D;

import edu.cmu.sun.animation.AnimationPlan;
import edu.cmu.sun.folds.LayoutComponent;
import edu.cmu.sun.interpolators.SimpleMotionAnimation;

/**
 * Parent view for all items in a folded-scrolling view.
 * 
 * This is the parent for folds (top, middle, bottom) and the list
 * portions of the view.
 * 
 * It privides a pointer to the LayoutComponent that this list item
 * is required to draw.  It also privdes the appearance for all of the views.
 * 
 * 
 * @author Braden Kowitz, Jessica Smith
 *
 */
public abstract class AbstractListView extends Component3D{

	LayoutComponent layout;
	
	private static Appearance cullAppearance;
	private static Appearance noncullAppearance;
	
	public AbstractListView(LayoutComponent layout)
	{
		this.layout = layout;
		this.setAnimation(new SimpleMotionAnimation(250));
	}
	
	
	public abstract float getHeight();
	
	public abstract void update(AnimationPlan plan, int msec);
	
	/**
	 * used as the common appearacnce for all list views
	 */
	synchronized public static Appearance getAppearance(boolean backfaceCulling)
	{
		if (cullAppearance == null || noncullAppearance == null);
		
		cullAppearance = new Appearance();
		noncullAppearance = new Appearance();
		
		// culling
		PolygonAttributes pa = new PolygonAttributes();
        pa.setCullFace(PolygonAttributes.CULL_NONE);
        noncullAppearance.setPolygonAttributes(pa);

        // material colors
        Color3f aColor  = new Color3f(1f, 1f, 1f);
        Color3f eColor  = new Color3f(0.0f, 0.0f, 0.0f);
        Color3f dColor  = new Color3f(0.8f, 0.8f, 0.8f);
        Color3f sColor  = new Color3f(1.0f, 1.0f, 1.0f);
        Material mat = new Material(aColor, eColor, dColor, sColor, 15.0f);
        mat.setLightingEnable(true);
        mat.setShininess(128f);
        cullAppearance.setMaterial(mat);
        noncullAppearance.setMaterial(mat);
        
        
		if (backfaceCulling) return cullAppearance;
		else return noncullAppearance;
	}


	public LayoutComponent getLayout() {
		return layout;
	}


	public void setLayout(LayoutComponent layout) {
		this.layout = layout;
	}
	
}
