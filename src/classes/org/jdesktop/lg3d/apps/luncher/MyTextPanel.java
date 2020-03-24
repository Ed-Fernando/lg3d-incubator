/*
 * MyTextPanel.java
 *
 * Created on August 24, 2004, 10:58 AM
 */
package org.jdesktop.lg3d.apps.luncher;

import org.jdesktop.lg3d.sg.*;
import javax.vecmath.*;
import java.awt.*;
import java.awt.image.*;
import org.jdesktop.lg3d.utils.shape.*;


/**
 *
 * @author  Henrik Baastrup
 *
 * This object is a copy of the TextPanel, with a more dark text color. It would
 * be nice if this was a property of the TextPanel together with the text font.
 */
public class MyTextPanel extends Shape3D {
    private static final Font textFont = new Font("Serif", Font.PLAIN, 22);
//    private static final Color textColor = new Color(0.5f, 0.5f, 0.5f, 0.5f);
    private static final Color textColor = new Color(0f, 0f, 0f);
    private static final int widthMargin = 1;
    private static final int heightMargin = 1;
    private static final FontMetrics fontMetrics;

    private float width;
    private float prevAdjustedWidth;
    private float height;
    private IndexedGeometryArray geometry;
    private float widthRatio;
    private float heightRatio;
    private boolean vertical;


    static {
	BufferedImage bi 
	    = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2d = (Graphics2D)bi.getGraphics();
	g2d.setFont(textFont);
	fontMetrics = g2d.getFontMetrics();
	g2d.dispose();
    }

    private BufferedImage createTextureImage(String text,
	int xShift, int yShift)
    {
	if (text == null) {
	    return null;
	}

	int textWidth = fontMetrics.stringWidth(text) + widthMargin * 2;
	int textHeight = fontMetrics.getHeight() + heightMargin * 2;
	int descent = fontMetrics.getDescent();

	int width = getRoundUptoPow2(textWidth);
	int height = getRoundUptoPow2(textHeight);

	widthRatio = (float)textWidth / (float)width;
	heightRatio = (float)textHeight / (float)height;

	int x = widthMargin;
	int y = height - heightMargin - descent;

	BufferedImage bi 
	    = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2d = (Graphics2D)bi.getGraphics();

	AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC);
	g2d.setComposite(ac);
	g2d.setRenderingHints(
	    new RenderingHints(
		RenderingHints.KEY_TEXT_ANTIALIASING,
		RenderingHints.VALUE_TEXT_ANTIALIAS_ON));

	g2d.setColor(textColor);
	////g2d.fillRect(0, 0, width, height);
	g2d.setFont(textFont);
	g2d.setColor(Color.WHITE);
	g2d.drawString(text, x + xShift, y + yShift);
	g2d.setColor(Color.BLACK);
	g2d.drawString(text, x - xShift, y - yShift);
	g2d.setColor(textColor);
	////g2d.setColor(Color.RED);
	g2d.drawString(text, x, y);
	g2d.dispose();

	return bi;
    }

    private int getRoundUptoPow2(int n) {
	if (n <= 1) {
	    return 1;
	}

	int pow = 2;
	for ( ; pow < n; pow *= 2);

	return pow;
    }

    private Texture createTexture(String text, int xShift, int yShift) {
	BufferedImage bi = createTextureImage(text, xShift, yShift);
	ImageComponent2D ic2d 
	    = new ImageComponent2D(ImageComponent.FORMAT_RGBA, bi);
	Texture2D t2d 
	    = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, 
//		ic2d.getWidth(), ic2d.getHeight());
                bi.getWidth(), bi.getHeight());
	t2d.setMinFilter(Texture.BASE_LEVEL_LINEAR);
	t2d.setMagFilter(Texture.BASE_LEVEL_LINEAR);
	t2d.setImage(0, ic2d);

	return t2d;
    }

    public void setWidth(float maxWidth) {
	float w = (width > maxWidth)?(maxWidth):(width);

	if (w == prevAdjustedWidth) {
	    return;
	}
	prevAdjustedWidth = w;

	float[] coords 
	    = (vertical)
	    ?(new float[] {
		0.0f,  0.0f,   0.0f,
		0.0f, -w,      0.0f,
		0.0f, -w,     -height,
		0.0f,  0.0f,  -height,
	    }):(new float[] {
		0.0f,  0.0f,   0.0f,
		w,     0.0f,   0.0f,
		w,     height, 0.0f,
		0.0f,  height, 0.0f,
	    });

	geometry.setCoordinates(0, coords);
    }

    public MyTextPanel(String text, float widthScale, float maxWidth, float height, int xShift, int yShift, boolean vertical)
    {
	this.height = height;
	this.vertical = vertical;

	Appearance app 
	    = new SimpleAppearance(
		0.6f, 1.0f, 0.6f, 0.6f,
		SimpleAppearance.ENABLE_TEXTURE
		| SimpleAppearance.DISABLE_CULLING
		);
        
	Texture texture = createTexture(text, xShift, yShift);
	this.width 
	    = height * (texture.getWidth() * widthRatio) 
	    / (texture.getHeight() * heightRatio);
	this.width *= widthScale;

	app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
	app.setTexture(texture);
	setAppearance(app);

	geometry
	    = new IndexedQuadArray(4, 
		GeometryArray.COORDINATES
		| GeometryArray.TEXTURE_COORDINATE_2
		| GeometryArray.NORMALS,
		1, new int[] {0},
		4);
	geometry.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
	setGeometry(geometry);

	setWidth(maxWidth);

	int[] indices = {
	    0, 1, 2, 3,
	};

	float[] texCoords = {
	    0.0f,       0.0f,
	    widthRatio, 0.0f,
	    widthRatio, heightRatio,
	    0.0f,       heightRatio,
	};

	float[] normals = {
	    0.0f, 0.0f, 1.0f,
	};

	int[] normalIndices = {
	    0, 0, 0, 0,
	};

	geometry.setCoordinateIndices(0, indices);
	geometry.setTextureCoordinates(0, 0, texCoords);
	geometry.setTextureCoordinateIndices(0, 0, indices);
	geometry.setNormals(0, normals);
	geometry.setNormalIndices(0, normalIndices);

	// settings for allowing picking
        //PickTool.setCapabilities(this, PickTool.INTERSECT_COORD);
    }
}


