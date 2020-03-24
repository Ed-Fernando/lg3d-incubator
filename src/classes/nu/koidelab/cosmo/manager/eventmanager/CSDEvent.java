/*
 * date¡§ 2006/01/12 
 */
package nu.koidelab.cosmo.manager.eventmanager;

import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 * @author fumi_ss
 * CSD Inner Event base class
 */
public abstract class CSDEvent {
    protected LgEventSource source;
    
    protected CSDEvent(LgEventSource src){
        /*
        if(src == null)
            throw new RuntimeException("Argument source cannot be null.");
            */
        source = src;        
    }
            
    public LgEventSource getSource(){
        return source;
    }
}

