package org.jdesktop.lg3d.apps.archviz3d.views;

import java.io.File;
import java.util.Map;


import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Node3D;
import org.jdesktop.lg3d.apps.archviz3d.views.ConfigurationBuild3D;
import org.jdesktop.lg3d.apps.archviz3d.views.FactoryMapping;
import org.jdesktop.lg3d.apps.archviz3d.xml.CAndCMapHandler;


public class BuitlStratWithBuilder extends BuildStrategy3D {
	protected Map map;
	protected ConfigurationBuild3D bst;
	
	/**Mapea el nombre de un componente de la arquitectura (ejemplo: ATM) con 
	 * la parte del ComponentAndConnectorMappings.XML para dicho componente
	 * @param configurationBuild3D
	 * @param dir_file
	 */
	public BuitlStratWithBuilder(ConfigurationBuild3D configurationBuild3D, String dir_file) {
		super();
		try { 
			if (dir_file.charAt(0) == File.separatorChar) 
				map = CAndCMapHandler.getMap(new File(dir_file));	
			else 
				map = CAndCMapHandler.getMap(new File(dir_file));
		} 
		catch (Exception e) { 
			e.printStackTrace(); 
		}
		bst = configurationBuild3D;
	}
		
	/**Construye el componente 3D pasado como parametro
	 *@param client componente a construir 
	 */ 
	public void build(J3DComponent client) {
		// Se obtiene el subject de client, el cual es el abstractor y se construye el componente  
	 	J3DComponent g = (J3DComponent)FactoryMapping.getInstance().buildVisual(client.getSubject(), bst, map);
	 	((Node3D)g).setVisiblePart(false);
		((J3DCompositeComponent)client).addComponent(g); 
	 }
	
}
