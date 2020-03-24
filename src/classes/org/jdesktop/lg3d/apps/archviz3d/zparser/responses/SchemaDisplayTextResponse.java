package org.jdesktop.lg3d.apps.archviz3d.zparser.responses;

import org.jdesktop.lg3d.apps.archviz3d.zparser.responses.PredicateResponse;

public class SchemaDisplayTextResponse {
    public String decl=null;
    public PredicateResponse predicate=null;
    
    public String toString() {
        return super.toString() + "Dec: " + decl +  " Predicate: " + predicate; 
    }
}
