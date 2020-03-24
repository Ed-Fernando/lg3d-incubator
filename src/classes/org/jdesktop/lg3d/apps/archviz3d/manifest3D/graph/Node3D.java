package org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph;

import java.util.Vector;
import java.util.Enumeration;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.ComponentGraph3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Edge3D;

/**
 * Esta clase representa un nodo de un grafo.
 * 
 * @author Gaston Peirano & Mauricio Poncio
 */
public abstract class Node3D extends ComponentGraph3D {
    private Vector edges = new Vector();
    /**Radio actual.*/
    private float radius = 0.1f;
     /**Radio anterior.*/
    protected float antRadius = 0.1f;
    /**Masa del nodo.*/
    private float mass = 1f;
    
    /**
     * Crea un nodo con valores por defecto.
     */
    public Node3D(){
    }

    /**
     * Agrega un arco al nodo.
     * @param edge el arco a agregar
     */
    @SuppressWarnings("unchecked")
	public void addEdge(Edge3D edge) {
        edges.add(edge);
        addObserver(edge); //cuando cambio un nodo de lugar el edge tiene que estar observando
    }
    
    /**
     * Elimina un arco de un nodo.
     * @param edge el arco a remover
     */
    public void removeEdge(Edge3D edge) {
        edges.remove(edge);
        deleteObserver(edge);
    }

    /**
     * Elimina el nodo.
     */
    public void delete() {
        edges.removeAllElements();
    }
    
    /**
     * Retorna los arcos que est?n unidos al nodo.
     * @return los arcos
     */
    public Vector getEdges() {
        return edges;
    }
    
    /**
     * Averigua todos los arcos donde el nodo es el principio.
     * @return un vector con los arcos
     */
    @SuppressWarnings("unchecked")
    public Vector getOutEdges() {
        Vector out = new Vector();
        Enumeration enume = edges.elements();
        while(enume.hasMoreElements()) {
            Edge3D e = (Edge3D)enume.nextElement();
            if(e.getStart()== this) {
                out.add(e);
            }
        }
        return out;
    }
    
    /**
     * Setea los arcos del nodo.
     * @param edges los nuevos arcos del nodo
     */
    @SuppressWarnings("unchecked")
    public void setEdges(Vector edges) {
        edges.addAll(edges);
    }
    
    /**
     * Setea el radio del nodo.
     * @param radius nuevo radio
     */
    public void setRadius(float radius) {
        antRadius = getRadius();
        this.radius = radius;
    }
    
   /**
    * Devuelve el radio del nodo.
    * @return el radio
    */
    public float getRadius() {
        return radius;
    }
    
    /**
    * Devuelve el radio del nodo antes de haber sido cambiado.
    * @return el radio anterior
    */
    public float getAntRadius() {
        return antRadius;
    }
    
    /**
     * Setea la masa del nodo.
     * @param mass nueva masa
     */
    public void setMass(float mass) {
        this.mass = mass;
    }
    
    /**
     * Cada nodo tiene su masa.
     * @return la masa del nodo
     */
    public float getMass() {
        return mass;
    }
    
    /**
     * Retorna la cantidad de arcos unidos al nodo.
     * @return la cantidad de arcos
     */
    public int getDegree() {
        return edges.size();
    }
    
    /**
     * Traslada el nodo.
     * @param translation Traslacion a realizar.
     */
    public void setTranslation(Vector3f translation) {
        super.setTranslation(translation);
        setChanged();
        notifyObservers();
    }
    
}