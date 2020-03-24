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
 *   SearchEvent.java                                                      *
 *                                                                         *
 *   Created on July 10, 2005, 9:08 PM                                     *
 *                                                                         *
 ***************************************************************************/


package org.jdesktop.lg3d.apps.googler.engine.events;



/**
 * Class used as root for SearchEvents hierarchy. Provides the basic methods to 
 * allow authenication of the receiver
 * @author opsi
 */
public abstract class SearchEvent {
    /**
     * The object that will receive this event
     */
    protected ResultEventListener receiver;
    /**
     * Id of the thread that generated this event
     */
    protected long threadId;
    /**
     * Checks if the given receiver is the correct one. A receiver not accepted 
     * by this method won't be able to extract anything from this event.
     * @param receiver The object that was to extract info from this event.
     * @return True if receiver is allowed to read this.
     */
    public final boolean checkAccess(ResultEventListener receiver) {
        return this.receiver == receiver;
    }
    /**
     * Returns the Id of the generating thread
     * @return threadId
     */
    public final long getThreadId() {
        return threadId;
    }
}
