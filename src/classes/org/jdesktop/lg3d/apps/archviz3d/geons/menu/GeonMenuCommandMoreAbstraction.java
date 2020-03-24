package org.jdesktop.lg3d.apps.archviz3d.geons.menu;


import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonWheel;
import org.jdesktop.lg3d.apps.archviz3d.geons.frames.GeonFrameManager;
import org.jdesktop.lg3d.apps.archviz3d.geons.main.ArchViz3D;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuCommand;


/**
 * Esta clase encapsula la funcionalidad del boton More Abstraction del Menu. 
 * 
 * @author Juan Feldman
 *
 */
public class GeonMenuCommandMoreAbstraction extends GeonMenuCommand {

	/**
	 * Inicializa el comando.
	 * @param invoker Invocador del commando.
	 * @param receiver Receptor del commando.
	 * @param params lista de parametros.
	 */
	public GeonMenuCommandMoreAbstraction(GeonMenu invoker, GeonFrameManager receiver, Object[] params) {
		super(invoker, receiver, params);
	}

	/** 
	 * Ejecuta el comando.
	 */
	public void execute() {
		int buttonNumber = 1;
		if (invoker.viewTwoButton.isVisible() && invoker.viewTwoButton.getClicked())
			buttonNumber = 2;

		receiver.moreAbstractionButton_click(buttonNumber);
		
		ArchViz3D archViz3D = (ArchViz3D)getParameter(0);
		GeonWheel geonWheel = (GeonWheel)archViz3D.getArchitectureContainer(buttonNumber).getComponent();
		geonWheel.decreaseAbstractionLevel();
	}

}
