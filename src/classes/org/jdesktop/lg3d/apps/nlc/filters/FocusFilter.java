/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.filters;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jdesktop.lg3d.apps.nlc.data.Application;
import org.jdesktop.lg3d.apps.nlc.knowledge.CurrentScene;
import org.jdesktop.lg3d.wg.Frame3D;

/**
 * Filters out all the applications which are not focussed. After this filter is
 * applied we are only left with focussed applications.
 * 
 * @author harsh
 */
public class FocusFilter implements Filter {

	private static Logger logger = Logger.getLogger("lg.nlc");

	private CurrentScene scene;

	public FocusFilter(CurrentScene scene) {
		if (scene == null) {
			throw new NullPointerException(
					"Argument to method FocusFilter is null");
		}
		this.scene = scene;
	}

	public List<Application> filter(List<Application> apps) {
		List<Application> fapps = new ArrayList<Application>();
		if (scene.getFocussedApplication() == null) {
			logger
					.warning("No application is focussed. Please address some application in your command");
			return fapps;
		}
		for (Application app : apps) {
			Frame3D frame = app.getFrame();
			if (frame == null) {
				// these are unlaunched application, can't be focussed
				continue;
			}
			if (scene.getFocussedApplication().equals(app)) {
				fapps.add(app);
			}
		}
		return fapps;
	}
}
