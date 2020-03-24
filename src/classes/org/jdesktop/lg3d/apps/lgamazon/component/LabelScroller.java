/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: LabelScroller.java,v $
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
 * $Date: 2007-03-09 09:37:38 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.component;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import org.jdesktop.lg3d.utils.action.ActionBooleanInt;
import org.jdesktop.lg3d.utils.action.ActionInt;
import org.jdesktop.lg3d.wg.event.LgEventSource;

public class LabelScroller implements ActionBooleanInt, ActionInt {
    
    private int scrollX = 0;
    private int scrollY = 0;

    private LabelComponent label;
    
    
    private Thread scrollThread = null;
    
    
    private int[] baseTriangleX;
    private int[] baseTriangleY;
    
    private int[] workTriangleX = new int[3];
    private int[] workTriangleY = new int[3];;
    
    
    public LabelScroller(LabelComponent label) {
        this.label = label;
    }
    
    
    public void performAction(
    LgEventSource source, boolean state, int value) {                        
                                
        if (!state) {
            return;
        }
        
        processKey(value);
    }      
    
    
    private void processKey(int key) {
        
        if (scrollThread != null) {
            return;
        }

        scrollThread = new ScrollThread(key);
        scrollThread.start();        
    }



    public void performAction(LgEventSource source, int value) {
        processKey((value > 0) ? KeyEvent.VK_DOWN : KeyEvent.VK_UP);
    }
    
    
            
    class ScrollThread extends Thread {
        
        int key;
        
        ScrollThread(int key) {
            this.key = key;
        }
                
        public void run() {                        
            
            switch (key) {
            
                case KeyEvent.VK_UP:  
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_LEFT:
                             
                    if (scroll(key)) {
                        label.draw();
                    }
                    break;
                                
                default:                
                    break;
            }
            
            scrollThread = null;
        }
    }
    
    
    
    
    
    public void drawIndicator(Graphics2D g) {
        
        if (baseTriangleX == null) {
            
            int h = (int) (15 * (1.0f / label.getScale()));
            
            baseTriangleX = new int[3];
            baseTriangleY = new int[3];
            
            baseTriangleX[0] = 0;
            baseTriangleY[0] = 0;            
            baseTriangleX[1] = h;
            baseTriangleY[1] = 0;
            baseTriangleX[2] = h / 2;
            baseTriangleY[2] = (int) (h * 1.4f);            
        }
        
        
        int x = label.viewArea.x + label.viewArea.width - baseTriangleX[1];        
        
        g.setColor(new Color(0, 0, 0, 128));
        
        if (label.viewPos.y > 0) {
            g.fillPolygon(
                transrate(baseTriangleX, workTriangleX, x, false),
                transrate(baseTriangleY, workTriangleY, label.viewArea.y + baseTriangleY[2], true),
                3);
        }
        
        if (label.viewPos.y + label.viewArea.height < label.getDrawHeight()) {
            
            int y = label.viewArea.y + label.viewArea.height - baseTriangleY[2];
            
            g.fillPolygon(
                transrate(baseTriangleX, workTriangleX, x, false),
                transrate(baseTriangleY, workTriangleY, y, false),
                3);
        }
    }
    
    
    private int[] transrate(int[] src, int[] dst, int d, boolean isUpper) {
        
        for (int i = 0; i < src.length; i++) {
                        
            if (isUpper && i == 2) {
                dst[i] = -src[i] + d; 
            }
            else {
                dst[i] = src[i] + d;
            }
        }
        
        return dst;
    }
    
    
    
    /**
     * 
     * @param key
     * @return 
     */
    private boolean scroll(int key) {
        
        scrollY = label.viewArea.height / 4;    
        
        if (key == KeyEvent.VK_UP) {
            
            if (label.viewPos.y == 0) {
                return false;
            }
            
            if (label.viewPos.y - scrollY < 0) {
                label.viewPos.y = 0;
            }
            else {
                label.viewPos.y -= scrollY;
            }
            
            return true;
            
        }
        else if (key == KeyEvent.VK_DOWN) {
                                                
            int left = label.getDrawHeight() - label.viewPos.y;
            
            if (left > label.viewArea.height) {
                
                label.viewPos.y += scrollY;
                
                if (label.viewPos.y + label.viewArea.height > label.getDrawHeight()) {
                    label.viewPos.y = label.getDrawHeight() - label.viewArea.height;
                }
                
                return true;                    
            }
        }
        
        
        return false;
        
//        if (multiple) {
//            if (key == KeyEvent.VK_UP) {  
//                
//                
//                if (viewY == 0) {
//                    return false;
//                }
//                
//                if (viewY - scrollY < 0) {
//                    viewY = 0;
//                }
//                else {
//                    viewY -= scrollY;
//                }
//                
//                return true;
//                
//            }
//            else if (key == KeyEvent.VK_DOWN) {
//                                                    
//                int left = drawHeight - viewY;
//                int area = imageHeight - (topMargin + bottomMargin);
//                if (left > area) {
//                    
//                    viewY += scrollY;
//                    
//                    
//                    if (viewY + area > drawHeight) {
//                        viewY = drawHeight - area;
//                    }
//                    
//                    return true;                    
//                }
//            }
//        }
//        else {
//            
//            if (key == KeyEvent.VK_LEFT) {
//                
//                if (viewX == 0) {
//                    return false;
//                }
//                
//                if (viewX - scrollX < 0) {
//                    viewX = 0;
//                }
//                else {
//                    viewX -= scrollX;
//                }
//                
//                return true;
//            }
//            else if (key == KeyEvent.VK_RIGHT) {
//                
//                int left = drawWidth - viewX;
//                int area = imageWidth - (leftMargin + rightMargin);
//                if (left > area) {
//                    
//                    viewX += scrollX;
//                    
//                    
//                    if (viewX + area > drawWidth) {
//                        viewX = drawWidth - area;
//                    }
//                    
//                    return true;                    
//                }                
//            }
//        }   
//        
//        return false;
    }
}

