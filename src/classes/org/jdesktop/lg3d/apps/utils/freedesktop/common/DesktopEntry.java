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
 * DesktopEntry.java
 *
 * Created on 8 de mayo de 2006, 21:20
 *
 * $Revision: 1.3 $
 * $Date: 2006-08-17 23:22:19 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop.common;

import java.util.Locale;
import java.util.Set;

/**
 * Encapsulates each of the Desktop Entries that are to be included in the Desktop Entry poll
 * @author opsi
 */
public interface DesktopEntry {
//    public Set<String> getCategories();    
//    public Set<String> getKeys();
//    public Set<String> getKeys(String category);    
//    public String getValueFor(String key);
//    public String getValueFor(String key,Locale locale);
//    public String getValueFor(String category,String key,Locale locale);
    public String getName(Locale locale);
    public String getIconName();
    public void performAction();
    public boolean isValid();    
}
