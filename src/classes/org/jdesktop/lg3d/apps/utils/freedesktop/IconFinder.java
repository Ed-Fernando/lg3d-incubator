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
 * IconFinder.java
 *
 * Created on 3 de junio de 2006, 16:18
 *
 * $Revision: 1.7 $
 * $Date: 2006-08-17 23:22:18 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.DesktopEntry;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.FileDesktopEntry;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.XDG;

/**
 *
 * @author opsi
 */
public class IconFinder {
    private Logger logger;
    private static IconFinder instance;
    private List<IconTheme> themes;
    /** Creates a new instance of IconFinder */
    private IconFinder() {
        logger = Logger.getLogger("lg.desktop");
        themes = new LinkedList<IconTheme>();
        try {
            StringTokenizer tok = new StringTokenizer(XDG.getValue(XDG.DIR.XDG_DATA_DIRS),":");
            while(tok.hasMoreTokens()) {
                String dirName = tok.nextToken();
                if(!dirName.equals("/usr/share/pixmaps"))
                    dirName+="/icons";
                File currDir = new File(dirName);
                if(currDir.isDirectory() && currDir.canRead()) {
                    File [] list = currDir.listFiles(new FileFilter() {
                        public boolean accept(File pathname) {
                            return pathname.isDirectory() && pathname.canRead();
                        }
                    });
                    for(File themeDir : list) {
                        IconTheme theme;
                        try {
                            theme = new IconTheme(themeDir);
                            themes.add(theme);
                        } catch (IllegalArgumentException ex) {
//                            logger.warning(ex.getMessage());
//                            ex.printStackTrace();
                        } catch (IOException ex) {
//                            logger.warning(ex.getMessage());
//                            ex.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.warning("It's impossible to load some icon theme");
        }
    }
    
    public static IconFinder getInstance() {
        if(instance==null)
            instance = new IconFinder();
        return instance;
    }
    
    public URL findIconFor(String name,  int size) {
        URL output = null;
        if(name!=null) {
            if(!name.startsWith("/")) {
                for(int i = themes.size()-1; i >=0  && output == null;i--) {
                    //TODO The icon theme should be read from somewhere else
                    output = themes.get(i).findIconFor(name,size);
                }
            } else //Icon name is an absolute path, just build the URL
            {
                try {
                    output = new File(name).toURI().toURL();
                } catch (MalformedURLException ex) {
                    logger.warning("It's not possible to create URL for : "+name);
                }
            }
        }
        if(output==null) {
            try {
//            try {
                
                output = new File(System.getProperty("lg.resourcedir")+"/images/icon/lg3d-logo.png").toURI().toURL();
            } catch (MalformedURLException ex) {
                //We can't get the icon
                logger.severe("I can't load the fallback icon !!");
            }
        }
        return output;
    }
    public URL findIconForMime(String mimeType,int size) {
        URL output = null;
        for(int i =0; i < themes.size()  && output == null;i++) {
            //TODO The icon theme should be read from somewhere else
            output = themes.get(i).findIconForMime(mimeType,size);
        }
        if(output==null) {
            try {
//            try {
                //TODO make it jar dependant not folder based
                //output = new File("src/resources/images/icon/defaultapp.png").toURL();
                logger.fine("Impossible to find an adequate icon for MIME type : " + mimeType + ", using fallback icon ");
                output = new File(System.getProperty("lg.resourcedir")+"/images/icon/lg3d-logo.png").toURI().toURL();
            } catch (MalformedURLException ex) {
                //We can't get the icon
                logger.severe("I can't load the fallback icon !!");
            }
        }
        return output;
    }
}