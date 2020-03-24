package org.jdesktop.lg3d.apps.archviz3d.geons.menu;


import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameManager;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommand;

/**
 * Esta clase encapsula la funcionalidad para cuando el usuario clickea
 * el boton accept del frame compare architectures. 
 * 
 * @author Juan Feldman
 *
 */
public class GeonMenuCommandAcceptCompare extends GeonMenuCommand {

	/**
	 * Inicializa el comando.
	 * @param invoker Invocador del commando.
	 * @param receiver Receptor del commando.
	 */
	public GeonMenuCommandAcceptCompare(GeonMenu invoker, GeonFrameManager receiver) {
		super(invoker, receiver);
	}

	/** 
	 * Ejecuta el comando.
	 */
	public void execute() {
		invoker.viewOneButton.setClicked(true);
		invoker.viewOneButton.setVisible(true);
		invoker.viewTwoButton.setClicked(false);
		invoker.viewTwoButton.setVisible(true);
	}

}
