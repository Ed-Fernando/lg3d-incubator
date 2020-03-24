/***************************************************************************
 *   Copyright (C) 2005 by Juan Gonzalez                                   *
 *   juan@aga-system.com                                                   *
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
 ***************************************************************************/

package org.jdesktop.lg3d.apps.clock;

import java.util.Calendar;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;

/**
 *
 * @author opsi
 */
public class MixedClock extends Clock {
    private AnalogicClock analogic;
    private DigitalClock digital;
    /** Creates a new instance of MixedClock */
    public MixedClock(float width) {
        analogic = new AnalogicClock(width);
        digital = new DigitalClock(width,new SimpleAppearance(0.66f,0.2f,1.0f,0.9f));
        addChild(analogic);
        addChild(digital);
    }        
    public void setTime(Calendar currentDate)
    {
        analogic.setTime(currentDate);
        digital.setTime(currentDate);
    }
}
