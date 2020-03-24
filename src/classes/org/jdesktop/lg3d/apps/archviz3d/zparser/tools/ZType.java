
/**
 * Title:        Requirements Visualization<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Alfredo Teyseyre<p>
 * Company:      GOV - ISISTAN<p>
 * @author Alfredo Teyseyre
 * @version 1.0
 */
package org.jdesktop.lg3d.apps.archviz3d.zparser.tools;

public class ZType {

    protected String type;
    protected String metaType;

    public ZType(String type) {
        this.type=type;
    }

     public ZType(String type,String metaType) {
        this.type=type;
        this.metaType=metaType;
    }

    public String type() {
        return type;
    }

    public String metaType() {
        return metaType;
    }
    public String toString() {
        return "Type name: " + type() + " metatype: " + metaType() ;
    }

}

