package org.jdesktop.lg3d.apps.archviz3d.geons;

import java.util.Observable;

import javax.vecmath.Color3f;


import org.jdesktop.lg3d.apps.archviz3d.geons.builders.GeonBuilderSur;
import org.jdesktop.lg3d.apps.archviz3d.geons.events.GeonEventClick;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DSimpleComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.graph.Node3D;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;


/**
 * Esta clase representa un Geon simple. 
 * Los Geones simples no contienen otros Geones dentro.  
 *  
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public class GeonSimple extends Node3D {
	/** Contenedor del shape que representa al Geon.
	 *  Este variable se podria obtener haciendo lo siguiente (ver GeonBuilder.build()): 
	 *  ((J3DCompositeComponent)((LabeledObject3D)getCuerpo()).getObejct()).getComponent(0). */ 
	private J3DSimpleComponent shapeContainer = null;
	
    /**
     * Se instancia un Geon.  
     * @param o Abstractor. 
     * @param builder Builder.
     * @param componentName Nombre del componente de la arquitectura que sera representado
     * por este Geon.
     */
    public GeonSimple(Observable o, BuildStrategy3D builder, String componentName) {
    	// Seteo el Abstractor
        this.setSubject(o);
    	
        // Seteo el nombre del componente de la arquitectura que representa este Geon
        setName(componentName); 
        
		// Agrego el evento del click (para poder seleccionar componentes 
		// de la arquitectura)
        this.addListener(new MouseClickedEventAdapter(GeonEventClick.instance()));
        this.setMouseEventPropagatable(true);

        this.builder = builder;
        build();
    }
    
    /**
     * Setea el contenedor del shape que representa a este Geon.
     * @param shapeContainer Contenedor del shape que representa a este Geon.
     */
    public void setShapeContainer(J3DSimpleComponent shapeContainer) {
    	this.shapeContainer = shapeContainer;
    }
    
    /**
     * Retorna el contenedor del shape que representa a este Geon.
     * @return J3DSimpleComponent Contenedor del shape que representa a este Geon.
     */
	public J3DSimpleComponent getShapeContainer() {
		return shapeContainer;
	}
	
    /**
     * Este metodo se llama cuando el usuario cambia el Geon 
     * o el color que representan a este componente.
     * 
     * @param geonName nombre del geon que representa a este componente.
     * @param color color de este componente.
     */
    public void updateImage(String geonName, Color3f color) {
    	super.updateImage(geonName, color);

    	// Creo el shape para el Geon y lo seteo
		Shape3D geonShape = GeonBuilderSur.instance().buildGeonByName(geonName);
		geonShape.setAppearance(getAppearance());
		shapeContainer.setShape(geonShape);
    }
    
}
