/*
 * ThumbnailViewerContainer.java
 *
 * Created on 2007/01/05, 13:39
 *
 */

package org.jdesktop.lg3d.apps.jsaddle.thumbnailaction;

import javax.vecmath.Vector3f;
import java.util.Vector;
import org.jdesktop.lg3d.apps.jsaddle.*;

import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Component3D;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class Thumbnail3DTimeFrameAction extends ThumbnailAction {

    private final static float IMAGE_WIDTH = 0.06f;
    private final static float IMAGE_HEIGHT = 0.045f;

    public Thumbnail3DTimeFrameAction(Component3D c, Vector<Component3D> p) {
        super(c,p);
    }

    public void setLocation(int value){
        setLocation(value,0);
    }

    public boolean setLocation(int value, int rotation){

        boolean result = false;
        
        if(panelList != null){

            int size = panelList.size();
            if( value > 0 && value <= size ){
                for(int i=0; i < size; i++){
                    float x = (i-value+1) * 0.04f ;
                    Component3D c = panelList.get(i);
                    c.changeTranslation(x,0.0f,0.0f);
                }
                result = true;
            }
        }
        
        return result;
    }

    public void initLocation(){

        component.changeRotationAngle(0.0f);

        if(panelList != null){
            int size = panelList.size();
            // double radian = 2 * Math.PI; // 360 degrees
            double rotateAngle = - Math.PI /  3.0 ; // 60 degrees
            for(int i=0; i < size; i++){
                Component3D c = panelList.get(i);
                c.setRotationAngle((float)rotateAngle);        
                c.setVisible(true);
            }
        }
    }
    
}
