/*
 * PlayerBase.java
 *
 * Created on 2005/11/04, 15:14
 *
 */

package org.jdesktop.lg3d.apps.trumplayer.base;

import org.jdesktop.lg3d.apps.trumplayer.mp3player.FileInfoBean;
import org.jdesktop.lg3d.apps.trumplayer.base.LG3DMP3Player;
import org.jdesktop.lg3d.apps.trumplayer.mp3player.M3UReader;
import org.jdesktop.lg3d.apps.trumplayer.mp3util.ID3Tag;

import java.util.AbstractList;

import javax.vecmath.Vector3f;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.utils.shape.Disc;
import org.jdesktop.lg3d.utils.shape.Cylinder;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.Text2D;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;

import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.Thumbnail;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public abstract class PlayerBase extends Container3D {

    // MP3 player object for trumplayer(LG3D)
    protected LG3DMP3Player mp3player;

    // charactor encode information
    protected String encode = null;
    
    // font name
    protected static String fontName ="Serif";

    // manager
    protected Manager manager = null;

    public void setManager(Manager manager){
        this.manager = manager;
    }
    
    public Manager getManager(){
        return manager;
    }
    
    public LG3DMP3Player getPlayer(){
        return mp3player;
    }

    protected void init(){
        initialize();
    }
    
    protected void init(String fontName){
        this.fontName = fontName;
        initialize();
    }
    
    protected void init(String fontName, String encode){
        if(encode != null && encode.trim().equals("") == false)
            this.encode = encode;
        if(fontName != null && fontName.trim().equals("") == false)
            this.fontName = fontName;
        this.mp3player = new LG3DMP3Player(encode);
        initialize();
    }
    
    protected abstract void initialize();
    
    public abstract boolean inPlayerArea(float x, float y);
    
    public void setFontName(String fontName){
        if(fontName != null && fontName.trim().equals("") == false)
            this.fontName = fontName;
    }
    
    public String getFontName(){
        return fontName;
    }
    
    public Thumbnail getThumbnail(){
        return null;
    }
    
    public String getEncode(){ return encode; }
    
    public void setEncode(String encode){
        if(encode != null && encode.trim().equals("") == false)
            this.encode = encode;
    }
}
