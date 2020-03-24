package org.jdesktop.lg3d.apps.archviz3d.geons.events;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionFloat3;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 * Clase para manejar la traslacion de un contenedor.
 * 
 * @author Juan Feldman
 * 
 */
public class GeonEventTraslation implements ActionFloat3, ActionBoolean {
	/** Centro del componente a trasladar.*/
	private Vector3f center = new Vector3f(); 

	/**
	 * Instancia esta clase.
	 */
	public GeonEventTraslation() {
	}

	/**
	 * Traslado el contenedor.
	 * @param source Objeto que genero el evento.
	 * @param x Traslacion en x.
	 * @param y Traslacion en y.
	 */
	public void performAction(LgEventSource source, float x, float y, float z) {
		Component3D comp = (Component3D)source;

		Vector3f translation = new Vector3f(); 
		comp.getTranslation(translation);
		
		comp.setTranslation(center.x + x, center.y + y, translation.z);
	}

	/**
	 * Obtengo la posicion del contenedor al comenzar el arrastre del mouse.
	 * @param source Objeto que genero el evento.
	 * @param state Estado del mouse (PRESSED = true).
	 */
	public void performAction(LgEventSource source, boolean state) {
		Component3D comp = (Component3D)source;

		if (state) //MOUSE PRESSED
			comp.getTranslation(center);
	}

}