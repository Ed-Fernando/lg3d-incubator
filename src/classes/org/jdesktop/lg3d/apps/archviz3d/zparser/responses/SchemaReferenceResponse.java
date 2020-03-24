package org.jdesktop.lg3d.apps.archviz3d.zparser.responses;

public class SchemaReferenceResponse {
    protected String name=null;
    protected boolean delta=false;
    protected boolean decor=false;
    protected String getContains;

    public SchemaReferenceResponse() {
	getContains= new String();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name=name;
    }

    public boolean isDelta() {
	return delta;
    }

    public void setDelta(boolean delta) {
	this.delta=delta;
    }

    public boolean isDecor() {
	return decor;
    }

    public void setDecor(boolean decor) {
	this.decor=decor;
    }

    public String getContains() {
	return getContains;
    }

    public void setContains(String getContains) {
	this.getContains=getContains;
    }
    
}
