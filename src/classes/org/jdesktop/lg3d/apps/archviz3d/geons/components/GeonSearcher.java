package org.jdesktop.lg3d.apps.archviz3d.geons.components;

import java.util.Enumeration;

import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.sg.TransformGroup;



/**
 * Esta clase se encarga de buscar un componente de la arquictecura 
 * dentro del grafo de una vista. 
 * 
 * @author Juan Feldman
 *
 */
public class GeonSearcher {
	/** Vista en la cual se buscara al componente.*/ 
	private J3DCompositeComponent view;
	
	/**
	 * Se intancia el GeonSearcher. 
	 * @param view Vista en la cual se buscara al componente. 
	 */
	public GeonSearcher(J3DCompositeComponent view) {
		this.view = view;
	}
	
	/**
	 * Retorna el componente que se desea encontrar.
	 * @param componentName Nombre del componente que se desea encontrar.
	 * @return GeonAbstract Componente que se desea encontrar.
	 */
	public GeonAbstract searchComponent(String componentName) {
		return getComponent(view.getTransformGroup(), componentName);
	}

	/**
	 * Retorna el evento del componente que se desea encontrar.
	 * @param componentName Nombre del componente cuyo evento se desea encontrar.
	 * @param eventName Nombre del evento que se desea encontrar.
	 * @return GeonAbstract Evento que se desea encontrar.
	 */
	public GeonAbstract searchComponentEvent(String componentName, String eventName) {
		GeonAbstract component = getComponent(view.getTransformGroup(), componentName);
		if (component != null) 
			return getComponentEvent(component.getTransformGroup(), eventName);
		return null;
	}
	
	
	/**
	 * Retorna el componente de la arquitectura cuyo nombre es igual al parametro
	 * <b>componentName</b>.
	 * @param tg Contenedor de los componentes de la arquitectura.
	 * @param componentName Nombre del componente de la arquitectura que se desea
	 * obtener.
	 */
	private GeonAbstract getComponent(TransformGroup tg, String componentName) {
    	for (Enumeration enume = tg.getAllChildren(); enume.hasMoreElements();) {
    		Object obj = enume.nextElement();
    		
    		if (obj instanceof J3DComponent) {
    			// Verifico si encontre el componente
	    		J3DComponent component = (J3DComponent)obj;

    			if (searchOk(component, componentName))
    				return (GeonAbstract)component;
	    		
	    		// Verifico si el componente esta en los hijos 
	    		component = getComponent(((J3DComponent)obj).getTransformGroup(), componentName);
    			
    			if (searchOk(component, componentName))
    				return (GeonAbstract)component;
    		}
    	}
    	return null;
    }

	/**
	 * Retorna el evento de la arquitectura cuyo nombre es igual al parametro
	 * <b>eventName</b>.
	 * @param tg Contenedor de los componentes de la arquitectura.
	 * @param componentName Nombre del evento de la arquitectura que se desea
	 * obtener.
	 */
	private GeonAbstract getComponentEvent(TransformGroup tg, String eventName) {
    	for (Enumeration enume = tg.getAllChildren(); enume.hasMoreElements();) {
    		Object obj = enume.nextElement();
    		
    		if (obj instanceof J3DComponent) {
    			// Verifico si encontre el componente
	    		J3DComponent component = (J3DComponent)obj;

    			if (searchOk(component, eventName))
    				return (GeonAbstract)component;
	    		
	    		// Verifico si el componente esta en los hijos (los contenedores).
    			// El nombre del contenedor debe ser nulo para evitar que se busque el evento
    			// en otro componente de la arquitectura.
    			if (component.getName() == null) {
        			component = getComponentEvent(((J3DComponent)obj).getTransformGroup(), eventName);
        			
        			if (searchOk(component, eventName))
        				return (GeonAbstract)component;
    			}
    		}
    	}
    	return null;
    }

	/**
	 * Retorna true si se encontro el componente de la arquitectura cuyo 
	 * nombre es igual al parametro <b>componentName<b>.
	 * 
	 * @param component Componente a analizar. 
	 * @param componentName Nombre del componente de la arquitectura que 
	 * se desea encontrar.
	 * @return boolean True si se encontro el componente, false en caso
	 * contrario.
	 */
    private boolean searchOk (J3DComponent component, String componentName) {
    	if (component != null) {
    		String name = component.getName();

    		if (name != null) { 
    			name = name.toLowerCase();

    			if (name.equals(componentName.toLowerCase())) 
    				return true;
    		}
    		return false;
    	}
		return false;
    }

}
