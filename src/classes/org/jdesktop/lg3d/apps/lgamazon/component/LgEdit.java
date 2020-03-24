/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: LgEdit.java,v $
 *
 * Copyright (c) 2006, INoX Software Development Group, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.1 $
 * $Date: 2007-03-09 09:37:38 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgamazon.util.ImageTexture;
import org.jdesktop.lg3d.apps.lgamazon.util.LgUtil;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.action.ActionBooleanInt;
import org.jdesktop.lg3d.utils.action.ActionChar;
import org.jdesktop.lg3d.utils.eventadapter.KeyPressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.KeyTypedEventAdapter;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;


/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class LgEdit extends Component3D {
    
    private ImageTexture texture;
    private final int imageWidth; 
    private final int imageHeight;
    
    
    private int drawWidth;
    private int drawHeight;
    
    /** 
     * 
     */
    private int viewX = 0;
    private int viewY = 0;
    /** 
     * 
     */    
    private int scrollY;
    private int scrollX;
    
    public Color background = Color.WHITE;
    public Color foreground = Color.BLACK;
    
    
    public int leftMargin = 5;
    public int rightMargin = 5;
    public int topMargin = 5;
    public int bottomMargin = 5;
    
    
    /** 
     * 
     */
    private StringBuffer textBuffer = new StringBuffer();

    private FontMetrics fontMetrics;
    
    
    public boolean editable = true;
    public boolean multiple = false;  
    
    
    public LgEdit(
    Appearance appearance, int width, int height, String text) {
        
        appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        
        float mWidth = LgUtil.pixel2meter(width);
        float mHeight = LgUtil.pixel2meter(height);
        
                
        // Generation of Shape3D. 
        Shape3D shape = new ImagePanel(mWidth, mHeight);
        shape.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
        shape.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);         
        shape.setAppearance(appearance);
        
        
        addChild(shape);
        addListener(new KeyTypedEventAdapter(new KeyListener()));    
        addListener(new KeyPressedEventAdapter(new CursorListener()));
        setPreferredSize(new Vector3f(mWidth, mHeight, 0f));        
        
        
        // The texture image is generated. 
        BufferedImage image = new BufferedImage(                            
            ImageTexture.getTextureImageSize(width),
            ImageTexture.getTextureImageSize(height),                
            BufferedImage.TYPE_INT_ARGB);
        
        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        
        texture = new ImageTexture(image);        
        appearance.setTexture(texture);
        
        setFont(new Font("Dialog", Font.PLAIN, 12));
        
        
        if (text != null) {
            textBuffer.append(text);                    
        }
    }
    
        
    public void setFont(Font font) {  
        
        Graphics g = texture.image.getGraphics();
        g.setFont(font);
        fontMetrics = g.getFontMetrics();
        g.dispose();
        
        scrollY = fontMetrics.getHeight(); 
        scrollX = scrollY;        
    }
    
    
    public Font getFont() {        
        return fontMetrics.getFont();
    }
    
    
    class KeyListener implements ActionChar {

		public void performAction(LgEventSource source, char value) {
			
            if (!editable) {
                return;
            }
            
            // System.out.println("Key:" + value + " : " + Character.isLetterOrDigit((int) value));
            
            if (value == KeyEvent.VK_BACK_SPACE) {
                return;
            }
            
            
            if (value == '\n') {
                
                if (multiple) {                
                    drawHeight += fontMetrics.getHeight();  
                    textBuffer.append((char) value);
                    if (scroll(KeyEvent.VK_DOWN)) {
                        draw();
                    }
                }
                else {
                }
            }
            else {            
                textBuffer.append((char) value);
                drawWidth += fontMetrics.charWidth(value);   
                scroll(KeyEvent.VK_RIGHT);
                draw();     
            }
		}    	
    }
    
    
    class CursorListener implements ActionBooleanInt {

        public void performAction(
        LgEventSource source, boolean state, int value) {                        
                                    
            if (!state) {
                return;
            }

            // System.out.println("Cur:" + value);
            
            switch (value) {
            
                case KeyEvent.VK_UP:  
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_LEFT:
                                        
                    if (scroll(value)) {
                        draw();
                    }
                    break;
                
                case KeyEvent.VK_BACK_SPACE:
                    
                    int len = textBuffer.length();
                    if (len > 0) {
                        textBuffer.delete(textBuffer.length() - 1, textBuffer.length());
                        draw();
                    }
                                        
                    break;
                    
                default:                
                    break;
            }    
        }        
    }
    
        
    
    private boolean scroll(int key) {
        
        if (multiple) {
            if (key == KeyEvent.VK_UP) {  
                
                if (viewY == 0) {
                    return false;
                }
                
                if (viewY - scrollY < 0) {
                    viewY = 0;
                }
                else {
                    viewY -= scrollY;
                }
                
                return true;
                
            }
            else if (key == KeyEvent.VK_DOWN) {
                                                    
                int left = drawHeight - viewY;
                int area = imageHeight - (topMargin + bottomMargin);
                if (left > area) {
                    
                    viewY += scrollY;
                    
                    if (viewY + area > drawHeight) {
                        viewY = drawHeight - area;
                    }
                    
                    return true;                    
                }
            }
        }
        else {
            
            if (key == KeyEvent.VK_LEFT) {
                
                if (viewX == 0) {
                    return false;
                }
                
                if (viewX - scrollX < 0) {
                    viewX = 0;
                }
                else {
                    viewX -= scrollX;
                }
                
                return true;
            }
            else if (key == KeyEvent.VK_RIGHT) {
                
                int left = drawWidth - viewX;
                int area = imageWidth - (leftMargin + rightMargin);
                if (left > area) {
                    
                    viewX += scrollX;
                    
                    if (viewX + area > drawWidth) {
                        viewX = drawWidth - area;
                    }
                    
                    return true;                    
                }                
            }
        }   
        
        return false;
    }
    
    
    public void draw() {
        
        // System.out.println("LgEdit.draw()");
        
        Graphics2D g = (Graphics2D) texture.image.getGraphics(); 
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        g.setFont(fontMetrics.getFont());
        
        g.setBackground(background);
        g.clearRect(0, 0, texture.image.getWidth(), texture.image.getHeight());
        
        
        g.setColor(foreground);
        
        String str = textBuffer.toString();         
        
        g.clipRect(leftMargin, topMargin, 
                imageWidth - (leftMargin + rightMargin), imageHeight - (topMargin + bottomMargin));
        
//        // debug
//        g.setColor(Color.RED);
//        g.fillRect(0, 0, texture.image.getWidth(), texture.image.getHeight());
//        g.setColor(foreground);

        if (multiple) {
            drawMultiple(g, str);
        }
        else {
            drawSingle(g, str);
        }
                
        g.dispose();
        
        texture.updateImage();        
    }
    
    
    private void drawSingle(Graphics2D g, String str) {
        
        int x = leftMargin - viewX; 
        int y = topMargin;
        
        g.drawString(str, x, y + fontMetrics.getAscent() - viewY);
        Rectangle2D rect = fontMetrics.getStringBounds(str, 0, str.length(), g);
        drawWidth = (int) rect.getWidth();
        
        drawHeight = fontMetrics.getHeight(); 
    }
    
    
    private void drawMultiple(Graphics2D g, String str) {
        
        int x = leftMargin;
        int y = topMargin;  
        drawHeight = 0;
        for (int i = 0; i < str.length(); ) {
            
            int lb = getLinebreak(g, fontMetrics, str, i, imageWidth - (leftMargin + rightMargin));
            
            if (y >= viewY && y <= viewY + imageHeight - bottomMargin) {                
                g.drawString(str.substring(i, lb), x, y + fontMetrics.getAscent() - viewY);
            }
                        
            i = lb;
            y += fontMetrics.getHeight();
            drawHeight += fontMetrics.getHeight();
        }
        
        drawWidth = imageWidth - (leftMargin + rightMargin);
    }
    
    
    /**
     * 
     * 
     * @param context
     * @param fontMetrics
     * @param str
     * @param beginIndex
     * @param width
     * 
     */
    private int getLinebreak(
    Graphics context, FontMetrics fontMetrics, 
    String str, int beginIndex, int width) {
        
        for (int i = beginIndex; i <= str.length(); i++) {
            
            if (i != str.length() && str.charAt(i) == '\n') {
                return i + 1;
            }
            
            Rectangle2D rect = fontMetrics.getStringBounds(str, beginIndex, i, context);
            
            if (rect.getWidth() > width) {
                return i - 1;
            }
        }
        
        return str.length();
    }
    
    
    public String getText() {
        return textBuffer.toString();
    }
    
    
    public void setText(String text) {
        
        textBuffer.delete(0, textBuffer.length());
        textBuffer.append(text);        
        viewX = viewY = 0;
    }
    

    public static void main(String[] args) {

        //      Appearance is generated. 
        Appearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                                                     SimpleAppearance.ENABLE_TEXTURE 
                                                     | SimpleAppearance.DISABLE_CULLING);        
        LgEdit edit = new LgEdit(appearance, 256, 64, "123456789");
        
        
        Frame3D frame = new Frame3D();
        frame.addChild(edit);
        
        frame.changeEnabled(true);
        frame.changeVisible(true);
    }
}
