/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.data;

public class ApplicationConfig {
	
	public static final ApplicationConfig DEFAULT = new ApplicationConfig("DEFAULT", "DEFAULT");
	
	private String name;
	
	private String command;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ApplicationConfig(String command, String name) {
		this();
		this.command = command;
		this.name = name;
	}
	
	public ApplicationConfig(){
		
	}
	
	@Override
	public boolean equals(Object obj) {
		ApplicationConfig other = (ApplicationConfig) obj;
		return command.equals(other.command);
	}
	
	@Override
	public int hashCode() {
		return command.hashCode();
	}
}
