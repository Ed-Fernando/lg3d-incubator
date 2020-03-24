/*
 * ExitButtonComponent3D.java
 *
 * Created on 2007/03/04, 3:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jdesktop.lg3d.apps.jsaddle.utils;

import java.net.URL;

import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.action.ActionBoolean;


/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class ExitButtonComponent3D extends ImagePanelButtonComponent3D {
    
    private Frame3D frame;
    
    /** Creates a new instance of ExitButtonComponent3D */
    private ExitButtonComponent3D() {
    }
    
    /** Creates a new instance of ExitButtonComponent3D */
    public ExitButtonComponent3D(Frame3D f, float width, float height){
        initialize(f,null,width,height);
    }

    /** Creates a new instance of ExitButtonComponent3D */
    public ExitButtonComponent3D(Frame3D f, URL url, float width, float height){
        initialize(f,url,width,height);
    }
    
    /** Creates a new instance of ExitButtonComponent3D
     *  default value : width = 0.01f, height = 0.01f
     */
    public ExitButtonComponent3D(Frame3D f){
        initialize(f,null,0.01f,0.01f);
    }

    /** Creates a new instance of ExitButtonComponent3D
     *  default value : width = 0.01f, height = 0.01f
     */
    public ExitButtonComponent3D(Frame3D f, URL url){
        initialize(f,url,0.01f,0.01f);
    }

    private void initialize(Frame3D f, URL url, float width, float height){
        frame = f;

        ClassLoader loader = this.getClass().getClassLoader();
        if(url == null)
            url = loader.getResource("resources/images/button/window-close.png");
        
        initialize(url,width,height);

        setPressedAction(new ActionBoolean(){
            public void performAction(LgEventSource source, boolean state){
                if(state && frame != null){
                    frame.changeEnabled(false);
                }
            }
        });

    }
    
}
