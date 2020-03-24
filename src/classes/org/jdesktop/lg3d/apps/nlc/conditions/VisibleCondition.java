/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.conditions;

import org.jdesktop.lg3d.apps.nlc.data.Application;
import org.jdesktop.lg3d.wg.Frame3D;

public class VisibleCondition implements Condition {

	public boolean satisfy(Application app) {
		Frame3D frame = app.getFrame();
		if (frame == null)
			return false;
		return frame.isEnabled() && frame.isVisible();
	}

}
