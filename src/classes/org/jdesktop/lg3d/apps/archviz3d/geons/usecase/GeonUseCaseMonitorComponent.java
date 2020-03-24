package org.jdesktop.lg3d.apps.archviz3d.geons.usecase;

/**
 * Esta clase actua como un monitor para los threads encargados
 * de animar un componente de la arquitectura durante la ejecucion
 * de un caso de uso. Dichos threads deben pausarse cuando el usuario
 * clickee el boton pause.
 * 
 * @author Juan Feldman
 *
 */
public class GeonUseCaseMonitorComponent {
    /** Indica si se debe pausar la animacion.*/
	private boolean pause;
	
	/**
	 * Setea el valor de la pausa.
	 * @param pause Valor de la pausa.
	 */
	public synchronized void setPause(boolean pause) {
		this.pause = pause;
		if (!pause)
			notifyAll();
	}
	
	/**
	 * Retorna el valor de la pausa.
	 * @return boolean Valor de la pausa.
	 */
	public synchronized boolean getPause() {
		return pause;
	}
	
	/**
	 *  Si el valor de pause es true, duerme a los threads
	 *  hasta que el valor de pause sea false. 
	 */
	public synchronized void haveToWait() {
		try {
			while (pause)
				wait();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
