/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: Product.java,v $
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
 * $Date: 2007-03-09 09:37:39 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.component;


import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgamazon.stub.CustomerReviews;
import org.jdesktop.lg3d.apps.lgamazon.stub.EditorialReview;
import org.jdesktop.lg3d.apps.lgamazon.stub.EditorialReviews;
import org.jdesktop.lg3d.apps.lgamazon.stub.Image;
import org.jdesktop.lg3d.apps.lgamazon.stub.Item;
import org.jdesktop.lg3d.apps.lgamazon.util.LgUtil;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionFloat3;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.c3danimation.TransparencyChangeVisiblePlugin;
import org.jdesktop.lg3d.utils.eventaction.Component3DMover;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;


/**
 * 
 * 
 */ 
public abstract class Product extends Container3D {
    
    /** 
     * 
     */
    private FrontCover frontCover; 
    
    /** 
     * 
     */
    private BackCover backCover;
    
    /** 
     * 
     */
    private SpineCover spineCover; 
    
    private MainPanel mainPanel; 


    private Toolkit3D toolkit = Toolkit3D.getToolkit3D(); 
    
    
    private DefinePos frontCoverPos;
    private DefinePos backCoverPos;
    
    
    private boolean isTakeProduct = false;
    private boolean showFront = false;

    
    
    private float zoom = 0.6f;
    float selectedZpos = 0.03f;
    
    /** 
     * 
     * 
     */
    private float viewScale = 1.3f;
    
    
    public final MouseClickedEventAdapter button1Listener = new MouseClickedEventAdapter( 
            MouseEvent3D.ButtonId.BUTTON1, new MouseClickAction());
    
    public final MouseClickedEventAdapter button3Listener = new MouseClickedEventAdapter( 
            MouseEvent3D.ButtonId.BUTTON3, new ReverseAction());

    
    protected Product(
    MainPanel mainPanel, 
    FrontCover frontCover, BackCover backCover, SpineCover spineCover) {
    	
    	this.mainPanel = mainPanel;
        
        this.frontCover = frontCover;
        this.backCover = backCover;
        this.spineCover = spineCover;
        
        frontCoverPos = new DefinePos();
        backCoverPos = new DefinePos();
        
        Component3DMover mover = new Component3DMover(this);
        
        addListener(mover);
        setAnimation(new NaturalMotionAnimation(500, new TransparencyChangeVisiblePlugin(1000)));
        
        addListener(button1Listener);
        addListener(button3Listener);
        addListener(new MouseEnteredEventAdapter(new MouseEnteredAction()));
        
        frontCover.addListener(button1Listener);
        frontCover.addListener(button3Listener);
        frontCover.addListener(mover);
        backCover.addListener(button1Listener);
        backCover.addListener(button3Listener);
        backCover.addListener(mover);
    }
    
    
    /**
     * 
     * @return
     */
    public static SimpleAppearance createWhiteAppearance() {
        
        SimpleAppearance app = new SimpleAppearance(
                1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);                                                   

        app.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
        
        return app;
    }
    
    
    /**
     * 
     * 
     */
    class MouseEnteredAction implements ActionBoolean {
        
        public void performAction(LgEventSource source, boolean state) {            
            
            changeScale((state ? zoom : defaultScale), 1000);
                        
            if (!isTakeProduct) {
                
                if (state) {
                    setRotationAxis(0, 1, 0);
                    changeRotationAngle(-(float) Math.PI / 2, 1000);
                    Vector3f pos = LgUtil.getTranslation((Product) source);
                    changeTranslation(pos.x, pos.y, selectedZpos);
                }
                else {
                    goHome();
                }
                
                return;
            }
            
            
            if (state) {
                
                openCover();                
                Vector3f pos = LgUtil.getTranslation((Product) source);  
                changeTranslation(pos.x, pos.y, selectedZpos);
            }
            else {
                
                setRotationAxis(0, 1, 0);
                changeRotationAngle(-(float) Math.PI / 2, 1000);
                closeCover();
                
                Vector3f pos = LgUtil.getTranslation((Product) source);
                changeTranslation(pos.x, pos.y, 0.0f);
            }
        }
    }
    
    
    public void setItem(Item item) throws IOException {
        
        Image image = item.getLargeImage();
        //Image image = item.getSmallImage();
        //Image image = item.getMediumImage();
        
//        if (image != null) {
//            System.out.println(image.getURL());
//            System.out.println(image.getWidth() + " x " + image.getHeight());
//        }
//        System.out.println(item.getItemAttributes().getTitle());
//        System.out.println(item.getItemAttributes().getNumberOfPages() + " page");        
//        System.out.println();
                
        
        EditorialReviews edReviews = item.getEditorialReviews();
        EditorialReview[] edReview = null;
        if (edReviews != null) {
            edReview = edReviews.getEditorialReview();
//          for (int i = 0; i < review.length; i++) {
//              System.out.println("RS:" + review[i].getSource());
//              System.out.println("RC:" + review[i].getContent());
//          }
        }
        
        
        CustomerReviews csReviews = item.getCustomerReviews();
//        if (csReviews != null) {
//            
//            Review[] csReview = csReviews.getReview();            
//            for (int i = 0; i < csReview.length; i++) {
//                System.out.println("ID:" + csReview[i].getCustomerId());
//                System.out.println("Date:" + csReview[i].getDate());
//                System.out.println("Rateing:" + csReview[i].getRating());
//                System.out.println("TotalVotes:" + csReview[i].getTotalVotes());
//                System.out.println("HelpfulVotes:" + csReview[i].getHelpfulVotes()); 
//                System.out.println("Summary:" + csReview[i].getSummary());
//                System.out.println("Content:" + csReview[i].getContent());
//                System.out.println();
//            }
//        }
        
        
        removeAllChildren();    

        
        
        float width = getThickness(item);
                
        float height, depth;        
        Dimension d = getCoverImageSize(image);
        height = toolkit.widthNativeToPhysical(d.height);
        depth =  toolkit.widthNativeToPhysical(d.width);
        
        height *= viewScale;
        depth *= viewScale;

        
        Vector3f coverSize = new Vector3f(width / 2.0f, height, depth);
        frontCover.setInfo(coverSize, image, csReviews);
        addChild(frontCover);

        frontCoverPos.homeAngle = 0.0f;
        frontCoverPos.homePos = new Vector3f(width / 4.0f, 0.0f, 0.0f); 

        frontCover.setTranslation(frontCoverPos.homePos.x,
                frontCoverPos.homePos.y, frontCoverPos.homePos.z);

        backCover.setInfo(item, coverSize, edReview);
        addChild(backCover);

        backCoverPos.homeAngle = 0.0f;
        backCoverPos.homePos = new Vector3f(-width / 4.0f, 0.0f, 0.0f); 
        backCoverPos.openAngle = -(float) Math.PI / 1.0f; 
        backCoverPos.openPos = new Vector3f(width / 4.0f, 0.0f, -depth * 2.0f);

        backCover.setTranslation(backCoverPos.homePos.x,
                backCoverPos.homePos.y, backCoverPos.homePos.z);

        
        Color bg = new Color(frontCover.coverTexture.image.getRGB(3, 3));  
        spineCover.setInfo(cutTitle(item.getItemAttributes().getTitle()), height, width, bg);        
        addChild(spineCover);
        
        setPreferredSize(new Vector3f(width, height, depth));
    }
    
    
    protected Dimension getCoverImageSize(Image image) {
        
        Dimension d = new Dimension(frontCover.noImage.getWidth(), frontCover.noImage.getHeight());
        
        if (image != null) {            
            d.width = image.getWidth().get_value().intValue();
            d.height = image.getHeight().get_value().intValue();
        }
        
        return d;
    }
    
    
    /**
     * 
     * 
     * @return
     */
    protected abstract float getThickness(Item item); 
    
    
    
    /**
     * 
     */
    private static final String[][] IMPU_TAG = new String[][]{
            {"<p>", "<p/>"},
            {"<P>", "<p/>"},
            {"<br>", "<br/>"},
            {"<BR>", "<br/>"},
            {"<p>", "<p/>"},
            {"<body>", "<body/>"},
            {"&", "&amp;"},
    };
    
    
    public static String imputationTag(String document) {
        
        for (int i = 0; i < IMPU_TAG.length; i++) {
            document = document.replaceAll(IMPU_TAG[i][0], IMPU_TAG[i][1]);
        }
        
        return document;
    }
    
            
    
    public static String cutTitle(String title) {

        char[] separator = new char[] {'(', ':', ','};
        
        for (int i = 0; i < separator.length; i++) {
        
            int p = title.indexOf(separator[i]);
            if (p != -1) {
                title = title.substring(0, p);
            }
        }
        
        return title;
//        
//        
////        final int TITLE_LENGTH = 20;    
//        
//        char[] separator = new char[] {'(', ':', ','};
//        
////        if (title.length() < TITLE_LENGTH) {
////            return title;
////        }
//        
//        for (int i = 0; i < title.length(); i++) {
//            
//            char c = title.charAt(i);
//            
//            for (int j = 0; j < separator.length; j++) {
//                if (c == separator[j]) {
//                    return title.substring(0, i).trim();
//                }
//            }
//        }
//        
//        return title;
    }
        

    /**
     * 
     */
    class MouseClickAction implements ActionFloat3 {
        
        public void performAction(
        LgEventSource source, float x, float y, float z) {            
            
            isTakeProduct = !isTakeProduct;
            
            if (isTakeProduct) {                
                openCover();                
                showFront = true;
                centering();
            }
            else {
                goHome();
            }
        }        
    }  
    
    
    /**
     * 
     */
    class ReverseAction implements ActionFloat3 {
        
        public void performAction(
        LgEventSource source, float x, float y, float z) {            
            
            if (isTakeProduct) {   
                
                showFront = !showFront;
                
                setRotationAxis(0, 1, 0);
                
                float angle = showFront ? -(float) Math.PI / 2 : (float) Math.PI / 2;
                                                
                changeRotationAngle(angle, 1000);
                centering();
            }
        }        
    }
    
    
    /**
     * 
     */
    private void centering() {
    	
    	Vector3f size = LgUtil.getSize(this);
    	
    	float y = (LgUtil.getTranslation(mainPanel).y - (LgUtil.getSize(mainPanel).y / 2.0f)) - (size.y / 2.0f) * getScale();
    	y -= 0.005f;	
         
        float x = showFront ? -size.z : size.z; 
        changeTranslation(x * zoom, y, selectedZpos);
    }
    
    
    private void openCover() {
      
        setRotationAxis(0, 1, 0);
        
        changeRotationAngle(-(float) Math.PI / 2, 1000);
        changeScale(zoom, 1000);  
        
        Vector3f p = LgUtil.getTranslation(this);
        p.z = 0.01f;                
        changeTranslation(p, 1000);
        
        backCover.setRotationAxis(0, 1, 0);
        backCover.changeRotationAngle(backCoverPos.openAngle, 1000);
        backCover.changeTranslation(backCoverPos.openPos, 1000);
        
        backCover.setTranslation(backCoverPos.openPos.x, backCoverPos.openPos.y, backCoverPos.openPos.z);
    }
    
    private void closeCover() {
        
        frontCover.changeTranslation(frontCoverPos.homePos, 500);
        
        backCover.changeTranslation(backCoverPos.homePos, 500);
        backCover.setRotationAxis(0, 1, 0);
        backCover.changeRotationAngle(backCoverPos.homeAngle, 500);                
    }
    
    
    private Vector3f home;
    private float defaultScale;
    private Vector3f defaultAxis;
    private float defaultAngle;
    
    public void setHome(
    Vector3f pos, float scale, Vector3f axis, float angle) {
        
        home = pos;
        defaultScale = scale;        
        defaultAxis = axis;
        defaultAngle = angle;
    }
    
    
    public void goHome() {
        
        if (home == null) {
            return;
        }
        
        isTakeProduct = false;
        showFront = false;
                
        changeScale(defaultScale, 500);
        
        setRotationAxis(defaultAxis.x, defaultAxis.y, defaultAxis.z);
        changeRotationAngle(defaultAngle, 500);        
        
        changeTranslation(home, 500);
        
        closeCover();
    }
    
    
    class DefinePos {
        
        Vector3f homePos;
        float homeAngle;
        
        Vector3f openPos;
        float openAngle;
    }
}
