package org.jdesktop.lg3d.apps.archviz3d.geons;

import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.BoundingBox;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;

import org.jdesktop.lg3d.apps.archviz3d.abstractors.Abstractor;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonSimple;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderSur;
import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventClick;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DSimpleComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.objects.LabeledObjectD3D;


/**
 * Esta clase representa un Geon en la vista de Modulos y la
 * vista de Deployment. 
 * Un geon en la vista de Modulos representa a un modulo. 
 * Un geon en la vista de Deployment representa a un componente
 * de software o de hardware.
 * 
 * @author Juan Feldman
 *
 */
public class GeonSimpleMD extends GeonSimple {
	/**
	 * Instancia el GeonSimpleMD. 
	 * @param componentName Nombre del componente que representa esta clase.
	 * @param abstractor Abstractor del componente.
	 */
    public GeonSimpleMD (String componentName, Abstractor abstractor) {
    	super(abstractor, null, componentName);
    	abstractor.setComponentView(this);
		
    	// Obtengo el abstractor
    	Abstractor geonAbstractor = (Abstractor)abstractor;
    	
	    // Creo la imagen para el Geon
	    createImage(geonAbstractor);
	}
    
    /**
     * Crea la imagen para el componente.
	 * @param geonAbstractor Abstractor del componente.
     */
    public void createImage(Abstractor geonAbstractor) {
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
	
	/**
	 * Setea la transparencia del geon.
	 * @param transparentValue Valor de la transparencia del geon.
	 */
	public void setTransparent(float transparentValue) {
		getAppearance().setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.FASTEST, transparentValue));
	}
	
}