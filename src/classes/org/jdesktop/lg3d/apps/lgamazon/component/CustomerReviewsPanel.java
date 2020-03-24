/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: CustomerReviewsPanel.java,v $
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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;


import javax.imageio.ImageIO;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.lgamazon.stub.CustomerReviews;
import org.jdesktop.lg3d.apps.lgamazon.stub.Review;
import org.jdesktop.lg3d.apps.lgamazon.util.LgUtil;
import org.jdesktop.lg3d.utils.action.ActionFloat3;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.KeyPressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseWheelEventAdapter;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;


public class CustomerReviewsPanel extends Container3D {

    private static BufferedImage starON, starOFF, starHalf;
    
    static {
        
        try {
            Class c = CustomerReviewsPanel.class;
            
            starON = ImageIO.read(c.getResource("/org/jdesktop/lg3d/apps/lgamazon/resources/star_on.png"));
            starOFF = ImageIO.read(c.getResource("/org/jdesktop/lg3d/apps/lgamazon/resources/star_off.png"));
            starHalf = ImageIO.read(c.getResource("/org/jdesktop/lg3d/apps/lgamazon/resources/star_half.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    private Review[] review;
    private int current;    
    private int currentPage;
    
    
    private final static Toolkit3D toolkit = Toolkit3D.getToolkit3D(); 
    
    private HTMLLabel titleLabel;
    private ReviewPanel reviewPanel;
    
    private Component3D[] panelComponents;
    
    
    private CustomerReviews csReviews;
    
    
    
    public CustomerReviewsPanel() {
        
        titleLabel = new HTMLLabel(Product.createWhiteAppearance());
        titleLabel.background = new Color(255, 255, 255, 0);
        titleLabel.labelRender = new TitleRenderer();
        titleLabel.topMargin = titleLabel.bottomMargin = 0;
        titleLabel.leftMargin = titleLabel.rightMargin = 5;
        addChild(titleLabel);
                        
        reviewPanel = new ReviewPanel();        
        addChild(reviewPanel);
        
        reviewPanel.addListener(new MouseClickedEventAdapter(MouseEvent3D.ButtonId.BUTTON1, 
                new MouseClickAction(-1)));        
        reviewPanel.addListener(new MouseClickedEventAdapter(MouseEvent3D.ButtonId.BUTTON3, 
                new MouseClickAction(1)));
        
        panelComponents = new Component3D[] {titleLabel, reviewPanel};
    }
    
    
    public void setCustomerReviews(
    float width, float height, CustomerReviews csReviews) { 
    	
        this.csReviews = csReviews;        
        
        currentPage = 1;
        current = 0;        
        Review first = null;
        if (csReviews != null) {
            review = csReviews.getReview();
            first = review[current];
        }
        
        titleLabel.setSize(width, toolkit.widthNativeToPhysical(40));
        drawTitle();
        
        float x = -width / 2.0f;
        float y = height / 2.0f;
        
        reviewPanel.setSizePos(
                x, y - LgUtil.getSize(titleLabel).y, 
                width, height - LgUtil.getSize(titleLabel).y);
        reviewPanel.draw(first);    
        
        // simpleLayout(x, y, 0.001f, panelComponents);
        simpleLayout(x, y, 0.01f, panelComponents);     
    }
    
    
    private void drawTitle() {
        
        titleLabel.setFont("Dialog", Font.PLAIN, 15);
        StringBuffer sb = new StringBuffer();
        sb.append("<body>");
        sb.append("<font size='25' color='#CC6600'><b>Customer Review</b></font>");
        if (csReviews != null) {
            sb.append("  Ave.");
        }
        sb.append("</body>");
        
        titleLabel.setText(sb.toString());        
        titleLabel.draw();        
    }
    
    
    
//    
//    private float simpleLayout(
//    float width, float height, float z, Component3D[] list) {
//        
//        
//        Vector3f v = new Vector3f(-width / 2.0f, height / 2.0f, z);
//        
//        float y = v.y;
//        float totalHeight = 0.0f;
//        
//        for (int i = 0; i < list.length; i++) {
//            
//            Vector3f size = LgUtil.getSize(list[i]);
//            list[i].setTranslation(v.x + (size.x / 2.0f), y - (size.y / 2.0f), v.z);
//            totalHeight += size.y;
//            y -= size.y;
//        }
//        
//        return totalHeight;
//    }
    
    
    private float simpleLayout(
    float x, float y, float z, Component3D[] list) {
        
      float totalHeight = 0.0f;
      
      for (int i = 0; i < list.length; i++) {
          
          Vector3f size = LgUtil.getSize(list[i]);
          list[i].setTranslation(x + (size.x / 2.0f), y - (size.y / 2.0f), z);
          totalHeight += size.y;
          y -= size.y;
      }

      return totalHeight;
    }
    
    class MouseClickAction implements ActionFloat3 {
        
        int dir;
        
        MouseClickAction(int dir) {
            this.dir = dir;
        }
        
        public void performAction(
        LgEventSource source, float x, float y, float z) {    
            
            if (csReviews == null) {
                return;
            }
            
            int p = current + dir;
            if (p >= review.length) {
                
                if (currentPage == csReviews.getTotalReviewPages().intValue()) {
                    return;
                }

                
                System.out.println("Plaese get revew!! " + review.length);
                return;     
            }
            else if (p < 0) {
                return;
            }
            
            reviewPanel.setRotationAxis(0.0f, 1.0f, 0.0f);
            float angle = (dir > 0) ? (float) Math.PI * 2.0f : (float) -Math.PI * 2.0f;
            angle += reviewPanel.getRotationAngle();
            reviewPanel.changeRotationAngle(angle);
            
            current = p;

            reviewPanel.draw(review[current]);
        }        
    }  

    class TitleRenderer implements LabelRenderer {
        
        public void standby(LabelComponent label, Graphics2D g) {            
        }
        
        public void finish(LabelComponent label, Graphics2D g) {
            
            if (csReviews == null) {
                return;
            }
            
            float scale = (1.0f / label.getScale()) * 1.7f;
            int x = drawStars(g, label.getX(), label.getY() - (int) (starON.getHeight() * scale), scale, 
                    csReviews.getAverageRating());
            
            g.drawString("(" + csReviews.getTotalReviews() + ")", x + 10, label.getY());
        }
    }
    
    
    private int drawStars(
    Graphics2D g, int x, int y, float scale, BigDecimal rating) {        
        
        for (int i = 0; i < 5; i++) {
            
            float left = rating.floatValue() - (float) i;
            
            BufferedImage image = starON;
            if (left <= 0.0f) {
                image = starOFF;
            }
            else if (left == 0.5f) {
                image = starHalf;
            }
                        
            int w = (int) (image.getWidth() * scale);
            g.drawImage(image, x, y, w, (int) (image.getHeight() * scale), null);
            x += w;            
        }
        
        return x;
    }
    
    
    class ReviewPanel extends Component3D {
        
        Review review;
        
        HTMLLabel info;
        HTMLLabel contents;
        
        private Component3D[] components;
        
        
        float INFO_HEIGHT = toolkit.heightNativeToPhysical(128);
        
        ReviewPanel() {
            
            setAnimation(new NaturalMotionAnimation(1000));
            
            info = new HTMLLabel(Product.createWhiteAppearance());  
            info.topMargin = info.bottomMargin = 10;
            info.leftMargin = info.rightMargin = 10;
            info.background = new Color(255, 0, 255, 0);
            info.labelRender = new CSRInfoRenderer();
            addChild(info);
            
            contents = new HTMLLabel(Product.createWhiteAppearance());
            contents.topMargin = contents.bottomMargin = 20;
            contents.leftMargin = contents.rightMargin = 20;
            contents.background = new Color(255, 255, 0, 0);
            addChild(contents);
            
            LabelScroller scroller = new LabelScroller(contents);        
            contents.addListener(new KeyPressedEventAdapter(scroller));
            contents.addListener(new MouseWheelEventAdapter(scroller));
            contents.labelRender = new CSRRenderer(scroller);            
            
            
            components = new Component3D[] {info, contents};
        }
        
        
        void setSizePos(
        float x, float y, float width, float height) {
            
            info.setSize(width, INFO_HEIGHT); 
            info.setFont("Dialog", Font.PLAIN, 17);
            
            contents.setSize(width, height - INFO_HEIGHT);
            contents.setFont("Dialog", Font.PLAIN, 20);
            
            simpleLayout(x, y, 0.0f, components);
            
            Vector3f size = new Vector3f(width, height, 0.0f);
            setPreferredSize(size);    
        }
        
        
        void draw(Review review) {
        	       	
            this.review = review;
        	contents.viewPos.x = contents.viewPos.y = 0;
            
            StringBuffer sb = new StringBuffer();
            
            if (review == null) {
                sb.append("<body>No reviews.</body>");
                info.setText(sb.toString());
                info.draw();
                
                contents.setText("");
                contents.draw();
                
                return;
            }
            
            sb.append("<body>");
            sb.append("                      ");    
            sb.append("<font size='15'> #").append(current + 1).append("</font> ");
            sb.append("<b><font size='23' color='#0000FF'>").append(review.getSummary());
            sb.append("</font></b>");
            sb.append("<i> (");
            sb.append(review.getDate()).append(")</i><br/>");
            sb.append("Reviewer: <b>").append(review.getCustomerId()).append("</b><br/>");
            sb.append(review.getHelpfulVotes()).append(" of ").append(review.getTotalVotes());
            sb.append(" people found the following review helpful:");
            
            sb.append("</body>");
            info.setText(sb.toString());
            info.draw();
            
            
            sb.delete(0, sb.length());
            sb.append("<body>");            
            sb.append(review.getContent());
            sb.append("</body>");
            
            contents.setText(sb.toString());
            contents.draw();
        }
        
        
        class CSRInfoRenderer implements LabelRenderer {
            
            public void standby(LabelComponent label, Graphics2D g) {
                
                float scale = (1.0f / label.getScale()) * 1.5f; 
                
                int margin = (int) (10 * scale);
                g.setColor(new Color(0xFFDF91));
                g.fillRoundRect(
                        label.client.x, label.client.y, 
                        label.client.width, label.client.height,
                        margin, margin);
                
                
                if (review == null) {
                    return;
                }
                
                int x = label.viewArea.x;
                for (int i = 0; i < 5; i++) {
                    
                    BufferedImage image = (review.getRating().intValue()) >= i ? starON : starOFF;
                    int w = (int) (image.getWidth() * scale);
                    g.drawImage(image, x, label.viewArea.y, w, (int) (image.getHeight() * scale), null);
                    x += w;
                }
            }
            
            public void finish(LabelComponent label, Graphics2D g) {
                
            }
        }
        
        
        class CSRRenderer implements LabelRenderer {
            
            LabelScroller scroller;
            
            CSRRenderer(LabelScroller scroller) {
                this.scroller = scroller;
            }
            
            public void standby(LabelComponent label, Graphics2D g) {
                
                float scale = 1.0f / label.getScale();
                
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(
                        label.viewArea.x - 3, label.viewArea.y - 3, 
                        label.viewArea.width + 6, label.viewArea.height + 6);
                
                g.setColor(new Color(0xEFA632));
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


        @Override
        public void addListener(LgEventListener listener) {
            
            super.addListener(listener);
            info.addListener(listener);
            contents.addListener(listener);
        }
    }
}
