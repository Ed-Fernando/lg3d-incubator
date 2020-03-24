package org.jdesktop.lg3d.apps.archviz3d.geons.menu;


import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameManager;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommand;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuStateInitial;


/**
 * Esta clase encapsula la funcionalidad del boton Mapping del Menu. 
 * 
 * @author Juan Feldman
 *
 */
public class GeonMenuCommandMapping extends GeonMenuCommand {

	/**
	 * Inicializa el comando.
	 * @param invoker Invocador del commando.
	 * @param receiver Receptor del commando.
	 */
	public GeonMenuCommandMapping(GeonMenu invoker, GeonFrameManager receiver) {
		super(invoker, receiver);
	}

	/** 
	 * Ejecuta el comando.
	 */
	public void execute() {
		GeonAbstract geonSelected = (GeonAbstract)getParameter(0);
		receiver.geonMappingButton_click(geonSelected);

		// Se inicializa el menu
		invoker.changeState(new GeonMenuStateInitial(invoker));
	}

}
