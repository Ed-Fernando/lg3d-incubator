/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/components/NodeDialogC3D.java,v 1.5 2006-12-13 22:01:40 paulby Exp $
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
import java.net.URL;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.kwebdemo1.Application;
import org.jdesktop.lg3d.apps.kwebdemo1.events.NodeDialogMouseClickedAction;
import org.jdesktop.lg3d.apps.kwebdemo1.events.SearchDialogSequenceAction;
import org.jdesktop.lg3d.apps.kwebdemo1.nodes.*;
import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.QuadArray;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
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
 * The component that handles the Node dialog animation.
 * 
 * This class brings the dialog into and out of view and positions it in the
 * center of the the scene.  It also setups up event listeners, actions that
 * are to take place when an animation finishes. And, handles all the dialog
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
public class NodeDialogC3D extends Component3D
{
  private Application app = null;
  private BranchGroup dialogBG = null;
  private AnimationGroup dialogAG = null;
  private Shape3D dialogShape3D = null;
  private GlassyPanel dialogBox = null;
  private int step = -1;
  private SimpleAppearance dialogBoxApp = null;
  private Texture dialogTexture = null;
  private SimpleAppearance dialogApp = null;
  public BasicNode currentKNode = null;
  
  private TranslationAnimationBoolean zoomIntoViewTA;
  private TranslationAnimationBoolean zoomOutOfViewTA;
  
  final URL dialog00 =
    getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/NodeNoInfo.png");
  final URL dialog01 =
    getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/NodeMozart01.png");
  final URL dialog02 =
    getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/NodeMozart02.png");
  final URL dialog03 =
    getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/NodeMozart03.png");
  final URL dialog04 =
    getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/NodeMozart04.png");
  final URL dialog05 =
    getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/NodeMozart05.png");
  final URL dialog06 =
    getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/NodeMozart06.png");
  final URL dialog07 =
    getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/NodeMozart07.png");
  final URL dialog08 =
    getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/NodeMozart08.png");
  final URL dialog09 =
    getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/NodeMozart09.png");
  final URL dialog10 =
    getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/NodeMozart10.png");
  final URL dialog11 =
    getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/NodeMozart11.png");
  final URL dialog12 =
    getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/NodeMozart12.png");

  /**
   * Initializes the object with a reference to the class that will actually
   * drive the animations.
   * 
   * @param app instance of the main Application class
   */
  public NodeDialogC3D(Application app)
  {
    super();
    this.app = app;
    dialogBG = new BranchGroup();
    dialogAG = createDialog();
    dialogBG.addChild(dialogAG);
    this.addChild(dialogBG);
    addMouseClickedListener();
    // setMouseEventPropagatable(true);
    setCursor(Cursor3D.SMALL_CURSOR);
  }

  /**
   * Runs an animation that flys the dialog into view. And, it sets up an
   * animation finished event with an event listner to do something once the
   * animation stops. The event is consumed by
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.events.StartupSequenceAction StartupSequenceAction}. 
   * 
   * @param millisecs how long to run the flying animation
   * @param nextStep which step number to execute when the animation finishes
   */
  public void zoomIntoView(int millisecs, int nextStep, BasicNode kn)
  {
    currentKNode = kn;
    if (kn.getNodeId() == 1168 /* Mozart */) changeTexture(dialog01);
    else changeTexture(dialog00);
    this.setVisible(true);
    zoomIntoViewTA =
      new TranslationAnimationBoolean(new Vector3f(0f, 0f, 0.8f),
                                      new Vector3f(-0.03f, -0.02f, 0.1f),
                                      millisecs);
    dialogAG.setAnimation(zoomIntoViewTA);
    LgEventConnector.getLgEventConnector().addListener(zoomIntoViewTA,
            new GenericEventAdapter(LgEvent.class,
                                    new SearchDialogSequenceAction(app, nextStep))
            );
    zoomIntoViewTA.setAnimationFinishedEvent(LgEvent.class);
    zoomIntoViewTA.performAction(null, true);
    step = 0;
  }
  
  /**
   * Runs an animation that flys the dialog out of view and makes it invisible.
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
      new TranslationAnimationBoolean(new Vector3f(-0.03f, -0.02f, 0.1f),
                                      new Vector3f(0f, 0f, 0.8f),
                                      millisecs,
                                      new AcceleratingVector3fSmoother());
    dialogAG.setAnimation(zoomOutOfViewTA);
    LgEventConnector.getLgEventConnector().addListener(zoomOutOfViewTA,
            new GenericEventAdapter(LgEvent.class,
                                    new SearchDialogSequenceAction(app, nextStep))
            );
    zoomOutOfViewTA.setAnimationFinishedEvent(LgEvent.class);
    zoomOutOfViewTA.performAction(null, true);
    step = -1;
  }

  /**
   * Handles mouse clicks on the dialog.  Any mouse click that isn't at a
   * step that makes sense for the mouse at that time causes the dialog to
   * close.  Otherwise, the mouse clicks appear to enable check boxes and do
   * a node. 
   */
  public void handleMouseClick()
  {
    int id = currentKNode.getNodeId();
    if (step == 0 && id == 1168 /* Mozart */) step = 10;
    switch (step)
      {
      case 10: changeTexture(dialog02); step = 11; break;
      case 11: app.nodeDialogSequence(2, id, currentKNode); break;
      default: app.nodeDialogSequence(3, id, currentKNode);
      }
  }

  /**
   * Switches textures on the dialog to simulate an active dialog UI.
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
        dialogApp.setTexture(filename);
      }
    catch (FileNotFoundException e)
      {
        System.err.println(
              "Kwebdemo1 NodeDialogC3D changeTexture: " +
              e.toString());
      }
    catch (IOException e)
      {
        System.err.println(
              "Kwebdemo1 NodeDialogC3D changeTexture: " +
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
  private AnimationGroup createDialog()
  {
    Appearance boxApp =
      new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                           SimpleAppearance.DISABLE_CULLING);
    dialogBox = new GlassyPanel(0.1f, 0.05f, 0.005f, boxApp);
    AnimationGroup ag = new AnimationGroup();
    ag.addChild(dialogBox);
    try
      {
        dialogShape3D = createDialogShape3D(dialog00, 0.1f, 0.05f);
        ag.addChild(dialogShape3D);
      }
    catch (FileNotFoundException e)
      {
        System.err.println("Kwebdemo1 NodeDialog(): " +
                           e.toString());
      }
    catch (IOException e)
      {
        System.err.println("Kwebdemo1 NodeDialog(): " +
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
  private Shape3D createDialogShape3D(URL filename,
                                      float width, float height)
    throws FileNotFoundException, IOException
  {
    TextureAttributes ta = new TextureAttributes();
    ta.setTextureMode(TextureAttributes.REPLACE);
    dialogApp =
      new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                           SimpleAppearance.ENABLE_TEXTURE |
                           SimpleAppearance.DISABLE_CULLING);
    dialogApp.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
    dialogApp.setTextureAttributes(ta);
    // dialogApp.setTexture(filename);
    QuadArray labelGeometry = setDialogGeometry(width, height);
    Shape3D dialog =
      new Shape3D(labelGeometry, dialogApp);
    dialog.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
    dialog.setCapability(Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
    return dialog;
  }

  /**
   * Creates a simple rectangle geometry which has it's registration point
   * in the center of the rectangle with a texture covering the entire surface.
   * That means that the aspect ratio of the geometry must match the
   * aspect ratio of the image in order to avoid distortions.
   *  
   * @return a rectangle geometry that can be textured
   */
  private QuadArray setDialogGeometry(float width, float height)
  {
    QuadArray dialogGeometry = new QuadArray(4,
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
    dialogGeometry.setCoordinates(0, geomCoords);
    dialogGeometry.setTextureCoordinates(0, 0, texCoords);
    return dialogGeometry;    
  }

  /**
   * Add a mouse button release listener to this dialog. Delegates events to
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.events.NodeDialogMouseClickedAction NodeDialogMouseClickedAction}. 
   */
  private void addMouseClickedListener()
  {
    addListener(new MouseClickedEventAdapter(
                  new NodeDialogMouseClickedAction(this)));
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
