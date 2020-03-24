package org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts;

import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;

/**
 * Clase padre de todos los layouts.
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public abstract class LayoutStrategy3D {
	/**Componente a aplicar el layout.*/
	private J3DCompositeComponent client;

	/**
	 * Instancia un Layout vacio.
	 */
	public LayoutStrategy3D() {
	}

	/**
	 * Instancia un Layout.
	 */
	public LayoutStrategy3D(J3DCompositeComponent client) {
		this.client = client;
	}

	/**
	 * Retorna el componente a aplicar el layout.
	 * @return J3DCompositeComponent componente a aplicar el layout.
	 */
	public J3DCompositeComponent getClient() {
		return client;
	}

	/**
	 * Setea el componente a aplicar el layout.
	 * @param client componente a aplicar el layout.
	 */
	public void setClient(J3DCompositeComponent client) {
		this.client = client;
	}

	/**
	 * Realiza el layout.
	 */
	public abstract void layout();
}