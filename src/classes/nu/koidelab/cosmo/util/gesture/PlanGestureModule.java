package nu.koidelab.cosmo.util.gesture;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;

import nu.koidelab.cosmo.wg.nodes.EditableOrbiter;

import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseMotionEvent3D;

import edu.berkeley.guir.lib.satin.recognizer.Classification;
import edu.berkeley.guir.lib.satin.recognizer.Recognizer;
import edu.berkeley.guir.lib.satin.recognizer.rubine.RubineRecognizer;
import edu.berkeley.guir.lib.satin.stroke.TimedStroke;

/**
 * @author fumi
 * PlanGestureModule
 * nu.koidelab.cosmo.util
 */
public class PlanGestureModule extends AppGestureModuleBase{
    private Recognizer recognizer;
	private static final String FILE_NAME = "resources/cosmo/editor-gestures.gsa";
    
    public PlanGestureModule(){
        super(EditableOrbiter.class);
        try {
            logger.fine("Loading Gesture data :" + FILE_NAME);            
			Reader reader = new InputStreamReader( getClass().getClassLoader().getResourceAsStream(FILE_NAME) );
            recognizer = new RubineRecognizer( reader );
            logger.fine("Gesture recognizer configured (PlanGestureModule)");
        } catch(Exception e) {
            logger.log( Level.SEVERE, "Failed to load gesture definitions", e);
        }
    }
    
    @Override
    protected void startGesture(MouseButtonEvent3D evt) {
        if( !( (EditableOrbiter)(evt.getIntersectedComponent3D(0, EditableOrbiter.class)) ).isGhostMode()){
            return;
        }
        super.startGesture(evt);
    }
        
    void processGesture() {
        logger.finer("Starting processGesture, eventQueue size "+gestureEvents.size());
        TimedStroke ts = new TimedStroke();
        
        float scale = 4000f;
        
        for( MouseMotionEvent3D evt : gestureEvents ) {
            ts.addPoint( evt.getImagePlateX()*scale, -evt.getImagePlateY()*scale, evt.getWhen() );
        }
        
        Classification classification = recognizer.classify( ts );
        logger.info("Gesture "+classification.getFirstKey());
        
        assert(startEvent.getSource() instanceof EditableOrbiter.Body);
        if((startEvent.getSource() instanceof EditableOrbiter.Body)){
            EditableOrbiter startComp = (EditableOrbiter)startEvent.getIntersectedComponent3D(0, EditableOrbiter.class);        
            if (startComp==null) return;
                
            String gestureName = (String)classification.getFirstKey();
            if (gestureName.equals("check")) {                                
//                startComp.nameEditMode(true);                
            } else if (gestureName.equals("cross") || gestureName.equals("cross-R") || gestureName.equals("cross-rotate")) {
                
//                if(startComp.isGhostMode())
//                    CSDGeneralManager.getInstance().removePlan(startComp.getPlan());
                
                
            } else if (gestureName.equals("up")) {
            	/*
                if (CSDGeneralManager.getInstance().getFunction() instanceof Parabola) {
                    Parabola p = (Parabola) (CSDGeneralManager.getInstance().getFunction());
                    String newCategory = p.getCategoryOf(true, startComp.getPlan().getCategory());
                    startComp.getPlan().setCategory(newCategory);
                    startComp.requestParentToRevalidate();                    
                };
                */
            } else if (gestureName.equals("down")) {
            	/*
                if (CSDGeneralManager.getInstance().getFunction() instanceof Parabola) {
                    Parabola p = (Parabola) (CSDGeneralManager.getInstance().getFunction());
                    String newCategory = p.getCategoryOf(false, startComp.getPlan().getCategory());
                    startComp.getPlan().setCategory(newCategory);
                    startComp.requestParentToRevalidate();                    
                };
                */
            } else if (gestureName.equals("clockwise")) {
                
//                startComp.addPriority(1);
                
            } else if (gestureName.equals("anticlockwise")) {
                
//                startComp.addPriority(-1);
                
            }
        }        
    }
}
