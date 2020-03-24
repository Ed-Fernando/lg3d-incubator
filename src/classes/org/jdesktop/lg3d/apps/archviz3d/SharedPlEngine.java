package org.jdesktop.lg3d.apps.archviz3d;

import java.io.PrintWriter;
import java.util.Hashtable;

import JavaLog.PlEngine;

public class SharedPlEngine {
	private PlEngine engine;

	private Hashtable answer;

	private boolean free;

	/**
	 * Crea una instancia nueva de esta clase
	 */
	public SharedPlEngine(PlEngine engine) {
		free = true;
		this.engine = new PlEngine((PrintWriter) engine.getOutput(), engine
				.database(), engine.operators(), engine.builtIns());
	}

	/**
	 * Devuelve el engine
	 * 
	 * @return PlEngine
	 */
	public PlEngine getEngine() {
		return engine;
	}

	/** 
	 */
	public void acquire() {
		free = false;
	}

	/**
	 * 
	 */
	public void release() {
		free = true;
	}

	/**
	 * @return boolean
	 */
	public boolean isFree() {
		return free;
	}

	/**
	 * @param query
	 * @param args
	 * @return boolean
	 */
	public synchronized boolean answerQuery(String query, Object args[]) {
		if (engine.eCall(query, args)) {
			answer = engine.goal().state();

			return true;
		}
		return false;
	}

	/**
	 * @param query
	 * @return boolean
	 */
	public synchronized boolean answerQuery(String query) {
		if (engine.eCall(query)) {
			answer = engine.goal().state();
			return true;
		}
		return false;
	}

	/**
	 * @return Hashtable
	 */
	public synchronized Hashtable answer() {
		return answer;
	}

	/**
	 * 
	 */
	public void finalize() {
		release();
	}

}
