/*
 * LG3D Incubator Project - gol3d
 * A simple Game of Life for Looking Glass 3D
 *
 * $RCSfile: GolGroundComponent.java,v $
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.2 $
 * $Date: 2005-06-24 19:56:40 $
 * Author: Van der Haegen Mathieu (dwarfy)
 */

package org.jdesktop.lg3d.apps.gol3d;

import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.wg.Component3D;


public class GolGroundComponent extends Component3D {
    public final static float groundBorderRatio = 0.05f;
    public final static float groundHeight = 0.005f;
    public final static float groundWidth = 0.1f;
    public final static float groundInnerWidth = groundWidth * (1.0f - (2.0f * groundBorderRatio));
    public final static float groundBorderWidth = groundWidth * groundBorderRatio;        

    GolGroundComponent(GolConfig gcfg) {
        SimpleAppearance groundApp = new SimpleAppearance(gcfg.getGroundColorR(),gcfg.getGroundColorG(),gcfg.getGroundColorB(),gcfg.transparency);
        this.addChild(new Box(groundWidth / 2,groundHeight / 2,groundWidth / 2,Box.GENERATE_NORMALS ,groundApp));
    }
}

