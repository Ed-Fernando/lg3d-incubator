/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.listeners;


import org.jdesktop.lg3d.apps.nlc.events.FocusFrameEvent;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.Component3DToFrontEvent;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;

public class FocusFrameListener implements LgEventListener {

	public void processEvent(LgEvent evt) {
		Frame3D frame = (Frame3D)evt.getSource();
		frame.postEvent(new Component3DToFrontEvent());
		frame.setVisible(true);
		frame.postEvent(new Component3DToFrontEvent());
	}

	public Class<LgEvent>[] getTargetEventClasses() {
		return new Class[]{
			FocusFrameEvent.class	
		};
	}

}
