package org.jdesktop.lg3d.apps.archviz3d.geons.wrappers;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.BoundingBox;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderSur;
import org.jdesktop.lg3d.apps.archviz3d.geons.wrappers.GeonWrapper;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DSimpleComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.objects.LabeledObjectD3D;


/**
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public class GeonWrapperLineal extends GeonWrapper {
    
	/**
	 * Crea un Wrapper. 
	 * @param comp 
	 * @param componentName Nombre del componente de la arquitectura asociado con
	 * el wrapper
	 */
	public GeonWrapperLineal (J3DComponent comp, String componentName) {
		super(comp, componentName);
	}

	/** 
	 * Construye el wrapper.
	 */ 
	public void buildWrapper() {
		BoundingBox bounds = nodes.getBoundingBox();
		Point3f p1 = new Point3f();
        Point3f p2 = new Point3f();
        bounds.getLower(p1);
        bounds.getUpper(p2);
        p2.sub(p1);
	        	
		// Creo la imagen para el wrapper
        shapeWrapper = GeonBuilderSur.instance().buildGeonByName(getGeonName());
		shapeWrapper.setAppearance(getAppearance());
		wrapper = new J3DSimpleComponent(shapeWrapper);
		
		bounds.getLower(p1);
        bounds.getUpper(p2);
        p2.add(p1);
        p2.scale(0.5f);
	    
        wrapper.setTranslation(p2.x - 0.1f, p2.y, p2.z);
        
        bounds = wrapper.getBoundingBox();
        
		// Creo el label del wrapper
        if (nodes.getSubject() != null)        
        	label = new LabeledObjectD3D(wrapper, ((PROComponent)nodes.getSubject()).getName(), new Point3f(-0.2f, 0.3f, 0.0f));
        else 
        	label = new LabeledObjectD3D(wrapper, nodes.getName(), new Point3f(-0.2f, 0.3f, 0.0f));
        label.setTranslation(new Vector3f(p2));
        
		//Reubicamos el componente dentro de la visualizacion
		super.buildWrapper();

		if (this.isVisible()) { 
	    	this.addComponent(wrapper);
	       	this.addComponent(label);
	    }
    }
}
