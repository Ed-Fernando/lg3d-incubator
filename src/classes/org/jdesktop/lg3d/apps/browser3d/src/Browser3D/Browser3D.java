/**
 * Project Looking Glass
 *
 * $RCSfile: Browser3D.java,v $
 *
 * Copyright (c) 2004, browser3d project team, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * This is a 3D browser demo application 
 *
 */

package org.jdesktop.lg3d.apps.browser3d;

import org.jdesktop.lg3d.wg.*;

/**
 * The main browser3d class.
 */
public class Browser3D extends Frame3D {
    
    private ToolBar3D myToolBar;
    
    public static void main(String[] args) {
        Frame3D app = new Browser3D();
	app.setActive (true);
	app.setVisible(true);
    }
    
    public Browser3D()
    {
        setName("Browser3D");
        setCursor(Cursor3D.DEFAULT_CURSOR);
        
        // The top container is introduced to deal with visual effects like
        // swinging motion.  Since the scenemanager is free to change the
        // public properties (e.g. location, rotation, scale) of "this"
        // object for management of this application, the top container is
        // introduced in order to avoid conflicts with the manager's use.
        Container3D top = new Container3D();
        addChild(top);
    
    }
}

