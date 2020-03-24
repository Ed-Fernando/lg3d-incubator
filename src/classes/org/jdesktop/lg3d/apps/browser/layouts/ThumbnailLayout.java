/*
 * Created on Aug 16, 2005
 *
 * Some license info should go here!
 */
package org.jdesktop.lg3d.apps.browser.layouts;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.browser.Browser3DComponent;
import org.jdesktop.lg3d.utils.action.ActionBoolean;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;

public class ThumbnailLayout implements LayoutManager3D
{
  private Container3D cont;

  private ArrayList<Component3D> compList = new ArrayList<Component3D>();

  private LgEventListener mouseOver;

  public ThumbnailLayout()
  {
    mouseOver = new MouseEnteredEventAdapter(new ActionBoolean()
    {
      public void performAction(LgEventSource source, boolean mouseOver)
      {
        doMouseOver(source, mouseOver);
      }
    });
  }

  public void setContainer(Container3D cont)
  {
    this.cont = cont;
  }

  public void layoutContainer()
  {
    float width = 0.18f;
    float xSpacing = width / (compList.size());
    float x = width / 2f * -1 - (xSpacing / 2f);

    float depth = 0.3f;
    float zSpacing = depth / (compList.size());
    float z = depth / 2f * -1 - (zSpacing / 2f);
    Vector3f tempVec = new Vector3f();

    for (int ii = 0; ii < compList.size(); ii++)
    {
      Browser3DComponent comp = (Browser3DComponent) compList.get(ii);

      comp.changeScale(0.2f);
      x += (xSpacing);

      if ((compList.size()) % 2 == 0 && compList.size() / 2 == ii)
      {
        // TODO: fix this!!
      }// end if
      // NOTE: x should be zero in the middle, but we are dealing with floats,
      // just use a really small number...
      else if (x <= 0.00001)
      {
        z += zSpacing;
      }// end if
      else
      {
        z -= zSpacing;
      }// end else

      float y = (0.0001f * x * x) - comp.getPreferredSize(tempVec).y / 2f;

      comp.changeTranslation(x, y, z);
    }// end for
  }

  public void addLayoutComponent(Component3D comp, Object constraints)
  {
    compList.add(0, comp);
    comp.addListener(mouseOver);
  }

  public void doMouseOver(LgEventSource source, boolean mouseOver)
  {
    // make sure the source is a Component3D
    if (source instanceof Browser3DComponent)
    {
      Browser3DComponent comp = (Browser3DComponent) source;

      if (mouseOver)
        comp.changeScale(0.33f);
      else
        comp.changeScale(0.2f);
    }// end if
  }// end doMouseOver

  public void removeLayoutComponent(Component3D comp)
  {
    compList.remove(comp);
    comp.removeListener(mouseOver);
  }

  public boolean rearrangeLayoutComponent(Component3D comp, Object newConstraints)
  {
    assert (compList.contains(comp));
    if (newConstraints != null && newConstraints instanceof Integer)
    {
      int idx = (Integer) newConstraints;
      if (idx == Integer.MAX_VALUE)
      {
        // add to the last
        idx = compList.size() - 1;
      }
      if (compList.indexOf(comp) == idx)
      {
        return false;
      }
      compList.remove(comp);
      compList.add(idx, comp);
    }
    else
    {
      if (compList.indexOf(comp) == 0)
      {
        return false;
      }
      compList.remove(comp);
      compList.add(0, comp);
    }

    // relayout the container
    this.layoutContainer();
    return true;
  }

}
