package org.jdesktop.lg3d.apps.archviz3d.geons.wrappers;

import java.util.Observable;

import javax.vecmath.Color3f;

import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.geons.components.GeonRepaired;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.TransparencyAttributes;



/**
 * Clase padre de todos los Wrappers.
 * 
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public abstract class GeonWrapper extends J3DCompositeComponent {
	/** Contenedor para los componentes del cluster que el wrapper envuelve.*/
	protected J3DComponent nodes = null;
	/** Contenedor del Geon wrapper.*/
	protected J3DComponent wrapper = null;
	/** Label del wrapper.*/
	protected J3DComponent label = null;
	/** Imagen del Geon para el wrapper.*/
	protected Shape3D shapeWrapper;
	/** Escala del wrapper.*/
	protected float scale = 1.05f;
	/** Radio del wrapper.*/
	protected float radius = 0;
	/**Nombre del Componente al cual pertenece el wrapper*/
	protected String componentName;
	
	/**
	 * Crea un Wrapper. 
	 * @param nodes Componentes a los que el wrapper envuelve.
	 * @param componentName Nombre del componente de la arquitectura asociado con
	 * el wrapper.
	 */
	public GeonWrapper(J3DComponent nodes, String componentName) {
		this.nodes = nodes;
		nodes.setTranslation(0f, 0.12f, 0f);
	    
		// Seteo capacidades de la Apariencia 
	    Appearance apNode = getAppearance();
		apNode.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_READ);
		apNode.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);

		// Creo la Transparencia 
		TransparencyAttributes tra = new TransparencyAttributes(TransparencyAttributes.FASTEST, 0.6f);
		tra.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
		
		// Seteo la Transparencia 
		apNode.setTransparencyAttributes(tra);
		
		this.scale = 1.15f;
		this.componentName = componentName;
		setComponent();	
	}

	/** 
	 * Construye el wrapper.
	 */ 
	public void buildWrapper(){
 	    GeonRepaired.instance().repairWrapper(getcomponentName(), wrapper);
 	    GeonRepaired.instance().repairLabel(getcomponentName(), label);
	}
    
	public void removeComponent(J3DComponent child) {
		if (child != null) 
			getTransformGroup().removeChild(child);
    }
	
	public void update(Observable o, Object arg) {
		this.removeComponent(wrapper);
		this.removeComponent(label);
		buildWrapper();
    }
	
///******************************************************************************************************/
///*										METODOS GET Y SET 											  */ 
///******************************************************************************************************/
	/**
	 * Devuelve el radio del wrapper
	 */
	public float getRadius() {
		return radius;
	}
	
	/**
	 * Setea el radio del wrapper.
	 * @param radius Radio del wrapper.
	 */
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public void setComponent() {
		if (nodes != null) { 
			this.removeComponent(wrapper);
			this.removeComponent(nodes);
			nodes.deleteObserver(this);
		}
		this.addComponent(nodes);
		nodes.addObserver(this);		 
		buildWrapper();
	}
		
	/**
     * Setea la visibilidad del wrapper menos el label.
	 * @param visible Visibilidad del wrapper.
	 */
	public void setVisible(boolean visible) {
		if (wrapper != null) 
			wrapper.setVisible(visible);
	}
	
	/**
     * Retorna la visibilidad del wrapper menos el label.
	 * @return boolean Visibildad del wrapper.
	 */
	public boolean isVisible() {
		return wrapper.isVisible();
	}
	
	/**
	 * Actualiza la imagen del Geon para el wrapper. Este metodo
	 * se invoca cuando se clickea un Geon y el boton "Mapping" del
	 * menu esta seleccionado.
	 *
	 */
	public void changeWrapper() {
		getTransformGroup().removeChild(wrapper);
		getTransformGroup().removeChild(label);
    	buildWrapper();
	}
	
    /**
     * Retorna el nombre del Geon que reprensenta al wrapper
     * @return geonName Nombre del Geon que reprensenta al  
     * wrapper
     */
    public String getGeonName() {
    	return ((AbstractorCC)nodes.getSubject()).getGeonName();
    }
    
    /**
     * Retorno el color del wrapper 
     * @return color del wrapper
     */
    public Color3f getColor() {
    	Color3f color = ((AbstractorCC)nodes.getSubject()).getColor();
    	
    	if (color == null)
    		return new Color3f(1f, 0f, 0f);
    	
    	return color;
    }
    
    /**
     * Retorna la apariencia
     * @return Appearance apariencia
     */
    public Appearance getAppearance() {
    	return ((AbstractorCC)nodes.getSubject()).getAppearance();
    }
    
    /**
     * Retorna el nombre del componente asociado con el wrapper
     * @return nombre del componente
     */
    public String getcomponentName() {
    	return componentName;
    }
    
}
