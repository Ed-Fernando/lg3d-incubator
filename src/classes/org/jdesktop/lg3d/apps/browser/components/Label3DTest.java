/*
 * Created on Aug 9, 2005
 *
 * Some license info should go here!
 */
package org.jdesktop.lg3d.apps.browser.components;

import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Toolkit3D;

public class Label3DTest
{
  /**
   * @param args
   */
  public static void main(String[] args)
  {
    Frame3D frame = new Frame3D();
    
    Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
    
    float physicalWidth = toolkit3d.widthNativeToPhysical(300);
    float physicalHeight = toolkit3d.heightNativeToPhysical(200);
    
    SimpleAppearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.9f, SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);

    GlassyPanel glassPanel = new GlassyPanel(physicalWidth, physicalHeight, 0.001f, 0.0f, appearance);

    Component3D comp1 = new Component3D();
    comp1.addChild(glassPanel);
    frame.addChild(comp1);

    String text = "http://www.google.com";
    Label3D label = new Label3D(text,physicalWidth, physicalHeight);
    comp1.addChild(label);

    frame.changeEnabled(true);
    frame.changeVisible(true);
  }
}
