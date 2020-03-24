package org.jdesktop.lg3d.apps.archviz3d.geons.menu;

import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;

/**
 * Clase padre de todos los GeonMenuState.
 * Las clases GeonMenuState fueron implementadas de acuerdo al Patron
 * de Diseño "State". 
 * 
 * @author Juan Feldman
 *
 */
public abstract class GeonMenuStateAbstract {
	/** Menu de la aplicacion.*/
	protected GeonMenu geonMenu = null;
	
	/**
	 * Instancia GeonMenuStateAbstract.
	 * @param geonMenu Menu de la aplicacion.
	 */
	public GeonMenuStateAbstract (GeonMenu geonMenu) {
		this.geonMenu = geonMenu; 
	}
	
	/**
	 * Maneja la habilitacion/deshabilitacion y las visibilidad de los botones del menu. 
	 */
	public abstract void handle();

}
