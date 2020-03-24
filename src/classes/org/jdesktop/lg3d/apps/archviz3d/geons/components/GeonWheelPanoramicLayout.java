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
public class GeonWheelPanoramicLayout extends GeonWheelAbstractLayout {
	/** Vector de componentes:
	 * 0: va a la izquierda
	 * 1: va a la centro
	 * 2: va a la derecha.*/
	
	/**
	 * Se acomodan los componentes.
	 * @param comp Componente a acomodar.
	 * @param newConstraints Restricciones.
	 * @return boolean True si se debe ejecutar el layout (layoutContainer) y false
	 * en caso contrario.
	 */
	public boolean rearrangeLayoutComponent(Component3D comp, Object newConstraints) {
		 if (comp != null && components.size() == 3) {
			int index = components.indexOf(comp);

			if (index == 0) 
				switchLeftCenter();
			else if (index == 2) 
				switchRightCenter();
			else 
				return false;

			// Si index = 0 o index = 2 entonces se deben llamar a layoutContainer 
			return true;
		 }

		 return false;
	}

	/**
	 * Mueve el componente que esta en el centro a la derecha, y el
	 * que esta a la derecha al centro.
	 */
	private void switchRightCenter() {
		Component3D center = components.get(1);
		Component3D right = components.get(2);

		components.remove(center);
		components.remove(right);

		components.add(1, right);
		components.add(2, center);
	}

	/**
	 * Mueve el componente que esta en el centro a la izquierda, y el
	 * que esta a la izquierda al centro.
	 */
	private void switchLeftCenter() {
		Component3D center = components.get(1);
		Component3D left = components.get(0);

		components.remove(center);
		components.remove(left);

		components.add(0, center);
		components.add(1, left);
	}

	/**
	 * Se realiza el layout de los componentes.
	 */
	public void layoutContainer() {
		if (components.size() == 3) {
			Component3D left = components.elementAt(0);
			Component3D center = components.elementAt(1);
			Component3D right = components.elementAt(2);
			
			left.changeTranslation(new Vector3f(-0.08f, 0, 0.1f));
			left.changeRotationAngle((float)Math.PI/4);
			left.changeScale(0.5f);
	
			center.changeTranslation(new Vector3f(0, 0, 0));
			center.changeRotationAngle(0);
			center.changeScale(0.5f);
	
			right.changeTranslation(new Vector3f(0.08f, 0, 0.1f));
			right.changeRotationAngle(-(float)Math.PI/4);
			right.changeScale(0.5f);
		}
	}

}
