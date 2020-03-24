/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: LabelComponent.java,v $
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
 * $Date: 2007-03-09 09:37:37 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.component;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.Stack;
import java.util.Vector;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgamazon.TextureRecycler;
import org.jdesktop.lg3d.apps.lgamazon.util.ImageTexture;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Toolkit3D;



public class LabelComponent extends Component3D {

    private static Stack<LabelComponent> components = new Stack<LabelComponent>();
    
    
    public final SimpleAppearance appearance;
    private ImagePanel imagePanel;
    private ImageTexture imageTexture;
    
    private Toolkit3D toolkit = Toolkit3D.getToolkit3D();
    
    private String text;
    
    protected Rectangle client = new Rectangle();
    protected Rectangle viewArea;
    
    private Font font;
    public Color foreground = Color.BLACK;
    public Color background = Color.WHITE;
        
    
    private int x, y;   
    
    private Graphics2D graphics;
    
    
    public int leftMargin, rightMargin, topMargin, bottomMargin;
    
    
    private int drawWidth, drawHeight;
    
    public Point viewPos = new Point(0, 0);
    
    public LabelRenderer labelRender;
     
    public boolean ignoreNewline = true;
    
    
    /**
     * 
     */
    public LabelComponent(SimpleAppearance appearance) {
        
        this.appearance = appearance;
        appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        
        components.push(this);
    }
    
    
    public void setSize(float width, float height) {        
        createImage(width, height); 
    }
    
    
    private void createImage(float width, float height) {
        
        if (imagePanel != null) {
            removeChild(imagePanel);
        }
                
        int imageW = ImageTexture.getTextureImageSize(toolkit.widthPhysicalToNative(width));
        int imageH = ImageTexture.getTextureImageSize(toolkit.heightPhysicalToNative(height));
                
        float realW = toolkit.widthNativeToPhysical(imageW);
        float realH = toolkit.heightNativeToPhysical(imageH);
        

        if (imagePanel != null) {
            imagePanel.setSize(realW, realH);
        }
        else {
            imagePanel = new ImagePanel(realW, realH);
        }
        
        imagePanel.setAppearance(appearance);        
        addChild(imagePanel);
        
        if (imageTexture == null || 
                !(imageTexture.image.getWidth() == imageW && imageTexture.image.getHeight() == imageH)) {
            
            if (imageTexture != null) {
                TextureRecycler.recycle(imageTexture);
            }
            
            imageTexture = TextureRecycler.createImage(imageW, imageH);
            appearance.setTexture(imageTexture);
        }
        
        
        float scale = width / realW;    
        setScale(scale);
                
        client.x = 0;
        client.y = 0;
        client.width = imageW;
        client.height = imageH;
        viewArea = new Rectangle(); 
        
        float sh = realH * scale;
        if (sh > height) {  
            
            int diff = toolkit.heightPhysicalToNative(sh - height);
            client.height = imageH - diff;
            client.y = diff / 2;
        }   
        
        setPreferredSize(new Vector3f(
                toolkit.widthNativeToPhysical(client.width) * scale, 
                toolkit.heightNativeToPhysical(client.height) * scale, 
                0.0f));
        
        
        font = imageTexture.image.getGraphics().getFont();
        setFont(font.getName(), font.getStyle(), font.getSize());
//        foreground = Color.BLACK;
//        background = Color.WHITE;
    }
    
    
    public void setFont(String name, int style, int size) {
        font = getScaledFont(name, style, size);
    }
    
    
    protected Font getScaledFont(String name, int style, int size) {        
        return new Font(name, style, ((int) ((1.0f / getScale()) * size)) + 1);  
    }
    
    
    public void setText(String text) {
        this.text = text;
        viewPos.x = viewPos.y = 0; 
    }
    
    public String getText() {
        return text;
    }
    
    /**
     * 
     * 
     * @return
     */
    public Graphics2D getGraphics() {        
        return (Graphics2D) imageTexture.image.getGraphics();
    }
    
    
    /**
     * 
     * 
     *
     */
    private void drawInit() {
        
        graphics = (Graphics2D) imageTexture.image.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
        
        graphics.setBackground(new Color(background.getRed(), background.getGreen(), background.getBlue(), 0)); 
        graphics.clearRect(0, 0, imageTexture.image.getWidth(), imageTexture.image.getHeight());

        graphics.setClip(client.x, client.y, client.width, client.height);

        graphics.setColor(background);
        graphics.fillRect(client.x, client.y, client.width, client.height);
        
        float scale = 1.0f / getScale();
        viewArea.x = client.x + (int) (leftMargin * scale);
        viewArea.y = client.y + (int) (topMargin * scale);
        viewArea.width = client.width - (int) ((leftMargin + rightMargin) * scale);
        viewArea.height = client.height - (int) ((topMargin + bottomMargin) * scale);
        
        graphics.setFont(font);
        graphics.setColor(foreground);
        
        setX(viewArea.x);
        setY(viewArea.y + graphics.getFontMetrics().getAscent());
        
        drawWidth = 0;
        drawHeight = 0;
        
        if (labelRender != null) {
            labelRender.standby(this, graphics);
            graphics.setColor(foreground);  
            graphics.setFont(font);
        }
        
        graphics.setClip(viewArea.x, viewArea.y, viewArea.width, viewArea.height);
    }
    
    
    public final void draw() {
        
        drawInit();
        
        draw(graphics);
        
        drawFinished();
    }
    
    
    protected void draw(Graphics2D g) {
        
        char[] ch = text.toCharArray();
        drawChars(ch, 0, ch.length);
    }
    
    
//    protected void drawChars(char[] chars, int start, int length) {
//        
//        
//        for (int i = 0; i < length; i++) {
//            
//            FontMetrics fontMetrics = graphics.getFontMetrics(); 
//            
//            int w = fontMetrics.charWidth(chars[start + i]);
//            
//            if ((x - viewArea.x) + w > viewArea.width) {                
//                newLine(fontMetrics);
//            }
//            
//            if (chars[start + i] == '\n') {
//                if (!ignoreNewline) {
//                    newLine(fontMetrics);
//                }
//                continue;
//            }
//            
//            if (viewPos.y <= y) {
//                graphics.drawChars(chars, start + i, 1, x, y - viewPos.y);
//            }
//                        
//            setX(x + w);
//        }
//    }
    
    
    protected void drawChars(char[] chars, int start, int length) {
        
        StringBuffer sb = new StringBuffer();
        Vector<String> v = new Vector<String>();
        
        int px = x;
        for (int i = 0; i < length; i++) {
            
            FontMetrics fontMetrics = graphics.getFontMetrics(); 
            
            char c = chars[start + i];
            int w = fontMetrics.charWidth(c);
            if ((x - viewArea.x) + w > viewArea.width) {                
                v.add(sb.toString());
                sb.delete(0, sb.length());  
                x = viewArea.x;
            }
            
            
            if (c == '\n') {
                if (!ignoreNewline) {
                    v.add(sb.toString());
                    sb.delete(0, sb.length());
                    x = viewArea.x;
                }
            }
            else {
                sb.append(c);
                setX(x + w);
            }
        }
        
        if (sb.length() > 0) {
            v.add(sb.toString());
        }
        
        x = px;
        
        
        for (int i = 0; i < v.size(); i++) {
            
            FontMetrics fontMetrics = graphics.getFontMetrics();
            
            if (v.get(i).length() > 0) {
            
                AttributedString as = new AttributedString(v.get(i));             
                as.addAttribute(TextAttribute.FONT, graphics.getFont());
                
                setAttribute(as);
                
                if (viewPos.y <= y) {
                    graphics.drawString(as.getIterator(), x, y - viewPos.y);
                }
                                
                int w = fontMetrics.stringWidth(v.get(i));
                setX(x + w);    
            }
                     
            if (i + 1 != v.size()) {
                newLine(fontMetrics);
            }
        }
    }
        
    
    /**
     * 
     * 
     */
    protected void setAttribute(AttributedString string) {
        
    }
    
    
    protected void newLine(FontMetrics fontMetrics) {
        
        setX(viewArea.x);
        setY(y + fontMetrics.getHeight());
    }
    
    
    private void drawFinished() {

        FontMetrics fontMetrics = graphics.getFontMetrics();
        setY(y + fontMetrics.getDescent());
        
        graphics.setClip(client.x, client.y, client.width, client.height);
        
        if (labelRender != null) {
            labelRender.finish(this, graphics);
        }
        
        graphics.dispose();        
        imageTexture.updateImage();
        graphics = null;
    }
    
    
    protected void setY(int y) {

        this.y = y;
        
        if (y > drawHeight) {
            drawHeight = y - viewArea.y;            
        }
    }
    
    protected int getY() {
        return y;
    }
    
    protected int getX() {
        return x;
    }
    
    protected void setX(int x) {

        this.x = x;
        
        if (x > drawWidth) {
            drawWidth = x;
        }
    }
    
    
    public int getDrawWidth() {
        return drawWidth;
    }
    
    public int getDrawHeight() {
        return drawHeight;
    }
    
    
    /**
     * 
     *
     */
    public static void recycleAll() {
        
        while (!components.isEmpty()) {
            
            LabelComponent c = components.pop();
            
            if (c.imageTexture == null) {
                continue;
            }
            
            TextureRecycler.recycle(c.imageTexture);
            c.imageTexture = null;
        }        
    }
}
