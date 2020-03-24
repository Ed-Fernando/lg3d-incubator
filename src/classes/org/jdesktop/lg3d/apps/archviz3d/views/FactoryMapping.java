package org.jdesktop.lg3d.apps.archviz3d.views;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import org.candc.Ccmap;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.views.BSHLayoutMappingBuilder;
import org.jdesktop.lg3d.apps.archviz3d.views.BSHMappingBuilder;
import org.jdesktop.lg3d.apps.archviz3d.views.ClassMappingBuilder;
import org.jdesktop.lg3d.apps.archviz3d.views.ConfigurationBuild3D;
import org.jdesktop.lg3d.apps.archviz3d.views.FactoryMapping;
import org.jdesktop.lg3d.apps.archviz3d.views.FactoryMappingBuilder;

/**
 * @author teyseyre
 */
public class FactoryMapping  {
	protected Map<String, FactoryMappingBuilder> builders;
	protected static FactoryMapping instance = new FactoryMapping();
	
	private FactoryMapping() {
		builders = new HashMap<String, FactoryMappingBuilder>();
		builders.put("Class",new ClassMappingBuilder());
		builders.put("BSH",new BSHMappingBuilder());
		builders.put("layout",new BSHLayoutMappingBuilder());
	}
	
	public static FactoryMapping getInstance() {
		return instance;
	}
	
	public FactoryMappingBuilder getBuilder(String name) {
		return (FactoryMappingBuilder) builders.get(name);
	}
	
	/**Construye el componente 3D
	 * @param o Abstractor
	 * @param builder Constructor
	 * @param map Mapea el nombre de un componente de la arquitectura (ejemplo: ATM) con 
	 * la parte del ComponentAndConnectorMappings.XML para dicho componente
	 * @return componente 3D construido
	 */
	protected Object buildVisual(Observable o, ConfigurationBuild3D builder, Map map) {
		Ccmap ccmap = (Ccmap)map.get(((PROComponent)o).getName());
		if (ccmap == null) 
			return null;
		FactoryMappingBuilder fmb = builders.get(ccmap.getBuilderType());
		return fmb.buildVisual(o, builder, ccmap);
	}
	
}
