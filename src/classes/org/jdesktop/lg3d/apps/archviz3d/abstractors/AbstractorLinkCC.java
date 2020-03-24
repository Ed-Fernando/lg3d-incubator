package org.jdesktop.lg3d.apps.archviz3d.abstractors;


import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROComponent;
import org.jdesktop.lg3d.apps.archviz3d.ZExecution.PROLink;
import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;

/**
 * Esta clase representa el abstractor para un link en la vista de C&C. 
 *
 * @author teyseyre
 *
 */
public class AbstractorLinkCC extends AbstractorCC implements PROLink {
	/** Origen del link.*/
	private PROComponent origin;
	/** Destino del link.*/
	private PROComponent target;
	
    /**
     * Se instancia el Abstractor.
     * @param subject Text.pl asociado con el abstractor 
     * @param level Nivel de abstraccion. 
     * @param container Abstractor que contiene a este abstractor. 
     */
	public AbstractorLinkCC(PROComponent subject, int level, AbstractorCC container) {
		super(subject, level, container);
		
	}
	
	/** 
	 * Setea el origen del link.
	 * @param origin Origen del link.
	 */
    public void  setOrigin(PROComponent origin) {
		this.origin = origin;
	}
    
	/** 
	 * Retorna el origen del link.
	 * @return PROComponent Origen del link.
	 */
	public PROComponent getOrigin() {
		// Container es konwledge abstractor que esta dentro de un module y en el 
		// container esta el relacionado
		if (origin == null) 		
			origin = container.getContainer().getContainer().findAbstractorOf(((PROLink)subject).getOrigin());
		return origin;
	}
	
	/** 
	 * Setea el destino del link.
	 * @param target Destino del link.
	 */
    public void setTarget(PROComponent target)   {
    	this.target = target;
    }
    
	/** 
	 * Retorna el destino del link.
	 * @return PROComponent Destino del link.
	 */
    public PROComponent getTarget() {
    	// Container es konwledge abstractor que esta dentro de un module y en el 
    	// container esta el relacionado
    	if (target == null) 		
			target = container.getContainer().getContainer().findAbstractorOf(((PROLink)subject).getTarget());
    	return target;
    }
	
	/**
	 * Configura el abstractor.
	 */
	public void configureAbstractor() {
	}
	
	/**
	 * Retorna la representacion del abstractor.
	 * Dicha representacion es:
	 * a) "Module" para AbstractorCCComponent.
	 * b) "Configuration" + nivel de abstracion para AbstractorCCConfiguration.
	 * @return Object Representacion del abstractor.
	 */
	public Object abstractionRepresented() {
		return null;
	}

}
