/*
 * Created on Aug 9, 2005
 *
 * Some license info should go here!
 */
package org.jdesktop.lg3d.apps.browser.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Toolkit3D;

public class Label3D extends Component3D
{

  private BufferedImage bufferedImage;

  private JLabel label;

  private Graphics graphics;

  private ImagePanel imagePanel;

  private SimpleAppearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.9f, SimpleAppearance.ENABLE_TEXTURE
      | SimpleAppearance.DISABLE_CULLING);

  public Label3D(String text, float width, float height)
  {
    Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
    
    int nativeWidth = toolkit3d.widthPhysicalToNative( width );
    int nativeHeight = toolkit3d.widthPhysicalToNative( height );
    
    label = new JLabel(text);

    label.setSize(nativeWidth, nativeHeight);
    label.setVisible(true);

    imagePanel = new ImagePanel(width, height);
    imagePanel.setAppearance(appearance);

    this.paintComponent();

    this.addChild(imagePanel);
  }// end Label3D

  private void paintComponent()
  {
    // TODO: this is going to be slow... can i make the image Transparent another way?
    this.bufferedImage = new BufferedImage(label.getWidth(), label.getHeight(), BufferedImage.TYPE_INT_ARGB);
    graphics = bufferedImage.getGraphics();

    label.paint(graphics);
    Texture texture = new TextureLoader(bufferedImage).getTexture();
    appearance.setTexture(texture);
  }// end paintComponent

  public void setText(String text)
  {
    label.setText(text);
    this.paintComponent();
  }// end setText

  public String getText()
  {
    return label.getText();
  }// end getText

}
