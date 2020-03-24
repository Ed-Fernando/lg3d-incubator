/*
 * 3D File Manager - Project Looking Glass 
 * Copyright Sun Microsystems, 2005
 * 
 * Project Course in Human-Computer Interaction
 * Carnegie Mellon University
 * 
 * Aditya Chand, Braden Kowitz, Jake Pierson, Jessica Smith
 */

package edu.cmu.sun.components;

import org.jdesktop.lg3d.wg.Component3D;

import javax.vecmath.Point3f;
import org.jdesktop.lg3d.sg.BoundingBox;

import edu.cmu.sun.model.SceneModel;

/**
 * TextComponent is a Component3D that displays a simple string at a
 * specified size and weight (boldness). 
 * 
 * This is a wraper class for the extremely hacked Text2DView class.
 * Unlike most components used in this application, this Class registers
 * along the baseline of the font.
 * 
 * It is useful to have text as a Component because it's attributes
 * can be changed after it is created.
 * 
 * @author Braden Kowitz
 */
public class TextComponent extends Component3D {

	/**
	 * The default quality of the text to be rendered.
	 * @see Text2DView
	 */
	static final int DEFAULT_QUALITY = 3; 
	
	/**
	 * The default size of the text to be rendered. (in meters)
	 */
	public static final float DEFAULT_SIZE = 50f * SceneModel.P2M;
	
	/**
	 * The string to rendered.
	 */
	String string;
	
	/**
	 * Whether the string should be rendered in a bold font.
	 */
	boolean bold = false;
	
	/**
	 * The size of the font to be rendered.
	 */
	float size = DEFAULT_SIZE;
	
	
	/**
	 * The Shape3D object that is actuially responsible for the 
	 * rendering of the text.
	 */
	Text2DView textView;
	
	

	
	/**
	 * Create a text component at the default size, quality, and weight.
	 * @param string
	 */
	public TextComponent(String string)
	{
		this.string = string;
		update();
	}
	
	/**
	 * Render text at a specified size.
	 * @param string Text to be rendered
	 * @param size specified size (in meters?)
	 * @see Text2DView
	 */
	public TextComponent(String string, float size)
	{
		this.string = string;
		this.size = size;
		update();
	}

	
	public TextComponent(String string, boolean bold)
	{
		this.string = string;
		this.bold = bold;
		update();
	}

	/**
	 * Internal method for updating the state of the component.
	 */
	private void update()
	{
		this.removeAllChildren();
		textView = new Text2DView(string, size, DEFAULT_QUALITY, bold);
                
		this.addChild(textView);
	}

	/**
	 * The width of the resulting text component
	 * @return Width in meters
	 */
	public float getWidth()
	{
		return textView.getWidth();
	}

	/**
	 * The height of the resulting text component
	 * @return Height in meters
	 */
	public float getHeight()
	{
		return textView.getHeight();
	}
	
	/**
	 * @return True if component is drawn in a bold font.
	 */
	public boolean isBold() {
		return bold;
	}

	/**
	 * Allows user to change this Component to render text in a bold font.
	 * @param bold True if font is to be drawn in a bold font, false otherwise.
	 */
	public void setBold(boolean bold) {
		this.bold = bold;
		update();
	}
	
	
}
