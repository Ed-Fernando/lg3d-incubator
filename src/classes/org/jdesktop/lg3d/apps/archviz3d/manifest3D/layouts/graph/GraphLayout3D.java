package org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.graph;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Graph3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.LayoutStrategy3D;


public abstract class GraphLayout3D extends LayoutStrategy3D {
	protected PROComponent modelGraph;

	public void layout() {
		J3DCompositeComponent components = ((Graph3D)getClient()).getNodes3d();
		if (components.numComponents() > 0)
			traverse(components);
	}

	public PROComponent getModelGraph() {
		return modelGraph;
	}

	public void setClient(J3DCompositeComponent c) {
		super.setClient(c);
		modelGraph = ((Graph3D)getClient()).getModelGraph();
	}

	public abstract void traverse(J3DCompositeComponent graphComponents);
}
