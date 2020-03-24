package org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts;

import javax.vecmath.Vector3d;

import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.RevolutionLayout3D;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase ubica los eventos de un componentes alrededor
 * del mismo de forma circular. 
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public class SphericalLayout3D extends RevolutionLayout3D {
	/** Logger de mensajes.*/
	private static Logger logger = Logger.getLogger("lg.ArchViz3D");	

	/**
	 * 
	 * @return Vector vector con los puntos calculados
	 */
	@SuppressWarnings ("unchecked")
	public Vector calculatePoints() {
		try {
			if (complexity == -1)
				setComplexity((int)Math.ceil(Math.sqrt(getClient().numComponents())));
			points = new Vector();
			double increment = Math.toRadians(180.0 / getComplexity());
			double angle = Math.toRadians(-90.0 + 90.0 / getComplexity());
			double sin, cos;
			for (int i = 0; i < this.getComplexity(); i++) {
				cos = Math.cos(angle);
				sin = Math.sin(angle);
				angle = angle + increment;
				points.add(new Vector3d((cos * getRadius()), (sin * getRadius()), 0.0f));
			}
			return points;
		}
		catch (NullPointerException e) {
			logger.log(Level.INFO, "El layout no tiene un cliente asignado");
			e.printStackTrace();
			return null;
		}
	}
}
