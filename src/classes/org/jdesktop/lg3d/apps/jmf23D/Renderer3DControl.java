package org.jdesktop.lg3d.apps.jmf23D;

import com.sun.j3d.loaders.objectfile.ObjectFile;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;

import java.io.File;
import java.util.*;
import java.net.URL;

import javax.media.*;
import javax.media.control.*;
import javax.media.format.*;
import javax.media.protocol.*;
import javax.media.util.*;

import javax.swing.JFileChooser;
import javax.swing.JPanel;


// java3d
import javax.vecmath.*;

import org.jdesktop.lg3d.scenemanager.utils.background.Background;
import org.jdesktop.lg3d.scenemanager.utils.event.BackgroundChangeRequestEvent;
import org.jdesktop.lg3d.sg.*;
import org.jdesktop.lg3d.utils.action.*;
import org.jdesktop.lg3d.utils.c3danimation.*;
import org.jdesktop.lg3d.utils.eventadapter.*;
import org.jdesktop.lg3d.utils.layoutmanager.*;
import org.jdesktop.lg3d.utils.shape.*;
import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.wg.event.*;


class Renderer3DControl implements Control, ControllerListener {

    protected static Frame3D frame;
    protected static FaceList faceList;
    protected Foreground foreground;
    protected VideoBackground background;
    protected Component3D visualComponent;
    
    Player player;
    Renderer3D renderer3D;
    static LinkedList<Renderer3DControl> controls;
    static Player currentPlayer;
    
    private static void init() {
	/*
        Frame3D frame = new Frame3D();
        Thumbnail thumb = new Thumbnail();
	Component3D thumbComp = getVisualComponent();
	thumbComp.changeTranslation(0.0f, 0.04f, 0.0f);
        thumb.addChild(thumbComp);
        //thumb.setPreferredSize(new Vector3f(0.01f, 0.01f, 0.01f));
        frame.setThumbnail(thumb);
        frame.addChild(getVisualComponent());
        frame.addChild(Renderer3DControl.getControlComponent3D(null));
        frame.changeEnabled(true);
        frame.changeVisible(true);
	*/
	controls = new LinkedList<Renderer3DControl>();
	faceList = new FaceList();
	frame = new Frame3D();
	frame.addChild(faceList);
	frame.addChild(getControlComponent3D());
	frame.changeEnabled(true);
	frame.changeVisible(true);
    }

    public Renderer3DControl(Renderer3D renderer3D) {
	if(controls==null)
		init();
        this.renderer3D = renderer3D;
	controls.add(this);
	if(renderer3D!=null) {
		faceList.addAppearance(renderer3D.getAppearance(), renderer3D.getAspectRatio());
		foreground = new Foreground(renderer3D.getAspectRatio());
	} else 
		faceList.addChild(new Component3D());
    }

    public void setPlayer(Player p) {
	    if(currentPlayer==null)
		    currentPlayer=p;
	    this.player = p;
	    p.addControllerListener(this);
    }
    
    public static FaceList getFaceList() {
        return faceList;
    }
    
    public Component3D getVisualComponent() {

        if (visualComponent == null)
            visualComponent = newVisualComponent();

        return visualComponent;
    }

    public void setVisualComponent(Component3D c3d) {
        AlgeaUtilities.setCapabilitiesRecursive(c3d);
        AlgeaUtilities.setAppearanceRecursive(c3d, renderer3D.getAppearance());
	int myIndex = controls.indexOf(this);
	faceList.removeChild(myIndex);
	controls.remove(myIndex);
    }

    public Component3D newVisualComponent() {
        Component3D visualComponent = new Component3D();
	visualComponent.setAnimation(new NaturalMotionAnimation(500));
        TransformGroup objScale = new TransformGroup();
        Transform3D t3d = new Transform3D();
        t3d.setScale(0.5f);
        objScale.setTransform(t3d);
        visualComponent.addChild(objScale);
        TransformGroup objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        objTrans.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        objTrans.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        objTrans.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        objScale.addChild(objTrans);
	Appearance app = renderer3D.getAppearance();
        Box textureCube = new Box(0.16f, 0.09f, 0.0f, 
                                  Box.GENERATE_TEXTURE_COORDS, app);
        objTrans.addChild(textureCube);
        return visualComponent;
    }

    public static void setFullscreen(boolean fullscreen) {
	    if(fullscreen) {
		    if(controls.getFirst().renderer3D == null)
		        throw new UnsupportedOperationException("audio streams cannot be set to fullscreen");
		   	controls.getFirst().foreground.setAppearance(controls.getFirst().renderer3D.getAppearance());
	    }
	   controls.getFirst().foreground.changeVisible(fullscreen);
    }

    public Background getBackground() {
	if(renderer3D == null)
		throw new UnsupportedOperationException("audio streams cannot be set as background");
        if (background == null) {

            Group rendNode = AlgeaUtilities.createRenderingNode();
            ((Box)rendNode.getChild(1)).setAppearance(renderer3D.getAppearance());
            AlgeaUtilities.setCapabilitiesRecursive(rendNode);
            background = new VideoBackground(rendNode);
        }

        return background;
    }

    public Component getControlComponent() {

        return null;
    }

    public static Component3D getControlComponent3D() {

        return new ControlComponent();
    }
    
    
    public void controllerUpdate(ControllerEvent evt) {

	if(evt.getSourceController()!=player)
		return;
        if (evt instanceof ConfigureCompleteEvent || 
            evt instanceof RealizeCompleteEvent || 
            evt instanceof PrefetchCompleteEvent) {
		    
        } else if (evt instanceof ResourceUnavailableEvent) {
		close();
        } else if(evt instanceof EndOfMediaEvent) {
		
	}
	
	}
    public void close() {
	    player.stop();
	    player.close();
	    faceList.removeChild(controls.indexOf(this));
	    controls.remove(this);
	    if(controls.size()==0){
		    frame.changeEnabled(false);
		    controls = null;
	    }
    }
    
    public static void performAction(ControlComponent.ButtonType btype) {
	    switch(btype) {
		    case REWIND: skip(-1);break;
		    case PLAY:	 currentPlayer.start();break;
		    case STOP:	 if(currentPlayer.getState()==Player.Realized | currentPlayer.getState()==Player.Prefetched)
		    			 controls.getFirst().close();
				 else
					 currentPlayer.stop();
				 break;
		    case RECORD: faceList.setRolled(!faceList.getRolled());break;
		    case EJECT:	 try {
			    Algea3D.main(new String[]{""});
		    } catch(Exception e){};break;
		    case FORWARD:skip(1);break;
	    }
    }
    
    public static void skip(int i) {
	    
	    if(i>0) {
		    faceList.next(i);
	    } else {
		    faceList.previous(-i);
	    }
	    
	    int num = controls.size();
	    
	    while(i<0){
	    i++;
	    Renderer3DControl nextControl = controls.get(0);
            for (int j = 0; j < num; j++) {

                int nextIndex = (j + 1) % num;
                Renderer3DControl temp = controls.get(nextIndex);
                controls.remove(nextControl);
                controls.add(nextIndex, nextControl);
                nextControl = temp;
            }
	    }

	    while (i>0) {
		i--;
		Renderer3DControl child0 = controls.get(0);
	    	controls.remove(0);
	    	controls.add(child0);
            }
	    currentPlayer = controls.getFirst().player;
	}
}

class ControlComponent extends Container3D {
    public ControlComponent() {
        this.setTranslation(0.07f, -0.055f, 0.0f);
        this.setLayout(new HorizontalLayout(
                               HorizontalLayout.AlignmentType.RIGHT, 0.02f));

        for (ButtonType bType: ButtonType.values()) {
            this.addChild(bType.createComponent());
        }

        this.setScale(1.0f);
    }

public enum ButtonType {
    REWIND("ff.obj") ,
    PLAY ("play.obj"),
    STOP ("stop.obj"),
    RECORD("record.obj"),
    EJECT ("eject.obj"),
    FORWARD("ff.obj");

    private URL url = null;
    
    ButtonType(String filename) {
	try {
	    url = getClass().getClassLoader().getResource(
				"resources/models/" + filename);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
     
     public Component3D createComponent(){
	     ModelLoader component = new ModelLoader(null, url, ObjectFile.class);
	     component.setAnimation(new NaturalMotionAnimation(200));
             component.resize(new Vector3f(0.0f, 0.0f, 0.0f), (float)Math.sqrt(2) * 0.01f);
	     setRotation(component);
	     component.addListener(new MouseClickedEventAdapter(Boolean.FALSE, 
                                                               new org.jdesktop.lg3d.utils.action.ActionFloat2() {
                           public void performAction(LgEventSource source, float x, float y) {
				   try{
					   Renderer3DControl.performAction(ButtonType.this);
				   } catch(Exception e){
					   e.printStackTrace();
				   }
                           }
                       }));
      addScaleAction(component);
      if(this==RECORD) {
	      try{
		      SimpleAppearance sa = new SimpleAppearance(
			getClass().getClassLoader().getResource("resources/images/icon/alg3d.png"));
		      AlgeaUtilities.setAppearanceRecursive(component, sa);
	      } catch(Exception e){
	      e.printStackTrace();}
      }
	      
      return component;
     }
            
     public void setRotation(Component3D c3d) {
      Vector3f rotationAxis;
      float angle = (float) Math.PI / 2;
      switch(this){
       case PLAY: c3d.setRotationAxis(0.0f, 0.0f, 1.0f);break;
       case FORWARD: c3d.setRotationAxis(0.0f, 1.0f, 0.0f);break;
       case REWIND: c3d.setRotationAxis(0.0f, -1.0f, 0.0f);break;
       default: return;
      }
      c3d.setRotationAngle(angle);
    }
    
        public void addScaleAction(final Component3D component) {

        ActionBoolean act = new org.jdesktop.lg3d.utils.action.ActionBoolean() {
            public void performAction(LgEventSource source, boolean state) {

                if (state == true) {
                    component.changeScale(1.5f);
                }

                if (state == false) {
                    component.changeScale( 1.0f);
                }
            }
        };

        component.addListener(new MouseEnteredEventAdapter(act));
    }
}
}
