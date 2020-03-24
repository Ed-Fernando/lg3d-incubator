/*
 * LDAPContactService.java
 *
 * Created on June 20, 2005, 11:36 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.framework.contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.jdesktop.lg3d.apps.orgchart.framework.AbstractService;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceException;


/**
 *
 * @author cc144453
 */
public class PreferenceContactService extends AbstractService implements ContactService {
    
    public static final String PREF_ROOT = "root";
    public static final String PREF_QUERY_MANAGER = "manager";
    public static final String PREF_QUERY_REPORTS = "reports";
    public static final String PREF_MANAGER_FLAG = "ismanager";
    public static final String PREF_ATTRIBUTES = "attributes";
    
    public static final String DEFAULT_ROOT = "/contacts";
    public static final String DEFAULT_QUERY_MANAGER = "${manager}";
    public static final String DEFAULT_QUERY_REPORTS = "${manager}";
    
    private Preferences contactsRoot;
    private String queryManagerExpr;
    private String queryReportsExpr;
    private String isManagerExpr;
    
    public void start() throws ServiceException {
        String root = prefs.get(PREF_ROOT, null);
        if (root != null) {
            root = context.eval(root);
        }
        if (root == null) {
            root = prefs.absolutePath() + DEFAULT_ROOT;
        }
        logger.info("PreferenceContactService root=" + root);
        contactsRoot = Preferences.userRoot().node(root);
        
        // setup other attributes
        queryManagerExpr = prefs.get(PREF_QUERY_MANAGER, DEFAULT_QUERY_MANAGER);
        logger.info("Preference manager expr=" + queryManagerExpr);
        queryReportsExpr = prefs.get(PREF_QUERY_REPORTS, DEFAULT_QUERY_REPORTS);
        logger.info("Preference reports expr=" + queryReportsExpr);
        isManagerExpr = prefs.get(PREF_MANAGER_FLAG, null);
        if (isManagerExpr != null) {
            logger.info("Preference manager indicator expr=" + isManagerExpr);
        }
        
        // make service available
        available = true;
    }
    
    public void restart() throws ServiceException {
        disconnect();
        start();
    }
    
    protected void disconnect() {
        synchronized (this) {
            contactsRoot = null;
        }
    }
    
    public Contact lookup(String query) throws ServiceException {
        try {
            if (contactsRoot.nodeExists(query)) {
                return new PreferenceContact(contactsRoot.node(query));
            } else {
                return null;
            }
        } catch (BackingStoreException backE) {
            throw new ServiceException("Error lookup query=" + query, backE);
        }
    }
    
    public Iterator<Map> search(String query) throws ServiceException {
        logger.fine("Preferences search query=" + query);
        throw new UnsupportedOperationException("Remove is not supported");
    }
    
    public Map searchManager(Map contact)
    throws ServiceException {
        String query = context.eval(queryManagerExpr, contact);
        return (query != null) ? lookup(query) : null;
    }
    
    public boolean hasDirectReports(Map contact) {
        boolean hasDirectReports = true;
        if (isManagerExpr != null) {
            String indicator = context.eval(isManagerExpr, contact);
            hasDirectReports =
                    !(indicator == null || indicator.startsWith("N") || indicator.startsWith("n"));
        } else {
            try {
                Iterator<Map> reports = searchDirectReports(contact);
                hasDirectReports = reports.hasNext();
            } catch (ServiceException serviceE) {
                logger.log(Level.SEVERE,
                        "Error searching for direct reports to check for manager", serviceE);
            }
        }
        return hasDirectReports;
    }
    
    public Iterator<Map> searchDirectReports(Map contact)
    throws ServiceException {
        try {
            // scan all the contacts sequentially
            String[] contactNames = contactsRoot.childrenNames();
            int numContacts = contactNames.length;
            int managerHashCode = contact.hashCode();
            ArrayList<Map> reports = new ArrayList<Map>(8);
            for (int i = 0; i < numContacts; i++) {
                Preferences contactNode = contactsRoot.node(contactNames[i]);
                if (contactNode.absolutePath().hashCode() == managerHashCode) {
                    reports.add(new PreferenceContact(contactNode));
                }
            }
            return reports.iterator();
        } catch (BackingStoreException backE) {
            throw new ServiceException("Error search direct report", backE);
        }
        
    }
    
    private class PreferenceContact extends HashMap<String, Object> implements Contact {
        private int hashCode;
        private PreferenceContact(Preferences contactNode) {
            hashCode = contactNode.absolutePath().hashCode();
            try {
                String[] keys = contactNode.keys();
                for (int i = 0; i < keys.length; i++) {
                    String key = keys[i];
                    put(key, contactNode.get(key, null));
                }
            } catch (BackingStoreException backE) {
                // no op
            }
        }
        public int hashCode() {
            return hashCode;
        }
    }
}

