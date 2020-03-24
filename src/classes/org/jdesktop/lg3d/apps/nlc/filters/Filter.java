/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.filters;


import java.util.List;

import org.jdesktop.lg3d.apps.nlc.data.Application;

public interface Filter {

	public List<Application> filter(List<Application> apps);
}
