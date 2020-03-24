package org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph;

import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;


/**
 * Superclase de todos los componentes de un grafo.
 *
 * @author Gaston Peirano & Mauricio Poncio
 * 
 */
public abstract class ComponentGraph3D extends GeonAbstract {
    /** Indica el estado del componente.*/
    protected boolean visible = true;
    private J3DComponent cuerpo;

    /**
     * Averigua si el componente del grafo esta siendo dibujado o no.
     * @return true si es visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Oculta el componente.
     */
    public void hide() {
    	visible = false;
    	setChanged();
    	notifyObservers(new Boolean(visible));
    }

    /**
     * Muestra el componente.
     */
    public void show() {
        visible = true;
        setChanged();
        notifyObservers(new Boolean(visible));
    }
    
    /**
     * Devuelve el cuerpo grafico de un componente.
     * @return un J3DSimpleComponent
     */
    public J3DComponent getCuerpo() {
        return cuerpo;
    }
    
    /**
     * Le da un cuerpo grafico al componente.
     * @param cuerpo el cuerpo grafico
     */
    public void setCuerpo(J3DComponent cuerpo) {
    	if (this.cuerpo != null) 
    		this.removeComponent(this.cuerpo);
    	
        this.cuerpo = cuerpo;
        addComponent(cuerpo); //agrego a la composicion
    }
    
    /**
     * Elimina el cuerpo
     */
    public void removeCuerpo() {
    	if (this.cuerpo != null) 
    		this.removeComponent(this.cuerpo);
        this.cuerpo = null;
    }
}
