/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.language.utils;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Vocabulary helps us resolve words. Currently this and CommandRegistry
 * are hardcoded. This is bad. This will move on to a common config file
 * from where both of these can read the actions.  
 * 
 * @author harsh
 */
public class Vocabulary {
	
	private static Vocabulary vocabulary;
	
	private static final String[] allowedVerbs = {
		"open",
		"close",
		"hide",
		"show",
		"focus",
		"launch",
		"kill",
		"terminate",
		"rotate",
		"turn",
		"park"
	};
	
	private Set<String> allowedVerbsSet;
	
	private Vocabulary(){
		allowedVerbsSet = new TreeSet<String>(Arrays.asList(allowedVerbs));
	}
	
	public static Vocabulary getInstance(){
		if (vocabulary == null){
			vocabulary = new Vocabulary();
		}
		return vocabulary;
	}
	
	public boolean isAllowedVerbLabel(String label){
		return label.equals("VB")
			|| label.equals("RP");
	}

	public boolean isAllowedVerb(String verb) {
		return allowedVerbsSet.contains(verb);
	}
	
	public boolean isPossibleNounLabel(String label){
		return label.equals("NN")
			|| label.equals("NNS")
			|| label.equals("NNP");
	}

	public boolean isPossibleDeterminerLabel(String label) {
		return label.equals("DT");
	}

	public boolean isPossibleAdjectiveLabel(String label) {
		return label.equals("JJ");
	}
	
	
}
