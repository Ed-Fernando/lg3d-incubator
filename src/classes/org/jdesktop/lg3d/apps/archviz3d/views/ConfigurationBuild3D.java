package org.jdesktop.lg3d.apps.archviz3d.views;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.vecmath.Color3f;

import org.candc.Ccmap;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.Dispatcher;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ZEvent;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.builders.GraphBuilder3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Node3D;
import org.jdesktop.lg3d.apps.archviz3d.views.FactoryMapping;
import org.jdesktop.lg3d.apps.archviz3d.xml.CAndCMapHandler;


public class ConfigurationBuild3D extends GraphBuilder3D {
	/** Logger de mensajes.*/
	private static Logger logger = Logger.getLogger("lg.ArchViz3D");	
	/**Mapea el nombre de un componente de la arquitectura (ejemplo: ATM) con 
	   la parte del ComponentAndConnectorMappings.XML para dicho componente.*/
	private Map map;
	
	/**
	 * Instancia el ConfigurationBuild3D
	 * @param dirFile path al file que contiene el ComponentAndConnectorMappings.xml
	 */
	public ConfigurationBuild3D(String dirFile) {
		try { 
			if (dirFile.charAt(0) == File.separatorChar)
				map = CAndCMapHandler.getMap(new File(dirFile));
			else 
				map = CAndCMapHandler.getMap(new File(dirFile));
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
	}

	public  J3DComponent getNodeShape(Observable o) {
		J3DComponent j3d = (J3DComponent)FactoryMapping.getInstance().buildVisual(o, this, map);
		if (j3d == null) 
			return null;
		Dispatcher Disp = Dispatcher.instance();
		Disp.addObserver(o, j3d, ZEvent.Z_ALL, false);
		return j3d;
	} 

	/**
	 * Crea un link entre el node1 y el nodo2
	 * @param node1 inicio del link 
	 * @param node2 fin del link
	 * @param o abstractor del link 
	 */
	public  J3DComponent getEdgeShape(Node3D node1, Node3D node2, Observable o) {
		// Obtengo el Component And Connector Mappings para el Link
		String componentName = ((PROComponent)o).getName();
		Ccmap ccmap = (Ccmap)map.get(componentName);

		// Le seteo los datos al abstractor
		AbstractorCC abstractor = (AbstractorCC)o;
		if (ccmap.getGeonList() != null && abstractor.getGeonName() == null)
			abstractor.setGeonName(ccmap.getGeonList().getGeonArray(0));

		if (ccmap.getColor() != null && abstractor.getColor() == null)
			abstractor.setColor(new Color3f(ccmap.getColor().getRed().floatValue(), ccmap.getColor().getGreen().floatValue(), ccmap.getColor().getBlue().floatValue()));

		// Le seteo la lista de Geones que pueden representarlo
		if (ccmap.getGeonList() != null && abstractor.getGeonsList().size() == 0) 
			abstractor.setGeonsList(ccmap.getGeonList().getGeonArray());
		
		// Creo el Link
		try {
			logger.log(Level.INFO, "Instanziating Component \"" + ccmap.getComponent() + "\"");

			BuildStrategy3D builder = (BuildStrategy3D)Class.forName(ccmap.getBuilder()).newInstance();

			Constructor cons = Class.forName(ccmap.getClass1()).getConstructor(new Class[]{Node3D.class, Node3D.class, Observable.class , BuildStrategy3D.class, String.class});
			J3DComponent j3d = (J3DComponent)cons.newInstance(node1, node2, o, builder, componentName); 
			
			Dispatcher Disp = Dispatcher.instance();
			Disp.addObserver(o, j3d, ZEvent.Z_ALL, false);

			return j3d;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
