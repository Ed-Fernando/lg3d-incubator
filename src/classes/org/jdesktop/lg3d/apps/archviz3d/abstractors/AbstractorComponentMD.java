package org.jdesktop.lg3d.apps.archviz3d.abstractors;

import java.util.HashMap;
import java.util.Vector;

import javax.vecmath.Point3f;

import org.module.Property;

import org.jdesktop.lg3d.apps.archviz3d.abstractors.Abstractor;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;


/**
 * Abstractor para los componentes de la vista de Modulos y de Deployment.
 *
 * @author Juan Feldman
 *
 */
public class AbstractorComponentMD extends Abstractor {
	/** Valores para los distintos niveles de abstraccion.*/
	private boolean[] abstractionsValues = null;
	/** Nivel de abstraccion.*/
	private int abstractionLevel;
    /** Lista de propiedades.*/
    private HashMap<String, String> propertyList = null;
	/** Puntos del conector.*/
	private Vector<Point3f> points = null;
	/** Tipo de conector.*/
	private int connectorType;
	/** True si se debe graficar la punta del conector, false en caso contrario.*/
	private boolean withArrow;
	/** True si se debe mostrar el nombre del componente, false en caso contrario.*/
	private boolean nameVisible;

	/**
	 * Instancia un MDAbstractor.
	 * @param componentView Componente a abstraer.
	 */
	public AbstractorComponentMD(J3DComponent componentView) {
		super(componentView);
		abstractionsValues = new boolean[4];
		abstractionLevel = 4;
		propertyList = new HashMap<String, String>();
		points = new Vector<Point3f>();
	}

	/**
	 * Setea el nivel de abstraccion.
	 * @param abstractionLevel Nivel de abstraccion.
	 */
	public void setAbstractionLevel(int abstractionLevel) {
		this.abstractionLevel = abstractionLevel;
		changeVisibility();
	}

	/** 
	 * Aumenta el nivel de abstraccion.
	 */
	public void increaseAbstractionLevel() {
		if (abstractionLevel < 4) {
			abstractionLevel++;
			changeVisibility();
		}
	}

	/** 
	 * Disminuye el nivel de abstraccion.
	 */
	public void decreaseAbstractionLevel() {
		if (abstractionLevel > 1) {
			abstractionLevel--;
			changeVisibility();
		}
	}

	/**
	 * Cambia la visibilidad del componente al cual
	 * este abstractor abstrae.
	 */
	private void changeVisibility() {
		getComponentView().setVisible(abstractionsValues[abstractionLevel - 1]);
	}
	
    /**
     * Setea la lista de propiedades del componente.
     * @param propertyList Lista de propiedades del componente. 
     */
    public void setProperties(Property[] propertyList) {
    	this.propertyList.clear();       
    	
    	for (int i = 0; i < propertyList.length; i++)
    		this.propertyList.put(propertyList[i].getName(), propertyList[i].getValue());
    }

    /**
     * Retorna todas las propiedades del componente.
     * @return HashMap<String, String> Propiedades del componente.
     */
	public HashMap<String, String> getProperties() {
    	return propertyList;
	}
	
    /**
     * Agrega un punto del conector.
     * @param point Punto del conector. 
     */
    public void addPoint(Point3f point) {
    	points.add(point);
    }

    /**
     * Retorna los puntos del conector.
     * @return Vector Puntos del conector.
     */
	public Vector<Point3f> getPoints() {
    	return points;
	}
	
    /**
     * Setea los valores de los niveles de abstraccion.
     * @param abstractionsValues Valores de los niveles de abstraccion. 
     */
    public void setAbstractionsValues(boolean[] abstractionsValues) {
    	this.abstractionsValues = abstractionsValues.clone();
    }

    /**
     * Setea el tipo de conector.
     * @param connectorType Tipo de conector. 
     */
    public void setConnectorType(int connectorType) {
    	this.connectorType = connectorType;
    }

    /**
     * Retorna el tipo de conector.
     * @return int El tipo de conector.
     */
	public int getConnectorType() {
    	return connectorType;
	}
	
    /**
     * Setea el valor que indica si se debe graficar la punta del conector.
     * @param withArrow Valor que indica si se debe graficar la punta 
     * del conector. 
     */
    public void setWithArrow(boolean withArrow) {
    	this.withArrow = withArrow;
    }

    /**
     * Retorna el valor que indica si se debe graficar la punta del conector.
     * @return boolean Valor que indica si se debe graficar la punta del conector.
     */
	public boolean getWithArrow() {
    	return withArrow;
	}
	
    /**
     * Setea el valor que indica si se debe mostrar el nombre del componente.
     * @param nameVisible Valor que indica si se debe mostrar el nombre del componente. 
     */
    public void setNameVisible(boolean nameVisible) {
    	this.nameVisible = nameVisible;
    }

    /**
     * Retorna el valor que indica si se debe mostrar el nombre del componente.
     * @return boolean Valor que indica si se debe mostrar el nombre del componente.
     */
	public boolean getNameVisible() {
    	return nameVisible;
	}
	
}
