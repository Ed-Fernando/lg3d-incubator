/*
 * ºîÀ®Æü: 2005/08/05
 */
package nu.koidelab.cosmo.wg.nodes;



import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.wg.Container3D;

public abstract class Orbiter extends Container3D {
    protected Orbiter() {
        setAnimation(new NaturalMotionAnimation(500));        
    }
    
    public abstract long getTime(); 
        
}
