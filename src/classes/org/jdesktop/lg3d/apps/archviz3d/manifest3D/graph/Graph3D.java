package org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Observable;


import javax.vecmath.Color3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonNumerator;
import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventClick;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Edge3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Node3D;

/**
 * Esta clase representa un grafo.
 * 
 * @author Martin Uzquiano - Manuel Corrales
 */
public class Graph3D extends GeonAbstract {
	private PROComponent modelGraph;
	private J3DCompositeComponent nodes3d = new J3DCompositeComponent();
	private J3DCompositeComponent edges3d = new J3DCompositeComponent();
	private HashMap<Observable, Node3D> dictionary;
 
	/**Constructor de la clase.
	 * @param modelGraph Recibe un grafo modelo como parametro.
	 * @param builder    Recibe un builder para generar la representacion
	 *                   tridimendional del grafo modelo.
	 */
	public Graph3D(PROComponent modelGraph, BuildStrategy3D builder) {
		this.setSubject((Observable)modelGraph);
        this.build();
		this.modelGraph = modelGraph;
		dictionary = new HashMap<Observable, Node3D>();
		this.addComponent(nodes3d);
		this.addComponent(edges3d);
		this.setBuilder(builder);

		// Agrego el evento del click (para poder seleccionar componentes 
		// de la arquitectura)
        this.addListener(new MouseClickedEventAdapter(GeonEventClick.instance()));
        this.setMouseEventPropagatable(true);
	}
	
	/**
	 * Agrega un nodo al grafo tridimensional
	 *
	 * @param node El nodo que se quiere agregar al grafo
	 */
	public void addNode(Node3D node) {
		if (node != null) {
			dictionary.put(node.getSubject(), node);
			nodes3d.addComponent(node);
		}
	}

	/**
	 * Agrega un arco al grafo tridimensional
	 *
	 * @param edge El arco que se quiere agregar al grafo
	 */
	public void addEdge(Edge3D edge) {
		edges3d.addComponent(edge);
		String edgeName = edge.getName();
		edge.setName(edgeName + "_" + GeonNumerator.instance().getNextValue(edgeName));
	}

	/**
	 * Retorna el grafo modelo del cual this es la representacion
	 *
	 * @return Grafo modelo (Graph)
	 */
	public PROComponent getModelGraph() {
		return modelGraph;
	}

	/**
	 * Retorna la representacion 3D (Node3D) de un nodo del grafo modelo
	 *
	 * @param node El nodo modelo del que se quiere su representacion
	 * @return Un Node3D
	 */
	public Node3D getNode3D(PROComponent node) {
		return (Node3D) dictionary.get(node);
	}

	/**
	 * Retorna los nodos del grafo
	 *
	 * @return J3DCompositeComponent con los nodos
	 */
	public J3DCompositeComponent getNodes3d() {
		return nodes3d;
	}

	/**
	 * Retorna los arcos del grafo
	 *
	 * @return J3DCompositeComponent con los arcos
	 */
	public J3DCompositeComponent getEdges3d() {
		return edges3d;
	}
	
    /**
     * Este metodo se llama cuando el usuario
     * cambio el Geon o el color que representan
     * a este componente
     * @param geonName nombre del geon que representa a este componente.
     * @param color color de este componente.
     */
    public void updateImage(String geonName, Color3f color) {
    	super.updateImage(geonName, color);
    	
    	for (Enumeration e = edges3d.getAllComponents(); e.hasMoreElements(); ) {
    		Edge3D edge = (Edge3D)e.nextElement();
    		edge.updateImage(geonName, color);
    	}
    }

	/**
	 * Retorna el abstractor.
	 * Este metodo lo utiliza la clase GeonAbstract para
	 * saber que geon, color y lista de geones posee un
	 * componente de la arquitectura.
     * @return Observable abstractor
	 */
	public Observable getGeonAbstractor() {
		return ((J3DComponent)edges3d.getComponent(0)).getSubject();
	}

    /**
     * Seteo el color 
     * @param color color a setear
     */
    public void setColor(Color3f color) {
    	for (Enumeration e = edges3d.getAllComponents(); e.hasMoreElements(); ) {
    		Edge3D edge = (Edge3D)e.nextElement();
    		edge.setColor(color);
    	}
    }

    /**
     * Setea el nombre del Geon que reprensenta a este componente
     * @param geonName Nombre del Geon que reprensenta a este 
     * componente
     */
    public void setGeonName(String geonName) {
    	for (Enumeration e = edges3d.getAllComponents(); e.hasMoreElements(); ) {
    		Edge3D edge = (Edge3D)e.nextElement();
    		((AbstractorCC)edge.getGeonAbstractor()).setGeonName(geonName);
    	}
    }
    
    /**
     * Retorna el nombre del componente de la arquitectura que
     * representa esta clase.
     * @return String nombre del componente de la arquitectura que
     * representa esta clase.
     */
    public String getName() {
    	if (edges3d.numComponents() > 0) 
    		return ((J3DComponent)edges3d.getComponent(0)).getName().split("_")[0];
    	return null;
    }
    
    /**
     * Retorna la apariencia.
     * @return Appearance Apariencia.
     */
    public Appearance getAppearance() {
    	return null;
    }
    
}
