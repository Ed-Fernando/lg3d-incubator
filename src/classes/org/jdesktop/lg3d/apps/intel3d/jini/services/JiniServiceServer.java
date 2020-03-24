/*
 * JiniServiceServer.java
 *
 * Created on November 24, 2003, 2:25 PM
 */

package org.jdesktop.lg3d.apps.intel3d.jini.services;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

import net.jini.lease.LeaseListener;
import net.jini.lease.LeaseRenewalEvent;
import net.jini.lease.LeaseRenewalManager;

import net.jini.core.lease.Lease;
import net.jini.core.lookup.ServiceID;
import net.jini.core.lookup.ServiceItem;
import net.jini.core.lookup.ServiceRegistrar;
import net.jini.core.lookup.ServiceRegistration;

import net.jini.discovery.DiscoveryEvent;
import net.jini.discovery.LookupDiscovery;
import net.jini.discovery.DiscoveryListener;

import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;

/**
 *
 * @author  asrivast
 */
public class JiniServiceServer implements DiscoveryListener, LeaseListener {
   
    protected LeaseRenewalManager lManager = new LeaseRenewalManager();
    protected ServiceID serviceID = null;
    
    /** Creates a new instance of JiniServiceServer */
    public JiniServiceServer () {
        DataInput dIn = null;
        try {
            dIn = new DataInputStream(new FileInputStream("fileClassifier.id"));
            serviceID = new ServiceID(dIn);
        } catch(Exception exp) {
            // -- do nothing
        }
        
        System.setSecurityManager (new RMISecurityManager());
        
        LookupDiscovery discover = null;
        try {
             discover = new LookupDiscovery(LookupDiscovery.ALL_GROUPS);
        } catch(Exception exp) {
            System.out.println("Discovery failed " + exp.toString ());
            System.exit(1);
        }
        
        discover.addDiscoveryListener (this);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) {
        new JiniServiceServer();
        
        // -- keep the application alive
        Object keepAlive = new Object();
        synchronized(keepAlive) {
            try {
                keepAlive.wait ();
            } catch(InterruptedException iExp) {
                iExp.printStackTrace ();
            }
        }
    }
    
    public void discarded (DiscoveryEvent discoveryEvent) {
        
    }
    
    public void discovered (DiscoveryEvent discoveryEvent) {
        ServiceRegistrar[] registrars = discoveryEvent.getRegistrars ();
        for (int i = 0; i < registrars.length; i++) {
            ServiceRegistrar registrar = registrars[i];
            ServiceItem item = new ServiceItem(serviceID, 
                                               new JiniServiceImpl(),
                                               null);
            ServiceRegistration reg = null;
            try {
                System.out.println("Host : " + 
				    registrar.getLocator ().getHost ());
                System.out.println("Port : " + 
				    registrar.getLocator ().getPort ());
                reg = registrar.register (item, Lease.FOREVER);
            } catch(RemoteException rExp) {
                System.err.println("Registration Exception : " + 
				    rExp.toString());
                continue;
            }
            System.out.println("Service registered with id : " + 
				reg.getServiceID ());
            
            lManager.renewUntil(reg.getLease(), Lease.FOREVER, this);
            
            if(serviceID == null) {
                serviceID = reg.getServiceID ();
                DataOutputStream dOut = null;
                try {
                    dOut = new DataOutputStream(
                           new FileOutputStream("fileClassifier.id"));
                    serviceID.writeBytes (dOut);
                    dOut.flush ();
                } catch(Exception exp) {
                    // -- do nothing
                }
            }
        }
    }
    
    public void notify (LeaseRenewalEvent leaseRenewalEvent) {
        System.out.println("Lease expired : " + leaseRenewalEvent.toString());
    }
    
}
