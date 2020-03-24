package org.jdesktop.lg3d.apps.archviz3d.geons.menu;


import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameManager;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommand;


/**
 * Esta clase encapsula la funcionalidad del boton Show Hide Cluster del Menu. 
 * 
 * @author Juan Feldman
 *
 */
public class GeonMenuCommandShowHideCluster extends GeonMenuCommand {

	/**
	 * Inicializa el comando.
	 * @param invoker Invocador del commando.
	 * @param receiver Receptor del commando.
	 */
	public GeonMenuCommandShowHideCluster(GeonMenu invoker, GeonFrameManager receiver) {
		super(invoker, receiver);
	}

	/** 
	 * Ejecuta el comando.
	 */
	public void execute() {
		GeonAbstract geonSelected = (GeonAbstract)getParameter(0);
		receiver.showHideClusterButton_click(geonSelected );
		geonSelected.setVisiblePart(!geonSelected.isVisiblePart());
	}

}
