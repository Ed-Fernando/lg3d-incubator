package org.jdesktop.lg3d.apps.archviz3d.ZExecution;

import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author  teyseyre
 */
public interface PROComponent {
    
	public String getName();
    public Iterator getComponentsIterator(); 
    public Iterator getLinksIterator(); 
    public Vector getComponents(); 
    public Vector getLinks(); 
    
}
