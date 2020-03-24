/*
 * StaticAppLoader.java
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
 * Created on November 21, 2003, 12:07 PM
 */

package org.jdesktop.lg3d.apps.intel3d.util;

import java.beans.Beans;

import java.io.IOException;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.SwingUtilities;

import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.MissingResourceException;

/** The class reads jwsApps.properties file and adds all the tools
 * in that properties file to the parent Window container.
 * @author asrivast
 * @since 11/21/2003
 * @version 1.0
 */
public class StaticAppLoader extends Thread {
    /** The Container where all the apps are to be added */    
    private Container deskTop;
    /** The file which contains the list of all jws applications */    
    private PropertyResourceBundle prBundle, dtProps = null;
    
    /** Creates a new instance of StaticAppLoader
     * @param deskTop Container The parent Container object where all the
     * applications are to be added
     */
    public StaticAppLoader (Container deskTop) {
        this.deskTop = deskTop;
        try {
            prBundle = (PropertyResourceBundle)
                        PropertyResourceBundle.getBundle("jwsApps");
            dtProps = (PropertyResourceBundle)
                       PropertyResourceBundle.getBundle("dt");
        } catch(MissingResourceException mrExp) {
            // -- not there? do nothing
        }
    }
    
    /** The class extends Thread and run() has to be implemented. The
     * method gets all the classes defined in jwsApps resource bundle,
     * makes them visible, adds MouseListener and finally adds them
     * to the parent container. The classes (apps) defined in the properties
     * file is only added if its an instanceof java.awt.Container object.
     */    
    public void run() {
        // -- Add the Toolbar
        ToolBar toolBar = new ToolBar((JDesktopPane)deskTop);
        toolBar.setVisible (true);  
        deskTop.add(toolBar);
        
        // -- Load all the applications
        if(prBundle != null) {
            int index = 1;
            Enumeration enumur = prBundle.getKeys ();
            while(enumur.hasMoreElements()) {
                try {
                    final String className = 
                             (String)prBundle.getString(String.valueOf(index));
                    enumur.nextElement(); 
                    Object object = (Object)Beans.instantiate(null, className);
                    if(object instanceof java.awt.Container) {
                        final Container container = (Container)object;
                        // -- Assuming all the classes will have a package name
                        String iconName = className.substring(
					  className.lastIndexOf (".") + 1);
                        // -- Add the corresponding icon
                        final JButton jComp = IConCreator.getComponent(iconName,
                                              dtProps.getString (iconName), 
                                              deskTop, index);
                        
                        jComp.addMouseListener(new MouseListener() {
                            public void mouseClicked(MouseEvent mEvent) {
                                try {
                                    // -- Recreate a brand new Object. If the 
				    // -- same object is added twice it will 
				    // -- throw IllegalArgumentException:
                                    // -- illegal component position
                                    Object object = 
			                  (Object)Beans.instantiate (null, 
				     			             className);
                                    addContainer((Container)object, deskTop);
                                } catch(IOException ioExp) {
                                    // -- do nothing
                                } catch(ClassNotFoundException cnExp) {
                                    // -- do nothing
                                }
                            }
                            public void mousePressed(MouseEvent mEvent) {}
                            public void mouseEntered(MouseEvent mEvent) {}
                            public void mouseExited(MouseEvent mEvent) {}
                            public void mouseReleased(MouseEvent mEvent) {}
                        });
                        
                        jComp.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent aEvent) {
                            }
                        });
                        jComp.addMouseMotionListener(new MouseMotionAdapter() {
                            public void mouseDragged(MouseEvent e) {
                                jComp.setLocation(
				SwingUtilities.convertMouseEvent(jComp, e, 
							   deskTop).getPoint());
                            }
                        });
                        jComp.setVisible(true);
                        deskTop.add(jComp);
                    }
                } catch(IOException ioExp) {
                    ioExp.printStackTrace ();
                    // -- do nothing
                } catch(ClassNotFoundException cnfExp) {
                    cnfExp.printStackTrace();
                    // -- do nothing
                } catch(Exception exp) {
                    exp.printStackTrace ();
                }
                index++;
            }
        }
    }
    
    private void addContainer(Container container, Container deskTop) {
        container.setVisible(true);
        deskTop.add(container);
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
