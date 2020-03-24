/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: DefaultSorter.java,v $
 *
 * Copyright (c) 2006, INoX Software Development Group, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.2 $
 * $Date: 2006-04-17 14:05:23 $
 * Author: E_INOUE
 */
 
package org.jdesktop.lg3d.apps.lgscope;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

/**
 * Sorter that supports ascending order and descending order. 
 * 
 */
public class DefaultSorter implements Sorter {
    
    private final String name;
    private final boolean isAscend;
    private Comparator comparator;
    
    public DefaultSorter(String name, boolean isAscend) {
        this.name = name;
        this.isAscend = isAscend;
        comparator = new DefaultComparator();
    }

    public String getName() {
        return name;
    }

    public File[] sort(File[] files) {
        
        // The directory and the file are divided. 
        Vector file = new Vector();
        Vector dir = new Vector();
        for (int i = 0; i < files.length; i++) {
            
            if (files[i].isDirectory()) {
                dir.add(files[i]);
            }
            else {
                file.add(files[i]);
            }
        }
        
        File[] fileList = new File[file.size()];
        file.copyInto(fileList);
        File[] dirList = new File[dir.size()];
        dir.copyInto(dirList);
        
        // Sorting is put. 
        Arrays.sort(fileList, comparator);
        Arrays.sort(dirList, comparator);
        
        File[] newList = new File[files.length];
        System.arraycopy(dirList, 0, newList, 0, dirList.length);
        System.arraycopy(fileList, 0, newList, dirList.length, fileList.length);
        
        return newList;
    }
    
    
    class DefaultComparator implements Comparator {
    
        public int compare(Object o1, Object o2) {
            return isAscend ? ((File) o1).compareTo((File) o2) : ((File) o2).compareTo((File) o1);
        }
           
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    };
}

