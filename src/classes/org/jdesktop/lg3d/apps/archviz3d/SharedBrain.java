package org.jdesktop.lg3d.apps.archviz3d;

import java.io.*;
import java.util.Observable;

import org.jdesktop.lg3d.apps.archviz3d.Command;
import org.jdesktop.lg3d.apps.archviz3d.PlEnginePool;

/**
 * @author Alfredo Teyseyre
 */
public abstract class SharedBrain extends Observable {
	private String fileName, nameOnly;

	/**
	 * @param command
	 * @return boolean
	 */
	public abstract boolean execute(String command);

	/**
	 * @param predicate
	 * @param parameters
	 * @return boolean
	 */
	public synchronized boolean execute(String predicate, String parameters) {
		return this.execute(predicate + "(" + parameters + ")");
	}

	/**
	 * @param command
	 */
	public void command(String command) {
		new Command(this, command).run();
	}

	/**
	 * @return String
	 */
	public String fileName() {
		return fileName;
	}

	/**
	 * @return String
	 */
	public String fileNameBase() {
		return new File(fileName).getParent();
	}

	/**
	 * @return String
	 */
	public String fileNameOnly() {
		return nameOnly;
	}

	/**
	 * @return String
	 */
	public String module() { //por compatibilidad
		return nameOnly;
	}

	/**
	 * @return String
	 */
	public String moduleQ() { //por compatibilidad
		return "'" + nameOnly +"'";
	}

	/**
	 * @param fileName
	 */
	public void script(String fileName) {
		try {
			this.fileName = fileName.replace("\\", "/");
			String s = new File(fileName).getName();
			nameOnly = s.substring(0, s.indexOf('.'));
			PlEnginePool.instance().consult(this.fileName);
		} catch (Exception e) {e.printStackTrace();}
	}

}