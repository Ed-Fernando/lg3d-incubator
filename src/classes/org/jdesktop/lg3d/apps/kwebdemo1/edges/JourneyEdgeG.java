/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/edges/JourneyEdgeG.java,v 1.2 2005-06-24 19:56:52 paulby Exp $
 * $Date: 2005-06-24 19:56:52 $
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

/**
 * James Burke's Knowledge Web graph journey edge rendering.
 * 
 * <p>This class is initialized by
 * {@link org.jdesktop.lg3d.apps.kwebdemo1.singletons.EdgeData EdgeData}
 * ...</p>
 *
 * @see org.jdesktop.lg3d.apps.kwebdemo1.edges.BasicEdge
 * @see org.jdesktop.lg3d.apps.kwebdemo1.edges.DisplayedEdge
 * @see org.jdesktop.lg3d.apps.kwebdemo1.edges.JourneyEdge
 *
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.2 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator} 0.7.0
 */
public class JourneyEdgeG extends JourneyEdge
{
  public JourneyEdgeG(int srcId, int dstId, String srcName,
                   String predicate, String dstName)
  {
    super(srcId, dstId, srcName, predicate, dstName);
  }
}
