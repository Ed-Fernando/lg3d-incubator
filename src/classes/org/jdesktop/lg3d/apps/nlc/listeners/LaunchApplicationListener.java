/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.listeners;


import org.jdesktop.lg3d.apps.nlc.events.LaunchApplicationEvent;
import org.jdesktop.lg3d.displayserver.EventProcessor;
import org.jdesktop.lg3d.utils.action.AppLaunchAction;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;

public class LaunchApplicationListener implements LgEventListener {

	public void processEvent(LgEvent evt) {
		LaunchApplicationEvent event = (LaunchApplicationEvent) evt;
		new AppLaunchAction(event.getCommand()).performAction(null);
	}

	public Class<LgEvent>[] getTargetEventClasses() {

		return new Class[]{
			LaunchApplicationEvent.class	
		};
	}

}
