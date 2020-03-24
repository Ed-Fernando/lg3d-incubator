/*
 * AbstractService.java
 *
 * Created on June 20, 2005, 1:19 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.framework;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;


/**
 *
 * @author cc144453
 */
public abstract class AbstractService implements Service {
    
    public static final String PREF_LOGLEVEL = "log.level";
    
    protected ServiceContext context;
    protected Preferences prefs;
    protected Logger logger;
    protected boolean available;
    
    public void init(ServiceContext context, Preferences prefs)
    throws ServiceException {
        this.context = context;
        this.prefs = prefs;
        this.logger = Logger.getLogger(getClass().getName());
        
        String logLevel = prefs.get(PREF_LOGLEVEL, null);
        if (logLevel != null) {
            logger.setLevel(Level.parse(logLevel));
        }
    }
    
    public ServiceContext getContext() {
        return context;
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    public boolean available() {
        return available;
    }
    
    protected void checkAvailable() throws ServiceException {
        if (!available) {
            throw new ServiceException("Service " + getClass().getName() + " not available");
        }
    }
}
