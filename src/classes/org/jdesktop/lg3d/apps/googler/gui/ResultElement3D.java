/***************************************************************************
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
 * Created on July 15, 2005, 12:53 PM
 */

package org.jdesktop.lg3d.apps.googler.gui;
import java.awt.Color;
import java.util.LinkedList;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import org.jdesktop.lg3d.apps.googler.engine.GoogleSearchParameters;
import org.jdesktop.lg3d.apps.googler.engine.SearchEngine;
import org.jdesktop.lg3d.apps.googler.engine.events.ResultEvent;
import org.jdesktop.lg3d.apps.googler.engine.events.ResultEventListener;
import org.jdesktop.lg3d.apps.googler.engine.internal.ResultElement;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionInt;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseWheelEventAdapter;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;


/**
 * Represents a ResultElement in the scenegraph. It keeps a reference to the associated ResultElement and to parent GooglerFrame.
 * @author opsi
 */
public class ResultElement3D extends Container3D implements ResultEventListener{
    /**
     * This is the current shape for results
     */
    private Component3D sphere;
    /**
     * Associated ResultElement returned by google
     */
    private ResultElement element;
    
    /**
     * A container to hold the related elements
     */
    private Container3D related;
    private LinkedList<ResultElement3D> relatedElements;
    /**
     * The Frame this element is attached to. This reference si used to call methods that manipulate the scene
     */
    private GooglerFrame3D frame;
    /**
     * Radius for this element
     */
    private float radius;
    /**
     * Id of the thread that is searching related documents
     */
    private long relThreadId;
    /**
     * This is the shape used to close the related elements
     */
    private Component3D closeShape;
    /**
     * Base color for the related elements
     */
    private Color relatedColor;    
    protected Sphere sphereShape;  
    private ResultsLayoutManager lMan;
    /**
     * Creates a new instance of ResultElement3D
     * @param radius Radius to be used for the shape
     * @param app Apperance for the shape
     * @param frame The main frame to which we are attached
     */
    public ResultElement3D(float radius,Appearance app, GooglerFrame3D frame) {
        this.element = null;
        this.frame = frame;
        this.radius = radius;
        relatedElements = new LinkedList();
        TransparencyAttributes ta = new TransparencyAttributes();
        ta.setTransparency(0);
        app.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);        
        app.setTransparencyAttributes(ta);
        
        sphere = new Component3D();
        sphereShape = new Sphere(radius,Sphere.ENABLE_APPEARANCE_MODIFY| Sphere.GENERATE_NORMALS |Sphere.GEOMETRY_NOT_SHARED,app);                
        sphere.addChild(sphereShape);
        addChild(sphere);
        
        sphere.setAnimation(new NaturalMotionAnimation(5000));        
        
        related = new Container3D();
        related.setAnimation(new NaturalMotionAnimation(5000));
        related.setLayout(lMan = new ResultsLayoutManager(radius*3, radius*3, -radius*12, 3, new Color3f(0.5f,0.2f,0.7f), 0.7f));        
        addChild(related);
        
        closeShape = new Component3D();
        closeShape.addChild(new Box(radius/1.5f,radius/1.5f,radius/1.5f,new SimpleAppearance(0,0,1)));
        closeShape.addListener(new MouseClickedEventAdapter(MouseEvent3D.ButtonId.BUTTON3,new ActionNoArg() {
            public void performAction(LgEventSource src) {
                removeRelated();
            }
        }));
        closeShape.addListener(new MouseClickedEventAdapter(false,new ActionNoArg() {
            public void performAction(LgEventSource src) {
                showUseInfo();
            }
        }));
        closeShape.changeVisible(false);
        addChild(closeShape);
        initEvents();
    }
    private void showSubtree(boolean show)
    {
        TransparencyAttributes ta = new TransparencyAttributes(TransparencyAttributes.BLENDED,show ? 0.0f :0.8f);
        ta.setCapability(ta.ALLOW_VALUE_READ | ta.ALLOW_VALUE_WRITE);
        for(int i = 0; i < relatedElements.size();i++)
        {
            relatedElements.get(i).sphereShape.getAppearance().setTransparencyAttributes(ta);
        }
    }
    /**
     * Shows the use info in the parent frame
     */
    private void showUseInfo() {
        frame.resultPanel.setTitle("This element is used to close this subtree");
        frame.resultPanel.setURL("");
        frame.resultPanel.setSnnipet("Right click to close");
        frame.resultPanel.setSummary("");
    }
    /**
     * Eliminates the branch of related elements from the scene
     */
    private void removeRelated() {
        if(relThreadId!=0) {
            SearchEngine.getEngine().stopSearch(relThreadId);
            related.removeAllChildren();
            closeShape.changeVisible(false);
            relatedElements = new LinkedList();
        }
    }
    /**
     * Starts the search of related elements.
     */
    private void findRelated() {
        if(element != null ) {
            if( element.isRelatedInformationPresent()) {
                relatedColor = frame.getNextColor();
                related.setTranslation(0,0,radius*5);
                closeShape.setTranslation(0,0,radius*5);
                MyExtendableLine line = new MyExtendableLine();
                line.setLineStyle(0f, 0.5f,0.7f, 0.5f,3f);
                line.addVertex(new Point3f(0f,0f,0f));
                line.addVertex(new Point3f(0,0,-radius*5));// -radius cause line is under closeShape
                closeShape.addChild(line);
                
                GoogleSearchParameters params = new GoogleSearchParameters();
                params.setQuery("related:"+element.getURL());
                params.setMaxResults(10);
                relThreadId = SearchEngine.getEngine().queryRequest(params, this);
                closeShape.changeVisible(true);
            } else logger.warning("Related info not available for : " + element.getURL());
        } else logger.warning("Element was null while searching related info");
    }
    /**
     * Stablish the element associated with this 3d result.
     * @param element If it's null, the 3d element will dissapear (scale down) from scene, else it will scale to PageRank equivalent.
     */
    public void setElement(ResultElement element) {
        if(element == null) {
            removeRelated();
            sphere.changeScale(0.0f);
            sphere.setVisible(false);
        } else {
            sphere.setVisible(true);
            sphere.changeScale(0.5f + 0.1f*element.getPagerank());
        }
        this.element = element;
    }
    /**
     * Initiates events associated with this ResultElement3D
     */
    private void initEvents() {
        sphere.addListener(new MouseEnteredEventAdapter(new ActionBoolean() {
            public void performAction(LgEventSource source, boolean state) {
                enlarge(state);
            }
        }));
        sphere.addListener(new MouseClickedEventAdapter(false,new ActionNoArg() {
            public void performAction(LgEventSource source) {                
                toFront();
            }
        }));
        sphere.addListener(new MouseClickedEventAdapter(true,new ActionNoArg() {
            public void performAction(LgEventSource source) {                
                findRelated();
            }
        }));      
        sphere.addListener(new MouseWheelEventAdapter(new ActionInt() {
            public void performAction(LgEventSource src,int val) {
                frame.resultsContainer.rotateResults(val);
            }
        })); 
    }
    /**
     * Accesor method that is called from an eventlistener (mouse clicked). It call some methods from frame to make
     * this ResultElement the active one.
     */
    private void toFront() {
        frame.showElementDescription(element);
    }
    /**
     * Accessor method called from an eventlistener (mouse entered). Show some related info (enlarge parent groups).
     * @param state Indicate if the mouse has entered(true) or has left (false) this element.
     */
    private void enlarge(boolean state) {
        if(state) {
            changeTransparency(0.7f);
        } else {
            changeTransparency(0.0f);
        }
    }
    /**
     * Process searchevents. This class is expected to handle all subclasses of
     * SearchEvent.
     * @param sevt Event to process
     */
    public void processEvent(org.jdesktop.lg3d.apps.googler.engine.events.SearchEvent sevt) {
        //We won't handle anything else here
        if(sevt.checkAccess(this) && sevt instanceof ResultEvent) {
            addResultElement(((ResultEvent)sevt).getResult(this));
        }
    }
    /**
     * Adds a new ResultElement to this object.
     * @param newElement element to add
     */
    public void addResultElement(ResultElement newElement) {
        float r = ((float)relatedColor.getRed()/255)-(relatedElements.size()*0.05f);
        float g = ((float)relatedColor.getGreen()/255)-(relatedElements.size()*0.05f);
        float b = ((float)relatedColor.getBlue()/255)-(relatedElements.size()*0.05f);        
        SimpleAppearance app = new SimpleAppearance(r,g,b);
        if(relatedElements.size()==1)
            lMan.lineShape.setLineStyle(r,g,b, 0.7f, 2f);
        ResultElement3D relate = new ResultElement3D(radius*0.9f, app,frame);        
        relate.sphere.addListener(new MouseEnteredEventAdapter(new ActionBoolean() {
            public void performAction(LgEventSource source, boolean state) {                
                showSubtree(state);
            }
        }));
        relate.setElement(newElement);
        relatedElements.add(relate);
        related.addChild(relate);        
    }
}