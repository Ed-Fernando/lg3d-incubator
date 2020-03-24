 /*
  * 3D File Manager - Project Looking Glass 
  * Copyright Sun Microsystems, 2005
  * 
  * Project Course in Human-Computer Interaction
  * Carnegie Mellon University
  * 
  * Aditya Chand, Braden Kowitz, Jake Pierson, Jessica Smith
  */

 package edu.cmu.sun.interpolators;

import org.jdesktop.lg3d.utils.c3danimation.ChangeVisibleAnimationPlugin;
import org.jdesktop.lg3d.utils.c3danimation.PluggableC3DAnimation;

/**
 * A PluggableC3DAnimation that uses Simple smoothers to do linear translations.
 * 
 * @author Braden Kowitz
 */
public class SimpleMotionAnimation extends PluggableC3DAnimation {

    public SimpleMotionAnimation(int defaultDuration) {
        super(
            defaultDuration,
            new SimpleVector3fSmoother(),
            new SimpleFloatSmoother(),
            new SimpleVector3fSmoother(),
            new SimpleFloatSmoother());
    }
    
    public SimpleMotionAnimation(int defaultDuration, 
        ChangeVisibleAnimationPlugin cvaPlugin) 
    {
        super(
            defaultDuration,
            new SimpleVector3fSmoother(),
            new SimpleFloatSmoother(),
            new SimpleVector3fSmoother(),
            new SimpleFloatSmoother(),
            cvaPlugin);
    }

}
