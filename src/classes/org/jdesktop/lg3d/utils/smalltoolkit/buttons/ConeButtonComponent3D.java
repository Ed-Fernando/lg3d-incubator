/*
 * ConeButtonComponent3D.java
 *
 * Created on 2007/03/04, 3:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.lg3d.utils.smalltoolkit.buttons;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Cone;

/**
 *
 * @author Yasuhiro Fujitsuki (thaniwa)
 */
public class ConeButtonComponent3D extends ButtonComponent3D {
    
    public enum Direction { UP, DOWN, RIGHT, LEFT }
    
    Direction direction = Direction.UP;
    
    /** Creates a new instance of ConeButtonComponent3D */
    public ConeButtonComponent3D() {
        super();
        initialize(0.005f, 0.01f, new SimpleAppearance(1.0f,1.0f,1.0f,1.0f), Direction.UP);
    }
    
    public ConeButtonComponent3D(Direction direction){
        initialize(0.005f, 0.01f, new SimpleAppearance(1.0f,1.0f,1.0f,1.0f), direction);
    }
    
    public ConeButtonComponent3D(float radius, float height, Appearance appearance){
        initialize(radius, height, appearance, Direction.UP);
    }

    public ConeButtonComponent3D(float radius, float height, Appearance appearance, Direction direction){
        initialize(radius, height, appearance, direction);
    }
    
    private void initialize(float radius, float height, Appearance appearance, Direction direction){
        Cone cone = new Cone(radius,height,appearance);
        addChild(cone);
        setDirection(direction);
    }
    
    /** get direction of cone component */
    public Direction getDirection(){ return direction; }
    
    /** set direction of cone component
     *  ( Values : Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT )
     */
    public void setDirection(Direction direction){

        this.direction = direction;
        
        if(direction == Direction.UP)
            setRotationAngle(0.0f);
        else if(direction == Direction.DOWN){
            setRotationAxis(1.0f,0.0f,0.0f);
            setRotationAngle((float)Math.PI);
        }
        else if(direction == Direction.RIGHT){
            setRotationAxis(1.0f,1.0f,0.0f);
            setRotationAngle((float)Math.PI);
        }
        else {
            setRotationAxis(1.0f,-1.0f,0.0f);
            setRotationAngle((float)Math.PI);
        }
    }

}
