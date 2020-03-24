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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jdesktop.lg3d.sg.Geometry;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.IndexedQuadArray;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;

import javax.vecmath.Point3f;
import org.jdesktop.lg3d.sg.BoundingBox;


/**
 * A Component3D object that replaces the ImagePanel Shape3D that LG3D provides.
 * This panel is alligned upper-left-front.  Much od the code in this class
 * is taken from ImagePanel in LG3D.  However, this class also CACHES 
 * acess to the images. This means that every time you create an image panel
 * the system does not have to hit the disk.  This dramatically speeds up the
 * placing of many duplicate images in the scenegraph.
 * 
 * At the moment, there is no way to clear the image cache, or remoive a loaded
 * image from the cache.  In the future, perhaps we could use a Factory object
 * to create ImageComponents.  The Factory could be responsible for caching image loads.
 *  
 * @author Braden kowitz
 */
public class ImageComponent extends Component3D {

	/**
	 * A map from the filename string and the SimpleAppearance that contains
	 * the texture.  This is where we keep the image cache, so the next time 
	 * an ImageComponent needs an image file as a texture, we take the
	 * appearance from this field.
	 */
	static Map<String, SimpleAppearance> appearances = new HashMap<String, SimpleAppearance>();
	
	/**
	 * Field that is passed directly to the ImagePanel compoennt
	 */
	boolean shiny = false;
	
	/**
	 * TODO: change this to a constant somewhere.
	 */
	float alpha = 1.0f;
	
	/**
	 * Create a new ImageComponent for an image file.  It will be upper-left aligned.
	 * The image will be stretched over the size of the panel, so it is important
	 * that the ImageComponent be given the correct width and height proportions
	 * of the image to avoid distortion.
	 * @param imagePath file path to the image
	 * @param width width of resulting panel (in meters)
	 * @param height height of resulting panel (in meters)
	 */
	public ImageComponent(String imagePath, float width, float height)
	{
		Shape3D panel = new Shape3D();
		panel.setGeometry(getGeometry(width, height));
		panel.setAppearance(getAppearance(imagePath));
                
		this.addChild(panel);
	}
	
	/**
	 * returns the geometry of a simple panel for the image component to be drawn on.
	 */
	private Geometry getGeometry(float width, float height) {


		float[] coords = {
		    0, -height, 0.0f,
		    width, -height, 0.0f,
		    width,  0, 0.0f,
		    0,  0, 0.0f,
		};

		int[] indices = {
		    0, 1, 2, 3,
		};

		float[] texCoords = {
		    0.0f, 0.0f,
		    1.0f, 0.0f,
		    1.0f, 1.0f,
		    0.0f, 1.0f,
		};

		float[] normals = {
		    0.0f, 0.0f, 1.0f,
		};

		int[] normalIndices = {
		    0, 0, 0, 0,
		};

		IndexedQuadArray geom = new IndexedQuadArray(4, 
			GeometryArray.COORDINATES
			| GeometryArray.TEXTURE_COORDINATE_2
			| ((shiny)?(GeometryArray.NORMALS):(0)),
			1, new int[] {0},
			4);
	        
	    geom.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
		geom.setCoordinates(0, coords);
		geom.setCoordinateIndices(0, indices);
		geom.setTextureCoordinates(0, 0, texCoords);
		geom.setTextureCoordinateIndices(0, 0, indices);
		if (shiny) {
		    geom.setNormals(0, normals);
		    geom.setNormalIndices(0, normalIndices);
		}
		
		return geom;
	}

	/**
	 * Returns the appearance for a panel, with the provided texture.
	 * This function also stores and retreives appearances from a cache.
	 * So, it does not read an image from disk twice.
	 * @param textureFilePath path to the texture file
	 * @return an appearance for the panel, if an error occours, returns an
	 * 	empty panel.
	 */
	private SimpleAppearance getAppearance(String textureFilePath)
	{
		if (appearances.containsKey(textureFilePath))
			return appearances.get(textureFilePath);
		
		// otherwise, we create the new appearance:

		SimpleAppearance app 
	    	= new SimpleAppearance(
	    			1.0f, 1.0f, 1.0f, alpha,
	    			SimpleAppearance.ENABLE_TEXTURE
	    			| ((shiny)?(0):(SimpleAppearance.NO_GLOSS))
	    			| SimpleAppearance.DISABLE_CULLING
	    			);
		try {
                        URL imageUrl = getClass().getClassLoader().getResource(textureFilePath);
			app.setTexture(imageUrl);
		} catch (FileNotFoundException e) {
			System.err.println("Texture File Not Found: " + textureFilePath);
		} catch (IOException e) {
			System.err.println("IO Error reading texture: " + textureFilePath);
		}
		
		appearances.put(textureFilePath, app);
		return app;
	}
	
}
