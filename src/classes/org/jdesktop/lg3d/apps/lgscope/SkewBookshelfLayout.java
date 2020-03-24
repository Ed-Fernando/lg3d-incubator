/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: SkewBookshelfLayout.java,v $
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

/**
 * Bookshelf style layout.
 * It inclines it only a little.  
 */
public class SkewBookshelfLayout extends BookshelfLayout {

    public String getName() {
        return "Bookshelf2";
    }
    
        
    /**
     * It alternately inclines it. 
     * 
     */
    protected float getAngle(int steps) {
        return (steps % 2 == 0) ? (float) (-Math.PI / 2.1) : (float) (-Math.PI / 1.9);       
    }
}
