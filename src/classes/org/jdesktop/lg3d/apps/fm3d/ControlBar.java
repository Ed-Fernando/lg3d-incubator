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
 * ControlBar.java
 *
 * @author John Maltby
 */

import java.awt.*;
import org.jdesktop.lg3d.sg.*;
import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.wg.event.*;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.shape.*;
import org.jdesktop.lg3d.utils.action.*;
import org.jdesktop.lg3d.utils.eventadapter.*;

/** Control bar for mouse events */
public class ControlBar extends Component3D implements WindowConstants {
    private Fm3DMain fm3dMain;
    private Fm3DWindow parentWindow;
    private int barPointsWidth =  (int)(POINTS_PER_METRE * BAR_WIDTH);
    private int barPointsHeight = (int)(POINTS_PER_METRE * BAR_HEIGHT);
    private String dirName;
    private SimpleAppearance opaqueAppearance = new SimpleAppearance(0.75f,0.75f,0.75f,1.0f);
    private SimpleAppearance transparentAppearance = new SimpleAppearance(0.5f,0.5f,0.9f,0.4f);
    private SimpleAppearance activeOpaqueAppearance = new SimpleAppearance(0.75f,0.75f,0.9f,0.8f); 
    private SimpleAppearance activeTransparentAppearance = new SimpleAppearance(0.0f,0.0f,0.9f,0.8f);     
    private Component3D parent = new Component3D();
    private GlassyPanel controlPanel;
 
    private IconAppearance helpIconOffAppearance
            = new IconAppearance("resources/images/icon/help.png", false, true);
    private IconAppearance helpIconOnAppearance
            = new IconAppearance("resources/images/icon/help.png", true, true);
    private IconAppearance upDirIconOffAppearance
            = new IconAppearance("resources/images/icon/up_dir.png", false, true);
    private IconAppearance upDirIconOnAppearance
            = new IconAppearance("resources/images/icon/up_dir.png", true, true);
    private IconAppearance scrollOutIconOffAppearance
            = new IconAppearance("resources/images/icon/scroll_out.png", false, true);
    private IconAppearance scrollOutIconOnAppearance
            = new IconAppearance("resources/images/icon/scroll_out.png", true, true);
    private IconAppearance scrollInIconOffAppearance
            = new IconAppearance("resources/images/icon/scroll_in.png", false, true);
    private IconAppearance scrollInIconOnAppearance
            = new IconAppearance("resources/images/icon/scroll_in.png", true, true);
    private IconAppearance billboardOffIconOffAppearance
            = new IconAppearance("resources/images/icon/billboard_off.png", false, true);
    private IconAppearance billboardOffIconOnAppearance
            = new IconAppearance("resources/images/icon/billboard_off.png", true, true);
    private IconAppearance billboardOnIconOffAppearance
            = new IconAppearance("resources/images/icon/billboard_on.png", false, true);
    private IconAppearance billboardOnIconOnAppearance
            = new IconAppearance("resources/images/icon/billboard_on.png", true, true);    
    private IconAppearance transparencyOffIconOffAppearance
            = new IconAppearance("resources/images/icon/transparent_off.png", false, true);
    private IconAppearance transparencyOffIconOnAppearance
            = new IconAppearance("resources/images/icon/transparent_off.png", true, true);
    private IconAppearance transparencyOnIconOffAppearance
            = new IconAppearance("resources/images/icon/transparent_on.png", false, true);
    private IconAppearance transparencyOnIconOnAppearance
            = new IconAppearance("resources/images/icon/transparent_on.png", true, true);  
    private IconAppearance pathOffIconOffAppearance
            = new IconAppearance("resources/images/icon/path_off.png", false, true);
    private IconAppearance pathOffIconOnAppearance
            = new IconAppearance("resources/images/icon/path_off.png", true, true);
    private IconAppearance pathOnIconOffAppearance
            = new IconAppearance("resources/images/icon/path_on.png", false, true);
    private IconAppearance pathOnIconOnAppearance
            = new IconAppearance("resources/images/icon/path_on.png", true, true);      
    private IconAppearance colourWhiteIconOffAppearance
            = new IconAppearance("resources/images/icon/colour_white.png", false, true);
    private IconAppearance colourWhiteIconOnAppearance
            = new IconAppearance("resources/images/icon/colour_white.png", true, true);     
    private IconAppearance colourBlackIconOffAppearance
            = new IconAppearance("resources/images/icon/colour_black.png", false, true);
    private IconAppearance colourBlackIconOnAppearance
            = new IconAppearance("resources/images/icon/colour_black.png", true, true);     
    private IconAppearance colourRedIconOffAppearance
            = new IconAppearance("resources/images/icon/colour_red.png", false, true);
    private IconAppearance colourRedIconOnAppearance
            = new IconAppearance("resources/images/icon/colour_red.png", true, true);     
    private IconAppearance colourGreenIconOffAppearance
            = new IconAppearance("resources/images/icon/colour_green.png", false, true);
    private IconAppearance colourGreenIconOnAppearance
            = new IconAppearance("resources/images/icon/colour_green.png", true, true);     
    private IconAppearance colourBlueIconOffAppearance
            = new IconAppearance("resources/images/icon/colour_blue.png", false, true);
    private IconAppearance colourBlueIconOnAppearance
            = new IconAppearance("resources/images/icon/colour_blue.png", true, true);     
    private IconAppearance stickyOffIconOffAppearance
            = new IconAppearance("resources/images/icon/sticky_off.png", false, true);
    private IconAppearance stickyOffIconOnAppearance
            = new IconAppearance("resources/images/icon/sticky_off.png", true, true);
    private IconAppearance stickyOnIconOffAppearance
            = new IconAppearance("resources/images/icon/sticky_on.png", false, true);
    private IconAppearance stickyOnIconOnAppearance
            = new IconAppearance("resources/images/icon/sticky_on.png", true, true);      
    private IconAppearance closeIconOffAppearance
            = new IconAppearance("resources/images/icon/window_close.png", false, true);
    private IconAppearance closeIconOnAppearance
            = new IconAppearance("resources/images/icon/window_close.png", true, true);
    private Icon helpIcon
            = new Icon(CTRL_ICON_OFF_SIZE, helpIconOffAppearance, CTRL_ICON_ON_SIZE, helpIconOnAppearance);
    private Icon upDirIcon
            = new Icon(CTRL_ICON_OFF_SIZE, upDirIconOffAppearance, CTRL_ICON_ON_SIZE, upDirIconOnAppearance);
    private Icon scrollOutIcon
            = new Icon(CTRL_ICON_OFF_SIZE, scrollOutIconOffAppearance, CTRL_ICON_ON_SIZE, scrollOutIconOnAppearance);
    private Icon scrollInIcon
            = new Icon(CTRL_ICON_OFF_SIZE, scrollInIconOffAppearance, CTRL_ICON_ON_SIZE, scrollInIconOnAppearance);
    private Icon billboardOffIcon
            = new Icon(CTRL_ICON_OFF_SIZE, billboardOffIconOffAppearance, CTRL_ICON_ON_SIZE, billboardOffIconOnAppearance);
    private Icon billboardOnIcon
            = new Icon(CTRL_ICON_OFF_SIZE, billboardOnIconOffAppearance, CTRL_ICON_ON_SIZE, billboardOnIconOnAppearance);
    private Icon transparencyOffIcon
            = new Icon(CTRL_ICON_OFF_SIZE, transparencyOffIconOffAppearance, CTRL_ICON_ON_SIZE, transparencyOffIconOnAppearance);
    private Icon transparencyOnIcon
            = new Icon(CTRL_ICON_OFF_SIZE, transparencyOnIconOffAppearance, CTRL_ICON_ON_SIZE, transparencyOnIconOnAppearance);    
    private Icon pathOffIcon
            = new Icon(CTRL_ICON_OFF_SIZE, pathOffIconOffAppearance, CTRL_ICON_ON_SIZE, pathOffIconOnAppearance);
    private Icon pathOnIcon
            = new Icon(CTRL_ICON_OFF_SIZE, pathOnIconOffAppearance, CTRL_ICON_ON_SIZE, pathOnIconOnAppearance);    
    private Icon colourWhiteIcon
            = new Icon(CTRL_ICON_OFF_SIZE, colourWhiteIconOffAppearance, CTRL_ICON_ON_SIZE, colourWhiteIconOnAppearance);
    private Icon colourBlackIcon
            = new Icon(CTRL_ICON_OFF_SIZE, colourBlackIconOffAppearance, CTRL_ICON_ON_SIZE, colourBlackIconOnAppearance);    
    private Icon colourRedIcon
            = new Icon(CTRL_ICON_OFF_SIZE, colourRedIconOffAppearance, CTRL_ICON_ON_SIZE, colourRedIconOnAppearance);     
    private Icon colourGreenIcon
            = new Icon(CTRL_ICON_OFF_SIZE, colourGreenIconOffAppearance, CTRL_ICON_ON_SIZE, colourGreenIconOnAppearance); 
    private Icon colourBlueIcon
            = new Icon(CTRL_ICON_OFF_SIZE, colourBlueIconOffAppearance, CTRL_ICON_ON_SIZE, colourBlueIconOnAppearance);     
    private Icon stickyOffIcon
            = new Icon(CTRL_ICON_OFF_SIZE, stickyOffIconOffAppearance, CTRL_ICON_ON_SIZE, stickyOffIconOnAppearance);
    private Icon stickyOnIcon
            = new Icon(CTRL_ICON_OFF_SIZE, stickyOnIconOffAppearance, CTRL_ICON_ON_SIZE, stickyOnIconOnAppearance);     
    private Icon closeIcon
            = new Icon(CTRL_ICON_OFF_SIZE, closeIconOffAppearance, CTRL_ICON_ON_SIZE, closeIconOnAppearance);    
    private IconText barText;
    
    private boolean transparencyOn;
    
    private void toggleBillboarding(boolean billboardingOn) {
        parentWindow.toggleBillboarding(billboardingOn);
        // set for all future windows
        fm3dMain.setBillboard(billboardingOn);
        if(billboardingOn) {
            parent.removeChild(billboardOnIcon);
            if(parent.indexOfChild(billboardOffIcon) == -1)
                parent.addChild(billboardOffIcon);
            toggleTransparency(true);
        }
        else {
            parent.removeChild(billboardOffIcon);
            if(parent.indexOfChild(billboardOnIcon) == -1)            
                parent.addChild(billboardOnIcon);
        }
    }    

    private void toggleTransparency(boolean transparent) {
        transparencyOn = transparent;
        parentWindow.toggleTransparency(transparencyOn);
        setBarAppearance(true);        
        // set for all future windows
        fm3dMain.setTransparent(transparencyOn);
        if(transparencyOn) {
            parent.removeChild(transparencyOnIcon);
            if(parent.indexOfChild(transparencyOffIcon) == -1)               
                parent.addChild(transparencyOffIcon);
        }
        else {
            parent.removeChild(transparencyOffIcon);
            if(parent.indexOfChild(transparencyOnIcon) == -1)            
                parent.addChild(transparencyOnIcon); 
            toggleBillboarding(false);
        }
    } 

    private void togglePath(boolean pathOn) {
        parentWindow.togglePath(pathOn);
        // set for all future windows
        fm3dMain.setPath(pathOn);
        if(pathOn) {
            parent.removeChild(pathOnIcon);
            if(parent.indexOfChild(pathOffIcon) == -1)
                parent.addChild(pathOffIcon);
        }
        else {
            parent.removeChild(pathOffIcon);
            if(parent.indexOfChild(pathOnIcon) == -1)            
                parent.addChild(pathOnIcon);
        }
    }     
    
    /** Toggles text colour in the order BLACK, WHITE, RED, GREEN, BLUE */
    private void toggleColour(Color color) {
        parentWindow.setTextColour(color);
        // set for all future windows
        fm3dMain.setTextColour(color);
        barText.setTextColour(color);
        if(color == Color.BLACK) {
            parent.removeChild(colourBlackIcon);
            parent.addChild(colourWhiteIcon);
        }
        else if(color == Color.WHITE) {
            parent.removeChild(colourWhiteIcon);
            parent.addChild(colourRedIcon);            
        }
        else if(color == Color.RED) {
            parent.removeChild(colourRedIcon);
            parent.addChild(colourGreenIcon);            
        }
        else if(color == Color.GREEN) {
            parent.removeChild(colourGreenIcon);
            parent.addChild(colourBlueIcon);              
        }
        else {
            parent.removeChild(colourBlueIcon);
            parent.addChild(colourBlackIcon);              
        }
    }
    
    private void toggleSticky(boolean stickyOn) {
        parentWindow.toggleSticky(stickyOn);
        // set for all future windows
        fm3dMain.setSticky(stickyOn);        
        if(stickyOn) {
            parent.removeChild(stickyOnIcon);
            parent.addChild(stickyOffIcon); 
        }
        else {
            parent.removeChild(stickyOffIcon);
            parent.addChild(stickyOnIcon); 
        }        
    }     
    
    public ControlBar(Fm3DMain main, Fm3DWindow parentFm3d, float height, String dirname, Color color, boolean transparent) {
        fm3dMain = main;
        parentWindow = parentFm3d;
        dirName = dirname;
        transparencyOn = transparent;
        opaqueAppearance.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.NONE, 0.0f));        
        activeOpaqueAppearance.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.NONE, 0.0f));        
        
        if(transparencyOn)
            controlPanel = new GlassyPanel(BAR_WIDTH, BAR_HEIGHT, 0.002f, 0.001f, transparentAppearance);
        else
            controlPanel = new GlassyPanel(BAR_WIDTH, BAR_HEIGHT, 0.002f, 0.001f, opaqueAppearance); 
        
        controlPanel.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        setRotationAxis(0.0f, 1.0f, 0.0f);
        
        /*
        if(dirName.length() > MAX_LETTERS)
            dirName = dirName.substring(0, MAX_LETTERS);
        */
        
        // BAR_WIDTH and barPointsWidth must allow for full path to be displayed
        barText = new IconText(4*BAR_WIDTH, BAR_HEIGHT, 4*barPointsWidth, barPointsHeight,
                new Font("Monospaced", Font.BOLD, 16), dirName, color);
        
        setCursor(Cursor3D.SMALL_CURSOR);
        parent.setTranslation(0.0f, 0.008f+height/2.0f, 0.0f);
        helpIcon.setTranslation(-4.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f);
        upDirIcon.setTranslation(-3.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f);
        scrollOutIcon.setTranslation(-2.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f);
        scrollInIcon.setTranslation(-1.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f);
        billboardOffIcon.setTranslation(-0.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f);
        billboardOnIcon.setTranslation(-0.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f);
        transparencyOffIcon.setTranslation(0.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f);
        transparencyOnIcon.setTranslation(0.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f);  
        pathOffIcon.setTranslation(1.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f);
        pathOnIcon.setTranslation(1.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f);          
        colourWhiteIcon.setTranslation(2.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f);         
        colourBlackIcon.setTranslation(2.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f); 
        colourRedIcon.setTranslation(2.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f); 
        colourGreenIcon.setTranslation(2.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f);         
        colourBlueIcon.setTranslation(2.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f);
        stickyOffIcon.setTranslation(3.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f);        
        stickyOnIcon.setTranslation(3.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.0001f); 
        closeIcon.setTranslation(4.5f*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP), 0.0f, 0.001f);
        barText.changeTranslation(0.0f, -0.005f, 0.0f);

        boolean billboadingOn = parentWindow.getBillboarding();
        boolean pathOn = parentWindow.getPath();
        boolean stickyOn = parentWindow.getSticky();

        parent.addChild(controlPanel);
        parent.addChild(helpIcon);
        parent.addChild(upDirIcon);
        parent.addChild(scrollInIcon);
        parent.addChild(scrollOutIcon);
        parent.addChild(billboadingOn ? billboardOffIcon : billboardOnIcon);
        parent.addChild(transparencyOn ? transparencyOffIcon : transparencyOnIcon);
        parent.addChild(pathOn ? pathOffIcon : pathOnIcon);        
        
        if(color == Color.BLACK)
            parent.addChild(colourWhiteIcon);           
        else if(color == Color.WHITE)
            parent.addChild(colourRedIcon);            
        else if(color == Color.RED)
            parent.addChild(colourGreenIcon);            
        else if(color == Color.GREEN)
            parent.addChild(colourBlueIcon);            
        else            
            parent.addChild(colourBlackIcon);
        
        parent.addChild(stickyOn ? stickyOffIcon : stickyOnIcon);
        parent.addChild(closeIcon);
        parent.addChild(barText);
        addChild(parent);

        helpIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                fm3dMain.displayHelp();
            }
        }));        
        
        upDirIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                fm3dMain.changeDirUp();
            }
        }));

        scrollOutIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                fm3dMain.scrollOut();
            }
        }));
 
        scrollInIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                fm3dMain.scrollIn();
            }
        }));
        
        closeIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                fm3dMain.closeWindow(true);
            }
        }));
        
        billboardOffIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                toggleBillboarding(false); 
            }
        }));        
        
        billboardOnIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                toggleBillboarding(true);
            }
        }));
        
        transparencyOffIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                toggleTransparency(false);
            }
        }));        
        
        transparencyOnIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                toggleTransparency(true);
            }
        }));

        pathOffIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                togglePath(false);
            }
        }));        
        
        pathOnIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                togglePath(true);
            }
        }));
        
        colourBlackIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                toggleColour(Color.BLACK);
            }
        }));
        
        colourWhiteIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                toggleColour(Color.WHITE);
            }
        }));
        
        colourRedIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                toggleColour(Color.RED);
            }
        }));
        
        colourGreenIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                toggleColour(Color.GREEN);
            }
        }));
        
        colourBlueIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                toggleColour(Color.BLUE);
            }
        }));     
        
        stickyOffIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                toggleSticky(false); 
            }
        }));        
        
        stickyOnIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                toggleSticky(true);
            }
        }));        
    }

    public void setBarAppearance(boolean active) {
       if(active)
            controlPanel.setAppearance(transparencyOn ? activeTransparentAppearance : activeOpaqueAppearance);    
       else
            controlPanel.setAppearance(transparencyOn ? transparentAppearance : opaqueAppearance);
    }       
    
    public GlassyPanel getControlPanel() {
        return controlPanel;
    }
    
    public IconText getIconIext() {
       return barText;
    }
}

