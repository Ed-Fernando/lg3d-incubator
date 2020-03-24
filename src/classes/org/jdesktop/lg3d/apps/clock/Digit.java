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

import java.util.Timer;
import java.util.TimerTask;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.sg.TransformGroup;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;

/**
 * This class provides a digit "ala" seven segment display
 * @author opsi
 */
public class Digit extends Container3D {
    private boolean isDots;
    //private Timer dotsTimer;
    private Component3D []bars;
    private Component3D []dots;    
           
    public Digit(float digitWidth) {
        this(digitWidth,null);
    }
    public Digit(float digitWidth ,Appearance app) {
        super();
        int initialDigit = 10;
                
        float barX = digitWidth/5;
        float barY = digitWidth;
        float barZ = digitWidth/30;
        
        if(app==null)
            app = new SimpleAppearance(0.7f,2.0f,0.4f);
        
        TransformGroup[] barsTg;
        Transform3D t3d = new Transform3D();
        
        bars = new Component3D[7];
        barsTg = new TransformGroup[7];
        dots = new Component3D[2];
        
        for(int i = 0;i<7;i++) {
            barsTg[i] = new TransformGroup();
            bars[i] = new Component3D();
            if(i%3 == 0)
            {
                barsTg[i].addChild(new Box(barY,barX,barZ,app));               
            }
            else
            {
                barsTg[i].addChild(new Box(barX,barY,barZ,app));             
            }
            bars[i].addChild(barsTg[i]);
            bars[i].setVisible(false);
            addChild(bars[i]);
        }
        
        t3d.setTranslation(new Vector3f(0.0f,barY*2-(barX),0.0f));
        barsTg[0].setTransform(t3d);
        
        t3d.setTranslation(new Vector3f(barY*1-(barX),barY*1-(barX),0.0f));
        barsTg[1].setTransform(t3d);
        
        t3d.setTranslation(new Vector3f(barY*1-(barX),-barY*1+(barX),0.0f));
        barsTg[2].setTransform(t3d);
        
        t3d.setTranslation(new Vector3f(0.0f,-barY*2+(barX),0.0f));
        barsTg[3].setTransform(t3d);
        
        t3d.setTranslation(new Vector3f(-barY*1+(barX),-barY*1+(barX),0.0f));
        barsTg[4].setTransform(t3d);
        
        t3d.setTranslation(new Vector3f(-barY*1+(barX),barY*1-(barX),0.0f));
        barsTg[5].setTransform(t3d);
        
        t3d.setTranslation(new Vector3f(0.0f,0.0f,0.0f));
        barsTg[6].setTransform(t3d);
                
        for(int i = 0;i<2;i++) {
            dots[i]= new Component3D();
            dots[i].addChild(new Sphere(digitWidth/5,Sphere.GENERATE_NORMALS,app));
            dots[i].setVisible(false);
            addChild(dots[i]);
        }
        
        dots[0].setTranslation(0.0f, digitWidth, 0.0f);
        dots[1].setTranslation(0.0f, -digitWidth, 0.0f);
        
        isDots = false;
    }
    public void toggleDots() {
        if(isDots) {
            //dotsTimer.cancel();
            dots[0].setVisible(false);
            dots[1].setVisible(false);
            for(int i = 0;i<7;i++) {
                bars[i].setVisible(true);
            }
            isDots = false;
        } else {
            for(int i = 0;i<7;i++) {
                bars[i].setVisible(false);
            }
            dots[0].setVisible(true);
            dots[1].setVisible(true);
            isDots = true;
            //dotsTimer = new Timer("Dots Timer");
            //dotsTimer.scheduleAtFixedRate(new BlinkDots(dots),0,1000);
        }
    }
    public void setDigit(int digit) {
        if(!isDots) {
            switch(digit) {
                case 0:
                    for(int i = 0;i<6;i++) {
                        bars[i].setVisible(true);
                    }
                    bars[6].setVisible(false);
                    break;
                case 1:
                    for(int i = 0;i<7;i++) {
                        if(i == 1 || i == 2)
                            bars[i].setVisible(true);
                        else
                            bars[i].setVisible(false);
                    }
                    break;
                case 2:
                    for(int i = 0;i<7;i++) {
                        if(i == 2 || i == 5)
                            bars[i].setVisible(false);
                        else
                            bars[i].setVisible(true);
                    }
                    break;
                case 3:
                    for(int i = 0;i<7;i++) {
                        if(i== 4 || i == 5)
                            bars[i].setVisible(false);
                        else
                            bars[i].setVisible(true);
                    }
                    break;
                case 4:
                    for(int i = 0;i<7;i++) {
                        if(i == 1 || i == 2 || i== 5 || i == 6)
                            bars[i].setVisible(true);
                        else
                            bars[i].setVisible(false);
                    }
                    break;
                case 5:
                    for(int i = 0;i<7;i++) {
                        if(i == 1 || i == 4 )
                            bars[i].setVisible(false);
                        else
                            bars[i].setVisible(true);
                    }
                    break;
                case 6:
                    for(int i = 0;i<7;i++) {
                        if(i == 1)
                            bars[i].setVisible(false);
                        else
                            bars[i].setVisible(true);
                    }
                    break;
                case 7:
                    for(int i = 0;i<7;i++) {
                        if(i < 3)
                            bars[i].setVisible(true);
                        else
                            bars[i].setVisible(false);
                    }
                    break;
                case 8:
                    for(int i = 0;i<7;i++) {
                        bars[i].setVisible(true);
                    }
                    break;
                case 9:
                    for(int i = 0;i<7;i++) {
                        if(i == 4)
                            bars[i].setVisible(false);
                        else
                            bars[i].setVisible(true);
                    }
                    break;
                default:
                    for(int i = 0;i<7;i++) {
                        bars[i].setVisible(false);
                    }
            }
        }
    }
}
/*
class BlinkDots extends TimerTask {
    Component3D dots[];
    public BlinkDots(Component3D dots[]) {
        this.dots = new Component3D[dots.length];
        for(int i = 0;i<dots.length;i++) {
            this.dots[i]=dots[i];
        }
    }
    public void run() {
        boolean vis[] = new boolean[dots.length];
        for(int i = 0;i<dots.length;i++) {
            vis[i]= !dots[i].isVisible();
        }
        for(int i = 0;i<dots.length;i++) {
            dots[i].setVisible(vis[i]);
        }
    }
}*/
