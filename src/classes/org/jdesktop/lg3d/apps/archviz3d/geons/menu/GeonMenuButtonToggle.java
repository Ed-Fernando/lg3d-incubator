package org.jdesktop.lg3d.apps.archviz3d.geons.menu;

import java.util.Enumeration;

import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.action.ActionFloat2;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuButton;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuButtonToggle;

/**
 * Esta clase representa un boton que puede tener dos estados:
 * 1) Seleccionado.
 * 2) No seleccionado.
 * Se utiliza el estado del boton para su visualizacion. 
 * Ademas, dentro de un contenedor solo puede haber un solo
 * GeonMenuToggleButton seleccionado.  
 * 
 * @author Juan Feldman
 *
 */
public class GeonMenuButtonToggle extends GeonMenuButton {
	/** Contenedor del boton.*/
	private Component3D container = null;
	/** Estado del boton.*/
	private boolean clicked = false;

	/**
	 * Instancia esta clase. 
	 * @param container Contenedor del boton. 
	 * @param imageName Nombre de la imagen del boton.
	 * @param label Texto del boton. 
	 * @param position Posicion del boton.  
	 */
	public GeonMenuButtonToggle(Component3D container, String imageName, String label, Point3f position) {
		super(imageName, label, position);
		this.container = container;
	}
		
	/**
	 * Instancia esta clase. 
	 * @param container Contenedor del boton. 
	 * @param imageName Nombre de la imagen del boton.
	 * @param label Texto del boton. 
	 * @param position Posicion del boton.  
	 * @param width Ancho del boton.
	 * @param height Largo del boton.
	 * @param deep Profundidad del boton.
	 */
	public GeonMenuButtonToggle(Component3D container, String imageName, String label, Point3f position, float width, float height, float deep) {
		super(imageName, label, position, width, height, deep);
		this.container = container;
	}

	/**
	 * Agrego el comportamiento para que cuando clickeo el boton cambie el 
	 * color del panel de fondo.  
	 */ 
	protected void addMouseClickedListener() {
		addListener(new MouseClickedEventAdapter(new ActionFloat2() {
			public void performAction(LgEventSource source, float x, float y) {
				GeonMenuButtonToggle button = (GeonMenuButtonToggle) source;
				
				if (!clicked) {
					// Seteo todos los botones al estado no clickeado
					for (Enumeration e = container.getAllChildren(); e.hasMoreElements();) {
						GeonMenuButtonToggle buttonAux = (GeonMenuButtonToggle)e.nextElement();
						buttonAux.setClicked(false);
					}

					// Seteo el boton que recibio el click al estado clickeado
					button.setClicked(true);
				}
				else 
					button.setClicked(false);
			}
		}));
	}
	
	/**
	 * Cambia la apariencia del panel de fondo. 
	 */
	private void changeAppearance() {
		Appearance app;

		if (clicked) 
			app = new SimpleAppearance(0.7f, 0.0f, 0f, 1.0f, SimpleAppearance.DISABLE_CULLING);
		else  
			app = new SimpleAppearance(0.0f, 0.5f, 0f, 1.0f, SimpleAppearance.DISABLE_CULLING);

	    getGlassyPanel().setAppearance(app);
	}

	/**
	 * Seteo el estado del boton (clickeado/no clickeado).
	 * @param clicked Estado del boton.
	 */
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
		this.changeAppearance();
	}
	
	/**
	 * Devuelve true si el boton esta clickeado o false en caso contrario.
	 * @return clicked Estado del boton.
	 */
	public boolean getClicked() {
		return clicked; 
	}
	
}
