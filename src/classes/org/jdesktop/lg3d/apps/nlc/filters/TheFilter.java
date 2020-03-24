/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.filters;


import java.util.ArrayList;
import java.util.List;

import org.jdesktop.lg3d.apps.nlc.data.Application;

/**
 * This is a very complicated filter. It filters out
 * all but focussed running application and non running
 * applications.
 * 
 * "the"
 * "current"
 *  
 * @author harsh
 */
public class TheFilter extends DeterminerFilter {

	public TheFilter(String determiner) {
		super(determiner);
	}

	@Override
	public List<Application> filter(List<Application> apps) {
		List<Application> fapps = new ArrayList<Application>();
		fapps.addAll(new RunningFilter().filter(apps));
		fapps.addAll(new LaunchFilter().filter(apps));
		return fapps;
	}

}
