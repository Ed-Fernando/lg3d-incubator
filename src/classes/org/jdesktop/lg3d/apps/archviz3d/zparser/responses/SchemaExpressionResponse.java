package org.jdesktop.lg3d.apps.archviz3d.zparser.responses;


import java.util.Vector;

import java.lang.ArrayIndexOutOfBoundsException;

import org.jdesktop.lg3d.apps.archviz3d.zparser.responses.SchemaExpressionResponse;


public class SchemaExpressionResponse {
    Vector orlist = new Vector();

    public SchemaExpressionResponse() {
	orlist.add("");
    }

    public void addAndExpression(String sexp) {
	for (int i=0; i<orlist.size(); i++) {
	    try {
		String or = (String)orlist.elementAt(i);
		or += ("".equals(or))? sexp : ",\n"+sexp;
		orlist.remove(i);
		orlist.add(i,or);
	    } catch (ArrayIndexOutOfBoundsException e) {
		System.err.println("ZTreeParser:PredicateResponse:addAndPredicate(s):  ArrayIndexOutOfBoundsException!");
	    }
	}
    }

    public void addAndExpression(SchemaExpressionResponse exp) {
	try {
	    int number = exp.getNumberOfOrExpressions();
	    Object[] ae = orlist.toArray();
	    orlist.removeAllElements();
	    for (int i=0; i<ae.length; i++) {
		for (int j=0; j<number; j++) {
		    String e1 = (String)ae[i];
		    String e2 = exp.getOrExpressionAt(j);
		    String e = ("".equals(e1))? e2 : e1+",\n"+e2;
		    addOrExpression(e);
		}
	    }
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.err.println("ZTreeParser:PredicateResponse:addAndPredicate:  ArrayIndexOutOfBoundsException!");
	}				
    }

    public void addOrExpression(String sexp) {
	orlist.add(sexp);
    }

    public void addOrExpression(SchemaExpressionResponse exp) {
	try {
	    int number = exp.getNumberOfOrExpressions();
	    for (int i=0; i<number; i++) {
		String e1 = exp.getOrExpressionAt(i);
		addOrExpression(e1);
	    }
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.err.println("ZTreeParser:PredicateResponse:addOrPredicate:  ArrayIndexOutOfBoundsException!");
	}				
    }

    public int getNumberOfOrExpressions() {
	return orlist.size();
    }

    public String getOrExpressionAt(int pos) throws ArrayIndexOutOfBoundsException {
	return (String)orlist.elementAt(pos);
    }

    public String toString() {
	String s="";
	Object[] ap = orlist.toArray();
	for (int i=0; i<ap.length; i++) {
	    s += (String)ap[i]+ "\n";
	}
	return s;		
    }
}


