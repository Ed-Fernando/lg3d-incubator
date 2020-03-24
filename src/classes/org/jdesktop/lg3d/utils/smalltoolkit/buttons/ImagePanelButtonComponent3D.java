/*
 * ImagePanelButtonComponent3D.java
 *
 * Created on 2007/03/04, 3:07
 *
 */

package org.jdesktop.lg3d.utils.smalltoolkit.buttons;

import java.net.URL;

import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.action.ActionBoolean;


/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class ImagePanelButtonComponent3D extends ButtonComponent3D {
    
    /** Creates a new instance of ImagePanelButtonComponent3D */
    protected ImagePanelButtonComponent3D() {
    }
    
    /** Creates a new instance of ImagePanelButtonComponent3D */
    public ImagePanelButtonComponent3D(URL url, float width, float height){
        initialize(url,width,height);
    }
    
    /** Creates a new instance of ImagePanelButtonComponent3D
     *  default value : width = 0.01f, height = 0.01f
     */
    public ImagePanelButtonComponent3D(URL url){
        initialize(url,0.01f,0.01f);
    }

    protected void initialize(URL url, float width, float height){
        try {
            ImagePanel panel = new ImagePanel(url, width, height);
            addChild(panel);
        } catch (java.io.IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
}
