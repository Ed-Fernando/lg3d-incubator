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


/**
 * Similar to the linear interpolator, except that it smooths the 
 * interpolation using trig functions. (So it's relatively fast)
 * 
 * Ideally, we'd use Bezier curves in the system, but we go with
 * this because it's simple enough for now.
 * 
 * @author Braden Kowitz
 */
public class SmoothInterpolator1D extends Interpolator1D {

	float diff;
	
	public SmoothInterpolator1D(float from, float to) {
		super(from, to);
		diff = to-from;
	}

	@Override
	public float get(float value) {
		float smooth = 0.5f + (float)(Math.cos(value * Math.PI)) / -2f;
		smooth *= smooth;
		return from + diff * smooth;
	}


}
