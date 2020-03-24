/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/edges/BasicEdge.java,v 1.2 2005-06-24 19:56:51 paulby Exp $
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

import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

/**
 * Base class for edges.
 * 
 * Basic edges are not displayed. See the {@link DisplayedEdge DisplayedEdge}
 * class for edge rendering.
 * 
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.2 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator} 0.7.0
 */
public class BasicEdge extends Object
{
  /**
   * GraphAEL id (assigned by a Perl script) of source node.  This is the
   * node that is making a reference to another node.
   */
  protected int srcId;
  /**
   * GraphAEL id (assigned by a Perl script) of target node.  This is the
   * node that is being referenced by another node.
   */
  protected int dstId;
  /**
   * URL-encoded Node name of source node from the XML bio id. This is the
   * KWeb bio id like, <code>"Einstein%2C+Albert"</code>
   */
  protected String srcName;
  /**
   * The transistion text that explains how the two nodes connected by this
   * edge are related. e.g. <code>"was influnced by"</code>.
   */
  protected String predicate;
  /**
   * URL-encoded Node name of destination node from the XML bio id. This is the
   * KWeb bio id like, <code>"Schelling%2C+Frederick+Wilhelm+Joseph+von"</code>.
   */
  protected String dstName;

  /**
   * Contructor for setting up a directed graph edge.
   * 
   * @param srcId Node id which is referencing another node.
   * @param dstId Node id of node being referenced.
   * @param srcName URL-encoded Node name of source node from the XML bio id. 
   * @param predicate The transistion text that explains how the two nodes are
   *   related, e.g. "was influnced by".
   * @param dstName URL-encoded Node name of destination node from the XML bio
   *   id.
   */
  public BasicEdge(int srcId, int dstId, String srcName,
                   String predicate, String dstName)
  {
    this.srcId = srcId;
    this.dstId = dstId;
    this.srcName = srcName;
    this.predicate = predicate;
    this.dstName = dstName;
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
   * @return the cartesian point
   */
  public Point3f sphericalToCartesian(double rho, double theta, double phi)
  {
    double sinOfPhi = Math.sin(phi);
    double z = rho * sinOfPhi * Math.cos(theta);
    double x = rho * sinOfPhi * Math.sin(theta);
    double y = rho * Math.cos(phi);
    Point3d p3d = new Point3d(x, y, z);
    Point3f p3f = new Point3f(p3d);
    return p3f;
  }

  /**
   * Find the node which is at the opposite end of this edge regardless of
   * which direction the edge is pointing.
   * 
   * @param nodeid  id of our node so we don't return ourselves.
   * @return opposite node (never same as ourself).
   */
  public int getOppositeNodeId(int nodeid)
  {
    if (srcId == nodeid) return dstId;
    else return srcId;
  }

}
