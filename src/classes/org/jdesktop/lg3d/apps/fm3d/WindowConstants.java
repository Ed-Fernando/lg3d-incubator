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
 * WindowConstants.java
 *
 * @author John Maltby
 *
 */

/**
 * Provides constants for laying out icons in windows
 */
public interface WindowConstants {
    /** Maximum number of letters in displayed filenames */
    int MAX_LETTERS = 10;   
    /** Maximum number of icons allowed in one scroll slice of the window */
    int MAX_ICONS = 30;     //
    /** Font points per metres conversion */
    int POINTS_PER_METRE = 3000;
    /** Magnification factor for icon under cursor */
    float ICON_ZOOM_FACTOR = 1.15f;
    /** Size of icon when not under cursor */
    float ICON_OFF_SIZE = 0.02f;
    /** Size of icon when under cursor */ 
    float ICON_ON_SIZE = ICON_OFF_SIZE*ICON_ZOOM_FACTOR;
    /** Gap between icon columns in window */
    float ICON_COL_GAP = 0.003f;
    /** Gap between icon rows in window */
    float ICON_ROW_GAP = 0.0007f;
    /** Period of icon columns in window */    
    float ICON_COL_PERIOD = ICON_COL_GAP+ICON_OFF_SIZE;
    /** Period of icon rows in window */ 
    float ICON_ROW_PERIOD = ICON_ROW_GAP+ICON_OFF_SIZE;
    /** vertical displacement of prefix beneath icon */
    float PREFIX_ICON_SEPARATION = -0.005f;
    /** vertical displacement of suffix beneath icon */
    float SUFFIX_ICON_SEPARATION = -0.008f;            
    /** Size of control bar icon when not under cursor */
    float CTRL_ICON_OFF_SIZE = 0.005f;
    /** Size of control bar icon when under cursor */
    float CTRL_ICON_ON_SIZE = CTRL_ICON_OFF_SIZE*1.15f;
     /** Size of left arrow icon when not under cursor */
    float ARROW_OFF_SIZE = 0.015f;
    /** Size of left arrow icon when under cursor */
    float ARROW_ON_SIZE = ARROW_OFF_SIZE*1.15f;   
    /** Gap between icon columns in control bar */    
    float CTRL_ICON_GAP = 0.001f;
    /** Width of icon text */  
    float ICONTEXT_WIDTH = 0.04f;
    /** Height of icon text */ 
    float ICONTEXT_HEIGHT = 0.008f;
    /** Width of control bar */      
    float BAR_WIDTH = 10*(CTRL_ICON_OFF_SIZE+CTRL_ICON_GAP) + CTRL_ICON_GAP;
    /** Height of control bar */ 
    float BAR_HEIGHT = CTRL_ICON_ON_SIZE;
    /** Rotation increment in degrees */
    float DELTA_ROT = 6.0f; 
    /** Z-coord of window icons relative to window */
    float ICON_Z_COORD = 0.001f;
    /** Icon page scroll increment */
    float SCROLL_INC = 0.03f;
    /** Icon oage zoom increment */
    float ZOOM_INC = 0.0005f;
    /** Maximum number of help screens */
    int MAX_HELP_SCREENS = 4;    
}
