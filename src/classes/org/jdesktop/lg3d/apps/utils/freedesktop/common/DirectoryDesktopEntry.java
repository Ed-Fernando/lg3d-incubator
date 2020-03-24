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
 * DirectoryDesktopEntry.java
 *
 * Created on 3 de junio de 2006, 20:24
 *
 * $Revision: 1.3 $
 * $Date: 2006-08-17 23:22:18 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop.common;

import java.awt.EventQueue;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;
import org.jdesktop.lg3d.displayserver.LgClassLoader;

/**
 *
 * @author opsi
 */
public class DirectoryDesktopEntry implements DesktopEntry{
    private File directory;
    private Logger logger;
    /** Creates a new instance of DirectoryDesktopEntry */
    public DirectoryDesktopEntry(File directory) {
        if(!directory.isDirectory())
            throw new IllegalArgumentException("Parameter \"directory\" must be a directory");
        this.directory = directory;
        logger = Logger.getLogger("lg.desktop");
    }    
    
    public void performAction() {
        logger.warning("This should open the folder");
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                openDirectory();
            }
        });
    }
    private void openDirectory() {
        try {            
            Class fm3d = Class.forName("edu.cmu.sun.exe.LaunchDemo",false,LgClassLoader.getClassLoader(LgClassLoader.generateUniqueID()));
            Constructor constructor = fm3d.getConstructor(String.class);
            constructor.newInstance(directory.getAbsolutePath());            
        } catch (Exception ex) {
            logger.warning("Unable to find class to launch Fm3D : "+ex.getClass().getName());
        }
    }
    public boolean isValid() {
        return true;
    }

    public String getName(Locale locale) {
        return directory.getName();
    }

    public String getIconName() {
        return "folder";
    }
    
}
