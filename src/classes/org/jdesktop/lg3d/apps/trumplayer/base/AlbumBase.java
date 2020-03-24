/*
 * AlbumBase.java
 *
 * Created on 2005/10/23, 22:53
 *
 */

package org.jdesktop.lg3d.apps.trumplayer.base;

import javax.vecmath.Vector3f;
import javax.vecmath.Color3f;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;

import org.jdesktop.lg3d.utils.action.ActionFloat2;
import org.jdesktop.lg3d.utils.eventadapter.MouseDraggedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.EventAdapter;
import org.jdesktop.lg3d.wg.event.MouseDraggedEvent3D;
import org.jdesktop.lg3d.wg.event.LgEvent;

import java.io.IOException;
import java.io.FileNotFoundException;

import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.shape.Text2D;
import org.jdesktop.lg3d.sg.ColoringAttributes;
 
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;

import org.jdesktop.lg3d.apps.trumplayer.mp3player.FileInfoBean;
import org.jdesktop.lg3d.apps.trumplayer.mp3util.ID3Tag;
import java.util.AbstractList;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public abstract class AlbumBase extends Component3D {

    // current position
    protected Vector3f translation;
    
    // informations of CD
    protected String jacket;
    protected String title;
    protected String artist;
    protected AbstractList<FileInfoBean> fileList;
    protected String[] playList;
    
    /**
     * a flag wheter Album Component can execute Drag and Drop Action
     */
    protected boolean dragAndDrop = false;
    private boolean dragged = false;
    
    /**
     * set Mouse Position
     * When Album Component is dragged and dropped,
     * Album Component set mouseX and mouseY as Dragged Position
     *
     */
    protected float mouseX,mouseY;

    // font name
    protected String fontName ="Serif";

    /**
     * Creates a new instance of AlbumBase 
     */
    public AlbumBase(){
        this(null);
    }
    
    /**
     * Creates a new instance of AlbumBase 
     */
    public AlbumBase(String fontName) {
        if(fontName != null && fontName.trim().equals("") == false)
            this.fontName = fontName;
        this.fontName = fontName;
        init();
    }

    protected Manager manager = null;

    public void setManager(Manager manager){
        this.manager = manager;
    }
    
    public Manager getManager(){
        return manager;
    }

    /**
     * initialize AlbumBase Component
     * add Drag and Drop event automatically
     *
     */
    public void init(){
        initialize();

        // move item 
        MouseDraggedEventAdapter draggedAdapter =
                new MouseDraggedEventAdapter(ButtonId.BUTTON1, new DraggedAction(this));
        addListener(draggedAdapter);

        MouseReleasedEventAdapter releasedAdapter =
                new MouseReleasedEventAdapter( new DroppedAction(this) );
        addListener(releasedAdapter);

        //setMouseEventPropagatable(true);
        setMouseEventPropagatable(false);
        
    }
    
    /**
     * Initialize of Album component
     * Implement GUI parts of Album Component
     *
     */
    protected abstract void initialize();
    
    /** 
     * set default position(translation) of Album Component 
     */
    public void setDefaultTranslation(Vector3f translation){
        this.translation = translation;
    }
    
    /** 
     * set default position(translation) of Album Component 
     */
    public void setDefaultTranslation(float x,float y, float z){
        setDefaultTranslation(new Vector3f(x,y,z));
    }
    
    /** 
     * get default position(translation) of Album Component 
     */
    public Vector3f getDefaultTranslation(){
        return translation;
    }
    
    /** 
     * move album component to default position(translation) 
     */
    public void changeDefaultTranslation(){
        this.changeTranslation(translation);
    }

    public void setJacket(String jacket){
        this.jacket = jacket;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    public void setArtist(String artist){
        this.artist = artist;
    }

    public void setFileList(AbstractList<FileInfoBean> fileList){
        this.fileList = fileList;
        if( fileList != null ) {
            int size = fileList.size();
            playList = new String[size];
            for(int i=0; i<size; i++){
                FileInfoBean fib = fileList.get(i);
                ID3Tag id3 = fib.getID3Tag();
                if( id3 != null ){
                    if( id3.getTitle() != null && id3.getTitle().equals("") != true)
                        playList[i] = id3.getTitle();
                    else
                        playList[i] = fib.getFilePath();
                }
                else playList[i] = fib.getFilePath();
            }
        } else playList = null;
    }
    
    public void paintJacket() {
        paintJacket(this.jacket);
    }
    
    public void paintTitle(){
        paintTitle(this.artist, this.title);
    }
    
    public void paintPlayList(){
        paintPlayList(playList);
    }

    /** Paint Jacket Image File to Shape */
    public abstract void paintJacket(String filename);
    
    /** Paint Jacket Image using Texture */
    public abstract void paintJacket(Texture texture);
    
    /** get Jacket Texture Image */
    public abstract Texture getJacketTexture();
    
    /** Paint Play List Information to Shape */
    public abstract void paintPlayList(String[] playList);
    
    /** Paint Play List Information to Shape using texture*/
    public abstract void paintPlayList(Texture texture);

    /** get Play List Texture */
    public abstract Texture getPlayListTexture();

    /** Paint Album Title to Shape */
    public abstract void paintTitle(String artist, String title);

    /** Paint Album Title to Shape using texture */
    public abstract void paintTitle(Texture texure);

    /** get Play List Texture */
    public abstract Texture getTitleTexture();

    public AlbumBase getCurrentAlbumComponent(){
        return this;
    }

    /** set Default Color of album component */
    public abstract void setDefaultColor();
    
    /** set Special Color of Current Playing Album Component */
    public abstract void setSpecialColor();

    /** set Focus Color of Album Component */
    public abstract void setFocusColor();

    /** set Focus Color of Current Playing Album Component */
    public abstract void setFocusSpecialColor();

    /** get Play List */
    public AbstractList<FileInfoBean> getPlayList(){ return this.fileList; }
    
    public void setDragAndDrop(boolean dragAndDrop){
        this.dragAndDrop = dragAndDrop;
    }
    
    public boolean enableDragAndDrop(){
        return dragAndDrop;
    }

    public void doDraggedAction(Component3D component,float x,float y){
        // move action
        Vector3f loc = new Vector3f();
        Vector3f bLoc = getManager().getFrameLocation();
        Vector3f sLoc = getManager().getShelfLocation();
        component.getFinalTranslation(loc);
        //component.setTranslation(x-bLoc.x-sLoc.x,y-bLoc.y-sLoc.y,loc.z);
        float scale = 1.0f / manager.getFrame3D().getFinalScale();
        component.setTranslation( (x-bLoc.x-sLoc.x) * scale , (y-bLoc.y-sLoc.y) * scale ,loc.z);
    }

    protected void setDraggedMousePosition(float x, float y){
        mouseX = x;
        mouseY = y;
    }

    private class DraggedAction implements ActionFloat2 {
        Component3D component;
        
        /** Creates a new instance of RotateAction */
        public DraggedAction(Component3D c) {
            component = c;
        }
        
        public void performAction(LgEventSource source, float f1, float f2) {
            if( enableDragAndDrop() ){
                mouseX = f1;
                mouseY = f2;
                dragged = true;
                doDraggedAction(component,f1,f2);
            }
        }
    }

    public void preDroppedAction(Component3D component,float x,float y){
        Manager manager = getManager();
        Vector3f loc = getDefaultTranslation();
        Vector3f current = new Vector3f();
        component.getFinalTranslation(current);
        component.changeTranslation(loc.x,loc.y,current.z,1000);        
    }
    
    public void doDroppedAction(Component3D component,float x,float y){
        // play music
        getManager().changePlayList(this);
        setDragAndDrop(false);
        // initialize mouseX,mouseY
        Vector3f nloc = new Vector3f();
        component.getFinalTranslation(nloc);
        setDraggedMousePosition(nloc.x,nloc.y);
    }
    
    private class DroppedAction implements ActionBoolean {

        Component3D component;

        /** Creates a new instance of RotateAction */
        public DroppedAction(Component3D c) {
            component = c;
        }

        public void performAction(LgEventSource source, boolean b){
            if(b && enableDragAndDrop() && dragged){
                preDroppedAction(component,mouseX,mouseY);
                if(getManager().inPlayerArea(mouseX,mouseY))
                    doDroppedAction(component,mouseX,mouseY);
                dragged = false;
            }
        }
    }
    
    private AlbumInformation albumInformation = null;
    public void setAlbumInformation(AlbumInformation i){
        albumInformation = i;
    }
    public AlbumInformation getAlbumInformation(){
        return albumInformation;
    }
}
