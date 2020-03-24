package org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;


import org.jdesktop.lg3d.apps.archviz3d.geons.GeonCluster;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.ComponentGraph3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Node3D;


import java.util.Hashtable;
import java.util.Observable;
import java.util.Vector;


/**
 * Esta clase representa un arco de un grafo.
 * 
 * @author Gaston Peirano & Mauricio Poncio
 */
public abstract class Edge3D extends ComponentGraph3D {
	private Node3D start;
	private Node3D end;
	private float length;
	private Vector3f vector = new Vector3f();
	private Hashtable<GeonCluster, Node3D> collapseHistoryTable = new Hashtable<GeonCluster, Node3D>();
	private boolean directed = false;
	private float weight = 0.5f;
	
	/**
	 * vertex es un vector con los puntos que generan el arco.
	 */
	protected Vector<Point3f> vertex = new Vector<Point3f>();

	/**
	 * Genera un arco que esta unido a dos nodos.
	 * @param start nodo principio
	 * @param end nodo fin
	 */
	public Edge3D(Node3D start, Node3D end) {
		super();
		setStart(start);
		setEnd(end);
	}
	
	/**
	 * Genera un arco que esta unido a dos nodos de acuerdo a una parte grafica.
	 * @param start el nodo donde comienza el arco
	 * @param end el nodo donde termina el arco
	 * @param o el observador del arco
	 * @param builder un EdgeBuild3D
	 */
	public Edge3D(Node3D start, Node3D end,Observable o, BuildStrategy3D builder) {
		setStart(start);
		setEnd(end);
		setBuilder(builder);
        this.setSubject(o);
        this.build();
	}

	/**
	 * Devuelve el nodo principio.
	 * @return  nodo principio
	 */
	public Node3D getStart() {
		return start;
	}
	
	/**
	 * Setea el nodo principio.
	 * @param newStart nuevo nodo principio
	 */
	public void setStart(Node3D newStart) {
		start = newStart;
		newStart.addEdge(this);
	}
	
	/**
	 * Setea el nodo fin.
	 * @param newEnd nuevo nodo fin
	 */
	public void setEnd(Node3D newEnd) {
		end = newEnd;
		newEnd.addEdge(this);
	}
	
	/**
	 * Devuelve el nodo fin.
	 * @return nodo fin
	 */
	public Node3D getEnd() {
		return end;
	}
	
	/**
	 * Cambia la direccion del arco.
	 */
	public void reverseDirection() {
		start.removeEdge(this);
		end.removeEdge(this);
		Node3D n = start;
		setStart(end);
		setEnd(n);
	}
	
	/**
	 * Devuelve la longitud del arco.
	 * @return la longitud
	 */
	public float getLength() {
		return length;
	}
	
	/**
	 * Cambia uno de los nodos unidos al arco por otro.
	 * @param oldNode el nodo a cambiar
	 * @param newNode el nuevo nodo
	 */
	private void swapNode(Node3D oldNode, Node3D newNode) {
		if(oldNode==start) {
			setStart(newNode);
		}
		else if(oldNode==end) {
			setEnd(newNode);
		}
	}
	
	/**
	 * Cuando encapsulo un cluster, cada arco que iba hacia el interior del mismo (a un nodo)
	 * deja de estar unido al nodo y pasa a estar unido al cluster.
	 * @param cluster el cluster que encapsulo
	 * @param node el nodo dentro del cluster hacia el que iba el arco
	 */
	public void collapse(GeonCluster cluster, Node3D node) {
		collapseHistoryTable.put(cluster,node);
		swapNode(node, cluster);
	}
	
	/**
	 * Cuando desencapsulo un cluster, le devuelvo al arco su nodo original.
	 * @param cluster el cluster que desencapsulo
	 */
	public void expand(GeonCluster cluster) {
		Node3D node = (Node3D)collapseHistoryTable.remove(cluster);
		swapNode(cluster, node);
	}
	
	/**
	 * Elimina el arco.
	 */
	public void delete() {
		start.removeEdge(this);
		end.removeEdge(this);
	}
	
	/**
	 * Recalcula el vector y la longitud del arco.
	 */
	public void recalculate() {
		vector.sub(end.getPosition(), start.getPosition());
		length = vector.length();
	}
	
	/**
	 * Setea el vector que representa al arco.
	 * @param v nuevo vector
	 */
	public void setVector(Vector3f v) {
		vector.set(v);
	}
	
	/**
	 * Devuelve el vector que representa al arco.
	 * @return el vector
	 */
	public Vector3f getVector() {
		return vector;
	}
	
	/**
	 * Informa si el arco es dirigido o no.
	 * @return true si es dirigido
	 */
	public boolean isDirected() {
		return directed;
	}
	
	/**
	 * Setea si un arco es dirigido o no.
	 * @param directed true para que sea dirigido
	 */
	public void setDirected(boolean directed) {
		this.directed = directed;
	}
	
	/**
	 * Setea el peso del arco.
	 * @param weight nuevo peso
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	/**
	 * Devuelve el peso del arco.
	 * @return peso del arco
	 */
	public float getWeight() {
		return weight;
	}
	
	/**
	 * Devuelve los vertices que conforman el arco.
	 * @return los vertices del arco (posicion del nodo origen y posicion del nodo fin)
	 */
	public Vector getVertexs() {
		vertex.add(getStart().getPosition());
		vertex.add(getEnd().getPosition());
		return vertex;
	}

	public synchronized void update(Observable o, Object arg) {
		super.update(o,arg);
	}
}