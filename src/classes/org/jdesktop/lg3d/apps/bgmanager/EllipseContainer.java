/*
 * EllipseContainer.java
 *
 * Created on July 5, 2006, 3:19 PM
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

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import javax.vecmath.Color4f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.apps.bgmanager.layouts.RotatingEllipseLayout;
import org.jdesktop.lg3d.scenemanager.utils.appcontainer.NaturalMotionF3DAnimationFactory;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.utils.action.ActionBooleanInt;
import org.jdesktop.lg3d.utils.action.ActionInt;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.action.ScaleActionBoolean;
import org.jdesktop.lg3d.utils.eventadapter.KeyPressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseWheelEventAdapter;
import org.jdesktop.lg3d.utils.shape.Disc;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;
import org.jdesktop.lg3d.wg.ModelLoader;
import org.jdesktop.lg3d.wg.event.LgEventSource;


public class EllipseContainer extends Container3D implements BgSorting{
    private final float CONTAINER_SIZE = 0.08f;
    private final float FRONT_SCALE = 1.5f;
    private LayoutManager3D ellipseLayout;
    private Container3D  compContainer,menuContainer;
    private Component3D leftButton,rightButton,animButton,okButton;
    private BgTypes bgtype;
    private ArrayList <BgLgComponent>bgCompList;
    private BgLgComponent tmpBg;
    private final int STEP = 1;
    private Preferences prefs;
    private AnimThread anim;
    private String modelDir;
    private Color4f textColor = new Color4f(0.6f,1.0f,0.6f,1.0f);
    
    public EllipseContainer(ArrayList bgCompList , Preferences prefs) {
        
        this.bgCompList=bgCompList;
        this.prefs = prefs;
        
        modelDir = System.getProperty("lg.resourcedir") + "/models/BgManager";
        modelDir.replace('/', File.separatorChar);
        
        compContainer = new Container3D();
        menuContainer = new Container3D();
        
        leftButton = leftButton();
        menuContainer.addChild(leftButton);
        rightButton = rightButton();
        menuContainer.addChild(rightButton);
        okButton = okButton();
        menuContainer.addChild(okButton);
        animButton = animButton();
        menuContainer.addChild(animButton);
        menuContainer.setCursor(Cursor3D.SMALL_CURSOR);
        
        setPreferredSize(new Vector3f(CONTAINER_SIZE, CONTAINER_SIZE, CONTAINER_SIZE));
        
        ellipseLayout = new RotatingEllipseLayout(
                CONTAINER_SIZE / 2, CONTAINER_SIZE / 2,(float)Math.toRadians(-30),
                RotatingEllipseLayout.Direction.HORIZONTAL,FRONT_SCALE,
                new NaturalMotionF3DAnimationFactory(150));
        
        compContainer.setLayout(ellipseLayout);
        
        this.addListener(
                new MouseWheelEventAdapter(
                new ActionInt() {
            public void performAction(LgEventSource source, int value) {
                if(value >0){
                    nextBg();
                } else {
                    prewievBg();
                }
            }
        }));
        
        this.addListener(
                new KeyPressedEventAdapter(
                new ActionBooleanInt() {
            public void performAction(LgEventSource source,
                    boolean pressed, int key) {
                if (pressed && key == KeyEvent.VK_LEFT) {
                    prewievBg();
                } else if(pressed && key == KeyEvent.VK_RIGHT){
                    nextBg();
                }
            }
        }));
        this.addChild(compContainer);
        this.addChild(menuContainer);
    }
    
    public void showBg(BgTypes type) {
        bgtype = type;
        if(compContainer.numChildren() != 0) {
            compContainer.removeAllChildren();
        } else if (this == null)  {
            compContainer.setLayout(ellipseLayout);
        }
        
        for (int i=0;i<bgCompList.size();i++) {
            tmpBg = bgCompList.get(i);
            tmpBg.init();
            if(tmpBg.getBgTpe() == bgtype || bgtype == BgTypes.ALLBG) {
                
                tmpBg.addListener(
                        new MouseClickedEventAdapter(
                        new ActionNoArg() {
                    public void performAction(LgEventSource source) {
                        rearaangeBg((BgLgComponent)source);
                    }
                }));
                tmpBg.addListener(
                        new MouseClickedEventAdapter(true,
                        new ActionNoArg() {
                    public void performAction(LgEventSource source) {
                        BgLgComponent initialBg =(BgLgComponent)source;
                        initialBg.setBackground();
                        prefs.put("initialBackgroundConfigFileURL",initialBg.getConfigURL());
                    }
                }));
                tmpBg.setMouseEventPropagatable(true);
                compContainer.addChild(tmpBg);
                compContainer.revalidate();
               
            }
        }
    }
    private BgLgComponent getComp(int witch) {
        
        BgLgComponent tmpBgComp = null;
        for(int i=0; i< compContainer.numChildren(); i++) {
            tmpBgComp = (BgLgComponent)compContainer.getChild(i);
            if (tmpBgComp.getFinalScale() > 1.25f) {
                if (witch == 0){
                    return tmpBgComp;
                } else if (witch > 0 && witch < compContainer.numChildren()){
                    tmpBgComp = (BgLgComponent) compContainer.getChild(i+witch-1);
                    return tmpBgComp;
                    
                } else if (witch < 0 && witch > - compContainer.numChildren()){
                    
                    tmpBgComp = (BgLgComponent)compContainer.getChild(i-witch-1);
                    return tmpBgComp;
                }
            }
        }
        return tmpBgComp;
    } 
    private void nextBg() {
        int idx = compContainer.indexOfChild(getComp(0));
        Component3D node;
        if(idx == (compContainer.numChildren()-1)){
            node = (Component3D)compContainer.getChild(0);
        } else {
            node = (Component3D)compContainer.getChild(idx+1);
        }
        
        compContainer.rearrangeChildLayout(node,null);
        getComp(STEP);
        
    }
    private void prewievBg() {
        int idx = compContainer.indexOfChild(getComp(0));
        Component3D node;
        if(idx == 0){
            node = (Component3D)compContainer.getChild(compContainer.numChildren()-1);
        } else {
            node = (Component3D)compContainer.getChild(idx-1);
        }
        compContainer.rearrangeChildLayout((Component3D) node,null);
        getComp(-STEP);
        
    }
    private void rearaangeBg(BgLgComponent clickedBg) {
        compContainer.rearrangeChildLayout(clickedBg,null);
        
    }
    private class AnimThread extends Thread {
        private boolean animating=true;
        
        public boolean isStoped() {
            return animating;
        }
        public synchronized void state() {
            if (animating) {
                animating= false;
                notify();
            } else {
                animating= true;
            }
        }
        public void run() {
            while (true) {
                try {
                    anim.sleep(600);
                    synchronized(this) {
                        while (animating)
                            anim.wait();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                nextBg();
            }
        }
    }
    private void playBackgrounds() {
        if(anim == null) {
            anim = new AnimThread();
            anim.start();
        }
        anim.state();
    }
    private Component3D leftButton(){
        Component3D leftButton = new Component3D();
        Matrix4f m4f = new Matrix4f();
        m4f.rotX( (float)Math.toRadians(-40));
        ModelLoader model = new ModelLoader(
                modelDir, "pfeil_krumm2.obj", com.sun.j3d.loaders.objectfile.ObjectFile.class,m4f);
        model.setScale(0.005f);
        
        BranchGroup bg = new BranchGroup();
        bg.addChild( model );
        leftButton.addChild( bg );
        leftButton.setTranslation(-0.023f, -0.045f, 0.04f);
        leftButton.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                prewievBg();
            }
        }));
        leftButton.addListener(
                new MouseEnteredEventAdapter(
                new ScaleActionBoolean(leftButton,1.1f, 100)));
        leftButton.setMouseEventPropagatable(true);
        return leftButton;
    }
    private Component3D rightButton(){
        
        Component3D rightButton = new Component3D();
        Matrix4f changeAxis = new Matrix4f();
        Matrix4f m4f = new Matrix4f();
        m4f.rotY( (float)Math.toRadians(180) );
        changeAxis.rotX( (float)Math.toRadians(40));
        changeAxis.mul(m4f,changeAxis);
        ModelLoader model;
        model = new ModelLoader(modelDir, "pfeil_krumm2.obj", com.sun.j3d.loaders.objectfile.ObjectFile.class,changeAxis);
        model.setScale(0.005f);
        BranchGroup bg = new BranchGroup();
        bg.addChild( model );
        rightButton.addChild( bg );
        rightButton.setTranslation(0.023f, -0.045f, 0.04f);
        rightButton.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                nextBg();
            }
        }));
        rightButton.addListener(
                new MouseEnteredEventAdapter(
                new ScaleActionBoolean(rightButton,1.1f, 100)));
        rightButton.setMouseEventPropagatable(true);
        return rightButton;
    }
    private Component3D okButton(){
        Component3D okButton = new Component3D();
        
        Appearance app = new SimpleAppearance(
                1.0f, 1.0f, 1.0f, 1.0f,
                SimpleAppearance.ENABLE_TEXTURE
                | SimpleAppearance.DISABLE_CULLING);
        try {
            
            ((SimpleAppearance)app).setTexture(this.getClass().getClassLoader().getResource("org/jdesktop/lg3d/apps/bgmanager/res/okButton.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        okButton.addChild(new Disc(0.015f, 36, app));
        
        
        okButton.setTranslation(0.0f, -0.05f, 0.048f);
        okButton.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                BgLgComponent initialBg =(BgLgComponent)getComp(0);
                initialBg.setBackground();
                prefs.put("initialBackgroundConfigFileURL",initialBg.getConfigURL());
                
            }
        }));
        okButton.addListener(
                new MouseEnteredEventAdapter(
                new ScaleActionBoolean(okButton,1.2f, 100)));
        okButton.addListener(
                new MousePressedEventAdapter(
                new ScaleActionBoolean(okButton,1.15f, 100)));
        okButton.setMouseEventPropagatable(true);
        return okButton;
    }
    private Component3D animButton(){
        Component3D animButton= new Component3D();
        Matrix4f changeAxis = new Matrix4f();
        Matrix4f m4f = new Matrix4f();
        m4f.rotY( (float)Math.toRadians(0) );
        changeAxis.rotX( (float)Math.toRadians(180));
        changeAxis.mul(m4f,changeAxis);
        ModelLoader model =  new ModelLoader(modelDir,
                "refresh.obj", com.sun.j3d.loaders.objectfile.ObjectFile.class);
        model.setScale(0.0015f);
        BranchGroup bg = new BranchGroup();
        bg.addChild( model );
        animButton.addChild(bg);
        animButton.setTranslation(0.0f, 0.0f, 0.0f);
        animButton.addListener(
                new MouseClickedEventAdapter(
                new ActionNoArg() {
            public void performAction(LgEventSource source) {
                playBackgrounds();
            }
        }));
        animButton.addListener(
                new MouseEnteredEventAdapter(
                new ScaleActionBoolean(animButton,1.3f, 100)));
        animButton.setMouseEventPropagatable(true);
        return animButton;
    }
    
    public Vector3f getViewMenuTranslation() {
        return new Vector3f(0.06f, -0.015f, 0.0f);
    }
    
    public Vector3f getSortingMenuTranslation() {
        return new Vector3f(-0.06f, -0.015f, 0.0f);
    }
    
    public Vector3f getCloseButtonTranslation() {
        return new Vector3f(0.12f, 0.03f, -0.02f);
    }

   

    public void changeEnableContainer(boolean enable) {
        if(enable){
        menuContainer.changeVisible(false,1000);
        }else{
        menuContainer.changeVisible(true,1000);
        }
    }

    public void setBgCompList(ArrayList<BgLgComponent> BgList) {
        this.bgCompList=BgList;
    }
}
