/*
 * BoxButtonComponent3D.java
 *
 * Created on 2007/03/04, 3:03
 *
 */

package org.jdesktop.lg3d.utils.smalltoolkit.buttons;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.ImagePanel;

import java.net.URL;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class BoxButtonComponent3D extends ButtonComponent3D {
    
    /** Creates a new instance of BoxButtonComponent3D */
    private BoxButtonComponent3D() {
    }

    /** Create a new instance of BoxButtonComponent3D
     *  Default Values : xdim = 0.005f
     *                   ydim = 0.005f
     *                   zdim = 0.005f
     *                   panel = 0.1f * 0.1f
     *                   color(R,G,B,Alpha) = 1.0f, 1.0f, 1.0f, 1.0f
     *  url ... image of the button (null can be set in url field)
     */
    public BoxButtonComponent3D(URL url){
        Box box = new Box(0.005f,0.005f,0.005f,new SimpleAppearance(1.0f,1.0f,1.0f,1.0f));
        ImagePanel panel = createPanel(0.01f,0.01f,url);
        initialize(0.005f, box, panel);
    }
    
    /** Create a new instance of CylinderButtonComponent3D
     *  If you want to use textures to Cylinder, please use this constractor.
     */
    public BoxButtonComponent3D(Box box, URL url){
        ImagePanel panel = createPanel(box.getXdimension() *2,box.getYdimension() *2,url);
        initialize(box.getZdimension(),box,panel);
    }
    
    /** Create a new instance of BoxButtonComponent3D
     *  caution : ImagePanel(width,height), Box(xdim,ydim,zdim)
     *  width = 2* xdim, height = 2* ydim
     */
    public BoxButtonComponent3D(Box box, ImagePanel panel){
        initialize(box.getZdimension(),box,panel);
    }
    
    /** Create a new instance of CylinderButtonComponent3D
     */
    public BoxButtonComponent3D(float xdim, float ydim, float zdim, Appearance appearance, URL url){
        Box box = new Box(xdim,ydim,zdim,appearance);
        ImagePanel panel = createPanel(xdim*2,ydim*2,url);
        initialize(zdim, box, panel);
    }
    
    private ImagePanel createPanel(float width, float height, URL url){
        ImagePanel panel = null;
        if(url != null){
            try{
                panel = new ImagePanel(url,width,height);
            } catch( java.io.IOException ioe ){
                ioe.printStackTrace();
            }
        }
        return panel;
    }

    private void initialize(float zdim, Box box, ImagePanel panel){

        Component3D boxComponent = new Component3D();
        boxComponent.addChild(box);

        if(panel != null){
            Component3D panelComponent = new Component3D();
            panelComponent.addChild(panel);
            panelComponent.setTranslation(0.0f,0.0f, zdim + 0.0005f);
            addChild(panelComponent);
        }

        addChild(boxComponent);
    }

}
