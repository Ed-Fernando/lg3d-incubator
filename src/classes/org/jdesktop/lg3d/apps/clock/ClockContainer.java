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
import java.util.Timer;
import java.util.TimerTask;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.FuzzyEdgePanel;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Thumbnail;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 *
 * @author opsi
 */
public class ClockContainer extends Frame3D{
    private int registeredClocks = 0;
    private int currentClock = 0;
    private Clock activeClock = null;
    private Container3D top;
    //private Timer toggleTimer;
    protected static Timer scheduler = new Timer("Clock3D Timer");;    
    /**
     * Container for all the clocks that this Container has
     */
    private Container3D clocks;
    private Thumbnail thumb;
    /**
     * The box that "contains" all clocks in the 3D space
     */
    private Component3D outBox;
    /**
     * A sphere that is used to switch betwen clocks
     */
    private Component3D controlButton;
    /**
     * When clicked closes the ClockContainer (i.e. changeActive(false))
     */
    private Component3D exitButton;    
    /**
     * Creates a new instance of ClockContainer given the desired width
     * @param width The width (and height) of this container
     */
    public ClockContainer(float width) {
        setName("Clock");
        initCommon(width);
        initEvents();        
        changeEnabled(true);
//        changeVisible(true);
        
    }
    /**
     * Creates the common shapes of this Container (controlButton,exitButton,...)
     */
    public void initCommon(float width) {
        float depth = width/3;
        top = new Container3D();
        top.setAnimation(new NaturalMotionAnimation(500));
        clocks = new Container3D();
        controlButton = new Component3D();
        exitButton = new Component3D();
        outBox = new Component3D();        
        
        controlButton.addChild(new Sphere(width/30,Sphere.GENERATE_NORMALS,10,
                new SimpleAppearance(0.1f,0.1f,1.0f,0.5f)));
        
        controlButton.setCursor(Cursor3D.SW_RESIZE_CURSOR);
        controlButton.setTranslation(-width/2.1f,width/2.1f, depth/2.1f);
        
        exitButton.addChild(new Sphere(width/30,Sphere.GENERATE_NORMALS,10,
                new SimpleAppearance(1.0f,0.1f,0.1f,0.5f)));
        exitButton.setCursor(Cursor3D.NE_RESIZE_CURSOR);
        exitButton.setTranslation(width/2.1f,width/2.1f, depth/2.1f);
        //Comment the next line out ...
        outBox.addChild(new GlassyPanel(width,width,depth,width/5, new SimpleAppearance(0.5f,0.5f,0.9f,0.4f)));
        // ...and uncomment this to change your clock envelopment ;)
        //outBox.addChild(new FuzzyEdgePanel(width,width,depth,new SimpleAppearance(0.5f,0.5f,0.9f,0.4f)));
        outBox.setTranslation(0.0f,0.0f,width/6);
        
        addChild(top);
        top.addChild(clocks);
        top.addChild(outBox);
        top.addChild(controlButton);
        top.addChild(exitButton);  
        
        scheduler.scheduleAtFixedRate(new ClockTimeAction(this),0,1000);
        
        thumb = DigitalClock.getThumbnail(width * 0.15f);
        scheduler.scheduleAtFixedRate(new ClockTimeAction(thumb),0,1000);
        setThumbnail((Thumbnail)thumb);
        
        //toggleTimer = new Timer("Toggle");
    }
    /**
     * Adds events listeners for the common shapes
     */
    private void initEvents() {
        controlButton.addListener(
                new MouseClickedEventAdapter(
                        new ActionNoArg() {
                        public void performAction(LgEventSource source) {
                        toggle();
                        }
        }));
        exitButton.addListener(
        new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {                                
                changeEnabled(false);
            }
        }));
   }
    /**
     * This goes rotating along the registered clocks of this container using a nice animation
     */
    public void toggle() {
        final int time = 5000;        
        if(registeredClocks > 0) {            
            // TODO : This is supposed to act on top Container, but there it doesn't work
            top.setRotationAxis(1.0f, -1.0f,0.0f);
            top.changeRotationAngle((float)Math.toRadians(180*3),time/2);
            top.changeScale(0.0f,time/2);
            scheduler.schedule(
                    new TimerTask(){
                public void run() {
                    Clock current = ((Clock)clocks.getChild(currentClock));
                    current.changeVisible(false);                    
                    
                    currentClock = (currentClock+1)%registeredClocks;
                    
                    current = ((Clock)clocks.getChild(currentClock));                    
                    activeClock = current;
                    current.changeVisible(true);                                    
                }
            },time/6);
            scheduler.schedule(
                    new TimerTask(){
                public void run() {
                    top.changeRotationAngle(0,time/2);
                    top.changeScale(1,time/2);
                }
            },time/4);
        }
    }
    /**
     * Adds a new Clock to this container. The first to be added is executed inmediatly
     */
    public void register(Clock newClock) {
        registeredClocks++;
        Vector3f ret = new Vector3f();        
        clocks.addChild(newClock);       
        if(registeredClocks == 1) {
            activeClock = newClock;
            newClock.changeVisible(true);            
        } else
            newClock.setVisible(false);
    }
    public void setTime(Calendar currentDate)
    {
        if(activeClock != null)
            activeClock.setTime(currentDate);
    }
    /**
     * Entry point for this application where, for now, the clocks are added manually
     */
    public static void main(String []args) {
        ///This is a bit dummy?
        float screenwidth = Toolkit3D.getToolkit3D().getScreenWidth();
        ClockContainer cont = new ClockContainer(screenwidth/5);
        cont.register(new MixedClock(screenwidth/5));
        cont.register(new AnalogicClock(screenwidth/5));        
        cont.register(new DigitalClock(screenwidth/5,new SimpleAppearance(0.66f,0.2f,1.0f,0.9f)));
        cont.setPreferredSize(new Vector3f(screenwidth/5, screenwidth/5, screenwidth/5/3));
        cont.setTranslation(-Toolkit3D.getToolkit3D().getScreenWidth()/3.0f, Toolkit3D.getToolkit3D().getScreenHeight()/3.0f,0);
        cont.changeVisible(true);        
        /*cont.register(new TubeClock(cont.getScreenWidth(),new Color3f(0.5f,0.5f,0.5f)));*/
    }   
}
