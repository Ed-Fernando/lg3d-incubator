package org.jdesktop.lg3d.apps.bgmanager.layouts;

import java.util.ArrayList;
import java.util.HashMap;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.c3danimation.Component3DAnimationFactory;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Component3DAnimation;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;

public class RaisingSegmentedWheelLayout implements LayoutManager3D,RaiseAction {
    private Container3D bGcont;
    private ArrayList<Component3D>bGList;
    private int numBg;
    private float startRadius,endRadius;
    private double radSeg;
    private Component3D node;
    private float size;
    private boolean isRaise;
    private HashMap<Component3D, Component3DAnimation> c3dAnimMap = null;
    private Component3DAnimationFactory c3daFactory = null;
    
    
    public RaisingSegmentedWheelLayout(float startRadius,float endRadius,float size,
            Component3DAnimationFactory c3daFactory) {
        
        isRaise = false;
        this.startRadius = startRadius;
        this.endRadius = endRadius;
        this.size = size;
        
        if(startRadius > endRadius){
            throw new IllegalArgumentException("startRadius must be smaller then endRadius");
        }
        
        if(startRadius == endRadius){
            throw new IllegalArgumentException("radius cannot be the same");
        }
        
        bGList = new ArrayList<Component3D>();
        if (c3daFactory == null) {
            throw new IllegalArgumentException("the factory argument cannot be null");
        }
        this.c3daFactory = c3daFactory;
        c3dAnimMap = new HashMap<Component3D, Component3DAnimation>();
    }
    
    
    public void addLayoutComponent(Component3D Bg, Object constraints) {
        bGList.add(0,Bg);
        if (c3daFactory != null) {
            Component3DAnimation prev = Bg.getAnimation();
            Component3DAnimation c3da = c3daFactory.createInstance();
            Bg.setAnimation(c3da);
            assert(c3dAnimMap != null);
            c3dAnimMap.put(Bg, prev);
        }
    }
    public void removeLayoutComponent(Component3D bG) {
        bGList.remove(bG);
        if (c3daFactory != null) {
            assert(c3dAnimMap != null);
            Component3DAnimation prev = c3dAnimMap.remove(bG);
            bG.setAnimation(prev);
        }
    }
    public boolean rearrangeLayoutComponent(Component3D Bg, Object constraints) {
        int number = bGList.size();
        
        return false;
    }
    public void setContainer(Container3D bGcont) {
        this.bGcont = bGcont;
        
    }
    public void layoutContainer() {
        int numBg = bGList.size();
        Vector3f vec = new Vector3f(0.0f,0.0f,0.0f);
        double radSeg = (float) Math.PI * 2.0 / numBg;
        for(int i = 0;i<numBg;i++) {
            int idx = i % numBg;
            Component3D comp = bGList.get(idx);
            if(isRaise){
                double aa = (Math.PI * (endRadius - startRadius) / numBg * idx) + startRadius;
                float x =  size * (float)Math.sin(aa);
                float y =  size * (float)Math.cos(aa);
                float z =  0.0f; //TODO
                comp.changeTranslation(x,y,z,1000);
                comp.changeScale(1.0f);
                //comp.changeTransparency(1.0f);
            } else {
                vec = bGcont.getTranslation(vec);
                comp.changeTranslation(vec.x,vec.y,vec.z-0.001f);
                comp.changeScale(0.001f);
               // comp.changeTransparency(0.0f);
            }
        }
    }
    
    public synchronized void raise(boolean isRaise) {
        this.isRaise = isRaise;
        this.layoutContainer();
    }
}

