package org.jdesktop.lg3d.apps.archviz3d.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.vecmath.Vector3f;

import org.candc.Ccmap;
import org.candc.Point;
import org.candc.VisualInformationDocument;
import org.candc.VisualInformationDocument.VisualInformation;

/**
 * @author teyseyre
 */
public class CAndCMapHandler {
	/** Esta tabla de hashing se utiliza para almacenar los ComponentAndConnectorMappings.xml
	 * que ya fueron leidos, para evitar volver a leerlos si ya fueron leidos.*/
	private static Hashtable<String, VisualInformationDocument> xmlFiles = new Hashtable<String, VisualInformationDocument>();
	/** Informacion del ComponentAndConnectorMappings.xml.*/ 
	private static VisualInformation visualInformation;
	
	/**
	 * Lee todos los mappings del ComponentAndConnectorMappings.XML.
	 * 
	 * @param file path al ComponentAndConnectorMappings.XML.  
	 * @return Map mapea el nombre de un componente de la 
	 * arquitectura con sus datos en el XML. 
	 * @throws Exception
	 */
	public synchronized static Map<String, Ccmap> getMap(File file) throws Exception {
		Map<String, Ccmap> map = new HashMap<String, Ccmap>();

		// Obtengo los datos del XML
		VisualInformationDocument aDoc = null;
		if (xmlFiles.containsKey(file.getAbsolutePath())) 
			aDoc = xmlFiles.get(file.getAbsolutePath());
		else {
			aDoc = VisualInformationDocument.Factory.parse(file);
			xmlFiles.put(file.getAbsolutePath(), aDoc);
		}
		visualInformation = aDoc.getVisualInformation();
		
		// Genero el vector de mapeo
		Ccmap [] maps = visualInformation.getCcmapArray();
		for(int i = 0; i < maps.length; i++)
			map.put(maps[i].getComponent(), maps[i]);

		return map;
	}
	
	/**
	 * Retorna el nombre de la arquitectura. 
	 * @param file Path al ComponentAndConnectorMappings.XML.  
	 * @return String Nombre de la arquitectura.
	 * @throws Exception
	 */
	public synchronized static String getArchitectureName(File file) throws Exception {
		return visualInformation.getName();
	}
	
	/**
	 * Retorna la escala de la arquitectura. 
	 * @param file Path al ComponentAndConnectorMappings.XML.  
	 * @return Float Escala de la arquitectura.
	 * @throws Exception
	 */
	public synchronized static float getArchitectureScale(File file) throws Exception {
		return visualInformation.getArchitectureScale().floatValue();
	}
	
	/**
	 * Retorna la traslacion de la arquitectura. 
	 * @param file Path al ComponentAndConnectorMappings.XML.  
	 * @return Float Traslacion de la arquitectura.
	 * @throws Exception
	 */
	public synchronized static Vector3f getArchitectureTranslation(File file) throws Exception {
		Point point = visualInformation.getArchitectureTranslation();
		Vector3f translation = new Vector3f();
		translation.x = point.getX().floatValue();
		translation.y = point.getY().floatValue();
		translation.z = point.getZ().floatValue();
		return translation; 
	}
	
}
