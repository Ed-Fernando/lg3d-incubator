/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.filters;


import java.util.List;

import org.jdesktop.lg3d.apps.nlc.data.Application;

public class DefaultFilter implements Filter {

	/**
	 * Quite a complicated filter. First find out if there is a 
	 * running application, if there's a running application. 
	 * 
	 */
	public List<Application> filter(List<Application> apps) {
		List<Application> fapps = new RunningFilter().filter(apps);
		if (fapps.size() == 0)
			return apps;
		else
			return fapps;
	}

}
