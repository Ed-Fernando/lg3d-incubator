/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.mascot;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Logger;

import javax.vecmath.Vector3f;
import javax.vecmath.Color4f;

import org.jdesktop.lg3d.apps.nlc.Nlc;
import org.jdesktop.lg3d.apps.nlc.data.Application;
import org.jdesktop.lg3d.apps.nlc.events.ShutDownNLCEvent;
import org.jdesktop.lg3d.apps.nlc.knowledge.SceneFactory;
import org.jdesktop.lg3d.displayserver.AppConnectorPrivate;
import org.jdesktop.lg3d.utils.shape.GlassyText2D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.KeyEvent3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;

public class BasicMascot extends Mascot {

	private Application focussedApplication;

	private static Logger logger = Logger.getLogger("lg.nlc");

	public BasicMascot(Nlc nlc) {
		super(nlc);
	}

	private static final float WIDTHSCALE = 1.5f;

	private static final float MAXWIDTH = 0.1f;

	private static final float MAXHEIGHT = 0.01f;

	private Frame3D frame;

	private GlassyText2D tp;

	private void draw() {
		frame = new Frame3D();
		Component3D component = new Component3D();
		tp = new GlassyText2D(
                        "", MAXWIDTH, MAXHEIGHT, new Color4f(Color.YELLOW),
                        GlassyText2D.LightDirection.TOP_LEFT,
                        GlassyText2D.Alignment.LEFT, WIDTHSCALE);
		// tp.setText("Enter your command");
		component.addChild(tp);
		frame.addChild(component);
		frame.setPreferredSize(new Vector3f(0.05f, 0.05f, 0.05f));
		frame.changeEnabled(true);
		frame.changeVisible(true);
	}

	@Override
	public void start() {
		if (frame == null) {
			draw();
			frame.addListener(new CommandListener(this));
		} else {
			frame.setVisible(true);
		}
	}

	@Override
	public void stop() {
		frame.setEnabled(false);
	}

	public void speak(String text) {
		tp.setText(text);
	}

	public void hide() {
		frame.setVisible(false);
	}

	public boolean isTakingInput() {
		return frame != null && frame.isVisible();
	}

	@Override
	public void takeAction(String command) throws IOException {
		frame.setVisible(false);
		if (command.equals("NLCQUIT")) {
			// Post the quit event..
			AppConnectorPrivate.getAppConnector().postEvent(
					new ShutDownNLCEvent(), null);
		} else {
			super.takeAction(command);
		}
	}

	private class CommandListener implements LgEventListener {

		private StringBuffer command;

		private BasicMascot mascot;

		private static final String DEFAULT_MESSAGE = "Type your command in Natural Language";

		public CommandListener(BasicMascot mascot) {
			this.mascot = mascot;
			command = new StringBuffer();
			mascot.speak(DEFAULT_MESSAGE);
		}

		public void processEvent(LgEvent evt) {
			KeyEvent3D event = (KeyEvent3D) evt;
			if (!event.isPressed())
				return;
			int k = event.getKeyCode();
			switch (k) {
			case KeyEvent.VK_ENTER: {
				logger.info("Taking action on command " + command.toString());
				logger.info("Restoring focussed application "
						+ focussedApplication);
				SceneFactory.getInstance().getCurrentScene()
						.setFocussedApplication(focussedApplication);
				try {
					mascot.takeAction(command.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
				command = new StringBuffer();
				mascot.speak(DEFAULT_MESSAGE);
				break;
			}
			case KeyEvent.VK_BACK_SPACE: {
				if (command.length() > 0) {
					command.deleteCharAt(command.length() - 1);
					mascot.speak(command.toString());
				}
			}
			default: {
				char c = event.getKeyChar();
				if (Character.isLetter(c) || Character.isSpace(c)) {
					command.append(c);
					mascot.speak(command.toString());
				}
			}

			}
		}

		public Class<LgEvent>[] getTargetEventClasses() {
			return new Class[] { KeyEvent3D.class };
		}
	}

	public void storeFocussedApplication(Application focussedApplication) {
		this.focussedApplication = focussedApplication;
	}
}
