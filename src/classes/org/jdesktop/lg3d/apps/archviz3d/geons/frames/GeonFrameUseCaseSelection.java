package org.jdesktop.lg3d.apps.archviz3d.geons.frames;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.vecmath.Point3f;

import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.reqarch3D.Ucmap;

import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonAnimator;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrame;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameButton;
import org.jdesktop.lg3d.apps.archviz3d.geons.usecase.GeonUseCaseManager;


/**
 * Esta clase permite seleccionar el caso de uso a ejecutar. 
 * 
 * @author Juan Feldman
 *
 */
public class GeonFrameUseCaseSelection extends GeonFrame {
	/** Encargado de ejecutar el caso de uso.*/
	private GeonUseCaseManager geonUseCaseManager = null;
	/** Contenedor de las imagenes.*/
	private Vector<Component3D> containers = null;
	/** Caso de uso seleccionado.*/
	private String useCaseNameSelected = null;

	/**
	 * Instancia el Frame de seleccion.
	 * 
	 * @param width Ancho del Frame.
	 * @param height Largo del Frame.
	 * @param depth Profundidad del Frame.
	 * @param initialPosition Posicion initial del Frame.
	 * @param geonUseCaseManager Encargado de ejecutar el caso de uso.  
	 * @param caller Llamador del Frame.
	 */
	public GeonFrameUseCaseSelection(float width, float height, float depth, Point3f initialPosition, GeonUseCaseManager geonUseCaseManager , Component3D caller) {
        super(width, height, depth, initialPosition, caller);
        this.geonUseCaseManager = geonUseCaseManager ;
        this.containers = new Vector<Component3D>();
        createFrame();
		setTitle("Use Case Selection");
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
        
        Map map = geonUseCaseManager.getUseCaseMap();
        float cantUses = 3;
        float yPosition = getCentralHeight()/(cantUses*2f);
        for (Iterator iterator = map.values().iterator(); iterator.hasNext();) {
        	Ucmap ucmap = (Ucmap)iterator.next();
   
        	GeonFrameButton buttonUseCase = new GeonFrameButton("usecase-name/" + ucmap.getName() + ".png", new Point3f(getInitialPosition().x + getWidth()/2f, getInitialPosition().y - getUpperHeight() - GeonFrame.SUBFRAME_SEPARATION - yPosition, getInitialPosition().z + 0.0035f), getWidth() - 0.004f, getCentralHeight()/cantUses - 0.0035f);
        	buttonUseCase.setName(ucmap.getName());
        	buttonUseCase.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
            	public void performAction(LgEventSource source) {
            		image_click((Component3D)source);
            	}
            }));
        	
        	yPosition = yPosition + getCentralHeight()/cantUses;
        	getFrameContainer().addChild(buttonUseCase);
            containers.add(buttonUseCase);
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
		useCaseNameSelected = ((GeonFrameButton)containerClicked).getName();
	}

	/** 
	 * Click para el boton Accept del frame. 
	 */
	public void buttonAccept_click() {
		if (useCaseNameSelected != null) {
			getGeonFrameManager().buttonAccept_GeonFrameUseCaseSelection_click(geonUseCaseManager, useCaseNameSelected);	

			// Cierro el frame
			close();
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
