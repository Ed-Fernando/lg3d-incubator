/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/components/BigGlassyPanelC3D.java,v 1.3 2006-12-13 22:01:36 paulby Exp $
 * $Date: 2006-12-13 22:01:36 $
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

package org.jdesktop.lg3d.apps.kwebdemo1.components;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.kwebdemo1.Application;
import org.jdesktop.lg3d.apps.kwebdemo1.events.StartupSequenceAction;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.utils.animation.TranslationAnimationBoolean;
import org.jdesktop.lg3d.utils.eventadapter.GenericEventAdapter;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.AnimationGroup;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.Cursor3D;

/**
 * The component that handles the Big Glassy Panel animation.
 * 
 * This class brings the bigGlassyPanel into and out of view and positions it in the
 * center of the the scene.  It also setups up event listeners, actions that
 * are to take place when an animation finishes. And, handles all the bigGlassyPanel
 * UI actions.
 * 
 * @TODO This code could be refactored better to eliminate duplication.  Should
 * create a <code>TitleComponentBase</code> class that does most of the work
 * with customizations in image filenames and positioning added by this class.
 * 
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.3 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator} 0.7.0
 */
public class BigGlassyPanelC3D extends Component3D
{
  private Application app = null;
  private BranchGroup bigGlassyPanelBG = null;
  private AnimationGroup bigGlassyPanelAG = null;
  private GlassyPanel bigGlassyPanelBox = null;
  private int step = -1;
  private SimpleAppearance bigGlassyPanelApp = null;
  
  private TranslationAnimationBoolean bigGlassyPanelTA;
  /**
   * Initializes the object with a reference to the class that will actually
   * drive the animations.
   * 
   * @param app instance of the main Application class
   */
  public BigGlassyPanelC3D(Application app)
  {
    super();
    this.app = app;
    bigGlassyPanelBG = new BranchGroup();
    bigGlassyPanelAG = createBigGlassyPanel();
    bigGlassyPanelBG.addChild(bigGlassyPanelAG);
    this.addChild(bigGlassyPanelBG);
    // setMouseEventPropagatable(true);
  }

  /**
   * Runs an animation that flys the bigGlassyPanel into view. And, it sets up an
   * animation finished event with an event listner to do something once the
   * animation stops. The event is consumed by
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.events.StartupSequenceAction StartupSequenceAction}. 
   * 
   * @param millisecs how long to run the flying animation
   * @param nextStep which step number to execute when the animation finishes
   */
  public void zoomIntoView(int millisecs, int nextStep)
  {
    bigGlassyPanelTA =
      new TranslationAnimationBoolean(new Vector3f(0f, 0f, -20f),
                                      new Vector3f(0f, 0.012f, -0.17f),
                                      millisecs);
    bigGlassyPanelAG.setAnimation(bigGlassyPanelTA);
    LgEventConnector.getLgEventConnector().addListener(bigGlassyPanelTA,
            new GenericEventAdapter(LgEvent.class,
                                    new StartupSequenceAction(app, nextStep))
            );
    bigGlassyPanelTA.setAnimationFinishedEvent(LgEvent.class);
    bigGlassyPanelTA.performAction(null, true);
    step = 0;
  }
  
  /**
   * Creates an <code>AnimationGroup</code> which contains a
   * <code>GlassyPanel</code>.
   * 
   * @return a scenegraph group that can be animated. If it fails to load the
   * image file, the group is returned anyway but will have no children.
   * 
   * @TODO An error is generated using the nonportable System class, should
   * use the logger API for this but I'm too pressed for time to worry about
   * this right now.
   */
  private AnimationGroup createBigGlassyPanel()
  {
    Appearance boxApp =
      new SimpleAppearance(0f, 0f, 0f, 1.0f,
                           SimpleAppearance.DISABLE_CULLING);
    bigGlassyPanelBox = new GlassyPanel(0.3f, 0.22f, 0.005f, boxApp);
    AnimationGroup ag = new AnimationGroup();
    ag.addChild(bigGlassyPanelBox);
    return ag;
  }
  
}
