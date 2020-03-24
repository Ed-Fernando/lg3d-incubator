 /*
  * 3D File Manager - Project Looking Glass 
  * Copyright Sun Microsystems, 2005
  * 
  * Project Course in Human-Computer Interaction
  * Carnegie Mellon University
  * 
  * Aditya Chand, Braden Kowitz, Jake Pierson, Jessica Smith
  */
 
 package edu.cmu.sun.model;

 /**
  * 
  * Interface for any object that wishes to listen to changes in models.
  * 
  * Most classes in the View package are ModelListeners.
  * 
  * @author Braden Kowitz
  *
  */
public interface ModelListener {

	void modelChanged(Model m);
	
}
