/*
 * Created on Jul 9, 2005
 *
 * Some license info should go here!
 */
package org.jdesktop.lg3d.apps.browser;

import java.io.IOException;

import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.browser.components.Button3D;
import org.jdesktop.lg3d.apps.browser.components.TextField3D;
import org.jdesktop.lg3d.apps.browser.components.TextField3DTest;
import org.jdesktop.lg3d.apps.browser.layouts.BrowserLayout;
import org.jdesktop.lg3d.apps.browser.layouts.ThumbnailLayout;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.Container3D;
import org.jdesktop.lg3d.wg.Cursor3D;
import org.jdesktop.lg3d.wg.Frame3D;
import org.jdesktop.lg3d.wg.Thumbnail;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;

/**
 * 
 * Main class for the 3D Browser.
 * 
 * @author demers
 */
public class Browser3DApp //extends Frame3D
{
  private Frame3D frame = new Frame3D();

  // final private Browser3D browser;
  private Browser3DComponent browser;

  private Container3D browserContainer = new Container3D();

  private Container3D thumbnailContainer = new Container3D();

  private TextField3D textField;

  private Component3D nav;

  /**
   * Creats a new Browser3DApp.
   * 
   * @throws Exception
   *           generic catch all. TODO: should throw each Exception.
   */
  public Browser3DApp() throws Exception
  {

    nav = getNavigationComponent();

    LgEventListener mouseRightClicked = new MouseClickedEventAdapter(MouseButtonEvent3D.ButtonId.BUTTON3, new ActionNoArg()
    {
      public void performAction(LgEventSource source)
      {
        doRightClick(source);
      }
    });

    SimpleAppearance backApp = new SimpleAppearance(1.0f, 1.0f, 1.0f, 5.0f, SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING | SimpleAppearance.NO_GLOSS);

    int width = 800;
    int height = 500;

    Container3D mainContainer = new Container3D();
    browser = new Browser3DComponent(width, height, backApp, null);
    browser.addBrowser3DUIAdapter(new BrowserEventAdapter());
    // kind of a hack need to move the component the first time.
    Vector3f tempVec = new Vector3f();
    browser.getPreferredSize(tempVec);
    browser.setTranslation(0, tempVec.y / 4f, 0);

    // Browser3DComponent fakeComp1 = new Browser3DComponent( width, height,
    // backApp, null );

    Browser3DComponent fakeComp1 = new Browser3DComponent(width, height, backApp, getClass().getResource("/org/jdesktop/lg3d/apps/browser/images/loonmtn.png"));
    Browser3DComponent fakeComp2 = new Browser3DComponent(width, height, backApp, getClass().getResource("/org/jdesktop/lg3d/apps/browser/images/nin.png"));
    Browser3DComponent fakeComp3 = new Browser3DComponent(width, height, backApp, getClass().getResource("/org/jdesktop/lg3d/apps/browser/images/redhat.png"));
    Browser3DComponent fakeComp4 = new Browser3DComponent(width, height, backApp, getClass().getResource("/org/jdesktop/lg3d/apps/browser/images/slashdot.png"));
    Browser3DComponent fakeComp5 = new Browser3DComponent(width, height, backApp, getClass().getResource("/org/jdesktop/lg3d/apps/browser/images/suse.png"));
    Browser3DComponent fakeComp6 = new Browser3DComponent(width, height, backApp, getClass().getResource("/org/jdesktop/lg3d/apps/browser/images/weather.png"));

    browserContainer.setLayout(new BrowserLayout());
    thumbnailContainer.setLayout(new ThumbnailLayout());

    mainContainer.addChild(browserContainer);
    mainContainer.addChild(thumbnailContainer);

    browser.addListener(mouseRightClicked);
    fakeComp1.addListener(mouseRightClicked);
    fakeComp2.addListener(mouseRightClicked);
    fakeComp3.addListener(mouseRightClicked);
    fakeComp4.addListener(mouseRightClicked);
    fakeComp5.addListener(mouseRightClicked);
    fakeComp6.addListener(mouseRightClicked);

    this.browserContainer.addChild(browser);
    this.thumbnailContainer.addChild(fakeComp1);
    this.thumbnailContainer.addChild(fakeComp2);
    this.thumbnailContainer.addChild(fakeComp3);
    this.thumbnailContainer.addChild(fakeComp4);
    this.thumbnailContainer.addChild(fakeComp5);
    this.thumbnailContainer.addChild(fakeComp6);

    this.frame.addChild(mainContainer);

    this.frame.addChild(nav);
//    Thumbnail thumb = new Thumbnail();
//    thumb.addChild(nav);
////    thumb = Thumbnail.DEFAULT;
////    thumb.addChild(nav);
//    this.frame.setThumbnail(thumb);
    
    Vector3f pos = new Vector3f();
    pos = new Vector3f();
    nav.getTranslation(pos);
    nav.changeTranslation(pos.x, pos.y - 0.1f, pos.z);

    this.frame.setCursor(Cursor3D.SMALL_CURSOR);
    
  }// end Browser3DApp

  /**
   * Minimizes a Browser3DCompoent
   * 
   * @param source
   *          Browser3DCompoent to be minimized
   */
  public void doRightClick(LgEventSource source)
  {
    // make sure the source is a Component3D
    if (source instanceof Browser3DComponent)
    {
      Browser3DComponent comp = (Browser3DComponent) source;

      comp.setMaximized(!comp.isMaximized());

      this.browserContainer.removeChild(comp);
      this.thumbnailContainer.removeChild(comp);

      if (comp.isMaximized())
      {
        this.browserContainer.addChild(comp);
      }// end if
      else
      {
        this.thumbnailContainer.addChild(comp);
      }// end else

      this.thumbnailContainer.revalidate();
      this.browserContainer.revalidate();

    }// end if
  }// end doRightClick

  /**
   * Sets the Frame3D to be visible
   */
  public void showFrame3D()
  {
    this.frame.changeEnabled(true);
    this.frame.changeVisible(true);
  }// end showFrame3D

  /**
   * Creates a Navigation Component
   * 
   * TODO: Move this into its own class
   * 
   * @return the Navigation Component
   */
  private Component3D getNavigationComponent()
  {
    Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();

    float physicalWidth = toolkit3d.widthNativeToPhysical(600);
    float physicalHeight = toolkit3d.heightNativeToPhysical(90);

    float iconWidth = toolkit3d.widthNativeToPhysical(85);
    float iconHeight = toolkit3d.heightNativeToPhysical(85);

    float textFieldWidth = toolkit3d.widthNativeToPhysical(300);
    float textFieldHeight = toolkit3d.heightNativeToPhysical(50);

    Component3D comp = new Component3D();

    SimpleAppearance backApp = new SimpleAppearance(1.0f, 1.0f, 1.0f, 0.9f, SimpleAppearance.ENABLE_TEXTURE | SimpleAppearance.DISABLE_CULLING);
    GlassyPanel glass = new GlassyPanel(physicalWidth, physicalHeight, 0.001f, 0.0f, backApp);
    Component3D glassComp = new Component3D();
    glassComp.addChild(glass);

    textField = new TextField3D("http://google.com/", textFieldWidth, textFieldHeight);

    Button3D leftButton = null;
    Button3D rightButton = null;
    Button3D goButton = null;

    try
    {
      leftButton = new Button3D(getClass().getResource("/org/jdesktop/lg3d/apps/browser/images/button/Left.png"), iconWidth, iconHeight);
      rightButton = new Button3D(getClass().getResource("/org/jdesktop/lg3d/apps/browser/images/button/Right.png"), iconWidth, iconHeight);
      goButton = new Button3D(getClass().getResource("/org/jdesktop/lg3d/apps/browser/images/button/Go.png"), iconWidth / 2f, iconHeight / 2f);

      leftButton.addListener(new MouseClickedEventAdapter(MouseButtonEvent3D.ButtonId.BUTTON1, new ActionNoArg()
      {
        public void performAction(LgEventSource source)
        {
          if (browser.getHistoryTracker().hasBack())
          {
            HistoryItem back = browser.getHistoryTracker().moveBack();

            browser.setURL(back.getUrl());
          }// end if
        }
      }));

      rightButton.addListener(new MouseClickedEventAdapter(MouseButtonEvent3D.ButtonId.BUTTON1, new ActionNoArg()
      {
        public void performAction(LgEventSource source)
        {
          if (browser.getHistoryTracker().hasForward())
          {
            HistoryItem forward = browser.getHistoryTracker().moveForward();

            browser.setURL(forward.getUrl());
          }// end if
        }
      }));

      goButton.addListener(new MouseClickedEventAdapter(MouseButtonEvent3D.ButtonId.BUTTON1, new ActionNoArg()
      {
        public void performAction(LgEventSource source)
        {
          browser.setURL(textField.getText());
        }
      }));

    }// end try
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }// end catch

    glassComp.changeRotationAxis(1f, 0, 0, 0);
    glassComp.changeRotationAngle((float) Math.PI / -2.3f, 0);

    comp.addChild(glassComp);

    comp.addChild(textField);
    comp.addChild(goButton);
    comp.addChild(leftButton);
    comp.addChild(rightButton);

    Vector3f loc = new Vector3f();

    glassComp.getTranslation(loc);
    glassComp.setTranslation(loc.x, loc.y - iconHeight / 3.33f, loc.z);

    goButton.getTranslation(loc);
    goButton.setTranslation(loc.x + textFieldWidth / 2f + iconWidth / 2f, loc.y, loc.z + 0.01f);

    leftButton.getTranslation(loc);
    leftButton.setTranslation(loc.x - (physicalWidth / 2f) + (iconWidth / 2f), loc.y, loc.z + 0.01f);

    rightButton.getTranslation(loc);
    rightButton.setTranslation(loc.x + (physicalWidth / 2f) - (iconWidth / 2f), loc.y, loc.z + 0.01f);

    comp.setPreferredSize(new Vector3f(physicalWidth, physicalHeight, 0.001f));

    return comp;
  }// end add Navigation


  /**
   * 
   * Only used to set the URL on the navigation control
   * 
   * @author demers
   */
  class BrowserEventAdapter implements Browser3DUIAdapter
  {
    public BrowserEventAdapter()
    {}// end BrowserEventAdapter

    public void titleChanged(String title)
    {}

    public void statusChanged(String status)
    {}

    public void urlChanged(String url)
    {
      textField.setText(url);
    }
  }
  

  /**
   * Kicks off a new Browser3D and loads the Frame
   * 
   * @param args
   */
  public static void main(String[] args) throws Exception
  { 
//    try
//    {
      Browser3DApp app = new Browser3DApp();
      app.showFrame3D();
      
//    TextField3DTest.main(args);
    
//    }// end try
//    catch (Throwable e)
//    {
//      e.printStackTrace();
//    }// end catch
  }
  
}
