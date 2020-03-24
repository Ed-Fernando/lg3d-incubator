package org.jdesktop.lg3d.apps.archviz3d.geons.usecase;

import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.reqarch3D.Ucmap;

import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonSearcher;
import org.jdesktop.lg3d.apps.archviz3d.geons.usecase.GeonUseCaseManager;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;


/**
 * Esta clase se encarga de realizar la animacion de un caso de uso.
 * 
 * @author Juan Feldman
 *
 */
public class GeonUseCaseThreadManager implements Runnable {
	/** Logger de mensajes.*/
	private static Logger logger = Logger.getLogger("lg.ArchViz3D");	

	/** Vista de la arquitectura a la cual se le ejecutara el caso de uso.*/
	private J3DCompositeComponent view = null; 
	/** Contiene los datos del caso de uso, segun el file UseCaseMapping.xml.*/
	private Ucmap useCaseMap = null;
	/** Thread que ejecutara el caso de uso.*/
	private Thread myThread = null;
	/** Encargado de ejecutar el caso de uso.*/
	private GeonUseCaseManager geonUseCaseManager = null; 
	/** Para dejar a los componentes en su estado original ya que si el usuario
		hace un stop de la animacion, todos los componentes deben dejarse en su estado original.
		Tambien para evitar errores de redondeo.**/
	private Vector<GeonAbstract> components = null;

	/**
	 * Instancia el thread que se encarga de ejecutar el caso de uso.
	 * @param view Vista de la arquitectura a la cual se le ejecutara el caso de uso.
	 * @param useCaseMap Contiene los datos del caso de uso, segun el file UseCaseMapping.xml.
	 * @param geonUseCaseManager Encargado de ejecutar el caso de uso.  
	 */
	public GeonUseCaseThreadManager(J3DCompositeComponent view, Ucmap useCaseMap, GeonUseCaseManager geonUseCaseManager) {
		this.view = view;
		this.useCaseMap = useCaseMap;
		this.geonUseCaseManager = geonUseCaseManager;
		this.components = new Vector<GeonAbstract>();
		myThread = new Thread(this);
	}
	
	/**
	 * Comienza el thread.
	 */
	public void start() {
		if (myThread != null)
			myThread.start();
	}
	
	/**
	 * Frena el thread.
	 */
	public void stop() {
		myThread = null;
		restoreAllComponents();
	}
	
	/**
	 * Pongo a todos los componentes a su estado original
	 */
	public void restoreAllComponents() {
		for (Enumeration<GeonAbstract> e = components.elements(); e.hasMoreElements();) 
			e.nextElement().restore();
		
		components.removeAllElements();
	}
	
	/**
	 * Ejecuta el caso de uso.
	 */
	public void run() {
		// Creo un searcher para la vista de la arquitectura
		GeonSearcher geonSearcher = new GeonSearcher(view);  
		
		try {
			logger.log(Level.INFO, "BEGIN USE CASE: " + useCaseMap.getName().toUpperCase());
			
    		for (int i = 0; i < useCaseMap.getComponentList().sizeOfComponentArray(); i++) {
    			// Obtengo el nombre del componente de la arquitectura por el 
    			// cual debe pasar el flujo
    			String componentName = useCaseMap.getComponentList().getComponentArray(i);
    			String[] names = componentName.split("-");
    			componentName = names[0];
    			String eventName = null;
    			if (names.length > 1)
    				eventName = names[1];

    			// Obtengo el componente de la arquitectura por el cual debe  
    			// pasar el flujo
    			GeonAbstract component = null;
    			if (names.length > 1) 
					component = geonSearcher.searchComponentEvent(componentName, eventName);
    			else
        			component = geonSearcher.searchComponent(componentName);

    			// Ejecuto el flujo.
    			// El componente puede ser nulo en caso de que se hayan usado las abstracciones.
    			if (component != null) {
        			// Guardo todos los componentes para luego volverlos a su estado original
        			if (!components.contains(component)) {
        				component.saveOriginalValue();
        				components.add(component);
        			}

        			// Ejecuto la animacion en el componente
    				component.executeUseCase(geonUseCaseManager);

    				// Espero a que termine la animacion en el componente
    				//component.useCaseThreadManagerWait();
    				geonUseCaseManager.useCaseThreadManagerWait();

    				logger.log(Level.INFO, "Component Name: " + useCaseMap.getComponentList().getComponentArray(i));
    			}
    		}
			logger.log(Level.INFO, "END USE CASE  : " + useCaseMap.getName().toUpperCase());

		}catch(Exception e){
			e.printStackTrace();
		}
		
		// Pongo a todos los componentes a su estado original
		restoreAllComponents();
		
		// Termino el caso de uso 
		geonUseCaseManager.stopUseCase();
	}
}
