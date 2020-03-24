/*
 * MenuContainer.java
 *
 * Created on July 5, 2006, 3:25 PM
 * Project Looking Glass
 *
 * @author Radek Kierner
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 */

package org.jdesktop.lg3d.apps.bgmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.vecmath.Color4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.apps.bgmanager.layouts.RaisingSegmentedWheelLayout;
import org.jdesktop.lg3d.scenemanager.utils.appcontainer.NaturalMotionF3DAnimationFactory;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.ScaleActionBoolean;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseHoverEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.shape.Disc;
import org.jdesktop.lg3d.utils.shape.GlassyText2D;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.ModelLoader;
import org.jdesktop.lg3d.wg.event.LgEventSource;


public class SharedMenuContainer extends Container3D{
    private Component3D closeButton,layeredButton,panoramaButton,simpleButton,allButton,sortButton;
    private Component3D viewsButton,tilleButton,ellipseButton,mouseButton;
    private Container3D sortContainer,viewsContainer,sortContainerHelper,viewsContainerHelper;
    private BgSorting cont;
    private RaisingSegmentedWheelLayout sortWheel,viewsWheel;
    private Vector3f sortMenuTrans,viewsMenuTrans,closeButtonTrans;
    private BgFrame frame;
  
    
    public SharedMenuContainer(BgSorting cont,BgFrame frame) {
        
 /*
  * this --> SortContainerHelper -->  SortContainer --> MenuComponents...
  *  |               |                      |
  *  |    SortButton,CloseButton        WheelLayout
  *  |
  *   --> ViewvContainerHelper -->   ViewsContainer --> ViewsComponents...
  *                  |                      |
  *              ViewsButton            WheelLayout
  */
        
        this.frame = frame;
        this.cont = cont;
        this.setAnimation(new NaturalMotionAnimation(2000));
        this.setCursor(Cursor3D.SMALL_CURSOR);
        
        sortContainerHelper = new Container3D();
        sortContainer = new Container3D();
        sortContainer.setAnimation(new NaturalMotionAnimation(700));
        sortContainer.setMouseEventPropagatable(true);
        sortWheel = new RaisingSegmentedWheelLayout(3.0f,4.3f,0.012f,new NaturalMotionF3DAnimationFactory(1000));
        sortContainer.setLayout(sortWheel);
        
        
        
        closeButton = closeButton();
        sortContainerHelper.addChild(closeButton);
        sortButton = chooseButton();
        sortContainerHelper.addChild(sortButton);
        
        layeredButton = layeredButton();
        sortContainer.addChild(layeredButton);
        panoramaButton = panoramaButton();
        sortContainer.addChild(panoramaButton);
        simpleButton = simpleButton();
        sortContainer.addChild(simpleButton);
        allButton = allButton();
        sortContainer.addChild(allButton);
        
        sortContainerHelper.addChild(sortContainer);
        
        this.addChild(sortContainerHelper);
        
        
        MouseHoverEventAdapter sortHoover = new MouseHoverEventAdapter(250,2000,50,
                new ActionBoolean() {
            public void performAction(LgEventSource lgEventSource, boolean b) {
                if(b){
                    sortWheel.raise(true);
                    sortButton.setMouseEventEnabled(false);
                    sortButton.changeScale(0.5f);
                }else{
                    sortWheel.raise(false);
                    sortButton.setMouseEventEnabled(true);
                    sortButton.changeScale(1.0f);
                }
            }
        });
        sortContainer.addListener(sortHoover);
        sortButton.addListener(sortHoover);
        
        
        viewsContainerHelper = new Container3D();
        viewsContainer = new Container3D();
        viewsContainer.setAnimation(new NaturalMotionAnimation(700));
        viewsContainer.setMouseEventPropagatable(true);
        viewsWheel = new RaisingSegmentedWheelLayout(0.01f,1.6f,0.01f,new NaturalMotionF3DAnimationFactory(1000));
        viewsContainer.setLayout(viewsWheel);
        
        viewsButton = viewsButton();
        viewsContainerHelper.addChild(viewsButton);
        tilleButton = tilleButton();
        viewsContainer.addChild(tilleButton);
        ellipseButton = ellipseButton();
        viewsContainer.addChild(ellipseButton);
        mouseButton = mouseButton();
        viewsContainer.addChild(mouseButton);
        
        
        viewsContainerHelper.addChild(viewsContainer);
        this.addChild(viewsContainerHelper);
        
        MouseHoverEventAdapter viewsHoover = new MouseHoverEventAdapter(250,2000,50,
                new ActionBoolean() {
            public void performAction(LgEventSource lgEventSource, boolean b) {
                if(b){
                    viewsWheel.raise(true);
                    viewsButton.setMouseEventEnabled(false);
                    viewsButton.changeScale(0.5f);
                }else{
                    viewsWheel.raise(false);
                    viewsButton.setMouseEventEnabled(true);
                    viewsButton.changeScale(1.0f);
                }
            }
        });
        
        viewsContainer.addListener(viewsHoover);
        viewsButton.addListener(viewsHoover);
        
        changeComponentTranslation(cont);
    }
    
    private Component3D closeButton(){
        Component3D closeButton= new Component3D();
        Appearance app = new SimpleAppearance(
                1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE
                | SimpleAppearance.DISABLE_CULLING);
        try {
            
            ((SimpleAppearance)app).setTexture(this.getClass().getClassLoader().getResource("org/jdesktop/lg3d/apps/bgmanager/res/closeButton.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeButton.addChild(new Disc(0.012f, 36, app));
        closeButton.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                frame.close();
            }
        }));
        closeButton.addListener(
                new MouseEnteredEventAdapter(
                new ScaleActionBoolean(closeButton,1.3f, 100)));
        return closeButton;
    }
    private Component3D layeredButton(){
        Component3D layeredButton= new Component3D();
        Appearance app = new SimpleAppearance(
                1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE
                | SimpleAppearance.DISABLE_CULLING);
        try {
            
            ((SimpleAppearance)app).setTexture(this.getClass().getClassLoader().getResource("resources/images/icon/leaf.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        layeredButton.addChild(new Disc(0.01f, 36, app));
        layeredButton.setAnimation(new NaturalMotionAnimation(700));
        layeredButton.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                cont.showBg(BgTypes.LAYEREDBG);
                frame.setBgType(BgTypes.LAYEREDBG);
            }
        }));
        layeredButton.addListener(
                new MouseEnteredEventAdapter(
                new ScaleActionBoolean(layeredButton,1.3f, 100)));
        layeredButton.addListener(
                new MousePressedEventAdapter(
                new ScaleActionBoolean(layeredButton,1.15f, 100)));
        layeredButton.setMouseEventPropagatable(true);
        return layeredButton;
    }
    private Component3D simpleButton(){
        Component3D simpleButton= new Component3D();
        Appearance app = new SimpleAppearance(
                1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE
                | SimpleAppearance.DISABLE_CULLING);
        try {
            
            ((SimpleAppearance)app).setTexture(this.getClass().getClassLoader().getResource("resources/images/icon/star.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        simpleButton.addChild(new Disc(0.01f, 36, app));
        simpleButton.setAnimation(new NaturalMotionAnimation(700));
        simpleButton.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                cont.showBg(BgTypes.SIMPLEBG);
                frame.setBgType(BgTypes.SIMPLEBG);
            }
        }));
        simpleButton.addListener(
                new MousePressedEventAdapter(
                new ScaleActionBoolean(simpleButton,1.15f, 100)));
        simpleButton.addListener(
                new MouseEnteredEventAdapter(
                new ScaleActionBoolean(simpleButton,1.3f, 100)));
        simpleButton.setMouseEventPropagatable(true);
        return simpleButton;
    }
    private Component3D panoramaButton(){
        Component3D panoramaButton= new Component3D();
        Appearance app = new SimpleAppearance(
                1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE
                | SimpleAppearance.DISABLE_CULLING);
        try {
            
            ((SimpleAppearance)app).setTexture(this.getClass().getClassLoader().getResource("resources/images/icon/hoover.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        panoramaButton.addChild(new Disc(0.01f, 36, app));
        panoramaButton.setAnimation(new NaturalMotionAnimation(700));
        panoramaButton.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                cont.showBg(BgTypes.PANORAMABG);
                frame.setBgType(BgTypes.PANORAMABG);
            }
        }));
        panoramaButton.addListener(
                new MousePressedEventAdapter(
                new ScaleActionBoolean(panoramaButton,1.15f, 100)));
        panoramaButton.addListener(
                new MouseEnteredEventAdapter(
                new ScaleActionBoolean(panoramaButton,1.3f, 100)));
        panoramaButton.setMouseEventPropagatable(true);
        return panoramaButton;
    }
    private Component3D allButton(){
        Component3D allButton= new Component3D();
        try {
            allButton.addChild(new ImagePanel(getClass().getClassLoader().getResource("resources/images/icon/bgicon2.png"),0.01f,0.01f,false));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        allButton.setAnimation(new NaturalMotionAnimation(700));
        allButton.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                cont.showBg(BgTypes.ALLBG);
                frame.setBgType(BgTypes.ALLBG);
            }
        }));
        allButton.addListener(
                new MousePressedEventAdapter(
                new ScaleActionBoolean(allButton,1.15f, 100)));
        allButton.addListener(
                new MouseEnteredEventAdapter(
                new ScaleActionBoolean(allButton,1.3f, 100)));
        allButton.setMouseEventPropagatable(true);
        return allButton;
    }
    private Component3D chooseButton(){
        Component3D chooseButton= new Component3D();
        chooseButton.setAnimation(new NaturalMotionAnimation(700));
        try {
            chooseButton.addChild(new ImagePanel(getClass().getClassLoader().getResource("resources/images/icon/bgicon.png"),0.01f,0.01f,false));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        chooseButton.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                sortWheel.raise(true);
            }
        }));
        
        chooseButton.addListener(
                new MousePressedEventAdapter(
                new ScaleActionBoolean(chooseButton,1.15f, 100)));
        chooseButton.addListener(
                new MouseEnteredEventAdapter(
                new ScaleActionBoolean(chooseButton,1.3f, 100)));
        chooseButton.setMouseEventPropagatable(true);
        return chooseButton;
    }
    private Component3D viewsButton(){
        Component3D viewsButton= new Component3D();
        viewsButton.setAnimation(new NaturalMotionAnimation(700));
        try {
            viewsButton.addChild(new ImagePanel(getClass().getClassLoader().getResource("resources/images/icon/bgicon.png"),0.01f,0.01f,false));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        viewsButton.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                viewsWheel.raise(true);
            }
        }));
        
        viewsButton.addListener(
                new MousePressedEventAdapter(
                new ScaleActionBoolean(viewsButton,1.15f, 100)));
        viewsButton.addListener(
                new MouseEnteredEventAdapter(
                new ScaleActionBoolean(viewsButton,1.3f, 100)));
        viewsButton.setMouseEventPropagatable(true);
        return viewsButton;
    }
    private Component3D ellipseButton(){
        Component3D ellipseButton= new Component3D();
        Appearance app = new SimpleAppearance(
                1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE
                | SimpleAppearance.DISABLE_CULLING);
        try {
            
            ((SimpleAppearance)app).setTexture(this.getClass().getClassLoader().getResource("resources/images/icon/hoover.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ellipseButton.addChild(new Disc(0.01f, 36, app));
        ellipseButton.setAnimation(new NaturalMotionAnimation(700));
        ellipseButton.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                frame.showView(BgFrame.BgViews.ELLIPSE);
                  System.out.println("ELLIPSE");
            }
        }));
        ellipseButton.addListener(
                new MousePressedEventAdapter(
                new ScaleActionBoolean(ellipseButton,1.15f, 100)));
       ellipseButton.addListener(
                new MouseEnteredEventAdapter(
                new ScaleActionBoolean(ellipseButton,1.3f, 100)));
        ellipseButton.setMouseEventPropagatable(true);
        return ellipseButton;
    }
    private Component3D tilleButton(){
        Component3D tilleButton= new Component3D();
        Appearance app = new SimpleAppearance(
                1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE
                | SimpleAppearance.DISABLE_CULLING);
        try {
            
            ((SimpleAppearance)app).setTexture(this.getClass().getClassLoader().getResource("resources/images/icon/hoover.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tilleButton.addChild(new Disc(0.01f, 36, app));
        tilleButton.setAnimation(new NaturalMotionAnimation(700));
        tilleButton.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                frame.showView(BgFrame.BgViews.TILLED);
                  System.out.println("TILLE");
            }
        }));
        tilleButton.addListener(
                new MousePressedEventAdapter(
                new ScaleActionBoolean(tilleButton,1.15f, 100)));
        tilleButton.addListener(
                new MouseEnteredEventAdapter(
                new ScaleActionBoolean(tilleButton,1.3f, 100)));
        tilleButton.setMouseEventPropagatable(true);
        return tilleButton;
    }
    private Component3D mouseButton(){
        Component3D mouseButton= new Component3D();
        Appearance app = new SimpleAppearance(
                1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE
                | SimpleAppearance.DISABLE_CULLING);
        try {
            
            ((SimpleAppearance)app).setTexture(this.getClass().getClassLoader().getResource("resources/images/icon/hoover.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mouseButton.addChild(new Disc(0.01f, 36, app));
        mouseButton.setAnimation(new NaturalMotionAnimation(700));
        mouseButton.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                frame.showView(BgFrame.BgViews.MOUSE_TRANSLATION);
                System.out.println("MOUSE");
            }
        }));
        mouseButton.addListener(
                new MousePressedEventAdapter(
                new ScaleActionBoolean(mouseButton,1.15f, 100)));
        mouseButton.addListener(
                new MouseEnteredEventAdapter(
                new ScaleActionBoolean(mouseButton,1.3f, 100)));
        mouseButton.setMouseEventPropagatable(true);
        return mouseButton;
    }
    public void changeComponentTranslation(BgSorting cont){
        this.cont = cont;
        sortMenuTrans = new Vector3f();
        sortMenuTrans = cont.getSortingMenuTranslation();
        sortContainerHelper.changeTranslation(sortMenuTrans,1000);
        
        closeButtonTrans = new Vector3f();
        closeButtonTrans = cont.getCloseButtonTranslation();
        closeButton.changeTranslation(closeButtonTrans,1000);
        
        viewsMenuTrans = new Vector3f();
        viewsMenuTrans = cont.getViewMenuTranslation();
        viewsContainerHelper.changeTranslation(viewsMenuTrans,1000);
    }
}

