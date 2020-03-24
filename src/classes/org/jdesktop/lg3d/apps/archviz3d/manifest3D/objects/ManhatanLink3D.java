package org.jdesktop.lg3d.apps.archviz3d.manifest3D.objects;


import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import org.jdesktop.lg3d.apps.archviz3d.geons.GeonLink;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Edge3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Node3D;
import org.jdesktop.lg3d.sg.Appearance;


import java.util.Enumeration;
import java.util.Observable;
import java.util.Vector;

/**
 * Arco cuyos componentes son paralelos a los ejes cartesianos ortogonales.
 * 
 * @author Gastón Peirano & Mauricio Poncio
 * 
 */
public class ManhatanLink3D extends Edge3D {
	private float longitudFlecha = 0.13f;

    /**
     * Crea un arco Manhatan entre dos nodos.
     * @param start nodo inicio
     * @param end nodo fin
     * @param o Abstractor del link
     * @param builder Builder del link
     * @param componentName Nombre del componente de la arquitectura al
     * cual representa este link
     */
    public ManhatanLink3D(Node3D start, Node3D end, Observable o, BuildStrategy3D builder, String componentName) {
    	super(start,end);
    	linePoints();
    	this.setName(componentName);
        this.builder = builder;
        this.setSubject(o);
        this.build();
    }

    /**
     * Devuelve la longitud de la flecha.
     * @return tamaño de la flecha
     */
    public float longitudFlecha() {
        return longitudFlecha;
    }
    /**
     * Setea la longitud de la flecha.
     * @param longitudFlecha nueva longitud
     */
    public void longitudFlecha(float longitudFlecha) {
        this.longitudFlecha = longitudFlecha;
    }

    /**
     * Genera los puntos que conforman el arco Manhatan.
     */
    public void linePoints() {
        vertex = new Vector<Point3f>();
        Point3f posStart;
        Point3f posEnd;
        posStart = getStart().getPosition();
        posEnd = getEnd().getPosition();

        vertex.add(posStart);
        vertex.add(new Point3f(posStart.x,posStart.y,posEnd.z));
        vertex.add(new Point3f(posStart.x,posEnd.y,posEnd.z));
        vertex.add(new Point3f(posEnd.x,posEnd.y,posEnd.z));
    }
    
    /**
     * Devuelve los puntos que conforman el arco Manhatan.
     * @return un vector con los puntos
     */
     public Vector getVertexs() {
        return vertex;
    }
     
    /**
     * Este metodo se llama cuando el usuario cambia el Geon 
     * o el color que representan a este componente
     * @param geonName nombre del geon que representa a este componente.
     * @param color color de este componente.
     */
    public void updateImage(String geonName, Color3f color) {
    	super.updateImage(geonName, color);

    	// Seteo el color del link
    	for (Enumeration e = this.getAllComponents(); e.hasMoreElements();) {
    		Object obj = e.nextElement();
    		if (obj instanceof GeonLink) 
    			((GeonLink)obj).updateImage(geonName, color);
    	}
    }
    
    /**
     * Retorna la apariencia
     * @return Appearance apariencia
     */
    public Appearance getAppearance() {
    	return null;
    }
    
}