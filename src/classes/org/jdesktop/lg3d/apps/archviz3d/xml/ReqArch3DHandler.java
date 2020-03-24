package org.jdesktop.lg3d.apps.archviz3d.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.xmlbeans.XmlOptions;
import org.reqarch3D.ArchitecturalInformationDocument;
import org.reqarch3D.ArchitecturalInformationDocument.ArchitecturalInformation;;

/**
 * @author teyseyre
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public class ReqArch3DHandler {
	/** Esta tabla de hashing se utiliza para almacenar los ArquitecturalMappings.xml
	 * que ya fueron leidos, para evitar volver a leerlos si ya fueron leidos.*/
	private static Hashtable<String, ArchitecturalInformationDocument> xmlFiles = new Hashtable<String, ArchitecturalInformationDocument>();
	
	/**
	 * Lee el ArchitecturalMappings.xml.
	 * 
	 * @param file path al ArchitecturalMappings.xml.
	 * @return Map mapea el nombre de un componente de la 
	 * arquitectura con sus datos en el XML. 
	 * @throws Exception
	 */
	public synchronized static Map<String, org.reqarch3D.Map> getMap(File file) throws Exception {
		XmlOptions validateOptions = new XmlOptions();

		validateOptions.setValidateOnSet();

		Map<String, org.reqarch3D.Map> map = new HashMap<String, org.reqarch3D.Map>();

		// Obtengo los datos del XML
		ArchitecturalInformationDocument aDoc = null;
		if (xmlFiles.containsKey(file.getAbsolutePath())) 
			aDoc = xmlFiles.get(file.getAbsolutePath());
		else {
			aDoc = ArchitecturalInformationDocument.Factory.parse(file, validateOptions);
			xmlFiles.put(file.getAbsolutePath(), aDoc);
		}

		// Genero el vector de mapeo
		ArchitecturalInformation ai = aDoc.getArchitecturalInformation();
		org.reqarch3D.Map[] maps = ai.getMapArray();
		for (int i = 0; i < maps.length; i++)
			map.put(maps[i].getComponent(), maps[i]);

		return map;
	}

}
