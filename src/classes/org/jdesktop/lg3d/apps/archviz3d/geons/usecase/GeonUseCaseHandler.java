package org.jdesktop.lg3d.apps.archviz3d.geons.usecase;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.reqarch3D.Ucmap;
import org.reqarch3D.UseCaseInformationDocument;
import org.reqarch3D.UseCaseInformationDocument.UseCaseInformation;

/**
 * Esta clase se encarga de leer los datos que se encuentran
 * en el file UseCaseMapping.xml.
 * 
 * @author Juan Feldman
 *
 */
public class GeonUseCaseHandler {
	
	/**
	 * Lee el UseCaseMappings.xml.
	 * 
	 * @param file Path al UseCaseMappings.xml.
	 * @return Map Mapea el nombre de un caso de uso   
	 * con sus datos en el XML. 
	 * @throws Exception
	 */
	public static Map getMap(File file) throws Exception {
		Map<String, Ucmap> map = new HashMap<String, Ucmap>();

		UseCaseInformationDocument aDoc = UseCaseInformationDocument.Factory.parse(file);
		UseCaseInformation ai= aDoc.getUseCaseInformation();

		Ucmap[] maps= ai.getUcmapArray();
		for(int i=0; i< maps.length; i++)
			map.put(maps[i].getName(), maps[i]);

		return map;
	}

}
