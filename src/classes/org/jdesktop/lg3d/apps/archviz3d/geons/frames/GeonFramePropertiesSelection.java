package org.jdesktop.lg3d.apps.archviz3d.geons.frames; 

import java.util.Enumeration;
import java.util.Vector;
import javax.vecmath.Point3f;

import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonAnimator;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrame;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameButton;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;


/**
 * Esta clase permite seleccionar una propiedad asociada con una
 * arquitectura.
 * 
 * @author Juan Feldman
 *
 */
public class GeonFramePropertiesSelection extends GeonFrame {
	/** Contenedores de las imagenes.*/
	private Vector<Component3D> containers = null;
	/** Vista de la arquitectura.*/
	private J3DCompositeComponent view = null;
	/** Nombre de la propiedad seleccioanda.*/
	private String propertyNameSelected = null;

	/**
	 * Instancia el Frame de seleccion.
	 * 
	 * @param width Ancho del Frame.
	 * @param height Largo del Frame.
	 * @param depth Profundidad del Frame.
	 * @param initialPosition Posicion initial del Frame.
	 * @param view Vista de la Arquitectura.
	 * @param caller Llamador del Frame.
	 */
	public GeonFramePropertiesSelection(float width, float height, float depth, Point3f initialPosition, J3DCompositeComponent view, Component3D caller) {
		super(width, height, depth, initialPosition, caller);
		this.containers = new Vector<Component3D>();
		this.view = view;
		createFrame();
		setTitle("Property Selection");
	}

	/**
	 * Crea el Frame de seleccion.
	 */
	private void createFrame() {
		// Creo los botones del Frame
		GeonFrameButton buttonAccept = new GeonFrameButton("buttons/accept.png", new Point3f(getInitialPosition().x + 0.1f*getWidth() + 0.003f, getInitialPosition().y - getUpperHeight() - getCentralHeight() - getBottomHeight()/2f - 2 * GeonFrame.SUBFRAME_SEPARATION, getInitialPosition().z + 0.001f), getWidth()/5f, getBottomHeight() - 0.004f);
		buttonAccept.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				buttonAccept_click();
			}
		}
		));

		//Agrego un encabezado, que indica la escala
		GeonFrameButton buttonEscale = new GeonFrameButton("properties/scale.png", new Point3f(getInitialPosition().x + (getWidth() * (0.75f)), getInitialPosition().y - getUpperHeight() - GeonFrame.SUBFRAME_SEPARATION - (getCentralHeight()/2f), getInitialPosition().z + 0.0035f), (getWidth() * (0.5f)) - 0.004f, getCentralHeight() - 0.0035f);
		getFrameContainer().addChild(buttonEscale);

    	// Genero el frame con la lista de propiedades
		String[] propertiesName = new String[] {"Performance", "Reliability", "Reusability", "Modificability"};

		float yPosition = (getCentralHeight()/(propertiesName.length*2f));
		for (int i = 0; i < propertiesName.length; i++) {
			GeonFrameButton buttonProperty = new GeonFrameButton("properties/" + propertiesName[i] + ".png", new Point3f(getInitialPosition().x + getWidth()/4f, getInitialPosition().y - getUpperHeight() - GeonFrame.SUBFRAME_SEPARATION - yPosition, getInitialPosition().z + 0.0035f), getWidth()/2f - 0.004f, getCentralHeight()/4f - 0.0035f);
			buttonProperty.setName(propertiesName[i]);
        	buttonProperty.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            	public void performAction(LgEventSource source) {
            		image_click((Component3D)source);
            	}
            }));
        	
        	yPosition = yPosition + (getCentralHeight()/(propertiesName.length));
        	getFrameContainer().addChild(buttonProperty);
            containers.add(buttonProperty);
		}

		getFrameContainer().addChild(buttonAccept);
	}

	/**
	 * Click sobre la imagen.
	 */
	public void image_click(Component3D containerClicked) {
		// Pongo todas las imagenes a su estado initial
		for (Enumeration enume = containers.elements(); enume.hasMoreElements(); ) {
			Component3D container = (Component3D)enume.nextElement();
			container.setScale(1f);
		}

		//Resalto la imagen seleccionada
		containerClicked.setScale(0.9f);
		propertyNameSelected = ((GeonFrameButton)containerClicked).getName();
	}

	/** 
	 * Click para el boton Accept del frame. 
	 */
	public void buttonAccept_click() {
		if (propertyNameSelected != null) {
			// Cierro el frame
			close();

			// Creo la vista
			getGeonFrameManager().buttonAccept_GeonFramePropertiesSelection_click(view, propertyNameSelected);	
		}
	}

	/**
	 * Cierra el Frame.
	 */
	public void close() {
		this.detach();
	    getGeonFrameManager().frameClosed(this);

		// Muestro las vistas
		GeonAnimator.instance().pullAll();
	}

}
