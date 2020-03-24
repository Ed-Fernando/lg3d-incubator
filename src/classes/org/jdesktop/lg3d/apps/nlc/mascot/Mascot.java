/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.mascot;


import java.io.IOException;

import org.jdesktop.lg3d.apps.nlc.Nlc;

public abstract class Mascot {

	private Nlc nlc;
	
	public Mascot(Nlc nlc){
		if (nlc == null) {
			throw new NullPointerException("Argument to method Mascot is null");
		}
		this.nlc = nlc;
	}
	
	public abstract void start();
	
	public abstract boolean isTakingInput();
	
	public void takeAction(String command) throws IOException{
		nlc.takeAction(command);
	}
	
	public abstract void stop();
}
