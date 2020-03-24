package org.jdesktop.lg3d.apps.archviz3d.abstractors;

import java.util.HashMap;
import java.util.Observable;
import java.util.Vector;

import javax.vecmath.Color3f;

import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.sg.Appearance;



/**
 * Clase padre de todos los abstractores.
 * 
 * @author Juan Feldman
 *
 */
public abstract class Abstractor extends Observable {
	/** Componente de la arquitectura al cual este abstractor abstrae.*/
	private J3DComponent componentView = null;

	/** Apariencia del componente de la arquitectura asociado con este abstractor.*/
    private Appearance appearance = null;
    /** Geones que pueden representar al componente de la arquitectura asociado con este abstractor.*/
    private Vector<String> geonsList  = null;
    /** Nombre del geon que representa al componente de la arquitectura asociado con este abstractor.**/
    private String geonName  = null;

    /**
     * Instancia Abstractor
     */
	public Abstractor(J3DComponent componentView) {
		// No se instancia la apariencia en el constructor, porque se debe 
		// diferencia la primera vez que se crea el componente del 
		// resto de las veces, para que la clase ClassMappingBuilder setee el
		// color la primera vez y despues no lo haga cuando se cambie el nivel de
		// abstraccion.
		setComponentView(componentView);
		geonsList = new Vector<String>();
	}
    
	/** Setea el nivel de abstraccion.*/
	public abstract void setAbstractionLevel(int level); 

	/** Aumenta el nivel de abstraccion.*/
	public abstract void increaseAbstractionLevel();

	/** Disminuye el nivel de abstraccion.*/
	public abstract void decreaseAbstractionLevel(); 

    /**
     * Retorna todas las propiedades del componente.
     * @return propiedades del componente.
     */
	public abstract HashMap<String, String> getProperties(); 

	/**
     * Setea la lista de geones que pueden representar al 
     * componente de la arquitectura asociado con este abstractor
     * @param geonsList Lista de geones que pueden representar al 
     * componente de la arquitectura asociado con este abstractor
     */
    public void setGeonsList(String[] geonsList) {
    	this.geonsList.removeAllElements();        
    	
    	for (int i = 0; i < geonsList.length; i++)
    		this.geonsList.add(geonsList[i]);
    }
      
    /**
     * Retorna la lista de geones que pueden representar al 
     * componente de la arquitectura asociado con este abstractor
     * @return Lista de geones que pueden representar al 
     * componente de la arquitectura asociado con este abstractor
     */
    public Vector<String> getGeonsList() {
    	return geonsList;
    }
    
    /**
     * Setea el nombre del Geon que reprensenta al componente de 
     * la arquitectura asociado con este abstractor
     * @param geonName Nombre del Geon que reprensenta al  
     * componente de la arquitectura asociado con este abstractor
     */
    public void setGeonName(String geonName) {
    	this.geonName = geonName;
    }
    
    /**
     * Retorna el nombre del Geon que reprensenta al componente de la 
     * arquitectura asociado con este abstractor
     * @return geonName Nombre del Geon que reprensenta al  
     * componente de la arquitectura asociado con este abstractor
     */
    public String getGeonName() {
    	return geonName;
    }

    /**
     * Seteo la apariencia del componente de la arquitectura asociado con este abstractor.
     * @param appearance Apariencia del componente de la arquitectura asociado con este abstractor.
     */
    public void setAppearance(Appearance appearance) {
        this.appearance = appearance;
    }

    /**
     * Retorna la apariencia del componente de la arquitectura asociado con este abstractor.
     * @return Appearance Apariencia del componente de la arquitectura asociado con este abstractor.
     */
    public Appearance getAppearance() {
        return appearance;
    }

    /**
     * Seteo el color del componente de la arquitectura asociado con este abstractor 
     * @param color color del componente de la arquitectura asociado con este abstractor
     */
    public void setColor(Color3f color) {
    	if (appearance == null)
    		appearance = Factory3D.instance().createAppearance(null); 
    	appearance.getMaterial().setDiffuseColor(color);
    }

    /**
     * Retorno el color del componente de la arquitectura asociado con este abstractor 
     * @return color del componente de la arquitectura asociado con este abstractor
     */
    public Color3f getColor() {
    	if (appearance != null) {
	    	Color3f color = new Color3f();
	        appearance.getMaterial().getDiffuseColor(color);
	        return color;
    	}
    	return null;
    }

    /**
     * Setea la vista del componente al cual este abstractor abstrae. 
     * @param componentView Vista a la cual este abstractor abstrae.
     */
    public void setComponentView(J3DComponent componentView) {
        this.componentView = componentView;
    }

    /**
     * Retorna la vista del componente al cual este abstractor abstrae. 
     * @return J3DComponent Vista a la cual este abstractor abstrae.
     */
    public J3DComponent getComponentView() {
        return componentView;
    }

}
