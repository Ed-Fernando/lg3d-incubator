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
 *
 * This file was originally auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 29, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package org.jdesktop.lg3d.apps.googler.engine.internal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import org.jdesktop.lg3d.apps.googler.engine.*;

/**
 * This is the interface used to define the remote object which actually does the search
 */
public interface GoogleSearchPort extends Remote {
    /**
     * Obtains the google's snapshot of the given url if it exists
     * @param key The GoogleAPI developer key
     * @param url The web we want to get from google's cache
     * @return A byte array with the info from google
     * @throws java.rmi.RemoteException Exception from rmi
     */
    public byte[] doGetCachedPage(String key, String url) throws RemoteException;
    /**
     * Request a spelling suggestion from google
     * @param key GoogleAPI developer's key
     * @param phrase The phrase we want Google to scan
     * @throws java.rmi.RemoteException Exception from rmi
     * @return The spelling suggestion from Google, null if none
     */
    public String doSpellingSuggestion(String key, String phrase) throws RemoteException;
    /**
     * Method to request a search query from google
     * @param key Provided by Google, this is required for you to access the Google service. Google uses the key for authentication and logging.
     * @param q Query for Google
     * @param start Zero-based index of the first desired result.
     * @param maxResults Number of results desired per query. The maximum value per query is 10.
     * @param filter Activates or deactivates automatic results filtering, which hides very similar results and results that all come from the same Web host.
     * @param restrict Restricts the search to a subset of the Google Web index, such as a country like "Ukraine" or a topic like "Linux." See http://www.google.com/apis/reference.html#2_4
     * @param safeSearch A Boolean value which enables filtering of adult content in the search results.
     * @param lr Language Restrict - Restricts the search to documents within one or more languages.
     * @param ie Input Encoding - this parameter has been deprecated and is ignored. All requests to the APIs should be made with UTF-8 encoding.
     * @param oe Output Encoding - this parameter has been deprecated and is ignored. All requests to the APIs should be made with UTF-8 encoding.
     * @throws java.rmi.RemoteException Exception from rmi
     * @return The results from this search
     */
    public GoogleSearchResult doGoogleSearch(String key, String q, int start, int maxResults, boolean filter, String restrict, boolean safeSearch, String lr, String ie, String oe) throws RemoteException;
}
