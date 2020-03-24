/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/events/JourneyDialogSequenceAction.java,v 1.2 2005-06-24 19:56:55 paulby Exp $
 * $Date: 2005-06-24 19:56:55 $
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

package org.jdesktop.lg3d.apps.kwebdemo1.events;

import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.apps.kwebdemo1.Application;

/**
 * This is an Action associated with an LgEvent. This action is associated with
 * events generated during the journey sequence. Whenever an animation occurs,
 * the appropriate instance of this object is called to perform the action.
 * 
 * <p><em>Implementation:</em> No real work is done by this class.
 * All it does is to delegate to a method in the main
 * {@link org.jdesktop.lg3d.apps.kwebdemo1.Application Application} class.
 * </p>
 * 
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.2 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator 0.7.0
 */
public class JourneyDialogSequenceAction implements ActionNoArg
{
  private Application app = null;
  private int stepNumber = -1;

  /**
   * Initializes some info so this class can delegate the action to the right
   * receiver of the action.
   * 
   * @param app  instance of the main Application class that contain the
   *   method for handling the action.
   * @param step sequence step this action is associated with.
   */
  public JourneyDialogSequenceAction(Application app, int step)
  {
    this.app = app;
    this.stepNumber = step;
  }

  /**
   * Required method which is called whenever an LgEventListener needs to
   * perform an action on this class.
   * 
   * <p><em>Implementation:</em> In this case, the action always calls the
   * <code>journeyDialogSequence(stepNumber)</code> method in the main
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.Application Application} class.
   * </p>
   * 
   * @param arg0 Source object of the event (but currently ignored).
   */
  public void performAction(LgEventSource arg0)
  {
    app.journeyDialogSequence(stepNumber);
  }

}
