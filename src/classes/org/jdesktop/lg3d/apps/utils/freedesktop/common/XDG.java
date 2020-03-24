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
 * XDG.java
 *
 * Created on 17 de junio de 2006, 13:23
 *
 * $Revision: 1.3 $
 * $Date: 2006-08-17 23:22:18 $
 * $State: Exp $
 */
package org.jdesktop.lg3d.apps.utils.freedesktop.common;

/**
 * A common place to obtain the default locations of the files referred in all the
 * freedesktop standards.
 * @author opsi
 */
public class XDG {
    public enum DIR {
        XDG_DATA_HOME,
        XDG_DATA_DIRS,
        XDG_CONFIG_HOME,
        XDG_CONFIG_DIRS,
        XDG_CACHE_HOME
    }
    public static String getValue(DIR dir)
    {
        String output = "";
        switch(dir)
        {
            case XDG_DATA_HOME:
                output = System.getProperty("user.home")+"/.local/share";
                break;
            case XDG_DATA_DIRS:
                output = "/usr/local/share/:/usr/share/";
                break;
            case XDG_CONFIG_HOME:
                output = System.getProperty("user.home")+"/.config";
                break;
            case XDG_CONFIG_DIRS:
                output = "/etc/xdg";
                break;
            case XDG_CACHE_HOME:
                output = System.getProperty("user.home")+"/.cache";
                break;
        }
        return output;
    }
}
