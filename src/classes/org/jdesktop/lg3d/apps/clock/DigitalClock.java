/***************************************************************************
 *   Copyright (C) 2005 by Juan Gonzalez                                   *
 *   juan@aga-system.com                                                   *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************/

package org.jdesktop.lg3d.apps.clock;

import java.util.Calendar;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.sg.TransformGroup;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Thumbnail;

/**
 *
 * @author opsi
 */
public class DigitalClock extends Clock{
    /**
     * The digital clock container
     */
    private Container3D digital;
    /**
     * The digits for this clock
     */
    private Digit []digits;    
    /** 
     * Creates a new instance of DigitalClock given the requested width         
     * @param width Clock width
     */    
    public DigitalClock(float width) {
        this(width,null);
    }    
    /**
     * Creates a new instance of DigitalClock given the requested width, using 
     * app as the digit appearance.     
     * @param width Clock width
     * @param app The appearance to be used for this clock's digits (can be null)
     */
    public DigitalClock(float width,Appearance app) {
        float digitWidth = width/13; //Why 13?
        TransformGroup []digitsTG;
        Transform3D t3d = new Transform3D();
        digital = new Container3D();
        
        digitsTG = new TransformGroup[8];
        digits = new Digit[8];
        
        for(int i = 0;i<5;i++) {
            digits[i] = new Digit(digitWidth,app);
            digital.addChild(digits[i]);
        }        
        for(int i = 2,j=2;j<5;i--,j++) {
            digits[i].setTranslation(-(2-i)*digitWidth*2.2f ,0.0f,0.0f);
            digits[j].setTranslation((j-2)*digitWidth*2.2f,0.0f,0.0f);
        }
        digital.setTranslation(0.0f,0.0f, digitWidth);
        digits[2].toggleDots();
        addChild(digital);
    }
    /**
     * Sets the time of this clock
     * @param currentDate The time to set this clock to. Usually current time
     */
    public void setTime(Calendar currentDate) {
        int mins = currentDate.get(Calendar.MINUTE);
        int hrs = currentDate.get(Calendar.HOUR_OF_DAY);
        digits[0].setDigit(hrs/10);
        digits[1].setDigit(hrs-((int)hrs/10)*10);        
        if(digits[2].isVisible())
            digits[2].setVisible(false);
        else
            digits[2].setVisible(true);
        digits[3].setDigit(mins/10);
        digits[4].setDigit(mins - ((int)mins/10)*10);                
    }
    /**
     * Creates a thumbnail of a DigitalClock to be used by the taskbar
     */
    public static Thumbnail getThumbnail(float width) {
        Thumbnail thumb = new Thumbnail();
        width*=2;
        DigitalClock clock = new DigitalClock(width);        
        Component3D panel = new Component3D();        
        thumb.addChild(clock);   
        //panel.addChild(new FuzzyEdgePanel(0.1f,0.05f,0.01f,new SimpleAppearance(0.0f,0.0f,0.0f)));
        panel.addChild(new GlassyPanel(width,width*0.66f,0.0004f,
                new SimpleAppearance(0.5f,0.8f,0.5f,0.5f)));

        thumb.addChild(panel);        
        thumb.setPreferredSize(new Vector3f(width*2,width*0.66f,0.0004f));
        return thumb;
    }
}
