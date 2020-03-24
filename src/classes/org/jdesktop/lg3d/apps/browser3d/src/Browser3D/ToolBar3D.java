 
/**
 * Project Looking Glass
 *
 * $RCSfile: ToolBar3D.java,v $
 *
 * Copyright (c) 2004, David Vallejo, dragmor@gmail.con, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * This is the toolbar for the 3D browser demo application 
 *
 */

package org.jdesktop.lg3d.apps.browser3d;

import org.jdesktop.lg3d.wg.*;

public abstract class ToolBar3D extends Container3D
{
    public abstract void initialize(SceneManagerBase sceneManager);
    public abstract void addToolBar(ToolBar3D toolbar);
    public abstract void addComponent(Component3D component);
    //public abstract void addButton();
    
    public abstract void removeToolBar(ToolBar3D toolbar);
    public abstract void removeComponent(Component3D component);
    //public abstract void removeButton();
    
    
}