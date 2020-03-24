/*
 * 作成日： 2005/03/26
 *
 */
package nu.koidelab.cosmo.util.c3danimation;

import nu.koidelab.cosmo.wg.smoother.TrapzoidFloatSmoother;

import org.jdesktop.lg3d.utils.c3danimation.ChangeVisibleAnimationPlugin;
import org.jdesktop.lg3d.utils.c3danimation.PluggableC3DAnimation;
import org.jdesktop.lg3d.utils.smoother.NaturalFloatSmoother;
import org.jdesktop.lg3d.utils.smoother.NaturalVector3fSmoother;

/**
 * @author fumi_ss
 * 19:33:00
 */
public class BlinkingAnimation extends PluggableC3DAnimation{    
    public BlinkingAnimation(int defaultDuration) {
        super(
            defaultDuration,
            new NaturalVector3fSmoother(),
            new NaturalFloatSmoother(),
            new NaturalFloatSmoother(),
            new TrapzoidFloatSmoother());
    }
    
    public BlinkingAnimation(int defaultDuration, float increasePeriod, float decreasePeriod) {
        super(
            defaultDuration,
            new NaturalVector3fSmoother(),
            new NaturalFloatSmoother(),
            new NaturalFloatSmoother(),
            new TrapzoidFloatSmoother(increasePeriod, decreasePeriod));
    }
    
    public BlinkingAnimation(int defaultDuration, float increasePeriod, float decreasePeriod, float initTransparencyValue) {
        super(
            defaultDuration,
            new NaturalVector3fSmoother(),
            new NaturalFloatSmoother(),
            new NaturalFloatSmoother(),
            new TrapzoidFloatSmoother(increasePeriod, decreasePeriod, initTransparencyValue));
    }
    
    public BlinkingAnimation(int defaultDuration, 
            ChangeVisibleAnimationPlugin cvaPlugin) 
    {
        super(
            defaultDuration,
            new NaturalVector3fSmoother(),
            new NaturalFloatSmoother(),
            new NaturalFloatSmoother(),
            new TrapzoidFloatSmoother(),
            cvaPlugin);
    }
    
    public BlinkingAnimation(int defaultDuration, float increasePeriod, float decreasePeriod,
            ChangeVisibleAnimationPlugin cvaPlugin) {
        super(
            defaultDuration,
            new NaturalVector3fSmoother(),
            new NaturalFloatSmoother(),
            new NaturalFloatSmoother(),
            new TrapzoidFloatSmoother(increasePeriod, decreasePeriod),
            cvaPlugin);
    }
}
