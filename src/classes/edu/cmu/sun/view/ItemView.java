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


import javax.vecmath.Color4f;
import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Geometry;
import org.jdesktop.lg3d.sg.LineArray;
import org.jdesktop.lg3d.sg.LineAttributes;
import org.jdesktop.lg3d.sg.PolygonAttributes;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;

import edu.cmu.sun.animation.AnimationPlan;
import edu.cmu.sun.animation.TransitionManager;
import edu.cmu.sun.components.BoxComponent;
import edu.cmu.sun.components.TextComponent;
import edu.cmu.sun.controller.ItemController;
import edu.cmu.sun.model.ItemModel;
import edu.cmu.sun.model.Model;
import edu.cmu.sun.model.ModelListener;
import edu.cmu.sun.model.SceneModel;

/**
 * Draws the line-item that represents a file and handles click-events.
 * 
 * An ItemView is based on an ItemModel and updates its appearance whenever the
 * underlying model changes.  It also functions as a listener to itself. When a mouse event
 * occours, the ItemView sends off the appropriate action to a controller.
 * 
 * @author Braden Kowitz, Jessica Smith
 */
public class ItemView extends Component3D implements LgEventListener, ModelListener
{

	ItemModel model;
	ItemController controller;
	
	TextComponent textComp;
	BoxComponent selectionComp;
	Component3D pinnedComp;
	BoxComponent backdropComp;
	
	// drawing constatnts
	static final float WIDTH = WindowView.WIDTH;
	static final float HEIGHT = ItemModel.HEIGHT;
	static final float HIGHLIGHT_DEPTH = 0f;
	static final float Z_LAYER_1 = WindowView.Z_LAYERING;
	static final float Z_LAYER_2 = WindowView.Z_LAYERING * 2;
	static final float SPACING = ItemModel.SPACING;
	static final float ICON_SIZE = ItemModel.SPACING;
	
	static final float INSET = 2.5f * SceneModel.P2M;
	static final float RIGHT_INSET = 20 * SceneModel.P2M; // was 15
	//private static final float HIGHLIGHT_WIDTH = WIDTH - INSET - RIGHT_INSET;
	private static final float HIGHLIGHT_WIDTH = WIDTH - INSET *2f;
	
	static final Appearance SELECTED_APP = new SimpleAppearance(1.0f, 1.0f, 0.8f, 0.75f);
	//static final Appearance ACTIVE_APP = new SimpleAppearance(1.0f, 0.9f, 0.7f);

	static final Color4f PINNED_OUTLINE_C4F = new Color4f(1.0f, 0.7f, 0f, 1f);;
	
	static final float LINE_WIDTH = 2f;
	private static final float LABEL_WIDTH = WIDTH - SPACING*3 - ICON_SIZE;
	
	static Appearance pinnedAppearance = null;
	static Geometry pinnedGeometry = null;
	
	public ItemView(ItemModel model)
	{
		this.model = model;
		model.addModelListener(this);
		
		this.addListener(this);
		controller = new ItemController(model);
		initialize();
		update();
	}


	private void initialize() {
		// icon
		IconView iconView = new IconView(model.getFile());
		iconView.setTranslation(
				ICON_SIZE,
				-ICON_SIZE,
				Z_LAYER_2);
		this.addChild(iconView);
		
		
		// text
		String name = model.getFile().getName();
		textComp = new TextComponent(name);
		while (textComp.getWidth() > LABEL_WIDTH)
		{
			name = truncate(name);
			textComp = new TextComponent(name);
		}
		textComp.setTranslation(
				ItemModel.SPACING * 2f + ItemModel.CONTENT_HEIGHT,
				ItemModel.SPACING - ItemModel.HEIGHT,
				Z_LAYER_2);
		this.addChild(textComp);
		
		
		// selection
		selectionComp = new BoxComponent(
				HIGHLIGHT_WIDTH, 
				HEIGHT - INSET*2, 
				HIGHLIGHT_DEPTH, 
				SELECTED_APP);
		selectionComp.setTranslation(INSET, - INSET, Z_LAYER_1);
		selectionComp.setVisible(false);
		this.addChild(selectionComp);
		
		// backdrop
		backdropComp = new BoxComponent(
				HIGHLIGHT_WIDTH, 
				HEIGHT - INSET*2, 
				HIGHLIGHT_DEPTH, 
				AbstractListView.getAppearance(true));
		backdropComp.setTranslation(INSET, - INSET, Z_LAYER_1);
		backdropComp.setVisible(false);
		this.addChild(backdropComp);
		
		// pinned
		pinnedComp = new Component3D();
		Shape3D pinnedShape = new Shape3D();
		pinnedShape.setAppearance(getPinnedAppearance());
		pinnedShape.setGeometry(getPinnedGeometry());
		pinnedComp.addChild(pinnedShape);
		this.addChild(pinnedComp);
		
		
	}



	
	private String truncate(String name) {
		String words[]  = name.split(" ");
		String newName = "";
		if (words.length > 2)
		{
			for (int i=0; i<words.length-2; i++)
			{
				if (!words[i].equals("..."))
					newName += words[i] + " ";
			}
		}
		else
		{
			if (name.length() > 9) 
				newName = name.substring(0, name.length() - 8);
		}
		newName += "...";
		return newName;
	}


	private synchronized Geometry getPinnedGeometry() {
		// return one if we've got it.
		if (pinnedGeometry != null) return pinnedGeometry;
		
		// geometry:
		
		float x1 = INSET;
		float x2 = WIDTH - RIGHT_INSET;
		float y1 = -INSET;
		float y2 = INSET - HEIGHT;
		float z = WindowView.Z_LAYERING * 2;
		
		Point3f p0 = new Point3f(x1, y2, z);
		Point3f p1 = new Point3f(x2, y2, z);
		Point3f p2 = new Point3f(x2, y1, z);
		Point3f p3 = new Point3f(x1, y1, z);
		
		int flags = LineArray.COORDINATES | LineArray.COLOR_4;
		LineArray la = new LineArray(6, flags);
		
		la.setCoordinate(0, p2);
		la.setCoordinate(1, p3);
		la.setCoordinate(2, p3);
		la.setCoordinate(3, p0);
		la.setCoordinate(4, p0);
		la.setCoordinate(5, p1);
		
		// colors for the vertices
		
		Color4f color = PINNED_OUTLINE_C4F;
		
		for (int i=0; i<6; i++) la.setColor(i, color);

		
		// store and return the result
		pinnedGeometry = la;
		return pinnedGeometry;
	}


	private synchronized Appearance getPinnedAppearance() {
		if (pinnedAppearance != null) return pinnedAppearance;

		Appearance app = new Appearance();
		
		LineAttributes line = new LineAttributes(LINE_WIDTH, LineAttributes.PATTERN_SOLID, true);
		app.setLineAttributes(line);
		
		PolygonAttributes poly = new PolygonAttributes(
				PolygonAttributes.POLYGON_LINE,
				PolygonAttributes.CULL_BACK,
				3f); // z-buffer offset
		app.setPolygonAttributes(poly);
		
		pinnedAppearance = app;
		return pinnedAppearance;
		
	}


	/**
	 * update the visual appearance of this view from the model.
	 *
	 */
	public void update()
	{
		AnimationPlan plan = TransitionManager.getTransitionPlan();
		
		// make the selection comp visible or hidden to match the model
		plan.setVisible(selectionComp, 0, model.isSelected());

		plan.setVisible(backdropComp, 0, ! model.isSelected());
		
		// make the pinned comp visible or hidden to match the model
		plan.setVisible(pinnedComp, 0, model.isPinned());
		
		/*
		if (backdrop != null) removeChild(backdrop);
		Appearance app;
		if (model.isPinned()) app = ACTIVE_APP;
		else if (model.isSelected()) app = SELECTED_APP;
		else app = AbstractListView.getAppearance(true);
		backdrop = new BoxComponent(WIDTH, HEIGHT, HIGHLIGHT_DEPTH, app);
		backdrop.setTranslation(0f, 0f, Z_LAYER_1);
		this.addChild(backdrop);
		*/
	}






	public void processEvent(LgEvent e) {
		if (e.getClass() == MouseButtonEvent3D.class)
		{
			MouseButtonEvent3D m = (MouseButtonEvent3D) e;
			if (m.isClicked() && m.getSource() == this & m.getClickCount() == 1)
			{
				if (m.getButton() == MouseEvent3D.ButtonId.BUTTON1)
				{
					controller.leftClick();
				}
				else
				{
					controller.rightClick();
				}
			}
		}
	}




	@SuppressWarnings("unchecked")
	public Class<LgEvent>[] getTargetEventClasses() {
		Class[] types = new Class[] {MouseButtonEvent3D.class};
		return types;
	}




	public void modelChanged(Model m) {
		if (m != model) return;
		update();
	}


	public ItemModel getModel() {
		return model;
	}




	
}

