/*
 * 作成日： 2005/02/15
 *
 */
package nu.koidelab.cosmo.wg.shape;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;

/**
 * @author fumi_ss
 * 18:48:22
 */
public abstract class BlurComponent extends Container3D {
    protected Component3D body;
    protected Component3D[] blur; 
    private int blurNum;
    
    protected BlurComponent(int blurNum, int duration){
        super();
        body = new Component3D();
        body.setAnimation(new NaturalMotionAnimation(duration));
        blur = new Component3D[blurNum];
        addChild(body);
        
        for(int i = 0; i < blurNum; i++ ){
            blur[i] = new Component3D();
            blur[i].setAnimation(new NaturalMotionAnimation(duration));
            addChild(blur[i]);
        }
        this.blurNum = blurNum;
    }
    
    protected Component3D getBody(){
        return this.body;
    }
    
    protected Component3D getBlur(int num){
        return this.blur[num];
    }
    
    protected int getBlurNum(){
        return blurNum;
    }
    
    public void changeTranslation(Vector3f arg0) {
        Vector3f tmp = new Vector3f();
        body.getTranslation(tmp);                
        
        for (int i = 0; i < blurNum; i++) {
            if (i == 0) {
                blur[0].changeTranslation(tmp);
            } else {
                blur[i - 1].getTranslation(tmp);
                blur[i].changeTranslation(tmp);
            }
        }
        
        body.changeTranslation(arg0);
    }
    
}
