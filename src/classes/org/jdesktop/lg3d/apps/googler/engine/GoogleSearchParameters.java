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

/**
 * Encapsulates all the possible parameters for a Google search
 * @author opsi
 */
public class GoogleSearchParameters {
    /**
     * The key for the GoogleAPI service.     
     */
    private final String key = "No2SuwxQFHKW9Qb84hoASfPkltr1pehY";        
    /**
     * The query for Google
     */
    private String query;
    /**
     * The initial position in Google's rank from where we want to begin.
     */
    private int initial;
    /**
     * Maximun number of results for this query
     */
    private int maxResults;
    /**
     * A boolean tha indicates if we want to filter the results
     */
    private boolean filtered;
    /**
     * The string to filter our results
     */
    private String filter;
    /**
     * String that indicates the language preference for this search.
     * Valid values are
     * <table border=1 cellpadding=4 cellspacing=0>
     *     <tr>
     *         <td style="background-color: #fff9e4" valign=top><div align=center><font size=-1><b>Language</b></font></div></td>
     *         <td style="background-color: #fff9e4" valign=top><div align=center><font size=-1><b>&lt;lr&gt; value</b></font></div></td>
     *     </tr>
     *     <tr><td valign=top><font size=-1>Icelandic</font></td><td valign=top><font size=-1>lang_is</font></td></tr>
     *     <tr><td valign=top><font size=-1>Italian</font></td><td valign=top><font size=-1>lang_it</font></td></tr>
     *     <tr><td valign=top><font size=-1>Japanese</font></td><td valign=top><font size=-1>lang_ja</font></td></tr>
     *     <tr><td valign=top><font size=-1>Korean</font></td><td valign=top><font size=-1>lang_ko</font></td></tr>
     *     <tr><td valign=top><font size=-1>Latvian</font></td><td valign=top><font size=-1>lang_lv</font></td></tr>
     *     <tr><td valign=top><font size=-1>Lithuanian</font></td><td valign=top><font size=-1>lang_lt</font></td></tr>
     *     <tr><td valign=top><font size=-1>Norwegian</font></td><td valign=top><font size=-1>lang_no</font></td></tr>
     *     <tr><td valign=top><font size=-1>Portuguese</font></td><td valign=top><font size=-1>lang_pt</font></td></tr>
     *     <tr><td valign=top><font size=-1>Polish</font></td><td valign=top><font size=-1>lang_pl</font></td></tr>
     *     <tr><td valign=top><font size=-1>Romanian</font></td><td valign=top><font size=-1>lang_ro</font></td></tr>
     *     <tr><td valign=top><font size=-1>Russian</font></td><td valign=top><font size=-1>lang_ru</font></td></tr>
     *     <tr><td valign=top><font size=-1>Spanish</font></td><td valign=top><font size=-1>lang_es</font></td></tr>
     *     <tr><td valign=top><font size=-1>Swedish</font></td><td valign=top><font size=-1>lang_sv</font></td></tr>
     *     <tr><td valign=top><font size=-1>Turkish</font></td><td valign=top><font size=-1>lang_tr</font></td></tr>
     *     <tr><td valign=top><font size=-1>Arabic</font></td><td valign=top><font size=-1>lang_ar</font></td></tr>
     *     <tr><td valign=top><font size=-1>Chinese (S)</font></td><td valign=top><font size=-1>lang_zh-CN</font></td></tr>
     *     <tr><td valign=top><font size=-1>Chinese (T)</font></td><td valign=top><font size=-1>lang_zh-TW</font></td></tr>
     *     <tr><td valign=top><font size=-1>Czech</font></td><td valign=top><font size=-1>lang_cs</font></td></tr>
     *     <tr><td valign=top><font size=-1>Danish</font></td><td valign=top><font size=-1>lang_da</font></td></tr>			
     *     <tr><td valign=top><font size=-1>Dutch</font></td><td valign=top><font size=-1>lang_nl</font></td></tr>
     *     <tr><td valign=top><font size=-1>English</font></td><td valign=top> <font size=-1>lang_en</font></td></tr>
     *     <tr><td valign=top><font size=-1>Estonian</font></td><td valign=top><font size=-1>lang_et</font></td></tr>
     *     <tr><td valign=top><font size=-1>Finnish</font></td><td valign=top><font size=-1>lang_fi</font></td></tr>
     *     <tr><td valign=top><font size=-1>French</font></td><td valign=top><font size=-1>lang_fr</font></td></tr>
     *     <tr><td valign=top><font size=-1>German</font></td><td valign=top><font size=-1>lang_de</font></td></tr>
     *     <tr><td valign=top><font size=-1>Greek</font></td><td valign=top><font size=-1>lang_el</font></td></tr>
     *     <tr><td valign=top><font size=-1>Hebrew</font></td><td valign=top><font size=-1>lang_iw</font></td></tr>
     *     <tr><td valign=top><font size=-1>Hungarian</font></td><td valign=top><font size=-1>lang_hu</font></td></tr>
     * </table>
     */
    private String language;
    
    /** Creates a new instance of GoogleSearchParameters */
    public GoogleSearchParameters() {
        query = "";
        initial = 0;
        maxResults = 10;
        filtered = false;
        filter = "";
        language = "";
    }

    /**
     * Getter for query
     * @return The query associated
     */
    public String getQuery() {
        return query;
    }

    /**
     * Setter for query
     * @param query Value for the query associated
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Returns the initial position in the rank
     * @return Position of the first result in the rank for this search
     */
    public int getInitial() {
        return initial;
    }

    /**
     * Setter for initial
     * @param initial New value for initial
     */
    public void setInitial(int initial) {
        this.initial = initial;
    }

    /**
     * Obtains the maximun number of results for this search
     * @return Maximun number of results
     */
    public int getMaxResults() {
        return maxResults;
    }

    /**
     * Setter for maxResults
     * @param maxResults New value
     */
    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * Indicates if this search is filtered.
     * @return true if this search is filtered, false if not
     */
    public boolean isFiltered() {
        return filtered;
    }

    /**
     * Stablish if this search must be filtered
     * @param filtered Boolean indicating if we must filter(true) or not(false)
     */
    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    /**
     * Obtains the filter associated with this search.Remember to use setFiltered also.
     * @return The filter of this search
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Setter for the filter. Remember to use setFiltered also
     * @param filter New value for the filter
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Obtains the GoogleAPI key associated with this search
     * @return The key
     */
    public String getKey() {
        return key;
    }    

    /**
     * Obtains the language string for the query associated
     * @return Language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language for the query associated.     
     * 
     * @param language The language for this query
     */
    public void setLanguage(String language) {
        this.language = language;
    }    
    
    public boolean equals(Object other)
    {
        if(!(other instanceof GoogleSearchParameters))
            return false;
        GoogleSearchParameters otherp = (GoogleSearchParameters) other;
        return (getFilter()==otherp.getFilter() &&                 
                getLanguage()==otherp.getLanguage() && 
                getQuery() == otherp.getQuery() && 
                getMaxResults() == otherp.getMaxResults());
    }
}
