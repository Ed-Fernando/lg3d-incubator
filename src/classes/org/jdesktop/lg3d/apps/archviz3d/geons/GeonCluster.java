package org.jdesktop.lg3d.apps.archviz3d.geons;

import java.util.Enumeration;
import java.util.Observable;

import javax.vecmath.Color3f;

import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderCluster;
import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventClick;
import org.jdesktop.lg3d.apps.archviz3d.geons.wrappers.GeonWrapper;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Node3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.LayoutStrategy3D;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;



/**
 * Esta clase representa un Geon compuesto. 
 * Los Geones compuestos pueden contener otros geones.
 * 
 * @author Juan Feldman - Lucas Vigneau
 * 
 */
public class GeonCluster extends Node3D {
	/** Contenedor para los componentes de este cluster.*/
	private J3DCompositeComponent nodes; 
    /** Wrapper asociado con el cluster.*/
    private GeonWrapper wrapper;
    
/** VARIABLES PARA LA ANIMACION DEL CASO DE USO **/
	/** Valor para modificar la transparencia.*/
	private float valueAnimation;
	/** Valor para animar el cluster.*/
	private float originalTransparencyValue;

	/**
     * Se instancia un Geon.  
     * @param o Abstractor. 
     * @param builder Builder.
     * @param componentName Nombre del componente de la arquitectura.
     */
    public GeonCluster(Observable o, BuildStrategy3D builder, String componentName) {
    	// Seteo el Abstractor
        this.setSubject(o);
    	
        // Seteo el nombre del componente de la arquitectura que representa este Geon
    	this.setName(componentName);
		
		// Creo el contenedor para los hijos del cluster
		nodes = new J3DCompositeComponent(o, null);
		
		// Creo el wrapper y lo agrego al cluster  
		wrapper = createWrapper();
		super.addComponent(wrapper);
		
		// Agrego el evento del click (para poder seleccionar componentes 
		// de la arquitectura)
		this.addListener(new MouseClickedEventAdapter(GeonEventClick.instance()));
        this.setMouseEventPropagatable(true);

		// Seteo el builder y construyo el Cluster
        this.builder = builder;
        this.build();
    }
    
    /**
     * Crea el wrapper asociado con el cluster.
     * @return GeonWrapper Wrapper asociado con el cluster.
     */
    @SuppressWarnings("unchecked")
	private GeonWrapper createWrapper() {
	    // Obtengo el wrapper para el cluster 
    	GeonWrapper gw = null;
    	try {
    		gw = (GeonWrapper)((AbstractorCC)getSubject()).getWrapperClass().getConstructor(new Class[]{J3DComponent.class, String.class}).newInstance(new Object[]{nodes, getName()});
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
	    
        // Devuelvo el wrapper construido 
	    return gw;
    }
    
    /**
     * Retorna el wrapper asociado con el cluster.
     * @return GeonWrapper Wrapper asociado con el cluster.
     */
    public GeonWrapper getWrapper() {
    	return wrapper;
    }
    
    /**
     * Setea el layout.
     * @param layout Layout a setear.
     */
    public void setLayout(LayoutStrategy3D layout) {
        nodes.setLayout(layout);        
    }
    
    /**
     * Agrega un componente al composite.
     * @param child Componente a agregar.
     */
    public void addComponent(J3DComponent child) {
        nodes.addComponent(child);
        child.addObserver(this);
        child.addObserver(wrapper);
    }
    
    /**
     * Agrega eventos al cluster. 
     * @param child Eventos del cluster.
     */
    public void addEvents(J3DCompositeComponent child) {
        super.addComponent(child);
    }
    
    /**
     * Retorna todos los componentes del composite.
     * @return Enumeration con los componentes del composite.
     */
    public Enumeration getAllComponents() {
        return nodes.getAllComponents();
    }
        
    /**
     * Retorna el componente que se encuentra en la posicion
     * index del composite.
     * @param index Posicion del componente que se desea obtener.
     * @return J3DComponent Componente retornado.
     */
    public J3DComponent getComponent(int index) {
        return nodes.getComponent(index);
    }
    
    /**
     * Elimina el componente que se encuentra en la posicion
     * index del composite.
     * @param index Posicion del componente dentro del composite
     * que se desea eliminar.
     */
    public void removeComponent(int index) {
        nodes.removeComponent(index);
    }
    
    /**
     * Elimina del composite al componente pasado como parametro.
     * @param child Componente a eliminar.
     */
    public void removeComponent(J3DComponent child) {
    	nodes.removeComponent(child);
    	child.deleteObserver(this);
        child.deleteObserver(wrapper);
    }

    /**
     * Elimina todos los componentes del composite.
     */
    public void removeAllComponents() {
        nodes.removeAllComponents();
    }    
    
    /**
	 * Setea la visibilidad de todo el Cluster menos el label.
	 * @param visible Visibilidad.
	 */
	public void setVisiblePart(boolean visible) {
		wrapper.setVisible(visible);
	}
	
    /**
     * Retorna la visibilidad del todo el Cluster menos el label.
	 * @return boolean Visibildad.
     */
    public boolean isVisiblePart() {
		return wrapper.isVisible();
	}

    /**
     * Este metodo se llama cuando el usuario cambia el Geon 
     * o el color que representan a este componente.
     * @param geonName Nombre del geon que representa a este componente.
     * @param color Color de este componente.
     */
    public void updateImage(String geonName, Color3f color) {
    	super.updateImage(geonName, color);
		
		// Actualizo el wrapper
		getWrapper().changeWrapper();

		// Actualizo los eventos del cluster
		((GeonBuilderCluster)getBuilder()).rebuildEvents(this);
    }
    
/******************************************************************************************************/
/*					INTERFACE PARA EJECUTAR UN CASO DE USO EN UN COMPONENTE				  			  */ 
/******************************************************************************************************/
	/**
	 * Se salva el valor a modificar durante la animacion del caso de uso,
	 * para que una vez finalizado se restaure el valor original.
	 */
	public void saveOriginalValue() {
		originalTransparencyValue = getAppearance().getTransparencyAttributes().getTransparency();
	}

	/**
     * Retorna la transparencia del componente.
     * @return Object Transparencia a modificar.
     */
	public Object getValueToChange() {
		float transparencyValue = getAppearance().getTransparencyAttributes().getTransparency();
		valueAnimation = 0.015f;
		if (transparencyValue > 0.8f)
			valueAnimation = -valueAnimation;
		
		return new Float(getAppearance().getTransparencyAttributes().getTransparency());
	}
	
	/**
	 * Se ejecutan las acciones que correspondan para la animacion
	 * del caso de uso.
	 * @param value Valor de la transparencia a cambiar.
	 * @param loopNumber numero de iteracion.
	 * @return Object Nuevo valor de la transparencia .
	 */
	public Object loopAction(Object value, int loopNumber) {
		float valueTransparency = ((Float)value).floatValue() + valueAnimation;
		Object newValue = new Float(valueTransparency);
		return newValue; 
	}
	
	/**
	 * Se aplican los cambios realizados en el metodo
	 * <b>loopAction</b>
	 * @param value Nueva transparencia.
	 */
	public void applyChange(Object value) {
		getAppearance().getTransparencyAttributes().setTransparency(((Float)value).floatValue());
	}
	
	/**
	 * Se deshacen los cambios realizados en el metodo
	 * <b>applyChange</b>
	 */
	public void restore() {
		getAppearance().getTransparencyAttributes().setTransparency(originalTransparencyValue);
	}
	
}
