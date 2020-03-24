/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.filters;


import java.util.List;

import org.jdesktop.lg3d.apps.nlc.data.Application;

/**
 * All filter works for determiners "all", "every", "each",
 * etc.
 * 
 * @author harsh
 */
public class AllFilter extends DeterminerFilter {

	public AllFilter(String determiner) {
		super(determiner);
	}

	@Override
	public List<Application> filter(List<Application> apps) {
		return apps; 
	}

}
