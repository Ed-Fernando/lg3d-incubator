/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: BackCover.java,v $
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
 * $Date: 2007-03-09 09:37:36 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.component;


import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgamazon.stub.EditorialReview;
import org.jdesktop.lg3d.apps.lgamazon.stub.Item;
import org.jdesktop.lg3d.apps.lgamazon.util.LgUtil;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventListener;


/**
 * 
 * 
 * 
 * 
 *
 */
public abstract class BackCover extends Component3D {

    /** 
     * 
     */
    private Component3D info;
    /** EditorsReview. */
    private EditorsReviewPanel edrPanel;  
    
    
    /**
     * 
     * 
     */
    public BackCover(Component3D info) {
        
        this.info = info;
        
        edrPanel = new EditorsReviewPanel(Product.createWhiteAppearance());
        
        setAnimation(new NaturalMotionAnimation(500));
    }
    
    
    @Override
    public void addListener(LgEventListener listener) {
        
        super.addListener(listener);
        edrPanel.addListener(listener);
    }
    
    
    public void setInfo(
    Item item, Vector3f size, EditorialReview[] review) {
        
        removeAllChildren();
        
        GlassyPanel panel = 
            new GlassyPanel(size.x, size.y, size.z, 0.001f, Product.createWhiteAppearance());
        addChild(panel);
        
        Component3D backCoverComponent = new Component3D();                
        backCoverComponent.setRotationAxis(0.0f, 1.0f, 0.0f);
        backCoverComponent.setRotationAngle((float) -Math.PI / 2);   
        backCoverComponent.setTranslation(-size.x / 2.0f, 0.0f, -size.z / 2.0f);
        addChild(backCoverComponent);
        
        
        
        float[] bgColor = new float[] {0.956f, 0.956f, 0.886f, 0.8f};
        
        ImagePanel bgPanel = new ImagePanel(size.z, size.y);
        Appearance bgPanelApp = new SimpleAppearance(
                bgColor[0], bgColor[1], bgColor[2], bgColor[3], 
                SimpleAppearance.DISABLE_CULLING);
        bgPanel.setAppearance(bgPanelApp);
        backCoverComponent.addChild(bgPanel);
        
        
        setInfo(info, item, size.z, size.y, bgColor[0], bgColor[1], bgColor[2], bgColor[3]); 
        
        info.setRotationAxis(0.0f, 1.0f, 0.0f);
        info.setRotationAngle((float) -Math.PI / 2);   
        Vector3f infoSize = LgUtil.getSize(info);
        info.setTranslation(
                -((size.x / 2.0f) + 0.001f), 
                (size.y / 2.0f) - (infoSize.y / 2.0f),
                -(size.z / 2.0f));  
        addChild(info);
        
        
        edrPanel.setReview(review, size.z, size.y);
        
        edrPanel.setRotationAxis(0.0f, 1.0f, 0.0f);
        edrPanel.setRotationAngle((float) Math.PI / 2);   
        Vector3f edrSize = LgUtil.getSize(edrPanel);
        edrPanel.setTranslation(
                size.x / 2.0f + 0.001f, 
                (size.y / 2.0f) - (edrSize.y / 2.0f), 
                -(size.z / 2.0f));     
        addChild(edrPanel);
    } 
    
    
    protected abstract void setInfo(
    Component3D info,
    Item item, float width, float height, 
    float red, float green, float blue, float alpha);
    
}
