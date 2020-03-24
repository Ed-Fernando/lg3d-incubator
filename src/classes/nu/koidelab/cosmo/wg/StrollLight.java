/*
 * ºîÀ®Æü: 2005/02/10
 */
package nu.koidelab.cosmo.wg;

import java.util.Calendar;

import javax.vecmath.Vector3f;

import nu.koidelab.cosmo.manager.CSDGeneralManager;
import nu.koidelab.cosmo.manager.CosmoConfig;

import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.sg.TransformGroup;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.utils.shape.Disc;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;

/**
 * @author fumi StrollLight org.wg
 */
public class StrollLight extends Component3D implements Runnable{
    private TransformGroup tg = new TransformGroup();
    private static boolean hasInstance = false;
    
    public StrollLight() {
        addChild(tg);
        tg.addChild(getBody());
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        startAnimation();
    }
    
    private Component3D getBody(){        
        SimpleAppearance bodyApp = new SimpleAppearance(0.9f, 0.6f, 0.6f, 1.0f,
              SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.NO_GLOSS);
        TransparencyAttributes ta = bodyApp.getTransparencyAttributes();
        ta.setDstBlendFunction(TransparencyAttributes.BLEND_ONE);
        ta.setSrcBlendFunction(TransparencyAttributes.BLEND_SRC_ALPHA);
        bodyApp.setTransparencyAttributes(ta);
        TextureAttributes texa = bodyApp.getTextureAttributes();
        texa.setTextureMode(TextureAttributes.MODULATE);
        bodyApp.setTextureAttributes(texa);
        bodyApp.setTexture(CosmoConfig.getConfig().getLightTexture());
        
        Component3D comp = new Component3D();
        Disc body = new Disc(0.005f, 16, bodyApp);
        comp.addChild(body);
      return comp;  
    }
       
    private void startAnimation(){
        Thread thread = new Thread(this);
        thread.setPriority(1);
        thread.start();
    }
    
    public void run() {
        Transform3D t3d = new Transform3D();
        Vector3f vec = new Vector3f();
        Calendar tmpCal = Calendar.getInstance();
//        int MAX_FUTURE_DATE = CosmoConfig.DISPLAY_NUM_OF_DAY;
        int MAX_FUTURE_DATE = 0;
        tmpCal.add(Calendar.DAY_OF_MONTH, -MAX_FUTURE_DATE);
        long min = tmpCal.getTimeInMillis();
        tmpCal.add(Calendar.DAY_OF_MONTH, MAX_FUTURE_DATE*2);
        long now = tmpCal.getTimeInMillis();
        int diff = 1000*60*60*1;
        
        long current;
        long last;
        last = current = System.currentTimeMillis();
        while(true){
            current = System.currentTimeMillis();
            if( (current - last) > 16){ // frame rate 1/60 ? is this needed???

            if (now < min) now = tmpCal.getTimeInMillis();
            CSDGeneralManager.getInstance().getFunction().getPosition(now, vec);
            t3d.setTranslation(vec);
            tg.setTransform(t3d);
            now = now - diff;
            
            last = current;
            } else {
                try{
                    Thread.sleep(10);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
    
}
