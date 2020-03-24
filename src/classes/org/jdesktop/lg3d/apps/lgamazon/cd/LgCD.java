/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: LgCD.java,v $
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
 * $Date: 2007-03-09 09:36:53 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.cd;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.jdesktop.lg3d.apps.lgamazon.component.BackCover;
import org.jdesktop.lg3d.apps.lgamazon.component.FrontCover;
import org.jdesktop.lg3d.apps.lgamazon.component.MainPanel;
import org.jdesktop.lg3d.apps.lgamazon.component.Product;
import org.jdesktop.lg3d.apps.lgamazon.component.SpineCover;
import org.jdesktop.lg3d.apps.lgamazon.stub.Image;
import org.jdesktop.lg3d.apps.lgamazon.stub.Item;
import org.jdesktop.lg3d.wg.Component3D;

public class LgCD extends Product {
    
    private static BufferedImage NO_IMAGE;
    
    static {

        try {
            NO_IMAGE = ImageIO.read(
                    Product.class.getResourceAsStream("/org/jdesktop/lg3d/apps/lgamazon/resources/CDNoImage.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public LgCD(MainPanel mainPanel) {        
        super(mainPanel, new FrontCover(NO_IMAGE), new CDBackCover(), new SpineCover());
    }

    @Override
    protected float getThickness(Item item) {
        return 0.0104f;   // 1.04cm
    }

    
    @Override
    protected Dimension getCoverImageSize(Image image) {

        // When the image is small, it compulsorily enlarges it. 
        Dimension d = super.getCoverImageSize(image);
        
        if (d.width < 400) {
            d.width = 512;
        }
        if (d.height < 400) {
            d.height = 512;
        }
        
        return d;
    }    
}


class CDBackCover extends BackCover {

    CDBackCover() {
        super(new CDInfoPanel(Product.createWhiteAppearance()));       
    }
    
    
    @Override
    protected void setInfo(
    Component3D info, Item item, float width, float height, 
    float red, float green, float blue, float alpha) {
                
        CDInfoPanel cdInfo = (CDInfoPanel) info; 
        
        cdInfo.setInfo(item, width, height, red, green, blue, alpha);               
    }    
}
