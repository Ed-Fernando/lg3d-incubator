package org.jdesktop.lg3d.apps.archviz3d.geons.menu;

import javax.vecmath.Point3f;

import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;


import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventClick;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameManager;
import org.jdesktop.lg3d.apps.archviz3d.geons.main.ArchViz3D;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuButton;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuButtonToggle;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommandClearView;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommandCompare;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommandLessAbstraction;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommandMapping;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommandMenu;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommandMoreAbstraction;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommandPropertiesTable;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommandPropertiesView;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommandShowHideCluster;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommandUseCase;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommandView;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuStateAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuStateViewVisible;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;


/**
 * Esta clase representa el menu de la aplicacion.
 *  
 * @author Juan Feldman
 *
 */
public class GeonMenu extends Component3D {
	/** Boton principal del Menu.*/
	protected GeonMenuButton menuButton = null;
	/** Boton para visualizar la vista.*/
	protected GeonMenuButton viewButton = null;
	/** Boton para limpiar la pantalla.*/
	protected GeonMenuButton clearButton = null;
	/** Boton para setear la visibilidad de los wrappers de la vista.*/
	protected GeonMenuButtonToggle showHideCluster = null;
	/** Boton para comparar las vistas.*/
	protected GeonMenuButton compareButton = null;
	/** Boton para representar la primer vista en una comparacion (para las abstracciones).*/
	protected GeonMenuButtonToggle viewOneButton = null;
	/** Boton para representar la segunda vista en una comparacion (para las abstracciones).*/
	protected GeonMenuButtonToggle viewTwoButton = null;
	/** Boton para el mas de las abstracciones.*/
	protected GeonMenuButton moreAbstractionButton = null;
	/** Boton para el menos de las abstracciones.*/
	protected GeonMenuButton lessAbstractionButton = null;
	/** Boton para el mapping de los Geones.*/
	protected GeonMenuButtonToggle geonMappingButton = null;
	/** Boton para las propiedades de los Geones.*/
	protected GeonMenuButtonToggle geonPropertiesTableButton = null;
	/** Boton para los casos de uso de la arquitectura.*/
	protected GeonMenuButton geonPropertiesViewButton = null;
	/** Boton para los casos de uso de la arquitectura.*/
	protected GeonMenuButton useCaseButton = null;

	/** Clase main de la aplicacion.*/
	protected ArchViz3D archviz = null;
	
	/** Estado del menu.*/
	protected GeonMenuStateAbstract state = null;

	/**
	 * Instancia el GeonMenu. 
	 * @param archviz Clase main de la aplicacion.
	 */
	public GeonMenu (ArchViz3D archviz) {
		this.archviz = archviz;
		createMenu();
	}
	
	/**
	 * Crea el menu de la aplicacion.
	 */
	private void createMenu() {
		// Contenedor del menu
		Component3D compMenu = new Component3D();
		// Contenedor de los botones Uno y Dos para las vistas
		Component3D compMenuOneTwo = new Component3D();
		// Contenedor de los botones Mapping, Properties y VisibilityWrapper
		Component3D compMenuMapProVis = new Component3D();
		// Creo el manager de frames
		GeonFrameManager geonFrameManager = new GeonFrameManager(archviz, this);

		float yInic = 0.02f;
		float gap = 0.02f;
		menuButton = new GeonMenuButton("menu.png", "Menu", new Point3f(0.0f, yInic, 0));
		viewButton = new GeonMenuButton("view.png", "View", new Point3f(0.005f, yInic - gap * 1, 0));
		compareButton = new GeonMenuButton("compare.png", "Compare", new Point3f(0.005f, yInic - gap * 2, 0));
		viewOneButton = new GeonMenuButtonToggle(compMenuOneTwo, "one.png", "Arch One", new Point3f(0.022f, yInic - gap * 2 + 0.00375f, 0), 0.01f, 0.007f, 0.003f);
		viewTwoButton = new GeonMenuButtonToggle(compMenuOneTwo, "two.png", "Arch Two", new Point3f(0.022f, yInic - gap * 2 - 0.00375f, 0), 0.01f, 0.007f, 0.003f);
		clearButton = new GeonMenuButton("clear.png", "Clear", new Point3f(0.005f, yInic - gap * 3, 0));
		showHideCluster = new GeonMenuButtonToggle(compMenuMapProVis, "cut.png", "Show/Hide Cluster", new Point3f(0.005f, yInic - gap * 4, 0));
		moreAbstractionButton = new GeonMenuButton("more.png", "More Abstraction", new Point3f(0.005f, yInic - gap * 5, 0));
		lessAbstractionButton = new GeonMenuButton("less.png", "Less Abstraction", new Point3f(0.030f, yInic - gap * 5, 0));
		geonMappingButton = new GeonMenuButtonToggle(compMenuMapProVis, "mapping.png", "Mapping", new Point3f(0.005f, yInic - gap * 6, 0));
		geonPropertiesTableButton = new GeonMenuButtonToggle(compMenuMapProVis, "properties-table.png", "Properties Values", new Point3f(0.005f, yInic - gap * 7, 0));
		geonPropertiesViewButton = new GeonMenuButton("properties-view.png", "Property View", new Point3f(0.030f, yInic - gap * 7, 0));
		useCaseButton = new GeonMenuButton("usecase.png", "Use Case", new Point3f(0.005f, yInic - gap * 8, 0));

		// Agrego el listener para el evento del click a los botones del menu
		menuButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				menuButton_click();
			}
		}));
		viewButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				viewButton_click();
			}
		}));
		clearButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				clearButton_click();
			}
		}));
		compareButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				compareButton_click();
			}
		}));
		viewOneButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				viewOneButton_click();
			}
		}));
		viewTwoButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				viewTwoButton_click();
			}
		}));
		moreAbstractionButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				moreAbstractionButton_click();
			}
		}));
		lessAbstractionButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				lessAbstractionButton_click();
			}
		}));
		geonPropertiesViewButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				geonPropertiesViewButton_click();
			}
		}));
		useCaseButton.addListener(new MouseClickedEventAdapter(new ActionNoArg() {
			public void performAction(LgEventSource source) {
				useCaseButton_click();
			}
		}));
		
		// Seteo los comandos a los botones del menu
		menuButton.setCommand(new GeonMenuCommandMenu(this, geonFrameManager));
		viewButton.setCommand(new GeonMenuCommandView(this, geonFrameManager));
		clearButton.setCommand(new GeonMenuCommandClearView(this, geonFrameManager, new Object[]{archviz}));
		showHideCluster.setCommand(new GeonMenuCommandShowHideCluster(this, geonFrameManager));
		compareButton.setCommand(new GeonMenuCommandCompare(this, geonFrameManager));
		moreAbstractionButton.setCommand(new GeonMenuCommandMoreAbstraction(this, geonFrameManager, new Object[]{archviz}));
		lessAbstractionButton.setCommand(new GeonMenuCommandLessAbstraction(this, geonFrameManager, new Object[]{archviz}));
		geonMappingButton.setCommand(new GeonMenuCommandMapping(this, geonFrameManager));
		geonPropertiesTableButton.setCommand(new GeonMenuCommandPropertiesTable(this, geonFrameManager));
		geonPropertiesViewButton.setCommand(new GeonMenuCommandPropertiesView(this, geonFrameManager));
		useCaseButton.setCommand(new GeonMenuCommandUseCase(this, geonFrameManager));

		// Seteo la visibilidad de los botones del menu
		menuButton.setVisible(true);
		viewButton.setVisible(true);
		clearButton.setVisible(true);
		showHideCluster.setVisible(true);
		compareButton.setVisible(true);
		moreAbstractionButton.setVisible(true);
		lessAbstractionButton.setVisible(true);
		geonMappingButton.setVisible(true);
		geonPropertiesTableButton.setVisible(true);
		geonPropertiesViewButton.setVisible(true);
		viewOneButton.setVisible(false);
		viewTwoButton.setVisible(false);
		useCaseButton.setVisible(true);

		// Agrego los componentes al menu y los ubico en la pantalla
		compMenu.addChild(menuButton);
		compMenu.addChild(viewButton);
		compMenu.addChild(clearButton);
		compMenu.addChild(compareButton);
		compMenu.addChild(moreAbstractionButton);
		compMenu.addChild(lessAbstractionButton);
		compMenu.addChild(geonPropertiesViewButton);
		compMenu.addChild(useCaseButton);
		compMenu.addChild(compMenuOneTwo);
		compMenu.addChild(compMenuMapProVis);

		compMenuOneTwo.addChild(viewOneButton);
		compMenuOneTwo.addChild(viewTwoButton);

		compMenuMapProVis.addChild(geonMappingButton);
		compMenuMapProVis.addChild(geonPropertiesTableButton);
		compMenuMapProVis.addChild(showHideCluster);

		this.addChild(compMenu);
		
		// Ubico el menu en la pantalla
		float width = Toolkit3D.getToolkit3D().getScreenWidth() * 0.9f / 2;
		compMenu.setTranslation(-width, 0.07f, 0f);
		
		// Agrego el listener de traslacion al menu
		Factory3D.instance().addListenerTranslation(this);
	}
	
/************************************************************************************************************************/	
/*										METODOS PARA LOS BOTONES MENU													*/	
/************************************************************************************************************************/
	/**
	 * Ejecuta las acciones del boton menu.
	 */
	public void menuButton_click() {
		menuButton.execute();
	}

	/**
	 * Ejecuta las acciones del boton view.
	 */
	public void viewButton_click() {
		viewButton.execute();
	}
	
	/**
	 * Ejecuta las acciones del boton clear.
	 */
	public void clearButton_click() {
		clearButton.execute();
	}
	
	/**
	 * Ejecuta las acciones del boton compare.
	 */
	public void compareButton_click() {
		compareButton.execute();
	}
	
	/**
	 * Ejecuta las acciones del boton ver vista uno.
	 */
	public void viewOneButton_click() {
		// Se debe analizar si se habilita o no el boton usecase 
		changeState(new GeonMenuStateViewVisible(this));
	}

	/**
	 * Ejecuta las acciones del boton ver vista dos.
	 */
	public void viewTwoButton_click() {
		// Se debe analizar si se habilita o no el boton usecase 
		changeState(new GeonMenuStateViewVisible(this));
	}

	/**
	 * Ejecuta las acciones del boton more abstraction.
	 */
	public void moreAbstractionButton_click() {
		moreAbstractionButton.execute();
	}
	
	/**
	 * Ejecuta las acciones del boton less abstraction.
	 */
	public void lessAbstractionButton_click() {
		lessAbstractionButton.execute();
	}
	
	/**
	 * Ejecuta las acciones del boton properties view.
	 */
	public void geonPropertiesViewButton_click() {
		geonPropertiesViewButton.execute();
	}
	
	/**
	 * Ejecuta las acciones del boton use case.
	 */
	public void useCaseButton_click() {
		useCaseButton.execute();
	}
	
	/**
	 * Ejecuta las acciones del boton show hide cluster.
	 * @param geonSelected Geon clickeado por el usuario
	 */
	public void showHideCluster_click(GeonAbstract geonSelected) {
		showHideCluster.getCommand().setParam(geonSelected, 0);
		showHideCluster.execute();
	}

	/**
	 * Ejecuta las acciones del boton mapping de geones.
	 * @param geonSelected Geon clickeado por el usuario.
	 */
	public void geonMappingButton_click(GeonAbstract geonSelected) {
		geonMappingButton.getCommand().setParam(geonSelected, 0);
		geonMappingButton.execute();
	}

	/**
	 * Ejecuta las acciones del boton tabla de propiedades de geones.
	 * @param geonSelected Geon clickeado por el usuario.
	 */
	public void geonPropertiesTableButton_click(GeonAbstract geonSelected) {
		geonPropertiesTableButton.getCommand().setParam(geonSelected, 0);
		geonPropertiesTableButton.execute();
	}

/************************************************************************************************************************/	
/*												METODOS GENERALES														*/	
/************************************************************************************************************************/	
	/**
	 * Se encarga de llamar a la accion que corresponda cuando se clickea sobre
	 * un Geon.
	 * @param geonSelected Geon seleccionado por el usuario.
	 */
	public void handleGeonSelection(GeonAbstract geonSelected) {
		if (geonMappingButton.getClicked())
			geonMappingButton_click(geonSelected);

		else if (geonPropertiesTableButton.getClicked())
			geonPropertiesTableButton_click(geonSelected);

		else if (showHideCluster.getClicked())
			showHideCluster_click(geonSelected);

		GeonEventClick.instance().removeAllSelection();
	}
	
	/**
	 * Cambia el estado del menu.
	 * @param newState Nuevo estado del menu.
	 */
	public void changeState(GeonMenuStateAbstract newState) {
		state = newState;
		state.handle();	
	}
	
	/**
	 * Retorna el numero del boton de seleccion de vistas que esta
	 * clickeado.
	 * @return int Numero del boton de seleccion de vistas que esta
	 * clickeado.
	 */
	public int getViewSelectionButtonNumber() {
		if (viewTwoButton.getClicked())
			return 2;
		else 
			return 1;
	}
}
