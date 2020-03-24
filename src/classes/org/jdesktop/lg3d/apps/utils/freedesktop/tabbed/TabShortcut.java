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
 * TabShortcut.java
 *
 * Created on 19 de junio de 2006, 18:12
 *
 * $Revision: 1.4 $
 * $Date: 2006-08-17 23:22:19 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop.tabbed;

import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.scenemanager.utils.appcontainer.NaturalMotionWithSwayAnimation;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.sg.TransformGroup;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.GlassyText2D;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;

/**
 *
 * @author opsi
 */
public class TabShortcut extends Component3D {
    private BranchGroup detachableGroup;
    private GlassyPanel shortcutPanel;
    private SimpleAppearance panelApp;
    private String name;
    private float height;
    
    /** Creates a new instance of TabShortcut */
    public TabShortcut(String name,float width,float height) {
        setAnimation(new NaturalMotionWithSwayAnimation(1000));
        panelApp = new SimpleAppearance(0.5f,0.5f,0.5f,0.0f);
        panelApp.setCapability(SimpleAppearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
        update(name,width,height);
    }
    public void update(String name, float width, float height) {
        this.name = name;
        this.height = height;
        if(detachableGroup!=null) {
            removeChild(detachableGroup);
        }
        detachableGroup = new BranchGroup();
        detachableGroup.setCapability(BranchGroup.ALLOW_DETACH);
        
        GlassyText2D shortcutText = new GlassyText2D(name,width,height,new Color4f(0.5f,0.5f,0.5f,1.0f));
        
        Transform3D t3d = new Transform3D();
        t3d.setTranslation(new Vector3f(-width/2.2f,-height/2.5f,0));
        TransformGroup labeltg = new TransformGroup(t3d);
        labeltg.addChild(shortcutText);
        detachableGroup.addChild(labeltg);
        
        shortcutPanel = new GlassyPanel(width,height,0.01f,panelApp);
        shortcutPanel.setCapability(GlassyPanel.ALLOW_APPEARANCE_READ);
        detachableGroup.addChild(shortcutPanel);
        addChild(detachableGroup);
    }
    public void update(float width) {
        update(this.name,width,this.height);
    }
    public void highlight(boolean state) {
        setRotationAxis(1,0,0);
        if(state) {
            changeRotationAngle((float)Math.toRadians(360));
            shortcutPanel.getAppearance().setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.NICEST,0.0f));
        } else {
            changeRotationAngle(0);
            shortcutPanel.getAppearance().setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.NICEST,1.0f));
        }
    }
}
