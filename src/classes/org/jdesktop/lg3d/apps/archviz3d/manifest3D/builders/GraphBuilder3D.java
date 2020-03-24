package org.jdesktop.lg3d.apps.archviz3d.manifest3D.builders;

import java.util.Iterator;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROLink;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Edge3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Graph3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Node3D;



public abstract class GraphBuilder3D extends BuildStrategy3D {
	/** Logger de mensajes.*/
	private static Logger logger = Logger.getLogger("lg.ArchViz3D");	
	   
	public void build(J3DComponent client) {	
		try {	
			Iterator i = ((Graph3D) client).getModelGraph().getComponentsIterator();

			while (i.hasNext()) {
				Observable graphNode = (Observable) i.next();
				((Graph3D) client).addNode((Node3D)getNodeShape(graphNode));
			}
		} catch(Exception e) {
			logger.log(Level.SEVERE, "BAD Client: " + client.getClass());
		}
		
		this.createLinks((Graph3D)client);
	}

	/**
	 * Crea links teniendo en cuenta que los links estan dentro de los componentes
	 * @param client Grafo de componentes
	 */
	public void createLinks(Graph3D client) {
		PROComponent modelGraph = client.getModelGraph();
		Iterator n = modelGraph.getComponentsIterator();
		while (n.hasNext()) {			
			Iterator i = ((PROComponent)n.next()).getLinksIterator();

			while (i.hasNext()) {
				PROLink graphEdge = (PROLink) i.next();
				PROComponent n1 = graphEdge.getOrigin();
				PROComponent n2 = graphEdge.getTarget();
				Node3D nodo1 = client.getNode3D(n1);
				Node3D nodo2 = client.getNode3D(n2);

				if (nodo1 != null && nodo2 != null)
					client.addEdge((Edge3D)getEdgeShape(nodo1, nodo2, (Observable)graphEdge));
			}
		}
	}
	
	/**
	 * Crea links teniendo en cuenta que componentes y link en mismo nivel 
	 * @param client Grafo de componentes
	 */
	public void createLinks2(Graph3D client) {
		PROComponent modelGraph = client.getModelGraph();
		Iterator i = modelGraph.getLinksIterator();

		while (i.hasNext()) {
			PROLink graphEdge = (PROLink) i.next();
			
			PROComponent n1 = graphEdge.getOrigin();
			PROComponent n2 = graphEdge.getTarget();
			Node3D nodo1 = client.getNode3D(n1);
			Node3D nodo2 = client.getNode3D(n2);
			
			if (nodo1 != null && nodo2 != null)
				client.addEdge((Edge3D)getEdgeShape(nodo1, nodo2, (Observable)graphEdge));
		}
	}

	public abstract J3DComponent getNodeShape(Observable o);

	public abstract J3DComponent getEdgeShape(Node3D p1, Node3D p2, Observable o);
}
