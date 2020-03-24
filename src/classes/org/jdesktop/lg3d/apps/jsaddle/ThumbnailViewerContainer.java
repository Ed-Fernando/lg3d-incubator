/*
 * ThumbnailViewerContainer.java
 *
 * Created on 2007/01/05, 13:39
 *
 */

package org.jdesktop.lg3d.apps.jsaddle;

import java.awt.image.BufferedImage;
import javax.vecmath.Vector3f;
import java.util.Vector;
import org.jdesktop.lg3d.apps.jsaddle.thumbnailaction.Thumbnail3DTimeFrameAction;
import org.jdesktop.lg3d.apps.jsaddle.thumbnailaction.ThumbnailFilmLikeAction;
import org.jdesktop.lg3d.apps.jsaddle.thumbnailaction.ThumbnailZScrollAction;
import org.jdesktop.lg3d.apps.jsaddle.thumbnailaction.ThumbnailRotateAction;
import org.jdesktop.lg3d.apps.jsaddle.thumbnailaction.ThumbnailAction;

import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.Cylinder;
import org.jdesktop.lg3d.utils.shape.Text2D;
import org.jdesktop.lg3d.utils.shape.Cone;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.wg.event.MouseWheelEvent3D;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseWheelEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.action.ActionInt;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.ActionBoolean;

import org.jdesktop.lg3d.wg.event.LgEventSource;

import org.jdesktop.lg3d.apps.jsaddle.utils.CylinderButtonComponent3D;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class ThumbnailViewerContainer extends Container3D {

    private Vector<Component3D> panelList = new Vector<Component3D>();
    private Vector<Box> boxList = new Vector<Box>();

    public final static float IMAGE_WIDTH = 0.06f;
    public final static float BOX_WIDTH = (IMAGE_WIDTH+0.004f)/2.0f;
    public final static float IMAGE_HEIGHT = 0.045f;
    public final static float BOX_HEIGHT = (IMAGE_HEIGHT+0.004f)/2.0f;
    
    private JSaddleManager manager;
    private Component3D thumbnailViewer = new Component3D();

    private int current;

    private ThumbnailAction[] actions = {
        new ThumbnailRotateAction(thumbnailViewer,panelList),
        new ThumbnailFilmLikeAction(thumbnailViewer,panelList),
        new Thumbnail3DTimeFrameAction(thumbnailViewer,panelList),
        new ThumbnailZScrollAction(thumbnailViewer,panelList)
    };
    
    private ThumbnailAction thumbnailAction = actions[0];
    
    /** Creates a new instance of ThumbnailViewerContainer */
    public ThumbnailViewerContainer(JSaddleManager manager) {
        this.manager = manager;
        initialize();
        initializeButton();
    }

    public ThumbnailViewerContainer(JSaddleManager manager, Vector<BufferedImage> imageList){
        this(manager);
        setThumbnailImage(imageList);
    }

    public ThumbnailViewerContainer(JSaddleManager manager, BufferedImage[] imageArray){
        this(manager);
        Vector<BufferedImage> imageList = null;
        if(imageArray != null){
            imageList = new Vector<BufferedImage>();
            for(int i=0; i<imageArray.length; i++)
                imageList.add(imageArray[i]);
        }
        setThumbnailImage(imageList);
    }
    

    private void initialize(){
        
        thumbnailViewer.setAnimation(new NaturalMotionAnimation(1000));
        thumbnailViewer.setTranslation(0.0f,0.0f,-0.05f);
        addChild(thumbnailViewer);
        
        setAnimation(new NaturalMotionAnimation(1000));
        //setRotationAxis(0.0f,0.0f,0.0f);

        // event of changing scale of pdf viewer
        MouseWheelEventAdapter wheelAdapter = new MouseWheelEventAdapter(
            new ActionInt(){
                public void performAction(LgEventSource source, int value){
                    scrollAction(value);
                }
            }
        );
        addListener(wheelAdapter);

        setRotationAxis(0.0f,1.0f,0.0f);
    }

    private void scrollAction(int value){
        current += value;
        boolean result = thumbnailAction.setLocation(current,value);
        if(result == false)
            current -= value;
    }
    

    private void initializeButton(){
    
        // mode button
        for(int i=0; i < actions.length; i++){
            Component3D component = new Component3D();
            Cylinder cylinder = new Cylinder(0.005f,0.01f,
                    new SimpleAppearance(0.5f,1.0f,1.0f,1.0f));
            component.addChild(cylinder);
            component.setRotationAxis(1.0f,0.0f,0.0f);
            component.setRotationAngle((float)Math.PI/2.0f);
            component.setAnimation(new NaturalMotionAnimation(1000));
            component.addListener(new MousePressedEventAdapter(MouseEvent3D.ButtonId.BUTTON1,
                new PressedAction( component, i ){
                    public void performAction(LgEventSource source,boolean b){
                        super.performAction(source,b);
                        if(b){
                            thumbnailAction = actions[num];
                            thumbnailAction.initLocation();
                            boolean result = thumbnailAction.setLocation(current,0);
                            if(result == false){
                                int size = panelList.size();
                                System.out.print(current + " : ");
                                if(current > size)
                                    current = current % size;
                                else if(current <= 0)
                                    current = size + current % size;
                                thumbnailAction.setLocation(current,0);
                            }
                        }
                    }
                }
            ));
            component.addListener(new MouseEnteredEventAdapter(new EnteredAction(component)));
            component.setTranslation(i*0.02f-0.03f,-0.08f,0.0f);
            // component.setTranslation(0.005f*i,0.0f,0.0f);
            addChild(component);
        }


        // scroll next button
        Component3D nextComponent = new Component3D();
        Cone nextCone = new Cone(0.004f,0.01f,Cone.CAP,20,3,new SimpleAppearance(1.0f,1.0f,0.0f,1.0f));
        nextComponent.addChild(nextCone);
        nextComponent.setRotationAxis(0.0f,0.0f,1.0f);
        nextComponent.setRotationAngle(- (float)(Math.PI/2.0));
        
        CylinderButtonComponent3D nextButton = new CylinderButtonComponent3D(nextComponent);
        nextButton.setTranslation(0.03f,-0.065f,0.0f);
        nextButton.setPressedAction(new ActionBoolean(){
            public void performAction(LgEventSource source, boolean b){
                if(b)
                    scrollAction(1);
            }
        });
        addChild(nextButton);

        // scroll prex button
        Component3D prevComponent = new Component3D();;
        Cone prevCone = new Cone(0.004f,0.01f,Cone.CAP,20,3,new SimpleAppearance(1.0f,1.0f,0.0f,1.0f));
        prevComponent.addChild(prevCone);
        prevComponent.setRotationAxis(0.0f,0.0f,1.0f);
        prevComponent.setRotationAngle((float)(Math.PI/2.0));

        CylinderButtonComponent3D prevButton = new CylinderButtonComponent3D(prevComponent);
        prevButton.setTranslation(-0.03f,-0.065f,0.0f);
        prevButton.setPressedAction(new ActionBoolean(){
            public void performAction(LgEventSource source, boolean b){
                if(b)
                    scrollAction(-1);
            }
        });
        addChild(prevButton);

        // return main view button
        Component3D returnComponent = new Component3D();;
        Box returnBox = new Box(0.004f,0.004f,0.002f,
                new SimpleAppearance(1.0f,0.5f,1.0f,1.0f));
        returnComponent.addChild(returnBox);
        CylinderButtonComponent3D returnButton = new CylinderButtonComponent3D(returnComponent);
        returnButton.setTranslation(0.00f,-0.065f,0.0f);
        returnButton.setDepth(0.0f);
        returnButton.setPressedAction(new ActionBoolean(){
            public void performAction(LgEventSource source, boolean b){
                if(b)
                    manager.changeMode();
            }
        });
        addChild(returnButton);

    }

    public class EnteredAction implements ActionBoolean {
        protected Component3D component;
        //boolean pressed = false;
        protected float z = 0.0f;

        public Component3D getComponent3D(){
            return component;
        }
    
        public EnteredAction(Component3D c){
            component = c;
            Vector3f loc = new Vector3f();
            component.getFinalTranslation(loc);
            z = loc.z;
        }

        public void performAction(LgEventSource source, boolean b){
            Vector3f loc = new Vector3f();
            component.getFinalTranslation(loc);
            if(!b)
                component.changeTranslation(loc.x,loc.y,z, 200);
        }
    }

    public class PressedAction implements ActionBoolean {
        protected Component3D component;
        //boolean pressed = false;
        protected float z = 0.015f;
        protected int num = 0;

        public Component3D getComponent3D(){
            return component;
        }
    
        public PressedAction(Component3D c,int i){
            component = c;
            num = i;
        }
    
        public PressedAction(Component3D c,int i,float z){
            component = c;
            this.z = z;
            num = i;
        }
        
        public void performAction(LgEventSource source, boolean b){
            Vector3f loc = new Vector3f();
            component.getFinalTranslation(loc);
            if(b)
                component.changeTranslation(loc.x,loc.y,loc.z-z, 200);
            else
                component.changeTranslation(loc.x,loc.y,loc.z+z, 200);
        }
    }


    public void setThumbnailImage(Vector<BufferedImage> imageList){
        clearThumbnailImage();
        if(imageList != null && imageList.size() > 0){
            for(int i=0; i<imageList.size(); i++){
                addThumbnailImage(imageList.get(i));
            }
        }
    }

    public void clearThumbnailImage(){
        // removeAllChildren();
        thumbnailViewer.removeAllChildren();
        panelList.removeAllElements();
        boxList.removeAllElements();
    }
    
    public void addThumbnailImage(BufferedImage image){

        Component3D imageComponent = new Component3D();
        ImagePanel imagePanel = new ImagePanel(IMAGE_WIDTH, IMAGE_HEIGHT);
        SimpleAppearance appearance = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
        appearance.setCapability(SimpleAppearance.ALLOW_TEXTURE_WRITE);

        TextureLoader loader = new TextureLoader(image);
        Texture texture = loader.getTexture();
        appearance.setTexture(texture);

        imagePanel.setCapability(ImagePanel.ALLOW_APPEARANCE_OVERRIDE_WRITE);
        imagePanel.setAppearance(appearance);
        imageComponent.addChild(imagePanel);
        imageComponent.setAnimation(new NaturalMotionAnimation(1000));

        Component3D component = new Component3D();
        SimpleAppearance appearance2 = new SimpleAppearance(0.95f,0.95f,0.95f,0.5f);
        appearance2.setCapability(SimpleAppearance.ALLOW_MATERIAL_WRITE);
        // GlassyPanel glassyPanel = new GlassyPanel(GLASSY_WIDTH, GLASSY_HEIGHT,0.005f,appearance2);
        // component.addChild(glassyPanel);
        Box box = new Box(BOX_WIDTH, BOX_HEIGHT, 0.0025f,appearance2);
        component.addChild(box);
        component.setTranslation(0.0f,0.0f,-0.00255f);

        // glassyPanelList.add(glassyPanel);
        boxList.add(box);
        panelList.add(imageComponent);
        imageComponent.addChild(component);
                
        imageComponent.setRotationAxis(0.0f,1.0f,0.0f);
                
        setPanelEvent(imageComponent);
        thumbnailViewer.addChild(imageComponent);
        // addChild(thumbnailViewer);
        thumbnailAction.initLocation();

    }
    
    public void setPanelEvent(Component3D component){
        MouseEnteredEventAdapter enteredAdapter = new MouseEnteredEventAdapter(
                new PanelActionBoolean(component)
        );
        component.addListener(enteredAdapter);

        MouseClickedEventAdapter clickedAdapter = new MouseClickedEventAdapter(
                new ClickedAction(component)
        );
        component.addListener(clickedAdapter);

        component.setMouseEventPropagatable(true);
    }

    private class ClickedAction implements ActionNoArg {
        private Component3D component;
        public ClickedAction(Component3D component){
            this.component = component;
        }
        public void performAction(LgEventSource source){
            int number = panelList.indexOf(component) + 1;
            manager.changePage(number);
            manager.changeMode();
        }
    }
    
    private class PanelActionBoolean implements ActionBoolean {
        private Component3D component;
        public PanelActionBoolean(Component3D c){
            component = c;
        }
        public void performAction(LgEventSource source, boolean state){
            int number = panelList.indexOf(component);
            Box box = boxList.get(number);
            SimpleAppearance sa = (SimpleAppearance)box.getAppearance();
            if(state)
                sa.setColor(0.50f,0.95f,0.95f);
            else
                sa.setColor(0.95f,0.95f,0.95f);
        }
    }

    public void setLocation(int value){
        current = value;
        thumbnailAction.setLocation(value,0);
    }


    
}
