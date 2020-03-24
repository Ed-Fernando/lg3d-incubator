/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.language;


import java.io.IOException;
import java.util.List;

import org.jdesktop.lg3d.apps.nlc.language.data.Command;
import org.jdesktop.lg3d.apps.nlc.language.data.NounPhrase;

/**
 * Abstract CommandAnalyzer. A command analyzer takes the 
 * command in natural language. For each command it gives 
 * the Command and all Noun Phrases. In future we will also 
 * support connectors but they are not required now. Currently
 * 
 * @author harsh
 *
 */
public abstract class CommandAnaylizer {
	
	/**
	 * The command entered by user
	 */
	private String command;
	
	/**
	 * There is one command anaylizer per command
	 * 
	 * @param command
	 * @throws IOException 
	 */
	public CommandAnaylizer(String command) throws IOException{
		if (command == null) {
			throw new NullPointerException(
					"Argument to method CommandAnaylizer is null");
		}
		this.command = command;
		analyze();
	}
	
	/**
	 * Called from constructor.  
	 * @throws IOException 
	 */
	protected abstract void analyze() throws IOException;
	
	public abstract Command getCommand();

	public abstract int getNumNounPhrases();
	
	public abstract NounPhrase getNounPhrase(int i);
	
	public abstract List<NounPhrase> getAllNounPhrases();
	
	public String getCommandString(){
		return command;
	}
}
