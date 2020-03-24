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
 * TabbedDesktopPlugin.java
 *
 * Created on 12 de junio de 2006, 14:51
 *
 * $Revision: 1.8 $
 * $Date: 2006-08-27 10:32:45 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop.tabbed;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import org.jdesktop.lg3d.apps.utils.freedesktop.DesktopPluginInterface;
import org.jdesktop.lg3d.apps.utils.freedesktop.IconFinder;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.DesktopEntry;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.DirectoryDesktopEntry;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.FileDesktopEntry;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.MimeDesktopEntry;
import org.jdesktop.lg3d.scenemanager.utils.event.ScreenResolutionChangedEvent;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.c3danimation.ScaleAndRotateChangeVisiblePlugin;
import org.jdesktop.lg3d.utils.c3danimation.TransparencyChangeVisiblePlugin;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 *
 * @author opsi
 */
public class TabbedDesktopPlugin implements DesktopPluginInterface, LgEventListener{
    private Logger logger;
    private Container3D top;
    private Container3D iconsContainer;
    private Container3D shortcutsContainer;
    
    private Component3D panelComp;
    
    private List<Container3D> tabIconsContainers;
    private Map<TabShortcut,Container3D> shortcut2tab;
    
    private SimpleAppearance panelApp;
    private final static float maxScalingFactor = 0.9f;
    private final static float minScalingFactor = 0.1f;
    private final static float widthScaleAdj = 0.8f;
    private final static float heightScaleAdj = 0.9f;
    private final static int NUM_ROWS = 9;
    private final static int NUM_COLS = 8;
    private float screenWidth;
    private float screenHeight;
    
    private final static long MOUSE_THRESHOLD = 2500;
    private long lastMouseEntered = 0;
    private boolean minimized = false;
    /** Creates a new instance of TabbedDesktopPlugin */
    public TabbedDesktopPlugin() {
        logger = Logger.getLogger("lg.desktop");
        top = new Container3D();
        top.setVisible(false);
        LgEventConnector.getLgEventConnector().addListener(LgEventSource.ALL_SOURCES,this);
    }
    
    private String [] getFoldersToTab() {
        return new String [] {System.getProperty("user.home")+"/Desktop"};
    }
    private void addFolderAsTab(File folder) {
        Container3D tabIconsContainer = new Container3D();
        tabIconsContainers.add(tabIconsContainer);
        float width = screenWidth*maxScalingFactor*widthScaleAdj;
        float height = screenHeight*maxScalingFactor*heightScaleAdj;
        
        tabIconsContainer.setLayout(new InfinityMatrixLayout(width,height,NUM_ROWS,NUM_COLS,new DefaultSorter()));
        tabIconsContainer.setAnimation(new NaturalMotionAnimation(2000,new ScaleAndRotateChangeVisiblePlugin(2000)));
        tabIconsContainer.changeVisible(false,0);
        tabIconsContainer.setTranslation(-width/2,height/2,0);
        iconsContainer.addChild(tabIconsContainer);
        File[] files = folder.listFiles();
        DesktopEntry entry;
        URL imageURL;
        for(File file:files) {
            try {
                if(!file.canRead()){//folder_locked
                    entry = new MimeDesktopEntry(file);
                    imageURL = IconFinder.getInstance().findIconFor("folder_locked", 64);
                    if(imageURL!=null)
                        tabIconsContainer.addChild(new DesktopIcon(entry,imageURL));
                } else if(file.getName().endsWith(".desktop")) {
                    entry = new FileDesktopEntry(file);
                    if(entry.getIconName()!=null) {
                        imageURL = IconFinder.getInstance().findIconFor(entry.getIconName(), 64);
                        if(imageURL!=null)
                            tabIconsContainer.addChild(new DesktopIcon(entry,imageURL));
                    }
                } else if(file.isDirectory()) {
                    entry = new DirectoryDesktopEntry(file);
                    imageURL = IconFinder.getInstance().findIconFor(entry.getIconName(),  64);
                    if(imageURL!=null)
                        tabIconsContainer.addChild(new DesktopIcon(entry,imageURL));
                } else {
                    entry = new MimeDesktopEntry(file);
                    imageURL = IconFinder.getInstance().findIconForMime(entry.getIconName(), 64);
                    if(imageURL!=null)
                        tabIconsContainer.addChild(new DesktopIcon(entry,imageURL));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        final TabShortcut shortcut = new TabShortcut(folder.getName(),(screenWidth*maxScalingFactor)/getFoldersToTab().length,screenHeight/20);
        shortcut.addListener(new MouseClickedEventAdapter(false,new ActionNoArg() {
            public void performAction(LgEventSource source) {
                showAssociatedTab(shortcut);
            }
        }));
        shortcutsContainer.addChild(shortcut);
        shortcut2tab.put(shortcut,tabIconsContainer);
        
        if(tabIconsContainers.size()==1)
            showAssociatedTab(shortcut);
    }
    private void showAssociatedTab(TabShortcut shortcut) {
        for(TabShortcut scut : shortcut2tab.keySet()) {
            scut.highlight(scut == shortcut);
            shortcut2tab.get(scut).changeVisible(scut == shortcut);
        }
    }
    public void populateDesktop() {
        screenWidth = Toolkit3D.getToolkit3D().getScreenWidth();
        screenHeight = Toolkit3D.getToolkit3D().getScreenHeight();
        
        tabIconsContainers = new LinkedList<Container3D>();
        shortcut2tab = new Hashtable<TabShortcut,Container3D>();
        
        panelApp = new SimpleAppearance(0.4f,0.4f,0.0f,1.0f);
        panelComp = new Component3D();
        GlassyPanel panel = new GlassyPanel(screenWidth*maxScalingFactor,screenHeight*maxScalingFactor,0.001f,panelApp);
        BranchGroup pgroup = new BranchGroup();
        pgroup.setCapability(BranchGroup.ALLOW_DETACH);
        pgroup.addChild(panel);
        panelComp.addChild(pgroup);
        panelComp.setTranslation(0,0,-0.01f);
        top.addChild(panelComp);
        
        top.setAnimation(
                new NaturalMotionAnimation((int)MOUSE_THRESHOLD,
                new TransparencyChangeVisiblePlugin((int)MOUSE_THRESHOLD)));
        top.setScale(0);
        //Using this listener the plugin grows/shrinks on mouse click in the panel
//        panelComp.addListener(new MouseClickedEventAdapter(new ActionNoArg(){
//            public void performAction(LgEventSource source /*, boolean state*/) {
//                mouseEntered(minimized,false);
//            }
//        }));
        
//        Using this listener the plugin grows/shrink inmediatly on mousehover
        top.addListener(new MouseEnteredEventAdapter(new ActionBoolean() {
            public void performAction(LgEventSource source, boolean state) {
                mouseEntered(state,false);
            }
        }));
        
        iconsContainer = new Container3D();
        top.addChild(iconsContainer);
        
        shortcutsContainer = new Container3D();
        shortcutsContainer.setLayout(new MyHorizontalReorderableLayout(MyHorizontalLayout.AlignmentType.CENTER,screenWidth*maxScalingFactor/getFoldersToTab().length));
        shortcutsContainer.setTranslation(screenWidth*maxScalingFactor/getFoldersToTab().length,screenHeight*maxScalingFactor/2+screenHeight/40,0);
        
        mouseEntered(false,  false);
        
        top.addChild(shortcutsContainer);
        for(String folder : getFoldersToTab()) {
            File dir = new File(folder);
            if(dir.isDirectory() && dir.canRead())
                addFolderAsTab(dir);
        }
        top.changeVisible(true);
    }
    
    public Component3D getDesktopRoot() {
        return top;
    }
    
    public void fitToScreen(ScreenResolutionChangedEvent event) {
        screenWidth = event.getWidth();
        screenHeight = event.getHeight();
        float width = screenWidth*maxScalingFactor*widthScaleAdj;
        float height = screenHeight*maxScalingFactor*heightScaleAdj;
        
        panelComp.removeChild(0);
        GlassyPanel panel = new GlassyPanel(screenWidth *maxScalingFactor,screenHeight*maxScalingFactor,0.001f,panelApp);
        BranchGroup pgroup = new BranchGroup();
        pgroup.setCapability(BranchGroup.ALLOW_DETACH);
        pgroup.addChild(panel);
        panelComp.addChild(pgroup);
        for(Container3D ctab :tabIconsContainers) {
            ((InfinityMatrixLayout)ctab.getLayout()).setSize(width,height);
            ctab.getLayout().layoutContainer();
            ctab.setTranslation(-width/2,height/2,0);
        }
        
        ((MyHorizontalLayout)shortcutsContainer.getLayout()).setSpacing(screenWidth*maxScalingFactor/getFoldersToTab().length);
        shortcutsContainer.setTranslation(screenWidth*maxScalingFactor/getFoldersToTab().length,screenHeight*maxScalingFactor/2+screenHeight/40,0);
        for(TabShortcut scut : shortcut2tab.keySet()) {
            scut.update(screenWidth*maxScalingFactor/getFoldersToTab().length);
        }
        mouseEntered(!minimized,  true);
    }
    
    private void mouseEntered(boolean state,  boolean forceChange) {
        if((System.currentTimeMillis()-lastMouseEntered) < MOUSE_THRESHOLD && !forceChange)
            return;
        if(state && minimized || forceChange && state) {
            top.setRotationAxis(0.5f,0.5f,0.0f);
            top.changeRotationAngle((float)Math.toRadians(360));
            top.changeScale(0.8f);
            top.changeTranslation(0,0,0.1f);
            top.changeTransparency(0.0f);
            minimized = false;
            if(!forceChange)
                lastMouseEntered=System.currentTimeMillis();
        } else if(!state && !minimized || forceChange && !state) {
            float width = (screenWidth*maxScalingFactor)/2;
            float height = (screenHeight*maxScalingFactor)/2;
            
            top.setRotationAxis(0.5f,0.5f,0.0f);
            top.changeRotationAngle(0);
            top.changeScale(minScalingFactor);
            top.changeTranslation(-width,height,0);
            top.changeTransparency(0.7f);
            minimized = true;
            if(!forceChange)
                lastMouseEntered=System.currentTimeMillis();
        }
    }
    
    public void processEvent(LgEvent evt) {
        mouseEntered(false,true);
    }
    
    public Class[] getTargetEventClasses() {
        return new Class[] {HideTabbedPanelEvent.class};
    }
    
    public void switchVisible() {
        top.changeVisible(!top.isVisible());
    }
}
class DefaultSorter implements Comparator<Component3D>, Serializable {
    public int compare(Component3D o1, Component3D o2) {
        if(o1 instanceof DesktopIcon && o2 instanceof DesktopIcon) {
            DesktopIcon e1 = (DesktopIcon)o1;
            DesktopIcon e2 = (DesktopIcon)o2;
            return e1.entry.getName(Locale.getDefault()).compareTo(e2.entry.getName(Locale.getDefault()));
        } else
            throw new IllegalArgumentException("Only DesktopIcon's allowed for this comparator");
    }
}
