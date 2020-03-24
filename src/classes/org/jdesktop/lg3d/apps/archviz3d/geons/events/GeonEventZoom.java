package org.jdesktop.lg3d.apps.archviz3d.geons.events;

import java.awt.event.KeyEvent;

import org.jdesktop.lg3d.utils.action.ActionBooleanInt;
import org.jdesktop.lg3d.utils.action.ActionInt;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

/**
 * Clase para manejar el zoom de un contenedor.
 * 
 * @author Juan Feldman
 * 
 */
public class GeonEventZoom implements ActionBooleanInt, ActionInt {
	/**
	 * Instancia esta clase.
	 */
	public GeonEventZoom() {
	}

	/**
	 * Para manejar la rueda del mouse.
	 * @param source Objeto que genero el evento.
	 * @param value Este valor puede ser 1 cuando se hizo un 
	 * zoomin o -1 cuando se hizo un zoomout.
	 */
	public void performAction(LgEventSource source, int value) {
		Component3D comp = (Component3D)source;
		
		if (value < 0 && comp.getScale() > 0.4f || value > 0) 
			comp.changeScale(comp.getScale() + value/5f); 
	} 

	/**
	 * Con UP y DOWN hago el zoom.
	 * Con HOME vuelvo al inicio.
	 * @param source Objeto que genero el evento.
	 * @param pressed True si la tecla esta presionada, false en caso contrario.
	 * @param key Tecla presionada.
	 */
	public void performAction(LgEventSource source, boolean pressed, int key) {
		Component3D comp = (Component3D)source;

		if (pressed && key == KeyEvent.VK_DOWN) { 
			if (comp.getScale() > 0.4f) 
				comp.changeScale(comp.getScale() - 0.2f);
		}
		else if (pressed && key == KeyEvent.VK_UP) 
			comp.changeScale(comp.getScale() + 0.2f);
	}
}