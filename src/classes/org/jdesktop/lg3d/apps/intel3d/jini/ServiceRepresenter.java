/*
 * ServiceRepresenter.java
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
 * Created on November 20, 2003, 8:03 PM
 */

package org.jdesktop.lg3d.apps.intel3d.jini;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.awt.Container;

import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetContext;
import java.awt.dnd.DropTargetListener;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;

import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionAdapter;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import java.rmi.RemoteException;

import net.jini.core.lookup.ServiceTemplate;
import net.jini.core.lookup.ServiceRegistrar;

import org.jdesktop.lg3d.apps.intel3d.jini.ServiceFinder;
import org.jdesktop.lg3d.apps.intel3d.jini.services.IService;

import org.jdesktop.lg3d.apps.intel3d.util.IConCreator;

import java.util.PropertyResourceBundle;

/** The class acts as a interface to all the jini services and the
 * GUI application. The class uses IConCreator to build icons on
 * a desktop for each jini service it found.
 * @author asrivast
 * @since 11/20/2003
 * @see com.sun.jws.util.IConCreator
 */
public class ServiceRepresenter extends Thread {
    
    /** The Container where the services are to be presented in form
     * of icons
     */    
    private Container deskTop;

    private IService iService = null;

    private PropertyResourceBundle dtProps = 
       (PropertyResourceBundle)PropertyResourceBundle.getBundle("dt");
    
    /** Creates a new instance of ServiceRepresenter
     * @param deskTop Container the parent where all the icons have to appear
     * @see java.awt.Container
     */
    public ServiceRepresenter (Container deskTop) {
        this.deskTop = deskTop;
    }
    
    /** The class builds all the icons in background and thats the
     * reason a Thread infrastructure has been provided. The method
     * reads all the ServiceRegistrar available and adds an icon
     * representing each of one of these.
     * In real application this has to be changed to each <i>jini service </i>
     * instead of proxy.
     */    
     public void run() {
         // -- Add a icon
	 ServiceFinder service = null;

         String unicastServer = dtProps.getString("jiniServer");
	 if(unicastServer != null || !unicastServer.trim().equals("")) {
	    service = new ServiceFinder(unicastServer);
	 } else {
	    // -- They should be same constructor. A null unicastServer
	    // -- should put the mode in Multicast lookup. Lets use 
	    // -- this by the time that change is done
            service = new ServiceFinder();
         }

         final Container pan = deskTop;
         ServiceRegistrar[] registrars = service.getRegistrars ();
	 ServiceRegistrar registrar;
         Class[] services = 
		 new Class[] { org.jdesktop.lg3d.apps.intel3d.jini.services.IService.class };
	 ServiceTemplate sTemplate = 
			 new ServiceTemplate(null, services, null);
           
         if(registrars != null && (registrars.length != 0)) {
             System.out.println("Found Registrars :" + registrars.length);
             for (int i = 0; i < registrars.length; i++) {
                 final JButton jComp;
	         try {
		    registrar = registrars[i]; 
                    System.out.println("Finding in : " + 
					registrar.getLocator ().getHost());
		    iService = (IService)registrar.lookup(sTemplate);
                    System.out.println("Found a IService provider");
	         } catch(RemoteException rExp) {
                     rExp.printStackTrace();
		    continue;
                 }

		 if(iService == null) {
                     System.out.println("IService is null");
		    continue;
	         } else {
                     System.out.println("Got the IService interface");
		    // -- Create icons only for desired interface
                    jComp = IConCreator.getComponent(
                                   // -- sTemplate.serviceID.toString (),
                                   "jini service found", 
                                   "conf/icons/genieFound.jpg", deskTop, i);
                    try {
                        jComp.setToolTipText (iService.getDescription());
                    } catch(RemoteException rExp) {
                        // -- can't find? do nothing
                    }
                    
                    jComp.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent aEvent) {
                            System.out.println("Some event : " + 
						aEvent.getActionCommand());
	                    try {
			       iService.doService("Jini service found");
			    } catch(java.rmi.RemoteException rExp) {
			       rExp.printStackTrace();
			    }
                        }
                    });
                    jComp.addMouseMotionListener(new MouseMotionAdapter() {
                        public void mouseDragged(MouseEvent e) {
                            jComp.setLocation(
			    SwingUtilities.convertMouseEvent(jComp, 
							     e, 
							     pan).getPoint());
                        }
                    });
                    jComp.setVisible(true);
                    setDropTarget(jComp, iService);
                    deskTop.add(jComp);
	         }
             }
         } else {
             // -- In production it is not needed
             JButton jComp = IConCreator.getComponent("No Service found", 
                                              "conf/icons/notFound.gif",
                                               deskTop, 0);
             jComp.setVisible(true);
             deskTop.add(jComp);
         }
         
     }
     
     private void setDropTarget(JComponent jComp, IService  iService) {
         jComp.setDropTarget(new DropTarget(jComp, 
                             new DropTargetConfigurator(iService)));
     }
     
     private class DropTargetConfigurator implements DropTargetListener {
         
         private IService iService = null;
         
         public DropTargetConfigurator(IService iService) {
             this.iService = iService;
         }
         
         public void dragEnter (DropTargetDragEvent dtde) {
             dtde.acceptDrag(1);
         }
         
         public void dragExit (DropTargetEvent dte) {
         }
         
         public void dragOver (DropTargetDragEvent dtde) {
             dtde.acceptDrag (1);
         }
         
         public void drop (DropTargetDropEvent dtde) {
            DropTargetContext dtContext = dtde.getDropTargetContext();
            InputStreamReader iReader = null;
            StringWriter sWriter = new StringWriter();
            BufferedReader bReader = null;
            boolean isDropComplete = false;
            boolean isEOF = false;
            
            if((dtde.getSourceActions () & 0x1) != 0) {
                dtde.acceptDrop (1);
            } else {
                dtde.rejectDrop ();
                return;
            }
            DataFlavor dataFlavors[] = dtde.getCurrentDataFlavors ();
            InputStream iStream = null;
            DataFlavor dataFlavor = null;
            Transferable transferable = dtde.getTransferable ();
            for(int i = 0; i < dataFlavors.length; i++) {
                isEOF = false;
                try {
                    iStream = 
                      (InputStream)transferable.getTransferData(dataFlavors[i]);
                } catch(IOException ioExp) {
                    dtde.dropComplete (false);
                    return;
                } catch(UnsupportedFlavorException usfExp) {
                    dtde.dropComplete(false);
                    return;
                }
                
                if(iStream != null) {
                    try {
                        iReader = new InputStreamReader(iStream);
                        bReader = new BufferedReader(iReader);
                        int j = -1;
                        while(!isEOF) {
                            j = bReader.read ();
                            if(j != -1) {
                                sWriter.write (j);
                            } else {
                                isEOF = true;
                            }
                        }
                        // -- Call the service 
                        ((IService)iService).doService(sWriter.toString ());
                        isDropComplete = true;
                    } catch(IOException ioExp) {
                        // -- do nothing
                    }
                } else {
                    isDropComplete = false;
                }
            }
            dtde.dropComplete (isDropComplete);  
         }
         
         public void dropActionChanged (DropTargetDragEvent dtde) {
         }
         
     }
}
