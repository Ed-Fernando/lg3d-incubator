package org.jdesktop.lg3d.apps.presentoire;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;

import org.jdesktop.lg3d.apps.presentoire.transition.Transition;
import org.jdesktop.lg3d.apps.presentoire.transition.TransitionFactory;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 * This is the presentoire main class.
 * It initializes the application, opens the document...
 * @author Pierre Ducroquet
 */
public class Presentoire {
    
	private GlassyPanel panel;
	private Component3D slide, nextBtn, prevBtn, mainComponent;
	private Frame3D frame3d;
	private int currentPageNumber;
	private PresentoireDocument doc;
	
	// Prerender next page...
	private Component3D nextPageRendered;
	private Transition nextTransition;
	
    /**
     * Creates a new instance of Presentoire 
     */
    public Presentoire() {
        System.out.println("Launching Presentoire...");
        currentPageNumber = 0;
        frame3d = new Frame3D();
        mainComponent = new Component3D();
        SimpleAppearance app = new SimpleAppearance(0.6f, 0.8f, 0.6f, 0.7f);
        panel = new GlassyPanel(0.2f, 0.2f, 0.01f, app);
        mainComponent.addChild(panel);
        slide = new Component3D();
        mainComponent.addChild(slide);
        
        // BUTTONS...
       
        nextBtn = new Component3D();
        nextBtn.addChild(new Sphere(0.005f,Sphere.GENERATE_NORMALS,10,
                new SimpleAppearance(0.1f,0.1f,1.0f,0.5f)));
        nextBtn.setVisible(false); // The button will be shown later, by the preRenderNextPage
        nextBtn.setCursor(Cursor3D.DEFAULT_CURSOR);
        nextBtn.setTranslation(0.1f, -0.1f, 0.0f);
        nextBtn.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
            	nextPage();
            }
        }));
        mainComponent.addChild(nextBtn);
        
        prevBtn = new Component3D();
        prevBtn.addChild(new Sphere(0.005f,Sphere.GENERATE_NORMALS,10,
                new SimpleAppearance(0.1f,0.1f,1.0f,0.5f)));
        prevBtn.setVisible(false); // The button will be shown later, by nextPage
        prevBtn.setCursor(Cursor3D.DEFAULT_CURSOR);
        prevBtn.setTranslation(-0.1f, -0.1f, 0.0f);
        prevBtn.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
            	currentPageNumber = currentPageNumber - 2;
            	preRenderNextPage();
            	nextPage();
            }
        }));
        mainComponent.addChild(prevBtn);
       
        Component3D lowerBtn = new Component3D();
        lowerBtn.addChild(new Sphere(0.005f,Sphere.GENERATE_NORMALS,10,
                new SimpleAppearance(0.1f,0.1f,1.0f,0.5f)));
        lowerBtn.setCursor(Cursor3D.DEFAULT_CURSOR);
        lowerBtn.setTranslation(0.1f, 0.1f, 0.0f);
        lowerBtn.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
            	frame3d.changeVisible(false);
            }
        }));
        mainComponent.addChild(lowerBtn);
        
        frame3d.addChild(mainComponent);
        frame3d.setVisible(true);
        frame3d.setEnabled(true);

        


        /*		Small test code, to know the coordonates of a point on the slide...
         slide.addListener(new MouseClickedEventAdapter(new ActionFloat2() {
        			public void performAction(LgEventSource source, float x, float y) {
        				System.out.println("X : " + x);
        				System.out.println("Y : " + y);
        			}
                }));*/
        
        /*
         * The right fileChooser code.
        JFileChooser chooser = new JFileChooser();
        int retVal = chooser.showOpenDialog(null);
        if (retVal == JFileChooser.APPROVE_OPTION) {
        	openDocument(chooser.getSelectedFile());
        }*/
        new FileChooser(this).setVisible(true);

    }
    
    /**
     * Open the document specified in the argument.
     * @param document
     */
    public void openDocument (File document) {
        try {
			doc = new PresentoireDocument(document);
	        slide.addChild(doc.getSlide(currentPageNumber).drawPage());
	        preRenderNextPage();
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Ask for the next Slide to be shown.
     * Execute the transition if available.
     * Launch the preRenderer for the next slide.
     */
    public void nextPage () {
    	nextBtn.setVisible(false);
    	currentPageNumber++;

    	if (nextTransition == null) {
    		if (doc.getSlide(currentPageNumber).getTransition() != null)
    			System.err.println("WARNING : TRANSITION NOT FOUND !");
    		slide.removeAllChildren(); 
    		slide.addChild(nextPageRendered);
    	} else {
    		System.out.println("Ok, launching the transition...");
    		nextTransition.executeTransition(mainComponent, slide, nextPageRendered);
    	}
    	
    	prevBtn.setVisible(true);
    	preRenderNextPage();
    }
    
    /**
     * The prerenderer. In fact, everything is prerendered...
     * The only drawing done is removing the current slide and adding the next slide rendered.
     * So here we only store the next Slide in a Component3D. BTW, we show the nextBtn.. 
     * That way, the user can ask a new Slide while the prerendering is occuring. 
     */
    private void preRenderNextPage () {
    	if (currentPageNumber < doc.getSlideCount() - 1) {
    		Slide nextSlidePrerender = doc.getSlide(currentPageNumber+1);
    		nextPageRendered = nextSlidePrerender.drawPage();
    		nextTransition = TransitionFactory.getTransition(nextSlidePrerender.getTransition(), doc);
    		nextBtn.setVisible(true);
    	} else {
    		nextBtn.setVisible(false);
    	}
    }
    
    /**
     * This is the main call. Nothing interesting here.
     * @param args
     */
    public static void main(String[] args) {
        new Presentoire();
    }
}
