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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.ImageComponent;
import org.jdesktop.lg3d.sg.ImageComponent2D;
import org.jdesktop.lg3d.sg.IndexedGeometryArray;
import org.jdesktop.lg3d.sg.IndexedQuadArray;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.Texture2D;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;


/**
 * Renders text in LG3D by mapping a bitmap onto a polygon.
 * This class was heavily based off of code from Sun Microsystems.
 * This class is used because LG3D does not yet have a text system.
 * 
 * The Shape3D that is created is aligned on the lwoer left point
 * at the decender line.  Other text functions usuially place
 * text at the baseline.  Be careful of this distinction.
 * (If this doesn't make sense, look for typography diagrams to 
 * show the parts of a font.)
 * 
 * @author Braden Kowitz, Jake Pierson, Jessica Smith, and Others
 */
public class Text2DView extends Shape3D {

	
	// NOTE:  This calss is pretty much a hack from other code that we
	// received.  It's not polished at all, and should probably
	// be re-written or replaced entirely with a vector-based class.
	
	/** Margin width * */
	private static final int WIDTH_MARGIN = 1;

	/** Margin height * */
	private static final int HEIGHT_MARGIN = 1;

	/** Max font quality * */
	public static final int MAX_FONT_QUALITY = 10;

	/** Min font quality * */
	public static final int MIN_FONT_QUALITY = 1;

	/** Text properties * */
	private static Font textFont;

	/** Text color * */
	private static Color textColor;

	/** Font metrics * */
	private static FontMetrics fontMetrics;

	/** Font quality * */
	private static int fontQuality;

	/** Width * */
	private float width;

	/** Height * */
	private float height;
	
	protected float w;
	
	protected float h;

	/** Indexed geometry * */
	private IndexedGeometryArray geometry;

	/** Width ratio * */
	private float widthRatio;

	/** Height ration * */
	private float heightRatio;

	private float prevAdjustedWidth;

	private boolean vertical;
	
	private boolean bold = false;

	
	
	public Text2DView(String text, float size, int quality, boolean bold) {

		this.bold = bold;
		
		// Set default values for color and transparency
		Color defaultColor = Color.BLACK;
		float defaultAlpha = Color.OPAQUE;

		// Create the 2D text object
		createText2DColorTransp(text, size, quality, defaultColor, defaultAlpha);
	}
	
	// ----------------------------//
	// ------- Constructors -------//
	// ----------------------------//
	/**
	 * Class constructor that create a 2D text object.
	 * 
	 * @param text
	 *            string to create
	 * @param size
	 *            of the text object
	 * @param quality
	 *            of the font used. Ranges from MAX_FONT_QUALITY = 10 to
	 *            MIN_FONT_QUALITY = 1;
	 */
	public Text2DView(String text, float size, int quality) {

		// Set default values for color and transparency
		Color defaultColor = Color.BLACK;
		float defaultAlpha = Color.OPAQUE;

		// Create the 2D text object
		createText2DColorTransp(text, size, quality, defaultColor, defaultAlpha);

	}
	
	/**
	 * Constructor to create non-black 2D text object
	 * 
	 */
	
	public Text2DView(String text, float size, int quality, Color theColor) {

		// Set default values for color and transparency
		float defaultAlpha = Color.OPAQUE;

		// Create the 2D text object
		createText2DColorTransp(text, size, quality, theColor, defaultAlpha);

	}
	

	/**
	 * Class constructor that create a 2D text object with user specified color
	 * and alpha.
	 * 
	 * @param text
	 *            string to create
	 * @param size
	 *            of the text object
	 * @param quality
	 *            of the font used. Ranges from MAX_FONT_QUALITY = 10 to
	 *            MIN_FONT_QUALITY = 1
	 * @param color
	 *            of the text
	 * @param alpha
	 *            /transparency of the text
	 */
	public Text2DView(String text, float size, int quality, Color color, float alpha) {

		// Create the 2D text object
		createText2DColorTransp(text, size, quality, color, alpha);
	}

	/**
	 * Sets the width of the text
	 * 
	 * @param maxWidth
	 *            of the text
	 */
	public void setWidth(float maxWidth) {
		float realWidth = (width > maxWidth) ? (maxWidth) : (width);

		if (realWidth == prevAdjustedWidth) {
			return;
		}
		prevAdjustedWidth = realWidth;

		float[] coords = (vertical) ? (new float[] { 0.0f, 0.0f, 0.0f, 0.0f,
				-realWidth, 0.0f, 0.0f, -realWidth, -height, 0.0f, 0.0f, -height, })
				: (new float[] { 0.0f, 0.0f, 0.0f, realWidth, 0.0f, 0.0f, realWidth, height,
						0.0f, 0.0f, height, 0.0f, });

		geometry.setCoordinates(0, coords);
	}

	/**
	 * Create a 2D text object with a transperancy. Ranges from MAX_FONT_QUALITY =
	 * 10 to MIN_FONT_QUALITY = 1.
	 * 
	 * @param text
	 *            string to create
	 * @param size
	 *            of the text object
	 * @param quality
	 *            of the font used.
	 * @param color
	 *            of the text
	 * @param alpha
	 *            /transparency of the text
	 */
	public void createText2DColorTransp(String text, float size, int quality,
			Color color, float alpha) {

		// Set the quality of the font
		setFontQuality(quality);

		// Set the font
		if (bold) textFont = new Font("SansSerif", Font.BOLD, fontQuality);
		else textFont = new Font("SansSerif", Font.PLAIN, fontQuality);

		// Set the font color
		float[] colorValues = color.getRGBColorComponents(null);

		textColor = new Color(ColorSpace.getInstance(ColorSpace.CS_sRGB),
				colorValues, alpha);

		// Get font metrics
		BufferedImage bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) bi.getGraphics();

		g2d.setFont(textFont);
		fontMetrics = g2d.getFontMetrics();
		
		// BFK:  This method sets transparecny, which we want to avoid
		//Appearance app = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
		//		SimpleAppearance.ENABLE_TEXTURE
		//				| SimpleAppearance.DISABLE_CULLING);
        
		Appearance app = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
				SimpleAppearance.ENABLE_TEXTURE
						| SimpleAppearance.DISABLE_CULLING);
		Texture texture = createTexture(text);

		// Set the size of the text object
		// Rescale the size for finer granularity input
		size *= 0.5f;

		this.height = size;

		width = height * (texture.getWidth() * widthRatio)
				/ (texture.getHeight() * heightRatio);

		app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);

		app.setTexture(texture);
		setAppearance(app);

		if (geometry != null) {
			// RemoveGeometry(geometry);
			// Geometry = null;
		}

		geometry = new IndexedQuadArray(4, GeometryArray.COORDINATES
				| GeometryArray.TEXTURE_COORDINATE_2 | GeometryArray.NORMALS,
				1, new int[] { 0 }, 4);

		geometry.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
		geometry.setCapability(GeometryArray.ALLOW_COORDINATE_READ);
		geometry.setCapability(GeometryArray.ALLOW_COUNT_READ);
		geometry.setCapability(GeometryArray.ALLOW_COUNT_WRITE);
		geometry.setCapability(GeometryArray.ALLOW_NORMAL_WRITE);
		geometry.setCapability(GeometryArray.ALLOW_NORMAL_READ);
		geometry.setCapability(GeometryArray.ALLOW_TEXCOORD_WRITE);
		geometry.setCapability(GeometryArray.ALLOW_FORMAT_READ);

		geometry.setCapability(IndexedGeometryArray.ALLOW_COORDINATE_INDEX_WRITE);

		geometry.setCapability(IndexedGeometryArray.ALLOW_COORDINATE_INDEX_READ);

		geometry.setCapability(IndexedGeometryArray.ALLOW_TEXCOORD_INDEX_WRITE);

		geometry.setCapability(IndexedGeometryArray.ALLOW_TEXCOORD_INDEX_READ);

		geometry.setCapability(IndexedGeometryArray.ALLOW_NORMAL_INDEX_WRITE);

		geometry.setCapability(IndexedGeometryArray.ALLOW_NORMAL_INDEX_READ);
		geometry.setCapability(IndexedGeometryArray.ALLOW_TEXCOORD_WRITE);
		geometry.setCapability(IndexedGeometryArray.ALLOW_TEXCOORD_READ);

		setGeometry(geometry);

		//setWidth(0.05f);
		setWidth( texture.getWidth() );

		int[] indices = { 0, 1, 2, 3, };

		float[] textCoords = { 0.0f, 0.0f, widthRatio, 0.0f, widthRatio,
				heightRatio, 0.0f, heightRatio, };

		float[] normals = { 0.0f, 0.0f, 1.0f, };

		int[] normalIndices = { 0, 0, 0, 0, };

		geometry.setCoordinateIndices(0, indices);
		geometry.setTextureCoordinates(0, 0, textCoords);
		geometry.setTextureCoordinateIndices(0, 0, indices);
		geometry.setNormals(0, normals);
		geometry.setNormalIndices(0, normalIndices);
	}

	/**
	 * Set the font quality between 12pt and 122pt
	 * 
	 * @param quality
	 *            of the font of which to be set.
	 */
	private void setFontQuality(int quality) {

		int defaultQuality = 24;
		int qualityScalar = 6;

		if (quality < MIN_FONT_QUALITY || quality > MAX_FONT_QUALITY) {
			Text2DView.fontQuality = defaultQuality;
		} else {
			Text2DView.fontQuality = quality * qualityScalar;
		}

	}
	

	public BufferedImage createMultilineImage( ArrayList<String> textLines, int maxLineLength, int indentPixels ) {
		
		if( textLines == null ) return null;
		
		char[] sampleLine = new char[maxLineLength];
		
		int textWidth = fontMetrics.stringWidth( String.valueOf(sampleLine) ) + WIDTH_MARGIN * 2;
		int textHeight = fontMetrics.getHeight() + HEIGHT_MARGIN * 2;
		int roundedWidth = getRoundUptoPow2(textWidth + indentPixels);
		int roundedHeight = getRoundUptoPow2(textHeight);
		
		roundedHeight = roundedHeight * textLines.size();

		BufferedImage bi = new BufferedImage(roundedWidth, roundedHeight, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = (Graphics2D) bi.getGraphics();
		
		for( final String line: textLines ) {
			
			g2d.drawImage( createTextureImage(line), null, 0 + indentPixels, textLines.indexOf(line) * textHeight);
		}
			
		return bi;		
	}

	/**
	 * Create a buffered image from the text. This rasterized the text, making
	 * it suitable for display.
	 * 
	 * @param text
	 *            string to display.
	 * @return BufferedImage rasterized image to return.
	 */
	private BufferedImage createTextureImage(String text) {
		if (text == null) {
			return null;
		}

		int textWidth = fontMetrics.stringWidth(text) + WIDTH_MARGIN * 2;
		int textHeight = fontMetrics.getHeight() + HEIGHT_MARGIN * 2;
		int descent = fontMetrics.getDescent();

		int roundedWidth = getRoundUptoPow2(textWidth);
		int roundedHeight = getRoundUptoPow2(textHeight);
		
		w = (float)roundedWidth;
		h = (float)roundedHeight;

		widthRatio = (float) textWidth / (float) roundedWidth;
		heightRatio = (float) textHeight / (float) roundedHeight;

		int x = WIDTH_MARGIN;
		int y = roundedHeight - HEIGHT_MARGIN - descent;

		BufferedImage bi = new BufferedImage(roundedWidth, roundedHeight,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) bi.getGraphics();

		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC);
		g2d.setComposite(ac);
		g2d.setRenderingHints(new RenderingHints(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON));

		// Set font
		g2d.setFont(textFont);

		// Set color
		g2d.setColor(textColor);

		// Draw the text
		g2d.drawString(text, x, y);

	
		g2d.dispose();

		return bi;
	}

	/**
	 * Round up method.
	 * 
	 * @param n
	 *            is the number to round up.
	 * @return int number rounded up to the nearest power of 2.
	 */
	private int getRoundUptoPow2(int n) {
		if (n <= 1) {
			return 1;
		}

		int pow = 2;
		for (; pow < n; pow *= 2) {
			;
		}

		return pow;
	}

	/**
	 * Create a texture for the text.
	 * 
	 * @param text
	 *            string to texture.
	 * @return Texture of the text.
	 */
	private Texture createTexture(String text) {
		BufferedImage bi = createTextureImage(text);
		ImageComponent2D ic2d = new ImageComponent2D(
				ImageComponent.FORMAT_RGBA, bi);
		Texture2D t2d = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, bi
				.getWidth(), bi.getHeight());
		t2d.setMinFilter(Texture.BASE_LEVEL_LINEAR);
		t2d.setMagFilter(Texture.BASE_LEVEL_LINEAR);
		t2d.setImage(0, ic2d);

		return t2d;
	}



	
	public float getHeight() {
		return height;
	}
	
	public float getWidth() {
		return width;
	}
}
