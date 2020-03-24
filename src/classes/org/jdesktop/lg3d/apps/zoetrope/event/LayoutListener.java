/*
 * LG3D Incubator Project - Zoetrope
 *
 * $RCSfile:$
 *
 * Copyright (c) 2004, Zoetrope Project Team, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision:$
 * $Date:$
 * Author: yuichi sakuraba
 */

package org.jdesktop.lg3d.apps.zoetrope.event;

import java.util.EventListener;

public interface LayoutListener extends EventListener {
    public void layoutDone(LayoutEvent event);
}
