/*
 * LG3D Incubator Project - gol3d
 * A simple Game of Life for Looking Glass 3D
 *
 * $RCSfile: GolGridComponent.java,v $
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision: 1.4 $
 * $Date: 2006-07-28 23:29:32 $
 * Author: Van der Haegen Mathieu (dwarfy)
 */

package org.jdesktop.lg3d.apps.gol3d;

import org.jdesktop.lg3d.wg.*;
import org.jdesktop.lg3d.utils.shape.*;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Switch;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;


/////////GAME OF LIFE 3D: simple try with lg3d api !

////////DWARFY

///////to do : 
////control panel
//finish load startgrid from file or do it with the mouse ?
//javadoc it
//use linked list instead of vector ?
public class GolGridComponent extends Component3D
{
    //Items
    public final static float itemBorderRatio = 0.2f;
    public final static float itemHeight = 0.01f;
    private float itemWidth;
    private float itemInnerWidth;
    private float itemBorderWidth;
    private int dim;
    private int boolMatrixSize;
    private Switch switchNode;
    private BitSet boolMatrix;    
    
    private GolConfig gcfg;
    
    private static Logger logger = Logger.getLogger("lg.gol3d");
    
    GolGridComponent(GolConfig cfg) {
        gcfg = cfg;
    }
    
    public void setItemWidth(float itemW) {
        itemWidth = itemW;
    }
    public void setItemInnerWidth(float itemI) {
        itemInnerWidth = itemI;
    }
    public void setItemBorderWidth(float itemB) {
        itemBorderWidth = itemB;
    }
    public void setDim(int d){
        dim = d;
    }
    public void setBoolMatrixSize(int boolM) {
        boolMatrixSize =boolM;
    }
    public void setBoolMatrix(BitSet b) {
        boolMatrix = b;
    }
            
    public void setGrid(String fname) {
        killGrid();
        getGridConfig(fname);
        
    }
    
   private void killGrid() {
        this.removeAllChildren();
        boolMatrix = null ;
        
        //necessary ?
        //System.runFinalization();
        //System.gc(); 
    }
    
    private void getGridConfig(String fname) {
            GolGridReader cr = new GolGridReader();
            boolMatrix = new BitSet();
            cr.setBoolMatrix(boolMatrix);
            cr.setFileName(fname);
            cr.setGolGridComp(this);
            cr.start();
    }
    public void build() {
        SimpleAppearance itemApp = new SimpleAppearance(gcfg.getBarsColorR(),gcfg.getBarsColorG(),gcfg.getBarsColorB(),gcfg.transparency);        
        
        Java3DGraph j3dwrap = new Java3DGraph();
        BranchGroup objRoot = new BranchGroup();
        
        switchNode = new Switch(Switch.CHILD_MASK);
        switchNode.setChildMask(boolMatrix);
        switchNode.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        switchNode.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        
        for (int x=0;x<dim;x++) {
        	for (int y=0;y<dim;y++) {
                    TransformGroup a = new TransformGroup();    

                    Box b = new Box(itemInnerWidth / 2,itemHeight / 2,itemInnerWidth / 2,Box.GENERATE_NORMALS,itemApp);
                    a.addChild((javax.media.j3d.Node) b.getWrapped().getWrapped()); //?

                    Vector3f v = new Vector3f(((x * itemWidth) + itemBorderWidth + (itemInnerWidth/2)),0f,((y * itemWidth) + itemBorderWidth + (itemInnerWidth /2 )));
                    Transform3D t = new Transform3D();
                    t.setTranslation(v);                        
                    a.setTransform(t);

                    switchNode.addChild(a);
        	}
        }
        
        objRoot.addChild(switchNode);
        j3dwrap.addJ3dChild(objRoot);
        
        
        j3dwrap.setTranslation((-0.5f * GolGroundComponent.groundWidth) + GolGroundComponent.groundBorderWidth, (GolGroundComponent.groundHeight / 2.0f) + (itemHeight / 2.0f) ,(-0.5f * GolGroundComponent.groundWidth) + GolGroundComponent.groundBorderWidth);
        
        
        this.addChild(j3dwrap);
        
    }
    
    


        
    private int countAliveNeighbours(int _x,int _y) {
        int count = 0;
        
        if (_x == 0) {
            if (_y == 0) {
                if(boolMatrix.get((_x * dim) + _y + 1)) { count++; }
                if(boolMatrix.get(((_x + 1) * dim) + _y)) { count++; }
                if(boolMatrix.get(((_x + 1) * dim)+ _y + 1)) { count++; }            
            }

            else if (_y == dim-1) {
                if(boolMatrix.get((_x * dim) + _y - 1)) { count++; } 
                if(boolMatrix.get(((_x + 1) * dim) + _y - 1)) { count++; }
                if(boolMatrix.get(((_x + 1) * dim) + _y)) { count++; }            
            }
            else {
                if(boolMatrix.get((_x * dim) + _y - 1)) { count++; }
                if(boolMatrix.get((_x * dim) + _y + 1)) { count++; }
                if(boolMatrix.get(((_x + 1) * dim) + _y - 1)) { count++; }
                if(boolMatrix.get(((_x + 1) * dim) + _y)) { count++; }
                if(boolMatrix.get(((_x + 1) * dim)+ _y + 1)) { count++; }   
            }
        }
        else if (_x == dim-1) {
            if (_y == 0) {
                if(boolMatrix.get(((_x - 1) * dim) + _y)) { count++; }
                if(boolMatrix.get(((_x - 1) * dim)+ _y + 1)) { count++; }
                if(boolMatrix.get((_x * dim) + _y + 1)) { count++; }
            }
            
            else if (_y == dim-1) {
                if(boolMatrix.get(((_x - 1) * _y) + _y - 1)) { count++; }
                if(boolMatrix.get(((_x - 1) * _y) + _y)) { count++; } 
                if(boolMatrix.get((_x * _y) + _y - 1)) { count++; } 
            }
            else {
                if(boolMatrix.get(((_x - 1) * dim) + _y - 1)) { count++; }
                if(boolMatrix.get(((_x - 1) * dim) + _y)) { count++; }
                if(boolMatrix.get(((_x - 1) * dim)+ _y + 1)) { count++; }
                if(boolMatrix.get((_x * dim) + _y - 1)) { count++; }
                if(boolMatrix.get((_x * dim) + _y + 1)) { count++; }                
            }
        }
        
        else if (_y == 0) {
            if(boolMatrix.get(((_x - 1) * dim) + _y)) { count++; }
            if(boolMatrix.get(((_x - 1) * dim)+ _y + 1)) { count++; }
            if(boolMatrix.get((_x * dim) + _y + 1)) { count++; }
            if(boolMatrix.get(((_x + 1) * dim) + _y)) { count++; }
            if(boolMatrix.get(((_x + 1) * dim)+ _y + 1)) { count++; }            
        }
            
        else if (_y == dim-1) {
            if(boolMatrix.get(((_x - 1) * dim) + _y - 1)) { count++; }
            if(boolMatrix.get(((_x - 1) * dim) + _y)) { count++; } 
            if(boolMatrix.get((_x * dim) + _y - 1)) { count++; } 
            if(boolMatrix.get(((_x + 1) * dim) + _y - 1)) { count++; }
            if(boolMatrix.get(((_x + 1) * dim) + _y)) { count++; }            
        }
        else {
            if(boolMatrix.get((_x * dim) + _y - 1)) { count++; }
            if(boolMatrix.get((_x * dim) + _y + 1)) { count++; }
            if(boolMatrix.get(((_x + 1) * dim) + _y - 1)) { count++; }
            if(boolMatrix.get(((_x + 1) * dim) + _y)) { count++; }
            if(boolMatrix.get(((_x + 1) * dim)+ _y + 1)) { count++; }
            if(boolMatrix.get(((_x - 1) * dim) + _y - 1)) { count++; }
            if(boolMatrix.get(((_x - 1) * dim) + _y)) { count++; }
            if(boolMatrix.get(((_x - 1) * dim)+ _y + 1)) { count++; }
        }
            
        
 
        return count;
    }
    
    public void nextGeneration(int _biS,int _bsS,int _bo) {
        BitSet savedMatrix = new BitSet(boolMatrixSize);
        int temp;
        
        for (int x=0;x<dim;x++) {	
            for (int y=0;y<dim;y++) {
                temp = countAliveNeighbours(x,y);
                if ( boolMatrix.get((x * dim) +  y)) {
                    if (temp >= _biS && temp <= _bsS) {
                        savedMatrix.set((x * dim) + y); //it survives
                    }
                } else {
                    if (temp == _bo) {
                        savedMatrix.set((x * dim) + y); //it borns
                    }				
                }
            }
        }

        boolMatrix = savedMatrix;
        switchNode.setChildMask(savedMatrix); //should refresh screen ?
        
    }
    
}
