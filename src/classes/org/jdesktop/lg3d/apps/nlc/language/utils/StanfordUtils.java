/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.language.utils;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jdesktop.lg3d.apps.nlc.language.data.NounPhrase;
import org.jdesktop.lg3d.apps.nlc.language.data.PTree;

import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.trees.Sentence;

public class StanfordUtils {
	
	public static Sentence tokenize(String command){
		PTBTokenizer tokenizer = new PTBTokenizer(new StringReader(command));
		return new Sentence(tokenizer.tokenize());
	}
	
	private static boolean isBaseNP(PTree np){
		if (np.getLabel().equals("NP")){
			LinkedList<PTree> queue = new LinkedList<PTree>();
			queue.addAll(np.getChildren());
			while (queue.size() != 0){
				PTree child = queue.removeFirst();
				if (child.getLabel().equals("NP")){
					return false;
				}
				queue.addAll(child.getChildren());
			}
			return true;
		}
		return false;
	}
	
	public static List<PTree> getAllBaseNPs(PTree root){
		List<PTree> baseNPs = new ArrayList<PTree>();
		LinkedList<PTree> queue = new LinkedList<PTree>();
		queue.addLast(root);
		while (queue.size() != 0){
			PTree child = queue.removeFirst();
			if (isBaseNP(child)){
				baseNPs.add(child);
			} else {
				queue.addAll(child.getChildren());
			}
		}
		return baseNPs;
	}
	
	public static List<PTree> getLeafs(PTree root){
		List<PTree> leafs = new ArrayList<PTree>();
		LinkedList<PTree> queue = new LinkedList<PTree>();
		queue.addLast(root);
		while (queue.size() != 0){
			PTree child = queue.removeFirst();
			if (child.isLeaf()){
				leafs.add(child);
			} 
			queue.addAll(child.getChildren());
		}
		return leafs;
	}
	
	/**
	 * This has to be the base NP. 
	 * 
	 * @param np
	 * @return
	 */
	public static NounPhrase transform(PTree np) {
		if (!isBaseNP(np))
			throw new IllegalArgumentException("Please enter only base nps");
		NounPhrase phrase = new NounPhrase();
		Vocabulary vocab = Vocabulary.getInstance();
		List<PTree> leafs = StanfordUtils.getLeafs(np);
		for (PTree child:leafs){
			String plabel = child.getParent().getLabel();
			if (vocab.isPossibleNounLabel(plabel)){
				phrase.addNoun(child.getLabel());
			} 
			//we avoid else if because 
			//1) It wont matter
			//2) We might have qualifiers for both
			if (vocab.isPossibleDeterminerLabel(plabel)){
				phrase.setDeterminer(child.getLabel());
			}
			if (vocab.isPossibleAdjectiveLabel(plabel)){
				phrase.addAdjective(child.getLabel());
			}
		}
		//It might be possible for phrase to have 0 nouns
		if (phrase.numNouns() == 0){
			return null;
		}
		return phrase;
	}
	
	/**
	 * We traverse the tree and extract the imperative verb out of it.
	 * For the current implemenation, we assume that imperative verb is
	 * the first verb encountered.
	 * 
	 * @param tree
	 * @return
	 */
	public static String getImperativeVerb(PTree root){
		List<String> allVerbs = getAllVerbs(root);
		Vocabulary vocab = Vocabulary.getInstance();
		//first word defined in vocabulary is the action verb
		for (int i=0;i<allVerbs.size();i++){
			if (vocab.isAllowedVerb(allVerbs.get(i))){
				return allVerbs.get(i);
			}
		}
		return null;
	}
	
	/**
	 * This is a Breadth-First traversal of the parse tree
	 * to get all the verbs.
	 * 
	 * @param root
	 * @return
	 */
	public static List<String> getAllVerbs(PTree root){
		LinkedList<PTree> queue = new LinkedList<PTree>();
		List<String> verbs = new ArrayList<String>();
		Vocabulary vocab = Vocabulary.getInstance();
		queue.addLast(root);
		while (queue.size() != 0){
			PTree cur = queue.removeFirst();
			if (cur.isLeaf() && vocab.isAllowedVerbLabel(cur.getParent().getLabel())){
				verbs.add(cur.getLabel());
			} 
			for (PTree child:cur.getChildren()){
				queue.addLast(child);
			}
		}
		return verbs;
	}
}
