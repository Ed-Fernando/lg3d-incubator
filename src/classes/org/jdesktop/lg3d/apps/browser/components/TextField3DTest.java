/*
 * Created on Aug 3, 2005
 *
 * Some license info should go here!
 */
package org.jdesktop.lg3d.apps.browser.components;

import java.io.IOException;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Toolkit3D;

public class TextField3DTest
{

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    Frame3D frame = new Frame3D();
    
    Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
    
//    float physicalWidth = toolkit3d.widthNativeToPhysical( 300 );
//    float physicalHeight = toolkit3d.heightNativeToPhysical( 200 );
    
    float width = toolkit3d.widthNativeToPhysical(270);
    float height = toolkit3d.heightNativeToPhysical(50);
    
//    SimpleAppearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 5.0f,
//        SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING | SimpleAppearance.NO_GLOSS);
    
//    GlassyPanel glassPanel = new GlassyPanel(physicalWidth, physicalHeight, 0.001f, 0.0f, appearance);
    
    Component3D comp1 = new Component3D();
//    comp1.addChild(glassPanel);
    frame.addChild(comp1);
    
//    try
//    {
      
      TextField3D textField = new TextField3D( width, height ); 
      comp1.addChild( textField );
      
//      Vector3f loc = new Vector3f();
//      button.getTranslation( loc );
//      button.setTranslation( loc.x - 0.02f , loc.y, loc.z );
      
//    }// end try
//    catch( IOException ioe )
//    {
//      ioe.printStackTrace();
//    }// end catch
    
    frame.changeEnabled(true);
    frame.changeVisible(true);
  }

}
