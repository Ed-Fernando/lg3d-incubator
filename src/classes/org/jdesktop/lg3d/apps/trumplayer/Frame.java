/*
 * Frame.java
 *
 * Created on 2005/11/06, 16:52
 *
 */

package org.jdesktop.lg3d.apps.trumplayer;

import org.jdesktop.lg3d.apps.trumplayer.base.AlbumInformation;
import org.jdesktop.lg3d.apps.trumplayer.base.AlbumInformationReader;
import org.jdesktop.lg3d.apps.trumplayer.base.AlbumBase;
import org.jdesktop.lg3d.apps.trumplayer.base.ShelfBase;
import org.jdesktop.lg3d.apps.trumplayer.mp3player.FileInfoBean;
import org.jdesktop.lg3d.apps.trumplayer.base.PlayerBase;
import org.jdesktop.lg3d.apps.trumplayer.base.Manager;
import org.jdesktop.lg3d.apps.trumplayer.base.AlbumSearch;
import org.jdesktop.lg3d.apps.trumplayer.utils.TrumplayerDefaults;

import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Component3D;

import java.util.AbstractList;

import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import javax.imageio.ImageIO;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.Thumbnail;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;


/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class Frame {

    private PlayerBase player;
    private ShelfBase albumShelf;
    private Frame3D frame;
    private AlbumBase currentAlbumBase;

    // private AlbumBase currentAlbumBase;
    private AlbumInformation[] albumInformations;
    private String encode;
    private String fontname;

    // default
    public final static String defaultPlayer = "org.jdesktop.lg3d.apps.trumplayer.Player";
    public final static String defaultShelf = "org.jdesktop.lg3d.apps.trumplayer.AlbumShelf";

    private Manager manager;
    
    /**
     * Creates a new instance of Frame 
     */
    public Frame(){
        this(defaultPlayer, defaultShelf);
    }
    
    /**
     * Creates a new instance of Frame 
     */
    public Frame(String playerName, String shelfName) {

        manager = new Manager();

        boolean trumplayer_xml = true;
        boolean mp3plugin = false;

        // mp3plugin = mp3pluginCheck();
        // mp3plugin = true;
        
        // Read album informations, charactor encode and font name to use from trumplayer.xml
        AlbumInformationReader reader = null;

        String trumplayer_localhome = null;

        if(TrumplayerDefaults.HOME_DIR.equalsIgnoreCase("") == false){
            reader = AlbumInformationReader.getReader(TrumplayerDefaults.TRUMPLAYER_CONFIG_PATH,TrumplayerDefaults.TRUMPLAYER_LOCALHOME);
        }

        if ( reader == null )
            reader = AlbumInformationReader.getReader("trumplayer.xml",trumplayer_localhome);
        
        if ( reader == null ){
            reader = AlbumInformationReader.getReader("org/jdesktop/lg3d/apps/trumplayer/resources/trumplayer_sample.xml",trumplayer_localhome);
            trumplayer_xml = false;
        }
        albumInformations = reader.getAlbumInformations();
        AlbumSearch.getAlbumSearch(albumInformations);
        
        encode = reader.getCharacterCode();
        fontname = reader.getFontName();

        
        // set charactor encode and font name
        manager.setEncode(encode);
        manager.setFontName(fontname);
        
        frame = manager.getFrame3D();
        frame.setPreferredSize(new Vector3f(0.1f, 0.1f, 0.1f));


        // make a exit button and a minimize button
        makeButton();
        player = makePlayer(playerName);
        frame.addChild(player);
        manager.initializePlayer();

        // make a thumbnail
        Thumbnail thumbnail = player.getThumbnail();
        frame.setThumbnail(thumbnail);

        // display the frame
        frame.changeEnabled(true);
        frame.changeVisible(true);

        // make an album shelf
        albumShelf = makeShelf(shelfName, albumInformations);
        frame.addChild(albumShelf);
        if(albumShelf == null){
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("albumShelf is null");
            System.out.println("");
            System.out.println("");
            System.out.println("");
        }
        manager.initializeShelf();

        // check plugins and display warnings
        // displayWarningWindow(trumplayer_xml, mp3plugin);
        
    }
    
    
    /** 
     * create a player for trumplayer
     */
    private PlayerBase makePlayer(String name){
        PlayerBase player = null;
        if( name == null || name.equals("") ){
            System.err.println("Player class name not found.");
            System.err.println("Trumplayer Use default Player");
            name = defaultPlayer;
        }
        try{
            player = manager.makePlayer(name);
            //manager.initializePlayer();
        } catch (Exception e){
            e.printStackTrace();
        }
        return player;
    }
    
    /** 
     * create an album shelf
     */
    private ShelfBase makeShelf(String name, AlbumInformation[] albumInformations){
        ShelfBase shelf = null;
        if( name == null || name.equals("") ){
            System.err.println("ShelfBase class name not found.");
            System.err.println("Trumplayer Use default Shelf");
            name = defaultShelf;
        }
        try{
            shelf = manager.makeShelf(name,albumInformations);
        } catch (Exception e){
            e.printStackTrace();
        }
        return shelf;
        
    }
    
    /** 
     * create a exit button and a minimize button
     */
    private void makeButton(){
        try{
            // Window Close Button
            BufferedImage closeImage = ImageIO.read(ClassLoader.getSystemResourceAsStream("resources/images/button/window-close.png"));
            Texture closeTexture = new TextureLoader(closeImage).getTexture();
            ImagePanel closePanel = new ImagePanel(0.01f,0.01f);
            SimpleAppearance closeAppearance = new SimpleAppearance(1.0f,1.0f,0.8f,1.0f, SimpleAppearance.ENABLE_TEXTURE);
            closeAppearance.setTexture(closeTexture);
            closePanel.setAppearance(closeAppearance);

            Component3D closeComponent = new Component3D();
            closeComponent.addChild(closePanel);
            closeComponent.setTranslation(0.06f, -0.05f, 0.0f);
            closeComponent.addListener(
                new MouseClickedEventAdapter(
                    new ActionNoArg() {
                        public void performAction(LgEventSource source) {
                            close();
                        }
                }));
            frame.addChild(closeComponent);

            // Window Minimize Button
            BufferedImage minimizeImage = ImageIO.read( ClassLoader.getSystemResourceAsStream("resources/images/button/window-minimize.png"));
            Texture minimizeTexture = new TextureLoader(minimizeImage).getTexture();
            ImagePanel minimizePanel = new ImagePanel(0.01f,0.01f);
            SimpleAppearance minimizeAppearance = new SimpleAppearance(1.0f,1.0f,0.8f,1.0f, SimpleAppearance.ENABLE_TEXTURE);
            minimizeAppearance.setTexture(minimizeTexture);
            minimizePanel.setAppearance(minimizeAppearance);
            Component3D minimizeComponent = new Component3D();
            minimizeComponent.addChild(minimizePanel);
            minimizeComponent.setTranslation(0.05f, -0.05f, 0.0f);
            minimizeComponent.addListener(
                new MouseClickedEventAdapter(
                    new ActionNoArg() {
                        public void performAction(LgEventSource source) {
                            minimize();
                        }
                }));
            frame.addChild(minimizeComponent);
        } catch (Exception e){
        }
        
    }

    /** for exit */
    protected void close(){
        player.getPlayer().stop();
        frame.changeEnabled(false);
    }

    /** minimize the Frame */
    protected void minimize(){
        frame.changeVisible(false);
    }
    
    public boolean isEnabled(){
        return frame.isEnabled();
    }
    
    public void setEnabled(boolean b){
        frame.setEnabled(b);
    }
    
    
}
