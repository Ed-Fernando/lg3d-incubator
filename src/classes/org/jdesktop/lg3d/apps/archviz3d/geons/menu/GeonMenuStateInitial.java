package org.jdesktop.lg3d.apps.archviz3d.geons.menu;

import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuStateAbstract;

/**
 * Esta clase maneja los botones del menu seteandolos a su estado inicial.
 * Esto ocurre cuando, por ejemplo, se muestra un frame o se hace un clear.
 * 
 * @author Juan Feldman
 *
 */
public class GeonMenuStateInitial extends GeonMenuStateAbstract {

	/**
	 * Instancia GeonMenuStateFrameOpen.
	 * @param geonMenu Menu de la aplicacion.
	 */
	public GeonMenuStateInitial(GeonMenu geonMenu) {
		super(geonMenu);
	}

	/**
	 * Maneja la habilitacion/deshabilitacion y las visibilidad de los botones del menu. 
	 */
	public void handle() {
		geonMenu.viewOneButton.setVisible(false);
		geonMenu.viewTwoButton.setVisible(false);

		// Inicializo los Toggle Button 
		geonMenu.showHideCluster.setClicked(false);
		geonMenu.viewOneButton.setClicked(false);
		geonMenu.viewTwoButton.setClicked(false);
		geonMenu.geonMappingButton.setClicked(false);
		geonMenu.geonPropertiesTableButton.setClicked(false);
		
		geonMenu.showHideCluster.setEnabled(false);
		geonMenu.moreAbstractionButton.setEnabled(false);
		geonMenu.lessAbstractionButton.setEnabled(false);
		geonMenu.geonMappingButton.setEnabled(false);
		geonMenu.geonPropertiesTableButton.setEnabled(false);
		geonMenu.geonPropertiesViewButton.setEnabled(false);
		geonMenu.useCaseButton.setEnabled(false);
	}
}
