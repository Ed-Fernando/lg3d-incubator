package org.jdesktop.lg3d.apps.presentoire;

import org.jdesktop.lg3d.apps.presentoire.elements.Element;
import org.jdesktop.lg3d.apps.presentoire.elements.ElementFactory;
import org.jdesktop.lg3d.wg.Component3D;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Parses the slide node and renders it to a Component3D object.
 * @author Pierre Ducroquet
 *
 */
public class Slide {
	private String id, transition;
	private Node slideNode = null;
	private PresentoireDocument document;
	
	/**
	 * The constructor
	 * @param node the XML node for this slide.
	 * @param doc the document containing the slide. (Needed for images for instance)
	 */
	public Slide (Node node, PresentoireDocument doc) {
		document = doc;
		slideNode = node;
		NamedNodeMap attrs = node.getAttributes();
		id = attrs.getNamedItem("id").getTextContent();
		transition = "";
		if (attrs.getNamedItem("transition") != null)
			transition = attrs.getNamedItem("transition").getTextContent();
		//elements = new Vector<Element>();
	}
	
	/**
	 * @return the Id of the slide.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return the document containing this slide.
	 */
	public PresentoireDocument getDocument() {
		return document;
	}
	
	/**
	 * @return the name of the transition to show this slide.
	 */
	public String getTransition() {
		return transition;
	}
	
	/**
	 * Draw the page. Creates Elements for each element of the slide, render them in a Component3D.
	 * @return a Component3D containing the slide.
	 */
	public Component3D drawPage () {
			Component3D result = new Component3D();
			Node child = slideNode.getFirstChild();
			while (child != null) {
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element elmt = ElementFactory.getElement(this, child);
					if (elmt != null) {
						result.addChild(elmt.drawElement());
					}
				}
				child = child.getNextSibling();
			}
		return result;
	}
}
