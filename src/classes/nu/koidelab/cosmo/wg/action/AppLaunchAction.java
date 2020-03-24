/**
 * Project Looking Glass
 *
 * $RCSfile$
 *
 * Copyright (c) 2004, Sun Microsystems, Inc., All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision$
 * $Date$
 * $State$
 */
package nu.koidelab.cosmo.wg.action;

import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * An ActionNoArg class that launches an application based on the specified
 * command string.
 */
public class AppLaunchAction implements ActionNoArg {
	// protected static final Logger logger = Logger.getLogger("lg.wg");
	private String command;

	public AppLaunchAction(String command) {
		this.command = command;
	}

	public void performAction(LgEventSource source) {
		System.err.println(command);
		//
		// FIXME and WARNING
		// -- this actually executes the java app in the same JVM!
		//
		if (command.startsWith("java ")) {
			// logger.warning("Executing java app in the same JVM: " + command);

			final String[] cmd = command.split("\\s");
			final String className = cmd[1];

			Runnable r = new Runnable() {
				public void run() {
					try {
						Class cls = Class.forName(className);
						Class[] argClses = { String[].class };
						Method mainMethod = cls.getMethod("main", argClses);

						Object argObjects ;
						if (cmd.length > 2) {
							String[] args = new String[cmd.length - 2];
							for (int i = 0; i < args.length; i++) {
								args[i] = cmd[i + 2];
							}
							argObjects = args;
						}else {
							argObjects=new String[0];
						}
						mainMethod.invoke(null, argObjects);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			};
			(new Thread(r)).start();

			return;
		}

		String displayName = System.getProperty("lg.lgserverdisplay");
		if (displayName == null) {
			displayName = ":0";
		}

		try {
			// logger.finer("Executing command: " + command);
			ArrayList<String> commandArgs = new ArrayList<String>();

			int start = 0;
			int end = 0;
			while (end < command.length()) {
				end = command.indexOf(' ', start);
				if (end == -1)
					end = command.length();
				commandArgs.add(command.substring(start, end));
				start = end + 1;
			}

			ProcessBuilder pb = new ProcessBuilder(commandArgs);
			Map<String, String> env = pb.environment();
			env.put("DISPLAY", displayName);
			pb.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
