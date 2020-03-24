/*
 * Created on Aug 16, 2005
 *
 * Some license info should go here!
 */
package org.jdesktop.lg3d.apps.browser.layouts;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.browser.Browser3DComponent;
import org.jdesktop.lg3d.utils.eventaction.Component3DMover;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.LayoutManager3D;

public class BrowserLayout implements LayoutManager3D
{
  private Container3D cont;
  private ArrayList<Component3D> compList = new ArrayList<Component3D>();
  
  private Component3DMover componentMover = new Component3DMover();
  
  public void setContainer(Container3D cont)
  {
    this.cont = cont;
  }

  public void layoutContainer()
  {
    for( int ii=0; ii<compList.size(); ii++ )
    {
//    draw compList[0] as the main panel
      Browser3DComponent comp = (Browser3DComponent)compList.get(ii);
            
      if( comp.getScale() < 0.98f )
      {
        comp.changeScale(1f);
        
        Vector3f tempVec = new Vector3f();
        comp.getPreferredSize(tempVec);
        comp.setTranslation(0, tempVec.y/4f, 0);
      }// end if
    }// end for
  }
  
  public void addLayoutComponent(Component3D comp, Object constraints)
  {
    compList.add(0, comp);
    comp.addListener( componentMover );
  }

  public void removeLayoutComponent(Component3D comp)
  {
    compList.remove(comp);
    comp.removeListener( componentMover );
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
    
//  relayout the container
    this.layoutContainer();
    return true;
  }
}
