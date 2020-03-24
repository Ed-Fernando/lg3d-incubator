/*
 * Player.java
 *
 * Created on 2005/11/04, 15:14
 *
 */

package org.jdesktop.lg3d.apps.trumplayer;

import org.jdesktop.lg3d.apps.trumplayer.mp3player.FileInfoBean;
import org.jdesktop.lg3d.apps.trumplayer.base.PlayerBase;
import org.jdesktop.lg3d.apps.trumplayer.base.ShelfBase;
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
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.Text2D;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;

import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D.ButtonId;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.InputEvent3D;
import org.jdesktop.lg3d.wg.Thumbnail;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class Player extends PlayerBase {
    
    // panel to display music information
    private ImagePanel displayPanel;
    private Component3D displayComponent;
    private boolean enterDisplayPanel = false;
    
    // size of player
    private final static float playerWidth = 0.1f;
    private final static float playerHeight = 0.02f;
    private float playerMoveX;
    private float playerMoveY;

    // Number Component
    private Component3D numPanel[] = new Component3D[32];
    private ImagePanel backgroundPanel[] = new ImagePanel[32];
    int numPanelCurrent = 0;
    int numPanelPosition = 0;
    private Component3D numNext = new Component3D();
    private Component3D numPrev = new Component3D();
    private boolean displayNumberPanel = false;
    private Component3D informationComponent = new Component3D();
    private ImagePanel informationPanel = null;
    
    // button's default position
    private float BUTTON_Y = 0.0f;
    private float BUTTON_Z = 0.0f;

    public void initialize(){
        // initialize
        playerMoveX = 0.0f;
        playerMoveX = 0.0f;
        makePlayer();
        setTranslation(0.0f, -0.085f, 0.0f);
        playerMoveY += -0.085f;
    }

    private void makePlayer(){
        Component3D component;

        // stop button
        Component3D stop = new Component3D();
        // play button
        Component3D play = new Component3D();
        // next button
        Component3D next = new Component3D();
        // previous button
        Component3D prev = new Component3D();
        // pause button
        Component3D pause = new Component3D();
        // menu button
        Component3D menu = new Component3D();

        // change scale button
        Component3D scale = new Component3D();
        
        
        // Disc to print image of signature
        Disc stopFront = null;
        Disc playFront = null;
        Disc nextFront = null;
        Disc prevFront = null;
        Disc pauseFront = null;
        Disc menuFront = null;
        Disc scaleFront = null;

        // read images
        try{
            ClassLoader loader = this.getClass().getClassLoader();
            stopFront = new Disc(0.01f,100,new SimpleAppearance( loader.getResource("org/jdesktop/lg3d/apps/trumplayer/resources/stop.png") ));
            playFront = new Disc(0.01f,100,new SimpleAppearance( loader.getResource("org/jdesktop/lg3d/apps/trumplayer/resources/play.png")));
            nextFront = new Disc(0.01f,100,new SimpleAppearance( loader.getResource("org/jdesktop/lg3d/apps/trumplayer/resources/next.png")));
            prevFront = new Disc(0.01f,100,new SimpleAppearance( loader.getResource("org/jdesktop/lg3d/apps/trumplayer/resources/prev.png")));
            pauseFront = new Disc(0.01f,100,new SimpleAppearance( loader.getResource("org/jdesktop/lg3d/apps/trumplayer/resources/pause.png")));
            menuFront = new Disc(0.01f,100,new SimpleAppearance( loader.getResource("org/jdesktop/lg3d/apps/trumplayer/resources/menu.png")));
            scaleFront = new Disc(0.01f,100,new SimpleAppearance( loader.getResource("org/jdesktop/lg3d/apps/trumplayer/resources/menu.png")));

        }catch(Exception e){
            e.printStackTrace();
        }

        // play previous song button
        prev = makeButton(prevFront, new Color4f(0.6f, 1.0f, 1.0f, 1.0f));
        prev.setTranslation(-0.04f, BUTTON_Y, BUTTON_Z);
        prev.setAnimation(new NaturalMotionAnimation(1000));
        prev.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
                new PressedAction(prev){
                    public void performAction(LgEventSource source,boolean b){
                        super.performAction(source,b);
                        if(b)
                            getPlayer().playPrev();
                    }
                }));

        prev.addListener(new MouseEnteredEventAdapter(
            new PressedAction(prev){
                public void performAction(LgEventSource source,boolean b){
                    Vector3f loc = new Vector3f();
                    getComponent3D().getFinalTranslation(loc);
                    if(loc.z != BUTTON_Z && b == false){
                        super.performAction(source,b);
                    }
                }
            }
        ));
        

        // stop button
        stop = makeButton(stopFront, new Color4f(1.0f, 0.6f, 0.6f, 1.0f));
        stop.setTranslation(-0.02f,  BUTTON_Y, BUTTON_Z);
        stop.setAnimation(new NaturalMotionAnimation(1000));
        stop.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
                new PressedAction(stop){
                    public void performAction(LgEventSource source,boolean b){
                        super.performAction(source,b);
                        if(b)
                            getPlayer().stop();
                    }
                }));
        
        // play button
        play = makeButton(playFront, new Color4f(0.6f, 1.0f, 0.6f, 1.0f));
        play.setTranslation(0.0f,  BUTTON_Y, BUTTON_Z);
        play.setAnimation(new NaturalMotionAnimation(1000));
        play.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
                new PressedAction(play){
                    public void performAction(LgEventSource source,boolean b){
                        super.performAction(source,b);
                        if(b)
                            getPlayer().play();
                    }
                }));
        stop.addListener(new MouseEnteredEventAdapter(
            new PressedAction(stop){
                public void performAction(LgEventSource source,boolean b){
                    Vector3f loc = new Vector3f();
                    getComponent3D().getFinalTranslation(loc);
                    if(loc.z != BUTTON_Z && b == false){
                        super.performAction(source,b);
                    }
                }
            }
        ));
        

        // pause button
        pause = makeButton(pauseFront, new Color4f(1.0f, 1.0f, 0.6f, 1.0f));
        pause.setTranslation(0.02f,  BUTTON_Y, BUTTON_Z);
        pause.setAnimation(new NaturalMotionAnimation(1000));
        pause.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
                new PressedAction(pause){
                    public void performAction(LgEventSource source,boolean b){
                        super.performAction(source,b);
                        if(b)
                            getPlayer().pause();
                    }
                }));
        pause.addListener(new MouseEnteredEventAdapter(
            new PressedAction(pause){
                public void performAction(LgEventSource source,boolean b){
                    Vector3f loc = new Vector3f();
                    getComponent3D().getFinalTranslation(loc);
                    if(loc.z != BUTTON_Z && b == false){
                        super.performAction(source,b);
                    }
                }
            }
        ));
        
        // next button
        next = makeButton(nextFront, new Color4f(0.6f, 1.0f, 1.0f, 1.0f));
        next.setTranslation(0.04f,  BUTTON_Y, BUTTON_Z);
        next.setAnimation(new NaturalMotionAnimation(1000));
        next.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
                new PressedAction(next){
                    public void performAction(LgEventSource source,boolean b){
                        super.performAction(source,b);
                        if(b)
                            getPlayer().playNext();
                    }
                }));
        next.addListener(new MouseEnteredEventAdapter(
            new PressedAction(next){
                public void performAction(LgEventSource source,boolean b){
                    Vector3f loc = new Vector3f();
                    getComponent3D().getFinalTranslation(loc);
                    if(loc.z != BUTTON_Z && b == false){
                        super.performAction(source,b);
                    }
                }
            }
        ));
        
        // menu button
        menu = makeButton(menuFront, new Color4f(1.0f, 1.0f, 1.0f, 1.0f));
        menu.setTranslation(0.06f, 0.015f, BUTTON_Z);
        menu.setAnimation(new NaturalMotionAnimation(1000));
        menu.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
                new PressedAction(menu){
                    public void performAction(LgEventSource source, boolean b){
                        super.performAction(source,b);
                        ShelfBase shelf = manager.getShelfBase();
                        if(shelf != null && b){
                            if(shelf.getFinalScale() == 0.0f)
                                shelf.changeScale(1.0f,1000);
                            else
                                shelf.changeScale(0.0f,1000);
                        }
                    }
                }));
        menu.addListener(new MouseEnteredEventAdapter(
            new PressedAction(menu){
                public void performAction(LgEventSource source,boolean b){
                    Vector3f loc = new Vector3f();
                    getComponent3D().getFinalTranslation(loc);
                    if(loc.z != BUTTON_Z && b == false){
                        super.performAction(source,b);
                    }
                }
            }
        ));


        // change scale button
        scale = makeButton(scaleFront, new Color4f(1.0f, 0.2f, 0.2f, 1.0f));
        scale.setTranslation(-0.06f, 0.015f, BUTTON_Z);
        scale.setAnimation(new NaturalMotionAnimation(1000));
        scale.addListener(new MousePressedEventAdapter(ButtonId.BUTTON1,
                new PressedAction(scale){
                    public void performAction(LgEventSource source, boolean b){
                        super.performAction(source,b);
                        if(b){
                            Frame3D frame = manager.getFrame3D();
                            if(frame.getFinalScale() == 0.5f)
                                frame.changeScale(1.0f);
                            else frame.changeScale(0.5f);
                        }
                    }
                }));
        scale.addListener(new MouseEnteredEventAdapter(
            new PressedAction(scale){
                public void performAction(LgEventSource source,boolean b){
                    Vector3f loc = new Vector3f();
                    getComponent3D().getFinalTranslation(loc);
                    if(loc.z != BUTTON_Z && b == false){
                        super.performAction(source,b);
                    }
                }
            }
        ));

        addChild(stop);
        addChild(play);
        addChild(next);
        addChild(prev);
        addChild(pause);
        addChild(menu);
        addChild(scale);

        // Create a Display Panel
        Component3D display = makeDisplay();
        display.setTranslation(0.0f, 0.015f,0.0f);
        addChild(display);
        

        // Display Panel Turn Button
        Component3D turnComponent = new Component3D();
        ImagePanel turnPanel = null;

        // read image
        try{
            turnPanel = new ImagePanel( 0.01f,0.01f );
            turnPanel.setCapability(ImagePanel.ALLOW_APPEARANCE_OVERRIDE_WRITE);

            ClassLoader loader = this.getClass().getClassLoader();
            SimpleAppearance turnImage = new SimpleAppearance( loader.getResource("org/jdesktop/lg3d/apps/trumplayer/resources/turn.png") );
            turnPanel.setAppearance(turnImage);

        } catch(Exception e){
            e.printStackTrace();
        }
        turnComponent.addChild(turnPanel);
        turnComponent.setTranslation(0.045f,0.024f,0.003f);
        turnComponent.addListener(
                    new MouseClickedEventAdapter(
                        new DisplayPanelTurnAction(displayComponent)
                    ));

        addChild(turnComponent);

        // initialize noPanel
        initNumPanel();

        display.setAnimation(new NaturalMotionAnimation(1000));

        
        playerMoveY += 0.015f;

    }
    
    private void initNumPanel(){
        
        for(int i=0; i < 32; i++){
            numPanel[i] = new Component3D();
            
            // create a number panel
            Text2D text = new Text2D(Integer.toString(i+1), new Color3f(0.0f,0.0f,0.0f), fontName, 18, 0);
            Appearance a = text.getAppearance();
            Texture t = a.getTexture();

            ImagePanel numImagePanel = new ImagePanel(0.01f, 0.015f);

            SimpleAppearance sa1 = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
            sa1.setCapability(SimpleAppearance.ALLOW_TEXTURE_WRITE);
            sa1.setTexture(t);
            
            numImagePanel.setAppearance(sa1);
            
            
            // create a background panel

            SimpleAppearance appearance = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
            appearance.setCapability(SimpleAppearance.ALLOW_MATERIAL_WRITE);
            // GlassyPanel backgroundPanel = new GlassyPanel(0.005f,0.01f,0.001f,0.002f,appearance);
            // FuzzyEdgePanel backgroundPanel = new FuzzyEdgePanel(0.01f, 0.015f, 0.002f, appearance);
            backgroundPanel[i] = new ImagePanel(0.01f, 0.015f);
            backgroundPanel[i].setAppearance(appearance);
            
            Component3D numComponent = new Component3D();
            numComponent.addChild(numImagePanel);
            numComponent.setTranslation(0.0f,0.0f,0.002f);
            numPanel[i].addChild(numComponent);
            numPanel[i].addChild(backgroundPanel[i]);
            numPanel[i].setTranslation(- 0.04f + 0.011f*i,0.015f,0.01f);
            
            // for test : if(i > 7) numPanel[i].setVisible(false);
            numPanel[i].setVisible(false);
            numPanel[i].setScale(0.0f);
            numPanel[i].setAnimation(new NaturalMotionAnimation(1000));

            // Popup when mouse entered in the number panel field
            MouseEnteredEventAdapter enteredAdapter =
                    new MouseEnteredEventAdapter(new NumPanelEnteredAction(numPanel[i]));
            numPanel[i].addListener(enteredAdapter);
            numPanel[i].addListener(
                    new MouseClickedEventAdapter(
                        new ClickedNumberPanelAction(numPanel[i])
                    ));

            addChild(numPanel[i]);
    
        }
        

        // create a next button
        // create a number panel
        Text2D next = new Text2D(new String(">"), new Color3f(0.0f,0.0f,0.0f), fontName, 18, 0);
        Appearance na1 = next.getAppearance();
        Texture nt1 = na1.getTexture();

        ImagePanel nNumImagePanel = new ImagePanel(0.005f, 0.015f);
        
        SimpleAppearance nsa1 = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
        nsa1.setTexture(nt1);

        nNumImagePanel.setAppearance(nsa1);
            
        // create a background panel
        SimpleAppearance nAppearance = new SimpleAppearance(0.6f,0.6f,1.0f,1.0f);
        nAppearance.setCapability(SimpleAppearance.ALLOW_MATERIAL_WRITE);
        ImagePanel nBackgroundPanel = new ImagePanel(0.005f, 0.015f);

        // appearance = new SimpleAppearance(1.0f,0.5f,0.5f,1.0f);
        nBackgroundPanel.setAppearance(nAppearance);

        Component3D nextComponent = new Component3D();
        nextComponent.addChild(nNumImagePanel);
        nextComponent.setTranslation(0.0f,0.0f,0.002f);
        numNext.addChild(nextComponent);
        numNext.addChild(nBackgroundPanel);
        numNext.setTranslation(0.046f,0.015f,0.01f);

        numNext.setAnimation(new NaturalMotionAnimation(1000));
        numNext.setScale(0.0f);

        numNext.addListener(
                new MouseEnteredEventAdapter(new PanelEnteredAction(nAppearance)) );

        numNext.addListener(
                    new MouseClickedEventAdapter(
                        new ChangeNumberAction(1)
                    ));
        
        addChild(numNext);

        // create a prev button
        Text2D prev = new Text2D(new String("<"), new Color3f(0.0f,0.0f,0.0f), fontName, 18, 0);
        Appearance pa1 = prev.getAppearance();
        Texture pt1 = pa1.getTexture();

        ImagePanel pNumImagePanel = new ImagePanel(0.005f, 0.015f);
        
        SimpleAppearance psa1 = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
        psa1.setTexture(pt1);

        pNumImagePanel.setAppearance(psa1);
            
        // create a background panel
        SimpleAppearance pAppearance = new SimpleAppearance(0.6f,0.6f,1.0f,1.0f);
        pAppearance.setCapability(SimpleAppearance.ALLOW_MATERIAL_WRITE);
        ImagePanel pBackgroundPanel = new ImagePanel(0.005f, 0.015f);

        // appearance = new SimpleAppearance(1.0f,0.5f,0.5f,1.0f);
        pBackgroundPanel.setAppearance(pAppearance);

        Component3D prevComponent = new Component3D();
        prevComponent.addChild(pNumImagePanel);
        prevComponent.setTranslation(0.0f,0.0f,0.002f);
        numPrev.addChild(prevComponent);
        numPrev.addChild(pBackgroundPanel);
        numPrev.setTranslation(- 0.049f,0.015f,0.01f);

        numPrev.setAnimation(new NaturalMotionAnimation(1000));
        numPrev.setScale(0.0f);
        numPrev.addListener(
                new MouseEnteredEventAdapter(new PanelEnteredAction(pAppearance)) );
        numPrev.addListener(
                    new MouseClickedEventAdapter(
                        new ChangeNumberAction(-1)
                    ));
                
        addChild(numPrev);

        
        // create a information display
        // information panel
        informationPanel = new ImagePanel(0.1f, 0.02f);
        SimpleAppearance isa = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
        isa.setCapability(SimpleAppearance.ALLOW_TEXTURE_WRITE);
        informationPanel.setAppearance(isa);
            
        // create a background panel
        SimpleAppearance isa_back = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
        ImagePanel informationBackPanel = new ImagePanel(0.1f,0.02f);
        
        informationBackPanel.setAppearance(isa_back);

        Component3D informationPanelComponent = new Component3D();
        informationPanelComponent.addChild(informationPanel);
        informationPanelComponent.setTranslation(0.0f,0.0f,0.0005f);
        
        informationComponent.addChild(informationPanelComponent);
        informationComponent.addChild(informationBackPanel);
        informationComponent.setAnimation(new NaturalMotionAnimation(1000));
        
        informationComponent.setTranslation(0.035f, 0.04f,0.01f);
        
        informationComponent.setVisible(false);
        
        addChild(informationComponent);

    
    }
    
    public void updateInformationComponent(Component3D component){
        int number = 0;
        for(int i=0; i<32; i++){
            if( numPanel[i].equals(component) ){
                number = i;
                break;
            }
        }
        
        // get ID3Tag Information
        AbstractList<FileInfoBean> playlist = mp3player.getPlayList();
        FileInfoBean mp3 = playlist.get(number);
        ID3Tag id3 = mp3.getID3Tag();

        // create a texture of MP3
        BufferedImage image = new BufferedImage(500,100,BufferedImage.TYPE_INT_ARGB|BufferedImage.SCALE_SMOOTH);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        g.setColor(Color.BLACK);
        
        //System.out.println("FontName : " + fontName);
        g.setFont(new Font(fontName, Font.BOLD, 22));
        if( id3 != null ){
            g.drawString( ("Title : " + id3.getTitle()) ,0,24);
            g.drawString( ("Artist : " +id3.getArtist()) ,0,48);
            if( id3.getAlbum() != null && id3.getAlbum().equals("") == false )
                g.drawString( "Album : " + id3.getAlbum() , 0,72);
        } else if ( mp3.getFilePath() != null && mp3.getFilePath().equals("") == false ) {
            g.drawString( ("File : " + mp3.getFilePath() ) ,0,24);
        } else {
            g.drawString( "No Information" ,0,24);
        }

        g.dispose();
        
        TextureLoader loader = new TextureLoader(image);
        Texture texture = loader.getTexture();
        this.informationPanel.getAppearance().setTexture(texture);
        
    }

    private Thumbnail thumbnail = null;
    
    public Thumbnail getThumbnail(){
        if(thumbnail == null){
            thumbnail = new Thumbnail();
            thumbnail.setPreferredSize(new Vector3f(0.01f, 0.01f, 0.01f));
            
            Component3D component = new Component3D();

            // stop button
            Component3D stop = new Component3D();
            // play button
            Component3D play = new Component3D();
            // next button
            Component3D next = new Component3D();
            // previous button
            Component3D prev = new Component3D();
            // pause button
            Component3D pause = new Component3D();
            // menu button
            Component3D menu = new Component3D();

            // change scale button
            Component3D scale = new Component3D();


            // play previous song button
            prev = makeButton(null, new Color4f(0.6f, 1.0f, 1.0f, 1.0f));
            prev.setTranslation(-0.04f, 0.0f,0.0f);
            // stop button
            stop = makeButton(null, new Color4f(1.0f, 0.6f, 0.6f, 1.0f));
            stop.setTranslation(-0.02f, 0.0f,0.0f);
            // play button
            play = makeButton(null, new Color4f(0.6f, 1.0f, 0.6f, 1.0f));
            play.setTranslation(0.0f, 0.0f,0.0f);
            // pause button
            pause = makeButton(null, new Color4f(1.0f, 1.0f, 0.6f, 1.0f));
            pause.setTranslation(0.02f, 0.0f,0.0f);
            // next button
            next = makeButton(null, new Color4f(0.6f, 1.0f, 1.0f, 1.0f));
            next.setTranslation(0.04f, 0.0f,0.0f);
            // menu button
            menu = makeButton(null, new Color4f(1.0f, 1.0f, 1.0f, 1.0f));
            menu.setTranslation(0.06f, 0.015f,0.0f);

            // change scale button
            scale = makeButton(null, new Color4f(1.0f, 0.2f, 0.2f, 1.0f));
            scale.setTranslation(-0.06f, 0.015f,0.0f);

            component.addChild(stop);
            component.addChild(play);
            component.addChild(next);
            component.addChild(prev);
            component.addChild(pause);
            component.addChild(menu);
            component.addChild(scale);

            // Create a Display Panel
            Component3D display = new Component3D();

            SimpleAppearance appearance = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
            GlassyPanel panel = new GlassyPanel(playerWidth,playerHeight,0.001f,0.002f,appearance);

            display.addChild(panel);

            Text2D text = new Text2D(new String("trumplayer"), new Color3f(0.0f,0.0f,0.2f), fontName, 18, 0);
            Appearance a = text.getAppearance();
            Texture t = a.getTexture();

            ImagePanel displayPanel = new ImagePanel(0.1f-0.01f, 0.02f);

            SimpleAppearance sa = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
            sa.setCapability(SimpleAppearance.ALLOW_TEXTURE_WRITE);
            sa.setTexture(t);
        
            displayPanel.setCapability(ImagePanel.ALLOW_APPEARANCE_OVERRIDE_WRITE);
            displayPanel.setAppearance(sa);

            Component3D panelComponent = new Component3D();
            panelComponent.addChild(displayPanel);
            panelComponent.setTranslation(0.001f, 0.004f, 0.0f);

            
            display.addChild(panelComponent);
            display.setTranslation(0.0f,0.015f,0.0f);
        
            component.addChild(display);
            component.setScale(0.20f);
            
            thumbnail.addChild(component);
        }
        return thumbnail;
    }
    
    private Component3D makeButton(Disc disc, Color4f color){
        Component3D result = new Component3D();
        Component3D component = new Component3D();

        Cylinder cylinder = new Cylinder(0.005f,0.01f,
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

    private Component3D makeDisplay(){
        
        displayComponent = new Component3D();

        SimpleAppearance appearance = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
        GlassyPanel panel = new GlassyPanel(playerWidth,playerHeight,0.001f,0.002f,appearance);

        Text2D text = new Text2D(new String("trumplayer"), new Color3f(0.0f,0.0f,0.2f), fontName, 18, 0);
        Appearance a = text.getAppearance();
        Texture t = a.getTexture();

        displayPanel = new ImagePanel(0.1f-0.01f, 0.02f);

        SimpleAppearance sa = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
        sa.setCapability(SimpleAppearance.ALLOW_TEXTURE_WRITE);
        sa.setTexture(t);
        
        displayPanel.setCapability(ImagePanel.ALLOW_APPEARANCE_OVERRIDE_WRITE);
        displayPanel.setAppearance(sa);

        Component3D panelComponent = new Component3D();
        panelComponent.addChild(displayPanel);
        panelComponent.setTranslation(0.001f, 0.004f, 0.0f);

        panelComponent.addListener(
                mp3player.getMusicChangeEventAdapter(
                    new ActionNoArg(){
                        public void performAction(LgEventSource source){
                            updateDisplay();
                        }
                    }, panelComponent) );

                    
        displayComponent.addChild(panel);
        panelComponent.setMouseEventPropagatable(true);
        displayComponent.addChild(panelComponent);
        
        displayComponent.setMouseEventPropagatable(false);
        
        return displayComponent;
    }

    private class DisplayPanelTurnAction implements ActionNoArg {
        Component3D component;
        //boolean pressed = false;
        float z = 0.015f;
        
        public DisplayPanelTurnAction(Component3D c){
            component = c;
        }
        
        public void performAction(LgEventSource source){
            Vector3f loc = new Vector3f();
            component.getFinalTranslation(loc);
            float f = component.getFinalRotationAngle();
            if(f == 0.0f){
                component.changeRotationAngle((float)Math.PI,1000);
                numPrev.changeScale(1.0f,2000);
                numNext.changeScale(1.0f,2000);
                for(int i=0; i < 32; i++)
                    numPanel[i].changeScale(1.0f,2000);
                // display current number field
                displayNumberPanel = true;
                updateNumberPanel();
            } else {
                component.changeRotationAngle(0.0f,1000);
                numPrev.changeScale(0.0f);
                numNext.changeScale(0.0f);
                for(int i=0; i < 32; i++){
                    numPanel[i].changeScale(0.0f);
                }
                displayNumberPanel = false;
            }
        }
        
    }

    
    private class ClickedNumberPanelAction implements ActionNoArg {
        Component3D component;
        
        public ClickedNumberPanelAction(Component3D c){
            component = c;
        }
        
        public void performAction(LgEventSource source){
            // I want to add an animation.....
            int number = 0;
            for(int i=0; i<32; i++){
                if( numPanel[i].equals(component) ){
                    number = i;
                    break;
                }
            }
            mp3player.play(mp3player.getPlayList(),number);
            
        }
        
    }

    
    private void updateNumberPanel(){
        int length = 0;
        AbstractList list = mp3player.getPlayList();
        if( list != null )
            length = list.size();

        int current = mp3player.getCurrent();

        numPanelPosition = current/8 ;
        for(int i=0; i < 32; i++){
            Vector3f loc = new Vector3f();
            numPanel[i].getFinalTranslation(loc);
            numPanel[i].changeTranslation(- 0.04f + 0.011f*(i-8*numPanelPosition),loc.y,loc.z);
            if( i/8 == numPanelPosition && i < length )
                numPanel[i].setVisible(true);
            else numPanel[i].setVisible(false);
        }
    }
    
    private class ChangeNumberAction implements ActionNoArg {
        int number = 0;

        public ChangeNumberAction (int i){
            number = i;
        }
        
        public void performAction(LgEventSource source){
            int length = 0;
            AbstractList list = mp3player.getPlayList();
            if( list != null )
                length = list.size();

            if( numPanelPosition + number >= 0 && numPanelPosition + number < (length-1)/8 + 1 ){
                numPanelPosition += number;
                for(int i=0; i < 32; i++){
                    numPanel[i].changeTranslation(- 0.04f + 0.011f*(i-8*numPanelPosition),0.015f,0.01f);
                    if( i/8 == numPanelPosition && i < length )
                        numPanel[i].setVisible(true);
                    else numPanel[i].setVisible(false);
                }
            }            

        }
        
    }

    
    private void updateDisplay(){                
        ID3Tag id3 = mp3player.getID3Tag();
        
        // create a texture of MP3
        BufferedImage image = new BufferedImage(500,100,BufferedImage.TYPE_INT_ARGB|BufferedImage.SCALE_SMOOTH);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        g.setColor(Color.BLUE);
        
        g.setFont(new Font(fontName, Font.BOLD, 22));

        if ( id3 != null ) {
            g.drawString( "Title : " + id3.getTitle() , 20,55);
            g.drawString( "Artist : " +id3.getArtist() ,20,75);
            g.drawString( "Album : " + id3.getAlbum() , 20,95);
        }
        else {
            g.drawString( "File : " +  mp3player.getFilename() , 20,55);
        }

        g.dispose();

        TextureLoader loader = new TextureLoader(image);
        Texture texture = loader.getTexture();

        // set Texture
        Appearance appearance = displayPanel.getAppearance();
        appearance.setTexture(texture);

        // update number display (backpanel)
        // change color of current playing music number plate
        int current = mp3player.getCurrent();
        if ( current >= 0 && current < 32 ){
            SimpleAppearance appearance2 = (SimpleAppearance) backgroundPanel[numPanelCurrent].getAppearance();
            appearance2.setColor( 1.0f, 1.0f, 1.0f );
            appearance2 = (SimpleAppearance) backgroundPanel[current].getAppearance();
            appearance2.setColor( 1.0f, 0.7f, 0.7f );
            numPanelCurrent = current;
        }
        
        if(displayNumberPanel)
            updateNumberPanel();

    }
    
    public boolean inPlayerArea(float x, float y){

        float scale = manager.getFrame3D().getFinalScale();

        x -= playerMoveX * scale;
        y -= playerMoveY * scale;
        
        boolean result = false;
        if( playerWidth/2.0f  > x && playerWidth/-2.0f < x)
            if( playerHeight > y && playerHeight/-2.0f < y)
                result = true;
        return result;
    }

    private class NumPanelEnteredAction implements ActionBoolean {
        Component3D component;

        /** Creates a new instance of RotateAction */
        public NumPanelEnteredAction(Component3D c) {
            component = c;
        }
        
        public void performAction(LgEventSource source, boolean b) {
            Vector3f loc = new Vector3f();
            component.getFinalTranslation(loc);
            if(b){
                loc.y += 0.005f;
                updateInformationComponent(component);
            }
            else
                loc.y -= 0.005f;
            component.changeTranslation( loc ); 
            informationComponent.changeVisible(b);
        }
    }

    private class PanelEnteredAction implements ActionBoolean {
        SimpleAppearance appearance;

        /** Creates a new instance of RotateAction */
        public PanelEnteredAction(SimpleAppearance a) {
            appearance = a;
        }
        
        public void performAction(LgEventSource source, boolean b) {
            if(b)
                appearance.setColor( 0.6f, 1.0f, 1.0f );
            else 
                appearance.setColor( 0.6f, 0.6f, 1.0f );
        }
    }

}
