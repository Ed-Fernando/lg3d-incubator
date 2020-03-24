/*
 * LG3D Incubator Project - LgAmazon
 *
 * $RCSfile: CDInfoPanel.java,v $
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
 * $Date: 2007-03-09 09:36:53 $
 * Author: E_INOUE
 */

package org.jdesktop.lg3d.apps.lgamazon.cd;

import org.jdesktop.lg3d.apps.lgamazon.component.InfoPanel;
import org.jdesktop.lg3d.apps.lgamazon.stub.Item;
import org.jdesktop.lg3d.apps.lgamazon.stub.ItemAttributes;
import org.jdesktop.lg3d.apps.lgamazon.stub.Tracks;
import org.jdesktop.lg3d.apps.lgamazon.stub.TracksDisc;
import org.jdesktop.lg3d.apps.lgamazon.stub.TracksDiscTrack;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;

/**
 * Panel which displays information on CD. 
 */
public class CDInfoPanel extends InfoPanel {

    public CDInfoPanel(SimpleAppearance app) {        
        super(app);
    }

    @Override
    protected String[][] getProductDetails(Item item, ItemAttributes attr) {
                
        String[][] details = new String[3][2];
        
        details[0][0] = "Artist";
        details[0][1] = "";
        String[] artist = attr.getArtist();
        if (artist != null) {
            for (int i = 0; i < artist.length; i++) {
                details[0][1] += artist[i] + ",";
            }
        }
        
        details[1][0] = "Label";
        details[1][1] = attr.getLabel();

        
        details[2][0] = "Number of discs";
        details[2][1] = "Unknown";
        if (attr.getNumberOfDiscs() != null) {
            details[2][1] = attr.getNumberOfDiscs().toString();
        }
        
                
        return details;
    }
    
    
    protected String setMoreInfo(Item item, ItemAttributes attr) {
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("<hr/>");
        sb.append("<font size='15'>");
        
        Tracks tracks = item.getTracks();
        if (tracks == null) {
            return null;
        }
        
        TracksDisc[] tracksDisc = tracks.getDisc(); 
        
        for (int i = 0; i < tracksDisc.length; i++) {
            
            TracksDiscTrack[] tracksDiscTrack = tracksDisc[i].getTrack();
            
            for (int j = 0; j < tracksDiscTrack.length; j++) {
                sb.append(tracksDiscTrack[j].getNumber()).append(". ");
                sb.append(tracksDiscTrack[j].get_value());
                sb.append("<br/>");
            }
        }
        
        return sb.toString();
    }
}
