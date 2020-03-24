/**
 * Project Looking Glass
 *
 * $RCSfile: GlassyCubeTaskbarItem.java,v $
 *
 * Copyright (c) 2004, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.4 $
 * $Date: 2005-06-24 19:57:18 $
 * $State: Exp $
 */
package org.jdesktop.lg3d.apps.luncher;

import org.jdesktop.lg3d.utils.component.Pseudo3DShortcut;
import org.jdesktop.lg3d.utils.shape.ColorCube;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Tapp;
import javax.vecmath.Vector3f;

/**
 *
 * @author paulby
 */
public class GlassyCubeTaskbarItem extends Tapp {
    
    GlassyCubeMenu gcm = new GlassyCubeMenu();
    
    /** Creates a new instance of GlassyCubeTaskbarItem */
    public GlassyCubeTaskbarItem() {
        gcm = new GlassyCubeMenu();
        this.setPreferredSize(new Vector3f(0.01f, 0.01f, 0.01f));
        gcm.setVisible(false);
        gcm.initialize();
        addChild(gcm.getMenuIcon());
        addChild(gcm);
        setVisible(true);       
    }
    
}
