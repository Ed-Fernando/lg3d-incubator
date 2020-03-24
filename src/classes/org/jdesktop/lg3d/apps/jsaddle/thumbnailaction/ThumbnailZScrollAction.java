/*
 * ThumbnailZScroll.java
 *
 * Created on 2007/02/11, 11:40
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
public class ThumbnailZScrollAction extends ThumbnailAction {


    public ThumbnailZScrollAction(Component3D c, Vector<Component3D> p) {
        super(c,p);
    }

    public void setLocation(int value){
        setLocation(value,0);
    }

    public boolean setLocation(int value, int rotation){

        boolean result = false;
        
        if(panelList != null){
            double radian = 2 * Math.PI; // 360 degrees
            int size = panelList.size();
            int max = size / 2;
            if( value > 0 && value <= size ){
                for(int i=0; i < size; i++){
                    int num = i - value + 1;
                    float z = - num * 0.065f ;
                    float y = num * 0.018f ;
                    float x = num * 0.03f ;
                    Component3D c = panelList.get(i);
                    c.changeTranslation(x,y,z);
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
            for(int i=0; i < size; i++){
                Component3D c = panelList.get(i);
                c.changeRotationAngle(0.0f);
                c.setVisible(true);
            }
        }
    }
}
