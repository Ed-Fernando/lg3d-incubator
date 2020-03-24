/**
 * Project Looking Glass
 *
 * $RCSfile: TextPanel.java,v $
 *
 * Copyright (c) 2004, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.2 $
 * $Date: 2005-09-07 20:24:31 $
 * $State: Exp $
 */
package org.jdesktop.lg3d.apps.orgchart.ui.common;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
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


public class TextPanel extends Shape3D {
    private static final Font textFont = new Font("Serif", Font.PLAIN, 22);
    private static final int widthMargin = 1;
    private static final int heightMargin = 1;
    private static final FontMetrics fontMetrics;
    
    private IndexedGeometryArray geometry;
    private Appearance appearance;
    private float width;
    private float prevAdjustedWidth;
    private float height;
    private float widthScale;
    private float maxWidth;
    private int xShift;
    private int yShift;
    private boolean vertical;
    private float widthRatio;
    private float heightRatio;
    
    static {
        BufferedImage bi
                = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D)bi.getGraphics();
        g2d.setFont(textFont);
        fontMetrics = g2d.getFontMetrics();
        g2d.dispose();
    }
    
    public TextPanel(String text, float widthScale, float maxWidth,
            float height, int xShift, int yShift, boolean vertical) {
        this(text, null, widthScale, maxWidth, height, xShift, yShift, vertical);
    }
    
    public TextPanel(String text1, String text2, float widthScale, float maxWidth,
            float height, int xShift, int yShift, boolean vertical) {
        this.widthScale = widthScale;
        this.maxWidth = maxWidth;
        this.height = height;
        this.xShift = xShift;
        this.yShift = yShift;
        this.vertical = vertical;
        
        appearance = new SimpleAppearance(
                0.6f, 1.0f, 0.6f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE
                | SimpleAppearance.DISABLE_CULLING
                );
        
        Texture texture = createTexture(text1, text2);
        
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
    
    private BufferedImage createTextureImage(String text1, String text2) {
        if (text1 == null) {
            text1 = ""; // in order to simplify the special case, text==null
        }
        
        int textWidth = fontMetrics.stringWidth(text1) + widthMargin * 2;
        if (text2 != null) {
            int textWidth2 = fontMetrics.stringWidth(text2) + widthMargin * 2;
            if (textWidth2 > textWidth) {
                textWidth = textWidth2;
            }
        }
        
        int textRowHeight = fontMetrics.getHeight() + heightMargin * 2;
        int textGap = (int)(textRowHeight * 0.2f);
        int textHeight = textRowHeight;
        if (text2 != null) {
            textHeight = textHeight * 2 + textGap;
        }
        int descent = fontMetrics.getDescent();
        
        int biWidth = getRoundUptoPow2(textWidth);
        int biHeight = getRoundUptoPow2(textHeight);
        
        widthRatio = (float)textWidth / (float)biWidth;
        heightRatio = (float)textHeight / (float)biHeight;
        
        width = height * (biWidth * widthRatio)
        / (biHeight * heightRatio) * widthScale;
        
        int x = widthMargin;
        int y = biHeight - heightMargin - descent;
        
        BufferedImage bi
                = new BufferedImage(biWidth, biHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D)bi.getGraphics();
        
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC);
        g2d.setComposite(ac);
        g2d.setRenderingHints(
                new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        
        g2d.setFont(textFont);
        if (text2 == null) {
            drawText(g2d, x, y, text1);
        } else {
            drawText(g2d, x, y - textRowHeight, text1);
            drawText(g2d, x, y, text2);
        }
        g2d.dispose();
        
        return bi;
    }
    
    private void drawText(Graphics2D g2d, int x, int y, String text) {
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x + xShift, y + yShift);
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, x - xShift, y - yShift);
        g2d.drawString(text, x, y);
    }
    
    private Texture createTexture(String text1, String text2) {
        BufferedImage bi = createTextureImage(text1, text2);
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
    
    private int getRoundUptoPow2(int n) {
        if (n <= 1) {
            return 1;
        }
        
        int pow = 2;
        for ( ; pow < n; pow *= 2);
        
        return pow;
    }
    
    public void setWidth(float widthLimit) {
        float w = (width < widthLimit)?(width):(widthLimit);
        
        if (w == prevAdjustedWidth) {
            return;
        }
        prevAdjustedWidth = w;
        maxWidth = widthLimit;
        
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
    
    public void setText(String text1, String text2) {
        // since the size of image for the texture can change,
        // we recreate from a texture object
        Texture texture = createTexture(text1, text2);
        appearance.setTexture(texture);
        
        float[] texCoords = {
            0.0f,       0.0f,
                    widthRatio, 0.0f,
                    widthRatio, heightRatio,
                    0.0f,       heightRatio,
        };
        geometry.setTextureCoordinates(0, 0, texCoords);
        
        setWidth(maxWidth);
    }
}


