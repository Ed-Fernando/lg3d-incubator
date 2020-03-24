package org.jdesktop.lg3d.apps.presentoire.elements;

import javax.vecmath.Color4f;

import org.jdesktop.lg3d.apps.presentoire.Colors;
import org.jdesktop.lg3d.apps.presentoire.Slide;
import org.jdesktop.lg3d.utils.shape.GlassyText2D;
import org.jdesktop.lg3d.wg.Component3D;
import org.w3c.dom.Node;

public class TextElement extends Element {

	public TextElement(Slide docSlide, Node docNode) {
		super(docSlide, docNode);
	}

	public Component3D drawElement() {
		Component3D result = getBaseComponent();
		float width = Float.parseFloat(attrs.getNamedItem("width").getNodeValue());
		float height = Float.parseFloat(attrs.getNamedItem("height").getNodeValue());
		String color = "black";
		if (attrs.getNamedItem("color") != null) 
			color = attrs.getNamedItem("color").getNodeValue();
		Color4f colorVector = Colors.getColor(color);
		String txt = node.getTextContent();
		if (txt.split("\n").length > 1) {
			// Several lines... means several txt2D, meaning sub components.
			// Ho, I'm going to hate this.
			Component3D multiline = new Component3D();
			String[] lines = txt.split("\n");
			int count = lines.length;
			for (int idx = 0 ; idx < count ; idx++) {
				System.out.println(lines[idx]);
				GlassyText2D txt2D = new GlassyText2D(lines[idx], width, height/count, colorVector);
				Component3D lineCmp = new Component3D();
				lineCmp.addChild(txt2D);
				lineCmp.setTranslation(0.0f, -height/count * idx, 0.0f);
				multiline.addChild(lineCmp);
			}
			result.addChild(multiline);
		} else {
			result.addChild(new GlassyText2D(txt, width, height, colorVector));
		}
		return result;
	}
	
}
