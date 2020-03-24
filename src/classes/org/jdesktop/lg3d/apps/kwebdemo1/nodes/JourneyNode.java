/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/nodes/JourneyNode.java,v 1.2 2005-06-24 19:56:57 paulby Exp $
 * $Date: 2005-06-24 19:56:57 $
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

import org.jdesktop.lg3d.apps.kwebdemo1.singletons.NodeData;

/**
 * James Burke's Knowledge Web graph journey node rendering.
 * 
 * <p>This class is initialized by
 * {@link org.jdesktop.lg3d.apps.kwebdemo1.singletons.NodeData NodeData}
 * ...</p>
 *
 * @see org.jdesktop.lg3d.apps.kwebdemo1.nodes.BasicNode
 * @see org.jdesktop.lg3d.apps.kwebdemo1.nodes.JourneyNode
 *
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.2 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator} 0.7.0
 */
public class JourneyNode extends BasicNode
{
  public JourneyNode(NodeData global, int id, String name,
                     int cent, double theta, double phi,
                     String oneLiner)
  {
    super(global, id, name, cent, theta, phi, oneLiner);
  }
}
