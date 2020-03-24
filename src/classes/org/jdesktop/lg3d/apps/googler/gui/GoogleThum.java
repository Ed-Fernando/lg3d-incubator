/*
 ***************************************************************************
 *   Copyright (C) 2005 by Juan Gonzalez                                   *
 *   juan@aga-system.com                                                   *
 *                                                                         *
 *   This program has been developed under Google's "Summer of Code 2005". *
 *   Special thanks goes to all people from Google and Sun Microsystems    *
 *   who made this cool experience a kind of success.                      *
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
 ***************************************************************************
 * GoogleThum.java
 *
 * Created on 27 de agosto de 2005, 18:11
 */

package org.jdesktop.lg3d.apps.googler.gui;
import java.awt.Color;
import java.awt.Font;
import java.util.TimerTask;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.apps.googler.engine.events.SearchRunningLgEvent;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.OriginTranslation;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Thumbnail;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 *
 * @author opsi
 */
public class GoogleThum extends Thumbnail implements LgEventListener{
    private Container3D top = new Container3D();
    private Component3D textComp = new Component3D();
    private Component3D shape = new Component3D();
    private TimerTask workingTask;
    private TextPanel3D textPanel;
    private float size;
    private boolean innerShown;
    private int delay = 3000;
    
    private Color blue = new Color(0, 0.22f, 0.71f);
    private Color red = new Color(0.76f, 0.07f, 0);
    private Color yellow = new Color(0.95f, 0.77f, 0.09f);
    private Color green = new Color(0.18f, 0.65f, 0.18f);
    
    /** Creates a new instance of GoogleThum */
    public GoogleThum(float size) {
        this.size = size;
        
        OriginTranslation ot = new OriginTranslation(
                (textPanel= new TextPanel3D(3, 3, size/3)),
                new Vector3f(size/5, size*0.8f, size/15));
        textComp.addChild(ot);                
        
        shape.addChild(new GlassyPanel(size,size,size/20, new SimpleAppearance(0.9f,0.9f,0.9f)));                
        
        top.addChild(textComp);
        top.addChild(shape);
        top.setAnimation(new NaturalMotionAnimation(delay));
        addChild(top);
        setPreferredSize(new Vector3f(size,size,size/20));
        
        setVisible(true);
        LgEventConnector.getLgEventConnector().addListener(LgEventSource.ALL_SOURCES, this);
        writeGoogler();
    }
    private void writeGoogler() {
        
        textPanel.removeRows();
        textPanel.setLetter(0, 0, 'G', blue,blue, null, 0);
        textPanel.setLetter(0, 1, 'O', red,red, null, 0);
        textPanel.setLetter(0, 2, 'O', yellow,yellow, null, 0);
        textPanel.setLetter(1, 0, 'G', blue,blue, null, 0);
        textPanel.setLetter(1, 1, 'L', green,green, null, 0);
        textPanel.setLetter(1, 2, 'E', red,red, null, 0);
        textPanel.setLetter(2, 1, 'R', Color.BLACK,Color.BLACK, null, 0);
    }
    private void writeWorking() {
        textPanel.removeRows();
        textPanel.setLetter(0, 0, 'W', blue,blue, null, 0);
        textPanel.setLetter(0, 1, 'O', red,red, null, 0);
        textPanel.setLetter(1, 0, 'R', yellow,yellow, null, 0);
        textPanel.setLetter(1, 1, 'K', blue,blue, null, 0);
        textPanel.setLetter(1, 2, 'I', green,green, null, 0);
        textPanel.setLetter(2, 1, 'N', red,red, null, 0);
        textPanel.setLetter(2, 2, 'G', Color.BLACK,Color.BLACK, null, 0);
    }
    public void working(boolean working) {
        if(workingTask!=null)
            workingTask.cancel();
        if(working) {
            writeWorking();
            workingTask = new TimerTask() {
                public void run() {
                    top.setRotationAxis(0,0,1f);
                    top.changeRotationAngle(top.getRotationAngle()+(float)Math.toRadians(360),(int)(delay/1.5));
                };
            };
            GooglerTimer.getTimer().schedule(workingTask,0, delay);
        } else {
            writeGoogler();
            top.setRotationAxis(0,0,1f);
            top.setRotationAngle(0);                        
        }
    }
    
    public void processEvent(org.jdesktop.lg3d.wg.event.LgEvent evt) {
        working(((SearchRunningLgEvent)evt).running);
    }
    
    public Class[] getTargetEventClasses() {
        return new Class[] { SearchRunningLgEvent.class };
    }
}
