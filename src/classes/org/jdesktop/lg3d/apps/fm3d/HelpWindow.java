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
 * HelpWindow.java
 *
 * @author John Maltby
 */

import org.jdesktop.lg3d.wg.event.*;
import org.jdesktop.lg3d.utils.action.*;
import org.jdesktop.lg3d.utils.eventadapter.*;
import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.sg.*;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.shape.*;

public class HelpWindow extends Frame3D implements WindowConstants {
    private Component3D window = new Component3D();
    private GlassyPanel windowPanel;   
    private float width = 0.15f;
    private float height = 0.12f;  
    private int screenNumber = 1;

    private IconAppearance helpAppearance_1
            = new IconAppearance("resources/images/menu/help_1.png", false, false);
    private IconAppearance helpAppearance_2
            = new IconAppearance("resources/images/menu/help_2.png", false, false);
    private IconAppearance helpAppearance_3
            = new IconAppearance("resources/images/menu/help_3.png", false, false); 
    private IconAppearance leftArrowOffAppearance
            = new IconAppearance("resources/images/icon/left_arrow.png", false, true);
    private IconAppearance leftArrowOnAppearance
            = new IconAppearance("resources/images/icon/left_arrow.png", true, true);  
    private IconAppearance rightArrowOffAppearance
            = new IconAppearance("resources/images/icon/right_arrow.png", false, true);
    private IconAppearance rightArrowOnAppearance
            = new IconAppearance("resources/images/icon/right_arrow.png", true, true);      
    private IconAppearance closeIconOffAppearance
            = new IconAppearance("resources/images/icon/window_close.png", false, true);
    private IconAppearance closeIconOnAppearance
            = new IconAppearance("resources/images/icon/window_close.png", true, true);    
    
    private Icon help_1 = new Icon(height*0.9f, helpAppearance_1, height*0.9f, helpAppearance_1); 
    private Icon help_2 = new Icon(height*0.9f, helpAppearance_2, height*0.9f, helpAppearance_2); 
    private Icon help_3 = new Icon(height*0.9f, helpAppearance_3, height*0.9f, helpAppearance_3);     
    private Icon leftArrow
            = new Icon(ARROW_OFF_SIZE, leftArrowOffAppearance, ARROW_ON_SIZE, leftArrowOnAppearance);  
    private Icon rightArrow
            = new Icon(ARROW_OFF_SIZE, rightArrowOffAppearance, ARROW_ON_SIZE, rightArrowOnAppearance);      
    private Icon closeIcon
            = new Icon(CTRL_ICON_OFF_SIZE, closeIconOffAppearance, CTRL_ICON_ON_SIZE, closeIconOnAppearance);  
    
    /** Creates a new instance of HelpWindow */
    public HelpWindow() {
        setPreferredSize(new Vector3f(width, height, 0.10f));
        windowPanel = new GlassyPanel(width, height, 0.002f, new SimpleAppearance(0.5f,0.5f,0.9f,0.7f));
        window.addChild(windowPanel);
        window.setTranslation(0.0f, 0.0f, 0.06f);
        addChild(window);
        
        TextureAttributes texattr = new TextureAttributes();
        texattr.setTextureMode(TextureAttributes.REPLACE);
        helpAppearance_1.setTextureAttributes(texattr);
        helpAppearance_2.setTextureAttributes(texattr);
        helpAppearance_3.setTextureAttributes(texattr);        
        help_1.setTranslation(0.0f, 0.0f, 0.0f); 
        leftArrow.setTranslation(-width*0.5f+ARROW_ON_SIZE*0.6f, 0.0f, 0.005f);
        rightArrow.setTranslation(width*0.5f-ARROW_ON_SIZE*0.6f, 0.0f, 0.005f);
        closeIcon.setTranslation(width/2.0f-CTRL_ICON_OFF_SIZE, height/2.0f-CTRL_ICON_OFF_SIZE, 0.0001f);
        
        window.addChild(help_1);
        window.addChild(leftArrow);
        window.addChild(rightArrow);
        window.addChild(closeIcon);

        leftArrow.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                if(screenNumber == 3) {
                    screenNumber = 2;
                    window.removeChild(help_3);
                    window.addChild(help_2);
                } else
                if(screenNumber == 2) {
                    screenNumber = 1;
                    window.removeChild(help_2);
                    window.addChild(help_1);
                }                
            }
        }));   
        
        rightArrow.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                if(screenNumber == 1) {
                    screenNumber = 2;
                    window.removeChild(help_1);
                    window.addChild(help_2);
                } else
                if(screenNumber == 2) {
                    screenNumber = 3;
                    window.removeChild(help_2);
                    window.addChild(help_3);
                }
            }
        }));           
        
        closeIcon.addListener(new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                changeEnabled(false);
            }
        }));        
    }
}
