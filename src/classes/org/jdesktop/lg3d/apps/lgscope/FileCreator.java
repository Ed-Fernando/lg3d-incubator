/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: FileCreator.java,v $
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


/**
 * Creator to generate component corresponding to file. 
 * 
 */
public interface FileCreator {
    
    /**
     * The component corresponding to the file is made. 
     * 
     * 
     * @param file
     * @return Component.
     */
    public Component3D create(File file);
    
    
    /**
     * It processes it for the file's entering the state of the selection. 
     * 
     * 
     * @param component Component corresponding to file.
     * @param selected Selected=true / Unselected = false;
     */
    public void changeSelected(Component3D component, boolean selected);    
}
