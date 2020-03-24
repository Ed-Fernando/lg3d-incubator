package org.jdesktop.lg3d.apps.presentoire.elements;

import org.jdesktop.lg3d.apps.presentoire.Slide;
import org.jdesktop.lg3d.wg.Component3D;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class Element {
	protected Node node = null;
	protected Slide slide = null;
	protected NamedNodeMap attrs = null;
	
	public Element (Slide docSlide, Node docNode) {
		node = docNode;
		slide = docSlide;
		attrs = node.getAttributes();
	}
	
	public Component3D getBaseComponent() {
		// Do the basic "parsing"... X, Y, Z
		// Later, we'll parse onclick events for instance...
		Component3D result = new Component3D();
		float x = 0.0f;
		if (attrs.getNamedItem("x") != null) {
				x = Float.parseFloat(attrs.getNamedItem("x").getNodeValue());
		}
		float y = 0.0f;
		if (attrs.getNamedItem("y") != null) {
			y = Float.parseFloat(attrs.getNamedItem("y").getNodeValue());
		}
		float z = 0.01f; //Protection against a kind of flickering...
		if (attrs.getNamedItem("z") != null) {
			z = Float.parseFloat(attrs.getNamedItem("z").getNodeValue());
		}
		result.setTranslation(x, y, z);
		return result;
	}
	
	public Component3D drawElement() {
		return null;
	}
}
