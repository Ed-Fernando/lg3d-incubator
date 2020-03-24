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
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.Cylinder;
import org.jdesktop.lg3d.utils.shape.OriginTranslation;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;



/**
 *
 * @author opsi
 */
public class AnalogicClock extends Clock {
    private long baseSecAngle = 0;
    private long baseMinAngle = 0;
    private long baseHourAngle = 0;
    private int nextMode;
    // The analogic clock container
    private Container3D analogic;
    // And all it's components
    private Box []ticks;
    private Component3D tickComp;
    private Component3D secondsBar;
    private Component3D hoursBar;
    private Component3D minutesBar;
    private Component3D center;
    
    private Appearance app;
    
    private int lastSecond =100;
    
    public AnalogicClock(float width) {
        float modifier = 0.99f;
        float depth = width/50;
        Component3D tick = new Component3D();
        Component3D intick;
        
        TransformGroup secondsTG = new TransformGroup();
        TransformGroup minutesTG = new TransformGroup();
        TransformGroup hoursTG = new TransformGroup();
        TransformGroup ticksTG = new TransformGroup();
        Transform3D t3d = new Transform3D();
        
        Vector3f tmpRotationAxis = new Vector3f();
        analogic = new Container3D();
        secondsBar = new Component3D();
        minutesBar = new Component3D();
        hoursBar = new Component3D();
        ticks = new Box[60];
        center = new Component3D();
        
        for(int i = 0;i< ticks.length; i++) {
            OriginTranslation currentTick;
            if(i%5 != 0)
                modifier=modifier/3;
            else if(i%3 != 0)
                modifier=2*modifier/3;
            
            
            app = new SimpleAppearance(modifier,0.1f,modifier,modifier*2);            
            tick = new Component3D();
            intick = new Component3D();
            intick.addChild(
                    new Box(modifier*width/50,modifier*width/15,depth/3,
                    Box.GENERATE_NORMALS | Box.ENABLE_APPEARANCE_MODIFY | Box.GEOMETRY_NOT_SHARED, app)); 
            
            currentTick = new OriginTranslation(intick,new Vector3f(0.0f,(width/2)-(modifier*width/15),0.0f));                    
            tick.addChild(currentTick);
            
            tick.setRotationAxis(0,0, 1);
            tick.changeRotationAngle(-(float)Math.toRadians(i*360/ticks.length));
            
            addChild(tick);
            
            modifier=1;
        }
        
        t3d.setTranslation(new Vector3f(0.0f, width/4.4f,0));
        secondsTG.setTransform(t3d);
        secondsTG.addChild(
                new Box(width/150,width/4.4f,depth/3,Box.GENERATE_NORMALS,
                new SimpleAppearance(0.8f,0.2f,0.2f)));
        
        secondsBar.addChild(secondsTG);
        secondsBar.setRotationAxis(0, 0, 1);
        
        t3d.setTranslation(new Vector3f(0.0f, width/5.5f, 0.0f));
        minutesTG.setTransform(t3d);
        minutesTG.addChild(
                new Box(width/90,width/5.5f,depth/3,Box.GENERATE_NORMALS,
                new SimpleAppearance(0.3f,0.1f,0.6f)));
        
        minutesBar.addChild(minutesTG);
        minutesBar.setRotationAxis(0, 0, 1);
        
        t3d.setTranslation(new Vector3f(0.0f, width/6, 0));
        hoursTG.setTransform(t3d);
        hoursTG.addChild(
                new Box(width/110,width/7,depth/3,Box.GENERATE_NORMALS,
                new SimpleAppearance(0.4f,0.1f,0.8f)));
        
        hoursBar.addChild(hoursTG);
        hoursBar.setRotationAxis(0, 0, 1);
        center.addChild(
                new Cylinder(depth,3*depth,
                new SimpleAppearance(0.6f,0.5f,0.0f)));
        
        center.setRotationAxis(1.0f, 0.0f,0.0f);
        center.setRotationAngle((float)Math.toRadians(90));
        
        analogic.addChild(secondsBar);
        analogic.addChild(minutesBar);
        analogic.addChild(hoursBar);
        analogic.addChild(center);
        
        addChild(analogic);
        //setTime(Calendar.getInstance());
    }
    // TODO : This doesn't work properly everytime
    public void setTime(Calendar currentDate) {        
        int secs = currentDate.get(Calendar.SECOND);        
        if(secs!=lastSecond) {
            int mins = currentDate.get(Calendar.MINUTE);
            int hrs = currentDate.get(Calendar.HOUR);
            
            double secAngle = (secs+1)*6;
            double minAngle = (mins * 60 + secs) * 0.1;
            double hourAngle = (hrs * 60 + mins) * 0.5;
                        
            if(secAngle == 0)
                baseSecAngle+=360;
            if(minAngle == 0)
                baseMinAngle+=360;
            if(minAngle == 0)
                baseMinAngle+=360;
            if(hourAngle == 0)
                baseHourAngle+=360;
            secondsBar.changeRotationAngle((float)Math.toRadians(-baseSecAngle-secAngle));
            minutesBar.changeRotationAngle((float)Math.toRadians(-baseMinAngle-minAngle));
            hoursBar.changeRotationAngle((float)Math.toRadians(-baseHourAngle-hourAngle));
        }
        lastSecond = secs;
    }
}
