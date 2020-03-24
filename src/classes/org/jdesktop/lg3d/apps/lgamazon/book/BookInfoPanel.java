/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: BookInfoPanel.java,v $
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
 * $Date: 2007-03-09 09:36:08 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.book;

import org.apache.axis.types.NonNegativeInteger;
import org.jdesktop.lg3d.apps.lgamazon.component.InfoPanel;
import org.jdesktop.lg3d.apps.lgamazon.stub.Item;
import org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributes;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;


/**
 * Panel which displays information in book. 
 * 
 * 
 */
public class BookInfoPanel extends InfoPanel {

    
    public BookInfoPanel(SimpleAppearance app) {        
        super(app);
    }
    
    protected String[][] getProductDetails(Item item, ItemAttributes attr) {
        
        String[][] detail = new String[2][2];
        
        detail[0][0] = "Paperback";
        detail[0][1] = "unknown";
        NonNegativeInteger paperback = attr.getNumberOfPages();
        if (paperback != null) {
            detail[0][1] = paperback + " pages"; 
        }
        
        detail[1][0] = "Publisher";
        detail[1][1] = "unknown";
        if (attr.getPublisher() != null) {
            detail[1][1] = attr.getPublisher();
        }
        
//        sb.append("<b>Language: </b>");
//        ItemAttributesLanguagesLanguage[] language = attr.getLanguages().getLanguage();
//        for (int i = 0; i < language.length; i++) {
//            sb.append(language[i]).append(' ');
//        }
//        sb.append("<br/>");
        
        return detail;
    }
    
}
