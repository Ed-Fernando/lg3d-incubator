package org.jdesktop.lg3d.apps.archviz3d.manifest3D.objects;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.Material;

import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.objects.LabeledObject3D;



/** 
 * Esta clase se utiliza para colocar labels a algunos componentes de la 
 * arquitectura.
 *  
 * @author Gaston Peirano & Mauricio Poncio
 */
public class LabeledObjectD3D extends LabeledObject3D {

    /**
     * Crea un label a partir de un string para un objeto dado.
     * @param object Objeto al cual se le agregara el label.
     * @param label String del objeto.
     * @param position Posicion del LabeledObject3D.
     * @param textColor Color para el texto del label.
     * @param materialBack Material para el fondo del label.
     */
    public LabeledObjectD3D(J3DComponent object, String label, Point3f position, Color3f textColor, Material materialBack) {
    	super(object, label, position, textColor, materialBack);
    }
    
    /**
     * Crea un label a partir de un string para un objeto dado.
     * @param object Objeto al cual se le agregara el label.
     * @param label String del objeto.
     * @param position Posicion del LabeledObject3D.
     */
    public LabeledObjectD3D(J3DComponent object, String label, Point3f position) {
    	super(object, label, position);
    }
    
    /**
     * Setea el objeto al cual esta asignado el label.
     * @param object el objeto
     */
    public void setObject(J3DComponent object) {
	    this.object = object;
    }
    
}
