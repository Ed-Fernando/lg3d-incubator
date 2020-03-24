package org.jdesktop.lg3d.apps.archviz3d.geons.main;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderViews;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonAnimation;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonAnimator;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonSplashScreen;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonWaitMessage;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuStateInitial;
import org.jdesktop.lg3d.scenemanager.utils.background.Background;
import org.jdesktop.lg3d.scenemanager.utils.background.LayeredImageBackground;
import org.jdesktop.lg3d.scenemanager.utils.event.BackgroundChangeRequestEvent;
import org.jdesktop.lg3d.scenemanager.utils.event.ScreenResolutionChangedEvent;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.ModelLoader;
import org.jdesktop.lg3d.wg.Thumbnail;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;


import java.io.File;
import java.net.URL;
import java.util.Vector;

/**
 * Clase principal de la aplicacion.
 * 
 * @author Juan Feldman
 *
 */
public class ArchViz3D extends Frame3D {
	/** Contenedores de las tres vistas de una arquitectura.*/
	private GeonAnimation containerViews1;
	private GeonAnimation containersView2;

	/** Encargado de construir las vistas de una arquitectura.*/
	private GeonBuilderViews geonBuilderViews;

	/** Mensaje de espera (para cuando se crea una arquitectura nueva).*/
	private GeonWaitMessage waitMessage = null;
	
	/**
	 * Instancia la aplicacion.
	 */
	public ArchViz3D() {
		// Seteo el cursor
		this.setCursor(Cursor3D.DEFAULT_CURSOR);

		// Creo los contenedores de las vistas
		containerViews1 = new GeonAnimation();
		containersView2 = new GeonAnimation();

		// Creo el menu
		final GeonMenu geonMenu = new GeonMenu(this);
		GeonAnimator.instance().setGeonMenu(geonMenu);

		// Creo el constructor de vistas
		geonBuilderViews = new GeonBuilderViews(this, geonMenu);
		
		// Creo el mensaje de espera
		waitMessage = new GeonWaitMessage("wait-message.png", 0.1f, 0.05f);
		
		// Agrego el listener para reubicar el menu cuando se cambia 
		// el tamaño de la pantalla 
		LgEventConnector.getLgEventConnector().addListener(
                LgEventSource.ALL_SOURCES,
                new LgEventListener() {
                    public void processEvent(final LgEvent event) {
                        ScreenResolutionChangedEvent srce = (ScreenResolutionChangedEvent)event;
                        geonMenu.setTranslation(-srce.getWidth()/2 + 0.14f, 0f, 0f);  
                    }
                    @SuppressWarnings("unchecked")
					public Class<LgEvent>[] getTargetEventClasses() {
                        return new Class[] {ScreenResolutionChangedEvent.class};
                    }
                });
                
		// Seteo el Background
		try {
			Background bg = new LayeredImageBackground(new URL[] { getClass()
					.getResource("/resources/images/background.png") },
					new float[][] { { 1.0f, 0.0f, 1.0f } });
			this.postEvent(new BackgroundChangeRequestEvent(bg));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Agrego el Thumbnail
		Thumbnail thumbnail = new Thumbnail();
		try {
			ModelLoader model = null;
			try {
				model = new ModelLoader(System.getProperty("lg.resourcedir")
						+ "archviz3d/images", "thumbnail.3ds", Class
						.forName("org.opentools.java3d.ThreeDSLoader"));
				model.setRotationAxis(1, 0, 0);
				model.setRotationAngle((float) -Math.PI / 2);
				model.resize(new Vector3f(0f, 0.0f, 0.0f), 0.01f);
			} catch (ClassNotFoundException cnfe) {
				cnfe.printStackTrace();
			}
			thumbnail.addChild(model);

			this.setThumbnail(thumbnail);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Creo el Splash
		GeonSplashScreen geonSplash = new GeonSplashScreen();

		// Seteo la visibilidad del Splash
		geonSplash.setVisible(false);

		// Agrego los componentes al frame
		this.addChild(geonMenu);
		this.addChild(containerViews1);
		this.addChild(containersView2);
		this.addChild(geonSplash);

		// Muestro la aplicacion
		this.changeEnabled(true);
		this.changeVisible(true);
		
		// Comienzo la animacion del Splash 
		geonSplash.nextStep(1);
		
		// Se inicializa el menu
		geonMenu.changeState(new GeonMenuStateInitial(geonMenu));
	}

/************************************************************************************************************************/	
/*												METODOS GENERALES														*/	
/************************************************************************************************************************/	
	/**
	 * Retorna la cantidad de arquitecturas que hay en la pantalla.
	 * @return int Cantidad de arquitecturas que hay en la pantalla.
	 */
	public int getNumberArchitectures() {
		if (containersView2.getComponent() != null)
			return 2;
		else if (containerViews1.getComponent() != null)
			return 1;
		return 0;
	}
	
	/**
	 * Retorna el contenedor de una arquitectura (tres vistas).
	 * @param architectureNumber Numero de arquitectura cuyo contenedor se desea.
	 * @return GeonAnimation Contenedor de una arquitectura.
	 */
	public GeonAnimation getArchitectureContainer(int architectureNumber) {
		if (architectureNumber == 1)
			return containerViews1;
		else 
			return containersView2;
	}

	/**
	 * Muestra la arquitectura seleccionada por el usuario.
	 * @param architectureNameSelected Nombre de la arquitectura a  
	 * visualizar.
	 */
	public void showView(String architectureNameSelected) {
		this.addChild(waitMessage);
		
		geonBuilderViews.initialize();
		geonBuilderViews.addArchitecture(architectureNameSelected, 1f, new Point3f(0.01f, 0f, 0f), containerViews1);
		geonBuilderViews.createViews();
	}
	
	/**
	 * Muestra las dos arquitecturas a comparar.
	 * @param architectureName1Selected Nombre de la primer arquitectura
	 * a comparar.
	 * @param architectureName2Selected Nombre de la segunda arquitectura
	 * a comparar.
	 */
	public void showView(String architectureName1Selected, String architectureName2Selected) {
		this.addChild(waitMessage);
		
		geonBuilderViews.initialize();
		geonBuilderViews.addArchitecture(architectureName1Selected, 0.6f, new Point3f(-0.05f, 0.05f, 0f), containerViews1);
		geonBuilderViews.addArchitecture(architectureName2Selected, 0.6f, new Point3f(0.05f, -0.03f, 0f), containersView2);
		geonBuilderViews.createViews();
	}

	/**
	 * Este metodo se llama cuando se termina la construccion de una 
	 * arquitectura (tres vistas). 
	 */
	public void detachWaitMessage() {
		waitMessage.detach();
	}
	
	/**
	 * Retorna los nombres de las arquitecturas que pueden visualizarse.
	 * @return Vector Nombres de las arquitecturas que pueden visualizarse. 
	 */
	public static Vector<String> getArchitecturesNames() { 
		Vector<String> architecturesNames = new Vector<String>(); 
		try {
			File file = new File(System.getProperty("lg.resourcedir") + "archviz3d/architectures");
			File[] files = file.listFiles();
			
			for (int i = 0; i < files.length; i++) 
				if (files[i].isDirectory()) 
					architecturesNames.add(files[i].getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return architecturesNames; 
	}
	
	/**
	 * Metodo Principal: inicia la Aplicacion.
	 * @param args Argumentos de la Aplicacion (ninguno).
	 */
	public static void main(String[] args) {
		new ArchViz3D();
	}
	
}