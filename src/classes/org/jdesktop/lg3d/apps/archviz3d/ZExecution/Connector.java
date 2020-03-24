package org.jdesktop.lg3d.apps.archviz3d.ZExecution;

import java.util.Vector;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ArqComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROLink;

/**
 *
 * @author  teyseyre
 */
public class Connector extends ArqComponent implements PROLink {
    
    /** Creates a new instance of Component */
    public Connector(String s) {
    	super(s);
    }
    
    public PROComponent getOrigin() {
    	Vector v = getPorts();
		return ((PROLink)v.get(0)).getTarget();
    }
    
    public PROComponent getTarget() {
    	Vector v = getPorts();
    	return ((PROLink)v.get(1)).getTarget();
    }
    
}
