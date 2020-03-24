/*
 * ServiceException.java
 *
 * Created on June 20, 2005, 11:41 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.framework;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cc144453
 */
public class ServiceException extends Exception {
    
    private Logger logger = Logger.getLogger(getClass().getName());
    
    public ServiceException() {
        this(null, null);
    }
    public ServiceException(String message) {
        this(message, null);
    }
    
    public ServiceException(Throwable t) {
        this(null, t);
    }
    
    public ServiceException(String message, Throwable t) {
        super(message, t);
        if (t != null) {
            logger.log(Level.SEVERE,
                    ((message != null) ? message : getClass().getName()), t);
        } else {
            logger.log(Level.SEVERE,
                    ((message != null) ? message : getClass().getName()));
            
        }
    }
    
}
