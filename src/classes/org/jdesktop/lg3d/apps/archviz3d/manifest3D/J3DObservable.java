package org.jdesktop.lg3d.apps.archviz3d.manifest3D;

import java.util.Observable;

import org.jdesktop.lg3d.wg.Component3D;

/**
 * Clase nesesaria para poder marcar la variable changed de Observable.
 *
 * @author Gastón Peirano & Mauricio Poncio
 * @version 1.0
 */
public class J3DObservable extends Observable {
	private Component3D realObservable; 
	
    public J3DObservable(Component3D real) {
    	realObservable = real;
    }
    
    public void setChanged() {
        super.setChanged();
    }
    
    public  void clearChanged() {
    	super.clearChanged();
    }
    
    public Component3D getRealObservable() {
    	return realObservable;
    }
    
}
