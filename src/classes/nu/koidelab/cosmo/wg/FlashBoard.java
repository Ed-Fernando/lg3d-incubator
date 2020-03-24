/*
 * date: 2006/01/12 
 */
package nu.koidelab.cosmo.wg;

import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.shape.Box;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;

/**
 * @author fumi_ss
 * This class produce a visual effect such as white-out, black-out.
 * These effects is implemented by changing a transparency of a board.  
 */
public class FlashBoard extends Component3D {
  
  public FlashBoard(){
      setAnimation(new NaturalMotionAnimation(1000));
      setPickable(false);
      // "Box" have a role of a effect board.
      Box box = new Box(1.0f, 1.0f, 0.001f, new SimpleAppearance(1f,
              1f, 1f, 0.98f, SimpleAppearance.DISABLE_CULLING));
      addChild(box);
      setTransparency(0.95f);
      setVisible(false);
  }
  
  /**
   * Start a flash animation within the specified duration.  
   * @param duration
   */
  public void flash(int duration){
    Thread thread = new Thread(new Animator(duration));
    thread.start();      
  }
  
  // Animator class for changing a transparency of a effect board 
  private class Animator implements Runnable{
    private int duration;

    private Animator(int duration){
          this.duration =duration; 
    }
    
    public void run() {
        setVisible(true);
        setTranslation(0, 0, 0.1f);
        changeTransparency(0, duration);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        changeTransparency(0.95f, duration);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setVisible(false);
    }    
    
  }
}
