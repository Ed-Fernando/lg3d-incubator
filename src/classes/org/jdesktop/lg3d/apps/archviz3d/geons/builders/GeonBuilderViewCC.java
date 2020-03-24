package org.jdesktop.lg3d.apps.archviz3d.geons.builders;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;

import org.jdesktop.lg3d.apps.archviz3d.PlEnginePool;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ArqComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ArqFactory;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.Configuration;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractionLevel;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorConfigurationCC;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderViews;
import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventClick;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameButton;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.LayoutStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.graph.DepthFirstLayout3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.graph.MultipleDimension3D;
import org.jdesktop.lg3d.apps.archviz3d.views.BuitlStratWithBuilder;
import org.jdesktop.lg3d.apps.archviz3d.views.ConfigurationBuild3D;
import org.jdesktop.lg3d.apps.archviz3d.xml.CAndCMapHandler;


/**
 * Esta clase se encarga de construir la vista de C&C para las arquitecturas.
 * 
 * @author Juan Feldman
 *
 */
public class GeonBuilderViewCC implements Runnable {
	/** Menu de la aplicacion.*/ 
	protected GeonMenu geonMenu = null;
	/** Vista a construir.*/
	protected J3DCompositeComponent view = null;
	/** Nombre de la arquitectura a construir.*/
	protected String architectureName = null;
	/** Contenedor de la vista.*/
	protected Component3D viewContainer = null;
	/** Nombre de la vista.*/
	protected String viewName = null;
	/** Clase llamadora. Se utiliza para notificarle cuando se termina la
	 * construccion de la vista.*/
	protected GeonBuilderViews caller = null;
	/** Log de mensajes.*/
	protected Logger logger = Logger.getLogger("lg.ArchViz3D");
	

	/**
	 * Se intancia el constructor de vistas de C&C.
	 * @param geonMenu Menu de la aplicacion.
	 * @param architectureName Nombre de la arquitectura a construir.
	 * @param viewContainer Contenedor de la vista.
	 * @param viewName Nombre de la vista.
	 * @param caller Clase llamadora.
	 */
	public GeonBuilderViewCC(GeonMenu geonMenu, String architectureName, Component3D viewContainer, String viewName, GeonBuilderViews caller) {
		this.geonMenu = geonMenu;
		this.architectureName = architectureName;
		this.viewContainer = viewContainer;
		this.viewName = viewName;
		this.caller = caller;
	}

	/**
	 * Crea la vista de C&C para cada una de las arquitecturas agregadas.
	 */
	public void createView() {
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * Crea la vista de C&C.
	 */
	public void run() {
		PlEnginePool.instance().initialize();
		
		// Path al directorio donde se guardan los datos de la arquitectura
		String pathArchitectureResources = System.getProperty("lg.resourcedir") + "archviz3d/architectures/" + architectureName + "/";

		// Levanto el Arquitectural Mapping
		ArqFactory.instance().loadMap(pathArchitectureResources);

		// Levanto todos los files tex.pl que describen la arquitectura
		ArqComponent bankGlobal = new Configuration(
				System.getProperty("lg.appcodebase") + "/ArchViz3D.jar!/resources/architectures/" + 
				architectureName + "/" + architectureName + ".tex.pl");

		LayoutStrategy3D layout;
		layout = new DepthFirstLayout3D(6);
		((MultipleDimension3D) layout).setRadiousIni(4);

		ConfigurationBuild3D sb = new ConfigurationBuild3D(pathArchitectureResources + "ComponentAndConnectorMappings.xml");

		// Creo las Abstracciones
		AbstractorCC abs = new AbstractorConfigurationCC(bankGlobal);
		abs.setAbstractionLevel(new AbstractionLevel(new String[] {
				"Configuration0", "Configuration1", "Configuration2",
		"Module" }));
		abs.setAbstractionLevel(4);

		// Creo la vista
		BuitlStratWithBuilder bs = new BuitlStratWithBuilder(sb, pathArchitectureResources + "ComponentAndConnectorMappings.xml");

		view = new J3DCompositeComponent(abs, bs);

		abs.setComponentView(view);

		// Obtengo la escala y la traslacion de la vista
		String pathMapping = pathArchitectureResources + "ComponentAndConnectorMappings.xml";
		try {
			CAndCMapHandler.getMap(new File(pathMapping));
			float scale = CAndCMapHandler.getArchitectureScale(new File(pathMapping));
			Vector3f translation = CAndCMapHandler.getArchitectureTranslation(new File(pathMapping));
			view.setName(CAndCMapHandler.getArchitectureName(new File(pathMapping)));

			// Escalo y centro la vista
			view.setScale(scale);
			view.setTranslation(translation);
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
		GeonFrameButton buttonViewName = new GeonFrameButton("views/" + viewName.toLowerCase() + ".png", new Point3f(0.0f, -0.1f, 0.05f), 0.08f, 0.013f);

		// Agrego al container la vista y su label
        viewContainer.addChild(view);
		viewContainer.addChild(buttonViewName);
		viewContainer.setName(viewName);

		logger.log(Level.INFO, "FINISH VIEW CREATION: " + viewName);
		
		caller.endCreateView();
	}
	
}

