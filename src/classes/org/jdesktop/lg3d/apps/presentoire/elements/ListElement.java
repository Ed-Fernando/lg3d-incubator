package org.jdesktop.lg3d.apps.presentoire.elements;

import javax.vecmath.Color4f;

import org.jdesktop.lg3d.apps.presentoire.Colors;
import org.jdesktop.lg3d.apps.presentoire.Slide;
import org.jdesktop.lg3d.utils.shape.GlassyText2D;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.wg.Component3D;
import org.w3c.dom.Node;

public class ListElement extends Element {

	public ListElement(Slide docSlide, Node docNode) {
		super(docSlide, docNode);
	}

	public Component3D drawElement() {
		Component3D result = getBaseComponent();
		float width = Float.parseFloat(attrs.getNamedItem("width").getNodeValue());
		float height = Float.parseFloat(attrs.getNamedItem("height").getNodeValue());
		String color = attrs.getNamedItem("color").getNodeValue();
		Color4f colorVector = Colors.getColor(color);
		Node child;
		int count = 0;
		child = node.getFirstChild();
		while (child != null) {
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals("li")) {
					count++;
				}
			}
			child = child.getNextSibling();
		}
		if (count == 0)
			return null;
		child = node.getFirstChild();
		int idx = 0;
		while (child != null) {
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				if (child.getNodeName().equals("li")) {
					GlassyText2D txt2D = new GlassyText2D(child.getTextContent(), width, height/count, colorVector);
					Component3D lineCmp = new Component3D();
					// SIMPLE "style"...
					System.out.println("Ajout de la sphère");
					Component3D sphereCmp = new Component3D();
					Component3D textCmp = new Component3D();
					
			        sphereCmp.addChild(new Sphere(height/(4*count),Sphere.GENERATE_NORMALS,10, 
			                new SimpleAppearance(colorVector.x,colorVector.y,colorVector.z, 0.5f)));
					textCmp.addChild(txt2D);
					sphereCmp.setTranslation(-height/(4*count), height/(2*count), 0.0f);
					textCmp.setTranslation(0.0f, 0.0f, 0.0f);
					lineCmp.addChild(textCmp);
					lineCmp.addChild(sphereCmp);
					lineCmp.setTranslation(height/count, -height/count * idx, 0.0f);
					result.addChild(lineCmp);
					idx++;
				}
			}
			child = child.getNextSibling();
		}
		return result;

	}
	
}
