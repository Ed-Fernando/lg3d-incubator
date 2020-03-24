package org.jdesktop.lg3d.apps.archviz3d.geons.usecase;

import java.io.File;
import java.util.Map;

import org.reqarch3D.Ucmap;

import org.jdesktop.lg3d.apps.archviz3d.geons.usecase.GeonUseCaseHandler;
import org.jdesktop.lg3d.apps.archviz3d.geons.usecase.GeonUseCaseMonitorComponent;
import org.jdesktop.lg3d.apps.archviz3d.geons.usecase.GeonUseCaseMonitorManager;
import org.jdesktop.lg3d.apps.archviz3d.geons.usecase.GeonUseCaseThreadManager;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;


/**
 * Esta clase se encarga de ejecutar los casos de uso de una arquitectura.
 * Esta clase actua como un monitor para la clase GeonUseCaseThreadComponent.
 * 
 * @author Juan Feldman
 *
 */
public class GeonUseCaseManager {
    /** Mapea el nombre de un caso de uso con sus datos en el file UseCaseMappings.XML.*/
    private Map map = null;
    /** Vista de la arquitectura a la cual se le ejecutara el caso de uso.*/
    private J3DCompositeComponent view = null;
    /** Tiempo que se duermen los thread encargados de la animacion
     * del caso de uso.*/
    private int sleepTime;
    /** Numero de iteraciones para los thread encargados de la animacion
     * del caso de uso.*/
    public final static int NUMBER_LOOPS = 20;
    /** Tiempo maximo que se puede dormir un thread.*/
    public final static int MAX_SLEEP = 180;
    /** Tiempo minimo que se puede dormir un thread.*/
    public final static int MIN_SLEEP = 20;
    /** Thread encargado de la animacion del caso de uso.*/
    private GeonUseCaseThreadManager thread = null; 
    /** Monitor para pausar la animacion del caso de uso.*/
    private GeonUseCaseMonitorComponent monitorComponent = null;
    /** Monitor para pausar el thread encargado de animar el caso de uso
     * y comenzar la animacion del componente de la arquitectura.*/
    private GeonUseCaseMonitorManager monitorManager = null;
	
    /**
     * Se instancia el GeonUseCaseManager.
     * @param view Vista de la arquitectura a la cual se le ejecutara el caso de uso.
     */
    public GeonUseCaseManager(J3DCompositeComponent view) {
    	this.view = view;
    	this.sleepTime = 100; 
    	this.monitorComponent = new GeonUseCaseMonitorComponent();
    	this.monitorManager = new GeonUseCaseMonitorManager();
    	try {
    		map = GeonUseCaseHandler.getMap(new File(System.getProperty("lg.resourcedir") + "archviz3d/architectures/" + view.getName() + "/UseCaseMappings.xml"));
    	} catch(Exception e) {e.printStackTrace();}
    }
    
    /**
     * Ejecuta el caso de uso. 
     * 
     * @param useCaseName Nombre del caso de uso a ejecutar.   
     */
    public void executeUseCase(String useCaseName) {
        // Esta logica es compleja porque se tiene que evitar que el
        // usuario quiera ejecutar un caso de uso antes de que la 
        // ejecucion del caso de uso anterior haya terminado (por clickear
    	// dos o mas veces play).
    	
    	// El caso de uso no esta ejecutando o no fue ejecutado nunca 
    	// Aca entra cuando:
    	// a) La primera vez que se ejecuta el caso de uso
    	// b) Cuando el caso de uso termino de ejecutarse y se quiere volver
    	// a ejecutarlo
    	// c) Cuando se hizo un stop de la ejecucion
    	if (thread == null) {  
			Ucmap useCaseMap = (Ucmap)map.get(useCaseName);
			thread = new GeonUseCaseThreadManager(view, useCaseMap, this);
	    	thread.start();
    	}        
    	// El caso de uso fue pausado
    	// Aca entra cuando:
    	// a) Se pauso el caso de uso y luego se clickeo play
    	else if ((thread != null) && getPause())  
			setPause(false);
    }
    
    /**
     * Finaliza la ejecucion del caso de uso.
     */
    public void stopUseCase() {
    	if (thread != null) 
    		thread.stop();
    	thread = null;
    	monitorComponent = new GeonUseCaseMonitorComponent();
    	monitorManager = new GeonUseCaseMonitorManager();
    }
    
    /**
     * Pausa la ejecucion del caso de uso.
     */
    public void pauseUseCase() {
    	setPause(true);
    }
    
    /**
     * Retorna los datos de los casos de uso, segun el file
     * UseCaseMappings.XML.
     * @return Map Datos de los casos de uso.
     */
    public Map getUseCaseMap() {
    	return map;
    }
    
    /**
     * Decrementa la cantidad de tiempo que se duermen los threads
     * durante la animacion. Esto provoca que la animacion sea 
     * mas rapida.
     */
    public synchronized void incrementAnimationSpeed() {
    	if (sleepTime - 20 >= MIN_SLEEP)
    		sleepTime -= 20;
    }
    
    /**
     * Incrementa la cantidad de tiempo que se duermen los threads
     * durante la animacion. Esto provoca que la animacion sea 
     * mas lenta.
     */
    public synchronized void decrementAnimationSpeed() {
    	if (sleepTime + 20 <= MAX_SLEEP)
    		sleepTime += 20;
    }
    
    /**
     * Retorna el tiempo que se duerme el thread encargado
     * de animar un componente de la arquitectura.
     * @return int Tiempo que se duerme el thread encargado
     * de animar un componente de la arquitectura.
     */
    public synchronized int getAnimationSpeed() {
    	return sleepTime;
    }
    
/******************************************************************************************************/
/*					METODOS PARA MANEJAR LA EJECUCION DEL THREAD COMPONENT 					  	  	  */ 
/******************************************************************************************************/
	/**
	 *  Si el valor de pause es true, duerme a los threads
	 *  hasta que el valor de pause sea false. 
	 */
	public void haveToWait() {
    	monitorComponent.haveToWait();
	}
	
	/**
	 * Setea el valor de la pausa.
	 * @param pause Valor de la pausa.
	 */
	public void setPause(boolean pause) {
    	monitorComponent.setPause(pause);
	}
	
	/**
	 * Retorna el valor de la pausa.
	 * @return boolean Valor de la pausa.
	 */
	public boolean getPause() {
    	return monitorComponent.getPause();
	}
	
	/**
	 * Retorna true si el el caso de uso esta ejecutando y false
	 * en caso contrario.
	 * @return boolean True si el caso de uso esta ejecutando y false
	 * en caso contrario.
	 */
	public synchronized boolean isRunning() {
		return (thread != null);
	}
	
/******************************************************************************************************/
/*					METODOS PARA MANEJAR LA EJECUCION DEL THREAD MANAGER  					  	  	  */ 
/******************************************************************************************************/
	/**
	 * Duerme al thread GeonUseCaseThreadManager hasta que termine la animacion
	 * del componente de la arquitectura que se esta animando. 
	 */
	public void useCaseThreadManagerWait() {
    	monitorManager.useCaseThreadManagerWait();
    }

	/**
	 * Despierta al thread GeonUseCaseThreadManager, encargado de la animacion del caso de uso.
	 */
    public void useCaseThreadManagerWakeup() {
    	monitorManager.useCaseThreadManagerWakeup();
    }
	
}
