/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.listeners;

import java.awt.event.KeyEvent;

import org.jdesktop.lg3d.apps.nlc.knowledge.CurrentScene;
import org.jdesktop.lg3d.apps.nlc.knowledge.SceneFactory;
import org.jdesktop.lg3d.apps.nlc.mascot.BasicMascot;
import org.jdesktop.lg3d.wg.event.KeyEvent3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;

public class F12Listener implements LgEventListener {

	private BasicMascot mascot;

	public F12Listener(BasicMascot mascot) {
		if (mascot == null) {
			throw new NullPointerException(
					"Argument to method F12Listener is null");
		}
		this.mascot = mascot;
	}

	public void processEvent(LgEvent evt) {
		KeyEvent3D event = (KeyEvent3D) evt;
		if (!event.isPressed())
			return;
		if (event.getKeyCode() == KeyEvent.VK_F12) {
			System.out.println("Detected Hot Key for NLC");
			// User pressed F12
			if (mascot.isTakingInput()) {
				System.err.println("Do Not press F12 again and again");
			} else {
				mascot.storeFocussedApplication(SceneFactory.getInstance()
						.getCurrentScene().getFocussedApplication());
				mascot.start();
			}
		}
	}

	public Class<LgEvent>[] getTargetEventClasses() {
		return new Class[] { KeyEvent3D.class };
	}

}
