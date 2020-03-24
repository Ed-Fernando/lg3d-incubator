package org.jdesktop.lg3d.apps.archviz3d.geons.builders;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.module.Color;
import org.module.Momap;
import org.module.Point;

import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorComponentMD;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorConfigurationMD;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonClusterMD;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonLinkMD;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonSimpleMD;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderViewCC;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderViews;
import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventClick;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameButton;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.xml.ModuleDeploymentMapHandler;


/**
 * Esta clase se encarga de construir la vista de 
 * Modulos y la vista de Deployment para las arquitecturas.
 * 
 * @author Juan Feldman
 *
 */
public class GeonBuilderViewMD extends GeonBuilderViewCC {
	/** Nombre del XML que contiene los datos de la vista a crear.*/
	private String xmlName = null;
	
	/**
	 * Se intancia el constructor de vistas de Modulos y de Deployment.
	 * @param geonMenu Menu de la aplicacion.
	 * @param architectureName Nombre de la arquitectura a construir.
	 * @param viewContainer Contenedor de la vista.
	 * @param viewName Nombre de la vista.
	 * @param caller Clase llamadora.
	 */
	public GeonBuilderViewMD(GeonMenu geonMenu, String architectureName, Component3D viewContainer, String viewName, GeonBuilderViews caller) {
		super(geonMenu, architectureName, viewContainer, viewName, caller);
		this.xmlName = viewName + "Mappings.xml";
	}

	/**
	 * Crea la vista de Modulos o de Deployment.
	 */
	public void run() {
		// Path al directorio donde se guardan los datos de la arquitectura
		String pathArchitectureResources = System.getProperty("lg.resourcedir") + "archviz3d/architectures/" + architectureName + "/";

		// Creo el abstractor para el contenedor de la vista
		AbstractorConfigurationMD abstractorContainer = new AbstractorConfigurationMD(null);
		
		// Creo el contenedor de la vista
		view = new GeonClusterMD(architectureName, abstractorContainer);
		
		// Agrego los componentes a la vista
		try {
			Vector<Momap> xmlMappings = ModuleDeploymentMapHandler.getVector(new File(pathArchitectureResources + xmlName));
			
			// Creo la vista
			for (Iterator<Momap> iterator = xmlMappings.iterator(); iterator.hasNext();) {
				Momap momap = iterator.next();
				
				// Creo el abstractor para el componente
				AbstractorComponentMD abstractor = new AbstractorComponentMD(null);
				
				//Seteo los niveles de abstraccion 
				if (momap.getAbstraction() != null) 
					abstractor.setAbstractionsValues(new boolean[] {
							momap.getAbstraction().getLevel1(), momap.getAbstraction().getLevel2(), 
							momap.getAbstraction().getLevel3(), momap.getAbstraction().getLevel4(),});
				
				//Seteo el color
				if (momap.getColor() != null) {
					Color color = momap.getColor();
					abstractor.setColor(new Color3f(color.getRed().floatValue(), color.getGreen().floatValue(), color.getBlue().floatValue()));
				}
				
				// Seteo el nombre del geon que lo representa
				String geonName = "cube";
				if (momap.getGeonList() != null)
					geonName = momap.getGeonList().getGeonArray(0);
				abstractor.setGeonName(geonName);
				
				// Seteo los datos para el conector
				if (momap.getConnector() != null) {
					Point[] points = momap.getConnector().getPointArray();
					for (int i = 0; i < points.length; i++) 
						abstractor.addPoint(new Point3f(points[i].getX().floatValue(), points[i].getY().floatValue(), points[i].getZ().floatValue()));
					abstractor.setConnectorType(momap.getConnector().getType().intValue());
					abstractor.setWithArrow(momap.getConnector().getArrow());
				}

				//Seteo la lista de geones que pueden representar al componente
				if (momap.getGeonList() != null) 
					abstractor.setGeonsList(momap.getGeonList().getGeonArray());
				 
				//Seteo la lista de propiedades del componente 
				if (momap.getPropertyList() != null) 
					abstractor.setProperties(momap.getPropertyList().getPropertyArray());
				 
				//Seteo la visibilidade del nombre
				abstractor.setNameVisible(momap.getName().getVisible());
				
				// Creo el componente
				GeonAbstract component = null;
				if (momap.getConnector() != null) 
	    			component = new GeonLinkMD(momap.getName().getValue(), abstractor);
		    	else if (momap.getTransparent()) 
		    		component = new GeonClusterMD(momap.getName().getValue(), abstractor);
		    	else
		    		component = new GeonSimpleMD(momap.getName().getValue(), abstractor);
				
				//Seteo la escala
				if (momap.getScale() != null) 
					component.setScale(momap.getScale().floatValue());
				
				//Seteo la rotacion
				if (momap.getRotation() != null) { 
					component.setRotationAxis(1, 0, 0);
					component.setRotationAngle(momap.getRotation().getX().floatValue());

					component.setRotationAxis(0, 1, 0);
					component.setRotationAngle(momap.getRotation().getY().floatValue());

					component.setRotationAxis(0, 0, 1);
					component.setRotationAngle(momap.getRotation().getZ().floatValue());
				}

				//Seteo la traslacion
				if (momap.getTranslation() != null) {
					Point point = momap.getTranslation();
					component.setTranslation(new Vector3f(point.getX().floatValue(), point.getY().floatValue(), point.getZ().floatValue()));
				}
				
				//Seteo la transparencia
				if (momap.getTransparent()) 
					((GeonSimpleMD)component).setTransparent(0.6f);
				
				// Agrego el componente a la vista
				view.addComponent(component);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 

		// Le agrego a la vista el evento del click para los botones
		// Mapping y Properties del menu 
		view.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				if (GeonEventClick.instance().haveSelection())
					geonMenu.handleGeonSelection(GeonEventClick.instance().getGeonSelected());
			}
		}));
		view.setMouseEventPropagatable(true);

		// Creo el label que indica el tipo de vista construido
		GeonFrameButton buttonViewName = new GeonFrameButton("views/" + viewName.toLowerCase() + ".png", new Point3f(0.0f, -0.1f, 0.03f), 0.055f, 0.013f);

		// Agrego al container la vista y su label
		viewContainer.addChild(view);
		viewContainer.addChild(buttonViewName);
		viewContainer.setName(viewName);
		
		logger.log(Level.INFO, "FINISH VIEW CREATION: " + viewName);

		caller.endCreateView();
	}
}


