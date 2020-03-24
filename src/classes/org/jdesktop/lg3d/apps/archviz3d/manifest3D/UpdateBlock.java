package org.jdesktop.lg3d.apps.archviz3d.manifest3D;

import java.util.Observable;
import java.util.Observer;

/**
 * @author teyseyre
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface UpdateBlock {
	
	public void update(Observable observable, Observer observer, Object arg);

}
