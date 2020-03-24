package org.jdesktop.lg3d.apps.archviz3d.manifest3D;

import java.util.Observable;

import org.jdesktop.lg3d.sg.BoundingBox;
import org.jdesktop.lg3d.sg.BoundingSphere;

import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.layouts.LayoutStrategy3D;


import java.util.Enumeration;

import javax.vecmath.Point3f;

public class J3DCompositeComponent extends J3DComponent {
	/** Estrategia del layout para el composite.*/
    private LayoutStrategy3D layout;

    /** 
     * Crea un J3DCompositeComponent 
     */
    public J3DCompositeComponent() {
    	super();
    }

    /** 
     * Crea un J3DCompositeComponent 
     * @param o Abstractor
     * @param builder
     */
    public J3DCompositeComponent(Observable o, BuildStrategy3D builder) {
    	super(o, builder);
    }
    
    /**
     * Aplica la estrategia de layout en el composite. 
     */
    public void doLayout() {
        if (getLayout() != null) {
        	getLayout().layout();
            setChanged();
            notifyObservers();
        }
    }
    
    /**
     * Retorna la estrategia de layout.
     * @return LayoutStrategy3D estrategia de layout.
     */
    public LayoutStrategy3D getLayout() {
    	return layout;
    }

    /**
     * Setea la estrategia de layout.
     * @param layout estrategia de layout.
     */
    public void setLayout(LayoutStrategy3D layout) {
    	this.layout = layout;
        layout.setClient(this);
        doLayout();
    }

    /**
     * Agrega un componente.
     * @param child componente a agregar.
     */
    public void addComponent(J3DComponent child) {
        child.setParent(this);
        getTransformGroup().addChild(child);
        doLayout();
    }

    /**
     * Reemplaza el componente que se encuentra en la
     * posicion index por el componente que se recibe 
     * como parametro.
     * @param child componente a agregar.
     * @param index indice en donde se agregara el componente
     */
    public void setComponent(J3DComponent child, int index) {
        child.setParent(this);
        getTransformGroup().setChild(child, index);
        doLayout();
    }

    /**
     * Inserta el componente componente que se recibe 
     * como parametro en la posicion index.
     * @param child componente a agregar.
     * @param index indice en donde se agregara el componente
     */
    public void insertComponent(J3DComponent child, int index) {
        child.setParent(this);
        getTransformGroup().insertChild(child, index);
        doLayout();
    }

    /**
     * Elimina el componente que se encuentra en la
     * posicion index
     * @param index posicion del componente a eliminar 
     */
    public void removeComponent(int index) {
    	getTransformGroup().removeChild(index);
        doLayout();
    }
    
    /**
     * Elimina el componente que se recibe como parametro
     * @param child componente a eliminar
     */
    public void removeComponent(J3DComponent child) {
    	getTransformGroup().removeChild(child);
        doLayout();
    }

    /**
     * Elimina todos los componentes
     */
    public void removeAllComponents() {
    	getTransformGroup().removeAllChildren();
        doLayout();
    }
    
    /**
     * Retorna el componente que se encuentra en la posicion
     * index.
     * @return Node componente que se encuentra en la posicion
     * index. 
     */
    public J3DComponent getComponent(int index) {
        return (J3DComponent)getTransformGroup().getChild(index);
    }

    /**
     * Retorna todos los componentes
     * @return Enumeration todos los componentes
     */
    public Enumeration getAllComponents() {
        return getTransformGroup().getAllChildren();
    }

    /**
     * Retorna la cantidad de componentes
     * @return int cantidad de componentes
     */
    public int numComponents() {
        return getTransformGroup().numChildren();
    }

    /**
     * Retorna el BoundingBox del composite
     * @return BoundingBox boundingBox del composite
     */
    public BoundingBox getBoundingBox() {
    	BoundingBox boundingBox = new BoundingBox(new BoundingSphere(new Point3f(0.0f,0.0f,0.0f), 0.0f));
    	for (Enumeration e = getAllComponents(); e.hasMoreElements();) {
    		BoundingBox temp =((J3DComponent)e.nextElement()).getBoundingBox();
    		boundingBox.combine(temp);
    	}
    	boundingBox.transform(this.getMatrix());
    
    	return boundingBox;
    }
    
    /**
     * Reconstruye el componente y todos sus hijos 
     */
    public void rebuild() {
	    super.rebuild();
	    this.doLayout();
    }

}

