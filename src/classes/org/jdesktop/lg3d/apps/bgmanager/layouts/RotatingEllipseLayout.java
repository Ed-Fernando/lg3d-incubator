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

package org.jdesktop.lg3d.apps.bgmanager.layouts;

import java.util.ArrayList;
import java.util.HashMap;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.utils.c3danimation.Component3DAnimationFactory;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Component3DAnimation;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;

public class RotatingEllipseLayout implements LayoutManager3D {
    public enum Direction {VERTICAL,HORIZONTAL};
    public enum ComponentDirection {FRONT,CONNECT};
    private Container3D bGcont;
    private ArrayList<Component3D>bGList;
    private int numBg;
    private float rotZ;
    private float smallr,bigr;
    private double radSeg;
    private Component3D node;
    private float frontScale;
    Direction direction;
    ComponentDirection componentDirection;
    private HashMap<Component3D, Component3DAnimation> c3dAnimMap = null;
    private Component3DAnimationFactory c3daFactory = null;
    
    public RotatingEllipseLayout(float smallr,float bigr,Direction direction) {
        this.direction = direction;
        this.smallr = smallr;
        this.bigr = bigr;
        bGList = new ArrayList<Component3D>();
    }
    public RotatingEllipseLayout(float smallr,float bigr,float rotZ,Direction direction) {
        this.direction = direction;
        this.smallr = smallr;
        this.bigr = bigr;
        this.rotZ = rotZ;
        bGList = new ArrayList<Component3D>();
    }
    public RotatingEllipseLayout(float smallr,float bigr,float rotZ,Direction direction, float frontScale) {
        this.direction = direction;
        this.frontScale = frontScale;
        this.smallr = smallr;
        this.bigr = bigr;
        this.rotZ = rotZ;
        bGList = new ArrayList<Component3D>();
    }
    public RotatingEllipseLayout(float smallr,float bigr,float rotZ, Direction direction,
            float frontScale,Component3DAnimationFactory c3daFactory) {
        this.direction = direction;
        this.frontScale = frontScale;
        this.smallr = smallr;
        this.bigr = bigr;
        this.rotZ = rotZ;
        
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
        synchronized(bGList) {
            
            if (constraints != null && constraints instanceof Integer && Bg != null) {
                int step = (Integer)constraints;
                setFrontComp(step);
                return true;
            } else if (constraints == null && Bg != null) {
                setFrontComp(bGList.indexOf(Bg));
                return true;
            }  else if (constraints == null && Bg == null)  {
                return false;
            }
        }
        return false;
    }
    public void setContainer(Container3D bGcont) {
        this.bGcont = bGcont;
        
    }
    public void layoutContainer() {
        int numBg = bGList.size();	
        double radSeg = (float) Math.PI * 2.0 / numBg;
	
        for(int i = 0;i<numBg;i++) {
            int idx = i % numBg;
            Component3D comp = bGList.get(idx);
            if (idx == 0) {
               comp.changeScale(frontScale,150);		
            } else {
                comp.changeScale(1.0f,150);
            }
	    
          double aa = Math.PI * 1.8 / numBg * idx + ((idx !=0)?(Math.PI * 0.1):(0));
            switch (direction) {
                case HORIZONTAL: {
                    float x =  bigr * (float)Math.sin(aa);
                    float z =  smallr * (float)Math.cos(aa);
                    float y =  z* (float) Math.tan(rotZ);
                    
		    comp.changeTranslation(x,y,z,1000);		   
		    
		    if (idx==0 ){
			comp.changeTranslation(x, y,z +0.004f,1000);
		    } 
                    break;
                     }
                case VERTICAL: {
                    float y =  bigr * (float)Math.sin(aa);
                    float z =  smallr * (float)Math.cos(aa);
                    float x =  z* (float) Math.tan(rotZ);
                    if (idx == 0) {
                        z += 0.002f;
                    }
                    comp.changeTranslation(x,y,z,1000);
                    break;
                }
            }
         /*   switch (componentDirection) {
            case CONNECT: {
                 comp.setRotationAxis(0.0f,1.0f,0.0f);
                 comp.changeRotationAngle((float)(-aa));
            break;
            }
             case FRONT: {
                //TODO
            break;
          }
        }*/
        }
    }
    private void setFrontComp(int index){
        boolean rotating = true;
        if (index <= bGList.size() /2 && index !=0) {
            while(rotating) {
                Component3D node = (Component3D) bGList.get(0);
                bGList.remove(node);
                bGList.add(bGList.size(),node);
                index --;
                if (index == 0) {
                    rotating = false;
                }
            }
        } else if(index > bGList.size() /2 && index !=0) {
            while(rotating) {
                Component3D node = (Component3D) bGList.get(bGList.size()-1);
                bGList.remove(node);
                bGList.add(0,node);
                index ++;
                if (index == bGList.size()) {
                    rotating = false;
                }
            }
        }
    }
}

