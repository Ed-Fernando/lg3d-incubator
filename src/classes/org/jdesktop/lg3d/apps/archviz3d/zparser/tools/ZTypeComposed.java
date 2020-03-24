
/**
 * Title:        Requirements Visualization<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Alfredo Teyseyre<p>
 * Company:      GOV - ISISTAN<p>
 * @author Alfredo Teyseyre
 * @version 1.0
 */
package org.jdesktop.lg3d.apps.archviz3d.zparser.tools;

import java.util.*;

import org.jdesktop.lg3d.apps.archviz3d.zparser.tools.ZType;

public class ZTypeComposed extends ZType {

    Set elements;

    public ZTypeComposed(String type,String metaType) {
        super(type,metaType);
        elements=new HashSet();
    }

    public boolean add(String elem) {
        return elements.add(elem);
    }

    public boolean contains(String elem) {
        return elements.contains(elem);
    }



}




