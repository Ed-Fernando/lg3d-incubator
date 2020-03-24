package org.jdesktop.lg3d.apps.archviz3d.abstractors;

import java.util.Enumeration;


import org.jdesktop.lg3d.apps.archviz3d.ZExecution.Configuration;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorComponentCC;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorConfigurationCC;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorKnowledeCC;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorWithKnowledgeCC;



/**
 * Esta clase representa el abstractor para un componente compuesta en la
 * vista de C&C. Un componte compuesto contiene otros componentes ademas de
 * poseer eventos.
 * 
 * @author teyseyre
 * 
 */
public class AbstractorConfigurationCC extends AbstractorWithKnowledgeCC {

    /**
     * Se instancia el Abstractor.
     * @param subject Text.pl asociado con el abstractor 
     */
	public AbstractorConfigurationCC(PROComponent subject) {
		super(subject);
	}

    /**
     * Se instancia el Abstractor.
     * @param subject Text.pl asociado con el abstractor 
     * @param level Nivel de abstraccion. 
     * @param container Abstractor que contiene a este abstractor. 
     */
	public AbstractorConfigurationCC(PROComponent subject, int level, AbstractorCC container) {
		super(subject, level, container);
	}

	/**
	 * Configura el abstractor.
	 */
	public void configureAbstractor() {
		// module for configuration for view events
		AbstractorCC abstractorComponent = new AbstractorComponentCC(getSubject(), 0, this);
		abstractorComponent.setName(this.getName() + "_Mod");
		abstractorComponent.setAbstractionLevel(this.getAbstractionLevel());
		this.addAbstractor(abstractorComponent);

		// configuracion
		AbstractorCC abstractor;
		for (Enumeration e = getFullComponents().elements(); e.hasMoreElements();) {
			Object o = e.nextElement();

			if (o instanceof Configuration)
				abstractor = new AbstractorConfigurationCC((PROComponent) o, level + 1, this);
			else
				abstractor = new AbstractorComponentCC((PROComponent) o, 0, this);

			abstractor.setAbstractionLevel(this.getAbstractionLevel());
			this.addAbstractor(abstractor);
		}
		kAbs = new AbstractorKnowledeCC(subject, level, this);
		kAbs.setAbstractionLevel(this.getAbstractionLevel());
	}

	/**
	 * Retorna la representacion del abstractor.
	 * Dicha representacion es:
	 * a) "Module" para AbstractorCCComponent.
	 * b) "Configuration" + nivel de abstracion para AbstractorCCConfiguration.
	 * @return Object Representacion del abstractor.
	 */
	public Object abstractionRepresented() {
		return "Configuration" + level;
	}

}
