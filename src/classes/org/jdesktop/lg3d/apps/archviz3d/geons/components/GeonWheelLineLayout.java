package org.jdesktop.lg3d.apps.archviz3d.geons.components;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.wg.Component3D;

import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonWheelAbstractLayout;

/**
 * Esta clase se encarga del layout de las tres vistas.
 *  
 * @author Juan Feldman
 *
 */
public class GeonWheelLineLayout extends GeonWheelAbstractLayout {
	/** Vector de componentes:
	 * 0: va adelante
	 * 1: va detras de 0
	 * 2: va detras de 1.*/

	/**
	 * Se acomodan los componentes.
	 * @param comp Componente a acomodar.
	 * @param newConstraints Restricciones.
	 * @return boolean True si se debe ejecutar el layout (layoutContainer) y false
	 * en caso contrario.
	 */
	public boolean rearrangeLayoutComponent(Component3D comp, Object newConstraints) {
		if (comp != null && components.size() == 3) {
			switchFirst(comp);
			return true;
		}

		return false;
	}

	/**
	 * Mueve el componente que esta adelante hacia atras, y el que le sigue
	 * se pasa adelante.
	 */
	private void switchFirst(Component3D component) {
		int index = components.indexOf(component);
		
		for (int i = 0; i < index; i++) {
			Component3D first = components.elementAt(0);
			components.remove(first);
			components.add(2, first);
		}
	}

	/**
	 * Se realiza el layout de los componentes.
	 */
	public void layoutContainer() {
		if (components.size() == 3) {
			Component3D first = components.elementAt(0);
			Component3D second = components.elementAt(1);
			Component3D third = components.elementAt(2); 
	
			first.changeTranslation(new Vector3f(0.0f, 0.0f, 0.1f));
			first.changeRotationAngle(0);
			first.changeScale(0.5f);

			second.changeTranslation(new Vector3f(0.05f, 0.05f, 0.0f));
			second.changeRotationAngle(0);
			second.changeScale(0.5f);
	
			third.changeTranslation(new Vector3f(0.1f, 0.1f, -0.1f));
			third.changeRotationAngle(0);
			third.changeScale(0.5f);
		}
	}

}
