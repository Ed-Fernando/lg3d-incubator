/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: DefaultModelViewer.java,v $
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
 * The model of default ModelViewer is defined.  
 * <pre>
 * Correspondence form:
 * Wavefront (.obj)
 * LightWave (.lwo) [It does not operate now.]
 * </pre>
 */
public class DefaultModelViewer extends ModelViewer {

    public DefaultModelViewer(File file) {        
        super(file);
    }

    protected Class getModelLoader() {  
        
        String filename = file.getName().toLowerCase(); 
        
        if (filename.endsWith(".obj")) {
            return com.sun.j3d.loaders.objectfile.ObjectFile.class;    
        }
        else if (filename.endsWith(".lwo")) {
            return com.sun.j3d.loaders.lw3d.Lw3dLoader.class;
        }
                      
        return null;        
    }
}
