package org.jdesktop.lg3d.apps.archviz3d.views;

import java.util.Observable;
import org.candc.Ccmap;

import org.jdesktop.lg3d.apps.archviz3d.views.ConfigurationBuild3D;


/**
 * @author teyseyre
 *
 */
public abstract class FactoryMappingBuilder  {

	public abstract  Object buildVisual(Observable o, ConfigurationBuild3D builder, Ccmap ccmap);

}
