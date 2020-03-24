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
 * Fm3DMain.java
 *
 * @author John Maltby
 *
 * Use -Xmx512m in Java command line to increase heap memory, otherwise
 * you will run out of memory with large directories 
 * 
 * PROBLEMS
 * Desktop object is Frame3D so shows as icon on taskbar
 * It can thus be closed from there, disabling the desktop
 * Similary, when all windows are closed, desktop icon remains
 * Need some way of closing all windows when desktop is closed and vice-versa
 * (for now, I've just disabled the icon for the desktiop on the taskbar)
 *
 * Program occasionally crashes on the following errors:
 *
 * On start-up, if window maximize is attempted during initilisation:
 *    Exception in thread "main" java.lang.RuntimeException: ERROR - Illegal change of NodeID
 * 
 * Occasionally on attempted pick of control bar icon:
 *    SEVERE: Exception caught in PickEngine: java.lang.NullPointerException
 */

import java.awt.Color;
import java.io.File;
import java.util.*;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import javax.vecmath.Vector3f;

/** Main class */
public class Fm3DMain {
    private LinkedList<Fm3DWindow> windowList = new LinkedList<Fm3DWindow>();
    private LinkedList<String> pathList = new LinkedList<String>();
    private Color textColour = Color.BLACK; 
    private boolean billboard = true;
    private boolean transparent = true;
    private boolean path = false;
    private boolean sticky = true;
    private Frame3D desktop;
    private Frame3D helpScreen;
    private Properties props;
    private File activeDir;
    private Fm3DWindow target;
        
    /** Creates a new window and makes it the active directory */
    private void openWindow(File activeDir) {
        Fm3DWindow newWindow = new Fm3DWindow(this, desktop, activeDir, textColour, billboard, transparent, path, sticky);
        newWindow.changeVisible(true);
        newWindow.changeEnabled(true);
        windowList.addLast(newWindow);
        pathList.addLast(activeDir.getAbsolutePath());
    }
    
    /** Find a window */
    private Fm3DWindow findWindow(File dir) {
        ListIterator iterator = pathList.listIterator();
        while(iterator.hasNext()) {
            if(dir.getAbsolutePath().equals((String)iterator.next())) {
                return (Fm3DWindow)windowList.get(iterator.previousIndex());
            }
        }
        return null;
    }
    
    /** Check to see if a window has been opened */
    private boolean isWindowOpen(File dir) {
        if(pathList.indexOf(dir.getAbsolutePath()) != -1)
            return true;
        else
            return false;
    }

    /** Make the window of the passed file active */
    private void makeWindowActive(File file) {
        Fm3DWindow activeWindow = findWindow(file);
        // now make window active
        if(activeWindow != null) { 
            activeWindow.setPanelAppearance(true);
            setActiveWindow(activeWindow);
            activeWindow.resetPosition(500);
        }
    }      
        
    /** Creates a new instance of fm3d */
    public Fm3DMain() {
        desktop = new Frame3D();
        desktop.setThumbnail(null);
        desktop.changeVisible(true);
        desktop.changeEnabled(true);
        props = System.getProperties();
        activeDir = new File(props.getProperty("user.dir"));
        openWindow(activeDir);
    }
    
    /** Reset default appearence of all non-active windows */
    public void resetWindowAppearances(Fm3DWindow nonActiveWindow) {
        Fm3DWindow window;
        
        ListIterator iterator = windowList.listIterator();
        while(iterator.hasNext()) {
            if((window = (Fm3DWindow)iterator.next()) != nonActiveWindow) {
                window.setPanelAppearance(false);
            }
        }
    }
    
    public void changeDirUp() {
        if(!sticky && activeDir.getParentFile() != null) 
            closeWindow(false);        
        if(activeDir.getParentFile() != null) {
            if(!isWindowOpen(activeDir.getParentFile())) {
                activeDir = activeDir.getParentFile();
                openWindow(activeDir);
            } else
                makeWindowActive(activeDir.getParentFile());
        }
    }
    
    public void changeDirDown(File file) {
        if(!sticky) 
            closeWindow(false);
        if(!isWindowOpen(file))
            openWindow(file);
        else
            makeWindowActive(file);
    }

    public void scrollIn() {
        Fm3DWindow activeWindow = findWindow(activeDir);
        activeWindow.scrollIn();
    }    

    public void scrollOut() {
        Fm3DWindow activeWindow = findWindow(activeDir);
        activeWindow.scrollOut();      
    }  
    
    /** Closes current active window */
    public void closeWindow(boolean directClose) {
        Fm3DWindow activeWindow = findWindow(activeDir);
        // remove from lists
        windowList.remove(activeWindow);
        pathList.remove(activeDir.getAbsolutePath());
        // terminate
        activeWindow.changeEnabled(false);
        // if last window and directly closed from close icon, also terminate desktop
        if(windowList.isEmpty() & directClose)
            desktop.changeEnabled(false);            
    }
        
    public void displayHelp() {
        helpScreen = new HelpWindow();
        helpScreen.changeVisible(true);
        helpScreen.changeEnabled(true);
    }
    
    public void setActiveWindow(Fm3DWindow window) {
        activeDir = window.getThisDir();
        resetWindowAppearances(window);
    }
    
    public void setBillboard(boolean bill) {
        billboard = bill;
    }  
    
    public void setTransparent(boolean tran) {
        transparent = tran;
    }

    public void setPath(boolean pth) {
        path = pth;
        Fm3DWindow activeWindow = findWindow(activeDir);
        activeWindow.togglePathText();
    }    
    
    public void setSticky(boolean stick) {
        sticky = stick;
    }    
    
    public void setTextColour(Color color) {
        textColour = color;
    }  
    
    public Fm3DWindow findTargetWindow(Fm3DWindow source, float icon_x, float icon_y) {
        Fm3DWindow target;
        Vector3f targetPos = new Vector3f();        
        ListIterator iterator = windowList.listIterator();
                 
        float centre_x, centre_y, left, right, top, bottom, width, height;
        final float scr_d = 0.40f; // perspective in screen transformation
        while(iterator.hasNext()) {
            target = (Fm3DWindow)iterator.next();
            targetPos = target.getFinalTranslation(targetPos); // world coords

            width = target.getWidth()*(float)Math.cos(Math.toRadians(target.getRotationY()))*scr_d/(scr_d-targetPos.z);
            height = target.getHeight()*scr_d/(scr_d-targetPos.z);
            centre_x = targetPos.x*scr_d/(scr_d-targetPos.z);
            centre_y = targetPos.y*scr_d/(scr_d-targetPos.z);
            
            left = centre_x - width/2; 
            right = centre_x + width/2; 
            top = centre_y + height/2;
            bottom = centre_y - height/2;

            if(target != source & icon_x > left & icon_x < right & icon_y > bottom & icon_y < top) {
                return target;
            }
        }
        return null; 
    }
    
    public static void main(String[] args) {
        new Fm3DMain();
    }
}






