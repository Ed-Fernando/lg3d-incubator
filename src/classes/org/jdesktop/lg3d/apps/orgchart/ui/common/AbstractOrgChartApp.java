/*
 * OrgChartApp.java
 *
 * Created on September 7, 2005, 10:38 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.ui.common;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.ContactService;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceContext;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceContextFactory;
import org.jdesktop.lg3d.apps.orgchart.framework.Util;
import org.jdesktop.lg3d.wg.Frame3D;


/**
 *
 * @author cc144453
 */
public abstract class AbstractOrgChartApp extends Frame3D {
    
    public static final int LAYOUT_DURATION = 150; // 150 ms
    public static final int ANIMATION_DURATION = 150; // 150ms

    private Logger logger = Logger.getLogger(getClass().getName());;
    private ServiceContext context;
    private ContactService contactService;
    private BufferedImage defaultPhoto;
    
    public AbstractOrgChartApp() {
        super();
        try {
            // create ServiceContext
            context = ServiceContextFactory.getInstance().createServiceContext();
            context.setResourceRoot("org/jdesktop/lg3d/apps/orgchart");
            
            // launch contact service
            contactService = (ContactService)context.getService("contacts.chart3d");
            contactService.start();
            
            // load default photo
            defaultPhoto = Util.imageFromResource(context, "ui/images/DefaultPhoto.png");
            
            // create UI
            createUI();
        } catch (Exception e) {
            UIUtil.errorDialog(context, "Fatal exception", e);
        }
    }
    
    public abstract void createUI() throws Exception;
    
    public ServiceContext getContext() {
        return context;
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    public ContactService getContactService() {
        return contactService;
    }
    
    public BufferedImage getDefaultPhoto() {
        return defaultPhoto;
    }
    
    public Iterator<Map> search(String searchTerm) {
        if (searchTerm.startsWith("*")) {
            searchTerm = "*" + searchTerm;
        }
        if (searchTerm.endsWith("*")) {
            searchTerm += "*";
        }
        String query =
                "(|(cn=" + searchTerm +
                ")(uid=" + searchTerm +
                ")(givenName=" + searchTerm +
                ")(sn=" + searchTerm +
                ")(employeeNumber=" + searchTerm +
                ")(mail=" + searchTerm + "))";
        logger.info("Searching for " + query);
        try {
            return contactService.search(query);
        } catch (Exception ex) {
            UIUtil.errorDialog(context, "Error searching for contact, query=" + query, ex);
            return null;
        }
    }
    
    public boolean isManager(Map contact) {
        return contactService.hasDirectReports(contact);
    }
    
    public Iterator<Map> searchDirectReports(Map contact) {
        try {
            return contactService.searchDirectReports(contact);
        } catch (Exception ex) {
            UIUtil.errorDialog(context, "Error searching for direct reports", ex);
            return null;
        }
    }
    
}
