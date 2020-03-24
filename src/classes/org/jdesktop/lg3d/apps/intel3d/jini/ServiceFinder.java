/*
 * ServiceFinder.java
 *
 * Copyright (c) 2003 Sun Microsystems, Inc.
 * 901 San Antonio Road, Palo Alto, California, 94303, U.S.A.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Sun
 * Microsystems, Inc. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 * 
 * Created on November 20, 2003, 4:31 PM
 */

package org.jdesktop.lg3d.apps.intel3d.jini;

import java.io.IOException;

import net.jini.core.lookup.ServiceItem;
import net.jini.core.lookup.ServiceTemplate;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceRegistration;

import net.jini.core.discovery.LookupLocator;

import net.jini.discovery.DiscoveryEvent;
import net.jini.discovery.LookupDiscovery;
import net.jini.discovery.DiscoveryListener;

import java.rmi.RMISecurityManager;

import java.net.MalformedURLException;

/** The class creates a client interface to all the jini services
 * running. The class does the lookup and prepares a list of all
 * <code> ServiceRegistrar </code> availaible in the Lookup service.
 * @author asrivast
 * @since 11/20/2003
 * @version 1.0
 * @see net.jini.core.lookup.ServiceRegistrar
 */
public class ServiceFinder implements DiscoveryListener {
    
    /** The object to use a specific lookup service */    
    private LookupLocator lookup = null;
    /** The object used to use Multicast discovery */    
    private LookupDiscovery discover = null;
    // -- For all the service registrars 
    /** The registrar object representing the services */    
    private ServiceRegistrar[] registrars = null;
    private ServiceRegistrar registrar = null;
    
    static {
        System.setSecurityManager (new RMISecurityManager());
    }
    
    /** Creates a new instance of ServiceFinder. The constructor invokes
     * the <code>ServiceFinder(String)</code> constructor with null
     * value so that a list of all the available ServiceRegistrars in
     * the group can be prepared.
     */
    ServiceFinder () {
        this(null);
    }
    
    /** If the user already knows where to look, provide a valid
     * location. If null, it looks for all the groups
     * @param lookupLocator String the lookup locator like 
     *        <i>jini://skyelou.central"</i>
     */
    ServiceFinder(String lookupLocator) {
        setRegistrar(lookupLocator);
        /*
         * We don't need this as its a GUI application
         * // -- lets wait for ever 
         
        Object keepAlive = new Object();
        synchronized(keepAlive) {
            try   {
                 keepAlive.wait();
             } catch(InterruptedException iExp) {
                 // -- do nothing
             }
         }
         */
    }
    
    /** The method does a unicast or multicast lookup for the
     * ServiceRegistrars depending on if lookupLocator is provided
     * or not. The method prepares an array (with single element if
     * lookup locator is already know) of ServiceRegistrar objects
     * @param lookupLocator String. If lookupLocator is null a multicast lookup
     * is done otherwise unicast.
     */    
    private void setRegistrar(String lookupLocator) {
        try {
        if(lookupLocator == null || lookupLocator.trim().equals("")) {
            try {
                discover = new LookupDiscovery(LookupDiscovery.ALL_GROUPS); 
                System.out.println("Did a lookup discovery");
                registrars = discover.getRegistrars ();
            } catch(IOException ioExp) {
                ioExp.printStackTrace ();
            }
            discover.addDiscoveryListener(this); 
            System.out.println("Added a discovery listener");
            try {
                // -- May be in 10 secs it will find one
                Thread.sleep (10000);
            } catch(InterruptedException iExp) {
                iExp.printStackTrace();
            }
        } else {
            try {
                lookup = new LookupLocator(lookupLocator);
            } catch(MalformedURLException mExp) {
                mExp.printStackTrace();
            } 
            
            try {
                registrar = (ServiceRegistrar)lookup.getRegistrar();
                System.out.println("Host : " + 
				    registrar.getLocator().getHost());
                System.out.println("Port : " + 
				    registrar.getLocator().getPort());
                registrars = new ServiceRegistrar[1];
                registrars[0] = registrar;
                
            } catch(IOException ioExp) {
                ioExp.printStackTrace();
            } catch(ClassNotFoundException cnfExp) {
                cnfExp.printStackTrace();
            }
        }
      } catch(Exception exp) {
          exp.printStackTrace();
      }
   }
        
    /** The method returns an array of all ServiceRegistrar available
     * @return An Array of ServiceRegistrar
     * @see net.jini.core.lookup.ServiceRegistrar
     */    
    protected ServiceRegistrar[] getRegistrars() {
        return registrars;
    }
    
    /** As the class implements DiscoveryListener interface it implements
     * the discarded() method.
     * @param discoveryEvent DiscoveryEvent
     */    
    public void discarded(DiscoveryEvent discoveryEvent) {
        // Don't do anything
    }    
        
    /** The method retrieves an array of Serviceregistrars available from
     * the DiscoveryEvent
     * @param discoveryEvent DiscoveryEvent
     * @see net.jini.core.lookup.ServiceRegistrar
     * @see net.jini.discovery.DiscoveryEvent
     */    
    public void discovered(DiscoveryEvent discoveryEvent) {
      try {
        registrars = discoveryEvent.getRegistrars ();
      } catch(Exception exp) {
	System.out.println("Caught the exception");
      }
        System.out.println("Got the multicast registration");
    }
}
