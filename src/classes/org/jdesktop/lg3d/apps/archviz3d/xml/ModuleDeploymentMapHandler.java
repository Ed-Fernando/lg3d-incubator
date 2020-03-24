package org.jdesktop.lg3d.apps.archviz3d.xml;

import java.io.File;
import java.util.Hashtable;
import java.util.Vector;

import org.module.Momap;
import org.module.VisualInformationDocument;
import org.module.VisualInformationDocument.VisualInformation;

/**
 * Esta clase mapea el nombre de un componente en la vista de 
 * Modulos o en la vista de Deployment con sus datos en el 
 * ModuleMappings.XML o DeploymentMappings.XML. 
 * 
 * @author Juan Feldman
 * 
 */
public class ModuleDeploymentMapHandler {
	/** Esta tabla de hashing se utiliza para almacenar los ModuleMappings.xml y
	 * DeploymentMappings.xml que ya fueron leidos, para evitar volver a leerlos si ya fueron leidos.*/
	private static Hashtable<String, VisualInformationDocument> xmlFiles = new Hashtable<String, VisualInformationDocument>();
	
	/**
	 * Lee todos los mappings del ModuleMappings.XML o
	 * DeploymentMappings.XML.
	 * 
	 * @param file path al XML.  
	 * @return Map mapea el nombre de un componente de la 
	 * arquitectura con sus datos en el XML. 
	 * @throws Exception
	 */
	public synchronized static Vector<Momap> getVector(File file) throws Exception {
		Vector<Momap> map = new Vector<Momap>();

		// Obtengo los datos del XML
		VisualInformationDocument aDoc = null;
		if (xmlFiles.containsKey(file.getAbsolutePath())) 
			aDoc = xmlFiles.get(file.getAbsolutePath());
		else {
			aDoc = VisualInformationDocument.Factory.parse(file);
			xmlFiles.put(file.getAbsolutePath(), aDoc);
		}
		VisualInformation  visualInformation = aDoc.getVisualInformation();

		// Genero el vector de mapeo
		Momap [] maps = visualInformation.getMomapArray();
		for(int i = 0; i < maps.length; i++)
			map.add(maps[i]);

		return map;
	}

}
