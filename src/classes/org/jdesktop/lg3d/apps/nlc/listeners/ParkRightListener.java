/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.listeners;


import org.jdesktop.lg3d.apps.nlc.events.ParkRightEvent;
import org.jdesktop.lg3d.scenemanager.utils.event.Component3DGestureMoveRightEvent;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;

public class ParkRightListener implements LgEventListener {

	public void processEvent(LgEvent evt) {
		Frame3D frame = (Frame3D) evt.getSource();
		if (frame.isVisible()){
			frame.postEvent(new Component3DGestureMoveRightEvent());
		}
	}

	public Class<LgEvent>[] getTargetEventClasses() {
		return new Class[]{
				ParkRightEvent.class
		};
	}

}
