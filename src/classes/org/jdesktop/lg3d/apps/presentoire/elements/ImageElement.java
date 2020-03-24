package org.jdesktop.lg3d.apps.presentoire.elements;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.jdesktop.lg3d.apps.presentoire.Slide;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.w3c.dom.Node;

public class ImageElement extends Element {

	public ImageElement(Slide docSlide, Node docNode) {
		super(docSlide, docNode);
	}

	public Component3D drawElement() {
		Component3D result = getBaseComponent();
		float width = Float.parseFloat(attrs.getNamedItem("width").getNodeValue());
		float height = Float.parseFloat(attrs.getNamedItem("height").getNodeValue());
		String src = attrs.getNamedItem("src").getNodeValue();
		ImagePanel img = new ImagePanel(width, height);
		try {
			Texture image = new TextureLoader(ImageIO.read(slide.getDocument().getZIPInputStream(src))).getTexture();
			SimpleAppearance app = new SimpleAppearance(0.0f, 0.0f, 0.0f);
			app.setTexture(image);
			img.setAppearance(app);
			result.addChild(img);
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
