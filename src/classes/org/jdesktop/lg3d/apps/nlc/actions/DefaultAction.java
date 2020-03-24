/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.actions;

import org.jdesktop.lg3d.apps.nlc.data.Application;

public class DefaultAction implements Action {

	public void takeAction(Application app) {
		if (app.getFrame() == null){
			new LaunchAction().takeAction(app);
		} else {
			new FocusAction().takeAction(app);
		}
	}
	
	@Override
	public String toString() {
		return "Default-Action";
	}
	
	
}
