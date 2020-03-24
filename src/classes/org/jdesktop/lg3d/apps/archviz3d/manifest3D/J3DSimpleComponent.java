package org.jdesktop.lg3d.apps.archviz3d.manifest3D;

import java.util.Observable;

import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.sg.Node;

import org.jdesktop.lg3d.apps.archviz3d.manifest3D.BuildStrategy3D;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;

public class J3DSimpleComponent extends J3DComponent {
	/** Nodo que encapsula esta clase. Tiene que ser de tipo
	 * Node para poder encapsular la clase Primitive como la
	 * Shape3D.*/ 
    private Node node = null;

    /**
     * Instancia un componente simple
     * @param node nodo que contiene este componente
     */
    public J3DSimpleComponent(Node node) {
        super();
        setShape(node);
    }

    /**
     * Instancia un componente simple
     * @param o Observable del componente (Abstractor)
     * @param builder Builder del componente
     */
    public J3DSimpleComponent(Observable o, BuildStrategy3D builder) {
        super(o, builder);
    }

    /**
     * Retorna el nodo del componente
     * @return Nodo del componente
     */
    public Node getShape() {
        return node;
    }
    
    /**
     * Setea el nodo del componente. Siempre se 
     * setea en la posicion 0.
     * 
     * @param node Nodo del componente.
     */
    public void setShape(Node node) {
        this.node = node;

        // Se tiene que agregar el nodo a un BranchGroup porque el   
        // metodo setChild reemplaza el nodo existente en la posicion
        // indicada, para ello debe eliminar dicho nodo y solo se pueden
        // eliminar objetos que sean BranchGroup. 
        BranchGroup bg =  new BranchGroup();
        bg.setCapability(BranchGroup.ALLOW_DETACH);
        bg.addChild(node);

        if (getTransformGroup().numChildren() == 0)
        	getTransformGroup().addChild(bg);
        else 
        	getTransformGroup().setChild(bg, 0);
    }

}
