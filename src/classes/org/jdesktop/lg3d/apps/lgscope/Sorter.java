/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: Sorter.java,v $
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

/**
 * The sorter is defined.  
 */
public interface Sorter {
    
    /**
     * The name of the sorter is returned. 
     * 
     * @return sorter name.
     */
    public String getName();
    
    
    /**
     * Sorting is executed. 
     * 
     * @param files File list.
     * @return Sorted file list. 
     */
    public File[] sort(File[] files);
}
