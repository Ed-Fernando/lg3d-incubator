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
 * MimeTypes.java
 *
 * Created on 17 de junio de 2006, 13:55
 *
 * $Revision: 1.4 $
 * $Date: 2006-08-17 23:22:19 $
 * $State: Exp $
 */
package org.jdesktop.lg3d.apps.utils.freedesktop.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

/**
 *
 * @author opsi
 */
public class MimeTypes {
    private static final String MIME_MAGIC_HEADER="MIME-Magic\0";
    private static Logger logger;
    private static class MimeInfo {
        int priority;
        String name;
        public MimeInfo(int priority,String name) {
            this.priority = priority;
            this.name = name;
        }
    }
    static
    {
        logger = Logger.getLogger("lg.desktop");
    }
    /** Creates a new instance of MimeTypes */
    private MimeTypes() {
        
    }
    private static String matchInMagicFile(File path, File magicFile) throws IOException{
        int c,tmpPriority=50;
        String type = "",line,tmpMimeName="";
        RandomAccessFile raf = new RandomAccessFile(path,"r");
        List<MimeInfo> infos = new ArrayList<MimeInfo>();
        
        if(magicFile.canRead()) {
            BufferedReader reader = new BufferedReader(new FileReader(magicFile));
            try {
                line = reader.readLine();
                if(!MIME_MAGIC_HEADER.equals(line))
                    logger.warning("The file " + magicFile.getAbsolutePath() + " is supposed to be a mime magic file, but doesn't have MIME-Magic header");
                else {
                    while((line = reader.readLine())!=null) {
                        //This line should have the priority and mime type
                        if(line.startsWith("[") && line.endsWith("]")) {
                            StringTokenizer tok = new StringTokenizer(line,"[]:");
                            if(tok.countTokens()==2)
                                tmpPriority = Integer.parseInt(tok.nextToken());
                            else
                                tmpPriority=50;
                            tmpMimeName = tok.nextToken();
//                        System.err.println("name = " + tmpMimeName);
//                        if(tmpMimeName.equals("image/png"))
//                            System.err.println("PNG");
                        } else if(line.startsWith(">")){
                            StringTokenizer tok = new StringTokenizer(line,">=+");
                            int offset = Integer.parseInt(tok.nextToken());
                            String tmp = tok.nextToken();
                            if(tmp.length()>2)
                                tmp = tmp.substring(2);
                            int region = tmp.length();
                            if(tok.hasMoreTokens()) {
                                try {
                                    region = Integer.parseInt(tok.nextToken());
                                } catch (NumberFormatException ex) {
                                    region = tmp.length();
                                }
                            }
                            if(raf.length()>= offset+region) {
                                byte [] bytes = new byte[tmp.length()];
                                int i = 0;
                                while(i <=  region-tmp.length()) {
                                    raf.seek(offset+i);
                                    if(raf.read(bytes)!=-1 && tmp.equals(new String(bytes))) {
                                        //logger.info("Hit:" + tmpMimeName);
                                        infos.add(new MimeInfo(tmpPriority,tmpMimeName));
                                        break;
                                    }
                                    i++;
                                }
                            }
                        }
                    }
                }
            } finally {
                reader.close();
            }
            int maxPriority = 0;
            MimeInfo def = null;
            for(MimeInfo info:infos) {
                if(info.priority > maxPriority)
                    def = info;
            }
            if(def != null) {
                type = def.name;
            }
        }
        return type;
    }
    private static String magicSearch(File path) throws IOException {
        File magicFile = new File(XDG.getValue(XDG.DIR.XDG_DATA_HOME)+"mime/magic");
        String type = "";
        if(magicFile.canRead()) {
            type = matchInMagicFile(path, magicFile);
        }
        if(type.length()==0) {
            StringTokenizer dirTokenizer = new StringTokenizer(XDG.getValue(XDG.DIR.XDG_DATA_DIRS),":");
            while(type.length()==0 && dirTokenizer.hasMoreTokens()) {
                magicFile  = new File(dirTokenizer.nextToken()+"mime/magic");
                type = matchInMagicFile(path, magicFile);
            }
        }
        return type;
    }
    private static String patternSearch(File path) throws IOException {
        String type = "", pattern, line;
        BufferedReader reader;
        StringTokenizer lineTokenizer,dirTokenizer;
        
        //We are initially using pattern based match
        File globFile = new File(XDG.getValue(XDG.DIR.XDG_DATA_HOME)+"mime/globs");
        if(globFile.canRead()) {
            reader = new BufferedReader(new FileReader(globFile));
            try {
                while((line = reader.readLine())!=null) {
                    if(!line.startsWith("#")) {
                        lineTokenizer = new StringTokenizer(line,":");
                        type = lineTokenizer.nextToken();
                        pattern = lineTokenizer.nextToken();
                        if(path.getName().endsWith(pattern.substring(1)))
                            break;
                        else
                            type = "";
                    }
                }
            } finally {
                reader.close();
            }
        }
        if(type.length()==0) {
            dirTokenizer = new StringTokenizer(XDG.getValue(XDG.DIR.XDG_DATA_DIRS),":");
            while(type.length()==0 && dirTokenizer.hasMoreTokens()) {
                globFile = new File(dirTokenizer.nextToken()+"mime/globs");
                if(globFile.canRead()) {
                    reader = new BufferedReader(new FileReader(globFile));
                    try {
                        while((line = reader.readLine())!=null) {
                            if(!line.startsWith("#")) {
                                lineTokenizer = new StringTokenizer(line,":");
                                type = lineTokenizer.nextToken();
                                pattern = lineTokenizer.nextToken();
                                if(path.getName().endsWith(pattern.substring(1)))
                                    break;
                                else
                                    type = "";
                            }
                        }
                    } finally {
                        reader.close();
                    }
                }
            }
        }
        return type;
    }
    public static String getType(File path) throws IOException {
        if(!path.canRead())
            return "misc";
        String output = patternSearch(path);
        if(output .length() == 0)
            output = magicSearch(path);
        logger.fine("Mime type for " + path.getName() + " is " + output);
        if(output==null || output.length()==0)
            output = "misc";
        return output;
    }
}
