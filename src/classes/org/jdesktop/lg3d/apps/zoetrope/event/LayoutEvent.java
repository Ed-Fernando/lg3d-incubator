/*
 * LG3D Incubator Project - Zoetrope
 *
 * $RCSfile:$
 *
 * Copyright (c) 2004, Zoetrope Project Team, All Rights Reserved
 *
 * Redistributions in source code form must reproduce the above
 * copyright and this condition.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 *
 * $Revision:$
 * $Date:$
 * Author: yuichi sakuraba
 */

package org.jdesktop.lg3d.apps.zoetrope.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;

import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;

public class LayoutEvent extends EventObject{
    private static final long serialVersionUID = 1L;

    private Container3D container;
    private List<Component3D> components;

    public LayoutEvent(LayoutManager3D layoutManager,
		       Container3D container,
		       List<Component3D> components) {
        super(layoutManager);

	this.container = container;
	this.components = Collections.unmodifiableList(new ArrayList<Component3D>(components));
    }

    public Container3D getContainer() {
	return container;
    }

    public List getLayoutedComponents() {
	return components;
    }

    public Component3D getLayoutedComponent(int index) {
	return components.get(index);
    }

    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("LayoutEvent [Source:");
	builder.append(getSource());
	builder.append(" Container; ");
	builder.append(container);
	builder.append(" Components: ");
	builder.append(components);
	
	return builder.toString();
    }
}
