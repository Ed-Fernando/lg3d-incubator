/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/onionskins/Century17thSkin.java,v 1.2 2005-06-24 19:57:00 paulby Exp $
 * $Date: 2005-06-24 19:57:00 $
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

import javax.vecmath.Color3f;

/**
 * Onionskin nested sphere, nodes and edges related to the 17th Century skin
 * 
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.2 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator} 0.7.0
 */
public class Century17thSkin extends Onionskin
{
  /**
   * Century number for this onion skin.  (It happens to be 17 but you should
   * use this constant even though I ignore my own advise and use 17 anyway.)
   */
  public static final int CENTURY = 17;
  
  /**
   * Sets up customizations for this onionskin and delegates everything else
   * to its parent class.
   * 
   * @param radius in meters of the onionskin (adjusted for viewing distance),
   *   therefore value has almost no meaning at all.  I just try different
   *   values until things are about the right size.
   * @param color of onionskin and associated links and nodes.
   */
  public Century17thSkin(float radius, Color3f color)
  {
    super(CENTURY, radius, color);
  }
}
