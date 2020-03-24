package org.jdesktop.lg3d.apps.archviz3d.geons.components;

import java.util.Vector;

import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;

/**
 * Clase padre para todos los layouts de las tres vistas.
 * Las clases que heredan de esta clase fueron implementadas de acuerdo
 * al patron de Diseño "Strategy"
 *  
 * @author Juan Feldman
 *
 */
public abstract class GeonWheelAbstractLayout implements LayoutManager3D {
	/** Lista con los componentes de la rueda.*/
	protected Vector<Component3D> components;

	/**
	 * Instancia GeonWheelAbstractLayout.
	 */
	public GeonWheelAbstractLayout() {
		components = new Vector<Component3D>();
	}
	
	/**
	 * Setea el contenedor al cual se le aplicara el layout.
	 * @param container Contenedor al cual se le aplicara el layout.
	 */
	public void setContainer(Container3D container) {
	}

	/**
	 * Se agrega un componente a la rueda.
	 * @param comp Componente a agregar.
	 * @param constraints Restricciones.
	 */
	public void addLayoutComponent(Component3D comp, Object constraints) {
		comp.setRotationAxis(0.0f, 1.0f, 0.0f);
		components.add(comp);
	}

	/**
	 * Se elimina un componente de la rueda.
	 * @param comp Componente a eliminar.
	 */
	public void removeLayoutComponent(Component3D comp) {
		components.remove(comp);
	}

}
