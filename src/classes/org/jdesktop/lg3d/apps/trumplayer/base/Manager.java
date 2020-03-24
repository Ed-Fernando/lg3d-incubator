/*
 * Manager.java
 *
 * Created on 2005/11/06, 16:52
 *
 */

package org.jdesktop.lg3d.apps.trumplayer.base;

import org.jdesktop.lg3d.apps.trumplayer.mp3player.FileInfoBean;

import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Component3D;

import java.util.AbstractList;


/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class Manager {

    private PlayerBase player;
    private ShelfBase shelf;
    private Frame3D frame;
    private AlbumBase currentAlbumBase;
        
    private AlbumInformation[] albumInformations;
    private AlbumInformation currentAlbumInformation;
    private String encode;
    private String fontname = "Serif";

    
    public Frame3D getFrame3D(){
        if(frame == null)
            frame = new Frame3D();
        return frame;
    }
    
    public PlayerBase makePlayer(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class c = Class.forName(name);
        PlayerBase p = (PlayerBase)c.newInstance();
        p.setManager(this);
        // p.init(fontname,encode);
        player = p;
        return player;
    }
    
    public void initializePlayer(){
        player.init(fontname,encode);
    }
    
    public ShelfBase makeShelf(String name, AlbumInformation[] albumInformations) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class c = Class.forName(name);
        ShelfBase s = (ShelfBase)c.newInstance();
        s.setManager(this);
        this.albumInformations = albumInformations;
        // s.init(albumInformations, fontname, encode);
        shelf = s;
        return shelf;
    }

    public void initializeShelf(){
        shelf.init(albumInformations, fontname, encode);
    }
    
    public ShelfBase getShelfBase(){ return shelf; }
    public PlayerBase getPlayerBase(){ return player; }
    public AlbumBase getCurrentAlbumBase(){ return this.currentAlbumBase; }

    public void setEncode(String encode){
        if(encode != null && encode.equals(""))
            this.encode = null;
        else
            this.encode = encode;
    }

    public void setFontName(String font){
        if(font != null && font.equals(""))
            this.fontname = "Serif";
        else
            this.fontname = font;
    }
    
    public void setCurrentAlbumInformation(AlbumInformation current){
        currentAlbumInformation = current;
    }
    
    /**
     * get a LG3DMP3Player object
     */
    public LG3DMP3Player getPlayer(){
        return player.getPlayer();
    }

    protected void close(){
        player.getPlayer().stop();
        frame.changeEnabled(false);
    }

    protected void minimize(){
        frame.changeVisible(false);
    }

    /**
     * get location of Frame3D object
     */
    public Vector3f getFrameLocation(){
        Vector3f loc = new Vector3f();
        frame.getFinalTranslation(loc);
        return loc;        
    }

    /**
     * get location of PlayerBase object
     */
    public Vector3f getPlayerLocation(){
        Vector3f loc = new Vector3f();
        player.getFinalTranslation(loc);
        return loc;        
    }

    /**
     * get location of ShelfBase object
     */
    public Vector3f getShelfLocation(){
        Vector3f loc = new Vector3f();
        shelf.getFinalTranslation(loc);
        return loc;
    }

    public boolean inPlayerArea(float x, float y) {
        Vector3f frameLoc = getFrameLocation();
        Vector3f shelfLoc = getShelfLocation();
        float cx = x-frameLoc.x-shelfLoc.x;
        float cy = y-frameLoc.y-shelfLoc.y;
        return player.inPlayerArea(cx,cy);
    }

    public void changePlayList(AlbumBase album){
        AbstractList<FileInfoBean>list = album.getPlayList();
        getPlayer().play(list);
        changeAlbumBase(album);
    }
    
    public void changeAlbumBase(AlbumBase album){
        if(currentAlbumBase != null)
            currentAlbumBase.setDefaultColor();
        if(album != null){
            album.setSpecialColor();
            setCurrentAlbumInformation(album.getAlbumInformation());
            // System.out.println(album.getAlbumInformation().getArtist());
        }
        currentAlbumBase = album;
    }
    
    public AlbumInformation getCurrentAlbumInformation(){
        return currentAlbumInformation;
    }
    
}
