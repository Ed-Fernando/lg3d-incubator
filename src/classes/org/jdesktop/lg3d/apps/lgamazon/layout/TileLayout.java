/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: TileLayout.java,v $
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
 * $Date: 2007-03-09 09:39:18 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.layout;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgamazon.Layout;
import org.jdesktop.lg3d.apps.lgamazon.component.Product;
import org.jdesktop.lg3d.apps.lgamazon.util.LgUtil;
import org.jdesktop.lg3d.wg.Toolkit3D;



public class TileLayout implements Layout {
    
    private float angle;
    private float defaultScale;
    
    public TileLayout(float defaultScale, float angle) {
        
        this.defaultScale = defaultScale;
        this.angle = angle;
    }

    public void layout(Product[] products, int count) {

        float margin = 0.01f * defaultScale;        
                
        float y = 0.0f;
        
        float x = 0.0f;
        float maxHeight = 0.0f;
        
        float screenWidth = Toolkit3D.getToolkit3D().getScreenWidth();        
                        
        for (int i = 0; i < products.length; i++) {    
             
            if (i >= count) {
                break;
            }
            
            Vector3f size = LgUtil.getSize(products[i]);
            
            x += size.z + margin;
                        
            if (maxHeight < size.y) {
                maxHeight = size.y;
            }
        }
        x -= margin;    
        x *= (float) Math.abs(Math.sin(angle)) * defaultScale;
        
        x = -(x / 2.0f);    
        

        Vector3f axis = new Vector3f(0.0f, 1.0f, 0.0f);        
        
        for (int i = 0; i < products.length; i++) {
            
            if (i >= count) {
                products[i].changeVisible(false);
                continue;
            }
            
            products[i].changeVisible(true);
            
            Vector3f size = LgUtil.getSize(products[i]);            
            float half = ((size.z * (float) Math.abs(Math.sin(angle))) / 2.0f) * defaultScale;   
            float gy = ((maxHeight - size.y) / 2.0f) * defaultScale;   
            
            Vector3f pos = new Vector3f(x + half, y - gy, 0.0f);
            
            products[i].setHome(pos, defaultScale, axis, angle);
            products[i].goHome();
            
            x += (half * 2.0f) + margin;
        }
        
    }

}
