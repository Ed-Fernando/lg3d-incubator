/**
 * Project Looking Glass
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
 */
package org.jdesktop.lg3d.apps.weather;

import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Component3D;

import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;

import javax.vecmath.Vector3f;
/**
 *
 * @author bhursey (Brian Hursey)
 *
 */
public class weather {
 public static void main(String[] args) {
        new weather();
    }
    public weather() {
        Frame3D frame3d = new Frame3D();
        SimpleAppearance app = new SimpleAppearance(0.6f, 0.8f, 0.6f);
        Box box = new Box(0.04f, 0.03f, 0.02f, app);
        Component3D comp = new Component3D();
        comp.addChild(box);
        frame3d.addChild(comp);
        frame3d.setPreferredSize(new Vector3f(0.08f, 0.06f, 0.04f));
        frame3d.changeEnabled(true);
        frame3d.changeVisible(true);
    }
    
}