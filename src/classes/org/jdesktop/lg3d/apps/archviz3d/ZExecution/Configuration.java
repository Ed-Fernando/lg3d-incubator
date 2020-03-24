package org.jdesktop.lg3d.apps.archviz3d.ZExecution;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ArqComponent;

/**
 * Esta clase actua como una "marca" (similar a la clase Clonnable).
 * Dicha marca la utiliza la clase ConfigurationAbstractor en el metodo 
 * configureAbstractor.
 * NOTA: ver la clase Component y el ArchitecturalMapping.xml. 
 *
 * @author teyseyre
 */
public class Configuration extends ArqComponent {

	/** Crea una instancia nueva de Component */
	public Configuration(String s) {
		super(s);
	}

}
