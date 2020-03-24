/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.listeners;


import java.util.logging.Logger;

import org.jdesktop.lg3d.apps.nlc.knowledge.CurrentScene;
import org.jdesktop.lg3d.scenemanager.utils.event.Frame3DRemovedEvent;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;

public class Frame3DRemovedListener implements LgEventListener {

	private static Logger logger = Logger.getLogger("lg.nlc");
	
	private CurrentScene scene;
	
	public Frame3DRemovedListener(CurrentScene scene){
		if (scene == null) {
			throw new NullPointerException(
					"Argument to method EndFrameEventListener is null");
		}
		this.scene = scene;
	}
	
	public void processEvent(LgEvent evt) {
		Frame3DRemovedEvent event = (Frame3DRemovedEvent) evt;
		Frame3D frame = event.getFrame3D();
		System.out.println("Removing frame3d " + frame.getName());
		scene.removeApplication(frame);
	}

	public Class<LgEvent>[] getTargetEventClasses() {
		return new Class[]{
				Frame3DRemovedEvent.class
		};
	}

}
