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

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.wg.Component3D;


/**
 * This is a simple 3D box.  It wraps a Box Shape3D object, and alighns the
 * box to the upper left.  It also fixes a bug in the underlying Box implementation
 * where the width, height, and depth of the box are twice their specified valye.
 * 
 * @author Braden Kowitz
 */
public class BoxComponent extends Component3D {

	Box boxShape;
	
	/**
	 * Createa a new Box that is upper-left-front aligned.
	 * @param width With (in meters) of the box.
	 * @param height Height (in meters) of the box
	 * @param depth Depth (in meters) of the box
	 * @param appearance The appearance that is applied to the underlying Box Shape3D
	 */
	public BoxComponent(float width, float height, float depth, Appearance appearance)
	{
		boxShape = new Box(
				width/2f,
				height/2f,
				depth/2f,
				appearance);
		
                
		Component3D registered = new Component3D();
		registered.addChild(boxShape);
		registered.setTranslation(
				width/2f,
				-height/2f,
				-depth);
		
		this.addChild(registered);
		
	}
        
}
