/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.actions;

import org.jdesktop.lg3d.apps.nlc.data.Application;
import org.jdesktop.lg3d.apps.nlc.events.ParkLeftEvent;
import org.jdesktop.lg3d.displayserver.AppConnectorPrivate;

public class ParkLeftAction implements Action {

	public void takeAction(Application app) {
		if (app.getFrame() == null){
			throw new IllegalArgumentException();
		}
		AppConnectorPrivate.getAppConnector().postEvent(new ParkLeftEvent(), app.getFrame());
	}

}
