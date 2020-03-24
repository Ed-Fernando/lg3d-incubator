/*
 * AlbumSearch.java
 *
 * Created on 2006/08/15, 10:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.lg3d.apps.trumplayer.base;

import java.util.Vector;

/**
 *
 * @author Yasuhiro Fujitsuki (thaniwa)
 */
public class AlbumSearch {

    private static AlbumSearch albumSearch;
    private AlbumInformation[] base;
    
    /** Creates a new instance of AlbumSearch */
    private AlbumSearch() {
    }
    
    private AlbumSearch(AlbumInformation[] base){
        this.base = base;       
    }
    
    public static AlbumSearch getAlbumSearch(){
        if(albumSearch == null)
            albumSearch = new AlbumSearch();
        return albumSearch;
    }

    public static AlbumSearch getAlbumSearch(AlbumInformation[] base){
        AlbumSearch search = getAlbumSearch();
        search.setBaseAlbumInformations(base);
        return albumSearch;
    }

    public void setBaseAlbumInformations(AlbumInformation[] base){
        this.base = base;
    }
    
    public AlbumInformation[] search(String artist, String album){

        Vector<AlbumInformation> tmp = new Vector<AlbumInformation>();
        Vector<AlbumInformation> result = new Vector<AlbumInformation>();        
        
        if(base == null)
            return null;

        // System.out.println("Search");
        // System.out.println("Artist : " + artist);
        // System.out.println("Album : " + album);
        
        // artist name search
        for(int i=0; i < base.length; i++){
            if( artist == null || artist.trim().equals("") || 
                    base[i].getArtist().toLowerCase().indexOf( artist.trim().toLowerCase() ) >= 0 )
                tmp.add(base[i]);
        }
        
        // album name search
        if( album == null || album.trim().equals(""))
            result = tmp;
        else {
            for( int i=0; i < tmp.size(); i++ ){
                AlbumInformation a = tmp.get(i);
                if( a.getTitle().toLowerCase().indexOf( album.trim().toLowerCase() ) >= 0 )
                    result.add(a);
            }
        }
        
        if(result.size() == 0)
            return null;
        
        // System.out.println("Find Albums : " + result.size());
        
        AlbumInformation[] rValue = new AlbumInformation[result.size()];
        result.toArray(rValue);

        return rValue;
    }
}
