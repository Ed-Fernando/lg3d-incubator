/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: DefaultSelectedListener.java,v $
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

import org.jdesktop.lg3d.apps.lgscope.util.LgUtil;
import org.jdesktop.lg3d.apps.lgscope.viewer.ImageViewer;
import org.jdesktop.lg3d.apps.lgscope.viewer.TextViewer;
import org.jdesktop.lg3d.apps.lgscope.viewer.model.DXFViewer;
import org.jdesktop.lg3d.apps.lgscope.viewer.model.DefaultModelViewer;
import org.jdesktop.lg3d.apps.lgscope.viewer.model.VRMLViewer;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;


/**
 * Standard SelectedListener.
 */
public class DefaultSelectedListener implements FileActionListener {
    
    private LgScope lgScope;
    
    
    DefaultSelectedListener(LgScope lgScope) {
        this.lgScope = lgScope;        
    }


    public void execute(
    Component3D component, File file,
    LgEventSource source, float x, float y, float z) {   
             
        String filename = file.getName().toLowerCase(); 
        
        // It is a directory. 
        if (file.isDirectory()) {            
            lgScope.scope(file);
            return;
        }
        
        
        // It is an image. 
        if (LgUtil.canReadImage(filename)) {
            new ImageViewer(file);
        }
        else if (filename.endsWith(".obj") || filename.endsWith(".lwo")) {
            new DefaultModelViewer(file);
        }        
        else if (filename.endsWith(".wrl")) {
            new VRMLViewer(file);
        }
        else if (filename.endsWith(".dxf")) {
            new DXFViewer(file);
        }
//        else if (filename.endsWith(".xls")) {
//            new org.jdesktop.lg3d.apps.lgscope.viewer.poi.ExcelViwer(file);
//        }
        else {
            new TextViewer(file);
        }
    }        
}
