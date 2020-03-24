/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.filters;


import java.util.ArrayList;
import java.util.List;

import org.jdesktop.lg3d.apps.nlc.data.Application;
import org.jdesktop.lg3d.wg.Frame3D;

/**
 * Returns all applications that are running. 
 * 
 * @author harsh
 */
public class RunningFilter implements Filter {

	public List<Application> filter(List<Application> apps) {
		List<Application> fapps = new ArrayList<Application>();
		for (Application app:apps){
			Frame3D frame = app.getFrame();
			if (frame != null && frame.isEnabled()){
				fapps.add(app);
			}
		}
		return fapps;
	}

}
