/*
 * Copyright (c) 2005, 2006 John Maltby
 *
 * Portions of code based upon:
 * Ls3D copyright (c) 2005 ENDO Yasuyuki
 * PingPong copyright (c) 2004, Johann Glaser
 * Folder and file images Ls3D copyright (c) 2005 ENDO Yasuyuki 
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.jdesktop.lg3d.apps.fm3d;

/**
 * IconAppearance.java
 *
 * @author John Maltby
 */

import org.jdesktop.lg3d.utils.shape.*;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.TransparencyAttributes;

public class IconAppearance extends SimpleAppearance {
    private TransparencyAttributes tranAttr;
    
    public IconAppearance(String textureFilename, boolean on, boolean color) {
        super(1.0f, 1.0f, 1.0f, 1.0f, SimpleAppearance.DISABLE_CULLING | SimpleAppearance.ENABLE_TEXTURE);
        
        /*
        setCapability(TransparencyAttributes.ALLOW_BLEND_FUNCTION_WRITE |
            TransparencyAttributes.ALLOW_MODE_WRITE |
            TransparencyAttributes.ALLOW_VALUE_WRITE);

        tranAttr = new TransparencyAttributes(
            TransparencyAttributes.BLENDED, 1.0f,
            TransparencyAttributes.BLEND_SRC_ALPHA,
            TransparencyAttributes.BLEND_ONE_MINUS_SRC_ALPHA);
        
        //tranAttr = new TransparencyAttributes(TransparencyAttributes.SCREEN_DOOR, 0.0f);        
        setTransparencyAttributes(tranAttr);
        */
        
        if (color) {
            if (on) {
                setColor(1.0f, 0.6f, 0.6f, 0.8f);
            } else {
                setColor(0.6f, 1.0f, 0.6f, 0.6f);
            }
        }
        try {
            setTexture(getClass().getClassLoader().getResource(textureFilename));
        } catch (Exception e) {
            throw new RuntimeException("failed to load texture: " + e);
        }
    }
    
    /*
    public void setIconTransparency(float transparency) {
        tranAttr.setTransparency(transparency);
        setTransparencyAttributes(tranAttr);        
    }
    */
}

