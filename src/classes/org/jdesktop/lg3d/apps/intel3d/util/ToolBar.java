/*
 * ToolBar.java
 *
 * Created on November 21, 2003, 3:30 PM
 */

package org.jdesktop.lg3d.apps.intel3d.util;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.FileDialog;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel; 
import javax.swing.JToolBar;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JDesktopPane;
import javax.swing.JToggleButton;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import java.util.PropertyResourceBundle;

import org.jdesktop.lg3d.apps.intel3d.util.MemoryMonitor;

/**
 *
 * @author  asrivast
 */
public class ToolBar extends JInternalFrame  
                     implements ActionListener, InternalFrameListener {
    
    
    private JPanel mainPanel; 
    private JToolBar toolBar;
    private JMenuItem menuItem;
    private FileDialog fileDialog;
    private JInternalFrame[] allFrames;
    
    private static boolean trigger = true;
    private static boolean firstClicked = true;
    private static boolean secondClicked = true;
    
    private PropertyResourceBundle dtProps = 
            (PropertyResourceBundle)PropertyResourceBundle.getBundle("dt");

    /** Creates a new instance of ToolBar */
    public ToolBar(JDesktopPane deskTop) {
        super("JWS Toolbar", true, false, false, true);
        final JDesktopPane desk = deskTop;
        
    
        Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        setSize (screenSize.width, 120);
        setLocation(1, (screenSize.height - 200));
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
                            // new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        // toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));
        
        JToggleButton firstWS = 
                      new JToggleButton(dtProps.getString ("firstWSText"),
                      new ImageIcon(dtProps.getString("firstWSImage")), true);
        JToggleButton secondWS = 
                      new JToggleButton(dtProps.getString("secondWSText"), 
                      new ImageIcon(dtProps.getString("secondWSImage")));
        JToggleButton exit = 
                      new JToggleButton(dtProps.getString("exitWSText"),
                      new ImageIcon(dtProps.getString("exitWSImage")));

	// -- Group them in one group
	ButtonGroup buttonGroup = new ButtonGroup();
	buttonGroup.add(firstWS);
	buttonGroup.add(secondWS);
	buttonGroup.add(exit);

        firstWS.setFocusPainted (true);
        firstWS.setBackground(Color.darkGray);
        firstWS.setBorderPainted (true);
        firstWS.setEnabled (true);
        firstWS.doClick ();
        firstWS.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent aEvent) {
                firstClicked = true;
                allFrames = desk.getAllFrames ();
                // -- Don't know what it is doing...
                if(firstClicked && secondClicked && !trigger) {
                    for (int i = 0; i < allFrames.length; i++) {
                        if(allFrames[i].isVisible ()) {
                            if(!allFrames[i].getClass ().getName().equals (
                                                "com.sun.jws.util.ToolBar")) {
                                allFrames[i].setVisible(false);
                            }
                        } else {
                            allFrames[i].setVisible(true);
                        }
                    }
                }
                secondClicked = false;
                trigger = false;
            }
        });
        
        secondWS.setFocusPainted (true);
        secondWS.setBorderPainted (true);
        secondWS.setBackground (Color.darkGray);
        secondWS.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent aEvent) {
                secondClicked = true;
                allFrames = desk.getAllFrames ();
                // -- Don't know what it is doing...
                if(firstClicked && secondClicked) {
                    for (int i = 0; i < allFrames.length; i++) {
                        if(allFrames[i].isVisible ()) {
                            if(!allFrames[i].getClass ().getName().equals (
                                               "com.sun.jws.util.ToolBar")) { 
                                allFrames[i].setVisible (false);
                            }
                        } else {
                            allFrames[i].setVisible(true);
                        }
                    }
                }
                firstClicked = false;
            }
        });
        
        exit.setBackground (Color.darkGray);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent aEvent) {
                System.exit(0);
            }
        });
        
        JPanel memUsage = new MemoryMonitor();
                
        firstWS.setVisible(true);
        secondWS.setVisible(true);
        exit.setVisible(true);
        memUsage.setVisible(true);
                
        mainPanel.add(firstWS);
        mainPanel.add(secondWS);
        mainPanel.add(exit);
        
        mainPanel.setVisible(true);
        mainPanel.setBackground (Color.BLACK);
        toolBar.add(mainPanel);
        toolBar.add(memUsage);
        toolBar.setBackground (Color.darkGray);
        toolBar.setVisible(true);
        this.getContentPane().add(toolBar);
        validate ();
    }
    
    public void actionPerformed (ActionEvent e) {}
    
    public void internalFrameActivated (InternalFrameEvent e) {}
    
    public void internalFrameClosed (InternalFrameEvent e) {}
    
    public void internalFrameClosing (InternalFrameEvent e) {}
    
    public void internalFrameDeactivated (InternalFrameEvent e) {}
    
    public void internalFrameDeiconified (InternalFrameEvent e) {}
    
    public void internalFrameIconified (InternalFrameEvent e) {}
    
    public void internalFrameOpened (InternalFrameEvent e) {}
    
}
