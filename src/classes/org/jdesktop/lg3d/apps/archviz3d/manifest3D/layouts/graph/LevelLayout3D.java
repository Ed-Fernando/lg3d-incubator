package org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.graph;

import java.util.Iterator;

import javax.vecmath.Point3i;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;


import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROLink;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Graph3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Node3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.graph.SimpleDimension3D;


/**
 * Esta clase la utilizan los files .bsh.
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public class LevelLayout3D extends SimpleDimension3D {

	public LevelLayout3D(Point3i dimension) {
        super(dimension);
	}

    public void traverse(J3DCompositeComponent graphComponents) {
        calculatePoints(graphComponents.numComponents());
        dfs((Node3D)graphComponents.getComponent(0), 0);
    }

	public void dfs(Node3D node, int indice) {
		if (indice != 0) {
          Vector3f v = node.getTranslation();
		  v.add((Tuple3f)this.points.elementAt(indice));
		  node.setTranslation(v);
        }

		PROComponent  nodeM = (PROComponent)node.getSubject();
		PROComponent nodeAux;
		Iterator iterator = nodeM.getLinksIterator();
		while (iterator.hasNext()) {
			nodeAux = (PROComponent)((PROLink)iterator.next()).getTarget();
			Node3D node3D = ((Graph3D)getClient()).getNode3D(nodeAux);
			if (node3D != null && this.visitated.contains(node3D) == false) {
				this.visitated.add(node3D);
                indice++;
				dfs(node3D, indice);
                indice--;
			}
		}
	}
}

