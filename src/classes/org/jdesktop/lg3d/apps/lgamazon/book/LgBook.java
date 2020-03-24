/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: LgBook.java,v $
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
 * $Date: 2007-03-09 09:36:08 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.book;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.jdesktop.lg3d.apps.lgamazon.component.BackCover;
import org.jdesktop.lg3d.apps.lgamazon.component.FrontCover;
import org.jdesktop.lg3d.apps.lgamazon.component.MainPanel;
import org.jdesktop.lg3d.apps.lgamazon.component.Product;
import org.jdesktop.lg3d.apps.lgamazon.component.SpineCover;
import org.jdesktop.lg3d.apps.lgamazon.stub.Item;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Toolkit3D;

public class LgBook extends Product {

    private Toolkit3D toolkit = Toolkit3D.getToolkit3D();
    
    private final static int MAX_THICKNESS = 256; 
    private final static int MIN_THICKNESS = 64;
    private final static int DEFAULT_THICKNESS = 128;
    
    
    private static BufferedImage NO_IMAGE;
    
    static {

        try {
            NO_IMAGE = ImageIO.read(
                    Product.class.getResourceAsStream("/org/jdesktop/lg3d/apps/lgamazon/resources/BookNoImage.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public LgBook(MainPanel mainPanel) {
        
        super(mainPanel, new FrontCover(NO_IMAGE), new BookBackCover(), new SpineCover());
    }
    
    
    @Override
    protected float getThickness(Item item) {
        
        
        // The size of the back cover is decided. 
        // Thickness a page is assumed to be 0.005cm. 
        float width; 
        
        if (item.getItemAttributes().getNumberOfPages() != null) {  
            width = (item.getItemAttributes().getNumberOfPages().intValue() * 0.005f) / 100.0f;
        }
        else {
            width = toolkit.widthNativeToPhysical(DEFAULT_THICKNESS);
        }
        if (toolkit.widthPhysicalToNative(width) > MAX_THICKNESS) {
            width = toolkit.widthNativeToPhysical(MAX_THICKNESS);
        }
        else if (toolkit.widthPhysicalToNative(width) < MIN_THICKNESS) {
            width = toolkit.widthNativeToPhysical(MIN_THICKNESS);            
        }
        
        
        return width;
    }
}


class BookBackCover extends BackCover {
    
    BookBackCover() {
        super(new BookInfoPanel(Product.createWhiteAppearance()));
    }
    
    
    @Override
    protected void setInfo(
    Component3D info, Item item, float width, float height, 
    float red, float green, float blue, float alpha) {
        
        BookInfoPanel bookInfo = (BookInfoPanel) info; 
        
        bookInfo.setInfo(item, width, height, red, green, blue, alpha);
    }
    
}
