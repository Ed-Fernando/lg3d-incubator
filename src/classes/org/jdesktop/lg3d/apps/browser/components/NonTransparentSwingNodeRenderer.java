package org.jdesktop.lg3d.apps.browser.components;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.displayserver.nativewindow.NativeWindowFuzzyEdgePanel;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.PolygonAttributes;
import org.jdesktop.lg3d.sg.Texture2D;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.wg.SwingNodeRenderer;
import org.jdesktop.lg3d.wg.Toolkit3D;

public class NonTransparentSwingNodeRenderer extends SwingNodeRenderer
{
  private Appearance swingAppearance;
  private NativeWindowFuzzyEdgePanel body;

  public NonTransparentSwingNodeRenderer()
  {
    width3D = 0.08f;
    height3D = 0.06f;

    swingAppearance = new Appearance();
    TextureAttributes texAttr = new TextureAttributes();
    texAttr.setTextureMode(TextureAttributes.REPLACE);
    swingAppearance.setTextureAttributes(texAttr);
    swingAppearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
    swingAppearance.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE, 0.0f, false, 0.0f));

    // swingAppearance.setTransparencyAttributes(
    // new TransparencyAttributes(TransparencyAttributes.FASTEST, 0.8f));
    // Material mat = new Material(new Color3f(1f,0f,0f), new Color3f(1f,0f,0f),
    // new Color3f(1f,0f,0f), new Color3f(1f,0f,0f), 64f);
    // swingAppearance.setMaterial(mat);

    body = new NativeWindowFuzzyEdgePanel(width3D, height3D, swingAppearance);
    addChild(body);
  }

  public void textureChanged(Texture2D texture)
  {
    int swingImageWidth = panel.getWidth();
    int swingImageHeight = panel.getHeight();
    float p2width = texture.getWidth();
    float p2height = texture.getHeight();
    Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
    float localWidth = toolkit3d.widthNativeToPhysical(panel.getWidth());
    float localHeight = toolkit3d.heightNativeToPhysical(panel.getHeight());

    swingAppearance.setTexture(texture);
    if (localWidth != width3D || localHeight != height3D)
    {
      width3D = localWidth;
      height3D = localHeight;
      body.setSize(width3D, height3D, p2width / (float) swingImageWidth, p2height / (float) swingImageHeight);
//      setPreferredSize(new Vector3f(width3D, height3D, 0f));
    }
  }
}
