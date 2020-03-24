
package org.jdesktop.lg3d.apps.blackgoat.button;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.action.AppearanceChangeAction;
import org.jdesktop.lg3d.utils.action.ScaleActionBoolean;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.wg.Component3D;

/**
 * @author dai
 */
/**
 * this button class has 2 appearances, on focusing case and on not focusing case.
 */
public class Button extends Component3D {
	/**
	 * constructs buttion size and its appearance. in this case, button focusing on's and off's
	 * appearance are same.
	 * @param size
	 * @param app
	 */
	public Button(float size, Appearance app) {
		this(size, app, size, app);
	}

	/**
	 * constructs button size and its appearance.
	 * @param sizeOff
	 * @param appOff
	 * @param sizeOn
	 * @param appOn
	 */
	public Button(float sizeOff, Appearance appOff, float sizeOn,
			Appearance appOn) {
		Shape3D shape = new ImagePanel(sizeOff, sizeOff);
		shape.setAppearance(appOff);
		addChild(shape);
		if (appOff != appOn) {
			addListener(new MouseEnteredEventAdapter(
					new AppearanceChangeAction(shape, appOn)));
		}
		if (sizeOff != sizeOn) {
			addListener(new MouseEnteredEventAdapter(
					new ScaleActionBoolean(this, sizeOn / sizeOff, 100)));
		}
	}
}
