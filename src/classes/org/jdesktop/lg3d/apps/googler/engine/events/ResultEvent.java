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
 *   ResultEvent.java                                                      *
 *                                                                         *
 *   Created on July 6, 2005, 10:38 PM                                     *
 *                                                                         *
 ***************************************************************************/

package org.jdesktop.lg3d.apps.googler.engine.events;

import org.jdesktop.lg3d.apps.googler.engine.internal.ResultElement;


/**
 * This event transports a ResultElement from the searchengine to the receiver
 * that requested the search
 * @author opsi
 */
public class ResultEvent extends SearchEvent{    
    /**
     * The element the is been transported
     */
    private ResultElement element;    
    /**
     * Creates a new instance of ResultEvent
     * @param receiver Event listener that will be allowed to extract the element
     * @param element The element to encapsulate
     */
    public ResultEvent(long threadId, ResultEventListener receiver,ResultElement element) {
        super();
        this.threadId = threadId;
        this.receiver = receiver;
        this.element = element;
    }
    /**
     * Method to extract the encapsulated ResultElement from this event
     * @param receiver Event listener that wants to extract the element
     * @return element if the receiver is the correct one, null if not
     */
    public ResultElement getResult(ResultEventListener receiver)
    {
        return this.receiver.equals(receiver) ? element:null;
    }    
}
