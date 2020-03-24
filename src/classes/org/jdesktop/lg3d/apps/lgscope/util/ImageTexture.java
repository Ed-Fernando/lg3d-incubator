/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: ImageTexture.java,v $
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
 * $Revision: 1.2 $
 * $Date: 2006-06-05 21:31:41 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgscope.util;


import java.awt.image.BufferedImage;

import org.jdesktop.lg3d.sg.ImageComponent2D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.Texture2D;


/**
 * Put image on texture easily.
 */
public class ImageTexture extends Texture2D {

    public final BufferedImage image;
    private ImageComponent2D imageComponent;
    

    /**
     * The image is constructed specifying it. 
     * The image size should be an involution of two. 
     * 
     */
    public ImageTexture(BufferedImage image) {
        
        super(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
        
        this.image = image;
                    
        setCapability(Texture2D.ALLOW_IMAGE_READ);
        setCapability(Texture2D.ALLOW_IMAGE_WRITE);
        setMinFilter(Texture2D.BASE_LEVEL_LINEAR);
        setMagFilter(Texture2D.BASE_LEVEL_LINEAR);
        // It is Texture.RGBA and there is a transparency. 
        
        // ImageComponent is generated. 
        imageComponent = new ImageComponent2D(ImageComponent2D.FORMAT_RGBA, image, true, true);
        imageComponent.setCapability(ImageComponent2D.ALLOW_IMAGE_WRITE);
        
        updateImage();
    }


    /**
     * The image of the texture is updated. 
     */
    public void updateImage() {         
        
        imageComponent.set(image);
        
        setImage(0, imageComponent);   
    }
    
    
    /**
     * A necessary size of Image put on the texture is calculated. 
     * 
     * 
     * @param size Demanded size. 
     * @return Necessary size.
     */
    public static int getTextureImageSize(int size) {
        
        for (int i = 1; ; i *= 2) {
            
            if (i > size) {
                
                int m1 = size - (i / 2);
                int m2 = i - size; 
                
                return (m1 > m2) ? i : (i / 2); 
            }
        }
    }    
}
