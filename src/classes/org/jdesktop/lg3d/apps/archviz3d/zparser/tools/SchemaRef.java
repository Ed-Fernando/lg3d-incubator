package org.jdesktop.lg3d.apps.archviz3d.zparser.tools;

public class SchemaRef {
    public String schema;
    public String mode;


    public SchemaRef(String s, String m) {
	this.schema=s;
	this.mode=m;
    }

    public String toString() {
    return "SchemaRef ////Schema: " + schema + "   mode: " + mode;
    }
}

