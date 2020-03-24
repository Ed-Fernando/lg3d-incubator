package org.jdesktop.lg3d.apps.archviz3d.geons.events;

import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonSplashScreen;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.wg.event.LgEventSource;



/**
 * Evento para la animacion del Splash.
 * 
 * @author Juan Feldman
 *
 */
public class GeonEventSplash implements ActionNoArg {
	/** Splash.*/
	private GeonSplashScreen splash = null;
	/** Siguiente paso a ejecutar.*/
	private int stepNumber = -1;

  /**
   * Inicializa las variables para que esta clase pueda delegar la accion al
   * receptor adecuado. 
   * 
   * @param splash Pantalla que contiene el splash a mostrar.
   * @param step Numero de paso de esta accion.
   */
  public GeonEventSplash(GeonSplashScreen splash, int step) {
    this.splash = splash;
    this.stepNumber = step;
  }

  /**
   * Metodo que es llamado cuando un LgEventListener necesita realizar una
   * accion en esta clase.
   * 
   * @param arg0 Objeto generador del evento.
   */
  public void performAction(LgEventSource arg0) {
	  splash.nextStep(stepNumber);
  }

}
