/*
 * Created on Aug 3, 2005
 *
 * Some license info should go here!
 */
package org.jdesktop.lg3d.apps.browser.components;

import java.io.IOException;
import java.net.URL;

import javax.swing.SwingUtilities;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;

public class Button3D extends Component3D
{

  private ImagePanel imagePanel;

  private float buttonChange = 0.01f;
  private boolean depressed;

  public Button3D(URL fileName, float width, float height) throws IOException
  {
    imagePanel = new ImagePanel(fileName, width, height);
    this.addChild(imagePanel);

    // add the Mouse Pressed listener to make the button go up and down
    this.addListener(new MousePressedEventAdapter(MouseButtonEvent3D.ButtonId.BUTTON1, new ButtonPressedAction()));

  }// end Button3D

  private void depress()
  {
    // something might happen to the event
    if( depressed )
    {
      this.raise();
      return;
    }// end if
    
    Vector3f loc = new Vector3f();
    this.getTranslation(loc);
    this.setTranslation(loc.x, loc.y, loc.z - buttonChange);
    this.depressed = true;
  } // end depress

  private void raise()
  {
    Vector3f loc = new Vector3f();
    this.getTranslation(loc);
    this.setTranslation(loc.x, loc.y, loc.z + buttonChange);
    this.depressed = false;
  } // end depress

  class ButtonPressedAction implements ActionBoolean
  {
    public void performAction(LgEventSource source, boolean pressed)
    {
      if (source instanceof Button3D)
      {
        if (pressed)
        {
          ((Button3D) source).depress();
        }// end if
        else
        {
          ((Button3D) source).raise();
        }// end else
      }// end if
    }
  }// end ButtonPressedAction class
}
