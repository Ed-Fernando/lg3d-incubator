package org.jdesktop.lg3d.apps.jmf23D;

import java.awt.Color;

import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import javax.media.*;
import javax.media.protocol.*;
import javax.media.format.*;
import javax.media.control.*;

import javax.vecmath.*;

import org.jdesktop.lg3d.sg.*;
import org.jdesktop.lg3d.utils.action.*;
import org.jdesktop.lg3d.utils.c3danimation.*;
import org.jdesktop.lg3d.utils.eventadapter.*;
import org.jdesktop.lg3d.utils.shape.*;
import org.jdesktop.lg3d.scenemanager.utils.event.*;
import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.wg.event.*;

/**
* 
**/
public class AlgeaUtilities {
    static Frame3D frame;
    static InputPane input;
    private AlgeaUtilities() {
    }

    public static void setAppearanceRecursive(Node node, Appearance app) {

        if (node instanceof Group)

            for (Enumeration e = ((Group)node).getAllChildren();
                 e.hasMoreElements();) {
                setAppearanceRecursive((Node)e.nextElement(), app);
            }

        if (node instanceof Shape3D) {

            Shape3D s3 = (Shape3D)node;
            s3.setAppearance(app);
        }
    }

    
    public static void setCapabilitiesRecursive(Node node) {
        node.setCapability(Node.ALLOW_PARENT_READ);
        node.setCapability(Node.ENABLE_PICK_REPORTING);
        org.jdesktop.lg3d.sg.utils.traverser.ChangeTextureAttributes.setTextureEnable(
                node, true);

        if (node instanceof Group)

            for (Enumeration e = ((Group)node).getAllChildren();
                 e.hasMoreElements();) {
                setCapabilitiesRecursive((Node)e.nextElement());
            }

        if (node instanceof Shape3D) {

            Shape3D s3 = (Shape3D)node;
            s3.setCapability(GeometryArray.ALLOW_COUNT_READ);
            s3.setCapability(Shape3D.ALLOW_GEOMETRY_READ);
            s3.setCapability(Geometry.ALLOW_INTERSECT);
            s3.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        }
    }

    public static Component3D createRenderingNode() {
	    return createRenderingNode(16f/9f);
    }
    
    public static Component3D createRenderingNode(float aspectRatio) {
	    
        Appearance blackApp = new Appearance();
        Color3f black = new Color3f(Color.black);
        Material m = new Material(black, black, black, black, 1.0f);
        blackApp.setMaterial(m);

        Frame3D frame = new Frame3D();
	final float initWidth = Toolkit3D.getToolkit3D().getScreenWidth();
	final float initHeight = Toolkit3D.getToolkit3D().getScreenHeight();
        Box blackBackground = new Box(initWidth, 
                                      initHeight, 0.0f, blackApp);
        TransformGroup backgroundGroup = new TransformGroup();
        Transform3D t3d = new Transform3D();
        t3d.setTranslation(new Vector3f(0.0f, 0.0f, -1e-5f));
        backgroundGroup.setTransform(t3d);
        backgroundGroup.addChild(blackBackground);
	
	float width;
	float height;
	if(aspectRatio < Toolkit3D.getToolkit3D().getScreenHeight()/Toolkit3D.getToolkit3D().getScreenWidth()){
		width = Toolkit3D.getToolkit3D().getScreenWidth()/3.52f;
		height = Toolkit3D.getToolkit3D().getScreenWidth()*aspectRatio/(3.53f);
	} else {
		width = Toolkit3D.getToolkit3D().getScreenHeight()/(aspectRatio*3.52f);
		height = Toolkit3D.getToolkit3D().getScreenHeight()/3.52f;
	}
        Box imagePlane = new Box(width, 	//stupid hack
                                 height, 0.0f, 		//hack
                                 Box.GENERATE_TEXTURE_COORDS, null);
        final Component3D tg = new Component3D();
	tg.setTranslation(0.0f,0.0f,0.2f);
        tg.addChild(backgroundGroup);
        tg.addChild(imagePlane);

	LgEventConnector.getLgEventConnector().addListener(
                LgEventSource.ALL_SOURCES, new LgEventListener() {
			public void processEvent(final LgEvent event) {
				ScreenResolutionChangedEvent csce = (ScreenResolutionChangedEvent)event;
				float scaleW = csce.getWidth()/initWidth;
				float scaleH = csce.getHeight()/initHeight;
				float scale = (scaleW > scaleH) ? (scaleW) : (scaleH);
				tg.setScale(scale);
			}

			public Class<LgEvent>[] getTargetEventClasses() {
				return new Class[] { ScreenResolutionChangedEvent.class };
			}
		});
    	
        return tg;
    }

    public static String getMovieURI() {
	    if(frame==null){
		frame = new Frame3D();
	        input = new InputPane();
	        frame.addChild(input);
	    }
	frame.changeEnabled(true);
	frame.changeVisible(true);
	
        String uri = input.getURI();
	frame.changeVisible(false);

        return uri;
    }
    
    public static Player createRealizedPlayer(DataSource ds) throws java.io.IOException, UnsupportedPlugInException, NoPlayerException {
	    
                Processor p = Manager.createProcessor(ds);
		AlgeaUtilities.WaitListener wl = new AlgeaUtilities.WaitListener(p);
		p.configure();
		System.out.println(wl.waitForState(p.Configured));
		p.setContentDescriptor(null);
		TrackControl[] tracks = p.getTrackControls();
		for(TrackControl tc: tracks){
			if(tc.getFormat() instanceof VideoFormat)
				tc.setRenderer(new Renderer3D());
			else if(tc.getFormat() instanceof AudioFormat) {
				try{
				String rendName = (String)PlugInManager.getPlugInList(new AudioFormat(AudioFormat.LINEAR), null, PlugInManager.RENDERER).firstElement();
				Renderer audioR = (Renderer)Class.forName(rendName).newInstance();
				tc.setRenderer(audioR);
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		p.realize();
		wl.waitForState(p.Realized);
		Renderer3DControl r3Cont = null;
                for (Control cont: p.getControls()) {
                    if (cont instanceof Renderer3DControl) {
			r3Cont = (Renderer3DControl)cont;
                        r3Cont.setPlayer(p);
                    }
                }
		if(r3Cont == null){
			r3Cont = new Renderer3DControl(null);
			r3Cont.setPlayer(p);
		}
		return p;
    }
    /**
    * Utility class that blocks until a Player reaches a given state.
    *
    **/
    static class WaitListener implements ControllerListener {
	    
    Player p;
    Object waitSync;
    boolean stateTransOK = true;
    public WaitListener(Player p){
	    waitSync = new Object();
	    this.p=p;
	    p.addControllerListener(this);
    }
    
    /**
    * Blocks until the player enters the given state.
    * @param	state	an int that represents the state the player will be in when the function returns
    * @return	true if the player reached the given state, false if an error occurred
    * @see	javax.media.Player
    **/
    public boolean waitForState(int state) {

        synchronized (waitSync) {

            try {

                while (p.getState() != state && stateTransOK) {
                    waitSync.wait();
                }
            } catch (Exception ex) {
            }

            return stateTransOK;
        }
    }
    
    public void controllerUpdate(ControllerEvent evt) {

	Player p = (Player)evt.getSourceController();
        if (evt instanceof ConfigureCompleteEvent || 
            evt instanceof RealizeCompleteEvent || 
            evt instanceof PrefetchCompleteEvent) {

            synchronized (waitSync) {
                stateTransOK = true;
                waitSync.notifyAll();
            }
        } else if (evt instanceof ResourceUnavailableEvent) {

            synchronized (waitSync) {
                stateTransOK = false;
                waitSync.notifyAll();
	    }
        } else if(evt instanceof EndOfMediaEvent) {
		    
            synchronized (waitSync) {
                stateTransOK = false;
                waitSync.notifyAll();
	    }
	}
	
	}
    }
}
