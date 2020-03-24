package org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3d;

import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.ComponentsLayout3D;

import java.util.Vector;

/**
 * Clase padre para el layout SphericalLayout3D  
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public abstract class RevolutionLayout3D extends ComponentsLayout3D {
	/**Complejidad.*/
	protected int complexity = -1;
	/**Puntos.*/
	protected Vector points = new Vector();
	/**Radio.*/
	protected float radius;

	/**
	 * Constructor de RevolutionLayout3D
	 */
	public RevolutionLayout3D() {
		radius = 0.5f;
	}

	/**
	 * Retorna los puntos calculados
	 * @return Vector vector con los puntos calculados
	 */
	public abstract Vector<Vector3d> calculatePoints();

	/**
	 * @return Vector  
	 */
	@SuppressWarnings ("unchecked")
	public Vector layoutPoints() {
		float angle;
		Matrix4f mat = new Matrix4f();
		Vector3d rot = new Vector3d();
		Vector vertices = new Vector();
		
		points = calculatePoints();
		
		int pCount = points.size();
		
		if (pCount > 0 && getComplexity() > 0) {
			for (int i = 0; i < pCount; i++) 
				vertices.add(new Vector3d(((Vector3d) points.elementAt(i)).x, ((Vector3d) points.elementAt(i)).y, 0.0f));

			for (int i = 1; i <= this.getComplexity() - 1; i++) {
				angle = (float)(Math.PI * 2.0 / (getComplexity()) * i);
				mat.setIdentity();
				mat.rotY(angle);
				for (int j = 0; j < pCount; j++) {
					Vector3d aux = (Vector3d) vertices.elementAt(j);
					rot = multiply(mat, aux);
					vertices.add(rot);
				}
			}
		}
		return vertices;
	}

	/**
	 * Multiplica la matriz por el vector 
	 * @param mat matriz a multiplicar
	 * @param vec vector a multiplicar
	 * @return Vector3f vector resultado
	 */
	private Vector3d multiply(Matrix4f mat, Vector3d vec) {
		Vector3d ret = new Vector3d();
		ret.x = (mat.m00 * vec.x + mat.m01 * vec.y + mat.m02 * vec.z);
		ret.y = (mat.m10 * vec.x + mat.m11 * vec.y + mat.m12 * vec.z);
		ret.z = (mat.m20 * vec.x + mat.m21 * vec.y + mat.m22 * vec.z);

		return ret;
	}

	/**
	 * Retorna la complejidad
	 * @return int complejidad
	 */
	public int getComplexity() {
		return complexity;
	}

	/**
	 * Setea la complejidad
	 * @param complexity complejidad
	 */
	public void setComplexity(int complexity) {
		this.complexity = complexity;
	}

	/**
	 * Retorna los puntos 
	 * @return points puntos
	 */
	public Vector getPoints() {
		return points;
	}

	/**
	 * Setea los puntos 
	 * @param points puntos
	 */
	public void setPoints(Vector points) {
		this.points = points;
	}

	/**
	 * Retorna el radio
	 * @return float Radio
	 */
	public float getRadius() {
		return radius;
	}

	/**
	 * Setea el radio
	 * @param radius Radio
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}

}
