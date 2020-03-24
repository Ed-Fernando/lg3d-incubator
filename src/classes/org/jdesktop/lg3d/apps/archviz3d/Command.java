package org.jdesktop.lg3d.apps.archviz3d;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jdesktop.lg3d.apps.archviz3d.SharedBrain;

/**
 * @author Alfredo Teyseyre
 */
public class Command implements Runnable, ActionListener {
	protected String command;

	protected SharedBrain component;

	/**
	 * @param component
	 * @param command
	 */
	public Command(SharedBrain component, String command) {
		this.command = command;
		this.component = component;
	}

	/**
	 * Crea un nuevo thread y lo ejecuta.
	 */
	public void runThread() {
		new Thread(this).start();
	}

	/** 
	 */
	public void run() {
		component.execute(command);
	}

	/**
	 */
	public void actionPerformed(ActionEvent e) {
		this.run();
	}

}