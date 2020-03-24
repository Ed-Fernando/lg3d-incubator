/*
 * Service.java
 *
 * Created on June 20, 2005, 11:32 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.framework;

import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 *
 * @author cc144453
 */
public interface Service {
    void init(ServiceContext context, Preferences prefs)
    throws ServiceException;
    
    ServiceContext getContext();
    
    Logger getLogger();
    
    void start() throws ServiceException;
    
    void restart() throws ServiceException;
    
    boolean available();
}
