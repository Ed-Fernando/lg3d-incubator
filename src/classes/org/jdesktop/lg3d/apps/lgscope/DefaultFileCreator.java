/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: DefaultFileCreator.java,v $
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
 * $Revision: 1.3 $
 * $Date: 2006-04-19 17:24:39 $
 * Author: E_INOUE
 */
 
package org.jdesktop.lg3d.apps.lgscope;


import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgscope.util.ImageTexture;
import org.jdesktop.lg3d.apps.lgscope.util.LgUtil;
import org.jdesktop.lg3d.sg.*;
import org.jdesktop.lg3d.utils.action.*;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventaction.Component3DMover; 

import org.jdesktop.lg3d.utils.eventadapter.*;
import org.jdesktop.lg3d.utils.shape.*;
import org.jdesktop.lg3d.wg.*;


/**
 * Standard FileCreator. 
 * Refer to the icon.properties for the image of the icon. 
 */
public class DefaultFileCreator implements FileCreator {
        
    // The size of the image is defined.
    public int imageWidth;
    public int imageHeight;

    public Color bgColor;
    public Color filenameColor;
    public Color fileinfoColor;
    public Color filesizeColor;
    public Color selectedColor;
    public Color dirnameColor;
        
    public Font defaultFont;

    private Stack componentStack = new Stack(); 
    
    
    /**
     * The image of the icon is preserved. 
     * 
     * 
     * key: Extension.(String) 
     * value: Image of icon.(BufferedImage) 
     */
    static Hashtable iconImages = new Hashtable();

    static BufferedImage defaultIcon;
    static BufferedImage folderIcon;

    
    // The image of the icon is read. 
    static {
        try {                
            ResourceBundle iconResource = ResourceBundle.getBundle("org.jdesktop.lg3d.apps.lgscope.icon");
            Hashtable loaded = new Hashtable();        
            for (Enumeration e = iconResource.getKeys(); e.hasMoreElements(); ) {
                String key = (String) e.nextElement();
                
                String file = iconResource.getString(key);
         
                key = key.toLowerCase();    
                // Capital letters and small letters are not distinguished. 
                if (!loaded.containsKey(file)) {      
                    System.out.println("File "+file);
                    System.out.println("ClassLoader "+DefaultFileCreator.class.getClassLoader());
                    BufferedImage img = ImageIO.read(getResource(file));                
                    iconImages.put(key, img);
                    loaded.put(file, img);
                }
                else {
                    iconImages.put(key, loaded.get(file));
                }
            }
            
            defaultIcon = (BufferedImage) iconImages.get("default");
            folderIcon = (BufferedImage) iconImages.get("folder");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * The component is constructed by using the value of default.
     * 
     */
    public DefaultFileCreator() {
        
        // You should make here the involution of two. The aspect ratio shifts when it is not an involution of two.
        imageWidth = 256;
        imageHeight = 32;

        bgColor = Color.WHITE;
        filenameColor = Color.BLACK;
        fileinfoColor = Color.BLACK;
        filesizeColor = Color.BLACK;
        selectedColor = new Color(193, 201, 255);
        dirnameColor = Color.BLUE;
        
        defaultFont = new Font("San-Serif", Font.PLAIN, 20);        
    }
    
   
   /**
     * The component is constructed specifying the parameter. 
     * 
     * 
     * @param imageWidth Width of image.    
     * @param imageHeight Height of image.  
     * @param bgColor Background color.     
     * @param filenameColor File name drawing color.    
     * @param fileinfoColor File information drawing color. 
     * @param filesizeColor Color of drawing size of file.  
     * @param selectedColor Background color when selecting it. 
     * @param dirnameColor Directory name drawing color. 
     * @param defaultFont Displayed font. 
     */ 
    public DefaultFileCreator(
    int imageWidth, int imageHeight,
    Color bgColor, Color filenameColor, Color fileinfoColor, Color filesizeColor,
    Color selectedColor, Color dirnameColor, Font defaultFont) {
        
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.bgColor = bgColor;
        this.filenameColor = filenameColor;
        this.fileinfoColor = fileinfoColor;
        this.filesizeColor = filesizeColor;
        this.selectedColor = selectedColor;
        this.dirnameColor = dirnameColor;
        this.defaultFont = defaultFont;
    }
    
    
    private static InputStream getResource(String filename) {        
        return DefaultFileCreator.class.getResourceAsStream("/" + filename);
    }
    
    public Component3D create(File file) {
        
        FileComponent c;
        
        if (!componentStack.isEmpty()) {
            c = (FileComponent) componentStack.pop();
        }
        else {
            c = new FileComponent(this);
        }
        
        c.setFile(file);
        
        return c;        
    }
    
    
    public void changeSelected(Component3D component, boolean selected) {
                    
        FileComponent c = (FileComponent) component;
        c.changeSelected(selected);
    }
        
    
    void unuse(Component3D c) {
        componentStack.push(c);
    }    
}


/**
 * The component of the file is defined.  
 */
class FileComponent extends Component3D {
    
    class Surface {
        
        int partId;
        ImageTexture texture;
        Appearance appearance; 

        Surface(BufferedImage image) {
            texture = new ImageTexture(image);
        }        
    };
    
    
    private Surface frontSurface;
    private Surface backSurface;
    private Surface topSurface;
    private Surface bottomSurface;
    private Surface rightSurface;
    private Surface leftSurface;

    
    
    private Box box;
    private final int imageWidth, imageHeight;
    
    private final DefaultFileCreator fileCreator;
        
    
    private File file;
    
    
    public FileComponent(DefaultFileCreator fileCreator) {
        
        this.fileCreator = fileCreator;
        
        this.imageWidth = fileCreator.imageWidth;
        this.imageHeight = fileCreator.imageHeight;
        
        
        // Box doesn't make from size change impossibility. 
        
        BufferedImage emptyImage = createImage(1, 1);
        
                
        // Surface and the image are generated. 
        // The polygon by which it does not draw in the character is made here. 
        frontSurface = new Surface(createImage(imageWidth, imageHeight));
        setupSurface(Box.FRONT, frontSurface);
        
        backSurface = new Surface(emptyImage);
        setupSurface(Box.BACK, backSurface);
        
        topSurface = new Surface(emptyImage);
        setupSurface(Box.TOP, topSurface);        
                
        leftSurface = new Surface(emptyImage);
        setupSurface(Box.LEFT, leftSurface);
        
         
        // Setting of event
                
        // This is always necessary. It combines with each Action without using it in the unit it is possible to come and it uses it.
        setAnimation(new NaturalMotionAnimation(500));
                                   
        // When the mouse enters, the size is changed.      
        addListener(new MouseEnteredEventAdapter(new ScaleActionBoolean(this, 1.2f, 500)));  
                       
        // Setting which can be moved.
        addListener(new Component3DMover(this));
    }
    
    
    /**
     * Appearance, Texture, and ImageComponent are generated.
     * 
     */
    private void setupSurface(int partId, Surface surface) {
        
        surface.partId = partId;
        
        // Appearance is generated.
        surface.appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                            SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
        
        // Revokable is made the texture. 
        surface.appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
                
        surface.appearance.setTexture(surface.texture); 
    }
    
    
    
    private BufferedImage createImage(int width, int height) {
        
        return new BufferedImage(
            ImageTexture.getTextureImageSize(width), ImageTexture.getTextureImageSize(height), 
            BufferedImage.TYPE_INT_ARGB);        
    }
    
    
    /**
     * File is set to the component.
     * 
     * 
     * @param file Set file.
     */
    public void setFile(File file) {
        
        this.file = file;
        
        if (box != null) {
            removeChild(box);
        }
        
        
        // The size of Box is decided. 
        float width = LgUtil.pixel2meter(imageWidth);
        float height = LgUtil.pixel2meter(imageHeight);        
        float depth = (float) file.length() * (float) 0.01;     // The depth is reduced by 1%.  
        
        int intDepth = (int) (depth > 256 ? 256 : depth); // The depth installs the limitation. 
        
        // Check on the lowest size.
        if (intDepth < 64) {
            intDepth = 64;
        }

        // Decision of depth
        depth = LgUtil.pixel2meter(intDepth);        
                
        // A right and left, upper and lower size is decided here.        
        rightSurface = new Surface(createImage(intDepth, imageHeight));
        setupSurface(Box.RIGHT, rightSurface);
        
        bottomSurface = new Surface(createImage(imageWidth, intDepth));
        setupSurface(Box.BOTTOM, bottomSurface); 
        
        
        // Because the unit of LG3D is m  (meter), 0.01 shows the cube with all sides of 1cm. 
        float f = 0.3f;  // TODO Because it is too large whyBecause it is too large why  
        box = new Box(   
            width * f, height * f, depth * f,     
            Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS | Primitive.GEOMETRY_NOT_SHARED, 
            null);

        // Because the size actually displayed as the size specified with Box is different why, the size actually displayed here is set.                  
        setPreferredSize(new Vector3f(width * (f * 2.0f), height * (f * 2.0f), depth * (f * 2.0f)));
         
        addChild(box);
          
        // Image is renewed.
        // front 
        drawFrontImage(frontSurface, file, false);
        frontSurface.texture.updateImage();
        
        box.setAppearance(frontSurface.partId, frontSurface.appearance);
        // back 
        fillImage(backSurface.texture.image);
        backSurface.texture.updateImage();
        box.setAppearance(backSurface.partId, backSurface.appearance);
        // top 
        fillImage(topSurface.texture.image);
        topSurface.texture.updateImage();
        box.setAppearance(topSurface.partId, topSurface.appearance);
        // bottom 
        drawBottomImage(bottomSurface.texture.image, file);     
        bottomSurface.texture.updateImage();
        box.setAppearance(bottomSurface.partId, bottomSurface.appearance);
        // right 
        drawRightImage(rightSurface.texture.image, file);
        rightSurface.texture.updateImage();
        box.setAppearance(rightSurface.partId, rightSurface.appearance);
        // left 
        fillImage(leftSurface.texture.image);        
        leftSurface.texture.updateImage();
        box.setAppearance(leftSurface.partId, leftSurface.appearance);
    }
    
    
    private void fillImage(BufferedImage image) {        
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setBackground(fileCreator.bgColor);
        g.clearRect(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
    }
    
        
    public void changeSelected(boolean selected) {        
        drawFrontImage(frontSurface, file, selected);
        frontSurface.texture.updateImage();
    }
    
    
    private void drawFrontImage(Surface surface, File file, boolean selected) {
        
        Graphics2D g = (Graphics2D) surface.texture.image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        int w = surface.texture.image.getWidth();
        int h = surface.texture.image.getHeight();

        // w = surface.orgWidth;    // Now is good though it is here if it is anxious compared with the aspect. 
        // h = surface.orgHeight;
            
        // The background is painted. 
        // When selecting it. 
        if (selected) {             
            g.setBackground(fileCreator.selectedColor);            
        }
        else {            
            g.setBackground(fileCreator.bgColor);
        }
        g.clearRect(0, 0, w, h);
        
                
        // The icon is acquired. 
        BufferedImage icon;
        if (file.isDirectory()) {
            g.setColor(fileCreator.dirnameColor);
            icon = DefaultFileCreator.folderIcon;
        }
        else {
            g.setColor(fileCreator.filenameColor);
            icon = DefaultFileCreator.defaultIcon;
            
            // The extension is acquired. 
            String name = file.getName();  
            int i = name.lastIndexOf('.');            
            if (i != -1 && i != name.length()) {  // The case where it does not end by "."is excluded. 
                
                String ext = name.substring(i + 1).toLowerCase();
                if (DefaultFileCreator.iconImages.containsKey(ext)) {
                    icon = (BufferedImage) DefaultFileCreator.iconImages.get(ext);
                }                
            }            
        }

        
        // Drawing of icon
        
        // Left margin of icon. 
        int wMargin = 2;
        // Top and bottom of icon margin. 
        int hMargin = 2;
        
        // The size of the icon is defined. 
        int iconW = icon.getWidth();
        int iconH = icon.getHeight();
        
        
        // When height is high, the icon.
        if (h < (iconH + hMargin * 2)) {
            iconH = h - (hMargin * 2);
            iconW = iconH;
        }
        // Drawing of icon       
        g.drawImage(icon, wMargin, ((h - iconH) / 2), iconW, iconH, null);  // The length position is centered. 


        /*
         * Graphics is generated and FontMetrics cannot be taken. 
         * 
         */
                       
        Font font = fileCreator.defaultFont;
        g.setFont(font);
        FontMetrics fontMetrics = g.getFontMetrics();

        // The position where the character string is begun drawing is calculated. 
        int x = wMargin + iconW + wMargin;

        // The fontsize is reduced when beginning to see from the image by a present fontsize.          
        while (fontMetrics.stringWidth(file.getName()) > (w - x)) {
            font = new Font(font.getFamily(), font.getStyle(), font.getSize() - 1);
            g.setFont(font);
            fontMetrics = g.getFontMetrics();
        }
        
        // It draws in the file name. 
        int y = (h - fontMetrics.getHeight()) / 2;     // Centering 
        g.drawString(file.getName(), x, y + fontMetrics.getAscent());
                            
        g.dispose();
    }
    
    
    /**
     * Information on the file is displayed. 
     * 
     */
    private void drawBottomImage(BufferedImage image, File file) {
        
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.setBackground(fileCreator.bgColor);        
        g.clearRect(0, 0, image.getWidth(), image.getHeight());
                        
        g.setColor(fileCreator.fileinfoColor);
        
        Font font = new Font("San-Serif", Font.PLAIN, 15);       
        g.setFont(font);
        FontMetrics fontMetrics = g.getFontMetrics();
        
        int x = 5;
        int y = fontMetrics.getAscent();        
        String s;
        
        s = file.getParentFile().getPath();        
        g.drawString(s , x, y);
        y += fontMetrics.getAscent();
        
        if (!file.isDirectory()) {
            s = file.length() + " byte";
            g.drawString(s , x, y);
            y += fontMetrics.getAscent();
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        s = sdf.format(new Date(file.lastModified()));
        
        g.drawString(s , x, y);
        y += fontMetrics.getAscent();
        
        s = file.canWrite() ? "Writable" : "Readonly";
        g.drawString(s , x, y);
        y += fontMetrics.getAscent();
        
        g.dispose();
    }
    
    
    
    /**
     * The size of the file is displayed. 
     * 
     */
    private void drawRightImage(BufferedImage image, File file) {
        
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setBackground(fileCreator.bgColor);        
        g.clearRect(0, 0, image.getWidth(), image.getHeight());
        
        if (file.isDirectory()) {
            g.dispose();
            return;
        }
                                
        g.setColor(fileCreator.filesizeColor);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                         
        g.setFont(fileCreator.defaultFont);
        FontMetrics fontMetrics = g.getFontMetrics();
        String s = (((file.length() / 1000) == 0) ? 1 : (file.length() / 1000)) + "KB"; 
        g.drawString(s , 5, fontMetrics.getAscent());
        
        g.dispose();
    }

    
    public void setVisible(boolean b) {
        
        if (!b) {
            fileCreator.unuse(this);
        }
    }    
}
