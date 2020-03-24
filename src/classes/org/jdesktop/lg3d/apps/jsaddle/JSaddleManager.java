/*
 * JSaddleManager.java
 *
 * Created on 2007/01/02, 23:30
 *
 */

package org.jdesktop.lg3d.apps.jsaddle;

import java.io.File;
import java.awt.image.BufferedImage;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Thumbnail;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;

//import org.jdesktop.lg3d.apps.jsaddle.event.JSaddleEvent;
//import org.jdesktop.lg3d.apps.jsaddle.event.JSaddleEventAdapter;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.wg.event.LgEventSource;

import org.jdesktop.lg3d.apps.jsaddle.utils.ExitButtonComponent3D;
import org.jdesktop.lg3d.apps.jsaddle.utils.MinimizeButtonComponent3D;

import java.util.Vector;

import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;

import org.jdesktop.lg3d.utils.shape.ImagePanel;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class JSaddleManager {
    
    private final static float IMAGE_WIDTH = 0.24f;
    private final static float IMAGE_HEIGHT = 0.18f;
   
    private PdfManager pdfManager = null;
    
    private Frame3D frame = new Frame3D();
    private boolean visibleFrame = true;
    private ViewerContainer viewerContainer = null;
    private float viewerContainerScale = 1.0f;
    private ThumbnailViewerContainer thumbnailViewerContainer = null;
    private Vector<BufferedImage> thumblist = new Vector<BufferedImage>();
    private ImagePanel thumbnailPanel = null;

    /** Creates a new instance of TestViewer */
    public JSaddleManager() {

        // PDFの読み込み
        initializePdfManager();

        // Create a Component
        Component3D component3d = new Component3D();

        // Add a shape to Component
        // component.addChild(imagePanel);
        viewerContainer = new ViewerContainer(this);
        // minimize button, exit button
        viewerContainer.setMinimizeButton(new MinimizeButtonComponent3D(frame));
        viewerContainer.setExitButton(new ExitButtonComponent3D(frame));
        component3d.addChild(viewerContainer);
        
        thumbnailViewerContainer = new ThumbnailViewerContainer(this);
        thumbnailViewerContainer.setScale(0.0f);
        component3d.addChild(thumbnailViewerContainer);
        component3d.setAnimation(new NaturalMotionAnimation(1000));
        
        // add a component to frame
        frame.addChild(component3d);
        
        // フレームの表示
        // フレームの大きさを設定
        // 引数は、Java3Dのクラス
        // javax.vecmath.Vector3f(float x,float y,float z)
        // x = 幅、y = 高さ, z = 奥行き (単位はメートル)
        frame.setPreferredSize(new Vector3f(IMAGE_WIDTH, IMAGE_WIDTH, 0.01f));

        frame.setThumbnail( createThumbnail() );

        // フレームの表示
        frame.changeEnabled(true);
        frame.changeVisible(true);
        
        // change to JFileChooser side
        viewerContainer.changeViewerMode();
        
    }

    public JSaddleManager(String name) {
        this();
        decodePdf(name);
    }

    public void decodePdf(String name){
        // System.out.println(name);
        pdfManager.decodeFile(name);
        pdfManager.setPage(1);
        if(pdfManager.getLastPageNumber() != 0){
            // display front
            viewerContainer.changeViewerMode();
            createPdfImages();
        }
    }
    
    public void deocdeResourceURL(String name){
        pdfManager.decodeResourceURL(name);
        pdfManager.setPage(1);
        if(pdfManager.getLastPageNumber() != 0){
            // display front
            viewerContainer.changeViewerMode();
            createPdfImages();
        }        
    }
    
    public void createPdfImages() {
        // main image
        BufferedImage image = pdfManager.getImage();
        viewerContainer.setImage(image);

        thumblist.clear();
        thumbnailViewerContainer.clearThumbnailImage();

        BufferedImage bufferedImage = pdfManager.getThumbnailImage(1);
        thumblist.add(bufferedImage);
        thumbnailViewerContainer.addThumbnailImage(bufferedImage);

        if(bufferedImage != null){
            TextureLoader loader = new TextureLoader(bufferedImage);
            thumbnailPanel.getAppearance().setTexture(loader.getTexture());
        }
        
        // read and make thumbnail cache
        Thread thumbnailThread = new Thread(){
            public void run(){
                for(int i=2; i <= pdfManager.getLastPageNumber();i++){
                    BufferedImage bufferedImage = pdfManager.getThumbnailImage(i);
                    thumblist.add(bufferedImage);
                    thumbnailViewerContainer.addThumbnailImage(bufferedImage);
                    thumbnailViewerContainer.setLocation(pdfManager.getCurrentPageNumber());
                }
            }
        };
        thumbnailThread.start();
    }
    
    private Thumbnail createThumbnail(){
        Thumbnail thumbnail = new Thumbnail();

        Component3D imageComponent = new Component3D();
        thumbnailPanel = new ImagePanel(IMAGE_WIDTH/6, IMAGE_HEIGHT/6);
        SimpleAppearance appearance = new SimpleAppearance(1.0f,1.0f,1.0f,1.0f);
        appearance.setCapability(SimpleAppearance.ALLOW_TEXTURE_WRITE);

        thumbnailPanel.setCapability(ImagePanel.ALLOW_APPEARANCE_OVERRIDE_WRITE);
        thumbnailPanel.setAppearance(appearance);
        imageComponent.addChild(thumbnailPanel);
        imageComponent.setAnimation(new NaturalMotionAnimation(1000));

        Component3D component = new Component3D();
        SimpleAppearance appearance2 = new SimpleAppearance(0.95f,0.95f,0.95f,1.0f);
        appearance2.setCapability(SimpleAppearance.ALLOW_MATERIAL_WRITE);
        GlassyPanel glassyPanel = new GlassyPanel(IMAGE_WIDTH/6+0.003f, IMAGE_HEIGHT/6+0.003f,0.004f,appearance2);
        component.addChild(glassyPanel);
        component.setTranslation(0.0f,0.0f,-0.00005f);

        imageComponent.addChild(component);
        imageComponent.setTranslation(0.0f,0.01f,0.0f);
        thumbnail.addChild(imageComponent);

        thumbnail.setAnimation(new NaturalMotionAnimation(1000));
        thumbnail.setMouseEventPropagatable(false);
        
        MouseClickedEventAdapter clickedAdapter = new MouseClickedEventAdapter(
            MouseEvent3D.ButtonId.BUTTON1, 
            new ActionNoArg(){
                public void performAction(LgEventSource source){
                    if(visibleFrame){
                        frame.changeTranslation(0.0f,-1.0f,0.0f);
                        frame.changeScale(0.0f);
                        visibleFrame = false;
                    }
                    else{
                        frame.changeTranslation(0.0f,0.0f,0.0f);
                        frame.changeScale(1.0f);
                        visibleFrame = true;
                    }
                }
            }
        );

        thumbnail.addListener(clickedAdapter);

        MouseClickedEventAdapter middleClickedAdapter = new MouseClickedEventAdapter(
            MouseEvent3D.ButtonId.BUTTON2, 
            new ActionNoArg(){
                public void performAction(LgEventSource source){
                    changeMode();
                }
            }
        );

        thumbnail.addListener(middleClickedAdapter);
        
        
        return thumbnail;
    }

    public void changeMode(){
        if(thumbnailViewerContainer.getFinalScale() == 0.0f && pdfManager.getLastPageNumber() != 0){
            viewerContainerScale = viewerContainer.getFinalScale();
            viewerContainer.changeScale(0.0f);
            thumbnailViewerContainer.changeScale(1.0f);
            thumbnailViewerContainer.setLocation( pdfManager.getCurrentPageNumber() );
        } else {
            viewerContainer.changeScale(viewerContainerScale);
            thumbnailViewerContainer.changeScale(0.0f);
        }
    }
    
    public void changePage(int value){
        int last = pdfManager.getLastPageNumber();
        //System.out.println(last);
        if( value > 0 && value <= last){
            pdfManager.setPage(value);
            viewerContainer.setImage(pdfManager.getImage());

            BufferedImage thumbImage = thumblist.get(value-1);
            if(thumbImage != null){
                TextureLoader loader = new TextureLoader(thumbImage);
                thumbnailPanel.getAppearance().setTexture(loader.getTexture());
            }
            if(viewerContainer.getFinalRotationAngle() != 0.0f)
                viewerContainer.changeViewerMode();
        }
    }
    
    public void movePageFromCurrent(int value){
        int current = pdfManager.getCurrentPageNumber() + value;
        int last = pdfManager.getLastPageNumber();
        if( current > 0 && current <= last){
            pdfManager.setPage(current);

            TextureLoader loader = new TextureLoader(thumblist.get(current-1));
            thumbnailPanel.getAppearance().setTexture(loader.getTexture());
        }
    }

    public BufferedImage getPageFromCurrent(int value){
        BufferedImage result = null;
        int current = pdfManager.getCurrentPageNumber() + value;
        int last = pdfManager.getLastPageNumber();
        if( current > 0 && current <= last){
            result = pdfManager.getImage(current);
        }
        return result;
    }

    public void initializePdfManager(){
        //pdfManager = new PdfManager(800,600);
        //pdfManager = new PdfManager(1024,768);
        //pdfManager = new PdfManager(1024,1024);
        pdfManager = new PdfManager(2048,1024);
        //pdfManager = new PdfManager(1280,1024);
    }

}
