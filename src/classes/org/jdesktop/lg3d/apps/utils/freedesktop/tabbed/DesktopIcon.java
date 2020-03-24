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
 * DesktopIcon.java
 *
 * Created on 27 de mayo de 2006, 1:41
 *
 * $Revision: 1.6 $
 * $Date: 2006-08-27 10:38:10 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop.tabbed;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import org.jdesktop.lg3d.scenemanager.utils.appcontainer.NaturalMotionWithSwayAnimation;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.DesktopEntry;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.c3danimation.ScaleAndRotateChangeVisiblePlugin;
import org.jdesktop.lg3d.utils.c3danimation.ScaleChangeVisiblePlugin;
import org.jdesktop.lg3d.utils.component.Pseudo3DIcon;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 *
 * @author opsi
 */
public class DesktopIcon extends Container3D{
    private Pseudo3DIcon icon3d;
    protected DesktopEntry entry;
    private DesktopIconLabel label;
    
    /** Creates a new instance of DesktopIcon */
    public DesktopIcon(DesktopEntry entry,URL url) throws IOException{
        this.entry = entry;
        
        try {
            icon3d = new Pseudo3DIcon(url);
        } catch(Exception npe) {
            icon3d = new Pseudo3DIcon(new File(System.getProperty("lg.resourcedir")+"/images/icon/lg3d-logo.png").toURI().toURL());
        }
        icon3d.setAnimation(new NaturalMotionAnimation(2000,new ScaleChangeVisiblePlugin(1000)));
        addChild(icon3d);
        icon3d.changeVisible(true,0);
        
        String iconName = entry.getName(Locale.getDefault());
        label = new DesktopIconLabel(iconName);
        addChild(label);
        
        icon3d.addListener(
                new MouseEnteredEventAdapter(new ActionBoolean() {
            public void performAction(LgEventSource source, boolean state) {
                showLabel(state);
            }
        }));
        icon3d.addListener(
                new MouseClickedEventAdapter(false,new ActionNoArg() {
            public void performAction(LgEventSource source) {
                dblClick();
            }
        }));
        
        setAnimation(new NaturalMotionWithSwayAnimation(2000,new ScaleAndRotateChangeVisiblePlugin(2000)));
        showLabel(false);
    }
    private void showLabel(boolean show) {
        label.showLabel(show);
    }
    private void dblClick() {
        logger.info("Performing action");
        entry.performAction();
        LgEventConnector.getLgEventConnector().postEvent(new HideTabbedPanelEvent(),this);
    }
}
