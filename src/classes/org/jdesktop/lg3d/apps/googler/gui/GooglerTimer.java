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
 ***************************************************************************
 * GUITimer.java                                                           *
 *                                                                         *
 * Created on July 14, 2005, 5:24 PM                                       *
 *                                                                         *
 ***************************************************************************/

package org.jdesktop.lg3d.apps.googler.gui;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This is a single Timer that can be used all along the app to schedule tasks.
 * @author opsi
 */
public class GooglerTimer extends Timer{
    /**
     * Single-static timer for googler
     */
    private static GooglerTimer timer;
    static
    {
        timer = new GooglerTimer();           
    }    
    /**
     * This constructor is private to avoid multiplication of Timers on the app
     */
    private GooglerTimer() {        
    }
    /**
     * Returns a reference of the single-static Timer
     * @return timer
     */
    public static GooglerTimer getTimer()
    {
        return timer;
    }  
}