/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.filters;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jdesktop.lg3d.apps.nlc.data.Application;
import org.jdesktop.lg3d.apps.nlc.knowledge.external.AppKnowledge;

public class NounsFilter implements Filter {

	private List<String> nouns;

	private AppKnowledge knowledge;

	public NounsFilter(AppKnowledge knowledge, List<String> nouns) {
		if (knowledge == null ) {
			throw new NullPointerException("Argument to method NounsFilter is null");
		}
		if (nouns == null ) {
			throw new NullPointerException("Argument to method NounsFilter is null");
		}
		this.knowledge = knowledge;
		this.nouns = nouns;
	}




	/**
	 * TODO We should ideally use a Morphological processor to match words.
	 */
	public List<Application> filter(List<Application> apps) {
		List<Application> filtered = new ArrayList<Application>();
		for (Application app : apps) {
			List<String> syns = knowledge.getSynonyms(app.getAppConfig());
			// syns should have the base form..
			boolean ok = false;
			for (String noun : nouns) {
				for (String syn : syns) {
					if (noun.contains(syn)){
						ok = true;
						break;
					}
				}
				if (ok)
					break;
			}
			if (ok){
				filtered.add(app);
			}
		}
		return filtered;
	}

}
