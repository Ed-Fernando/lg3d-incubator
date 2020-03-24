/**
 * This code was written during my participation in Google's Summer
 * of Code 2005. Thanks to Google for bringing out such a program
 * and there commitment to open-source.
 */
package org.jdesktop.lg3d.apps.nlc.language.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.stanford.nlp.trees.Tree;

/**
 * A light-weight implementation of a tree. We cant 
 * directly use Stanford Trees because they dont have
 * parent stored. Parent is needed for several operations.
 * 
 * @author harsh
 *
 */
public class PTree {
	
	private String label;
	
	private List<PTree> children;
	
	private PTree parent;
	
	private Map<String, String> attributes;

	/**
	 * Construct a PTree from stanford tree. A recursive
	 * constructor
	 * 
	 * @param tree
	 */
	public PTree(Tree tree){
		this.label = tree.label().value();
		this.children = new ArrayList<PTree>();
		Tree[] children = tree.children();
		for (int i=0;i<children.length;i++){
			PTree child = new PTree(children[i]);
			child.setParent(this);
			this.children.add(child);
		}
		this.attributes = new HashMap<String, String>();
	}
	
	
	public List<PTree> getChildren() {
		return children;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public PTree getParent() {
		return parent;
	}

	public void setParent(PTree parent) {
		this.parent = parent;
	}
	
	public boolean isLeaf(){
		return children.size() == 0;
	}
	
	public void setAttribute(String key, String value){
		if (key == null ) {
			throw new NullPointerException("Argument to method setAttribute is null");
		}
		attributes.put(key, value);
	}
	
	public void clearAllAttributes(){
		attributes.clear();
	}

	@Override
	public String toString() {
		return label;
	}
}
