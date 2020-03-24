package org.jdesktop.lg3d.apps.archviz3d.geons.builders;

import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.vecmath.Point3f;

import org.jdesktop.lg3d.wg.Component3D;

import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderViewCC;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderViewMD;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonAnimation;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonNumerator;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonWheel;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrame;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameWheelLayoutSelection;
import org.jdesktop.lg3d.apps.archviz3d.geons.main.ArchViz3D;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuStateViewVisible;


/**
 * Esta clase se encarga de construir las tres vistas 
 * (Componentes y Conectores, Modulos y Deployment) para una
 * arquitectura.
 * 
 * @author Juan Feldman
 *
 */
public class GeonBuilderViews implements Runnable {
	/** Instancia de la clase Main.*/ 
	protected ArchViz3D archviz = null;
	/** Menu de la aplicacion.*/ 
	protected GeonMenu geonMenu = null;
	/** Nombres de las arquitecturas que se desean visualizar.*/
	protected Vector<String> architecturesNames = null;
	/** Escalas de las vistas a construir.*/
	protected Vector<Float> scales = null;
	/** Traslaciones de la vistas a construir.*/
	protected Vector<Point3f> translations = null;
	/** Contenedores de las vistas a construir.*/
	protected Vector<GeonAnimation> containersView = null;
	/** Indice de la arquitectura a construir.*/
	protected int index;
	/** Cantidad de vistas construidas.*/
	protected int numberViewsCreated;
	/** Loger de mensajes.*/
	protected Logger logger = Logger.getLogger("lg.ArchViz3D");

	/** Contantes para identificar a las vistas.*/
	public static final String VIEW_CANDC = "CAndC";
	public static final String VIEW_MODULE = "Module";
	public static final String VIEW_DEPLOYMENT = "Deployment";
	public static final int VIEWS_NUMBER = 3;
	
	/**
	 * Se intancia el constructor de vistas.
	 * @param archviz Instancia de la clase Main.
	 * @param geonMenu Menu de la aplicacion.
	 */
	public GeonBuilderViews(ArchViz3D archviz, GeonMenu geonMenu) {
		this.archviz = archviz;  
		this.geonMenu = geonMenu;  
		this.architecturesNames = new Vector<String>();  
		this.scales = new Vector<Float>();
		this.translations = new Vector<Point3f>();
		this.containersView = new Vector<GeonAnimation>();
		initialize();
	}
	
	/**
	 * Inicializa los atributos.
	 */
	public void initialize() {
		architecturesNames.removeAllElements();
		scales.removeAllElements();
		translations.removeAllElements();
		containersView.removeAllElements();
		index = 0;
	}

	/**
	 * Agrega una arquitectura a la cual se le construiran las vistas.
	 *  
	 * @param architectureName Nombre de la arquitectura a construir. 
	 * @param scale Escala de la vista a construir.
	 * @param translation Traslacion de la vista a construir.
	 * @param containerView Contenedor de la vista a construir.
	 */
	public void addArchitecture(String architectureName, float scale, Point3f translation, GeonAnimation containerView) {
		architecturesNames.add(architectureName);  
		scales.add(new Float(scale));
		translations.add(translation);
		containersView.add(containerView);
	}

	/**
	 * Crea las vistas para las arquitecturas agregadas.
	 */
	public void createViews() {
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * Este metodo se llama cuando se termino de construir las tres vistas de una arquitectura.
	 */
	protected void endView() {
		index++;
		// Hay mas arquitecturas por construir
		if (index < architecturesNames.size()) {
			Thread thread = new Thread(this);
			thread.start();
		}
		// Se termino de construir la arquitectura
		else {
			for (Enumeration<GeonAnimation> enume = containersView.elements(); enume.hasMoreElements(); ) 
				enume.nextElement().setVisible(true);
			
			// Se actualiza el menu
			geonMenu.changeState(new GeonMenuStateViewVisible(geonMenu));
			// Se eliminar el mensaje de espera
			archviz.detachWaitMessage();
		}
	}
	
	/**
	 * Ejecuta la construccion de las vistas para una arquitectura.
	 */
	public void run() {
		// Obtengo el nombre de la arquitectura a construir
		String architectureName = architecturesNames.elementAt(index);

		// Creo la vista de C&C
		GeonNumerator.instance().initialize();
		Component3D cAndCContainer = new Component3D();
		GeonBuilderViewCC geonViewBuilder = new GeonBuilderViewCC(geonMenu, architectureName, cAndCContainer, VIEW_CANDC, this);
		geonViewBuilder.createView();
		
		// Creo la vista de Modulos
		Component3D moduleContainer = new Component3D();
		geonViewBuilder = new GeonBuilderViewMD(geonMenu, architectureName, moduleContainer, VIEW_MODULE, this);
		geonViewBuilder.createView();
		
		// Creo la vista de Deployment
		Component3D deploymentContainer = new Component3D();
		geonViewBuilder = new GeonBuilderViewMD(geonMenu, architectureName, deploymentContainer, VIEW_DEPLOYMENT, this);
		geonViewBuilder.createView();
		
		// Agrego los contenedores de cada una de las tres vistas en el contenedor geonWheel 
		containersView.elementAt(index).setTranslation(translations.elementAt(index).x, translations.elementAt(index).y, translations.elementAt(index).z);
		containersView.elementAt(index).setScale(scales.elementAt(index).floatValue());
		GeonWheel geonWheel = new GeonWheel(geonMenu);
		geonWheel.addChild(cAndCContainer);
		geonWheel.addChild(moduleContainer);
		geonWheel.addChild(deploymentContainer);
		
		// Creo el frame de seleccion de layouts
		GeonFrame geonFrame = new GeonFrameWheelLayoutSelection(geonWheel, archviz);
		geonWheel.addFrame(geonFrame);
		
		// Agrego el contenedor geonWheel en su contenedor
		containersView.elementAt(index).setComponent(geonWheel);
		containersView.elementAt(index).setVisible(false);
		
		// Espero que finalice la construccion de las tres vistas
		waitConstruction();
		
		endView();
	}
	
	/**
	 * Espera a que finalize la construccion de las tres vistas.
	 */
	public synchronized void waitConstruction() {
		try {
			wait();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Notifica la construccion de las tres vistas.
	 */
	public synchronized void endCreateView() {
		numberViewsCreated++;
		if (numberViewsCreated == VIEWS_NUMBER) {
			numberViewsCreated = 0;
			logger.log(Level.INFO, "FINISH ARCHITECTURE: " + architecturesNames.elementAt(index));
			notifyAll();
		}
	}
		
}

