/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 *                                                                         *
 *   Copyright (C) 2006                                                    * 
 *                  Juan Gonzalez (juan@aga-system.com)                    *
 *                & Sun Microsystems                                       *
 *                                                                         * 
 ***************************************************************************
 * DesktopPluginInterface.java
 *
 * Created on 12 de junio de 2006, 14:52
 *
 * $Revision: 1.4 $
 * $Date: 2006-08-27 10:32:45 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop;

import org.jdesktop.lg3d.scenemanager.utils.event.ScreenResolutionChangedEvent;
import org.jdesktop.lg3d.wg.Component3D;

/**
 *
 * @author opsi
 */
public interface DesktopPluginInterface {    
    public void populateDesktop();
    public Component3D getDesktopRoot();
    public void fitToScreen(ScreenResolutionChangedEvent event);
    public void switchVisible();
}
