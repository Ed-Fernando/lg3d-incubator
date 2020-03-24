package org.jdesktop.lg3d.apps.archviz3d.geons.wrappers;

import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.BoundingSphere;
import org.jdesktop.lg3d.sg.Shape3D;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderSur;
import org.jdesktop.lg3d.apps.archviz3d.geons.wrappers.GeonWrapper;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DSimpleComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.objects.LabeledObjectD3D;


/**
 * 
 * @author teyseyre
 * 
 */
public class SphereWrapper extends GeonWrapper {

	/**
	 * Crea un Wrapper. 
	 * @param comp Componentes a los que el wrapper envuelve
	 * @param componentName Nombre del componente de la arquitectura asociado con
	 * el wrapper
	 */
	public SphereWrapper(J3DComponent comp, String componentName) {
		super(comp, componentName);
	}

	/** 
	 * Construye el wrapper.
	 */ 
	public void buildWrapper() {
		BoundingSphere bounds = new BoundingSphere();
		bounds.set(nodes.getBounds());

		// Calculo el radio del wrapper
		float fa = getScale();
		if (!bounds.isEmpty()) 
			radius = (float)bounds.getRadius() * fa/2;
		else 
			radius = 0.3f * fa;
		
		// Creo la imagen para el wrapper
		Shape3D shapeWrapper = GeonBuilderSur.instance().buildGeonByName(getGeonName());
		shapeWrapper.setAppearance(getAppearance());
		wrapper = new J3DSimpleComponent(shapeWrapper);
		wrapper.setScale(radius + 0.6f);
		
		// Calculo el centro del wrapper
		Point3f cen = new Point3f();
		bounds.getCenter(cen);
		cen.x = cen.x + 1f; 
		cen.y = cen.y - 1f; 
		wrapper.setTranslation(cen.x, cen.y, cen.z);
		
		// Creo el label para el wrapper
		if (nodes.getSubject() != null)
			label = new LabeledObjectD3D(wrapper, ((PROComponent) nodes.getSubject()).getName(), new Point3f(-0.2f, 0.75f, 0.0f));
		else
			label = new LabeledObjectD3D(wrapper, nodes.getName(), new Point3f(-0.2f, 0.75f, 0.0f));
		
		// Ubico el label para el wrapper
		label.setTranslation(cen.x, cen.y - 0.3f, cen.z);
		
		//Reubicamos el componente dentro de la visualizacion
		super.buildWrapper();

		if (this.isVisible()) {
			this.addComponent(wrapper);
			this.addComponent(label);
		}

	}

//	public void setProperties() {
//	}
	
}
