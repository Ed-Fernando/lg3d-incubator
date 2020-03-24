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
 * IconWithText.java
 *
 * @author John Maltby
 */

import java.awt.*;
import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.sg.*;
import org.jdesktop.lg3d.utils.action.*;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;

public class IconWithText extends Component3D implements WindowConstants {
    private int iconPointsWidth = (int)(POINTS_PER_METRE * ICONTEXT_WIDTH);
    private int iconPointsHeight = (int)(POINTS_PER_METRE * ICONTEXT_HEIGHT);
    
    private float sizeOff;
    private Appearance appOff;
    private float sizeOn;
    private Appearance appOn;    
    private String filename;    
    private Vector3f positionInWindow;
    private Icon icon;
    private IconText prefixText;
    private IconText suffixText;
    
    private String getPrefix() {
        String prefix = "";
        int index = filename.lastIndexOf('.');
        if(index == -1) // char not found
            index = filename.length();
        if(index > MAX_LETTERS)
            index = MAX_LETTERS;  // max characters
        if(index > 0)
            prefix = filename.substring(0, index);
        return prefix;
    }
    
    private String getSuffix() {
        String suffix = "";
        int length = filename.length();
        int index = filename.lastIndexOf('.');
        if(index == -1) // char not found
            index = length;
        int ext = length - index;
        if(ext > MAX_LETTERS)
            ext = MAX_LETTERS;   // characters
        if(index > 0)
            suffix = filename.substring(index, index+ext);
        return suffix;
    }
    
    public IconWithText(float sizeOff, Appearance appOff, float sizeOn, Appearance appOn, String fn, Color color) {
        this.sizeOff = sizeOff;
        this.appOff = appOff;
        this.sizeOn = sizeOn;
        this.appOn = appOn;
        filename = fn;
        Vector3f textPosition;

        icon = new Icon(sizeOff/2.0f, appOff, sizeOff/2.0f, appOn);
        prefixText = new IconText(ICONTEXT_WIDTH, ICONTEXT_HEIGHT, iconPointsWidth,
                iconPointsHeight, new Font("SansSerif", Font.BOLD, 10), getPrefix(), color);
        suffixText = new IconText(ICONTEXT_WIDTH, ICONTEXT_HEIGHT, iconPointsWidth,
                iconPointsHeight, new Font("SansSerif", Font.BOLD, 10), getSuffix(), color);
        
        icon.changeTranslation(new Vector3f(0.0f, 0.003f, 0.0f));
        prefixText.changeTranslation(new Vector3f(0.0f, PREFIX_ICON_SEPARATION, 0.0f));
        suffixText.changeTranslation(new Vector3f(0.0f, SUFFIX_ICON_SEPARATION, 0.0f));
        addChild(icon);
        addChild(prefixText);
        addChild(suffixText);
        
        if(sizeOff != sizeOn)
            addListener(new MouseEnteredEventAdapter(new ScaleActionBoolean(this, sizeOn/sizeOff, 100)));
    }
    
    public void setTextColour(Color color) {
        prefixText.setTextColour(color);
        suffixText.setTextColour(color);
    }
    
    public void setPositionInWindow(Vector3f pos) {
        positionInWindow = pos;
        changeTranslation(positionInWindow);
    }
    
    public Vector3f getPositionInWindow() {
        return positionInWindow;
    }
    
    public Icon getIcon() {
        return icon;
    }
}

