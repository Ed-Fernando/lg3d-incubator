/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/components/JourneyButtonC3D.java,v 1.4 2006-12-13 22:01:39 paulby Exp $
 * $Date: 2006-12-13 22:01:39 $
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
import java.net.URL;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.kwebdemo1.Application;
import org.jdesktop.lg3d.apps.kwebdemo1.events.ButtonClickedAction;
import org.jdesktop.lg3d.apps.kwebdemo1.events.StartupSequenceAction;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.utils.action.AppearanceChangeAction;
import org.jdesktop.lg3d.utils.animation.TranslationAnimationBoolean;
import org.jdesktop.lg3d.utils.eventadapter.GenericEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MousePressedEventAdapter;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.AnimationGroup;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.wg.Cursor3D;

/**
 * The component that handles the Journey button animation at the begining of
 * the startup sequence and all the UI actions with the button.
 * 
 * <p>The purpose of the Journey button is to show a popup dialog that allows
 * the user to see an outline of where there journey has taken them thus far.
 * It also provides a shortcut way to go back to any previously visited node
 * in the journey and allow one to save and restore journeys by name.</p>
 * 
 * <p>This class brings the button into view and positions it at the far left
 * of the the scene (assuming a 1024x768 display).  It also setups up event
 * listeners, actions that are to take place when an animation finishes and
 * when the button is clicked.</p>
 * 
 * @TODO This code could be refactored better to eliminate duplication.  Should
 * create a <code>ButtonComponentBase</code> class that does most of the work
 * with customizations in image filenames and positioning added by this class.
 * 
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.4 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator} 0.7.0
 */
public class JourneyButtonC3D extends Component3D
{
  private Application app = null;
  private BranchGroup buttonBG = null;
  private AnimationGroup buttonAG = null;
  private ImagePanel buttonIP = null;
  private TranslationAnimationBoolean buttonTA;

  /**
   * Initializes the object with a reference to the class that will actually
   * drive the animations.
   * 
   * @param app instance of the main Application class
   */
  public JourneyButtonC3D(Application app)
  {
    super();
    this.app = app;
    buttonBG = new BranchGroup();
    buttonAG = createIcon();
    buttonBG.addChild(buttonAG);
    this.addChild(buttonBG);
    addMouseRolloverListener();
    addMousePressedListener();
    addMouseReleasedListener(app, "Journey");
    // setMouseEventPropagatable(true);
    setCursor(Cursor3D.SMALL_CURSOR);
  }
  
  /**
   * Runs an animation that flys the button into view. And, it sets up an
   * animation finished event with an event listner to do something once the
   * animation stops. The event is consumed by
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.events.StartupSequenceAction StartupSequenceAction}. 
   * 
   * @param millisecs how long to run the flying animation
   * @param nextStep which step number to execute when the animation finishes
   */
  public void zoomIntoView(int millisecs, int nextStep)
  {
    buttonTA =
      new TranslationAnimationBoolean(new Vector3f(0f, 0f, 0.8f),
                                      new Vector3f(-0.125f, 0.08125f, -0.15f),
                                      millisecs);
    buttonAG.setAnimation(buttonTA);
    LgEventConnector.getLgEventConnector().addListener(buttonTA,
            new GenericEventAdapter(LgEvent.class,
                                    new StartupSequenceAction(app, nextStep))
            );
    buttonTA.setAnimationFinishedEvent(LgEvent.class);
    buttonTA.performAction(null, true);
  }
  
  /**
   * Creates an <code>AnimationGroup</code> which contains an
   * <code>ImagePanel</code> that has the normal button icon textured
   * onto it.
   * 
   * @return a scenegraph group that can be animated. If it fails to load the
   * image file, the group is returned anyway but will have no children.
   * 
   * @TODO An error is generated using the nonportable System class, should
   * use the logger API for this but I'm too pressed for time to worry about
   * this right now.
   */
  private AnimationGroup createIcon()
  {
    AnimationGroup ag = new AnimationGroup();
    try
      {
        URL iconNFilename 
          = getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/icon/InfoN_64x64.png");
        buttonIP 
          = new ImagePanel(iconNFilename, 0.0125f, 0.0125f);
        ag.addChild(buttonIP);
      }
    catch (FileNotFoundException e)
      {
        System.err.println("Kwebdemo1 createIcon(): " + e.toString());
      }
    catch (IOException e)
      {
        System.err.println("Kwebdemo1 createIcon(): " + e.toString());
      }
    return ag;
  }

  /**
   * Add a mouse rollover (changes appearance as cursor passes over shape)
   * listener to this button. Changes the button appearance to the rollover
   * icon image. 
   * 
   * <p><em>Implementation:</em> Swaps images similar to the way web page
   * rollovers work. In this case we create a new appearance object, load
   * a new image into it and pass the new appearance as the thing to change when
   * the mouse cursor passes over the shape.</p>
   * 
   * @TODO This code needs to be refactored.  Too much duplication with the
   * next method.  Need a common button class that handles both of these
   * methods without duplication. Run out of time with a deadline to do it
   * right now.
   */
  private void addMouseRolloverListener()
  {
    SimpleAppearance app 
      = new SimpleAppearance(
        1.0f, 1.0f, 1.0f, 1.0f,
        SimpleAppearance.ENABLE_TEXTURE
        | SimpleAppearance.DISABLE_CULLING
        );
    try
      {
        app.setTexture(getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/icon/InfoR_64x64.png"));
        addListener(new MouseEnteredEventAdapter(
                new AppearanceChangeAction(buttonIP, app)));
      }
    catch (FileNotFoundException e)
      {
        System.err.println(
                "Kwebdemo1 JourneyButtonC3D addMouseRolloverListener(): " +
                e.toString());
      }
    catch (IOException e)
      {
        System.err.println(
                "Kwebdemo1 JourneyButtonC3D addMouseRolloverListener(): " +
                e.toString());
      }
  }
  
  /**
   * Add a mouse pressed listener to this button. Changes the button appearance
   * to the selected icon image or decreases the size of the button slightly as
   * if being pushed. (You'll have to look at the code to see which action is
   * taken.)
   * 
   * <p><em>Implementation:</em> Swaps images similar to the way web page
   * rollovers work. In this case we create a new appearance object, load
   * a new image into it and pass the new appearance as the thing to change when
   * the mouse cursor passes over the shape. If the size change is active,
   * it's done by reducing the scale slightly.</p>
   * 
   * @TODO This code needs to be refactored.  Too much duplication with the
   * next method.  Need a common button class that handles both of these
   * methods without duplication. Run out of time with a deadline to do it
   * right now.
   */
  private void addMousePressedListener()
  {
    SimpleAppearance app =
      new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                           SimpleAppearance.ENABLE_TEXTURE |
                           SimpleAppearance.DISABLE_CULLING
                           );
  try
    {
      app.setTexture(getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/icon/InfoS_64x64.png"));
      addListener(new MousePressedEventAdapter(
              new AppearanceChangeAction(buttonIP, app)));
              // new ScaleActionBoolean(this, 0.99f)));
    }
  catch (FileNotFoundException e)
    {
      System.err.println(
              "Kwebdemo1 JourneyButtonC3D addMousePressedListener(): " +
              e.toString());
    }
  catch (IOException e)
    {
      System.err.println(
              "Kwebdemo1 JourneyButtonC3D addMousePressedListener(): " +
              e.toString());
    }
  }

  /**
   * Add a mouse released (clicked) listener to this button.  This is the
   * listener that actually makes the button do something within the
   * application. A mouse click hands the action over to an instance of the
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.events.ButtonClickAction ButtonClickedAction}
   * class.
   */
  private void addMouseReleasedListener(Application app, String label)
  {
    addListener(new MouseClickedEventAdapter(
            new ButtonClickedAction(app, label)
            ));
  }


}
