/*
 * CylinderButtonComponent3D.java
 *
 * Created on 2007/03/04, 3:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.lg3d.utils.smalltoolkit.buttons;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Cylinder;
import org.jdesktop.lg3d.utils.shape.Disc;

import java.net.URL;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class CylinderButtonComponent3D extends ButtonComponent3D {

    /*
    float radius = 0.005f;
    float height = 0.01f;
    float red = 1.0f;
    float green = 1.0f;
    float blue = 1.0f;
    float alpha = 1.0f;
     */
    
    /** Creates a new instance of CylinderButtonComponent3D */
    private CylinderButtonComponent3D() {
    }
    
    /** Create a new instance of CylinderButtonComponent3D
     *  Default Values : radius = 0.05f(cylinder), 0.10f(disc)
     *                   height = 0.01f
     *                   color(R,G,B,Alpha) = 1.0f, 1.0f, 1.0f, 1.0f
     *  url ... image of the button (null can be set in url field)
     */
    public CylinderButtonComponent3D(URL url){
        Cylinder cylinder = new Cylinder(0.005f,0.01f, new SimpleAppearance(1.0f,1.0f,1.0f,1.0f));
        Disc disc = createDisc(0.005f, url);
        initialize(0.01f, cylinder, disc);
    }
    
    /** Create a new instance of CylinderButtonComponent3D
     *  If you want to use textures to Cylinder, please use this constractor.
     */
    public CylinderButtonComponent3D(Cylinder cylinder, URL url){
        float height = cylinder.getHeight();
        Disc disc = createDisc(height, url);
        initialize(height, cylinder, disc);
    }
    
    /** Create a new instance of CylinderButtonComponent3D
     *  caution : radius of the disc is a half of the radius of the cylinder.
     */
    public CylinderButtonComponent3D(Cylinder cylinder, Disc disc){
        initialize(cylinder.getHeight(), cylinder, disc);
    }
    
    /** Create a new instance of CylinderButtonComponent3D
     */
    public CylinderButtonComponent3D(float radius, float height, Appearance appearance, URL url){
        Cylinder cylinder = new Cylinder(radius, height, appearance);
        Disc disc = createDisc(radius, url);
        initialize(height, cylinder, disc);
    }
    
    private Disc createDisc(float radius, URL url){
        Disc disc = null;
        if( url != null ){
            try{
                disc = new Disc(radius*2, 100, new SimpleAppearance(url) );
            } catch( java.io.IOException ioe ){
                ioe.printStackTrace();
            }
        }
        return disc;
    }
    
    private void initialize(float height, Cylinder cylinder, Disc disc){

        Component3D cylinderComponent = new Component3D();

        cylinderComponent.addChild(cylinder);
        cylinderComponent.setRotationAxis(1.0f,0.0f,0.0f);
        cylinderComponent.setRotationAngle((float)Math.PI/2.0f);

        if(disc != null){
            Component3D discComponent = new Component3D();
            discComponent.addChild(disc);
            discComponent.setTranslation(0.0f,0.0f, height/2 + 0.0005f);
            addChild(discComponent);
        }

        addChild(cylinderComponent);
    }

}
