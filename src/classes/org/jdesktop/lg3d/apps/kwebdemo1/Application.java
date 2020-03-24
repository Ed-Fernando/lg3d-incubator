/*
 * $Header: /zpool01/javanet/scm/svn/tmp/cvs2svn/lg3d-incubator/src/classes/org/jdesktop/lg3d/apps/kwebdemo1/Application.java,v 1.5 2006-04-19 17:24:37 paulby Exp $
 * $Date: 2006-04-19 17:24:37 $
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

package org.jdesktop.lg3d.apps.kwebdemo1;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.kwebdemo1.components.*;
import org.jdesktop.lg3d.apps.kwebdemo1.singletons.FixNodePosData;
import org.jdesktop.lg3d.apps.kwebdemo1.singletons.NodeData;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Thumbnail;
import org.jdesktop.lg3d.utils.shape.Disc;
import org.jdesktop.lg3d.utils.shape.RingShadow;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.sg.Shape3D;
import org.jdesktop.lg3d.apps.kwebdemo1.nodes.*;

/**
 * James Burke's Knowledge Web main application.
 * 
 * @author Jim Zaun &lt;jz-lg@zaun.com&gt; (a.k.a. &lt;zaun@acm.org&gt;)
 * @version $Revision: 1.5 $
 * @since JDK 1.5.0, Java3D 1.4.0, lg3d-{core,demo,incubator} 0.7.0
 */
public class Application extends Object
{
  public int journeyIdx = 0;
  private Frame3D frame3d = null;  
  private KWebThumbnail thumbnail = null;
  private BigGlassyPanelC3D bigGlassyPanelC3D = null;
  private SplashScreenC3D kwebSplashC3D = null;
  private KWebGraphC3D kwebGraphC3D = null;
  private AgesInHistoryC3D agesInHistoryC3D = null;
  private GatewaysLegendC3D gatewaysLegendC3D = null;
  private Century20thLegendC3D century20thLegendC3D = null;
  private Century19thLegendC3D century19thLegendC3D = null;
  private Century18thLegendC3D century18thLegendC3D = null;
  private Century17thLegendC3D century17thLegendC3D = null;
  private SearchButtonC3D searchButtonC3D = null;
  private JourneyButtonC3D journeyButtonC3D = null;
  private TimelineButtonC3D timelineButtonC3D = null;
  private LocationButtonC3D locationButtonC3D = null;
  private CloseButtonC3D closeButtonC3D = null;
  private CreditsScreenC3D creditsScreenC3D = null;
  private SearchDialogC3D searchDialogC3D = null;
  private JourneyDialogC3D journeyDialogC3D = null;
  private NodeDialogC3D nodeDialogC3D = null;
  private BioDialogC3D bioDialogC3D = null;
  private Component3D oneLinerC3D = null;

  /**
   * Private constructor, so this code must be invoked through main().
   */
  private Application()
  {
    NodeData nodeData = NodeData.getInstance();
    FixNodePosData.doit(nodeData);
    frame3d = new Frame3D();
    // The thumbnail must be set while the frame3d is empty or else it
    // gives us an error for some reason.
    thumbnail = new KWebThumbnail();
    frame3d.setThumbnail(thumbnail);
    frame3d.setName("KWeb Demo #1");
    /*
     * Create Component3D's
     */
    bigGlassyPanelC3D = new BigGlassyPanelC3D(this);
    kwebSplashC3D = new SplashScreenC3D(this);
    kwebGraphC3D = new KWebGraphC3D(this);
    agesInHistoryC3D = new AgesInHistoryC3D(this);
    gatewaysLegendC3D = new GatewaysLegendC3D(this);
    century20thLegendC3D = new Century20thLegendC3D(this);
    century19thLegendC3D = new Century19thLegendC3D(this);
    century18thLegendC3D = new Century18thLegendC3D(this);
    century17thLegendC3D = new Century17thLegendC3D(this);
    searchButtonC3D = new SearchButtonC3D(this);
    journeyButtonC3D = new JourneyButtonC3D(this);
    timelineButtonC3D = new TimelineButtonC3D(this);
    locationButtonC3D = new LocationButtonC3D(this);
    closeButtonC3D = new CloseButtonC3D(this);
    creditsScreenC3D = new CreditsScreenC3D(this);
    searchDialogC3D = new SearchDialogC3D(this);
    journeyDialogC3D = new JourneyDialogC3D(this);
    nodeDialogC3D = new NodeDialogC3D(this);
    bioDialogC3D = new BioDialogC3D(this);
    oneLinerC3D = kwebGraphC3D.createOneLinerC3D(nodeData, "One Liner");
    /*
     * Make everything invisible to start with
     */
    bigGlassyPanelC3D.setVisible(false);
    kwebSplashC3D.setVisible(false);
    kwebGraphC3D.setVisible(false);
    agesInHistoryC3D.setVisible(false);
    gatewaysLegendC3D.setVisible(false);
    century20thLegendC3D.setVisible(false);
    century19thLegendC3D.setVisible(false);
    century18thLegendC3D.setVisible(false);
    century17thLegendC3D.setVisible(false);
    searchButtonC3D.setVisible(false);
    journeyButtonC3D.setVisible(false);
    timelineButtonC3D.setVisible(false);
    locationButtonC3D.setVisible(false);
    closeButtonC3D.setVisible(false);
    creditsScreenC3D.setVisible(false);
    searchDialogC3D.setVisible(false);
    journeyDialogC3D.setVisible(false);
    nodeDialogC3D.setVisible(false);
    bioDialogC3D.setVisible(false);
    oneLinerC3D.setVisible(false);
    /*
     * Frame 3D setup
     */
    frame3d.addChild(bigGlassyPanelC3D);
    frame3d.addChild(kwebSplashC3D);
    frame3d.addChild(kwebGraphC3D);
    frame3d.addChild(agesInHistoryC3D);
    frame3d.addChild(gatewaysLegendC3D);
    frame3d.addChild(century20thLegendC3D);
    frame3d.addChild(century19thLegendC3D);
    frame3d.addChild(century18thLegendC3D);
    frame3d.addChild(century17thLegendC3D);
    frame3d.addChild(searchButtonC3D);
    frame3d.addChild(journeyButtonC3D);
    frame3d.addChild(timelineButtonC3D);
    frame3d.addChild(locationButtonC3D);
    frame3d.addChild(closeButtonC3D);
    frame3d.addChild(creditsScreenC3D);
    frame3d.addChild(searchDialogC3D);
    frame3d.addChild(journeyDialogC3D);
    frame3d.addChild(nodeDialogC3D);
    frame3d.addChild(bioDialogC3D);
    frame3d.addChild(oneLinerC3D);
    /*
     * Set Frame attributes
     */
    frame3d.setPreferredSize(new Vector3f(0.3f, 0.2f, 0.2f));
    frame3d.changeEnabled(true);
    frame3d.changeVisible(true);
    startupSequence(0);
  }

  /**
   * Sequences through the animation steps during startup.
   * Each step is controlled by animation completion events in the LG event
   * system.
   * 
   * <p><em>Implementation:</em> Each animation step specifies a duration
   * (millisecs) and a next step number. When each animation completes, the
   * next step is then called in the sequence. For some animations we don't
   * care what the next step is so we set those to 99 (which gets caught by
   * the default case).</p>
   * 
   * @param stepNumber the animations to perform in this step. 
   */
  public void startupSequence(int stepNumber)
  {
    switch (stepNumber)
      {
      case 0:
        bigGlassyPanelC3D.setVisible(true);
        bigGlassyPanelC3D.zoomIntoView(3000, 1);
        break;
      case 1:
        kwebSplashC3D.zoomIntoView(6000, 2);
        kwebSplashC3D.setVisible(true);
        kwebGraphC3D.zoomIntoView(15000, 99);
        kwebGraphC3D.setVisible(true);
        break;
      case 2:
        kwebSplashC3D.zoomOutOfView(3000, 3);
        // kwebSplashC3D.zoomOutOfView(6000, 11);
        break;
      case 3:
        kwebSplashC3D.setVisible(false);
        frame3d.removeChild(kwebSplashC3D);
        agesInHistoryC3D.setVisible(true);
        agesInHistoryC3D.zoomIntoView(2000, 4);
       break;
      case 4:
        gatewaysLegendC3D.setVisible(true);
        gatewaysLegendC3D.zoomIntoView(2000, 5);
        kwebGraphC3D.showLabels(0, true);  
        break;
      case 5:
        kwebGraphC3D.setOnionTranslucent(0);
        kwebGraphC3D.showLabels(0, false);  
        kwebGraphC3D.showLabels(20, true);  
        century20thLegendC3D.setVisible(true);
        century20thLegendC3D.zoomIntoView(2000, 6);
        break;
      case 6:
        kwebGraphC3D.setOnionTranslucent(20);  
        kwebGraphC3D.showLabels(20, false);  
        kwebGraphC3D.showLabels(19, true);  
        century19thLegendC3D.setVisible(true);
        century19thLegendC3D.zoomIntoView(2000, 7);
        break;
      case 7:
        kwebGraphC3D.setOnionTranslucent(19);  
        kwebGraphC3D.showLabels(19, false);  
        kwebGraphC3D.showLabels(18, true);  
        century18thLegendC3D.setVisible(true);
        century18thLegendC3D.zoomIntoView(2000, 8);
        break;
      case 8:
        kwebGraphC3D.setOnionTranslucent(18);  
        kwebGraphC3D.showLabels(18, false);  
        kwebGraphC3D.showLabels(17, true);  
        century17thLegendC3D.setVisible(true);
        century17thLegendC3D.zoomIntoView(2000, 9);
        break;
      case 9:
        kwebGraphC3D.setOnionTranslucent(17);  
        century17thLegendC3D.zoomOutOfView(5000, 20);
        century18thLegendC3D.zoomOutOfView(4000, 13);
        century19thLegendC3D.zoomOutOfView(3000, 12);
        century20thLegendC3D.zoomOutOfView(2000, 11);
        gatewaysLegendC3D.zoomOutOfView(1000, 10);
        break;
      case 10:
        kwebGraphC3D.showLabels(0, true);  
        break;
      case 11:
        kwebGraphC3D.showLabels(20, true);  
        break;
      case 12:
        kwebGraphC3D.showLabels(19, true);  
        break;
      case 13:
        kwebGraphC3D.showLabels(18, true);  
        break;
      case 20:
        kwebGraphC3D.startSlowRotation();
        agesInHistoryC3D.zoomOutOfView(1000, 21);
        gatewaysLegendC3D.setVisible(false);
        frame3d.removeChild(gatewaysLegendC3D);
        century20thLegendC3D.setVisible(false);
        frame3d.removeChild(century20thLegendC3D);
        century19thLegendC3D.setVisible(false);
        frame3d.removeChild(century19thLegendC3D);
        century18thLegendC3D.setVisible(false);
        frame3d.removeChild(century18thLegendC3D);
        century17thLegendC3D.setVisible(false);
        frame3d.removeChild(century17thLegendC3D);
        searchButtonC3D.setVisible(true);
        searchButtonC3D.zoomIntoView(1000, 99);
        journeyButtonC3D.setVisible(true);
        journeyButtonC3D.zoomIntoView(2000, 99);
        timelineButtonC3D.setVisible(true);
        timelineButtonC3D.zoomIntoView(3000, 99);
        locationButtonC3D.setVisible(true);
        locationButtonC3D.zoomIntoView(4000, 99);
        closeButtonC3D.setVisible(true);
        closeButtonC3D.zoomIntoView(5000, 99);
        break;
      case 21:
        oneLinerC3D.setVisible(true);
        agesInHistoryC3D.setVisible(false);
        frame3d.removeChild(agesInHistoryC3D);
        break;
      default:
        // System.err.println(
        //  "@@ kwebdemo1 Application startupSequence DEFAULT step: " +
        //  stepNumber);
      }
  }

  /**
   * Sequences through the animation steps during credits.
   * Each step is controlled by animation completion events in the LG event
   * system.
   * 
   * <p><em>Implementation:</em> Each animation step specifies a duration
   * (millisecs) and a next step number. When each animation completes, the
   * next step is then called in the sequence. For some animations we don't
   * care what the next step is so we set those to 99 (which gets caught by
   * the default case).</p>
   * 
   * @param stepNumber the animations to perform in this step. 
   */
  public void creditsSequence(int stepNumber)
  {
    switch (stepNumber)
      {
      case 1:
        creditsScreenC3D.zoomIntoView(3000, 99);
        break;
      case 2:
        creditsScreenC3D.zoomOutOfView(1000,3);
        break;
      case 3:
        creditsScreenC3D.setVisible(false);
        frame3d.changeEnabled(false);
        break;
      default:
        // System.err.println(
        //  "@@ kwebdemo1 Application creditsSequence DEFAULT step: " +
        //  stepNumber);
      }
  }
  
  /**
   * Sequences through the animation steps during search dialog.
   * Each step is controlled by animation completion events in the LG event
   * system.
   * 
   * <p><em>Implementation:</em> Each animation step specifies a duration
   * (millisecs) and a next step number. When each animation completes, the
   * next step is then called in the sequence. For some animations we don't
   * care what the next step is so we set those to 99 (which gets caught by
   * the default case).</p>
   * 
   * @param stepNumber the animations to perform in this step. 
   */
  public void searchDialogSequence(int stepNumber)
  {
    switch (stepNumber)
      {
      case 1:
      	journeyIdx = 0;
        searchDialogC3D.zoomIntoView(1000, 99);
        break;
      case 2: // case when search dialog not completed
        searchDialogC3D.zoomOutOfView(1000, 3);
        break;
      case 3:
        searchDialogC3D.setVisible(false);
        break;
      case 4: // case when search dialog is completed
        searchDialogC3D.zoomOutOfView(1000, 5);
        break;
      case 5: // zoom into Mozart
        searchDialogC3D.setVisible(false);
        kwebGraphC3D.zoomInMore(3000, 6);
        break;
      case 6:
        kwebGraphC3D.rotateToNode(1168 /* Mozart */, 5000, 7);
        break;
      case 7:
        kwebGraphC3D.highlightNode(1168 /* Mozart */, true);  
        kwebGraphC3D.zoomBack(3000, 99);
        break;
      case 8:  // follows step 2 or 3 in the nodeDialogSequence
        nodeDialogC3D.setVisible(false);
        journeyIdx = 1;
        break;
      case 9: // follows step 2 in the bioDialogSequence
        bioDialogC3D.setVisible(false);
        kwebGraphC3D.highlightNode(1168 /* Mozart */, false);  
        kwebGraphC3D.zoomInMore(3000, 10);
        break;
      case 10:  
        kwebGraphC3D.rotateToNode(114 /* Beaumarchais */, 5000, 11);
        break;
      case 11:
        kwebGraphC3D.highlightNode(114 /* Beaumarchais */, true);
        kwebGraphC3D.zoomBack(3000, 99);
        journeyIdx = 2;
        break;
      default:
        // System.err.println(
        //  "@@ kwebdemo1 Application creditsSequence DEFAULT step: " +
        //  stepNumber);
      }
  }
  
  /**
   * Sequences through the animation steps during search dialog.
   * Each step is controlled by animation completion events in the LG event
   * system.
   * 
   * <p><em>Implementation:</em> Each animation step specifies a duration
   * (millisecs) and a next step number. When each animation completes, the
   * next step is then called in the sequence. For some animations we don't
   * care what the next step is so we set those to 99 (which gets caught by
   * the default case).</p>
   * 
   * @param stepNumber the animations to perform in this step. 
   */
  public void journeyDialogSequence(int stepNumber)
  {
    switch (stepNumber)
      {
      case 1:
        journeyDialogC3D.zoomIntoView(1000, 99);
        break;
      case 2:
        journeyDialogC3D.zoomOutOfView(1000, 3);
        break;
      case 3:
        journeyDialogC3D.setVisible(false);
        break;
      default:
        // System.err.println(
        //  "@@ kwebdemo1 Application creditsSequence DEFAULT step: " +
        //  stepNumber);
      }
  }
  
  /**
   * Sequences through the animation steps during node dialog.
   * Each step is controlled by animation completion events in the LG event
   * system.
   * 
   * <p><em>Implementation:</em> Each animation step specifies a duration
   * (millisecs) and a next step number. When each animation completes, the
   * next step is then called in the sequence. For some animations we don't
   * care what the next step is so we set those to 99 (which gets caught by
   * the default case).</p>
   * 
   * @param stepNumber the animations to perform in this step. 
   */
  public void nodeDialogSequence(int stepNumber, int kNodeId, BasicNode knode)
  {
    // System.err.println("@@ nodeDialogSequence(" + stepNumber + ")");
    switch (stepNumber)
      {
      case 1:
        nodeDialogC3D.zoomIntoView(3000, 99, knode);
        // Goes to step 99 in searchSequence, not here
        break;
      case 2: // case when Mozart dialog is completed
        nodeDialogC3D.zoomOutOfView(1000, 8);
        // Goes to step 8 in searchSequence, not here
        if (kNodeId == 1168 /* Mozart */)
          bioDialogSequence(1, kNodeId, knode);  
        break;
      case 3: // case when no info dialog is completed  
        nodeDialogC3D.zoomOutOfView(1000, 8);
        // Goes to step 8 in searchSequence, not here
        break;
      default:
        // System.err.println(
        //  "@@ kwebdemo1 Application creditsSequence DEFAULT step: " +
        //  stepNumber);
      }
  }
  
  /**
   * Sequences through the animation steps during bio dialog.
   * Each step is controlled by animation completion events in the LG event
   * system.
   * 
   * <p><em>Implementation:</em> Each animation step specifies a duration
   * (millisecs) and a next step number. When each animation completes, the
   * next step is then called in the sequence. For some animations we don't
   * care what the next step is so we set those to 99 (which gets caught by
   * the default case).</p>
   * 
   * @param stepNumber the animations to perform in this step. 
   */
  public void bioDialogSequence(int stepNumber, int kNodeId, BasicNode knode)
  {
    // System.err.println("@@ nodeDialogSequence(" + stepNumber + ")");
    switch (stepNumber)
      {
      case 1:
        bioDialogC3D.zoomIntoView(10000, 99, knode);
        // Goes to step 99 in searchSequence, not here
        break;
      case 2: // case when Mozart bio is completed
        bioDialogC3D.zoomOutOfView(3000, 9);
        // Goes to step 9 in searchSequence, not here
        break;
      default:
        // System.err.println(
        //  "@@ kwebdemo1 Application creditsSequence DEFAULT step: " +
        //  stepNumber);
      }
  }
  
  /**
   * Handles mouse button clicks for the main application buttons.
   * 
   * @param buttonName like "Search", "Journey", "Timeline", "Location" and
   * "Close".
   */
  public void handleButtonClick(String buttonName)
  {
    // System.err.println("@@ Button " + buttonName + " was clicked");
    if (buttonName.equals("Search"))
      {
        kwebGraphC3D.stopSlowRotation();
        searchDialogSequence(1);
      }
    else if (buttonName.equals("Journey"))
      {
        journeyDialogSequence(1);
      }
    else if (buttonName.equals("Timeline"))
      {	
        kwebGraphC3D.highlightNode(114, false);
        kwebGraphC3D.stopSlowRotation();
      }
    else if (buttonName.equals("Location"))
      {
        kwebGraphC3D.startSlowRotation();
      }
    else if (buttonName.equals("Close"))
      {	
        creditsSequence(1);
      }
  }
  
  /**
   * Causes main thead to sleep for the specified milliseconds.
   * 
   * This is a kludge and should be avoided whenever possible as it makes the
   * application non-responsive to events.  But, it's useful in certain limited
   * situations.
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
  
  /**
   * Application entry point.
   * 
   * @param args ignored
   */
  public static void main(String[] args)
  {
    new Application();
  }

  /**
   * This is just a placeholder thumbnail until I figure out something better.
   */
  private class KWebThumbnail extends Thumbnail
  {
    private SimpleAppearance app;
      
    private KWebThumbnail()
    {
      app = new SimpleAppearance(1.0f, 1.0f, 1.0f, 1.0f,
                                 SimpleAppearance.ENABLE_TEXTURE
                                 | SimpleAppearance.DISABLE_CULLING);
      try
        {
          app.setTexture(getClass().getResource("/org/jdesktop/lg3d/apps/kwebdemo1/resources/images/icon/GeoR_64x64.png"));
        }
      catch (Exception e)
        {
          throw new RuntimeException(
            "failed to load thumbnail image: " + e);
        }
      Shape3D kwebThumbS3D = new Disc(0.015f, 36, app);
      // kwebThumbS3D.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
      addChild(kwebThumbS3D);
      addChild(new RingShadow(0.016f, 0.015f, 36, 0.3f));
      setPreferredSize(new Vector3f(0.015f, 0.015f, 0f));
    }
  }

}
