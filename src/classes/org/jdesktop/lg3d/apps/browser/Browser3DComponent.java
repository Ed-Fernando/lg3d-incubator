/*
 * Created on Aug 9, 2005
 *
 * Some license info should go here!
 */
package org.jdesktop.lg3d.apps.browser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.JPanel;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.jdesktop.lg3d.apps.browser.components.Button3D;
import org.jdesktop.lg3d.apps.browser.components.Label3D;
import org.jdesktop.lg3d.apps.browser.components.NonTransparentSwingNodeRenderer;
import org.jdesktop.lg3d.utils.action.ActionNoArg;
import org.jdesktop.lg3d.utils.c3danimation.NaturalMotionAnimation;
import org.jdesktop.lg3d.utils.eventaction.Component3DMover;
import org.jdesktop.lg3d.utils.eventadapter.MouseClickedEventAdapter;
import org.jdesktop.lg3d.utils.shape.GlassyPanel;
import org.jdesktop.lg3d.utils.shape.ImagePanel;
import org.jdesktop.lg3d.utils.shape.SimpleAppearance;
import org.jdesktop.lg3d.wg.Component3D;
import org.jdesktop.lg3d.wg.SwingNode;
import org.jdesktop.lg3d.wg.Toolkit3D;
import org.jdesktop.lg3d.wg.event.LgEvent;
import org.jdesktop.lg3d.wg.event.LgEventListener;
import org.jdesktop.lg3d.wg.event.LgEventSource;
import org.jdesktop.lg3d.wg.event.MouseButtonEvent3D;
import org.jdesktop.lg3d.wg.event.MouseEvent3D;


/**
 * Component used to wrapp a Browser3D.  It will grab Browser3D objects from
 * the Browser3DFactory.
 * 
 * @author demers
 */

public class Browser3DComponent extends Component3D
{
  private Label3D label;
  private Browser3D browser;
  private Component3D tabComponent;
  private SwingNode browserComp;
  private JPanel hackPanel;
  
  private boolean maximized = false;
  
  
  /**
   * Constucts a Browser3D, only one Browser3DComponent will work,  This is limitaion do to the SwingNode
   * BlackGoat got 2 different SwingNodes to work.  It should be possible
   * TODO: change width and height to floats
   * 
   * @param width width for the Browser3D
   * @param height height for Browser3D
   * @param appearance for the control
   * @param fileName used to create fake browsers using only a Image Panel
   * @throws ClassNotFoundException if Browser3D class could not be loaded from Browser.properties
   * @throws IOException if window-close.png could not be found
   */
  public Browser3DComponent( int width, int height, SimpleAppearance appearance, URL fileName ) throws ClassNotFoundException, IOException
  {
    Toolkit3D toolkit3d = Toolkit3D.getToolkit3D();
    
    float physicalWidth = toolkit3d.widthNativeToPhysical( width );
    float physicalHeight = toolkit3d.heightNativeToPhysical( height );
    
    float labelWidth = toolkit3d.widthNativeToPhysical( 300 );
    float labelHeight = toolkit3d.widthNativeToPhysical( 50 );
    
    float tabWidth = labelWidth;
    float tabHeight = 0.006f;
    
    // this is the title Tab
    GlassyPanel tabHandle = new GlassyPanel(tabWidth, tabHeight, 0.001f, 0.0f, appearance);
    tabComponent = new Component3D();
    tabComponent.addChild( tabHandle );
    
    float buttonWidth = tabHeight - 0.0015f;
    
    Button3D closeButton = new Button3D( getClass().getResource("/resources/images/button/window-close.png"), buttonWidth, buttonWidth );
    closeButton.addListener(new MouseClickedEventAdapter(
        MouseButtonEvent3D.ButtonId.BUTTON1, new ActionNoArg()
        {
          public void performAction(LgEventSource source)
          {
            doCloseButton();
          }
        }));
    
    Vector3f pos = new Vector3f();
    tabComponent.getTranslation(pos);
    closeButton.setTranslation( pos.x + (tabWidth/2f) - (buttonWidth/2f) , pos.y, pos.z );
    tabComponent.addChild(closeButton);
    
    // hack to make the label work right
    String labelValue = fileName == null ? "http://www.google.com": fileName.toExternalForm();
    // add the label to the tab
    label = new Label3D(labelValue, labelWidth, labelHeight);
    tabComponent.addChild( label );
    
    GlassyPanel glassPanel = new GlassyPanel(physicalWidth, physicalHeight, 0.001f, 0.0f, appearance);
    
    if( fileName == null )
    {
      this.addBrowser(width, height);
    }// end if
    else
    {
      ImagePanel image = new ImagePanel(fileName, physicalWidth, physicalHeight);
      this.addChild( image );
    }// end else
    
    this.addChild( glassPanel );    
    this.addChild( tabComponent );
    
    tabComponent.getTranslation(pos);
    float tabPositionX = (physicalWidth/-2f) + (tabWidth/2f);
    float tabPositionY = (physicalHeight/2f) + (tabHeight/2f);
    
    tabComponent.changeTranslation(tabPositionX, tabPositionY, pos.z);
    
    label.getTranslation( pos );
    label.changeTranslation( pos.x+0.003f, pos.y, pos.z );
    
    
    Vector3f size = new Vector3f( physicalWidth, physicalHeight+tabHeight, 0.001f  );
    this.setPreferredSize( size ); 
    this.setAnimation( new NaturalMotionAnimation(300) );
  }
  
  
  /**
   * Adds the Browser3D object's panel to a SwingNode.  
   * TODO: this needs to be refactored because a Browser3D should contain a 
   * Component3D not a JPanel.
   * 
   * @param width browser width
   * @param height browser height
   * @throws ClassNotFoundException thrown if Browser3D can not be loaded from 
   * the Browser3DFactory
   */
  private void addBrowser( int width, int height ) throws ClassNotFoundException
  {
    browserComp = new SwingNode(new NonTransparentSwingNodeRenderer());
    
    browser = Browser3DFactroy.getBrowser3D();
    browser.addBrowser3DUIAdapter(new TestAdapter());
    browser.addBrowser3DUIAdapter( new BrowserURLEventAdapter() );
//    browser.setURL("http://www.google.com");
    
    hackPanel = new JPanel();
    hackPanel.setPreferredSize(new Dimension(width, height));
    hackPanel.setLayout(new BorderLayout());
    hackPanel.add(browser.getBrowserPanel(), BorderLayout.CENTER);

    browserComp.setPanel(hackPanel);
    
    this.addChild( browserComp );
  }// end addBrowser()
  
  
  /**
   * closes the Browser Component.
   */
  public void doCloseButton()
  {
    this.detach();    
  }// end doCloseButton
  
  
  /**
   * Add a Browser3DUIAdapter
   * 
   * @param adapter to be added
   */
  public void addBrowser3DUIAdapter( Browser3DUIAdapter adapter )
  {
    browser.addBrowser3DUIAdapter( adapter );
  }
  
  /**
   * Remove a Browser3DUIAdapter
   * @param adapter to be removed
   */
  public void removeBrowser3DUIAdapter( Browser3DUIAdapter adapter )
  {
    browser.removeBrowser3DUIAdapter( adapter );
  }
  
  /**
   * Sets the text on the Tab Handle
   * @param text Web page title
   */
  public void setLabel(String text)
  {
    this.label.setText(text);
  }// end setLabel
  
  /**
   * Gets the text of the Tab Handle
   * @return web page title
   */
  public String getLabel()
  {
    return label.getText();
  }// end getLabel
  
  /**
   * Change the web page
   * @param url url to change the web page too. 
   */
  public void setURL( String url )
  {
    browser.setURL(url);
  }// end setURL
  
  /**
   * returns the History Tracker associated with this browser
   * @return the History Tracker for this object
   */
  public HistoryTracker getHistoryTracker()
  {
    return browser.getHistoryTracker();
  }

  // TODO: refactor this code this should not be needed
  /**
   * is component maximized
   * @return true if not a thumbnail
   */
  public boolean isMaximized()
  {
    return maximized;
  }

  /**
   * set to true if component is not a thumbnail
   * @param maximized true if not a thumbnail
   */
  public void setMaximized(boolean maximized)
  {
    this.maximized = maximized;
  }
  
  /**
   * Only used to set the title of the webpage
   */
  class BrowserURLEventAdapter implements Browser3DUIAdapter
  {

    /* (non-Javadoc)
     * @see org.jdesktop.lg3d.apps.browser.Browser3DUIAdapter#titleChanged(java.lang.String)
     */
    public void titleChanged(String title)
    {
      label.setText(title);      
    }

    /* (non-Javadoc)
     * @see org.jdesktop.lg3d.apps.browser.Browser3DUIAdapter#statusChanged(java.lang.String)
     */
    public void statusChanged(String status)
    {
    }

    /* (non-Javadoc)
     * @see org.jdesktop.lg3d.apps.browser.Browser3DUIAdapter#urlChanged(java.lang.String)
     */
    public void urlChanged(String url)
    {
    }
    
  }// BrowserURLEventAdapter

}
