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
 *   SearchErrorEvent.java                                                 *
 *                                                                         *
 *   Created on July 20, 2005, 2:24 PM                                     *
 *                                                                         *
 ***************************************************************************/

package org.jdesktop.lg3d.apps.googler.engine.events;

/**
 * Encapsulates an error that has happened during search so the receiver could
 * handle it, and inform user
 * @author opsi
 */
public class SearchErrorEvent extends SearchEvent{
    /**
     * The exception thrown by the error
     */
    private Exception excp;
    /**
     * The handler of this event
     */
    private ResultEventListener receiver;
    /**
     * Creates a new SearchErrorEvent with the given contents
     * @param receiver Receiver for this event
     * @param excp Exception to propagate
     * @param threadId Id of the generating thread
     */
    public SearchErrorEvent(ResultEventListener receiver,Exception excp, long threadId) {
        super();
        this.threadId = threadId;
        this.excp = excp;
        this.receiver = receiver;
    }
    /**
     * Extracts the contained Exception
     * @param receiver The object that wants to extract the exception
     * @return If the receiver is correct, the encapsulated exception, else null
     */
    public Exception getException(ResultEventListener receiver)
    {
        return this.receiver.equals(receiver) ? excp:null;
    }
}
