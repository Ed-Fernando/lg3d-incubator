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
 * FileDesktopEntry.java
 *
 * Created on 3 de junio de 2006, 15:30
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author opsi
 */
public class FileDesktopEntry implements DesktopEntry{
    private static final String DESKTOP_ENTRY_CATEGORY = "Desktop Entry";
    private Map<String,Map<String,String>> categories;
//    private Pattern catPattern,keyPattern;
    private Logger logger;
    private String filePath;
    private boolean isValidEntry;
    private final String[] requiredKeys = new String[]{"Type","Encoding","Name"};
    /**
     * Does nothing
     **/
    public FileDesktopEntry() {
        
    }
    /**
     * Initializes local variables
     */
    public FileDesktopEntry(File file) throws IOException{
        logger = Logger.getLogger("lg3d.desktop");
        categories = new HashMap<String,Map<String,String>>();
        
//        catPattern = Pattern.compile("\\[[^ ].*\\]");
//        keyPattern = Pattern.compile("[^ ].*=.*[^ ]");
        
        isValidEntry = false;
        fromFile(file);
    }
    /**
     * Loads the desktop entry that's in the given path.
     * @param path Path to the given Desktop Entry
     * @see FileDesktopEntry#fromFile(File path)
     * @throws java.io.IOException if the file cannot be read or doesn't contain a valid Desktop Entry.
     */
    public void fromFile(String path) throws IOException {
        fromFile(new File(path));
    }
    /**
     * Loads the desktop entry that's in the given path. It reads from the file line a line checking
     * that the first line (ignoring comments) is the [Desktop Entry] category and that all keys have a value.
     * It adds each key/value pair in the corresponding category.
     * @param file The file to be loaded
     * @throws java.io.IOException if the file cannot be read or doesn't contain a valid Desktop Entry.
     */
    public void fromFile(File file) throws IOException {
        int lineNumber = 1;
        String category = DESKTOP_ENTRY_CATEGORY,key,value;
        StringTokenizer tokenizer;
        isValidEntry = false;
        
        if(!file.canRead())
            throw new IOException("Cannot read file : " + file.getPath());
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            
            String line = reader.readLine();
            while(line != null && line.startsWith("#"))
                line = reader.readLine();
            
            if(line == null || !line.trim().equals("["+category+"]"))
                throw new IOException(
                        "Not a valid Desktop Entry file (Missing [Desktop Entry] category):"+
                        file.getAbsolutePath()+":"+
                        lineNumber);
            //We have the [Desktop Entry] category here
            addCategory(category);
            while((line=reader.readLine())!=null) {
                lineNumber++;
                line = line.trim();
                if(!line.startsWith("#")) {
                    //if(catPattern.matcher(line).matches()) {
                    if(line.startsWith("[") && line.endsWith("]")) {
                        category = line.substring(1,line.length()-1);
                        addCategory(category);
//                } else if(keyPattern.matcher(line).matches()) {
                    } else if(line.indexOf('=')!=-1 && line.indexOf('=') == line.lastIndexOf('=') && line.length() > line.indexOf('=')+1) {
                        tokenizer = new StringTokenizer(line,"=");
                        key = tokenizer.nextToken();
                        value = tokenizer.nextToken();
                        addToCategory(category,key,value);
                    }else
                        logger.fine("Unrecognized line format : " + file.getAbsolutePath() +":"+lineNumber+" \""+line+"\"");
                }
            }
            filePath = file.getAbsolutePath();
            isValidEntry = checkSanity();
        } finally {
            reader.close();
        }
    }
    public String getFilePath() {
        return filePath;
    }
    public String toString() {
        StringBuffer buf = new StringBuffer("File : " + getFilePath() + "\n");
        for(String catName : getCategories()) {
            buf.append("[" + catName + "]\n");
            for(String key : getLocalKeys(catName)) {
                buf.append(key+"="+getValueFor(catName,key,Locale.getDefault())+"\n");
            }
        }
        return buf.toString();
    }
    public Set<String> getCategories() {
        return categories.keySet();
    }
    public Set<String> getKeys() {
        return getKeys(DESKTOP_ENTRY_CATEGORY);
    }
    public Set<String> getKeys(String category) {
        if(!categories.containsKey(category))
            throw new IllegalArgumentException(category + " category doesn't exist");
        return categories.get(category).keySet();
    }
    public String getValueFor(String key) {
        return getValueFor(DESKTOP_ENTRY_CATEGORY,key,null);
    }
    public String getValueFor(String key,Locale locale) {
        return getValueFor(DESKTOP_ENTRY_CATEGORY,key,locale);
    }
    public String getValueFor(String category,String key,Locale locale) {
        
        String value = null;
        Map<String,String> catMap;
        if(!categories.containsKey(category))
            throw new IllegalArgumentException(category + " category doesn't exist");
        catMap = categories.get(category);
        if(locale==null) {
            if(catMap.containsKey(key))
                value = catMap.get(key);
        } else {//locale!=null
            if(catMap.containsKey(key+"["+locale.getLanguage()+"]"))
                value = catMap.get(key+"["+locale.getLanguage()+"]");
            else if(catMap.containsKey(key))
                value = catMap.get(key);
        }
        return value;
    }
    public Set<String> getLocalKeys() {
        return getLocalKeys(DESKTOP_ENTRY_CATEGORY);
    }
    public Set<String> getLocalKeys(String category) {
        if(!categories.containsKey(category))
            throw new IllegalArgumentException(category + " category doesn't exist");
        Set<String> out = new HashSet();
        Pattern pattern = Pattern.compile(".*\\[.*\\]");
        for(String key : categories.get(category).keySet()) {
            if(!pattern.matcher(key).matches())
                out.add(key);
        }
        return out;
    }
    public boolean isValid() {
        return isValidEntry;
    }
    private void addCategory(String newCategory) {
        if(categories.containsKey(newCategory))
//            throw new IllegalArgumentException(newCategory + " category already exists");
            return;
        categories.put(newCategory,new HashMap<String,String>());
    }
    private void addToCategory(String category,String key,String value) {
        if(!categories.containsKey(category))
            throw new IllegalArgumentException(category + " category doesn't exist");
//            return;
        Map<String,String> catMap = categories.get(category);
        if(catMap.containsKey(key))
//            throw new IllegalArgumentException(category + " category already contains a key named " + key);
            return;
        catMap.put(key,value);
    }
    private boolean checkSanity() {
        boolean output = true;
        if(!getCategories().contains("Desktop Entry"))
            output=false;
        Set<String> keys = getKeys();
        for(String req: requiredKeys) {
            if(!keys.contains(req))
                output=false;
        }
        return output;
    }
    
    public void performAction() {
        String executable = getValueFor("Exec");
        if(executable != null) {
            try {
                Runtime.getRuntime().exec(executable);
            } catch (IOException ex) {
                logger.warning("It's not possible to execute the file : "+ex.getMessage());
            }
        }
    }
    
    public String getName(Locale locale) {
        return getValueFor("Name",locale);
    }
    
    public String getIconName() {
        return getValueFor("Icon");
    }
    
    public String[] getDesktopCategories() {
        String [] output = null;
        String cats = getValueFor("Categories");
        if(cats!=null)
            output = cats.split(";");
        return output;
    }
}
