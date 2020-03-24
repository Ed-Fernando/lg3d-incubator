/*
 * MinimizeButtonComponent3D.java
 *
 * Created on 2007/03/04, 3:07
 *
 */

package org.jdesktop.lg3d.apps.jsaddle.utils;

import java.net.URL;

import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.action.ActionBoolean;


/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class MinimizeButtonComponent3D extends ImagePanelButtonComponent3D {
    
    private Component3D component;
    
    /** Creates a new instance of MinimizeButtonComponent3D */
    private MinimizeButtonComponent3D() {
    }
    
    /** Creates a new instance of MinimizeButtonComponent3D */
    public MinimizeButtonComponent3D(Component3D c, float width, float height){
        initialize(c,null,width,height);
    }

    /** Creates a new instance of MinimizeButtonComponent3D */
    public MinimizeButtonComponent3D(Component3D c, URL url, float width, float height){
        initialize(c,url,width,height);
    }
    
    /** Creates a new instance of MinimizeButtonComponent3D
     *  default value : width = 0.01f, height = 0.01f
     */
    public MinimizeButtonComponent3D(Component3D c){
        initialize(c,null,0.01f,0.01f);
    }

    /** Creates a new instance of MinimizeButtonComponent3D
     *  default value : width = 0.01f, height = 0.01f
     */
    public MinimizeButtonComponent3D(Component3D c, URL url){
        initialize(c,url,0.01f,0.01f);
    }

    private void initialize(Component3D c, URL url, float width, float height){
        component = c;

        ClassLoader loader = this.getClass().getClassLoader();
        if(url == null)
            url = loader.getResource("resources/images/button/window-minimize.png");
        
        initialize(url,width,height);

        setPressedAction(new ActionBoolean(){
            public void performAction(LgEventSource source, boolean state){
                if(state && component != null){
                    component.changeVisible(false);
                }
            }
        });

    }
    
}
