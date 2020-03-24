/*
 * ServiceContextFactory.java
 *
 * Created on June 21, 2005, 11:00 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.framework;

import java.util.prefs.Preferences;

/**
 *
 * @author cc144453
 */
public class ServiceContextFactory {
    
    private static ServiceContextFactory instance = new ServiceContextFactory();
    
    public static ServiceContextFactory getInstance() {
        return instance;
    }
    
    public ServiceContext createServiceContext()
    throws ServiceException {
        return createServiceContext(null);
    }

    public ServiceContext createServiceContext(Preferences prefs)
    throws ServiceException {
        ServiceContext context = new DefaultServiceContext();
        context.init(prefs);
        return context;
    }
}
