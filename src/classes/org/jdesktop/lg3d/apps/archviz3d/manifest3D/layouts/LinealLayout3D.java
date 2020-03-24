package org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.BoundingBox;

import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.ComponentsLayout3D;



import java.util.Enumeration;
import java.util.Vector;

/**
 *
 * @author Alfredo Teyseyre
 */
public class LinealLayout3D extends ComponentsLayout3D {
	/**Separacion y direccion.*/
	private Vector3f separation, direction;

	/**
	 * Instancia un LinealLayout3D
	 */
	public LinealLayout3D() {
		separation = new Vector3f();
		direction = new Vector3f(0.0f, 1.0f, 0.0f); //Eje Y
	}

	/**
	 * Instancia un LinealLayout3D
	 * @param separation separacion entre los componentes
	 * a los cuales se les aplicara el layout.
	 */
	public LinealLayout3D(Vector3f separation) {
		this.separation = separation;
		direction = new Vector3f(0.0f, 1.0f, 0.0f); //Eje Y
	}

	/**
	 * Instancia un LinealLayout3D
	 * @param direction direccion del layout.
	 * @param separation separacion entre los componentes
	 * a los cuales se les aplicara el layout.
	 */
	public LinealLayout3D(Vector3f direction, Vector3f separation) {
		this.direction = direction;
		this.separation = separation;
	}

	/**
	 * Retorna la separacion entre los componentes
	 * a los cuales se les aplicara el layout.
	 * @return Vector3f separacion entre los componentes
	 * a los cuales se les aplicara el layout. 
	 */
	public Vector3f getSeparation() {
		return separation;
	}

	/**
	 * Setea la separacion entre los componentes
	 * a los cuales se les aplicara el layout.
	 * @param separation separacion entre los componentes
	 * a los cuales se les aplicara el layout. 
	 */
	public void setSeparation(Vector3f separation) {
		this.separation = separation;
	}

	/**
	 * Retorna la direccion del layout
	 * @return Vector3f direccion del layout
	 */
	public Vector3f getDirection() {
		return direction;
	}

	/**
	 * Setea la direccion del layout
	 * @param direction Direccion del layout
	 */
	public void setDirection(Vector3f direction) {
		this.direction = direction;
	}

	/**
	 * @return Vector 
	 */
	@SuppressWarnings ("unchecked")
	protected Vector layoutPoints() {
		Vector points = new Vector();

		Vector3d position = new Vector3d();

		for (Enumeration e = getClient().getAllComponents(); e.hasMoreElements();) {
			points.add(new Vector3d(position));
			
			J3DComponent object3d = (J3DComponent) e.nextElement();

			BoundingBox bound = new BoundingBox(object3d.getBounds());
			Point3f low, up, lon;
			low = new Point3f();
			up = new Point3f();
			lon = new Point3f();
			bound.getUpper(up);
			bound.getLower(low);
			lon.sub(up, low);

			//position.add(new Vector3f(1 * lon.x * direction.x + separation.x, 1 * lon.y * direction.y + separation.y, 1 * lon.z * direction.z + separation.z));
			position.add(new Vector3d(direction.x * separation.x, direction.y * separation.y, direction.z * separation.z));
		}

		return points;
	}
}