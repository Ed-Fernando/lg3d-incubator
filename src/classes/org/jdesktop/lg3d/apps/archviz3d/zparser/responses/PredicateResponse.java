package org.jdesktop.lg3d.apps.archviz3d.zparser.responses;


import java.util.Vector;

import java.lang.ArrayIndexOutOfBoundsException;

import org.jdesktop.lg3d.apps.archviz3d.zparser.responses.PredicateResponse;


public class PredicateResponse {
    Vector orlist = new Vector();

    public PredicateResponse() {
	orlist.add("");
    }

    public void addAndPredicate(String spred) {
	//System.out.println("ZTreeParser:PredicateResponse:addAndPredicate:\n  adding predicate="+spred);
	for (int i=0; i<orlist.size(); i++) {
	    try {
		String or = (String)orlist.elementAt(i);
		or += ("".equals(or))? spred : ",\n"+spred;
		orlist.remove(i);
		orlist.add(i,or);
		//System.out.println("        se actualizo a pred(i="+i+")="+(String)orlist.elementAt(i));
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.err.println("ZTreeParser:PredicateResponse:addAndPredicate(s):  ArrayIndexOutOfBoundsException!");
	    }
	}
    }

    public void addAndPredicate(PredicateResponse pred) {
	try {
	    int number = pred.getNumberOfOrPredicates();
	    Object[] ap = orlist.toArray();
	    orlist.removeAllElements();
	    for (int i=0; i<ap.length; i++) {
		for (int j=0; j<number; j++) {
		    String p1 = (String)ap[i];
		    String p2 = pred.getOrPredicateAt(j);
		    String p = ("".equals(p1))? p2 : p1+",\n"+p2;
		    addOrPredicate(p);
		}
	    }
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.err.println("ZTreeParser:PredicateResponse:addAndPredicate:  ArrayIndexOutOfBoundsException!");
	}				
    }

    public void addOrPredicate(String spred) {
	//System.out.println("ZTreeParser:PredicateResponse:addOrPredicate:\n  adding predicate="+spred);
	orlist.add(spred);
    }

    public void addOrPredicate(PredicateResponse pred) {
	//System.out.println("addOrPredicate(p): dump this antes\n "+this);
	//System.out.println("addOrPredicate(p): dump pred antes\n "+pred);
	try {
	    int number = pred.getNumberOfOrPredicates();
	    for (int i=0; i<number; i++) {
		String p1 = pred.getOrPredicateAt(i);
		addOrPredicate(p1);
	    }
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.err.println("ZTreeParser:PredicateResponse:addOrPredicate:  ArrayIndexOutOfBoundsException!");
	}				
	//System.out.println("addOrPredicate(p): dump this despues\n "+this);
    }

    public int getNumberOfOrPredicates() {
	return orlist.size();
    }

    public String getOrPredicateAt(int pos) throws ArrayIndexOutOfBoundsException {
	return (String)orlist.elementAt(pos);
    }

    public String toString() {
	String s="";
	Object[] ap = orlist.toArray();
	for (int i=0; i<ap.length; i++) {
	    s +=  (String)ap[i]+ "\n";
	}
	return s;		
    }
}
