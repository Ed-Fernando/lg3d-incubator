package org.jdesktop.lg3d.apps.archviz3d.geons.events;

import java.util.Vector;

import org.jdesktop.lg3d.utils.action.ActionFloat3;
import org.jdesktop.lg3d.wg.event.LgEventSource;

import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventClick;



/**
 * Clase para manejar la seleccion de un Geon de la
 * arquitectura.
 * 
 * @author Juan Feldman
 *
 */
public class GeonEventClick implements ActionFloat3 {
	/** Unica instancia de esta clase.*/
	private static GeonEventClick geonClickAction = null;
	/** Contiene todos los geones en los cuales se hizo click.*/
	private Vector<GeonAbstract> geonsSelected = null;

	/**
	 * Instancia esta clase.
	 */
    private GeonEventClick () {
    	geonsSelected = new Vector<GeonAbstract>();
    }

    /**
     * Retorna la unica instancia de esta clase.
     * @return Unica instancia de esta clase.
     */
    public static GeonEventClick  instance() {
        if (geonClickAction  == null)
        	geonClickAction = new GeonEventClick ();
        return geonClickAction ;
    }

    /**
	 * Agrega el Geon seleccionado al vector de geones
	 * seleccionados.
	 */
	public void performAction(LgEventSource source, float x, float y, float z) {
		geonsSelected.add((GeonAbstract)source);
	}

	/**
	 * Devuelve el Geon seleccionado por el usuario (al clickear sobre el).
	 * @return GeonAbstract Geon seleccionado.
	 */
	public GeonAbstract getGeonSelected() {
		return geonsSelected.elementAt(0);
	}

	/**
	 * Retorna si true el usuario ha seleccionado algun Geon y false
	 * en caso contrario. 
	 * @return boolean True si el usuario ha seleccionado algun Geon,
	 * false en caso contrario. 
	 */
	public boolean haveSelection() {
		return geonsSelected.size() > 0; 
	}

	/**
	 * Elimina del vector todos los geones seleccionados.
	 */
	public void removeAllSelection() {
		geonsSelected.removeAllElements();
	}
}  

