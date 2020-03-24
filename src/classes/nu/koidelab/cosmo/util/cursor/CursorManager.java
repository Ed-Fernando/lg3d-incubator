package nu.koidelab.cosmo.util.cursor;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.Node;
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.Component3DManualMoveEvent;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;

public class CursorManager {
    private static final float EYE_DISTANCE = 0.47f;
    private final Vector3f v2eye = new Vector3f(0, 0, EYE_DISTANCE);
    
    private Container3D displayContainer = new Container3D();    
    private Transform3D t3d = new Transform3D();
    private Vector3f v2c = new Vector3f();
    private Vector3f eye2c = new Vector3f();
    private Vector3f pos = new Vector3f();
           
    public CursorManager(Frame3D parent){
        parent.addChild(displayContainer);
//        parent.addListener(new Frame3DManualMoveEventListener());
    }

    public void addCursor(Cursor cursor){
        if(!checkDuplication(cursor))
            return;
        displayContainer.addChild(cursor);
        resetCursor(cursor);
        cursor.addListener(new Component3DManualMoveEventListener());
    }
    
    private boolean checkDuplication(Cursor newCursor){
    	int i = displayContainer.indexOfChild(newCursor);    	
    	return (i < 0);
    }
        
    public void removeAllCursors(){
        for (int i = displayContainer.numChildren() - 1; i >= 0; i--) {
            Node node = displayContainer.getChild(i);
            if (node instanceof Cursor)          
                removeCursor( (Cursor)node );                
        }
    }
    
    /*
    * Remove cursors that target to its component.
     * */
    public void removeCursors(Component3D c){
    	for (int i = displayContainer.numChildren() - 1; i >= 0; i--) {
    		Cursor cursor = (Cursor)(displayContainer.getChild(i));
    		if(cursor.getTarget().equals(c)){
    			removeCursor(cursor);
    			i = displayContainer.numChildren() - 1;
    		}
    	}
    }
    
    /*
     * Remove cursors that target to its component.
      * */
     public void removeCursors(Component3D target, Class cursorClass){
     	for (int i = displayContainer.numChildren() - 1; i >= 0; i--) {
     		Cursor cursor = (Cursor)(displayContainer.getChild(i));
     		if(cursor.getTarget().equals(target)){
     			if(cursor.getClass().equals(cursorClass)){
     				removeCursor(cursor);
     				i = displayContainer.numChildren() - 1;
     			}
     		}
     	}
     }
    
    /*
     * Remove it's cursor.
     */
    public void removeCursor(Cursor c){    
    	if(checkDuplication(c)) return;
        c.getTarget().removeListener(c.listener);                
        displayContainer.removeChild(c);
    }
    
    /*
    * Return indices relation to args. 
    *  if not exist, return -1 
     */
    private List<Integer> getCursorIndex(Component3D comp){
        List<Integer> indices = new ArrayList<Integer>();
        
        for (int i = displayContainer.numChildren() - 1; i >= 0; i--) {
            Node node = displayContainer.getChild(i);
            if (node instanceof Cursor) {          
                Cursor c = (Cursor)node;
                if(c.getTarget().equals(comp))
                    indices.add(i);
            }
        }
        return indices;
    }
    
    public void resetCursorAll(){
        for (int i = displayContainer.numChildren() - 1; i >= 0; i--) {
            Node node = displayContainer.getChild(i);
            if (node instanceof Cursor) {
                resetCursor((Cursor) node);
            }
        }
    }
    
    void frame3DMoved(float x, float y, float z){
        displayContainer.setTranslation(-x, -y, -z);
        resetCursorAll();
    }
    
    private void resetCursor(Cursor cursor){
        t3d.normalize();
        cursor.getTarget().getLocalToVworld(t3d);        
        v2c.normalize();
        t3d.get(v2c);

        eye2c.sub(v2c, v2eye);
        float ratio = - EYE_DISTANCE / eye2c.z;   
        eye2c.scale(ratio);
        pos.add(v2eye, eye2c);
         
        cursor.changeTranslation(pos.x, pos.y, pos.z);
        cursor.startAnimation();
    }
    
    private class Component3DManualMoveEventListener implements LgEventListener{
        private Class[] target = {Component3DManualMoveEvent.class};

        public Class<LgEvent>[] getTargetEventClasses() {
            return target;
        }
        
        public void processEvent(LgEvent evt) {
                Component3DManualMoveEvent mme = (Component3DManualMoveEvent) evt;
                Component3D comp = (Component3D)(evt.getSource());
                List<Integer> indices = getCursorIndex(comp);
                for (int i = indices.size() - 1; i >= 0; i--) {
                    Cursor cursor = (Cursor)(displayContainer.getChild(indices.get(i)));
                    if(mme.isStarted()){
                        cursor.setVisible(false);
                    } else {
                        cursor.setVisible(true);                    
                        resetCursor(cursor);
                    }
                }
        }
    }
    
    /*
    private class Frame3DManualMoveEventListener implements LgEventListener{
        private Class[] target = {Component3DManualMoveEvent.class, Frame3DAnimationFinishedEvent.class};
        
        public Class<LgEvent>[] getTargetEventClasses() {
            return target;
        }
        
        public void processEvent(LgEvent evt) {
            LgEventSource source = evt.getSource();
            
	        Component3DManualMoveEvent mme = (Component3DManualMoveEvent) evt;				
            if (mme.isStarted()) {
                displayContainer.setVisible(false);
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO 自動生成された catch ブロック
                    e.printStackTrace();
                }
                displayContainer.setVisible(true);
                Frame3D f = (Frame3D) source;
                Vector3f vec = new Vector3f();
                f.getFinalTranslation(vec);
                vec.scale(-1);
                displayContainer.setTranslation(vec.x, vec.y, vec.z);
                resetCursorAll();
            }
        }

    }
    */

    public static abstract class Cursor extends Container3D{
        private Component3D target;
        private Component3DManualMoveEventListener listener;
        
        public Cursor(){}
        
        public Cursor(Component3D target){
          if(target == null)
              throw new IllegalArgumentException("Target Component3D cannot be null.");
          this.target = target;
        }
        
        public abstract void startAnimation();
        
        public void setTarget(Component3D target){
        	this.target = target;
        }
        
        public Component3D getTarget(){
            return target;
        }
        
        private void addListener(Component3DManualMoveEventListener listener){
            target.addListener(listener);
            this.listener = listener;
        }
    }
       
}
