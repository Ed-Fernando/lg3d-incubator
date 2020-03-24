/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: LgDVD.java,v $
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
 * $Date: 2007-03-09 09:38:09 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.dvd;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.jdesktop.lg3d.apps.lgamazon.component.BackCover;
import org.jdesktop.lg3d.apps.lgamazon.component.FrontCover;
import org.jdesktop.lg3d.apps.lgamazon.component.MainPanel;
import org.jdesktop.lg3d.apps.lgamazon.component.Product;
import org.jdesktop.lg3d.apps.lgamazon.component.SpineCover;
import org.jdesktop.lg3d.apps.lgamazon.stub.Item;
import org.jdesktop.lg3d.wg.Component3D;



/**
 * 
 * 
 * 
 * 
 * 
 */
public class LgDVD extends Product {

    private static BufferedImage NO_IMAGE;
    
    static {

        try {
            NO_IMAGE = ImageIO.read(
                    Product.class.getResourceAsStream("/org/jdesktop/lg3d/apps/lgamazon/resources/DVDNoImage.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public LgDVD(MainPanel mainPanel) {        
        super(mainPanel, new FrontCover(NO_IMAGE), new DVDBackCover(), new SpineCover());
    }

    @Override
    protected float getThickness(Item item) {
        return 0.015f;   // 1.5cm
    }
}


class DVDBackCover extends BackCover {

    DVDBackCover() {
        super(new DVDInfoPanel(Product.createWhiteAppearance()));       
    }
    
    
    @Override
    protected void setInfo(
    Component3D info, Item item, float width, float height, 
    float red, float green, float blue, float alpha) {
                
        DVDInfoPanel dvdInfo = (DVDInfoPanel) info; 
        
        dvdInfo.setInfo(item, width, height, red, green, blue, alpha);
               
    }
    
}

