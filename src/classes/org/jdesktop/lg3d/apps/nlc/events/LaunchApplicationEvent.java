/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.events;

import org.jdesktop.lg3d.wg.event.LgEvent;

public class LaunchApplicationEvent extends LgEvent {
	
	private String command;
	
	public LaunchApplicationEvent(String command) {
		if (command == null) {
			throw new NullPointerException(
					"Argument to method LaunchApplicationEvent is null");
		}
		this.command = command;
	}
	
	public String getCommand() {
		return command;
	}
}
