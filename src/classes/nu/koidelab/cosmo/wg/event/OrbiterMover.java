/*
 * ��.��: 2005/05/03
 */
package nu.koidelab.cosmo.wg.event;

import javax.vecmath.Point3f;

import nu.koidelab.cosmo.wg.nodes.EditableOrbiter;

import org.jdesktop.lg3d.wg.event.Component3DManualMoveEvent;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseDraggedEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;

/**
 * @author fumi OrbiterMover nu.koidelab.cosmo.wg.event
 */
public class OrbiterMover implements LgEventListener {
    private static final Class[] targetEventClasses = {
            MouseButtonEvent3D.class, MouseDraggedEvent3D.class };

    private float initIPLocX;
    private float initIPLocY;
    private long ghostBaseTime = -1;// base time of ghost mode

    public OrbiterMover() {}

    public Class<LgEvent>[] getTargetEventClasses() {
        return targetEventClasses;
    }

    public void processEvent(LgEvent evt) {
        MouseEvent3D me = (MouseEvent3D) evt;
        EditableOrbiter target = (EditableOrbiter)(me.getIntersectedComponent3D(0, EditableOrbiter.class));        
        
        if (evt instanceof MouseButtonEvent3D) {
            MouseButtonEvent3D mbe = (MouseButtonEvent3D) evt;
            if (!target.isGhostMode()) {
                
                /* switch in ghost mode (operation mode) */
                if (mbe.isClicked()
                        && mbe.getClickCount() == 2
                        && mbe.getButton() == MouseButtonEvent3D.ButtonId.BUTTON1) {
//                    initIPLocX = mbe.getImagePlateX();
//                    initIPLocY = mbe.getImagePlateY();  
//                	startGhostMode(target);
                    return;
                }
                
            } else {
                if (mbe.isPressed() && mbe.getButton() == MouseEvent3D.ButtonId.BUTTON1) {                    
                    /* Start moving an orbiter */
                    initIPLocX = mbe.getImagePlateX();
                    initIPLocY = mbe.getImagePlateY();
                    target.postEvent(new Component3DManualMoveEvent(true, mbe.getIntersection(new Point3f())));
                    
                } else  if(mbe.isClicked() && mbe.getButton() == MouseEvent3D.ButtonId.BUTTON3){                    
                    // Ghost mode CANCEL 
//                	target.endGhostMode(false);
                	endGhostMode(target, false);
                    
                } else if (mbe.isReleased()) {                    
                    // Set temporary time, and reset cursor.  
                    ghostBaseTime = target.getPlan().getTime();
                    target.postEvent(new Component3DManualMoveEvent(false, mbe.getIntersection(new Point3f())));
                    
                } else if (mbe.isClicked() && mbe.getClickCount() == 2) {
                    if (mbe.getButton() == MouseEvent3D.ButtonId.BUTTON1) {
                        // Cancel single-click event 
                        thread = null;                        
                        // Ghost mode END 
//                        target.endGhostMode(true);
//                        endGhostMode(target, true);
                    }
                }
                
                if (mbe.isClicked() && mbe.getClickCount() == 1 &&
                        mbe.getButton() == MouseEvent3D.ButtonId.BUTTON1){
                    startSingleClickTimer(mbe);
                }
                
                /* -------------------------------------------------------------------------------------------------------- */                
            }
            return;            
        }

        /* Process MouseDraggedEvents if ghost-mode */
        assert (evt instanceof MouseDraggedEvent3D);
        if (evt instanceof MouseDraggedEvent3D) {
            MouseDraggedEvent3D mde = (MouseDraggedEvent3D) evt;
            if (!mde.getButton().equals(MouseEvent3D.ButtonId.BUTTON1))
                return;
            
            if (target.isGhostMode()) {
                /* dragging on Ghost mode */                                           
                float tmpX = mde.getImagePlateX() - initIPLocX;
                float tmpY = mde.getImagePlateY() - initIPLocY;
                int direction = (tmpX >= 0) ? 1 : -1;
                float tmp = (float)Math.sqrt(tmpX*tmpX + tmpY*tmpY) * direction;
                int def = (int) (tmp * 700);
                int acc1;
                if (Math.abs(def) <= 24)
                    acc1 = 0;
                else if (Math.abs(def) <= 48)
                    acc1 = 2 * (Math.abs(def) - 24);
                else if (Math.abs(def) <= 72)
                    acc1 = 48 + 5 * (Math.abs(def) - 48);
                else
                    acc1 = 168 + 12 * (Math.abs(def) - 72);
                if (def < 0)
                    acc1 = -acc1;
                target.getPlan().setTime(ghostBaseTime - (def + acc1) * 1800000l);
                target.requestParentToRevalidate();
            }
        }
    }
    
    public void startGhostMode(EditableOrbiter target){
    	if(ghostBaseTime >= 0) return;
        target.startGhostMode();
        ghostBaseTime = target.getPlan().getTime();    	
    }
    
    public void endGhostMode(EditableOrbiter target, boolean editOK){
    	if(ghostBaseTime < 0) return;
    	ghostBaseTime = -1;
        target.endGhostMode(editOK);
    }
            
    /* ---------------------- Do event processing only when single-click events occur. -------------------- */
    private Thread thread = null;    
    private void startSingleClickTimer(MouseButtonEvent3D mbe){
        thread = new Thread(new SingleClickEventHandler(mbe));
        thread.start();
    }
    
    /* Do event processing only when single-click events occur. */
    private class SingleClickEventHandler implements Runnable{
        MouseButtonEvent3D mbe;
        private SingleClickEventHandler(MouseButtonEvent3D mbe){
            this.mbe = mbe;
        }
        
        public void run() {                
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }               
            if(thread == null)/* Do nothing if null is assigned to thread by double-click events  */
                return;            
                        
            /* Process when single-click events occur 
            EditableOrbiter eo = ((EditableOrbiter)(mbe.getIntersectedComponent3D(0, EditableOrbiter.class)));            
            CSDGeneralManager.getInstance().focusTo(eo);            
            eo.launchEditor();
            */
        }
    }
                
}
