/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.actions;

import java.util.logging.Logger;

import org.jdesktop.lg3d.apps.nlc.data.Application;
import org.jdesktop.lg3d.apps.nlc.events.RotateFrameEvent;
import org.jdesktop.lg3d.displayserver.AppConnectorPrivate;

public class RotateAction implements Action {

	private static Logger logger = Logger.getLogger("lg.nlc");

	public void takeAction(Application app) {
		if (app.getFrame() == null){
			logger.warning("Illegal application sent for rotation");
			throw new IllegalArgumentException();
		}
		AppConnectorPrivate.getAppConnector().
			postEvent(new RotateFrameEvent(), app.getFrame());
		
	}

	@Override
	public String toString() {
		return "Rotate-Action";
	}

}
