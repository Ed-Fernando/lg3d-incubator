package org.jdesktop.lg3d.apps.archviz3d.geons.components;

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.vecmath.Color3f;

import org.candc.Ccmap;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ArqComponent;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonSearcher;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.apps.archviz3d.xml.CAndCMapHandler;


/**
 * Esta clase se encarga de representar una propiedad de la arquitectura 
 * a traves de la visualizacion de los colores de sus componentes.
 *  
 * @author Juan Feldman
 *
 */
public class GeonPropertiesPainter {
	/** Vista de la arquitectura a visualizar.*/
	private J3DCompositeComponent view;
	/** Propiedad a visualizar.*/
	private String property;

	/**
	 * Se instancia el manager de propiedades. 
	 * @param view Vista de la arquitectura a visualizar. 
	 * @param property Propiedad a visualizar.
	 */
	public GeonPropertiesPainter(J3DCompositeComponent view, String property) {
		this.view = view;
		this.property = property; 
	}
	
	/**
	 * Pinta los componentes de la arquitectura segun los valores
	 * que posean para la propiedad que se esta visualizando. 
	 */
	public void paint() {
		GeonSearcher geonSearcher = new GeonSearcher(view);
		
		String pathMapping =  System.getProperty("lg.resourcedir") 
		+ "archviz3d/architectures/" + view.getName() + "/ComponentAndConnectorMappings.xml";
		
		Map<String, Ccmap> mappings = null;
		try {
			mappings = CAndCMapHandler.getMap(new File(pathMapping));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		for (Iterator<String> iterator = mappings.keySet().iterator(); iterator.hasNext();) {
			String componentName = iterator.next();
			
			GeonAbstract component = geonSearcher.searchComponent(componentName);
			if (component != null) {
				String value = component.getProperties().get(property);
				
				if (!view.getName().equals(componentName)) {
					Color3f color = getColor(value);
					component.updateImage(component.getGeonName(), color);
	
					Vector event = ((ArqComponent)((AbstractorCC)component.getSubject()).getSubject()).getEvents();
					for (Enumeration enumeEvent = event.elements(); enumeEvent.hasMoreElements();) {
						String eventName = (String)enumeEvent.nextElement();
						GeonAbstract componentEvent = geonSearcher.searchComponentEvent(componentName, eventName);
						if (componentEvent != null)
							componentEvent.updateImage(componentEvent.getGeonName(), color);
					}
				}
			}
		}
	}
	
	/**
	 * Retorna el color asociado a un valor de la
	 * propiedad que se esta visualizando.
	 * 
	 * @param value Valor de la propiedad.
	 * @return Color3f Color para la propiedad.
	 */
	private Color3f getColor(String value) {
		if (value == null) 
			return new Color3f(1, 1, 1);
		
		float floatValue = Float.parseFloat(value);
		
		// Ptos: x1= 0 y1= 0.5 ; x2= 3 y2= 1
		// Recta: y = x/6 + 0.5
		if ((floatValue >= 0) && (floatValue <= 3))
			return new Color3f(0.166f*floatValue + 0.5f, 0, 0);

		// Ptos: x1= 3 y1= 0.5 ; x2= 6 y2= 1
		// Recta: y = x/6
		if ((floatValue > 3) && (floatValue <= 6))
			return new Color3f(0.166f*floatValue, 0.166f*floatValue, 0);
		
		// Ptos: x1= 6 y1= 0.5 ; x2= 10 y2= 1
		// Recta: y = x/8 - 0.25
		if ((floatValue > 6) && (floatValue <= 10)) 
			return new Color3f(0, 0.125f*floatValue - 0.25f, 0);
	
		return new Color3f(1, 1, 1);
	}
	
}
