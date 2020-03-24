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
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.IndexedQuadArray;
import org.jdesktop.lg3d.sg.LineArray;
import org.jdesktop.lg3d.sg.LineAttributes;
import org.jdesktop.lg3d.sg.PolygonAttributes;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;

import edu.cmu.sun.model.ItemModel;
import edu.cmu.sun.model.Model;
import edu.cmu.sun.model.ModelListener;

/**
 * Responsible for drawing a single glue (or spread) into the scene.
 * 
 * Mostly just geometric drawing and styling to define
 * the visual look of the glue.
 * 
 * @author Braden Kowitz, Jessica Smith
 */
public class GlueView extends Component3D implements ModelListener
{
	
	static final Color4f SELECTED_LEFT_FILL = new Color4f(1.0f,1.0f,0.8f,1f);
	static final Color4f SELECTED_RIGHT_FILL = new Color4f(1.0f,1.0f,0.8f,0.1f);
	static final Color4f SELECTED_FILL_APP = new Color4f(1.0f,1.0f,0.8f, 1.0f);
	
	static final Color4f DESELECTED_LEFT_FILL = new Color4f(0.8f, 0.8f, 0.8f,0.9f);
	static final Color4f DESELECTED_RIGHT_FILL = new Color4f(0.8f, 0.8f, 0.8f,0.1f);
	static final Color4f DESELECTED_FILL_APP = new Color4f(0.8f, 0.8f, 0.8f, 1.0f);
	
	
	static final Color4f PINC = ItemView.PINNED_OUTLINE_C4F;
	static final Color4f PINNED_OUTLINE_LEFT = new Color4f(PINC.x, PINC.y, PINC.z, 1f);
	static final Color4f PINNED_OUTLINE_RIGHT = new Color4f(PINC.x, PINC.y, PINC.z, .2f);
	static final Color4f SELECTED_OUTLINE_LEFT = new Color4f(1.0f,1.0f,0.7f, 1f);
	static final Color4f SELECTED_OUTLINE_RIGHT = new Color4f(1.0f,1.0f,0.7f, .2f);
	
	static final float LINE_WIDTH = 2f;
	static final float INSET_PADDING = AbstractFoldView.CRINKLE_PINCH_X;
	
	float leftY;
	float leftH;
	float rightY;
	float rightH;
	float width;
	
	ItemModel model;
	
	Shape3D fillShape;
	Shape3D outlineShape;
	
	Component3D glue;
	
	public GlueView(ItemModel model)
	{
		this.model = model;
		model.addModelListener(this);
	}

	public void setLeft(float leftY, float leftH)
	{
		this.leftY = leftY;
		this.leftH = leftH;
	}
	
	public void setRight(float rightY, float rightH)
	{
		this.rightY = rightY;
		this.rightH = rightH;
	}
	
	public void setWidth(float width)
	{
		this.width = width;
	}
	
	
	public void update()
	{
		if (glue != null) this.removeChild(glue);
		glue = new Component3D();
		createGlue();
		this.addChild(glue);
	}
	
	private void createGlue() {
		if (!model.isPinned() && !model.isSelected()) return;
		
		fillShape = new Shape3D();
		fillShape.setGeometry(getFillGeometry());
		fillShape.setAppearance(getFillAppearance());
		glue.addChild(fillShape);
		
		outlineShape = new Shape3D();
		outlineShape.setGeometry(getOutlineGeometry());
		outlineShape.setAppearance(getOutlineAppearance());
		glue.addChild(outlineShape);
	}


	private Appearance getFillAppearance() {
		Appearance app;
		if (! model.isSelected())
		{
			app = new SimpleAppearance(
					DESELECTED_FILL_APP.x, 
					DESELECTED_FILL_APP.y,
					DESELECTED_FILL_APP.z,
					DESELECTED_FILL_APP.w);
			
			//app = AbstractListView.getAppearance(false);
		}
		else
		{
			
			app = new SimpleAppearance(
					SELECTED_FILL_APP.x, 
					SELECTED_FILL_APP.y,
					SELECTED_FILL_APP.z,
					SELECTED_FILL_APP.w);
			
			
		}
		return app;
		
		/*
		Appearance app = new Appearance();
		PolygonAttributes poly = new PolygonAttributes(
				PolygonAttributes.POLYGON_FILL,
				PolygonAttributes.CULL_BACK,
				0f);
		app.setPolygonAttributes(poly);
		
		ColoringAttributes color = new ColoringAttributes(FILL_COLOR, ColoringAttributes.SHADE_GOURAUD);;
		app.setColoringAttributes(color);
		
		return app;
		*/
	}


	private Appearance getOutlineAppearance() {
		
		Appearance app = new Appearance();
		
		LineAttributes line = new LineAttributes(LINE_WIDTH, LineAttributes.PATTERN_SOLID, true);
		app.setLineAttributes(line);
		
		//ColoringAttributes color = new ColoringAttributes(
		//		OUTLINE,
		//		ColoringAttributes.SHADE_FLAT);
		//app.setColoringAttributes(color);
		
		PolygonAttributes poly = new PolygonAttributes(
				PolygonAttributes.POLYGON_LINE,
				PolygonAttributes.CULL_BACK,
				3f);
		app.setPolygonAttributes(poly);
		
		return app;
	}
	
	private Geometry getFillGeometry() {
		
		float z = WindowView.Z_LAYERING * 1;
		
		float[] coords = {
			    -width, -(leftY + leftH), z,
			    0, -(rightY + rightH), z,
			    0, -rightY, z,
			    -width, -leftY, z,
			};
		
		int[] indices = {
			    0, 1, 2, 3,
			};
		
		int flags = GeometryArray.COORDINATES | GeometryArray.COLOR_4;
		
		IndexedQuadArray geom = new IndexedQuadArray(4, 
				flags,
				0, new int[] {0},
				4);
		
		
		Color4f[] colors = new Color4f[2];
		
		if (!model.isSelected())
		{
			colors[0] = DESELECTED_LEFT_FILL;
			colors[1] = DESELECTED_RIGHT_FILL;
		}
		else
		{
			colors[0] = SELECTED_LEFT_FILL;
			colors[1] = SELECTED_RIGHT_FILL;
		}
		
		int[] colorIndices = {
				0,1,1,0
		};
		
	    geom.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
		geom.setCoordinates(0, coords);
		geom.setCoordinateIndices(0, indices);
		geom.setColors(0, colors);
		geom.setColorIndices(0, colorIndices);
		
		return geom;
	
	}

	private Geometry getOutlineGeometry() {
		
		int flags = LineArray.COORDINATES | LineArray.COLOR_4;
		LineArray la = new LineArray(4, flags);
		
		float z = WindowView.Z_LAYERING * 2;
		Point3f p0 = new Point3f(-width, -(leftY + leftH), z);
		Point3f p1 = new Point3f(0, -(rightY + rightH), z);
		Point3f p2 = new Point3f(0, -rightY, z);
		Point3f p3 = new Point3f(-width, -leftY, z);
		

		la.setCoordinate(0, p0);
		la.setCoordinate(1, p1);
		la.setCoordinate(2, p2);
		la.setCoordinate(3, p3);
		
		Color4f left;
		Color4f right;
		
		if (model.isPinned())
		{
			left = PINNED_OUTLINE_LEFT;
			right = PINNED_OUTLINE_RIGHT;
		}
		else
		{
			left = SELECTED_OUTLINE_LEFT;
			right = SELECTED_OUTLINE_RIGHT;
		}
		
		
		la.setColor(0, left);
		la.setColor(1, right);
		la.setColor(2, right);
		la.setColor(3, left);
		
		return la;
		
		/*
		float z = WindowView.Z_LAYERING * 2;
		
		float[] coords = {
			    -width, -(leftY + leftH), z,
			    0, -(rightY + rightH), z,
			    0, -rightY, z,
			    -width, -leftY, z,
			};
		
		int[] indices = {
			    0, 1, 2, 3,
			};
		
		int flags = GeometryArray.COORDINATES | GeometryArray.COLOR_4;
		
		IndexedQuadArray geom = new IndexedQuadArray(4, 
				flags,
				0, new int[] {0},
				4);
		
		
		Color4f[] colors = { OUTLINE };
		
		int[] colorIndices = {
				0,0,0,0
		};
		
	    geom.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
		geom.setCoordinates(0, coords);
		geom.setCoordinateIndices(0, indices);
		geom.setColors(0, colors);
		geom.setColorIndices(0, colorIndices);
		
		return geom;
	*/
	}
	
	public void modelChanged(Model m) {
		if (m == model) update();
	}
	
	

	
	
}
