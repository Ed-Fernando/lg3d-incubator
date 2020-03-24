package org.jdesktop.lg3d.apps.archviz3d.geons.usecase;

/**
 * Esta clase actua como un monitor para el thread encargado de animar
 * el caso de uso. Dicho thread se dormira cuando se empiece a animar
 * un componente de la arquitectura y se despertara cuando finalize 
 * dicha animacion, para que pueda continuar con el siguiente componente.
 * 
 * @author Juan Feldman
 *
 */
public class GeonUseCaseMonitorManager {
	/**
	 * Duerme al thread GeonUseCaseThreadManager hasta que termine la animacion
	 * del componente de la arquitectura que se esta animando. 
	 */
    public synchronized void useCaseThreadManagerWait() {
        // La idea es la siguiente: cuando comienza la animacion de un componente
        // de la arquitectura (GeonAbstract.executeUseCase) se frena el thread GeonUseCaseThreadManager
    	// (metodo useCaseThreadManagerWait), y cuando termina la animacion se 
    	// continua con dicho thread (metodo useCaseThreadManagerWakeup).
    	try {
    		wait();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
	/**
	 * Despierta al thread GeonUseCaseThreadManager, encargado de la animacion del caso de uso.
	 */
    public synchronized void useCaseThreadManagerWakeup() {
    	notifyAll();
    }
}
