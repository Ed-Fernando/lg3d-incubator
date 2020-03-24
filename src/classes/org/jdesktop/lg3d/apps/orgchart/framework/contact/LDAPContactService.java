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
import java.util.Hashtable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.SizeLimitExceededException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.InitialLdapContext;
import org.jdesktop.lg3d.apps.orgchart.framework.AbstractService;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceException;


/**
 *
 * @author cc144453
 */
public class LDAPContactService extends AbstractService implements ContactService {

    public static final String PREF_URI = "uri";
    public static final String PREF_BASEDN = "basedn";
    public static final String PREF_BINDDN = "binddn";
    public static final String PREF_PASSWORD = "password";
    public static final String PREF_MAX = "limit";
    public static final String PREF_QUERY_MANAGER = "manager";
    public static final String PREF_QUERY_REPORTS = "reports";
    public static final String PREF_MANAGER_FLAG = "ismanager";
    public static final String PREF_ATTRIBUTES = "attributes";

    private static final String DEFAULT_QUERY_MANAGER = "${manager}";
    private static final String DEFAULT_QUERY_REPORTS = "${manager}";

    private String baseDN;
    private Hashtable baseProps;
    private LdapContext baseContext;
    private Hashtable searchProps;
    private LdapContext searchContext;
    private String queryManagerExpr;
    private String queryReportsExpr;
    private String isManagerExpr;
    private SearchControls sc;
    private HashSet<String> scAttrSet;

    public void start() throws ServiceException {
        // first verify that URI is contactable
        String uri = prefs.get(PREF_URI, null);
        if (uri != null) {
            uri = context.eval(uri);
        }
        if (uri == null) {
            throw new ServiceException("URI parameter not specified");
        }

        // setup the LDAP connection properties
        baseDN = prefs.get(PREF_BASEDN, "");
        baseProps = new Hashtable();
        baseProps.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        baseProps.put(Context.PROVIDER_URL, uri);
        searchProps = new Hashtable();
        searchProps.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        if (!uri.endsWith("/")) {
            uri += "/";
        }
        searchProps.put(Context.PROVIDER_URL, uri + baseDN);
        if (uri.toLowerCase().startsWith("ldaps")) {
            baseProps.put(Context.SECURITY_PROTOCOL, "ssl");
            searchProps.put(Context.SECURITY_PROTOCOL, "ssl");
        }
        String user = prefs.get(PREF_BINDDN, null);
        if (user != null && user.length() > 0) {
            baseProps.put(Context.SECURITY_PRINCIPAL, user);
            searchProps.put(Context.SECURITY_PRINCIPAL, user);
        }
        String password = prefs.get(PREF_PASSWORD, null);
        if (password != null && password.length() > 0) {
            baseProps.put(Context.SECURITY_AUTHENTICATION, "simple");
            baseProps.put(Context.SECURITY_CREDENTIALS, password);
            searchProps.put(Context.SECURITY_AUTHENTICATION, "simple");
            searchProps.put(Context.SECURITY_CREDENTIALS, password);
        }

        logger.info("LDAP uri=" +
                (String)baseProps.get(Context.PROVIDER_URL) +
                " base=" + baseDN +
                " bind=" +
                (String)baseProps.get(Context.SECURITY_PRINCIPAL));
        if (baseDN.length() > 0) {
            baseDN = "," + baseDN;
        }

        // setup other attributes
        queryManagerExpr = prefs.get(PREF_QUERY_MANAGER, DEFAULT_QUERY_MANAGER);
        logger.info("LDAP manager expr=" + queryManagerExpr);
        queryReportsExpr = prefs.get(PREF_QUERY_REPORTS, DEFAULT_QUERY_REPORTS);
        logger.info("LDAP reports expr=" + queryReportsExpr);
        isManagerExpr = prefs.get(PREF_MANAGER_FLAG, null);
        if (isManagerExpr != null) {
            logger.info("LDAP manager indicator expr=" + isManagerExpr);
        }

        // setup the attributes to return
        scAttrSet = new HashSet<String>(32);
        scAttrSet.add(Contact.ATTR_UID);
        scAttrSet.add(Contact.ATTR_FIRSTNAME);
        scAttrSet.add(Contact.ATTR_LASTNAME);
        scAttrSet.add(Contact.ATTR_EMPLOYEENUMBER);
        scAttrSet.add(Contact.ATTR_MANAGER);
        scAttrSet.add(Contact.ATTR_LOCATION);
        scAttrSet.add(Contact.ATTR_PHOTO);
        scAttrSet.add(Contact.ATTR_PHOTOURL);
        scAttrSet.add(Contact.ATTR_CALENDAR);
        scAttrSet.add(Contact.ATTR_EMAIL);
        scAttrSet.add(Contact.ATTR_PAGER);
        scAttrSet.add(Contact.ATTR_PAGERMAIL);
        scAttrSet.add(Contact.ATTR_PHONE);
        scAttrSet.add(Contact.ATTR_SCREENNAME);
        scAttrSet.add(Contact.ATTR_URL);
        scAttrSet.add(Contact.ATTR_PRESENCE);
        String prefAttr = prefs.get(PREF_ATTRIBUTES, null);
        if (prefAttr != null) {
            String[] prefAttrs = prefAttr.split(",");
            for (int i = 0; i < prefAttrs.length; i++) {
                scAttrSet.add(prefAttrs[i]);
            }
        }
        String[] scAttrs = new String[scAttrSet.size()];
        int i = 0;
        for (String scAttribute : scAttrSet) {
            scAttrs[i++] = scAttribute;
        }
        sc = new SearchControls();
        sc.setReturningAttributes(scAttrs);
        //sc.setTimeLimit(10000);

        // setup max count
        long max = prefs.getLong(PREF_MAX, -1L);
        if (max > 0) {
            logger.info("LDAP query limit=" + max);
            sc.setCountLimit(max);
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
            if (baseContext != null) {
                logger.info("Disconnecting from LDAP server");

                try {
                    baseContext.close();
                } catch (NamingException nameE) {
                    logger.log(Level.SEVERE, "Error disconnecting from LDAP server", nameE);
                } finally {
                    baseContext = null;
                }
            }
        }
    }

    private LdapContext getBaseContext() throws ServiceException {
        checkAvailable();

        // only connect if not done already
        if (baseContext == null) {
            try {
                baseContext = new InitialLdapContext(baseProps, null);
            } catch (NamingException nameE) {
                throw new ServiceException(
                        "Unable to connect to LDAP server",
                        nameE);
            }
        }
        return baseContext;
    }

    public Map lookup(String query) throws ServiceException {
        LdapContext contactContext = null;
        try {
            contactContext = (LdapContext)getBaseContext().lookup(query);
        }  catch (NameNotFoundException notFoundE) {
            logger.fine("Lookup failed, searching query=" + query);
            return searchForOne(query);
        } catch (NamingException nameE) {
            // one more try in case connection died
            disconnect();
            try {
                contactContext = (LdapContext)getBaseContext().lookup(query);
            }  catch (NameNotFoundException notFoundE2) {
                logger.fine("Lookup failed, searching query=" + query);
                return searchForOne(query);
            } catch (NamingException nameE2) {
                throw new ServiceException(
                        "Error looking up query=" + query);
            }
        }
        logger.finer("Looked up query=" + query);
        try {
            return createLDAPContact(query, contactContext.getAttributes(""));
        } catch (NamingException nameE) {
            throw new ServiceException("Exception construction contact=" + query);
        }
    }

    private LdapContext getSearchContext() throws ServiceException {
        checkAvailable();

        // only connect if not done already
        if (searchContext == null) {
            try {
                searchContext = new InitialLdapContext(searchProps, null);
            } catch (NamingException nameE) {
                throw new ServiceException(
                        "Unable to connect to LDAP server",
                        nameE);
            }
        }
        return searchContext;
    }
    private Map searchForOne(String query) throws ServiceException {
        Iterator<Map> contacts = search(query);
        if (contacts == null) {
            return null;
        } else {
            Map contact = contacts.next();
            if (contacts.hasNext()) {
                throw new ServiceException("More than one Contact found for query=" + query);
            }
            return contact;
        }
    }

    public Iterator<Map> search(String query) throws ServiceException {
        logger.fine("LDAP search query=" + query);
        try {
            if (query == null) {
                return null;
            } else {
                return new LDAPContactIterator(
                        getSearchContext().search("", query, sc));
            }
        } catch (NamingException nameE) {
            // retry the one more time the search
            // the connection may have been shutdown by the remote server
            disconnect();
            try {
                return new LDAPContactIterator(
                        getSearchContext().search("", query, sc));
            } catch (NamingException nameE2) {
                throw new ServiceException(
                        "Error searching query=" + query,
                        nameE2);
            }
        }
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
        String query = context.eval(queryReportsExpr, contact);
        return search(query);
    }

    private String translateAttribute(String attributeID) {
        // resolve case-sensitivity issues by translating the attribute names
        // to match what Contact class expects
        if (scAttrSet.contains(attributeID)) {
            return attributeID;
        } else {
            for (String scAttr : scAttrSet) {
                if (scAttr.equalsIgnoreCase(attributeID)) {
                    return scAttr;
                }
            }
            return null;
        }
    }

    private LDAPContact createLDAPContact(String ldapName, Attributes attributes) {
        LDAPContact contact = new LDAPContact(ldapName);
        NamingEnumeration entries = attributes.getAll();
        while (entries.hasMoreElements()) {
            Attribute attribute = (Attribute)entries.nextElement();
            // if there are >1 attribute with the same name, get only the first :(
            // always use lowercase
            String attributeID = translateAttribute(attribute.getID());
            if (attributeID != null) {
                Object value = toValue(attribute);
                if (value != null) {
                    contact.put(attributeID, value);
                }
            }
        }
        return contact;
    }

    private Object toValue(Attribute attribute) {
        try {
            Object result = null;
            NamingEnumeration values = attribute.getAll();
            while (values.hasMore()) {
                Object value = values.next();
                if (result != null) {
                    // if there are >1 value, create a List and put them all there
                    if (result instanceof List) {
                        ((List)result).add(value);
                    } else {
                        List newList = new ArrayList(8);
                        newList.add(result);
                        newList.add(value);
                        result = newList;
                    }
                } else {
                    result = value;
                }
            }
            return result;
        }  catch (NamingException nameE) {
            logger.log(Level.WARNING, "Error handling multiple attribute entry", nameE);
            return null;
        }
    }

    private class LDAPContact extends HashMap<String, Object> implements Contact {

        private String ldapName;

        LDAPContact(String ldapName) {
            this.ldapName = ldapName;
        }

        public boolean equals(Object object) {
            if (object instanceof LDAPContact) {
                return ((LDAPContact)object).hashCode() == hashCode();
            } else {
                return false;
            }
        }

        public int hashCode() {
            return ldapName.hashCode();
        }

        public String getName() {
            return ldapName;
        }

        public String toString() {
            return "[name=" + ldapName + "," + super.toString() + "]";
        }
    }

    private class LDAPContactIterator implements Iterator<Map> {

        private NamingEnumeration values;

        private LDAPContactIterator(NamingEnumeration values) {
            this.values = values;
        }

        public void close() {
            try {
                if (values != null) {
                    values.close();
                }
            } catch (NamingException nameE) {
                throw new RuntimeException("Error closing iterator", nameE);
            }
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported");
        }

        public boolean hasNext() {
            if (values != null) {
                try {
                    return values.hasMore();
                } catch (SizeLimitExceededException sizeE) {
                    values = null;
                    return false;
                } catch (NamingException nameE) {
                    logger.log(Level.WARNING,
                            "Exception when enumarating through contacts",
                            nameE);
                    throw new RuntimeException(
                            "Exception when enumarating through contacts",
                            nameE);
                }

            } else {
                return false;
            }
        }

        public synchronized Map next() {
            try {
                if (values != null && hasNext()) {
                    SearchResult result = (SearchResult)values.next();
                    return createLDAPContact(result.getName() + baseDN, result.getAttributes());
                } else {
                    return null;
                }
            } catch (Exception e) {
                logger.log(Level.WARNING,
                        "Exception moving to next contact",
                        e);
                throw new RuntimeException(
                        "Exception moving to next contact",
                        e);
            }

        }
    }

}

