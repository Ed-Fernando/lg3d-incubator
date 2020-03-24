package org.jdesktop.lg3d.apps.archviz3d.ZExecution;

import java.io.File;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ArqFactory;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ZAnimat;
import org.jdesktop.lg3d.apps.archviz3d.xml.ReqArch3DHandler;



/**
 * 
 * @author teyseyre
 */
public class ArqFactory {
	/** Unica instancia de esta clase.*/
	static ArqFactory instance = new ArqFactory();

	/** Logger de mensajes.*/
	private static Logger logger = Logger.getLogger("lg.ArchViz3D");	

	/** Mapea el nombre de un componente con sus datoas en el ArchitecturalMapping.XML.*/
	protected Map map;

	/**
	 * Retorna la unica instancia de esta clase
	 * @return ArqFactory Unica instancia de esta clase
	 */
	public static ArqFactory instance() {
		return instance;
	}

	/** 
	 * Instancia esta clase. 
	 */
	private ArqFactory() {
	}

	/**
	 * Lee el ArchitecturalMappings.xml.
	 * @param dir Directorio en donde se encuentra el ArchitecturalMappings.xml
	 */
	public void loadMap(String dir) {
		try {
			logger.log(Level.CONFIG, "Load Map: " + dir + "ArchitecturalMappings.xml");
			map = ReqArch3DHandler.getMap(new File(dir + "ArchitecturalMappings.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getType(String name) {
		return ((org.reqarch3D.Map) map.get(name)).getType();
	}

	public String getArchType(String name) {
		return ((org.reqarch3D.Map) map.get(name)).getArcType();
	}

	public ZAnimat create(ZAnimat z, String name) {
		ZAnimat executor;
		if (getType(name) != null) {
			try {
				Class c = Class.forName("org.jdesktop.lg3d.apps.archviz3d.ZExecution." + getType(name));

//				logger.log(Level.INFO, "ARQFACT:  Arquitecture  " + name + "  " + getType(name));

				executor = (ZAnimat) c.getConstructors()[0]
						.newInstance(new Object[] { z.fileNameBase() + "/"
								+ name + ".tex.pl" });

			} catch (Exception e) {
				logger.log(Level.SEVERE, "Unable to Create ZAnimator Object from: " + name);
				e.printStackTrace();
				return null;
			}
		} else
			executor = new ZAnimat(z.fileNameBase() + "/" + name + ".tex.pl");

		setProperties(executor, name);
		return executor;
	}

	private void setProperties(ZAnimat executor, String name) {
		org.reqarch3D.Property[] props = ((org.reqarch3D.Map) map.get(name))
				.getPropertyArray();

		for (int i = 0; i < props.length; i++)
			executor.setProperty(props[i].getName(), props[i].getValue());
	}

}
