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
 * ResultsContainer.java
 *
 * Created on 26 de agosto de 2005, 20:43
 */

package org.jdesktop.lg3d.apps.googler.gui;
import java.util.Hashtable;
import java.util.LinkedList;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.apps.googler.engine.events.ResultEvent;
import org.jdesktop.lg3d.apps.googler.engine.events.ResultEventListener;
import org.jdesktop.lg3d.apps.googler.engine.events.SearchErrorEvent;
import org.jdesktop.lg3d.apps.googler.engine.events.SearchEvent;
import org.jdesktop.lg3d.apps.googler.engine.internal.ResultElement;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionBooleanInt;
import org.jdesktop.lg3d.utils.action.ActionFloat2;
import org.jdesktop.lg3d.utils.action.ActionInt;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.actionadapter.Float2Scaler;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.c3danimation.ScaleAndRotateChangeVisiblePlugin;
import org.jdesktop.lg3d.utils.eventadapter.KeyPressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseDraggedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseWheelEventAdapter;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 * This class is the container for all the results we obtain, and it's also the direct receiver of them.
 * @author opsi
 */
public class ResultsContainer extends Container3D implements ResultEventListener{
    /**
     * Parent frame reference used to show messages to user and more
     */
    private GooglerFrame3D frame;
    /**
     * Container used to rotate in the X axis
     */
    private Container3D topX;
    /**
     * Container used to rotate in the X axis
     */
    private Container3D topY;
    /**
     * Container used to rotate in the X axis
     */
    private Container3D topZ;
    /**
     * A reference to topY. This is the reference used to add elements to this container
     */
    private Container3D top;
    /**
     * Hashtable used to identify logical results with 3d results
     */
    private Hashtable<ResultElement,ResultElement3D> elementsHash;
    /**
     * A list with all the elements that we have in this container (not related)
     */
    private LinkedList<ResultElement3D> elements3D;
    /**
     * Number of results received since the last search began
     */
    private int numResults = 0;
    /**
     * Maximun number of results to request from google
     */
    private int max_results;
    /**
     * Acumulated X rotation
     */
    private float rotXAcum = 0;
    /**
     * Acumulated Y rotation
     */
    private float rotYAcum = 0;
    /**
     * Boolean indicating if a CAPS is pressed
     */
    private boolean capsPressed;
    private boolean ctrlPressed;
    /**
     * Crates a new ResultsContainer with the given parameters
     * @param max_results Max number of results this container will have
     * @param externalRadius External radius of this container
     * @param frame Parent frame
     */
    public ResultsContainer(int max_results,float externalRadius,GooglerFrame3D frame) {
        this.max_results = max_results;
        this.frame = frame;
        elements3D = new LinkedList();
        elementsHash = new Hashtable();
        Container3D cont = new Container3D();
        //cont.changeVisible(false,0);
        ResultElement3D element;
        cont.setLayout(new ResultsLayoutManager(externalRadius/6, externalRadius/6, externalRadius/8, 15, new Color3f(0f, 0.6f,0.6f), 0.7f));        
        cont.setCursor(Cursor3D.SMALL_MOVE_CURSOR);
        for(int i = 0;i < max_results;i++) {
            element = new ResultElement3D(externalRadius/40, new SimpleAppearance(1-0.025f*i,1-0.0125f*i,0.0180f*i,1.0f), frame);
            element.setElement(null);
            elements3D.add(element);
            cont.addChild(element);
        }
        cont.setRotationAxis(1f, 0,0);
        cont.setRotationAngle((float)Math.toRadians(90));
        
        Component3D parkShape = new Component3D();
        parkShape.addChild(new Box(externalRadius/30,externalRadius/30,externalRadius/30,new SimpleAppearance(1.0f,0.0f,0.5f)));
        parkShape.addListener(new MouseWheelEventAdapter(new ActionInt() {
            public void performAction(LgEventSource src,int val) {
                rotateResults(val);
            }
        }));
        parkShape.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            public void performAction(LgEventSource src) {
                showUseInfo();
            }
        }));
        
        addListener(new KeyPressedEventAdapter(new ActionBooleanInt() {
            public void performAction(LgEventSource src, boolean pressed,int c) {                
                keyPressed(pressed,c);
            }
        }));
        parkShape.addListener(new MouseDraggedEventAdapter(new Float2Scaler(2,2,new ActionFloat2() {
            public void performAction(LgEventSource src, float x, float y) {
                moveContainer(x,y);
            }
        })));
        parkShape.addListener(new MouseEnteredEventAdapter(new ActionBoolean() {
            public void performAction(LgEventSource source, boolean state) {                
                if(!state)
                    keyPressed(false, '?');
            }
        }));
        parkShape.setTranslation(0, -externalRadius/30,0);
        topX = new Container3D();
        topY = new Container3D();
        topZ = new Container3D();
        
        topX.setAnimation(new NaturalMotionAnimation(5000));
        topY.setAnimation(new NaturalMotionAnimation(5000));
        topZ.setAnimation(new NaturalMotionAnimation(5000));
        
        topY.addChild(parkShape);
        topY.addChild(cont);
        
        topZ.addChild(topY);
        topX.addChild(topZ);
        
        addChild(topX);
        topX.setRotationAxis(1f, 0f,0);
        topX.setRotationAngle((float)Math.toRadians(-15));
        setAnimation(new NaturalMotionAnimation(5000,new ScaleAndRotateChangeVisiblePlugin(10000)));
        top = topY;
    }
    /**
     * Moves this container to the given possition referent to frame
     * @param x X displacement
     * @param y Y displacement
     */
    protected void moveContainer(float x, float y) {
        Vector3f pos = getTranslationTo(frame, new Vector3f());
        changeTranslation(pos.x+x,pos.y+y,pos.z);
    }
    
    /**
     * Shows a use tip on the frame
     */
    private void showUseInfo() {
        frame.resultPanel.setTitle("Any element can be used to rotate the results");
        frame.resultPanel.setURL("Only this one can be used to move the results");
        frame.resultPanel.setSnnipet("Use the mouse wheel to rotate the results around Y axis");
        frame.resultPanel.setSummary("Use the mouse wheel with the CAPS key pressed to rotate the results around X axis, and CTRL+CAPS with wheel rotates in Z");
    }
    /**
     * Rotates the results container. If CAPS is hold it rotates in the X axis, else it rotates in Y axis
     * @param clockWise If positive it rotates clockwise, counter else
     */
    protected void rotateResults(int clockWise) {
        Container3D rot;        
        if(capsPressed) {
            if(ctrlPressed) {
                rot = topZ;
                rot.setRotationAxis(0, 0, 1f);
            } else {
                rot = topX;
                rot.setRotationAxis(1f,0,0);
            }
        } else {
            rot = topY;
            rot.setRotationAxis(0,1f,0);
        }
        float newAngle = rot.getRotationAngle() + (float)Math.toRadians(clockWise < 0 ? -30:30);
        rot.changeRotationAngle(newAngle);
    }
    /**
     * Stablish the key pressed and its status
     * @param pressed Indicate if the key is pressed
     * @param c Indicate the key char
     */
    protected synchronized void keyPressed(boolean pressed,int c) {
        if(pressed) {
            if(c == 17) //Control
                ctrlPressed = true;
            else if (c == 16) // Caps
                capsPressed = true;
        } else {            
                ctrlPressed = false;            
                capsPressed = false;
        }
    }
    /**
     * Removes all children from the top container
     */
    protected void removeResults() {
        for(int i = 0; i < elements3D.size();i++)
            elements3D.get(i).setElement(null);
        elementsHash = new Hashtable();
        numResults = 0;
    }
    public void addResultElement(ResultElement newElement) {
        if(!elementsHash.containsKey(newElement) && numResults < max_results){
            ResultElement3D element3d = elements3D.get(numResults++);
            element3d.setElement(newElement);
            elementsHash.put(newElement,element3d);
        } else logger.warning("Result was already in or results full :" +numResults+"/"+max_results);
    }
    public void processEvent(SearchEvent sevt) {        
        if(sevt.checkAccess(this)) {
            if(sevt instanceof SearchErrorEvent) {
                Exception excp = ((SearchErrorEvent)sevt).getException(this);
                frame.resultPanel.setTitle("There was an error :");
                frame.resultPanel.setSummary(excp.getMessage());
                frame.resultPanel.setSnnipet("Click the box to try a new query");
            } else if(sevt instanceof ResultEvent) {
                frame.notifyFirstArrival();
                addResultElement(((ResultEvent) sevt).getResult(this));
            }
        }
    }
}
