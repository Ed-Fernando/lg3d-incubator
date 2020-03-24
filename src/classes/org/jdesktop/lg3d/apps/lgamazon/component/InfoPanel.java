/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: InfoPanel.java,v $
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
 * $Date: 2007-03-09 09:37:37 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.component;


import java.awt.Color;
import java.awt.Font;

import org.jdesktop.lg3d.apps.lgamazon.component.HTMLLabel;
import org.jdesktop.lg3d.apps.lgamazon.component.Product;
import org.jdesktop.lg3d.apps.lgamazon.stub.Item;
import org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributes;
import org.jdesktop.lg3d.apps.lgamazon.stub.OfferSummary;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;


/**
 * 
 */
public abstract class InfoPanel extends HTMLLabel {

    private Item item;
    
    
    public InfoPanel(SimpleAppearance app) {        
        super(app);
    }
    
    public void setInfo(
    Item item,
    float width, float height,
    float red, float green, float blue, float alpha) {
        
        this.item = item;
        
        setSize(width, height);
        appearance.setColor(red, green, blue, alpha);
        
        topMargin = 10;
        leftMargin = 10;
        rightMargin = 10;
        background = new Color(red, green, blue, 0.0f);    
        foreground = Color.BLACK;
        setFont("Dialog", Font.PLAIN, 20);
        
        drawInfo();         
    }
    
    
    private void drawInfo() {
        
        ItemAttributes attr = item.getItemAttributes();
        OfferSummary offerSummary = item.getOfferSummary();
                
        StringBuffer sb = new StringBuffer();
        sb.append("<body>");
        
        String title = Product.cutTitle(attr.getTitle());
        String subtitle = attr.getTitle().substring(title.length()).trim();
        sb.append("<font size='30'>");
        sb.append("<b>");
        sb.append(Product.imputationTag(title));
        sb.append("</b>");
        sb.append("</font>");
        if (subtitle.length() > 0) {
            sb.append("<font size='15'>");
            sb.append("<i>");
            sb.append(Product.imputationTag(subtitle));
            sb.append("</i>");
            sb.append("</font>");
        }
        sb.append("<br/>");
        
        String[] author = attr.getAuthor();
        if (author != null) {
            sb.append("by ");
            for (int i = 0; i < author.length; i++) {
                
                sb.append(Product.imputationTag(author[i]));
                if (i < author.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("<br/>");
        }
        
        if (attr.getListPrice() != null) {
            sb.append("<b>List Price: </b>");        
            sb.append(attr.getListPrice().getFormattedPrice());
            sb.append("<br/>");
        }
        if (offerSummary.getLowestNewPrice() != null) {
            sb.append("<b><font color='#ff0000'>Lowest New Price: </font>");
            sb.append(offerSummary.getLowestNewPrice().getFormattedPrice());
            sb.append("</b>");
        }
        sb.append("<hr/>");
                
        sb.append("<b><font size='25' color='#CC6600'>");
        sb.append("Product Details<br/></font></b>");

        String[][] details = getProductDetails(item, attr);
        for (int i = 0; i < details.length; i++) {
            sb.append("<b>").append(details[i][0]).append(": </b>");
            sb.append(details[i][1]);
            sb.append("<br/>");
        }
        
        String isbn = attr.getISBN();
        if (isbn != null) {
            sb.append("<b>ISBN: </b>");
            sb.append(isbn);
            sb.append("<br/>");
        }
        else {
            sb.append("<b>ASIN: </b>");
            sb.append(item.getASIN());
            sb.append("<br/>");
        }
        /*
         * 
         */

        String salesRank = item.getSalesRank();
        if (salesRank != null) {
            sb.append("<b>Amazon.com Sales Rank: </b>");
            sb.append('#').append(item.getSalesRank());
            // sb.append("<br/>");
        }
        
        
        String info = setMoreInfo(item, attr);
        if (info != null) {
            sb.append(info);
        }
        
        sb.append("</body>");
        
        
                
        setText(sb.toString());     
        draw();
        
        
//        System.out.println("SalesRank:" + item.getSalesRank());        
//        System.out.println("AudienceRating:" + attr.getAudienceRating());        
//        System.out.println("ClothingSize:" + attr.getClothingSize()); 
//        System.out.println("ISBN:" + attr.getISBN()); 
//        System.out.println("IssuesPerYear:" + attr.getIssuesPerYear()); 
//        System.out.println("Label" + attr.getLabel());  
//        System.out.println("Publisher:" + attr.getPublisher()); 
//        System.out.println("PublicationDate:" + attr.getPublicationDate()); 
//        System.out.println("Size:" + attr.getSize()); 
//        System.out.println("Title:" + attr.getTitle()); 
//        System.out.println("ListPrice:" + attr.getListPrice().getFormattedPrice());
    }

    
    protected abstract String[][] getProductDetails(Item item, ItemAttributes attr);
    
    
    protected String setMoreInfo(Item item, ItemAttributes attr) {
        return null;
    }
}
