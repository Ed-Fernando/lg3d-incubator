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

import javax.vecmath.Point3f;

/**
 * Comibnes three Interpolator1D objects and allows for acces to iterated points through
 * a Point3f object.
 * 
 * @author Braden Kowitz
 */
public class CompositeInterpolator3D {

	Interpolator1D x;
	Interpolator1D y;
	Interpolator1D z;
	
	public CompositeInterpolator3D(Interpolator1D x, Interpolator1D y, Interpolator1D z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3f get(float value)
	{
		return new Point3f(x.get(value), y.get(value), z.get(value));
	}

}
