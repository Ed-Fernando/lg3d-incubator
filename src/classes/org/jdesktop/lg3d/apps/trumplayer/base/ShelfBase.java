/*
 * ShelfBase.java
 *
 * Created on 2005/11/02, 14:20
 *
 */

package org.jdesktop.lg3d.apps.trumplayer.base;

import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;

import org.jdesktop.lg3d.apps.trumplayer.base.AlbumInformation;

/**
 *
 * @author Yasuhiro Fujitsuki (thaniwa)
 */
public abstract class ShelfBase extends Container3D {
    
    protected AlbumInformation[] albuminfo;
    protected String fontname;
    protected String encode;
    protected Manager manager = null;

    /**
     * Use ShelfBase.initialize() to make a AlbumBase object.
     * In this time, AlbumBase object has no Manager object, 
     * so set a Manager object to an AlbumBase object in this method.
     */
    public void addChild(AlbumBase album){
        super.addChild(album);
        album.setManager(manager);
    }

    /** set a Manager object */
    public void setManager(Manager manager){
        this.manager = manager;
    }
    
    /** get a Manager object */
    public Manager getManager(){
        return manager;
    }

    /** initialize */
    public void init(AlbumInformation[] albuminfo){
        init(albuminfo,null,null);
    }

    /** initialize */
    public void init(AlbumInformation[] albuminfo, String fontname){
        init(albuminfo,fontname,null);
    }
    
    /** initialize */
    public void init(AlbumInformation[] albuminfo, String fontname,String encode){
        this.albuminfo = albuminfo;
        if(encode != null && encode.trim().equals("") == false)
            this.encode = encode;
        if(fontname != null && fontname.trim().equals("") == false)
            this.fontname = fontname;
        initialize();
    }

    /** get AlbumInformation[] */
    public AlbumInformation[] getAlbumInformation(){ return albuminfo; }
    /** get Font name */
    public String getFontName(){ return fontname; }
    /** get charactor encode information */
    public String getEncode(){ return encode; }
    
    /** initialize */
    public abstract void initialize();

}
