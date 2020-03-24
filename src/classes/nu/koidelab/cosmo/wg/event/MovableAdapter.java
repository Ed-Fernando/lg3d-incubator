/*
 * ��.��: 2005/06/10
 */
package nu.koidelab.cosmo.wg.event;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.utils.eventadapter.EventAdapter;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.Component3DManualMoveEvent;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseDraggedEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;

public class MovableAdapter implements EventAdapter{
    public static final Class[] targetEventClasses = {
        MouseButtonEvent3D.class,
        MouseDraggedEvent3D.class
    };
    private MouseEvent3D.ButtonId button = MouseEvent3D.ButtonId.BUTTON1;
    private Point3f tmpP3f = new Point3f();        
    private Point3f initLoc = new Point3f();        
    private Vector3f orgSrcLoc = new Vector3f();        
    
    public MovableAdapter(){}
    
    public MovableAdapter(MouseEvent3D.ButtonId button){        
        this();
        setButton(button);
    }
    
    public Class<LgEvent>[] getTargetEventClasses() {
        return targetEventClasses;
    }
        
    public void setButton(MouseEvent3D.ButtonId button){
        this.button = button;
    }
        
    public void processEvent(LgEvent event) {        
        assert(event instanceof MouseEvent3D);
        MouseEvent3D me3d = (MouseEvent3D)event;
        if (!me3d.getButton().equals(button)) {
            return;
        }
        
        if (event instanceof MouseButtonEvent3D) {
            MouseButtonEvent3D mbe3d = (MouseButtonEvent3D)event;
            if (mbe3d.isPressed()) {
                mbe3d.getIntersection(initLoc);
                ((Component3D)(mbe3d.getSource())).getFinalTranslation(orgSrcLoc);
                mbe3d.getSource().postEvent(new Component3DManualMoveEvent(true, mbe3d.getIntersection(new Point3f())));
            } else if(mbe3d.isReleased()){
                mbe3d.getSource().postEvent(new Component3DManualMoveEvent(false, mbe3d.getIntersection(new Point3f())));
            }
            return;
        }
        
        assert(event instanceof MouseDraggedEvent3D);
        MouseDraggedEvent3D mme3d = (MouseDraggedEvent3D)event;
        mme3d.getDragPoint(tmpP3f);
        float distX = tmpP3f.x - initLoc.x; 
        float distY = tmpP3f.y - initLoc.y; 
        float distZ = tmpP3f.z - initLoc.z; 
         ((Component3D)mme3d.getSource()).setTranslation(distX+orgSrcLoc.x, distY+orgSrcLoc.y, distZ+orgSrcLoc.z);
    }

}
