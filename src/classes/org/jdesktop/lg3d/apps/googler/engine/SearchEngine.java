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
 ***************************************************************************/

package org.jdesktop.lg3d.apps.googler.engine;

import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Logger;
import org.jdesktop.lg3d.apps.googler.engine.events.ResultEventListener;
import org.jdesktop.lg3d.apps.googler.engine.GoogleSearchParameters;


/**
 * This is the main class for the Google search engine. All querys are made to
 * this class.
 *
 * @author opsi
 * @see SearchThread
 * @see ResultsEventListener
 */
public class SearchEngine{
    /**
     * The one and only engine that you will see. This is statically created, 
     * and all clients get a copy of it through getEngine
     */
    private static SearchEngine engine;
    /**
     * Holds references to all registered searchs.
     */
    private static Hashtable<Long,SearchThread> searchs;
    /**
     * The keys that are stored in {@link searchs}
     */
    private static Vector<Long> keys;
    /**
     * For logging purposes
     */
    private static Logger logger;
    static
    {
        searchs = new Hashtable();
        keys = new Vector();
        logger = Logger.getLogger("apps.googler.engine");
        engine = new SearchEngine();
    }
    /**
     * This protected constructor ensure that all clients get their reference to
     * the global engine
     */
    protected SearchEngine() {
        //We ensure there is only one engine for everybody
    }
    /**
     * Starts a new search in google. The search is processed in an independent thread
     * @return Returns the ID associated with the thread for this query
     * @param receiver Reference to the object that will process the results of
     * the search
     * @param params Parameters for this search
     */
    public synchronized long queryRequest(GoogleSearchParameters params,ResultEventListener receiver) {
        SearchThread newQuery= new SearchThread(params, receiver);                    
        newQuery.start();
        searchs.put(newQuery.getId(),newQuery);
        keys.add(newQuery.getId());
        return newQuery.getId();
    }
    /**
     * Stops the given thread as soon as possible
     * @param threadID Id of the thread to stop
     * @return True if the thread exists and was created by this object.
     */
    public synchronized boolean stopSearch(long threadID)
    {
        SearchThread toStop = searchs.get(threadID);
        if(toStop!=null)
        {
            toStop.stopThread();
            searchs.remove(threadID);
            return true;
        } else 
           return false;
    }
    /**
     * This is the only method to obtain a SearchEngine, you can't instantiate
     * this class.
     * @return The searchengine of this vm
     */
    public static SearchEngine getEngine() {
        return engine;
    }
}
