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
 * DirectoryEntryPool.java
 *
 * Created on 4 de agosto de 2006, 15:47
 *
 * $Revision: 1.2 $
 * $Date: 2006-08-17 23:22:20 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop.menu;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.DesktopEntry;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.FileDesktopEntry;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.XDG;

/**
 *
 * @author opsi
 */
public class DirectoryEntryPool {
    private static Hashtable<String,FileDesktopEntry> entries;
    private static final String DIRECTORY_SUFFIX = ".directory";
    static
    {
        entries = new Hashtable<String,FileDesktopEntry>();
    }
    /** Creates a new instance of DesktopEntryPool */
    protected DirectoryEntryPool() {
    }
    public static void processDefaultDirectoryDirs() throws IOException {
        String defaultappdirs = XDG.getValue(XDG.DIR.XDG_DATA_DIRS);
        StringTokenizer tokenizer = new StringTokenizer(defaultappdirs,":");
        while(tokenizer.hasMoreTokens()) {
            File appdir = new File(tokenizer.nextToken()+"/desktop-directories/");
            if(appdir.canRead())
                processDirectoryDir(appdir,appdir);
        }
    }
    public static void processDirectoryDir(File directoryDir,File basedir) throws IOException {
        if(!directoryDir.isDirectory())
            throw new IOException(directoryDir.getAbsolutePath() + " is not a directory");
        File[] dirFiles = directoryDir.listFiles();
        for(File file: dirFiles) {
            if(file.isDirectory())
                processDirectoryDir(file,basedir);
            else if(file.getName().endsWith(".directory")) {
                String desktopid = file.getAbsolutePath().substring(basedir.getAbsolutePath().length()+1);                
                entries.put(desktopid,new FileDesktopEntry(file));
            }
        }
    }
   
    public static void processDirectoryDir(String directoryDir) throws IOException {        
        File dir = new File(directoryDir);
        if(dir.canRead() && dir.isDirectory())
            processDirectoryDir(dir,dir);
    }
    public static FileDesktopEntry getDirectoryEntry(String filename)
    {
        FileDesktopEntry  output =entries.get(filename);
        return output;
    }
}


