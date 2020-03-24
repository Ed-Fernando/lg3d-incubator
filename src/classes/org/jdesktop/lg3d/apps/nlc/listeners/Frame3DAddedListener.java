/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.listeners;


import org.jdesktop.lg3d.apps.nlc.data.Application;
import org.jdesktop.lg3d.apps.nlc.data.ApplicationConfig;
import org.jdesktop.lg3d.apps.nlc.knowledge.CurrentScene;
import org.jdesktop.lg3d.scenemanager.utils.event.Frame3DAddedEvent;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;

public class Frame3DAddedListener implements LgEventListener {

	private CurrentScene scene;
	
	public Frame3DAddedListener(CurrentScene scene){
		if (scene == null) {
			throw new NullPointerException(
					"Argument to method StartFrameEventListener is null");
		}
		this.scene = scene;
	}
	
	public void processEvent(LgEvent evt) {
		Frame3DAddedEvent event = (Frame3DAddedEvent) evt;
		Frame3D frame = event.getFrame3D();
		System.out.println("command : " + scene.getRecent() + " frame " + frame.getName());
		//TODO How to handle cases when the config is null
		if (scene.getRecent() != null){
			scene.addApplication(new Application(frame, new ApplicationConfig(scene.getRecent(), null)));
			//This must erase the recent config as its used
			scene.eraseRecent();
		} else {
			scene.addApplication(new Application(frame));
		}
	}

	public Class<LgEvent>[] getTargetEventClasses() {
		return new Class[]{
			Frame3DAddedEvent.class
		};
	}

}
