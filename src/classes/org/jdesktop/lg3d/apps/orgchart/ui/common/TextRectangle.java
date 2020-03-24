package org.jdesktop.lg3d.apps.orgchart.ui.common;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import org.jdesktop.lg3d.wg.Toolkit3D;
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

public class TextRectangle extends Shape3D {
    static final Font DEFAULT_TEXT_FONT = new Font("Serif", Font.PLAIN, 18);
    static final Color DEFAULT_TEXT_COLOR = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    static final Color DEFAULT_BG_COLOR = new Color(0.5f, 0.5f, 0.5f, 1.0f);
    private static final int DEFAULT_MARGIN_WIDTH = 2;
    private static final int DEFAULT_MARGIN_HEIGHT = 2;
    private static final FontMetrics DEFAULT_FONT_METRICS;

    private static final BufferedImage FONT_METRICS_IMAGE;
    private IndexedGeometryArray geometry;
    private Appearance appearance;
    private int widthMargin;
    private int heightMargin;
    private float width;
    private float height;
    private float prevAdjustedWidth;
    private float widthRatio;
    private float heightRatio;
    private boolean vertical;
    private Font textFont;
    private FontMetrics fontMetrics;

    static {
	FONT_METRICS_IMAGE 
	    = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        DEFAULT_FONT_METRICS = getFontMetrics(DEFAULT_TEXT_FONT);
    }
    
    private static final FontMetrics getFontMetrics(Font textFont) {
	Graphics2D g2d = (Graphics2D)FONT_METRICS_IMAGE.getGraphics();
	g2d.setFont(DEFAULT_TEXT_FONT);
	FontMetrics fontMetrics  = g2d.getFontMetrics();
	g2d.dispose();
        return fontMetrics;
    }
    
    public TextRectangle(String text)
    {
        this(text, false, DEFAULT_TEXT_FONT, DEFAULT_TEXT_COLOR, DEFAULT_BG_COLOR,
             DEFAULT_MARGIN_WIDTH, DEFAULT_MARGIN_HEIGHT,
             -1.0f, -1.0f);
    
    }
        
    public TextRectangle(String text, Color textColor, Color bgColor)
    {
        this(text, false, DEFAULT_TEXT_FONT, textColor, bgColor,
             DEFAULT_MARGIN_WIDTH, DEFAULT_MARGIN_HEIGHT,
             -1.0f, -1.0f);
    
    }
        
    public TextRectangle(boolean vertical, Font textFont, float width, float height)
    {
        this(null, vertical, textFont, DEFAULT_TEXT_COLOR, DEFAULT_BG_COLOR,
             DEFAULT_MARGIN_WIDTH, DEFAULT_MARGIN_HEIGHT,
             width, height);    
    }

    public TextRectangle(String text, Font textFont, Color textColor, Color bgColor, 
                         float width, float height)
    {
        this(text, false, textFont, textColor, bgColor,
             DEFAULT_MARGIN_WIDTH, DEFAULT_MARGIN_HEIGHT,
             width, height);    
    }

    public TextRectangle(String text, boolean vertical,
                         Font textFont, Color textColor, Color bgColor,
                         float width, float height) {
        this(text, vertical, textFont, textColor, bgColor,
             DEFAULT_MARGIN_WIDTH, DEFAULT_MARGIN_HEIGHT,
             width, height);    
    }
    
    public TextRectangle(String text, boolean vertical,
                         Font textFont, Color textColor, Color bgColor,
                         int widthMargin, int heightMargin,
                         float width, float height)
    {
        this.textFont = textFont;
        this.widthMargin = widthMargin;
        this.heightMargin = heightMargin;
        this.width = width;
	this.height = height;
        this.vertical = vertical;
        if (textFont == DEFAULT_TEXT_FONT) {
            fontMetrics = DEFAULT_FONT_METRICS;
        } else {
            fontMetrics = getFontMetrics(textFont);
        }
        
	appearance = new SimpleAppearance(
		0.6f, 1.0f, 0.6f, 1.0f,
		SimpleAppearance.ENABLE_TEXTURE
		| SimpleAppearance.DISABLE_CULLING
		);
        
	Texture texture = createTexture(text, textColor, bgColor);
        
	appearance.setCapability(Appearance.ALLOW_TEXTURE_READ);
        appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
	appearance.setTexture(texture);
	setAppearance(appearance);

	geometry
	    = new IndexedQuadArray(4, 
		GeometryArray.COORDINATES
		| GeometryArray.TEXTURE_COORDINATE_2
		| GeometryArray.NORMALS,
		1, new int[] {0},
		4);
	geometry.setCapability(GeometryArray.ALLOW_COORDINATE_WRITE);
        geometry.setCapability(GeometryArray.ALLOW_TEXCOORD_WRITE);
	setGeometry(geometry);

	adjustWidth();

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
    
    private BufferedImage createTextureImage(
            String text, Color textColor, Color bgColor) {
	if (text == null) {
	    text = ""; // in order to simplify the special case, text==null
	}
        Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
        
	int textWidth = fontMetrics.stringWidth(text) + widthMargin * 2;
	int textHeight = fontMetrics.getHeight() + heightMargin * 2;
	int descent = fontMetrics.getDescent();

        // adjust width and height to fit just right if necessary
        if (width < 0.0f) {
            width = toolkit3d.widthNativeToPhysical(
                    textWidth + widthMargin * 2);
        }
        if (height < 0.0f) {
            height = toolkit3d.widthNativeToPhysical(
                    textHeight + heightMargin * 2);
        }
	int biWidth = getRoundUptoPow2(toolkit3d.widthPhysicalToNative(width));
	int biHeight = getRoundUptoPow2(toolkit3d.heightPhysicalToNative(height));
        
        widthRatio = (float)textWidth / (float)biWidth;
        heightRatio = (float)textHeight / (float)biHeight;

	int x = widthMargin;
	int y = biHeight - heightMargin;

	BufferedImage bi 
	    = new BufferedImage(biWidth, biHeight, BufferedImage.TYPE_INT_ARGB);
	Graphics2D g2d = (Graphics2D)bi.getGraphics();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC);
	g2d.setComposite(ac);
	g2d.setRenderingHints(
	    new RenderingHints(
		RenderingHints.KEY_TEXT_ANTIALIASING,
		RenderingHints.VALUE_TEXT_ANTIALIAS_ON));

	g2d.setColor(bgColor);
	g2d.fillRect(0, 0, biWidth, biHeight);
	g2d.setFont(textFont);
	g2d.setColor(textColor);
	g2d.drawString(text, x, y);
	g2d.dispose();

	return bi;
    }
    
    private Texture createTexture(String text, Color textColor, Color bgColor) {
	BufferedImage bi = createTextureImage(text, textColor, bgColor);
	ImageComponent2D ic2d 
	    = new ImageComponent2D(ImageComponent.FORMAT_RGBA, bi);
	Texture2D t2d 
	    = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, 
                bi.getWidth(), bi.getHeight());
	t2d.setMinFilter(Texture.BASE_LEVEL_LINEAR);
	t2d.setMagFilter(Texture.BASE_LEVEL_LINEAR);
	t2d.setImage(0, ic2d);

	return t2d;
    }
    
    private int getRoundUptoPow2(int n) {
	if (n <= 1) {
	    return 1;
	}

	int pow = 2;
	for ( ; pow < n; pow *= 2);

	return pow;
    }
    
    private void adjustWidth() {
	if (width == prevAdjustedWidth) {
	    return;
	}
	prevAdjustedWidth = width;

	float[] coords 
	    = (vertical)
	    ?(new float[] {
		0.0f,  0.0f,   0.0f,
		0.0f, -width,      0.0f,
		0.0f, -width,     -height,
		0.0f,  0.0f,  -height,
	    }):(new float[] {
		0.0f,  0.0f,   0.0f,
		width,     0.0f,   0.0f,
		width,     height, 0.0f,
		0.0f,  height, 0.0f,
	    });
            
	geometry.setCoordinates(0, coords);
    }
    
    public void setText(String text, Color textColor, Color bgColor) {
        // since the size of image for the texture can change,
        // we recreate from a texture object
        Texture texture = createTexture(text, textColor, bgColor);
        appearance.setTexture(texture);
        
        float[] texCoords = {
	    0.0f, 0.0f,
	    1.0f, 0.0f,
	    1.0f, 1.0f,
	    0.0f, 1.0f,
	};
        geometry.setTextureCoordinates(0, 0, texCoords);
        adjustWidth();
    }
    
    public float getWidth() {
        return width;
    }
    
    public float getHeight() {
        return height;
    }
}


