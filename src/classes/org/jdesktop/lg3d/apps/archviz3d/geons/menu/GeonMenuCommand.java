package org.jdesktop.lg3d.apps.archviz3d.geons.menu;


import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameManager;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;

/**
 * Clase padre de todos los commands para el menu.
 * Las clases GeonMenuCommand fueron implementadas de acuerdo
 * al patron de Diseño "Command"
 * 
 * @author Juan Feldman
 */
public abstract class GeonMenuCommand {
	/** Invocador del commando.*/
	protected GeonMenu invoker = null;
	/** Receptor del commando.*/
	protected GeonFrameManager receiver = null;
	/** Parametros del commando.*/
	private Object[] params = null;

	/**
	 * Inicializa el comando.
	 * @param invoker Invocador del commando.
	 * @param receiver Receptor del commando.
	 */
	public GeonMenuCommand(GeonMenu invoker, GeonFrameManager receiver) {
		this.invoker = invoker;
		this.receiver = receiver;
		this.params = new Object[10]; 
	}

	/**
	 * Inicializa el comando.
	 * @param invoker Invocador del commando.
	 * @param receiver Receptor del commando.
	 * @param params lista de parametros.
	 */
	public GeonMenuCommand(GeonMenu invoker, GeonFrameManager receiver, Object[] params) {
		this(invoker, receiver);
		this.params = params; 
	}

	/** 
	 * Ejecuta el comando.
	 */
	public abstract void execute(); 

	/**
	 * Setea un parametro para el comando.
	 * @param param Parametro del comando.
	 * @param index Indice del parametro.
	 */
	public void setParam(Object param, int index) {
		params[index] = param;
	}
	
	/**
	 * Retorna un parametro del comando. 
	 * @param index Indice del parametro.
	 * @return Object Parametro del comando.
	 */
	public Object getParameter(int index) {
		return params[index];
	}
}
