/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: LgUtil.java,v $
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
 * $Date: 2007-03-09 09:43:43 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.util;

import javax.imageio.ImageIO;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Component3D;

/**
 * The method of utility is defined. 
 */
public class LgUtil {

    // For meter conversion  0.0254 [inch/m] / 72.0f [pixel/inch]  
    private static final float UNIT_TRANS_FACTOR = 0.0254f / 72.0f;
    //private static final float UNIT_TRANS_FACTOR = 0.0254f / 96.0f;

        
    /**
     * It converts it from the meter into the pixel. 
     * 
     */
    public static int meter2pixel(float f) {
        return (int) (f / UNIT_TRANS_FACTOR);
    }
    
    
    /**
     * It converts it from the pixel into the meter. 
     * 
     */
    public static float pixel2meter(int i) {
        return i * UNIT_TRANS_FACTOR;
    }    


    /**
     * Translation of the component is acquired. 
     */
    public static Vector3f getTranslation(Component3D c) {
        Vector3f v = new Vector3f();
        v = c.getTranslation(v);
        return v;
    }
    
    
    /**
     * Size of the component is acquired. 
     */
    public static Vector3f getSize(Component3D c) {
        Vector3f v = new Vector3f();
        v = c.getPreferredSize(v);
        return v;
    }
    
    
    /**
     * RotationAxis of the component is acquired.
     */
    public static Vector3f getRotationAxis(Component3D c) {
        Vector3f v = new Vector3f(); 
        v = c.getRotationAxis(v);
        return v;
    }
    
    final static String[] formatNames = ImageIO.getReaderFormatNames();

    /**
     * Whether the specified file can be read as an image is examined. 
     * 
     * @param filename Filename
     * @return true when it is possible to read.
     * @see ImageIO.getReaderFormatNames();
     */    
    public static boolean canReadImage(String filename) {
        
        int dot = filename.lastIndexOf('.');
        if (dot == -1) {
            return false;
        }
                
        String ext = filename.substring(dot + 1).toLowerCase();
        
        for(int i = 0; i < formatNames.length; i++) {
            if (formatNames[i].toLowerCase().equals(ext)) {
                return true;
            }
        }
        
        return false;
    }    
}
