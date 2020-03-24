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

public class Button3DTest
{

  /**
   * @param args
   */
  public static void main(String[] args)
  {
      new Button3DTest();
  }
  
  public Button3DTest() {
    Frame3D frame = new Frame3D();
    
    Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
    
    float physicalWidth = toolkit3d.widthNativeToPhysical( 300 );
    float physicalHeight = toolkit3d.heightNativeToPhysical( 200 );
    
    float buttonWidth = toolkit3d.widthNativeToPhysical( 80 );
    float buttonHeight = toolkit3d.heightNativeToPhysical( 80 );
    
    SimpleAppearance appearance = new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.9f,
        SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
    
    GlassyPanel glassPanel = new GlassyPanel(physicalWidth, physicalHeight, 0.001f, 0.0f, appearance);
    
    Component3D comp1 = new Component3D();
    Component3D comp2 = new Component3D();
    comp2.addChild(glassPanel);
    frame.addChild( comp2 );
    
    try
    {
      
      Button3D button = new Button3D( getClass().getResource("org/jdesktop/lg3d/apps/browser/images/button/Left.png"), buttonWidth, buttonHeight ); 
      comp1.addChild( button );
      comp2.addChild( comp1 );
      
      Vector3f loc = new Vector3f();
      button.getTranslation( loc );
      button.setTranslation( loc.x - 0.02f , loc.y, loc.z );
      
    }// end try
    catch( IOException ioe )
    {
      ioe.printStackTrace();
    }// end catch
    
    frame.changeEnabled(true);
    frame.changeVisible(true);
  }

}
