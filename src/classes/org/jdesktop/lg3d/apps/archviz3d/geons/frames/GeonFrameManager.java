package org.jdesktop.lg3d.apps.archviz3d.geons.frames;

import javax.vecmath.Point3f;

import org.jdesktop.lg3d.wg.Component3D;

import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderViews;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonAnimator;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonPropertiesPainter;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonWheel;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrame;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameArchitectureSelection;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameCompareSelection;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameMapping;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameProperties;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFramePropertiesSelection;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameUseCaseExecution;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameUseCaseSelection;
import org.jdesktop.lg3d.apps.archviz3d.geons.main.ArchViz3D;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommand;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommandAcceptCompare;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuStateViewVisible;
import org.jdesktop.lg3d.apps.archviz3d.geons.usecase.GeonUseCaseManager;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;


/**
 * Esta clase se encarga de manejar los frames de la aplicacion.
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public class GeonFrameManager {
	/** Instancia de la clase main.*/
	private ArchViz3D archviz = null;
	/** Frame para la ejecucion de los casos de uso en la arquitectura 1.*/
	private GeonFrame frameUseCaseExecution1 = null;
	/** Frame para la ejecucion de los casos de uso en la arquitectura 2.*/
	private GeonFrame frameUseCaseExecution2 = null;
	/** Frame para el resto de la funcionalidad.*/
	private GeonFrame geonFrame = null;
	/** Menu de la aplicacion.*/
	private GeonMenu geonMenu = null;
	
	/**
	 * Instancia el GeonFrameManager.
	 * @param archviz Instancia de la clase main.
	 */
	public GeonFrameManager(ArchViz3D archviz, GeonMenu geonMenu) {
		this.archviz = archviz; 
		this.geonMenu = geonMenu; 
	}

/************************************************************************************************************************/	
/*												METODOS PARA EL MENU													*/	
/************************************************************************************************************************/	
	/**
	 * Ejecuta las acciones del boton view.
	 */
	public void viewButton_click() {
		geonFrame = new GeonFrameArchitectureSelection(0.15f, 0.07f, 0.004f, new Point3f(-0.07f, 0.05f, 0.0f), archviz);
		initializeFrame(geonFrame);
	}
	
	/**
	 * Ejecuta las acciones del boton limpiar.
	 */
	public void clearButton_click() {
		if (frameUseCaseExecution1 != null)
			frameUseCaseExecution1.close();		
		
		if (frameUseCaseExecution2 != null)
			frameUseCaseExecution2.close();		

		if (geonFrame != null)
			geonFrame.close();		
	}

	/**
	 * Ejecuta las acciones del boton mostrar/ocultar.
	 * @param geonSelected Geon clickeado por el usuario.
	 */
	public void showHideClusterButton_click(GeonAbstract geonSelected) {
	}

	/**
	 * Ejecuta las acciones del boton comparar.
	 */
	public void compareButton_click() {
		if (frameUseCaseExecution1 != null)
			frameUseCaseExecution1.setTranslation(0.04f, 0.07f, 0.0f);
		
    	geonFrame = new GeonFrameCompareSelection(0.10f, 0.135f, 0.004f, new Point3f(-0.05f, 0.07f, 0.0f), archviz);
		initializeFrame(geonFrame);
	}

	/**
	 * Ejecuta las acciones del boton mas abstracion.
	 * @param viewNumber Numero de la vista seleccionada
	 * por el usuario.
	 */
	public void moreAbstractionButton_click(int viewNumber) {
	}

	/**
	 * Ejecuta las acciones del boton menos abstracion.
	 * @param viewNumber Numero de la vista seleccionada
	 * por el usuario.
	 */
	public void lessAbstractionButton_click(int viewNumber) {
	}

	/**
	 * Ejecuta las acciones del boton mapping de geones.
	 * @param geonSelected Geon clickeado por el usuario.
	 */
	public void geonMappingButton_click(GeonAbstract geonSelected) {
		geonFrame = new GeonFrameMapping(0.12f, 0.10f, 0.004f, new Point3f(-0.05f, 0.07f, 0.0f), geonSelected, archviz);
		initializeFrame(geonFrame);
	}

	/**
	 * Ejecuta las acciones del boton propiedades de geones. 
	 * Se muestra el frame con la tabla de propiedades.
	 * @param geonSelected Geon clickeado por el usuario.
	 */
	public void geonPropertiesTableButton_click(GeonAbstract geonSelected) {
		geonFrame = new GeonFrameProperties(0.12f, 0.10f, 0.004f, new Point3f(-0.05f, 0.07f, 0.0f), geonSelected, archviz);
		initializeFrame(geonFrame);
	}

	/**
	 * Ejecuta las acciones del boton propiedades de geones.
	 * Se muestra el frame de seleccion de propiedades.
	 * @param buttonNumber Numero del boton seleccionado
	 * por el usuario. 
	 */
	public void geonPropertiesViewButton_click(int buttonNumber) {
		// Obtengo la vista de Componentes y Conectores
		J3DCompositeComponent view = getCAndCView(buttonNumber);
		
		// Se crea el frame
		geonFrame = new GeonFramePropertiesSelection(0.12f, 0.10f, 0.004f, new Point3f(-0.05f, 0.07f, 0.0f), view, archviz);
		initializeFrame(geonFrame);
	}

	/**
	 * Ejecuta las acciones del boton use case.
	 * @param buttonNumber Numero del boton seleccionado
	 * por el usuario.
	 */
	public void useCaseButton_click(int buttonNumber) {
		if ((buttonNumber == 2) && (frameUseCaseExecution2 != null)) {
			frameUseCaseExecution2.detach();
			frameUseCaseExecution2 = null;
		}
		
		if ((buttonNumber == 1) && (frameUseCaseExecution1 != null)) {
			frameUseCaseExecution1.detach();
			frameUseCaseExecution1 = null;
		}

		// Se crea el frame
		J3DCompositeComponent view = getCAndCView(buttonNumber);
		GeonUseCaseManager geonUseCaseManager = new GeonUseCaseManager(view);
		geonFrame = new GeonFrameUseCaseSelection(0.08f, 0.08f, 0.004f, new Point3f(-0.05f, 0.07f, 0.0f), geonUseCaseManager, archviz);
		initializeFrame(geonFrame);
	}

/************************************************************************************************************************/	
/*										METODOS GENERALES																*/	
/************************************************************************************************************************/	
	/**
	 * El usuario cierra el frame pasado como parametro.
	 * 
	 * @param geonFrameClosed Frame cerrado por el usuario.
	 */
	public void frameClosed(GeonFrame geonFrameClosed) {
		if ((frameUseCaseExecution1 != null) && frameUseCaseExecution1.equals(geonFrameClosed)) 
			frameUseCaseExecution1 = null;		
		else if ((frameUseCaseExecution2 != null) && frameUseCaseExecution2.equals(geonFrameClosed)) 
			frameUseCaseExecution2 = null;		
		else if ((geonFrame != null) && geonFrame.equals(geonFrameClosed)) 
			geonFrame = null;

		// Se actualiza el menu
		if (archviz.getNumberArchitectures() > 0) 
			geonMenu.changeState(new GeonMenuStateViewVisible(geonMenu));
	}
	
	/**
	 * Setea el frame UseCaseExecution.
	 *
	 * @param geonFrame Frame UseCaseExecution.
	 */
	private void setFrameUseCaseExecution(GeonFrame geonFrame) {
		if (geonMenu.getViewSelectionButtonNumber() == 1)   
			frameUseCaseExecution1 = geonFrame;
		else   
			frameUseCaseExecution2 = geonFrame;

		// Hay dos arquitecturas
		if (archviz.getNumberArchitectures() == 2) {
			if (frameUseCaseExecution2 != null)
				frameUseCaseExecution2.setTranslation(-0.09f, -0.03f, 0.0f);

			if (frameUseCaseExecution1 != null)
				frameUseCaseExecution1.setTranslation(0.04f, 0.07f, 0.0f);
		}
		// Hay una arquitectura
		else if (frameUseCaseExecution1 != null)
			frameUseCaseExecution1.setTranslation(-0.1f, 0.07f, 0.0f);
	}

	/**
	 * Inicializa el frame que se recibe como parametro.
	 * @param geonFrame Frame a inicializar.
	 */
	private void initializeFrame(GeonFrame geonFrame) {
		geonFrame.setScale(1.3f);
		geonFrame.setGeonFrameManager(this);
		archviz.addChild(geonFrame);

		// Corro las arquitecturas
		if (archviz.getArchitectureContainer(1).getComponent() != null)
			GeonAnimator.instance().push(archviz.getArchitectureContainer(1), geonFrame);
		if (archviz.getArchitectureContainer(2).getComponent() != null)
			GeonAnimator.instance().push(archviz.getArchitectureContainer(2), geonFrame);

		// Corro los frames para los casos de uso
		if (frameUseCaseExecution2 != null) 
			GeonAnimator.instance().push(frameUseCaseExecution2, geonFrame);
		if (frameUseCaseExecution1 != null) 
			GeonAnimator.instance().push(frameUseCaseExecution1, geonFrame);
	}
	
	/**
	 * Retorna la vista de Componentes y Conectores.
	 * @param buttonNumber Numero de arquitectura.
	 * @return J3DCompositeComponent Vista que se esta visualizando.
	 */
	private J3DCompositeComponent getCAndCView(int buttonNumber) {
		Component3D component = archviz.getArchitectureContainer(buttonNumber).getComponent();
		Component3D viewContainer = ((GeonWheel)component).getViewContainer(GeonBuilderViews.VIEW_CANDC);
		J3DCompositeComponent view = (J3DCompositeComponent)viewContainer.getChild(0);
		return view;
	}
	
/************************************************************************************************************************/	
/*										METODOS PARA EL BOTON ACCEPT													*/	
/************************************************************************************************************************/	
	/**
	 * El usuario selecciona un caso de uso.
	 * 
	 * @param geonUseCaseManager Encargado de ejecutar el 
	 * caso de uso.
	 * @param useCaseNameSelected Nombre del caso de uso a 
	 * ejecutar.
	 */
	public void buttonAccept_GeonFrameUseCaseSelection_click(GeonUseCaseManager geonUseCaseManager, String useCaseNameSelected) { 
		GeonFrame geonFrame = new GeonFrameUseCaseExecution(0.08f, 0.03f, 0.004f, new Point3f(0.0f, 0.0f, 0.0f), geonUseCaseManager, useCaseNameSelected, archviz);
		geonFrame.setScale(0.8f);
		geonFrame.setGeonFrameManager(this);
		archviz.addChild(geonFrame);
		geonFrame.changeVisible(true);
		
		setFrameUseCaseExecution(geonFrame);
	}
	
	/**
	 * El usuario selecciona una vista para visualizar.
	 * @param architectureNameSelected Nombre de la arquitectura a  
	 * visualizar.
	 */
	public void buttonAccept_GeonFrameArchitectureSelection_click(String architectureNameSelected) {
		archviz.showView(architectureNameSelected);
	}
	
	/**
	 * El usuario selecciona una propiedad.
	 * @param view Vista de la arquitectura a la cual se desea
	 * visualizar la propiedad seleccionada.
	 * @param propertyNameSelected Propiedad que se desea 
	 * visualizar.
	 */
	public void buttonAccept_GeonFramePropertiesSelection_click(J3DCompositeComponent view, String propertyNameSelected) {
		GeonPropertiesPainter geonPropertiesManager = new GeonPropertiesPainter(view, propertyNameSelected); 
		geonPropertiesManager.paint();
	}
	
	/**
	 * El usuario selecciona dos vistas para comparar.
	 * @param architectureName1Selected Nombre de la primer arquitectura
	 * a comparar.
	 * @param architectureName2Selected Nombre de la segunda arquitectura
	 * a comparar.
	 */
	public void buttonAccept_GeonFrameCompareSelection_click(String architectureName1Selected, String architectureName2Selected) {
		GeonMenuCommand command = new GeonMenuCommandAcceptCompare(geonMenu, this);
		command.execute();
		archviz.showView(architectureName1Selected, architectureName2Selected);
	}
	
}
