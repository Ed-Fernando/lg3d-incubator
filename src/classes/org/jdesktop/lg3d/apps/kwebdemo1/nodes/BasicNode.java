/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/nodes/BasicNode.java,v 1.3 2006-06-21 20:39:22 hideya Exp $
 * $Date: 2006-06-21 20:39:22 $
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

package org.jdesktop.lg3d.apps.kwebdemo1.nodes;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.AlphaComposite;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.QuadArray;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.TransformGroup;
import org.jdesktop.lg3d.sg.Switch;
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.OrientedShape3D;
import org.jdesktop.lg3d.sg.ImageComponent;
import org.jdesktop.lg3d.sg.ImageComponent2D;
import org.jdesktop.lg3d.sg.Texture2D;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.shape.Sphere;
import org.jdesktop.lg3d.apps.kwebdemo1.singletons.NodeData;
import org.jdesktop.lg3d.apps.kwebdemo1.edges.DisplayedEdge;
import org.jdesktop.lg3d.sg.Node;

/**
 * Base class for nodes.  Nodes identify a specific person, thing or event
 * in history in the Knowledge Web graph.
 * 
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.3 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator} 0.7.0
 */
public class BasicNode extends Object
{
  /**
   * Reference to a the NodeData singleton which contains some common utils.
   * I should have probably put that in a utils class.  Haven't decided what
   * I want to do quite yet.
   */  
  protected NodeData global;
  /**
   * GraphAEL id number for this node (as assigned by a Perl script).
   */
  protected int id;
  /**
   * URL name (XML bio id) for this node.
   */
  protected String name;
  /**
   * Century this node belongs to.  This is normally specified by a derived
   * class as a <code>CENTURY</code> constant.
   */
  protected int cent;
  
  /**
   * In spherical coordinates, the Euclidean distance between the center of the
   * coordinate system and the point.  Would be safer to call this the radial
   * distance or coordinate. Some texts call it 'r' for radius.  
   */
  public double rho = 0.0d;
  
  /**
   * theta is the counter-clockwise angle (looking from above) along the
   * horizontal plane starting from the axis pointing toward the viewer (z).
   * Some math texts define phi for this so it can get confusing. A value of
   * pi/2 (90 degrees) is aligned with the axis going right (x). Would be safer
   * to call this the azimuthal angle or coordinate.  Same as longitude.
   */
  public double theta;
  
  /**
   * phi is the angle starting at the polar vector (y).  A value of pi/2 (90
   * degrees) means the point is located on the equator.  Beware, some texts
   * reverse theta and phi.  Would be safer to call this the "polar" angle or
   * coordinate (also known as colatitude).  Same as latitude if you subtract
   * a 90 degree offset. 
   */
  public double phi;

  /**
   * Longer description of node including name and profession
   */
  protected String oneLiner;
  /**
   * Cartesian coordinate of this node in point (double float) form
   */
  protected Point3d p3d = null;
  /**
   * Cartesian coordinate of this node in vector form
   */
  protected Vector3f v3f = null;
  /**
   * How the node looks, color, texture, reflections, etc.
   */
  protected Appearance appearence = null;
  /**
   * Group that switches node labels on and off.
   */
  protected Switch sg = null;
  /**
   * Displayed edges connected to this node are listed here.
   */
  protected boolean labelOn = false;
  
  /**
   * The list of displayed edges connected to this node.  The node may be
   * connected to other edges which are not displayed.  This is because a lot
   * of edges are directional in both directions, e.g. "A taught B" and "B
   * was student of A". However, to reduce display overhead, only one of these
   * edges is actually displayed.
   * 
   * <p>This is public but shouldn't be.  I was running out of time and needed
   * to get things done quickly rather than correctly.  Sigh...</p>
   */
  public ArrayList<DisplayedEdge> displayedEdges = new ArrayList<DisplayedEdge>(32);

  /**
   * Creates a KWeb node with all the info it needs to be rendered on
   * a graph.
   *
   * @param global reference to class holding shared data.  
   * @param id ID number needed by the GraphAEL layout system.  This
   *   assigned by an external script. 
   * @param name URL-encoded ID used in the KWeb XML bios.
   * @param cent Which onionskin this node is assigned to by century.
   *   Gateway nodes are assigned to century 0.
   * @param theta angle around vertical-axis (y) in radians of node position 
   * @param phi angle from vertical-axis (y) in radians of node position 
   * @param oneLiner brief description of node contents
   */
  public BasicNode(NodeData global, int id, String name,
                   int cent, double theta, double phi,
                   String oneLiner)
  {
    this.global = global;  
    this.id = id;
    this.name = name;
    this.cent = cent;
    this.theta = theta;
    this.phi = phi;
    this.oneLiner = oneLiner;
  }

  /**
   * Creates a SceneGraph TransformGroup for this node for rendering.
   * 
   * @param radius Radius of this onionskin.
   * @param app Appearance of all node in this onionskin.
   * @return a TransfromGroup of this node for inclusion into the
   *   SceneGraph
   */
  public TransformGroup newTransformGroup(float radius,
                                          Appearance app)
  {
    this.rho = (double)radius;
    this.appearence = app;
    this.v3f = getCartesianVector();
    Transform3D t3d = new Transform3D();
    t3d.set(1.0f,v3f);
    TransformGroup tg = new TransformGroup(t3d);
    Sphere s = new Sphere(0.002f, app);
    s.setCapability(Sphere.ENABLE_PICK_REPORTING);
    org.jdesktop.lg3d.sg.internal.wrapper.SceneGraphObjectWrapper w = s.getWrapped();
    javax.media.j3d.Group g = (javax.media.j3d.Group)w.getWrapped();
    global.nodePickTable.put(new Integer(g.getChild(0).hashCode()),
                             new Integer(this.id));
    // s.setCapability(Sphere.ALLOW_BOUNDS_READ);
    s.setPickable(true);
    tg.addChild(s);
    sg = new Switch(Switch.CHILD_NONE);
    sg.setPickable(false);
    sg.setCapability(Switch.ALLOW_SWITCH_WRITE);
    sg.addChild(addLabelShape());
    tg.addChild(sg);
    return tg;
  }

  /**
   * Gives us the GraphAEL node id for this node.
   * 
   * @return this KWeb node's id.
   */
  public int getNodeId()
  {
    return id;
  }
  
  /**
   * Adds connecting edge to list of connected and displayed edges. This is
   * so we can highlight the connected edges when this node is picked.
   * 
   * @param edge edge to highlight.
   */
  public void addDisplayedEdge(DisplayedEdge edge)
  {
    displayedEdges.add(edge); 
  }
  
  /**
   * Switches between rendering or not rendering the node label.
   * 
   * @param render true id displayed; false if, well you know.
   */
  public void showLabel(boolean render)
  {
    if (sg == null) return;
    if (render)
      {
        sg.setWhichChild(Switch.CHILD_ALL);
        labelOn = true;
      }
    else
      {
        sg.setWhichChild(Switch.CHILD_NONE);
        labelOn = false;
      }
  }
 
  /**
   * Makes the node label popup as the mouse passes over it.
   * 
   * @param state true to show (mouse enters); false to hide (mouse leaves).
   */
  public void popupLabel(boolean state)
  {
    if (!labelOn)
      {
        if (state)
          {
            sg.setWhichChild(Switch.CHILD_ALL);
          }
        else
          {
            sg.setWhichChild(Switch.CHILD_NONE);
          }
      }
  }

  /**
   * Does all the Java3D stuff to create a label that always faces the user
   * no matter what angle the user views it at.
   * 
   * @return a shape that pivets the node label just in from of the associated
   *   node shape.
   */
  private Shape3D addLabelShape()
  {
    String name = "";  
    try
      {
        name = URLDecoder.decode(this.name, "ISO-8859-1");
      }
    catch (java.io.UnsupportedEncodingException e)
      {
        name = this.name;
      }
    BufferedImage image = labelImage(name,global.getNodeFontName());
    TextureLoader loader = new TextureLoader(image);
    Texture texture = loader.getTexture();
    Appearance labelApp =
      new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                           SimpleAppearance.ENABLE_TEXTURE |
                           SimpleAppearance.DISABLE_CULLING);
    labelApp.setTexture(texture);
    QuadArray labelGeometry = setLabelGeometry();
    Point3f labelPt = new Point3f(0f,0f,0f);
    OrientedShape3D label =
      new OrientedShape3D(labelGeometry, labelApp,
                          OrientedShape3D.ROTATE_ABOUT_POINT,
                          labelPt);
    return label;
  }

  /**
   * Image pixel size
   */
  private static final int WIDTH = 512;
  private static final int HEIGHT = 32;

  /**
   * Create a simple rectangular geometry for this label.  Registration point
   * is in the lower left corner.
   * 
   * <p>This code is duplicated elsewhere.  Needs to be refactored.</p>
   * 
   * @return a rectangular geometry for node labels
   */
  private QuadArray setLabelGeometry()
  {
    QuadArray labelGeometry = new QuadArray(4,
      GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2);
    float[] geomCoords = new float[]
      {
        0f, 0f, 0.05f,
        0.064f, 0f, 0.05f,
        0.064f, 0.004f, 0.05f,
        0f, 0.004f, 0.05f
      };
    float[] texCoords = new float[]
      {
        0f, 1f,
        1f, 1f,
        1f, 0f,
        0f, 0f,
      };
    labelGeometry.setCoordinates(0, geomCoords);
    labelGeometry.setTextureCoordinates(0, 0, texCoords);
    return labelGeometry;    
  }

  /**
   * This renders a text image where the black text is surrounded by a white
   * halo. This makes the text easier to read under a variety of ligthing
   * conditions.
   * 
   * @TODO This should be moved to a util class.
   * 
   * @param text
   * @return
   */
  private BufferedImage labelImage(String text, String fontName)
  {
      int style = Font.BOLD;
      BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
                                              BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = (Graphics2D) image.getGraphics();
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                         RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g.setFont(new Font(fontName, style, 22));
      g.setColor(Color.WHITE);
      AlphaComposite composite = 
        AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
      g.setComposite(composite);
      drawStringAsHalo(g, text, 2);
      composite = 
          AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
      g.setComposite(composite);
      g.setColor(Color.BLACK);
      g.drawString(text, 4, 24);
      g.dispose();
      return image;
  }

  /**
   * Draws a halo around a rendered text image.
   * 
   * <p>This code is duplicated elsewhere.  Needs to be refactored</p>
   * 
   * @param g grahics context (already setup)
   * @param text string to be rendered as a halo
   * @param offset how large in pixels the halo should be. Large offsets will
   *   not look good.  Only use a few pixels at the most.
   */
  private void drawStringAsHalo(Graphics2D g, String text, int offset)
  {
    int offset2 = (int) Math.round((float) offset / Math.sqrt(2.0));
    g.drawString(text, 4-offset, 24);
    g.drawString(text, 4-offset2, 24-offset2);
    g.drawString(text, 4, 24-offset);
    g.drawString(text, 4+offset2, 24-offset2);
    g.drawString(text, 4+offset, 24);
    g.drawString(text, 4+offset2, 24+offset2);
    g.drawString(text, 4, 24+offset);
    g.drawString(text, 4-offset2, 24+offset2);
  }

  /**
   * This creates a texture from an image.
   * 
   * I tested this and it works but I decided to use the TextureLoader which
   * does the same thing with less effort.
   * 
   * @TODO This should be moved to a util class.
   * 
   * @param image
   * @return a texture of the image which can be pasted to 3D surfaces.
   */
  private Texture2D labelTexture(BufferedImage image)
  {
    ImageComponent2D img =
      new ImageComponent2D(ImageComponent.FORMAT_RGBA, image);
    Texture2D tex =
      new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                    image.getWidth(), image.getHeight());
    tex.setMinFilter(Texture.BASE_LEVEL_LINEAR);
    tex.setMagFilter(Texture.BASE_LEVEL_LINEAR);
    tex.setImage(0, img);
    return tex;
  }
  
/**
 * Get the x,y,z position in Java 3D coordinates of this node.
 * 
 * There is no standard consenses of which Cartesian coordinate translates to
 * which spherical coordinate.  In text books, the z-axis is shown as the
 * vertical axis and the x and y axies make up the horizontal plane.  However,
 * most computer display systems show the Y-axis as vertical (as in 2-D systems)
 * and the Z-axis represents depth, though some (like Pixel-3D) specify
 * positive z as farther away, while others (like Java3D) specify positive z
 * as toward the viewer.  These equations have been adjusted to use the Java3D
 * coordinate system.
 * 
 * Conversions:
 * <table>
 *   <tr>
 *     <td>Coordinate orientation</td>
 *     <td>Text book and Perl coordinate</td>
 *     <td>Java3D and LookingGlass coordinate</td>
 *   </tr>
 *   <tr>
 *     <td>Towards right</td>
 *     <td>y</td>
 *     <td>x</td>
 *   </tr> 
 *   <tr>
 *     <td>Upward</td>
 *     <td>z</td>
 *     <td>y</td>
 *   </tr> 
 *   <tr>
 *     <td>Towards viewer</td>
 *     <td>x</td>
 *     <td>z</td>
 *   </tr> 
 * </table>
 * 
 * @return the point position of this node
 */
  public Point3d getCartesianPosition()
  {
    if (this.p3d == null)
      {
        double sinOfPhi = Math.sin(phi);
        double z = rho * sinOfPhi * Math.cos(theta);
        double x = rho * sinOfPhi * Math.sin(theta);
        double y = rho * Math.cos(phi);
        this.p3d = new Point3d(x,y,z);
      }
    return this.p3d;
  }

/**
 * Get the vector from the center of the onionskin to the node position
 *  
 * @return a vector to the node
 */
  public Vector3f getCartesianVector()
  {
    if (this.v3f == null)
      {
        this.v3f = new Vector3f(getCartesianPosition());
      }
    return this.v3f;
  }

  /**
   * Get the century (and onionskin) this node is part of.  Gateway nodes are
   * listed in the 0 century.
   * 
   * @return the century number, e.g. 19 for 19th century
   */
  public int getCentury()
  {
    return this.cent;
  }

  /**
   * Fetches the one liner for this node, usually provides the person's or
   *   thing's name and what they are famous for.
   * 
   * @return string which is normally 30 to 80 characters in length.
   */
  public String getOneLiner()
  {
    return this.oneLiner;
  }
  /**
   * get the SceneGraph node color, gloss, texture attributes.
   * 
   * @return the appearance object associated with this node.
   */
  public Appearance getAppearance()
  {
    return this.appearence;
  }

  /**
   * Standard Object method.  Shows this object in a more readable way.
   * 
   * @return String that represents this object
   */
  public String toString()
  {
    return "BasicNode " + this.id + " " + this.name;
  }
}
