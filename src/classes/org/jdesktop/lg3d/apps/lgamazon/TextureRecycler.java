/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: TextureRecycler.java,v $
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
 * $Date: 2007-03-09 09:38:42 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon;

import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.Vector;

import org.jdesktop.lg3d.apps.lgamazon.util.ImageTexture;


/**
 * Texture is recycled class. 
 */
public class TextureRecycler {

    private static Hashtable<String, Vector> table = new Hashtable<String, Vector>();
    
    
    /**
     * Texture is newly taken out. 
     * 
     * @param width
     * @param height
     * @return
     */
    public static ImageTexture createImage(int width, int height) {
        
        Vector<ImageTexture> v = table.get(createKey(width, height));
        
        if (v == null) {
            return new ImageTexture(
                    new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
        }
        
        synchronized (v) {
            
            if (v.size() == 0) {
                
                return new ImageTexture(
                        new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
            }
            else {
                ImageTexture texture = v.get(0);
                v.remove(0);
                return texture;
            }
        }
    }
    
    
    /**
     * Texture which finished using it is turned to recycling. 
     * 
     * @param texture
     */
    public static void recycle(ImageTexture texture) {
        
        String key = createKey(texture.image.getWidth(), texture.image.getHeight());
        Vector<ImageTexture> v = table.get(key);
        
        if (v == null) {
            v = new Vector<ImageTexture>();
            table.put(key, v);
        }
        
        synchronized (v) {            
            v.add(texture);
        }
    }
    
    
    private static String createKey(int width, int height) {
        return width + "x" + height;
    }
    
    
//    public static BufferedImage createImage(int width, int height) {
//        
//        Vector v = (Vector) table.get(createKey(width, height));
//        
//        synchronized (v) {
//            
//            if (v == null || v.size() == 0) {
//                return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//            }
//            else {
//                BufferedImage image = (BufferedImage) v.get(0);
//                v.remove(0);
//                return image;
//            }
//        }
//    }
//    
//    public static void recycle(BufferedImage image) {
//        
//        Vector v = (Vector) table.get(createKey(image.getWidth(), image.getHeight()));
//        
//        synchronized (v) {
//            
//            if (v == null) {
//                v = new Vector();
//                table.put(createKey(image.getWidth(), image.getHeight()), v);
//            }
//            
//            v.add(image);
//        }
//    }
}
