package org.jdesktop.lg3d.apps.browser.impl;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;

import org.jdesktop.lg3d.apps.browser.Browser3D;
import org.jdesktop.lg3d.apps.browser.Browser3DUIAdapter;

import com.icesoft.browser.bean.Browser;
import com.icesoft.browser.bean.event.BrowserUIAdapter;
import com.icesoft.browser.bean.event.BrowserUIEvent;

/**
 * <p>
 * </p>
 * 
 */
public class ICEBrowser3DImpl extends Browser3D
{

  private Browser browser = new Browser();

  private String currentURL = "";
  
  private String currentTitle = "";
  
  public ICEBrowser3DImpl()
  {
    browser.addBrowserUIListener( new BrowserBrowserUIListener() );
  }// end ICEBrowser3DImpl

  /**
   * @return
   */
  public JComponent getBrowserPanel()
  {
    return browser;
  }
  
  @Override
  protected void setImplURL(String url)
  {
    this.browser.renderLocation( url );
  }
  
  @Override
  protected String getURL()
  {
    return this.currentURL;
  }

  @Override
  protected String getTitle()
  {
    return currentTitle;
  }

  private class BrowserBrowserUIListener extends BrowserUIAdapter
  {
    public void titleChanged(BrowserUIEvent event)
    {
      final String newTitle = event.getValue();
      
      currentTitle = newTitle;
      
      ArrayList adapters = getBrowser3DUIAdapters();
      
      for( Iterator iter = adapters.iterator(); iter.hasNext() ; )
      {
        Browser3DUIAdapter adapter = (Browser3DUIAdapter)iter.next();
        adapter.titleChanged( newTitle );
      }// end for
    }

    public void locationChanged(BrowserUIEvent event)
    {
      final String location = event.getValue();
      
      currentURL = location;
      
      ArrayList adapters = getBrowser3DUIAdapters();
      
      for( Iterator iter = adapters.iterator(); iter.hasNext() ; )
      {
        Browser3DUIAdapter adapter = (Browser3DUIAdapter)iter.next();
        adapter.urlChanged( location );
      }// end for
    }// end locationChanged

    public void statuslineChanged(BrowserUIEvent event)
    {
      final String statusMessage = event.getValue();
      ArrayList adapters = getBrowser3DUIAdapters();
      
      for( Iterator iter = adapters.iterator(); iter.hasNext() ; )
      {
        Browser3DUIAdapter adapter = (Browser3DUIAdapter)iter.next();
        adapter.statusChanged( statusMessage );
      }// end for
    }
  } // end BrowserBrowserUIListener



}