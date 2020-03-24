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
 * Interpolates between the from and to values in a linear fashion.
 * 
 * @author Braden Kowitz
 */
public class LinearInterpolator1D extends Interpolator1D
{

	public LinearInterpolator1D(float from, float to) {
		super(from, to);
	}

	@Override
	public float get(float value) {
		return from + (to - from) * value;
	}



}
