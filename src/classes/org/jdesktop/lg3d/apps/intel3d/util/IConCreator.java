/*
 * IConCreator.java
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
 * Created on November 20, 2003, 5:58 PM
 */

package org.jdesktop.lg3d.apps.intel3d.util;

import java.awt.Container;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/** The class creates the Icons on a Container
 * @author asrivast
 * @since 11/20/2003
 * @version 1.0
 */
public class IConCreator {
    
    private static int iconCount = 0;
    private static int xCoord = 0;
    private static int yCoord = 0;
    
    /** Creates a new instance of IConCreator */
    public IConCreator() {}
    
    /** The method reads the iconName, the image file which has to
     * appear on the Button and the destination Container and builds
     * a JButton.
     * @param iconName String the name of the Icon
     * @param imageFile String The name of the image file to appear on top
     * of the Button
     * @param panel Container The Container on which the button has to
     * appear
     * @param iconIndex The number of icon which has to be built on a given
     * desktop. This is also used to dynamically generate
     * the location of the Button to appear
     * @return JComponent
     * @see javax.swing.JComponent
     * @see javax.swing.Icon
     *
     * @see javax.swing.ImageIcon
     * @see java.awt.Container
     */    
    public static JButton getComponent(String iconName, 
				       String imageFile, 
                                       Container panel, 
				       int iconIndex) {
        Icon icon = new ImageIcon(imageFile);
        final JButton jButton = new JButton(iconName, icon);
        jButton.setSize(50, 50);

        int x = xCoord % panel.getWidth(); 
        int yx = (iconCount * 50);
        int y = yCoord % panel.getHeight();
        jButton.setAlignmentX(x);
        jButton.setAlignmentY(y);
        jButton.setLocation(x, y);
        jButton.setAutoscrolls(true);
        jButton.setDoubleBuffered(true);
        jButton.setToolTipText(iconName);
        jButton.setEnabled(true);
        
        iconCount ++;
	yCoord = yCoord + 50;
        if(yx > panel.getHeight()) {
            yCoord = 0;
	    xCoord = xCoord + 50;
	    iconCount = 0;
        }
	if(yx > panel.getWidth()) {
	    xCoord = 0;
	    yCoord = 0;
	    iconCount = 0;
	}
        
        return jButton;
    }
 }
