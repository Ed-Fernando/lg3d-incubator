package org.jdesktop.lg3d.apps.archviz3d.abstractors;

import java.util.Vector;


import org.jdesktop.lg3d.apps.archviz3d.ZExecution.ArqComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorKnowledeCC;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorWithKnowledgeCC;



/**
 * Esta clase representa el abstractor para un componente simple en la
 * vista de C&C. Un componte simple no contiene otros componentes, solo
 * posee eventos.
 * 
 * @author teyseyre
 *
 */
public class AbstractorComponentCC extends AbstractorWithKnowledgeCC {

    /**
     * Se instancia el Abstractor.
     * @param subject Text.pl asociado con el abstractor 
     */
	public AbstractorComponentCC(PROComponent subject) {
		super(subject);
	}
	
    /**
     * Se instancia el Abstractor.
     * @param subject Text.pl asociado con el abstractor 
     * @param level Nivel de abstraccion. 
     * @param container Abstractor que contiene a este abstractor. 
     */
	public AbstractorComponentCC(PROComponent subject, int level, AbstractorCC container) {
		super(subject, level, container);
	}
	
	/**
	 * Retorna los eventos que cumplan con el nivel de abstraccion.
	 * @return Vector Eventos que cumplan con el nivel de abstraccion..
	 */
	public Vector getComponents() {
    	if (abstractionLevel.moreThan(abstractionRepresented())) 
    		return getEvents();
    	else 
    		return getEmptyComponents();
    }

	/**
	 * Retorna los eventos asociados con el abstractor.
	 * @return Vector Eventos asociados con el abstractor.
	 */
	public Vector getEvents() {
		return ((ArqComponent)subject).getEvents();
	}
	
	/**
	 * Configura el abstractor.
	 */
	public void configureAbstractor() {
//		for (Enumeration e=getFullComponents().elements(); e.hasMoreElements();) {
//			Object o= e.nextElement();
//			//TODO agregar elementos modulo
//			//components.add(new ModuleAbstractor((PROComponent)o));
//		}
		kAbs = new AbstractorKnowledeCC(subject, level, this);
		kAbs.setAbstractionLevel(this.getAbstractionLevel());
	}

	/**
	 * Retorna la representacion del abstractor.
	 * Dicha representacion es:
	 * a) "Module" para AbstractorComponent.
	 * b) "Configuration" + nivel de abstracion para AbstractorConfiguration.
	 * @return Object Representacion del abstractor.
	 */
	public Object abstractionRepresented() {
		return "Module";
	}

}
