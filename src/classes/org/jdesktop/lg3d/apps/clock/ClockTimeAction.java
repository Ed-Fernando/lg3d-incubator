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
 *   Copyright (C) 2006 by Juan Gonzalez (juan@aga-system.com)             *
 *                                                                         *
 ***************************************************************************
 * ClockTimeAction.java
 *
 * Created on 9 de septiembre de 2006, 14:15
 *
 * $Revision: 1.1 $
 * $Date: 2006-09-09 16:47:16 $
 * $State: Exp $
 */

package org.jdesktop.lg3d.apps.clock;

import java.util.Calendar;
import java.util.TimerTask;
import org.jdesktop.lg3d.wg.Thumbnail;

public class ClockTimeAction extends TimerTask {
    ClockContainer local = null;
    Thumbnail cont = null;
    Clock clock = null;
    public ClockTimeAction(ClockContainer clk) {
        local = clk;
    }
    public ClockTimeAction(Thumbnail clk) {
        cont = clk;
    }
    public ClockTimeAction(Clock clk) {
        clock = clk;
    }
    public void run() {
        if(local!=null)
            local.setTime(Calendar.getInstance());
        if(cont != null)
            ((Clock)cont.getChild(0)).setTime(Calendar.getInstance());
        if(clock != null)
            clock.setTime(Calendar.getInstance());
    }
}
