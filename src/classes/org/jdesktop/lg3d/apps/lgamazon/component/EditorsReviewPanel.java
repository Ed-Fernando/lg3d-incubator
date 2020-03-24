/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: EditorsReviewPanel.java,v $
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
 * $Date: 2007-03-09 09:37:36 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.component;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


import org.jdesktop.lg3d.apps.lgamazon.stub.EditorialReview;
import org.jdesktop.lg3d.utils.eventadapter.KeyPressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseWheelEventAdapter;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;

public class EditorsReviewPanel extends HTMLLabel {

    public EditorsReviewPanel(SimpleAppearance app) {
        
        super(app);
        
        LabelScroller scroller = new LabelScroller(this);
        labelRender = new EDRRenderer(scroller);
        
        addListener(new KeyPressedEventAdapter(scroller));
        addListener(new MouseWheelEventAdapter(scroller));
        
        background = new Color(1.0f, 1.0f, 1.0f, 0.0f);
    }
    
    
    public void setReview(EditorialReview[] review, float width, float height) {
        
        StringBuffer reviewMessage = new StringBuffer();
        reviewMessage.append("<body>");
        if (review != null) {
                        
            for (int i = 0; i < review.length; i++) {
                reviewMessage.append("<b>");
                reviewMessage.append(review[i].getSource());
                reviewMessage.append("</b><br/>");
                reviewMessage.append(Product.imputationTag(review[i].getContent()));
                
                if (i < review.length - 1) {
                    reviewMessage.append("<p/>");
                }
                // System.out.println(review[i].getContent());
            }
        }
        else {
            reviewMessage.append("No Reviews.");
        }
        reviewMessage.append("</body>");
                
        setSize(width, height);        
        
        leftMargin = rightMargin = bottomMargin = 13; 
        topMargin = 35;  
        
        setFont("Dialog", Font.PLAIN, 20);
        setText(reviewMessage.toString());
        
        draw();
    } 

    
    class EDRRenderer implements LabelRenderer {
        
        LabelScroller scroller;
        
        EDRRenderer(LabelScroller scroller) {
            this.scroller = scroller;
        }
        
        public void standby(LabelComponent label, Graphics2D g) {
            
            float scale = 1.0f / label.getScale();
            
            g.setColor(new Color(0xCC, 0x66, 0x00));
            g.setFont(new Font("Dialog", Font.BOLD, (int) (25.0f * scale)));
            g.drawString("Editorial Reviews", 
                    label.viewArea.x, label.viewArea.y - g.getFontMetrics().getDescent() - 3);
            
            g.setColor(Color.WHITE);
            g.fillRect(
                    label.viewArea.x - 3, label.viewArea.y - 3, 
                    label.viewArea.width + 6, label.viewArea.height + 6);
            
            g.setColor(new Color(0x5C, 0x9E, 0xBF));
            g.setStroke(new BasicStroke(3.0f * scale, 
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.drawRect(
                    label.viewArea.x - 3, label.viewArea.y - 3, 
                    label.viewArea.width + 6, label.viewArea.height + 6);
            
        }
        
        public void finish(LabelComponent label, Graphics2D g) {
            scroller.drawIndicator(g);
        }
    }
}
