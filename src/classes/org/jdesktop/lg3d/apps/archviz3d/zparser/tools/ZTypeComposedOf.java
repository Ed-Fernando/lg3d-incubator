
/**
 * Title:        Requirements Visualization<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Alfredo Teyseyre<p>
 * Company:      GOV - ISISTAN<p>
 * @author Alfredo Teyseyre
 * @version 1.0
 */
package org.jdesktop.lg3d.apps.archviz3d.zparser.tools;

import org.jdesktop.lg3d.apps.archviz3d.zparser.tools.ZType;

public class ZTypeComposedOf extends ZType {

    protected ZType elemType;

    public ZTypeComposedOf(String type, String metaType,ZType elemType) {
        super(type,metaType);
        this.elemType= elemType;
    }

     protected ZType elemType() {
        return elemType;
    }

    public String type() {
        return type + "[" + this.elemType().type + "]";
    }

}