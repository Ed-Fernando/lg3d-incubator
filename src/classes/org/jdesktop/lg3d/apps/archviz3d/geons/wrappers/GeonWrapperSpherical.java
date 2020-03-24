package org.jdesktop.lg3d.apps.archviz3d.geons.wrappers;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.sg.BoundingSphere;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderSur;
import org.jdesktop.lg3d.apps.archviz3d.geons.wrappers.GeonWrapper;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DSimpleComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.objects.LabeledObjectD3D;


/**
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public class GeonWrapperSpherical extends GeonWrapper {
    
	/**
	 * Crea un Wrapper. 
	 * @param comp 
	 * @param componentName Nombre del componente de la arquitectura asociado con
	 * el wrapper
	 */
	public GeonWrapperSpherical (J3DComponent comp, String componentName) {
		super(comp, componentName);
	}

	public void buildWrapper() {
		BoundingSphere bounds = new BoundingSphere();
		bounds.set(nodes.getBounds());
		
		Point3f cen = new Point3f ();
		
		// Creo la imagen para el wrapper
		shapeWrapper = GeonBuilderSur.instance().buildGeonByName(getGeonName());
		shapeWrapper.setAppearance(getAppearance());
		getAppearance().getMaterial().setDiffuseColor(getColor());
		wrapper = new J3DSimpleComponent(shapeWrapper);

		// Seteo la escala y el radio
		if (!bounds.isEmpty()) { 
			wrapper.setScale(scale*1.7f);
			radius = (float)(bounds.getRadius()*scale);
		}
		else  {
			wrapper.setScale(0.5f*scale);
			radius = 0.5f*scale;
		}
		
		// Seteo la posicion del wrapper
		cen = new Point3f(radius - 0.7f, 0.15f, 0.0f);
		wrapper.setTranslation(new Vector3f(cen));

		// Creo el label del wrapper
		if (nodes.getSubject() != null)
			label = new LabeledObjectD3D(wrapper, ((PROComponent)nodes.getSubject()).getName(), new Point3f(-0.1f, 0.62f, 0.0f));
		else 
			label = new LabeledObjectD3D(wrapper, nodes.getName(), new Point3f(-0.1f, 0.62f, 0.0f));
		label.setTranslation(new Vector3f(cen));
		
		//Reubicamos el componente dentro de la visualizacion
		super.buildWrapper();

		if (this.isVisible()) { 
			this.addComponent(wrapper);
			this.addComponent(label);
		}
	}
}