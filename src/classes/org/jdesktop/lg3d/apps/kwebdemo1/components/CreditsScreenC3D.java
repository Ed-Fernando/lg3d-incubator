/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/components/CreditsScreenC3D.java,v 1.5 2006-12-13 22:01:39 paulby Exp $
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
import org.jdesktop.lg3d.apps.kwebdemo1.events.CreditsScreenMouseClickedAction;
import org.jdesktop.lg3d.apps.kwebdemo1.events.CreditsSequenceAction;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.QuadArray;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.utils.animation.TranslationAnimationBoolean;
import org.jdesktop.lg3d.utils.eventadapter.GenericEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.smoother.AcceleratingVector3fSmoother;
import org.jdesktop.lg3d.wg.AnimationGroup;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventConnector;

/**
 * The component that handles the Credits Screen animation.
 * 
 * This class brings the Credits Screen into and out of view and positions it in the
 * center of the the scene.  It also setups up event listeners, actions that
 * are to take place when an animation finishes. And, handles all the Screen
 * UI actions.
 * 
 * @TODO This code could be refactored better to eliminate duplication.  Should
 * create a <code>TitleComponentBase</code> class that does most of the work
 * with customizations in image filenames and positioning added by this class.
 * 
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.5 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator} 0.7.0
 */
public class CreditsScreenC3D extends Component3D
{
  private Application app = null;
  private BranchGroup screenBG = null;
  private AnimationGroup screenAG = null;
  private Shape3D screenShape3D = null;
  private GlassyPanel screenBox = null;
  private int step = -1;
  private SimpleAppearance screenBoxApp = null;
  private Texture screenTexture = null;
  private SimpleAppearance screenApp = null;
  
  private TranslationAnimationBoolean zoomIntoViewTA;
  private TranslationAnimationBoolean zoomOutOfViewTA;
  
  final URL screen00 =
    getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/CreditsScreen.png");

  /**
   * Initializes the object with a reference to the class that will actually
   * drive the animations.
   * 
   * @param app instance of the main Application class
   */
  public CreditsScreenC3D(Application app)
  {
    super();
    this.app = app;
    screenBG = new BranchGroup();
    screenAG = createScreen();
    screenBG.addChild(screenAG);
    this.addChild(screenBG);
    addMouseClickedListener();
    // setMouseEventPropagatable(true);
    setCursor(Cursor3D.SMALL_CURSOR);
  }

  /**
   * Runs an animation that flys the Screen into view. And, it sets up an
   * animation finished event with an event listner to do something once the
   * animation stops. The event is consumed by
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.events.CreditsSequenceAction CreditsSequenceAction}. 
   * 
   * @param millisecs how long to run the flying animation
   * @param nextStep which step number to execute when the animation finishes
   */
  public void zoomIntoView(int millisecs, int nextStep)
  {
    changeTexture(screen00);
    this.setVisible(true);
    TranslationAnimationBoolean zoomIntoViewTA =
      new TranslationAnimationBoolean(new Vector3f(0f, 0f, 0.8f),
                                      new Vector3f(0f, 0f, 0.15f),
                                      millisecs);
    screenAG.setAnimation(zoomIntoViewTA);
    LgEventConnector.getLgEventConnector().addListener(zoomIntoViewTA,
            new GenericEventAdapter(LgEvent.class,
                                    new CreditsSequenceAction(app, nextStep))
            );
    zoomIntoViewTA.setAnimationFinishedEvent(LgEvent.class);
    zoomIntoViewTA.performAction(null, true);
    step = 0;
  }
  
  /**
   * Runs an animation that flys the Screen out of view and makes it invisible.
   * Likewise, it sets up an animation finished event with an event listner to
   * do something once the animation stops. The event is consumed by
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.events.CreditsSequenceAction CreditsSequenceAction}. 
   * 
   * @param millisecs how long to run the flying animation
   * @param nextStep which step number to execute when the animation finishes
   */
  public void zoomOutOfView(int millisecs, int nextStep)
  {
    zoomOutOfViewTA =
      new TranslationAnimationBoolean(new Vector3f(0f, 0f, 0.15f),
                                      new Vector3f(0f, 0f, 0.8f),
                                      millisecs,
                                      new AcceleratingVector3fSmoother());
    screenAG.setAnimation(zoomOutOfViewTA);
    LgEventConnector.getLgEventConnector().addListener(zoomOutOfViewTA,
            new GenericEventAdapter(LgEvent.class,
                                    new CreditsSequenceAction(app, nextStep))
            );
    zoomOutOfViewTA.setAnimationFinishedEvent(LgEvent.class);
    zoomOutOfViewTA.performAction(null, true);
    step = -1;
  }

  /**
   * Handles mouse clicks on the Screen.  Any mouse click that isn't at a
   * step that makes sense for the mouse at that time causes the Screen to
   * close.  Otherwise, the mouse clicks appear to enable check boxes and do
   * a Credits. 
   */
  public void handleMouseClick()
  {
    if (step == -1) return;
    switch (step)
      {
        default: app.creditsSequence(2);   
      }
  }

  /**
   * Switches textures on the Screen to simulate an active Screen UI.
   * 
   * @param filename image filename to swap into texture
   * 
   * @TODO This method should be moved to a base class
   * 
   * @TODO An error is generated using the nonportable System class, should
   * use the logger API for this but I'm too pressed for time to worry about
   * this right now.
   */
  private void changeTexture(URL filename)
  {
    try
      {
        screenApp.setTexture(filename);
      }
    catch (FileNotFoundException e)
      {
        System.err.println(
              "Kwebdemo1 CreditsScreenC3D: " +
              e.toString());
      }
    catch (IOException e)
      {
        System.err.println(
              "Kwebdemo1 CreditsScreenC3D: " +
              e.toString());
      }
  }
  /**
   * Creates an <code>AnimationGroup</code> which contains a
   * <code>GlassyPanel</code> and my replacement for <code>ImagePanel</code>
   * because the LG version doesn't handle texture swapping well.
   * 
   * @return a scenegraph group that can be animated. If it fails to load the
   * image file, the group is returned anyway but will have no children.
   * 
   * @TODO An error is generated using the nonportable System class, should
   * use the logger API for this but I'm too pressed for time to worry about
   * this right now.
   * 
   * @TODO This method should be moved to a base class
   */
  private AnimationGroup createScreen()
  {
    Appearance boxApp =
      new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                           SimpleAppearance.DISABLE_CULLING);
    screenBox = new GlassyPanel(0.1f, 0.1f, 0.005f, boxApp);
    AnimationGroup ag = new AnimationGroup();
    ag.addChild(screenBox);
    try
      {
        screenShape3D = createScreenShape3D(screen00, 0.1f, 0.1f);
        ag.addChild(screenShape3D);
      }
    catch (FileNotFoundException e)
      {
        System.err.println("Kwebdemo1 CreditsScreen(): " +
                           e.toString());
      }
    catch (IOException e)
      {
        System.err.println("Kwebdemo1 CreditsScreen(): " +
                           e.toString());
      }
    return ag;
  }

  /**
   * I was going to use ImagePanel for this but swapping textures with it
   * is painfully slow due to the way it is implemented, so I basically wrote
   * my own version of it that allows me to swap textures more efficiently.
   * 
   * @param image filename to load, needs to be PNG or JPG? I think.
   * @param width width in Java3D unit (meters adjusted for distance from
   *   viewer).
   * @param height width in Java3D unit (meters adjusted for distance from
   *   viewer).
   * @return shape with image texture on it.
   * 
   * @TODO This method should be moved to a base class
   */
  private Shape3D createScreenShape3D(URL filename,
                                      float width, float height)
    throws FileNotFoundException, IOException
  {
    TextureAttributes ta = new TextureAttributes();
    ta.setTextureMode(TextureAttributes.REPLACE);
    screenApp =
      new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                           SimpleAppearance.ENABLE_TEXTURE |
                           SimpleAppearance.DISABLE_CULLING);
    screenApp.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
    screenApp.setTextureAttributes(ta);
    // ScreenApp.setTexture(ScreenTexture);
    QuadArray labelGeometry = setScreenGeometry(width, height);
    Shape3D screenS3D = new Shape3D(labelGeometry, screenApp);
    screenS3D.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
    screenS3D.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
    return screenS3D;
  }

  /**
   * Creates a simple rectangle geometry which has it's registration point
   * in the center of the rectangle with a texture covering the entire surface.
   * That means that the aspect ratio of the geometry must match the
   * aspect ratio of the image in order to avoid distortions.
   *  
   * @return a rectangle geometry that can be textured
   */
  private QuadArray setScreenGeometry(float width, float height)
  {
    QuadArray screenGeometry = new QuadArray(4,
      GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2);
    float half_width = width / 2.0f;
    float half_height = height / 2.0f;
    float[] geomCoords = new float[]
      {
        -half_width, -half_height, 0f,
        half_width, -half_height, 0f,
        half_width, half_height, 0f,
        -half_width, half_height, 0f
      };
    float[] texCoords = new float[]
      {
        0f, 1f,
        1f, 1f,
        1f, 0f,
        0f, 0f,
      };
    screenGeometry.setCoordinates(0, geomCoords);
    screenGeometry.setTextureCoordinates(0, 0, texCoords);
    return screenGeometry;    
  }

  /**
   * Add a mouse button release listener to this Screen. Delegates events to
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.events.CreditsScreenMouseClickedAction CreditsScreenMouseClickedAction}. 
   */
  private void addMouseClickedListener()
  {
    addListener(new MouseClickedEventAdapter(
                  new CreditsScreenMouseClickedAction(this)));
  }

  /**
   * Causes main thead to sleep for the specified milliseconds.
   * 
   * This is a kludge and should be avoided whenever possible as it makes the
   * application non-responsive to events.  But, it's useful in certain limited
   * situations.
   * 
   * @TODO This function is a duplicate of that in <code>Application</code>.
   * Need to move this and some other stuff to a utility class.  No time
   * right now.
   * 
   * @param millisecs
   */
  private void sleep(int millisecs)
  {
    try
      {
        Thread.sleep(millisecs);
      }
    catch (InterruptedException e)
      {
        System.err.println("@@ kwebdemo1 Application SLEEP INTERRUPTED: " + e);
      }
  }
  
}
