package org.jdesktop.lg3d.apps.archviz3d.abstractors;

import java.util.Enumeration;


import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROLink;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorLinkCC;



/**
 * 
 * @author teyseyre
 * 
 */
public class AbstractorKnowledeCC extends AbstractorCC {

    /**
     * Se instancia el Abstractor.
     * @param subject Text.pl asociado con el abstractor 
     * @param level Nivel de abstraccion. 
     * @param container Abstractor que contiene a este abstractor. 
     */
	public AbstractorKnowledeCC(PROComponent subject, int level, AbstractorCC container) {
		super(subject, level, container);
	}
	
	/**
	 * Configura el abstractor.
	 */
	public void configureAbstractor() {
		for (Enumeration e = getFullLinks().elements(); e.hasMoreElements();) {
			PROLink link = (PROLink) e.nextElement();
			
			AbstractorLinkCC linkAbstractor = new AbstractorLinkCC((PROComponent)link, 0, this);

			this.addAbstractor(linkAbstractor);
			linkAbstractor.setAbstractionLevel(this.getAbstractionLevel());
		}
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

