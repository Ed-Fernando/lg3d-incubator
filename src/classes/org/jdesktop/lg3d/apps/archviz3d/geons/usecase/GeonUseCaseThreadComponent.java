package org.jdesktop.lg3d.apps.archviz3d.geons.usecase;

import org.jdesktop.lg3d.apps.archviz3d.geons.usecase.GeonUseCaseInterface;
import org.jdesktop.lg3d.apps.archviz3d.geons.usecase.GeonUseCaseManager;

/**
 * Esta clase se encarga de ejecutar la animacion del caso de 
 * uso en el componente de la arquitectura. 
 * 
 * @author Juan Feldman
 * 
 */
public class GeonUseCaseThreadComponent implements Runnable {
	/** Componente de la arquitectura al cual se le aplicara la animacion.*/
	private GeonUseCaseInterface geonToAnimate;
	/** Encargado de ejecutar el caso de uso.*/
	private GeonUseCaseManager geonUseCaseManager;

	/**
	 * Se intancia la clase.
	 * @param geonToAnimate Componente de la arquitectura al cual 
	 * se le aplicara la animacion. 
     * @param geonUseCaseManager Encargado de ejecutar el caso de uso. 
	 */
	public GeonUseCaseThreadComponent (GeonUseCaseInterface geonToAnimate, GeonUseCaseManager geonUseCaseManager) {
		this.geonToAnimate = geonToAnimate; 
		this.geonUseCaseManager = geonUseCaseManager; 
	}

	/**
	 * Se ejecuta el caso de uso en el componente de la arquitectura.
	 */
	public void run() {
		try {
			Object valueToChange = geonToAnimate.getValueToChange();
			for (int i = 0; i < GeonUseCaseManager.NUMBER_LOOPS && geonUseCaseManager.isRunning(); i++) {
				// Verifico si se pauso la animacion del caso de uso
				geonUseCaseManager.haveToWait();
				// Obtengo el valor a modificar
				valueToChange = geonToAnimate.loopAction(valueToChange, i);
				// Aplico la modificacion
				geonToAnimate.applyChange(valueToChange);
				// Duermo el thread
				Thread.sleep(geonUseCaseManager.getAnimationSpeed());
			}
		}catch(Exception e){e.printStackTrace();}

    	// Continua el thread GeonUseCaseThreadManager
		geonUseCaseManager.useCaseThreadManagerWakeup();
	}

}
