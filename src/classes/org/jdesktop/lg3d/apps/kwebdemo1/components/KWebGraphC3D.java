/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/components/KWebGraphC3D.java,v 1.4 2006-12-13 22:01:40 paulby Exp $
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

import java.util.Iterator;

import javax.media.j3d.Node;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import javax.vecmath.Matrix3f;

import org.jdesktop.lg3d.apps.kwebdemo1.Application;
import org.jdesktop.lg3d.apps.kwebdemo1.onionskins.Century17thSkin;
import org.jdesktop.lg3d.apps.kwebdemo1.onionskins.Century18thSkin;
import org.jdesktop.lg3d.apps.kwebdemo1.onionskins.Century19thSkin;
import org.jdesktop.lg3d.apps.kwebdemo1.onionskins.Century20thSkin;
import org.jdesktop.lg3d.apps.kwebdemo1.onionskins.GatewaySkin;
import org.jdesktop.lg3d.sg.BranchGroup;
import org.jdesktop.lg3d.sg.TransformGroup;
import org.jdesktop.lg3d.sg.PickBounds;
import org.jdesktop.lg3d.sg.SceneGraphPath;
import org.jdesktop.lg3d.sg.TextureAttributes;
import org.jdesktop.lg3d.utils.action.ActionBooleanInt;
import org.jdesktop.lg3d.utils.animation.RotationAnimation;
import org.jdesktop.lg3d.utils.animation.RotationAnimationBoolean;
import org.jdesktop.lg3d.utils.animation.TimedAnimation;
import org.jdesktop.lg3d.utils.smoother.LinearFloatSmoother;
import org.jdesktop.lg3d.utils.smoother.AcceleratingVector3fSmoother;
import org.jdesktop.lg3d.wg.AnimationGroup;
import org.jdesktop.lg3d.wg.Component3D;
import java.lang.Integer;
import java.net.URLDecoder;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.vecmath.Point3f;

import org.jdesktop.lg3d.sg.Appearance;
import org.jdesktop.lg3d.sg.GeometryArray;
import org.jdesktop.lg3d.sg.ImageComponent;
import org.jdesktop.lg3d.sg.ImageComponent2D;
import org.jdesktop.lg3d.sg.LineArray;
import org.jdesktop.lg3d.sg.LineAttributes;
import org.jdesktop.lg3d.sg.OrientedShape3D;
import org.jdesktop.lg3d.sg.QuadArray;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.sg.Texture;
import org.jdesktop.lg3d.sg.Texture2D;
import org.jdesktop.lg3d.sg.Transform3D;
import org.jdesktop.lg3d.sg.utils.image.TextureLoader;
import org.jdesktop.lg3d.sg.ImageComponent2D;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.utils.action.AppearanceChangeAction;
import org.jdesktop.lg3d.utils.action.RotateActionBoolean;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.animation.TranslationAnimationBoolean;
import org.jdesktop.lg3d.utils.eventadapter.GenericEventAdapter;
import org.jdesktop.lg3d.utils.eventadapter.MouseEnteredEventAdapter;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.LgEventConnector;
import org.jdesktop.lg3d.apps.kwebdemo1.singletons.*;
import org.jdesktop.lg3d.apps.kwebdemo1.onionskins.*;
import org.jdesktop.lg3d.apps.kwebdemo1.nodes.*;
import org.jdesktop.lg3d.apps.kwebdemo1.edges.*;
import org.jdesktop.lg3d.apps.kwebdemo1.events.*;

/**
 * The component that handles the KWeb graph animations.
 * 
 * This class can move, rotate and selectively show different parts of the
 * KWeb graph.  It also setups up event listeners, actions that
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
public class KWebGraphC3D extends Component3D
{
  private Application app = null;
  private BranchGroup kwebGraphBG = null;
  private AnimationGroup kwebGraphAG = null;
  private TransformGroup kwebGraphTG = null;
  private RotationAnimation kwebGraphRA = null;
  private RotationAnimationBoolean kwebGraphRAB = null;
  private Hashtable<Integer,Onionskin> skins = new Hashtable<Integer,Onionskin>();
  private Texture oneLinerTexture = null;
  private Appearance oneLinerApp = null;
  private TranslationAnimationBoolean zoomIntoViewTA;  
  private TranslationAnimationBoolean zoomInMoreTA;
  private TranslationAnimationBoolean kwebGraphTA;
  /**
   * Initializes the object with a reference to the class that will actually
   * drive the animations.
   * 
   * @param app instance of the main Application class
   */
  public KWebGraphC3D(Application app)
  {
    super();
    this.app = app;
    kwebGraphBG = new BranchGroup();
    kwebGraphAG = new AnimationGroup();
    kwebGraphAG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    kwebGraphTG = createKWebGraph();
    kwebGraphAG.addChild(kwebGraphTG);
    kwebGraphBG.addChild(kwebGraphAG);
    this.addChild(kwebGraphBG);
    addListener(new KWebMouseEnteredEventAdapter(
      new KWebMouseEnteredAction(app)));
    addListener(new KWebMouseClickedEventAdapter(
      new KWebMouseClickedAction(app)));
  }

  /**
   * Runs an animation that brings the KWeb graph into view. And, it sets up an
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
      new TranslationAnimationBoolean(new Vector3f(0f, 0f, -2f),
                                      new Vector3f(0f, 0f, 0f),
                                      millisecs);
    kwebGraphAG.setAnimation(zoomIntoViewTA);
    LgEventConnector.getLgEventConnector().addListener(zoomIntoViewTA,
            new GenericEventAdapter(LgEvent.class,
                                    new StartupSequenceAction(app, nextStep))
            );
    zoomIntoViewTA.setAnimationFinishedEvent(LgEvent.class);
    zoomIntoViewTA.performAction(null, true);
  }
  
  /**
   * Runs an animation that brings the KWeb graph closer into view. And, it
   * sets up an animation finished event with an event listner to do something
   * once the animation stops. The event is consumed by
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.events.SearchDialogSequenceAction SearchDialogSequenceAction}
   * which is a hack as it's much too specific for this method.  Probably should
   * pass the <code>ActionNoArg</code> class as an argument to this method so
   * we can associated this zoom with different sequences.
   * 
   * @param millisecs how long to run the flying animation
   * @param nextStep which step number to execute when the animation finishes
   */
  public void zoomInMore(int millisecs, int nextStep)
  {
    zoomInMoreTA =
      new TranslationAnimationBoolean(new Vector3f(0f, 0f, 0f),
                                      new Vector3f(0f, 0f, 0.3f),
                                      millisecs);
    kwebGraphAG.setAnimation(zoomInMoreTA);
    LgEventConnector.getLgEventConnector().addListener(zoomInMoreTA,
            new GenericEventAdapter(LgEvent.class,
                                    new SearchDialogSequenceAction(app, nextStep))
            );
    zoomInMoreTA.setAnimationFinishedEvent(LgEvent.class);
    zoomInMoreTA.performAction(null, true);
  }
  
  /**
   * Runs an animation that pulls the KWeb back. And, it
   * sets up an animation finished event with an event listner to do something
   * once the animation stops. The event is consumed by
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.events.SearchDialogSequenceAction SearchDialogSequenceAction}
   * which is a hack as it's much too specific for this method.  Probably should
   * pass the <code>ActionNoArg</code> class as an argument to this method so
   * we can associated this zoom with different sequences.
   * 
   * @param millisecs how long to run the flying animation
   * @param nextStep which step number to execute when the animation finishes
   */
  public void zoomBack(int millisecs, int nextStep)
  {
    kwebGraphTA =
      new TranslationAnimationBoolean(new Vector3f(0f, 0f, 0.3f),
                                      new Vector3f(0f, 0f, 0.0f),
                                      millisecs);
    kwebGraphAG.setAnimation(kwebGraphTA);
    LgEventConnector.getLgEventConnector().addListener(kwebGraphTA,
            new GenericEventAdapter(LgEvent.class,
                                    new SearchDialogSequenceAction(app, nextStep))
            );
    kwebGraphTA.setAnimationFinishedEvent(LgEvent.class);
    kwebGraphTA.performAction(null, true);
  }
  /**
   * Runs an animation that brings the desired node of the KWeb graph into view.
   * And, it sets up an animation finished event with an event listner to do
   * something once the animation stops. The event is consumed by
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.events.StartupSequenceAction StartupSequenceAction}. 
   * 
   * <p>Not finished yet.  Only vertical rotations work.</p>
   * 
   * @param millisecs how long to run the flying animation
   * @param nextStep which step number to execute when the animation finishes
   */
  public void rotateToNode(int nodeId, int millisecs, int nextStep)
  {
    NodeData nodeData = NodeData.getInstance();
    BasicNode kn = nodeData.getNode(nodeId);
    if (kn == null) return;
    kwebGraphRAB =
        new RotationAnimationBoolean(new Vector3f(1f,0f,0f),
                             (float) 0f,
                             (float) -(kn.phi - Math.PI / 2.0d),
                             millisecs);
    kwebGraphAG.setAnimation(kwebGraphRAB);
    LgEventConnector.getLgEventConnector().addListener(kwebGraphRAB,
            new GenericEventAdapter(LgEvent.class,
                                    new SearchDialogSequenceAction(app, nextStep))
            );
    kwebGraphRAB.setAnimationFinishedEvent(LgEvent.class);
    kwebGraphRAB.performAction(null, true);
  }

  /**
   * Highlights only the selected node and its immediate neighbors to make
   * finding what's important to look at easier to see.
   * 
   * @param nodeId KWeb node id to highlight
   * @param state true if only selected nodes are to highlighted, false to
   *   return to all node labels showing; (the nodeId is actually ignored in
   *   the false case).
   */
  public void highlightNode(int nodeId, boolean state)
  {
    NodeData nodeData = NodeData.getInstance();
    BasicNode kn = nodeData.getNode(nodeId);
    if (kn == null) return; 
    if (state)
      {  
        showLabels(0, false);
        showLabels(20, false);
        showLabels(19, false);
        showLabels(18, false);
        showLabels(17, false);
        Iterator i = kn.displayedEdges.iterator();
        int oppositeId = 0;
        BasicNode oppositeNode = null;
        kn.showLabel(state);
        while (i.hasNext())
          {
            DisplayedEdge de = (DisplayedEdge) i.next();
            de.setEdgeOn(true);
            oppositeId = de.getOppositeNodeId(kn.getNodeId());
            oppositeNode = nodeData.getNode(oppositeId);
            oppositeNode.showLabel(state);
          }
        showOneLiner(nodeData, kn);
      }
    else
     {
        /*
         * Turn all labels on and all egde highlights off.
         */
        showLabels(0, true);
        showLabels(20, true);
        showLabels(19, true);
        showLabels(18, true);
        showLabels(17, true);
        EdgeData edgeData = EdgeData.getInstance();
        Enumeration<BasicEdge> edgeList;
        for (edgeList = edgeData.elements(); edgeList.hasMoreElements(); )
          {
            BasicEdge edge = edgeList.nextElement();
            if (edge instanceof DisplayedEdge)
              {
                DisplayedEdge de = (DisplayedEdge)edge;
                de.setEdgeOn(false);
              }
          }
     }
  }
    
  /**
   * Runs an animation that slowly rotates the KWeb graph.  This was
   * specifically regquested by James Burke. The KWebGraph will continously
   * rotate once every 100 seconds until stopped by
   * <code>stopSlowRotation</code>. There are no events associated with this
   * animation.
   * 
   * <p>While this
   * animation is running, I don't think LG lets you running any of the
   * event driven animations or change the appearance of the graph.  I might
   * be wrong, but it seems that way to me.  Anyway, you should stop this
   * particular animation when it's time to do <em>real</em> stuff.</p>
   */
  public void startSlowRotation()
  {
    /*
     * Setup a slow continous rotation.  Hope I can figure out a way to stop it.
     */
    if (kwebGraphRA != null && kwebGraphRA.isRunning()) return;
    kwebGraphRA =
        new RotationAnimation(new Vector3f(0f,1f,0f),
                              0f, (float)Math.toRadians(360),
                              100000, RotationAnimation.LOOP_FOREVER,
                              RotationAnimation.LoopType.REPEAT,
                              new LinearFloatSmoother());
    kwebGraphAG.setAnimation(kwebGraphRA);
    kwebGraphRA.setRunning(true);
  }

  /**
   * Stops the animation started by <code>startSlowRotation</code>. Then
   * rotates the KWeb graph more rapidly and by the shortest rotation back to
   * its initial starting point.
   * 
   * <p><em>Implementation:</em> We do this by getting the
   * <code>Transform3D</code> of the <code>AnimationGroup</code>. From there
   * I get the 3x3 rotation matrix. Because the rotation is around the
   * <em>y</em>-axis the top row is all we need which is a unit vector of
   * (<em>x</em>, 0, <em>z</em>).  The longitude is then obtained from
   * <em>arctan</em>(<em>z</em>/<em>x</em>).  We plug that in as the starting
   * point for the return-to-zero animation and run a 10 second animation that
   * returns to KWeb back to longitute 0 facing the viewer. <code>m00</code> is
   * <em>x</em>; <code>m02</code> is <em>z</em>. I tried using
   * <code>this.getFinalRotationAngle()</code> but it always returns 0.0, most
   * likely because it assumes we are using the <code>Component3D</code> 
   * rotation methods which we are not.</p>
   */
  public void stopSlowRotation()
  {
    if (kwebGraphRA != null)
      {
        Transform3D currentKWebTransform = new Transform3D();
        if (kwebGraphRA.isRunning()) kwebGraphRA.setRunning(false);
        kwebGraphAG.getTransform(currentKWebTransform);
        Matrix3f kwebRotationMatrix = new Matrix3f();
        currentKWebTransform.get(kwebRotationMatrix);
        double theta =
            Math.atan(kwebRotationMatrix.m02 / kwebRotationMatrix.m00);
        if (Math.abs(theta) > 0.0001d)
          {
            kwebGraphRA =
              new RotationAnimation(new Vector3f(0f,1f,0f),
                                   (float) theta, 0f,
                                   4000, 1, RotationAnimation.LoopType.ONCE);
            kwebGraphAG.setAnimation(kwebGraphRA);
            kwebGraphRA.setRunning(true);
          }
      }
  }

  /**
   * Makes an onionskin complete opaque hidding all the onionskins and node
   * nested within it.
   * 
   * @param cent This probably should be obtained from one of the onionskin
   * classes like:
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.onionskins.Century17thSkin Century17thSkin.CENTURY}
   *   
   * @return true if skin exists for century, false otherwise.
   */
  public boolean setOnionOpaque(int cent)
  {
    Onionskin skin = skins.get(new Integer(cent));
    if (skin != null)
      {
        skin.setOnionOpaque();
        return true;
      }
    return false;
  }

  /**
   * Makes an onionskin 80% translucent (more clear than solid) so you can
   * still discern the skin but you can see all its insides, (kind of like x-ray
   * vision).
   * 
   * @param cent This probably should be obtained from one of the onionskin
   * classes like:
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.onionskins.Century20thSkin Century20thSkin.CENTURY}
   *   
   * @return true if skin exists for century, false otherwise.
   */
  public boolean setOnionTranslucent(int cent)
  {
    Onionskin skin = skins.get(new Integer(cent));
    if (skin != null)
      {
        skin.setOnionTranslucent();
        return true;
      }
    return false;
  }

  /**
   * Makes an onionskin 100% translucent (invisible) so you can
   * see all the details inside without any <em>fogging</em>. Doesn't seem to
   * work as expected.  The transparency doesn't stick for some reason.
   * 
   * @param cent This probably should be obtained from one of the onionskin
   * classes like:
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.onionskins.GatewaySkin GatewaySkin.CENTURY}
   *   
   * @return true if skin exists for century, false otherwise.
   */
  public boolean setOnionTransparent(int cent)
  {
    Onionskin skin = skins.get(new Integer(cent));
    if (skin != null)
      {
        skin.setOnionTransparent();
        return true;
      }
    return false;
  }

  /**
   * Shows or hides node labels for a particular century (skin).
   * 
   * @param cent This probably should be obtained from one of the onionskin
   * classes like:
   * {@link org.jdesktop.lg3d.apps.kwebdemo1.onionskins.GatewaySkin GatewaySkin.CENTURY}
   * @param show true means show; false means hide.
   *   
   * @return true if skin exists for century, false otherwise.
   */
  public boolean showLabels(int cent, boolean show)
  {
    Onionskin skin = skins.get(new Integer(cent));
    if (skin != null)
      {
        skin.showLabels(show);
        return true;
      }
    return false;
  }

  /**
   * Contructs the overall SceneGraph component of the KWeb graph.
   * 
   * @return KWeb graph component
   */
  private TransformGroup createKWebGraph()
  {
    /*
     * Create a TransformGroup under the animation group in case we need to
     * move the KWeb in some other way.
     */
    TransformGroup kwebGraphTG = new TransformGroup();
    /*
     * Node placement for each skin
     */
    GatewaySkin gatewaySkin = new GatewaySkin(0.07f, new Color3f(0f, 0f, 1f));
    Century20thSkin century20thSkin = new Century20thSkin(0.06f, new Color3f(0f,1f,0f));
    Century19thSkin century19thSkin = new Century19thSkin(0.05f, new Color3f(1f,0f,0f));  
    Century18thSkin century18thSkin = new Century18thSkin(0.04f, new Color3f(0f,1f,1f));  
    Century17thSkin century17thSkin = new Century17thSkin(0.03f, new Color3f(1f,1f,0f));
    /*
     * Add each skin to a skins hashtable
     */
    skins.put(new Integer(GatewaySkin.CENTURY), gatewaySkin);
    skins.put(new Integer(Century20thSkin.CENTURY), century20thSkin);
    skins.put(new Integer(Century19thSkin.CENTURY), century19thSkin);
    skins.put(new Integer(Century18thSkin.CENTURY), century18thSkin);
    skins.put(new Integer(Century17thSkin.CENTURY), century17thSkin);
    /*
     * Render the nodes in the scenegraph
     */
    kwebGraphTG.addChild(gatewaySkin.newBranchGroup());
    kwebGraphTG.addChild(century20thSkin.newBranchGroup());
    kwebGraphTG.addChild(century19thSkin.newBranchGroup());
    kwebGraphTG.addChild(century18thSkin.newBranchGroup());
    kwebGraphTG.addChild(century17thSkin.newBranchGroup());
    /*
     * Create and render the Graph Edges
     */
    kwebGraphTG.addChild(createEdges());
    /*
     * Render semi-transparent spheres to help visualize the skins better
     */
    kwebGraphTG.addChild(gatewaySkin.newTransparentSphereGroup());
    kwebGraphTG.addChild(century20thSkin.newTransparentSphereGroup());
    kwebGraphTG.addChild(century19thSkin.newTransparentSphereGroup());
    kwebGraphTG.addChild(century18thSkin.newTransparentSphereGroup());
    kwebGraphTG.addChild(century17thSkin.newTransparentSphereGroup());
    /*
     * Set the kweb graph into a slow rotation
     */
    return kwebGraphTG;
  }

  /**
   * Creates all the visible graph edges of the KWeb.
   * 
   * I'm not sure if I like this method and may do this another way pretty soon.
   * Because of z-ordering issues in Java3D, it became semi-difficult to pick
   * nodes if the edges where rendered over the node.  Therefore, I decided to
   * make the edge non-pickable even though I really wanted to leave them as
   * pickable so I could highlight the predicates (transitional phrase) between
   * nodes. Since I won't have time to implemented edge picking before JavaOne
   * I made the edges non-pickable for now. Anyway, we need to revisit this
   * issue.
   *  
   * @return a SceneGraph BranchGroup containing all the visible edges of the
   *   graph
   */
  private BranchGroup createEdges()
  {
    BranchGroup bg = new BranchGroup();
    bg.setPickable(false);
    LineAttributes defaultLA =
      new LineAttributes(2f, LineAttributes.PATTERN_SOLID, true);
    LineAttributes highlightLA =
      new LineAttributes(8f, LineAttributes.PATTERN_SOLID, true);
    EdgeData edgeData = EdgeData.getInstance();
    Enumeration<BasicEdge> edgeList;
    for (edgeList = edgeData.elements(); edgeList.hasMoreElements(); )
      {
        BasicEdge edge = edgeList.nextElement();
        if (edge instanceof DisplayedEdge)
          {
            Appearance edgeApp = new Appearance();
            edgeApp.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
            edgeApp.setLineAttributes(defaultLA);
            DisplayedEdge dEdge = (DisplayedEdge)edge;
            Shape3D visibleEdge =
              dEdge.newShape3D(edgeApp, defaultLA, highlightLA, skins);
            bg.addChild(visibleEdge);
          }
      }
    return bg;
  }

  /**
   * Displays the one liner at the top of the screen for the selected node.
   * 
   * @param nd list of nodes
   * @param knode this node
   */
  public void showOneLiner(NodeData nd, BasicNode knode)
  {
    String oneLiner = knode.getOneLiner();
    BufferedImage image = oneLinerImage(oneLiner, nd.getNodeFontName());
    TextureLoader loader = new TextureLoader(image);
    oneLinerTexture = loader.getTexture();
    oneLinerApp.setTexture(oneLinerTexture);
  }

  /**
   * Creates the one liner Component3D.  This approach isn't consistent with
   * the other Component3D creations, but I needed to access some internal
   * data within the KWebGraphC3D component, so it's created with that fact
   * in mind.  I may change my mind later and create by subclassing
   * Component3D in the same way the other components are created.
   * 
   * @param nd reference to NodeData instance since KWebGraphC3D already has
   *   it
   * @param oneLiner default one liner displayed before anything is selected.
   * @return an instance of the one liner component.
   */
  public Component3D createOneLinerC3D(NodeData nd, String oneLiner)
  {
    Component3D oneLinerC3D = new Component3D();
    oneLinerC3D.setPickable(false);
    Shape3D s3d = createOneLinerShape(nd, oneLiner);
    Transform3D t3d = new Transform3D();
    t3d.setTranslation(new Vector3f(0.0f, 0.077f, 0.0f));
    t3d.setScale(1.3f);
    TransformGroup tg = new TransformGroup(t3d);
    tg.addChild(s3d);
    oneLinerC3D.addChild(tg);
    return oneLinerC3D;
  }

  /**
   * I was going to use ImagePanel for this but swapping textures with it
   * is painfully slow due to the way it is implemented, so I basically wrote
   * my own version of it that allows me to swap textures more efficiently.
   */
  private Shape3D createOneLinerShape(NodeData nd, String oneLiner)
  {
    BufferedImage image = oneLinerImage(oneLiner, nd.getNodeFontName());
    TextureLoader loader = new TextureLoader(image);
    oneLinerTexture = loader.getTexture();
    TextureAttributes ta = new TextureAttributes();
    ta.setTextureMode(TextureAttributes.REPLACE);
    oneLinerApp =
      new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                           SimpleAppearance.ENABLE_TEXTURE |
                           SimpleAppearance.DISABLE_CULLING);
    oneLinerApp.setCapability(Appearance.ALLOW_TEXTURE_WRITE);
    oneLinerApp.setTextureAttributes(ta);
    oneLinerApp.setTexture(oneLinerTexture);
    QuadArray labelGeometry = setOneLinerGeometry();
    Point3f labelPt = new Point3f(0f,0.0f,0.0f);
    OrientedShape3D oneLinerShape =
      new OrientedShape3D(labelGeometry, oneLinerApp,
                          OrientedShape3D.ROTATE_ABOUT_POINT,
                          labelPt);
    oneLinerShape.setCapability(OrientedShape3D.ALLOW_APPEARANCE_WRITE);
    oneLinerShape.setCapability(OrientedShape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
    return oneLinerShape;
  }

  /**
   * Pixel width/height of image to create for texturing.  
   */
  private static final int WIDTH = 1024;
  private static final int HEIGHT = 32;

  /**
   * Creates a simple rectangle geometry which has it's registration point
   * in the center of the rectangle with a texture covering the entire surface.
   * That means that the aspect ratio of the geometry must match the
   * aspect ratio of the image in order to avoid distortions.
   *  
   * @return a rectangle geometry that can be textured
   */
  private QuadArray setOneLinerGeometry()
  {
    QuadArray oneLinerGeometry = new QuadArray(4,
      GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2);
    float[] geomCoords = new float[]
      {
        -0.064f, -0.002f, 0f,
        0.064f, -0.002f, 0f,
        0.064f, 0.002f, 0f,
        -0.064f, 0.002f, 0f
      };
    float[] texCoords = new float[]
      {
        0f, 1f,
        1f, 1f,
        1f, 0f,
        0f, 0f,
      };
    oneLinerGeometry.setCoordinates(0, geomCoords);
    oneLinerGeometry.setTextureCoordinates(0, 0, texCoords);
    return oneLinerGeometry;    
  }

  /**
   * This renders a text image where the black text is surrounded by a white
   * halo. This makes the text easier to read under a variety of ligthing
   * conditions.
   * 
   * <p>A little bit of <em>feathering<em> is done around the edges to make the
   * text blend in with the background. This is done by changing the alpha
   * transparency of the outer edges to 50% translucent</p>
   * 
   * @TODO This should be moved to a util class.
   * 
   * @param text text to be rendered as a bitmap
   * @param fontName font to use.  This is needed because different OSes have
   *   different sets of fonts and I wanted to use one that had a modern look.
   * @return a bitmap image of the rendered text.
   */
  private BufferedImage oneLinerImage(String text, String fontName)
  {
      int style = Font.BOLD;
      BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
                                              BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = (Graphics2D) image.getGraphics();
      g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                         RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g.setFont(new Font(fontName, style, 22));
      g.setColor(Color.WHITE);
      AlphaComposite composite = 
        AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
      g.setComposite(composite);
      drawStringAsHalo(g, text, 2);
      composite = 
          AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
      g.setComposite(composite);
      g.setColor(Color.BLACK);
      g.drawString(text, 4, 24);
      g.dispose();
      return image;
  }

  /**
   * Draws the text offset from the center in all directions.
   * 
   * <p>Offsets at 45 degrees are at the squareroot of the sum of squares. But,
   * since the x and y offset is the same, that reduces to x or y divided by
   * the squareroot of 2.</p>
   * 
   * @param g Graphics context (assumes it was already setup)
   * @param text string to be rendered
   * @param offset how much of a pixel offset to use for rendering the halo.
   */
  private void drawStringAsHalo(Graphics2D g, String text, int offset)
  {
    int offset2 = (int) Math.round((float) offset / Math.sqrt(2.0));
    g.drawString(text, 4-offset, 24);
    g.drawString(text, 4-offset2, 24-offset2);
    g.drawString(text, 4, 24-offset);
    g.drawString(text, 4+offset2, 24-offset2);
    g.drawString(text, 4+offset, 24);
    g.drawString(text, 4+offset2, 24+offset2);
    g.drawString(text, 4, 24+offset);
    g.drawString(text, 4-offset2, 24+offset2);
  }

  /**
   * This creates a texture from an image.
   * 
   * I tested this and it works but I decided to use the TextureLoader which
   * does the same thing with less effort.
   * 
   * @TODO This should be moved to a util class.
   * 
   * @param image
   * @return a texture of the image which can be pasted to 3D surfaces.
   */
  private Texture2D oneLinerTexture(BufferedImage image)
  {
    ImageComponent2D img =
      new ImageComponent2D(ImageComponent.FORMAT_RGBA, image);
    Texture2D tex =
      new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                    image.getWidth(), image.getHeight());
    tex.setMinFilter(Texture.BASE_LEVEL_LINEAR);
    tex.setMagFilter(Texture.BASE_LEVEL_LINEAR);
    tex.setImage(0, img);
    return tex;
  }
  
}
