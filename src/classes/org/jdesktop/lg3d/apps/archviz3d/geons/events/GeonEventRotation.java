package org.jdesktop.lg3d.apps.archviz3d.geons.events;

import java.awt.event.KeyEvent;

import org.jdesktop.lg3d.utils.action.ActionBooleanInt;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 * Clase para manejar la rotacion de un contenedor.
 * 
 * @author Juan Feldman
 * 
 */
public class GeonEventRotation implements ActionBooleanInt {

	/**
	 * Instancia esta clase.
	 */
	public GeonEventRotation() {
	}

	/**
	 * Con LEFT y RIGHT hago la rotacion.
	 * @param source Objeto que produjo este evento.
	 * @param pressed True si la tecla esta presionada, false en caso contrario.
	 * @param key Tecla presionada.
	 */
	public void performAction(LgEventSource source, boolean pressed, int key) {
		Component3D comp = (Component3D)source;  

		comp.setRotationAxis(0.0f, 1.0f, 0.0f);
		if (pressed && key == KeyEvent.VK_RIGHT) 
			comp.setRotationAngle(comp.getRotationAngle() + 0.1f);
		else if (pressed && key == KeyEvent.VK_LEFT) 
			comp.setRotationAngle(comp.getRotationAngle() - 0.1f);
	}
}
