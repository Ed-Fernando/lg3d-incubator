package org.jdesktop.lg3d.apps.archviz3d.ZExecution;

import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ArqComponent;

/**
 * Esta clase actua como una "marca" (similar a la clase Clonnable).
 * Dicha marca la utiliza la clase ConfigurationAbstractor en el metodo 
 * configureAbstractor. 
 * NOTA: ver la clase Configuration y el ArchitecturalMapping.xml. 
 * 
 * @author teyseyre
 */
public class Component extends ArqComponent {
    
    /** Creates a new instance of Component */
    public Component(String s) {
          super(s);
    }
    
}
