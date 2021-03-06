/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.filters;


import java.util.ArrayList;
import java.util.List;

import org.jdesktop.lg3d.apps.nlc.data.Application;
import org.jdesktop.lg3d.apps.nlc.data.ApplicationConfig;

public class BadFilter implements Filter {

	public List<Application> filter(List<Application> apps) {
		List<Application> fapps = new ArrayList<Application>();
		for (Application app:apps){
			if (app.getAppConfig() == null){
				continue;
			}
			if (app.getAppConfig().equals(ApplicationConfig.DEFAULT))
				continue;
			if (app.getAppConfig().getCommand().equals("java org.jdesktop.lg3d.apps.nlc.Main")){
				continue;
			}
			fapps.add(app);
			
		}
		return fapps;
	}

}
