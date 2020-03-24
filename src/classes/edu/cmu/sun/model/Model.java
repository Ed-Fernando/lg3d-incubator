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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A basic Observer pattern that holds off update notification.
 * 
 * All models can have observers (or listeners) who listen for changes
 * to the model.  Most models notify listeners when the change happens.
 * This model works slightly differently. The models mark when they need
 * an update. Then, when the static method notifyAllListeners() is called,
 * every Model object then notifys it's listeners if a change has occoured.
 * 
 * There is probably a better way to handle this change, but we use this to hold
 * off updates from applying to the scene graph untill all of the models
 * have been updated first.
 * 
 * @author Braden Kowitz
 */
public class Model {

	/**
	 * Pointers to every Model object created in the system.
	 * (I know this creates a memory leak, but this is just a prototype)
	 */
	private static List<Model> allModels = new LinkedList<Model>();
	
	
	/**
	 * A set of listeners for this Model.
	 * NOTE TO LG3D Developers: I ran into a bug where LG3D's event model
	 * appeared to NOT use a set for it's observer pattern.  An object that
	 * added itslef as a listener twice, received double updates.  I believe
	 * this behavior is wrong, and this Model class implements the observer
	 * pattern correctly (other than the hold-off feature).
	 */
	private Set<ModelListener> listeners;
	
	/**
	 * True if the model has changed that the listeners need an update.
	 */
	private boolean modelNeedsUpdate = false;
	
	/**
	 * Create a new model.
	 */
	public Model()
	{
		allModels.add(this);
		listeners = new HashSet<ModelListener>();
	}
	
	/**
	 * Add a listener to this model.  If the model changes, it will
	 * update all of the listeners when notifyAllListeners() is called.
	 */
	public void addModelListener(ModelListener l)
	{
		if (!listeners.contains(l)) listeners.add(l);
	}
	
	/**
	 * Remove a listener from this model.  The listener will no longer receive
	 * modelChanged() updates from this Model.
	 */
	public void removeModelListener(ModelListener l)
	{
		listeners.remove(l);
	}
	
	/**
	 * Calls notifyListeners() on every Model object in the system.
	 * (This is a bit of a hack)
	 */
	public static void notifyAllListeners()
	{
		List<Model> theseModels = new ArrayList<Model>(allModels);
		for (Model m : theseModels) m.notifyListeners();
	}
	
	/**
	 * Sends a modelChanged(...) call to all listeners of this model.
	 */
	public void notifyListeners()
	{
		if (!modelNeedsUpdate) return;
		modelNeedsUpdate = false;
		List<ModelListener> theseListeners = new ArrayList<ModelListener>(listeners);
		for (final ModelListener listener : theseListeners)
		{
			listener.modelChanged(this);
		}
	}
	
	/**
	 * Called by subclasses to signial that a model has changed and needs updating.
	 * Really, the views need updating, the model needs to send out modelChanged(...)
	 * updates.
	 */
	protected void needsUpdate()
	{
		modelNeedsUpdate = true;
	}
	
	
}
