/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.language.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NounPhrase {

	private List<String> adjectives;

	private String determiner;

	private boolean isPlural;

	private List<String> noun;

	public NounPhrase() {
		noun = new ArrayList<String>();
		adjectives = new ArrayList<String>();
		isPlural = false;
	}

	public String getDeterminer() {
		return determiner;
	}

	public void setDeterminer(String determiner) {
		if (determiner == null) {
			throw new NullPointerException(
					"Argument to method setDeterminer is null");
		}
		this.determiner = determiner;
	}

	public boolean isPlural() {
		return isPlural;
	}

	public void setPlural(boolean isPlural) {
		this.isPlural = isPlural;
	}

	public void addNoun(String noun) {
		if (noun == null) {
			throw new NullPointerException("Argument to method addNoun is null");
		}
		if (noun.trim().length() == 0) {
			throw new IllegalArgumentException("Noun should not be empty");
		}
		this.noun.add(noun);
	}

	public void addAdjective(String adjective){
		if (adjective == null) {
			throw new NullPointerException(
					"Argument to method addAdjective is null");
		}
		if (adjective.trim().length() == 0){
			throw new IllegalArgumentException("Adjective should not be empty");
		}
		this.adjectives.add(adjective);
	}
	
	public int numNouns() {
		return noun.size();
	}

	public int numAdjectives() {
		return adjectives.size();
	}

	public List<String> getAdjectives() {
		return Collections.unmodifiableList(adjectives);
	}

	public List<String> getAllNouns() {
		return Collections.unmodifiableList(noun);
	}

	public String getAdjective(int i) {
		return adjectives.get(i);
	}

	public String getNoun(int i) {
		return noun.get(i);
	}
}
