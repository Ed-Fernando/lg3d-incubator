/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.filters;

import java.util.ArrayList;
import java.util.List;

import org.jdesktop.lg3d.apps.nlc.conditions.Condition;
import org.jdesktop.lg3d.apps.nlc.data.Application;

public class ConditionFilter implements Filter {

	private Condition condition;
	
	public ConditionFilter(Condition c){
		if (c == null) {
			throw new NullPointerException(
					"Argument to method ConditionFilter is null");
		}
		this.condition = c;
	}
	
	public List<Application> filter(List<Application> apps) {
		List<Application> fapps = new ArrayList<Application>();
		for (Application app:apps){
			if (condition.satisfy(app))
				fapps.add(app);
		}
		return fapps;
	}

}
