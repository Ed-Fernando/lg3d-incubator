/*
 * Created on Jul 28, 2005
 *
 * Some license info should go here!
 */
package org.jdesktop.lg3d.apps.browser;

import java.io.IOException;

import org.jdesktop.lg3d.apps.browser.layouts.BrowserLayout;
import org.jdesktop.lg3d.apps.browser.layouts.ThumbnailLayout;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Frame3D;

/**
 * used only for debuging.
 * 
 * TODO: remove this from CVS
 * 
 * @author demers
 */
public class Test3D
{
  private Container3D browserContainer = new Container3D();
  private Container3D thumbnailContainer = new Container3D();
  
  public Test3D() throws ClassNotFoundException, IOException
  {
    Frame3D frame = new Frame3D();
    
    Browser3D browser = Browser3DFactroy.getBrowser3D();
    browser.addBrowser3DUIAdapter(new TestAdapter());

    int width = 800;
    int height = 500;
    SimpleAppearance backApp = new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.9f, SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);

    Browser3DComponent mainComp = new Browser3DComponent( width, height, backApp, null );
    mainComp.setMaximized(true);
    
    Browser3DComponent thumbComp =  new Browser3DComponent( width, height, backApp, getClass().getResource("/org/jdesktop/lg3d/apps/browser/images/loonmtn.png"));
 
    Container3D cont = new Container3D();

    browserContainer.setLayout( new BrowserLayout() );
    thumbnailContainer.setLayout( new ThumbnailLayout() );
    
    cont.addChild( browserContainer );
    cont.addChild( thumbnailContainer );
          
    browserContainer.addChild( mainComp );
    thumbnailContainer.addChild( thumbComp );
    
    frame.addChild( cont );
    
    frame.changeEnabled( true );
    frame.changeVisible( true );
  }// end Test3D

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception
  {
    new Test3D();
  }
  
  
  
}
