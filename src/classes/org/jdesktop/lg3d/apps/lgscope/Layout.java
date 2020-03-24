/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: Layout.java,v $
 *
 * Copyright (c) 2006, INoX Software Development Group, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.2 $
 * $Date: 2006-04-17 14:05:23 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgscope;

import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.wg.Component3D;


/**
 * LgScope layout class. 
 */
public interface Layout {
    
    /**
     * The layout name is returned. 
     * 
     * @return layout name.
     */
    public String getName();
    
    /**
     * The layout is done. 
     * 
     * @param list File-list (which has been sorted).
     * @param screenWidth Screen-Width.
     * @param screenHeight Screen-Height.
     * @param pos Display position on the left.
     * @param abs "pos" absolute position.
     */
    public void layout(
    Component3D[] list, float screenWidth, float screenHeight, 
    Vector3f pos, Vector3f abs);
}
