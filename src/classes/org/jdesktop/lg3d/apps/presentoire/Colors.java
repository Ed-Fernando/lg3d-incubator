package org.jdesktop.lg3d.apps.presentoire;

import javax.vecmath.Color4f;

/**
 * Implement a Color "parser"...
 * Supports 5 color names (easy to extend : just add a new static color field here in the class)
 * Supports HTML-like color strings : #aaffee or #12121212 (with alpha channel)
 * @author Pierre Ducroquet
 *
 */
public class Colors {
	public static final Color4f RED = new Color4f(1.0f, 0.0f, 0.0f, 1.0f);
	public static final Color4f GREEN = new Color4f(0.0f, 1.0f, 0.0f, 1.0f);
	public static final Color4f BLUE = new Color4f(0.0f, 0.0f, 1.0f, 1.0f);
	public static final Color4f WHITE = new Color4f(1.0f, 1.0f, 1.0f, 1.0f);
	public static final Color4f BLACK = new Color4f(0.0f, 0.0f, 0.0f, 1.0f);
	
	/**
	 * Parse a color string...
	 * @param the color string
	 * @return the right color, or black if color is not found. 
	 */
	public static Color4f getColor (String color) {
		// A # means it's an HTML-like color.
		if (color.startsWith("#")) {
			float r = Integer.parseInt(color.substring(1, 3), 16)/254;
			float g = Integer.parseInt(color.substring(3, 5), 16)/254;
			float b = Integer.parseInt(color.substring(5, 7), 16)/254;
			float a = 1.0f;
			if (color.length() == 9) {
				// There is an alpha channel in the color
				a = Integer.parseInt(color.substring(7, 9), 16)/254;
			}
			return new Color4f(r, g, b, a);
		}
		// Ok, try to find this color in our static fields.
		color = color.toUpperCase();
		try {
			return (Color4f) Colors.class.getDeclaredField(color).get(Colors.class);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		// Return default color.
		return BLACK;
	}
}
