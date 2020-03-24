/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: LgToolbar.java,v $
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
 * $Revision: 1.5 $
 * $Date: 2006-08-16 23:49:05 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgscope;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgscope.util.ImageTexture;
import org.jdesktop.lg3d.apps.lgscope.util.LgUtil;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.action.ActionFloat3;
import org.jdesktop.lg3d.utils.action.ActionInt;
import org.jdesktop.lg3d.utils.eventaction.Component3DMover;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseDraggedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseWheelEventAdapter;
import org.jdesktop.lg3d.utils.shape.ColorCube;
import org.jdesktop.lg3d.utils.shape.Cone;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.Primitive;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.wg.event.MouseWheelEvent3D;


/**
 * The toolbar is defined. 
 */
public class LgToolbar extends Container3D {
    
    private LgScope lgScope;
    private LgToolbar me;
        
    // Component to move the whole. 
    private Component3D mover; 
    
    
    public LgToolbar(
    LgScope lgScope, Vector3f tr, 
    Sorter[] sorters,
    Layout[] layouts) {
        
        this.lgScope = lgScope;
        me = this; 
        
        Vector v = new Vector();
        
        // Mover is generated. 
        mover = makeMover(0.01f);
        addChild(mover);
        
        // UpDirectory is generated. 
        v.add(makeUpDirectory());
        
        // Sorter is generated. 
        v.add(makeSorter(sorters));      
                
        // Layouter is generated. 
        v.add(makeLayouter(layouts));
        
        // ThemeChanger is generated. 
        v.add(makeThemeChanger());
        
        
        // When the position is not specified, it automatically decides it. 
        if (tr == null) {
            
            // It arranges it on the left. 
            float w = LgUtil.getSize(mover).x;
            float x = (Toolkit3D.getToolkit3D().getScreenWidth() / 2.0f) - w - (w / 2.0f);   // It is based on the center of mover.  
            float y = (Toolkit3D.getToolkit3D().getScreenHeight() / 2.0f) - LgUtil.getSize(mover).y;;
            
            tr = new Vector3f(-x, y, 0);
        }
                
        // Positioning of toolbar
        setTranslation(tr.x, tr.y, tr.z);
        
        float x = 0;
        float y = 0;
        float z = 0;
        
        // Positioning of mover
        mover.setTranslation(x, y, z);
        x += LgUtil.getSize(mover).x; 
        // Other positioning
        for (int i = 0; i < v.size(); i++) {
            Component3D c = (Component3D) v.elementAt(i);
            addChild(c);
            c.setTranslation(x, y, z);
            x += LgUtil.getSize(c).x;            
        }
    }

    
    /**
     * The position where the file list is displayed is returned. 
     * 
     * 
     * @return
     */
    public Vector3f getFileAlignment() {
        
         Vector3f v = LgUtil.getTranslation(this);  // This won't move. 
         v.y -= LgUtil.getSize(mover).y; 
         return v; 
    }
    
    
    public Vector3f getFileAbsAlignment() {
        
        Vector3f v = getFileAlignment();
        Vector3f m = LgUtil.getTranslation(mover);
        v.x += m.x;
        v.y += m.y;
        v.z += m.z;
        
        return v;
    }
    
    public Component3D makeMover(float r) {
        return makeMover(r, false);
    }
        
    public Component3D makeMover(float r, boolean noAction) {
        
        SimpleAppearance app = null;
        try {
            app = new SimpleAppearance(getClass().getClassLoader().getResource("org/jdesktop/lg3d/apps/lgscope/resources/LgScope.jpg"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Sphere ball = new Sphere(r, 
            Primitive.GENERATE_NORMALS | Primitive.GENERATE_TEXTURE_COORDS | Primitive.GEOMETRY_NOT_SHARED, 36, app);
                                        
        Component3D mover = new Component3D();                
        mover.setPreferredSize(new Vector3f(r * 2, r * 2, r * 2));        
        mover.addChild(ball);
        
        // FIXME -- short-term modifiaction to make thumbnail behavior
        // compatible with other LG3D apps.
        if (noAction) {
            return mover;
        }
        
        //mover.addListener(new Component3DMover(mover));
        mover.addListener(new ScrollAction());
                                                                  
        // The action of the movement is registered.        
        MoveAction mrAction = new MoveAction();        
        //new MouseDragDistanceAdapter(c, mrAction);
        
        mover.addListener(new MouseDraggedEventAdapter(MouseEvent3D.ButtonId.BUTTON1, mrAction));
        mover.addListener(new MouseWheelEventAdapter(mrAction));
        
        // The action of the rotation is registered. 
        RotationAction roAction = new RotationAction(); 
        mover.addListener(new MouseDraggedEventAdapter(MouseEvent3D.ButtonId.BUTTON3, roAction));
        
        // It ends by double-clicking. 
        mover.addListener(new MouseClickedEventAdapter( 
                // MouseEvent3D.ButtonId.BUTTON1,
                true,
                new ActionFloat3() {
                    public void performAction(LgEventSource source, float x, float y, float z) {            
                        lgScope.changeEnabled(false);
                        lgScope.changeVisible(false);                         
                    }
                }));
                        
        return mover;
    }

    
    
    /**
     * UpDirectory is generated. 
     * 
     */
    private Component3D makeUpDirectory() {
        
        Component3D c = new Component3D();
        c.setPreferredSize(new Vector3f(0.03f, 0.005f, 0.005f));
        //upDirectory.addChild(cb);
        
        // Cone is used. 
        Appearance app = new SimpleAppearance(1.0f, 0.6f, 0.6f, 1.0f, SimpleAppearance.DISABLE_CULLING);
        Cone cone = new Cone(0.005f, 0.01f, app);    // Diameter and height 
        c.addChild(cone);
        //Cylinder c = new Cylinder(0.01f,0.01f, Cylinder.TOP, app);
        //upDirectory.addChild(c);
        
        /* 
        AcceleratingMotionAnimationFactory factory = new AcceleratingMotionAnimationFactory(30);
        upDirectory.setAnimation(factory.createInstance());        
        NaturalMotionAnimationFactory factory2 = new NaturalMotionAnimationFactory(30);
        upDirectory.setAnimation(factory2.createInstance());
        */
                
        c.addListener(new Component3DMover(c));        
        c.addListener(new MouseClickedEventAdapter(MouseEvent3D.ButtonId.BUTTON1, new UpDirectoryAction()));
        
        return c;
    }
    
    
    /**
     * The action when the movement happens to Mover is defined. 
     * 
     */
    class MoveAction implements ActionFloat3, ActionInt {
        
        // The previous one coordinates are memorized. 
        private Vector3f prePos = null;
        
        public void performAction(LgEventSource source, float x, float y, float z) {            
            moveAction();
        }
        
        public void performAction(LgEventSource source, int value) {
            moveAction();
        }
                
        private void moveAction() {
            
            Vector3f mv = new Vector3f(); 
            mv = mover.getTranslation(mv);
            
            if (prePos == null) {
                prePos = mv;                
                return;
            }
            
            //Vector3f v = mover.getTranslation();
            
            float dx = mv.x - prePos.x;
            float dy = mv.y - prePos.y;
            float dz = mv.z - prePos.z;
            
            
            // The file list is moved. 
            lgScope.move(dx, dy, dz);
            
            for (Enumeration e = me.getAllChildren(); e.hasMoreElements(); ) {
                
                Component3D c = (Component3D) e.nextElement();
                
                if (c != mover) {
                    lgScope.moveComponent(c, dx, dy, dz);
                }
            }
                        
            prePos = mv;
        }
    }
    
    
    /**
     * The action of the scroll Z axially is defined.
     * 
     */
    class ScrollAction extends Component3DMover {
        
        public void processEvent(LgEvent event) {
            
            if (!(event instanceof MouseWheelEvent3D)) {
                super.processEvent(event);
                return;
            }
            
            MouseWheelEvent3D wheel = (MouseWheelEvent3D) event;
            float dz = wheel.getWheelRotation();
            
            lgScope.move(0, 0, dz / 5.0f);            
        }
    }
    
    
    /**
     * The action when the rotation happens to Mover is defined. 
     * 
     */
    class RotationAction implements ActionFloat3 {
        
        public void performAction(LgEventSource source, float x, float y, float z) {
            
            Vector3f v = LgUtil.getTranslation(mover);                
                     
            //component.setRotationAxis((v.y - y) * 100.0f, (v.x - x) * 100.0f, (v.z - z) * 100.0f);
            //component.setRotationAngle((float)Math.toRadians(60));
            //component.setRotationAngle((v.x - x) * 100.0f);

            
            Vector3f rot = LgUtil.getRotationAxis(mover);
            float d = 5.0f;    // It turns fast when this coefficient is increased. 
            float rx = rot.x + (v.x - x) * d;
            float ry = rot.y + (v.y - y) * d;
            float rz = rot.z + (v.z - z) * d;                           
            float r = (v.x - x) * d;            
            
            Vector3f size = LgUtil.getSize(mover); 
            
            // Center coordinates are requested. 
            float cx = v.x + (size.x / 2);
            //System.out.println(cx + "-" + x + "="+(cx-x));
            System.out.println(x-cx);
                        
            mover.setRotationAxis(ry, rx, rz);
            mover.setRotationAngle(r);       
            
            lgScope.rotate(rx, ry, rz, r);
            
            for (Enumeration e = me.getAllChildren(); e.hasMoreElements(); ) {
                
                Component3D c = (Component3D) e.nextElement();
                
                if (c != mover) {
                    lgScope.rotateComponent(c, ry, rx, rz, r);
                }
            }            
        }
    }    
    //component.setRotationAxis(0.0f, 1.0f, 0.0f);   // y axis fixation 
    

    /**
     * Action to open directory above one. 
     * 
     */    
    class UpDirectoryAction implements ActionFloat3
    {
        public void performAction(LgEventSource source, float x, float y, float z) {
            
            // It is root directory. 
            if (lgScope.dir.getParentFile() == null) {
                return;
            }
            
            // Meaning that directory above one was selected. 
            lgScope.fireExecuteEvent(null, lgScope.dir.getParentFile(), source, x, y, z);
        }
    }
    
    
    /**
     * The Sorter component is generated. 
     * 
     * 
     * @param sorters
     */
    private Component3D makeSorter(Sorter[] sorters) {
        
        // Appearance is generated. 
        Appearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                                                     SimpleAppearance.ENABLE_TEXTURE 
                                                     | SimpleAppearance.DISABLE_CULLING);                                                   
        float width = LgUtil.pixel2meter(128);
        float height = LgUtil.pixel2meter(32);
        
        appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        
        // Generation of Shape3D. 
        Shape3D shape = new FuzzyEdgePanel(width, height, 0.001f, appearance);
        //Shape3D shape = new GlassyPanel(width, height, 0, 0.0f, appearance);    // The texture cannot be pasted. 
                
        Component3D c = new Component3D();
        c.addChild(shape);
        
        c.setPreferredSize(new Vector3f(width, height, 0f));
        // new ComponentMover(sorter);
        c.addListener(new Component3DMover(c));
        c.addListener(
            new MouseClickedEventAdapter(
                MouseEvent3D.ButtonId.BUTTON1, new SorterAction(c, sorters, appearance)));
        
//        new MouseClickedEventAdapter(
//            sorter,
//            new ResilientRotateAction(sorter, 50.0f, 500), 
//            MouseEvent3D.ButtonId.BUTTON1);

        return c;        
    }
    
    
    
    /**
     * The action when Sorter is clicked is defined. 
     * 
     */
    class SorterAction implements ActionFloat3 {
        
        Sorter[] sorters;        
        
        // Texture by which it draws in character. 
        ImageTexture texture;
        
        // Size of component. 
        int width, height; 
        
        int index = 0;
        
        SorterAction(Component3D comp, Sorter[] sorters, Appearance appearance) {
            
            this.sorters = sorters;
            
            width = LgUtil.meter2pixel(LgUtil.getSize(comp).x);
            height = LgUtil.meter2pixel(LgUtil.getSize(comp).y);
            
            // The texture image is generated. 
            BufferedImage image = new BufferedImage(                            
                ImageTexture.getTextureImageSize(width),
                ImageTexture.getTextureImageSize(height),                
                BufferedImage.TYPE_INT_ARGB);
            
            texture = new ImageTexture(image);
            
            appearance.setTexture(texture);
            changeSorter();
        }
        
        
        public void performAction(LgEventSource source, float x, float y, float z) {            
            index = (index == sorters.length - 1) ? 0 : index + 1;
            changeSorter();
        }
        
        
        /**
         * Sorter is switched. 
         * 
         */
        private void changeSorter() {
            
            Graphics2D g = (Graphics2D) texture.image.getGraphics(); 
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
        
            g.setColor(Color.DARK_GRAY);
            Font font = new Font("San-Serif", Font.BOLD, 13); 
            g.setFont(font);
            // It centers it in the vertical direction. 
            int y = font.getSize() + ((height - font.getSize()) / 2);
            g.drawString(sorters[index].getName(), 3, y);
            g.dispose();
             
            texture.updateImage();
            
            lgScope.setSorter(sorters[index]);
            lgScope.sort();
        }        
    }
    
    
    private Component3D makeLayouter(Layout[] layouts) {
        
        // Appearance is generated. 
        Appearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                                                     SimpleAppearance.ENABLE_TEXTURE 
                                                     | SimpleAppearance.DISABLE_CULLING);                                                   
        float width = LgUtil.pixel2meter(128);
        float height = LgUtil.pixel2meter(32);        
        
        appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        
        // Generation of Shape3D. 
        Shape3D shape = new FuzzyEdgePanel(width, height, 0.001f, appearance);
                
        Component3D c = new Component3D();
        c.addChild(shape);
        
        c.setPreferredSize(new Vector3f(width, height, 0.0f));
        c.addListener(new Component3DMover(c));
        c.addListener(new MouseClickedEventAdapter(
            MouseEvent3D.ButtonId.BUTTON1, new LayouterAction(c, layouts, appearance)));
        
        
//        new MouseClickedEventAdapter(
//            layouter,
//            new ResilientRotateAction(layouter, 50.0f, 500), 
//            MouseEvent3D.ButtonId.BUTTON1);

        return c;        
    }
    
    
    class LayouterAction implements ActionFloat3 {
        
        Layout[] layouts;
        // Texture by which it draws in character. 
        ImageTexture texture;
        
        // Size of component. 
        int width, height;
                
        int index = 0;
        
        LayouterAction(Component3D comp, Layout[] layouts, Appearance appearance) {
            
            this.layouts = layouts;            
            
            width = LgUtil.meter2pixel(LgUtil.getSize(comp).x);
            height = LgUtil.meter2pixel(LgUtil.getSize(comp).y);
            
            // The texture image is generated. 
            BufferedImage image = new BufferedImage(                            
                ImageTexture.getTextureImageSize(width),
                ImageTexture.getTextureImageSize(height),                
                BufferedImage.TYPE_INT_ARGB);
            
            texture = new ImageTexture(image);
            
            appearance.setTexture(texture);
            
            changeLayout();
        }
        
        public void performAction(LgEventSource source, float x, float y, float z) {            
            index = (index == layouts.length - 1) ? 0 : index + 1;
            changeLayout();
        }
        
        
        private void changeLayout() {
            
            Graphics2D g = (Graphics2D) texture.image.getGraphics(); 
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
        
            g.setColor(Color.DARK_GRAY);
            Font font = new Font("San-Serif", Font.BOLD, 13); 
            g.setFont(font);
            // It centers it in the vertical direction. 
            int y = font.getSize() + ((height - font.getSize()) / 2);
            g.drawString(layouts[index].getName(), 3, y);
            g.dispose();
 
            texture.updateImage();
            
            lgScope.setLayout(layouts[index]);
            lgScope.layout();
        }        
    }
    
    
    private Component3D makeThemeChanger() {
        
        float w = 0.005f;
        
        Component3D c = new Component3D();
        c.setPreferredSize(new Vector3f(w, w, w));
        
        ColorCube cc = new ColorCube(w);
        c.addChild(cc);
                        
        c.addListener(new Component3DMover(c));        
        c.addListener(new MouseClickedEventAdapter(
            MouseEvent3D.ButtonId.BUTTON1, new ChangeThemeAction(MouseEvent3D.ButtonId.BUTTON1)));        
        c.addListener(new MouseClickedEventAdapter(
            MouseEvent3D.ButtonId.BUTTON3, new ChangeThemeAction(MouseEvent3D.ButtonId.BUTTON3)));
        
        return c;
    }
    
    /**
     * Theme is changed.
     * 
     */    
    class ChangeThemeAction implements ActionFloat3 {
        
        MouseEvent3D.ButtonId id;
        
        ChangeThemeAction(MouseEvent3D.ButtonId id) {
            this.id = id;
        }
        
        public void performAction(LgEventSource source, float x, float y, float z) {
            
            int dir = (id == MouseEvent3D.ButtonId.BUTTON1) ? 1 : -1;            
            lgScope.changeFileCreator(dir);             
        }
    }
}



