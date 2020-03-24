/*
 * AlbumShelf.java
 *
 * Created on 2005/11/02, 14:20
 *
 */

package org.jdesktop.lg3d.apps.trumplayer;

import org.jdesktop.lg3d.apps.trumplayer.base.AlbumBase;
import org.jdesktop.lg3d.apps.trumplayer.base.AlbumInformation;
import org.jdesktop.lg3d.apps.trumplayer.base.AlbumInformationReader;
import org.jdesktop.lg3d.apps.trumplayer.base.Manager;
import org.jdesktop.lg3d.apps.trumplayer.base.ShelfBase;
import org.jdesktop.lg3d.apps.trumplayer.utils.TrumplayerDefaults;
import javax.vecmath.Vector3f;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import org.jdesktop.lg3d.apps.trumplayer.UpdateBySearch;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;

import java.io.File;
import java.util.AbstractList;
import java.util.Arrays;
import org.jdesktop.lg3d.apps.trumplayer.mp3player.M3UReader;
import org.jdesktop.lg3d.apps.trumplayer.mp3player.FileInfoBean;
import org.jdesktop.lg3d.utils.shape.Disc;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.Cylinder;
import org.jdesktop.lg3d.utils.shape.Cone;
import org.jdesktop.lg3d.sg.Texture;
//import org.jdesktop.lg3d.apps.trumplayer.utils.PlayListManagerFrame;

import org.jdesktop.lg3d.apps.trumplayer.utils.UpdateAction;

import java.util.Vector;

import org.jdesktop.lg3d.wg.SwingNode;
import org.jdesktop.lg3d.apps.trumplayer.utils.ExtendedDragComponent3D;
import org.jdesktop.lg3d.apps.trumplayer.utils.PlayListManagerSwingNode;

/**
 * 
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class AlbumShelf extends ShelfBase implements UpdateBySearch, UpdateAction {

    // for internal new class
    AlbumShelf albumShelf = this;
    
    // AlbumComponent List
    Vector<AlbumComponent> componentList = new Vector<AlbumComponent>();
    Vector<AlbumComponent> subComponentList = new Vector<AlbumComponent>();
    // Button's Default Postion'
    public final static float BUTTON_Z = 0.0f;
    public final static float BUTTON_Y = -0.048f;
    // Button's popup
    public float BUTTON_POPUP = 0.03f;
    
    // component size
    private int mainSize = 0;
    private int subSize = 0;
    public final static int componentMax = 16;
    
    private String fontname;
    private String encode;
    
    // displaying album information
    private int currentAlbumPosition = 0;
    private AlbumInformation currentAlbumInfo[];

    private AlbumComponent[] componentCache;

    private Component3D nextAlbumButton = new Component3D();
    private Component3D prevAlbumButton = new Component3D();
    
    private boolean inPageChangeAction = false;

    // Search SwingNode with SearchSwingPanel (Change from SearchSwingFrame:JFrame)
    ExtendedDragComponent3D searchComponent = null;
    
    private void makeSearchBox(){
        searchComponent = new ExtendedDragComponent3D(this.getManager());
        searchComponent.setDragAndDrop(true);

        // create a swingnode object 
        SwingNode swingNode = new SwingNode();
        swingNode.setPanel(new SearchSwingPanel(searchComponent,albumShelf));
        
        float localHeight = swingNode.getLocalHeight();
        float localWidth = swingNode.getLocalWidth();
        
        Component3D search = makeSearchComponent();
        // search.setTranslation(-0.04f,0.022f,0.008f);
        search.setTranslation(- localWidth/2, localHeight/2 ,0.008f);
        searchComponent.addChild(search);
        
        // Box box = new Box( 0.05f, 0.027f, 0.0020f , new SimpleAppearance(0.4f,0.8f,1.0f,0.6f) );
        Box box = new Box( localWidth/2 + 0.005f, localHeight/2 + 0.005f, 0.0020f , new SimpleAppearance(0.4f,0.8f,1.0f,0.6f) );
        Component3D boxComponent = new Component3D();
        boxComponent.addChild(box);
        boxComponent.setTranslation(0.0f,0.0f,-0.005f);
        searchComponent.addChild(boxComponent);

        //searchComponent.initializeExitButton(0.04f,0.022f,0.005f);
        searchComponent.initializeExitButton(localWidth/2,localHeight/2,0.005f);

        // add swingnode to searchComponent
        searchComponent.addChild(swingNode);

        // searchComponent.setDefaultTranslation(0.0f,0.0f,-0.005f);
        searchComponent.setDefaultTranslation(0.0f,0.0f,0.02f);
        searchComponent.changeDefaultTranslation();
        searchComponent.setAnimation(new NaturalMotionAnimation(1000));
        searchComponent.setVisible(false);

        addChild(searchComponent);

    }
    
    
    public void initialize(){
        initialize(getAlbumInformation(), getFontName(), getEncode());
    }
    
    public void initialize(AlbumInformation[] albumInfo, String fontname, String encode){

        makeModeChangeButton();
        makeSearchButton();
        makeSearchBox();
        makePageButton();
        makeEditorButton();

        this.fontname = fontname;
        this.encode = encode;
        
        // Initialize an AlbumComonent object to create texture cache
        this.lookaheadAlbumComponent = new AlbumComponent(fontname);

        componentCache = new AlbumComponent[componentMax];
        for(int i=0; i < componentMax; i++){
            componentCache[i] = new AlbumComponent(fontname);
            componentCache[i].setAnimation(new NaturalMotionAnimation(1000));
            //componentCache[i].setAnimation(new NaturalMotionAnimation(1000));
            componentCache[i].paintJacket((String) null);
            componentCache[i].paintPlayList((String[]) null);
            componentCache[i].paintTitle("","");
        }

        updateAlbumInformations(albumInfo,0);

        setAnimation(new NaturalMotionAnimation(1000));

    }

    public void updateAlbumInformations(AlbumInformation[] albumInfo, int move){
        updateThread t = new updateThread(albumInfo,move);
        t.start();
    }
    
    private class updateThread extends Thread {
        AlbumInformation[] ai;
        int move;
        public updateThread(AlbumInformation[] albumInfo,int move){
            ai = albumInfo;
            this.move = move;
        }
        public void run(){
            updateAlbumInformations2(ai,move,this);
        }
    }

    // texture cache
    private class TextureCache{
        public String artist = "";
        public String title = "";
        public String jacket ="" ;
        public String m3u="";
        public long m3uLastModified = 0L;
        public Texture jacketTexture;
        public long jacketLastModified = 0L;
        public Texture playListTexture;

        public TextureCache(AlbumInformation info){
            artist = info.getArtist();
            title = info.getTitle();
            jacket = info.getImage();
            m3u = info.getM3U();
            if(m3u != null && m3u.trim().equals("") == false){
                File m3uFile = new File(m3u);
                if( m3uFile.exists() )
                    m3uLastModified = m3uFile.lastModified();
            }
            if(jacket != null && jacket.trim().equals("") == false){
                File jacketFile = new File(jacket);
                if( jacketFile.exists() )
                    jacketLastModified = jacketFile.lastModified();
            }
        }
    }

    // private int textureCacheMax = 15; // for test
    private int textureCacheMax = componentMax * 3;
    private Vector<TextureCache> textureCacheList = new Vector<TextureCache>();

    protected TextureCache searchTextureCache(TextureCache searchItem){
        TextureCache result = null;
        for(int i=0; i < textureCacheList.size(); i++){
        //for(int i=textureCacheList.size()-1; i >= 0; i--){
            TextureCache c = textureCacheList.get(i);
            if( c.artist.equals(searchItem.artist) && c.title.equals(searchItem.title) ){

                // update check
                if( c.jacket.equals(searchItem.jacket) && c.jacketLastModified == searchItem.jacketLastModified
                      && c.m3u.equals(searchItem.m3u) && c.m3uLastModified == searchItem.m3uLastModified )
                    result = c;
                else
                    textureCacheList.remove(i);
                
                break;
            }
        }
        return result;
    }
    
    protected void insertTextureCache(TextureCache cache){
        textureCacheList.remove(cache);
        textureCacheList.insertElementAt(cache,0);
    }
    
    protected void compressTextureCache(){
        while( textureCacheList.size() > textureCacheMax )
            textureCacheList.remove( textureCacheList.size() - 1 );
    }
    
    private void updateAlbumInformations2(AlbumInformation[] albumInfo, int move, Thread thread){

        if ( albumInfo != null && ( Arrays.equals(albumInfo,currentAlbumInfo) == false || move != 0 ) ){
            
            int newPosition = 0;
            if( move != 0 )
                newPosition = currentAlbumPosition + move;
            
            this.getManager().changeAlbumBase(null);

            if( newPosition >= 0 && (newPosition * componentMax) < albumInfo.length ){
                currentAlbumPosition = newPosition;
                if( currentAlbumPosition == 0 )
                    this.prevAlbumButton.changeVisible(false);
                else 
                    this.prevAlbumButton.changeVisible(true);
                if( (currentAlbumPosition+1) * componentMax < albumInfo.length )
                    this.nextAlbumButton.changeVisible(true);
                else
                    this.nextAlbumButton.changeVisible(false);

                // move to center
                for(int i=0; i<componentList.size();i++){
                    AlbumComponent a = componentList.get(i);
                    a.moveToDefaultPosition();
                    javax.vecmath.Vector3f loc = new javax.vecmath.Vector3f();
                    a.getFinalTranslation(loc);
                    a.changeTranslation(0.0f,0.0f,loc.z,1000);
                }

                try{
                    thread.sleep(1000);
                } catch(Exception e){
                    e.printStackTrace();
                }

                // clear
                for(int i=0; i<componentList.size();i++){
                    AlbumComponent a = componentList.get(i);
                    a.paintJacket((String) null);
                    a.paintPlayList((String[]) null);
                    a.setDefaultColor();
                }

                currentAlbumInfo = albumInfo;

                AlbumComponent a;
                
                int start = currentAlbumPosition * componentMax;
                int end = (currentAlbumPosition+1) * componentMax;

                if (albumInfo.length < end)
                    end = albumInfo.length;
                
                if( (end-start) < componentList.size() ){
                    for(int i = end-start; i < componentList.size(); i++)
                        this.removeChild( componentList.get(i) );
                } else {
                    for(int i=componentList.size(); i < end-start; i++)
                        this.addChild(componentCache[i]);
                }
                componentList.clear();


                AlbumInformation currentPlayingAlbumInformation
                        = this.getManager().getCurrentAlbumInformation();

                AlbumInformation[] tmpAlbumInformations = new AlbumInformation[end-start];
                AlbumComponent[] tmpAlbumComponents = new AlbumComponent[end-start];

                for(int i=0; i<end-start; i++){
                    
                    a = componentCache[i];
                    a.setTitle(albumInfo[i + start].getTitle());
                    a.setArtist(albumInfo[i + start].getArtist());
                    a.setAlbumInformation(albumInfo[i + start]);
                    a.paintTitle();

                    this.componentList.add(a);

                    // album check
                    if(currentPlayingAlbumInformation != null && 
                            albumInfo[i + start].getArtist().equals(currentPlayingAlbumInformation.getArtist()) &&
                            albumInfo[i + start].getTitle().equals(currentPlayingAlbumInformation.getTitle()) ){
                        getManager().changeAlbumBase(a);
                        a.setSpecialColor();
                    }

                    tmpAlbumInformations[i]= albumInfo[i + start];
                    tmpAlbumComponents[i] = a;
                }

                Vector<AlbumInformation> lookahead_list = new Vector<AlbumInformation>();
                AlbumInformation[] lookahead = null;
                
                //System.out.println("start = "+start);
                if( start - componentMax >= 0 ){
                    //System.out.println("exists previous");
                    for(int i=start - componentMax; i < start ; i++)
                        lookahead_list.add(albumInfo[i]);
                }
                for(int i=end; i < albumInfo.length && i < end + componentMax; i++)
                    lookahead_list.add(albumInfo[i]);
                if( lookahead_list.size() > 0) {
                    lookahead = new AlbumInformation[ lookahead_list.size() ];
                    lookahead_list.toArray(lookahead);
                }                

                informationLoad(this,tmpAlbumComponents,tmpAlbumInformations,encode);                
                lookAheadTextureCache(lookahead);

                try{
                    thread.sleep(500);
                } catch(Exception e){
                    e.printStackTrace();
                }

                changeMode(this.currentMode);

                try{
                    thread.sleep(1000);
                } catch(Exception e){
                    e.printStackTrace();
                }
                

            }  // if( newPosition >= 0

        } // if ( albumInfo != null &&

        this.inPageChangeAction = false;
    }

    public void updateAlbumInformations(AlbumInformation[] albumInfo){
        updateAlbumInformations(albumInfo,0);
    }
    
    // private PlayListManagerFrame playListManagerFrame;
    private PlayListManagerSwingNode playListSwingNode;

    private void makeEditorButton(){
        // playlist manager
        playListSwingNode = new PlayListManagerSwingNode(manager);
        playListSwingNode.setVisible(false);
        addChild( playListSwingNode );
 
        // button
        Component3D editorComponent = new Component3D();

        Component3D boxComponent1 = new Component3D();
        Box box1 = new Box(0.005f, 0.005f, 0.0005f, new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.6f, 
                                              SimpleAppearance.DISABLE_CULLING) );
        boxComponent1.addChild(box1);
        boxComponent1.setTranslation(0.0f,0.0f,-0.001f);
        
        Component3D boxComponent2 = new Component3D();
        Box box2 = new Box(0.005f, 0.005f, 0.0005f, new SimpleAppearance(0.7f, 0.7f, 1.0f, 0.6f, 
                                              SimpleAppearance.DISABLE_CULLING) );
        boxComponent2.addChild(box2);
        boxComponent2.setTranslation(0.001f,0.001f,-0.002f);

        Component3D boxComponent3 = new Component3D();
        Box box3 = new Box(0.005f, 0.005f, 0.0005f, new SimpleAppearance(0.7f, 1.0f, 0.7f, 0.6f, 
                                              SimpleAppearance.DISABLE_CULLING) );
        boxComponent3.addChild(box3);
        boxComponent3.setTranslation(0.002f,0.002f,-0.003f);

        Component3D handle = new Component3D();
        Cylinder handleCylinder = new Cylinder(0.0010f,0.008f,
                new SimpleAppearance(0.6f,0.6f,0.6f,1.0f));
        handle.addChild(handleCylinder);
        handle.setRotationAxis(0.0f,0.0f,1.0f);
        handle.setRotationAngle(-1*(float)Math.PI/4.0f);
        handle.setTranslation((float)(0.001f * Math.sin(Math.PI/4.0f)),
                (float)(0.001f * Math.cos(Math.PI/4.0f)),0.002f);

        Component3D handle2 = new Component3D();
        Cone handleCone = new Cone(0.001f,0.003f,Cone.CAP,20,3,new SimpleAppearance(0.0f,0.0f,0.0f,1.0f));
        handle2.addChild(handleCone);
        handle2.setRotationAxis(0.0f,0.0f,1.0f);
        handle2.setRotationAngle((float)(Math.PI/2.0f+Math.PI/4.0f));
        handle2.setTranslation((float)(-0.004f * Math.sin(Math.PI/4.0f)),
                (float)(-0.004f * Math.cos(Math.PI/4.0f)),0.002f);

        editorComponent.addChild(boxComponent1);
        editorComponent.addChild(boxComponent2);
        editorComponent.addChild(boxComponent3);
        editorComponent.addChild(handle);
        editorComponent.addChild(handle2);
        
        editorComponent.setTranslation(0.035f,this.BUTTON_Y,this.BUTTON_Z);
        
        editorComponent.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
            new PressedAction(editorComponent,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    super.performAction(source,b);

                    if(b){
                        if(playListSwingNode.isVisible() == true)
                            playListSwingNode.changeVisible(false);
                        else
                            playListSwingNode.changeVisible(true);
                    }

                    /**************
                    if(b){
                        if(playListManagerFrame == null){
                            playListManagerFrame = new PlayListManagerFrame();
                            // current LG3D does not support WindowListener.windowClosed,
                            // therefore use interface UpdateEvent instead of WindowListener.
                            // playListManagerFrame.addWindowListener(new PlayListManagerFrameCloseListener());
                            playListManagerFrame.setUpdateEvent(albumShelf);
                            playListManagerFrame.setLocation(300,300);
                            playListManagerFrame.setVisible(true);
                        } else {
                            java.awt.Point p = playListManagerFrame.getLocation();
                            playListManagerFrame.dispose();
                            playListManagerFrame = new PlayListManagerFrame();
                            // current LG3D does not support WindowListener.windowClosed,
                            // therefore use interface UpdateEvent instead of WindowListener.
                            // playListManagerFrame.addWindowListener(new PlayListManagerFrameCloseListener());
                            playListManagerFrame.setUpdateEvent(albumShelf);
                            playListManagerFrame.setLocation(p);
                            playListManagerFrame.setVisible(true);
                        }
                    }
                    **************/
                }
            }
        ));

        editorComponent.addListener(new MouseEnteredEventAdapter(
            new PressedAction(editorComponent,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    Vector3f loc = new Vector3f();
                    getComponent3D().getFinalTranslation(loc);
                    if(loc.z != BUTTON_Z && b == false)
                        super.performAction(source,b);
                }
            }
        ));

        addChild(editorComponent);
        
    }

    public void performUpdateAction(){
        // if( playListManagerFrame.updatedConfigFile() ) {
        if( playListSwingNode.updatedConfigFile() ) {
            // readAlbumInformations();
            AlbumInformationReader reader =
                AlbumInformationReader.getReader(TrumplayerDefaults.TRUMPLAYER_CONFIG_PATH,TrumplayerDefaults.TRUMPLAYER_LOCALHOME);
            AlbumInformation[] albumInfo = reader.getAlbumInformations();
            updateAlbumInformations(albumInfo,0);
        }
    }
    
    private void makePageButton(){

        Component3D pageGroup = new Component3D();
        
        Component3D next = this.nextAlbumButton;
        Cone nextCone = new Cone(0.004f,0.01f,Cone.CAP,20,3,new SimpleAppearance(1.0f,1.0f,0.0f,1.0f));
        next.addChild(nextCone);
        
        next.setTranslation(0.0f,0.007f,0.0f);
        next.setRotationAxis(1.0f,0.0f,0.0f);
        next.setRotationAngle(- (float)(Math.PI/6.0));

        next.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
            new PressedAction(next,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    super.performAction(source,b);
                    if(b){
                        if( inPageChangeAction == false){
                            inPageChangeAction = true;
                            updateAlbumInformations(currentAlbumInfo,1);
                        }
                    }
                }
            }
        ));

        next.addListener(new MouseEnteredEventAdapter(
            new PressedAction(next,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    Vector3f loc = new Vector3f();
                    getComponent3D().getFinalTranslation(loc);
                    if(loc.z != BUTTON_Z && b == false){
                        super.performAction(source,b);
                    }
                }
            }
        ));
        
        next.setAnimation(new NaturalMotionAnimation(1000));


        Component3D prev = this.prevAlbumButton;
        Cone prevCone = new Cone(0.004f,0.01f,Cone.CAP,20,3,new SimpleAppearance(1.0f,1.0f,0.0f,1.0f));
        prev.addChild(prevCone);

        prev.setTranslation(0.0f,-0.007f,0.0f);
        prev.setRotationAxis(1.0f,0.0f,0.0f);
        prev.setRotationAngle((float)Math.PI - (float)(Math.PI/6.0));

        prev.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
            new PressedAction(prev,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    super.performAction(source,b);
                    if(b){
                        if( inPageChangeAction == false){
                            inPageChangeAction = true;
                            updateAlbumInformations(currentAlbumInfo,-1);
                        }
                    }
                }
            }
        ));

        prev.addListener(new MouseEnteredEventAdapter(
            new PressedAction(prev,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    Vector3f loc = new Vector3f();
                    getComponent3D().getFinalTranslation(loc);
                    if(loc.z != BUTTON_Z && b == false){
                        super.performAction(source,b);
                    }
                }
            }
        ));

        prev.setAnimation(new NaturalMotionAnimation(1000));
        
        pageGroup.addChild(next);
        pageGroup.addChild(prev);
        
        pageGroup.setTranslation(0.005f,this.BUTTON_Y,this.BUTTON_Z);
        
        next.setVisible(false);
        prev.setVisible(false);
        
        addChild(pageGroup);
    }
    

    private Component3D makeSearchComponent(){
        Component3D search = new Component3D();

        Component3D lens = new Component3D();

        Cylinder lensCylinder = new Cylinder(0.0040f,0.002f,
                new SimpleAppearance(0.6f,0.6f,0.6f,1.0f));
        lens = new Component3D();
        lens.addChild(lensCylinder);
        lens.setRotationAxis(1.0f,0.0f,0.0f);
        lens.setRotationAngle((float)Math.PI/2.0f);

        Component3D handle = new Component3D();
        Cylinder handleCylinder = new Cylinder(0.0010f,0.005f,
                new SimpleAppearance(0.6f,0.6f,0.6f,1.0f));
        handle.addChild(handleCylinder);
        handle.setRotationAxis(0.0f,0.0f,1.0f);
        handle.setRotationAngle(-1*(float)Math.PI/4.0f);
        handle.setTranslation((float)(-0.005f * Math.sin(Math.PI/4.0f)),
                (float)(-0.005f * Math.cos(Math.PI/4.0f)),0.0f);
        
        Component3D glass = new Component3D();
        Cylinder glassCylinder = new Cylinder(0.0030f,0.00205f,
                new SimpleAppearance(0.7f,0.8f,0.8f,0.8f));
        glass.addChild(glassCylinder);
        glass.setRotationAxis(1.0f,0.0f,0.0f);
        glass.setRotationAngle((float)Math.PI/2.0f);

        search.addChild(glass);
        search.addChild(lens);
        search.addChild(handle);
        
        return search;
    }
    
    private void makeSearchButton(){
        Component3D search = makeSearchComponent();

        // needs to modify
        search.setTranslation(0.020f,this.BUTTON_Y,this.BUTTON_Z);
        search.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
            new PressedAction(search,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    super.performAction(source,b);
                    if(b){
                        //if(searchComponent.getFinalScale() == 1.0f){
                        if(searchComponent.isVisible() == true){
                            searchComponent.changeVisible(false);
                            //searchComponent.changeScale(0.0f);
                            //searchComponent.changeDefaultTranslation();
                        }
                        else
                            searchComponent.changeVisible(true);
                            //searchComponent.changeScale(1.0f);
                    }
                }
            }
        ));

        search.addListener(new MouseEnteredEventAdapter(
            new PressedAction(search,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    Vector3f loc = new Vector3f();
                    getComponent3D().getFinalTranslation(loc);
                    if(loc.z != BUTTON_Z && b == false)
                        super.performAction(source,b);
                }
            }
        ));
        
        addChild(search);
        
    }

    
    private void makeModeChangeButton(){
        Component3D modeA = new Component3D();
        Component3D modeB = new Component3D();
        Component3D modeC = new Component3D();
        Component3D modeD = new Component3D();
        
        // mode A
        modeA = makeButton(null, new Color4f(0.6f, 1.0f, 1.0f, 1.0f));
        modeA.setTranslation(-0.040f, this.BUTTON_Y, this.BUTTON_Z);
        modeA.setAnimation(new NaturalMotionAnimation(1000));
        modeA.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
            new PressedAction(modeA,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    super.performAction(source,b);
                    if(b){
                        changeMode("a");
                    }
                }
            }
        ));

        modeA.addListener(new MouseEnteredEventAdapter(
            new PressedAction(modeA,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    Vector3f loc = new Vector3f();
                    getComponent3D().getFinalTranslation(loc);
                    if(loc.z != BUTTON_Z && b == false)
                        super.performAction(source,b);
                }
            }
        ));


        // mode B
        modeB = makeButton(null, new Color4f(1.0f, 0.6f, 1.0f, 1.0f));
        modeB.setTranslation(-0.030f, this.BUTTON_Y, this.BUTTON_Z);
        modeB.setAnimation(new NaturalMotionAnimation(1000));
        modeB.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
            new PressedAction(modeB,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    super.performAction(source,b);
                    if(b){
                        changeMode("b");
                    }
                }
            }
        ));
        modeB.addListener(new MouseEnteredEventAdapter(
            new PressedAction(modeB,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    Vector3f loc = new Vector3f();
                    getComponent3D().getFinalTranslation(loc);
                    if(loc.z != BUTTON_Z && b == false){
                        super.performAction(source,b);
                    }
                }
            }
        ));

        // mode C
        modeC = makeButton(null, new Color4f(1.0f, 1.0f, 0.6f, 1.0f));
        modeC.setTranslation(-0.020f, this.BUTTON_Y, this.BUTTON_Z);
        modeC.setAnimation(new NaturalMotionAnimation(1000));
        modeC.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
            new PressedAction(modeC,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    super.performAction(source,b);
                    if(b){
                        changeMode("c");
                    }
                }
            }
        ));
        modeC.addListener(new MouseEnteredEventAdapter(
            new PressedAction(modeC,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    Vector3f loc = new Vector3f();
                    getComponent3D().getFinalTranslation(loc);
                    if(loc.z != BUTTON_Z && b == false)
                        super.performAction(source,b);
                }
            }
        ));

        // mode D
        modeD = makeButton(null, new Color4f(1.0f, 0.6f, 0.6f, 1.0f));
        modeD.setTranslation(-0.010f, this.BUTTON_Y, this.BUTTON_Z);
        modeD.setAnimation(new NaturalMotionAnimation(1000));
        modeD.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
            new PressedAction(modeD,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    super.performAction(source,b);
                    if(b){
                        // mode change : tile mode
                        changeMode("d");
                    }
                }
            }
        ));
        modeD.addListener(new MouseEnteredEventAdapter(
            new PressedAction(modeD,BUTTON_POPUP){
                public void performAction(LgEventSource source,boolean b){
                    Vector3f loc = new Vector3f();
                    getComponent3D().getFinalTranslation(loc);
                    if(loc.z != BUTTON_Z && b == false)
                        super.performAction(source,b);
                }
            }
        ));

        addChild(modeA);
        addChild(modeB);
        addChild(modeC);
        addChild(modeD);
    }


    private java.util.Random random = new java.util.Random(java.util.Calendar.getInstance().getTimeInMillis());
    private String currentMode = "a";
    public void changeMode(String mode){
        currentMode = mode;
        if("a".equals(mode)){
            // mode change : default
            int size = componentList.size();
            double radian = 2 * Math.PI; // 360 degrees
            for(int i=0; i < size; i++){
                AlbumComponent a = componentList.get(i);
                float x = (a.depth + 0.002f) * ((float)i + 0.5f - size/2);
                float y = 0.0f;
                Vector3f loc = new Vector3f();
                a.setDefaultTranslation(x,y,- a.getDefaultPopup() * 2);
                a.setDefaultRotationAngle((float)Math.PI/2.0f);
                a.setPopup(a.getDefaultPopup());
                a.setDefaultScale(1.0f);
                a.setMiddlePopupMode(true);
                a.moveToDefaultPosition();
            }            
        }
        else if("b".equals(mode)){
            // mode change : rotation mode
            int size = componentList.size();
            double radian = 2 * Math.PI; // 360 degrees
            for(int i=0; i < size; i++){
                float x = 0.1f * (float)Math.cos((double)(radian/size * i));
                float y = 0.1f * (float)Math.sin((double)(radian/size * i)) - 0.07f;
                AlbumComponent a = componentList.get(i);
                Vector3f loc = new Vector3f();
                a.setDefaultTranslation(x,y,- a.getDefaultPopup() * 2);
                a.setDefaultRotationAngle((float)(Math.PI/2.0f - Math.PI/8.0f));
                a.setPopup(a.getDefaultPopup());
                a.setDefaultScale(1.0f);
                a.setMiddlePopupMode(true);
                a.setDefaultScale(0.8f);
                a.moveToDefaultPosition();
            }            
        }
        else if("c".equals(mode)){
            // mode change : tile mode
            int size = componentList.size();
            double radian = 2 * Math.PI; // 360 degrees
            for(int i=0; i < size; i++){
                float x = 0.1f * (float)Math.cos((double)(radian/size * i));
                float y = 0.1f * (float)Math.sin((double)(radian/size * i)) - 0.07f;
                AlbumComponent a = componentList.get(i);
                Vector3f loc = new Vector3f();
                loc = a.getDefaultTranslation();
                a.setDefaultTranslation(x,y,- a.getDefaultPopup() * 2);
                a.setDefaultRotationAngle((float)(Math.PI/2.0f));
                a.setPopup(a.getDefaultPopup());
                a.setDefaultScale(0.5f);
                a.setMiddlePopupMode(false);
                a.moveToDefaultPosition();
            }
        }
        else if("d".equals(mode)){
            int size = componentList.size();
            double radian = 2 * Math.PI; // 360 degrees
            for(int i=0; i < size; i++){
            float x = 0.1f * (float)Math.cos((double)(radian * random.nextFloat()));
            float y = 0.1f * (float)Math.sin((double)(radian * random.nextFloat())) - 0.07f;
            AlbumComponent a = componentList.get(i);
            Vector3f loc = new Vector3f();
            loc = a.getDefaultTranslation();
            a.setDefaultTranslation(x,y,- a.getDefaultPopup() * 2);
            a.setDefaultRotationAngle((float)(Math.PI/2.0f));
            a.setPopup(a.getDefaultPopup());
            a.setDefaultScale(0.5f);
            a.setMiddlePopupMode(false);
            a.moveToDefaultPosition();
            }
        }
    }
    
    private Component3D makeButton(Disc disc, Color4f color){
        Component3D result = new Component3D();
        Component3D component = new Component3D();

        Cylinder cylinder = new Cylinder(0.0045f,0.01f,
                new SimpleAppearance(color.x,color.y,color.z,color.w));
        component = new Component3D();
        component.addChild(cylinder);
        component.setRotationAxis(1.0f,0.0f,0.0f);
        component.setRotationAngle((float)Math.PI/2.0f);
        result.addChild(component);

        component = new Component3D();
        if(disc != null)
            component.addChild(disc);
        component.setTranslation(0.0f,0.0f, 0.0055f);
        result.addChild(component);

        return result;
    }

    
    private AlbumComponent lookaheadAlbumComponent = null;

    
    private void informationLoad(AlbumShelf s, AlbumComponent[] components, AlbumInformation[] informations, String encode){
        for(int i=0; i < components.length; i++){

            TextureCache searchTarget = new TextureCache(informations[i]);
            TextureCache cache = searchTextureCache( searchTarget );
                    
            if(cache == null)
                cache = searchTarget;
                    
            components[i].setJacket( informations[i].getImage() );

            if( cache.jacketTexture != null ){
                components[i].paintJacket( cache.jacketTexture );
            }
            else{
                components[i].paintJacket();
                cache.jacketTexture = components[i].getJacketTexture();
            }
                    
            AbstractList<FileInfoBean> list = informations[i].getPlayList( encode );

            components[i].setAlbumInformation(informations[i]);
            components[i].setFileList(list);

            if( cache.playListTexture != null )
                components[i].paintPlayList( cache.playListTexture );
            else{
                components[i].paintPlayList();
                cache.playListTexture = components[i].getPlayListTexture();
            }
            insertTextureCache(cache);
        }
    }
                
        // compressTextureCache();

    private void lookAheadTextureCache(AlbumInformation[] lookahead){
        // make cache
        //lookahead = null;
        if( lookahead != null ){
            AlbumComponent a = lookaheadAlbumComponent;
            for(int i=0; i < lookahead.length; i++){
                TextureCache searchTarget = new TextureCache(lookahead[i]);
                TextureCache cache = searchTextureCache(searchTarget);
                if( cache != null )
                    insertTextureCache(cache);
                else {
                    cache = searchTarget;

                    a.setArtist(cache.artist);
                    a.setTitle(cache.title);

                    AbstractList<FileInfoBean> list = lookahead[i].getPlayList( encode );
                    a.setAlbumInformation( lookahead[i] );
                    a.setFileList(list);
                    a.paintPlayList();
                    cache.playListTexture = a.getPlayListTexture();

                    a.setJacket( lookahead[i].getImage() );
                    a.paintJacket();
                    cache.jacketTexture = a.getJacketTexture();
                    insertTextureCache(cache);
                }
            }
        }

        compressTextureCache();

    }

    
}
