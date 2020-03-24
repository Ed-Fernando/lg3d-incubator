package org.jdesktop.lg3d.apps.blackgoat.button;

import java.net.URL;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;

/**
 * @author dai
 */
/**
 * this class is used for setting button appearance. 
 */
public class ButtonAppearance extends SimpleAppearance {
	
	/**
	 * sets image on button.
	 * @param filename
	 * @param on
	 */
	public ButtonAppearance(URL filename, boolean on) {
		super(0.0f, 0.0f, 0.0f, 0.0f, SimpleAppearance.DISABLE_CULLING
				| SimpleAppearance.ENABLE_TEXTURE);

		if (on) {
			setColor(1.0f, 0.6f, 0.6f, 0.8f);
		} else {
			setColor(0.6f, 0.7f, 1.0f, 0.6f);
		}
		try {
			setTexture(filename);
		} catch (Exception e) {
			throw new RuntimeException(
					"failed to initilaze window button: " + e);
		}
	}

	/**
	 * sets image on button.
	 * @param filename
	 * @param on
	 * @param dummy
	 */
	public ButtonAppearance(URL filename, boolean on, int dummy) {
		super(0.0f, 0.0f, 0.0f, 0.0f, SimpleAppearance.DISABLE_CULLING
				| SimpleAppearance.ENABLE_TEXTURE);

		if (on) {
			setColor(1.0f, 1.0f, 1.0f, 1.0f);
		} else {
			setColor(0.7f, 0.7f, 0.7f, 0.9f);
		}
		try {
			setTexture(filename);
		} catch (Exception e) {
			throw new RuntimeException(
					"failed to initilaze window button: " + e);
		}
	}
}