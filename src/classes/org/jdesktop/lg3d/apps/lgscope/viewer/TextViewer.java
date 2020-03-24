/*
 * LG3D Incubator Project - LgScope
 *
 * $RCSfile: TextViewer.java,v $
 *
 * Copyright (c) 2006, INoX Software Development Group, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.1 $
 * $Date: 2006-04-17 09:56:21 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgscope.viewer;


// import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.vecmath.Vector3f;

//import org.jdesktop.lg3d.utils.eventaction.Component3DMover;
import org.jdesktop.lg3d.apps.lgscope.util.LgUtil;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.SwingNode;


/**
 * Text viewer is defined. 
 */
public class TextViewer extends LgViewer {

    public TextViewer(File file) {
        super(file);
    }
    
    protected Component3D createViewer(File file) {
        
        try {
            
            StringBuffer sb = new StringBuffer();
            InputStreamReader input = new InputStreamReader(new FileInputStream(file));
            char[] buf = new char[256];
            int reads;
            while ((reads = input.read(buf)) != -1) {
                sb.append(buf, 0, reads);
            }
            input.close();
                    
            JPanel jp = new JPanel();
            JTextArea text = new JTextArea(10, 30);
            text.setText(sb.toString());            
            
            JScrollPane jsp = new JScrollPane(text);
            jp.add(jsp);
            
            SwingNode sn = new SwingNode();
            sn.setPanel(jp);
            
            Component3D c = new Component3D();
            c.addChild(sn);
            // Dimension d = text.getSize();
            // Because the size changes every time why when it is this, it is temporarily assumed that it fixes.           
            //c.setPreferredSize(new Vector3f(LgUtil.pixel2meter(d.width), LgUtil.pixel2meter(d.height), 0.0f));
            c.setPreferredSize(new Vector3f(LgUtil.pixel2meter(300), LgUtil.pixel2meter(160), 0.0f));
            
            // sn.addListener(new Component3DMover(sn));           
            
            return c;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }        
    }
}
