/*
 * Contact.java
 *
 * Created on June 20, 2005, 11:33 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.framework.contact;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author cc144453
 */
public interface Contact extends Map<String, Object>, Serializable {
    static final String ATTR_UID = "uid";
    static final String ATTR_FIRSTNAME = "givenName";
    static final String ATTR_LASTNAME = "sn";
    static final String ATTR_MANAGER = "manager";
    static final String ATTR_EMPLOYEENUMBER = "employeeNumber";
    static final String ATTR_LOCATION = "l";
    static final String ATTR_PHOTO = "photo";
    static final String ATTR_PHOTOURL = "photoURL";
    static final String ATTR_CALENDAR = "calendar";
    static final String ATTR_CALENDAR_BUSY = "calendarBusy";
    static final String ATTR_EMAIL = "email";
    static final String ATTR_PAGER = "pager";
    static final String ATTR_PAGERMAIL = "pagermail";
    static final String ATTR_PHONE = "telephoneNumber";
    static final String ATTR_SCREENNAME = "screenName";
    static final String ATTR_URL = "labeledURI";
    static final String ATTR_PRESENCE = "presence";

    static final String PRESENCE_OPEN = "open";
    static final String PRESENCE_BUSY = "busy";
    static final String PRESENCE_IDLE = "idle";
        
}
