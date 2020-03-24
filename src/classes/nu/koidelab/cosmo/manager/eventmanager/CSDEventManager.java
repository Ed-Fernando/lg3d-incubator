package nu.koidelab.cosmo.manager.eventmanager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fumi_ss
 * This class manage CSD Inner events.
 * This is included in CSDGeneralManager as one of sub managers.
 */
public class CSDEventManager {
    private List<CSDEventListener> listeners = new ArrayList<CSDEventListener>();

    //TODO CSDEventListener#processEvent() must be processed by Threads. 
    private void dispatchEvent(CSDEvent evt){
        for(int i=0;i<listeners.size();i++){
            CSDEventListener listener = listeners.get(i);
            Class<CSDEvent>[] classes = listener.getTargetEventClasses();
            for (int j = classes.length - 1; j >= 0; j--) {
                if(evt.getClass().equals(classes[j])){
                    listener.processEvent(evt);          
                }
            }
        }
    }

    /**
     * @param evt
     * Argument(evt) is dispatched to registered listeners. 
     */
    public void postEvent(CSDEvent evt){
        dispatchEvent(evt);
    }
       
    /**
     * @param listener
     * Register a listener of argument to this manager. 
     */
    public void addListener(CSDEventListener listener){
        listeners.add(listener);
    }

    /**
     * @param listener
     * Delete a listener of argument from this manager. 
     */
    public void removeListener(CSDEventListener listener){
        listeners.remove(listener);
    }
}
