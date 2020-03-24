package org.jdesktop.lg3d.apps.archviz3d.geons;

import javax.vecmath.Color3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Node;
import org.jdesktop.lg3d.utils.shape.Cylinder;

import org.jdesktop.lg3d.apps.archviz3d.geons.GeonAbstract;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DSimpleComponent;


/**
 * Esta clase representa una parte de un Link. 
 *   
 * @author Juan Feldman - Lucas Vigneau
 *
 */
public class GeonLink extends GeonAbstract {
	/** Contenedor del shape que representa al Geon.*/ 
	private J3DSimpleComponent shapeContainer;

    /**
     * Instancia un GeonLink.  
     * @param node Shape del GeonEvent.
     * @param componentName Nombre del componente de la arquitectura que sera representado
     * por este GeonLink.
     */
    public GeonLink(Node node, String componentName) {
        // Seteo el nombre del componente de la arquitectura que representa este Geon
        setName(componentName); 
        
        // Agrego el shape del link
        shapeContainer = new J3DSimpleComponent(node);
        addComponent(shapeContainer);
    }
    
    /**
     * Este metodo se llama cuando el usuario cambia el Geon 
     * o el color que representan a este componente.
     * @param geonName Nombre del geon que representa a este componente.
     * @param color Color de este componente.
     */
    public void updateImage(String geonName, Color3f color) {
		Cylinder cil = (Cylinder)shapeContainer.getShape();
		cil.getAppearance().getMaterial().setDiffuseColor(color);
    }

    /**
     * Setea la apariencia.
     * @param appearance Apariencia a setear.
     */
    public void setAppearance(Appearance appearance) {
    	((Cylinder)shapeContainer.getShape()).setAppearance(appearance);
    }
    
    /**
     * Retorna la apariencia.
     * @return Appearance Apariencia.
     */
    public Appearance getAppearance() {
    	return ((Cylinder)shapeContainer.getShape()).getAppearance();
    }
    
    /**
     * Setea el color.
     * @param color Color a setear.
     */
    public void setColor(Color3f color) {
    	((Cylinder)shapeContainer.getShape()).getAppearance().getMaterial().setDiffuseColor(color);
    }

    /**
     * Retorna el color.
     * @return Color3f Color de parte del link.
     */
    public Color3f getColor() {
    	Color3f color = new Color3f();
    	((Cylinder)shapeContainer.getShape()).getAppearance().getMaterial().getDiffuseColor(color);
    	return color;
    }

}
