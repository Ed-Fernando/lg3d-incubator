package org.jdesktop.lg3d.apps.archviz3d.geons;

import java.util.Observable;
import java.util.Vector;

import javax.vecmath.Color3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;

import org.jdesktop.lg3d.apps.archviz3d.abstractors.AbstractorCC;
import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderSur;
import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventClick;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DSimpleComponent;


/**
 * Esta clase representa los eventos de los componentes de la arquitectura. 
 *   
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public class GeonEvent extends GeonAbstract {
	/** Contenedor del shape que repsenta al Geon.*/ 
	private J3DSimpleComponent shapeContainer;

    /**
     * Instancia un GeonEvent.  
     * @param o Abstractor. 
     * @param node Shape del GeonEvent.
     * @param eventName Nombre del evento de la arquitectura que sera representado
     * por este GeonEvent.
     */
    public GeonEvent(Observable o, Shape3D node, String eventName) {
    	// Seteo el Abstractor
        this.setSubject(o);
    	
        // Seteo el nombre del evento de la arquitectura que representa este Geon
        setName(eventName); 
        
		// Agrego el evento del click (para poder seleccionar componentes 
		// de la arquitectura)
        this.addListener(new MouseClickedEventAdapter(GeonEventClick.instance()));
        this.setMouseEventPropagatable(true);
        
        // Agrego el shape del evento
        shapeContainer = new J3DSimpleComponent(node);
        addComponent(shapeContainer);
    }
    
    /**
     * Este metodo se llama cuando el usuario cambia el Geon 
     * o el color que representan a este componente
     * @param geonName nombre del geon que representa a este componente.
     * @param color color de este componente.
     */
    public void updateImage(String geonName, Color3f color) {
    	super.updateImage(geonName, color);

    	// Creo la apariencia para el Geon
    	Appearance apNode = Factory3D.instance().createAppearance(color);

    	// Creo el shape para el Geon y lo seteo
		Shape3D geonShape = GeonBuilderSur.instance().buildGeonByName(geonName);
		geonShape.setAppearance(apNode);
		shapeContainer.setShape(geonShape);
    }
    
    /**
     * Setea la lista de geones que pueden representar a este componente.
     * @param geonsList Lista de geones que pueden representar a este componente.
     */
    public void setGeonsList(String[] geonsList) {
    	((AbstractorCC)getSubject()).setGeonsEventList(geonsList);
    }
      
    /**
     * Retorna la lista de geones que pueden representar a este componente.
     * @return Lista de geones que pueden representar a este componente.
     */
    public Vector<String> getGeonsList() {
    	return ((AbstractorCC)getSubject()).getGeonsEventList();
    }
    
    /**
     * Setea la apariencia.
     * @param appearance Apariencia a setear.
     */
    public void setAppearance(Appearance appearance) {
    	((Shape3D)shapeContainer.getShape()).setAppearance(appearance);
    }
    
    /**
     * Retorna la apariencia.
     * @return Appearance Apariencia.
     */
    public Appearance getAppearance() {
    	return ((Shape3D)shapeContainer.getShape()).getAppearance();
    }
    
    /**
     * Setea el color. 
     * @param color Color a setear.
     */
    public void setColor(Color3f color) {
    	getAppearance().getMaterial().setDiffuseColor(color);
    	((AbstractorCC)getSubject()).setEventColor(getName(), color);
    }

    /**
     * Retorna el color del componente. 
     * @return Color3f Color del componente.
     */
    public Color3f getColor() {
    	Color3f color = ((AbstractorCC)getSubject()).getEventColor(getName());

    	if (color == null) 
    		return new Color3f(1f, 0f, 0f);
        
    	return color;
    }

    /**
     * Setea el nombre del Geon que reprensenta a este componente.
     * @param geonName Nombre del Geon que reprensenta a este 
     * componente.
     */
    public void setGeonName(String geonName) {
		((AbstractorCC)getSubject()).setGeonEventName(getName(), geonName);
    }
    
    /**
     * Retorna el nombre del Geon que reprensenta a este componente.
     * @return geonName Nombre del Geon que reprensenta a este 
     * componente.
     */
    public String getGeonName() {
		return ((AbstractorCC)getSubject()).getGeonEventName(getName());
    }

}
