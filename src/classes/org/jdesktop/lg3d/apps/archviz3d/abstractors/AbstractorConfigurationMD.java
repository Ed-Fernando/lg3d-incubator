package org.jdesktop.lg3d.apps.archviz3d.abstractors;

import java.util.Enumeration;
import java.util.HashMap;


import org.jdesktop.lg3d.apps.archviz3d.abstractors.Abstractor;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;


/**
 * Abstractor para el contenedor de la vista de Modulos y de Deployment.
 *
 * @author Juan Feldman
 *
 */
public class AbstractorConfigurationMD extends Abstractor {
	/** Nivel de abstraccion.*/
	private int abstractionLevel;

	/**
	 * Instancia un MDCompositeAbstractor
	 * @param componentView Componente a abstraer.
	 */
	public AbstractorConfigurationMD(J3DComponent componentView) {
		super(componentView);
		abstractionLevel = 4;
	}

	/**
	 * Setea el nivel de abstraccion.
	 * @param abstractionLevel Nivel de abstraccion.
	 */
	public void setAbstractionLevel(int abstractionLevel) {
		this.abstractionLevel = abstractionLevel;
		changeVisibility();
	}

	/** 
	 * Aumenta el nivel de abstraccion.
	 */
	public void increaseAbstractionLevel() {
		if (abstractionLevel < 4) {
			abstractionLevel++;
			changeVisibility();
		}
	}

	/** 
	 * Disminuye el nivel de abstraccion.
	 */
	public void decreaseAbstractionLevel() {
		if (abstractionLevel > 1) {
			abstractionLevel--;
			changeVisibility();
		}
	}

	/**
	 * Cambia la visibilidad del componente al cual
	 * este abstractor abstrae.
	 */
	private void changeVisibility() {
		Enumeration enume = ((J3DCompositeComponent)getComponentView()).getAllComponents();
		
		while (enume.hasMoreElements()) {
			J3DComponent component = (J3DComponent)enume.nextElement();
			((Abstractor)component.getSubject()).setAbstractionLevel(abstractionLevel);		
		}
	}

    /**
     * Retorna todas las propiedades del componente.
     * @return propiedades del componente.
     */
	public HashMap<String, String> getProperties() {
		return new HashMap<String, String>();
	}
}
