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
 * Icon.java
 *
 * @author John Maltby
 */

import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.sg.*;
import org.jdesktop.lg3d.utils.action.*;
import org.jdesktop.lg3d.utils.shape.*;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;

public class Icon extends Component3D {
    public Icon(float size, Appearance app) {
        this(size, app, size, app);
    }
	
    public Icon(float sizeOff, Appearance appOff, float sizeOn, Appearance appOn) {
        Shape3D shape = new ImagePanel(sizeOff, sizeOff);
        shape.setAppearance(appOff);
        addChild(shape);
        if(appOff != appOn) 
            addListener(new MouseEnteredEventAdapter(new AppearanceChangeAction(shape, appOn)));
        if(sizeOff != sizeOn) 
            addListener(new MouseEnteredEventAdapter(new ScaleActionBoolean(this, sizeOn/sizeOff, 100)));
    }
}

