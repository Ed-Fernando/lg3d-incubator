/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 *                                                                         *
 *   Copyright (C) 2006                                                    * 
 *                  Juan Gonzalez (juan@aga-system.com)                    *
 *                & Sun Microsystems                                       *
 *                                                                         * 
 ***************************************************************************
 * DesktopIconLabel.java
 *
 * Created on 1 de julio de 2006, 1:00
 *
 * $Revision: 1.5 $
 * $Date: 2006-08-27 10:38:10 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop.tabbed;

import java.awt.Color;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.GlassyText2D;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.sg.TransformGroup;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.c3danimation.TransparencyChangeVisiblePlugin;
import org.jdesktop.lg3d.wg.Component3D;

/**
 *
 * @author opsi
 */
public class DesktopIconLabel extends Component3D {
    private static final float TEXT_HEIGHT = 0.006f;
    //private GlassyText2D label;
    //private Component3D labelComp;
    //private GlassyPanel cyl;
    //private String fullIconName;
    private Component3D fullLabelComp;
    private Component3D smallLabelComp;
    /** Creates a new instance of DesktopIconLabel */
    public DesktopIconLabel(String iconName) {
        fullLabelComp = genLabel(iconName);
        if(iconName.length()>8)
            smallLabelComp = genLabel(iconName.substring(0,8) + "...");
        else
            smallLabelComp = genLabel(iconName);
        
        smallLabelComp.setAnimation(new NaturalMotionAnimation(500,new TransparencyChangeVisiblePlugin(1000)));
        smallLabelComp.changeVisible(false,0); 
        smallLabelComp.setRotationAxis(0,1,0);
        smallLabelComp.setRotationAngle((float)Math.toRadians(15));
        addChild(smallLabelComp);
        
        fullLabelComp.setAnimation(new NaturalMotionAnimation(500,new TransparencyChangeVisiblePlugin(1000)));
        fullLabelComp.changeVisible(false,0);        
        addChild(fullLabelComp);
    }
    private Component3D genLabel(String text) {
        GlassyText2D label = new GlassyText2D(
                text,
                text.length(),
                TEXT_HEIGHT,
                new Color4f(Color.WHITE),
                GlassyText2D.LightDirection.BOTTOM_RIGHT,
                GlassyText2D.Alignment.CENTER,
                1.2f,
                false,
                new Color(0xE0FF78));        
        label.setCapability(label.ALLOW_APPEARANCE_READ);
        label.getAppearance().setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
        label.getAppearance().setCapability(Appearance.ALLOW_TEXTURE_READ);
        Transform3D t3d = new Transform3D();
        
        t3d.setTranslation(new Vector3f(0,-TEXT_HEIGHT*1.6f,0));
        TransformGroup tg = new TransformGroup(t3d);
        tg.addChild(label);
        
        Component3D labelComp = new Component3D();
        labelComp.addChild(tg);
                
        return labelComp;
    }
    protected void showLabel(boolean show) {
        if(show) {
            smallLabelComp.changeVisible(false,0);
            fullLabelComp.changeVisible(true);                                             
            fullLabelComp.changeTranslation(0,-TEXT_HEIGHT*1.5f,0.02f);            
        } else {            
            fullLabelComp.changeTranslation(0,0,0);
            fullLabelComp.changeVisible(false);            
            smallLabelComp.changeVisible(true);
        }
    }
}
