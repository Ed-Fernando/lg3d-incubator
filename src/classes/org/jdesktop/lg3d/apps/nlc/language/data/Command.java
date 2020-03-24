/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.language.data;

public class Command {

	public static final Command DEFAULT = new Command("DEFAULT");
	
	private String verb;

	public Command(String verb) {
		setVerb(verb);
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		if (verb == null) {
			throw new NullPointerException("Argument to method setVerb is null");
		}
		this.verb = verb;
	}

}
