/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: ModelViewer.java,v $
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
 * $Date: 2006-04-17 09:56:22 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgscope.viewer.model;

import java.io.File;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgscope.viewer.LgViewer;
import org.jdesktop.lg3d.utils.eventaction.Component3DMover;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.ModelLoader;



/**
 * Model viewer is defined. 
 */
public abstract class ModelViewer extends LgViewer {
    
    protected ModelViewer(File file) {
        super(file);        
    }
    
    protected Component3D createViewer(File file) {                       
        return createViewer(file, 0.08f);
    }
    
    
    private Component3D createViewer(File file, float size) {
                       
        ModelLoader model = new ModelLoader(file.getParent(), file.getName(), getModelLoader());                
        model.resize(new Vector3f(), size);
        
        Component3D c = new Component3D();
        c.addChild(model);
        // To our regret, the size cannot be acquired. 
        // Here enables the movement of the component. 
        c.addListener(new Component3DMover(c));
        // System.out.println(LgUtil.getSize(model));
                
        return c;
    }
    
    
    /**
     * The same component as the createThumbnail() is returned. 
     */
    protected Component3D createThumbnail(File file) {
        return createViewer(file, 0.01f);
    }
    
    
    /**
     * The ModelLoader is returned. 
     * @return ModelLoader class.
     */
    protected abstract Class getModelLoader();

}
