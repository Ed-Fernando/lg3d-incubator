/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: FrontCover.java,v $
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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import javax.imageio.ImageIO;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgamazon.stub.CustomerReviews;
import org.jdesktop.lg3d.apps.lgamazon.stub.Image;
import org.jdesktop.lg3d.apps.lgamazon.util.ImageTexture;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;

/**
 * 
 * 
 * 
 * 
 *
 */
public class FrontCover extends Component3D {

    BufferedImage noImage;
    
    public final static int DEFAULT_IMAGE_WIDTH = 512;
    public final static int DEFAULT_IMAGE_HEIGHT = 512;
    
    
    /** 
     * 
     */
    ImageTexture coverTexture;
    private SimpleAppearance coverAppearance;
    
    
    private GlassyPanel glassyPanel;
    private ImagePanel coverImage;
    private Component3D coverComponent = new Component3D();
    private Component3D backCoverComponent = new Component3D();
    private ImagePanel bgPanel;
    
    
    private CustomerReviewsPanel csrPanel;
    
    
    public FrontCover(BufferedImage noImage) {
        
        this.noImage = noImage;
        
        coverAppearance = Product.createWhiteAppearance();
        BufferedImage coverImage = new BufferedImage(
                DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);        
        coverTexture = new ImageTexture(coverImage);        
        coverAppearance.setTexture(coverTexture);
        
        csrPanel = new CustomerReviewsPanel();
        addChild(csrPanel);
                
        addChild(coverComponent);
        addChild(backCoverComponent);
        
        bgPanel = new ImagePanel(0.0f, 0.0f);
        
        Appearance bgPanelApp = new SimpleAppearance(
                0.956f, 0.956f, 0.886f, 0.8f, 
                SimpleAppearance.DISABLE_CULLING);
        bgPanel.setAppearance(bgPanelApp);
        backCoverComponent.addChild(bgPanel);
    }
    
    
    public void setInfo(
    Vector3f size, Image image, CustomerReviews reviews) {
        
//        if (glassyPanel == null) {
//            glassyPanel = new GlassyPanel(size.x, size.y, size.z, 0.001f, Product.createWhiteAppearance());
//            addChild(glassyPanel);
//        }
//        else {
//            glassyPanel.setSize(size.x, size.y);
//        }
        
        if (glassyPanel != null) {
            removeChild(glassyPanel);
        }
        
        glassyPanel = new GlassyPanel(size.x, size.y, size.z, 0.001f, Product.createWhiteAppearance());
        addChild(glassyPanel);
        
        
        if (coverImage == null) {
            coverImage = new ImagePanel(size.z, size.y);            
            coverImage.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
            coverImage.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE); 
            coverAppearance.setAlpha(1.0f);     
            coverImage.setAppearance(coverAppearance);
            
            coverComponent.addChild(coverImage);
        }
        else {
            coverImage.setSize(size.z, size.y);
        }
        
        
        backCoverComponent.setRotationAxis(0.0f, 1.0f, 0.0f);
        backCoverComponent.setRotationAngle((float) -Math.PI / 2);   
        backCoverComponent.setTranslation(-size.x / 2.0f, 0.0f, -size.z / 2.0f);
        
        bgPanel.setSize(size.z, size.y); 
        
        coverComponent.setRotationAxis(0.0f, 1.0f, 0.0f);
        coverComponent.setRotationAngle((float) Math.PI / 2);   
        coverComponent.setTranslation(size.x / 2.0f, 0.0f, -size.z / 2.0f);
        
        
        String url = (image != null) ? image.getURL() : null;
        new DrawFrontImageThread(url).start();

        
        csrPanel.setCustomerReviews(size.z, size.y, reviews);
        csrPanel.setRotationAxis(0.0f, 1.0f, 0.0f);
        csrPanel.setRotationAngle((float) -Math.PI / 2);
        csrPanel.setTranslation(-0.001f, 0.0f, -size.z / 2.0f); // z, y, x
    }
    
    
    /**
     * 
     */
    class DrawFrontImageThread extends Thread {
                
        String url = null;
        
        DrawFrontImageThread(String url) {
            this.url = url;
            setPriority(NORM_PRIORITY);
        }
        
        public void run() {
            
            BufferedImage realImage = noImage;
            
            Graphics2D g = (Graphics2D) coverTexture.image.getGraphics();
            
            try {            
                                
                if (url != null) {
                    realImage = getImage(url); 
                    
                    if (realImage == null) {
                        realImage = noImage;
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
            g.drawImage(
                realImage, 0, 0, coverTexture.image.getWidth(), coverTexture.image.getHeight(), null);
            g.dispose();
                
            coverTexture.updateImage();
        }
    }
    
    
    public BufferedImage getImage(String urlStr) 
    throws MalformedURLException, IOException {
        
        URL url = new URL(urlStr);
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        int code = conn.getResponseCode();
        if ((code / 100) != 2) {
            // throw new IOException("HTTP ERROR:" + code + ":" + urlStr);
            System.out.println("HTTP ERROR:" + code + ":" + urlStr);
            return null;
        }
        
        // int len = Integer.parseInt(conn.getHeaderField("Content-Length"));
        
        return ImageIO.read(conn.getInputStream());        
    }  
}
