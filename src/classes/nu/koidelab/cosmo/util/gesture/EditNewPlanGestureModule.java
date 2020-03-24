package nu.koidelab.cosmo.util.gesture;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;

import nu.koidelab.cosmo.manager.OrbiterEditor;
import nu.koidelab.cosmo.wg.nodes.group.DayGroup;

import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;

import edu.berkeley.guir.lib.satin.recognizer.Recognizer;
import edu.berkeley.guir.lib.satin.recognizer.rubine.RubineRecognizer;

/**
 * @author fumi
 * EditNewPlanGestureModule
 * nu.koidelab.cosmo.util
 */
public class EditNewPlanGestureModule extends AppGestureModuleBase {   
	private Recognizer recognizer;
	private static final String FILE_NAME = "resources/cosmo/editor-gestures.gsa";

	public EditNewPlanGestureModule(Class c3dForAttachLine) {
		super(c3dForAttachLine);
        try {
            logger.fine("Loading Gesture data :" + FILE_NAME);            
			Reader reader = new InputStreamReader( getClass().getClassLoader().getResourceAsStream(FILE_NAME) );
            recognizer = new RubineRecognizer( reader );
            logger.fine("Gesture recognizer configured (OrbiterEditor)");
        } catch(Exception e) {
            logger.log( Level.SEVERE, "Failed to load gesture definitions", e);
        }
	}
    
	public void processEvent(LgEvent evt) {
		MouseButtonEvent3D mevt = (MouseButtonEvent3D) evt;
        if(mevt.isReleased())
            System.err.println(mevt.getSource());
        

		if (mevt.isPressed()
				&& mevt.getButton() == MouseButtonEvent3D.ButtonId.BUTTON3) {
			startGesture(mevt);
		} else if (mevt.isReleased()
				&& mevt.getButton() == MouseButtonEvent3D.ButtonId.BUTTON3) {
//			System.err.println("B3" + mevt + " : " + mevt.getSource());
			endGesture(mevt, false);
		} else if (mevt.isClicked()
				&& mevt.getButton() == MouseButtonEvent3D.ButtonId.BUTTON1) {
//			System.err.println("B1" + mevt + " : " + mevt.getSource());
			if(mevt.getSource() instanceof DayGroup.DateIDPanel)
				endGesture(mevt, true);
			else
				endGesture(mevt, false);
		}
	}

    protected final void startGesture(MouseButtonEvent3D evt) {
        startEvent = evt;
        synchronized( gestureEvents ) {
            if (gestureStarted) 
                return;
            LgEventConnector.getLgEventConnector().addListener(
                    startEvent.getSource(), draggingListener);
            LgEventConnector.getLgEventConnector().addListener(DayGroup.DateIDPanel.class, this);
            gestureStarted = true;
        }
        
//      make nodes for Extendable Line to attach to the event source.  
        adapter = new Component3D();
        Component3D src =  (Component3D)(evt.getIntersectedComponent3D(0, c3dForAttachLine));        
        line3D = new ExtendableLine();
        adapter.addChild(line3D);
        adapter.setPickable(false);
        src.addChild(adapter);
        
    }
    
	protected final void endGesture(MouseButtonEvent3D evt, boolean clicked) {
		synchronized (gestureEvents) {
			if (!gestureStarted)
				return;

            //hide the Expand Line befor processing gesture event
            adapter.detach();
			LgEventConnector.getLgEventConnector().removeListener(
                    startEvent.getSource(), draggingListener);
            LgEventConnector.getLgEventConnector().removeListener(DayGroup.DateIDPanel.class, this);
			if (clicked){
				processGesture(evt);
			} else {
				processGesture();
			}
			gestureEvents.clear();
			gestureStarted = false;
		}

		logger.fine("endGesture");
		

        adapter = null;
        line3D = null;
	}

	void processGesture(){
        return;
        /*
        logger.finer("Starting processGesture, eventQueue size "+gestureEvents.size());
        TimedStroke ts = new TimedStroke();
        
        float scale = 4000f;
        
        for( MouseMotionEvent3D evt : gestureEvents ) {
            ts.addPoint( evt.getImagePlateX()*scale, -evt.getImagePlateY()*scale, evt.getWhen() );
        }
        
        Classification classification = recognizer.classify( ts );
        logger.info("Gesture "+classification.getFirstKey());
        

		if((startEvent.getSource() instanceof OrbiterEditor.BabyPlanet)){


		    OrbiterEditor.BabyPlanet babyPlanet = (OrbiterEditor.BabyPlanet)startEvent.getIntersectedComponent3D(0, OrbiterEditor.BabyPlanet.class);        
		    if (babyPlanet==null) return;
		    String gestureName = (String)classification.getFirstKey();
            
		    if (gestureName.equals("anticlockwise")) {
                babyPlanet.addPriority(-1);
            } else if (gestureName.equals("clockwise")) {
                babyPlanet.addPriority(1);
            } else if (gestureName.equals("check")) {
                babyPlanet.attachTo();
//            } else if (gestureName.equals("cross") || gestureName.equals("cross-R") || gestureName.equals("cross-rotate") ) {
            } else if (gestureName.equals("cross")) {
                CSDGeneralManager.getInstance().getEditor().removePlanetChild(babyPlanet);
            }
		    return;
            
            
        } else if((startEvent.getSource() instanceof AddPlanPanel)){
            AddPlanPanel startComp = (AddPlanPanel) startEvent
                    .getIntersectedComponent3D(0, AddPlanPanel.class);        
            if (startComp==null) return;

            String gestureName = (String)classification.getFirstKey();
            if (gestureName.equals("whorl-clock") || gestureName.equals("whorl-anticlock") || gestureName.equals("clumpling")) {
                startComp.createPlanetChild();
            } else if (gestureName.equals("cross") || gestureName.equals("cross-R") || gestureName.equals("cross-rotate") ) {
            	startComp.changeVisible(false);
            	startComp.changeEnabled(false);            	
            }            
        }*/
	}
	
    /* Add new planet on the DateIDPanel. */
	void processGesture(MouseButtonEvent3D evt) {
        if(!(startEvent.getSource() instanceof OrbiterEditor.BabyPlanet)) return;

		OrbiterEditor.BabyPlanet target = ((OrbiterEditor.BabyPlanet) startEvent.getSource());
		if (gestureEvents.size() > 0) {
//            CSDGeneralManager.getInstance().getEditor().addPlanetChild(target, ((DayGroup.DateIDPanel)evt.getSource()));
			target.attachTo(((DayGroup.DateIDPanel)evt.getSource()));
            gestureEvents.clear();            
		}
	}    
}
