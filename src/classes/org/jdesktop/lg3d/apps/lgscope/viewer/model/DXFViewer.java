/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: DXFViewer.java,v $
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
 * $Revision: 1.1 $
 * $Date: 2006-04-17 09:56:21 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgscope.viewer.model;

import java.io.File;

/**
 * The model of DXF viewer is defined.  
 * <pre>
 * Correspondence form:
 * AutoCAD (.dxf)
 * 
 * cv97.j3d.loader.DXFLoader is used here.
 * </pre>
 */
public class DXFViewer extends ModelViewer {

    public DXFViewer(File file) {        
        super(file);
    }

    protected Class getModelLoader() {
        
        try {
            return Class.forName("cv97.j3d.loader.DXFLoader");
        }
        catch (ClassNotFoundException e) {
            NoClassDefFoundError err = new NoClassDefFoundError();
            err.initCause(e);
            throw err;
        }
    }
}
