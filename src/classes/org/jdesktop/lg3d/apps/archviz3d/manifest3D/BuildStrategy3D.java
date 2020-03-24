package org.jdesktop.lg3d.apps.archviz3d.manifest3D;

import java.util.Observable;

import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;

/**
 * Clase padre de todos los builders.
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public abstract class BuildStrategy3D {

	/**
	 * Construye el componente que se recibe como parametro (Geon o GeonCluster) 
	 * @param client Componente a construir
	 */
	public abstract void build(J3DComponent client);

	/**
	 * Construye los eventos para el componente que se recibe como parametro (Geon o GeonCluster) 
	 * @param client Componente al cual se le contruiran los eventos
	 */
	public J3DCompositeComponent buildEvents(J3DComponent client) {return null;}

	public void update(J3DComponent client, Observable o, Object arg) {}

	/**
	 * Elimina los componentes del componente que se recibe como parametro 
	 * @param client Componente al cual se le eliminaran sus componentes
	 */
	public void clearBuild(J3DComponent client) {
		((J3DCompositeComponent)client).removeAllComponents();
	}
}
