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
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseDraggedEvent3D;

/**
 * @author fumi_ss 10:49:11
 */

public abstract class AppGestureModuleBase implements GestureModule,
        LgEventListener {
    protected static final Logger logger = Logger.getLogger("lg.gesture");
    protected ArrayList<MouseDraggedEvent3D> gestureEvents = new ArrayList<MouseDraggedEvent3D>();
    protected boolean gestureStarted = false;
    protected Class c3dForAttachLine;
    protected Component3D adapter;
    protected ExtendableLine line3D;
    /** The event which started the gesture */
    protected MouseButtonEvent3D startEvent;
    protected DraggingListener draggingListener;

    /** Creates a new instance of AppGestureModuleBase
     *   C3dForAttachLine is used for attaching cursor's trajectory.
     *   This argument class must extend Component3D class.
     *  */
    public AppGestureModuleBase(Class c3dForAttachLine) {
        draggingListener = new DraggingListener();
        this.c3dForAttachLine = c3dForAttachLine;
    }

    public void processEvent(LgEvent evt) {
        MouseButtonEvent3D mevt = (MouseButtonEvent3D) evt;

        if (mevt.isPressed()
                && mevt.getButton() == MouseButtonEvent3D.ButtonId.BUTTON3) {
            startGesture(mevt);
        } else if (mevt.isReleased()
                && mevt.getButton() == MouseButtonEvent3D.ButtonId.BUTTON3) {
            endGesture(mevt);
        }
    }

    public Class<LgEvent>[] getTargetEventClasses() {
        return new Class[] { MouseButtonEvent3D.class };
    }

    /**
     * Start capturing data for a gesture
     * 
     * @param evt
     *                       the event that initiated the gesture
     */
    protected void startGesture(MouseButtonEvent3D evt) {
        startEvent = evt;
        synchronized (gestureEvents) {
            if (gestureStarted)
                return;
            LgEventConnector.getLgEventConnector().addListener(
//                    startEvent.getSource(), draggingListener);
                    LgEventSource.ALL_SOURCES, draggingListener);
            gestureStarted = true;
        }
        
        // make nodes for Extendable Line to attach to the event source.
        adapter = new Component3D();
        Component3D src = (Component3D) (evt.getIntersectedComponent3D(0,
                c3dForAttachLine));
        
       line3D = new ExtendableLine();
       adapter.addChild(line3D);
       adapter.setPickable(false);
       src.addChild(adapter);              
    }

    public void setModuleRoot(BranchGroup root) {}
    
    /**
     * Finish capturing data and process the data collected so far. Post event for any recognised
     * gesture and clear the data queue
     */
    protected void endGesture(MouseButtonEvent3D evt) {
        synchronized (gestureEvents) {
            if (!gestureStarted)
                return;
            LgEventConnector.getLgEventConnector().removeListener(
//                    startEvent.getSource(), draggingListener);
                    LgEventSource.ALL_SOURCES, draggingListener);
            processGesture();

            gestureEvents.clear();
            gestureStarted = false;
        }

        logger.fine("endGesture");

        adapter.detach();
        adapter = null;
        line3D = null;
    }

    /**
     * Process the gesture data and post any resulting events.
     */
    abstract void processGesture();

    /**
     * A shape which renders a line to which vertices can be added
     */
    class ExtendableLine extends Shape3D {
        private static final int LINE_SIZE = 50;

        private LineArray currentLine;

        private int currentSize = 0;

        public ExtendableLine() {
            newLine();
            setCapability(Shape3D.ALLOW_GEOMETRY_WRITE);

            // logger.severe("New Extendable Line");

            Appearance app = new Appearance();
            LineAttributes lineAttr = new LineAttributes();
            lineAttr.setLineWidth(3f);
            app.setLineAttributes(lineAttr);
            ColoringAttributes colorAttr = new ColoringAttributes();
            colorAttr.setColor(0.5f, 0.5f, 1f);
            app.setColoringAttributes(colorAttr);
            TransparencyAttributes ta = new TransparencyAttributes(
                    TransparencyAttributes.BLENDED, 0.6f);
            app.setTransparencyAttributes(ta);
            setAppearance(app);
        }

        public void addVertex(Point3f vertex) {
            if (currentSize == LINE_SIZE)
                newLine();

            // logger.severe("Add vertex "+vertex);

            currentLine.setCoordinate(currentSize, vertex);
            currentSize++;

            if (currentSize % 2 == 0) {
                currentLine.setValidVertexCount(currentSize);

                if (currentSize == LINE_SIZE)
                    newLine();
                currentLine.setCoordinate(currentSize, vertex);
                currentSize++;
            }
        }

        private void newLine() {
            currentLine = new LineArray(LINE_SIZE, LineArray.COORDINATES);
            currentLine.setCapability(LineArray.ALLOW_COORDINATE_WRITE);
            currentLine.setCapability(LineArray.ALLOW_COUNT_WRITE);
            currentSize = 0;
            currentLine.setValidVertexCount(currentSize);
            this.addGeometry(currentLine);
        }
    }

    class DraggingListener implements LgEventListener {
        private Point3f tmpP3f = new Point3f();
        public void processEvent(LgEvent evt) {
            gestureEvents.add((MouseDraggedEvent3D) evt);
            if (line3D != null) {
                MouseDraggedEvent3D mde = (MouseDraggedEvent3D)evt;
                Point3f pos = mde.getCursorPosition(tmpP3f);                
                Component3D c3d = mde.getIntersectedComponent3D(0, c3dForAttachLine);

                if (c3d.isLive()) {
                    Transform3D t3d = new Transform3D();
                    c3d.getLocalToVworld(t3d);
                    t3d.invert();
                    t3d.transform(pos);
                    line3D.addVertex(pos);
                }
            }
        }

        public Class<LgEvent>[] getTargetEventClasses() {
            return new Class[] { MouseDraggedEvent3D.class };
        }
    }
}
