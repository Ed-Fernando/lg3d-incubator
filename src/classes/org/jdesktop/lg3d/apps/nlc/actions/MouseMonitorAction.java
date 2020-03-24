/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.actions;

import java.util.logging.Logger;

import org.jdesktop.lg3d.apps.nlc.data.Application;
import org.jdesktop.lg3d.apps.nlc.knowledge.CurrentScene;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

public class MouseMonitorAction implements ActionBoolean {

	private static Logger logger = Logger.getLogger("lg.nlc");
	
	private CurrentScene scene;
	
	public MouseMonitorAction(CurrentScene scene) {
		if (scene == null) {
			throw new NullPointerException(
					"Argument to method MouseMonitorAction is null");
		}
		this.scene = scene;
	}



	public void performAction(LgEventSource source, boolean state) {
		Frame3D frame = (Frame3D) source;
		if (state == false){
			logger.info("Removing focussed application");
			scene.setFocussedApplication(null);
		} else {
			Application app = scene.searchApplication(frame);
			logger.info("Setting focussed application " + app);
			scene.setFocussedApplication(app);
		}
		
	}

}
