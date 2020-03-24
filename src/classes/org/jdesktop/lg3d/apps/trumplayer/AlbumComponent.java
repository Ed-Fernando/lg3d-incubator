/*
 * AlbumComponent.java
 *
 * Created on 2005/10/23, 22:53
 *
 */

package org.jdesktop.lg3d.apps.trumplayer;

import org.jdesktop.lg3d.apps.trumplayer.base.AlbumBase;
import org.jdesktop.lg3d.apps.trumplayer.base.MouseReleasedEventAdapter;
import org.jdesktop.lg3d.apps.trumplayer.base.Manager;

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
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
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

import org.jdesktop.lg3d.displayserver.EventProcessor;


/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class AlbumComponent extends AlbumBase {

    // size of the CD case
    public final static float width = 0.08f;  // x
    public final static float height = 0.08f; // y
    public final static float depth = 0.01f;  // z
    // original size of cd case
    // public final static float width = imageWidth * 1.1f;

    // rotation degrees and popup length
    private final static float ANGLE = (float)(Math.PI/2.0f);
    private final static float ONEEIGHTY = (float)Math.PI;
    private final static float POPUP = 0.07f;
    //private final static float CLICKEDPOPUP = 0.01f;

    // box object as CD case
    private Box box;
    private ImagePanel frontPanel;
    private ImagePanel backPanel;
    private ImagePanel sidePanel;

    // color of the case
    private final static float RED = 0.95f;
    private final static float GREEN = 0.98f;
    private final static float BLUE = 1.0f;
    private final static float ALPHA = 0.75f;
    
    // front panel image exist flag
    private boolean frontPanelImageBoolean = true;
    
    // mouse in?
    private boolean mouseIn = false;

    // original
    private float rotationAngle = ANGLE;
    private float popup = POPUP;
    private boolean middlePopupMode = true;
    private boolean alwaysDragAndDrop = false;
    private float scale = 1.0f;
    
    /** Creates a new instance of AlbumComponent */
    public AlbumComponent(){
        this(null);
    }
    
    public AlbumComponent(String fontName) {
        super(fontName);
    }

    
    private class ReplacedTransparencyAttributes extends TransparencyAttributes {
        public ReplacedTransparencyAttributes(int a, float f){
            super(a,f);
        }
        public void setTransparency(float trans){
            if( !(getTransparency() == 0.0f && mouseIn == true) ){
                super.setTransparency(trans);
            }
        }
    }
 
    private class ReplacedSimpleAppearance extends SimpleAppearance {
        public ReplacedSimpleAppearance(float r, float g, float b, float a, int i){
            super(r,g,b,a,i);
        }
        public void setAlpha(float alpha){
            //System.out.println("cal setAlha " + alpha);
            float trans = 1.0f - alpha;
            TransparencyAttributes ta = getTransparencyAttributes();
            if (ta == null) {
                ta = new ReplacedTransparencyAttributes(TransparencyAttributes.BLENDED,
                    trans);
                ta.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
                setTransparencyAttributes(ta);
            }
            ta.setTransparency(trans);
        }
    }
    
    public void initialize(){
        // base
        Component3D component = new Component3D();

        SimpleAppearance appearance = new SimpleAppearance(RED,GREEN,BLUE,ALPHA);
        appearance.setCapability(SimpleAppearance.ALLOW_MATERIAL_WRITE);
        box = new Box(width/2.0f,height/2.0f,depth/2.0f,
                  Box.GENERATE_NORMALS | Box.GENERATE_TEXTURE_COORDS | Box.ENABLE_APPEARANCE_MODIFY,
                  appearance);

        component.addChild(box);
        addChild(component);

        // create the front side of the CD case to display the Jacket image
        component = new Component3D();
        frontPanel = new ImagePanel(width,height);
        frontPanel.setCapability(ImagePanel.ALLOW_APPEARANCE_OVERRIDE_WRITE);

//        SimpleAppearance fsa = new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.5f,
        SimpleAppearance fsa = new ReplacedSimpleAppearance(1.0f, 1.0f, 1.0f, 0.5f,
                SimpleAppearance.ENABLE_TEXTURE | 
                SimpleAppearance.DISABLE_CULLING |
                SimpleAppearance.ALLOW_TEXTURE_ATTRIBUTES_WRITE
                );

        // Texture Attributes
        TextureAttributes ta = new TextureAttributes();
        ta.setCapability( TextureAttributes.ALLOW_MODE_WRITE);
        ta.setTextureMode(TextureAttributes.MODULATE);
        fsa.setTextureAttributes(ta);

        frontPanel.setAppearance( fsa );
        component.addChild(frontPanel);
        component.setTranslation(0.0f,0.0f,depth/2.0f+0.0001f);        
        addChild(component);


        // print title
        Component3D component2 = new Component3D();
        sidePanel = new ImagePanel(height,depth);
        sidePanel.setCapability(ImagePanel.ALLOW_APPEARANCE_OVERRIDE_WRITE);
        sidePanel.setAppearance(
            new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
            SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING) );
        component2.addChild(sidePanel);
        component2.setRotationAxis(1.0f,0.0f,0.0f);
        component2.setRotationAngle( (float)Math.PI/2.0f );
        component = new Component3D();
        component.addChild(component2);
        component.setRotationAxis(0.0f,0.0f,1.0f);
        component.setRotationAngle( - (float)Math.PI/2.0f );
        component.setTranslation(-(width/2.0f+0.0001f),0.0f,0.0f);
        addChild(component);

        // create the back side of the CD case to print play list
        component = new Component3D();
        backPanel = new ImagePanel(width,height);
        backPanel.setCapability(ImagePanel.ALLOW_APPEARANCE_OVERRIDE_WRITE);
        backPanel.setAppearance(
            new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
            SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING) );
        component.addChild(backPanel);
        component.setRotationAxis(0.0f,1.0f,0.0f);
        component.setRotationAngle( (float)Math.PI );
        component.setTranslation(0.0f , 0.0f, -(depth/2.0f+0.0001f) );
        addChild(component);
        
        setAnimation(new NaturalMotionAnimation(1000));
        
        // rotate when left mouse button is clicked
        MouseClickedEventAdapter clickedAdapter =
                new MouseClickedEventAdapter(ButtonId.BUTTON1, new ClickedAction(this));
        addListener(clickedAdapter);

        // back to the origin when right mouse button is clicked.
        MouseClickedEventAdapter rightClickedAdapter =
                new MouseClickedEventAdapter(ButtonId.BUTTON3, new RightClickedAction(this));        
        addListener(rightClickedAdapter);
        
        // Popup when mouse is in the case
        MouseEnteredEventAdapter enteredAdapter =
                new MouseEnteredEventAdapter(new EnteredAction(this));
        addListener(enteredAdapter);

        setMouseEventPropagatable(false);

        setRotationAxis(0.0f,1.0f,0.0f);
        setRotationAngle(ANGLE);
        
        setTranslation(0.0f,0.0f, POPUP * -2);
        setDefaultTranslation(0.0f,0.0f,POPUP * -2);
        

    }

    /**
     * paint Jacket image to the front panel of the CD case
     */
    public void paintJacket(String filename) {


        boolean flag = true;
        if( filename == null ){
            flag = false;
        }
        else {
            java.io.File file = null;
            ClassLoader classLoader = this.getClass().getClassLoader();

            if (filename != null && filename.trim().equals("") == false )
                file = new java.io.File(filename);
            java.io.InputStream is = null;
            
            java.net.URL url = null;

            if(file != null && file.exists()){
                try{
                    TextureLoader loader = new TextureLoader(filename,new java.awt.Canvas());
                    Texture texture = loader.getTexture();
                    frontPanel.getAppearance().setTexture(texture);
                    frontPanelImageBoolean = true;
                } catch(Exception e){
                    System.out.println("Read File Error : " + filename);
                }
            }
            else if (filename != null && filename.trim().equals("") == false &&
                    (url = classLoader.getResource(filename)) != null ){
                System.out.println(url.toString());
                try{
                    // funny picture is displayed using texture which was created by TextureLoader(URL).
                    // ImageIO or TextureLoader seems to have a bug...
                    // Therefore, using SimpleAppearance insteads of TextureLoader
                    // TextureLoader loader = new TextureLoader(url,new java.awt.Canvas());
                    // Texture texture = loader.getTexture();
                    SimpleAppearance sa = new SimpleAppearance( url );
                    Texture texture = sa.getTexture();
                    frontPanel.getAppearance().setTexture(texture);
                    frontPanelImageBoolean = true;
                } catch(Exception e){
                    e.printStackTrace();
                    flag = false;
                }
            }
            else{
                BufferedImage image = new BufferedImage(240,240,BufferedImage.TYPE_INT_ARGB|BufferedImage.SCALE_SMOOTH);
                Graphics2D g = (Graphics2D) image.getGraphics();
                g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
                g.setColor(Color.BLACK);
                g.setFont(new Font(fontName, Font.BOLD, 14));
                g.drawString(this.title,10,110);
                g.drawString(this.artist,10,130);
                g.dispose();
                TextureLoader loader = new TextureLoader(image);
                Texture texture = loader.getTexture();
                frontPanel.getAppearance().setTexture(texture);
                
                // change texture attributes not to use transparency of the texture.
                frontPanelImageBoolean = false;
                TextureAttributes ta = frontPanel.getAppearance().getTextureAttributes();
                ta.setTextureMode(TextureAttributes.REPLACE);
            }
        }
        if( flag == false  ){  // paint white (clear jacket image)
            BufferedImage image = new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB|BufferedImage.SCALE_SMOOTH);
            Graphics2D g = (Graphics2D) image.getGraphics();
            g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
            g.dispose();
            TextureLoader loader = new TextureLoader(image);
            Texture texture = loader.getTexture();
            frontPanel.getAppearance().setTexture(texture);

            // change texture attributes not to use transparency of the texture.
            frontPanelImageBoolean = false;
            TextureAttributes ta = frontPanel.getAppearance().getTextureAttributes();
            ta.setTextureMode(TextureAttributes.REPLACE);                
        }

        if(mouseIn){
            if(frontPanelImageBoolean){
                // change Texture Attributes when front panel image exists.
                SimpleAppearance a = (SimpleAppearance)frontPanel.getAppearance();
                a.setAlpha(1.0f);
            } else {
                // change Texture Attributes when front panel image exists.
                SimpleAppearance a = (SimpleAppearance)frontPanel.getAppearance();
                a.setAlpha(0.5f);            
            }
        }
    }
    
    /**
     * paint Jacket image to the front panel of the CD case
     */
    public void paintJacket(Texture texture){
        if( texture != null ){
            frontPanel.getAppearance().setTexture(texture);
            this.frontPanelImageBoolean = true;
        }
        else paintJacket();
    }

    public Texture getJacketTexture(){
        return frontPanel.getAppearance().getTexture();
    }
    
    /**
     * paint play list to the back panel of the CD case
     */
    public void paintPlayList(String[] playList) {
        BufferedImage image = null;

        if(playList == null){
            image = new BufferedImage(10,10,BufferedImage.TYPE_INT_ARGB|BufferedImage.SCALE_SMOOTH);
        } else{
            image = new BufferedImage(400,400,BufferedImage.TYPE_INT_ARGB|BufferedImage.SCALE_SMOOTH);
            Graphics2D g = (Graphics2D) image.getGraphics();
            g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
            g.setColor(Color.BLACK);
            for(int i=0; playList != null && i < playList.length ; i++){
                g.setFont(new Font(fontName, Font.BOLD, 16));
                if(i < 9)
                    g.drawString("0"+ (i+1) + ": " + playList[i],5,18*(i+1));
                else
                    g.drawString((i+1) + ": " +playList[i],5,18*(i+1));
            }
            g.dispose();
        }
        
        TextureLoader loader = new TextureLoader(image);
        Texture texture = loader.getTexture();
        backPanel.getAppearance().setTexture(texture);
    }

    public void paintPlayList(Texture texture){
        if( texture != null)
            backPanel.getAppearance().setTexture(texture);
        else paintPlayList();
    }

    public Texture getPlayListTexture(){
        return backPanel.getAppearance().getTexture();
    }
    
    /**
     * paint CD title
     */
    public void paintTitle(String artist, String title){
        try{
        String printTitle = "";
        
        if(artist.equalsIgnoreCase(""))
            printTitle = title;
        else
            printTitle = artist + " - " + title;
         
        // create a texture of MP3
        BufferedImage image = new BufferedImage(440,32,BufferedImage.TYPE_INT_ARGB|BufferedImage.SCALE_SMOOTH);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        g.setColor(Color.BLACK);
        
        g.setFont(new Font(fontName, Font.BOLD, 18));
        g.drawString( printTitle , 10,28);

        g.dispose();
        
        TextureLoader loader = new TextureLoader(image);
        Texture texture = loader.getTexture();

        sidePanel.getAppearance().setTexture(texture);
        /*
        SimpleAppearance appearance = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
        appearance.setCapability(SimpleAppearance.ALLOW_TEXTURE_WRITE);
        appearance.setTexture(texture);
        sidePanel.setAppearance(appearance);
         */
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void paintTitle(Texture texture){
        if( texture != null)
            sidePanel.getAppearance().setTexture(texture);
    }
    
    public Texture getTitleTexture(){
        return sidePanel.getAppearance().getTexture();
    }
    
    public void moveToDefaultPosition(){
        if(alwaysDragAndDrop == false)
            setDragAndDrop(false);
        Vector3f loc = getDefaultTranslation();
        this.changeTranslation(loc,1000);
        //System.out.println(rotationAngle);
        if(middlePopupMode == true)
            this.changeRotationAngle(rotationAngle, 1000);
        else
            this.changeRotationAngle(0.0f,1000);
        //System.out.println(scale);
        changeScale(scale);
    }
    
    private class EnteredAction implements ActionBoolean {
        AlbumBase albumBase;

        /** Creates a new instance of RotateAction */
        public EnteredAction(AlbumBase a) {
            albumBase = a;
        }
        
        public void performAction(LgEventSource source, boolean b) {
            mouseIn = b;

            // Popup
            Vector3f loc = new Vector3f();
            albumBase.getFinalTranslation(loc);
            if(b){
                if(middlePopupMode && loc.z != -depth){
                    loc.z += popup;
                    changeScale((1.0f + scale)/2.0f);
                    albumBase.changeTranslation( loc );
                }
                else if(middlePopupMode == false){
                    popup();
                }
                else{
                    loc.z = 0.0f - depth;
                    albumBase.changeTranslation( loc );
                }
            }
            else{
                if(!(middlePopupMode && loc.z == -depth)){
                    loc.z = -2 * popup;
                    albumBase.changeTranslation( loc );
                    albumBase.changeScale(scale);
                }
            }

           // color change
            if(b){
                
                if( manager.getCurrentAlbumBase() != null && manager.getCurrentAlbumBase().equals(albumBase) )
                    albumBase.setFocusSpecialColor();
                else
                    albumBase.setFocusColor();
                
                if(frontPanelImageBoolean){
                    // change Texture Attributes when front panel image exists.
                    SimpleAppearance a = (SimpleAppearance)frontPanel.getAppearance();
                    a.setAlpha(1.0f);
                }
                
            } else {
                if( manager.getCurrentAlbumBase() != null && manager.getCurrentAlbumBase().equals(albumBase) )
                    albumBase.setSpecialColor();
                else
                    albumBase.setDefaultColor();

                if(frontPanelImageBoolean){
                    // change Texture Attributes when front panel image exists.
                    SimpleAppearance a = (SimpleAppearance)frontPanel.getAppearance();                    
                    a.setAlpha(0.5f);

                }
            }
        }
    }
    
    private class ClickedAction implements ActionNoArg {
        Component3D component;

        /** Creates a new instance of RotateAction */
        public ClickedAction(Component3D c) {
            component = c;
        }

        public void performAction(LgEventSource source) {

            Vector3f loc = new Vector3f();
            component.getFinalTranslation(loc);
            if(middlePopupMode == true && loc.z != - depth)
                popup();
            else if( component.getFinalRotationAngle() == ONEEIGHTY )
                component.changeRotationAngle(0.0f);
            else
                component.changeRotationAngle(ONEEIGHTY, 1000);
        }
    }

    
    private class RightClickedAction implements ActionNoArg {
        Component3D component;

        /** Creates a new instance of RotateAction */
        public RightClickedAction(Component3D c) {
            component = c;
        }
    
        public void performAction(LgEventSource source) {
            if(enableDragAndDrop() == true){ 
                moveToDefaultPosition();
            }
        }
    }

    /*
     * Action, back to origin, is already implemented in ActionBase.
     * In this method, additional actions are implemented.
     */
    /** overwrite to change action */
    public void doDroppedAction(Component3D component, float x, float y){
        super.doDroppedAction(component,x,y);
        if(alwaysDragAndDrop == true)
            setDragAndDrop(true);
        Vector3f nloc = new Vector3f();
        this.moveToDefaultPosition();
    }

    /** overwrite to change action */
    public void preDroppedAction(Component3D component,float x,float y){
    }    

    public void setDefaultColor(){
        SimpleAppearance appearance = (SimpleAppearance) box.getAppearance();
        appearance.setColor( RED,GREEN,BLUE );
    }
    
    public void setSpecialColor(){
        SimpleAppearance appearance = (SimpleAppearance) box.getAppearance();
        appearance.setColor( 1.0f,0.7f,0.7f );
    }

    public void setFocusColor(){
        SimpleAppearance appearance = (SimpleAppearance) box.getAppearance();
        appearance.setColor( 1.0f,1.0f,0.7f );
    }    

    public void setFocusSpecialColor(){
        SimpleAppearance appearance = (SimpleAppearance) box.getAppearance();
        appearance.setColor( 1.0f,0.7f,1.0f );
    }
        

    
    // original methods
    public void setDefaultRotationAngle(float f){
        rotationAngle = f;
    }
    
    public void popup(){
        setDragAndDrop(true);
        Vector3f loc = new Vector3f();
        this.getFinalTranslation(loc);
        this.changeScale(1.0f);
        this.changeTranslation( loc.x , loc.y, 0.0f - depth , 1000 );
        float rotation = this.getFinalRotationAngle();
        if( rotation != 0.0f && rotation != ONEEIGHTY)
            this.changeRotationAngle( 0.0f , 1000 );
    }
        
    public float getDefaultPopup(){
        return POPUP;
    }
    
    public void setPopup(float f){
        popup = f;
    }
    
    public void setMiddlePopupMode(boolean b){
        middlePopupMode = b;
    }

    public void setAlwaysDragAndDrop(boolean b){
        alwaysDragAndDrop = b;
        if(b)
            setDragAndDrop(b);
        // System.out.println(alwaysDragAndDrop);
    }
    
    public void setDefaultScale(float f){
        scale = f;
    }

}
