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
 * Clase para seleccionar los nombres de las arquitecturas que
 * se desean comparar. 
 * 
 * @author Juan Feldman
 *
 */
public class GeonFrameCompareSelection extends GeonFrame {
	/** Contenedor de las imagenes para la arquitectura 1.*/
	private Vector<Component3D> container1 = null;
	/** Contenedor de las imagenes para la arquitectura 2.*/
	private Vector<Component3D> container2 = null;
	/** Nombre de la arquitectura 1 seleccionada.*/
	private String architectureName1Selected = null;
	/** Nombre de la arquitectura 2 seleccionada.*/
	private String architectureName2Selected = null;

	/**
	 * Instancia el Frame de seleccion.
	 * 
	 * @param width Ancho del Frame.
	 * @param height Largo del Frame.
	 * @param depth Profundidad del Frame.
	 * @param initialPosition Posicion initial del Frame.
	 * @param caller Llamador del Frame.
	 */
	public GeonFrameCompareSelection(float width, float height, float depth, Point3f initialPosition, Component3D caller) {
		super(width, height, depth, initialPosition, caller);
		this.container1 = new Vector<Component3D>();
		this.container2 = new Vector<Component3D>();
		createFrame();
		setTitle("Compare Selection");
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
        float architecturesNumber = architecturesNames.size();

		//Agrego los encabezados de la seleccion
    	GeonFrameButton buttonTitleArq1 = new GeonFrameButton("architecture-selection/architectureone.png", new Point3f(getInitialPosition().x + 0.25f*getWidth(), getInitialPosition().y - getUpperHeight() - GeonFrame.SUBFRAME_SEPARATION - 0.0055f, getInitialPosition().z + 0.0035f), getWidth()/2f - 0.004f, getUpperHeight());
    	getFrameContainer().addChild(buttonTitleArq1);

    	GeonFrameButton buttonTitleArq2 = new GeonFrameButton("architecture-selection/architecturetwo.png", new Point3f(getInitialPosition().x + 0.75f*getWidth(), getInitialPosition().y - getUpperHeight() - GeonFrame.SUBFRAME_SEPARATION - 0.0055f, getInitialPosition().z + 0.0035f), getWidth()/2f - 0.004f, getUpperHeight());
    	getFrameContainer().addChild(buttonTitleArq2);
		
		// Creo las imagenes de las arquitecturas
        float yPosition = (getCentralHeight() - getUpperHeight() - 0.0055f)/(architecturesNumber*2f) + getUpperHeight() + 0.0015f;
		for (Enumeration<String> e = architecturesNames.elements(); e.hasMoreElements(); ) {
        	String architectureName = e.nextElement();
            
        	//Arquitecturas 1
        	GeonFrameButton buttonArch1 = new GeonFrameButton("architecture-selection/" + architectureName + ".png", new Point3f(getInitialPosition().x + 0.25f*getWidth(), getInitialPosition().y - getUpperHeight() - GeonFrame.SUBFRAME_SEPARATION - yPosition - 0.002f, getInitialPosition().z + 0.0035f), getWidth()/2f - 0.004f, (getCentralHeight() - getUpperHeight() - 0.0055f)/architecturesNumber - 0.001f);
        	buttonArch1.setName(architectureName);
        	buttonArch1.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            	public void performAction(LgEventSource source) {
					Component3D containerClicked = (Component3D)source;

					// Pongo las imagenes a su estado initial
            		initializeImages(container1);
            		
            		//Resalto la Arquitectura seleccionada
            		containerClicked.setScale(0.9f);
            		architectureName1Selected = ((GeonFrameButton)containerClicked).getName();
            	}
            }));
        	getFrameContainer().addChild(buttonArch1);
            container1.add(buttonArch1);

            //Arquitecturas 2
        	GeonFrameButton buttonArch2 = new GeonFrameButton("architecture-selection/" + architectureName + ".png", new Point3f(getInitialPosition().x + 0.75f*getWidth(), getInitialPosition().y - getUpperHeight() - GeonFrame.SUBFRAME_SEPARATION - yPosition - 0.002f, getInitialPosition().z + 0.0035f), getWidth()/2f - 0.004f, (getCentralHeight() - getUpperHeight() - 0.0055f)/architecturesNumber - 0.001f);
        	buttonArch2.setName(architectureName);
        	buttonArch2.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            	public void performAction(LgEventSource source) {
					Component3D containerClicked = (Component3D)source;

					// Pongo las imagenes a su estado initial
            		initializeImages(container2);
            		
            		//Resalto la Arquitectura seleccionada
            		containerClicked.setScale(0.9f);
            		architectureName2Selected = ((GeonFrameButton)containerClicked).getName();
            	}
            }));
        	getFrameContainer().addChild(buttonArch2);
            container2.add(buttonArch2);

            yPosition = yPosition + (getCentralHeight() - getUpperHeight() - 0.0055f)/architecturesNumber ;
		}

		getFrameContainer().addChild(buttonAccept);
	}

	/**
	 * Inicializo las imagenes de un contenedor.
	 * @param container Contenedor de las imagenes. 
	 */
	public void initializeImages(Vector<Component3D> container) {
		// Pongo todos los contenedores a su estado initial
		for (Enumeration enume = container.elements(); enume.hasMoreElements(); ) {
			Component3D imageContainer = (Component3D)enume.nextElement();
			imageContainer.setScale(1f);
		}
	}

	/** 
	 * Click para el boton Accept del frame. 
	 */
	public void buttonAccept_click() {
		if ((architectureName1Selected != null) && (architectureName2Selected != null)) {
			// Cierro el frame
			close();

			// Creo la vista
			getGeonFrameManager().buttonAccept_GeonFrameCompareSelection_click(architectureName1Selected, architectureName2Selected);	
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
