/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: ImageViewer.java,v $
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


import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgscope.util.LgUtil;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;

/**
 * Image viewer is defined.
 */
public class ImageViewer extends LgViewer {

    /**
     * The image is constructed. 
     * @param file Displayed file.
     */
    public ImageViewer(File file) {
        super(file);
    }
    
    protected Component3D createViewer(File file) {
        
        try {                
            
            BufferedImage image = ImageIO.read(file);
                
            TextureLoader loader = new TextureLoader(image);
            Texture texture =  loader.getTexture();

            // Appearance is generated. 
            Appearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                            SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
                            
            appearance.setTexture(texture);     

            float width = LgUtil.pixel2meter(image.getWidth()) / 3.0f;
            float height = LgUtil.pixel2meter(image.getHeight()) / 3.0f;
            
            FuzzyEdgePanel panel = new FuzzyEdgePanel(
                width, height, 0.0001f, 
                appearance);

            Component3D comp = new Component3D();            
            comp.addChild(panel);
            comp.setPreferredSize(new Vector3f(width, height, 0.0f));
            
            return comp;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    /**
     * The same component as the createThumbnail() is returned. 
     */
    protected Component3D createThumbnail(File file) {
        
        Component3D c = createViewer(file);        
        
        Vector3f v = LgUtil.getSize(c);        
        if (v.x > LgUtil.pixel2meter(100) || v.y > LgUtil.pixel2meter(100)) {
            c.setScale(0.15f);
        }
        
        return c;
    }
}
