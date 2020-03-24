/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: RandomLayout.java,v $
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
 * $Date: 2006-04-17 14:05:23 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgscope;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Component3D;

/**
 * A random layout is defined. 
 */
public class RandomLayout implements Layout 
{
    private int animationSpeed = 500; /* ms */

    public String getName() {
        return "Random";
    }
    
    public void layout(
    Component3D[] list, float screenWidth, float screenHeight, 
    Vector3f pos, Vector3f abs) {

        for (int i=0; i < list.length; i++) {
            
            float x = getValue(0.1f);
            float y = getValue(0.1f);
            float z = getValue(0.5f);
            
            float rx = getValue(6.28f);
            float ry = getValue(6.28f);
            float rz = getValue(6.28f);
                        
            list[i].changeTranslation(x, y, z, animationSpeed);     
            list[i].changeRotationAxis(rx, ry, rz, animationSpeed);
            list[i].changeRotationAngle(getValue(6.28f), animationSpeed);    // 3.14 = 180 degrees 
            
            /*
            list[i].setTranslation(x, y, z);     
            list[i].setRotationAxis(rx, ry, rz);
            list[i].setRotationAngle(getValue(6.28f));
            */
        }   
    }
    
    
    private float getValue(float limit) {
        
        float f = (float) (Math.random() % limit);
        
        if ( (int) (f * 100) % 2 == 0) {
            f *= -1.0f;
        }
        return f;
    }    
}
