package org.jdesktop.lg3d.apps.presentoire.elements;

import org.jdesktop.lg3d.apps.presentoire.Slide;
import org.w3c.dom.Node;

public class ElementFactory {
	public static Element getElement (Slide slide, Node node) {
		if (node.getNodeName().equals("text")) {
			System.out.println("Ajout d'un noeud text");
			return new TextElement(slide, node);
		} else if (node.getNodeName().equals("img")) {
			System.out.println("Ajout d'un noeud img");
			return new ImageElement(slide, node);
		} else if (node.getNodeName().equals("ul")) {
			System.out.println("Ajout d'un noeud text");
			return new ListElement(slide, node);
		} else {
			System.out.println("Unknown element :" + node.getNodeName());
			return null;
		}
	}
}
