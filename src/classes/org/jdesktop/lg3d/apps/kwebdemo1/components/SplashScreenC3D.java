/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/components/SplashScreenC3D.java,v 1.4 2006-12-13 22:01:40 paulby Exp $
 * $Date: 2006-12-13 22:01:40 $
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.kwebdemo1.Application;
import org.jdesktop.lg3d.apps.kwebdemo1.events.StartupSequenceAction;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.utils.animation.TranslationAnimationBoolean;
import org.jdesktop.lg3d.utils.smoother.AcceleratingVector3fSmoother;
import org.jdesktop.lg3d.utils.eventadapter.GenericEventAdapter;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.wg.AnimationGroup;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;

/**
 * The component that handles the splash screen animation at the begining of
 * the startup sequence.
 * 
 * This class brings the splash into and out of view and positions it in the
 * center of the the scene.  It also setups up event listeners, actions that
 * are to take place when an animation finishes.
 * 
 * <p><em>Implementation:</em> Apparently, there are two ways to do animations:
 * (1) using
 * <code>Component3D</code> methods, (2) using <code>AnimationGroups</code>.
 * The former is easier but I could not get the finished animation events to
 * work with that approach.  In fact, I got an error that prevented the
 * animation from running at all when attempting to setup the finish event.
 * Therefore, I ended up using the <code>AnimationGroup</code> approach.
 * However, setting up the finish event handling using the
 * <code>AnimationGroup</code> is less than obvious because there's no direct
 * mechanism to setup an event Listener. In turns out that one can setup a
 * listener by going through the <code>LgEventConnnector</code> class.</p>
 * 
 * @TODO This code could be refactored better to eliminate duplication.  Should
 * create a <code>TitleComponentBase</code> class that does most of the work
 * with customizations in image filenames and positioning added by this class.
 * 
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.4 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator} 0.7.0
 */
public class SplashScreenC3D extends Component3D
{
  private Application app = null;
  private BranchGroup kwebSplashBG = null;
  private AnimationGroup kwebSplashAG = null;
  
  private TranslationAnimationBoolean zoomIntoViewTA;
  private TranslationAnimationBoolean zoomOutOfViewTA;

  /**
   * Initializes the object with a reference to the class that will actually
   * drive the animations.
   * 
   * @param app instance of the main Application class
   */
  public SplashScreenC3D(Application app)
  {
    super();
    this.app = app;
    kwebSplashBG = new BranchGroup();
    kwebSplashAG = createLabel();
    kwebSplashBG.addChild(kwebSplashAG);
    this.addChild(kwebSplashBG);
  }

  /**
   * Runs an animation that flys the splash into view. And, it sets up an
   * animation finished event with an event listner to do something once the
   * animation stops. The event is consumed by
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.events.StartupSequenceAction StartupSequenceAction}. 
   * 
   * @param millisecs how long to run the flying animation
   * @param nextStep which step number to execute when the animation finishes
   */
  public void zoomIntoView(int millisecs, int nextStep)
  {
    zoomIntoViewTA =
      new TranslationAnimationBoolean(new Vector3f(0f, 0f, 0.8f),
                                      new Vector3f(0f, 0f, 0.2f),
                                      millisecs);
    kwebSplashAG.setAnimation(zoomIntoViewTA);
    LgEventConnector.getLgEventConnector().addListener(zoomIntoViewTA,
            new GenericEventAdapter(LgEvent.class,
                                    new StartupSequenceAction(app, nextStep))
            );
    zoomIntoViewTA.setAnimationFinishedEvent(LgEvent.class);
    zoomIntoViewTA.performAction(null, true);
  }
  
  /**
   * Runs an animation that flys the splash out of view and makes it invisible.
   * Likewise, it sets up an animation finished event with an event listner to
   * do something once the animation stops. The event is consumed by
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.events.StartupSequenceAction StartupSequenceAction}. 
   * 
   * @param millisecs how long to run the flying animation
   * @param nextStep which step number to execute when the animation finishes
   */
  public void zoomOutOfView(int millisecs, int nextStep)
  {
    zoomOutOfViewTA =
      new TranslationAnimationBoolean(new Vector3f(0f, 0f, 0.2f),
                                      new Vector3f(0f, 0f, 0.8f),
                                      millisecs,
                                      new AcceleratingVector3fSmoother());
    kwebSplashAG.setAnimation(zoomOutOfViewTA);
    LgEventConnector.getLgEventConnector().addListener(zoomOutOfViewTA,
            new GenericEventAdapter(LgEvent.class,
                                    new StartupSequenceAction(app, nextStep))
            );
    zoomOutOfViewTA.setAnimationFinishedEvent(LgEvent.class);
    zoomOutOfViewTA.performAction(null, true);
  }
  
  /**
   * Creates an <code>AnimationGroup</code> which contains an
   * <code>ImagePanel</code> that has the splash image textured
   * onto it.
   * 
   * @return a scenegraph group that can be animated. If it fails to load the
   * image file, the group is returned anyway but will have no children.
   * 
   * @TODO An error is generated using the nonportable System class, should
   * use the logger API for this but I'm too pressed for time to worry about
   * this right now.
   */
  private AnimationGroup createLabel()
  {
    AnimationGroup ag = new AnimationGroup();
    try
      {
        URL imageFilename =
          getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/JamesBurkesKnowledgeWeb.png");
        
        ImagePanel splash 
          = new ImagePanel(imageFilename, 0.1f, 0.05f);
        ag.addChild(splash);
      }
    catch (FileNotFoundException e)
      {
        System.err.println("Kwebdemo1 createKWebSplash(): " + e.toString());
      }
    catch (IOException e)
      {
        System.err.println("Kwebdemo1 createKWebSplash(): " + e.toString());
      }
    return ag;
  }

}
