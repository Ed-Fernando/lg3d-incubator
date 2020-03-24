/*
 * Randomizer.java
 *
 * Created on May 27, 2005, 11:26 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.jdesktop.lg3d.apps.orgchart.ui.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.lg3d.apps.orgchart.framework.ServiceContext;
import org.jdesktop.lg3d.apps.orgchart.framework.contact.Contact;

/**
 *
 * @author cc144453
 */
public class Randomizer {
    
    private Logger logger = Logger.getLogger(getClass().getName());
    
    public static final int RESOLUTION = 1000; // 1 second
    public static final int DEFAULT_MAX_CONTACTS = 3;
    public static final int DEFAULT_MIN_INTERVAL = 1 * 60; // 1 minute
    public static final int DEFAULT_MAX_INTERVAL = 3 * 60; // 3 minutes
    
    private static String[] STATUSES = new String[] {
        Contact.PRESENCE_OPEN, Contact.PRESENCE_BUSY, Contact.PRESENCE_IDLE
    };
    
    private ServiceContext context;
    private RandomizeThread randomizeThread;
    private Future randomizeTask;
    
    public Randomizer(ServiceContext context) {
        this(context,
                DEFAULT_MAX_CONTACTS, DEFAULT_MIN_INTERVAL, DEFAULT_MAX_INTERVAL);
    }
    
    public Randomizer(ServiceContext context,
            int maxContacts, int minInterval, int maxInterval) {
        randomizeThread = new RandomizeThread(maxContacts, minInterval, maxInterval);
        randomizeTask = context.execute(randomizeThread);
    }
    
    public void finalize() {
        done();
    }
    
    public void add(Contact contact) {
        randomizeThread.add(contact);
    }
    
    public void remove(Contact contact) {
        randomizeThread.remove(contact);
    }
    
    public synchronized void done() {
        if (randomizeTask != null) {
            randomizeTask.cancel(true);
            randomizeTask = null;
            randomizeThread = null;
        }
    }
    
    private class RandomizeThread implements Runnable {
        private Random random = new Random(System.currentTimeMillis());
        private int maxContacts;
        private int minInterval;
        private int maxInterval;
        private CopyOnWriteArrayList contacts = new CopyOnWriteArrayList();
        
        private RandomizeThread(int maxContacts, int minInterval, int maxInterval) {
            assert(maxContacts > 0);
            assert(maxInterval > minInterval);
            this.maxContacts = maxContacts;
            this.minInterval = minInterval;
            this.maxInterval = maxInterval - minInterval;
        }
        
        private void add(Contact contact) {
            if (!contacts.contains(contact)) {
                contacts.add(contact);
                contact.put(Contact.ATTR_PRESENCE, STATUSES[random.nextInt(STATUSES.length)]);
            }
        }
        
        private void remove(Contact contact) {
            if (!contacts.contains(contact)) {
                contacts.remove(contact);
            }
        }
        
        public void run() {
            try {
                for (;;) {
                    // randomly pick one or more contact within a random interval and change status
                    int totalContacts = contacts.size();
                    if (totalContacts > 0) {
                        int numContacts = random.nextInt(maxContacts);
                        if (numContacts > totalContacts) {
                            numContacts = totalContacts;
                        } else if (numContacts == 0) {
                            numContacts = 1;
                        }
                        for (int i = 0; i < numContacts; ++i) {
                            int idx = random.nextInt(totalContacts);
                            Contact contact = (Contact)contacts.get(idx);
                            if (contact.get(Contact.ATTR_PRESENCE).equals(Contact.PRESENCE_OPEN)) {
                                contact.put(Contact.ATTR_PRESENCE, Contact.PRESENCE_BUSY);
                            } else {
                                contact.put(Contact.ATTR_PRESENCE, Contact.PRESENCE_OPEN);
                            }
                        }
                    }
                    long sleepTime = (random.nextInt(maxInterval) + minInterval) * RESOLUTION;
                    Thread.currentThread().sleep(sleepTime);
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "Randomizer interrupted", e);
            }
        }
        
    }
}
