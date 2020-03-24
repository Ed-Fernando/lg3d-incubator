/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.actions;

import org.jdesktop.lg3d.apps.nlc.data.Application;
import org.jdesktop.lg3d.apps.nlc.events.CloseFrameEvent;
import org.jdesktop.lg3d.displayserver.AppConnectorPrivate;


public class CloseAction implements Action {

	public void takeAction(Application app) {
		AppConnectorPrivate.getAppConnector().postEvent(new CloseFrameEvent(),
				app.getFrame());
	}

	@Override
	public String toString() {
		return "Close-Action";
	}
}
