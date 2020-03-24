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
 *   Copyright (C) 2006 by Juan Gonzalez (juan@aga-system.com)             *
 *                                                                         *
 ***************************************************************************
 * StartMenuTapp.java
 *
 * Created on 9 de septiembre de 2006, 14:04
 *
 * $Revision: 1.2 $
 * $Date: 2006-09-11 15:54:30 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.clock;

import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.action.AppLaunchAction;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Tapp;

/**
 *
 * @author opsi
 */
public class StartMenuTapp extends Tapp{
    
    /** Creates a new instance of StartMenuTapp */
    public StartMenuTapp() {       
        Vector3f backPanelSize = new Vector3f(0.02f,0.01f,0.005f);
        Clock clock = new DigitalClock(backPanelSize.x,new SimpleAppearance(0.2f,0,0.6f));
        clock.setTranslation(0,0,backPanelSize.z);
        ClockContainer.scheduler.scheduleAtFixedRate(new ClockTimeAction(clock),0,1000);
        addChild(clock);
        Component3D panelComp = new Component3D();
        panelComp.addChild(new GlassyPanel(backPanelSize.x,backPanelSize.y,backPanelSize.z,new SimpleAppearance(0.5f,0.5f,0)));
        addChild(panelComp);
        setPreferredSize(backPanelSize);
        setRotationAxis(-backPanelSize.x,backPanelSize.y,0);
        setRotationAngle((float)Math.toRadians(10));             
    }
    
}
