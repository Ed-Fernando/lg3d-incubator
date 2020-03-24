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
 * DesktopEntryPool.java
 *
 * Created on 30 de julio de 2006, 20:11
 *
 * $Revision: 1.2 $
 * $Date: 2006-08-17 23:22:20 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop.menu;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.FileDesktopEntry;
import org.jdesktop.lg3d.apps.utils.freedesktop.common.XDG;

/**
 *
 * @author opsi
 */
public class DesktopEntryPool {
    private static Hashtable<String,FileDesktopEntry> entries;    
    static
    {
        entries = new Hashtable<String,FileDesktopEntry>();
    }
    /** Creates a new instance of DesktopEntryPool */
    protected DesktopEntryPool() {
    }
    public static void processDefaultAppDirs() throws IOException {
        String defaultappdirs = XDG.getValue(XDG.DIR.XDG_DATA_DIRS);
        StringTokenizer tokenizer = new StringTokenizer(defaultappdirs,":");
        while(tokenizer.hasMoreTokens()) {
            File appdir = new File(tokenizer.nextToken()+"/applications/");
            if(appdir.canRead())
                processAppDir(appdir,appdir);
        }
//        for(String key : entries.keySet())
//        {
//            System.out.println(key + "->" + entries.get(key).getValueFor("Categories"));
//        }
    }
    public static void processAppDir(File appdir,File basedir) throws IOException {
        if(!appdir.isDirectory())
            throw new IOException(appdir.getAbsolutePath() + " is not a directory");
        File[] dirFiles = appdir.listFiles();
        for(File file: dirFiles) {
            if(file.isDirectory())
                processAppDir(file,basedir);
            else if(file.getName().endsWith(".desktop")) {
                String desktopid = file.getAbsolutePath().
                        substring(basedir.getAbsolutePath().
                        length()+1).
                        replace('/','-').
                        replace(".desktop","");;                
                entries.put(desktopid,new FileDesktopEntry(file));
            }
        }
    }
   
    public static void processAppDir(String appdir) throws IOException {        
        File dir = new File(appdir);
        if(dir.canRead() && dir.isDirectory())
            processAppDir(dir,dir);
    }
    
    public static List<FileDesktopEntry> getEntriesFor(RuleSet rules)
    {
        LinkedList<FileDesktopEntry> output = new LinkedList<FileDesktopEntry>();
        for(String id: entries.keySet())
        {
            FileDesktopEntry entry = entries.get(id);            
            if(rules.matches(entry.getDesktopCategories(),new String[]{id}))
                output.add(entry);
        }
        return output;
    }
}
