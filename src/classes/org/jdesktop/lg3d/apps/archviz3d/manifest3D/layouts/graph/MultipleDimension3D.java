package org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.graph;


import javax.vecmath.Vector3d;


import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Graph3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Node3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.SphericalLayout3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.graph.GraphLayout3D;


import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Vector;


public abstract class MultipleDimension3D extends GraphLayout3D {
	protected Vector<Vector3d> points = new Vector<Vector3d>();
	protected float radiousIni = 0.3f;
    protected float radiousDif = 0.3f;

	protected float compIni = 3;
    protected float compSum = 3;

	protected LinkedList<Node3D> visitated = new LinkedList<Node3D>();
	protected Graph3D graph;
	protected float separation = 0.3f;
	protected float sep = 0.3f;

    protected int capas = 3;


	public MultipleDimension3D(int capas) {
		this.capas = capas;
	}

	public Vector3d findNearestPosition(Vector3d v, Vector p) {
		Enumeration e = p.elements();
		double minLenght = 50000000;
		Vector3d pos = null;
		Vector3d aux = new Vector3d();
		while (e.hasMoreElements()) {
			Vector3d vector3d = (Vector3d) e.nextElement();
			aux.sub(v, vector3d);
			if (minLenght > aux.length()) {
				pos = vector3d;
				minLenght = aux.length();
			}
		}
		return pos;
	}

	protected void aggregatePoints() {
		SphericalLayout3D sl = new SphericalLayout3D();
		sl.setComplexity((int) compIni);
		sl.setRadius(radiousIni);
		
		// Calculo los puntos
		Enumeration e = sl.layoutPoints().elements();
		
		while (e.hasMoreElements()) {
			Vector3d vector3d = (Vector3d) e.nextElement();
			points.add(vector3d);
		}
		radiousIni += radiousDif;
		compIni += compSum;
	}

    public void setRadiousIni(float radiousIni) {
        this.radiousIni = radiousIni;
    }

    public void setRadiousDif(float radiousDif) {
        this.radiousDif = radiousDif;
    }

    public void setCompIni(float compIni) {
        this.compIni = compIni;
    }

    public void setCompSum(float compSum) {
        this.compSum = compSum;
    }

    public void setSeparation(float separation) {
        this.separation = separation;
    }

    public void setSep(float sep) {
        this.sep = sep;
    }
}
