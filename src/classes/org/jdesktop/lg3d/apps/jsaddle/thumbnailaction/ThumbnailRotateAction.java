/*
 * ThumbnailRotateAction.java
 *
 * Created on 2007/01/05, 13:39
 *
 */

package org.jdesktop.lg3d.apps.jsaddle.thumbnailaction;

import javax.vecmath.Vector3f;
import java.util.Vector;
import org.jdesktop.lg3d.apps.jsaddle.*;

import org.jdesktop.lg3d.wg.Component3D;

/**
 *
 * @author Yasuhiro Fujitsuki(thaniwa)
 */
public class ThumbnailRotateAction extends ThumbnailAction {

    private final static float IMAGE_WIDTH = 0.06f;
    private final static float IMAGE_HEIGHT = 0.045f;
    

    public ThumbnailRotateAction(Component3D c, Vector<Component3D> p) {
        super(c,p);
    }

    public boolean setLocation(int value, int rotation){

        if(panelList != null){

            double radian = 2 * Math.PI; // 360 degrees
            float newRotationAngle = 0.0f;

            if(rotation == 1)
                newRotationAngle = component.getFinalRotationAngle() - (float)(radian / panelList.size());
            else if(rotation == -1)
                newRotationAngle = component.getFinalRotationAngle() + (float)(radian / panelList.size());
            else
                newRotationAngle = - (float)(radian / panelList.size() * (value-1));
            
            component.changeRotationAngle(newRotationAngle);

            int size = panelList.size();
            for(int i=0; i < size; i++){
                int num = i - value + 1;
                if( num < 0 ) num += size;
                Component3D component = panelList.get(i);
                Vector3f translation = new Vector3f();
                component.getFinalTranslation(translation);
                float y = 0.0075f -0.025f * (float)Math.sin((double)(radian/size * num + Math.PI/2));
                component.changeTranslation(translation.x,y,translation.z);
                component.changeRotationAngle(-newRotationAngle);
            }
        }
        return true;
    }

    public void initLocation(){

        component.setRotationAxis(0.0f,1.0f,0.0f);
        component.changeRotationAngle(0.0f);

        if(panelList != null){
            int size = panelList.size();
            double radian = 2 * Math.PI; // 360 degrees
            for(int i=0; i < size; i++){
                float x = 0.1f * (float)Math.cos((double)(- radian/size * i + Math.PI/2));
                float y = 0.0075f -0.025f * (float)Math.sin((double)(radian/size * i + Math.PI/2));
                float z = 0.1f * (float)Math.sin((double)(- radian/size * i + Math.PI/2));
                Component3D c = panelList.get(i);
                c.changeVisible(true);
                c.changeTranslation(x,y,z);
                c.changeRotationAngle(0.0f);                
            }
        }
    }
    
}
