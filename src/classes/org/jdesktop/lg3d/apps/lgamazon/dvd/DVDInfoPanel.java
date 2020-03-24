/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: DVDInfoPanel.java,v $
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
 * $Date: 2007-03-09 09:38:09 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.dvd;

import org.jdesktop.lg3d.apps.lgamazon.component.InfoPanel;
import org.jdesktop.lg3d.apps.lgamazon.stub.Item;
import org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributes;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;


/**
 * DVD Information Panel. 
 *
 */
public class DVDInfoPanel extends InfoPanel {

    public DVDInfoPanel(SimpleAppearance app) {        
        super(app);
    }
    
    
    @Override
    protected String[][] getProductDetails(Item item, ItemAttributes attr) {
        
        /*
         * Format: Color, Digital Sound, Full Screen, NTSC
         * Region: Region 1 (U.S. and Canada only. Read more about DVD formats.)
         * Number of discs: 2
         * Rating NR
         * Studio: D V Press
         * DVD Release Date: June 10, 2004
         * Run Time: 480 minutes
         * Average Customer Review: based on 10 reviews. (Write a review.)
         * ASIN: B0002AHVQ4
         * Amazon.com Sales Rank: #46,570 in DVD (See Top Sellers in DVD)
         * Yesterday: #46,657 in DVD
         * 
         */
        
        String[][] details = new String[6][2];
        
        details[0][0] = "Format";
        details[0][1] = "";
        String[] format = attr.getFormat();
        if (format != null) {
            for (int i = 0; i < format.length; i++) {
                details[0][1] += format[i] + ",";
            }
        }
        
        details[1][0] = "Region";
        details[1][1] = attr.getRegionCode();
        
        details[2][0] = "Number of discs";
        details[2][1] = "Unknown";
        if (attr.getNumberOfDiscs() != null) {
            details[2][1] = attr.getNumberOfDiscs().toString();
        }
        
        details[3][0] = "Studio";
        details[3][1] = attr.getStudio();
        
        details[4][0] = "DVD Release Date";
        details[4][1] = attr.getReleaseDate();
        
        details[5][0] = "Run Time";
        details[5][1] = attr.getRunningTime() != null ? attr.getRunningTime().toString() + " minutes" : "Unknown";
        
        return details;
    }

}
