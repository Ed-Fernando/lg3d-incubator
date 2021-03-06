package org.jdesktop.lg3d.apps.archviz3d.views;

import java.util.Observable;

import org.candc.Ccmap;

import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;
import org.jdesktop.lg3d.apps.archviz3d.views.ConfigurationBuild3D;
import org.jdesktop.lg3d.apps.archviz3d.views.FactoryMappingBuilder;



/**
 * @author teyseyre
 * 
 */
public class BSHMappingBuilder extends FactoryMappingBuilder {

	public Object buildVisual(Observable o, ConfigurationBuild3D builder, Ccmap ccmap) {
		return Factory3D.instance().runBeanScript(ccmap.getBuilder());
	}
	
}
