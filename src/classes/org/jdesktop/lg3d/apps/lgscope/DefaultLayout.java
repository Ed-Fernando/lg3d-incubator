/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: DefaultLayout.java,v $
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
 * $Date: 2006-04-17 14:05:22 $
 * Author: E_INOUE
 */
 
package org.jdesktop.lg3d.apps.lgscope;


import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgscope.util.LgUtil;
import org.jdesktop.lg3d.wg.Component3D;


/**
 * Standard layout.
 */
public class DefaultLayout implements Layout {
    
    private int animationSpeed = 500; /* ms */
        
    public String getName() {
        return "Tile";
    }


    public void layout(
    Component3D[] list, float screenWidth, float screenHeight, 
    Vector3f pos, Vector3f abs) {

        // Margin between components.           
        float margin = 0.001f;
        
        // Center coordinates of component. It can be said moved distance from the pos. 
        float rx = 0.0f; 
        float ry = 0.0f;
        float rz = 0.0f;
        
        // The display area of the file list of the screen is calculated. 
        float vw = (screenWidth / 2.0f) - abs.x;
        float vh = (screenHeight / 2.0f) + abs.y;
                
        // The maximum of the depth is calculated.
        float maxZ = 0.0f;
        
        for (int i = 0; i < list.length; i++) {
            
            Vector3f listSize = LgUtil.getSize(list[i]);
            
            ry -= (listSize.y / 2);
            
            // It is happy to do the animated cartoon. 
            list[i].changeRotationAngle(0.0f, animationSpeed);
            list[i].changeRotationAxis(0.0f, 0.0f, 0.0f, animationSpeed);
            
            list[i].changeTranslation(
                pos.x + rx + (listSize.x / 2), 
                pos.y + ry, 
                pos.z + rz - (listSize.z / 2),
                animationSpeed);
            // In changeTranslation(), center coordinates of the component are standards. 


            // The following position is calculated. 
            ry -= (listSize.y / 2) + margin;
                        
            // The depth is updated.
            if (maxZ < listSize.z) {
                maxZ = listSize.z;
            }
            
            // It moves it to the right when coming under the screen. 
            if ((Math.abs(ry) + listSize.y) > vh) {
                
                ry = 0.0f;
                rx += listSize.x + margin;
                
                // It moves it back when coming up to the right of the screen. 
                if ((rx + listSize.x) > vw) {                    
                    rz -= maxZ + margin;
                    rx = 0.0f;
                    maxZ = 0.0f;  
                }                
            }
        }
    } 
}


