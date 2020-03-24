/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: LgViewer.java,v $
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
 * $Date: 2006-04-17 09:56:21 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgscope.viewer;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgscope.util.ImageTexture;
import org.jdesktop.lg3d.apps.lgscope.util.LgUtil;
import org.jdesktop.lg3d.utils.action.ActionBooleanInt;
import org.jdesktop.lg3d.utils.action.ActionFloat3;
import org.jdesktop.lg3d.utils.eventadapter.KeyPressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.Primitive;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Thumbnail;
import org.jdesktop.lg3d.wg.event.LgEventSource;


/**
 * The file viewer in the LgScope is defined.  
 */
public abstract class LgViewer extends Frame3D {

    protected File file;
    protected Component3D viewer;
    protected Component3D baseComponent;
    private final float barHeight = 0.005f; // Height of title bar.  
    
    
    public LgViewer(File file) {
        
        this.file = file;
        
        // Component which becomes base
        baseComponent = new Component3D();
        addChild(baseComponent);
        
        // Component of title bar       
        Component3D bar = new Component3D();
        baseComponent.addChild(bar);
        
        // Making of Reloader.
        Component3D reloader = createReloader();
        bar.addChild(reloader);
        System.out.println(LgUtil.getSize(reloader).x);
        float x = LgUtil.getTranslation(reloader).x + LgUtil.getSize(reloader).x;
                
        // Generation of title
        Component3D title = createTitle();
        title.setTranslation(x + LgUtil.getSize(title).x / 2, 0.0f, 0.0f);
        bar.addChild(title);
        x += LgUtil.getSize(title).x;
        
        // Generation of Closer component
        Component3D closer = createCloser(); 
        closer.setTranslation(x + LgUtil.getSize(closer).x, 0.0f, 0.0f);
        bar.addChild(closer);
        
        reload();
        
        addListener(new KeyPressedEventAdapter(new KeyListener()));
        
        
        /*
        component = new Component3D();
        component.addChild(createViewer(file));
        base.addChild(component);
        
        component.addListener(new KeyPressedEventAdapter(new KeyListener()));
        
        base.addListener(new MouseClickedEventAdapter(
            MouseEvent3D.ButtonId.BUTTON1,
            new Boolean(true),
            new ActionFloat3() {
                public void performAction(LgEventSource source, float x, float y, float z) {            
                    changeEnabled(false);
                    changeVisible(false);                         
                }
            }));
        
        //System.out.println(LgUtil.getTranslation(component));
        
        
        SimpleAppearance app = new SimpleAppearance(1.0f, 0.6f, 0.6f, 1.0f, SimpleAppearance.DISABLE_CULLING);
        Sphere exitShape = new Sphere(0.0025f, 
                Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS | Primitive.GEOMETRY_NOT_SHARED, 36, app);
        Component3D exit = new Component3D();
        exit.addChild(exitShape);
        //base.addChild(exit);
        
        //component.setTranslation(0.0f, -0.01f, 0.0f);
        
        System.out.println(LgUtil.getSize(component));
        
        
        //base.addChild(createTitle(file));
        base.addChild(createReloader());
        */
        
        
        changeEnabled(true);
        changeVisible(true);
    }
    
    
    /**
     * The reload of the file is done. 
     */
    private void reload() {
        
        // The last component is removed. 
        if (viewer != null) {
            baseComponent.removeChild(viewer);
        }
        
        // Generation of Viewer.
        Component3D viewer = createViewer(file);
        Vector3f size = LgUtil.getSize(viewer);
        // So as not to come in succession, it lowers with the bar. 
        viewer.setTranslation(size.x / 2, -((size.y / 2) + (barHeight / 2) + 0.001f), -0.005f);
        baseComponent.addChild(viewer);
        this.viewer = viewer;
        
        // Thumbnail generation.
        Component3D thumbnail = createThumbnail(file);
        if (thumbnail != null) {
            Thumbnail t = getThumbnail();
            
            if (t == null) {
                t = new Thumbnail();
                setThumbnail(t);
            }
            
            t.removeAllChildren();
            t.addChild(thumbnail);            
        }
    }
    
    
    /**
     * The Component to shut the file is made. 
     */
    private Component3D createCloser() {
        
        // Diameter
        float r = 0.005f;
        
        SimpleAppearance app = new SimpleAppearance(1.0f, 0.0f, 0.0f, 0.7f, SimpleAppearance.DISABLE_CULLING);       
        Sphere ball = new Sphere(r / 2, 
                Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS | Primitive.GEOMETRY_NOT_SHARED, 36, app);
        Component3D c = new Component3D();
        c.addChild(ball);
        c.setPreferredSize(new Vector3f(r, r, r));
        
        // It ends by double-clicking. 
        c.addListener(new MouseClickedEventAdapter(
            new Boolean(true),
            new ActionFloat3() {
                public void performAction(LgEventSource source, float x, float y, float z) {            
                    changeEnabled(false);
                    changeVisible(false);                         
                }
            }));
        
        return c;        
    }
    
    
    /**
     * The file is made and the component because of reload it is made.  
     */
    private Component3D createReloader() {
        
        // Diameter
        float r = 0.005f;
        
        SimpleAppearance app = new SimpleAppearance(0.0f, 1.0f, 0.0f, 0.7f, SimpleAppearance.DISABLE_CULLING);       
        Sphere ball = new Sphere(r / 2, 
                Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS | Primitive.GEOMETRY_NOT_SHARED, 36, app);
        Component3D c = new Component3D();
        c.addChild(ball);
        c.setPreferredSize(new Vector3f(r, r, r));
                
        // It is reload in the click. 
        c.addListener(new MouseClickedEventAdapter(
            new Boolean(false),
            new ActionFloat3() {
                public void performAction(LgEventSource source, float x, float y, float z) {            
                    reload();                         
                }
            }));
        
        return c;        
    }
    
    
    /**
     * The component for the title is generated. 
     */
    private Component3D createTitle() {
   
        float width = LgUtil.pixel2meter(128);
        float height = LgUtil.pixel2meter(16);
        
        SimpleAppearance appearance = new SimpleAppearance(0.0f, 0.0f, 0.0f, 0.0f, 
                SimpleAppearance.DISABLE_CULLING);
        FuzzyEdgePanel panel = new FuzzyEdgePanel(width, height, 0.0f, appearance);
        
        ImageTexture texture = new ImageTexture(createTitleImage(128, 16));
        appearance.setTexture(texture);
        
        
        Component3D title = new Component3D();
        title.addChild(panel);
        title.setPreferredSize(new Vector3f(width, height, 0.0f));
        
        return title;
    }
    
    
    /**
     * The image for the title component is made. 
     * 
     * @param width Width of image.
     * @param height Height of image.
     * @return Made image. 
     */
    private BufferedImage createTitleImage(int width, int height) {
        
        // Making of image
        BufferedImage image = createImage(width, height);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
                               
        Font font = new Font("San-Serif", Font.PLAIN, 12);         
        g.setFont(font);
        FontMetrics fontMetrics = g.getFontMetrics();

        // The position where the character string is begun drawing is calculated. 
        int x = 2;

        /*
        while (fontMetrics.stringWidth(file.getName()) > (100 - x)) {
            font = new Font(font.getFamily(), font.getStyle(), font.getSize() - 1);
            g.setFont(font);
            fontMetrics = g.getFontMetrics();
        }
        */
        
        // It draws in the file name. 
        int y = (height - fontMetrics.getHeight()) / 2;     // Centering 
        g.setColor(Color.BLACK);
        g.drawString(file.getName(), x, y + fontMetrics.getAscent());
                            
        g.dispose();
 
        return image;
    }

    
    /**
     * The image is generated.
     * 
     * @param width Width of image.
     * @param height Height of image.
     * @return Made image. 
     */
    private BufferedImage createImage(int width, int height) {
           
       // Making of image.
       BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
       Graphics2D g = (Graphics2D) image.getGraphics();
               
       g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                          RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

       // The background is painted. 
       g.setColor(Color.WHITE);
       g.fillRect(0, 0, width, height);
       
       return image;
    }
       
    
    /**
     * The thumbnail of the file is generated. 
     * When null is returned, the thumbnail is not generated.
     * null is returned here. 
     * 
     * @param file
     * @return
     */
    protected Component3D createThumbnail(File file) {
        return null;
    }
    
    
    /**
     * The component for the file display is generated. 
     * 
     * @param file
     * @return Component by which file is displayed. 
     */   
    protected abstract Component3D createViewer(File file);
    
    
    /**
     * The action of the rotation by the keyboard operation is defined.  
     */
    class KeyListener implements ActionBooleanInt {
        
        private int preKey = -1;        // Key immediately before.  
        private final float dr = 0.1f;  // Rotation corner 
        private float px, py;           // Rotation axis immediately before 
        
        
        public void performAction(
        LgEventSource source, boolean state, int value) {
                        
            switch (value) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_LEFT:
                    rotate(value);
                    break;
            }
        }


        void rotate(int value) {
            
            float rotate = 0;
            float x = 0, y = 0;
            
            switch (value) {
                case KeyEvent.VK_UP:
                    rotate = dr * -1;
                    x = 0.01f;
                    y = 0;                    
                    break;
                    
                case KeyEvent.VK_DOWN:
                    rotate = dr;
                    x = 0.01f;
                    y = 0;
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    rotate = dr;
                    x = 0;
                    y = 0.01f;
                    break;
                    
                case KeyEvent.VK_LEFT:
                    rotate = dr * -1;
                    x = 0;
                    y = 0.01f;
                    break;
            }
            
            
                // There was a change in the rotation axis. 
                if (px != x) {
                    viewer.changeRotationAngle(0, 200);  // It turns it to the front once. With animation. 
                }
                // Direction of the same rotation.
                else {
                    float r = viewer.getRotationAngle();                                
                    viewer.setRotationAxis(x, y, 0);
                    viewer.setRotationAngle(r + rotate);
                    
                    //thumbnail.setRotationAxis(x, y, 0);
                    //thumbnail.setRotationAngle(r + rotate);
                }
            
            preKey = value;
            px = x;
            py = y;    
        }
    }
    
    /*
    class DragDistanceAction implements ActionFloat3 {
        
        float px = 0;
        
        public void performAction(
        LgEventSource event, float x, float y, float z) {
            
            System.out.println(x);
            
            component.setRotationAngle(component.getRotationAngle() + 0.01f);
            component.setRotationAxis(0, px-x, 0);
            px = x;
            
        }
    }
    */
}
