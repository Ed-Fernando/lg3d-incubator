package org.jdesktop.lg3d.apps.archviz3d.geons.menu;

import org.jdesktop.lg3d.wg.Component3D;

import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderViews;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonAnimation;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonWheel;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenu;
import org.jdesktop.lg3d.apps.archviz3d.geons.menu.GeonMenuStateAbstract;



/**
 * Esta clase maneja los botones del menu cuando se muestran las vistas.
 * 
 * @author Juan Feldman
 *
 */
public class GeonMenuStateViewVisible extends GeonMenuStateAbstract {

	/**
	 * Instancia GeonMenuStateViewVisible.
	 * @param geonMenu Menu de la aplicacion.
	 */
	public GeonMenuStateViewVisible(GeonMenu geonMenu) {
		super(geonMenu);
	}

	/**
	 * Maneja la habilitacion/deshabilitacion y las visibilidad de los botones del menu. 
	 */
	public void handle() {
		GeonAnimation geonAnimation = geonMenu.archviz.getArchitectureContainer(geonMenu.getViewSelectionButtonNumber());
		GeonWheel geonWheel = (GeonWheel)geonAnimation.getComponent();
		if (geonWheel.getNumberViewsVisible() != 3) {
			Component3D viewContainer = geonWheel.getViewContainer(GeonBuilderViews.VIEW_CANDC);
			if (viewContainer.isVisible()) {
				geonMenu.useCaseButton.setEnabled(true);
				geonMenu.geonPropertiesViewButton.setEnabled(true);
			}
		}
		else {
			geonMenu.useCaseButton.setEnabled(false);
			geonMenu.geonPropertiesViewButton.setEnabled(false);
		}
		geonMenu.showHideCluster.setEnabled(true);
		geonMenu.moreAbstractionButton.setEnabled(true);
		geonMenu.lessAbstractionButton.setEnabled(true);
		geonMenu.geonMappingButton.setEnabled(true);
		geonMenu.geonPropertiesTableButton.setEnabled(true);
	}
}
