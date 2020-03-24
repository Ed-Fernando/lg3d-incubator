package org.jdesktop.lg3d.apps.archviz3d.geons.menu;


import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameManager;
import org.jdesktop.lg3d.apps.archviz3d.geons.main.ArchViz3D;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommand;


/**
 * Esta clase encapsula la funcionalidad del boton Menu. 
 * 
 * @author Juan Feldman
 *
 */
public class GeonMenuCommandMenu extends GeonMenuCommand {

	/**
	 * Inicializa el comando.
	 * @param invoker Invocador del commando.
	 * @param receiver Receptor del commando.
	 */
	public GeonMenuCommandMenu(GeonMenu invoker, GeonFrameManager receiver) {
		super(invoker, receiver);
	}

	/** 
	 * Ejecuta el comando.
	 */
	public void execute() {
		invoker.viewButton.setVisible(!invoker.useCaseButton.isVisible());
		invoker.clearButton.setVisible(!invoker.useCaseButton.isVisible());
		invoker.showHideCluster.setVisible(!invoker.useCaseButton.isVisible());
		invoker.compareButton.setVisible(!invoker.useCaseButton.isVisible());
		invoker.moreAbstractionButton.setVisible(!invoker.useCaseButton.isVisible());
		invoker.lessAbstractionButton.setVisible(!invoker.useCaseButton.isVisible());
		invoker.geonMappingButton.setVisible(!invoker.useCaseButton.isVisible());
		invoker.geonPropertiesTableButton.setVisible(!invoker.useCaseButton.isVisible());
		invoker.geonPropertiesViewButton.setVisible(!invoker.useCaseButton.isVisible());
		invoker.useCaseButton.setVisible(!invoker.useCaseButton.isVisible());

		invoker.viewOneButton.setVisible(false);
		invoker.viewTwoButton.setVisible(false);
		
		ArchViz3D archviz = invoker.archviz;
		if (invoker.useCaseButton.isVisible() && archviz.getNumberArchitectures() == 2) {
			invoker.viewOneButton.setVisible(true);
			invoker.viewTwoButton.setVisible(true);
		}
	}

}
