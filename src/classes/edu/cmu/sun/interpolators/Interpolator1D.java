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
 * Absctrat class to define an one dimensional interpolator that will travel between
 * the from value and the two value.  Other interpolators may include other fields such
 * as ease in and ease out.
 * 
 * @author Braden Kowitz
 */
public abstract class Interpolator1D {

	protected float from;
	protected float to;
	
	public Interpolator1D(float from, float to)
	{
		this.from = from;
		this.to = to;
	}

	public abstract float get(float value);
	
}
