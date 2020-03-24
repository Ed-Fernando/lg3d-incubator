/*
 * AlbumInformation.java
 *
 * Created on 2005/11/12, 1:35
 *
 */

package org.jdesktop.lg3d.apps.trumplayer.base;

import org.jdesktop.lg3d.apps.trumplayer.mp3player.FileInfoBean;
import org.jdesktop.lg3d.apps.trumplayer.mp3player.M3UReader;

import java.util.AbstractList;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class AlbumInformation {
    
    private String artist;
    private String title;
    private String image;
    private String m3u;
    private boolean local = false;
    private AbstractList<FileInfoBean> playList = null;
    
    /** Creates a new instance of AlbumInformation */
    public AlbumInformation() {
    }

    public AlbumInformation(String artist, String title, String image, String m3u, boolean local){
        this.artist = artist;
        this.title = title;
        this.image = image;
        this.m3u = m3u;
        this.local = local;
    }
    
    public AlbumInformation(String artist, String title, String image, String m3u){
        this(artist,title,image,m3u,false);
    }
    
    public void setArtist(String artist){
        this.artist = artist;
    }

    public void setTitle(String title){
        this.title = title;
    }
    
    public void setImage(String image){
        this.image = image;
    }
    
    public void setM3U(String m3u){
        this.m3u = m3u;
    }
    
    public void setLocalCache(boolean local){
        this.local = local;
    }

    public String getArtist(){
        return artist;
    }

    public String getTitle(){
        return title;
    }
    
    public String getImage(){
        return image;
    }
    
    public String getM3U(){
        return m3u;
    }

    public boolean localCache(){
        return local;
    }

    public AbstractList<FileInfoBean> getPlayList(){
        return getPlayList("ISO-8859-1");
    }

    public AbstractList<FileInfoBean> getPlayList(String encode){
        if(playList == null){
            if( this.localCache() )
                playList = M3UReader.getPlayList( m3u, encode, "UTF8" );
            else
                playList = M3UReader.getPlayList( m3u, encode );
        }
        return playList;
    }

}
