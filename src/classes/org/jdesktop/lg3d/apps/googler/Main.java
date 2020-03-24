/***************************************************************************
 *   Copyright (C) 2005 by Juan Gonzalez                                   *
 *   juan@aga-system.com                                                   *
 *                                                                         *
 *   This program has been developed under Google's "Summer of Code 2005". *
 *   Special thanks goes to all people from Google and Sun Microsystems    *
 *   who made this cool experience a kind of success.                      *
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
 *   Main.java                                                             *
 *                                                                         *
 *   Created on July 6, 2005, 2:41 PM                                      *
 *                                                                         *
 ***************************************************************************/

package org.jdesktop.lg3d.apps.googler;
import org.jdesktop.lg3d.apps.googler.gui.GoogleThum;
import org.jdesktop.lg3d.apps.googler.gui.GooglerFrame3D;
import org.jdesktop.lg3d.wg.Toolkit3D;

/**
 * @todo Document this class
 * @author opsi
 */
public class Main{
    /** Creates a new instance of Main */
    private Main() {
        // Uncommenting the following will generate a lot of trash in log files
        //org.apache.log4j.BasicConfigurator.configure();
        GooglerFrame3D frame = new GooglerFrame3D();
        frame.setThumbnail(new GoogleThum(Toolkit3D.getToolkit3D().getScreenHeight()/2 * 0.15f));
        frame.setEnabled(true);
        frame.changeVisible(true);
    }
    public static void main(String [] args) {
        new Main();
    }
}
