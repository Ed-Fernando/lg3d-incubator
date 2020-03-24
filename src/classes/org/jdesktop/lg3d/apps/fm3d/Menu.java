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
 * Menu.java
 *
 * @author John Maltby
 */

//import java.awt.*;
import java.io.File;
import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.sg.*;
import org.jdesktop.lg3d.wg.event.*;
import org.jdesktop.lg3d.utils.action.*;
import org.jdesktop.lg3d.utils.eventadapter.*;
import org.apache.commons.io.FileUtils;
import javax.vecmath.Vector3f;

/** Menu class for file delete */
public class Menu extends Container3D {
    private float sizeOff = 0.04f;
    private float sizeOn = 0.05f;
    private Icon return_item;
    private Icon delete_item;
  
    /** Creates a new instance of Menu */
    public Menu(Fm3DWindow menuFrame, IconWithText icon, File menuFile) {
        final Fm3DWindow frame = menuFrame;
        final File file = menuFile;
        final IconWithText fileIcon = icon; 
        TextureAttributes texattr = new TextureAttributes();
        texattr.setTextureMode(TextureAttributes.REPLACE);
        
        // loads files and sets appearances to texture
        IconAppearance returnAppearance = new IconAppearance("resources/images/menu/return_item.png", false, false);
        returnAppearance.setTextureAttributes(texattr);
        IconAppearance deleteAppearance = new IconAppearance("resources/images/menu/delete_item.png", false, false);
        deleteAppearance.setTextureAttributes(texattr);
        
        // create icons with texture for appearance
        return_item = new Icon(sizeOff/2.0f, returnAppearance, sizeOn/2.0f, returnAppearance);
        delete_item = new Icon(sizeOff/2.0f, deleteAppearance, sizeOn/2.0f, deleteAppearance);
        return_item.changeTranslation(new Vector3f(0.0f, 0.01f, 0.05f));
        delete_item.changeTranslation(new Vector3f(0.0f, -0.01f, 0.05f)); 
        addChild(return_item);
        addChild(delete_item);
        
        // add listeners to menu items for single click        
        return_item.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            public void performAction(LgEventSource source) {
                frame.buildWindow(frame.origRot);
                frame.getDesktop().removeAllChildren();
            }
        }));       
        delete_item.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            public void performAction(LgEventSource source) {
                try {
                    FileUtils.forceDelete(file);
                }
                catch(java.io.IOException e) {
                    frame.getWindow().addChild(fileIcon);
                    System.out.println("CANNOT DELETE SOURCE");
                } 
                finally {
                    frame.getDesktop().removeAllChildren();
                }
                frame.buildWindow(frame.origRot);               
            }
        }));          
    }    
}

