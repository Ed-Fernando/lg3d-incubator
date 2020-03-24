package org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.graph;


import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;


import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROLink;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Graph3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Node3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.graph.MultipleDimension3D;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Esta clase la utilizan los files .bsh.
 * Se encarga de ubicar los componentes de la arquitectura en 
 * la posicion que corresponda.
 * 
 */
public class DepthFirstLayout3D extends MultipleDimension3D {
	/** Logger de mensajes.*/
	private static Logger logger = Logger.getLogger("lg.ArchViz3D");	

	/**
	 * Instancia la clase DepthFirstLayout3D 
	 * @param capas Cantidad de capas. 
	 */
	public DepthFirstLayout3D(int capas) {
		super(capas);
	}

	/**
	 * Recorre la arquitectura ubicando los componentes en la ubicacion
	 * que corresponda.
	 * @param graphComponents Grafo con los componentes de la arquitectura.
	 */
	public void traverse(J3DCompositeComponent graphComponents) {
		points.add(new Vector3d(0.0f, 0.0f, 0.0f));
		for (int i = 0; i < capas; i++) 
			aggregatePoints();

		for (int i = 0; i < graphComponents.numComponents(); i++) {
			Node3D node = (Node3D) graphComponents.getComponent(i);
			if (!visitated.contains(node))
				dfs(node, null);
		}
	}

	/**
	 * Realiza el Depth First Strategy, recorriendo la arquitectura y 
	 * ubicando los componentes en la ubicacion que corresponda.
	 * @param node Node a ubicar en la posicion que corresponda.  
	 * @param parent Padre del node a ubicar. 
	 */
	public void dfs(Node3D node, Node3D parent) {
		Vector3d pos;
		if (parent != null) {
			Vector3f v = parent.getTranslation();
			pos = findNearestPosition(new Vector3d(v.x, v.y, v.z), points);
			points.removeElement(pos);
		} else {
			pos = (Vector3d) points.elementAt(0);
			points.removeElement(pos);
		}
		if (pos == null) {
			logger.log(Level.SEVERE, "No hay suficientes posiciones generadas para ubicar los nodos del grafo (Sugerencia: aumentar el numero de capas al constructor del layout");
			return;
		}
		node.setTranslation(new Vector3f((float)pos.x, (float)pos.y, (float)pos.z));

		PROComponent nodeM = (PROComponent) node.getSubject();
		PROComponent nodeAux;

		Iterator e = nodeM.getLinksIterator();
		while (e.hasNext()) {
			nodeAux = (PROComponent) ((PROLink) e.next()).getTarget();
			Node3D node3D = ((Graph3D) getClient()).getNode3D(nodeAux);
			if (node3D != null && visitated.contains(node3D) == false) {
				visitated.add(node3D);
				dfs(node3D, node);
			}
		}
	}
}
