/*
 ***************************************************************************
 *   Copyright (C) 2005 by Juan Gonzalez                                   *
 *   juan@aga-system.com                                                   * 
 *                                                                         *
 *   This program has been developed under Google's "Summer of Code 2005". *
 *   Special thanks goes to all people from Google and Sun Microsystems    *
 *   who made this cool experience a kind of success.                      *
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
 ***************************************************************************
 * MyExtendableLine.java
 *
 * Created on 11 de agosto de 2005, 17:59
 */

package org.jdesktop.lg3d.apps.googler.gui;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.ColoringAttributes;
import org.jdesktop.lg3d.sg.LineAttributes;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.utils.shape.ExtendableLine;

/**
 * This class adds some convenience methods to manipulate the line appearance on a live scene
 * @author opsi
 */
class MyExtendableLine extends ExtendableLine
{
    /**
     * Calls the default supre constructor, and sets a manipulable appearance to this line
     */
    public MyExtendableLine()
    {
        super();
        setCapability(ALLOW_APPEARANCE_WRITE);
        setCapability(ALLOW_APPEARANCE_READ);
        
        Appearance app = new Appearance();
        app.setCapability(app.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
        app.setCapability(app.ALLOW_COLORING_ATTRIBUTES_WRITE);
        app.setCapability(app.ALLOW_LINE_ATTRIBUTES_WRITE);        
        
        setAppearance(app);
        setPickable(false);
    }    
    /**
     * Set transparency value for this line
     * @param tVal Value for transparency
     */
    public void setTrans(float tVal)
    {
        Appearance app = getAppearance();
        app.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.BLENDED,tVal));
        setAppearance(app);
    }
    /**
     * This method stablish the coloring values for this line
     * @param r Red value (0.0f - 1.0f
     * @param g Green value (0.0f - 1.0f
     * @param b Blue value (0.0f - 1.0f
     */
    public void setColoring(float r,float g, float b)
    {
        Appearance app = getAppearance();
        ColoringAttributes colorAttr = new ColoringAttributes();
        colorAttr.setColor(r,g,b);
        app.setColoringAttributes(colorAttr);
        setAppearance(app);
    }
    /**
     * Stablish the width of this line
     * @param lineWidth new width
     */
    public void setLineWidth(float lineWidth)
    {
        Appearance app = getAppearance();
        LineAttributes lineAttr = new LineAttributes();
        lineAttr.setLineWidth(lineWidth);
        app.setLineAttributes(lineAttr);
        setAppearance(app);
    }
    /**
     * Stablish all values for this line from one method
     * @param r Red channel value
     * @param g Green channel value
     * @param b Blue channel value
     * @param tVal Alpha channel value
     * @param lineWidth Width for the line
     */
    public void setLineStyle(float r,float g, float b, float tVal,float lineWidth)
    {                
        setLineWidth(lineWidth);
        setColoring(r,g,b);
        setTrans(tVal);        
    }
}
