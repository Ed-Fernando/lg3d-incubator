/*
 * ViewerContainer.java
 *
 * Created on 2007/01/04, 13:55
 *
 */

package org.jdesktop.lg3d.apps.jsaddle;

import java.awt.image.BufferedImage;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.SwingNode;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.wg.event.MouseWheelEvent3D;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseWheelEventAdapter;
import org.jdesktop.lg3d.utils.action.ActionInt;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.sg.TransparencyAttributes;

import org.jdesktop.lg3d.wg.event.LgEventSource;

import org.jdesktop.lg3d.apps.jsaddle.utils.CylinderButtonComponent3D;
import org.jdesktop.lg3d.apps.jsaddle.utils.ExitButtonComponent3D;
import org.jdesktop.lg3d.apps.jsaddle.utils.MinimizeButtonComponent3D;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class ViewerContainer extends Component3D {

    // private final static float IMAGE_WIDTH = 0.24f;
    // private final static float IMAGE_HEIGHT = 0.18f;
    private final static float IMAGE_WIDTH = 0.20f;
    private final static float IMAGE_HEIGHT = 0.15f;
    
    // scale of the display panel
    private float scale = 1.0f;
    private ViewerContainer viewerContainer = this;
    
    // image panel
    private ImagePanel currentImagePanel = new ImagePanel(IMAGE_WIDTH, IMAGE_HEIGHT);
    private ImagePanel nextImagePanel = new ImagePanel(IMAGE_WIDTH, IMAGE_HEIGHT);
    private ImagePanel prevImagePanel = new ImagePanel(IMAGE_WIDTH, IMAGE_HEIGHT);
    
    // Texture Cache
    private Texture currentTexture;
    private Texture nextTexture;
    private Texture prevTexture;
    
    private boolean nextBoolean = false;
    private boolean prevBoolean = false;
    
    // page change permission flag
    private boolean pageChangePermission = true;

    // image panel component
    private Component3D currentImageComponent = new Component3D();
    private Component3D nextImageComponent = new Component3D();
    private Component3D prevImageComponent = new Component3D();
    private static float PANEL_Z = 0.004f;

    private Component3D imagePanelComponent;
    private Component3D framePanelComponent;
    private SimpleAppearance framePanelAppearance;
    private SwingNode swingNode;
    
    private JSaddleManager manager;
    
    /** Creates a new instance of ViewerContainer */
    public ViewerContainer(JSaddleManager manager) {
        super();

        this.manager = manager;
        setAnimation(new NaturalMotionAnimation(1000));

        SimpleAppearance appearance = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
        appearance.setCapability(SimpleAppearance.ALLOW_TEXTURE_WRITE);
        currentImagePanel.setCapability(ImagePanel.ALLOW_APPEARANCE_OVERRIDE_WRITE);
        currentImagePanel.setAppearance(appearance);
        currentImageComponent.addChild(currentImagePanel);
        currentImageComponent.setAnimation(new NaturalMotionAnimation(1000));
        currentImageComponent.setTranslation(0.0f,0.0f,PANEL_Z);

        SimpleAppearance appearance2 = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
        appearance2.setCapability(SimpleAppearance.ALLOW_TEXTURE_WRITE);
        nextImagePanel.setCapability(ImagePanel.ALLOW_APPEARANCE_OVERRIDE_WRITE);
        nextImagePanel.setAppearance(appearance2);
        nextImageComponent.addChild(nextImagePanel);
        nextImageComponent.setAnimation(new NaturalMotionAnimation(1000));
        nextImageComponent.setScale(0.0f);
        nextImageComponent.setTranslation(0.0f,0.0f,PANEL_Z);

        SimpleAppearance appearance3 = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
        appearance3.setCapability(SimpleAppearance.ALLOW_TEXTURE_WRITE);
        prevImagePanel.setCapability(ImagePanel.ALLOW_APPEARANCE_OVERRIDE_WRITE);
        prevImagePanel.setAppearance(appearance3);
        prevImageComponent.addChild(prevImagePanel);
        prevImageComponent.setAnimation(new NaturalMotionAnimation(1000));
        prevImageComponent.setScale(0.0f);
        prevImageComponent.setTranslation(0.0f,0.0f,PANEL_Z);


        imagePanelComponent = new Component3D();
        imagePanelComponent.addChild(currentImageComponent);
        imagePanelComponent.addChild(nextImageComponent);
        imagePanelComponent.addChild(prevImageComponent);
        
        addChild(imagePanelComponent);

        framePanelComponent = new Component3D();
        framePanelAppearance = new SimpleAppearance(0.95f,0.95f,0.95f,0.5f);
        framePanelAppearance.setCapability(SimpleAppearance.ALLOW_MATERIAL_WRITE);
        Box box = new Box((IMAGE_WIDTH+0.02f)/2.0f, (IMAGE_HEIGHT+0.02f)/2.0f,0.0025f,framePanelAppearance);
        framePanelComponent.addChild(box);
        framePanelComponent.setAnimation(new NaturalMotionAnimation(1000));
        framePanelComponent.setMouseEventPropagatable(true);
        
        addChild(framePanelComponent);
        
        // file chooser setup(swingnode)
        swingNode = new SwingNode();
        swingNode.setPanel(new FileChooser(this.manager));
        swingNode.setRotationAxis(0.0f,1.0f,0.0f);
        swingNode.setRotationAngle((float)Math.PI);
        swingNode.setTranslation(0.0f,0.0f,-0.0026f);

        addChild(swingNode);
        
        initializeEvent();

        // menu button
        initializeMenu();
        
        setRotationAxis(0.0f,1.0f,0.0f);
        setTranslation(0.0f,0.0f,0.001f);

    }
    
    public void setMinimizeButton(MinimizeButtonComponent3D minimum){
        if(minimum!=null){
            minimum.setTranslation(0.093f,0.080f,0.003f);
            addChild(minimum);
        }
    }

    public void setExitButton(ExitButtonComponent3D exit){
        if(exit!=null){
            exit.setTranslation(0.103f,0.080f,0.003f);
            addChild(exit);
        }
    }

    private void initializeEvent(){
        // event of changing to next page of pdf
        MouseClickedEventAdapter clickedAdapter1 = new MouseClickedEventAdapter(
                MouseEvent3D.ButtonId.BUTTON1, new ActionNoArg(){
                    public void performAction(LgEventSource source){
                        changePage(1);
                    }
                }
        );
        imagePanelComponent.addListener(clickedAdapter1);

        // event of changing to prev page of pdf
        MouseClickedEventAdapter clickedAdapter3 = new MouseClickedEventAdapter(
                MouseEvent3D.ButtonId.BUTTON3, new ActionNoArg(){
                    public void performAction(LgEventSource source){
                        changePage(-1);
                    }
                }
        );
        imagePanelComponent.addListener(clickedAdapter3);

        // event of changing scale of pdf viewer
        MouseWheelEventAdapter wheelAdapter = new MouseWheelEventAdapter(
            new ActionInt(){
                public void performAction(LgEventSource source, int value){
                    changeViewerContainerScale(value);
                }
            }
        );
        imagePanelComponent.addListener(wheelAdapter);
        framePanelComponent.addListener(wheelAdapter);

        // event of changing to prev page of pdf
        MouseClickedEventAdapter framePanelClickedAdapter = new MouseClickedEventAdapter(
                MouseEvent3D.ButtonId.BUTTON3, new ActionNoArg(){
                    public void performAction(LgEventSource source){
                        changeViewerMode();
                    }
                }
        );
        framePanelComponent.addListener(framePanelClickedAdapter);

        MouseEnteredEventAdapter enteredAdapter = new MouseEnteredEventAdapter(
                new ActionBoolean(){
                    public void performAction(LgEventSource source, boolean state){
                        if(state)
                            framePanelAppearance.setColor(0.50f,0.95f,0.95f);
                        else
                            framePanelAppearance.setColor(0.95f,0.95f,0.95f);
                    }
                }
        );
        framePanelComponent.addListener(enteredAdapter);

    }
    
    public void changeViewerContainerScale(float value){
        float newscale = scale + 0.1f * value;
        if(newscale > 0.0f && newscale < 3.0f){
            scale = newscale;
            viewerContainer.changeScale(scale);
        }
    }
    
    private void initializeMenu(){
        ImagePanel plusImagePanel = new ImagePanel(IMAGE_WIDTH/5.5f, IMAGE_HEIGHT/5.5f);
        ImagePanel minusImagePanel = new ImagePanel(IMAGE_WIDTH/5.5f, IMAGE_HEIGHT/5.5f);
        ImagePanel thumbImagePanel = new ImagePanel(IMAGE_WIDTH/5.5f, IMAGE_HEIGHT/5.5f);
        try{
            ClassLoader loader = this.getClass().getClassLoader();
            SimpleAppearance appearance = new SimpleAppearance( loader.getResource("org/jdesktop/lg3d/apps/jsaddle/resources/plus.png") );
            plusImagePanel.setAppearance(appearance);
            appearance = new SimpleAppearance( loader.getResource("org/jdesktop/lg3d/apps/jsaddle/resources/minus.png") );
            minusImagePanel.setAppearance(appearance);
            appearance = new SimpleAppearance( loader.getResource("org/jdesktop/lg3d/apps/jsaddle/resources/thumb.png") );
            thumbImagePanel.setAppearance(appearance);
        } catch (java.io.IOException ioe){
            ioe.printStackTrace();
        }
        
        // plus button
        Component3D plusComponent = new Component3D();
        Box plusBox = new Box(IMAGE_WIDTH/10.0f, IMAGE_HEIGHT/10.0f,0.0025f,
                new SimpleAppearance(0.5f,1.0f,1.0f,0.7f));
        Component3D plusImageComponent = new Component3D();
        plusImageComponent.addChild(plusImagePanel);
        plusImageComponent.setTranslation(0.0f,0.0f,0.0026f);
        plusComponent.addChild(plusImageComponent);
        plusComponent.addChild(plusBox);
        CylinderButtonComponent3D plusButton = new CylinderButtonComponent3D(plusComponent);
        plusButton.setDepth(0.0f);
        plusButton.setTranslation(IMAGE_WIDTH/5.0f+0.005f,0.017f-IMAGE_HEIGHT/2.0f,0.024f);
        plusButton.setPressedAction(new ActionBoolean(){
            public void performAction(LgEventSource source, boolean b){
                if(b)
                    changeViewerContainerScale(1.0f);
            }
        });

        // minus button
        Component3D minusComponent = new Component3D();
        Box minusBox = new Box(IMAGE_WIDTH/10.0f, IMAGE_HEIGHT/10.0f,0.0025f,
                new SimpleAppearance(0.5f,1.0f,1.0f,0.7f));
        Component3D minusImageComponent = new Component3D();
        minusImageComponent.addChild(minusImagePanel);
        minusImageComponent.setTranslation(0.0f,0.0f,0.0026f);
        minusComponent.addChild(minusImageComponent);
        minusComponent.addChild(minusBox);
        CylinderButtonComponent3D minusButton = new CylinderButtonComponent3D(minusComponent);
        minusButton.setDepth(0.0f);
        minusButton.setTranslation(-IMAGE_WIDTH/5.0f-0.005f,0.017f-IMAGE_HEIGHT/2.0f,0.024f);
        minusButton.setPressedAction(new ActionBoolean(){
            public void performAction(LgEventSource source, boolean b){
                if(b)
                    changeViewerContainerScale(-1.0f);
            }
        });

        // thumb button
        Component3D thumbComponent = new Component3D();
        Box thumbBox = new Box(IMAGE_WIDTH/10.0f, IMAGE_HEIGHT/10.0f,0.0025f,
                new SimpleAppearance(0.5f,1.0f,1.0f,0.7f));
        Component3D thumbImageComponent = new Component3D();
        thumbImageComponent.addChild(thumbImagePanel);
        thumbImageComponent.setTranslation(0.0f,0.0f,0.0026f);
        thumbComponent.addChild(thumbImageComponent);
        thumbComponent.addChild(thumbBox);
        CylinderButtonComponent3D thumbButton = new CylinderButtonComponent3D(thumbComponent);
        thumbButton.setDepth(0.0f);
        thumbButton.setTranslation(0.0f,0.017f-IMAGE_HEIGHT/2.0f,0.024f);
        thumbButton.setPressedAction(new ActionBoolean(){
            public void performAction(LgEventSource source, boolean b){
                if(b)
                    manager.changeMode();
            }
        });

        // menu
        Component3D menuComponent = new Container3D();
        menuComponent.addChild(minusButton);
        menuComponent.addChild(thumbButton);
        menuComponent.addChild(plusButton);

        // clear box
        Box clearBox = new Box( 3*IMAGE_WIDTH/10.0f+0.005f, IMAGE_HEIGHT/40.0f,0.0025f,
                new SimpleAppearance(1.0f,1.0f,1.0f,0.0f));

        Component3D clearComponent = new Component3D();
        clearComponent.addChild(clearBox);
        clearComponent.setTranslation(0.0f,0.017f-IMAGE_HEIGHT/2.0f-IMAGE_HEIGHT/10.0f+IMAGE_HEIGHT/40.0f,0.024f);

        menuComponent.addListener(new MouseEnteredEventAdapter(new ButtonEnteredAction(clearComponent, menuComponent )));
        clearComponent.addListener(new MouseEnteredEventAdapter(new ButtonEnteredAction(clearComponent, menuComponent)));
        addChild(clearComponent);
        addChild(menuComponent);

        menuComponent.setVisible(false);
    
    }
    
    public class ButtonEnteredAction implements ActionBoolean {
        protected Component3D component1;
        protected Component3D component2;
    
        public ButtonEnteredAction(Component3D c1, Component3D c2){
            component1 = c1;
            component2 = c2;
        }

        public void performAction(LgEventSource source, boolean b){
            if(b){
                component1.setVisible(false);
                component2.setVisible(true);
            } else {
                component1.setVisible(true);
                component2.setVisible(false);                
            }
        }
    }

    public void changeViewerMode(){
        if(getFinalRotationAngle() != 0.0f)
            changeRotationAngle(0.0f);
        else
            changeRotationAngle((float)Math.PI);
    }
    
    ChangePageThread changePageThread;
    
    private void changePage(int value){
        synchronized(this){
            if( (changePageThread == null || changePageThread.isAlive() == false) &&
                pageChangePermission == true ){
                pageChangePermission = false;
                ChangePageThread thread = new ChangePageThread(value);
                thread.start();
            }
        }
    }
    
    private class ChangePageThread extends Thread {
        int value;
        public ChangePageThread(int value){
            this.value = value;
        }
        public void run(){
            Component3D tempImageComponent;
            ImagePanel tempImagePanel;
            Texture tempTexture;

            // new ChangePageThread(value).start();
            if(value == 1 && nextTexture != null){
                
                synchronized(this){
                    //currentImageComponent.setScale(0.0f);

                    Vector3f translation = new Vector3f();

                    // move previous object to the position of the next object
                    prevImageComponent.setTranslation(0.15f,0.0f,-PANEL_Z);
                    prevImageComponent.setRotationAxis(0.0f,1.0f,1.0f);
                    prevImageComponent.setScale(0.0f);
                    prevImageComponent.setRotationAngle(0.0f);
                    

                    // move current object to the position of the previous object
                    currentImageComponent.changeTranslation(-0.15f,0.0f,-PANEL_Z,2000);
                    currentImageComponent.setRotationAxis(0.0f,1.0f,1.0f);
                    currentImageComponent.setRotationAngle(- (float)Math.PI*3.0f );
                    currentImageComponent.changeScale(0.0f,2000);
                    currentImageComponent.changeRotationAngle(0.0f,2000);

                    try{
                        this.sleep(100);
                    } catch(Exception e){
                        e.printStackTrace();
                    }

                    // move next object to the position of the current object
                    nextImageComponent.setTranslation(0.15f,0.0f,-PANEL_Z);
                    nextImageComponent.changeTranslation(0.0f,0.0f,PANEL_Z,2000);
                    nextImageComponent.setRotationAxis(0.0f,1.0f,1.0f);
                    nextImageComponent.setRotationAngle(- (float)Math.PI*3.0f );
                    nextImageComponent.changeScale(1.0f,2000);
                    nextImageComponent.changeRotationAngle(0.0f,2000);
                    
                    try{
                        this.sleep(2200);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    
                    currentImageComponent.setTranslation(0.0f,0.0f,PANEL_Z);
                }
                if(nextBoolean == false)
                    requireNextImageCache();
                // change status
                nextBoolean = false;

                // swap object
                tempImageComponent = prevImageComponent;
                tempImagePanel = prevImagePanel;
                tempTexture = prevTexture;
                prevImageComponent = currentImageComponent;
                prevImagePanel = currentImagePanel;
                prevTexture = currentTexture;
                currentImageComponent = nextImageComponent;
                currentImagePanel = nextImagePanel;
                currentTexture = nextTexture;
                nextImageComponent = tempImageComponent;
                nextImagePanel = tempImagePanel;
                nextTexture = tempTexture;
                manager.movePageFromCurrent(1);
                requireNextImageCache();

                // change page change permission
                pageChangePermission = true;

            } else if (value == -1 && prevTexture != null){
                synchronized(this){

                    // move next object to the position of the previous object
                    nextImageComponent.setTranslation(-0.15f,0.0f,-PANEL_Z);
                    nextImageComponent.setRotationAxis(0.0f,1.0f,1.0f);
                    nextImageComponent.setScale(0.0f);
                    nextImageComponent.setRotationAngle(0.0f);

                    // move current object to the position of the next object
                    currentImageComponent.changeTranslation(0.15f,0.0f,-PANEL_Z,2000);
                    currentImageComponent.setRotationAxis(0.0f,1.0f,1.0f);
                    currentImageComponent.setRotationAngle((float)Math.PI*3.0f );
                    currentImageComponent.changeScale(0.0f,2000);
                    currentImageComponent.changeRotationAngle(0.0f,2000);

                    try{
                        this.sleep(100);
                    } catch(Exception e){
                        e.printStackTrace();
                    }

                    // move previous object to the position of the current object
                    prevImageComponent.setTranslation(-0.15f,0.0f,-PANEL_Z);
                    prevImageComponent.changeTranslation(0.0f,0.0f,PANEL_Z,2000);
                    prevImageComponent.changeScale(1.0f,2000);
                    prevImageComponent.setRotationAxis(0.0f,1.0f,1.0f);
                    prevImageComponent.setRotationAngle( (float)Math.PI*3.0f );
                    prevImageComponent.changeRotationAngle(0.0f,2000);


                    try{
                        this.sleep(2200);
                    } catch(Exception e){
                        e.printStackTrace();
                    }

                    currentImageComponent.setTranslation(0.0f,0.0f,PANEL_Z);

                }

                if(prevBoolean == false)
                    requireNextImageCache();
                // change status
                prevBoolean = false;

                // swap object
                tempImageComponent = nextImageComponent;
                tempImagePanel = nextImagePanel;
                tempTexture = nextTexture;
                nextImageComponent = currentImageComponent;
                nextImagePanel = currentImagePanel;
                nextTexture = currentTexture;
                currentImageComponent = prevImageComponent;
                currentImagePanel = prevImagePanel;
                currentTexture = prevTexture;
                prevImageComponent = tempImageComponent;
                prevImagePanel = tempImagePanel;
                prevTexture = tempTexture;
                manager.movePageFromCurrent(-1);
                requirePrevImageCache();

                // change page change permission
                pageChangePermission = true;
                
            }
        }
    }

    public void setImage(BufferedImage image){
        if(image != null){
            // create a texture and set it to the ImagePanel
            TextureLoader loader = new TextureLoader(image);
            currentTexture = loader.getTexture();
            currentImagePanel.getAppearance().setTexture(currentTexture);
            requireNextImageCache();
            requirePrevImageCache();
        }
    }
    
    public void requireNextImageCache(){
        BufferedImage nextImage = manager.getPageFromCurrent(1);
        if(nextImage != null){
            // create a texture and set it to the ImagePanel
            TextureLoader loader = new TextureLoader(nextImage);
            nextTexture = loader.getTexture();
            nextImagePanel.getAppearance().setTexture(nextTexture);
            nextBoolean = true;
        } else
            nextTexture = null;
    }

    public void requirePrevImageCache(){
        BufferedImage prevImage = manager.getPageFromCurrent(-1);
        if(prevImage != null){
            // create a texture and set it to the ImagePanel
            TextureLoader loader = new TextureLoader(prevImage);
            prevTexture = loader.getTexture();
            prevImagePanel.getAppearance().setTexture(prevTexture);
            prevBoolean = true;
        } else
            prevTexture = null;
    }
    
}
