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

import org.jdesktop.lg3d.utils.smoother.FloatTransitionSmoother;

/**
 * Built for PLG, this smother does linear translations between values.
 * 
 * @author Braden Kowitz
 */
public class SimpleFloatSmoother implements FloatTransitionSmoother {

	// transitioning from this value
	float fromVal;
	
	// transitioning to this value
	float toVal;
	
	// currently, at this value
	float currentVal;
	
	public void setTargetValue(float t) {
		fromVal = currentVal;
		toVal = t;
	}

	public void setInternalValue(float v) {
		toVal = fromVal = currentVal = v;
	}

	public float getValue(float n) {
		currentVal = n * toVal + (1f-n) * fromVal;
		return currentVal;
	}

	public float getLatestValue() {
		return currentVal;
	}

	public float getFinalValue() {
		return toVal;
	}

}
