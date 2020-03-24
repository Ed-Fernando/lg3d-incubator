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


import java.util.List;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Geometry;
import org.jdesktop.lg3d.sg.QuadArray;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;

import edu.cmu.sun.animation.AnimationPlan;
import edu.cmu.sun.components.TextComponent;
import edu.cmu.sun.controller.FoldViewController;
import edu.cmu.sun.folds.LayoutComponent;
import edu.cmu.sun.interpolators.CompositeInterpolator3D;
import edu.cmu.sun.interpolators.Interpolator1D;
import edu.cmu.sun.interpolators.LinearInterpolator1D;
import edu.cmu.sun.interpolators.SmoothInterpolator1D;
import edu.cmu.sun.model.ItemModel;
import edu.cmu.sun.model.SceneModel;

/**
 * Parent class of all Fold Views (top, middle, and bottom).
 * 
 * This class provides all of the geometry drawing for the fold views.
 * It also draws the labels that are common between views, and picks up events
 * from mouse clicks and passes the events to the controllers to handle the 
 * user's request.
 * 
 * @author Braden Kowitz, Jessica Smith
 */
public abstract class AbstractFoldView extends AbstractListView implements LgEventListener{
	
//	 TODO: reveist this class to factor out the geometry creation
//	 which will speed up drawing A LOT.
	
	// define some constants from our geometry drawing
	// see binder documentation
	private final int NUM_QUADS_PER_CRINKLE = 15;
	
	static final float WINDOW_WIDTH = WindowView.WIDTH;
	static final float WINDOW_DEPTH = WindowView.DEPTH;
	static final float CRINKLE_PINCH_X = 12f * SceneModel.P2M;
	static final float CRINKLE_PINCH_Y = ItemModel.HEIGHT / 2f;
	static final float CRINKLE_PINCH_Z = WindowView.DEPTH * 0.5f;
	
	protected static final float crinkle_x1 = 0.0f;
	protected static final float crinkle_x2 = CRINKLE_PINCH_X;
	protected static final float crinkle_x3 = WINDOW_WIDTH - CRINKLE_PINCH_X;
	protected static final float crinkle_x4 = WINDOW_WIDTH;
	
	protected static final float crinkle_y1 = 0.0f;
	protected static final float crinkle_y2 = -CRINKLE_PINCH_Y;
	
	protected static final float crinkle_z1 = 0.0f;
	protected static final float crinkle_z2 = -CRINKLE_PINCH_Z;
	
	protected Component3D textLayer;
	protected Component3D leftText;
	protected Component3D middleNumber;
	protected Component3D rightText;
	
	protected FoldViewController controller;
	
	protected final float TEXT_SIZE = TextComponent.DEFAULT_SIZE * .8f;
	protected final float TEXT_RIGHT_OFFSET = 44f * SceneModel.P2M;
	protected final float TEXT_LEFT_OFFSET = 14f * SceneModel.P2M;
	
	static Geometry TopFrontGeometry = null;
	static Geometry TopBackGeometry = null;
	static Geometry BottomFrontGeometry = null;
	static Geometry BottomBackGeometry = null;
	
	/**
	 * basic constructor (duh, I know this isn't helpful)
	 * @param listModel
	 * @param range
	 */
	public AbstractFoldView(LayoutComponent layout) {
		super(layout);
		controller = new FoldViewController(layout);
		initialize();
	}
	
	private void initialize()
	{
		//System.out.println("initializing CrinkleView!");
		
		// create a layer that is raised slightly above the window plane
		textLayer = new Component3D();
		//- Prop.LINE_HEIGHT - Prop.LINE_SPACING
		textLayer.setTranslation(0f, -getHeight()/2f - ItemModel.CONTENT_HEIGHT/2f , WindowView.Z_LAYERING);
		this.addChild(textLayer);
		
		leftText = getFirstItemText();
		textLayer.addChild(leftText);
		leftText.addListener(this);
		
		middleNumber = getNumberText();
		textLayer.addChild(middleNumber);
		
		rightText = getLastItemText();
		textLayer.addChild(rightText);
		rightText.addListener(this);
		
		this.addListener(this);
	}
	

	
	public void update(AnimationPlan plan, int msec)
	{
		// First, remove the old components:
		// hide them at the start of the animation
		plan.setVisible(leftText,0,false);
		plan.setVisible(middleNumber,0,false);
		plan.setVisible(rightText,0,false);
		// Then, clean them up at the end of the animation
		plan.addCleanup(leftText, textLayer);
		plan.addCleanup(middleNumber, textLayer);
		plan.addCleanup(rightText, textLayer);
		
		plan.changeTranslation(textLayer,0,msec,
				new Vector3f(0f, -getHeight()/2f - ItemModel.CONTENT_HEIGHT/2f, WindowView.Z_LAYERING));
		
		if (layout.getRange().size() > 0)
		{
			// make new components
			// be sure they are invisible when we add them to the SG.
			leftText = getFirstItemText();
			leftText.setVisible(false);
			textLayer.addChild(leftText);
			leftText.addListener(this);
			
			middleNumber = getNumberText();
			middleNumber.setVisible(false);
			textLayer.addChild(middleNumber);
			
			rightText = getLastItemText();
			rightText.setVisible(false);
			textLayer.addChild(rightText);
			rightText.addListener(this);
		
			// have the animation plan make then visible at the end:
			plan.setVisible(leftText, 0, true);
			plan.setVisible(rightText, 0, true);
			plan.setVisible(middleNumber, 0, true);
		}
		
	}
	
	@Override
	public float getHeight() {
		return LayoutComponent.getFoldComponentHeight(layout.getRange().size());
	}
	
	//	 ********************** TEXT DRAWING ***********************
	
	
	protected String makeAbreviation(ItemModel lim)
	{
		if (lim == null) return "";
		String s = lim.getFile().getName();
		if (s == null) return "";
		if (s.length() == 0) return "";
		
		// clip to the first 3 letters
		if (s.length() > 3) s = s.substring(0,3);
		
		// set capitolization
		s = s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
		
		return s;
	}
	
	public Component3D getFirstItemText() {
		List<ItemModel> items = layout.getIncludedItemModels();
		
		if (items.size() == 0) return new Component3D();
		
		ItemModel lim = items.get(0); // grab the first item.
		String abrev = makeAbreviation(lim); // make an abreviation for it
		
		TextComponent text = new TextComponent(abrev, TEXT_SIZE);
		text.setTranslation(
				TEXT_LEFT_OFFSET,
				0f,
				0.0f);
		
		
		return text;

	}
	
	public Component3D getLastItemText() {
		List<ItemModel> items = layout.getIncludedItemModels();
		
		if (items.size() == 0) return new Component3D();
		
		ItemModel lim = items.get(items.size() - 1); // grab the first item.
		String abrev = makeAbreviation(lim); // make an abreviation for it
		
		TextComponent text = new TextComponent(abrev, TEXT_SIZE);
		text.setTranslation(
				WINDOW_WIDTH - TEXT_RIGHT_OFFSET,
				0f,
				0.0f);
		return text;
	}
	
	protected Component3D getNumberText() {
		int size = layout.getIncludedItemModels().size();
		String str = Integer.toString(size);
		
		TextComponent text = new TextComponent(str, TEXT_SIZE);
		text.setTranslation(
				WINDOW_WIDTH/2 - (text.getWidth() * SceneModel.P2M / 2) - ItemModel.SPACING,
				0f,
				0.0f);
	    return text;
	}
	
	
	//	 ********************** CRINKLE DRAWING ***********************
	
	protected Component3D makeTopCrinkleShape()
	{
		// generate the geometry if needed
		if (TopFrontGeometry == null || TopBackGeometry == null)
		{
			// make the top fold down.
			Interpolator1D leftX = new SmoothInterpolator1D(crinkle_x1,crinkle_x2);
			Interpolator1D rightX = new SmoothInterpolator1D(crinkle_x4,crinkle_x3);
			Interpolator1D horizontal = new LinearInterpolator1D(crinkle_y1,crinkle_y2);
			Interpolator1D depth = new SmoothInterpolator1D(crinkle_z1, crinkle_z2);
			
			// these two interpolators descibe all the points on the right
			// and left curves of the fold.
			CompositeInterpolator3D leftEdge = new CompositeInterpolator3D(leftX, horizontal, depth);
			CompositeInterpolator3D rightEdge = new CompositeInterpolator3D(rightX, horizontal, depth);
			
			TopFrontGeometry = makeCrinkleGeometry(leftEdge, rightEdge, true);
			TopBackGeometry = makeCrinkleGeometry(leftEdge, rightEdge, false);
		}
		
		return makeCrinkle(TopFrontGeometry, TopBackGeometry);
	}
	
	protected Component3D makeBottomCrinkleShape()
	{
		// generate the geometry if needed
		if (BottomFrontGeometry == null || BottomBackGeometry == null)
		{
			// make the top fold down.
			Interpolator1D leftX = new SmoothInterpolator1D(crinkle_x2,crinkle_x1);
			Interpolator1D rightX = new SmoothInterpolator1D(crinkle_x3,crinkle_x4);
			Interpolator1D horizontal = new LinearInterpolator1D(crinkle_y1,crinkle_y2);
			Interpolator1D depth = new SmoothInterpolator1D(crinkle_z2, crinkle_z1);
			
			// these two interpolators descibe all the points on the right
			// and left curves of the fold.
			CompositeInterpolator3D leftEdge = new CompositeInterpolator3D(leftX, horizontal, depth);
			CompositeInterpolator3D rightEdge = new CompositeInterpolator3D(rightX, horizontal, depth);
			
			BottomFrontGeometry = makeCrinkleGeometry(leftEdge, rightEdge, true);
			BottomBackGeometry = makeCrinkleGeometry(leftEdge, rightEdge, false);
		}
		
		return makeCrinkle(BottomFrontGeometry, BottomBackGeometry);
	}
	
	/** 
	 * returns a top crinkle shape to be used in the layout
	 * @return
	 */
	private Geometry makeCrinkleGeometry(CompositeInterpolator3D leftEdge, CompositeInterpolator3D rightEdge, boolean forwardNormals)
	{
		
		QuadArray qa = new QuadArray(NUM_QUADS_PER_CRINKLE * 4, QuadArray.COORDINATES | QuadArray.NORMALS);
		
		for (int i=0; i<NUM_QUADS_PER_CRINKLE; i++)
		{
			// f1 and f2 represent the normaized distance [0,1] from
			// the top to the bottom of the curve
			float f1 = (float) i / (float) NUM_QUADS_PER_CRINKLE;
			float f2 = (float) (i+1) / (float) NUM_QUADS_PER_CRINKLE;
			int index = i * 4;
			
			// get the points that represent this poly
			Point3f p0 = leftEdge.get(f1);
			Point3f p1 = rightEdge.get(f1);
			Point3f p2 = rightEdge.get(f2);
			Point3f p3 = leftEdge.get(f2);
			
			// assign the points as coordinates
			qa.setCoordinate(index+0, p0);
			qa.setCoordinate(index+1, p1);
			qa.setCoordinate(index+2, p2);
			qa.setCoordinate(index+3, p3);
			
			// generate a normal for this face
			// do this by crossing vectors on the plane
			Vector3f v1 = new Vector3f(p1);
			v1.sub(p2);
			Vector3f v2 = new Vector3f(p1);
			v2.sub(p0);
			Vector3f normal = new Vector3f();
			if (forwardNormals) normal.cross(v2,v1);
			else normal.cross(v1,v2);
			normal.normalize();
			
			// assign the normal to every vertex in this poly
			qa.setNormal(index+0, normal);
			qa.setNormal(index+1, normal);
			qa.setNormal(index+2, normal);
			qa.setNormal(index+3, normal);
		}
		
		return qa;
	}
	
	private Component3D makeCrinkle(Geometry front, Geometry back)
	{
		Appearance appear = getAppearance(false);
		
		Shape3D frontShape = new Shape3D(front, appear);
		Component3D frontComp = new Component3D();
		frontComp.addChild(frontShape);
		
		Shape3D backShape = new Shape3D(back, appear);
		Component3D backComp = new Component3D();
		backComp.setTranslation(0f, 0f, -WINDOW_DEPTH);
		backComp.addChild(backShape);
		
		Component3D crinkle = new Component3D();
		crinkle.addChild(frontComp);
		crinkle.addChild(backComp);
		
		return crinkle;
	}
	
	/**
	 * the slab is a box that grows accomidate extra space in the crinkle.
	 * width: window_width - crinkle_pinch_x
	 * height: 1 meter!
	 * So, you need to scale this component in the Y dirrection when you get it.
	 * @return
	 */
	protected Component3D makeSlabShape()
	{
		float z = CRINKLE_PINCH_Z;
		float x = CRINKLE_PINCH_X;
		float width = WINDOW_WIDTH - CRINKLE_PINCH_X * 2;
		float depth = WINDOW_DEPTH;
		float height = 1f;
		Component3D boxComp = new Component3D();
		Box box = new Box(width/2f, height/2f, depth/2f, getAppearance(false));
		boxComp.addChild(box);
		boxComp.setTranslation(width/2f + x, -height/2f, -depth/2f - z);
		
		Component3D slab = new Component3D();
		slab.addChild(boxComp);
		return slab;
	}
	
	public void processEvent(LgEvent e) {
		if (e.getClass() != MouseButtonEvent3D.class) return;
		MouseButtonEvent3D m = (MouseButtonEvent3D) e;
		if (m.isClicked())
		{

			if (m.getSource() == rightText)
			{
				controller.clickOnEnd();
			}
			else if (m.getSource() == leftText)
			{
				controller.clickOnStart();
			}
			else if (m.getSource() == this)
			{
				controller.clickOnFold();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Class<LgEvent>[] getTargetEventClasses() {
		Class[] types = new Class[] {MouseButtonEvent3D.class};
		return types;
	}
	
	
}


