package org.jdesktop.lg3d.apps.archviz3d.geons.menu;


import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameManager;
import org.jdesktop.lg3d.apps.archviz3d.geons.main.ArchViz3D;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommand;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuStateInitial;


/**
 * Esta clase encapsula la funcionalidad del boton Clear View del Menu. 
 * 
 * @author Juan Feldman
 *
 */
public class GeonMenuCommandClearView extends GeonMenuCommand {

	/**
	 * Inicializa el comando.
	 * @param invoker Invocador del commando.
	 * @param receiver Receptor del commando.
	 * @param params lista de parametros.
	 */
	public GeonMenuCommandClearView(GeonMenu invoker, GeonFrameManager receiver, Object[] params) {
		super(invoker, receiver, params);
	}

	/** 
	 * Ejecuta el comando.
	 */
	public void execute() {
		// Borro los frames. Debe ir al principio (ver mas abajo)
		receiver.clearButton_click();
		
		// Borro las vistas
		ArchViz3D archviz = (ArchViz3D)getParameter(0);
		archviz.getArchitectureContainer(1).removeComponent();
		archviz.getArchitectureContainer(2).removeComponent();

		// Actualizo el estado del menu. Debe ir al final pq si hay frame abiertos
		// al cerrarse dispararan changeState con el state GeonMenuStateViewVisible.
		// Ver GeonFrameManager.clearButton_click
		invoker.changeState(new GeonMenuStateInitial(invoker));
	}

}
