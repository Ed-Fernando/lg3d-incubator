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
public class GeonWheelStackLayout extends GeonWheelAbstractLayout {
	/** Vector de componentes:
	 * 0: va al centro
	 * 1: va en el tope de la pila
	 * 2: va debajo del tope de la pila.*/

	/**
	 * Se acomodan los componentes.
	 * @param comp Componente a acomodar.
	 * @param newConstraints Restricciones.
	 * @return boolean True si se debe ejecutar el layout (layoutContainer) y false
	 * en caso contrario.
	 */
	public boolean rearrangeLayoutComponent(Component3D comp, Object newConstraints) {
		if (comp != null && components.size() == 3) {
			switchTop(comp);
			return true;
		}

		return false;
	}

	/**
	 * Mueve el componente que esta en el tope de la pila al centro,
	 * y el que esta en el centro debajo del tope de la pila.
	 */
	private void switchTop(Component3D component) {
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
			Component3D center = components.elementAt(0);
			Component3D top = components.elementAt(1);
			Component3D bottom = components.elementAt(2);

			center.changeTranslation(new Vector3f(-0.05f, 0.0f, 0.0f));
			center.changeRotationAngle(0);
			center.changeScale(0.7f);
		
			top.changeTranslation(new Vector3f(0.1f, 0.05f, 0.0f));
			top.changeRotationAngle(-(float)Math.PI/2);
			top.changeScale(0.3f);

			bottom.changeTranslation(new Vector3f(0.1f, -0.05f, 0.0f));
			bottom.changeRotationAngle(-(float)Math.PI/2);
			bottom.changeScale(0.3f);
		}
	}

}
