/*
 * ContactService.java
 *
 * Created on June 20, 2005, 11:33 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.framework.contact;

import java.util.Iterator;
import java.util.Map;
import org.jdesktop.lg3d.apps.orgchart.framework.Service;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceException;


/**
 *
 * @author cc144453
 */
public interface ContactService extends Service {
    
    Map lookup(String query)
    throws ServiceException;
    
    Iterator<Map> search(String query)
    throws ServiceException;
    
    Map searchManager(Map contact)
    throws ServiceException;
    
    boolean hasDirectReports(Map contact);
    
    Iterator<Map> searchDirectReports(Map contact)
    throws ServiceException;
}
