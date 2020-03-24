package org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.graph;

import javax.vecmath.Point3i;
import javax.vecmath.Vector3f;


import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Node3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.graph.GraphLayout3D;



import java.util.LinkedList;
import java.util.Vector;

public abstract class SimpleDimension3D extends GraphLayout3D {
   	protected Point3i dimension;  // Tiene que ser del tipo (0,0,1) (0,1,0) (1,0,0)
	protected float separation;
	protected float dx = 1;
	protected float dy = 1;
	protected float dz = 1;
	protected Vector<Vector3f> points = new Vector<Vector3f>();
	protected LinkedList<Node3D> visitated = new LinkedList<Node3D>();

    protected SimpleDimension3D(Point3i dimension) {
      	this.dimension = dimension;
		this.separation = 1.0f;
		this.dx *= this.dimension.x;
		this.dy *= this.dimension.y;
		this.dz *= this.dimension.z;
    }

    public float getSeparation() {
        return separation;
    }

    public void setSeparation(float separation) {
        this.separation = separation;
    }

    public Point3i getDimension() {
        return dimension;
    }

    public void setDimension(Point3i dimension) {
        this.dimension = dimension;
    }

    protected Vector calculatePoints(int numComp) {
		if (getClient() != null) {
			float sepaux = 0;
            points.add(new Vector3f(0f, 0f, 0f));
			for (int i = 1; i < numComp; i++) {
                sepaux += this.separation;
				points.add(new Vector3f((float)(sepaux * this.dx), (float)(sepaux * this.dy), (float)(sepaux * this.dz)));
            }
			return this.points;
		}
		return this.points;
	}
}
