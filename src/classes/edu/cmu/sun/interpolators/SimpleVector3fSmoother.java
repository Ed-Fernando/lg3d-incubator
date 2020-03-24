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

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.utils.smoother.Vector3fTransitionSmoother;

/**
 * 
 * A 3d smoother that does linear interpolation between the 3d points.
 * 
 * @author Braden Kowitz
 *
 */
public class SimpleVector3fSmoother implements Vector3fTransitionSmoother {

	Vector3f fromVal = new Vector3f();
	Vector3f toVal = new Vector3f();
	Vector3f currentVal = new Vector3f();
	
	public void setTargetValue(Vector3f t) {
		fromVal.set(currentVal);
		toVal.set(t);
	}
	
	public void setInternalValue(Vector3f v) {
		toVal.set(v);
		fromVal.set(v);
		currentVal.set(v);
	}

	public Vector3f getValue(Vector3f v, float n) {
		currentVal.x = n * toVal.x + (1f-n) * fromVal.x;
		currentVal.y = n * toVal.y + (1f-n) * fromVal.y;
		currentVal.z = n * toVal.z + (1f-n) * fromVal.z;
		v.set(currentVal);
		return v;
	}

	public Vector3f getLatestValue(Vector3f v) {
		v.set(currentVal);
		return v;
	}

	public Vector3f getFinalValue(Vector3f v) {
		v.set(toVal);
		return v;
	}

}
