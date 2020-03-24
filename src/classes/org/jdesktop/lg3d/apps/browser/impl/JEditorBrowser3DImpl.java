package org.jdesktop.lg3d.apps.browser.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

import org.jdesktop.lg3d.apps.browser.Browser3D;
import org.jdesktop.lg3d.apps.browser.Browser3DUIAdapter;

public class JEditorBrowser3DImpl extends Browser3D
{

  JEditorPane browser = null;

  JScrollPane scroll = null;

  public JEditorBrowser3DImpl()
  {
    browser = new JEditorPane();
    browser.setEditable(false);
    browser.setContentType("text/html");
    browser.addHyperlinkListener(new Hyperactive());

    scroll = new JScrollPane();
    scroll.setViewportView(browser);

  }// end JEditorBrowser3DImpl

  /**
   * @return
   */
  public JComponent getBrowserPanel()
  {
    return scroll;
  }// end getBrowserPanel

  class Hyperactive implements HyperlinkListener
  {
    public void hyperlinkUpdate(HyperlinkEvent e)
    {

      if (e.getEventType() == HyperlinkEvent.EventType.ENTERED)
      {
        fireStatusChange(e.getURL().toString());
      }
      else if (e.getEventType() == HyperlinkEvent.EventType.EXITED)
      {
        fireStatusChange("");
      }
      else if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
      {
        JEditorPane pane = (JEditorPane) e.getSource();
        if (e instanceof HTMLFrameHyperlinkEvent)
        {
          HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
          HTMLDocument doc = (HTMLDocument) pane.getDocument();
          doc.processHTMLFrameHyperlinkEvent(evt);          
        }
        else
        {
          try
          {
            pane.setPage(e.getURL());
            fireURLChanged( e.getURL().toString() );
          }
          catch (Throwable t)
          {
            t.printStackTrace();
          }
        }
      }
    }

  }
  
  private void fireURLChanged( String url )
  {
    ArrayList adapters = getBrowser3DUIAdapters();
    
    for( Iterator iter = adapters.iterator(); iter.hasNext() ; )
    {
      Browser3DUIAdapter adapter = (Browser3DUIAdapter)iter.next();
      adapter.urlChanged( url );
    }// end for
    
    // how do I set title? // this is a hack
    this.fireTitleChanged( url );
  }// end fireURLChanged
  
  private void fireTitleChanged( String title )
  {
    ArrayList adapters = getBrowser3DUIAdapters();
    
    for( Iterator iter = adapters.iterator(); iter.hasNext() ; )
    {
      Browser3DUIAdapter adapter = (Browser3DUIAdapter)iter.next();
      adapter.titleChanged( title );
    }// end for
  }// end fireTitleChanged
  
  private void fireStatusChange( String status )
  {
    ArrayList adapters = getBrowser3DUIAdapters();
    
    for( Iterator iter = adapters.iterator(); iter.hasNext() ; )
    {
      Browser3DUIAdapter adapter = (Browser3DUIAdapter)iter.next();
      adapter.statusChanged( status );
    }// end for
  }// end fireStatusChange

  @Override
  protected void setImplURL(String url)
  {
    // TODO: fix Exception throwing
    try
    {
      browser.setPage(url);
      this.fireURLChanged(url);
    }// end try
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }// end catch

  }// end setURL

  @Override
  protected String getURL()
  {
    return browser.getPage().toString();
  }

  @Override
  protected String getTitle()
  {
    return "Not implemented";
  }
}
