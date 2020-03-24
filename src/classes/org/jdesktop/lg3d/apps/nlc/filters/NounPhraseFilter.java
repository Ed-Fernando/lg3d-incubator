/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.filters;


import java.util.ArrayList;
import java.util.List;

import org.jdesktop.lg3d.apps.nlc.data.Application;
import org.jdesktop.lg3d.apps.nlc.knowledge.external.AppKnowledge;
import org.jdesktop.lg3d.apps.nlc.knowledge.external.DeterminerRegistry;
import org.jdesktop.lg3d.apps.nlc.language.data.NounPhrase;

public class NounPhraseFilter implements Filter {

	private NounPhrase np;

	private AppKnowledge knowledge;

	public NounPhraseFilter(NounPhrase np, AppKnowledge knowledge) {
		if (np == null) {
			throw new NullPointerException(
					"Argument to method NounPhraseFilter is null");
		}
		if (knowledge == null) {
			throw new NullPointerException(
					"Argument to method NounPhraseFilter is null");
		}
		this.np = np;
		this.knowledge = knowledge;
	}

	public List<Application> filter(List<Application> apps) {
		NounsFilter nounsFilter = new NounsFilter(knowledge, np.getAllNouns());
		List<Application> fapps = new ArrayList(apps);
		//filter by nouns
		fapps = nounsFilter.filter(fapps);
		//filter by determiner
		fapps = DeterminerRegistry.getInstance().getFilter(np.getDeterminer())
				.filter(fapps);
		//TODO filter by adjectives..
		return fapps;
	}

}
