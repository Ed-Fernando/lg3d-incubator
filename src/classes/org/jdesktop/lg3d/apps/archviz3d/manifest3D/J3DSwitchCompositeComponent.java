package org.jdesktop.lg3d.apps.archviz3d.manifest3D;

import java.util.Hashtable;

import org.jdesktop.lg3d.sg.Switch;

import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DComponent;
import org.jdesktop.lg3d.apps.archviz3d.manifest3D.J3DCompositeComponent;

/**
 * @author Alfredo Teyseyre
 */
public class J3DSwitchCompositeComponent extends J3DCompositeComponent {
    private Switch switchNode;
    private Hashtable<String, Integer> names;

    public J3DSwitchCompositeComponent() {
        names = new Hashtable<String, Integer>();
        switchNode = new Switch();
        switchNode.setCapability(Switch.ALLOW_SWITCH_WRITE);
        switchNode.setCapability(Switch.ALLOW_CHILDREN_WRITE);
        getTransformGroup().addChild(switchNode);
    }

    public void addComponent(J3DComponent child, String name) {
        names.put(name, new Integer(switchNode.numChildren()));
        child.setParent(this);
        switchNode.addChild(child);
    }

    public void setComponent(J3DComponent child, String name) {
        child.setParent(this);
        switchNode.setChild(child, ((Integer) (names.get(name))).intValue());
    }

    public void switchComponent(String name) {
        switchNode.setWhichChild(((Integer) (names.get(name))).intValue());
    }

    public void setComponent(J3DComponent child, int index) {
    }


    public void insertComponent(J3DComponent child, int index) {
    }

    public void removeComponent(int index) {
    }

    public J3DComponent getComponent(int index) {
        return (J3DComponent)switchNode.getChild(index);
    }

    public java.util.Enumeration getAllComponents() {
        return switchNode.getAllChildren();
    }

    public int numComponents() {
        return switchNode.numChildren();
    }


}