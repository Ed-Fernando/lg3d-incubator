package org.jdesktop.lg3d.apps.archviz3d.zparser.tools;

import java.util.*;

import org.jdesktop.lg3d.apps.archviz3d.zparser.tools.Prototype;

public class PrototypeFactory {
     protected Hashtable prototypes;

     /** 
      * Constructor
      */
     public PrototypeFactory() {
	prototypes = new Hashtable();
     }
        
    public Hashtable getPrototypes() {
	return prototypes;
    }
    
    public void put(Prototype p) {
	prototypes.put( (String)p.get("__name_prot"),p);
	//	System.out.println("Factory  : " + prototypes);
    }

    public Prototype copy(String s) {
	//	System.out.println("----------------------------------------");
	//	System.out.println("ProtName: " + s);
	//	System.out.println("----------------------------------------");
	
	
	return (Prototype) ((Prototype)prototypes.get(s)).clone();
    }

    public void dump() {
	for (Enumeration e=prototypes.elements(); e.hasMoreElements() ;) 
	    ((Prototype) e.nextElement()).dump();
    }
}

	
    




