/*
 * JavaWorkSpace.java
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
 * Created on November 18, 2003, 8:22 PM
 */

package org.jdesktop.lg3d.apps.intel3d.workspace;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.MediaTracker;

import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.FocusListener;
import java.awt.event.ActionListener;

import org.jdesktop.lg3d.apps.intel3d.util.StaticAppLoader;

import org.jdesktop.lg3d.apps.intel3d.jini.ServiceRepresenter;

/** The class creates a workspace
 * @author asrivast
 * @since 11/18/2003
 * @version 1.1
 */
public class JavaWorkSpace extends JFrame 
                           implements ActionListener, FocusListener { 
    
    public static JavaWorkSpace _this;
    
    /** The object representing the Container where all applications
     * has to live
     */    
    private JDesktopPane deskTop;
    // private TBars tools;
    private JPopupMenu popup;
    private JMenuItem menuItem;
    
    /** Creates a new instance of JavaWebSpace */
    public JavaWorkSpace () {
        super("Java Web Space");
        _this = this;
        this.toFront();
        createDeskTop();
    }
    
    /** The method creates the parent desktop element. */    
    private void createDeskTop() {
        deskTop = new JDesktopPane();
        // -- Lets try to add an Image
        
        Image backgroundImage = Toolkit.getDefaultToolkit ().getImage("conf/icons/first.gif");
        MediaTracker mTracker = new MediaTracker(this);
        mTracker.addImage(backgroundImage, 0);
        while(!mTracker.checkID (0, true)) {}
        deskTop.prepareImage(backgroundImage, 200, 200, null);
        setContentPane(deskTop);
        deskTop.putClientProperty("JDesktopPane.dragMode", "outline");
        
        createApps (deskTop);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent wEvent) {
                System.exit(0);
            }
            public void windowLostFocus(WindowEvent wEvent) {
                // toFront ();
            }
            public void windowStateChanged(WindowEvent wEvent) {
                pack();
            }
        });
        
        CustomPopupMenuListener mListener = new CustomPopupMenuListener();
        addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent mEvent) {}
        });
        
       // toBack();
    }
                
    /** The method adds all the tools available. It also delegates
     * the request to <code>ServiceRepresenter </code> to add apps
     * which are dynamically available, like jini services.
     */    
    private void createApps (JComponent deskTop) {
        // -- Load all static apps
        StaticAppLoader saLoader = new StaticAppLoader(deskTop);
        // -- The static apps should appear before all dynamic ones
        // -- interface why the priority is kept higher than the 
        // -- dynamic loader
        saLoader.setPriority(Thread.NORM_PRIORITY);
        saLoader.start();
              
        // -- Ask Service representer to add any icons of dynamically
        // -- available services
        ServiceRepresenter jsClient = new ServiceRepresenter(deskTop);
        // jsClient.setDaemon(true);
        jsClient.setPriority(Thread.MIN_PRIORITY);
        jsClient.start();
    }
        
    
        
    /** The method is the entry point for the application to run
     * @param args String[] the command line arguments
     */
    public static void main (String[] args) {
        try {
            UIManager.setLookAndFeel(
                      "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch(Exception exp) {}
        
        JavaWorkSpace jws = new JavaWorkSpace();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jws.setSize(screenSize.width, screenSize.height);
        jws.setVisible(true);
    }
    
    /** The class implements ActionListener for all windowing events
     * @param e ActionEvent
     */    
    public void actionPerformed (java.awt.event.ActionEvent e) {
        toBack();
    }
    
    /** The class implements the FocusListener
     * @param e FocusEvent
     */    
    public void focusGained (java.awt.event.FocusEvent e) {
    }
    
    /** The class implements the FocusListener
     * @param e FocusEvent
     */    
    public void focusLost (java.awt.event.FocusEvent e) {
    }
    
    /** The class implements the PopupMenuListener for all Popup menus
     * on desktop
     */    
    class CustomPopupMenuListener extends MouseAdapter
                                  implements PopupMenuListener {
         
        /** Method to process all mouse related events
         * @param mEvent MouseEvent
         */                                      
         public void mouseReleased(MouseEvent mEvent) {
             if(mEvent.isPopupTrigger()) {
               popup.show(mEvent.getComponent(), mEvent.getX(), mEvent.getY());
             }
         }
         /** If the request for Popup Menu is cancelled
          * @param e PopupMenuEvent
          */         
         public void popupMenuCanceled (PopupMenuEvent e) {
         }
         
         /** When the Popup menu becomes invisible
          * @param e PopupMenuEvent
          */         
         public void popupMenuWillBecomeInvisible (PopupMenuEvent e) {
         }
                                      
         /** When the Popup menu becomes visible
          * @param e PopupMenuEvent
          */         
         public void popupMenuWillBecomeVisible (PopupMenuEvent e) {
         }
    }
                                      
}
