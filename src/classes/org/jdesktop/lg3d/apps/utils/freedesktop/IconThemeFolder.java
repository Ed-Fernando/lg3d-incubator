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
 * IconThemeFolder.java
 *
 * Created on 18 de junio de 2006, 0:31
 *
 * $Revision: 1.3 $
 * $Date: 2006-08-17 23:22:18 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.utils.freedesktop;

/**
 *
 * @author opsi
 */
public class IconThemeFolder {
    private int size;
    private String context;
    private String type;
    private int maxSize;
    private int minSize;
    private int threshold;
    /** Creates a new instance of IconThemeFolder */
    public IconThemeFolder() {
        size = -1;
        maxSize = -1;
        minSize = -1;
        threshold = -1;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }        

    public String toString() {
        String out = "Size :" +size+"\n";
        out += "Context :" +context+"\n";
        out += "Type :" +type;
        return out;
    }
}
