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
 * DesktopPlugin.java
 *
 * Created on 5 de junio de 2006, 2:04
 *
 * $Revision: 1.9 $
 * $Date: 2006-08-27 10:32:45 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.jdesktop.lg3d.scenemanager.utils.SceneControl;
import org.jdesktop.lg3d.apps.utils.freedesktop.tabbed.TabbedDesktopPlugin;
import org.jdesktop.lg3d.scenemanager.utils.event.ScreenResolutionChangedEvent;
import org.jdesktop.lg3d.scenemanager.utils.plugin.SceneManagerPlugin;
import org.jdesktop.lg3d.scenemanager.utils.taskbar.TaskbarItemConfig;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 *
 * @author opsi
 */
public class DesktopPlugin implements SceneManagerPlugin,LgEventListener{
    private DesktopPluginInterface desktop;
    public static final ExecutorService executor =
            new ThreadPoolExecutor(3,3,2,TimeUnit.SECONDS,new ArrayBlockingQueue(6));
    /** Creates a new instance of DesktopPlugin */
    public DesktopPlugin() {
    }
    
    public void initialize(SceneControl sceneControl) {
        if(System.getProperty("os.name").equals("Linux")) {
            //TODO Get plugin class from some property
            desktop = new TabbedDesktopPlugin();            
            LgEventConnector.getLgEventConnector().addListener(LgEventSource.ALL_SOURCES,this);
            
            executor.execute(new Runnable() {
                public void run() {                                
                    desktop.populateDesktop();
//                    TaskbarItemConfig switchItemConf = new TaskbarItemConfig();   
//                    switchItemConf.setItemClass("org.jdesktop.lg3d.apps.utils.freedesktop.TaskbarSwitcher");
//                    switchItemConf.setItemIndex(Integer.MAX_VALUE);
//                    switchItemConf.doConfig();
                }
            });
//            executor.execute(new Runnable() {
//                public void run() {
//                    try {
//                        Thread.sleep(4000);
//                        new MenuLoader();
//                    } catch (Exception ex) {
//                        throw new RuntimeException(ex);
//                    }
//                }
//            });
        }
    }
    
    public void destroy() {
    }
    
    public boolean isRemovable() {
        return false;
    }
    
    public Component3D getPluginRoot() {
        if(System.getProperty("os.name").equals("Linux"))
            return desktop.getDesktopRoot();
        else
            return new Component3D();
    }

    public void processEvent(LgEvent evt) {
        if(evt instanceof ScreenResolutionChangedEvent) {            
            desktop.fitToScreen((ScreenResolutionChangedEvent)evt);
        } else if(evt instanceof ChangeDesktopVisibleEvent)
        {           
            desktop.switchVisible();
        }
        
    }

    public Class[] getTargetEventClasses() {
        return new Class[]{ChangeDesktopVisibleEvent.class,ScreenResolutionChangedEvent.class};
    }
}
