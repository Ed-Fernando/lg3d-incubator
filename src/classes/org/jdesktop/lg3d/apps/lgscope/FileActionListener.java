/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: FileActionListener.java,v $
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

import java.io.File;

import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;


/**
 * Listener who is called when action happens to file. 
 * 
 */
public interface FileActionListener {
    
//    /**
//     * 
//     * @param component
//     * @param file
//     * @param selected Selected=true/Unselected=false
//     * @param source
//     * @param x
//     * @param y
//     * @param z
//     */
//    public void selected(
//    Component3D component, File file, boolean selected,
//    LgEventSource source, float x, float y, float z);
    
    
    public void execute(
    Component3D component, File file, 
    LgEventSource source, float x, float y, float z);
}
