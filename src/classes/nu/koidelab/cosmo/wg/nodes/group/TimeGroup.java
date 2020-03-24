package nu.koidelab.cosmo.wg.nodes.group;

import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Vector3f;

import nu.koidelab.cosmo.util.schedule.Plan;
import nu.koidelab.cosmo.wg.nodes.Orbiter;

import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.EventAdapter;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEnteredEvent3D;

public abstract class TimeGroup extends Container3D {
    public long st;
    public long ed;
    protected List<Plan> plans;
    static{
        LgEventConnector.getLgEventConnector().addListener(
                IDPanel.class, new PanelEventHandler());
    }

    
    protected void pickoutPlans(List<Plan> target){
        if(plans == null)
            plans = new LinkedList<Plan>();
        for(int i = 0; i < target.size(); i++){
            Plan p = target.get(i);
            if(isInRange(p.getTime())){
                plans.add(p);
                target.remove(p);
                i--;
            }            
        }
        System.err.println(this.getClass() + " : " + plans.size());        
    }
    
    boolean isInRange(long msec){
        return (st <= msec && msec < ed);
    }
        
    public static abstract class IDPanel extends Orbiter {
        protected IDPanel(){
            setCursor(Cursor3D.SMALL_CURSOR);
            setAnimation(new NaturalMotionAnimation(350));
            setMouseEventSource(MouseButtonEvent3D.class, true);
            setMouseEventSource(MouseEnteredEvent3D.class, true);
            setRotationAxis(0, 1, 0);
        }
        
        protected abstract void setFocus(boolean focus);        
        protected abstract void doubleClicked();
        protected abstract void selected();
    }

    /* ********MouseEvent Handler for IDPanel********* */
    private static class PanelEventHandler implements EventAdapter{     
        private Class[] targets = {MouseButtonEvent3D.class, MouseEnteredEvent3D.class};
        private Vector3f orgLoc = new Vector3f();
        private Thread thread;
        
        public Class[] getTargetEventClasses() {
            return targets;
        };
        
        public void processEvent(LgEvent evt) {
            if (evt instanceof MouseButtonEvent3D) {
                MouseButtonEvent3D mbe = (MouseButtonEvent3D) evt;
                if (mbe.isClicked() && mbe.getClickCount() == 2) {
                    thread = null;
                    ((IDPanel) mbe.getSource()).doubleClicked();
                }
                if (mbe.isClicked() && mbe.getClickCount() == 1) {
                    startSingleClickTimer(mbe);
                }
                return;
            }
            if (evt instanceof MouseEnteredEvent3D) {
                MouseEnteredEvent3D mee = (MouseEnteredEvent3D) evt;
                IDPanel p = ((IDPanel) mee.getSource());
                if (mee.isEntered()) {
                    p.setFocus(true);
                } else {
                    p.setFocus(false);
                }
            }
        }
        
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
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /* Do nothing if null is assigned to thread by double-click events  */
                if(thread == null)
                    return;
                ((IDPanel) mbe.getSource()).selected();
            }
        }
    }
}
