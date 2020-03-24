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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import org.jdesktop.lg3d.apps.googler.engine.events.ResultEvent;
import org.jdesktop.lg3d.apps.googler.engine.events.ResultEventListener;
import org.jdesktop.lg3d.apps.googler.engine.events.SearchErrorEvent;
import org.jdesktop.lg3d.apps.googler.engine.events.SearchEvent;
import org.jdesktop.lg3d.apps.googler.engine.events.SearchRunningLgEvent;
import org.jdesktop.lg3d.apps.googler.engine.internal.GoogleSearchBindingStub;
import org.jdesktop.lg3d.apps.googler.engine.internal.GoogleSearchResult;
import org.jdesktop.lg3d.apps.googler.engine.internal.GoogleSearchServiceLocator;
import org.jdesktop.lg3d.apps.googler.engine.internal.ResultElement;
import org.jdesktop.lg3d.displayserver.EventProcessor;
import org.jdesktop.lg3d.wg.Component3D;








/**
 * This class represents an independent Search thread, that will query google
 * and group the results that it gives.
 * @author opsi
 */
public class SearchThread extends Thread {    
    /**
     * Pattern used to find the pagerank of the elements.
     */
    private Pattern pattern = Pattern.compile("PageRank: ");
    /**
     * A flag indicating this thread must end as soon as possible
     */
    private boolean stop = false;
    /**
     * Stores this search parameters
     */
    private GoogleSearchParameters params;    
    /**
     * The eventlistener that will receive the results of computations done in 
     * this thread
     */
    private ResultEventListener receiver;
    /**
     * Holds all the results produced by this thread
     */
    private Vector<ResultElement> elements;
    
    
    /**
     * For logging purposes
     */
    private Logger logger = Logger.getLogger("apps.googler");
    /**
     * Flag indicating that this thread should live now
     */
    private boolean mustExit = false;    
    
    /**
     * Registers a new query for this thread, including the maximun number of
     * results to return, a optional string filter, and also stores a reference
     * to the results handler.
     * @param receiver The object that will receive the events generated in this thread
     * @param params Search parameters for this search
     */
    protected SearchThread(GoogleSearchParameters params,ResultEventListener receiver) {
        this.params = params;
        this.receiver = receiver;
        elements = new Vector();                    
    }
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        setName("Google Search "+getId());
        Component3D comp = new Component3D();
        EventProcessor.processor().postEvent(new SearchRunningLgEvent(true), comp);
        initSearch();
        EventProcessor.processor().postEvent(new SearchRunningLgEvent(false), comp);
    }
    /**
     * This method actually makes the query  to Google, and registers all results
     * in the receiver.
     * @return true if there are elements to group, false if not
     */
    private boolean initSearch() {
        GoogleSearchServiceLocator locator;
        GoogleSearchBindingStub search;
        GoogleSearchResult gresult;
        ResultElement []tmpresults;        
        
        int resultsLeft = params.getMaxResults();
        int maxPerQuery = resultsLeft > 10 ? 10:resultsLeft;
        
        try {
            locator = new GoogleSearchServiceLocator(
                    System.getProperty("lg.resourcedir")+"/googler/GoogleSearch.wsdl",
                    new QName("urn:GoogleSearch", "GoogleSearchService"));
            search = new GoogleSearchBindingStub(
                    new URL(locator.getGoogleSearchPortAddress()), locator);
            // TODO this is buggy, it will round down to the next ten
            while(resultsLeft>0) {
                gresult = search.doGoogleSearch(
                        params.getKey(), params.getQuery(), params.getInitial(), maxPerQuery, false,
                        "", false, params.getLanguage(), "", "");
                tmpresults = gresult.getResultElements();
                logger.fine("I will add results now = "+ resultsLeft);
                for(ResultElement tmpelem : tmpresults) {
                    if(resultsLeft-- <= 0)
                        return true;
                    if(stop)
                        return false;
                    elements.add(tmpelem);
                    logger.fine("Posting new element : "+tmpelem.getURL());
                    if(stop)
                        return false;
                    tmpelem.setPagerank(calculatePageRank(tmpelem.getURL()));
                    
                    if(stop)
                        return false;
                    postEvent(new ResultEvent(getId(), receiver, tmpelem));
                }
                params.setInitial(params.getInitial()+maxPerQuery);                
            }
        } catch(Exception ex) {
            logger.warning("Exception :"+ex.getMessage());
            ex.printStackTrace();
            postEvent(new SearchErrorEvent(receiver,ex, getId()));
            return false;
        }
        return true;
    }
    
    /**
     * This method connects to http://www.googlecommunity.com/scripts/pagerank.php
     * and extracts pageranks from there.
     * @param url The url whose PageRank we want to know
     * @return PageRank of the given URL, or 0 if it can't obtain the rank
     */
    private int calculatePageRank(String url) {
        int rank = 0;
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    new URL("http://www.googlecommunity.com/scripts/pagerank.php?url="+url).openConnection().getInputStream()));
            String line = in.readLine();                        
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            rank = Integer.parseInt(line.substring(matcher.end()));
        } catch(Exception e) {
            logger.warning("Failed to obtain pagerank : " + e.getMessage());;
            //e.printStackTrace();
            return rank;
        }
        return rank;
    }    
    /**
     * This method is called to send a given SearchEvent to the receiver
     * @param event Event to send
     */
    public void postEvent(SearchEvent event) {
        //EventProcessor.processor().postEvent(lgEvent,rSource);
        receiver.processEvent(event);
    }
    /**
     * Set the stop flag to true. The thread checks that flag frequently, and if
     * it's true ends it execution.
     */
    public synchronized void stopThread() {        
        stop = true;
    }
}