/*
 * ContactChannel.java
 *
 * Created on June 23, 2005, 12:08 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.framework.contact;

import java.awt.Image;
import java.net.URL;
import java.util.Collection;
import java.util.EventObject;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.lg3d.apps.orgchart.framework.BasicEventChannel;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceContext;
import org.jdesktop.lg3d.apps.orgchart.framework.Util;

/**
 *
 * @author cc144453
 */
public class ContactChannel extends BasicEventChannel implements Contact {

    private ServiceContext context;
    private Map contact;
    private String name;

    public ContactChannel(ServiceContext context, Map contact) {
        this.context = context;
        this.contact = contact;
        this.name = (String)contact.get(Contact.ATTR_FIRSTNAME) + " " +
                (String)contact.get(Contact.ATTR_LASTNAME);
    }

    public void init() {
        // do this in a separate thread
        final ContactChannel channel = this;
        final Logger logger = context.getLogger();
        context.execute(
                new Runnable() {
            public void run() {
                // setup the photo image
                try {
                    Object photo = contact.get(Contact.ATTR_PHOTO);
                    if (photo == null) {
                        // try using the URL if photo is not setup
                        String photoURLStr = (String)contact.get(Contact.ATTR_PHOTOURL);
                        if (photoURLStr != null) {
                            photo = Util.imageFromURL(new URL(photoURLStr));
                            logger.fine("Loading photo contact=" + name +
                                    " url=" + photoURLStr);
                            put(Contact.ATTR_PHOTO, photo);
                        }
                    } else if (!(photo instanceof Image)) {
                        byte[] bytes = (byte[])photo;
                        logger.fine("Loading photo bytes contact=" + name);
                        put(Contact.ATTR_PHOTO, Util.imageFromBytes(bytes));
                    }
                } catch (Exception e) {
                    logger.log(Level.WARNING,
                            "Exception loading photo contact=" + name, e);
                }
            }
        });
    }

    public Map getContact() {
        return contact;
    }

    public String getName() {
        return name;
    }

    public int size() {
        return contact.size();
    }

    public boolean isEmpty() {
        return contact.isEmpty();
    }

    public boolean containsKey(Object key) {
        return contact.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return contact.containsValue(value);
    }

    public Object get(Object key) {
        return contact.get(key);
    }

    public Object put(String key, Object value) {
        Object oldValue = get(key);
        if (key.equals(ATTR_PHOTO)) {
        }
        if (oldValue != value) {
            contact.put(key, value);
            context.post(this, new ContactChangeEvent(key, oldValue));
        }
        return value;
    }

    public Object remove(Object obj) {
        return contact.remove(obj);
    }

    public void putAll(Map map) {
        contact.putAll(map);
    }

    public void clear() {
        contact.clear();
    }

    public Set keySet() {
        return contact.keySet();
    }

    public Collection values() {
        return contact.values();
    }

    public Set entrySet() {
        return contact.entrySet();
    }

    public boolean equals(Object obj) {
        return contact.equals(obj);
    }

    public int hashCode() {
        return contact.hashCode();
    }

    public String toString() {
        return "[ContactChanel:" + contact.toString() + "]";
    }

    public class ContactChangeEvent extends EventObject {
        private String attribute;
        private Object oldValue;
        private ContactChangeEvent(String attribute, Object oldValue) {
            super(contact);
            this.attribute = attribute;
            this.oldValue = oldValue;
        }
        public String getAttribute() {
            return attribute;
        }
        public Object getOldValue() {
            return oldValue;
        }
    }
}
