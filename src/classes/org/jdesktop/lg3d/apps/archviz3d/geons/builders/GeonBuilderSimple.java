package org.jdesktop.lg3d.apps.archviz3d.geons.builders;

import java.util.Iterator;
import java.util.Observable;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.Dispatcher;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ZEvent;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonEvent;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonSimple;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderSur;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonRepaired;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DSimpleComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.SphericalLayout3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.objects.LabeledObject3D;


/**
 * Esta clase se encarga de Construir Geones Simples. 
 * La construcion se realiza en el metodo build donde se recibe 
 * como parametro un J3DComponent (el Geon a construir), al cual
 * se le agregaran la imagen para dicho Geon (de acuerdo al 
 * nombre que posea el J3DComponent para el geon) y los eventos
 * para dicho Geon.   
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public class GeonBuilderSimple extends BuildStrategy3D {

 	/**
 	 * Construye el Geon que se recibe como parametro.
 	 * @param client Geon a construir.
 	 */
	public void build(J3DComponent client) {
    	GeonSimple geon = (GeonSimple)client;
    	
		// Creo el Geon para el componente de la arquitectura, le seteo la apariencia
		// y lo escalo
		Shape3D geonShape = GeonBuilderSur.instance().buildGeonByName(geon.getGeonName());
		geonShape.setAppearance(geon.getAppearance());
		J3DSimpleComponent containerGeonShape = new J3DSimpleComponent(geonShape);
		containerGeonShape.setScale(0.14f);
		geon.setShapeContainer(containerGeonShape);

    	// Eventos
    	J3DCompositeComponent compositeEvents = buildEvents(geon); 
    	compositeEvents.setScale(0.5f);
    	compositeEvents.setTranslation(0.0f,0.0f,0.1f);

    	// Geon y Eventos 
    	J3DCompositeComponent compositeGeon = new J3DCompositeComponent();
    	compositeGeon.addComponent(containerGeonShape);
    	compositeGeon.addComponent(compositeEvents);
    	
    	//Reubicamos el componente dentro de la visualizacion
    	GeonRepaired.instance().repairComponent(geon.getName(), geon, compositeGeon);
 	}
 	
	/**
	 * Construye los eventos para el Geon que se recibe como parametro.
	 * @param client Geon al cual se le agregaran los eventos.
	 */
 	public J3DCompositeComponent buildEvents(J3DComponent client) {
		GeonAbstract geon = (GeonAbstract)client;
    	J3DCompositeComponent compositeEvents = new J3DCompositeComponent();
    	
    	// Agrego los eventos del componente de la arquitectura al Geon
    	AbstractorCC abstractor =(AbstractorCC)client.getSubject();
    	Dispatcher disp = Dispatcher.instance();
    	for (Iterator i = abstractor.getComponentsIterator(); i.hasNext(); ) {
    		String eventName = (String)i.next();
    		
    		// Creo la apariencia para el evento
    		Appearance appearance = Factory3D.instance().createAppearance(geon.getEventColor(eventName));
    		
    		// Creo el Geon para el evento
    		Shape3D prop = GeonBuilderSur.instance().buildGeonByName(geon.getGeonEventName(eventName));
    		prop.setAppearance(appearance);
    		
    		// Agrego el Geon nuevo que representa al evento, con los eventos creados anteriormente
    		GeonEvent event = new GeonEvent(client.getSubject(), prop, eventName);
    		event.setScale(0.1f);

    		disp.addObserver((Observable)abstractor, event, ZEvent.Z_ALL,false);
    		compositeEvents.addComponent(new LabeledObject3D(event, eventName, new Point3f(1.0f,0.0f,0.0f), new Color3f(0.6f, 0.04f, 0.04f), Factory3D.rubberBlack));
    	}

    	// Seteo el layout de los eventos
    	SphericalLayout3D layout = new SphericalLayout3D();
    	layout.setComplexity(abstractor.getComponents().size());
    	layout.setRadius(((GeonSimple)client).getRadius()*2.3f);
    	compositeEvents.setLayout(layout);

    	return compositeEvents;
 	}
 	
}