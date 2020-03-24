package org.jdesktop.lg3d.apps.archviz3d;

import java.util.Vector;

import org.jdesktop.lg3d.apps.archviz3d.PlEnginePool;
import org.jdesktop.lg3d.apps.archviz3d.SharedPlEngine;

import JavaLog.PlEngine;

/**
 *
 * @author Teyseyre
 */
public class PlEnginePool {
	private PlEngine sharedEngine;

	private Vector<String> consultedModules;
	/** Unica instancia de esta clase.*/
	private static PlEnginePool instance = new PlEnginePool();

	/**
	 * Devuelve la unica instancia de esta clase (Singleton).
	 * @return PlEnginePool Unica instancia de esta clase.
	 */
	public static PlEnginePool instance() {
		return instance;
	}

	/**
	 * Crea una nueva instancia de esta clase.
	 */
	private PlEnginePool() {
	}
	
	/**
	 * Inicializa las variables de esta clase.
	 */
	public void initialize() {
		consultedModules = new Vector<String>();
		sharedEngine = new PlEngine();
	}

	/**
	 * @return SharedPlEngine
	 */
	public synchronized SharedPlEngine getNotPooled() {
		return new SharedPlEngine(sharedEngine);
	}

	/**
	 * @param e SharedPlEngine
	 */
	public synchronized void release(SharedPlEngine e) {
		e.release();
	}

	/**
	 * @param name Nombre
	 * @param shared Boolean 
	 */
	public synchronized void consult(String name, boolean shared) {
		if (!consultedModules.contains(name)) {
			sharedEngine.consult(name);
			if (shared)
				consultedModules.add(name);
		}
	}

	/**
	 * @param name
	 */
	public synchronized void consult(String name) {
		consult(name, true);
	}

}
