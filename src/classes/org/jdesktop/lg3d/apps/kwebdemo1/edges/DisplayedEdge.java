/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/edges/DisplayedEdge.java,v 1.2 2005-06-24 19:56:51 paulby Exp $
 * $Date: 2005-06-24 19:56:51 $
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

package org.jdesktop.lg3d.apps.kwebdemo1.edges;

import java.util.Hashtable;

import javax.swing.text.Highlighter;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.Material;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.LineArray;
import org.jdesktop.lg3d.sg.LineAttributes;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.apps.kwebdemo1.singletons.NodeData;
import org.jdesktop.lg3d.apps.kwebdemo1.nodes.BasicNode;
import org.jdesktop.lg3d.apps.kwebdemo1.onionskins.Onionskin;


/**
 * Displays an edge.  Basic edge rendering class.
 * 
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.2 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator} 0.7.0
 */
public class DisplayedEdge extends BasicEdge
{
  private Appearance edgeAppearance = null;  
  private LineAttributes defaultLineAttributes = null;
  private LineAttributes highlightLineAttributes = null;
  private boolean edgeOn = false;
  
  /**
   * Constructor for setting up a directed graph edge.
   * 
   * @param srcId Node id which is referencing another node.
   * @param dstId Node id of node being referenced.
   * @param srcName URL-encoded Node name of source node from the XML bio id. 
   * @param predicate The transistion text that explains how the two nodes are
   *   related, e.g. "was influnced by".
   * @param dstName URL-encoded Node name of destination node from the XML bio
   *   id.
   */
  public DisplayedEdge(int srcId, int dstId, String srcName,
                       String predicate, String dstName)
  {
    super(srcId, dstId, srcName, predicate, dstName);
  }

  /**
   * Returns a shape containing a wireframe rendered line that represents
   * this edge connecting a pair of nodes.
   * 
   * <p>Something is broken in the geodesic line drawing approach. Sometimes it
   * doesn't do the right thing.  Proabably has to do with not dealing with the
   * congruence of angels properly. Need to figure this out someday.</p>
   * 
   * @param defaultApp global appearance that applies to this (and most) edges,
   *   such as a global line width.
   * @return a shape that defines a line representing a graph edge.
   */
  public Shape3D newShape3D(Appearance edgeApp, LineAttributes defaultLA,
                            LineAttributes highlightLA,
                            Hashtable<Integer,Onionskin> skins)
  {
    edgeAppearance = edgeApp;  
    defaultLineAttributes = defaultLA;
    highlightLineAttributes = highlightLA;
    NodeData nodeData = NodeData.getInstance();  
    BasicNode srcNode = nodeData.getNode(srcId);
    BasicNode dstNode = nodeData.getNode(dstId);
    srcNode.addDisplayedEdge(this);
    dstNode.addDisplayedEdge(this);
    Vector3f a = srcNode.getCartesianVector();
    Vector3f b = dstNode.getCartesianVector();
    float angle = a.angle(b);
    LineArray lineArray = null;
    int geomfmt = GeometryArray.COORDINATES | GeometryArray.COLOR_3;
    int lineCount =  getGeodesicLineCount(srcNode, dstNode);
    int vertexCount = lineCount + lineCount;
    Point3d startPoint = srcNode.getCartesianPosition();
    Point3d stopPoint = dstNode.getCartesianPosition();
    lineArray = new LineArray(vertexCount, geomfmt);
    Point3f[] edgeCoords = new Point3f[vertexCount];
    double src_theta = srcNode.theta;
    double dst_theta = dstNode.theta;
    double src_phi = srcNode.phi;
    double dst_phi = dstNode.phi;
    if ((dst_theta - src_theta) > Math.PI)
      {
        /*
         * The distance in this direction is the long way around.
         * Swap the src and dst around so the segments connect the shorter way.
         */
        double swap_temp = src_theta + Math.PI + Math.PI;
        src_theta = dst_theta;
        dst_theta = swap_temp;
        swap_temp = src_phi;
        src_phi = dst_phi;
        dst_phi = swap_temp;
        startPoint = dstNode.getCartesianPosition();
        stopPoint = srcNode.getCartesianPosition();
        srcNode = nodeData.getNode(dstId);
        dstNode = nodeData.getNode(srcId);
      }
    edgeCoords[0] = new Point3f(startPoint);
    int j = 1;
    for (int i = 1; i < lineCount; i++)
      {
        double inc = (double) i / (double) lineCount;
        double theta = src_theta + inc * (dst_theta - src_theta);
        double phi = src_phi + inc * (dst_phi - src_phi);
        edgeCoords[j++] = sphericalToCartesian(srcNode.rho, theta, phi);
        edgeCoords[j++] = sphericalToCartesian(srcNode.rho, theta, phi);
      }
    edgeCoords[j] = new Point3f(stopPoint);
    Color3f srcColor = getNodeColor(skins, srcNode);
    Color3f dstColor = getNodeColor(skins, dstNode);
    Color3f[] edgeColors = new Color3f[vertexCount];
    edgeColors[0] = srcColor;
    for (int i = 1; i < vertexCount; i++)
      {
        edgeColors[i] = dstColor;
      }
    lineArray.setCoordinates(0, edgeCoords);
    lineArray.setColors(0, edgeColors);
    Shape3D edgeShape = new Shape3D(lineArray, edgeApp);
    return edgeShape;
  }

  /**
   * Figures out how many line segments it will take to approximately render
   * a great-circle arc.
   *  
   * An geodesic is the <em>shortest</em> distance between two point on the
   * surface of a manifold (like the great circle distance between cities on the
   * earth). (Not the same as a loxodrome which is line of constant compass
   * heading between two points on a sphere.)
   * 
   * @param srcNode Source Node
   * @param dstnode Destination Node
   * @return number of line segments to use for this arc approximation
   */
  private int getGeodesicLineCount(BasicNode srcNode,
                                   BasicNode dstNode)
  {
    int lineCount = 1;  
    if (srcNode.getCentury() == dstNode.getCentury())
      {
        Vector3f a = srcNode.getCartesianVector();
        Vector3f b = dstNode.getCartesianVector();
        float angle = a.angle(b);
        float distance = a.length() * angle;
        lineCount = (int) Math.floor(distance / 0.01d);
        if (lineCount == 0) lineCount = 1;
      }
    return lineCount;
  }

  /**
   * Finds our color to be the same as the associated skin color.  Each skin
   * is a differnet color based on centuries.
   * 
   * @return our skin's color
   */
  private Color3f getNodeColor(Hashtable<Integer,Onionskin> skins,
                               BasicNode node)
  {
     int century = node.getCentury();
     Onionskin skin = skins.get(new Integer(century));
     return skin.getColor();
  }

  /**
   * Makes this edge appear thicker so it grabs the viewer's attention.
   * 
   * @param state true when mouse passes over associated node that is
   *   connected to this edge. false when mouse leaves node and edge appearance
   *   need to return to normal.
   */
  public void highlightEdge(boolean state)
  {
    if (edgeAppearance != null && !edgeOn)
      {
        if (state) edgeAppearance.setLineAttributes(highlightLineAttributes);
        else edgeAppearance.setLineAttributes(defaultLineAttributes);
      }
  }

  /**
   * Similar to <code>highlightEdge</code> but the hightlight sticks until
   *   explicitly unset.
   * 
   * @param state true to highight; false to unhighlight.
   */
  public void setEdgeOn(boolean state)
  {
    if (edgeAppearance != null)
      {
        edgeOn = state;
        if (state) edgeAppearance.setLineAttributes(highlightLineAttributes);
        else edgeAppearance.setLineAttributes(defaultLineAttributes);
      }
  }

  /**
   * More readable description of this class instance
   */
  public String toString()
  {
    return "DisplayedEdge " + srcName + " " + predicate + " " + dstName;
  } 

}
