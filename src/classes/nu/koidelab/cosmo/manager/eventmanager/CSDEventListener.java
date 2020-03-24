/*
 * date¡§ 2006/01/12
 *
 */
package nu.koidelab.cosmo.manager.eventmanager;

/**
 * @author fumi_ss
 * Listener base class for CSD Inner Event 
 */

public interface CSDEventListener{
    public Class<CSDEvent>[] getTargetEventClasses();
    public void processEvent(CSDEvent evt);
}
