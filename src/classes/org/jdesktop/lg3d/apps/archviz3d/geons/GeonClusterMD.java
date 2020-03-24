package org.jdesktop.lg3d.apps.archviz3d.geons;

import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.BoundingBox;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;

import org.jdesktop.lg3d.apps.archviz3d.abstractors.Abstractor;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonSimpleMD;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderSur;
import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventClick;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DSimpleComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.objects.LabeledObjectD3D;


/**
 * Esta clase representa un componente compuesto en la vista de Modulos y la
 * vista de Deployment. 
 * 
 * @author Juan Feldman
 *
 */
public class GeonClusterMD extends GeonSimpleMD {

	/**
	 * Instancia el GeonClusterMD.
	 * @param componentName Nombre del componente.
	 * @param abstractor Abstractor del container.
	 */
	public GeonClusterMD(String componentName, Abstractor abstractor) {
		super(componentName, abstractor);
	}

    /**
     * Creo la imagen para el componente.
     * @param geonAbstractor Abstractor del Cluster. 
     */
    public void createImage(Abstractor geonAbstractor) {
    	if (geonAbstractor.getGeonName() != null) {
		    Shape3D geonShape = GeonBuilderSur.instance().buildGeonByName(geonAbstractor.getGeonName());
		    geonShape.setAppearance(getAppearance());
		    J3DSimpleComponent shapeContainer = new J3DSimpleComponent(geonShape);
		    setShapeContainer(shapeContainer);
		    this.addComponent(shapeContainer);
	
			// Le seteo el texto al Geon
			setBoundsAutoCompute(false);
			setBounds(new BoundingBox(new Point3f(-0.2f, -0.2f, -0.2f), new Point3f(0.2f, 0.2f, 0.2f)));
			setCuerpo(new LabeledObjectD3D(this, getName(), new Point3f(-1.5f, 0.0f, 4.0f)));
	
		    // Agrego el evento del click (para poder seleccionar componentes 
			// de la arquitectura)
	        this.addListener(new MouseClickedEventAdapter(GeonEventClick.instance()));
	        this.setMouseEventPropagatable(true);
    	}
    }
	
    /**
     * Retorna la visibilidad del Cluster menos el label.
	 * @return boolean Visibildad.
     */
    public boolean isVisiblePart() {
		if (getShapeContainer() != null)
			return getShapeContainer().isVisible();
		return false;
	}

    /**
     * Setea la visibilidad del Cluster menos el label.
	 * @param visible Visibilidad.
	 */
	public void setVisiblePart(boolean visible) {
		if (getShapeContainer() != null)
			getShapeContainer().setVisible(visible);
	}
	
}
