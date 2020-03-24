package org.jdesktop.lg3d.apps.archviz3d.manifest3D.objects;

import java.awt.Font;

import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.archviz3d.manifest3D.Factory3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DSimpleComponent;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BoundingBox;
import org.jdesktop.lg3d.sg.BoundingSphere;
import org.jdesktop.lg3d.sg.Bounds;
import org.jdesktop.lg3d.sg.Material;
import org.jdesktop.lg3d.utils.shape.Text2D;



/** 
 * Esta clase se utiliza para colocar labels a algunos componentes de la 
 * arquitectura. 
 * 
 * @author Gaston Peirano & Mauricio Poncio
 */
public class LabeledObject3D extends J3DCompositeComponent {
    protected J3DComponent object;
    protected float percentage = 0.09f;
    protected Bounds vol;
    protected Point3f extension;
    protected Point3f centre = new Point3f();
    protected Point3f position;
    /** Apariencia para el fondo del label.*/
    protected Appearance apBox;
    /** Color para el texto del label.*/
    protected Color3f textColor;

    /**
     * Agrega un label a partir de un string para un objeto dado.
     * @param object objeto al cual se le agregara el label
     * @param label string del objeto
     */
    public LabeledObject3D(J3DComponent object, String label) {
        this(object, label, new Point3f(1.0f, 0.0f, 0.0f));
    }
    
    /**
     * Agrega un label a partir de un string para un objeto dado.
     * @param object objeto al cual se le agregara el label
     * @param label string del objeto
     * @param position posicion del LabeledObject3D
     */
    public LabeledObject3D(J3DComponent object, String label, Point3f position) {
    	this(object, label, position, new Color3f(0f, 0f, 0.8f), Factory3D.rubberYellow);
    }
    
    /**
     * Agrega un label a partir de un string para un objeto dado.
     * @param object Objeto al cual se le agregara el label.
     * @param label String del objeto.
     * @param position Posicion del LabeledObject3D.
     * @param textColor Color para el texto del label.
     * @param materialBack Material para el fondo del label.
     */
    public LabeledObject3D(J3DComponent object, String label, Point3f position, Color3f textColor, Material materialBack) {
    	this.textColor = textColor;
		
		apBox = new Appearance();
		apBox.setMaterial(materialBack);
		
    	this.position = position;
        setObject(object);
        setLabel(label);
    }
    
    /**
     * Setea el objeto al cual esta asignado el label.
     * @param object el objeto
     */
    public void setObject(J3DComponent object) {
	    if (getObject() != null)
            removeComponent(0); //siempre lo ubico en el 0
	    this.addComponent(object);
	    this.object = object;
    }
    /**
     * Devuelve el objeto al cual esta asignado el label.
     * @return el objeto
     */
    public J3DComponent getObject() {
        return object;
    }
    /**
     * Setea el porcentaje usado para crear el label ( 0.0 < percentage < = 1.0 ).
     * @param percentage nuevo porcentaje
     */
    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }
    /**
     * Devuelve el porcentaje usado para crear el label.
     * @return el porcentaje
     */
    public float getPercentage() {
        return percentage;
    }

    private void calcBounds() {
        vol = object.getBounds();  //Retorna un Volume3D que indica el espacio que ocupa el objeto en coordenadas locales
        if (vol instanceof BoundingSphere) {
            BoundingSphere vol2 = (BoundingSphere)vol;
            extension = new Point3f(vol2.getRadius()*2,vol2.getRadius()*2,vol2.getRadius()*2);
        }
        if (vol instanceof BoundingBox) {
            BoundingBox vol3 = (BoundingBox)vol;
            Point3f upper = new Point3f();
            Point3f lower = new Point3f();
            vol3.getUpper(upper);
            vol3.getLower(lower);
            upper.sub(lower);
            extension = new Point3f(Math.abs(upper.x),Math.abs(upper.y),Math.abs(upper.z)); //valor absoluto de la resta
        }
    }
    /**
     * Cambia el texto del label.
     * @param string nuevo texto
     */
    public void setLabel(String string) {
        calcBounds();

        // Creo el texto y la geometria de fondo
        J3DCompositeComponent label = new J3DCompositeComponent();
        
        // Creo el texto
        Text2D text = new Text2D(string, textColor, new Font("Arial", Font.BOLD, 26));

        // Creo la geometria de fondo que se colocara detras del texto
        float width = string.length()*0.4f;

		J3DSimpleComponent caja = Factory3D.instance().createBox(width, 0.6f, 0.1f, apBox);
        caja.setTranslation(new Vector3f(width/15, 0.06f, -0.01f));
        caja.setScale(percentage);

        // Agrego la geometria de fondo
        label.addComponent(caja);
        
        // Agrego el texto
        J3DSimpleComponent text3D = new J3DSimpleComponent(text);
        label.addComponent(text3D);
        
        addComponent(label);

        // Posiciona el label centrado arriba del objeto
        label.setTranslation ((float)(extension.x/2 * position.x), (float)(extension.y/2  * position.y), (float)(extension.z/2 * position.z));
    }

}
