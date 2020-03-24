/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: SpineCover.java,v $
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
 * $Date: 2007-03-09 09:37:39 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Toolkit3D;

/**
 * 
 *
 */
public class SpineCover extends Component3D {

    private LabelComponent spineLabel;
    private Toolkit3D toolkit = Toolkit3D.getToolkit3D();
    
    public SpineCover() {
        
        spineLabel = new SpineLabelComponent(Product.createWhiteAppearance());
    }
    
    
    public void setInfo(
    String title, float width, float height, Color background) {
        
        removeAllChildren();
        
        if (background.getRGB() == -16777216) {     
            background = Color.WHITE;
        }
        float[] rgb = background.getRGBColorComponents(null);
        
        Appearance bgAppearance = new SimpleAppearance(rgb[0], rgb[1], rgb[2], 1.0f, 
                SimpleAppearance.DISABLE_CULLING);
        ImagePanel bgPanel = new ImagePanel(width, height);
        bgPanel.setAppearance(bgAppearance);        
        Component3D bgComponent = new Component3D();        
        bgComponent.addChild(bgPanel);        
        bgComponent.setTranslation(0.0f, 0.0f, -0.001f);    
        addChild(bgComponent);
        
                        
        spineLabel.setSize(width, height);
        spineLabel.appearance.setColor(rgb[0], rgb[1], rgb[2], 1.0f);
        
        spineLabel.topMargin = spineLabel.bottomMargin = 0;
        spineLabel.leftMargin = spineLabel.rightMargin = 10;
        
        
        int nw = toolkit.widthPhysicalToNative(width);
        int nh = toolkit.heightPhysicalToNative(height);
        int w = nw - (spineLabel.leftMargin + spineLabel.rightMargin);
        int h = nh - (spineLabel.topMargin + spineLabel.bottomMargin);
        
        
        Graphics2D g = spineLabel.getGraphics();
        
        int fontsize = h / 2;   
        Font font = new Font("Dialog", Font.BOLD, fontsize); 
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(); 
                        
        int sw = fm.stringWidth(title);
        if (sw > w) {            
            font = new Font(font.getName(), font.getStyle(), (int) (font.getSize() * 0.7f));
            g.setFont(font);
            fm = g.getFontMetrics();
            sw = fm.stringWidth(title);
        }
        
        
        
//        float scale = 1.0f / label.getScale();
//        int realH = (int) (nh * scale);
//        Font realFont = new Font(font.getName(), font.getStyle(), (int) (font.getSize() * scale));
//        System.out.println("realsize1=" + realFont.getSize());
//        System.out.println("realH=" + realH);
//        g.setFont(realFont);
//        FontMetrics realFM = g.getFontMetrics();
//        
//        System.out.println("realFM=" + realFM.getHeight());
//        System.out.println("realH=" + realH);
//        
//        if (realFM.getHeight() > realH) {
//            for (int i = realFont.getSize() - 1; i > 0; i--) {
//                realFont = new Font(realFont.getName(), realFont.getStyle(), i);
//                g.setFont(realFont);
//                realFM = g.getFontMetrics();
//                
//                if (realFM.getHeight() <= realH) {
//                    break;
//                }
//            }
//        }
//        
//        System.out.println("realsize2=" + realFont.getSize());
//        
//        System.out.println("LgBook.createSpine(3)" + fm.getHeight());
//        System.out.println("LgBook.createSpine(4)" + nh);
        
        
        /*
        if (fm.getHeight() > nh) {
            for (int i = font.getSize() - 1; i > 0; i--) {
                font = new Font(font.getName(), font.getStyle(), i);
                g.setFont(font);
                fm = g.getFontMetrics();
                sw = fm.stringWidth(title);
                
                if (fm.getHeight() <= nh) {
                    break;
                }
            }
        }
        */
        
        
        int hm;
        if (sw <= w) {            
            hm = (nh - fm.getHeight()) / 2;            
        }        
        else {
            hm = (nh - (fm.getHeight() * 2)) / 2;
        }
        
        
        spineLabel.topMargin = spineLabel.bottomMargin = hm;
        spineLabel.setFont(font.getName(), font.getStyle(), font.getSize());
        g.dispose();
                        
        
        spineLabel.background = new Color(rgb[0], rgb[1], rgb[2], 0.0f);
        spineLabel.foreground = new Color(1.0f - rgb[0], 1.0f - rgb[1], 1.0f - rgb[2]);  
        spineLabel.setText(title);
        spineLabel.draw();
        addChild(spineLabel);
                
        setRotationAxis(0.0f, 0.0f, 1.0f);
        setRotationAngle(-(float) Math.PI / 2);   
    }
    
    
    class SpineLabelComponent extends LabelComponent {
     
        SpineLabelComponent(SimpleAppearance appearance) {
            super(appearance);
        }
        
        
        @Override
        protected void setAttribute(AttributedString string) {
            
//            string.addAttribute(TextAttribute.FOREGROUND, Color.RED);
//            string.addAttribute(TextAttribute.BACKGROUND, Color.WHITE);
            
            // string.addAttribute(TextAttribute.SWAP_COLORS, TextAttribute.SWAP_COLORS_ON);            
        }
    }
}
