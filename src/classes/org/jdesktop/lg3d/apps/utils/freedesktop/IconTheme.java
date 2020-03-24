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
 * IconTheme.java
 *
 * Created on 17 de junio de 2006, 23:28
 *
 * $Revision: 1.5 $
 * $Date: 2006-08-17 23:22:18 $
 * $State: Exp $
 */
package org.jdesktop.lg3d.apps.utils.freedesktop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author opsi
 */
public class IconTheme {
    private Logger logger;
    private static final String ICON_HEADER = "[Icon Theme]";
    private String themeName;
    private String themeComment;
    private List<String> availableFolders;
    private List<File> sizedFolders;    
    private Map<String,File> name2file;
    private Map<File,IconThemeFolder> foldersInfo;
    private IconThemeFolder currentFolder;
    private File currentFolderFile;
    
    
    /** Creates a new instance of IconTheme */
    public IconTheme(File basedir) throws IOException,IllegalArgumentException {
        if(!basedir.canRead())
            throw new IOException("Cannot read directory : "+basedir);
        logger = Logger.getLogger("lg.desktop");
        File[] dirs = basedir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().equals("index.theme");
            }
        });
        if(dirs != null && dirs.length > 0) {
            Pattern catPattern = Pattern.compile("\\[..*\\]");
            Pattern keyPattern = Pattern.compile("..*=..*");
            Pattern commentPattern = Pattern.compile("#.*");
            
            availableFolders = new LinkedList<String>();
            sizedFolders = new LinkedList<File>();            
            name2file = new Hashtable<String,File>();
            foldersInfo = new Hashtable<File,IconThemeFolder>();
            
            File index = dirs[0];
            String line,currentCat,key,value;
            BufferedReader reader = new BufferedReader(new FileReader(index));
            StringTokenizer tok1,tok2;
            try {
                line=reader.readLine();
                if(!ICON_HEADER.equals(line))
                    throw new IllegalArgumentException(index.getAbsolutePath() + " file is not a valid [Icon Theme] file");
                currentCat = ICON_HEADER;
                int lineNumber = 0;
                while((line=reader.readLine()) != null) {
                    if(line.equals(""))
                        continue;
                    lineNumber++;
                    if(catPattern.matcher(line).matches()) {
                        if(currentCat.equals(ICON_HEADER) ) {
                            currentFolder = new IconThemeFolder();
                        } else if(availableFolders.contains(currentCat.substring(1,currentCat.length()-1))){
                            currentFolderFile = name2file.get(currentCat.substring(1,currentCat.length()-1));
                            if(currentFolder == null || currentFolderFile==null)
                                throw new IllegalArgumentException(index.getAbsolutePath() + " file is not a valid [Icon Theme] file");
                            if(foldersInfo.containsKey(currentFolderFile))
                                throw new IllegalArgumentException(index.getAbsolutePath() + " duplicated folder info");
                            foldersInfo.put(currentFolderFile,currentFolder);
                            currentFolder = new IconThemeFolder();
                            currentFolderFile = null;
                        } else
                            logger.fine(currentCat + " category doesn't have an associated directory entry in " + index.getAbsolutePath());
                        currentCat = line;
                    } else if(keyPattern.matcher(line).matches()) {
                        if(currentCat.equals(ICON_HEADER)) //We are reading info about the theme
                        {
                            tok1 = new StringTokenizer(line,"=");
                            assert(tok1.countTokens()==2); //If it passed the matcher...
                            key = tok1.nextToken();
                            value = tok1.nextToken();
                            if(key.equals("Name")) {
                                themeName = value;
                            } else if(key.equals("Comment")) {
                                themeComment = value;
                            } else if(key.equals("Directories")) {
                                tok2= new StringTokenizer(value,",");
                                while(tok2.hasMoreTokens()) {
                                    String actualDir = tok2.nextToken();
                                    File subdir = new File(basedir,actualDir);
                                    if(subdir.canRead() && subdir.isDirectory()) {
                                        availableFolders.add(actualDir);
                                        sizedFolders.add(subdir);
                                        name2file.put(actualDir,subdir);
                                    } else
                                        logger.fine("Directory " + basedir.getAbsolutePath() + " doesn't contain named sub-directory, " + subdir.getName());
                                }
                            } else
                                logger.fine("Key "+ key +" ignored on file " + index.getAbsolutePath());
                        } else //We are reading info about a subdir of the theme
                        {
                            tok1 = new StringTokenizer(line,"=");
                            assert(tok1.countTokens()==2); //If it passed the matcher...
                            key = tok1.nextToken();
                            value = tok1.nextToken();
                            if(key.equals("Size")) {
                                try {
                                    currentFolder.setSize(Integer.parseInt(value));
                                } catch(NumberFormatException ex) {
                                    throw new IOException("Illegal number in integer value",ex);
                                }
                            } else if(key.equals("Context")) {
                                currentFolder.setContext(value);
                            } else if(key.equals("Type")) {
                                currentFolder.setType(value);
                            } else if(key.equals("MinSize")) {
                                try {
                                    currentFolder.setMinSize(Integer.parseInt(value));
                                } catch(NumberFormatException ex) {
                                    throw new IOException("Illegal number in integer value",ex);
                                }
                            } else if(key.equals("MaxSize")) {
                                try {
                                    currentFolder.setMaxSize(Integer.parseInt(value));
                                } catch(NumberFormatException ex) {
                                    throw new IOException("Illegal number in integer value",ex);
                                }
                            } else
                                logger.fine("Key \""+ key +"\" ignored on file " + index.getAbsolutePath());
                        }
                    } else if(commentPattern.matcher(line).matches()) {
                        //Ignore comments
                    } else
                        logger.info("Unknown line format : (line " + lineNumber + ") " + line);
                }
            } finally {                
                reader.close();
            }
        } else
            throw new IllegalArgumentException("Directory " + basedir.getAbsolutePath() + " doesn't contain an index.theme");
    }
    
    public URL findIconFor(final String iconName,final int preferredSize) {
        URL out = null;
        File [] fileList;
        // We'll first seek for an icon of the prefered size
        for(File dir:foldersInfo.keySet()) {
            IconThemeFolder folder = foldersInfo.get(dir);
            if(folder.getSize() == preferredSize) {
                fileList = dir.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        String name = pathname.getName();
                        return name.endsWith(".png") && name.substring(0,name.lastIndexOf('.')).equals(iconName);
                    }
                });
                if(fileList!= null && fileList.length >0) {
                    try {
                        //We found it!!
                        out = fileList[0].toURI().toURL();
                    } catch (MalformedURLException ex) {
                        //This shouldn't happen
                    }
                    break;
                }
            }
        }
        if(out == null) //We didn't find one of the requested size
        {
            for(File dir:foldersInfo.keySet()) {
                IconThemeFolder folder = foldersInfo.get(dir);
                fileList = dir.listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        String name = pathname.getName();
                        return name.endsWith(".png") && name.substring(0,name.lastIndexOf('.')).equals(iconName);
                    }
                });
                if(fileList!= null && fileList.length >0) {
                    try {
                        //We found it!!
                        out = fileList[0].toURI().toURL();
                    } catch (MalformedURLException ex) {
                        //This shouldn't happen
                    }
                    break;
                }
            }
            
        }
        return out;
    }
    public URL findIconForMime(String type,int size) {
        if(type == "") {
            logger.fine("Overwritting unknown mime type ");
            type = "misc";
        }
        logger.fine("Searching icon for mime type " + type);
        URL out = null;
        File [] fileList;
        FileFilterMimeType fileFilter = new FileFilterMimeType(type);
        // We'll first seek for an icon of the prefered size
        for(File dir:foldersInfo.keySet()) {
            IconThemeFolder folder = foldersInfo.get(dir);
            if(folder.getSize() == size && folder.getContext().equals("MimeTypes")) {
                fileList = dir.listFiles(fileFilter);
                if(fileList!= null && fileList.length >0) {
                    try {
                        //We found it!!
                        out = fileList[0].toURI().toURL();
                        logger.fine("Icon found : " + out);
                    } catch (MalformedURLException ex) {
                        //This shouldn't happen
                    }
                    break;
                }
            }
        }
        if(out == null) //We didn't find one of the requested size
        {
            for(File dir:foldersInfo.keySet()) {
                IconThemeFolder folder = foldersInfo.get(dir);
                if(folder.getContext().equals("MimeTypes")) {
                    fileList = dir.listFiles(fileFilter);
                    if(fileList!= null && fileList.length >0) {
                        try {
                            //We found it!!
                            out = fileList[0].toURI().toURL();
                            logger.fine("Icon found : " + out);
                        } catch (MalformedURLException ex) {
                            //This shouldn't happen
                        }
                        break;
                    }
                }
            }
            
        }
        if(out== null)
            logger.fine("In theme : "+ getThemeName() +" Icon not found for mimetype : " + type );
        return out;
    }
    public String getThemeName() {
        return themeName;
    }
    
    public String getThemeComment() {
        return themeComment;
    }
}

class FileFilterMimeType implements FileFilter {
    private List<String > typeparts;
    
    public FileFilterMimeType(String mimetype) {
        typeparts = new LinkedList<String>();
        StringTokenizer tok = new StringTokenizer(mimetype,"/");
        while(tok.hasMoreTokens()) {
            typeparts.add(tok.nextToken());
        }
    }
    public boolean accept(File pathname) {
        boolean ret = false;
        String name = pathname.getName();
        if(name.endsWith(".png")) {
            for(String part :typeparts) {
                if(part.startsWith("x-"))
                    part=part.substring(2);
                ret = name.substring(0,name.indexOf(".png")).equals(part);
                if(ret)
                    break;
            }
//            ret= name.substring(0,name.lastIndexOf('.')).equals(mimetype);
//            //We try with the right side of the mime type
//            if(ret == false && mimetype.indexOf('/')!=-1)
//                ret=name.substring(0,name.lastIndexOf('.')).equals(mimetype.substring(mimetype.lastIndexOf('/')+1));
//            //We try to see if the right side has an "x-" prefix
//            if(ret == false && mimetype.substring(mimetype.lastIndexOf('/')+1).startsWith("x-"))
//                ret=name.substring(0,name.lastIndexOf('.')).equals(mimetype.substring(mimetype.lastIndexOf("/x-")+1));
//            //We try with the left side of the mime type
//            if(ret == false && mimetype.indexOf('/')!=-1)
//                ret=name.substring(0,name.lastIndexOf('.')).equals(mimetype.substring(0,mimetype.lastIndexOf('/')));
        }
        return ret;
    }
    
}
