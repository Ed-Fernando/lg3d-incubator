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
 * MimeDesktopEntry.java
 *
 * Created on 3 de junio de 2006, 15:30
 *
 * $Revision: 1.6 $
 * $Date: 2006-12-09 13:22:10 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop.common;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Logger;
import org.jdesktop.lg3d.utils.action.AppLaunchAction;

/**
 *
 * @author opsi
 */
public class MimeDesktopEntry implements DesktopEntry{
    private File file;
    private String mimeType = null;
    private static Logger logger;
    
    private static String externalRunner ="";
    private static String sessionType="";
    static
    {
        logger = Logger.getLogger("lg.desktop");
        String [] path = System.getenv("PATH").split(":");
        File runner;
        if("true".equals(System.getenv("KDE_FULL_SESSION")))
            sessionType = "kde";
        else if(!("".equals(System.getenv("GNOME_DESKTOP_SESSION_ID"))))
            sessionType = "gnome";
        
        for(String currPath : path) {
            if(sessionType.equals("kde") || sessionType.equals("")) {
                runner = new File(currPath+"/"+"kfmclient");
                if(runner.canExecute()) {
                    sessionType = "kde";
                    externalRunner = runner.getAbsolutePath();
                    break;
                }
            }
            
            if(sessionType.equals("gnome") || sessionType.equals("")) {
                runner = new File(currPath+"/"+"gnome-open");
                if(runner.canExecute()) {
                    sessionType = "gnome";
                    externalRunner = runner.getAbsolutePath();
                    break;
                }
            }
        }
        if("".equals(sessionType))
            logger.warning("Impossible to find a program to handle MIME based files");
    }
    
    
    /** Creates a new instance of MimeDesktopEntry */
    public MimeDesktopEntry(File file) {
        if(!file.canRead())
            throw new IllegalArgumentException("I can't read the file : " + file.getAbsolutePath());
        this.file = file;
        try {
            mimeType = MimeTypes.getType(file);
        } catch (IOException ex) {
            //This shouldnt happen as the file is checked beforehand
            mimeType = "misc";
        }
    }
    
    public String getName(Locale locale) {
        return file.getName();
    }
    
    public String getIconName() {
        if(mimeType.contains("executable")) {
            return "exec";
        }
        return mimeType;
    }
    
    public void performAction() {
        if(sessionType.equals("kde"))
            new AppLaunchAction(externalRunner+" exec "+file.getAbsolutePath(),getClass().getClassLoader()).performAction(null);
        else if(sessionType.equals("gnome"))
            new AppLaunchAction(externalRunner+" "+file.getAbsolutePath(),getClass().getClassLoader()).performAction(null);
        else
            logger.warning("It was impossible to find a program to handle MIME based files");
    }
    
    public boolean isValid() {
        return true;
    }
    
}
