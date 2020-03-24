package org.jdesktop.lg3d.apps.archviz3d.geons.components;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Component3D;

import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonWheelAbstractLayout;

/**
 * Esta clase se encarga del layout de las tres vistas en la rueda.
 *  
 * @author Juan Feldman
 *
 */
public class GeonWheelLayout extends GeonWheelAbstractLayout {
	private float EXPAND_RATIO = 1.5f;
	/** Tamaño del eje principal de rotacion.*/
	private float majorAxis;
	/** Tamaño del eje secundario de rotacion.*/
	private float minorAxis;

	/**
	 * Instancio el GeonWheelLayout  
	 * @param majorAxis Tamaño del eje principal de rotacion.
	 * @param minorAxis Tamaño del eje secundario de rotacion
	 */
	public GeonWheelLayout(float majorAxis, float minorAxis) {
		this.majorAxis = majorAxis;
		this.minorAxis = minorAxis;
	}

	/**
	 * Se acomodan los componentes.
	 * @param comp Componente a acomodar.
	 * @param newConstraints Restricciones.
	 * @return boolean True si se debe ejecutar el layout (layoutContainer) y false
	 * en caso contrario.
	 */
	public boolean rearrangeLayoutComponent(Component3D comp, Object newConstraints) {
		if (newConstraints != null) {
			if (newConstraints instanceof Integer) {
				if ((Integer)newConstraints > 0) 
					goForward();
				else if ((Integer)newConstraints < 0) 
					goBackward();
			} else 
				return false;
		} else if (comp != null) {
			int index = components.indexOf(comp);
			int number = components.size();

			if (index >= number / 2) 
				for (int c = number - index; c > 0; c--) 
					goBackward();
			else if (index < number / 2 && index > 0) 
				for (int c = index; c > 0; c--) 
					goForward();
			else 
				return false;
		} else 
			return false;

		return true;
	}

	/**
	 * La rueda va hacia adelante.
	 */
	private void goForward() {
		Component3D node = components.get(0);
		components.remove(node);
		components.add(node);
	}

	/**
	 * La rueda va hacia atras.
	 */
	private void goBackward() {
		int number = components.size();
		Component3D node = components.get(number - 1);
		components.remove(node);
		components.add(0, node);
	}

	/**
	 * Se realiza el layout de los componentes de la rueda.
	 */
	public void layoutContainer() {
		int number = components.size();
		
		if (number > 0) {
			Component3D comp0 = components.get(0);
	
			Vector3f v = new Vector3f();
			v = comp0.getPreferredSize(v);
	
			double length = (double)v.y;
			if (length < (double)v.x) 
				length = (double)v.x;
	
			int coefficient = calculateCoefficient(number, length * 1.25f);
	
			float x = 0.0f;
			float y = 0.0f;
	
			for (int i = 0; i < number; i++) {
				Component3D comp = components.get(i);
	
				double r = Math.PI * 2.0 / number * i;
				double rr = 1;
				double a = 1.0 / Math.PI;
	
				for (int j = 0; j < coefficient; j++) {
					rr *= (r - Math.PI);
					a *= Math.PI;
				}
	
				r = rr / a + Math.PI;
	
				float sin = (float)Math.sin(r);
				float cos = (float)Math.cos(r);
	
				float scale;
				if (i == 0) 
					scale = EXPAND_RATIO;
				 else 
					scale = (cos + 2.0f) / 3.0f;
				
				//COMIENZO MIO
				scale = scale/2;
				//FIN MIO
				
				comp.changeScale(scale);
				comp.changeRotationAngle(0);
	
				x = majorAxis * sin;
				float z = minorAxis * cos;
				comp.changeTranslation(x, y, z);
			}
		}
	}

	private int calculateCoefficient(int number, double length) {
		double radSeg = 2.0 * Math.PI / number;
		double x = 1;
		double a = 1;
		double pi2 = Math.PI * Math.PI;
		int coefficient;

		if (number < 3) {
			coefficient = 1;
		} else {
			for (coefficient = 1;; coefficient += 2) {
				if (coefficient == 1) {
					x = (radSeg - Math.PI);
				} else {
					x *= (radSeg - Math.PI);
					x *= (radSeg - Math.PI);
					a *= pi2;
				}

				if (length < majorAxis * Math.sin(x / a + Math.PI)) {
					break;
				}
			}
		}

		return coefficient;
	}

}
