package org.jdesktop.lg3d.apps.archviz3d.abstractors;

import java.util.Vector;


import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractionLevel;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorKnowledeCC;


/**
 * 
 * @author teyseyre
 *
 */
public abstract class AbstractorWithKnowledgeCC extends AbstractorCC {
	protected AbstractorKnowledeCC kAbs;
	
    /**
     * Se instancia el Abstractor.
     * @param subject Text.pl asociado con el abstractor 
     */
	public AbstractorWithKnowledgeCC(PROComponent subject) {
		super(subject, 0);
	}
	
    /**
     * Se instancia el Abstractor.
     * @param subject Text.pl asociado con el abstractor 
     * @param level Nivel de abstraccion. 
     * @param container Abstractor que contiene a este abstractor. 
     */
	public AbstractorWithKnowledgeCC(PROComponent subject, int level, AbstractorCC container) {
		super(subject, level, container);
	}
	
	/**
	 * Retorna los abstractores de los links.
	 * @return Vector Abstractores de los Links.
	 */
	public Vector getLinks() {
    	return kAbs.getComponents();
    }
	 
	/**
	 * Setea el nivel de abstraccion.
	 * @param abstractionLevel Nivel de abstraccccion.
	 */
	public void setAbstractionLevel(AbstractionLevel abstractionLevel){
		kAbs.setAbstractionLevel(abstractionLevel);
		super.setAbstractionLevel(abstractionLevel);
	}
	
	/**
	 * Setea el nivel de abstracion.
	 * @param level Nivel de abstraccccion.
	 */
	public void setAbstractionLevel(int level){
		kAbs.setAbstractionLevel(level);
		super.setAbstractionLevel(level);
	}
	 
}
