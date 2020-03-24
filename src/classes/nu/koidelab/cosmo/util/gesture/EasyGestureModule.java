/*
 * ��.�� 2005/04/18
 *
 */
package nu.koidelab.cosmo.util.gesture;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.vecmath.Point3f;

import org.jdesktop.lg3d.scenemanager.GestureModule;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.sg.ColoringAttributes;
import org.jdesktop.lg3d.sg.LineArray;
import org.jdesktop.lg3d.sg.LineAttributes;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseDraggedEvent3D;

/**
 * @author fumi_ss
 * 10:49:11
 */

public class EasyGestureModule implements GestureModule, LgEventListener {
    protected static final Logger logger = Logger.getLogger("lg.gesture");
    protected ArrayList<MouseDraggedEvent3D> gestureEvents = new ArrayList<MouseDraggedEvent3D>();
    private boolean gestureStarted = false;
    private BranchGroup gestureBG;
    private BranchGroup detachableBG;
    private ExtendableLine line3D;
    /** The event which started the gesture */
    protected MouseButtonEvent3D startEvent;
    
    
    /** Creates a new instance of SatinGestureModule */
    public EasyGestureModule() {
        LgEventConnector.getLgEventConnector().addListener(
                LgEventSource.ALL_SOURCES, new DraggingListener());
    }
        
    public void processEvent(LgEvent evt) {
        // logger.severe("GM Received event "+evt.getClass());
        MouseButtonEvent3D mevt = (MouseButtonEvent3D) evt;
        // Gesture recognition is too heavyweight to be done
        // in the event thread so we shoudl use a gesture thread to
        // asyncronously process the gesture data

        if (mevt.isPressed()
                && mevt.getButton() == MouseButtonEvent3D.ButtonId.BUTTON3) {
            System.err.println("StartGesture (EasyGestureModule2) :"
                    + mevt.getSource());
            startGesture(mevt);
        } else if (mevt.isReleased()
                && mevt.getButton() == MouseButtonEvent3D.ButtonId.BUTTON3) {
            System.err.println("EndGesture (EasyGestureModule2) :");
            endGesture();
        }
    }

    public Class<LgEvent>[] getTargetEventClasses() {
        return new Class[] { MouseButtonEvent3D.class };
    }
    
    /**
     * Start capturing data for a gesture
     *
     * @param evt the event that initiated the gesture
     */
    protected void startGesture(MouseButtonEvent3D evt) {
        synchronized( gestureEvents ) {
            if (gestureStarted) 
                return;
            LgEventConnector.getLgEventConnector().addListener(LgEventSource.ALL_SOURCES, this);
            gestureStarted = true;
        }
        startEvent = evt;
        
        logger.fine("startGesture triggered by button "+startEvent.getButton());
        
        if (gestureBG!=null) {
            detachableBG = new BranchGroup();
            detachableBG.setCapability(BranchGroup.ALLOW_DETACH);
            line3D = new ExtendableLine();
            detachableBG.addChild( line3D );
            gestureBG.addChild(detachableBG);
        }
    }
    
    /**
     * Finish capturing data and process the data collected so far.
     * Post event for any recognised gesture and clear the data queue
     */
    protected void endGesture() {
        synchronized(gestureEvents) {
            if (!gestureStarted) return;
            LgEventConnector.getLgEventConnector().removeListener(
                LgEventSource.ALL_SOURCES, this);
            
            processGesture();
            
            gestureEvents.clear();
            gestureStarted = false;
        }
        
        logger.fine("endGesture");
        
        if (gestureBG!=null) {
            detachableBG.detach();
            detachableBG = null;
            line3D = null;
        }
    }
    
    /**
     * Process the gesture data and post any resulting events.
     */
    void processGesture(){
        System.err.println("Process Gesture!!");
    }
    
    public static final GestureModule createGestureModule() {
        return new EasyGestureModule();
     }
    
    public void setModuleRoot( BranchGroup rootBG ) {
        gestureBG = rootBG;
    }
    
    /**
     * A shape which renders a line to which vertices can be added
     */
    class ExtendableLine extends Shape3D {
        private static final int LINE_SIZE = 50;
        private LineArray currentLine;
        private int currentSize = 0;
         
        public ExtendableLine() {
            newLine();
            setCapability( Shape3D.ALLOW_GEOMETRY_WRITE );
            
//            logger.severe("New Extendable Line");
            
            Appearance app = new Appearance();
            LineAttributes lineAttr = new LineAttributes();
            lineAttr.setLineWidth(3f);
            app.setLineAttributes(lineAttr);
            ColoringAttributes colorAttr = new ColoringAttributes();
            colorAttr.setColor(1f,0f,0f);
            app.setColoringAttributes(colorAttr);
            TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.6f);
        app.setTransparencyAttributes(ta);
            setAppearance(app);
        }
        
        public void addVertex(Point3f vertex) {
            if (currentSize==LINE_SIZE) newLine();
            
//            logger.severe("Add vertex "+vertex);
           
            currentLine.setCoordinate( currentSize, vertex);
            currentSize++;
            
            if (currentSize%2==0) {
                currentLine.setValidVertexCount(currentSize);
                
                if (currentSize==LINE_SIZE) newLine();
                currentLine.setCoordinate( currentSize, vertex);
                currentSize++;                
            }
        }
        
        private void newLine() {
            currentLine = new LineArray(LINE_SIZE, LineArray.COORDINATES);
            currentLine.setCapability( LineArray.ALLOW_COORDINATE_WRITE );
            currentLine.setCapability( LineArray.ALLOW_COUNT_WRITE );
            currentSize=0;
            currentLine.setValidVertexCount(currentSize);
            this.addGeometry(currentLine);
        }
    }
    
    class DraggingListener implements LgEventListener{
        private Point3f tmpP3f = new Point3f();
        public void processEvent( LgEvent evt ) {
            gestureEvents.add((MouseDraggedEvent3D)evt);
            if (line3D!=null) {
                System.err.println("processEvent local Position (EazyGestureModule) :" + ((MouseDraggedEvent3D)evt).getLocalCursorPosition(new Point3f()));
//                System.err.println("processEvent cursor Position (EazyGestureModule) :" + ((MouseDraggedEvent3D)evt).getCursorPosition());
//                System.err.println("");
//                line3D.addVertex( ((MouseDraggedEvent3D)evt).getLocalCursorPosition());                              
                line3D.addVertex( ((MouseDraggedEvent3D)evt).getCursorPosition(tmpP3f));                              
            }
        }
        
        public Class<LgEvent>[] getTargetEventClasses() {
            return new Class[] {MouseDraggedEvent3D.class};
        }
    }
}
