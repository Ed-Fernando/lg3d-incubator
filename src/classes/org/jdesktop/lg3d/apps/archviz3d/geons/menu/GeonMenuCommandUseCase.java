package org.jdesktop.lg3d.apps.archviz3d.geons.menu;


import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameManager;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommand;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuStateInitial;

/**
 * Esta clase encapsula la funcionalidad del boton Use Case del Menu. 
 * 
 * @author Juan Feldman
 *
 */
public class GeonMenuCommandUseCase extends GeonMenuCommand {

	/**
	 * Inicializa el comando.
	 * @param invoker Invocador del commando.
	 * @param receiver Receptor del commando.
	 */
	public GeonMenuCommandUseCase(GeonMenu invoker, GeonFrameManager receiver) {
		super(invoker, receiver);
	}

	/** 
	 * Ejecuta el comando.
	 */
	public void execute() {
		int useCaseButtonNumber = 1;
		if (invoker.viewTwoButton.isVisible() && invoker.viewTwoButton.getClicked())
			useCaseButtonNumber = 2;

		receiver.useCaseButton_click(useCaseButtonNumber);

		// Se inicializa el menu
		invoker.changeState(new GeonMenuStateInitial(invoker));
	}

}
