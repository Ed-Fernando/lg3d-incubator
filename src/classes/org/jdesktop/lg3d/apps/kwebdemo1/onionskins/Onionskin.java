/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/onionskins/Onionskin.java,v 1.2 2005-06-24 19:57:01 paulby Exp $
 * $Date: 2005-06-24 19:57:01 $
 *
 * Joint Copyright (c) 2005 by
 *   James A. Zaun, Consultant,
 *   The Burke Institute,
 *   Sun Microsystems, Inc.
 * ALL RIGHTS RESERVED.
 *
 * The contents of this file are subject to the GNU General Public
 * License, Version 2 (the "License"); you may not use this file
 * except in compliance with the License. A copy of the License is
 * available at http://www.opensource.org/licenses/gpl-license.php.
 */

package org.jdesktop.lg3d.apps.kwebdemo1.onionskins;

import java.util.ArrayList;
import java.util.Enumeration;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.sg.Material;
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.sg.TransformGroup;
import org.jdesktop.lg3d.sg.TransparencyAttributes;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.apps.kwebdemo1.singletons.NodeData;
import org.jdesktop.lg3d.apps.kwebdemo1.nodes.BasicNode;

/**
 * Base class for onionskins.
 * Manages the common scenegraph elements and animations associated with each
 * onionskin (era in history).
 * 
 * <p>This class should not be instantiated directly. Rather, it should be
 * extended by a century specific subclass.</p>
 * 
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.2 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator} 0.7.0
 */
public class Onionskin extends Object
{
  /**
   * Century number this onionskins is associated with like 18 for 18th
   * century. 0 means a gateway skin.
   */  
  protected int century = -1;
  /**
   * radius of this nested onionskin and vector distance of associated nodes
   * from the center of the KWeb graph.
   */
  protected float radius = 0f;
  /**
   * Color of this nested onionskin and associated nodes.
   */
  protected Color3f color = null;

  /**
   * This skins scenegraph branch group
   */
  protected BranchGroup onionBG = null;
  
  /**
   * Appearance applied to this skin
   */
  protected Appearance onionApp = null;
  
  /**
   * Material associated with our appearance
   */
  protected Material onionMat = null;
  
  /**
   * Transparency Attributes associated with out appearance.
   */
  protected TransparencyAttributes onionTrans = null;

  /**
   * Constructor
   * 
   * @param century Century number like 17 for 17th century or 0 for
   *   gateway nodes.
   * @param radius radius in meters of the onionskin for this century.
   * @param color RGB color of node sphere. Genrally all the nodes in a
   *   particular onionskin are the same color to help identify onionskins
   *   or centuries visually.
   */
  public Onionskin(int century, float radius, Color3f color)
  {
    this.century = century;
    this.radius = radius;
    this.color = color;
  }
  
/**
 * Creates a BranchGroup that can be included in the overall
 * KWeb graph for this onionskin.
 * 
 * @return a prebuilt BranchGroup for this skin 
 */
  public BranchGroup newBranchGroup()
  {
    BranchGroup bg = new BranchGroup();
    Appearance app = new Appearance();
    Material mat = new Material();
    app.setMaterial(mat);
    mat.setSpecularColor(color);
    mat.setDiffuseColor(color);
    NodeData nodeData = NodeData.getInstance();
    Enumeration<BasicNode> nodeList;
    for (nodeList = nodeData.elements(); nodeList.hasMoreElements(); )
      {
        BasicNode node = nodeList.nextElement();
        if (node.getCentury() == century)
          {
            TransformGroup tg = node.newTransformGroup(radius, app);
            bg.addChild(tg);
          }
      }
    return bg;
  }

  /**
   * Turns on/off display of node labels for this onionskin.  Used mostly to
   * reduce clutter and information overload.
   * 
   * @param show true to display node labels
   */
  public void showLabels(boolean show)
  {
    NodeData nodeData = NodeData.getInstance();
    Enumeration<BasicNode> nodeList;
    for (nodeList = nodeData.elements(); nodeList.hasMoreElements(); )
      {
        BasicNode node = nodeList.nextElement();
        if (node.getCentury() == century) node.showLabel(show);
      }
  }

  /**
   * Makes the onionskin itself. We were hoping that the nodes and links alone
   * would provide enough visual context that one could discern one onionskin
   * from the next with color coding.  But, such was not the case. So, it
   * was necessary to show the onionskins more directly.
   *   
   * @return a scenegraph BranchGroup for the onionskin itself.
   */
  public BranchGroup newTransparentSphereGroup()
  {
    onionBG = new BranchGroup();
    onionBG.setPickable(false);
    onionApp = new Appearance();
    onionApp.setCapability(Appearance.ALLOW_TRANSPARENCY_ATTRIBUTES_WRITE);
    onionMat = new Material();
    onionTrans = new TransparencyAttributes();
    onionTrans.setCapability(TransparencyAttributes.ALLOW_MODE_WRITE);
    onionTrans.setCapability(TransparencyAttributes.ALLOW_VALUE_WRITE);
    onionTrans.setTransparency(0.8f);
    onionTrans.setTransparencyMode(TransparencyAttributes.NONE);
    onionApp.setMaterial(onionMat);
    onionApp.setTransparencyAttributes(onionTrans);
    onionMat.setSpecularColor(color);
    onionMat.setDiffuseColor(color);
    onionBG.addChild(new Sphere(radius, onionApp));
    return onionBG;    
  }

  /**
   * Makes the onionskin opaque.  This is used during the initial startup to
   * help explain what the different nested onionskins mean.  Making a skin
   * opaque has the side-effect of rendering all the inner skins invisible.
   * This is useful when it's important to focus a persons attention on this
   * skin only.
   * 
   * <p>I kind of wanted to do this with an animation but the transparency
   * change animation was working for me at the time and I had to move on
   * to bigger and better things.</p>
   */
  public void setOnionOpaque()
  {
    onionTrans.setTransparency(0.0f);
    onionTrans.setTransparencyMode(TransparencyAttributes.NONE);
    onionApp.setTransparencyAttributes(onionTrans);
  }

  /**
   * This makes the onionskin translucent so you can still see the skin but
   * you can also see the nested inner skins and nodes as well.  It's a rather
   * nice effect but creates one hell of a performance hit on the rendering
   * engine.  I'm assuming the user has a fast 64Mb 3-D GPU installed in their
   * system. 
   */
  public void setOnionTranslucent()
  {
    onionTrans.setTransparency(0.8f);
    onionTrans.setTransparencyMode(TransparencyAttributes.FASTEST);
    onionApp.setTransparencyAttributes(onionTrans);
  }

  /**
   * This makes the onionskin disappear so only the individual nodes and labels
   * are showing. This allows the nodes and labels at the back appear more
   * crisply without the <em>fog</em> created by the translucent onionskins.
   */
  public void setOnionTransparent()
  {
    onionTrans.setTransparency(1.0f);
    onionTrans.setTransparencyMode(TransparencyAttributes.FASTEST);
    onionApp.setTransparencyAttributes(onionTrans);
  }
  
  /**
   * Color of things associated with this skin.
   * 
   * @return color of nodes and edges for this skin.
   */
  public Color3f getColor()
  {
    return this.color;
  }
}
