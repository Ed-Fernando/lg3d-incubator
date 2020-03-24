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
 *   ResultsEventListener.java                                                *
 *                                                                         *
 *   Created on July 6, 2005, 9:52 PM                                      *
 *                                                                         * 
 ***************************************************************************/


package org.jdesktop.lg3d.apps.googler.engine.events;
import org.jdesktop.lg3d.apps.googler.engine.internal.ResultElement;

/**
 * This is the interface that must implement all the objects who want to require
 * SearchEngine to make a query.
 * @author opsi
 * @todo this interface was initially defined in a LgEvent environment, but this
 * approach was abandoned, so it must be rewritten to have clearest names.
 */
public interface ResultEventListener {
    /**
     * Adds a new ResultElement to this object.
     * @param newElement element to add
     */
    public void addResultElement(ResultElement newElement);
    /**
     * Process searchevents. This class is expected to handle all subclasses of 
     * SearchEvent.
     * @param sevt Event to process
     */
    public void processEvent(SearchEvent sevt);
}


