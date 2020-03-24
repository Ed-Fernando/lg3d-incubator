package org.jdesktop.lg3d.apps.archviz3d.geons.frames;

import java.util.Enumeration;
import java.util.Vector;
import javax.vecmath.Point3f;

import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrame;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameButton;
import org.jdesktop.lg3d.apps.archviz3d.geons.main.ArchViz3D;


/**
 * Frame para seleccionar las arquitecturas que pueden visualizarse. 
 * 
 * @author Juan Feldman
 *
 */
public class GeonFrameArchitectureSelection extends GeonFrame {
	/** Contenedor de las imagenes de las arquitecturas.*/
	private Vector<Component3D> containers = null;
	/** Nombre de la arquitectura seleccionada.*/
	private String architectureNameSelected = null;

	/**
	 * Instancia el Frame de seleccion.
	 * 
	 * @param width Ancho del Frame.
	 * @param height Largo del Frame.
	 * @param depth Profundidad del Frame.
	 * @param initialPosition Posicion initial del Frame.
	 * @param caller Llamador del Frame
	 */
	public GeonFrameArchitectureSelection(float width, float height, float depth, Point3f initialPosition, Component3D caller) {
		super(width, height, depth, initialPosition, caller);
        this.containers = new Vector<Component3D>();
        createFrame();
		setTitle("Architecture Selection");
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

        Vector<String> architecturesNames = ArchViz3D.getArchitecturesNames();
        int architecturesNumber = architecturesNames.size();
        float xPosition = (1f/(architecturesNumber*2f))*getWidth();
        for (Enumeration<String> e = architecturesNames.elements(); e.hasMoreElements(); ) {
            String architectureName = e.nextElement();
        	GeonFrameButton buttonArq = new GeonFrameButton("architecture-selection/" +  architectureName + ".png", new Point3f(getInitialPosition().x + xPosition, getInitialPosition().y - getUpperHeight() - GeonFrame.SUBFRAME_SEPARATION - getCentralHeight()/2f, getInitialPosition().z + 0.0035f), getWidth()/3f - 0.004f, getHeight() - getUpperHeight() - getBottomHeight() - 0.0035f);
        	buttonArq.setName(architectureName);
        	xPosition = xPosition + (1f/architecturesNumber)*getWidth();
        	buttonArq.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            	public void performAction(LgEventSource source) {
            		architecture_click((Component3D)source);
            	}
            }));
        	getFrameContainer().addChild(buttonArq);
            containers.add(buttonArq);
        }

        getFrameContainer().addChild(buttonAccept);
	}
	
	/**
	 * Click sobre el texto.
	 */
	public void architecture_click(Component3D containerClicked) {
		// Pongo todas las arquitecturas a su estado initial
		for (Enumeration enume = containers.elements(); enume.hasMoreElements(); ) {
			Component3D container = (Component3D)enume.nextElement();
			container.setScale(1f);
		}

		//Resalto la Arquitectura seleccionada
		containerClicked.setScale(0.9f);
		architectureNameSelected = ((GeonFrameButton)containerClicked).getName();
	}

	/** 
	 * Click para el boton Accept del frame. 
	 */
	public void buttonAccept_click() {
		if (architectureNameSelected != null) {
			// Cierro el frame
			close();

			// Creo la vista
			getGeonFrameManager().buttonAccept_GeonFrameArchitectureSelection_click(architectureNameSelected);	
		}
	}
    
	/**
	 * Cierra el Frame.
	 */
	public void close() {
	    this.detach();
	    getGeonFrameManager().frameClosed(this);
	}

}
