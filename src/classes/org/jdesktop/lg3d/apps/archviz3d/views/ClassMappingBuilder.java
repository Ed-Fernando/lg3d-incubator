package org.jdesktop.lg3d.apps.archviz3d.views;

import java.lang.reflect.Constructor;
import org.candc.Ccmap;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.LayoutStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.views.ConfigurationBuild3D;
import org.jdesktop.lg3d.apps.archviz3d.views.FactoryMapping;
import org.jdesktop.lg3d.apps.archviz3d.views.FactoryMappingBuilder;

import java.util.Enumeration;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.vecmath.Color3f;


/**
 * @author teyseyre
 *
 */
public class ClassMappingBuilder extends FactoryMappingBuilder {
	/** Logger de mensajes.*/
	private static Logger logger = Logger.getLogger("lg.ArchViz3D");	
	
	/**Construye un componente de la arquitectura 
	 * @param o Abstractor
	 * @param builder Builder del componente 
	 * @param ccmap Component And Connector Mapping (XML)
	 */
	public Object buildVisual(Observable o, ConfigurationBuild3D builder, Ccmap ccmap) {
		try {
			BuildStrategy3D bs; 
			Constructor cons;
			
			try {  
			    String name = ccmap.getBuilder();
			    cons = Class.forName(name).getConstructor(new Class[]{BuildStrategy3D.class}); 
			}
			catch(NoSuchMethodException e) {
				cons = null;
			}
			
			// Obtengo el BuildStrategy
			if (cons != null) 
			    bs = (BuildStrategy3D)cons.newInstance(new Object[]{builder});
			else if (builder.getClass() != (Class.forName(ccmap.getBuilder()))) 
			    bs = (BuildStrategy3D)Class.forName(ccmap.getBuilder()).newInstance();
			else 
			    bs = builder;
			
			// Genero los parametros del constructor
			AbstractorCC abstractor = (AbstractorCC)o;
			if (ccmap.getGeonList() != null && abstractor.getGeonName() == null)
				abstractor.setGeonName(ccmap.getGeonList().getGeonArray(0));
			
			if (ccmap.getColor() != null && abstractor.getColor() == null)
				abstractor.setColor(new Color3f(ccmap.getColor().getRed().floatValue(), ccmap.getColor().getGreen().floatValue(), ccmap.getColor().getBlue().floatValue()));
			
			if (ccmap.getGeonList() != null && abstractor.getGeonsList().size() == 0) 
				abstractor.setGeonsList(ccmap.getGeonList().getGeonArray());

			if (ccmap.getWrapperClass() != null && abstractor.getWrapperClass() == null)
				abstractor.setWrapperClass(Class.forName(ccmap.getWrapperClass()));

			if (ccmap.getGeonEventList() != null && abstractor.getGeonsEventList().size() == 0)
				abstractor.setGeonsEventList(ccmap.getGeonEventList().getGeonArray());
			
			Object[] obs = new Object[]{o, bs, ((PROComponent)o).getName()};
			
			// Instancio el componente de la arquitectura
			logger.log(Level.INFO, "Instanziating Component \"" + obs[2] + "\"");
			cons = Class.forName(ccmap.getClass1()).getConstructor(new Class[]{Observable.class , BuildStrategy3D.class , String.class});
			J3DComponent j3d = (J3DComponent) cons.newInstance(obs);
			
			// Agrego el layout. 
			Enumeration e = j3d.getAllChildren();
			if  (!e.hasMoreElements()) 
				return null;
			else {
				if (ccmap.getLayout() != null)   
					((J3DCompositeComponent)((J3DCompositeComponent)j3d).getComponent(0)).setLayout((LayoutStrategy3D)FactoryMapping.getInstance().getBuilder("layout").buildVisual(o, builder, ccmap));
			}
			
			return j3d; 
		}
		catch(Exception e) {
			e.printStackTrace(); 
			logger.log(Level.SEVERE, "CAUSE: " + e.getCause());
			//e.getCause().printStackTrace();
		}
		
		return null;
	}

}
