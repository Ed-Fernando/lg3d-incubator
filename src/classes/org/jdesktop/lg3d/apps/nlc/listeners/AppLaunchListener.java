/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.listeners;


import org.jdesktop.lg3d.apps.nlc.knowledge.CurrentScene;
import org.jdesktop.lg3d.scenemanager.utils.event.AppLaunchEvent;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;

public class AppLaunchListener implements LgEventListener {

	private CurrentScene scene;
	
	public AppLaunchListener(CurrentScene scene){
		if (scene == null) {
			throw new NullPointerException(
					"Argument to method StartApplicationListener is null");
		}
		this.scene = scene;
	}
	
	public void processEvent(LgEvent evt) {
		AppLaunchEvent event = (AppLaunchEvent) evt;
		scene.setRecent(event.getCommand());
		System.out.println("Starting command " + event.getCommand());
	}

	public Class<LgEvent>[] getTargetEventClasses() {
		return new Class[]{
			AppLaunchEvent.class
		};
	}

}
