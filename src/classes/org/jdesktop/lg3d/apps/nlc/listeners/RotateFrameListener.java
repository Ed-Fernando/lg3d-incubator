/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.listeners;

import java.util.logging.Logger;

import org.jdesktop.lg3d.apps.nlc.events.RotateFrameEvent;
import org.jdesktop.lg3d.utils.action.RotateActionBoolean;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;

public class RotateFrameListener implements LgEventListener {

	private static Logger logger = Logger.getLogger("lg.nlc");

	private float round(float angle) {
		return Math.round((double) angle);
	}

	public void processEvent(LgEvent evt) {
		Frame3D frame = (Frame3D) evt.getSource();
		float angle = round(frame.getRotationAngle());
		logger.info("Attempting to rotate frame with rotation angle " + angle);
		if (angle == 0) {
			new RotateActionBoolean(frame, (float) Math.toRadians(180))
					.performAction(null, true);

		} else {
			new RotateActionBoolean(frame, (float) Math.toRadians(0))
			.performAction(null, true);
		}
	}

	public Class[] getTargetEventClasses() {
		return new Class[] { RotateFrameEvent.class };
	}

	public static void main(String[] args) {
		for (int i = 0; i <= 360; i += 10) {
			System.out.println(i + "\t" + Math.toRadians(i));
		}
	}

}
