/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.language.stanford;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdesktop.lg3d.apps.nlc.language.CommandAnaylizer;
import org.jdesktop.lg3d.apps.nlc.language.data.Command;
import org.jdesktop.lg3d.apps.nlc.language.data.NounPhrase;
import org.jdesktop.lg3d.apps.nlc.language.data.PTree;
import org.jdesktop.lg3d.apps.nlc.language.utils.StanfordFactory;
import org.jdesktop.lg3d.apps.nlc.language.utils.StanfordUtils;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class StanfordAnaylyzer extends CommandAnaylizer {

	private Command action;

	private List<NounPhrase> nounPhrases;

	public StanfordAnaylyzer(String command) throws IOException {
		super(command);
	}

	@Override
	protected void analyze() throws IOException {
		LexicalizedParser parser = StanfordFactory.getInstance().getParser();
		parser.parse(StanfordUtils.tokenize(getCommandString()));
		PTree tree = new PTree(parser.getBestParse());
		String verb = StanfordUtils.getImperativeVerb(tree);
		if (verb != null){
			action = new Command(verb);
		} else {
			action = Command.DEFAULT;
		}
		nounPhrases = new ArrayList<NounPhrase>();
		List<PTree> nps = StanfordUtils.getAllBaseNPs(tree);
		for (PTree np:nps){
			NounPhrase phrase = StanfordUtils.transform(np);
			if (phrase != null)
				nounPhrases.add(phrase);
		}
	}

	@Override
	public Command getCommand() {
		return action;
	}

	@Override
	public int getNumNounPhrases() {
		return nounPhrases.size();
	}

	@Override
	public NounPhrase getNounPhrase(int i) {
		return nounPhrases.get(i);
	}

	@Override
	public List<NounPhrase> getAllNounPhrases() {
		return Collections.unmodifiableList(nounPhrases);
	}

}
